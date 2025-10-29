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
package org.activiti.bpmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnAutoLayoutDiffblueTest {
  /**
   * Method under test:
   * {@link BpmnAutoLayout.CustomLayout#CustomLayout(mxGraph, int)}
   */
  @Test
  void testCustomLayoutNewCustomLayout() {
    // Arrange
    mxGraph graph = new mxGraph();

    // Act
    BpmnAutoLayout.CustomLayout actualCustomLayout = new BpmnAutoLayout.CustomLayout(graph, 1);

    // Assert
    assertNull(actualCustomLayout.getModel());
    assertEquals(0, actualCustomLayout.getParentBorder());
    assertEquals(1, actualCustomLayout.getOrientation());
    assertEquals(10.0d, actualCustomLayout.getParallelEdgeSpacing());
    assertEquals(30.0d, actualCustomLayout.getIntraCellSpacing());
    assertEquals(50.0d, actualCustomLayout.getInterRankCellSpacing());
    assertEquals(60.0d, actualCustomLayout.getInterHierarchySpacing());
    assertFalse(actualCustomLayout.isMoveParent());
    assertTrue(actualCustomLayout.isDisableEdgeStyle());
    assertTrue(actualCustomLayout.isFineTuning());
    assertTrue(actualCustomLayout.isResizeParent());
    assertTrue(actualCustomLayout.isUseBoundingBox());
    assertSame(graph, actualCustomLayout.getGraph());
  }

  /**
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  void testExecute() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    // Act
    bpmnAutoLayout.execute();

    // Assert
    BpmnModel bpmnModel = bpmnAutoLayout.bpmnModel;
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertNull(bpmnAutoLayout.getGraph());
    assertNull(bpmnAutoLayout.cellParent);
    assertNull(bpmnAutoLayout.boundaryEvents);
    assertNull(bpmnAutoLayout.generatedAssociationEdges);
    assertNull(bpmnAutoLayout.generatedSequenceFlowEdges);
    assertNull(bpmnAutoLayout.generatedVertices);
    assertNull(bpmnAutoLayout.handledArtifacts);
    assertNull(bpmnAutoLayout.associations);
    assertNull(bpmnAutoLayout.handledFlowElements);
    assertNull(bpmnAutoLayout.sequenceFlows);
    assertNull(bpmnAutoLayout.textAnnotations);
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getGlobalArtifacts().isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getDataStores().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
  }

  /**
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  void testExecute2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BoundaryEvent());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> (new BpmnAutoLayout(bpmnModel)).execute());
  }

  /**
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  void testLayout() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BoundaryEvent());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.layout(flowElementsContainer));
  }

  /**
   * Method under test: {@link BpmnAutoLayout#handleEvent(FlowElement)}
   */
  @Test
  void testHandleEvent() {
    // Arrange
    mxGraph graph = mock(mxGraph.class);
    when(graph.insertVertex(Mockito.<Object>any(), Mockito.<String>any(), Mockito.<Object>any(), anyDouble(),
        anyDouble(), anyDouble(), anyDouble(), Mockito.<String>any())).thenThrow(new RuntimeException("styleEvent"));
    when(graph.getStylesheet()).thenReturn(new mxStylesheet());

    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    bpmnAutoLayout.setGraph(graph);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.handleEvent(new AdhocSubProcess()));
    verify(graph, atLeast(1)).getStylesheet();
    verify(graph).insertVertex(isNull(), isNull(), isA(Object.class), eq(0.0d), eq(0.0d), eq(30.0d), eq(30.0d),
        eq("styleEvent"));
  }

  /**
   * Method under test: {@link BpmnAutoLayout#createEventVertex(FlowElement)}
   */
  @Test
  void testCreateEventVertex() {
    // Arrange
    mxGraph graph = mock(mxGraph.class);
    when(graph.insertVertex(Mockito.<Object>any(), Mockito.<String>any(), Mockito.<Object>any(), anyDouble(),
        anyDouble(), anyDouble(), anyDouble(), Mockito.<String>any())).thenThrow(new RuntimeException("styleEvent"));
    when(graph.getStylesheet()).thenReturn(new mxStylesheet());

    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    bpmnAutoLayout.setGraph(graph);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.createEventVertex(new AdhocSubProcess()));
    verify(graph, atLeast(1)).getStylesheet();
    verify(graph).insertVertex(isNull(), isNull(), isA(Object.class), eq(0.0d), eq(0.0d), eq(30.0d), eq(30.0d),
        eq("styleEvent"));
  }

  /**
   * Method under test: {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}
   */
  @Test
  void testCreateGatewayVertex() {
    // Arrange
    mxGraph graph = mock(mxGraph.class);
    when(graph.insertVertex(Mockito.<Object>any(), Mockito.<String>any(), Mockito.<Object>any(), anyDouble(),
        anyDouble(), anyDouble(), anyDouble(), Mockito.<String>any())).thenThrow(new RuntimeException("styleGateway"));
    when(graph.getStylesheet()).thenReturn(new mxStylesheet());

    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    bpmnAutoLayout.setGraph(graph);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.createGatewayVertex(new AdhocSubProcess()));
    verify(graph).getStylesheet();
    verify(graph).insertVertex(isNull(), isNull(), isA(Object.class), eq(0.0d), eq(0.0d), eq(40.0d), eq(40.0d),
        eq("styleGateway"));
  }

  /**
   * Method under test: {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}
   */
  @Test
  void testCreateGatewayVertex2() {
    // Arrange
    HashMap<String, Map<String, Object>> stringMapMap = new HashMap<>();
    stringMapMap.put("styleGateway", new HashMap<>());
    mxStylesheet mxStylesheet = mock(mxStylesheet.class);
    doThrow(new RuntimeException("styleGateway")).when(mxStylesheet)
        .putCellStyle(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    when(mxStylesheet.getStyles()).thenReturn(stringMapMap);
    mxGraph graph = mock(mxGraph.class);
    when(graph.getStylesheet()).thenReturn(mxStylesheet);

    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    bpmnAutoLayout.setGraph(graph);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.createGatewayVertex(new AdhocSubProcess()));
    verify(graph, atLeast(1)).getStylesheet();
    verify(mxStylesheet).getStyles();
    verify(mxStylesheet).putCellStyle(eq("styleGateway"), isA(Map.class));
  }

  /**
   * Method under test: {@link BpmnAutoLayout#euclidianDistance(mxPoint, mxPoint)}
   */
  @Test
  void testEuclidianDistance() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    mxPoint point1 = new mxPoint();

    // Act and Assert
    assertEquals(0.0d, bpmnAutoLayout.euclidianDistance(point1, new mxPoint()));
  }

  /**
   * Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  void testOptimizeEdgePoints() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    // Act and Assert
    assertTrue(bpmnAutoLayout.optimizeEdgePoints(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  void testOptimizeEdgePoints2() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    mxPoint mxPoint = new mxPoint();
    unoptimizedPointsList.add(mxPoint);
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    mxPoint mxPoint2 = new mxPoint();
    unoptimizedPointsList.add(mxPoint2);

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult = bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(2, actualOptimizeEdgePointsResult.size());
    assertSame(mxPoint, actualOptimizeEdgePointsResult.get(0));
    assertSame(mxPoint2, actualOptimizeEdgePointsResult.get(1));
  }

  /**
   * Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  void testOptimizeEdgePoints3() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    unoptimizedPointsList.add(new mxPoint());

    // Act and Assert
    assertEquals(unoptimizedPointsList, bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList));
  }

  /**
   * Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  void testOptimizeEdgePoints4() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());

    // Act and Assert
    assertEquals(unoptimizedPointsList, bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList));
  }

  /**
   * Method under test:
   * {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)}
   */
  @Test
  void testCreateDiagramInterchangeInformation() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    ActivitiListener element = new ActivitiListener();

    // Act
    bpmnAutoLayout.createDiagramInterchangeInformation(element, new ArrayList<>());

    // Assert
    Map<String, List<GraphicInfo>> flowLocationMap = bpmnAutoLayout.bpmnModel.getFlowLocationMap();
    assertEquals(1, flowLocationMap.size());
    assertTrue(flowLocationMap.get(null).isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)}
   */
  @Test
  void testCreateDiagramInterchangeInformation2() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    ActivitiListener element = new ActivitiListener();

    ArrayList<mxPoint> waypoints = new ArrayList<>();
    waypoints.add(new mxPoint());

    // Act
    bpmnAutoLayout.createDiagramInterchangeInformation(element, waypoints);

    // Assert
    Map<String, List<GraphicInfo>> flowLocationMap = bpmnAutoLayout.bpmnModel.getFlowLocationMap();
    assertEquals(1, flowLocationMap.size());
    List<GraphicInfo> getResult = flowLocationMap.get(null);
    assertEquals(1, getResult.size());
    GraphicInfo getResult2 = getResult.get(0);
    assertNull(getResult2.getExpanded());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0.0d, getResult2.getHeight());
    assertEquals(0.0d, getResult2.getWidth());
    assertEquals(0.0d, getResult2.getX());
    assertEquals(0.0d, getResult2.getY());
    assertSame(element, getResult2.getElement());
  }

  /**
   * Method under test:
   * {@link BpmnAutoLayout#createDiagramInterchangeInformation(FlowElement, int, int, int, int)}
   */
  @Test
  void testCreateDiagramInterchangeInformation3() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    AdhocSubProcess flowElement = new AdhocSubProcess();

    // Act
    GraphicInfo actualCreateDiagramInterchangeInformationResult = bpmnAutoLayout
        .createDiagramInterchangeInformation(flowElement, 2, 3, 1, 1);

    // Assert
    assertNull(actualCreateDiagramInterchangeInformationResult.getExpanded());
    assertEquals(0, actualCreateDiagramInterchangeInformationResult.getXmlColumnNumber());
    assertEquals(0, actualCreateDiagramInterchangeInformationResult.getXmlRowNumber());
    BpmnModel bpmnModel = bpmnAutoLayout.bpmnModel;
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertEquals(1.0d, actualCreateDiagramInterchangeInformationResult.getHeight());
    assertEquals(1.0d, actualCreateDiagramInterchangeInformationResult.getWidth());
    assertEquals(2.0d, actualCreateDiagramInterchangeInformationResult.getX());
    assertEquals(3.0d, actualCreateDiagramInterchangeInformationResult.getY());
    assertTrue(locationMap.containsKey(null));
    assertTrue(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(flowElement, actualCreateDiagramInterchangeInformationResult.getElement());
  }

  /**
   * Method under test:
   * {@link BpmnAutoLayout#translateNestedSubprocesses(Process)}
   */
  @Test
  void testTranslateNestedSubprocesses() {
    // Arrange
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLocationMap()).thenThrow(new RuntimeException("foo"));
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.translateNestedSubprocesses(process));
    verify(bpmnModel).getLocationMap();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)}
   *   <li>{@link BpmnAutoLayout#setEventSize(int)}
   *   <li>{@link BpmnAutoLayout#setGatewaySize(int)}
   *   <li>{@link BpmnAutoLayout#setGraph(mxGraph)}
   *   <li>{@link BpmnAutoLayout#setSubProcessMargin(int)}
   *   <li>{@link BpmnAutoLayout#setTaskHeight(int)}
   *   <li>{@link BpmnAutoLayout#setTaskWidth(int)}
   *   <li>{@link BpmnAutoLayout#getEventSize()}
   *   <li>{@link BpmnAutoLayout#getGatewaySize()}
   *   <li>{@link BpmnAutoLayout#getGraph()}
   *   <li>{@link BpmnAutoLayout#getSubProcessMargin()}
   *   <li>{@link BpmnAutoLayout#getTaskHeight()}
   *   <li>{@link BpmnAutoLayout#getTaskWidth()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    BpmnAutoLayout actualBpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    actualBpmnAutoLayout.setEventSize(3);
    actualBpmnAutoLayout.setGatewaySize(3);
    mxGraph graph = new mxGraph();
    actualBpmnAutoLayout.setGraph(graph);
    actualBpmnAutoLayout.setSubProcessMargin(1);
    actualBpmnAutoLayout.setTaskHeight(1);
    actualBpmnAutoLayout.setTaskWidth(1);
    int actualEventSize = actualBpmnAutoLayout.getEventSize();
    int actualGatewaySize = actualBpmnAutoLayout.getGatewaySize();
    mxGraph actualGraph = actualBpmnAutoLayout.getGraph();
    int actualSubProcessMargin = actualBpmnAutoLayout.getSubProcessMargin();
    int actualTaskHeight = actualBpmnAutoLayout.getTaskHeight();
    int actualTaskWidth = actualBpmnAutoLayout.getTaskWidth();

    // Assert that nothing has changed
    BpmnModel bpmnModel = actualBpmnAutoLayout.bpmnModel;
    assertTrue(bpmnModel.getResources() instanceof List);
    assertTrue(bpmnModel.getSignals() instanceof List);
    assertEquals(1, actualSubProcessMargin);
    assertEquals(1, actualTaskHeight);
    assertEquals(1, actualTaskWidth);
    assertEquals(3, actualEventSize);
    assertEquals(3, actualGatewaySize);
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.getGlobalArtifacts().isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(bpmnModel.getDataStores().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertSame(graph, actualGraph);
  }
}
