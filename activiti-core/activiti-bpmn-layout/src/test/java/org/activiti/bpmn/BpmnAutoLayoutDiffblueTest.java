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
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
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
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.ComplexGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Signal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnAutoLayoutDiffblueTest {
  /**
   * Test CustomLayout {@link CustomLayout#CustomLayout(mxGraph, int)}.
   * <p>
   * Method under test:
   * {@link BpmnAutoLayout.CustomLayout#CustomLayout(mxGraph, int)}
   */
  @Test
  @DisplayName("Test CustomLayout new CustomLayout(mxGraph, int)")
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

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  void testExecute() {
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
    assertTrue(frame instanceof Rectangle2D.Double);
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
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult = stringObjectMap.get(null);
    assertTrue(getResult instanceof mxCell);
    Rectangle rectangle = ((mxCell) getResult).getGeometry().getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    Rectangle bounds = bpmnAutoLayout.getGraph().getGraphBounds().getRectangle().getBounds();
    Rectangle2D bounds2D2 = bounds.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle bounds2 = bounds.getBounds();
    Rectangle2D bounds2D3 = bounds2.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    Rectangle bounds3 = bounds2.getBounds();
    Rectangle2D bounds2D4 = bounds3.getBounds2D();
    assertTrue(bounds2D4 instanceof Rectangle);
    Rectangle bounds4 = bounds3.getBounds();
    Rectangle bounds5 = bounds4.getBounds();
    Rectangle2D bounds2D5 = bounds5.getBounds2D();
    assertTrue(bounds2D5 instanceof Rectangle);
    Rectangle bounds6 = bounds2D2.getBounds().getBounds();
    Rectangle2D bounds2D6 = bounds6.getBounds2D();
    assertTrue(bounds2D6 instanceof Rectangle);
    Rectangle bounds7 = bounds2D3.getBounds();
    Rectangle2D bounds2D7 = bounds7.getBounds2D();
    assertTrue(bounds2D7 instanceof Rectangle);
    Rectangle2D frame = bounds2.getFrame();
    Rectangle bounds8 = frame.getBounds();
    Rectangle2D bounds2D8 = bounds8.getBounds2D();
    assertTrue(bounds2D8 instanceof Rectangle);
    Rectangle2D bounds2D9 = bounds2D4.getBounds2D();
    assertTrue(bounds2D9 instanceof Rectangle);
    Rectangle2D frame2 = rectangle.getFrame();
    assertTrue(frame2 instanceof Rectangle2D.Double);
    assertTrue(frame instanceof Rectangle2D.Double);
    Rectangle2D frame3 = bounds3.getFrame();
    assertTrue(frame3 instanceof Rectangle2D.Double);
    assertEquals(20.0d, bounds6.getCenterX());
    assertEquals(20.0d, bounds7.getCenterX());
    assertEquals(20.0d, bounds8.getCenterX());
    assertEquals(20.0d, frame3.getCenterX());
    Rectangle bounds9 = rectangle.getBounds().getBounds();
    assertEquals(20.0d, bounds9.getCenterY());
    assertEquals(20.0d, bounds5.getCenterY());
    assertEquals(20.0d, bounds2D.getCenterY());
    assertEquals(20.0d, bounds2D4.getCenterY());
    assertEquals(20.0d, bounds7.getCenterY());
    assertEquals(20.0d, bounds8.getCenterY());
    assertEquals(20.0d, frame2.getCenterY());
    assertEquals(20.0d, frame3.getCenterY());
    Dimension size = bounds2.getSize().getSize().getSize();
    Dimension size2 = size.getSize();
    assertEquals(40, size2.height);
    Dimension size3 = bounds3.getSize().getSize();
    Dimension size4 = size3.getSize();
    assertEquals(40, size4.height);
    Dimension size5 = bounds4.getSize();
    assertEquals(40, size5.getSize().height);
    Dimension size6 = ((Rectangle) bounds2D3).getSize();
    Dimension size7 = size6.getSize();
    assertEquals(40, size7.height);
    assertEquals(40, bounds5.getSize().height);
    Dimension size8 = bounds7.getSize();
    assertEquals(40, size8.height);
    Dimension size9 = bounds8.getSize();
    assertEquals(40, size9.height);
    Dimension size10 = ((Rectangle) bounds2D4).getSize();
    assertEquals(40, size10.height);
    assertEquals(40, size2.width);
    assertEquals(40, size4.width);
    assertEquals(40, size7.width);
    assertEquals(40, size8.width);
    assertEquals(40, size9.width);
    assertEquals(40, size10.width);
    assertEquals(40, bounds5.getBounds().height);
    Rectangle bounds10 = bounds6.getBounds();
    assertEquals(40, bounds10.height);
    Rectangle bounds11 = bounds7.getBounds();
    assertEquals(40, bounds11.height);
    Rectangle bounds12 = bounds8.getBounds();
    assertEquals(40, bounds12.height);
    assertEquals(40, bounds2D4.getBounds().height);
    Rectangle bounds13 = frame3.getBounds();
    assertEquals(40, bounds13.height);
    assertEquals(40, ((Rectangle) bounds2D5).height);
    assertEquals(40, ((Rectangle) bounds2D6).height);
    assertEquals(40, ((Rectangle) bounds2D7).height);
    assertEquals(40, ((Rectangle) bounds2D8).height);
    assertEquals(40, ((Rectangle) bounds2D9).height);
    assertEquals(40, bounds10.width);
    assertEquals(40, bounds11.width);
    assertEquals(40, bounds12.width);
    assertEquals(40, bounds13.width);
    assertEquals(40, ((Rectangle) bounds2D6).width);
    assertEquals(40, ((Rectangle) bounds2D7).width);
    assertEquals(40, ((Rectangle) bounds2D8).width);
    assertEquals(40.0d, size.getHeight());
    assertEquals(40.0d, size3.getHeight());
    assertEquals(40.0d, size5.getHeight());
    assertEquals(40.0d, size6.getHeight());
    assertEquals(40.0d, size.getWidth());
    assertEquals(40.0d, size3.getWidth());
    assertEquals(40.0d, size6.getWidth());
    assertEquals(40.0d, bounds5.getHeight());
    assertEquals(40.0d, bounds7.getHeight());
    assertEquals(40.0d, bounds8.getHeight());
    assertEquals(40.0d, bounds7.getWidth());
    assertEquals(40.0d, bounds8.getWidth());
    assertEquals(40.0d, bounds2D4.getHeight());
    assertEquals(40.0d, frame3.getHeight());
    assertEquals(40.0d, bounds2D4.getMaxX());
    assertEquals(40.0d, bounds7.getMaxX());
    assertEquals(40.0d, bounds8.getMaxX());
    assertEquals(40.0d, frame3.getMaxX());
    assertEquals(40.0d, bounds9.getMaxY());
    assertEquals(40.0d, bounds5.getMaxY());
    assertEquals(40.0d, bounds2D.getMaxY());
    assertEquals(40.0d, bounds2D4.getMaxY());
    assertEquals(40.0d, bounds7.getMaxY());
    assertEquals(40.0d, bounds8.getMaxY());
    assertEquals(40.0d, frame2.getMaxY());
    assertEquals(40.0d, frame3.getMaxY());
    assertEquals(40.0d, bounds2D4.getWidth());
    assertEquals(40.0d, frame3.getWidth());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  void testExecute3() {
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
    Rectangle2D bounds2D2 = bounds2D.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle2D bounds2D3 = bounds2D2.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    BpmnModel bpmnModel2 = bpmnAutoLayout.bpmnModel;
    Collection<FlowElement> flowElements = bpmnModel2.getMainProcess().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    FlowElement getResult2 = stringFlowElementMap.get(null);
    assertTrue(getResult2 instanceof ComplexGateway);
    assertEquals("styleGateway", ((mxCell) getResult).getStyle());
    assertNull(((ComplexGateway) getResult2).getDefaultFlow());
    Map<String, GraphicInfo> locationMap = bpmnModel2.getLocationMap();
    assertEquals(1, locationMap.size());
    assertEquals(20.0d, bounds2D2.getCenterX());
    Rectangle bounds = bounds2D.getBounds();
    Rectangle bounds2 = bounds.getBounds();
    assertEquals(20.0d, bounds2.getCenterY());
    assertEquals(20.0d, bounds2D2.getCenterY());
    Dimension size = bounds.getSize();
    Dimension size2 = size.getSize();
    assertEquals(40, size2.height);
    Dimension size3 = bounds2.getSize();
    assertEquals(40, size3.height);
    assertEquals(40, size2.width);
    assertEquals(40, size3.width);
    Rectangle bounds3 = bounds2D2.getBounds();
    assertEquals(40, bounds3.height);
    assertEquals(40, ((Rectangle) bounds2D3).height);
    assertEquals(40, bounds3.width);
    assertEquals(40, ((Rectangle) bounds2D3).width);
    assertEquals(40.0d, size.getHeight());
    assertEquals(40.0d, size.getWidth());
    assertEquals(40.0d, bounds2.getHeight());
    assertEquals(40.0d, bounds2.getWidth());
    assertEquals(40.0d, bounds2D2.getHeight());
    assertEquals(40.0d, bounds2.getMaxX());
    assertEquals(40.0d, bounds2.getMaxY());
    assertFalse(bounds2.isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
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
  void testExecute4() {
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
    Rectangle2D bounds2D = bpmnAutoLayout.getGraph().getGraphBounds().getRectangle().getBounds().getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    BpmnModel bpmnModel2 = bpmnAutoLayout.bpmnModel;
    Collection<FlowElement> flowElements = bpmnModel2.getMainProcess().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Map<String, GraphicInfo> locationMap = bpmnModel2.getLocationMap();
    assertEquals(1, locationMap.size());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertEquals(60.0d, bounds2D.getBounds().getBounds().getMaxY());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
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
  void testExecute5() {
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
    BpmnModel bpmnModel2 = bpmnAutoLayout.bpmnModel;
    Collection<FlowElement> flowElements = bpmnModel2.getMainProcess().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Map<String, GraphicInfo> locationMap = bpmnModel2.getLocationMap();
    assertEquals(1, locationMap.size());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
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
  void testExecute6() {
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
    Collection<FlowElement> flowElements = bpmnAutoLayout.bpmnModel.getMainProcess().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(element, stringFlowElementMap.get(null));
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute()")
  void testExecute7() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    AdhocSubProcess element2 = new AdhocSubProcess();
    element.addFlowElement(element2);

    Process process = new Process();
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
    mxGeometry geometry = ((mxCell) getResult).getGeometry();
    Rectangle rectangle = geometry.getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    mxRectangle graphBounds = bpmnAutoLayout.getGraph().getGraphBounds();
    Rectangle rectangle2 = graphBounds.getRectangle();
    Rectangle2D bounds2D2 = rectangle2.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle bounds = rectangle.getBounds();
    Rectangle2D bounds2D3 = bounds.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    Rectangle bounds2 = rectangle2.getBounds();
    Rectangle2D bounds2D4 = bounds2.getBounds2D();
    assertTrue(bounds2D4 instanceof Rectangle);
    Rectangle bounds3 = bounds.getBounds();
    Rectangle2D bounds2D5 = bounds3.getBounds2D();
    assertTrue(bounds2D5 instanceof Rectangle);
    Rectangle bounds4 = bounds2.getBounds();
    Rectangle2D bounds2D6 = bounds4.getBounds2D();
    assertTrue(bounds2D6 instanceof Rectangle);
    Rectangle bounds5 = bounds4.getBounds();
    Rectangle2D bounds2D7 = bounds5.getBounds2D();
    assertTrue(bounds2D7 instanceof Rectangle);
    Rectangle bounds6 = bounds5.getBounds();
    Rectangle2D bounds2D8 = bounds6.getBounds2D();
    assertTrue(bounds2D8 instanceof Rectangle);
    Rectangle bounds7 = bounds6.getBounds();
    Rectangle2D bounds2D9 = bounds7.getBounds2D();
    assertTrue(bounds2D9 instanceof Rectangle);
    Rectangle bounds8 = bounds2D2.getBounds();
    Rectangle bounds9 = bounds8.getBounds();
    Rectangle2D bounds2D10 = bounds9.getBounds2D();
    assertTrue(bounds2D10 instanceof Rectangle);
    Rectangle2D frame = rectangle2.getFrame();
    Rectangle bounds10 = frame.getBounds();
    Rectangle bounds11 = bounds10.getBounds();
    Rectangle2D bounds2D11 = bounds11.getBounds2D();
    assertTrue(bounds2D11 instanceof Rectangle);
    Rectangle2D bounds2D12 = bounds8.getBounds2D();
    assertTrue(bounds2D12 instanceof Rectangle);
    Rectangle bounds12 = bounds2D4.getBounds();
    Rectangle2D bounds2D13 = bounds12.getBounds2D();
    assertTrue(bounds2D13 instanceof Rectangle);
    Rectangle2D bounds2D14 = bounds10.getBounds2D();
    assertTrue(bounds2D14 instanceof Rectangle);
    Rectangle2D frame2 = bounds2.getFrame();
    Rectangle bounds13 = frame2.getBounds();
    Rectangle2D bounds2D15 = bounds13.getBounds2D();
    assertTrue(bounds2D15 instanceof Rectangle);
    Rectangle2D bounds2D16 = bounds2D.getBounds2D();
    assertTrue(bounds2D16 instanceof Rectangle);
    Rectangle2D bounds2D17 = bounds2D2.getBounds2D();
    assertTrue(bounds2D17 instanceof Rectangle);
    Rectangle2D bounds2D18 = bounds2D4.getBounds2D();
    assertTrue(bounds2D18 instanceof Rectangle);
    Rectangle2D bounds2D19 = bounds2D6.getBounds2D();
    assertTrue(bounds2D19 instanceof Rectangle);
    Rectangle2D bounds2D20 = bounds2D7.getBounds2D();
    assertTrue(bounds2D20 instanceof Rectangle);
    Rectangle2D bounds2D21 = bounds2D17.getBounds2D();
    assertTrue(bounds2D21 instanceof Rectangle);
    Rectangle2D bounds2D22 = frame.getBounds2D();
    assertTrue(bounds2D22 instanceof Rectangle2D.Double);
    Rectangle2D frame3 = rectangle.getFrame();
    assertTrue(frame3 instanceof Rectangle2D.Double);
    assertTrue(frame instanceof Rectangle2D.Double);
    assertTrue(frame2 instanceof Rectangle2D.Double);
    Rectangle2D frame4 = bounds4.getFrame();
    assertTrue(frame4 instanceof Rectangle2D.Double);
    Rectangle2D frame5 = bounds5.getFrame();
    assertTrue(frame5 instanceof Rectangle2D.Double);
    Rectangle2D frame6 = bounds2D2.getFrame();
    assertTrue(frame6 instanceof Rectangle2D.Double);
    Rectangle2D frame7 = frame.getFrame();
    assertTrue(frame7 instanceof Rectangle2D.Double);
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    FlowElement getResult2 = stringFlowElementMap.get(null);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult2).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult2 instanceof AdhocSubProcess);
    Map<String, GraphicInfo> locationMap = bpmnAutoLayout.bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    GraphicInfo getResult3 = locationMap.get(null);
    assertEquals(20.0d, getResult3.getX());
    assertEquals(20.0d, getResult3.getY());
    assertEquals(40.0d, geometry.getCenterX());
    assertEquals(40.0d, graphBounds.getCenterX());
    assertEquals(40.0d, geometry.getCenterY());
    assertEquals(40.0d, graphBounds.getCenterY());
    assertEquals(40.0d, rectangle.getCenterX());
    assertEquals(40.0d, rectangle2.getCenterX());
    assertEquals(40.0d, bounds.getCenterX());
    assertEquals(40.0d, bounds2.getCenterX());
    assertEquals(40.0d, bounds3.getCenterX());
    assertEquals(40.0d, bounds4.getCenterX());
    assertEquals(40.0d, bounds5.getCenterX());
    assertEquals(40.0d, bounds6.getCenterX());
    assertEquals(40.0d, bounds7.getCenterX());
    assertEquals(40.0d, bounds9.getCenterX());
    assertEquals(40.0d, bounds11.getCenterX());
    assertEquals(40.0d, bounds2D.getCenterX());
    assertEquals(40.0d, bounds2D2.getCenterX());
    assertEquals(40.0d, bounds2D4.getCenterX());
    assertEquals(40.0d, bounds2D6.getCenterX());
    assertEquals(40.0d, bounds2D7.getCenterX());
    assertEquals(40.0d, bounds2D17.getCenterX());
    assertEquals(40.0d, bounds2D22.getCenterX());
    assertEquals(40.0d, bounds8.getCenterX());
    assertEquals(40.0d, bounds12.getCenterX());
    assertEquals(40.0d, bounds10.getCenterX());
    assertEquals(40.0d, bounds13.getCenterX());
    assertEquals(40.0d, frame3.getCenterX());
    assertEquals(40.0d, frame.getCenterX());
    assertEquals(40.0d, frame2.getCenterX());
    assertEquals(40.0d, frame4.getCenterX());
    assertEquals(40.0d, frame5.getCenterX());
    assertEquals(40.0d, frame6.getCenterX());
    assertEquals(40.0d, frame7.getCenterX());
    assertEquals(40.0d, rectangle.getCenterY());
    assertEquals(40.0d, rectangle2.getCenterY());
    assertEquals(40.0d, bounds.getCenterY());
    assertEquals(40.0d, bounds2.getCenterY());
    assertEquals(40.0d, bounds3.getCenterY());
    assertEquals(40.0d, bounds4.getCenterY());
    assertEquals(40.0d, bounds5.getCenterY());
    assertEquals(40.0d, bounds6.getCenterY());
    assertEquals(40.0d, bounds7.getCenterY());
    assertEquals(40.0d, bounds9.getCenterY());
    assertEquals(40.0d, bounds11.getCenterY());
    assertEquals(40.0d, bounds2D.getCenterY());
    assertEquals(40.0d, bounds2D2.getCenterY());
    assertEquals(40.0d, bounds2D4.getCenterY());
    assertEquals(40.0d, bounds2D6.getCenterY());
    assertEquals(40.0d, bounds2D7.getCenterY());
    assertEquals(40.0d, bounds2D17.getCenterY());
    assertEquals(40.0d, bounds2D22.getCenterY());
    assertEquals(40.0d, bounds8.getCenterY());
    assertEquals(40.0d, bounds12.getCenterY());
    assertEquals(40.0d, bounds10.getCenterY());
    assertEquals(40.0d, bounds13.getCenterY());
    assertEquals(40.0d, frame3.getCenterY());
    assertEquals(40.0d, frame.getCenterY());
    assertEquals(40.0d, frame2.getCenterY());
    assertEquals(40.0d, frame4.getCenterY());
    assertEquals(40.0d, frame5.getCenterY());
    assertEquals(40.0d, frame6.getCenterY());
    assertEquals(40.0d, frame7.getCenterY());
    Dimension size = rectangle2.getSize();
    Dimension size2 = size.getSize();
    Dimension size3 = size2.getSize();
    Dimension size4 = size3.getSize();
    Dimension size5 = size4.getSize();
    assertEquals(80, size5.height);
    assertEquals(80, size4.height);
    Dimension size6 = bounds2.getSize();
    Dimension size7 = size6.getSize();
    Dimension size8 = size7.getSize();
    Dimension size9 = size8.getSize();
    assertEquals(80, size9.height);
    Dimension size10 = rectangle.getSize();
    Dimension size11 = size10.getSize();
    Dimension size12 = size11.getSize();
    assertEquals(80, size12.height);
    assertEquals(80, size3.height);
    assertEquals(80, size8.height);
    Dimension size13 = bounds4.getSize();
    Dimension size14 = size13.getSize();
    Dimension size15 = size14.getSize();
    assertEquals(80, size15.height);
    Dimension size16 = bounds5.getSize();
    Dimension size17 = size16.getSize();
    Dimension size18 = size17.getSize();
    assertEquals(80, size18.height);
    Dimension size19 = ((Rectangle) bounds2D2).getSize();
    Dimension size20 = size19.getSize();
    Dimension size21 = size20.getSize();
    assertEquals(80, size21.height);
    assertEquals(80, size11.height);
    assertEquals(80, size2.height);
    Dimension size22 = bounds.getSize();
    Dimension size23 = size22.getSize();
    assertEquals(80, size23.height);
    assertEquals(80, size7.height);
    assertEquals(80, size14.height);
    assertEquals(80, size17.height);
    Dimension size24 = bounds6.getSize();
    Dimension size25 = size24.getSize();
    assertEquals(80, size25.height);
    Dimension size26 = bounds8.getSize();
    Dimension size27 = size26.getSize();
    assertEquals(80, size27.height);
    Dimension size28 = bounds10.getSize();
    Dimension size29 = size28.getSize();
    assertEquals(80, size29.height);
    assertEquals(80, size20.height);
    Dimension size30 = ((Rectangle) bounds2D4).getSize();
    Dimension size31 = size30.getSize();
    assertEquals(80, size31.height);
    assertEquals(80, size10.height);
    assertEquals(80, size.height);
    assertEquals(80, size22.height);
    assertEquals(80, size6.height);
    Dimension size32 = bounds3.getSize();
    assertEquals(80, size32.height);
    assertEquals(80, size13.height);
    assertEquals(80, size16.height);
    assertEquals(80, size24.height);
    Dimension size33 = bounds7.getSize();
    assertEquals(80, size33.height);
    Dimension size34 = bounds9.getSize();
    assertEquals(80, size34.height);
    Dimension size35 = bounds11.getSize();
    assertEquals(80, size35.height);
    assertEquals(80, size26.height);
    Dimension size36 = bounds12.getSize();
    assertEquals(80, size36.height);
    assertEquals(80, size28.height);
    Dimension size37 = bounds13.getSize();
    assertEquals(80, size37.height);
    Dimension size38 = ((Rectangle) bounds2D).getSize();
    assertEquals(80, size38.height);
    assertEquals(80, size19.height);
    assertEquals(80, size30.height);
    Dimension size39 = ((Rectangle) bounds2D6).getSize();
    assertEquals(80, size39.height);
    Dimension size40 = ((Rectangle) bounds2D7).getSize();
    assertEquals(80, size40.height);
    Dimension size41 = ((Rectangle) bounds2D17).getSize();
    assertEquals(80, size41.height);
    assertEquals(80, size5.width);
    assertEquals(80, size4.width);
    assertEquals(80, size9.width);
    assertEquals(80, size12.width);
    assertEquals(80, size3.width);
    assertEquals(80, size8.width);
    assertEquals(80, size15.width);
    assertEquals(80, size18.width);
    assertEquals(80, size21.width);
    assertEquals(80, size11.width);
    assertEquals(80, size2.width);
    assertEquals(80, size23.width);
    assertEquals(80, size7.width);
    assertEquals(80, size14.width);
    assertEquals(80, size17.width);
    assertEquals(80, size25.width);
    assertEquals(80, size27.width);
    assertEquals(80, size29.width);
    assertEquals(80, size20.width);
    assertEquals(80, size31.width);
    assertEquals(80, size10.width);
    assertEquals(80, size.width);
    assertEquals(80, size22.width);
    assertEquals(80, size6.width);
    assertEquals(80, size32.width);
    assertEquals(80, size13.width);
    assertEquals(80, size16.width);
    assertEquals(80, size24.width);
    assertEquals(80, size33.width);
    assertEquals(80, size34.width);
    assertEquals(80, size35.width);
    assertEquals(80, size26.width);
    assertEquals(80, size36.width);
    assertEquals(80, size28.width);
    assertEquals(80, size37.width);
    assertEquals(80, size38.width);
    assertEquals(80, size19.width);
    assertEquals(80, size30.width);
    assertEquals(80, size39.width);
    assertEquals(80, size40.width);
    assertEquals(80, size41.width);
    assertEquals(80, rectangle.height);
    assertEquals(80, rectangle2.height);
    assertEquals(80, bounds.height);
    assertEquals(80, bounds2.height);
    assertEquals(80, bounds3.height);
    assertEquals(80, bounds4.height);
    Rectangle bounds14 = bounds3.getBounds();
    assertEquals(80, bounds14.height);
    assertEquals(80, bounds5.height);
    assertEquals(80, bounds6.height);
    assertEquals(80, bounds7.height);
    Rectangle bounds15 = bounds7.getBounds();
    assertEquals(80, bounds15.height);
    Rectangle bounds16 = bounds9.getBounds();
    assertEquals(80, bounds16.height);
    Rectangle bounds17 = bounds11.getBounds();
    assertEquals(80, bounds17.height);
    assertEquals(80, bounds9.height);
    Rectangle bounds18 = bounds12.getBounds();
    assertEquals(80, bounds18.height);
    assertEquals(80, bounds11.height);
    Rectangle bounds19 = bounds13.getBounds();
    assertEquals(80, bounds19.height);
    Rectangle bounds20 = bounds2D.getBounds();
    assertEquals(80, bounds20.height);
    assertEquals(80, bounds8.height);
    assertEquals(80, bounds12.height);
    Rectangle bounds21 = bounds2D6.getBounds();
    assertEquals(80, bounds21.height);
    Rectangle bounds22 = bounds2D7.getBounds();
    assertEquals(80, bounds22.height);
    Rectangle bounds23 = bounds2D17.getBounds();
    assertEquals(80, bounds23.height);
    Rectangle bounds24 = bounds2D22.getBounds();
    assertEquals(80, bounds24.height);
    Rectangle bounds25 = frame3.getBounds();
    assertEquals(80, bounds25.height);
    assertEquals(80, bounds10.height);
    assertEquals(80, bounds13.height);
    Rectangle bounds26 = frame4.getBounds();
    assertEquals(80, bounds26.height);
    Rectangle bounds27 = frame5.getBounds();
    assertEquals(80, bounds27.height);
    Rectangle bounds28 = frame6.getBounds();
    assertEquals(80, bounds28.height);
    Rectangle bounds29 = frame7.getBounds();
    assertEquals(80, bounds29.height);
    assertEquals(80, ((Rectangle) bounds2D).height);
    assertEquals(80, ((Rectangle) bounds2D2).height);
    assertEquals(80, ((Rectangle) bounds2D3).height);
    assertEquals(80, ((Rectangle) bounds2D4).height);
    assertEquals(80, ((Rectangle) bounds2D5).height);
    assertEquals(80, ((Rectangle) bounds2D6).height);
    assertEquals(80, ((Rectangle) bounds2D7).height);
    assertEquals(80, ((Rectangle) bounds2D8).height);
    assertEquals(80, ((Rectangle) bounds2D9).height);
    assertEquals(80, ((Rectangle) bounds2D10).height);
    assertEquals(80, ((Rectangle) bounds2D11).height);
    assertEquals(80, ((Rectangle) bounds2D12).height);
    assertEquals(80, ((Rectangle) bounds2D13).height);
    assertEquals(80, ((Rectangle) bounds2D14).height);
    assertEquals(80, ((Rectangle) bounds2D15).height);
    assertEquals(80, ((Rectangle) bounds2D16).height);
    assertEquals(80, ((Rectangle) bounds2D17).height);
    assertEquals(80, ((Rectangle) bounds2D18).height);
    assertEquals(80, ((Rectangle) bounds2D19).height);
    assertEquals(80, ((Rectangle) bounds2D20).height);
    assertEquals(80, ((Rectangle) bounds2D21).height);
    assertEquals(80, rectangle.width);
    assertEquals(80, rectangle2.width);
    assertEquals(80, bounds.width);
    assertEquals(80, bounds2.width);
    assertEquals(80, bounds3.width);
    assertEquals(80, bounds4.width);
    assertEquals(80, bounds14.width);
    assertEquals(80, bounds5.width);
    assertEquals(80, bounds6.width);
    assertEquals(80, bounds7.width);
    assertEquals(80, bounds15.width);
    assertEquals(80, bounds16.width);
    assertEquals(80, bounds17.width);
    assertEquals(80, bounds9.width);
    assertEquals(80, bounds18.width);
    assertEquals(80, bounds11.width);
    assertEquals(80, bounds19.width);
    assertEquals(80, bounds20.width);
    assertEquals(80, bounds8.width);
    assertEquals(80, bounds12.width);
    assertEquals(80, bounds21.width);
    assertEquals(80, bounds22.width);
    assertEquals(80, bounds23.width);
    assertEquals(80, bounds24.width);
    assertEquals(80, bounds25.width);
    assertEquals(80, bounds10.width);
    assertEquals(80, bounds13.width);
    assertEquals(80, bounds26.width);
    assertEquals(80, bounds27.width);
    assertEquals(80, bounds28.width);
    assertEquals(80, bounds29.width);
    assertEquals(80, ((Rectangle) bounds2D).width);
    assertEquals(80, ((Rectangle) bounds2D2).width);
    assertEquals(80, ((Rectangle) bounds2D3).width);
    assertEquals(80, ((Rectangle) bounds2D4).width);
    assertEquals(80, ((Rectangle) bounds2D5).width);
    assertEquals(80, ((Rectangle) bounds2D6).width);
    assertEquals(80, ((Rectangle) bounds2D7).width);
    assertEquals(80, ((Rectangle) bounds2D8).width);
    assertEquals(80, ((Rectangle) bounds2D9).width);
    assertEquals(80, ((Rectangle) bounds2D10).width);
    assertEquals(80, ((Rectangle) bounds2D11).width);
    assertEquals(80, ((Rectangle) bounds2D12).width);
    assertEquals(80, ((Rectangle) bounds2D13).width);
    assertEquals(80, ((Rectangle) bounds2D14).width);
    assertEquals(80, ((Rectangle) bounds2D15).width);
    assertEquals(80, ((Rectangle) bounds2D16).width);
    assertEquals(80, ((Rectangle) bounds2D17).width);
    assertEquals(80, ((Rectangle) bounds2D18).width);
    assertEquals(80, ((Rectangle) bounds2D19).width);
    assertEquals(80, ((Rectangle) bounds2D20).width);
    assertEquals(80, ((Rectangle) bounds2D21).width);
    assertEquals(80.0d, geometry.getHeight());
    assertEquals(80.0d, graphBounds.getHeight());
    assertEquals(80.0d, geometry.getWidth());
    assertEquals(80.0d, graphBounds.getWidth());
    assertEquals(80.0d, size4.getHeight());
    assertEquals(80.0d, size3.getHeight());
    assertEquals(80.0d, size8.getHeight());
    assertEquals(80.0d, size11.getHeight());
    assertEquals(80.0d, size2.getHeight());
    assertEquals(80.0d, size7.getHeight());
    assertEquals(80.0d, size14.getHeight());
    assertEquals(80.0d, size17.getHeight());
    assertEquals(80.0d, size20.getHeight());
    assertEquals(80.0d, size10.getHeight());
    assertEquals(80.0d, size.getHeight());
    assertEquals(80.0d, size22.getHeight());
    assertEquals(80.0d, size6.getHeight());
    assertEquals(80.0d, size13.getHeight());
    assertEquals(80.0d, size16.getHeight());
    assertEquals(80.0d, size24.getHeight());
    assertEquals(80.0d, size26.getHeight());
    assertEquals(80.0d, size28.getHeight());
    assertEquals(80.0d, size19.getHeight());
    assertEquals(80.0d, size30.getHeight());
    assertEquals(80.0d, size4.getWidth());
    assertEquals(80.0d, size3.getWidth());
    assertEquals(80.0d, size8.getWidth());
    assertEquals(80.0d, size11.getWidth());
    assertEquals(80.0d, size2.getWidth());
    assertEquals(80.0d, size7.getWidth());
    assertEquals(80.0d, size14.getWidth());
    assertEquals(80.0d, size20.getWidth());
    assertEquals(80.0d, size10.getWidth());
    assertEquals(80.0d, size.getWidth());
    assertEquals(80.0d, size22.getWidth());
    assertEquals(80.0d, size6.getWidth());
    assertEquals(80.0d, size13.getWidth());
    assertEquals(80.0d, size16.getWidth());
    assertEquals(80.0d, size24.getWidth());
    assertEquals(80.0d, size26.getWidth());
    assertEquals(80.0d, size28.getWidth());
    assertEquals(80.0d, size19.getWidth());
    assertEquals(80.0d, size30.getWidth());
    assertEquals(80.0d, rectangle.getHeight());
    assertEquals(80.0d, rectangle2.getHeight());
    assertEquals(80.0d, bounds.getHeight());
    assertEquals(80.0d, bounds2.getHeight());
    assertEquals(80.0d, bounds3.getHeight());
    assertEquals(80.0d, bounds4.getHeight());
    assertEquals(80.0d, bounds5.getHeight());
    assertEquals(80.0d, bounds6.getHeight());
    assertEquals(80.0d, bounds7.getHeight());
    assertEquals(80.0d, bounds9.getHeight());
    assertEquals(80.0d, bounds11.getHeight());
    assertEquals(80.0d, bounds8.getHeight());
    assertEquals(80.0d, bounds12.getHeight());
    assertEquals(80.0d, bounds10.getHeight());
    assertEquals(80.0d, bounds13.getHeight());
    assertEquals(80.0d, rectangle.getWidth());
    assertEquals(80.0d, rectangle2.getWidth());
    assertEquals(80.0d, bounds.getWidth());
    assertEquals(80.0d, bounds2.getWidth());
    assertEquals(80.0d, bounds3.getWidth());
    assertEquals(80.0d, bounds4.getWidth());
    assertEquals(80.0d, bounds5.getWidth());
    assertEquals(80.0d, bounds6.getWidth());
    assertEquals(80.0d, bounds7.getWidth());
    assertEquals(80.0d, bounds9.getWidth());
    assertEquals(80.0d, bounds11.getWidth());
    assertEquals(80.0d, bounds8.getWidth());
    assertEquals(80.0d, bounds12.getWidth());
    assertEquals(80.0d, bounds10.getWidth());
    assertEquals(80.0d, bounds13.getWidth());
    assertEquals(80.0d, bounds2D.getHeight());
    assertEquals(80.0d, bounds2D2.getHeight());
    assertEquals(80.0d, bounds2D4.getHeight());
    assertEquals(80.0d, bounds2D6.getHeight());
    assertEquals(80.0d, bounds2D7.getHeight());
    assertEquals(80.0d, bounds2D17.getHeight());
    assertEquals(80.0d, bounds2D22.getHeight());
    assertEquals(80.0d, frame3.getHeight());
    assertEquals(80.0d, frame.getHeight());
    assertEquals(80.0d, frame2.getHeight());
    assertEquals(80.0d, frame4.getHeight());
    assertEquals(80.0d, frame5.getHeight());
    assertEquals(80.0d, frame6.getHeight());
    assertEquals(80.0d, frame7.getHeight());
    assertEquals(80.0d, rectangle.getMaxX());
    assertEquals(80.0d, rectangle2.getMaxX());
    assertEquals(80.0d, bounds.getMaxX());
    assertEquals(80.0d, bounds2.getMaxX());
    assertEquals(80.0d, bounds3.getMaxX());
    assertEquals(80.0d, bounds4.getMaxX());
    assertEquals(80.0d, bounds5.getMaxX());
    assertEquals(80.0d, bounds6.getMaxX());
    assertEquals(80.0d, bounds7.getMaxX());
    assertEquals(80.0d, bounds9.getMaxX());
    assertEquals(80.0d, bounds11.getMaxX());
    assertEquals(80.0d, bounds2D.getMaxX());
    assertEquals(80.0d, bounds2D2.getMaxX());
    assertEquals(80.0d, bounds2D4.getMaxX());
    assertEquals(80.0d, bounds2D6.getMaxX());
    assertEquals(80.0d, bounds2D7.getMaxX());
    assertEquals(80.0d, bounds2D17.getMaxX());
    assertEquals(80.0d, bounds2D22.getMaxX());
    assertEquals(80.0d, bounds8.getMaxX());
    assertEquals(80.0d, bounds12.getMaxX());
    assertEquals(80.0d, bounds10.getMaxX());
    assertEquals(80.0d, bounds13.getMaxX());
    assertEquals(80.0d, frame3.getMaxX());
    assertEquals(80.0d, frame.getMaxX());
    assertEquals(80.0d, frame2.getMaxX());
    assertEquals(80.0d, frame4.getMaxX());
    assertEquals(80.0d, frame5.getMaxX());
    assertEquals(80.0d, frame6.getMaxX());
    assertEquals(80.0d, frame7.getMaxX());
    assertEquals(80.0d, rectangle.getMaxY());
    assertEquals(80.0d, rectangle2.getMaxY());
    assertEquals(80.0d, bounds.getMaxY());
    assertEquals(80.0d, bounds2.getMaxY());
    assertEquals(80.0d, bounds3.getMaxY());
    assertEquals(80.0d, bounds4.getMaxY());
    assertEquals(80.0d, bounds5.getMaxY());
    assertEquals(80.0d, bounds6.getMaxY());
    assertEquals(80.0d, bounds7.getMaxY());
    assertEquals(80.0d, bounds9.getMaxY());
    assertEquals(80.0d, bounds11.getMaxY());
    assertEquals(80.0d, bounds2D.getMaxY());
    assertEquals(80.0d, bounds2D2.getMaxY());
    assertEquals(80.0d, bounds2D4.getMaxY());
    assertEquals(80.0d, bounds2D6.getMaxY());
    assertEquals(80.0d, bounds2D7.getMaxY());
    assertEquals(80.0d, bounds2D17.getMaxY());
    assertEquals(80.0d, bounds2D22.getMaxY());
    assertEquals(80.0d, bounds8.getMaxY());
    assertEquals(80.0d, bounds12.getMaxY());
    assertEquals(80.0d, bounds10.getMaxY());
    assertEquals(80.0d, bounds13.getMaxY());
    assertEquals(80.0d, frame3.getMaxY());
    assertEquals(80.0d, frame.getMaxY());
    assertEquals(80.0d, frame2.getMaxY());
    assertEquals(80.0d, frame4.getMaxY());
    assertEquals(80.0d, frame5.getMaxY());
    assertEquals(80.0d, frame6.getMaxY());
    assertEquals(80.0d, frame7.getMaxY());
    assertEquals(80.0d, bounds2D.getWidth());
    assertEquals(80.0d, bounds2D2.getWidth());
    assertEquals(80.0d, bounds2D4.getWidth());
    assertEquals(80.0d, bounds2D6.getWidth());
    assertEquals(80.0d, bounds2D7.getWidth());
    assertEquals(80.0d, bounds2D17.getWidth());
    assertEquals(80.0d, bounds2D22.getWidth());
    assertEquals(80.0d, frame3.getWidth());
    assertEquals(80.0d, frame.getWidth());
    assertEquals(80.0d, frame2.getWidth());
    assertEquals(80.0d, frame4.getWidth());
    assertEquals(80.0d, frame5.getWidth());
    assertEquals(80.0d, frame6.getWidth());
    assertEquals(80.0d, frame7.getWidth());
    assertEquals(80.0d, getResult3.getHeight());
    assertEquals(80.0d, getResult3.getWidth());
    assertSame(element2, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute(); given Process (default constructor) addFlowElement BoundaryEvent (default constructor); then throw RuntimeException")
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
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is
   * {@link BpmnModel} (default constructor) {@link BpmnAutoLayout#bpmnModel}
   * Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute(); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) bpmnModel Resources List")
  void testExecute_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelBpmnModelResourcesList() {
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
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BpmnAutoLayout#execute()}.
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is
   * {@link BpmnModel} (default constructor) Graph Model Cells {@code 2}
   * {@link mxCell}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#execute()}
   */
  @Test
  @DisplayName("Test execute(); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) Graph Model Cells '2' mxCell")
  void testExecute_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelGraphModelCells2MxCell() {
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
    mxGraph graph = bpmnAutoLayout.getGraph();
    mxIGraphModel model = graph.getModel();
    Map<String, Object> cells = ((mxGraphModel) model).getCells();
    assertEquals(4, cells.size());
    Object getResult = cells.get("2");
    assertTrue(getResult instanceof mxCell);
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult2 = stringObjectMap.get(null);
    assertTrue(getResult2 instanceof mxCell);
    assertTrue(model instanceof mxGraphModel);
    mxGeometry geometry = ((mxCell) getResult2).getGeometry();
    Rectangle rectangle = geometry.getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    Rectangle bounds = rectangle.getBounds();
    Rectangle bounds2 = bounds.getBounds();
    Rectangle2D bounds2D2 = bounds2.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle rectangle2 = graph.getGraphBounds().getRectangle();
    Rectangle bounds3 = rectangle2.getBounds().getBounds().getBounds();
    Rectangle2D bounds2D3 = bounds3.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    Rectangle bounds4 = bounds3.getBounds();
    Rectangle bounds5 = bounds4.getBounds();
    Rectangle2D bounds2D4 = bounds5.getBounds2D();
    assertTrue(bounds2D4 instanceof Rectangle);
    Rectangle2D bounds2D5 = bounds2D.getBounds2D();
    assertTrue(bounds2D5 instanceof Rectangle);
    Rectangle2D bounds2D6 = bounds2D3.getBounds2D();
    assertTrue(bounds2D6 instanceof Rectangle);
    Rectangle2D frame = rectangle.getFrame();
    Rectangle2D bounds2D7 = frame.getBounds2D();
    assertTrue(bounds2D7 instanceof Rectangle2D.Double);
    assertTrue(frame instanceof Rectangle2D.Double);
    Rectangle2D frame2 = bounds2.getFrame();
    assertTrue(frame2 instanceof Rectangle2D.Double);
    Rectangle2D frame3 = bounds2D.getFrame();
    assertTrue(frame3 instanceof Rectangle2D.Double);
    Rectangle2D frame4 = frame.getFrame();
    assertTrue(frame4 instanceof Rectangle2D.Double);
    mxGeometry geometry2 = ((mxCell) getResult).getGeometry();
    assertNull(geometry2.getOffset());
    assertNull(geometry2.getSourcePoint());
    assertNull(geometry2.getTargetPoint());
    assertNull(geometry2.getAlternateBounds());
    assertNull(geometry2.getPoints());
    Rectangle rectangle3 = geometry2.getRectangle();
    assertEquals(0, rectangle3.x);
    assertEquals(0, rectangle3.y);
    assertEquals(0.0d, geometry2.getX());
    assertEquals(0.0d, geometry2.getY());
    Point location = geometry.getPoint().getLocation().getLocation();
    assertEquals(100, location.getLocation().y);
    Point location2 = rectangle.getLocation().getLocation();
    assertEquals(100, location2.getLocation().y);
    Point location3 = bounds.getLocation();
    assertEquals(100, location3.getLocation().y);
    assertEquals(100, bounds2.getLocation().y);
    assertEquals(100, ((Rectangle) bounds2D).getLocation().y);
    assertEquals(100, bounds2.getBounds().y);
    assertEquals(100, bounds2D.getBounds().y);
    assertEquals(100, frame.getBounds().y);
    assertEquals(100, ((Rectangle) bounds2D2).y);
    assertEquals(100, ((Rectangle) bounds2D5).y);
    assertEquals(100.0d, location.getY());
    assertEquals(100.0d, location2.getY());
    assertEquals(100.0d, location3.getY());
    assertEquals(100.0d, bounds2.getY());
    assertEquals(100.0d, bounds2.getMinY());
    assertEquals(100.0d, bounds2D.getMinY());
    assertEquals(100.0d, frame.getMinY());
    assertEquals(100.0d, bounds2D.getY());
    assertEquals(100.0d, frame.getY());
    assertEquals(120.0d, bounds2.getCenterY());
    assertEquals(120.0d, bounds2D.getCenterY());
    assertEquals(120.0d, frame.getCenterY());
    assertEquals(140, rectangle2.getSize().getSize().getSize().getSize().getSize().height);
    Dimension size = bounds4.getSize();
    assertEquals(140, size.getSize().height);
    assertEquals(140, bounds5.getSize().height);
    assertEquals(140, bounds5.getBounds().height);
    assertEquals(140, bounds2D3.getBounds().height);
    assertEquals(140, ((Rectangle) bounds2D4).height);
    assertEquals(140, ((Rectangle) bounds2D6).height);
    assertEquals(140.0d, size.getHeight());
    assertEquals(140.0d, bounds5.getHeight());
    assertEquals(140.0d, bounds2D3.getHeight());
    assertEquals(140.0d, bounds2.getMaxY());
    assertEquals(140.0d, bounds5.getMaxY());
    assertEquals(140.0d, bounds2D.getMaxY());
    assertEquals(140.0d, frame.getMaxY());
    assertEquals(20.0d, geometry2.getCenterX());
    assertEquals(20.0d, geometry2.getCenterY());
    assertEquals(40, rectangle3.height);
    assertEquals(40, rectangle3.width);
    assertEquals(40.0d, geometry2.getHeight());
    assertEquals(40.0d, geometry2.getWidth());
    assertEquals(70.0d, bounds5.getCenterY());
    assertEquals(70.0d, bounds2D3.getCenterY());
    assertFalse(geometry2.isRelative());
    assertTrue(cells.containsKey("1"));
    assertTrue(cells.containsKey("3"));
    assertEquals(rectangle, bounds2D7);
    assertEquals(rectangle, frame2);
    assertEquals(rectangle, frame3);
    assertEquals(rectangle, frame4);
    Dimension size2 = rectangle.getSize();
    assertEquals(size2, size2.getSize().getSize());
    assertEquals(size2, bounds.getSize().getSize());
    assertEquals(size2, bounds2.getSize());
    assertEquals(size2, ((Rectangle) bounds2D).getSize());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
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
    Rectangle bounds2 = bounds2D.getBounds();
    Rectangle2D frame = bounds2.getFrame();
    assertTrue(frame instanceof Rectangle2D.Double);
    Collection<FlowElement> flowElements = flowElementsContainer.getFlowElements();
    assertTrue(flowElements instanceof List);
    Dimension size = bounds2.getSize().getSize();
    assertEquals(0.0d, size.getHeight());
    assertEquals(0.0d, size.getWidth());
    Point location = bounds2.getLocation().getLocation();
    assertEquals(0.0d, location.getX());
    assertEquals(0.0d, location.getY());
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
    Rectangle bounds = bpmnAutoLayout.getGraph().getGraphBounds().getRectangle().getBounds().getBounds();
    Rectangle2D bounds2D2 = bounds.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle bounds2 = bounds.getBounds();
    Rectangle2D bounds2D3 = bounds2.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    Rectangle bounds3 = bounds2.getBounds();
    Rectangle bounds4 = bounds3.getBounds();
    Rectangle2D bounds2D4 = bounds4.getBounds2D();
    assertTrue(bounds2D4 instanceof Rectangle);
    Rectangle bounds5 = bounds2D2.getBounds();
    Rectangle2D bounds2D5 = bounds5.getBounds2D();
    assertTrue(bounds2D5 instanceof Rectangle);
    Rectangle2D frame = bounds.getFrame();
    Rectangle bounds6 = frame.getBounds();
    Rectangle2D bounds2D6 = bounds6.getBounds2D();
    assertTrue(bounds2D6 instanceof Rectangle);
    Rectangle2D bounds2D7 = bounds2D3.getBounds2D();
    assertTrue(bounds2D7 instanceof Rectangle);
    Rectangle2D frame2 = rectangle.getFrame();
    assertTrue(frame2 instanceof Rectangle2D.Double);
    assertTrue(frame instanceof Rectangle2D.Double);
    Rectangle2D frame3 = bounds2.getFrame();
    assertTrue(frame3 instanceof Rectangle2D.Double);
    assertEquals(20.0d, bounds2D3.getCenterX());
    assertEquals(20.0d, bounds5.getCenterX());
    assertEquals(20.0d, bounds6.getCenterX());
    assertEquals(20.0d, frame3.getCenterX());
    Rectangle bounds7 = rectangle.getBounds().getBounds();
    assertEquals(20.0d, bounds7.getCenterY());
    assertEquals(20.0d, bounds4.getCenterY());
    assertEquals(20.0d, bounds2D.getCenterY());
    assertEquals(20.0d, bounds2D3.getCenterY());
    assertEquals(20.0d, bounds5.getCenterY());
    assertEquals(20.0d, bounds6.getCenterY());
    assertEquals(20.0d, frame2.getCenterY());
    assertEquals(20.0d, frame3.getCenterY());
    Dimension size = bounds2.getSize().getSize();
    Dimension size2 = size.getSize();
    assertEquals(40, size2.height);
    Dimension size3 = bounds3.getSize();
    Dimension size4 = size3.getSize();
    assertEquals(40, size4.height);
    Dimension size5 = ((Rectangle) bounds2D2).getSize();
    Dimension size6 = size5.getSize();
    assertEquals(40, size6.height);
    assertEquals(40, bounds4.getSize().height);
    Dimension size7 = bounds5.getSize();
    assertEquals(40, size7.height);
    Dimension size8 = ((Rectangle) bounds2D3).getSize();
    assertEquals(40, size8.height);
    assertEquals(40, size2.width);
    assertEquals(40, size4.width);
    assertEquals(40, size6.width);
    assertEquals(40, size7.width);
    assertEquals(40, size8.width);
    assertEquals(40, bounds4.getBounds().height);
    Rectangle bounds8 = bounds5.getBounds();
    assertEquals(40, bounds8.height);
    Rectangle bounds9 = bounds6.getBounds();
    assertEquals(40, bounds9.height);
    Rectangle bounds10 = bounds2D3.getBounds();
    assertEquals(40, bounds10.height);
    Rectangle bounds11 = frame3.getBounds();
    assertEquals(40, bounds11.height);
    assertEquals(40, ((Rectangle) bounds2D4).height);
    assertEquals(40, ((Rectangle) bounds2D5).height);
    assertEquals(40, ((Rectangle) bounds2D6).height);
    assertEquals(40, ((Rectangle) bounds2D7).height);
    assertEquals(40, bounds8.width);
    assertEquals(40, bounds9.width);
    assertEquals(40, bounds10.width);
    assertEquals(40, bounds11.width);
    assertEquals(40, ((Rectangle) bounds2D5).width);
    assertEquals(40, ((Rectangle) bounds2D6).width);
    assertEquals(40, ((Rectangle) bounds2D7).width);
    assertEquals(40.0d, size.getHeight());
    assertEquals(40.0d, size3.getHeight());
    assertEquals(40.0d, size5.getHeight());
    assertEquals(40.0d, size.getWidth());
    assertEquals(40.0d, size3.getWidth());
    assertEquals(40.0d, size5.getWidth());
    assertEquals(40.0d, bounds4.getHeight());
    assertEquals(40.0d, bounds5.getHeight());
    assertEquals(40.0d, bounds6.getHeight());
    assertEquals(40.0d, bounds5.getWidth());
    assertEquals(40.0d, bounds2D3.getHeight());
    assertEquals(40.0d, frame3.getHeight());
    assertEquals(40.0d, bounds2D3.getMaxX());
    assertEquals(40.0d, bounds5.getMaxX());
    assertEquals(40.0d, bounds6.getMaxX());
    assertEquals(40.0d, frame3.getMaxX());
    assertEquals(40.0d, bounds7.getMaxY());
    assertEquals(40.0d, bounds4.getMaxY());
    assertEquals(40.0d, bounds2D.getMaxY());
    assertEquals(40.0d, bounds2D3.getMaxY());
    assertEquals(40.0d, bounds5.getMaxY());
    assertEquals(40.0d, bounds6.getMaxY());
    assertEquals(40.0d, frame2.getMaxY());
    assertEquals(40.0d, frame3.getMaxY());
    assertEquals(40.0d, bounds2D3.getWidth());
    assertEquals(40.0d, frame3.getWidth());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  void testLayout3() {
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
    Rectangle bounds = bpmnAutoLayout.getGraph().getGraphBounds().getRectangle().getBounds();
    Rectangle2D bounds2D = bounds.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    Rectangle bounds2 = bounds2D.getBounds().getBounds();
    Rectangle2D bounds2D2 = bounds2.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle bounds3 = bounds.getBounds();
    Rectangle2D frame = bounds3.getFrame();
    assertTrue(frame instanceof Rectangle2D.Double);
    assertEquals("styleGateway", ((mxCell) getResult).getStyle());
    Map<String, GraphicInfo> locationMap = bpmnAutoLayout.bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertEquals(20.0d, bounds2.getCenterX());
    assertEquals(20.0d, bounds2.getCenterY());
    Dimension size = bounds3.getSize().getSize().getSize();
    Dimension size2 = size.getSize();
    assertEquals(40, size2.height);
    Dimension size3 = bounds2.getSize();
    assertEquals(40, size3.height);
    Rectangle bounds4 = frame.getBounds();
    Dimension size4 = bounds4.getSize();
    assertEquals(40, size4.height);
    assertEquals(40, size2.width);
    assertEquals(40, size3.width);
    assertEquals(40, size4.width);
    Rectangle bounds5 = bounds2.getBounds();
    assertEquals(40, bounds5.height);
    assertEquals(40, ((Rectangle) bounds2D2).height);
    assertEquals(40, bounds5.width);
    assertEquals(40, ((Rectangle) bounds2D2).width);
    assertEquals(40.0d, size.getHeight());
    assertEquals(40.0d, size.getWidth());
    assertEquals(40.0d, bounds2.getHeight());
    assertEquals(40.0d, bounds2.getWidth());
    assertEquals(40.0d, bounds4.getWidth());
    assertEquals(40.0d, bounds2.getMaxX());
    assertEquals(40.0d, bounds2.getMaxY());
    assertFalse(bounds4.isEmpty());
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
  void testLayout4() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    BusinessRuleTask element = new BusinessRuleTask();
    flowElementsContainer.addFlowElement(element);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult = stringObjectMap.get(null);
    assertTrue(getResult instanceof mxCell);
    mxGeometry geometry = ((mxCell) getResult).getGeometry();
    Rectangle rectangle = geometry.getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    mxRectangle graphBounds = bpmnAutoLayout.getGraph().getGraphBounds();
    Rectangle rectangle2 = graphBounds.getRectangle();
    Rectangle2D bounds2D2 = rectangle2.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle bounds = rectangle.getBounds();
    Rectangle2D bounds2D3 = bounds.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    Rectangle bounds2 = rectangle2.getBounds();
    Rectangle2D bounds2D4 = bounds2.getBounds2D();
    assertTrue(bounds2D4 instanceof Rectangle);
    Rectangle bounds3 = bounds.getBounds();
    Rectangle2D bounds2D5 = bounds3.getBounds2D();
    assertTrue(bounds2D5 instanceof Rectangle);
    Rectangle bounds4 = bounds2.getBounds();
    Rectangle2D bounds2D6 = bounds4.getBounds2D();
    assertTrue(bounds2D6 instanceof Rectangle);
    Rectangle bounds5 = bounds4.getBounds();
    Rectangle2D bounds2D7 = bounds5.getBounds2D();
    assertTrue(bounds2D7 instanceof Rectangle);
    Rectangle bounds6 = bounds5.getBounds();
    Rectangle2D bounds2D8 = bounds6.getBounds2D();
    assertTrue(bounds2D8 instanceof Rectangle);
    Rectangle bounds7 = bounds6.getBounds();
    Rectangle2D bounds2D9 = bounds7.getBounds2D();
    assertTrue(bounds2D9 instanceof Rectangle);
    Rectangle bounds8 = bounds2D2.getBounds();
    Rectangle bounds9 = bounds8.getBounds();
    Rectangle2D bounds2D10 = bounds9.getBounds2D();
    assertTrue(bounds2D10 instanceof Rectangle);
    Rectangle2D frame = rectangle2.getFrame();
    Rectangle bounds10 = frame.getBounds();
    Rectangle bounds11 = bounds10.getBounds();
    Rectangle2D bounds2D11 = bounds11.getBounds2D();
    assertTrue(bounds2D11 instanceof Rectangle);
    Rectangle2D bounds2D12 = bounds8.getBounds2D();
    assertTrue(bounds2D12 instanceof Rectangle);
    Rectangle bounds12 = bounds2D4.getBounds();
    Rectangle2D bounds2D13 = bounds12.getBounds2D();
    assertTrue(bounds2D13 instanceof Rectangle);
    Rectangle bounds13 = bounds2D6.getBounds();
    Rectangle2D bounds2D14 = bounds13.getBounds2D();
    assertTrue(bounds2D14 instanceof Rectangle);
    Rectangle2D bounds2D15 = bounds10.getBounds2D();
    assertTrue(bounds2D15 instanceof Rectangle);
    Rectangle2D frame2 = bounds2.getFrame();
    Rectangle bounds14 = frame2.getBounds();
    Rectangle2D bounds2D16 = bounds14.getBounds2D();
    assertTrue(bounds2D16 instanceof Rectangle);
    Rectangle2D frame3 = bounds4.getFrame();
    Rectangle bounds15 = frame3.getBounds();
    Rectangle2D bounds2D17 = bounds15.getBounds2D();
    assertTrue(bounds2D17 instanceof Rectangle);
    Rectangle2D bounds2D18 = bounds2D.getBounds2D();
    assertTrue(bounds2D18 instanceof Rectangle);
    Rectangle2D bounds2D19 = bounds2D2.getBounds2D();
    assertTrue(bounds2D19 instanceof Rectangle);
    Rectangle2D bounds2D20 = bounds2D4.getBounds2D();
    assertTrue(bounds2D20 instanceof Rectangle);
    Rectangle2D bounds2D21 = bounds2D6.getBounds2D();
    assertTrue(bounds2D21 instanceof Rectangle);
    Rectangle2D bounds2D22 = bounds2D7.getBounds2D();
    assertTrue(bounds2D22 instanceof Rectangle);
    Rectangle2D bounds2D23 = bounds2D19.getBounds2D();
    assertTrue(bounds2D23 instanceof Rectangle);
    Rectangle2D bounds2D24 = frame.getBounds2D();
    assertTrue(bounds2D24 instanceof Rectangle2D.Double);
    Rectangle2D frame4 = rectangle.getFrame();
    assertTrue(frame4 instanceof Rectangle2D.Double);
    assertTrue(frame instanceof Rectangle2D.Double);
    assertTrue(frame2 instanceof Rectangle2D.Double);
    assertTrue(frame3 instanceof Rectangle2D.Double);
    Rectangle2D frame5 = bounds5.getFrame();
    assertTrue(frame5 instanceof Rectangle2D.Double);
    Rectangle2D frame6 = bounds2D2.getFrame();
    assertTrue(frame6 instanceof Rectangle2D.Double);
    Rectangle2D frame7 = frame.getFrame();
    assertTrue(frame7 instanceof Rectangle2D.Double);
    Map<String, GraphicInfo> locationMap = bpmnAutoLayout.bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    Dimension size = rectangle2.getSize();
    Dimension size2 = size.getSize();
    Dimension size3 = size2.getSize();
    Dimension size4 = size3.getSize();
    Dimension size5 = size4.getSize();
    assertEquals(100, size5.width);
    assertEquals(100, size4.width);
    Dimension size6 = bounds2.getSize();
    Dimension size7 = size6.getSize();
    Dimension size8 = size7.getSize();
    Dimension size9 = size8.getSize();
    assertEquals(100, size9.width);
    Dimension size10 = rectangle.getSize();
    Dimension size11 = size10.getSize();
    Dimension size12 = size11.getSize();
    assertEquals(100, size12.width);
    assertEquals(100, size3.width);
    assertEquals(100, size8.width);
    Dimension size13 = bounds4.getSize();
    Dimension size14 = size13.getSize();
    Dimension size15 = size14.getSize();
    assertEquals(100, size15.width);
    Dimension size16 = bounds5.getSize();
    Dimension size17 = size16.getSize();
    Dimension size18 = size17.getSize();
    assertEquals(100, size18.width);
    Dimension size19 = ((Rectangle) bounds2D2).getSize();
    Dimension size20 = size19.getSize();
    Dimension size21 = size20.getSize();
    assertEquals(100, size21.width);
    assertEquals(100, size11.width);
    assertEquals(100, size2.width);
    Dimension size22 = bounds.getSize();
    Dimension size23 = size22.getSize();
    assertEquals(100, size23.width);
    assertEquals(100, size7.width);
    assertEquals(100, size14.width);
    assertEquals(100, size17.width);
    Dimension size24 = bounds6.getSize();
    Dimension size25 = size24.getSize();
    assertEquals(100, size25.width);
    Dimension size26 = bounds8.getSize();
    Dimension size27 = size26.getSize();
    assertEquals(100, size27.width);
    Dimension size28 = bounds10.getSize();
    Dimension size29 = size28.getSize();
    assertEquals(100, size29.width);
    assertEquals(100, size20.width);
    Dimension size30 = ((Rectangle) bounds2D4).getSize();
    Dimension size31 = size30.getSize();
    assertEquals(100, size31.width);
    Dimension size32 = ((Rectangle) bounds2D6).getSize();
    Dimension size33 = size32.getSize();
    assertEquals(100, size33.width);
    assertEquals(100, size10.width);
    assertEquals(100, size.width);
    assertEquals(100, size22.width);
    assertEquals(100, size6.width);
    Dimension size34 = bounds3.getSize();
    assertEquals(100, size34.width);
    assertEquals(100, size13.width);
    assertEquals(100, size16.width);
    assertEquals(100, size24.width);
    Dimension size35 = bounds7.getSize();
    assertEquals(100, size35.width);
    Dimension size36 = bounds9.getSize();
    assertEquals(100, size36.width);
    Dimension size37 = bounds11.getSize();
    assertEquals(100, size37.width);
    assertEquals(100, size26.width);
    Dimension size38 = bounds12.getSize();
    assertEquals(100, size38.width);
    Dimension size39 = bounds13.getSize();
    assertEquals(100, size39.width);
    assertEquals(100, size28.width);
    Dimension size40 = bounds14.getSize();
    assertEquals(100, size40.width);
    Dimension size41 = bounds15.getSize();
    assertEquals(100, size41.width);
    Dimension size42 = ((Rectangle) bounds2D).getSize();
    assertEquals(100, size42.width);
    assertEquals(100, size19.width);
    assertEquals(100, size30.width);
    assertEquals(100, size32.width);
    Dimension size43 = ((Rectangle) bounds2D7).getSize();
    assertEquals(100, size43.width);
    Dimension size44 = ((Rectangle) bounds2D19).getSize();
    assertEquals(100, size44.width);
    assertEquals(100, rectangle.width);
    assertEquals(100, rectangle2.width);
    assertEquals(100, bounds.width);
    assertEquals(100, bounds2.width);
    assertEquals(100, bounds3.width);
    assertEquals(100, bounds4.width);
    Rectangle bounds16 = bounds3.getBounds();
    assertEquals(100, bounds16.width);
    assertEquals(100, bounds5.width);
    assertEquals(100, bounds6.width);
    assertEquals(100, bounds7.width);
    Rectangle bounds17 = bounds7.getBounds();
    assertEquals(100, bounds17.width);
    Rectangle bounds18 = bounds9.getBounds();
    assertEquals(100, bounds18.width);
    Rectangle bounds19 = bounds11.getBounds();
    assertEquals(100, bounds19.width);
    assertEquals(100, bounds9.width);
    Rectangle bounds20 = bounds12.getBounds();
    assertEquals(100, bounds20.width);
    Rectangle bounds21 = bounds13.getBounds();
    assertEquals(100, bounds21.width);
    assertEquals(100, bounds11.width);
    Rectangle bounds22 = bounds14.getBounds();
    assertEquals(100, bounds22.width);
    Rectangle bounds23 = bounds15.getBounds();
    assertEquals(100, bounds23.width);
    Rectangle bounds24 = bounds2D.getBounds();
    assertEquals(100, bounds24.width);
    assertEquals(100, bounds8.width);
    assertEquals(100, bounds12.width);
    assertEquals(100, bounds13.width);
    Rectangle bounds25 = bounds2D7.getBounds();
    assertEquals(100, bounds25.width);
    Rectangle bounds26 = bounds2D19.getBounds();
    assertEquals(100, bounds26.width);
    Rectangle bounds27 = bounds2D24.getBounds();
    assertEquals(100, bounds27.width);
    Rectangle bounds28 = frame4.getBounds();
    assertEquals(100, bounds28.width);
    assertEquals(100, bounds10.width);
    assertEquals(100, bounds14.width);
    assertEquals(100, bounds15.width);
    Rectangle bounds29 = frame5.getBounds();
    assertEquals(100, bounds29.width);
    Rectangle bounds30 = frame6.getBounds();
    assertEquals(100, bounds30.width);
    Rectangle bounds31 = frame7.getBounds();
    assertEquals(100, bounds31.width);
    assertEquals(100, ((Rectangle) bounds2D).width);
    assertEquals(100, ((Rectangle) bounds2D2).width);
    assertEquals(100, ((Rectangle) bounds2D3).width);
    assertEquals(100, ((Rectangle) bounds2D4).width);
    assertEquals(100, ((Rectangle) bounds2D5).width);
    assertEquals(100, ((Rectangle) bounds2D6).width);
    assertEquals(100, ((Rectangle) bounds2D7).width);
    assertEquals(100, ((Rectangle) bounds2D8).width);
    assertEquals(100, ((Rectangle) bounds2D9).width);
    assertEquals(100, ((Rectangle) bounds2D10).width);
    assertEquals(100, ((Rectangle) bounds2D11).width);
    assertEquals(100, ((Rectangle) bounds2D12).width);
    assertEquals(100, ((Rectangle) bounds2D13).width);
    assertEquals(100, ((Rectangle) bounds2D14).width);
    assertEquals(100, ((Rectangle) bounds2D15).width);
    assertEquals(100, ((Rectangle) bounds2D16).width);
    assertEquals(100, ((Rectangle) bounds2D17).width);
    assertEquals(100, ((Rectangle) bounds2D18).width);
    assertEquals(100, ((Rectangle) bounds2D19).width);
    assertEquals(100, ((Rectangle) bounds2D20).width);
    assertEquals(100, ((Rectangle) bounds2D21).width);
    assertEquals(100, ((Rectangle) bounds2D22).width);
    assertEquals(100, ((Rectangle) bounds2D23).width);
    assertEquals(100.0d, geometry.getWidth());
    assertEquals(100.0d, graphBounds.getWidth());
    assertEquals(100.0d, size4.getWidth());
    assertEquals(100.0d, size3.getWidth());
    assertEquals(100.0d, size8.getWidth());
    assertEquals(100.0d, size11.getWidth());
    assertEquals(100.0d, size2.getWidth());
    assertEquals(100.0d, size7.getWidth());
    assertEquals(100.0d, size14.getWidth());
    assertEquals(100.0d, size17.getWidth());
    assertEquals(100.0d, size20.getWidth());
    assertEquals(100.0d, size10.getWidth());
    assertEquals(100.0d, size.getWidth());
    assertEquals(100.0d, size22.getWidth());
    assertEquals(100.0d, size6.getWidth());
    assertEquals(100.0d, size13.getWidth());
    assertEquals(100.0d, size16.getWidth());
    assertEquals(100.0d, size24.getWidth());
    assertEquals(100.0d, size26.getWidth());
    assertEquals(100.0d, size28.getWidth());
    assertEquals(100.0d, size19.getWidth());
    assertEquals(100.0d, size30.getWidth());
    assertEquals(100.0d, size32.getWidth());
    assertEquals(100.0d, rectangle.getWidth());
    assertEquals(100.0d, rectangle2.getWidth());
    assertEquals(100.0d, bounds.getWidth());
    assertEquals(100.0d, bounds2.getWidth());
    assertEquals(100.0d, bounds3.getWidth());
    assertEquals(100.0d, bounds4.getWidth());
    assertEquals(100.0d, bounds5.getWidth());
    assertEquals(100.0d, bounds6.getWidth());
    assertEquals(100.0d, bounds7.getWidth());
    assertEquals(100.0d, bounds9.getWidth());
    assertEquals(100.0d, bounds11.getWidth());
    assertEquals(100.0d, bounds8.getWidth());
    assertEquals(100.0d, bounds12.getWidth());
    assertEquals(100.0d, bounds13.getWidth());
    assertEquals(100.0d, bounds10.getWidth());
    assertEquals(100.0d, bounds14.getWidth());
    assertEquals(100.0d, bounds15.getWidth());
    assertEquals(100.0d, rectangle.getMaxX());
    assertEquals(100.0d, rectangle2.getMaxX());
    assertEquals(100.0d, bounds.getMaxX());
    assertEquals(100.0d, bounds2.getMaxX());
    assertEquals(100.0d, bounds3.getMaxX());
    assertEquals(100.0d, bounds4.getMaxX());
    assertEquals(100.0d, bounds5.getMaxX());
    assertEquals(100.0d, bounds6.getMaxX());
    assertEquals(100.0d, bounds7.getMaxX());
    assertEquals(100.0d, bounds9.getMaxX());
    assertEquals(100.0d, bounds11.getMaxX());
    assertEquals(100.0d, bounds2D.getMaxX());
    assertEquals(100.0d, bounds2D2.getMaxX());
    assertEquals(100.0d, bounds2D4.getMaxX());
    assertEquals(100.0d, bounds2D6.getMaxX());
    assertEquals(100.0d, bounds2D7.getMaxX());
    assertEquals(100.0d, bounds2D19.getMaxX());
    assertEquals(100.0d, bounds2D24.getMaxX());
    assertEquals(100.0d, bounds8.getMaxX());
    assertEquals(100.0d, bounds12.getMaxX());
    assertEquals(100.0d, bounds13.getMaxX());
    assertEquals(100.0d, bounds10.getMaxX());
    assertEquals(100.0d, bounds14.getMaxX());
    assertEquals(100.0d, bounds15.getMaxX());
    assertEquals(100.0d, frame4.getMaxX());
    assertEquals(100.0d, frame.getMaxX());
    assertEquals(100.0d, frame2.getMaxX());
    assertEquals(100.0d, frame3.getMaxX());
    assertEquals(100.0d, frame5.getMaxX());
    assertEquals(100.0d, frame6.getMaxX());
    assertEquals(100.0d, frame7.getMaxX());
    assertEquals(100.0d, bounds2D.getWidth());
    assertEquals(100.0d, bounds2D2.getWidth());
    assertEquals(100.0d, bounds2D4.getWidth());
    assertEquals(100.0d, bounds2D6.getWidth());
    assertEquals(100.0d, bounds2D7.getWidth());
    assertEquals(100.0d, bounds2D19.getWidth());
    assertEquals(100.0d, bounds2D24.getWidth());
    assertEquals(100.0d, frame4.getWidth());
    assertEquals(100.0d, frame.getWidth());
    assertEquals(100.0d, frame2.getWidth());
    assertEquals(100.0d, frame3.getWidth());
    assertEquals(100.0d, frame5.getWidth());
    assertEquals(100.0d, frame6.getWidth());
    assertEquals(100.0d, frame7.getWidth());
    GraphicInfo getResult2 = locationMap.get(null);
    assertEquals(100.0d, getResult2.getWidth());
    assertEquals(30.0d, geometry.getCenterY());
    assertEquals(30.0d, graphBounds.getCenterY());
    assertEquals(30.0d, rectangle.getCenterY());
    assertEquals(30.0d, rectangle2.getCenterY());
    assertEquals(30.0d, bounds.getCenterY());
    assertEquals(30.0d, bounds2.getCenterY());
    assertEquals(30.0d, bounds3.getCenterY());
    assertEquals(30.0d, bounds4.getCenterY());
    assertEquals(30.0d, bounds5.getCenterY());
    assertEquals(30.0d, bounds6.getCenterY());
    assertEquals(30.0d, bounds7.getCenterY());
    assertEquals(30.0d, bounds9.getCenterY());
    assertEquals(30.0d, bounds11.getCenterY());
    assertEquals(30.0d, bounds2D.getCenterY());
    assertEquals(30.0d, bounds2D2.getCenterY());
    assertEquals(30.0d, bounds2D4.getCenterY());
    assertEquals(30.0d, bounds2D6.getCenterY());
    assertEquals(30.0d, bounds2D7.getCenterY());
    assertEquals(30.0d, bounds2D19.getCenterY());
    assertEquals(30.0d, bounds2D24.getCenterY());
    assertEquals(30.0d, bounds8.getCenterY());
    assertEquals(30.0d, bounds12.getCenterY());
    assertEquals(30.0d, bounds13.getCenterY());
    assertEquals(30.0d, bounds10.getCenterY());
    assertEquals(30.0d, bounds14.getCenterY());
    assertEquals(30.0d, bounds15.getCenterY());
    assertEquals(30.0d, frame4.getCenterY());
    assertEquals(30.0d, frame.getCenterY());
    assertEquals(30.0d, frame2.getCenterY());
    assertEquals(30.0d, frame3.getCenterY());
    assertEquals(30.0d, frame5.getCenterY());
    assertEquals(30.0d, frame6.getCenterY());
    assertEquals(30.0d, frame7.getCenterY());
    assertEquals(50.0d, geometry.getCenterX());
    assertEquals(50.0d, graphBounds.getCenterX());
    assertEquals(50.0d, rectangle.getCenterX());
    assertEquals(50.0d, rectangle2.getCenterX());
    assertEquals(50.0d, bounds.getCenterX());
    assertEquals(50.0d, bounds2.getCenterX());
    assertEquals(50.0d, bounds3.getCenterX());
    assertEquals(50.0d, bounds4.getCenterX());
    assertEquals(50.0d, bounds5.getCenterX());
    assertEquals(50.0d, bounds6.getCenterX());
    assertEquals(50.0d, bounds7.getCenterX());
    assertEquals(50.0d, bounds9.getCenterX());
    assertEquals(50.0d, bounds11.getCenterX());
    assertEquals(50.0d, bounds2D.getCenterX());
    assertEquals(50.0d, bounds2D2.getCenterX());
    assertEquals(50.0d, bounds2D4.getCenterX());
    assertEquals(50.0d, bounds2D6.getCenterX());
    assertEquals(50.0d, bounds2D7.getCenterX());
    assertEquals(50.0d, bounds2D19.getCenterX());
    assertEquals(50.0d, bounds2D24.getCenterX());
    assertEquals(50.0d, bounds8.getCenterX());
    assertEquals(50.0d, bounds12.getCenterX());
    assertEquals(50.0d, bounds13.getCenterX());
    assertEquals(50.0d, bounds10.getCenterX());
    assertEquals(50.0d, bounds14.getCenterX());
    assertEquals(50.0d, bounds15.getCenterX());
    assertEquals(50.0d, frame4.getCenterX());
    assertEquals(50.0d, frame.getCenterX());
    assertEquals(50.0d, frame2.getCenterX());
    assertEquals(50.0d, frame3.getCenterX());
    assertEquals(50.0d, frame5.getCenterX());
    assertEquals(50.0d, frame6.getCenterX());
    assertEquals(50.0d, frame7.getCenterX());
    assertEquals(60, size5.height);
    assertEquals(60, size4.height);
    assertEquals(60, size9.height);
    assertEquals(60, size12.height);
    assertEquals(60, size3.height);
    assertEquals(60, size8.height);
    assertEquals(60, size15.height);
    assertEquals(60, size18.height);
    assertEquals(60, size21.height);
    assertEquals(60, size11.height);
    assertEquals(60, size2.height);
    assertEquals(60, size23.height);
    assertEquals(60, size7.height);
    assertEquals(60, size14.height);
    assertEquals(60, size17.height);
    assertEquals(60, size25.height);
    assertEquals(60, size27.height);
    assertEquals(60, size29.height);
    assertEquals(60, size20.height);
    assertEquals(60, size31.height);
    assertEquals(60, size33.height);
    assertEquals(60, size10.height);
    assertEquals(60, size.height);
    assertEquals(60, size22.height);
    assertEquals(60, size6.height);
    assertEquals(60, size34.height);
    assertEquals(60, size13.height);
    assertEquals(60, size16.height);
    assertEquals(60, size24.height);
    assertEquals(60, size35.height);
    assertEquals(60, size36.height);
    assertEquals(60, size37.height);
    assertEquals(60, size26.height);
    assertEquals(60, size38.height);
    assertEquals(60, size39.height);
    assertEquals(60, size28.height);
    assertEquals(60, size40.height);
    assertEquals(60, size41.height);
    assertEquals(60, size42.height);
    assertEquals(60, size19.height);
    assertEquals(60, size30.height);
    assertEquals(60, size32.height);
    assertEquals(60, size43.height);
    assertEquals(60, size44.height);
    assertEquals(60, rectangle.height);
    assertEquals(60, rectangle2.height);
    assertEquals(60, bounds.height);
    assertEquals(60, bounds2.height);
    assertEquals(60, bounds3.height);
    assertEquals(60, bounds4.height);
    assertEquals(60, bounds16.height);
    assertEquals(60, bounds5.height);
    assertEquals(60, bounds6.height);
    assertEquals(60, bounds7.height);
    assertEquals(60, bounds17.height);
    assertEquals(60, bounds18.height);
    assertEquals(60, bounds19.height);
    assertEquals(60, bounds9.height);
    assertEquals(60, bounds20.height);
    assertEquals(60, bounds21.height);
    assertEquals(60, bounds11.height);
    assertEquals(60, bounds22.height);
    assertEquals(60, bounds23.height);
    assertEquals(60, bounds24.height);
    assertEquals(60, bounds8.height);
    assertEquals(60, bounds12.height);
    assertEquals(60, bounds13.height);
    assertEquals(60, bounds25.height);
    assertEquals(60, bounds26.height);
    assertEquals(60, bounds27.height);
    assertEquals(60, bounds28.height);
    assertEquals(60, bounds10.height);
    assertEquals(60, bounds14.height);
    assertEquals(60, bounds15.height);
    assertEquals(60, bounds29.height);
    assertEquals(60, bounds30.height);
    assertEquals(60, bounds31.height);
    assertEquals(60, ((Rectangle) bounds2D).height);
    assertEquals(60, ((Rectangle) bounds2D2).height);
    assertEquals(60, ((Rectangle) bounds2D3).height);
    assertEquals(60, ((Rectangle) bounds2D4).height);
    assertEquals(60, ((Rectangle) bounds2D5).height);
    assertEquals(60, ((Rectangle) bounds2D6).height);
    assertEquals(60, ((Rectangle) bounds2D7).height);
    assertEquals(60, ((Rectangle) bounds2D8).height);
    assertEquals(60, ((Rectangle) bounds2D9).height);
    assertEquals(60, ((Rectangle) bounds2D10).height);
    assertEquals(60, ((Rectangle) bounds2D11).height);
    assertEquals(60, ((Rectangle) bounds2D12).height);
    assertEquals(60, ((Rectangle) bounds2D13).height);
    assertEquals(60, ((Rectangle) bounds2D14).height);
    assertEquals(60, ((Rectangle) bounds2D15).height);
    assertEquals(60, ((Rectangle) bounds2D16).height);
    assertEquals(60, ((Rectangle) bounds2D17).height);
    assertEquals(60, ((Rectangle) bounds2D18).height);
    assertEquals(60, ((Rectangle) bounds2D19).height);
    assertEquals(60, ((Rectangle) bounds2D20).height);
    assertEquals(60, ((Rectangle) bounds2D21).height);
    assertEquals(60, ((Rectangle) bounds2D22).height);
    assertEquals(60, ((Rectangle) bounds2D23).height);
    assertEquals(60.0d, geometry.getHeight());
    assertEquals(60.0d, graphBounds.getHeight());
    assertEquals(60.0d, size4.getHeight());
    assertEquals(60.0d, size3.getHeight());
    assertEquals(60.0d, size8.getHeight());
    assertEquals(60.0d, size11.getHeight());
    assertEquals(60.0d, size2.getHeight());
    assertEquals(60.0d, size7.getHeight());
    assertEquals(60.0d, size14.getHeight());
    assertEquals(60.0d, size17.getHeight());
    assertEquals(60.0d, size20.getHeight());
    assertEquals(60.0d, size10.getHeight());
    assertEquals(60.0d, size.getHeight());
    assertEquals(60.0d, size22.getHeight());
    assertEquals(60.0d, size6.getHeight());
    assertEquals(60.0d, size13.getHeight());
    assertEquals(60.0d, size16.getHeight());
    assertEquals(60.0d, size24.getHeight());
    assertEquals(60.0d, size26.getHeight());
    assertEquals(60.0d, size28.getHeight());
    assertEquals(60.0d, size19.getHeight());
    assertEquals(60.0d, size30.getHeight());
    assertEquals(60.0d, size32.getHeight());
    assertEquals(60.0d, rectangle.getHeight());
    assertEquals(60.0d, rectangle2.getHeight());
    assertEquals(60.0d, bounds.getHeight());
    assertEquals(60.0d, bounds2.getHeight());
    assertEquals(60.0d, bounds3.getHeight());
    assertEquals(60.0d, bounds4.getHeight());
    assertEquals(60.0d, bounds5.getHeight());
    assertEquals(60.0d, bounds6.getHeight());
    assertEquals(60.0d, bounds7.getHeight());
    assertEquals(60.0d, bounds9.getHeight());
    assertEquals(60.0d, bounds11.getHeight());
    assertEquals(60.0d, bounds8.getHeight());
    assertEquals(60.0d, bounds12.getHeight());
    assertEquals(60.0d, bounds13.getHeight());
    assertEquals(60.0d, bounds10.getHeight());
    assertEquals(60.0d, bounds14.getHeight());
    assertEquals(60.0d, bounds15.getHeight());
    assertEquals(60.0d, bounds2D.getHeight());
    assertEquals(60.0d, bounds2D2.getHeight());
    assertEquals(60.0d, bounds2D4.getHeight());
    assertEquals(60.0d, bounds2D6.getHeight());
    assertEquals(60.0d, bounds2D7.getHeight());
    assertEquals(60.0d, bounds2D19.getHeight());
    assertEquals(60.0d, bounds2D24.getHeight());
    assertEquals(60.0d, frame4.getHeight());
    assertEquals(60.0d, frame.getHeight());
    assertEquals(60.0d, frame2.getHeight());
    assertEquals(60.0d, frame3.getHeight());
    assertEquals(60.0d, frame5.getHeight());
    assertEquals(60.0d, frame6.getHeight());
    assertEquals(60.0d, frame7.getHeight());
    assertEquals(60.0d, rectangle.getMaxY());
    assertEquals(60.0d, rectangle2.getMaxY());
    assertEquals(60.0d, bounds.getMaxY());
    assertEquals(60.0d, bounds2.getMaxY());
    assertEquals(60.0d, bounds3.getMaxY());
    assertEquals(60.0d, bounds4.getMaxY());
    assertEquals(60.0d, bounds5.getMaxY());
    assertEquals(60.0d, bounds6.getMaxY());
    assertEquals(60.0d, bounds7.getMaxY());
    assertEquals(60.0d, bounds9.getMaxY());
    assertEquals(60.0d, bounds11.getMaxY());
    assertEquals(60.0d, bounds2D.getMaxY());
    assertEquals(60.0d, bounds2D2.getMaxY());
    assertEquals(60.0d, bounds2D4.getMaxY());
    assertEquals(60.0d, bounds2D6.getMaxY());
    assertEquals(60.0d, bounds2D7.getMaxY());
    assertEquals(60.0d, bounds2D19.getMaxY());
    assertEquals(60.0d, bounds2D24.getMaxY());
    assertEquals(60.0d, bounds8.getMaxY());
    assertEquals(60.0d, bounds12.getMaxY());
    assertEquals(60.0d, bounds13.getMaxY());
    assertEquals(60.0d, bounds10.getMaxY());
    assertEquals(60.0d, bounds14.getMaxY());
    assertEquals(60.0d, bounds15.getMaxY());
    assertEquals(60.0d, frame4.getMaxY());
    assertEquals(60.0d, frame.getMaxY());
    assertEquals(60.0d, frame2.getMaxY());
    assertEquals(60.0d, frame3.getMaxY());
    assertEquals(60.0d, frame5.getMaxY());
    assertEquals(60.0d, frame6.getMaxY());
    assertEquals(60.0d, frame7.getMaxY());
    assertEquals(60.0d, getResult2.getHeight());
    assertSame(element, stringFlowElementMap.get(null));
    assertSame(element, getResult2.getElement());
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer)")
  void testLayout5() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    CallActivity element = new CallActivity();
    flowElementsContainer.addFlowElement(element);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult = stringObjectMap.get(null);
    assertTrue(getResult instanceof mxCell);
    mxGeometry geometry = ((mxCell) getResult).getGeometry();
    Rectangle rectangle = geometry.getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    mxRectangle graphBounds = bpmnAutoLayout.getGraph().getGraphBounds();
    Rectangle rectangle2 = graphBounds.getRectangle();
    Rectangle2D bounds2D2 = rectangle2.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle bounds = rectangle.getBounds();
    Rectangle2D bounds2D3 = bounds.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    Rectangle bounds2 = rectangle2.getBounds();
    Rectangle2D bounds2D4 = bounds2.getBounds2D();
    assertTrue(bounds2D4 instanceof Rectangle);
    Rectangle bounds3 = bounds.getBounds();
    Rectangle2D bounds2D5 = bounds3.getBounds2D();
    assertTrue(bounds2D5 instanceof Rectangle);
    Rectangle bounds4 = bounds2.getBounds();
    Rectangle2D bounds2D6 = bounds4.getBounds2D();
    assertTrue(bounds2D6 instanceof Rectangle);
    Rectangle bounds5 = bounds4.getBounds();
    Rectangle2D bounds2D7 = bounds5.getBounds2D();
    assertTrue(bounds2D7 instanceof Rectangle);
    Rectangle bounds6 = bounds5.getBounds();
    Rectangle2D bounds2D8 = bounds6.getBounds2D();
    assertTrue(bounds2D8 instanceof Rectangle);
    Rectangle bounds7 = bounds6.getBounds();
    Rectangle2D bounds2D9 = bounds7.getBounds2D();
    assertTrue(bounds2D9 instanceof Rectangle);
    Rectangle bounds8 = bounds2D2.getBounds();
    Rectangle bounds9 = bounds8.getBounds();
    Rectangle2D bounds2D10 = bounds9.getBounds2D();
    assertTrue(bounds2D10 instanceof Rectangle);
    Rectangle2D frame = rectangle2.getFrame();
    Rectangle bounds10 = frame.getBounds();
    Rectangle bounds11 = bounds10.getBounds();
    Rectangle2D bounds2D11 = bounds11.getBounds2D();
    assertTrue(bounds2D11 instanceof Rectangle);
    Rectangle2D bounds2D12 = bounds8.getBounds2D();
    assertTrue(bounds2D12 instanceof Rectangle);
    Rectangle bounds12 = bounds2D4.getBounds();
    Rectangle2D bounds2D13 = bounds12.getBounds2D();
    assertTrue(bounds2D13 instanceof Rectangle);
    Rectangle bounds13 = bounds2D6.getBounds();
    Rectangle2D bounds2D14 = bounds13.getBounds2D();
    assertTrue(bounds2D14 instanceof Rectangle);
    Rectangle2D bounds2D15 = bounds10.getBounds2D();
    assertTrue(bounds2D15 instanceof Rectangle);
    Rectangle2D frame2 = bounds2.getFrame();
    Rectangle bounds14 = frame2.getBounds();
    Rectangle2D bounds2D16 = bounds14.getBounds2D();
    assertTrue(bounds2D16 instanceof Rectangle);
    Rectangle2D frame3 = bounds4.getFrame();
    Rectangle bounds15 = frame3.getBounds();
    Rectangle2D bounds2D17 = bounds15.getBounds2D();
    assertTrue(bounds2D17 instanceof Rectangle);
    Rectangle2D bounds2D18 = bounds2D.getBounds2D();
    assertTrue(bounds2D18 instanceof Rectangle);
    Rectangle2D bounds2D19 = bounds2D2.getBounds2D();
    assertTrue(bounds2D19 instanceof Rectangle);
    Rectangle2D bounds2D20 = bounds2D4.getBounds2D();
    assertTrue(bounds2D20 instanceof Rectangle);
    Rectangle2D bounds2D21 = bounds2D6.getBounds2D();
    assertTrue(bounds2D21 instanceof Rectangle);
    Rectangle2D bounds2D22 = bounds2D7.getBounds2D();
    assertTrue(bounds2D22 instanceof Rectangle);
    Rectangle2D bounds2D23 = bounds2D19.getBounds2D();
    assertTrue(bounds2D23 instanceof Rectangle);
    Rectangle2D bounds2D24 = frame.getBounds2D();
    assertTrue(bounds2D24 instanceof Rectangle2D.Double);
    Rectangle2D frame4 = rectangle.getFrame();
    assertTrue(frame4 instanceof Rectangle2D.Double);
    assertTrue(frame instanceof Rectangle2D.Double);
    assertTrue(frame2 instanceof Rectangle2D.Double);
    assertTrue(frame3 instanceof Rectangle2D.Double);
    Rectangle2D frame5 = bounds5.getFrame();
    assertTrue(frame5 instanceof Rectangle2D.Double);
    Rectangle2D frame6 = bounds2D2.getFrame();
    assertTrue(frame6 instanceof Rectangle2D.Double);
    Rectangle2D frame7 = frame.getFrame();
    assertTrue(frame7 instanceof Rectangle2D.Double);
    Map<String, GraphicInfo> locationMap = bpmnAutoLayout.bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    Dimension size = rectangle2.getSize();
    Dimension size2 = size.getSize();
    Dimension size3 = size2.getSize();
    Dimension size4 = size3.getSize();
    Dimension size5 = size4.getSize();
    assertEquals(100, size5.width);
    assertEquals(100, size4.width);
    Dimension size6 = bounds2.getSize();
    Dimension size7 = size6.getSize();
    Dimension size8 = size7.getSize();
    Dimension size9 = size8.getSize();
    assertEquals(100, size9.width);
    Dimension size10 = rectangle.getSize();
    Dimension size11 = size10.getSize();
    Dimension size12 = size11.getSize();
    assertEquals(100, size12.width);
    assertEquals(100, size3.width);
    assertEquals(100, size8.width);
    Dimension size13 = bounds4.getSize();
    Dimension size14 = size13.getSize();
    Dimension size15 = size14.getSize();
    assertEquals(100, size15.width);
    Dimension size16 = bounds5.getSize();
    Dimension size17 = size16.getSize();
    Dimension size18 = size17.getSize();
    assertEquals(100, size18.width);
    Dimension size19 = ((Rectangle) bounds2D2).getSize();
    Dimension size20 = size19.getSize();
    Dimension size21 = size20.getSize();
    assertEquals(100, size21.width);
    assertEquals(100, size11.width);
    assertEquals(100, size2.width);
    Dimension size22 = bounds.getSize();
    Dimension size23 = size22.getSize();
    assertEquals(100, size23.width);
    assertEquals(100, size7.width);
    assertEquals(100, size14.width);
    assertEquals(100, size17.width);
    Dimension size24 = bounds6.getSize();
    Dimension size25 = size24.getSize();
    assertEquals(100, size25.width);
    Dimension size26 = bounds8.getSize();
    Dimension size27 = size26.getSize();
    assertEquals(100, size27.width);
    Dimension size28 = bounds10.getSize();
    Dimension size29 = size28.getSize();
    assertEquals(100, size29.width);
    assertEquals(100, size20.width);
    Dimension size30 = ((Rectangle) bounds2D4).getSize();
    Dimension size31 = size30.getSize();
    assertEquals(100, size31.width);
    Dimension size32 = ((Rectangle) bounds2D6).getSize();
    Dimension size33 = size32.getSize();
    assertEquals(100, size33.width);
    assertEquals(100, size10.width);
    assertEquals(100, size.width);
    assertEquals(100, size22.width);
    assertEquals(100, size6.width);
    Dimension size34 = bounds3.getSize();
    assertEquals(100, size34.width);
    assertEquals(100, size13.width);
    assertEquals(100, size16.width);
    assertEquals(100, size24.width);
    Dimension size35 = bounds7.getSize();
    assertEquals(100, size35.width);
    Dimension size36 = bounds9.getSize();
    assertEquals(100, size36.width);
    Dimension size37 = bounds11.getSize();
    assertEquals(100, size37.width);
    assertEquals(100, size26.width);
    Dimension size38 = bounds12.getSize();
    assertEquals(100, size38.width);
    Dimension size39 = bounds13.getSize();
    assertEquals(100, size39.width);
    assertEquals(100, size28.width);
    Dimension size40 = bounds14.getSize();
    assertEquals(100, size40.width);
    Dimension size41 = bounds15.getSize();
    assertEquals(100, size41.width);
    Dimension size42 = ((Rectangle) bounds2D).getSize();
    assertEquals(100, size42.width);
    assertEquals(100, size19.width);
    assertEquals(100, size30.width);
    assertEquals(100, size32.width);
    Dimension size43 = ((Rectangle) bounds2D7).getSize();
    assertEquals(100, size43.width);
    Dimension size44 = ((Rectangle) bounds2D19).getSize();
    assertEquals(100, size44.width);
    assertEquals(100, rectangle.width);
    assertEquals(100, rectangle2.width);
    assertEquals(100, bounds.width);
    assertEquals(100, bounds2.width);
    assertEquals(100, bounds3.width);
    assertEquals(100, bounds4.width);
    Rectangle bounds16 = bounds3.getBounds();
    assertEquals(100, bounds16.width);
    assertEquals(100, bounds5.width);
    assertEquals(100, bounds6.width);
    assertEquals(100, bounds7.width);
    Rectangle bounds17 = bounds7.getBounds();
    assertEquals(100, bounds17.width);
    Rectangle bounds18 = bounds9.getBounds();
    assertEquals(100, bounds18.width);
    Rectangle bounds19 = bounds11.getBounds();
    assertEquals(100, bounds19.width);
    assertEquals(100, bounds9.width);
    Rectangle bounds20 = bounds12.getBounds();
    assertEquals(100, bounds20.width);
    Rectangle bounds21 = bounds13.getBounds();
    assertEquals(100, bounds21.width);
    assertEquals(100, bounds11.width);
    Rectangle bounds22 = bounds14.getBounds();
    assertEquals(100, bounds22.width);
    Rectangle bounds23 = bounds15.getBounds();
    assertEquals(100, bounds23.width);
    Rectangle bounds24 = bounds2D.getBounds();
    assertEquals(100, bounds24.width);
    assertEquals(100, bounds8.width);
    assertEquals(100, bounds12.width);
    assertEquals(100, bounds13.width);
    Rectangle bounds25 = bounds2D7.getBounds();
    assertEquals(100, bounds25.width);
    Rectangle bounds26 = bounds2D19.getBounds();
    assertEquals(100, bounds26.width);
    Rectangle bounds27 = bounds2D24.getBounds();
    assertEquals(100, bounds27.width);
    Rectangle bounds28 = frame4.getBounds();
    assertEquals(100, bounds28.width);
    assertEquals(100, bounds10.width);
    assertEquals(100, bounds14.width);
    assertEquals(100, bounds15.width);
    Rectangle bounds29 = frame5.getBounds();
    assertEquals(100, bounds29.width);
    Rectangle bounds30 = frame6.getBounds();
    assertEquals(100, bounds30.width);
    Rectangle bounds31 = frame7.getBounds();
    assertEquals(100, bounds31.width);
    assertEquals(100, ((Rectangle) bounds2D).width);
    assertEquals(100, ((Rectangle) bounds2D2).width);
    assertEquals(100, ((Rectangle) bounds2D3).width);
    assertEquals(100, ((Rectangle) bounds2D4).width);
    assertEquals(100, ((Rectangle) bounds2D5).width);
    assertEquals(100, ((Rectangle) bounds2D6).width);
    assertEquals(100, ((Rectangle) bounds2D7).width);
    assertEquals(100, ((Rectangle) bounds2D8).width);
    assertEquals(100, ((Rectangle) bounds2D9).width);
    assertEquals(100, ((Rectangle) bounds2D10).width);
    assertEquals(100, ((Rectangle) bounds2D11).width);
    assertEquals(100, ((Rectangle) bounds2D12).width);
    assertEquals(100, ((Rectangle) bounds2D13).width);
    assertEquals(100, ((Rectangle) bounds2D14).width);
    assertEquals(100, ((Rectangle) bounds2D15).width);
    assertEquals(100, ((Rectangle) bounds2D16).width);
    assertEquals(100, ((Rectangle) bounds2D17).width);
    assertEquals(100, ((Rectangle) bounds2D18).width);
    assertEquals(100, ((Rectangle) bounds2D19).width);
    assertEquals(100, ((Rectangle) bounds2D20).width);
    assertEquals(100, ((Rectangle) bounds2D21).width);
    assertEquals(100, ((Rectangle) bounds2D22).width);
    assertEquals(100, ((Rectangle) bounds2D23).width);
    assertEquals(100.0d, geometry.getWidth());
    assertEquals(100.0d, graphBounds.getWidth());
    assertEquals(100.0d, size4.getWidth());
    assertEquals(100.0d, size3.getWidth());
    assertEquals(100.0d, size8.getWidth());
    assertEquals(100.0d, size11.getWidth());
    assertEquals(100.0d, size2.getWidth());
    assertEquals(100.0d, size7.getWidth());
    assertEquals(100.0d, size14.getWidth());
    assertEquals(100.0d, size17.getWidth());
    assertEquals(100.0d, size20.getWidth());
    assertEquals(100.0d, size10.getWidth());
    assertEquals(100.0d, size.getWidth());
    assertEquals(100.0d, size22.getWidth());
    assertEquals(100.0d, size6.getWidth());
    assertEquals(100.0d, size13.getWidth());
    assertEquals(100.0d, size16.getWidth());
    assertEquals(100.0d, size24.getWidth());
    assertEquals(100.0d, size26.getWidth());
    assertEquals(100.0d, size28.getWidth());
    assertEquals(100.0d, size19.getWidth());
    assertEquals(100.0d, size30.getWidth());
    assertEquals(100.0d, size32.getWidth());
    assertEquals(100.0d, rectangle.getWidth());
    assertEquals(100.0d, rectangle2.getWidth());
    assertEquals(100.0d, bounds.getWidth());
    assertEquals(100.0d, bounds2.getWidth());
    assertEquals(100.0d, bounds3.getWidth());
    assertEquals(100.0d, bounds4.getWidth());
    assertEquals(100.0d, bounds5.getWidth());
    assertEquals(100.0d, bounds6.getWidth());
    assertEquals(100.0d, bounds7.getWidth());
    assertEquals(100.0d, bounds9.getWidth());
    assertEquals(100.0d, bounds11.getWidth());
    assertEquals(100.0d, bounds8.getWidth());
    assertEquals(100.0d, bounds12.getWidth());
    assertEquals(100.0d, bounds13.getWidth());
    assertEquals(100.0d, bounds10.getWidth());
    assertEquals(100.0d, bounds14.getWidth());
    assertEquals(100.0d, bounds15.getWidth());
    assertEquals(100.0d, rectangle.getMaxX());
    assertEquals(100.0d, rectangle2.getMaxX());
    assertEquals(100.0d, bounds.getMaxX());
    assertEquals(100.0d, bounds2.getMaxX());
    assertEquals(100.0d, bounds3.getMaxX());
    assertEquals(100.0d, bounds4.getMaxX());
    assertEquals(100.0d, bounds5.getMaxX());
    assertEquals(100.0d, bounds6.getMaxX());
    assertEquals(100.0d, bounds7.getMaxX());
    assertEquals(100.0d, bounds9.getMaxX());
    assertEquals(100.0d, bounds11.getMaxX());
    assertEquals(100.0d, bounds2D.getMaxX());
    assertEquals(100.0d, bounds2D2.getMaxX());
    assertEquals(100.0d, bounds2D4.getMaxX());
    assertEquals(100.0d, bounds2D6.getMaxX());
    assertEquals(100.0d, bounds2D7.getMaxX());
    assertEquals(100.0d, bounds2D19.getMaxX());
    assertEquals(100.0d, bounds2D24.getMaxX());
    assertEquals(100.0d, bounds8.getMaxX());
    assertEquals(100.0d, bounds12.getMaxX());
    assertEquals(100.0d, bounds13.getMaxX());
    assertEquals(100.0d, bounds10.getMaxX());
    assertEquals(100.0d, bounds14.getMaxX());
    assertEquals(100.0d, bounds15.getMaxX());
    assertEquals(100.0d, frame4.getMaxX());
    assertEquals(100.0d, frame.getMaxX());
    assertEquals(100.0d, frame2.getMaxX());
    assertEquals(100.0d, frame3.getMaxX());
    assertEquals(100.0d, frame5.getMaxX());
    assertEquals(100.0d, frame6.getMaxX());
    assertEquals(100.0d, frame7.getMaxX());
    assertEquals(100.0d, bounds2D.getWidth());
    assertEquals(100.0d, bounds2D2.getWidth());
    assertEquals(100.0d, bounds2D4.getWidth());
    assertEquals(100.0d, bounds2D6.getWidth());
    assertEquals(100.0d, bounds2D7.getWidth());
    assertEquals(100.0d, bounds2D19.getWidth());
    assertEquals(100.0d, bounds2D24.getWidth());
    assertEquals(100.0d, frame4.getWidth());
    assertEquals(100.0d, frame.getWidth());
    assertEquals(100.0d, frame2.getWidth());
    assertEquals(100.0d, frame3.getWidth());
    assertEquals(100.0d, frame5.getWidth());
    assertEquals(100.0d, frame6.getWidth());
    assertEquals(100.0d, frame7.getWidth());
    GraphicInfo getResult2 = locationMap.get(null);
    assertEquals(100.0d, getResult2.getWidth());
    assertEquals(30.0d, geometry.getCenterY());
    assertEquals(30.0d, graphBounds.getCenterY());
    assertEquals(30.0d, rectangle.getCenterY());
    assertEquals(30.0d, rectangle2.getCenterY());
    assertEquals(30.0d, bounds.getCenterY());
    assertEquals(30.0d, bounds2.getCenterY());
    assertEquals(30.0d, bounds3.getCenterY());
    assertEquals(30.0d, bounds4.getCenterY());
    assertEquals(30.0d, bounds5.getCenterY());
    assertEquals(30.0d, bounds6.getCenterY());
    assertEquals(30.0d, bounds7.getCenterY());
    assertEquals(30.0d, bounds9.getCenterY());
    assertEquals(30.0d, bounds11.getCenterY());
    assertEquals(30.0d, bounds2D.getCenterY());
    assertEquals(30.0d, bounds2D2.getCenterY());
    assertEquals(30.0d, bounds2D4.getCenterY());
    assertEquals(30.0d, bounds2D6.getCenterY());
    assertEquals(30.0d, bounds2D7.getCenterY());
    assertEquals(30.0d, bounds2D19.getCenterY());
    assertEquals(30.0d, bounds2D24.getCenterY());
    assertEquals(30.0d, bounds8.getCenterY());
    assertEquals(30.0d, bounds12.getCenterY());
    assertEquals(30.0d, bounds13.getCenterY());
    assertEquals(30.0d, bounds10.getCenterY());
    assertEquals(30.0d, bounds14.getCenterY());
    assertEquals(30.0d, bounds15.getCenterY());
    assertEquals(30.0d, frame4.getCenterY());
    assertEquals(30.0d, frame.getCenterY());
    assertEquals(30.0d, frame2.getCenterY());
    assertEquals(30.0d, frame3.getCenterY());
    assertEquals(30.0d, frame5.getCenterY());
    assertEquals(30.0d, frame6.getCenterY());
    assertEquals(30.0d, frame7.getCenterY());
    assertEquals(50.0d, geometry.getCenterX());
    assertEquals(50.0d, graphBounds.getCenterX());
    assertEquals(50.0d, rectangle.getCenterX());
    assertEquals(50.0d, rectangle2.getCenterX());
    assertEquals(50.0d, bounds.getCenterX());
    assertEquals(50.0d, bounds2.getCenterX());
    assertEquals(50.0d, bounds3.getCenterX());
    assertEquals(50.0d, bounds4.getCenterX());
    assertEquals(50.0d, bounds5.getCenterX());
    assertEquals(50.0d, bounds6.getCenterX());
    assertEquals(50.0d, bounds7.getCenterX());
    assertEquals(50.0d, bounds9.getCenterX());
    assertEquals(50.0d, bounds11.getCenterX());
    assertEquals(50.0d, bounds2D.getCenterX());
    assertEquals(50.0d, bounds2D2.getCenterX());
    assertEquals(50.0d, bounds2D4.getCenterX());
    assertEquals(50.0d, bounds2D6.getCenterX());
    assertEquals(50.0d, bounds2D7.getCenterX());
    assertEquals(50.0d, bounds2D19.getCenterX());
    assertEquals(50.0d, bounds2D24.getCenterX());
    assertEquals(50.0d, bounds8.getCenterX());
    assertEquals(50.0d, bounds12.getCenterX());
    assertEquals(50.0d, bounds13.getCenterX());
    assertEquals(50.0d, bounds10.getCenterX());
    assertEquals(50.0d, bounds14.getCenterX());
    assertEquals(50.0d, bounds15.getCenterX());
    assertEquals(50.0d, frame4.getCenterX());
    assertEquals(50.0d, frame.getCenterX());
    assertEquals(50.0d, frame2.getCenterX());
    assertEquals(50.0d, frame3.getCenterX());
    assertEquals(50.0d, frame5.getCenterX());
    assertEquals(50.0d, frame6.getCenterX());
    assertEquals(50.0d, frame7.getCenterX());
    assertEquals(60, size5.height);
    assertEquals(60, size4.height);
    assertEquals(60, size9.height);
    assertEquals(60, size12.height);
    assertEquals(60, size3.height);
    assertEquals(60, size8.height);
    assertEquals(60, size15.height);
    assertEquals(60, size18.height);
    assertEquals(60, size21.height);
    assertEquals(60, size11.height);
    assertEquals(60, size2.height);
    assertEquals(60, size23.height);
    assertEquals(60, size7.height);
    assertEquals(60, size14.height);
    assertEquals(60, size17.height);
    assertEquals(60, size25.height);
    assertEquals(60, size27.height);
    assertEquals(60, size29.height);
    assertEquals(60, size20.height);
    assertEquals(60, size31.height);
    assertEquals(60, size33.height);
    assertEquals(60, size10.height);
    assertEquals(60, size.height);
    assertEquals(60, size22.height);
    assertEquals(60, size6.height);
    assertEquals(60, size34.height);
    assertEquals(60, size13.height);
    assertEquals(60, size16.height);
    assertEquals(60, size24.height);
    assertEquals(60, size35.height);
    assertEquals(60, size36.height);
    assertEquals(60, size37.height);
    assertEquals(60, size26.height);
    assertEquals(60, size38.height);
    assertEquals(60, size39.height);
    assertEquals(60, size28.height);
    assertEquals(60, size40.height);
    assertEquals(60, size41.height);
    assertEquals(60, size42.height);
    assertEquals(60, size19.height);
    assertEquals(60, size30.height);
    assertEquals(60, size32.height);
    assertEquals(60, size43.height);
    assertEquals(60, size44.height);
    assertEquals(60, rectangle.height);
    assertEquals(60, rectangle2.height);
    assertEquals(60, bounds.height);
    assertEquals(60, bounds2.height);
    assertEquals(60, bounds3.height);
    assertEquals(60, bounds4.height);
    assertEquals(60, bounds16.height);
    assertEquals(60, bounds5.height);
    assertEquals(60, bounds6.height);
    assertEquals(60, bounds7.height);
    assertEquals(60, bounds17.height);
    assertEquals(60, bounds18.height);
    assertEquals(60, bounds19.height);
    assertEquals(60, bounds9.height);
    assertEquals(60, bounds20.height);
    assertEquals(60, bounds21.height);
    assertEquals(60, bounds11.height);
    assertEquals(60, bounds22.height);
    assertEquals(60, bounds23.height);
    assertEquals(60, bounds24.height);
    assertEquals(60, bounds8.height);
    assertEquals(60, bounds12.height);
    assertEquals(60, bounds13.height);
    assertEquals(60, bounds25.height);
    assertEquals(60, bounds26.height);
    assertEquals(60, bounds27.height);
    assertEquals(60, bounds28.height);
    assertEquals(60, bounds10.height);
    assertEquals(60, bounds14.height);
    assertEquals(60, bounds15.height);
    assertEquals(60, bounds29.height);
    assertEquals(60, bounds30.height);
    assertEquals(60, bounds31.height);
    assertEquals(60, ((Rectangle) bounds2D).height);
    assertEquals(60, ((Rectangle) bounds2D2).height);
    assertEquals(60, ((Rectangle) bounds2D3).height);
    assertEquals(60, ((Rectangle) bounds2D4).height);
    assertEquals(60, ((Rectangle) bounds2D5).height);
    assertEquals(60, ((Rectangle) bounds2D6).height);
    assertEquals(60, ((Rectangle) bounds2D7).height);
    assertEquals(60, ((Rectangle) bounds2D8).height);
    assertEquals(60, ((Rectangle) bounds2D9).height);
    assertEquals(60, ((Rectangle) bounds2D10).height);
    assertEquals(60, ((Rectangle) bounds2D11).height);
    assertEquals(60, ((Rectangle) bounds2D12).height);
    assertEquals(60, ((Rectangle) bounds2D13).height);
    assertEquals(60, ((Rectangle) bounds2D14).height);
    assertEquals(60, ((Rectangle) bounds2D15).height);
    assertEquals(60, ((Rectangle) bounds2D16).height);
    assertEquals(60, ((Rectangle) bounds2D17).height);
    assertEquals(60, ((Rectangle) bounds2D18).height);
    assertEquals(60, ((Rectangle) bounds2D19).height);
    assertEquals(60, ((Rectangle) bounds2D20).height);
    assertEquals(60, ((Rectangle) bounds2D21).height);
    assertEquals(60, ((Rectangle) bounds2D22).height);
    assertEquals(60, ((Rectangle) bounds2D23).height);
    assertEquals(60.0d, geometry.getHeight());
    assertEquals(60.0d, graphBounds.getHeight());
    assertEquals(60.0d, size4.getHeight());
    assertEquals(60.0d, size3.getHeight());
    assertEquals(60.0d, size8.getHeight());
    assertEquals(60.0d, size11.getHeight());
    assertEquals(60.0d, size2.getHeight());
    assertEquals(60.0d, size7.getHeight());
    assertEquals(60.0d, size14.getHeight());
    assertEquals(60.0d, size17.getHeight());
    assertEquals(60.0d, size20.getHeight());
    assertEquals(60.0d, size10.getHeight());
    assertEquals(60.0d, size.getHeight());
    assertEquals(60.0d, size22.getHeight());
    assertEquals(60.0d, size6.getHeight());
    assertEquals(60.0d, size13.getHeight());
    assertEquals(60.0d, size16.getHeight());
    assertEquals(60.0d, size24.getHeight());
    assertEquals(60.0d, size26.getHeight());
    assertEquals(60.0d, size28.getHeight());
    assertEquals(60.0d, size19.getHeight());
    assertEquals(60.0d, size30.getHeight());
    assertEquals(60.0d, size32.getHeight());
    assertEquals(60.0d, rectangle.getHeight());
    assertEquals(60.0d, rectangle2.getHeight());
    assertEquals(60.0d, bounds.getHeight());
    assertEquals(60.0d, bounds2.getHeight());
    assertEquals(60.0d, bounds3.getHeight());
    assertEquals(60.0d, bounds4.getHeight());
    assertEquals(60.0d, bounds5.getHeight());
    assertEquals(60.0d, bounds6.getHeight());
    assertEquals(60.0d, bounds7.getHeight());
    assertEquals(60.0d, bounds9.getHeight());
    assertEquals(60.0d, bounds11.getHeight());
    assertEquals(60.0d, bounds8.getHeight());
    assertEquals(60.0d, bounds12.getHeight());
    assertEquals(60.0d, bounds13.getHeight());
    assertEquals(60.0d, bounds10.getHeight());
    assertEquals(60.0d, bounds14.getHeight());
    assertEquals(60.0d, bounds15.getHeight());
    assertEquals(60.0d, bounds2D.getHeight());
    assertEquals(60.0d, bounds2D2.getHeight());
    assertEquals(60.0d, bounds2D4.getHeight());
    assertEquals(60.0d, bounds2D6.getHeight());
    assertEquals(60.0d, bounds2D7.getHeight());
    assertEquals(60.0d, bounds2D19.getHeight());
    assertEquals(60.0d, bounds2D24.getHeight());
    assertEquals(60.0d, frame4.getHeight());
    assertEquals(60.0d, frame.getHeight());
    assertEquals(60.0d, frame2.getHeight());
    assertEquals(60.0d, frame3.getHeight());
    assertEquals(60.0d, frame5.getHeight());
    assertEquals(60.0d, frame6.getHeight());
    assertEquals(60.0d, frame7.getHeight());
    assertEquals(60.0d, rectangle.getMaxY());
    assertEquals(60.0d, rectangle2.getMaxY());
    assertEquals(60.0d, bounds.getMaxY());
    assertEquals(60.0d, bounds2.getMaxY());
    assertEquals(60.0d, bounds3.getMaxY());
    assertEquals(60.0d, bounds4.getMaxY());
    assertEquals(60.0d, bounds5.getMaxY());
    assertEquals(60.0d, bounds6.getMaxY());
    assertEquals(60.0d, bounds7.getMaxY());
    assertEquals(60.0d, bounds9.getMaxY());
    assertEquals(60.0d, bounds11.getMaxY());
    assertEquals(60.0d, bounds2D.getMaxY());
    assertEquals(60.0d, bounds2D2.getMaxY());
    assertEquals(60.0d, bounds2D4.getMaxY());
    assertEquals(60.0d, bounds2D6.getMaxY());
    assertEquals(60.0d, bounds2D7.getMaxY());
    assertEquals(60.0d, bounds2D19.getMaxY());
    assertEquals(60.0d, bounds2D24.getMaxY());
    assertEquals(60.0d, bounds8.getMaxY());
    assertEquals(60.0d, bounds12.getMaxY());
    assertEquals(60.0d, bounds13.getMaxY());
    assertEquals(60.0d, bounds10.getMaxY());
    assertEquals(60.0d, bounds14.getMaxY());
    assertEquals(60.0d, bounds15.getMaxY());
    assertEquals(60.0d, frame4.getMaxY());
    assertEquals(60.0d, frame.getMaxY());
    assertEquals(60.0d, frame2.getMaxY());
    assertEquals(60.0d, frame3.getMaxY());
    assertEquals(60.0d, frame5.getMaxY());
    assertEquals(60.0d, frame6.getMaxY());
    assertEquals(60.0d, frame7.getMaxY());
    assertEquals(60.0d, getResult2.getHeight());
    assertSame(element, stringFlowElementMap.get(null));
    assertSame(element, getResult2.getElement());
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
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is
   * {@link BpmnModel} (default constructor) Graph DefaultParent
   * {@link mxCell}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) Graph DefaultParent mxCell")
  void testLayout_thenBpmnAutoLayoutWithBpmnModelIsBpmnModelGraphDefaultParentMxCell() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    BooleanDataObject element = new BooleanDataObject();
    flowElementsContainer.addFlowElement(element);

    // Act
    bpmnAutoLayout.layout(flowElementsContainer);

    // Assert
    mxGraph graph = bpmnAutoLayout.getGraph();
    Object defaultParent = graph.getDefaultParent();
    assertTrue(defaultParent instanceof mxCell);
    Object object = bpmnAutoLayout.cellParent;
    assertTrue(object instanceof mxCell);
    mxIGraphModel model = graph.getModel();
    assertTrue(model instanceof mxGraphModel);
    mxRectangle graphBounds = graph.getGraphBounds();
    Rectangle rectangle = graphBounds.getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    Rectangle bounds = rectangle.getBounds();
    Rectangle2D bounds2D2 = bounds.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle bounds2 = bounds.getBounds();
    Rectangle2D bounds2D3 = bounds2.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    Rectangle bounds3 = bounds2.getBounds();
    Rectangle2D bounds2D4 = bounds3.getBounds2D();
    assertTrue(bounds2D4 instanceof Rectangle);
    Rectangle bounds4 = bounds3.getBounds();
    Rectangle2D bounds2D5 = bounds4.getBounds2D();
    assertTrue(bounds2D5 instanceof Rectangle);
    Rectangle bounds5 = bounds4.getBounds();
    Rectangle2D bounds2D6 = bounds5.getBounds2D();
    assertTrue(bounds2D6 instanceof Rectangle);
    Rectangle bounds6 = bounds2D.getBounds();
    Rectangle bounds7 = bounds6.getBounds();
    Rectangle bounds8 = bounds7.getBounds();
    Rectangle2D bounds2D7 = bounds8.getBounds2D();
    assertTrue(bounds2D7 instanceof Rectangle);
    Rectangle2D bounds2D8 = bounds7.getBounds2D();
    assertTrue(bounds2D8 instanceof Rectangle);
    Rectangle bounds9 = bounds2D2.getBounds();
    Rectangle bounds10 = bounds9.getBounds();
    Rectangle2D bounds2D9 = bounds10.getBounds2D();
    assertTrue(bounds2D9 instanceof Rectangle);
    Rectangle2D frame = rectangle.getFrame();
    Rectangle bounds11 = frame.getBounds();
    Rectangle bounds12 = bounds11.getBounds();
    Rectangle2D bounds2D10 = bounds12.getBounds2D();
    assertTrue(bounds2D10 instanceof Rectangle);
    Rectangle2D frame2 = bounds.getFrame();
    Rectangle bounds13 = frame2.getBounds();
    Rectangle bounds14 = bounds13.getBounds();
    Rectangle2D bounds2D11 = bounds14.getBounds2D();
    assertTrue(bounds2D11 instanceof Rectangle);
    Rectangle2D bounds2D12 = bounds6.getBounds2D();
    assertTrue(bounds2D12 instanceof Rectangle);
    Rectangle2D bounds2D13 = bounds9.getBounds2D();
    assertTrue(bounds2D13 instanceof Rectangle);
    Rectangle bounds15 = bounds2D3.getBounds();
    Rectangle2D bounds2D14 = bounds15.getBounds2D();
    assertTrue(bounds2D14 instanceof Rectangle);
    Rectangle2D bounds2D15 = bounds11.getBounds2D();
    assertTrue(bounds2D15 instanceof Rectangle);
    Rectangle2D bounds2D16 = bounds13.getBounds2D();
    assertTrue(bounds2D16 instanceof Rectangle);
    Rectangle2D frame3 = bounds2.getFrame();
    Rectangle bounds16 = frame3.getBounds();
    Rectangle2D bounds2D17 = bounds16.getBounds2D();
    assertTrue(bounds2D17 instanceof Rectangle);
    Rectangle2D bounds2D18 = bounds2D.getBounds2D();
    assertTrue(bounds2D18 instanceof Rectangle);
    Rectangle2D bounds2D19 = bounds2D2.getBounds2D();
    assertTrue(bounds2D19 instanceof Rectangle);
    Rectangle2D bounds2D20 = bounds2D3.getBounds2D();
    assertTrue(bounds2D20 instanceof Rectangle);
    Rectangle2D bounds2D21 = bounds2D4.getBounds2D();
    assertTrue(bounds2D21 instanceof Rectangle);
    Rectangle2D bounds2D22 = bounds2D12.getBounds2D();
    assertTrue(bounds2D22 instanceof Rectangle);
    Rectangle2D bounds2D23 = bounds2D18.getBounds2D();
    assertTrue(bounds2D23 instanceof Rectangle);
    Rectangle2D bounds2D24 = bounds2D19.getBounds2D();
    assertTrue(bounds2D24 instanceof Rectangle);
    Rectangle2D bounds2D25 = frame2.getBounds2D();
    Rectangle2D bounds2D26 = bounds2D25.getBounds2D();
    assertTrue(bounds2D26 instanceof Rectangle2D.Double);
    Rectangle2D bounds2D27 = frame.getBounds2D();
    assertTrue(bounds2D27 instanceof Rectangle2D.Double);
    assertTrue(bounds2D25 instanceof Rectangle2D.Double);
    Rectangle2D frame4 = bounds2D2.getFrame();
    Rectangle2D bounds2D28 = frame4.getBounds2D();
    assertTrue(bounds2D28 instanceof Rectangle2D.Double);
    Rectangle2D frame5 = bounds6.getFrame();
    Rectangle2D bounds2D29 = frame5.getBounds2D();
    assertTrue(bounds2D29 instanceof Rectangle2D.Double);
    Rectangle2D frame6 = frame2.getFrame();
    Rectangle2D bounds2D30 = frame6.getBounds2D();
    assertTrue(bounds2D30 instanceof Rectangle2D.Double);
    assertTrue(frame instanceof Rectangle2D.Double);
    assertTrue(frame2 instanceof Rectangle2D.Double);
    assertTrue(frame3 instanceof Rectangle2D.Double);
    Rectangle2D frame7 = bounds3.getFrame();
    assertTrue(frame7 instanceof Rectangle2D.Double);
    Rectangle2D frame8 = bounds8.getFrame();
    assertTrue(frame8 instanceof Rectangle2D.Double);
    Rectangle2D frame9 = bounds14.getFrame();
    assertTrue(frame9 instanceof Rectangle2D.Double);
    Rectangle2D frame10 = bounds2D.getFrame();
    assertTrue(frame10 instanceof Rectangle2D.Double);
    assertTrue(frame4 instanceof Rectangle2D.Double);
    Rectangle2D frame11 = bounds2D12.getFrame();
    assertTrue(frame11 instanceof Rectangle2D.Double);
    Rectangle2D frame12 = bounds2D19.getFrame();
    assertTrue(frame12 instanceof Rectangle2D.Double);
    Rectangle2D frame13 = bounds2D25.getFrame();
    assertTrue(frame13 instanceof Rectangle2D.Double);
    assertTrue(frame5 instanceof Rectangle2D.Double);
    Rectangle2D frame14 = frame.getFrame();
    assertTrue(frame14 instanceof Rectangle2D.Double);
    assertTrue(frame6 instanceof Rectangle2D.Double);
    Rectangle2D frame15 = frame4.getFrame();
    assertTrue(frame15 instanceof Rectangle2D.Double);
    Rectangle2D frame16 = frame5.getFrame();
    assertTrue(frame16 instanceof Rectangle2D.Double);
    Rectangle2D frame17 = frame6.getFrame();
    assertTrue(frame17 instanceof Rectangle2D.Double);
    assertEquals(0, ((mxCell) defaultParent).getChildCount());
    assertEquals(0, ((mxCell) object).getChildCount());
    Dimension size = rectangle.getSize();
    Dimension size2 = size.getSize();
    Dimension size3 = size2.getSize();
    Dimension size4 = size3.getSize();
    Dimension size5 = size4.getSize();
    assertEquals(0, size5.height);
    assertEquals(0, size4.height);
    Dimension size6 = bounds2.getSize();
    Dimension size7 = size6.getSize();
    Dimension size8 = size7.getSize();
    Dimension size9 = size8.getSize();
    assertEquals(0, size9.height);
    assertEquals(0, size3.height);
    assertEquals(0, size8.height);
    Dimension size10 = bounds3.getSize();
    Dimension size11 = size10.getSize();
    Dimension size12 = size11.getSize();
    assertEquals(0, size12.height);
    Dimension size13 = ((Rectangle) bounds2D).getSize();
    Dimension size14 = size13.getSize();
    Dimension size15 = size14.getSize();
    assertEquals(0, size15.height);
    assertEquals(0, size2.height);
    assertEquals(0, size7.height);
    assertEquals(0, size11.height);
    Dimension size16 = bounds4.getSize();
    Dimension size17 = size16.getSize();
    assertEquals(0, size17.height);
    Dimension size18 = bounds6.getSize();
    Dimension size19 = size18.getSize();
    assertEquals(0, size19.height);
    Dimension size20 = bounds11.getSize();
    Dimension size21 = size20.getSize();
    assertEquals(0, size21.height);
    assertEquals(0, size14.height);
    Dimension size22 = ((Rectangle) bounds2D2).getSize();
    Dimension size23 = size22.getSize();
    assertEquals(0, size23.height);
    Dimension size24 = ((Rectangle) bounds2D3).getSize();
    Dimension size25 = size24.getSize();
    assertEquals(0, size25.height);
    assertEquals(0, size.height);
    assertEquals(0, size6.height);
    assertEquals(0, size10.height);
    assertEquals(0, size16.height);
    Dimension size26 = bounds5.getSize();
    assertEquals(0, size26.height);
    Dimension size27 = bounds7.getSize();
    assertEquals(0, size27.height);
    Dimension size28 = bounds10.getSize();
    assertEquals(0, size28.height);
    Dimension size29 = bounds12.getSize();
    assertEquals(0, size29.height);
    assertEquals(0, size18.height);
    Dimension size30 = bounds9.getSize();
    assertEquals(0, size30.height);
    Dimension size31 = bounds15.getSize();
    assertEquals(0, size31.height);
    assertEquals(0, size20.height);
    Dimension size32 = bounds13.getSize();
    assertEquals(0, size32.height);
    Dimension size33 = bounds16.getSize();
    assertEquals(0, size33.height);
    assertEquals(0, size13.height);
    assertEquals(0, size22.height);
    assertEquals(0, size24.height);
    Dimension size34 = ((Rectangle) bounds2D4).getSize();
    assertEquals(0, size34.height);
    Dimension size35 = ((Rectangle) bounds2D18).getSize();
    assertEquals(0, size35.height);
    assertEquals(0, size5.width);
    assertEquals(0, size4.width);
    assertEquals(0, size9.width);
    assertEquals(0, size3.width);
    assertEquals(0, size8.width);
    assertEquals(0, size12.width);
    assertEquals(0, size15.width);
    assertEquals(0, size2.width);
    assertEquals(0, size7.width);
    assertEquals(0, size11.width);
    assertEquals(0, size17.width);
    assertEquals(0, size19.width);
    assertEquals(0, size21.width);
    assertEquals(0, size14.width);
    assertEquals(0, size23.width);
    assertEquals(0, size25.width);
    assertEquals(0, size.width);
    assertEquals(0, size6.width);
    assertEquals(0, size10.width);
    assertEquals(0, size16.width);
    assertEquals(0, size26.width);
    assertEquals(0, size27.width);
    assertEquals(0, size28.width);
    assertEquals(0, size29.width);
    assertEquals(0, size18.width);
    assertEquals(0, size30.width);
    assertEquals(0, size31.width);
    assertEquals(0, size20.width);
    assertEquals(0, size32.width);
    assertEquals(0, size33.width);
    assertEquals(0, size13.width);
    assertEquals(0, size22.width);
    assertEquals(0, size24.width);
    assertEquals(0, size34.width);
    assertEquals(0, size35.width);
    assertEquals(0, rectangle.height);
    assertEquals(0, bounds.height);
    assertEquals(0, bounds2.height);
    assertEquals(0, bounds3.height);
    assertEquals(0, bounds4.height);
    assertEquals(0, bounds5.height);
    Rectangle bounds17 = bounds5.getBounds();
    assertEquals(0, bounds17.height);
    assertEquals(0, bounds8.height);
    Rectangle bounds18 = bounds10.getBounds();
    assertEquals(0, bounds18.height);
    Rectangle bounds19 = bounds12.getBounds();
    assertEquals(0, bounds19.height);
    assertEquals(0, bounds7.height);
    assertEquals(0, bounds10.height);
    Rectangle bounds20 = bounds15.getBounds();
    assertEquals(0, bounds20.height);
    assertEquals(0, bounds12.height);
    assertEquals(0, bounds14.height);
    Rectangle bounds21 = bounds16.getBounds();
    assertEquals(0, bounds21.height);
    assertEquals(0, bounds6.height);
    assertEquals(0, bounds9.height);
    assertEquals(0, bounds15.height);
    Rectangle bounds22 = bounds2D4.getBounds();
    assertEquals(0, bounds22.height);
    Rectangle bounds23 = bounds2D18.getBounds();
    assertEquals(0, bounds23.height);
    Rectangle bounds24 = bounds2D27.getBounds();
    assertEquals(0, bounds24.height);
    assertEquals(0, bounds11.height);
    assertEquals(0, bounds13.height);
    assertEquals(0, bounds16.height);
    Rectangle bounds25 = frame7.getBounds();
    assertEquals(0, bounds25.height);
    Rectangle bounds26 = frame10.getBounds();
    assertEquals(0, bounds26.height);
    Rectangle bounds27 = frame14.getBounds();
    assertEquals(0, bounds27.height);
    assertEquals(0, ((Rectangle) bounds2D).height);
    assertEquals(0, ((Rectangle) bounds2D2).height);
    assertEquals(0, ((Rectangle) bounds2D3).height);
    assertEquals(0, ((Rectangle) bounds2D4).height);
    assertEquals(0, ((Rectangle) bounds2D5).height);
    assertEquals(0, ((Rectangle) bounds2D6).height);
    assertEquals(0, ((Rectangle) bounds2D8).height);
    assertEquals(0, ((Rectangle) bounds2D9).height);
    assertEquals(0, ((Rectangle) bounds2D10).height);
    assertEquals(0, ((Rectangle) bounds2D12).height);
    assertEquals(0, ((Rectangle) bounds2D13).height);
    assertEquals(0, ((Rectangle) bounds2D14).height);
    assertEquals(0, ((Rectangle) bounds2D15).height);
    assertEquals(0, ((Rectangle) bounds2D16).height);
    assertEquals(0, ((Rectangle) bounds2D17).height);
    assertEquals(0, ((Rectangle) bounds2D18).height);
    assertEquals(0, ((Rectangle) bounds2D19).height);
    assertEquals(0, ((Rectangle) bounds2D20).height);
    assertEquals(0, ((Rectangle) bounds2D21).height);
    assertEquals(0, ((Rectangle) bounds2D23).height);
    assertEquals(0, rectangle.width);
    assertEquals(0, bounds.width);
    assertEquals(0, bounds2.width);
    assertEquals(0, bounds3.width);
    assertEquals(0, bounds4.width);
    assertEquals(0, bounds5.width);
    assertEquals(0, bounds17.width);
    assertEquals(0, bounds8.width);
    assertEquals(0, bounds18.width);
    assertEquals(0, bounds19.width);
    assertEquals(0, bounds7.width);
    assertEquals(0, bounds10.width);
    assertEquals(0, bounds20.width);
    assertEquals(0, bounds12.width);
    assertEquals(0, bounds14.width);
    assertEquals(0, bounds21.width);
    assertEquals(0, bounds6.width);
    assertEquals(0, bounds9.width);
    assertEquals(0, bounds15.width);
    assertEquals(0, bounds22.width);
    assertEquals(0, bounds23.width);
    assertEquals(0, bounds24.width);
    assertEquals(0, bounds11.width);
    assertEquals(0, bounds13.width);
    assertEquals(0, bounds16.width);
    assertEquals(0, bounds25.width);
    assertEquals(0, bounds26.width);
    assertEquals(0, bounds27.width);
    assertEquals(0, ((Rectangle) bounds2D).width);
    assertEquals(0, ((Rectangle) bounds2D2).width);
    assertEquals(0, ((Rectangle) bounds2D3).width);
    assertEquals(0, ((Rectangle) bounds2D4).width);
    assertEquals(0, ((Rectangle) bounds2D5).width);
    assertEquals(0, ((Rectangle) bounds2D6).width);
    assertEquals(0, ((Rectangle) bounds2D8).width);
    assertEquals(0, ((Rectangle) bounds2D9).width);
    assertEquals(0, ((Rectangle) bounds2D10).width);
    assertEquals(0, ((Rectangle) bounds2D12).width);
    assertEquals(0, ((Rectangle) bounds2D13).width);
    assertEquals(0, ((Rectangle) bounds2D14).width);
    assertEquals(0, ((Rectangle) bounds2D15).width);
    assertEquals(0, ((Rectangle) bounds2D16).width);
    assertEquals(0, ((Rectangle) bounds2D17).width);
    assertEquals(0, ((Rectangle) bounds2D18).width);
    assertEquals(0, ((Rectangle) bounds2D19).width);
    assertEquals(0, ((Rectangle) bounds2D20).width);
    assertEquals(0, ((Rectangle) bounds2D21).width);
    assertEquals(0, ((Rectangle) bounds2D23).width);
    assertEquals(0.0d, graphBounds.getCenterX());
    assertEquals(0.0d, graphBounds.getCenterY());
    assertEquals(0.0d, graphBounds.getHeight());
    assertEquals(0.0d, graphBounds.getWidth());
    assertEquals(0.0d, size4.getHeight());
    assertEquals(0.0d, size3.getHeight());
    assertEquals(0.0d, size8.getHeight());
    assertEquals(0.0d, size2.getHeight());
    assertEquals(0.0d, size7.getHeight());
    assertEquals(0.0d, size11.getHeight());
    assertEquals(0.0d, size14.getHeight());
    assertEquals(0.0d, size23.getHeight());
    assertEquals(0.0d, size.getHeight());
    assertEquals(0.0d, size6.getHeight());
    assertEquals(0.0d, size10.getHeight());
    assertEquals(0.0d, size16.getHeight());
    assertEquals(0.0d, size27.getHeight());
    assertEquals(0.0d, size18.getHeight());
    assertEquals(0.0d, size30.getHeight());
    assertEquals(0.0d, size20.getHeight());
    assertEquals(0.0d, size32.getHeight());
    assertEquals(0.0d, size13.getHeight());
    assertEquals(0.0d, size22.getHeight());
    assertEquals(0.0d, size24.getHeight());
    assertEquals(0.0d, size4.getWidth());
    assertEquals(0.0d, size3.getWidth());
    assertEquals(0.0d, size8.getWidth());
    assertEquals(0.0d, size2.getWidth());
    assertEquals(0.0d, size7.getWidth());
    assertEquals(0.0d, size11.getWidth());
    assertEquals(0.0d, size14.getWidth());
    assertEquals(0.0d, size23.getWidth());
    assertEquals(0.0d, size.getWidth());
    assertEquals(0.0d, size6.getWidth());
    assertEquals(0.0d, size10.getWidth());
    assertEquals(0.0d, size16.getWidth());
    assertEquals(0.0d, size27.getWidth());
    assertEquals(0.0d, size18.getWidth());
    assertEquals(0.0d, size30.getWidth());
    assertEquals(0.0d, size20.getWidth());
    assertEquals(0.0d, size32.getWidth());
    assertEquals(0.0d, size13.getWidth());
    assertEquals(0.0d, size22.getWidth());
    assertEquals(0.0d, size24.getWidth());
    Point location = bounds.getLocation().getLocation().getLocation().getLocation();
    assertEquals(0.0d, location.getX());
    Point location2 = ((Rectangle) bounds2D2).getLocation().getLocation();
    assertEquals(0.0d, location2.getX());
    Point location3 = bounds7.getLocation();
    assertEquals(0.0d, location3.getX());
    Point location4 = bounds9.getLocation();
    assertEquals(0.0d, location4.getX());
    Point location5 = bounds13.getLocation();
    assertEquals(0.0d, location5.getX());
    assertEquals(0.0d, location.getY());
    assertEquals(0.0d, location2.getY());
    assertEquals(0.0d, location3.getY());
    assertEquals(0.0d, location4.getY());
    assertEquals(0.0d, location5.getY());
    assertEquals(0.0d, rectangle.getHeight());
    assertEquals(0.0d, bounds.getHeight());
    assertEquals(0.0d, bounds2.getHeight());
    assertEquals(0.0d, bounds3.getHeight());
    assertEquals(0.0d, bounds4.getHeight());
    assertEquals(0.0d, bounds5.getHeight());
    assertEquals(0.0d, bounds8.getHeight());
    assertEquals(0.0d, bounds7.getHeight());
    assertEquals(0.0d, bounds10.getHeight());
    assertEquals(0.0d, bounds12.getHeight());
    assertEquals(0.0d, bounds14.getHeight());
    assertEquals(0.0d, bounds6.getHeight());
    assertEquals(0.0d, bounds9.getHeight());
    assertEquals(0.0d, bounds15.getHeight());
    assertEquals(0.0d, bounds11.getHeight());
    assertEquals(0.0d, bounds13.getHeight());
    assertEquals(0.0d, bounds16.getHeight());
    assertEquals(0.0d, rectangle.getWidth());
    assertEquals(0.0d, bounds.getWidth());
    assertEquals(0.0d, bounds2.getWidth());
    assertEquals(0.0d, bounds3.getWidth());
    assertEquals(0.0d, bounds4.getWidth());
    assertEquals(0.0d, bounds5.getWidth());
    assertEquals(0.0d, bounds8.getWidth());
    assertEquals(0.0d, bounds7.getWidth());
    assertEquals(0.0d, bounds10.getWidth());
    assertEquals(0.0d, bounds12.getWidth());
    assertEquals(0.0d, bounds14.getWidth());
    assertEquals(0.0d, bounds6.getWidth());
    assertEquals(0.0d, bounds9.getWidth());
    assertEquals(0.0d, bounds15.getWidth());
    assertEquals(0.0d, bounds11.getWidth());
    assertEquals(0.0d, bounds13.getWidth());
    assertEquals(0.0d, bounds16.getWidth());
    assertEquals(0.0d, bounds8.getX());
    assertEquals(0.0d, bounds10.getX());
    assertEquals(0.0d, bounds14.getX());
    assertEquals(0.0d, bounds8.getY());
    assertEquals(0.0d, bounds10.getY());
    assertEquals(0.0d, bounds14.getY());
    assertEquals(0.0d, rectangle.getCenterX());
    assertEquals(0.0d, bounds.getCenterX());
    assertEquals(0.0d, bounds2.getCenterX());
    assertEquals(0.0d, bounds3.getCenterX());
    assertEquals(0.0d, bounds4.getCenterX());
    assertEquals(0.0d, bounds5.getCenterX());
    assertEquals(0.0d, bounds8.getCenterX());
    assertEquals(0.0d, bounds7.getCenterX());
    assertEquals(0.0d, bounds10.getCenterX());
    assertEquals(0.0d, bounds12.getCenterX());
    assertEquals(0.0d, bounds14.getCenterX());
    assertEquals(0.0d, bounds2D.getCenterX());
    assertEquals(0.0d, bounds2D2.getCenterX());
    assertEquals(0.0d, bounds2D3.getCenterX());
    assertEquals(0.0d, bounds2D4.getCenterX());
    assertEquals(0.0d, bounds2D12.getCenterX());
    assertEquals(0.0d, bounds2D18.getCenterX());
    assertEquals(0.0d, bounds2D19.getCenterX());
    assertEquals(0.0d, bounds2D27.getCenterX());
    assertEquals(0.0d, bounds2D25.getCenterX());
    assertEquals(0.0d, bounds6.getCenterX());
    assertEquals(0.0d, bounds9.getCenterX());
    assertEquals(0.0d, bounds15.getCenterX());
    assertEquals(0.0d, bounds11.getCenterX());
    assertEquals(0.0d, bounds13.getCenterX());
    assertEquals(0.0d, bounds16.getCenterX());
    assertEquals(0.0d, frame.getCenterX());
    assertEquals(0.0d, frame2.getCenterX());
    assertEquals(0.0d, frame3.getCenterX());
    assertEquals(0.0d, frame7.getCenterX());
    assertEquals(0.0d, frame10.getCenterX());
    assertEquals(0.0d, frame4.getCenterX());
    assertEquals(0.0d, frame5.getCenterX());
    assertEquals(0.0d, frame14.getCenterX());
    assertEquals(0.0d, frame6.getCenterX());
    assertEquals(0.0d, rectangle.getCenterY());
    assertEquals(0.0d, bounds.getCenterY());
    assertEquals(0.0d, bounds2.getCenterY());
    assertEquals(0.0d, bounds3.getCenterY());
    assertEquals(0.0d, bounds4.getCenterY());
    assertEquals(0.0d, bounds5.getCenterY());
    assertEquals(0.0d, bounds8.getCenterY());
    assertEquals(0.0d, bounds7.getCenterY());
    assertEquals(0.0d, bounds10.getCenterY());
    assertEquals(0.0d, bounds12.getCenterY());
    assertEquals(0.0d, bounds14.getCenterY());
    assertEquals(0.0d, bounds2D.getCenterY());
    assertEquals(0.0d, bounds2D2.getCenterY());
    assertEquals(0.0d, bounds2D3.getCenterY());
    assertEquals(0.0d, bounds2D4.getCenterY());
    assertEquals(0.0d, bounds2D12.getCenterY());
    assertEquals(0.0d, bounds2D18.getCenterY());
    assertEquals(0.0d, bounds2D19.getCenterY());
    assertEquals(0.0d, bounds2D27.getCenterY());
    assertEquals(0.0d, bounds2D25.getCenterY());
    assertEquals(0.0d, bounds6.getCenterY());
    assertEquals(0.0d, bounds9.getCenterY());
    assertEquals(0.0d, bounds15.getCenterY());
    assertEquals(0.0d, bounds11.getCenterY());
    assertEquals(0.0d, bounds13.getCenterY());
    assertEquals(0.0d, bounds16.getCenterY());
    assertEquals(0.0d, frame.getCenterY());
    assertEquals(0.0d, frame2.getCenterY());
    assertEquals(0.0d, frame3.getCenterY());
    assertEquals(0.0d, frame7.getCenterY());
    assertEquals(0.0d, frame10.getCenterY());
    assertEquals(0.0d, frame4.getCenterY());
    assertEquals(0.0d, frame5.getCenterY());
    assertEquals(0.0d, frame14.getCenterY());
    assertEquals(0.0d, frame6.getCenterY());
    assertEquals(0.0d, bounds2D.getHeight());
    assertEquals(0.0d, bounds2D2.getHeight());
    assertEquals(0.0d, bounds2D3.getHeight());
    assertEquals(0.0d, bounds2D4.getHeight());
    assertEquals(0.0d, bounds2D12.getHeight());
    assertEquals(0.0d, bounds2D18.getHeight());
    assertEquals(0.0d, bounds2D19.getHeight());
    assertEquals(0.0d, bounds2D27.getHeight());
    assertEquals(0.0d, bounds2D25.getHeight());
    assertEquals(0.0d, frame.getHeight());
    assertEquals(0.0d, frame2.getHeight());
    assertEquals(0.0d, frame3.getHeight());
    assertEquals(0.0d, frame7.getHeight());
    assertEquals(0.0d, frame10.getHeight());
    assertEquals(0.0d, frame4.getHeight());
    assertEquals(0.0d, frame5.getHeight());
    assertEquals(0.0d, frame14.getHeight());
    assertEquals(0.0d, frame6.getHeight());
    assertEquals(0.0d, rectangle.getMaxX());
    assertEquals(0.0d, bounds.getMaxX());
    assertEquals(0.0d, bounds2.getMaxX());
    assertEquals(0.0d, bounds3.getMaxX());
    assertEquals(0.0d, bounds4.getMaxX());
    assertEquals(0.0d, bounds5.getMaxX());
    assertEquals(0.0d, bounds8.getMaxX());
    assertEquals(0.0d, bounds7.getMaxX());
    assertEquals(0.0d, bounds10.getMaxX());
    assertEquals(0.0d, bounds12.getMaxX());
    assertEquals(0.0d, bounds14.getMaxX());
    assertEquals(0.0d, bounds2D.getMaxX());
    assertEquals(0.0d, bounds2D2.getMaxX());
    assertEquals(0.0d, bounds2D3.getMaxX());
    assertEquals(0.0d, bounds2D4.getMaxX());
    assertEquals(0.0d, bounds2D12.getMaxX());
    assertEquals(0.0d, bounds2D18.getMaxX());
    assertEquals(0.0d, bounds2D19.getMaxX());
    assertEquals(0.0d, bounds2D27.getMaxX());
    assertEquals(0.0d, bounds2D25.getMaxX());
    assertEquals(0.0d, bounds6.getMaxX());
    assertEquals(0.0d, bounds9.getMaxX());
    assertEquals(0.0d, bounds15.getMaxX());
    assertEquals(0.0d, bounds11.getMaxX());
    assertEquals(0.0d, bounds13.getMaxX());
    assertEquals(0.0d, bounds16.getMaxX());
    assertEquals(0.0d, frame.getMaxX());
    assertEquals(0.0d, frame2.getMaxX());
    assertEquals(0.0d, frame3.getMaxX());
    assertEquals(0.0d, frame7.getMaxX());
    assertEquals(0.0d, frame10.getMaxX());
    assertEquals(0.0d, frame4.getMaxX());
    assertEquals(0.0d, frame5.getMaxX());
    assertEquals(0.0d, frame14.getMaxX());
    assertEquals(0.0d, frame6.getMaxX());
    assertEquals(0.0d, rectangle.getMaxY());
    assertEquals(0.0d, bounds.getMaxY());
    assertEquals(0.0d, bounds2.getMaxY());
    assertEquals(0.0d, bounds3.getMaxY());
    assertEquals(0.0d, bounds4.getMaxY());
    assertEquals(0.0d, bounds5.getMaxY());
    assertEquals(0.0d, bounds8.getMaxY());
    assertEquals(0.0d, bounds7.getMaxY());
    assertEquals(0.0d, bounds10.getMaxY());
    assertEquals(0.0d, bounds12.getMaxY());
    assertEquals(0.0d, bounds14.getMaxY());
    assertEquals(0.0d, bounds2D.getMaxY());
    assertEquals(0.0d, bounds2D2.getMaxY());
    assertEquals(0.0d, bounds2D3.getMaxY());
    assertEquals(0.0d, bounds2D4.getMaxY());
    assertEquals(0.0d, bounds2D12.getMaxY());
    assertEquals(0.0d, bounds2D18.getMaxY());
    assertEquals(0.0d, bounds2D19.getMaxY());
    assertEquals(0.0d, bounds2D27.getMaxY());
    assertEquals(0.0d, bounds2D25.getMaxY());
    assertEquals(0.0d, bounds6.getMaxY());
    assertEquals(0.0d, bounds9.getMaxY());
    assertEquals(0.0d, bounds15.getMaxY());
    assertEquals(0.0d, bounds11.getMaxY());
    assertEquals(0.0d, bounds13.getMaxY());
    assertEquals(0.0d, bounds16.getMaxY());
    assertEquals(0.0d, frame.getMaxY());
    assertEquals(0.0d, frame2.getMaxY());
    assertEquals(0.0d, frame3.getMaxY());
    assertEquals(0.0d, frame7.getMaxY());
    assertEquals(0.0d, frame10.getMaxY());
    assertEquals(0.0d, frame4.getMaxY());
    assertEquals(0.0d, frame5.getMaxY());
    assertEquals(0.0d, frame14.getMaxY());
    assertEquals(0.0d, frame6.getMaxY());
    assertEquals(0.0d, bounds8.getMinX());
    assertEquals(0.0d, bounds14.getMinX());
    assertEquals(0.0d, bounds2D12.getMinX());
    assertEquals(0.0d, bounds2D19.getMinX());
    assertEquals(0.0d, bounds2D25.getMinX());
    assertEquals(0.0d, frame4.getMinX());
    assertEquals(0.0d, frame6.getMinX());
    assertEquals(0.0d, bounds8.getMinY());
    assertEquals(0.0d, bounds14.getMinY());
    assertEquals(0.0d, bounds2D12.getMinY());
    assertEquals(0.0d, bounds2D19.getMinY());
    assertEquals(0.0d, bounds2D25.getMinY());
    assertEquals(0.0d, frame4.getMinY());
    assertEquals(0.0d, frame6.getMinY());
    assertEquals(0.0d, bounds2D.getWidth());
    assertEquals(0.0d, bounds2D2.getWidth());
    assertEquals(0.0d, bounds2D3.getWidth());
    assertEquals(0.0d, bounds2D4.getWidth());
    assertEquals(0.0d, bounds2D12.getWidth());
    assertEquals(0.0d, bounds2D18.getWidth());
    assertEquals(0.0d, bounds2D19.getWidth());
    assertEquals(0.0d, bounds2D27.getWidth());
    assertEquals(0.0d, bounds2D25.getWidth());
    assertEquals(0.0d, frame.getWidth());
    assertEquals(0.0d, frame2.getWidth());
    assertEquals(0.0d, frame3.getWidth());
    assertEquals(0.0d, frame7.getWidth());
    assertEquals(0.0d, frame10.getWidth());
    assertEquals(0.0d, frame4.getWidth());
    assertEquals(0.0d, frame14.getWidth());
    assertEquals(0.0d, frame6.getWidth());
    assertEquals(0.0d, bounds2D12.getX());
    assertEquals(0.0d, bounds2D19.getX());
    assertEquals(0.0d, bounds2D25.getX());
    assertEquals(0.0d, frame4.getX());
    assertEquals(0.0d, frame6.getX());
    assertEquals(0.0d, bounds2D12.getY());
    assertEquals(0.0d, bounds2D19.getY());
    assertEquals(0.0d, bounds2D25.getY());
    assertEquals(0.0d, frame4.getY());
    assertEquals(0.0d, frame6.getY());
    Map<String, FlowElement> stringFlowElementMap = bpmnAutoLayout.handledFlowElements;
    assertEquals(1, stringFlowElementMap.size());
    assertEquals(2, graph.getView().getStates().size());
    Map<String, Object> cells = ((mxGraphModel) model).getCells();
    assertEquals(2, cells.size());
    BpmnModel bpmnModel = bpmnAutoLayout.bpmnModel;
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertTrue(rectangle.isEmpty());
    assertTrue(bounds.isEmpty());
    assertTrue(bounds2.isEmpty());
    assertTrue(bounds3.isEmpty());
    assertTrue(bounds4.isEmpty());
    assertTrue(bounds5.isEmpty());
    assertTrue(bounds8.isEmpty());
    assertTrue(bounds7.isEmpty());
    assertTrue(bounds10.isEmpty());
    assertTrue(bounds12.isEmpty());
    assertTrue(bounds14.isEmpty());
    assertTrue(bounds6.isEmpty());
    assertTrue(bounds9.isEmpty());
    assertTrue(bounds15.isEmpty());
    assertTrue(bounds11.isEmpty());
    assertTrue(bounds13.isEmpty());
    assertTrue(bounds16.isEmpty());
    assertTrue(bounds2D.isEmpty());
    assertTrue(bounds2D2.isEmpty());
    assertTrue(bounds2D3.isEmpty());
    assertTrue(bounds2D4.isEmpty());
    assertTrue(bounds2D12.isEmpty());
    assertTrue(bounds2D18.isEmpty());
    assertTrue(bounds2D19.isEmpty());
    assertTrue(bounds2D27.isEmpty());
    assertTrue(bounds2D25.isEmpty());
    assertTrue(frame.isEmpty());
    assertTrue(frame2.isEmpty());
    assertTrue(frame3.isEmpty());
    assertTrue(frame7.isEmpty());
    assertTrue(frame10.isEmpty());
    assertTrue(frame4.isEmpty());
    assertTrue(frame14.isEmpty());
    assertTrue(frame6.isEmpty());
    assertTrue(cells.containsKey("0"));
    assertTrue(cells.containsKey("1"));
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnAutoLayout.generatedVertices.isEmpty());
    assertEquals(rectangle, bounds8.getBounds());
    assertEquals(rectangle, bounds14.getBounds());
    assertEquals(rectangle, bounds2D12.getBounds());
    assertEquals(rectangle, bounds2D19.getBounds());
    assertEquals(rectangle, bounds2D25.getBounds());
    assertEquals(rectangle, frame4.getBounds());
    assertEquals(rectangle, frame5.getBounds());
    assertEquals(rectangle, frame6.getBounds());
    assertEquals(rectangle, bounds2D7);
    assertEquals(rectangle, bounds2D11);
    assertEquals(rectangle, bounds2D22);
    assertEquals(rectangle, bounds2D24);
    assertEquals(rectangle, bounds2D26);
    assertEquals(rectangle, bounds2D28);
    assertEquals(rectangle, bounds2D29);
    assertEquals(rectangle, bounds2D30);
    assertEquals(rectangle, frame8);
    assertEquals(rectangle, frame9);
    assertEquals(rectangle, frame11);
    assertEquals(rectangle, frame12);
    assertEquals(rectangle, frame13);
    assertEquals(rectangle, frame15);
    assertEquals(rectangle, frame16);
    assertEquals(rectangle, frame17);
    assertEquals(size, size23.getSize());
    assertEquals(size, size27.getSize());
    assertEquals(size, size30.getSize());
    assertEquals(size, size32.getSize());
    assertEquals(size, bounds8.getSize());
    assertEquals(size, bounds14.getSize());
    assertEquals(size, ((Rectangle) bounds2D12).getSize());
    assertEquals(size, ((Rectangle) bounds2D19).getSize());
    assertSame(element, stringFlowElementMap.get(null));
  }

  /**
   * Test {@link BpmnAutoLayout#layout(FlowElementsContainer)}.
   * <ul>
   *   <li>Then {@link BpmnAutoLayout#BpmnAutoLayout(BpmnModel)} with bpmnModel is
   * {@link BpmnModel} (default constructor) Graph Model Cells size is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#layout(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test layout(FlowElementsContainer); then BpmnAutoLayout(BpmnModel) with bpmnModel is BpmnModel (default constructor) Graph Model Cells size is four")
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
    Object getResult = cells.get("2");
    assertTrue(getResult instanceof mxCell);
    Map<String, Object> stringObjectMap = bpmnAutoLayout.generatedVertices;
    assertEquals(1, stringObjectMap.size());
    Object getResult2 = stringObjectMap.get(null);
    assertTrue(getResult2 instanceof mxCell);
    assertTrue(model instanceof mxGraphModel);
    mxGeometry geometry = ((mxCell) getResult2).getGeometry();
    Rectangle rectangle = geometry.getRectangle();
    Rectangle2D bounds2D = rectangle.getBounds2D();
    assertTrue(bounds2D instanceof Rectangle);
    Rectangle bounds = rectangle.getBounds();
    Rectangle bounds2 = bounds.getBounds();
    Rectangle2D bounds2D2 = bounds2.getBounds2D();
    assertTrue(bounds2D2 instanceof Rectangle);
    Rectangle rectangle2 = graph.getGraphBounds().getRectangle();
    Rectangle bounds3 = rectangle2.getBounds();
    Rectangle bounds4 = bounds3.getBounds().getBounds().getBounds().getBounds();
    Rectangle2D bounds2D3 = bounds4.getBounds2D();
    assertTrue(bounds2D3 instanceof Rectangle);
    Rectangle2D bounds2D4 = bounds2D.getBounds2D();
    assertTrue(bounds2D4 instanceof Rectangle);
    Rectangle2D frame = rectangle.getFrame();
    Rectangle2D bounds2D5 = frame.getBounds2D();
    assertTrue(bounds2D5 instanceof Rectangle2D.Double);
    assertTrue(frame instanceof Rectangle2D.Double);
    Rectangle2D frame2 = rectangle2.getFrame();
    assertTrue(frame2 instanceof Rectangle2D.Double);
    Rectangle2D frame3 = bounds2.getFrame();
    assertTrue(frame3 instanceof Rectangle2D.Double);
    Rectangle2D frame4 = bounds2D.getFrame();
    assertTrue(frame4 instanceof Rectangle2D.Double);
    Rectangle2D frame5 = frame.getFrame();
    assertTrue(frame5 instanceof Rectangle2D.Double);
    Rectangle2D frame6 = frame2.getFrame();
    assertTrue(frame6 instanceof Rectangle2D.Double);
    mxGeometry geometry2 = ((mxCell) getResult).getGeometry();
    assertNull(geometry2.getOffset());
    assertNull(geometry2.getSourcePoint());
    assertNull(geometry2.getTargetPoint());
    assertNull(geometry2.getAlternateBounds());
    assertNull(geometry2.getPoints());
    Rectangle rectangle3 = geometry2.getRectangle();
    assertEquals(0, rectangle3.x);
    assertEquals(0, rectangle3.y);
    assertEquals(0.0d, geometry2.getX());
    assertEquals(0.0d, geometry2.getY());
    Point location = geometry.getPoint().getLocation().getLocation();
    assertEquals(100, location.getLocation().y);
    Point location2 = rectangle.getLocation().getLocation();
    assertEquals(100, location2.getLocation().y);
    Point location3 = bounds.getLocation();
    assertEquals(100, location3.getLocation().y);
    assertEquals(100, bounds2.getLocation().y);
    assertEquals(100, ((Rectangle) bounds2D).getLocation().y);
    assertEquals(100, bounds2.getBounds().y);
    assertEquals(100, bounds2D.getBounds().y);
    assertEquals(100, frame.getBounds().y);
    assertEquals(100, ((Rectangle) bounds2D2).y);
    assertEquals(100, ((Rectangle) bounds2D4).y);
    assertEquals(100.0d, location.getY());
    assertEquals(100.0d, location2.getY());
    assertEquals(100.0d, location3.getY());
    assertEquals(100.0d, bounds2.getY());
    assertEquals(100.0d, bounds2.getMinY());
    assertEquals(100.0d, bounds2D.getMinY());
    assertEquals(100.0d, frame.getMinY());
    assertEquals(100.0d, bounds2D.getY());
    assertEquals(100.0d, frame.getY());
    assertEquals(120.0d, bounds2.getCenterY());
    assertEquals(120.0d, bounds2D.getCenterY());
    assertEquals(120.0d, frame.getCenterY());
    Dimension size = rectangle2.getSize().getSize().getSize().getSize();
    assertEquals(140, size.getSize().height);
    Dimension size2 = bounds3.getSize();
    Dimension size3 = size2.getSize();
    Dimension size4 = size3.getSize();
    assertEquals(140, size4.getSize().height);
    assertEquals(140, size4.height);
    assertEquals(140, size3.height);
    assertEquals(140, size2.height);
    assertEquals(140, bounds4.getSize().height);
    assertEquals(140, bounds4.getBounds().height);
    assertEquals(140, ((Rectangle) bounds2D3).height);
    assertEquals(140.0d, size.getHeight());
    assertEquals(140.0d, size4.getHeight());
    assertEquals(140.0d, size3.getHeight());
    assertEquals(140.0d, size2.getHeight());
    assertEquals(140.0d, bounds4.getHeight());
    assertEquals(140.0d, frame6.getHeight());
    assertEquals(140.0d, bounds2.getMaxY());
    assertEquals(140.0d, bounds4.getMaxY());
    assertEquals(140.0d, bounds2D.getMaxY());
    assertEquals(140.0d, frame.getMaxY());
    assertEquals(140.0d, frame6.getMaxY());
    assertEquals(20.0d, geometry2.getCenterX());
    assertEquals(20.0d, geometry2.getCenterY());
    assertEquals(40, rectangle3.height);
    assertEquals(40, rectangle3.width);
    assertEquals(40.0d, geometry2.getHeight());
    assertEquals(40.0d, geometry2.getWidth());
    assertEquals(70.0d, bounds4.getCenterY());
    assertEquals(70.0d, frame6.getCenterY());
    assertFalse(geometry2.isRelative());
    assertTrue(cells.containsKey("1"));
    assertTrue(cells.containsKey("3"));
    assertEquals(rectangle, bounds2D5);
    assertEquals(rectangle, frame3);
    assertEquals(rectangle, frame4);
    assertEquals(rectangle, frame5);
    Dimension size5 = rectangle.getSize();
    assertEquals(size5, size5.getSize().getSize());
    assertEquals(size5, bounds.getSize().getSize());
    assertEquals(size5, bounds2.getSize());
    assertEquals(size5, ((Rectangle) bounds2D).getSize());
  }

  /**
   * Test {@link BpmnAutoLayout#ensureSequenceFlowIdSet(SequenceFlow)}.
   * <p>
   * Method under test:
   * {@link BpmnAutoLayout#ensureSequenceFlowIdSet(SequenceFlow)}
   */
  @Test
  @DisplayName("Test ensureSequenceFlowIdSet(SequenceFlow)")
  void testEnsureSequenceFlowIdSet() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    SequenceFlow sequenceFlow = new SequenceFlow("Source Ref", "Target Ref");
    sequenceFlow.setId("Sequence Flow");

    // Act
    bpmnAutoLayout.ensureSequenceFlowIdSet(sequenceFlow);

    // Assert that nothing has changed
    assertEquals("Sequence Flow", sequenceFlow.getId());
  }

  /**
   * Test {@link BpmnAutoLayout#ensureArtifactIdSet(Artifact)}.
   * <ul>
   *   <li>Given {@code Artifact}.</li>
   *   <li>Then {@link Association} (default constructor) Id is
   * {@code Artifact}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#ensureArtifactIdSet(Artifact)}
   */
  @Test
  @DisplayName("Test ensureArtifactIdSet(Artifact); given 'Artifact'; then Association (default constructor) Id is 'Artifact'")
  void testEnsureArtifactIdSet_givenArtifact_thenAssociationIdIsArtifact() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    Association artifact = new Association();
    artifact.setId("Artifact");

    // Act
    bpmnAutoLayout.ensureArtifactIdSet(artifact);

    // Assert that nothing has changed
    assertEquals("Artifact", artifact.getId());
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
   *   <li>Then calls
   * {@link mxGraph#insertVertex(Object, String, Object, double, double, double, double, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#createGatewayVertex(FlowElement)}
   */
  @Test
  @DisplayName("Test createGatewayVertex(FlowElement); then calls insertVertex(Object, String, Object, double, double, double, double, String)")
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
  void testOptimizeEdgePoints_givenMxPoint_whenArrayListAddMxPoint_thenReturnArrayList() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    ArrayList<mxPoint> unoptimizedPointsList = new ArrayList<>();
    unoptimizedPointsList.add(new mxPoint());

    // Act and Assert
    assertEquals(unoptimizedPointsList, bpmnAutoLayout.optimizeEdgePoints(unoptimizedPointsList));
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
  void testOptimizeEdgePoints_givenMxPoint_whenArrayListAddMxPoint_thenReturnArrayList2() {
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
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnAutoLayout#optimizeEdgePoints(List)}
   */
  @Test
  @DisplayName("Test optimizeEdgePoints(List); given mxPoint(); when ArrayList() add mxPoint(); then return size is two")
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
  void testOptimizeEdgePoints_whenArrayList_thenReturnEmpty() {
    // Arrange
    BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(new BpmnModel());

    // Act and Assert
    assertTrue(bpmnAutoLayout.optimizeEdgePoints(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)}
   * with {@code element}, {@code waypoints}.
   * <p>
   * Method under test:
   * {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)}
   */
  @Test
  @DisplayName("Test createDiagramInterchangeInformation(BaseElement, List) with 'element', 'waypoints'")
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
   * Test
   * {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)}
   * with {@code element}, {@code waypoints}.
   * <p>
   * Method under test:
   * {@link BpmnAutoLayout#createDiagramInterchangeInformation(BaseElement, List)}
   */
  @Test
  @DisplayName("Test createDiagramInterchangeInformation(BaseElement, List) with 'element', 'waypoints'")
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
   * Test
   * {@link BpmnAutoLayout#createDiagramInterchangeInformation(FlowElement, int, int, int, int)}
   * with {@code flowElement}, {@code x}, {@code y}, {@code width},
   * {@code height}.
   * <p>
   * Method under test:
   * {@link BpmnAutoLayout#createDiagramInterchangeInformation(FlowElement, int, int, int, int)}
   */
  @Test
  @DisplayName("Test createDiagramInterchangeInformation(FlowElement, int, int, int, int) with 'flowElement', 'x', 'y', 'width', 'height'")
  void testCreateDiagramInterchangeInformationWithFlowElementXYWidthHeight() {
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
   * Test {@link BpmnAutoLayout#translateNestedSubprocesses(Process)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnAutoLayout#translateNestedSubprocesses(Process)}
   */
  @Test
  @DisplayName("Test translateNestedSubprocesses(Process); then throw RuntimeException")
  void testTranslateNestedSubprocesses_thenThrowRuntimeException() {
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
}
