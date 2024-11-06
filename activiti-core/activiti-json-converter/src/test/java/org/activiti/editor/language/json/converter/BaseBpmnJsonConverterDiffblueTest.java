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
import com.fasterxml.jackson.databind.node.ContainerNode;
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
import org.activiti.bpmn.model.FormValue;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseBpmnJsonConverterDiffblueTest {
  /**
   * Test
   * {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  @DisplayName("Test processDataStoreReferences(FlowElementsContainer, String, ArrayNode); given ArrayList() add AdhocSubProcess (default constructor)")
  void testProcessDataStoreReferences_givenArrayListAddAdhocSubProcess() {
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
   * Test
   * {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link SubProcess#getFlowElements()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  @DisplayName("Test processDataStoreReferences(FlowElementsContainer, String, ArrayNode); given ArrayList() add 'null'; then calls getFlowElements()")
  void testProcessDataStoreReferences_givenArrayListAddNull_thenCallsGetFlowElements() {
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
   * Test
   * {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link SubProcess#getFlowElements()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  @DisplayName("Test processDataStoreReferences(FlowElementsContainer, String, ArrayNode); given ArrayList(); then calls getFlowElements()")
  void testProcessDataStoreReferences_givenArrayList_thenCallsGetFlowElements() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given ArrayList() add Instance; then calls iterator()")
  void testConvertToBpmnModel_givenArrayListAddInstance_thenCallsIterator() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given Instance; when ArrayNode get(String) return Instance")
  void testConvertToBpmnModel_givenInstance_whenArrayNodeGetReturnInstance() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given Instance; when ArrayNode get(String) return Instance")
  void testConvertToBpmnModel_givenInstance_whenArrayNodeGetReturnInstance2() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given Instance; when JsonNode get(String) return Instance")
  void testConvertToBpmnModel_givenInstance_whenJsonNodeGetReturnInstance() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return False.</li>
   *   <li>When Instance.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode get(String) return False; when Instance; then calls asText()")
  void testConvertToBpmnModel_givenJsonNodeGetReturnFalse_whenInstance_thenCallsAsText() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode get(String) return Instance; when Instance")
  void testConvertToBpmnModel_givenJsonNodeGetReturnInstance_whenInstance() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode get(String) return Instance; when Instance")
  void testConvertToBpmnModel_givenJsonNodeGetReturnInstance_whenInstance2() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode isNull() return 'true'; then calls isNull()")
  void testConvertToBpmnModel_givenJsonNodeIsNullReturnTrue_thenCallsIsNull() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Then calls {@link SubProcess#addArtifact(Artifact)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); then calls addArtifact(Artifact)")
  void testConvertToBpmnModel_thenCallsAddArtifact() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); then calls iterator()")
  void testConvertToBpmnModel_thenCallsIterator() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Artifacts size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); when AdhocSubProcess (default constructor); then AdhocSubProcess (default constructor) Artifacts size is one")
  void testConvertToBpmnModel_whenAdhocSubProcess_thenAdhocSubProcessArtifactsSizeIsOne() {
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
    assertEquals("As Text", getResult.getId());
    assertNull(((Association) getResult).getSourceRef());
    assertNull(((Association) getResult).getTargetRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(AssociationDirection.NONE, ((Association) getResult).getAssociationDirection());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}
   */
  @Test
  @DisplayName("Test setPropertyValue(String, String, ObjectNode)")
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
   * Test
   * {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}
   */
  @Test
  @DisplayName("Test setPropertyValue(String, String, ObjectNode); when empty string")
  void testSetPropertyValue_whenEmptyString() {
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
   * Test
   * {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}
   */
  @Test
  @DisplayName("Test setPropertyValue(String, String, ObjectNode); when 'null'")
  void testSetPropertyValue_whenNull() {
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
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode)")
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
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode)")
  void testAddFormProperties2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setId(null);
    formValue.setName(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);
    formProperty.setVariable(null);
    formProperty.setName(null);
    formProperty.setType(null);
    formProperty.setDatePattern("Form Properties");
    formProperty.setExpression(null);
    formProperty.setId(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : null,\n" + "  \"variable\" : null,\n" + "  \"datePattern\" : \"Form Properties\",\n"
        + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n" + "    \"id\" : null\n" + "  } ],\n"
        + "  \"required\" : false,\n" + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "} ]",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : null,\n" + "    \"variable\" : null,\n"
        + "    \"datePattern\" : \"Form Properties\",\n" + "    \"enumValues\" : [ {\n" + "      \"name\" : null,\n"
        + "      \"id\" : null\n" + "    } ],\n" + "    \"required\" : false,\n" + "    \"readable\" : true,\n"
        + "    \"writable\" : true\n" + "  } ]\n" + "}", nextResult.toPrettyString());
    assertEquals("{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
        + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : null,\n"
        + "      \"variable\" : null,\n" + "      \"datePattern\" : \"Form Properties\",\n"
        + "      \"enumValues\" : [ {\n" + "        \"name\" : null,\n" + "        \"id\" : null\n" + "      } ],\n"
        + "      \"required\" : false,\n" + "      \"readable\" : true,\n" + "      \"writable\" : true\n" + "    } ]\n"
        + "  }\n" + "}", propertiesNode.toPrettyString());
    assertEquals("{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : null,\n" + "  \"variable\" : null,\n" + "  \"datePattern\" : \"Form Properties\",\n"
        + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n" + "    \"id\" : null\n" + "  } ],\n"
        + "  \"required\" : false,\n" + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "}",
        nextResult3.toPrettyString());
    assertEquals(10, nextResult3.size());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode)")
  void testAddFormProperties3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(new FormProperty());
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : null,\n" + "  \"variable\" : null,\n" + "  \"required\" : false,\n"
        + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "} ]", nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : null,\n" + "    \"variable\" : null,\n"
        + "    \"required\" : false,\n" + "    \"readable\" : true,\n" + "    \"writable\" : true\n" + "  } ]\n" + "}",
        nextResult.toPrettyString());
    assertEquals("{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
        + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : null,\n"
        + "      \"variable\" : null,\n" + "      \"required\" : false,\n" + "      \"readable\" : true,\n"
        + "      \"writable\" : true\n" + "    } ]\n" + "  }\n" + "}", propertiesNode.toPrettyString());
    assertEquals("{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : null,\n" + "  \"variable\" : null,\n" + "  \"required\" : false,\n"
        + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "}", nextResult3.toPrettyString());
    assertEquals(8, nextResult3.size());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) Expression is
   * {@code Form Properties}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode); given FormProperty (default constructor) Expression is 'Form Properties'")
  void testAddFormProperties_givenFormPropertyExpressionIsFormProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setId(null);
    formValue.setName(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);
    formProperty.setVariable(null);
    formProperty.setName(null);
    formProperty.setType(null);
    formProperty.setDatePattern(null);
    formProperty.setExpression("Form Properties");
    formProperty.setId(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : \"Form Properties\",\n" + "  \"variable\" : null,\n" + "  \"enumValues\" : [ {\n"
        + "    \"name\" : null,\n" + "    \"id\" : null\n" + "  } ],\n" + "  \"required\" : false,\n"
        + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "} ]", nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : \"Form Properties\",\n" + "    \"variable\" : null,\n"
        + "    \"enumValues\" : [ {\n" + "      \"name\" : null,\n" + "      \"id\" : null\n" + "    } ],\n"
        + "    \"required\" : false,\n" + "    \"readable\" : true,\n" + "    \"writable\" : true\n" + "  } ]\n" + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
            + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : \"Form Properties\",\n"
            + "      \"variable\" : null,\n" + "      \"enumValues\" : [ {\n" + "        \"name\" : null,\n"
            + "        \"id\" : null\n" + "      } ],\n" + "      \"required\" : false,\n"
            + "      \"readable\" : true,\n" + "      \"writable\" : true\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals("{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : \"Form Properties\",\n" + "  \"variable\" : null,\n" + "  \"enumValues\" : [ {\n"
        + "    \"name\" : null,\n" + "    \"id\" : null\n" + "  } ],\n" + "  \"required\" : false,\n"
        + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "}", nextResult3.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) Variable is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode); given FormProperty (default constructor) Variable is empty string")
  void testAddFormProperties_givenFormPropertyVariableIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setId(null);
    formValue.setName(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);
    formProperty.setVariable("");
    formProperty.setName(null);
    formProperty.setType(null);
    formProperty.setDatePattern(null);
    formProperty.setExpression(null);
    formProperty.setId(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals(
        "[ {\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n" + "  \"expression\" : null,\n"
            + "  \"variable\" : null,\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n" + "    \"id\" : null\n"
            + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "} ]",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : null,\n" + "    \"variable\" : null,\n"
        + "    \"enumValues\" : [ {\n" + "      \"name\" : null,\n" + "      \"id\" : null\n" + "    } ],\n"
        + "    \"required\" : false,\n" + "    \"readable\" : true,\n" + "    \"writable\" : true\n" + "  } ]\n" + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
            + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : null,\n"
            + "      \"variable\" : null,\n" + "      \"enumValues\" : [ {\n" + "        \"name\" : null,\n"
            + "        \"id\" : null\n" + "      } ],\n" + "      \"required\" : false,\n"
            + "      \"readable\" : true,\n" + "      \"writable\" : true\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(
        "{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n" + "  \"expression\" : null,\n"
            + "  \"variable\" : null,\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n" + "    \"id\" : null\n"
            + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "}",
        nextResult3.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) Variable is
   * {@code Form Properties}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode); given FormProperty (default constructor) Variable is 'Form Properties'")
  void testAddFormProperties_givenFormPropertyVariableIsFormProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setId(null);
    formValue.setName(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);
    formProperty.setVariable("Form Properties");
    formProperty.setName(null);
    formProperty.setType(null);
    formProperty.setDatePattern(null);
    formProperty.setExpression(null);
    formProperty.setId(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : null,\n" + "  \"variable\" : \"Form Properties\",\n" + "  \"enumValues\" : [ {\n"
        + "    \"name\" : null,\n" + "    \"id\" : null\n" + "  } ],\n" + "  \"required\" : false,\n"
        + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "} ]", nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : null,\n" + "    \"variable\" : \"Form Properties\",\n"
        + "    \"enumValues\" : [ {\n" + "      \"name\" : null,\n" + "      \"id\" : null\n" + "    } ],\n"
        + "    \"required\" : false,\n" + "    \"readable\" : true,\n" + "    \"writable\" : true\n" + "  } ]\n" + "}",
        nextResult.toPrettyString());
    assertEquals("{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
        + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : null,\n"
        + "      \"variable\" : \"Form Properties\",\n" + "      \"enumValues\" : [ {\n" + "        \"name\" : null,\n"
        + "        \"id\" : null\n" + "      } ],\n" + "      \"required\" : false,\n" + "      \"readable\" : true,\n"
        + "      \"writable\" : true\n" + "    } ]\n" + "  }\n" + "}", propertiesNode.toPrettyString());
    assertEquals("{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : null,\n" + "  \"variable\" : \"Form Properties\",\n" + "  \"enumValues\" : [ {\n"
        + "    \"name\" : null,\n" + "    \"id\" : null\n" + "  } ],\n" + "  \"required\" : false,\n"
        + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "}", nextResult3.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) Variable is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode); given FormProperty (default constructor) Variable is 'null'")
  void testAddFormProperties_givenFormPropertyVariableIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setId(null);
    formValue.setName(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);
    formProperty.setVariable(null);
    formProperty.setName(null);
    formProperty.setType(null);
    formProperty.setDatePattern(null);
    formProperty.setExpression(null);
    formProperty.setId(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals(
        "[ {\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n" + "  \"expression\" : null,\n"
            + "  \"variable\" : null,\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n" + "    \"id\" : null\n"
            + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "} ]",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : null,\n" + "    \"variable\" : null,\n"
        + "    \"enumValues\" : [ {\n" + "      \"name\" : null,\n" + "      \"id\" : null\n" + "    } ],\n"
        + "    \"required\" : false,\n" + "    \"readable\" : true,\n" + "    \"writable\" : true\n" + "  } ]\n" + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
            + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : null,\n"
            + "      \"variable\" : null,\n" + "      \"enumValues\" : [ {\n" + "        \"name\" : null,\n"
            + "        \"id\" : null\n" + "      } ],\n" + "      \"required\" : false,\n"
            + "      \"readable\" : true,\n" + "      \"writable\" : true\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(
        "{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n" + "  \"expression\" : null,\n"
            + "  \"variable\" : null,\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n" + "    \"id\" : null\n"
            + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "}",
        nextResult3.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FormValue} (default constructor) Id is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode); given FormValue (default constructor) Id is empty string")
  void testAddFormProperties_givenFormValueIdIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setId("");
    formValue.setName(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);
    formProperty.setVariable(null);
    formProperty.setName(null);
    formProperty.setType(null);
    formProperty.setDatePattern(null);
    formProperty.setExpression(null);
    formProperty.setId(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals(
        "[ {\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n" + "  \"expression\" : null,\n"
            + "  \"variable\" : null,\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n" + "    \"id\" : \"\"\n"
            + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "} ]",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : null,\n" + "    \"variable\" : null,\n"
        + "    \"enumValues\" : [ {\n" + "      \"name\" : null,\n" + "      \"id\" : \"\"\n" + "    } ],\n"
        + "    \"required\" : false,\n" + "    \"readable\" : true,\n" + "    \"writable\" : true\n" + "  } ]\n" + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
            + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : null,\n"
            + "      \"variable\" : null,\n" + "      \"enumValues\" : [ {\n" + "        \"name\" : null,\n"
            + "        \"id\" : \"\"\n" + "      } ],\n" + "      \"required\" : false,\n"
            + "      \"readable\" : true,\n" + "      \"writable\" : true\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(
        "{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n" + "  \"expression\" : null,\n"
            + "  \"variable\" : null,\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n" + "    \"id\" : \"\"\n"
            + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "}",
        nextResult3.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  void testAddFieldExtensions() {
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
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"fields\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"servicetaskfields\" : {\n    \"fields\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  void testAddFieldExtensions2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(null);
    fieldExtension.setExpression(null);
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> extensions = new ArrayList<>();
    extensions.add(fieldExtension);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n  \"name\" : null\n} ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"fields\" : [ {\n    \"name\" : null\n  } ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : null\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"servicetaskfields\" : {\n    \"fields\" : [ {\n      \"name\" : null\n    } ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  void testAddFieldExtensions3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(null);
    fieldExtension.setExpression(null);
    fieldExtension.setFieldName("Extensions");

    ArrayList<FieldExtension> extensions = new ArrayList<>();
    extensions.add(fieldExtension);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n  \"name\" : \"Extensions\"\n} ]", nextResult2.toPrettyString());
    assertEquals("\"Extensions\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"fields\" : [ {\n    \"name\" : \"Extensions\"\n  } ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : \"Extensions\"\n}", nextResult3.toPrettyString());
    assertEquals(
        "{\n  \"servicetaskfields\" : {\n    \"fields\" : [ {\n      \"name\" : \"Extensions\"\n    } ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  void testAddFieldExtensions4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(null);
    fieldExtension.setExpression("Extensions");
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> extensions = new ArrayList<>();
    extensions.add(fieldExtension);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n  \"name\" : null,\n  \"expression\" : \"Extensions\"\n} ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"fields\" : [ {\n    \"name\" : null,\n    \"expression\" : \"Extensions\"\n  } ]\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : null,\n  \"expression\" : \"Extensions\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"servicetaskfields\" : {\n" + "    \"fields\" : [ {\n" + "      \"name\" : null,\n"
        + "      \"expression\" : \"Extensions\"\n" + "    } ]\n" + "  }\n" + "}", propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  void testAddFieldExtensions5() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("Extensions");
    fieldExtension.setExpression(null);
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> extensions = new ArrayList<>();
    extensions.add(fieldExtension);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n  \"name\" : null,\n  \"stringValue\" : \"Extensions\"\n} ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"fields\" : [ {\n    \"name\" : null,\n    \"stringValue\" : \"Extensions\"\n  } ]\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : null,\n  \"stringValue\" : \"Extensions\"\n}", nextResult3.toPrettyString());
    assertEquals(
        "{\n" + "  \"servicetaskfields\" : {\n" + "    \"fields\" : [ {\n" + "      \"name\" : null,\n"
            + "      \"stringValue\" : \"Extensions\"\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  void testAddFieldExtensions6() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(null);
    fieldExtension.setExpression(null);
    fieldExtension.setFieldName("");

    ArrayList<FieldExtension> extensions = new ArrayList<>();
    extensions.add(fieldExtension);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n  \"name\" : \"\"\n} ]", nextResult2.toPrettyString());
    assertEquals("\"\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"fields\" : [ {\n    \"name\" : \"\"\n  } ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : \"\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"servicetaskfields\" : {\n    \"fields\" : [ {\n      \"name\" : \"\"\n    } ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) StringValue is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode); given FieldExtension (default constructor) StringValue is empty string")
  void testAddFieldExtensions_givenFieldExtensionStringValueIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("");
    fieldExtension.setExpression(null);
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> extensions = new ArrayList<>();
    extensions.add(fieldExtension);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("[ {\n  \"name\" : null\n} ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"fields\" : [ {\n    \"name\" : null\n  } ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : null\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"servicetaskfields\" : {\n    \"fields\" : [ {\n      \"name\" : null\n    } ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  void testAddEventProperties() throws IOException {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  void testAddEventProperties2() throws IOException {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  void testAddEventProperties3() throws IOException {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  void testAddEventProperties4() throws IOException {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  void testAddEventProperties5() throws IOException {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  void testAddEventProperties6() throws IOException {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  void testAddEventProperties7() throws IOException {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link CancelEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given CancelEventDefinition (default constructor)")
  void testAddEventProperties_givenCancelEventDefinition() {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ErrorEventDefinition} (default constructor) ErrorRef is
   * empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given ErrorEventDefinition (default constructor) ErrorRef is empty string")
  void testAddEventProperties_givenErrorEventDefinitionErrorRefIsEmptyString() {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ErrorEventDefinition} (default constructor) ErrorRef is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given ErrorEventDefinition (default constructor) ErrorRef is 'null'")
  void testAddEventProperties_givenErrorEventDefinitionErrorRefIsNull() {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link MessageEventDefinition} (default constructor) MessageRef is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given MessageEventDefinition (default constructor) MessageRef is 'null'")
  void testAddEventProperties_givenMessageEventDefinitionMessageRefIsNull() {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link SignalEventDefinition} (default constructor) SignalRef is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given SignalEventDefinition (default constructor) SignalRef is 'null'")
  void testAddEventProperties_givenSignalEventDefinitionSignalRefIsNull() {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link TimerEventDefinition} (default constructor) TimeDate is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given TimerEventDefinition (default constructor) TimeDate is 'null'")
  void testAddEventProperties_givenTimerEventDefinitionTimeDateIsNull() {
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
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); when BoundaryEvent (default constructor)")
  void testAddEventProperties_whenBoundaryEvent() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  void testConvertJsonToFormProperties() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  void testConvertJsonToFormProperties2() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  void testConvertJsonToFormProperties3() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  void testConvertJsonToFormProperties4() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayList() add Instance; then calls iterator()")
  void testConvertJsonToFormProperties_givenArrayListAddInstance_thenCallsIterator() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return '42'; then calls isTextual()")
  void testConvertJsonToFormProperties_givenArrayNodeAsTextReturn42_thenCallsIsTextual() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return 'As Text'")
  void testConvertJsonToFormProperties_givenArrayNodeAsTextReturnAsText() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return empty string")
  void testConvertJsonToFormProperties_givenArrayNodeAsTextReturnEmptyString() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return 'null'")
  void testConvertJsonToFormProperties_givenArrayNodeAsTextReturnNull() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode get(String) return Instance")
  void testConvertJsonToFormProperties_givenArrayNodeGetReturnInstance() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode get(String) return Instance; then calls isNull()")
  void testConvertJsonToFormProperties_givenArrayNodeGetReturnInstance_thenCallsIsNull() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode isNull() return 'true'; then calls iterator()")
  void testConvertJsonToFormProperties_givenArrayNodeIsNullReturnTrue_thenCallsIterator() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToFormProperties_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given Instance; when ArrayNode get(String) return Instance")
  void testConvertJsonToFormProperties_givenInstance_whenArrayNodeGetReturnInstance() {
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
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToTimerDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  void testConvertJsonToTimerDefinition_thenBoundaryEventEventDefinitionsSizeIsOne() {
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
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToTimerDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToTimerDefinition_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
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
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToSignalDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  void testConvertJsonToSignalDefinition_thenBoundaryEventEventDefinitionsSizeIsOne() {
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
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToSignalDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToSignalDefinition_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
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
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToMessageDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  void testConvertJsonToMessageDefinition_thenBoundaryEventEventDefinitionsSizeIsOne() {
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
    assertTrue(((MessageEventDefinition) getResult).getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToMessageDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToMessageDefinition_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
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
    assertTrue(((MessageEventDefinition) getResult).getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToErrorDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  void testConvertJsonToErrorDefinition_thenBoundaryEventEventDefinitionsSizeIsOne() {
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
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToErrorDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToErrorDefinition_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
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
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testGetValueAsString_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(
        associationJsonConverter.getValueAsString("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when Instance; then return 'null'")
  void testGetValueAsString_whenInstance_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsBoolean(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testGetValueAsBoolean_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(
        associationJsonConverter.getValueAsBoolean("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsBoolean(String, JsonNode); when Instance; then return 'false'")
  void testGetValueAsBoolean_whenInstance_thenReturnFalse() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(associationJsonConverter.getValueAsBoolean("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsList(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return Empty")
  void testGetValueAsList_whenArrayNodeWithNfIsWithExactBigDecimalsTrue_thenReturnEmpty() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(
        associationJsonConverter.getValueAsList("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)))
            .isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsList(String, JsonNode); when Instance; then return Empty")
  void testGetValueAsList_whenInstance_thenReturnEmpty() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(associationJsonConverter.getValueAsList("Name", MissingNode.getInstance()).isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   * with {@code name}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  void testAddFieldWithNameElementNodeTask() {
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
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   * with {@code name}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  void testAddFieldWithNameElementNodeTask2() {
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
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   * with {@code name}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  void testAddFieldWithNameElementNodeTask3() {
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
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   * with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; given ArrayNode get(String) return Instance; then calls get(String)")
  void testAddFieldWithNameElementNodeTask_givenArrayNodeGetReturnInstance_thenCallsGet() {
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
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   * with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; given ArrayNode get(String) return Instance; then calls get(String)")
  void testAddFieldWithNameElementNodeTask_givenArrayNodeGetReturnInstance_thenCallsGet2() {
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
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   * with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; given Instance; when ArrayNode get(String) return Instance")
  void testAddFieldWithNameElementNodeTask_givenInstance_whenArrayNodeGetReturnInstance() {
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
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   * with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor) FieldExtensions size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; then ServiceTask (default constructor) FieldExtensions size is one")
  void testAddFieldWithNameElementNodeTask_thenServiceTaskFieldExtensionsSizeIsOne() {
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
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   * with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; when Instance")
  void testAddFieldWithNameElementNodeTask_whenInstance() {
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
   * Test
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   * with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  void testAddFieldWithNamePropertyNameElementNodeTask() {
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
   * Test
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   * with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  void testAddFieldWithNamePropertyNameElementNodeTask2() {
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
   * Test
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   * with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  void testAddFieldWithNamePropertyNameElementNodeTask3() {
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
   * Test
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   * with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  void testAddFieldWithNamePropertyNameElementNodeTask4() {
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
   * Test
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   * with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; given ArrayNode get(String) return Instance")
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

    // Assert
    verify(arrayNode).get(eq("Property Name"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   * with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; given ArrayNode get(String) return Instance")
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

    // Assert
    verify(arrayNode).get(eq("Property Name"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   * with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; given Instance")
  void testAddFieldWithNamePropertyNameElementNodeTask_givenInstance() {
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
   * Test
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   * with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; when Instance")
  void testAddFieldWithNamePropertyNameElementNodeTask_whenInstance() {
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
   * Test
   * {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testGetPropertyValueAsString_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getPropertyValueAsString("Name",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsString(String, JsonNode); when Instance; then return 'null'")
  void testGetPropertyValueAsString_whenInstance_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getPropertyValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsBoolean(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testGetPropertyValueAsBoolean_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(associationJsonConverter.getPropertyValueAsBoolean("Name",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsBoolean(String, JsonNode); when Instance; then return 'false'")
  void testGetPropertyValueAsBoolean_whenInstance_thenReturnFalse() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(associationJsonConverter.getPropertyValueAsBoolean("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsList(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testGetPropertyValueAsList_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(associationJsonConverter
        .getPropertyValueAsList("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)))
        .isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsList(String, JsonNode); when Instance; then return Empty")
  void testGetPropertyValueAsList_whenInstance_thenReturnEmpty() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(associationJsonConverter.getPropertyValueAsList("Name", MissingNode.getInstance()).isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return 'null'")
  void testGetProperty_whenArrayNodeWithNfIsWithExactBigDecimalsTrue_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getProperty("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when Instance; then return 'null'")
  void testGetProperty_whenInstance_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getProperty("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code 42,foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); given ArrayList() add '42'; then return '42,foo'")
  void testConvertListToCommaSeparatedString_givenArrayListAdd42_thenReturn42Foo() {
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
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   * <ul>
   *   <li>Given {@code String List}.</li>
   *   <li>Then return {@code String List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); given 'String List'; then return 'String List'")
  void testConvertListToCommaSeparatedString_givenStringList_thenReturnStringList() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("String List");

    // Act and Assert
    assertEquals("String List", associationJsonConverter.convertListToCommaSeparatedString(stringList));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   * <ul>
   *   <li>Given three.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); given three; then return empty string")
  void testConvertListToCommaSeparatedString_givenThree_thenReturnEmptyString() {
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
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   * <ul>
   *   <li>Given two.</li>
   *   <li>When {@link COWArrayList} {@link COWArrayList#size()} return two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); given two; when COWArrayList size() return two")
  void testConvertListToCommaSeparatedString_givenTwo_whenCOWArrayListSizeReturnTwo() {
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

  /**
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); when ArrayList(); then return 'null'")
  void testConvertListToCommaSeparatedString_whenArrayList_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.convertListToCommaSeparatedString(new ArrayList<>()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); when 'null'; then return 'null'")
  void testConvertListToCommaSeparatedString_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new AssociationJsonConverter()).convertListToCommaSeparatedString(null));
  }
}
