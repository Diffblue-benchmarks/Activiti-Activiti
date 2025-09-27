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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.UnsupportedEncodingException;
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
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.Signal;
import org.activiti.editor.language.json.converter.BpmnJsonConverter.FlowWithContainer;
import org.activiti.editor.language.json.model.ModelInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnJsonConverterDiffblueTest {
  /**
   * Test FlowWithContainer getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link FlowWithContainer#FlowWithContainer(BpmnJsonConverter, SequenceFlow,
   *       FlowElementsContainer)}
   *   <li>{@link FlowWithContainer#setFlowContainer(FlowElementsContainer)}
   *   <li>{@link FlowWithContainer#setSequenceFlow(SequenceFlow)}
   *   <li>{@link FlowWithContainer#getFlowContainer()}
   *   <li>{@link FlowWithContainer#getSequenceFlow()}
   * </ul>
   */
  @Test
  @DisplayName("Test FlowWithContainer getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FlowWithContainer.<init>(BpmnJsonConverter, SequenceFlow, FlowElementsContainer)",
    "FlowElementsContainer FlowWithContainer.getFlowContainer()",
    "SequenceFlow FlowWithContainer.getSequenceFlow()",
    "void FlowWithContainer.setFlowContainer(FlowElementsContainer)",
    "void FlowWithContainer.setSequenceFlow(SequenceFlow)"
  })
  void testFlowWithContainerGettersAndSetters() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    SequenceFlow sequenceFlow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    FlowWithContainer actualFlowWithContainer =
        bpmnJsonConverter.new FlowWithContainer(sequenceFlow, new AdhocSubProcess());
    AdhocSubProcess flowContainer = new AdhocSubProcess();
    actualFlowWithContainer.setFlowContainer(flowContainer);
    SequenceFlow sequenceFlow2 = new SequenceFlow("Source Ref", "Target Ref");
    actualFlowWithContainer.setSequenceFlow(sequenceFlow2);
    FlowElementsContainer actualFlowContainer = actualFlowWithContainer.getFlowContainer();

    // Assert
    assertSame(flowContainer, actualFlowContainer);
    assertSame(sequenceFlow2, actualFlowWithContainer.getSequenceFlow());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode,
   * Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link AdhocSubProcess#getArtifacts()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer,
   * BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList(); then calls getArtifacts()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElements_givenArrayList_thenCallsGetArtifacts() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());
    when(container.getFlowElements()).thenReturn(new ArrayList<>());
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(
        container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(container).getArtifacts();
    verify(container).getFlowElements();
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode,
   * Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Then calls {@link Association#getSourceRef()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer,
   * BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); then calls getSourceRef()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElements_thenCallsGetSourceRef() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    Association artifact = mock(Association.class);
    when(artifact.getSourceRef()).thenReturn("not empty");
    when(artifact.getId()).thenReturn("42");
    doNothing().when(artifact).setSourceRef(Mockito.<String>any());
    artifact.setSourceRef("not empty");

    AdhocSubProcess container = new AdhocSubProcess();
    container.addArtifact(artifact);

    BpmnModel model = new BpmnModel();
    model.addProcess(new Process());
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(
        container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(artifact).getSourceRef();
    verify(artifact).setSourceRef("not empty");
    verify(artifact).getId();
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode,
   * Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Then calls {@link BusinessRuleTask#setBoundaryEvents(List)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer,
   * BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); then calls setBoundaryEvents(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElements_thenCallsSetBoundaryEvents() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    BusinessRuleTask element = mock(BusinessRuleTask.class);
    when(element.getId()).thenReturn(null);
    doNothing().when(element).setBoundaryEvents(Mockito.<List<BoundaryEvent>>any());
    doNothing().when(element).setDataInputAssociations(Mockito.<List<DataAssociation>>any());
    doNothing().when(element).setDataOutputAssociations(Mockito.<List<DataAssociation>>any());
    doNothing()
        .when(element)
        .setLoopCharacteristics(Mockito.<MultiInstanceLoopCharacteristics>any());
    doNothing().when(element).setDocumentation(Mockito.<String>any());
    doNothing().when(element).setName(Mockito.<String>any());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());
    doNothing().when(element).setExclusive(anyBoolean());
    doNothing().when(element).setOutgoingFlows(Mockito.<List<SequenceFlow>>any());
    element.setName("not empty");
    element.setDocumentation("not empty");
    element.setOutgoingFlows(outgoingFlows);
    element.setBoundaryEvents(boundaryEvents);
    element.setExclusive(false);
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(dataInputAssociations);
    element.setDataOutputAssociations(dataOutputAssociations);

    Association artifact = new Association();
    artifact.setSourceRef("not empty");

    AdhocSubProcess container = new AdhocSubProcess();
    container.addFlowElement(element);
    container.addArtifact(artifact);

    BpmnModel model = new BpmnModel();
    model.addProcess(new Process());
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(
        container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(element).setBoundaryEvents(isA(List.class));
    verify(element).setDataInputAssociations(isA(List.class));
    verify(element).setDataOutputAssociations(isA(List.class));
    verify(element).setLoopCharacteristics(isNull());
    verify(element, atLeast(1)).getId();
    verify(element).setDocumentation("not empty");
    verify(element).setName("not empty");
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).setExclusive(false);
    verify(element).setOutgoingFlows(isA(List.class));
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    AdhocSubProcess flowElement = new AdhocSubProcess();
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    MessageFlow messageFlow = new MessageFlow("BoundaryTimerEvent", "BoundaryTimerEvent");

    HashMap<String, MessageFlow> stringMessageFlowMap = new HashMap<>();
    stringMessageFlowMap.put("BoundaryTimerEvent", messageFlow);
    when(model.getMessageFlows()).thenReturn(stringMessageFlowMap);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link AdhocSubProcess}.
   *   <li>Then calls {@link BpmnModel#getGraphicInfo(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given '42'; when AdhocSubProcess; then calls getGraphicInfo(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_given42_whenAdhocSubProcess_thenCallsGetGraphicInfo() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = mock(AdhocSubProcess.class);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, null, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement, atLeast(1)).getId();
    verify(model).getGraphicInfo("42");
    verify(flowElement).getEventDefinitions();
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenAdhocSubProcess() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");
    when(flowElement.getAttachedToRef()).thenReturn(new AdhocSubProcess());
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add ActivitiListener (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenArrayListAddActivitiListener() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());
    when(flowElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");
    when(flowElement.getAttachedToRef()).thenReturn(new AdhocSubProcess());
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ {\n"
            + "        \"event\" : null\n"
            + "      } ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Association} (default constructor).
   *   <li>Then calls {@link AdhocSubProcess#getArtifacts()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add Association (default constructor); then calls getArtifacts()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenArrayListAddAssociation_thenCallsGetArtifacts() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    when(container.getArtifacts()).thenReturn(artifactList);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add CancelEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenArrayListAddCancelEventDefinition() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getId()).thenReturn(null);
    when(flowElement.getEventDefinitions()).thenReturn(eventDefinitionList);
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement).getEventDefinitions();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   *   <li>Then calls {@link ServiceTask#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add CancelEventDefinition (default constructor); then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenArrayListAddCancelEventDefinition_thenCallsGetId() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());
    when(flowElement.getEventDefinitions()).thenReturn(eventDefinitionList);
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryCancelEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenArrayListAddNull() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(null);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");
    when(flowElement.getAttachedToRef()).thenReturn(new AdhocSubProcess());
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true\n"
            + "  }\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link AdhocSubProcess#getArtifacts()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add 'null'; then calls getArtifacts()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenArrayListAddNull_thenCallsGetArtifacts() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(null);
    when(container.getArtifacts()).thenReturn(artifactList);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link SequenceFlow#SequenceFlow(String, String)}
   *       with {@code Source Ref} and {@code Target Ref}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add SequenceFlow(String, String) with 'Source Ref' and 'Target Ref'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenArrayListAddSequenceFlowWithSourceRefAndTargetRef() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    SequenceFlow sequenceFlow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");
    when(flowElement.getAttachedToRef()).thenReturn(new AdhocSubProcess());
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : null\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link Association} {@link Association#getSourceRef()} return {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given Association getSourceRef() return 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenAssociationGetSourceRefReturnNotEmpty() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);

    ArrayList<Artifact> artifactList = new ArrayList<>();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("not empty");
    artifactList.add(association);
    when(container.getArtifacts()).thenReturn(artifactList);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(association, atLeast(1)).getSourceRef();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link Association} {@link Association#getSourceRef()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given Association getSourceRef() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenAssociationGetSourceRefThrowRuntimeException() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);

    ArrayList<Artifact> artifactList = new ArrayList<>();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenThrow(new RuntimeException());
    artifactList.add(association);
    when(container.getArtifacts()).thenReturn(artifactList);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(association).getSourceRef();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  }\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenEmptyString() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When {@link BoundaryEvent} {@link BoundaryEvent#isCancelActivity()} return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given 'false'; when BoundaryEvent isCancelActivity() return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenFalse_whenBoundaryEventIsCancelActivityReturnFalse() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(false);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : false,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link BoundaryEvent} {@link BoundaryEvent#getAttachedToRef()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given 'null'; when BoundaryEvent getAttachedToRef() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenNull_whenBoundaryEventGetAttachedToRefReturnNull() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");
    when(flowElement.getAttachedToRef()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).getEventDefinitions();
    verify(flowElement).getAttachedToRef();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  }\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link BoundaryEvent} {@link BoundaryEvent#getDocumentation()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given 'null'; when BoundaryEvent getDocumentation() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenNull_whenBoundaryEventGetDocumentationReturnNull() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn(null);
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link BoundaryEvent} {@link BoundaryEvent#getName()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given 'null'; when BoundaryEvent getName() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenNull_whenBoundaryEventGetNameReturnNull() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn(null);

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link SequenceFlow#getId()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given SequenceFlow getId() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenSequenceFlowGetIdReturnEmptyString() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ServiceTask} {@link ServiceTask#getId()} return {@code 42}.
   *   <li>Then calls {@link ServiceTask#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ServiceTask getId() return '42'; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenServiceTaskGetIdReturn42_thenCallsGetId() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ServiceTask} {@link ServiceTask#getId()} return {@code 42}.
   *   <li>When {@code null}.
   *   <li>Then calls {@link ServiceTask#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ServiceTask getId() return '42'; when 'null'; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenServiceTaskGetIdReturn42_whenNull_thenCallsGetId() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, null, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  }\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Then calls {@link AdhocSubProcess#getArtifacts()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); then calls getArtifacts()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_thenCallsGetArtifacts() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");

    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(flowElement.getAttachedToRef()).thenReturn(serviceTask);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getExecutionListeners();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(sequenceFlow).getId();
    verify(serviceTask).getId();
    verify(container).getArtifacts();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true,\n"
            + "    \"executionlisteners\" : {\n"
            + "      \"executionListeners\" : [ ]\n"
            + "    }\n"
            + "  },\n"
            + "  \"outgoing\" : [ {\n"
            + "    \"resourceId\" : \"42\"\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); when BoundaryEvent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_whenBoundaryEvent() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    BoundaryEvent flowElement = new BoundaryEvent();
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} {@link BoundaryEvent#getId()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); when BoundaryEvent getId() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_whenBoundaryEventGetIdReturnNull() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getId()).thenReturn(null);
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement).getEventDefinitions();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} {@link BoundaryEvent#getId()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); when BoundaryEvent getId() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_whenBoundaryEventGetIdReturnNull2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getId()).thenReturn(null);
    AdhocSubProcess container = new AdhocSubProcess();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, null, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(flowElement).getId();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} {@link BoundaryEvent#getId()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); when BoundaryEvent getId() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_whenBoundaryEventGetIdThrowRuntimeException() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getId()).thenThrow(new RuntimeException());
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(flowElement).getId();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} {@link BoundaryEvent#getName()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); when BoundaryEvent getName() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_whenBoundaryEventGetNameThrowRuntimeException() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getName()).thenThrow(new RuntimeException());
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement).getName();
    verify(flowElement).getEventDefinitions();
    verify(model).getGraphicInfo("42");
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  }\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} {@link BoundaryEvent#isCancelActivity()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); when BoundaryEvent isCancelActivity() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_whenBoundaryEventIsCancelActivityThrowRuntimeException() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.isCancelActivity()).thenThrow(new RuntimeException());
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");
    when(flowElement.getAttachedToRef()).thenReturn(new AdhocSubProcess());
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).getEventDefinitions();
    verify(flowElement).getAttachedToRef();
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ]\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getMessageFlows()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); when BpmnModel getMessageFlows() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_whenBpmnModelGetMessageFlowsThrowRuntimeException() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    SequenceFlow sequenceFlow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);
    when(flowElement.getOutgoingFlows()).thenReturn(sequenceFlowList);
    when(flowElement.isCancelActivity()).thenReturn(true);
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getName()).thenReturn("not empty");
    when(flowElement.getAttachedToRef()).thenReturn(new AdhocSubProcess());
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenThrow(new RuntimeException());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).isCancelActivity();
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement, atLeast(1)).getEventDefinitions();
    verify(flowElement).getOutgoingFlows();
    verify(flowElement).getAttachedToRef();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    assertFalse(elementsResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    assertFalse(shapesArrayNode.isEmpty());
    Iterator<JsonNode> iteratorResult = shapesArrayNode.iterator();
    JsonNode actualNextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, actualNextResult);
    assertEquals(1, shapesArrayNode.size());
    assertEquals(
        "[ {\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 2.0,\n"
            + "      \"y\" : 3.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : -8.0,\n"
            + "      \"y\" : -7.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : \"42\",\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : \"BoundaryTimerEvent\"\n"
            + "  },\n"
            + "  \"dockers\" : [ {\n"
            + "    \"x\" : 0.0,\n"
            + "    \"y\" : 0.0\n"
            + "  } ],\n"
            + "  \"properties\" : {\n"
            + "    \"overrideid\" : \"42\",\n"
            + "    \"name\" : \"not empty\",\n"
            + "    \"documentation\" : \"not empty\",\n"
            + "    \"cancelactivity\" : true\n"
            + "  }\n"
            + "} ]",
        shapesArrayNode.toPrettyString());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode,
   * double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link AdhocSubProcess#getArtifacts()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer,
   * BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName(
      "Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given ArrayList(); then calls getArtifacts()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"
  })
  void testProcessArtifacts_givenArrayList_thenCallsGetArtifacts() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    bpmnJsonConverter.processArtifacts(container, model, new ArrayNode(nf), 10.0d, 10.0d);

    // Assert
    verify(container).getArtifacts();
  }

  /**
   * Test {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  @DisplayName("Test processMessageFlows(BpmnModel, ArrayNode); given HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverter.processMessageFlows(BpmnModel, ArrayNode)"})
  void testProcessMessageFlows_givenHashMap() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);

    // Act
    bpmnJsonConverter.processMessageFlows(model, shapesArrayNode);

    // Assert that nothing has changed
    verify(model).getMessageFlows();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  @DisplayName(
      "Test processMessageFlows(BpmnModel, ArrayNode); when BpmnModel (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverter.processMessageFlows(BpmnModel, ArrayNode)"})
  void testProcessMessageFlows_whenBpmnModel() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);

    // Act
    bpmnJsonConverter.processMessageFlows(model, shapesArrayNode);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)} with {@code modelNode},
   * {@code formKeyMap}, {@code decisionTableKeyMap}.
   *
   * <p>Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, Map, Map) with 'modelNode', 'formKeyMap', 'decisionTableKeyMap'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnModel BpmnJsonConverter.convertToBpmnModel(JsonNode, Map, Map)"})
  void testConvertToBpmnModelWithModelNodeFormKeyMapDecisionTableKeyMap() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    HashMap<String, String> formKeyMap = new HashMap<>();

    // Act
    BpmnModel actualConvertToBpmnModelResult =
        bpmnJsonConverter.convertToBpmnModel(modelNode, formKeyMap, new HashMap<>());

    // Assert
    Collection<Resource> resources = actualConvertToBpmnModelResult.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = actualConvertToBpmnModelResult.getSignals();
    assertTrue(signals instanceof List);
    assertEquals("http://activiti.org/test", actualConvertToBpmnModelResult.getTargetNamespace());
    assertNull(actualConvertToBpmnModelResult.getEventSupport());
    assertNull(actualConvertToBpmnModelResult.getSourceSystemId());
    assertNull(actualConvertToBpmnModelResult.getStartEventFormTypes());
    assertNull(actualConvertToBpmnModelResult.getUserTaskFormTypes());
    assertNull(actualConvertToBpmnModelResult.getMainProcess());
    assertFalse(actualConvertToBpmnModelResult.hasDiagramInterchangeInfo());
    assertTrue(actualConvertToBpmnModelResult.getMessages().isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getGlobalArtifacts().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getImports().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getInterfaces().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getPools().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getProcesses().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getDataStores().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getDefinitionsAttributes().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getErrors().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getFlowLocationMap().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getItemDefinitions().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getLabelLocationMap().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getLocationMap().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getMessageFlows().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getNamespaces().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverter#convertToBpmnModel(JsonNode)} with {@code modelNode}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then Resources return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode) with 'modelNode'; when valueOf ten; then Resources return List")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnModel BpmnJsonConverter.convertToBpmnModel(JsonNode)"})
  void testConvertToBpmnModelWithModelNode_whenValueOfTen_thenResourcesReturnList() {
    // Arrange and Act
    BpmnModel actualConvertToBpmnModelResult =
        new BpmnJsonConverter().convertToBpmnModel(DoubleNode.valueOf(10.0d));

    // Assert
    Collection<Resource> resources = actualConvertToBpmnModelResult.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = actualConvertToBpmnModelResult.getSignals();
    assertTrue(signals instanceof List);
    assertEquals("http://activiti.org/test", actualConvertToBpmnModelResult.getTargetNamespace());
    assertNull(actualConvertToBpmnModelResult.getEventSupport());
    assertNull(actualConvertToBpmnModelResult.getSourceSystemId());
    assertNull(actualConvertToBpmnModelResult.getStartEventFormTypes());
    assertNull(actualConvertToBpmnModelResult.getUserTaskFormTypes());
    assertNull(actualConvertToBpmnModelResult.getMainProcess());
    assertFalse(actualConvertToBpmnModelResult.hasDiagramInterchangeInfo());
    assertTrue(actualConvertToBpmnModelResult.getMessages().isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getGlobalArtifacts().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getImports().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getInterfaces().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getPools().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getProcesses().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getDataStores().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getDefinitionsAttributes().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getErrors().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getFlowLocationMap().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getItemDefinitions().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getLabelLocationMap().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getLocationMap().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getMessageFlows().isEmpty());
    assertTrue(actualConvertToBpmnModelResult.getNamespaces().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map,
   * Map, BpmnModel)}.
   *
   * <p>Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode,
   * BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"
  })
  void testProcessJsonElements() throws UnsupportedEncodingException {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(
        shapesArrayNode,
        modelNode,
        parentElement,
        shapeMap,
        formMap,
        decisionTableMap,
        new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get("id");
    verify(arrayNode2, atLeast(1)).get("stencil");
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map,
   * Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode,
   * BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); given ArrayList() add valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"
  })
  void testProcessJsonElements_givenArrayListAddValueOfTen_thenCallsIterator() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(
        shapesArrayNode,
        modelNode,
        parentElement,
        shapeMap,
        formMap,
        decisionTableMap,
        new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map,
   * Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode,
   * BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); given ArrayList() iterator; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"
  })
  void testProcessJsonElements_givenArrayListIterator_thenCallsIterator() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayNode shapesArrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(
        shapesArrayNode,
        modelNode,
        parentElement,
        shapeMap,
        formMap,
        decisionTableMap,
        new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map,
   * Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode,
   * BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); given ArrayNode get(String) return valueOf ten; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"
  })
  void testProcessJsonElements_givenArrayNodeGetReturnValueOfTen_thenCallsGet() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(
        shapesArrayNode,
        modelNode,
        parentElement,
        shapeMap,
        formMap,
        decisionTableMap,
        new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get("stencil");
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map,
   * Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode,
   * BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); given ArrayNode get(String) return valueOf ten; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"
  })
  void testProcessJsonElements_givenArrayNodeGetReturnValueOfTen_thenCallsGet2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(
        shapesArrayNode,
        modelNode,
        parentElement,
        shapeMap,
        formMap,
        decisionTableMap,
        new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get("id");
    verify(arrayNode2, atLeast(1)).get("stencil");
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map,
   * Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode,
   * BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); given ArrayNode get(String) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"
  })
  void testProcessJsonElements_givenArrayNodeGetThrowRuntimeException() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenThrow(new RuntimeException());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            bpmnJsonConverter.processJsonElements(
                shapesArrayNode,
                modelNode,
                parentElement,
                shapeMap,
                formMap,
                decisionTableMap,
                new BpmnModel()));
    verify(shapesArrayNode).iterator();
    verify(arrayNode).get("id");
    verify(arrayNode2).get("stencil");
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map,
   * Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode,
   * BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"
  })
  void testProcessJsonElements_thenCallsAsText() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenThrow(new RuntimeException());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode3);

    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            bpmnJsonConverter.processJsonElements(
                shapesArrayNode,
                modelNode,
                parentElement,
                shapeMap,
                formMap,
                decisionTableMap,
                new BpmnModel()));
    verify(shapesArrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get("id");
    verify(arrayNode3).get("stencil");
    verify(arrayNode).asText();
  }

  /**
   * Test new {@link BpmnJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link BpmnJsonConverter}
   */
  @Test
  @DisplayName("Test new BpmnJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverter.<init>()"})
  void testNewBpmnJsonConverter() {
    // Arrange, Act and Assert
    ObjectMapper objectMapper = new BpmnJsonConverter().objectMapper;
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    assertTrue(
        objectMapper.getDeserializationContext() instanceof DefaultDeserializationContext.Impl);
    assertTrue(objectMapper.getVisibilityChecker() instanceof Std);
    assertTrue(objectMapper.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(objectMapper.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(objectMapper.getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(objectMapper.getSerializerProvider() instanceof Impl);
    assertTrue(objectMapper.getSerializerProviderInstance() instanceof Impl);
    assertTrue(objectMapper.getDateFormat() instanceof StdDateFormat);
    assertNull(objectMapper.getInjectableValues());
    assertNull(objectMapper.getPropertyNamingStrategy());
    assertTrue(objectMapper.getRegisteredModuleIds().isEmpty());
    assertSame(factory, objectMapper.getJsonFactory());
  }
}
