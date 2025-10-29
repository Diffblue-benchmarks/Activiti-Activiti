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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
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
import org.activiti.editor.language.json.model.ModelInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnJsonConverterDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link BpmnJsonConverter.FlowWithContainer#FlowWithContainer(BpmnJsonConverter, SequenceFlow, FlowElementsContainer)}
   *   <li>
   * {@link BpmnJsonConverter.FlowWithContainer#setFlowContainer(FlowElementsContainer)}
   *   <li>{@link BpmnJsonConverter.FlowWithContainer#setSequenceFlow(SequenceFlow)}
   *   <li>{@link BpmnJsonConverter.FlowWithContainer#getFlowContainer()}
   *   <li>{@link BpmnJsonConverter.FlowWithContainer#getSequenceFlow()}
   * </ul>
   */
  @Test
  void testFlowWithContainerGettersAndSetters() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    SequenceFlow sequenceFlow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    BpmnJsonConverter.FlowWithContainer actualFlowWithContainer = bpmnJsonConverter.new FlowWithContainer(sequenceFlow,
        new AdhocSubProcess());
    AdhocSubProcess flowContainer = new AdhocSubProcess();
    actualFlowWithContainer.setFlowContainer(flowContainer);
    SequenceFlow sequenceFlow2 = new SequenceFlow("Source Ref", "Target Ref");

    actualFlowWithContainer.setSequenceFlow(sequenceFlow2);
    FlowElementsContainer actualFlowContainer = actualFlowWithContainer.getFlowContainer();

    // Assert that nothing has changed
    assertSame(flowContainer, actualFlowContainer);
    assertSame(sequenceFlow2, actualFlowWithContainer.getSequenceFlow());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements() {
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
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements2() {
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
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements3() {
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
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements4() {
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

    // Assert
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements5() {
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
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements6() {
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

    // Assert
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements7() {
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
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements8() {
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

    // Assert
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements9() {
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

    // Assert
    verify(model).getFlowLocationGraphicInfo(isNull());
    verify(model, atLeast(1)).getGraphicInfo(isNull());
    verify(container).getArtifacts();
    verify(container).getFlowElements();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements10() {
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
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements11() {
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
   * Method under test:
   * {@link BpmnJsonConverter#processFlowElements(FlowElementsContainer, BpmnModel, ArrayNode, Map, Map, double, double)}
   */
  @Test
  void testProcessFlowElements12() {
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
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    AdhocSubProcess container = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts2() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    Process container = new Process();
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, model, shapesArrayNode, 10.0d, 10.0d);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts3() {
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
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts4() {
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

    // Assert
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts5() {
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

    // Assert
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts6() {
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
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts7() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getArtifacts()).thenReturn(artifactList);
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processArtifacts(container, null, shapesArrayNode, 10.0d, 10.0d);

    // Assert
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts8() {
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

    // Assert
    verify(model).getFlowLocationGraphicInfo(isNull());
    verify(model, atLeast(1)).getGraphicInfo(isNull());
    verify(container).getArtifacts();
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts9() {
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
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts10() {
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
   * Method under test:
   * {@link BpmnJsonConverter#processArtifacts(FlowElementsContainer, BpmnModel, ArrayNode, double, double)}
   */
  @Test
  void testProcessArtifacts11() {
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
   * Method under test:
   * {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  void testProcessMessageFlows() {
    // Arrange
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    BpmnModel model = new BpmnModel();
    ArrayNode shapesArrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    bpmnJsonConverter.processMessageFlows(model, shapesArrayNode);

    // Assert that nothing has changed
    assertEquals("[ ]", shapesArrayNode.toPrettyString());
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  void testProcessMessageFlows2() {
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
    assertEquals(0, shapesArrayNode.size());
    assertFalse(shapesArrayNode.iterator().hasNext());
    assertFalse(shapesArrayNode.elements().hasNext());
    assertTrue(shapesArrayNode.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processMessageFlows(BpmnModel, ArrayNode)}
   */
  @Test
  void testProcessMessageFlows3() {
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
   * Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode)}
   */
  @Test
  void testConvertToBpmnModel() {
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
   * Method under test: {@link BpmnJsonConverter#convertToBpmnModel(JsonNode)}
   */
  @Test
  void testConvertToBpmnModel2() {
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
   * Method under test:
   * {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)}
   */
  @Test
  void testConvertToBpmnModel3() {
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
   * Method under test:
   * {@link BpmnJsonConverter#convertToBpmnModel(JsonNode, Map, Map)}
   */
  @Test
  void testConvertToBpmnModel4() {
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
   * Method under test:
   * {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  void testProcessJsonElements() {
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

    // Assert that nothing has changed
    verify(shapesArrayNode).iterator();
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  void testProcessJsonElements2() {
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

    // Assert that nothing has changed
    verify(shapesArrayNode).iterator();
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  void testProcessJsonElements3() {
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

    // Assert that nothing has changed
    verify(shapesArrayNode).iterator();
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  void testProcessJsonElements4() {
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

    // Assert that nothing has changed
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(eq("stencil"));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  void testProcessJsonElements5() {
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

    // Assert that nothing has changed
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(eq("stencil"));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  void testProcessJsonElements6() {
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

    // Assert that nothing has changed
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode2, atLeast(1)).get(eq("stencil"));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverter#processJsonElements(JsonNode, JsonNode, BaseElement, Map, Map, Map, BpmnModel)}
   */
  @Test
  void testProcessJsonElements7() {
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

    // Assert that nothing has changed
    verify(shapesArrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode2, atLeast(1)).get(eq("stencil"));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link BpmnJsonConverter}
   */
  @Test
  void testNewBpmnJsonConverter() throws MissingResourceException {
    // Arrange and Act
    BpmnJsonConverter actualBpmnJsonConverter = new BpmnJsonConverter();

    // Assert
    ObjectMapper objectMapper = actualBpmnJsonConverter.objectMapper;
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertTrue(serializationConfig.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    ContextAttributes attributes = deserializationConfig.getAttributes();
    assertTrue(attributes instanceof ContextAttributes.Impl);
    CacheProvider cacheProvider = deserializationConfig.getCacheProvider();
    assertTrue(cacheProvider instanceof DefaultCacheProvider);
    DeserializationContext deserializationContext = objectMapper.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ClassIntrospector classIntrospector = deserializationConfig.getClassIntrospector();
    assertTrue(classIntrospector instanceof BasicClassIntrospector);
    AccessorNamingStrategy.Provider accessorNaming = deserializationConfig.getAccessorNaming();
    assertTrue(accessorNaming instanceof DefaultAccessorNamingStrategy.Provider);
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    VisibilityChecker<?> visibilityChecker = objectMapper.getVisibilityChecker();
    assertTrue(visibilityChecker instanceof VisibilityChecker.Std);
    PolymorphicTypeValidator polymorphicTypeValidator = objectMapper.getPolymorphicTypeValidator();
    assertTrue(polymorphicTypeValidator instanceof LaissezFaireSubTypeValidator);
    SubtypeResolver subtypeResolver = objectMapper.getSubtypeResolver();
    assertTrue(subtypeResolver instanceof StdSubtypeResolver);
    SerializerFactory serializerFactory = objectMapper.getSerializerFactory();
    assertTrue(serializerFactory instanceof BeanSerializerFactory);
    SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
    assertTrue(serializerProvider instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    JsonSerializer<Object> defaultNullKeySerializer = serializerProvider.getDefaultNullKeySerializer();
    assertTrue(defaultNullKeySerializer instanceof FailingSerializer);
    JsonSerializer<Object> defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
    assertTrue(defaultNullValueSerializer instanceof NullSerializer);
    DeserializerFactoryConfig factoryConfig = ((BeanDeserializerFactory) factory2).getFactoryConfig();
    Iterable<Deserializers> deserializersResult = factoryConfig.deserializers();
    assertTrue(deserializersResult instanceof ArrayIterator);
    SerializerFactoryConfig factoryConfig2 = ((BeanSerializerFactory) serializerFactory).getFactoryConfig();
    Iterable<Serializers> serializersResult = factoryConfig2.serializers();
    assertTrue(serializersResult instanceof ArrayIterator);
    DateFormat dateFormat = objectMapper.getDateFormat();
    assertTrue(dateFormat instanceof StdDateFormat);
    assertEquals(" ", factory.getRootValueSeparator());
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    assertEquals("JSON", factory.getFormatName());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    assertEquals("[one of: 'yyyy-MM-dd'T'HH:mm:ss.SSSX', 'EEE, dd MMM yyyy HH:mm:ss zzz' (lenient)]",
        ((StdDateFormat) dateFormat).toPattern());
    Version versionResult = factory.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    Version versionResult2 = objectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult2.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-core/2.17.2", versionResult.toFullString());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult2.toFullString());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("jackson-core", versionResult.getArtifactId());
    assertEquals("jackson-databind", versionResult2.getArtifactId());
    assertEquals('=', base64Variant.getPaddingChar());
    assertNull(serializerProvider.getGenerator());
    assertNull(serializerProviderInstance.getGenerator());
    assertNull(deserializationContext.getParser());
    assertNull(factory.getCharacterEscapes());
    assertNull(factory.getInputDecorator());
    assertNull(factory.getOutputDecorator());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper.getInjectableValues());
    assertNull(deserializationContext.getContextualType());
    assertNull(defaultNullKeySerializer.getDelegatee());
    assertNull(defaultNullValueSerializer.getDelegatee());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(serializationConfig.getFullRootName());
    assertNull(objectMapper.getPropertyNamingStrategy());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(serializationConfig.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getFilterProvider());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(serializationConfig.getDefaultMergeable());
    assertNull(factory.getFormatReadFeatureType());
    assertNull(factory.getFormatWriteFeatureType());
    JsonInclude.Value defaultPropertyInclusion = deserializationConfig.getDefaultPropertyInclusion();
    assertNull(defaultPropertyInclusion.getContentFilter());
    assertNull(defaultPropertyInclusion.getValueFilter());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(serializerProviderInstance.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(serializationConfig.getActiveView());
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    assertNull(typeFactory.getClassLoader());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    assertNull(dateFormat.getNumberFormat());
    assertNull(dateFormat.getCalendar());
    assertNull(dateFormat.getTimeZone());
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(1, factory.getParserFeatures());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(17, versionResult2.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult2.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, versionResult2.getPatchLevel());
    assertEquals(2079, factory.getGeneratorFeatures());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(31, factory.getFactoryFeatures());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
    assertEquals(9999, nodeFactory.getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult2.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult2.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(versionResult2.isUnknownVersion());
    assertFalse(defaultNullKeySerializer.isUnwrappingSerializer());
    assertFalse(defaultNullValueSerializer.isUnwrappingSerializer());
    assertFalse(factoryConfig.hasAbstractTypeResolvers());
    assertFalse(factoryConfig.hasDeserializerModifiers());
    assertFalse(factoryConfig.hasDeserializers());
    assertFalse(factoryConfig.hasValueInstantiators());
    assertFalse(deserializationConfig.hasExplicitTimeZone());
    assertFalse(serializationConfig.hasExplicitTimeZone());
    assertFalse(factoryConfig2.hasKeySerializers());
    assertFalse(factoryConfig2.hasSerializerModifiers());
    assertFalse(factoryConfig2.hasSerializers());
    assertFalse(((ArrayIterator<Deserializers>) deserializersResult).hasNext());
    assertFalse(((ArrayIterator<Serializers>) serializersResult).hasNext());
    assertFalse(locale.hasExtensions());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(((StdDateFormat) dateFormat).isColonIncludedInTimeZone());
    assertTrue(dateFormat.isLenient());
    Set<Object> registeredModuleIds = objectMapper.getRegisteredModuleIds();
    assertTrue(registeredModuleIds.isEmpty());
    assertEquals(Integer.MAX_VALUE, base64Variant.getMaxLineLength());
    assertEquals('=', base64Variant.getPaddingByte());
    assertSame(nodeFactory, deserializationConfig.getNodeFactory());
    assertSame(registeredModuleIds, locale.getExtensionKeys());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleAttributes());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleKeys());
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    assertSame(typeFactory, serializerProviderInstance.getTypeFactory());
    assertSame(typeFactory, deserializationConfig.getTypeFactory());
    assertSame(typeFactory, serializationConfig.getTypeFactory());
    assertSame(versionResult2, annotationIntrospector.version());
    assertSame(base64Variant, serializationConfig.getBase64Variant());
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    assertSame(timeZone, serializerProviderInstance.getTimeZone());
    assertSame(timeZone, serializationConfig.getTimeZone());
    assertSame(defaultPropertyInclusion, serializationConfig.getDefaultPropertyInclusion());
    assertSame(defaultSetterInfo, serializationConfig.getDefaultSetterInfo());
    ObjectMapper expectedCodec = actualBpmnJsonConverter.objectMapper;
    assertSame(expectedCodec, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(attributes, serializationConfig.getAttributes());
    assertSame(cacheProvider, serializationConfig.getCacheProvider());
    assertSame(classIntrospector, serializationConfig.getClassIntrospector());
    assertSame(accessorNaming, serializationConfig.getAccessorNaming());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
    assertSame(visibilityChecker, deserializationConfig.getDefaultVisibilityChecker());
    assertSame(visibilityChecker, serializationConfig.getDefaultVisibilityChecker());
    assertSame(polymorphicTypeValidator, deserializationConfig.getPolymorphicTypeValidator());
    assertSame(polymorphicTypeValidator, serializationConfig.getPolymorphicTypeValidator());
    assertSame(subtypeResolver, deserializationConfig.getSubtypeResolver());
    assertSame(subtypeResolver, serializationConfig.getSubtypeResolver());
    assertSame(defaultNullKeySerializer, serializerProviderInstance.getDefaultNullKeySerializer());
    assertSame(defaultNullValueSerializer, serializerProviderInstance.getDefaultNullValueSerializer());
    assertSame(dateFormat, deserializationConfig.getDateFormat());
    assertSame(dateFormat, serializationConfig.getDateFormat());
  }
}
