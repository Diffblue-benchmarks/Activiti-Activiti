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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseBpmnJsonConverterDiffblueTest {
  /**
   * Test {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  @DisplayName("Test processDataStoreReferences(FlowElementsContainer, String, ArrayNode); given ArrayList() add AdhocSubProcess (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.processDataStoreReferences(FlowElementsContainer, String, ArrayNode)"})
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

    // Assert
    verify(container).getFlowElements();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link SubProcess#getFlowElements()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  @DisplayName("Test processDataStoreReferences(FlowElementsContainer, String, ArrayNode); given ArrayList() add 'null'; then calls getFlowElements()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.processDataStoreReferences(FlowElementsContainer, String, ArrayNode)"})
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

    // Assert
    verify(container).getFlowElements();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link SubProcess#getFlowElements()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  @DisplayName("Test processDataStoreReferences(FlowElementsContainer, String, ArrayNode); given ArrayList(); then calls getFlowElements()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.processDataStoreReferences(FlowElementsContainer, String, ArrayNode)"})
  void testProcessDataStoreReferences_givenArrayList_thenCallsGetFlowElements() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    // Act
    associationJsonConverter.processDataStoreReferences(container, "42",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    verify(container).getFlowElements();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given ArrayList() add Instance; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
  void testConvertToBpmnModel_givenArrayListAddInstance_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given Instance; when JsonNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
  void testConvertToBpmnModel_givenInstance_whenJsonNodeGetReturnInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return False.</li>
   *   <li>When Instance.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode get(String) return False; when Instance; then calls asText()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode get(String) return Instance; when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode get(String) return Instance; when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode isNull() return 'true'; then calls isNull()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
  void testConvertToBpmnModel_givenJsonNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Then calls {@link SubProcess#addArtifact(Artifact)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); then calls addArtifact(Artifact)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
  void testConvertToBpmnModel_thenCallsAddArtifact() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
  void testConvertToBpmnModel_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
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
    assertTrue(associationJsonConverter.processor instanceof BpmnJsonConverter);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Artifacts size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); when AdhocSubProcess (default constructor); then AdhocSubProcess (default constructor) Artifacts size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"})
  void testConvertToBpmnModel_whenAdhocSubProcess_thenAdhocSubProcessArtifactsSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNode jsonNode = mock(JsonNode.class);
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
   * Test {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}
   */
  @Test
  @DisplayName("Test setPropertyValue(String, String, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.setPropertyValue(String, String, ObjectNode)"})
  void testSetPropertyValue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.setPropertyValue("Name", "42", propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals("{\n  \"Name\" : \"42\"\n}", propertiesNode.toPrettyString());
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}
   */
  @Test
  @DisplayName("Test setPropertyValue(String, String, ObjectNode); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.setPropertyValue(String, String, ObjectNode)"})
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
   * Test {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}
   */
  @Test
  @DisplayName("Test setPropertyValue(String, String, ObjectNode); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.setPropertyValue(String, String, ObjectNode)"})
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
   * Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayList<FormProperty> formProperties = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertFalse(propertiesNode.iterator().hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties2() {
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
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setId("");
    formValue.setName(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("not empty");
    formProperty.setFormValues(formValues);
    formProperty.setName(null);
    formProperty.setType(null);
    formProperty.setId("Form Properties");

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
    assertEquals("[ {\n" + "  \"id\" : \"Form Properties\",\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : \"not empty\",\n" + "  \"variable\" : \"not empty\",\n"
        + "  \"datePattern\" : \"not empty\",\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n"
        + "    \"id\" : \"\"\n" + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n"
        + "  \"writable\" : true\n" + "} ]", nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : \"Form Properties\",\n"
        + "    \"name\" : null,\n" + "    \"type\" : null,\n" + "    \"expression\" : \"not empty\",\n"
        + "    \"variable\" : \"not empty\",\n" + "    \"datePattern\" : \"not empty\",\n"
        + "    \"enumValues\" : [ {\n" + "      \"name\" : null,\n" + "      \"id\" : \"\"\n" + "    } ],\n"
        + "    \"required\" : false,\n" + "    \"readable\" : true,\n" + "    \"writable\" : true\n" + "  } ]\n" + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n"
            + "      \"id\" : \"Form Properties\",\n" + "      \"name\" : null,\n" + "      \"type\" : null,\n"
            + "      \"expression\" : \"not empty\",\n" + "      \"variable\" : \"not empty\",\n"
            + "      \"datePattern\" : \"not empty\",\n" + "      \"enumValues\" : [ {\n" + "        \"name\" : null,\n"
            + "        \"id\" : \"\"\n" + "      } ],\n" + "      \"required\" : false,\n"
            + "      \"readable\" : true,\n" + "      \"writable\" : true\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals("{\n" + "  \"id\" : \"Form Properties\",\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : \"not empty\",\n" + "  \"variable\" : \"not empty\",\n"
        + "  \"datePattern\" : \"not empty\",\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n"
        + "    \"id\" : \"\"\n" + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n"
        + "  \"writable\" : true\n" + "}", nextResult3.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(nextResult3.iterator().hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) DatePattern is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode); given FormProperty (default constructor) DatePattern is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties_givenFormPropertyDatePatternIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setId(null);
    formValue.setName(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("");
    formProperty.setFormValues(formValues);
    formProperty.setName(null);
    formProperty.setType(null);
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
        + "  \"expression\" : \"not empty\",\n" + "  \"variable\" : \"not empty\",\n" + "  \"enumValues\" : [ {\n"
        + "    \"name\" : null,\n" + "    \"id\" : null\n" + "  } ],\n" + "  \"required\" : false,\n"
        + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "} ]", nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : \"not empty\",\n" + "    \"variable\" : \"not empty\",\n"
        + "    \"enumValues\" : [ {\n" + "      \"name\" : null,\n" + "      \"id\" : null\n" + "    } ],\n"
        + "    \"required\" : false,\n" + "    \"readable\" : true,\n" + "    \"writable\" : true\n" + "  } ]\n" + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
            + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : \"not empty\",\n"
            + "      \"variable\" : \"not empty\",\n" + "      \"enumValues\" : [ {\n" + "        \"name\" : null,\n"
            + "        \"id\" : null\n" + "      } ],\n" + "      \"required\" : false,\n"
            + "      \"readable\" : true,\n" + "      \"writable\" : true\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals("{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : \"not empty\",\n" + "  \"variable\" : \"not empty\",\n" + "  \"enumValues\" : [ {\n"
        + "    \"name\" : null,\n" + "    \"id\" : null\n" + "  } ],\n" + "  \"required\" : false,\n"
        + "  \"readable\" : true,\n" + "  \"writable\" : true\n" + "}", nextResult3.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode); given FormProperty (default constructor) FormValues is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties_givenFormPropertyFormValuesIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("not empty");
    formProperty.setFormValues(null);
    formProperty.setName(null);
    formProperty.setType(null);
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
        + "  \"expression\" : \"not empty\",\n" + "  \"variable\" : \"not empty\",\n"
        + "  \"datePattern\" : \"not empty\",\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n"
        + "  \"writable\" : true\n" + "} ]", nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : \"not empty\",\n" + "    \"variable\" : \"not empty\",\n"
        + "    \"datePattern\" : \"not empty\",\n" + "    \"required\" : false,\n" + "    \"readable\" : true,\n"
        + "    \"writable\" : true\n" + "  } ]\n" + "}", nextResult.toPrettyString());
    assertEquals("{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
        + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : \"not empty\",\n"
        + "      \"variable\" : \"not empty\",\n" + "      \"datePattern\" : \"not empty\",\n"
        + "      \"required\" : false,\n" + "      \"readable\" : true,\n" + "      \"writable\" : true\n" + "    } ]\n"
        + "  }\n" + "}", propertiesNode.toPrettyString());
    assertEquals("{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : \"not empty\",\n" + "  \"variable\" : \"not empty\",\n"
        + "  \"datePattern\" : \"not empty\",\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n"
        + "  \"writable\" : true\n" + "}", nextResult3.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FormValue} (default constructor) Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode); given FormValue (default constructor) Id is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties_givenFormValueIdIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setId(null);
    formValue.setName(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("not empty");
    formProperty.setFormValues(formValues);
    formProperty.setName(null);
    formProperty.setType(null);
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
        + "  \"expression\" : \"not empty\",\n" + "  \"variable\" : \"not empty\",\n"
        + "  \"datePattern\" : \"not empty\",\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n"
        + "    \"id\" : null\n" + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n"
        + "  \"writable\" : true\n" + "} ]", nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"formProperties\" : [ {\n" + "    \"id\" : null,\n" + "    \"name\" : null,\n"
        + "    \"type\" : null,\n" + "    \"expression\" : \"not empty\",\n" + "    \"variable\" : \"not empty\",\n"
        + "    \"datePattern\" : \"not empty\",\n" + "    \"enumValues\" : [ {\n" + "      \"name\" : null,\n"
        + "      \"id\" : null\n" + "    } ],\n" + "    \"required\" : false,\n" + "    \"readable\" : true,\n"
        + "    \"writable\" : true\n" + "  } ]\n" + "}", nextResult.toPrettyString());
    assertEquals("{\n" + "  \"formproperties\" : {\n" + "    \"formProperties\" : [ {\n" + "      \"id\" : null,\n"
        + "      \"name\" : null,\n" + "      \"type\" : null,\n" + "      \"expression\" : \"not empty\",\n"
        + "      \"variable\" : \"not empty\",\n" + "      \"datePattern\" : \"not empty\",\n"
        + "      \"enumValues\" : [ {\n" + "        \"name\" : null,\n" + "        \"id\" : null\n" + "      } ],\n"
        + "      \"required\" : false,\n" + "      \"readable\" : true,\n" + "      \"writable\" : true\n" + "    } ]\n"
        + "  }\n" + "}", propertiesNode.toPrettyString());
    assertEquals("{\n" + "  \"id\" : null,\n" + "  \"name\" : null,\n" + "  \"type\" : null,\n"
        + "  \"expression\" : \"not empty\",\n" + "  \"variable\" : \"not empty\",\n"
        + "  \"datePattern\" : \"not empty\",\n" + "  \"enumValues\" : [ {\n" + "    \"name\" : null,\n"
        + "    \"id\" : null\n" + "  } ],\n" + "  \"required\" : false,\n" + "  \"readable\" : true,\n"
        + "  \"writable\" : true\n" + "}", nextResult3.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFieldExtensions(List, ObjectNode)"})
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
   * Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
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
    assertEquals("[ {\n  \"name\" : null,\n  \"stringValue\" : \"not empty\",\n  \"expression\" : \"not empty\"\n} ]",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"fields\" : [ {\n" + "    \"name\" : null,\n" + "    \"stringValue\" : \"not empty\",\n"
        + "    \"expression\" : \"not empty\"\n" + "  } ]\n" + "}", nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : null,\n  \"stringValue\" : \"not empty\",\n  \"expression\" : \"not empty\"\n}",
        nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"servicetaskfields\" : {\n" + "    \"fields\" : [ {\n" + "      \"name\" : null,\n"
        + "      \"stringValue\" : \"not empty\",\n" + "      \"expression\" : \"not empty\"\n" + "    } ]\n" + "  }\n"
        + "}", propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFieldExtensions(List, ObjectNode)"})
  void testAddFieldExtensions3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(null);
    fieldExtension.setExpression("not empty");
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
    assertEquals("[ {\n  \"name\" : null,\n  \"expression\" : \"not empty\"\n} ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"fields\" : [ {\n    \"name\" : null,\n    \"expression\" : \"not empty\"\n  } ]\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : null,\n  \"expression\" : \"not empty\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"servicetaskfields\" : {\n" + "    \"fields\" : [ {\n" + "      \"name\" : null,\n"
        + "      \"expression\" : \"not empty\"\n" + "    } ]\n" + "  }\n" + "}", propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFieldExtensions(List, ObjectNode)"})
  void testAddFieldExtensions4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("not empty");
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
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertEquals("[ {\n  \"name\" : \"\",\n  \"stringValue\" : \"not empty\",\n  \"expression\" : \"not empty\"\n} ]",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"fields\" : [ {\n" + "    \"name\" : \"\",\n" + "    \"stringValue\" : \"not empty\",\n"
        + "    \"expression\" : \"not empty\"\n" + "  } ]\n" + "}", nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : \"\",\n  \"stringValue\" : \"not empty\",\n  \"expression\" : \"not empty\"\n}",
        nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"servicetaskfields\" : {\n" + "    \"fields\" : [ {\n" + "      \"name\" : \"\",\n"
        + "      \"stringValue\" : \"not empty\",\n" + "      \"expression\" : \"not empty\"\n" + "    } ]\n" + "  }\n"
        + "}", propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode); given FieldExtension (default constructor) Expression is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFieldExtensions(List, ObjectNode)"})
  void testAddFieldExtensions_givenFieldExtensionExpressionIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("");
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
    assertEquals("[ {\n  \"name\" : null,\n  \"stringValue\" : \"not empty\"\n} ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"fields\" : [ {\n    \"name\" : null,\n    \"stringValue\" : \"not empty\"\n  } ]\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : null,\n  \"stringValue\" : \"not empty\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"servicetaskfields\" : {\n" + "    \"fields\" : [ {\n" + "      \"name\" : null,\n"
        + "      \"stringValue\" : \"not empty\"\n" + "    } ]\n" + "  }\n" + "}", propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode); given FieldExtension (default constructor) Expression is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFieldExtensions(List, ObjectNode)"})
  void testAddFieldExtensions_givenFieldExtensionExpressionIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("not empty");
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
    assertEquals("[ {\n  \"name\" : null,\n  \"stringValue\" : \"not empty\"\n} ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"fields\" : [ {\n    \"name\" : null,\n    \"stringValue\" : \"not empty\"\n  } ]\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"name\" : null,\n  \"stringValue\" : \"not empty\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"servicetaskfields\" : {\n" + "    \"fields\" : [ {\n" + "      \"name\" : null,\n"
        + "      \"stringValue\" : \"not empty\"\n" + "    } ]\n" + "  }\n" + "}", propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
  void testAddEventProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef("not empty");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(signalEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals("{\n  \"signalref\" : \"not empty\"\n}", propertiesNode.toPrettyString());
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
  void testAddEventProperties2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ErrorEventDefinition errorEventDefinition = new ErrorEventDefinition();
    errorEventDefinition.setErrorRef("not empty");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(errorEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals("{\n  \"errorref\" : \"not empty\"\n}", propertiesNode.toPrettyString());
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
  void testAddEventProperties3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setMessageRef("not empty");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(messageEventDefinition);

    BoundaryEvent event = new BoundaryEvent();
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals("{\n  \"messageref\" : \"not empty\"\n}", propertiesNode.toPrettyString());
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link CancelEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given CancelEventDefinition (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
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
   *   <li>Given {@link ErrorEventDefinition} (default constructor) ErrorRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given ErrorEventDefinition (default constructor) ErrorRef is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
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
   *   <li>Given {@link MessageEventDefinition} (default constructor) MessageRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given MessageEventDefinition (default constructor) MessageRef is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
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
   *   <li>Given {@link SignalEventDefinition} (default constructor) SignalRef is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given SignalEventDefinition (default constructor) SignalRef is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
  void testAddEventProperties_givenSignalEventDefinitionSignalRefIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef("");

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
   *   <li>Given {@link SignalEventDefinition} (default constructor) SignalRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given SignalEventDefinition (default constructor) SignalRef is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
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
   *   <li>Given {@link TimerEventDefinition} (default constructor) EndDate is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given TimerEventDefinition (default constructor) EndDate is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
  void testAddEventProperties_givenTimerEventDefinitionEndDateIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    BoundaryEvent event = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDuration("not empty");
    timerEventDefinition.setTimeCycle("not empty");
    timerEventDefinition.setTimeDate("not empty");
    timerEventDefinition.setEndDate(null);
    eventDefinitions.add(timerEventDefinition);
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    JsonNode nextResult2 = iteratorResult.next();
    JsonNode nextResult3 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult2 instanceof TextNode);
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("{\n" + "  \"timerdurationdefinition\" : \"not empty\",\n"
        + "  \"timercycledefinition\" : \"not empty\",\n" + "  \"timerdatedefinition\" : \"not empty\"\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(3, propertiesNode.size());
    assertFalse(actualHasNextResult);
    assertEquals(nextResult, nextResult2);
    assertEquals(nextResult, nextResult3);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link TimerEventDefinition} (default constructor) TimeCycle is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given TimerEventDefinition (default constructor) TimeCycle is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
  void testAddEventProperties_givenTimerEventDefinitionTimeCycleIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    BoundaryEvent event = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDuration("not empty");
    timerEventDefinition.setTimeCycle(null);
    timerEventDefinition.setTimeDate("not empty");
    timerEventDefinition.setEndDate("not empty");
    eventDefinitions.add(timerEventDefinition);
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    JsonNode nextResult2 = iteratorResult.next();
    JsonNode nextResult3 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult2 instanceof TextNode);
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("{\n" + "  \"timerdurationdefinition\" : \"not empty\",\n"
        + "  \"timerdatedefinition\" : \"not empty\",\n" + "  \"timerenddatedefinition\" : \"not empty\"\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(3, propertiesNode.size());
    assertFalse(actualHasNextResult);
    assertEquals(nextResult, nextResult2);
    assertEquals(nextResult, nextResult3);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link TimerEventDefinition} (default constructor) TimeDate is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given TimerEventDefinition (default constructor) TimeDate is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
  void testAddEventProperties_givenTimerEventDefinitionTimeDateIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    BoundaryEvent event = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDuration("not empty");
    timerEventDefinition.setTimeCycle("not empty");
    timerEventDefinition.setTimeDate(null);
    timerEventDefinition.setEndDate("not empty");
    eventDefinitions.add(timerEventDefinition);
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    JsonNode nextResult2 = iteratorResult.next();
    JsonNode nextResult3 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult2 instanceof TextNode);
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("{\n" + "  \"timerdurationdefinition\" : \"not empty\",\n"
        + "  \"timercycledefinition\" : \"not empty\",\n" + "  \"timerenddatedefinition\" : \"not empty\"\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(3, propertiesNode.size());
    assertFalse(actualHasNextResult);
    assertEquals(nextResult, nextResult2);
    assertEquals(nextResult, nextResult3);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>Given {@link TimerEventDefinition} (default constructor) TimeDuration is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); given TimerEventDefinition (default constructor) TimeDuration is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
  void testAddEventProperties_givenTimerEventDefinitionTimeDurationIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    BoundaryEvent event = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDuration("");
    timerEventDefinition.setTimeCycle("not empty");
    timerEventDefinition.setTimeDate("not empty");
    timerEventDefinition.setEndDate("not empty");
    eventDefinitions.add(timerEventDefinition);
    event.setEventDefinitions(eventDefinitions);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    associationJsonConverter.addEventProperties(event, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    JsonNode nextResult2 = iteratorResult.next();
    JsonNode nextResult3 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult2 instanceof TextNode);
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("{\n" + "  \"timercycledefinition\" : \"not empty\",\n"
        + "  \"timerdatedefinition\" : \"not empty\",\n" + "  \"timerenddatedefinition\" : \"not empty\"\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(3, propertiesNode.size());
    assertFalse(actualHasNextResult);
    assertEquals(nextResult, nextResult2);
    assertEquals(nextResult, nextResult3);
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addEventProperties(Event, ObjectNode)}
   */
  @Test
  @DisplayName("Test addEventProperties(Event, ObjectNode); when BoundaryEvent (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addEventProperties(Event, ObjectNode)"})
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
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
  void testConvertJsonToFormProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
  void testConvertJsonToFormProperties2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
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

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).get(eq("formProperties"));
    verify(arrayNode2).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
  void testConvertJsonToFormProperties4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayList<FormProperty> formProperties = new ArrayList<>();
    associationJsonConverter.addFormProperties(formProperties,
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
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
    verify(arrayNode2).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayList() add Instance; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
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

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("formProperties"));
    verify(arrayNode3).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return '42'; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
  void testConvertJsonToFormProperties_givenArrayNodeAsTextReturn42_thenCallsIsTextual() {
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
    verify(arrayNode2).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
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
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
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

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("formProperties"));
    verify(arrayNode3).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
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

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("formProperties"));
    verify(arrayNode3).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
  void testConvertJsonToFormProperties_givenArrayNodeGetReturnInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode get(String) return Instance; then calls isNull()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
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

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).get(eq("formProperties"));
    verify(arrayNode2).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode isNull() return 'true'; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
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

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("formProperties"));
    verify(arrayNode3).get(eq("formproperties"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
  void testConvertJsonToFormProperties_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"})
  void testConvertJsonToFormProperties_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToTimerDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToTimerDefinition(JsonNode, Event)"})
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
   * Test {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToTimerDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToTimerDefinition(JsonNode, Event)"})
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
   * Test {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToSignalDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToSignalDefinition(JsonNode, Event)"})
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
   * Test {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToSignalDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToSignalDefinition(JsonNode, Event)"})
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
   * Test {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToMessageDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToMessageDefinition(JsonNode, Event)"})
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
   * Test {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToMessageDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToMessageDefinition(JsonNode, Event)"})
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
   * Test {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToErrorDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToErrorDefinition(JsonNode, Event)"})
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
   * Test {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}
   */
  @Test
  @DisplayName("Test convertJsonToErrorDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToErrorDefinition(JsonNode, Event)"})
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
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BaseBpmnJsonConverter.getValueAsString(String, JsonNode)"})
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
   * Method under test: {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when Instance; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BaseBpmnJsonConverter.getValueAsString(String, JsonNode)"})
  void testGetValueAsString_whenInstance_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsBoolean(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseBpmnJsonConverter.getValueAsBoolean(String, JsonNode)"})
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
   * Method under test: {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsBoolean(String, JsonNode); when Instance; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseBpmnJsonConverter.getValueAsBoolean(String, JsonNode)"})
  void testGetValueAsBoolean_whenInstance_thenReturnFalse() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(associationJsonConverter.getValueAsBoolean("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsList(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BaseBpmnJsonConverter.getValueAsList(String, JsonNode)"})
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
   * Method under test: {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsList(String, JsonNode); when Instance; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BaseBpmnJsonConverter.getValueAsList(String, JsonNode)"})
  void testGetValueAsList_whenInstance_thenReturnEmpty() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(associationJsonConverter.getValueAsList("Name", MissingNode.getInstance()).isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
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

    // Assert that nothing has changed
    verify(arrayNode).get(eq("CamelTask"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; given ArrayNode get(String) return Instance; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
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

    // Assert that nothing has changed
    verify(arrayNode).get(eq("CamelTask"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; given ArrayNode get(String) return Instance; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
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

    // Assert that nothing has changed
    verify(arrayNode).get(eq("CamelTask"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor) FieldExtensions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; then ServiceTask (default constructor) FieldExtensions size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
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
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask_whenInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
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

    // Assert that nothing has changed
    verify(arrayNode).get(eq("Property Name"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
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
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; given ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
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
    verify(arrayNode).get(eq("Property Name"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; given ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
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
    verify(arrayNode).get(eq("Property Name"));
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>Given Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; given Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask_givenInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get(eq("properties"));
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code name}, {@code propertyName}, {@code elementNode}, {@code task}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask_whenInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BaseBpmnJsonConverter.getPropertyValueAsString(String, JsonNode)"})
  void testGetPropertyValueAsString_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getPropertyValueAsString("Name",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsString(String, JsonNode); when Instance; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BaseBpmnJsonConverter.getPropertyValueAsString(String, JsonNode)"})
  void testGetPropertyValueAsString_whenInstance_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getPropertyValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsBoolean(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseBpmnJsonConverter.getPropertyValueAsBoolean(String, JsonNode)"})
  void testGetPropertyValueAsBoolean_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(associationJsonConverter.getPropertyValueAsBoolean("Name",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsBoolean(String, JsonNode); when Instance; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseBpmnJsonConverter.getPropertyValueAsBoolean(String, JsonNode)"})
  void testGetPropertyValueAsBoolean_whenInstance_thenReturnFalse() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertFalse(associationJsonConverter.getPropertyValueAsBoolean("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsList(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BaseBpmnJsonConverter.getPropertyValueAsList(String, JsonNode)"})
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
   * Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsList(String, JsonNode); when Instance; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BaseBpmnJsonConverter.getPropertyValueAsList(String, JsonNode)"})
  void testGetPropertyValueAsList_whenInstance_thenReturnEmpty() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertTrue(associationJsonConverter.getPropertyValueAsList("Name", MissingNode.getInstance()).isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode BaseBpmnJsonConverter.getProperty(String, JsonNode)"})
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
   * Method under test: {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when Instance; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode BaseBpmnJsonConverter.getProperty(String, JsonNode)"})
  void testGetProperty_whenInstance_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.getProperty("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   * <ul>
   *   <li>Given {@code String List}.</li>
   *   <li>Then return {@code String List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); given 'String List'; then return 'String List'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BaseBpmnJsonConverter.convertListToCommaSeparatedString(List)"})
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
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); when ArrayList(); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BaseBpmnJsonConverter.convertListToCommaSeparatedString(List)"})
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
   * Method under test: {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BaseBpmnJsonConverter.convertListToCommaSeparatedString(List)"})
  void testConvertListToCommaSeparatedString_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new AssociationJsonConverter()).convertListToCommaSeparatedString(null));
  }
}
