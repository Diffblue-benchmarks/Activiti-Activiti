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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxLine;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.BpmnAutoLayout.CustomLayout;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.ComplexGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.TextAnnotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnAutoLayoutDiffblueTest {
  /**
   * Test CustomLayout {@link CustomLayout#CustomLayout(mxGraph, int)}.
   *
   * <p>Method under test: {@link CustomLayout#CustomLayout(mxGraph, int)}
   */
  @Test
  @DisplayName("Test CustomLayout new CustomLayout(mxGraph, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CustomLayout.<init>(mxGraph, int)"})
  void testCustomLayoutNewCustomLayout() {
    // Arrange
    mxGraph graph = new mxGraph();

    // Act
    CustomLayout actualCustomLayout = new CustomLayout(graph, 1);

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
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnAutoLayout.<init>(BpmnModel)",
    "int BpmnAutoLayout.getEventSize()",
    "int BpmnAutoLayout.getGatewaySize()",
    "mxGraph BpmnAutoLayout.getGraph()",
    "int BpmnAutoLayout.getSubProcessMargin()",
    "int BpmnAutoLayout.getTaskHeight()",
    "int BpmnAutoLayout.getTaskWidth()",
    "void BpmnAutoLayout.setEventSize(int)",
    "void BpmnAutoLayout.setGatewaySize(int)",
    "void BpmnAutoLayout.setGraph(mxGraph)",
    "void BpmnAutoLayout.setSubProcessMargin(int)",
    "void BpmnAutoLayout.setTaskHeight(int)",
    "void BpmnAutoLayout.setTaskWidth(int)"
  })
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

    // Assert
    BpmnModel bpmnModel = actualBpmnAutoLayout.bpmnModel;
    assertTrue(bpmnModel.getResources() instanceof List);
    assertTrue(bpmnModel.getSignals() instanceof List);
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
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

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    mxGraph graph = bpmnAutoLayout.getGraph();
    Object defaultParent = graph.getDefaultParent();
    assertTrue(defaultParent instanceof mxCell);
    Object object = bpmnAutoLayout.cellParent;
    assertTrue(object instanceof mxCell);
    mxIGraphModel model = graph.getModel();
    assertTrue(model instanceof mxGraphModel);
    Collection<FlowElement> flowElements =
        bpmnAutoLayout.bpmnModel.getMainProcess().getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(0, ((mxCell) defaultParent).getChildCount());
    assertEquals(0, ((mxCell) object).getChildCount());
    mxRectangle graphBounds = graph.getGraphBounds();
    assertEquals(0.0d, graphBounds.getCenterX());
    assertEquals(0.0d, graphBounds.getCenterY());
    assertEquals(0.0d, graphBounds.getHeight());
    assertEquals(0.0d, graphBounds.getWidth());
    assertEquals(2, graph.getView().getStates().size());
    Map<String, Object> cells = ((mxGraphModel) model).getCells();
    assertEquals(2, cells.size());
    assertTrue(flowElements.isEmpty());
    assertTrue(cells.containsKey("0"));
    assertTrue(cells.containsKey("1"));
    assertTrue(bpmnAutoLayout.generatedVertices.isEmpty());
    assertTrue(bpmnAutoLayout.handledFlowElements.isEmpty());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    mxGraph graph = bpmnAutoLayout.getGraph();
    Object defaultParent = graph.getDefaultParent();
    assertTrue(defaultParent instanceof mxCell);
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    assertTrue(stringObjectMap.get(null) instanceof mxCell);
    Object object = bpmnAutoLayout.cellParent;
    assertTrue(object instanceof mxCell);
    mxIGraphModel model = graph.getModel();
    assertTrue(model instanceof mxGraphModel);
    assertEquals(1, ((mxCell) defaultParent).getChildCount());
    assertEquals(1, ((mxCell) object).getChildCount());
    assertEquals(3, graph.getView().getStates().size());
    Map<String, Object> cells = ((mxGraphModel) model).getCells();
    assertEquals(3, cells.size());
    assertTrue(cells.containsKey("0"));
    assertTrue(cells.containsKey("1"));
    assertTrue(cells.containsKey("2"));
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute3() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    assertEquals(2, bpmnAutoLayout.bpmnModel.getFlowLocationMap().size());
    assertEquals(2, bpmnAutoLayout.associations.size());
    assertEquals(2, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(2, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   *
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel}
   *       (default constructor) {@link BpmnAutoLayout#associations} size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName(
      "Test execute(); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) associations size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelAssociationsSizeIsOne() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   *
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel}
   *       (default constructor) {@link BpmnAutoLayout#associations} size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName(
      "Test execute(); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) associations size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelAssociationsSizeIsOne2() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   *
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel}
   *       (default constructor) {@link BpmnAutoLayout#cellParent} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName(
      "Test execute(); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) cellParent is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelCellParentIsNull() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    // Act
    bpmnAutoLayout.execute();

    // Assert that nothing has changed
    assertNull(bpmnAutoLayout.cellParent);
    assertNull(bpmnAutoLayout.generatedAssociationEdges);
    assertNull(bpmnAutoLayout.generatedVertices);
    assertNull(bpmnAutoLayout.handledArtifacts);
    assertNull(bpmnAutoLayout.associations);
    assertNull(bpmnAutoLayout.handledFlowElements);
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   *
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel}
   *       (default constructor) Graph Model Cells size is four.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName(
      "Test execute(); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) Graph Model Cells size is four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelGraphModelCellsSizeIsFour() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    mxIGraphModel model = bpmnAutoLayout.getGraph().getModel();
    Map<String, Object> cells = ((mxGraphModel) model).getCells();
    assertEquals(4, cells.size());
    assertTrue(cells.get("2") instanceof mxCell);
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult = stringObjectMap.get(null);
    assertTrue(getResult instanceof mxCell);
    assertTrue(model instanceof mxGraphModel);
    Rectangle rectangle = ((mxCell) getResult).getGeometry().getRectangle();
    assertTrue(rectangle.getBounds2D() instanceof Rectangle);
    assertTrue(rectangle.getFrame() instanceof Double);
    assertTrue(cells.containsKey("1"));
    assertTrue(cells.containsKey("3"));
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Rectangle2D bounds2D = bpmnAutoLayout.getGraph().getGraphBounds().getRectangle().getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    Rectangle bounds = bounds2D.getBounds();
    assertTrue(bounds.getBounds2D() instanceof Rectangle);
    assertTrue(bounds2D.getBounds2D() instanceof Rectangle);
    assertTrue(bounds.getFrame() instanceof Double);
    Collection<Artifact> artifacts = flowElementsContainer.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = flowElementsContainer.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(bpmnAutoLayout.handledArtifacts.isEmpty());
    assertTrue(bpmnAutoLayout.handledFlowElements.isEmpty());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout2() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());
    element.addArtifact(new Association());

    Association artifact = new Association();
    artifact.setId("Flow Elements Container");

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedAssociationEdges;
    assertEquals(1, stringObjectMap.size());
    assertTrue(stringObjectMap.get("Flow Elements Container") instanceof mxCell);
    Map<String, Association> stringAssociationMap = bpmnAutoLayout.associations;
    assertEquals(1, stringAssociationMap.size());
    Map<String, Artifact> stringArtifactMap = bpmnAutoLayout.handledArtifacts;
    assertEquals(1, stringArtifactMap.size());
    assertSame(artifact, stringArtifactMap.get("Flow Elements Container"));
    assertSame(artifact, stringAssociationMap.get("Flow Elements Container"));
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout3() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    BooleanDataObject element = new BooleanDataObject();
    element.setId(null);

    TextAnnotation artifact = new TextAnnotation();
    artifact.setId(null);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    Map<String, Artifact> expectedStringTextAnnotationMap = bpmnAutoLayout.handledArtifacts;
    assertEquals(expectedStringTextAnnotationMap, bpmnAutoLayout.textAnnotations);
    assertSame(element, stringFlowElementMap.get(null));
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout4() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(new Association());
    element.addFlowElement(new AdhocSubProcess());
    element.addFlowElement(new AdhocSubProcess());
    element.addArtifact(new Association());

    Association artifact = new Association();
    artifact.setId(null);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
    assertEquals(3, bpmnAutoLayout.bpmnModel.getFlowLocationMap().size());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement {@link
   *       AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName(
      "Test layout(FlowElementsContainer); given AdhocSubProcess (default constructor) addFlowElement AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_givenAdhocSubProcessAddFlowElementAdhocSubProcess() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());
    element.addArtifact(new Association());

    Association artifact = new Association();
    artifact.setId(null);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement {@link
   *       AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName(
      "Test layout(FlowElementsContainer); given AdhocSubProcess (default constructor) addFlowElement AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_givenAdhocSubProcessAddFlowElementAdhocSubProcess2() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());
    element.addFlowElement(new AdhocSubProcess());
    element.addArtifact(new Association());

    Association artifact = new Association();
    artifact.setId(null);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement {@link CallActivity}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName(
      "Test layout(FlowElementsContainer); given AdhocSubProcess (default constructor) addFlowElement CallActivity (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_givenAdhocSubProcessAddFlowElementCallActivity() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new CallActivity());
    element.addArtifact(new Association());

    Association artifact = new Association();
    artifact.setId(null);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement {@link ComplexGateway}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName(
      "Test layout(FlowElementsContainer); given AdhocSubProcess (default constructor) addFlowElement ComplexGateway (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_givenAdhocSubProcessAddFlowElementComplexGateway() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new ComplexGateway());
    element.addArtifact(new Association());

    Association artifact = new Association();
    artifact.setId(null);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement {@link Task} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName(
      "Test layout(FlowElementsContainer); given AdhocSubProcess (default constructor) addFlowElement Task (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_givenAdhocSubProcessAddFlowElementTask() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new Task());
    element.addArtifact(new Association());

    Association artifact = new Association();
    artifact.setId(null);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel}
   *       (default constructor) {@link BpmnAutoLayout#generatedVertices} size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName(
      "Test layout(FlowElementsContainer); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) generatedVertices size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelGeneratedVerticesSizeIsOne() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());
    element.addArtifact(new TextAnnotation());

    Association artifact = new Association();
    artifact.setId(null);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    assertTrue(stringObjectMap.get(null) instanceof mxCell);
    BpmnModel bpmnModel = bpmnAutoLayout.bpmnModel;
    assertEquals(1, bpmnModel.getFlowLocationMap().size());
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
    assertTrue(locationMap.containsKey(null));
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_thenThrowRuntimeException() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new BoundaryEvent());
    element.addArtifact(new Association());

    Association artifact = new Association();
    artifact.setId(null);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);
    flowElementsContainer.addArtifact(artifact);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.layout(flowElementsContainer));
  }

  /**
   * Test {@link BpmnAutoLayout#handleEvent(FlowElement)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#handleEvent(FlowElement)}
   */
  @Test
  @DisplayName("Test handleEvent(FlowElement); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.handleEvent(FlowElement)"})
  void testHandleEvent_thenThrowRuntimeException() {
    // Arrange
    mxGraph graph = mock(mxGraph.class);
    when(graph.insertVertex(
            Mockito.<Object>any(),
            Mockito.<String>any(),
            Mockito.<Object>any(),
            anyDouble(),
            anyDouble(),
            anyDouble(),
            anyDouble(),
            Mockito.<String>any()))
        .thenThrow(new RuntimeException());
    when(graph.getStylesheet()).thenReturn(new mxStylesheet());
    doNothing().when(graph).addSelectionCell(Mockito.<Object>any());
    doNothing().when(graph).enterGroup(Mockito.<Object>any());
    graph.enterGroup("Cell");
    graph.addSelectionCell("Cell");

    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    bpmnAutoLayout.setGraph(graph);

    SubProcess flowElement = mock(SubProcess.class);
    when(flowElement.getId()).thenReturn("42");
    doNothing().when(flowElement).addFlowElement(Mockito.<FlowElement>any());
    flowElement.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.handleEvent(flowElement));
    verify(graph).addSelectionCell(isA(Object.class));
    verify(graph).enterGroup(isA(Object.class));
    verify(graph, atLeast(1)).getStylesheet();
    verify(graph)
        .insertVertex(
            isNull(),
            eq("42"),
            isA(Object.class),
            eq(0.0d),
            eq(0.0d),
            eq(30.0d),
            eq(30.0d),
            eq("styleEvent"));
    verify(flowElement).getId();
    verify(flowElement).addFlowElement(isA(FlowElement.class));
  }

  /**
   * Test {@link BpmnAutoLayout#handleSubProcess(FlowElement)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#handleSubProcess(FlowElement)}
   */
  @Test
  @DisplayName("Test handleSubProcess(FlowElement); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.handleSubProcess(FlowElement)"})
  void testHandleSubProcess_thenThrowRuntimeException() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new BoundaryEvent());
    element.addArtifact(new Association());

    Association artifact = new Association();
    artifact.setId(null);

    AdhocSubProcess flowElement = new AdhocSubProcess();
    flowElement.addFlowElement(element);
    flowElement.addArtifact(artifact);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.handleSubProcess(flowElement));
  }

  /**
   * Test {@link BpmnAutoLayout#createEventVertex(FlowElement)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#createEventVertex(FlowElement)}
   */
  @Test
  @DisplayName("Test createEventVertex(FlowElement); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.createEventVertex(FlowElement)"})
  void testCreateEventVertex_thenThrowRuntimeException() {
    // Arrange
    mxGraph graph = mock(mxGraph.class);
    when(graph.insertVertex(
            Mockito.<Object>any(),
            Mockito.<String>any(),
            Mockito.<Object>any(),
            anyDouble(),
            anyDouble(),
            anyDouble(),
            anyDouble(),
            Mockito.<String>any()))
        .thenThrow(new RuntimeException());
    when(graph.getStylesheet()).thenReturn(new mxStylesheet());

    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    bpmnAutoLayout.setGraph(graph);

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> bpmnAutoLayout.createEventVertex(new AdhocSubProcess()));
    verify(graph, atLeast(1)).getStylesheet();
    verify(graph)
        .insertVertex(
            isNull(),
            isNull(),
            isA(Object.class),
            eq(0.0d),
            eq(0.0d),
            eq(30.0d),
            eq(30.0d),
            eq("styleEvent"));
  }

  /**
   * Test {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}.
   *
   * <ul>
   *   <li>Then calls {@link mxStylesheet#getStyles()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}
   */
  @Test
  @DisplayName("Test createGatewayVertex(FlowElement); then calls getStyles()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.createGatewayVertex(FlowElement)"})
  void testCreateGatewayVertex_thenCallsGetStyles() {
    // Arrange
    HashMap<String, Map<String, Object>> stringMapMap = new HashMap<>();
    stringMapMap.put("styleGateway", new HashMap<>());

    mxStylesheet mxStylesheet = mock(mxStylesheet.class);
    doThrow(new RuntimeException())
        .when(mxStylesheet)
        .putCellStyle(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    when(mxStylesheet.getStyles()).thenReturn(stringMapMap);

    mxGraph graph = mock(mxGraph.class);
    when(graph.getStylesheet()).thenReturn(mxStylesheet);

    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    bpmnAutoLayout.setGraph(graph);

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> bpmnAutoLayout.createGatewayVertex(new AdhocSubProcess()));
    verify(graph, atLeast(1)).getStylesheet();
    verify(mxStylesheet).getStyles();
    verify(mxStylesheet).putCellStyle(eq("styleGateway"), isA(Map.class));
  }

  /**
   * Test {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}.
   *
   * <ul>
   *   <li>Then calls {@link mxGraph#insertVertex(Object, String, Object, double, double, double,
   *       double, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}
   */
  @Test
  @DisplayName(
      "Test createGatewayVertex(FlowElement); then calls insertVertex(Object, String, Object, double, double, double, double, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.createGatewayVertex(FlowElement)"})
  void testCreateGatewayVertex_thenCallsInsertVertex() {
    // Arrange
    mxGraph graph = mock(mxGraph.class);
    when(graph.insertVertex(
            Mockito.<Object>any(),
            Mockito.<String>any(),
            Mockito.<Object>any(),
            anyDouble(),
            anyDouble(),
            anyDouble(),
            anyDouble(),
            Mockito.<String>any()))
        .thenThrow(new RuntimeException());
    when(graph.getStylesheet()).thenReturn(new mxStylesheet());

    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    bpmnAutoLayout.setGraph(graph);

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> bpmnAutoLayout.createGatewayVertex(new AdhocSubProcess()));
    verify(graph).getStylesheet();
    verify(graph)
        .insertVertex(
            isNull(),
            isNull(),
            isA(Object.class),
            eq(0.0d),
            eq(0.0d),
            eq(40.0d),
            eq(40.0d),
            eq("styleGateway"));
  }

  /**
   * Test {@link BpmnAutoLayout#euclidianDistance(mxPoint, mxPoint)}.
   *
   * <ul>
   *   <li>When {@link mxPoint#mxPoint()}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#euclidianDistance(mxPoint, mxPoint)}
   */
  @Test
  @DisplayName("Test euclidianDistance(mxPoint, mxPoint); when mxPoint(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"double BpmnAutoLayout.euclidianDistance(mxPoint, mxPoint)"})
  void testEuclidianDistance_whenMxPoint_thenReturnZero() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    mxPoint point1 = new mxPoint();

    // Act and Assert
    assertEquals(0.0d, bpmnAutoLayout.euclidianDistance(point1, new mxPoint()));
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName("Test optimizeEdgePoints(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    mxLine mxLine = new mxLine(10.0d, 10.0d, new mxPoint());
    unoptimizedPointsList.add(mxLine);
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
    unoptimizedPointsList.add(new mxPoint());

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult =
        bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(3, actualOptimizeEdgePointsResult.size());
    assertSame(mxLine, actualOptimizeEdgePointsResult.get(0));
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   *
   * <ul>
   *   <li>Given {@link mxPoint#mxPoint()} X is ten.
   *   <li>Then return first Point {@link Point#x} is ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName(
      "Test optimizeEdgePoints(List); given mxPoint() X is ten; then return first Point x is ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_givenMxPointXIsTen_thenReturnFirstPointXIsTen() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    mxPoint mxPoint = new mxPoint();
    mxPoint.setX(10.0d);

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
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
    unoptimizedPointsList.add(new mxPoint());

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult =
        bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(3, actualOptimizeEdgePointsResult.size());
    mxPoint getResult = actualOptimizeEdgePointsResult.get(0);
    Point point = getResult.getPoint();
    assertEquals(10, point.x);
    Point location = point.getLocation();
    assertEquals(10, location.x);
    Point location2 = location.getLocation();
    assertEquals(10, location2.x);
    Point location3 = location2.getLocation();
    assertEquals(10, location3.x);
    Point location4 = location3.getLocation();
    assertEquals(10, location4.x);
    assertEquals(10.0d, getResult.getX());
    assertEquals(10.0d, point.getX());
    assertEquals(10.0d, location.getX());
    assertEquals(10.0d, location2.getX());
    assertEquals(10.0d, location3.getX());
    assertEquals(10.0d, location4.getX());
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   *
   * <ul>
   *   <li>Given {@link mxPoint#mxPoint()} Y is ten.
   *   <li>Then return first Point {@link Point#y} is ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName(
      "Test optimizeEdgePoints(List); given mxPoint() Y is ten; then return first Point y is ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_givenMxPointYIsTen_thenReturnFirstPointYIsTen() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    mxPoint mxPoint = new mxPoint();
    mxPoint.setY(10.0d);

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
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
    unoptimizedPointsList.add(new mxPoint());

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult =
        bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(3, actualOptimizeEdgePointsResult.size());
    mxPoint getResult = actualOptimizeEdgePointsResult.get(0);
    Point point = getResult.getPoint();
    assertEquals(10, point.y);
    Point location = point.getLocation();
    assertEquals(10, location.y);
    Point location2 = location.getLocation();
    assertEquals(10, location2.y);
    Point location3 = location2.getLocation();
    assertEquals(10, location3.y);
    Point location4 = location3.getLocation();
    assertEquals(10, location4.y);
    assertEquals(10.0d, getResult.getY());
    assertEquals(10.0d, point.getY());
    assertEquals(10.0d, location.getY());
    assertEquals(10.0d, location2.getY());
    assertEquals(10.0d, location3.getY());
    assertEquals(10.0d, location4.getY());
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   *
   * <ul>
   *   <li>Given {@link mxPoint#mxPoint()}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link mxPoint#mxPoint()}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName(
      "Test optimizeEdgePoints(List); given mxPoint(); when ArrayList() add mxPoint(); then return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_givenMxPoint_whenArrayListAddMxPoint_thenReturnArrayList() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult =
        bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(unoptimizedPointsList, actualOptimizeEdgePointsResult);
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   *
   * <ul>
   *   <li>Given {@link mxPoint#mxPoint()}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link mxPoint#mxPoint()}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName(
      "Test optimizeEdgePoints(List); given mxPoint(); when ArrayList() add mxPoint(); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_givenMxPoint_whenArrayListAddMxPoint_thenReturnSizeIsOne() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    unoptimizedPointsList.add(new mxPoint());

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult =
        bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(1, actualOptimizeEdgePointsResult.size());
    Point location = actualOptimizeEdgePointsResult.get(0).getPoint().getLocation();
    assertEquals(0, location.x);
    assertEquals(0, location.y);
    assertEquals(0.0d, location.getX());
    assertEquals(0.0d, location.getY());
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   *
   * <ul>
   *   <li>Given {@link mxPoint#mxPoint()}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link mxPoint#mxPoint()}.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName(
      "Test optimizeEdgePoints(List); given mxPoint(); when ArrayList() add mxPoint(); then return size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_givenMxPoint_whenArrayListAddMxPoint_thenReturnSizeIsTwo() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
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
    unoptimizedPointsList.add(new mxPoint());
    mxPoint mxPoint = new mxPoint();
    unoptimizedPointsList.add(mxPoint);

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult =
        bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(2, actualOptimizeEdgePointsResult.size());
    assertSame(mxPoint, actualOptimizeEdgePointsResult.get(1));
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   *
   * <ul>
   *   <li>Then return size is five.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName("Test optimizeEdgePoints(List); then return size is five")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_thenReturnSizeIsFive() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());
    mxLine mxLine = new mxLine(10.0d, 10.0d, new mxPoint());
    unoptimizedPointsList.add(mxLine);
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
    mxPoint mxPoint = new mxPoint();
    unoptimizedPointsList.add(mxPoint);

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult =
        bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(5, actualOptimizeEdgePointsResult.size());
    assertSame(mxLine, actualOptimizeEdgePointsResult.get(2));
    assertSame(mxPoint, actualOptimizeEdgePointsResult.get(4));
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   *
   * <ul>
   *   <li>Then return size is four.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName("Test optimizeEdgePoints(List); then return size is four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_thenReturnSizeIsFour() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    unoptimizedPointsList.add(new mxPoint());
    mxLine mxLine = new mxLine(10.0d, 10.0d, new mxPoint());
    unoptimizedPointsList.add(mxLine);
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

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult =
        bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(4, actualOptimizeEdgePointsResult.size());
    assertSame(mxLine, actualOptimizeEdgePointsResult.get(1));
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName("Test optimizeEdgePoints(List); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_whenArrayList_thenReturnEmpty() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    // Act and Assert
    assertTrue(bpmnAutoLayout.optimizeEdgePoints(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)} with {@code
   * element}, {@code waypoints}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement,
   * List)}
   */
  @Test
  @DisplayName(
      "Test createDiagramInterchangeInformation(BaseElement, List) with 'element', 'waypoints'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.createDiagramInterchangeInformation(BaseElement, List)"})
  void testCreateDiagramInterchangeInformationWithElementWaypoints() {
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
   * Test {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)} with {@code
   * element}, {@code waypoints}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement,
   * List)}
   */
  @Test
  @DisplayName(
      "Test createDiagramInterchangeInformation(BaseElement, List) with 'element', 'waypoints'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnAutoLayout.createDiagramInterchangeInformation(BaseElement, List)"})
  void testCreateDiagramInterchangeInformationWithElementWaypoints2() {
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
   * Test {@link BpmnAutoLayout#createDiagramInterchangeInformation(FlowElement, int, int, int,
   * int)} with {@code flowElement}, {@code x}, {@code y}, {@code width}, {@code height}.
   *
   * <p>Method under test: {@link BpmnAutoLayout#createDiagramInterchangeInformation(FlowElement,
   * int, int, int, int)}
   */
  @Test
  @DisplayName(
      "Test createDiagramInterchangeInformation(FlowElement, int, int, int, int) with 'flowElement', 'x', 'y', 'width', 'height'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "GraphicInfo BpmnAutoLayout.createDiagramInterchangeInformation(FlowElement, int, int, int, int)"
  })
  void testCreateDiagramInterchangeInformationWithFlowElementXYWidthHeight() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    AdhocSubProcess flowElement = new AdhocSubProcess();

    // Act
    GraphicInfo actualCreateDiagramInterchangeInformationResult =
        bpmnAutoLayout.createDiagramInterchangeInformation(flowElement, 2, 3, 1, 1);

    // Assert
    BaseElement element = actualCreateDiagramInterchangeInformationResult.getElement();
    assertTrue(element instanceof AdhocSubProcess);
    assertNull(actualCreateDiagramInterchangeInformationResult.getExpanded());
    assertEquals(0, actualCreateDiagramInterchangeInformationResult.getXmlColumnNumber());
    assertEquals(0, actualCreateDiagramInterchangeInformationResult.getXmlRowNumber());
    assertEquals(1.0d, actualCreateDiagramInterchangeInformationResult.getHeight());
    assertEquals(1.0d, actualCreateDiagramInterchangeInformationResult.getWidth());
    assertEquals(2.0d, actualCreateDiagramInterchangeInformationResult.getX());
    assertEquals(3.0d, actualCreateDiagramInterchangeInformationResult.getY());
    assertSame(flowElement, element);
  }
}
