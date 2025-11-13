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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
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
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
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
    SequenceFlow sequenceFlow = new SequenceFlow();

    // Act
    FlowWithContainer actualFlowWithContainer =
        bpmnJsonConverter.new FlowWithContainer(sequenceFlow, new AdhocSubProcess());
    AdhocSubProcess flowContainer = new AdhocSubProcess();
    actualFlowWithContainer.setFlowContainer(flowContainer);
    SequenceFlow sequenceFlow2 = new SequenceFlow();
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
    outgoingFlows.add(new SequenceFlow());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setLoopCardinality("not empty");
    loopCharacteristics.setInputDataItem("not empty");
    loopCharacteristics.setCompletionCondition("not empty");
    loopCharacteristics.setSequential(false);
    loopCharacteristics.setElementVariable("not empty");

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    BusinessRuleTask element = mock(BusinessRuleTask.class);
    when(element.getId()).thenReturn("42");
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
    element.setLoopCharacteristics(loopCharacteristics);
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
    verify(element).setLoopCharacteristics(isA(MultiInstanceLoopCharacteristics.class));
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
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@code null}.
   *   <li>Then calls {@link BoundaryEvent#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given '42'; when 'null'; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_given42_whenNull_thenCallsGetId() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getId()).thenReturn("42");
    AdhocSubProcess container = new AdhocSubProcess();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, null, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).getId();
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenArrayList() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(new ArrayList<>());
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement).getEventDefinitions();
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
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenReturn(eventDefinitionList);
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement, atLeast(1)).getId();
    verify(flowElement).getEventDefinitions();
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_givenRuntimeException() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenThrow(new RuntimeException());
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).getId();
    verify(flowElement).getEventDefinitions();
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElement(FlowElement, FlowElementsContainer, BpmnModel,
   * ArrayNode, Map, Map, double, double)}.
   *
   * <ul>
   *   <li>Then calls {@link BpmnModel#getGraphicInfo(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processFlowElement(FlowElement,
   * FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName(
      "Test processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); then calls getGraphicInfo(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processFlowElement(FlowElement, FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"
  })
  void testProcessFlowElement_thenCallsGetGraphicInfo() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    BoundaryEvent flowElement = mock(BoundaryEvent.class);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getEventDefinitions()).thenThrow(new RuntimeException());
    AdhocSubProcess container = new AdhocSubProcess();

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
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElement(
        flowElement, container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(flowElement).getId();
    verify(model).getGraphicInfo("42");
    verify(flowElement).getEventDefinitions();
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
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}.
   *
   * <ul>
   *   <li>Given {@link MessageFlow} {@link MessageFlow#getId()} return empty string.
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  @DisplayName(
      "Test processMessageFlows(BpmnModel, ArrayNode); given MessageFlow getId() return empty string; then calls add(JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverter.processMessageFlows(BpmnModel, ArrayNode)"})
  void testProcessMessageFlows_givenMessageFlowGetIdReturnEmptyString_thenCallsAdd() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    MessageFlow messageFlow = mock(MessageFlow.class);
    when(messageFlow.getId()).thenReturn("");
    when(messageFlow.getName()).thenReturn("Name");
    when(messageFlow.getSourceRef()).thenReturn("Source Ref");
    when(messageFlow.getTargetRef()).thenReturn("Target Ref");

    HashMap<String, MessageFlow> stringMessageFlowMap = new HashMap<>();
    stringMessageFlowMap.put("Key", messageFlow);

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

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo3);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getMessageFlows()).thenReturn(stringMessageFlowMap);

    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(shapesArrayNode.add(Mockito.<JsonNode>any())).thenReturn(new ArrayNode(nf));

    // Act
    bpmnJsonConverter.processMessageFlows(model, shapesArrayNode);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(messageFlow, atLeast(1)).getId();
    verify(model).getFlowLocationGraphicInfo("");
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    verify(model).getMessageFlows();
    verify(messageFlow, atLeast(1)).getName();
    verify(messageFlow, atLeast(1)).getSourceRef();
    verify(messageFlow, atLeast(1)).getTargetRef();
  }

  /**
   * Test {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}.
   *
   * <ul>
   *   <li>Given {@link MessageFlow} {@link MessageFlow#getName()} return empty string.
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  @DisplayName(
      "Test processMessageFlows(BpmnModel, ArrayNode); given MessageFlow getName() return empty string; then calls add(JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverter.processMessageFlows(BpmnModel, ArrayNode)"})
  void testProcessMessageFlows_givenMessageFlowGetNameReturnEmptyString_thenCallsAdd() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    MessageFlow messageFlow = mock(MessageFlow.class);
    when(messageFlow.getId()).thenReturn("42");
    when(messageFlow.getName()).thenReturn("");
    when(messageFlow.getSourceRef()).thenReturn("Source Ref");
    when(messageFlow.getTargetRef()).thenReturn("Target Ref");

    HashMap<String, MessageFlow> stringMessageFlowMap = new HashMap<>();
    stringMessageFlowMap.put("Key", messageFlow);

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

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo3);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getMessageFlows()).thenReturn(stringMessageFlowMap);

    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(shapesArrayNode.add(Mockito.<JsonNode>any())).thenReturn(new ArrayNode(nf));

    // Act
    bpmnJsonConverter.processMessageFlows(model, shapesArrayNode);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(messageFlow, atLeast(1)).getId();
    verify(model).getFlowLocationGraphicInfo("42");
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    verify(model).getMessageFlows();
    verify(messageFlow).getName();
    verify(messageFlow, atLeast(1)).getSourceRef();
    verify(messageFlow, atLeast(1)).getTargetRef();
  }

  /**
   * Test {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  @DisplayName("Test processMessageFlows(BpmnModel, ArrayNode); then calls add(JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverter.processMessageFlows(BpmnModel, ArrayNode)"})
  void testProcessMessageFlows_thenCallsAdd() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    MessageFlow messageFlow = mock(MessageFlow.class);
    when(messageFlow.getId()).thenReturn("42");
    when(messageFlow.getName()).thenReturn("Name");
    when(messageFlow.getSourceRef()).thenReturn("Source Ref");
    when(messageFlow.getTargetRef()).thenReturn("Target Ref");

    HashMap<String, MessageFlow> stringMessageFlowMap = new HashMap<>();
    stringMessageFlowMap.put("Key", messageFlow);

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

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo3);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getMessageFlows()).thenReturn(stringMessageFlowMap);

    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(shapesArrayNode.add(Mockito.<JsonNode>any())).thenReturn(new ArrayNode(nf));

    // Act
    bpmnJsonConverter.processMessageFlows(model, shapesArrayNode);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(messageFlow, atLeast(1)).getId();
    verify(model).getFlowLocationGraphicInfo("42");
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    verify(model).getMessageFlows();
    verify(messageFlow, atLeast(1)).getName();
    verify(messageFlow, atLeast(1)).getSourceRef();
    verify(messageFlow, atLeast(1)).getTargetRef();
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
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#convertToBpmnModel(JsonNode)} with {@code modelNode}.
   *
   * <p>Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode) with 'modelNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnModel BpmnJsonConverter.convertToBpmnModel(JsonNode)"})
  void testConvertToBpmnModelWithModelNode() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    BpmnModel actualConvertToBpmnModelResult =
        bpmnJsonConverter.convertToBpmnModel(new ArrayNode(nf));

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
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode modelNode = new ArrayNode(nf);
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
   * Test {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)} with {@code modelNode},
   * {@code formKeyMap}, {@code decisionTableKeyMap}.
   *
   * <ul>
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, Map, Map) with 'modelNode', 'formKeyMap', 'decisionTableKeyMap'; when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnModel BpmnJsonConverter.convertToBpmnModel(JsonNode, Map, Map)"})
  void testConvertToBpmnModelWithModelNodeFormKeyMapDecisionTableKeyMap_whenValueOfTen() {
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
  void testProcessJsonElements() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    jsonNodeList.add(new ArrayNode(nf));

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
  void testProcessJsonElements2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

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
  void testProcessJsonElements3() throws UnsupportedEncodingException {
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
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map,
   * Map, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode,
   * BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); when ActivitiListener (default constructor); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"
  })
  void testProcessJsonElements_whenActivitiListener_thenDoesNotThrow() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    DoubleNode shapesArrayNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act and Assert
    assertDoesNotThrow(
        () ->
            bpmnJsonConverter.processJsonElements(
                shapesArrayNode,
                modelNode,
                parentElement,
                shapeMap,
                formMap,
                decisionTableMap,
                new BpmnModel()));
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map,
   * Map, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode,
   * BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"
  })
  void testProcessJsonElements_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode shapesArrayNode = new ArrayNode(nf);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act and Assert
    assertDoesNotThrow(
        () ->
            bpmnJsonConverter.processJsonElements(
                shapesArrayNode,
                modelNode,
                parentElement,
                shapeMap,
                formMap,
                decisionTableMap,
                new BpmnModel()));
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
