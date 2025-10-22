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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import com.fasterxml.jackson.databind.util.StdDateFormat;
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
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.TextAnnotation;
import org.activiti.editor.language.json.converter.BpmnJsonConverter.FlowWithContainer;
import org.activiti.editor.language.json.model.ModelInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnJsonConverterDiffblueTest {
  /**
   * Test FlowWithContainer getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FlowWithContainer#FlowWithContainer(BpmnJsonConverter, SequenceFlow, FlowElementsContainer)}
   *   <li>{@link FlowWithContainer#setFlowContainer(FlowElementsContainer)}
   *   <li>{@link FlowWithContainer#setSequenceFlow(SequenceFlow)}
   *   <li>{@link FlowWithContainer#getFlowContainer()}
   *   <li>{@link FlowWithContainer#getSequenceFlow()}
   * </ul>
   */
  @Test
  @DisplayName("Test FlowWithContainer getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowWithContainer.<init>(BpmnJsonConverter, SequenceFlow, FlowElementsContainer)",
      "FlowElementsContainer FlowWithContainer.getFlowContainer()", "SequenceFlow FlowWithContainer.getSequenceFlow()",
      "void FlowWithContainer.setFlowContainer(FlowElementsContainer)",
      "void FlowWithContainer.setSequenceFlow(SequenceFlow)"})
  void testFlowWithContainerGettersAndSetters() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    SequenceFlow sequenceFlow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    FlowWithContainer actualFlowWithContainer = bpmnJsonConverter.new FlowWithContainer(sequenceFlow,
        new AdhocSubProcess());
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
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());
    when(container.getFlowElements()).thenReturn(new ArrayList<>());
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new TextAnnotation());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(model).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
    verify(container).getFlowElements();
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    Iterator<JsonNode> iteratorResult = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ {\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 2.0,\n"
        + "      \"y\" : 3.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : -8.0,\n"
        + "      \"y\" : -7.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"TextAnnotation\"\n" + "  },\n" + "  \"properties\" : {\n"
        + "    \"overrideid\" : null\n" + "  },\n" + "  \"outgoing\" : [ ]\n" + "} ]",
        shapesArrayNode.toPrettyString());
    assertEquals("{\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 2.0,\n"
        + "      \"y\" : 3.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : -8.0,\n"
        + "      \"y\" : -7.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"TextAnnotation\"\n" + "  },\n" + "  \"properties\" : {\n"
        + "    \"overrideid\" : null\n" + "  },\n" + "  \"outgoing\" : [ ]\n" + "}", nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"lowerRight\" : {\n" + "    \"x\" : 2.0,\n" + "    \"y\" : 3.0\n" + "  },\n"
            + "  \"upperLeft\" : {\n" + "    \"x\" : -8.0,\n" + "    \"y\" : -7.0\n" + "  }\n" + "}",
        nextResult2.toPrettyString());
    assertEquals(6, nextResult.size());
    assertFalse(elementsResult.hasNext());
    assertTrue(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements3() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(172.0d);
    graphicInfo2.setWidth(172.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(model).getFlowLocationGraphicInfo(isNull());
    verify(model, atLeast(1)).getGraphicInfo(isNull());
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    Iterator<JsonNode> iteratorResult = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ {\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 172.0,\n"
        + "      \"y\" : 212.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : 128.0,\n"
        + "      \"y\" : 212.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"Association\"\n" + "  },\n" + "  \"dockers\" : [ {\n"
        + "    \"x\" : 5.0,\n" + "    \"y\" : 5.0\n" + "  }, {\n" + "    \"x\" : 5.0,\n" + "    \"y\" : 0.0\n"
        + "  } ],\n" + "  \"outgoing\" : [ {\n" + "    \"resourceId\" : null\n" + "  } ],\n" + "  \"target\" : {\n"
        + "    \"resourceId\" : null\n" + "  },\n" + "  \"properties\" : {\n" + "    \"overrideid\" : null\n" + "  }\n"
        + "} ]", shapesArrayNode.toPrettyString());
    assertEquals("{\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 172.0,\n"
        + "      \"y\" : 212.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : 128.0,\n"
        + "      \"y\" : 212.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"Association\"\n" + "  },\n" + "  \"dockers\" : [ {\n"
        + "    \"x\" : 5.0,\n" + "    \"y\" : 5.0\n" + "  }, {\n" + "    \"x\" : 5.0,\n" + "    \"y\" : 0.0\n"
        + "  } ],\n" + "  \"outgoing\" : [ {\n" + "    \"resourceId\" : null\n" + "  } ],\n" + "  \"target\" : {\n"
        + "    \"resourceId\" : null\n" + "  },\n" + "  \"properties\" : {\n" + "    \"overrideid\" : null\n" + "  }\n"
        + "}", nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"lowerRight\" : {\n" + "    \"x\" : 172.0,\n" + "    \"y\" : 212.0\n" + "  },\n"
            + "  \"upperLeft\" : {\n" + "    \"x\" : 128.0,\n" + "    \"y\" : 212.0\n" + "  }\n" + "}",
        nextResult2.toPrettyString());
    assertEquals(8, nextResult.size());
    assertFalse(elementsResult.hasNext());
    assertTrue(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements4() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    artifactList.add(new TextAnnotation());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(model).getFlowLocationGraphicInfo(isNull());
    verify(model, atLeast(1)).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
    verify(container).getFlowElements();
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    Iterator<JsonNode> iteratorResult = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ {\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 2.0,\n"
        + "      \"y\" : 3.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : -8.0,\n"
        + "      \"y\" : -7.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"TextAnnotation\"\n" + "  },\n" + "  \"properties\" : {\n"
        + "    \"overrideid\" : null\n" + "  },\n" + "  \"outgoing\" : [ ]\n" + "} ]",
        shapesArrayNode.toPrettyString());
    assertEquals("{\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 2.0,\n"
        + "      \"y\" : 3.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : -8.0,\n"
        + "      \"y\" : -7.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"TextAnnotation\"\n" + "  },\n" + "  \"properties\" : {\n"
        + "    \"overrideid\" : null\n" + "  },\n" + "  \"outgoing\" : [ ]\n" + "}", nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"lowerRight\" : {\n" + "    \"x\" : 2.0,\n" + "    \"y\" : 3.0\n" + "  },\n"
            + "  \"upperLeft\" : {\n" + "    \"x\" : -8.0,\n" + "    \"y\" : -7.0\n" + "  }\n" + "}",
        nextResult2.toPrettyString());
    assertEquals(6, nextResult.size());
    assertFalse(elementsResult.hasNext());
    assertTrue(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add AdhocSubProcess (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_givenArrayListAddAdhocSubProcess() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new AdhocSubProcess());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());
    when(container.getFlowElements()).thenReturn(flowElementList);
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Association} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add Association (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_givenArrayListAddAssociation() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Association}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add Association")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_givenArrayListAddAssociation2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(mock(Association.class));
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Association} (default constructor).</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add Association (default constructor); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_givenArrayListAddAssociation_whenNull() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, null, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TextAnnotation} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given ArrayList() add TextAnnotation (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_givenArrayListAddTextAnnotation() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new TextAnnotation());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>Given {@link TextAnnotation} (default constructor) Text is empty string.</li>
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given TextAnnotation (default constructor) Text is empty string; then calls add(JsonNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_givenTextAnnotationTextIsEmptyString_thenCallsAdd() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    TextAnnotation textAnnotation = new TextAnnotation();
    textAnnotation.setText("");

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(textAnnotation);
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.add(Mockito.<JsonNode>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(model).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
    verify(container).getFlowElements();
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>Given {@link TextAnnotation} (default constructor) Text is {@code TextAnnotation}.</li>
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); given TextAnnotation (default constructor) Text is 'TextAnnotation'; then calls add(JsonNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_givenTextAnnotationTextIsTextAnnotation_thenCallsAdd() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    TextAnnotation textAnnotation = new TextAnnotation();
    textAnnotation.setText("TextAnnotation");

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(textAnnotation);
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.add(Mockito.<JsonNode>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(model).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
    verify(container).getFlowElements();
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); then calls add(JsonNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_thenCallsAdd() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new TextAnnotation());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.add(Mockito.<JsonNode>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(model).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
    verify(container).getFlowElements();
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>Then calls {@link BpmnModel#getFlowLocationGraphicInfo(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); then calls getFlowLocationGraphicInfo(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_thenCallsGetFlowLocationGraphicInfo() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(model).getFlowLocationGraphicInfo(isNull());
    verify(model, atLeast(1)).getGraphicInfo(isNull());
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); when AdhocSubProcess (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_whenAdhocSubProcess() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  @DisplayName("Test processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double); when Process (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)"})
  void testProcessFlowElements_whenProcess() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    Process container = new Process();
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processFlowElements(container, model, shapesArrayNode, formKeyMap, new HashMap<>(), 10.0d, 10.0d);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new TextAnnotation());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert
    verify(model).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    Iterator<JsonNode> iteratorResult = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ {\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 2.0,\n"
        + "      \"y\" : 3.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : -8.0,\n"
        + "      \"y\" : -7.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"TextAnnotation\"\n" + "  },\n" + "  \"properties\" : {\n"
        + "    \"overrideid\" : null\n" + "  },\n" + "  \"outgoing\" : [ ]\n" + "} ]",
        shapesArrayNode.toPrettyString());
    assertEquals("{\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 2.0,\n"
        + "      \"y\" : 3.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : -8.0,\n"
        + "      \"y\" : -7.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"TextAnnotation\"\n" + "  },\n" + "  \"properties\" : {\n"
        + "    \"overrideid\" : null\n" + "  },\n" + "  \"outgoing\" : [ ]\n" + "}", nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"lowerRight\" : {\n" + "    \"x\" : 2.0,\n" + "    \"y\" : 3.0\n" + "  },\n"
            + "  \"upperLeft\" : {\n" + "    \"x\" : -8.0,\n" + "    \"y\" : -7.0\n" + "  }\n" + "}",
        nextResult2.toPrettyString());
    assertEquals(6, nextResult.size());
    assertFalse(elementsResult.hasNext());
    assertTrue(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(172.0d);
    graphicInfo2.setWidth(172.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert
    verify(model).getFlowLocationGraphicInfo(isNull());
    verify(model, atLeast(1)).getGraphicInfo(isNull());
    verify(container).getArtifacts();
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    Iterator<JsonNode> iteratorResult = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ {\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 172.0,\n"
        + "      \"y\" : 212.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : 128.0,\n"
        + "      \"y\" : 212.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"Association\"\n" + "  },\n" + "  \"dockers\" : [ {\n"
        + "    \"x\" : 5.0,\n" + "    \"y\" : 5.0\n" + "  }, {\n" + "    \"x\" : 5.0,\n" + "    \"y\" : 0.0\n"
        + "  } ],\n" + "  \"outgoing\" : [ {\n" + "    \"resourceId\" : null\n" + "  } ],\n" + "  \"target\" : {\n"
        + "    \"resourceId\" : null\n" + "  },\n" + "  \"properties\" : {\n" + "    \"overrideid\" : null\n" + "  }\n"
        + "} ]", shapesArrayNode.toPrettyString());
    assertEquals("{\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 172.0,\n"
        + "      \"y\" : 212.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : 128.0,\n"
        + "      \"y\" : 212.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"Association\"\n" + "  },\n" + "  \"dockers\" : [ {\n"
        + "    \"x\" : 5.0,\n" + "    \"y\" : 5.0\n" + "  }, {\n" + "    \"x\" : 5.0,\n" + "    \"y\" : 0.0\n"
        + "  } ],\n" + "  \"outgoing\" : [ {\n" + "    \"resourceId\" : null\n" + "  } ],\n" + "  \"target\" : {\n"
        + "    \"resourceId\" : null\n" + "  },\n" + "  \"properties\" : {\n" + "    \"overrideid\" : null\n" + "  }\n"
        + "}", nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"lowerRight\" : {\n" + "    \"x\" : 172.0,\n" + "    \"y\" : 212.0\n" + "  },\n"
            + "  \"upperLeft\" : {\n" + "    \"x\" : 128.0,\n" + "    \"y\" : 212.0\n" + "  }\n" + "}",
        nextResult2.toPrettyString());
    assertEquals(8, nextResult.size());
    assertFalse(elementsResult.hasNext());
    assertTrue(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts3() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    artifactList.add(new TextAnnotation());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert
    verify(model).getFlowLocationGraphicInfo(isNull());
    verify(model, atLeast(1)).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
    Iterator<JsonNode> elementsResult = shapesArrayNode.elements();
    JsonNode nextResult = elementsResult.next();
    Iterator<JsonNode> iteratorResult = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ {\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 2.0,\n"
        + "      \"y\" : 3.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : -8.0,\n"
        + "      \"y\" : -7.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"TextAnnotation\"\n" + "  },\n" + "  \"properties\" : {\n"
        + "    \"overrideid\" : null\n" + "  },\n" + "  \"outgoing\" : [ ]\n" + "} ]",
        shapesArrayNode.toPrettyString());
    assertEquals("{\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 2.0,\n"
        + "      \"y\" : 3.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : -8.0,\n"
        + "      \"y\" : -7.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"TextAnnotation\"\n" + "  },\n" + "  \"properties\" : {\n"
        + "    \"overrideid\" : null\n" + "  },\n" + "  \"outgoing\" : [ ]\n" + "}", nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"lowerRight\" : {\n" + "    \"x\" : 2.0,\n" + "    \"y\" : 3.0\n" + "  },\n"
            + "  \"upperLeft\" : {\n" + "    \"x\" : -8.0,\n" + "    \"y\" : -7.0\n" + "  }\n" + "}",
        nextResult2.toPrettyString());
    assertEquals(6, nextResult.size());
    assertFalse(elementsResult.hasNext());
    assertTrue(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_givenArrayList() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(new ArrayList<>());
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Association} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given ArrayList() add Association (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_givenArrayListAddAssociation() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Association}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given ArrayList() add Association")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_givenArrayListAddAssociation2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(mock(Association.class));
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Association} (default constructor).</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given ArrayList() add Association (default constructor); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_givenArrayListAddAssociation_whenNull() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, null, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TextAnnotation} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given ArrayList() add TextAnnotation (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_givenArrayListAddTextAnnotation() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new TextAnnotation());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BpmnModel#getFlowLocationGraphicInfo(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given ArrayList(); then calls getFlowLocationGraphicInfo(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_givenArrayList_thenCallsGetFlowLocationGraphicInfo() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    verify(model).getFlowLocationGraphicInfo(isNull());
    verify(model, atLeast(1)).getGraphicInfo(isNull());
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then calls add(JsonNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_givenArrayNodeWithNfIsWithExactBigDecimalsTrue_thenCallsAdd() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new TextAnnotation());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.add(Mockito.<JsonNode>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(model).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>Given {@link TextAnnotation} (default constructor) Text is empty string.</li>
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given TextAnnotation (default constructor) Text is empty string; then calls add(JsonNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_givenTextAnnotationTextIsEmptyString_thenCallsAdd() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    TextAnnotation textAnnotation = new TextAnnotation();
    textAnnotation.setText("");

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(textAnnotation);
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.add(Mockito.<JsonNode>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(model).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>Given {@link TextAnnotation} (default constructor) Text is {@code TextAnnotation}.</li>
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); given TextAnnotation (default constructor) Text is 'TextAnnotation'; then calls add(JsonNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_givenTextAnnotationTextIsTextAnnotation_thenCallsAdd() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    TextAnnotation textAnnotation = new TextAnnotation();
    textAnnotation.setText("TextAnnotation");

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(textAnnotation);
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.add(Mockito.<JsonNode>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(model).getGraphicInfo(isNull());
    verify(container, atLeast(1)).getArtifacts();
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); when AdhocSubProcess (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_whenAdhocSubProcess() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  @DisplayName("Test processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double); when Process (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)"})
  void testProcessArtifacts_whenProcess() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    Process container = new Process();
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  @DisplayName("Test processMessageFlows(BpmnModel, ArrayNode); given HashMap()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverter.processMessageFlows(BpmnModel, ArrayNode)"})
  void testProcessMessageFlows_givenHashMap() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processMessageFlows(model, shapesArrayNode);

    // Assert that nothing has changed
    verify(model).getMessageFlows();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}.
   * <ul>
   *   <li>Then calls {@link ArrayNode#add(JsonNode)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  @DisplayName("Test processMessageFlows(BpmnModel, ArrayNode); then calls add(JsonNode)")
  @Tag("MaintainedByDiffblue")
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
    stringMessageFlowMap.put("foo", messageFlow);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getMessageFlows()).thenReturn(stringMessageFlowMap);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.add(Mockito.<JsonNode>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    bpmnJsonConverter.processMessageFlows(model, shapesArrayNode);

    // Assert
    verify(shapesArrayNode).add(isA(JsonNode.class));
    verify(messageFlow, atLeast(1)).getId();
    verify(model).getFlowLocationGraphicInfo(eq("42"));
    verify(model, atLeast(1)).getGraphicInfo(Mockito.<String>any());
    verify(model).getMessageFlows();
    verify(messageFlow, atLeast(1)).getName();
    verify(messageFlow, atLeast(1)).getSourceRef();
    verify(messageFlow, atLeast(1)).getTargetRef();
  }

  /**
   * Test {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  @DisplayName("Test processMessageFlows(BpmnModel, ArrayNode); when BpmnModel (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverter.processMessageFlows(BpmnModel, ArrayNode)"})
  void testProcessMessageFlows_whenBpmnModel() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processMessageFlows(model, shapesArrayNode);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertFalse(shapesArrayNode.elements().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverter#convertToBpmnModel(JsonNode)} with {@code modelNode}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode) with 'modelNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BpmnModel BpmnJsonConverter.convertToBpmnModel(JsonNode)"})
  void testConvertToBpmnModelWithModelNode() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    // Act
    BpmnModel actualConvertToBpmnModelResult = bpmnJsonConverter
        .convertToBpmnModel(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

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
   * Test {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)} with {@code modelNode}, {@code formKeyMap}, {@code decisionTableKeyMap}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, Map, Map) with 'modelNode', 'formKeyMap', 'decisionTableKeyMap'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BpmnModel BpmnJsonConverter.convertToBpmnModel(JsonNode, Map, Map)"})
  void testConvertToBpmnModelWithModelNodeFormKeyMapDecisionTableKeyMap() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    ArrayNode modelNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    HashMap<String, String> formKeyMap = new HashMap<>();

    // Act
    BpmnModel actualConvertToBpmnModelResult = bpmnJsonConverter.convertToBpmnModel(modelNode, formKeyMap,
        new HashMap<>());

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
   * Test {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)} with {@code modelNode}, {@code formKeyMap}, {@code decisionTableKeyMap}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, Map, Map) with 'modelNode', 'formKeyMap', 'decisionTableKeyMap'; when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BpmnModel BpmnJsonConverter.convertToBpmnModel(JsonNode, Map, Map)"})
  void testConvertToBpmnModelWithModelNodeFormKeyMapDecisionTableKeyMap_whenInstance() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    MissingNode modelNode = MissingNode.getInstance();
    HashMap<String, String> formKeyMap = new HashMap<>();

    // Act
    BpmnModel actualConvertToBpmnModelResult = bpmnJsonConverter.convertToBpmnModel(modelNode, formKeyMap,
        new HashMap<>());

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
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then Resources return {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode) with 'modelNode'; when Instance; then Resources return List")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BpmnModel BpmnJsonConverter.convertToBpmnModel(JsonNode)"})
  void testConvertToBpmnModelWithModelNode_whenInstance_thenResourcesReturnList() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    // Act
    BpmnModel actualConvertToBpmnModelResult = bpmnJsonConverter.convertToBpmnModel(MissingNode.getInstance());

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
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"})
  void testProcessJsonElements() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    MissingNode modelNode = MissingNode.getInstance();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(shapesArrayNode, modelNode, parentElement, shapeMap, formMap,
        decisionTableMap, new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"})
  void testProcessJsonElements2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    MissingNode modelNode = MissingNode.getInstance();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(shapesArrayNode, modelNode, parentElement, shapeMap, formMap,
        decisionTableMap, new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(eq("stencil"));
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}.
   * <p>
   * Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"})
  void testProcessJsonElements3() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    MissingNode modelNode = MissingNode.getInstance();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(shapesArrayNode, modelNode, parentElement, shapeMap, formMap,
        decisionTableMap, new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode2, atLeast(1)).get(eq("stencil"));
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); given ArrayList() add Instance; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"})
  void testProcessJsonElements_givenArrayListAddInstance_thenCallsIterator() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    MissingNode modelNode = MissingNode.getInstance();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(shapesArrayNode, modelNode, parentElement, shapeMap, formMap,
        decisionTableMap, new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); given ArrayList() iterator; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"})
  void testProcessJsonElements_givenArrayListIterator_thenCallsIterator() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    ArrayNode shapesArrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    MissingNode modelNode = MissingNode.getInstance();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(shapesArrayNode, modelNode, parentElement, shapeMap, formMap,
        decisionTableMap, new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); given ArrayNode get(String) return Instance; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"})
  void testProcessJsonElements_givenArrayNodeGetReturnInstance_thenCallsGet() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    MissingNode modelNode = MissingNode.getInstance();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(shapesArrayNode, modelNode, parentElement, shapeMap, formMap,
        decisionTableMap, new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(eq("stencil"));
  }

  /**
   * Test {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel); given ArrayNode get(String) return Instance; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BpmnJsonConverter.processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)"})
  void testProcessJsonElements_givenArrayNodeGetReturnInstance_thenCallsGet2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    ArrayNode shapesArrayNode = mock(ArrayNode.class);
    when(shapesArrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    MissingNode modelNode = MissingNode.getInstance();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    HashMap<String, String> formMap = new HashMap<>();
    HashMap<String, String> decisionTableMap = new HashMap<>();

    // Act
    bpmnJsonConverter.processJsonElements(shapesArrayNode, modelNode, parentElement, shapeMap, formMap,
        decisionTableMap, new BpmnModel());

    // Assert
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode2, atLeast(1)).get(eq("stencil"));
  }

  /**
   * Test new {@link BpmnJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link BpmnJsonConverter}
   */
  @Test
  @DisplayName("Test new BpmnJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverter.<init>()"})
  void testNewBpmnJsonConverter() {
    // Arrange, Act and Assert
    ObjectMapper objectMapper = (new BpmnJsonConverter()).objectMapper;
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    assertTrue(objectMapper.getDeserializationContext() instanceof DefaultDeserializationContext.Impl);
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
