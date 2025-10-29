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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.core.util.COWArrayList;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.io.IOException;
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
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseBpmnJsonConverterDiffblueTest {
  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  void testProcessDataStoreReferences() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    // Act
    associationJsonConverter.processDataStoreReferences(container, "42",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert that nothing has changed
    verify(container).getFlowElements();
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  void testProcessDataStoreReferences2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new AdhocSubProcess());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(flowElementList);

    // Act
    associationJsonConverter.processDataStoreReferences(container, "42",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert that nothing has changed
    verify(container).getFlowElements();
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  void testProcessDataStoreReferences3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(null);
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(flowElementList);

    // Act
    associationJsonConverter.processDataStoreReferences(container, "42",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert that nothing has changed
    verify(container).getFlowElements();
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    MissingNode modelNode = MissingNode.getInstance();
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    MissingNode modelNode = MissingNode.getInstance();
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode);
    MissingNode modelNode = MissingNode.getInstance();
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode).get(eq("overrideid"));
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode);
    MissingNode modelNode = MissingNode.getInstance();
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode).get(eq("overrideid"));
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel5() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.get(Mockito.<String>any())).thenReturn(BooleanNode.getFalse());
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode);
    MissingNode modelNode = MissingNode.getInstance();
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(jsonNode).asText();
    verify(jsonNode, atLeast(1)).get(eq("overrideid"));
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel6() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(true);
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);
    MissingNode modelNode = MissingNode.getInstance();
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(jsonNode2).get(eq("overrideid"));
    verify(jsonNode).isNull();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel7() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(true);
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(modelNode).get(eq("childShapes"));
    verify(jsonNode2).get(eq("overrideid"));
    verify(jsonNode).isNull();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel8() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(true);
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
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(modelNode).get(eq("childShapes"));
    verify(jsonNode2).get(eq("overrideid"));
    verify(jsonNode).isNull();
    verify(jsonNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel9() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(true);
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(modelNode).get(eq("childShapes"));
    verify(jsonNode2).get(eq("overrideid"));
    verify(jsonNode).isNull();
    verify(jsonNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel10() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(true);
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
    AdhocSubProcess parentElement = new AdhocSubProcess();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(modelNode).get(eq("childShapes"));
    verify(jsonNode2).get(eq("overrideid"));
    verify(jsonNode).isNull();
    verify(jsonNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    Collection<Artifact> artifacts = parentElement.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Artifact getResult = ((List<Artifact>) artifacts).get(0);
    assertTrue(getResult instanceof Association);
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    assertEquals("As Text", getResult.getId());
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(((Association) getResult).getSourceRef());
    assertNull(((Association) getResult).getTargetRef());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(AssociationDirection.NONE, ((Association) getResult).getAssociationDirection());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  void testConvertToBpmnModel11() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(true);
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

    // Act
    associationJsonConverter.convertToBpmnModel(elementNode, modelNode, processor, parentElement, shapeMap,
        new BpmnModel());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(modelNode).get(eq("childShapes"));
    verify(jsonNode2).get(eq("overrideid"));
    verify(jsonNode).isNull();
    verify(jsonNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(parentElement).addArtifact(isA(Artifact.class));
    ActivityProcessor activityProcessor = associationJsonConverter.processor;
    assertTrue(activityProcessor instanceof BpmnJsonConverter);
    BpmnModel bpmnModel = associationJsonConverter.model;
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(processor.objectMapper, ((BpmnJsonConverter) activityProcessor).objectMapper);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}
   */
  @Test
  void testSetPropertyValue() throws IOException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.setPropertyValue("Name", "42", propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"42\"", nextResult.toPrettyString());
    assertEquals("{\n  \"Name\" : \"42\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}
   */
  @Test
  void testSetPropertyValue2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.setPropertyValue("Name", null, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}
   */
  @Test
  void testSetPropertyValue3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.setPropertyValue("Name", "", propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  void testAddFormProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayList<FormProperty> formProperties = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  void testAddFieldExtensions() throws IOException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayList<FieldExtension> extensions = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addFieldExtensions(extensions, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"fields\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"servicetaskfields\" : {\n    \"fields\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, nextResult2.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.ARRAY, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult2.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isArray());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult2.isEmpty());
    assertTrue(nextResult.isObject());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    BoundaryEvent event = new BoundaryEvent();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ErrorEventDefinition errorEventDefinition = new ErrorEventDefinition();
    errorEventDefinition.setErrorRef(null);

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(errorEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties3() throws IOException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ErrorEventDefinition errorEventDefinition = new ErrorEventDefinition();
    errorEventDefinition.setErrorRef("Event");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(errorEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Event\"", nextResult.toPrettyString());
    assertEquals("{\n  \"errorref\" : \"Event\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef(null);

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(signalEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties5() throws IOException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef("Event");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(signalEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Event\"", nextResult.toPrettyString());
    assertEquals("{\n  \"signalref\" : \"Event\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties6() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setMessageRef(null);

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(messageEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties7() throws IOException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setMessageRef("Event");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(messageEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Event\"", nextResult.toPrettyString());
    assertEquals("{\n  \"messageref\" : \"Event\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties8() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDate(null);
    timerEventDefinition.setTimeCycle(null);
    timerEventDefinition.setEndDate(null);
    timerEventDefinition.setTimeDuration(null);

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties9() throws IOException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDate(null);
    timerEventDefinition.setTimeCycle(null);
    timerEventDefinition.setEndDate(null);
    timerEventDefinition.setTimeDuration("Event");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Event\"", nextResult.toPrettyString());
    assertEquals("{\n  \"timerdurationdefinition\" : \"Event\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties10() throws IOException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDate(null);
    timerEventDefinition.setTimeCycle(null);
    timerEventDefinition.setEndDate("Event");
    timerEventDefinition.setTimeDuration(null);

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Event\"", nextResult.toPrettyString());
    assertEquals("{\n  \"timerenddatedefinition\" : \"Event\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties11() throws IOException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDate(null);
    timerEventDefinition.setTimeCycle("Event");
    timerEventDefinition.setEndDate(null);
    timerEventDefinition.setTimeDuration(null);

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Event\"", nextResult.toPrettyString());
    assertEquals("{\n  \"timercycledefinition\" : \"Event\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties12() throws IOException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDate("Event");
    timerEventDefinition.setTimeCycle(null);
    timerEventDefinition.setEndDate(null);
    timerEventDefinition.setTimeDuration(null);

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Event\"", nextResult.toPrettyString());
    assertEquals("{\n  \"timerdatedefinition\" : \"Event\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties13() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    BoundaryEvent event = new BoundaryEvent();
    event.addEventDefinition(new CancelEventDefinition());
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  void testAddEventProperties14() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ErrorEventDefinition errorEventDefinition = new ErrorEventDefinition();
    errorEventDefinition.setErrorRef("");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(errorEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(arrayNode).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(arrayNode).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties5() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(arrayNode).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties6() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).get(eq("formProperties"));
    verify(arrayNode2).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties7() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).get(eq("formProperties"));
    verify(arrayNode2).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties8() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("formProperties"));
    verify(arrayNode3).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties9() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("formProperties"));
    verify(arrayNode3).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties10() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
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
    verify(arrayNode2).get(eq("formProperties"));
    verify(arrayNode3).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties11() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn(null);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("formProperties"));
    verify(arrayNode3).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2).asText();
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties12() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("42");
    when(arrayNode.get(Mockito.<String>any())).thenReturn(mock(ArrayNode.class));
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
    verify(arrayNode2).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties13() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("formProperties"));
    verify(arrayNode3).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2).asText();
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToFormProperties14() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayList<FormProperty> formProperties = new ArrayList<>();
    associationJsonConverter.addFormProperties(formProperties,
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("42");
    when(arrayNode.get(Mockito.<String>any())).thenReturn(mock(ArrayNode.class));
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
    verify(arrayNode2).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}
   */
  @Test
  void testConvertJsonToTimerDefinition() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    MissingNode objectNode = MissingNode.getInstance();
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
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}
   */
  @Test
  void testConvertJsonToTimerDefinition2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
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
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}
   */
  @Test
  void testConvertJsonToSignalDefinition() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    MissingNode objectNode = MissingNode.getInstance();
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
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}
   */
  @Test
  void testConvertJsonToSignalDefinition2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
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
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}
   */
  @Test
  void testConvertJsonToMessageDefinition() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    MissingNode objectNode = MissingNode.getInstance();
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
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(((MessageEventDefinition) getResult).getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}
   */
  @Test
  void testConvertJsonToMessageDefinition2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
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
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(((MessageEventDefinition) getResult).getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}
   */
  @Test
  void testConvertJsonToErrorDefinition() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    MissingNode objectNode = MissingNode.getInstance();
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
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}
   */
  @Test
  void testConvertJsonToErrorDefinition2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
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
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}
   */
  @Test
  void testGetValueAsString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}
   */
  @Test
  void testGetValueAsString2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(
        associationJsonConverter.getValueAsString("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}
   */
  @Test
  void testGetValueAsBoolean() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(associationJsonConverter.getValueAsBoolean("Name", MissingNode.getInstance()));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}
   */
  @Test
  void testGetValueAsBoolean2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(
        associationJsonConverter.getValueAsBoolean("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}
   */
  @Test
  void testGetValueAsList() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(associationJsonConverter.getValueAsList("Name", MissingNode.getInstance()).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}
   */
  @Test
  void testGetValueAsList2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(
        associationJsonConverter.getValueAsList("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)))
            .isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField5() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(arrayNode).get(eq("CamelTask"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField6() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(arrayNode).get(eq("CamelTask"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField7() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(8L)));
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(arrayNode).get(eq("CamelTask"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("8", getResult.getStringValue());
    assertEquals("k", getResult.getFieldName());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField8() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(arrayNode).get(eq("CamelTask"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField9() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField10() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField11() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField12() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField13() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(arrayNode).get(eq("Property Name"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField14() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(arrayNode).get(eq("Property Name"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField15() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(arrayNode).get(eq("Property Name"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("1", getResult.getStringValue());
    assertEquals("Name", getResult.getFieldName());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  void testAddField16() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(arrayNode).get(eq("Property Name"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getPropertyValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsString2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getPropertyValueAsString("Name",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsBoolean() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(associationJsonConverter.getPropertyValueAsBoolean("Name", MissingNode.getInstance()));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsBoolean2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(associationJsonConverter.getPropertyValueAsBoolean("Name",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsList() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(associationJsonConverter.getPropertyValueAsList("Name", MissingNode.getInstance()).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsList2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(associationJsonConverter
        .getPropertyValueAsList("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)))
        .isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}
   */
  @Test
  void testGetProperty() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getProperty("Name", MissingNode.getInstance()));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}
   */
  @Test
  void testGetProperty2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getProperty("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  void testConvertListToCommaSeparatedString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.convertListToCommaSeparatedString(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  void testConvertListToCommaSeparatedString2() {
    // Arrange, Act and Assert
    assertNull((new AssociationJsonConverter()).convertListToCommaSeparatedString(null));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  void testConvertListToCommaSeparatedString3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("String List");

    // Act and Assert
    assertEquals("String List", associationJsonConverter.convertListToCommaSeparatedString(stringList));
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  void testConvertListToCommaSeparatedString4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    COWArrayList<String> stringList = mock(COWArrayList.class);

    ArrayList<String> stringList2 = new ArrayList<>();
    when(stringList.iterator()).thenReturn(stringList2.iterator());
    when(stringList.size()).thenReturn(3);

    // Act
    String actualConvertListToCommaSeparatedStringResult = associationJsonConverter
        .convertListToCommaSeparatedString(stringList);

    // Assert
    verify(stringList).iterator();
    verify(stringList).size();
    assertEquals("", actualConvertListToCommaSeparatedStringResult);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  void testConvertListToCommaSeparatedString5() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("42");
    stringList.add("foo");
    COWArrayList<String> stringList2 = mock(COWArrayList.class);
    when(stringList2.iterator()).thenReturn(stringList.iterator());
    when(stringList2.size()).thenReturn(3);

    // Act
    String actualConvertListToCommaSeparatedStringResult = associationJsonConverter
        .convertListToCommaSeparatedString(stringList2);

    // Assert
    verify(stringList2).iterator();
    verify(stringList2).size();
    assertEquals("42,foo", actualConvertListToCommaSeparatedStringResult);
  }

  /**
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  void testConvertListToCommaSeparatedString6() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("42");
    stringList.add("foo");
    COWArrayList<String> stringList2 = mock(COWArrayList.class);
    when(stringList2.iterator()).thenReturn(stringList.iterator());
    when(stringList2.size()).thenReturn(2);

    // Act
    String actualConvertListToCommaSeparatedStringResult = associationJsonConverter
        .convertListToCommaSeparatedString(stringList2);

    // Assert
    verify(stringList2).iterator();
    verify(stringList2).size();
    assertEquals("42,foo", actualConvertListToCommaSeparatedStringResult);
  }
}
