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
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
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
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.ComplexGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Process;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnAutoLayoutDiffblueTest {
  /**
   * Test CustomLayout {@link CustomLayout#CustomLayout(mxGraph, int)}.
   * <p>
   * Method under test: {@link CustomLayout#CustomLayout(mxGraph, int)}
   */
  @Test
  @DisplayName("Test CustomLayout new CustomLayout(mxGraph, int)")
  @Tag("MaintainedByDiffblue")
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
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.<init>(BpmnModel)", "int BpmnAutoLayout.getEventSize()",
      "int BpmnAutoLayout.getGatewaySize()", "mxGraph BpmnAutoLayout.getGraph()",
      "int BpmnAutoLayout.getSubProcessMargin()", "int BpmnAutoLayout.getTaskHeight()",
      "int BpmnAutoLayout.getTaskWidth()", "void BpmnAutoLayout.setEventSize(int)",
      "void BpmnAutoLayout.setGatewaySize(int)", "void BpmnAutoLayout.setGraph(mxGraph)",
      "void BpmnAutoLayout.setSubProcessMargin(int)", "void BpmnAutoLayout.setTaskHeight(int)",
      "void BpmnAutoLayout.setTaskWidth(int)"})
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
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    // Act
    bpmnAutoLayout.execute();

    // Assert that nothing has changed
    assertNull(bpmnAutoLayout.generatedAssociationEdges);
    assertNull(bpmnAutoLayout.generatedVertices);
    assertNull(bpmnAutoLayout.handledArtifacts);
    assertNull(bpmnAutoLayout.associations);
    assertNull(bpmnAutoLayout.handledFlowElements);
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    Rectangle rectangle = bpmnAutoLayout.getGraph().getGraphBounds().getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    Rectangle2D bounds2D2 = bounds2D.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle bounds = bounds2D2.getBounds();
    Rectangle2D frame = bounds.getFrame();
    assertTrue(frame instanceof Double);
    Collection<FlowElement> flowElements = bpmnAutoLayout.bpmnModel.getMainProcess().getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(0.0d, ((Rectangle) bounds2D2).getSize().getHeight());
    Point location = ((Rectangle) bounds2D2).getLocation();
    assertEquals(0.0d, location.getX());
    assertEquals(0.0d, location.getY());
    assertEquals(0.0d, bounds.getHeight());
    assertEquals(0.0d, bounds.getWidth());
    assertEquals(0.0d, bounds.getX());
    assertEquals(0.0d, bounds.getY());
    assertEquals(0.0d, bounds.getMaxX());
    assertEquals(0.0d, bounds.getMaxY());
    assertEquals(0.0d, bounds.getMinX());
    assertEquals(0.0d, bounds.getMinY());
    assertTrue(bounds.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(bpmnAutoLayout.handledFlowElements.isEmpty());
    assertEquals(rectangle, frame);
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult = stringObjectMap.get(null);
    assertTrue(getResult instanceof mxCell);
    Rectangle rectangle = ((mxCell) getResult).getGeometry().getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    Rectangle bounds = bpmnAutoLayout.getGraph().getGraphBounds().getRectangle().getBounds().getBounds();
    assertTrue(bounds.getBounds2D() instanceof Rectangle);
    Rectangle2D frame = rectangle.getFrame();
    assertTrue(frame instanceof Double);
    assertTrue(bounds.getFrame() instanceof Double);
    assertEquals(20.0d, bounds2D.getCenterY());
    assertEquals(20.0d, frame.getCenterY());
    assertEquals(40.0d, bounds2D.getMaxY());
    assertEquals(40.0d, frame.getMaxY());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute4() {
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
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute5() {
    // Arrange
    Process process = new Process();
    ComplexGateway element = new ComplexGateway();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult = stringObjectMap.get(null);
    assertTrue(getResult instanceof mxCell);
    Rectangle2D bounds2D = bpmnAutoLayout.getGraph().getGraphBounds().getRectangle().getBounds().getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    assertTrue(bounds2D.getBounds2D() instanceof Rectangle);
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    FlowElement getResult2 = stringFlowElementMap.get(null);
    assertTrue(getResult2 instanceof ComplexGateway);
    assertEquals("styleGateway", ((mxCell) getResult).getStyle());
    assertNull(((ComplexGateway) getResult2).getDefaultFlow());
    Map<String, GraphicInfo> locationMap = bpmnAutoLayout.bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertSame(element, getResult2);
    assertSame(element, locationMap.get(null).getElement());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute6() {
    // Arrange
    Process process = new Process();
    BusinessRuleTask element = new BusinessRuleTask();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnAutoLayout.bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertSame(element, stringFlowElementMap.get(null));
    assertSame(element, locationMap.get(null).getElement());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute7() {
    // Arrange
    Process process = new Process();
    CallActivity element = new CallActivity();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnAutoLayout.bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertSame(element, stringFlowElementMap.get(null));
    assertSame(element, locationMap.get(null).getElement());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute8() {
    // Arrange
    Process process = new Process();
    BooleanDataObject element = new BooleanDataObject();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertSame(element, stringFlowElementMap.get(null));
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute9() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);

    // Act
    bpmnAutoLayout.execute();

    // Assert
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    FlowElement getResult = stringFlowElementMap.get(null);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    mxRectangle graphBounds = bpmnAutoLayout.getGraph().getGraphBounds();
    assertEquals(40.0d, graphBounds.getCenterX());
    assertEquals(40.0d, graphBounds.getCenterY());
    assertEquals(80.0d, graphBounds.getHeight());
    assertEquals(80.0d, graphBounds.getWidth());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute(); given Process (default constructor) addFlowElement BoundaryEvent (default constructor); then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.execute()"})
  void testExecute_givenProcessAddFlowElementBoundaryEvent_thenThrowRuntimeException() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BoundaryEvent());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> (new BpmnAutoLayout(bpmnModel)).execute());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel} (default constructor) {@link BpmnAutoLayout#associations} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute(); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) associations size is one")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel} (default constructor) {@link BpmnAutoLayout#associations} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute(); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) associations size is one")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel} (default constructor) Graph Model Cells size is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute(); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) Graph Model Cells size is four")
  @Tag("MaintainedByDiffblue")
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
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Rectangle rectangle = bpmnAutoLayout.getGraph().getGraphBounds().getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    Rectangle2D bounds2D2 = bounds2D.getBounds2D();
    Rectangle bounds = bounds2D2.getBounds();
    Rectangle2D bounds2D3 = bounds.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle2D frame = bounds2D.getBounds().getFrame();
    assertTrue(frame instanceof Double);
    Collection<FlowElement> flowElements = flowElementsContainer.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(0.0d, bounds.getCenterX());
    assertEquals(0.0d, bounds.getCenterY());
    assertEquals(0.0d, frame.getMinX());
    assertEquals(0.0d, frame.getMinY());
    assertEquals(0.0d, frame.getWidth());
    assertEquals(0.0d, frame.getX());
    assertEquals(0.0d, frame.getY());
    assertTrue(frame.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(bpmnAutoLayout.handledFlowElements.isEmpty());
    assertEquals(rectangle, bounds.getBounds());
    assertEquals(rectangle, bounds2D3);
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout2() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult = stringObjectMap.get(null);
    assertTrue(getResult instanceof mxCell);
    Rectangle rectangle = ((mxCell) getResult).getGeometry().getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    assertTrue(bpmnAutoLayout.getGraph()
        .getGraphBounds()
        .getRectangle()
        .getBounds()
        .getBounds()
        .getBounds2D() instanceof Rectangle);
    Rectangle2D frame = rectangle.getFrame();
    assertTrue(frame instanceof Double);
    assertEquals(20.0d, bounds2D.getCenterY());
    assertEquals(20.0d, frame.getCenterY());
    assertEquals(40.0d, bounds2D.getMaxY());
    assertEquals(40.0d, frame.getMaxY());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout3() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(new Association());
    flowElementsContainer.addArtifact(new Association());
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(2, bpmnAutoLayout.bpmnModel.getFlowLocationMap().size());
    assertEquals(2, bpmnAutoLayout.associations.size());
    assertEquals(2, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(2, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout4() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    ComplexGateway element = new ComplexGateway();
    flowElementsContainer.addFlowElement(element);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult = stringObjectMap.get(null);
    assertTrue(getResult instanceof mxCell);
    assertTrue(
        bpmnAutoLayout.getGraph().getGraphBounds().getRectangle().getBounds().getBounds2D() instanceof Rectangle);
    assertEquals("styleGateway", ((mxCell) getResult).getStyle());
    Map<String, GraphicInfo> locationMap = bpmnAutoLayout.bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertSame(element, stringFlowElementMap.get(null));
    assertSame(element, locationMap.get(null).getElement());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout5() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    BusinessRuleTask element = new BusinessRuleTask();
    flowElementsContainer.addFlowElement(element);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    mxRectangle graphBounds = bpmnAutoLayout.getGraph().getGraphBounds();
    assertEquals(100.0d, graphBounds.getWidth());
    assertEquals(30.0d, graphBounds.getCenterY());
    assertEquals(50.0d, graphBounds.getCenterX());
    assertEquals(60.0d, graphBounds.getHeight());
    assertSame(element, stringFlowElementMap.get(null));
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout6() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    CallActivity element = new CallActivity();
    flowElementsContainer.addFlowElement(element);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    mxRectangle graphBounds = bpmnAutoLayout.getGraph().getGraphBounds();
    assertEquals(100.0d, graphBounds.getWidth());
    assertEquals(30.0d, graphBounds.getCenterY());
    assertEquals(50.0d, graphBounds.getCenterX());
    assertEquals(60.0d, graphBounds.getHeight());
    assertSame(element, stringFlowElementMap.get(null));
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer); given BoundaryEvent (default constructor); then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_givenBoundaryEvent_thenThrowRuntimeException() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BoundaryEvent());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> bpmnAutoLayout.layout(flowElementsContainer));
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel} (default constructor) {@link BpmnAutoLayout#associations} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) associations size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelAssociationsSizeIsOne() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(new Association());
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel} (default constructor) {@link BpmnAutoLayout#associations} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) associations size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelAssociationsSizeIsOne2() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(new Association());
    flowElementsContainer.addFlowElement(new AdhocSubProcess());
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertEquals(1, bpmnAutoLayout.associations.size());
    assertEquals(1, bpmnAutoLayout.generatedAssociationEdges.size());
    assertEquals(1, bpmnAutoLayout.handledArtifacts.size());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel} (default constructor) Graph DefaultParent {@link mxCell}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) Graph DefaultParent mxCell")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelGraphDefaultParentMxCell() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    BooleanDataObject element = new BooleanDataObject();
    flowElementsContainer.addFlowElement(element);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    assertTrue(bpmnAutoLayout.getGraph().getDefaultParent() instanceof mxCell);
    Object object = bpmnAutoLayout.cellParent;
    assertTrue(object instanceof mxCell);
    assertEquals(0, ((mxCell) object).getChildCount());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertTrue(bpmnAutoLayout.bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnAutoLayout.generatedVertices.isEmpty());
    assertSame(element, stringFlowElementMap.get(null));
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is {@link BpmnModel} (default constructor) Graph Model Cells size is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) Graph Model Cells size is four")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.layout(FlowElementsContainer)"})
  void testLayout_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelGraphModelCellsSizeIsFour() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    mxGraph graph = bpmnAutoLayout.getGraph();
    mxIGraphModel model = graph.getModel();
    Map<String, Object> cells = ((mxGraphModel) model).getCells();
    assertEquals(4, cells.size());
    assertTrue(cells.get("2") instanceof mxCell);
    assertTrue(model instanceof mxGraphModel);
    Rectangle2D frame = graph.getGraphBounds().getRectangle().getFrame();
    assertTrue(frame instanceof Double);
    assertTrue(frame.getFrame() instanceof Double);
    assertTrue(cells.containsKey("1"));
    assertTrue(cells.containsKey("3"));
  }

  /**
   * Test {@link BpmnAutoLayout#handleEvent(FlowElement)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#handleEvent(FlowElement)}
   */
  @Test
  @DisplayName("Test handleEvent(FlowElement); then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.handleEvent(FlowElement)"})
  void testHandleEvent_thenThrowRuntimeException() {
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
   * Test {@link BpmnAutoLayout#createEventVertex(FlowElement)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#createEventVertex(FlowElement)}
   */
  @Test
  @DisplayName("Test createEventVertex(FlowElement); then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.createEventVertex(FlowElement)"})
  void testCreateEventVertex_thenThrowRuntimeException() {
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
   * Test {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}.
   * <ul>
   *   <li>Then calls {@link mxStylesheet#getStyles()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}
   */
  @Test
  @DisplayName("Test createGatewayVertex(FlowElement); then calls getStyles()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.createGatewayVertex(FlowElement)"})
  void testCreateGatewayVertex_thenCallsGetStyles() {
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
   * Test {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}.
   * <ul>
   *   <li>Then calls {@link mxGraph#insertVertex(Object, String, Object, double, double, double, double, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}
   */
  @Test
  @DisplayName("Test createGatewayVertex(FlowElement); then calls insertVertex(Object, String, Object, double, double, double, double, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnAutoLayout.createGatewayVertex(FlowElement)"})
  void testCreateGatewayVertex_thenCallsInsertVertex() {
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
   * Test {@link BpmnAutoLayout#euclidianDistance(mxPoint, mxPoint)}.
   * <ul>
   *   <li>When {@link mxPoint#mxPoint()}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#euclidianDistance(mxPoint, mxPoint)}
   */
  @Test
  @DisplayName("Test euclidianDistance(mxPoint, mxPoint); when mxPoint(); then return zero")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link mxPoint#mxPoint()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link mxPoint#mxPoint()}.</li>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName("Test optimizeEdgePoints(List); given mxPoint(); when ArrayList() add mxPoint(); then return ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_givenMxPoint_whenArrayListAddMxPoint_thenReturnArrayList() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    unoptimizedPointsList.add(new mxPoint());
    unoptimizedPointsList.add(new mxPoint());

    // Act and Assert
    assertEquals(unoptimizedPointsList, bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList));
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   * <ul>
   *   <li>Given {@link mxPoint#mxPoint()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link mxPoint#mxPoint()}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName("Test optimizeEdgePoints(List); given mxPoint(); when ArrayList() add mxPoint(); then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_givenMxPoint_whenArrayListAddMxPoint_thenReturnSizeIsOne() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    mxPoint mxPoint = new mxPoint();
    unoptimizedPointsList.add(mxPoint);

    // Act
    List<mxPoint> actualOptimizeEdgePointsResult = bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(1, actualOptimizeEdgePointsResult.size());
    assertSame(mxPoint, actualOptimizeEdgePointsResult.get(0));
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   * <ul>
   *   <li>Given {@link mxPoint#mxPoint()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link mxPoint#mxPoint()}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName("Test optimizeEdgePoints(List); given mxPoint(); when ArrayList() add mxPoint(); then return size is two")
  @Tag("MaintainedByDiffblue")
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
    List<mxPoint> actualOptimizeEdgePointsResult = bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList);

    // Assert
    assertEquals(2, actualOptimizeEdgePointsResult.size());
    assertSame(mxPoint, actualOptimizeEdgePointsResult.get(1));
  }

  /**
   * Test {@link BpmnAutoLayout#optimizeEdgePoints(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName("Test optimizeEdgePoints(List); when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnAutoLayout.optimizeEdgePoints(List)"})
  void testOptimizeEdgePoints_whenArrayList_thenReturnEmpty() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    // Act and Assert
    assertTrue(bpmnAutoLayout.optimizeEdgePoints(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)} with {@code element}, {@code waypoints}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)}
   */
  @Test
  @DisplayName("Test createDiagramInterchangeInformation(BaseElement, List) with 'element', 'waypoints'")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)} with {@code element}, {@code waypoints}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)}
   */
  @Test
  @DisplayName("Test createDiagramInterchangeInformation(BaseElement, List) with 'element', 'waypoints'")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link BpmnAutoLayout#createDiagramInterchangeInformation(FlowElement, int, int, int, int)} with {@code flowElement}, {@code x}, {@code y}, {@code width}, {@code height}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#createDiagramInterchangeInformation(FlowElement, int, int, int, int)}
   */
  @Test
  @DisplayName("Test createDiagramInterchangeInformation(FlowElement, int, int, int, int) with 'flowElement', 'x', 'y', 'width', 'height'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"GraphicInfo BpmnAutoLayout.createDiagramInterchangeInformation(FlowElement, int, int, int, int)"})
  void testCreateDiagramInterchangeInformationWithFlowElementXYWidthHeight() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());
    AdhocSubProcess flowElement = new AdhocSubProcess();

    // Act
    GraphicInfo actualCreateDiagramInterchangeInformationResult = bpmnAutoLayout
        .createDiagramInterchangeInformation(flowElement, 2, 3, 1, 1);

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
