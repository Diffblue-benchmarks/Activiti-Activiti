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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.activiti.bpmn.BPMNLayout.Polygon;
import org.activiti.bpmn.BPMNLayout.Polyline;
import org.activiti.bpmn.BPMNLayout.TreeNode;
import org.activiti.bpmn.model.BpmnModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BPMNLayoutDiffblueTest {
  /**
   * Test {@link BPMNLayout#BPMNLayout(mxGraph)}.
   * <ul>
   *   <li>Given {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#BPMNLayout(mxGraph)}
   */
  @Test
  @DisplayName("Test new BPMNLayout(mxGraph); given PropertyChangeListener")
  void testNewBPMNLayout_givenPropertyChangeListener() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));

    // Act
    BPMNLayout actualBpmnLayout = new BPMNLayout(graph);

    // Assert
    assertNull(actualBpmnLayout.bpmnAutoLayout);
    assertEquals(20, actualBpmnLayout.getNodeDistance());
    assertEquals(40, actualBpmnLayout.getLevelDistance());
    assertFalse(actualBpmnLayout.isUseBoundingBox());
    assertFalse(actualBpmnLayout.isInvert());
    assertTrue(actualBpmnLayout.isHorizontal());
    assertTrue(actualBpmnLayout.isMoveTree());
    assertTrue(actualBpmnLayout.isResetEdges());
    assertTrue(actualBpmnLayout.isResizeParent());
    assertSame(graph, actualBpmnLayout.getGraph());
  }

  /**
   * Test {@link BPMNLayout#BPMNLayout(mxGraph, boolean)}.
   * <ul>
   *   <li>Given {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#BPMNLayout(mxGraph, boolean)}
   */
  @Test
  @DisplayName("Test new BPMNLayout(mxGraph, boolean); given PropertyChangeListener")
  void testNewBPMNLayout_givenPropertyChangeListener2() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));

    // Act
    BPMNLayout actualBpmnLayout = new BPMNLayout(graph, true);

    // Assert
    assertNull(actualBpmnLayout.bpmnAutoLayout);
    assertEquals(20, actualBpmnLayout.getNodeDistance());
    assertEquals(40, actualBpmnLayout.getLevelDistance());
    assertFalse(actualBpmnLayout.isUseBoundingBox());
    assertFalse(actualBpmnLayout.isInvert());
    assertTrue(actualBpmnLayout.isHorizontal());
    assertTrue(actualBpmnLayout.isMoveTree());
    assertTrue(actualBpmnLayout.isResetEdges());
    assertTrue(actualBpmnLayout.isResizeParent());
    assertSame(graph, actualBpmnLayout.getGraph());
  }

  /**
   * Test {@link BPMNLayout#BPMNLayout(mxGraph, boolean, boolean)}.
   * <ul>
   *   <li>Given {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#BPMNLayout(mxGraph, boolean, boolean)}
   */
  @Test
  @DisplayName("Test new BPMNLayout(mxGraph, boolean, boolean); given PropertyChangeListener")
  void testNewBPMNLayout_givenPropertyChangeListener3() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));

    // Act
    BPMNLayout actualBpmnLayout = new BPMNLayout(graph, true, true);

    // Assert
    assertNull(actualBpmnLayout.bpmnAutoLayout);
    assertEquals(20, actualBpmnLayout.getNodeDistance());
    assertEquals(40, actualBpmnLayout.getLevelDistance());
    assertFalse(actualBpmnLayout.isUseBoundingBox());
    assertTrue(actualBpmnLayout.isHorizontal());
    assertTrue(actualBpmnLayout.isInvert());
    assertTrue(actualBpmnLayout.isMoveTree());
    assertTrue(actualBpmnLayout.isResetEdges());
    assertTrue(actualBpmnLayout.isResizeParent());
    assertSame(graph, actualBpmnLayout.getGraph());
  }

  /**
   * Test {@link BPMNLayout#BPMNLayout(mxGraph)}.
   * <ul>
   *   <li>When {@link mxGraph#mxGraph()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#BPMNLayout(mxGraph)}
   */
  @Test
  @DisplayName("Test new BPMNLayout(mxGraph); when mxGraph()")
  void testNewBPMNLayout_whenMxGraph() {
    // Arrange
    mxGraph graph = new mxGraph();

    // Act
    BPMNLayout actualBpmnLayout = new BPMNLayout(graph);

    // Assert
    assertNull(actualBpmnLayout.bpmnAutoLayout);
    assertEquals(20, actualBpmnLayout.getNodeDistance());
    assertEquals(40, actualBpmnLayout.getLevelDistance());
    assertFalse(actualBpmnLayout.isUseBoundingBox());
    assertFalse(actualBpmnLayout.isInvert());
    assertTrue(actualBpmnLayout.isHorizontal());
    assertTrue(actualBpmnLayout.isMoveTree());
    assertTrue(actualBpmnLayout.isResetEdges());
    assertTrue(actualBpmnLayout.isResizeParent());
    assertSame(graph, actualBpmnLayout.getGraph());
  }

  /**
   * Test {@link BPMNLayout#BPMNLayout(mxGraph, boolean)}.
   * <ul>
   *   <li>When {@link mxGraph#mxGraph()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#BPMNLayout(mxGraph, boolean)}
   */
  @Test
  @DisplayName("Test new BPMNLayout(mxGraph, boolean); when mxGraph()")
  void testNewBPMNLayout_whenMxGraph2() {
    // Arrange
    mxGraph graph = new mxGraph();

    // Act
    BPMNLayout actualBpmnLayout = new BPMNLayout(graph, true);

    // Assert
    assertNull(actualBpmnLayout.bpmnAutoLayout);
    assertEquals(20, actualBpmnLayout.getNodeDistance());
    assertEquals(40, actualBpmnLayout.getLevelDistance());
    assertFalse(actualBpmnLayout.isUseBoundingBox());
    assertFalse(actualBpmnLayout.isInvert());
    assertTrue(actualBpmnLayout.isHorizontal());
    assertTrue(actualBpmnLayout.isMoveTree());
    assertTrue(actualBpmnLayout.isResetEdges());
    assertTrue(actualBpmnLayout.isResizeParent());
    assertSame(graph, actualBpmnLayout.getGraph());
  }

  /**
   * Test {@link BPMNLayout#BPMNLayout(mxGraph, boolean, boolean)}.
   * <ul>
   *   <li>When {@link mxGraph#mxGraph()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#BPMNLayout(mxGraph, boolean, boolean)}
   */
  @Test
  @DisplayName("Test new BPMNLayout(mxGraph, boolean, boolean); when mxGraph()")
  void testNewBPMNLayout_whenMxGraph3() {
    // Arrange
    mxGraph graph = new mxGraph();

    // Act
    BPMNLayout actualBpmnLayout = new BPMNLayout(graph, true, true);

    // Assert
    assertNull(actualBpmnLayout.bpmnAutoLayout);
    assertEquals(20, actualBpmnLayout.getNodeDistance());
    assertEquals(40, actualBpmnLayout.getLevelDistance());
    assertFalse(actualBpmnLayout.isUseBoundingBox());
    assertTrue(actualBpmnLayout.isHorizontal());
    assertTrue(actualBpmnLayout.isInvert());
    assertTrue(actualBpmnLayout.isMoveTree());
    assertTrue(actualBpmnLayout.isResetEdges());
    assertTrue(actualBpmnLayout.isResizeParent());
    assertSame(graph, actualBpmnLayout.getGraph());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNLayout#setHorizontal(boolean)}
   *   <li>{@link BPMNLayout#setInvert(boolean)}
   *   <li>{@link BPMNLayout#setLevelDistance(int)}
   *   <li>{@link BPMNLayout#setMoveTree(boolean)}
   *   <li>{@link BPMNLayout#setNodeDistance(int)}
   *   <li>{@link BPMNLayout#setResetEdges(boolean)}
   *   <li>{@link BPMNLayout#setResizeParent(boolean)}
   *   <li>{@link BPMNLayout#setBpmnAutoLayout(BpmnAutoLayout)}
   *   <li>{@link BPMNLayout#getGraph()}
   *   <li>{@link BPMNLayout#getLevelDistance()}
   *   <li>{@link BPMNLayout#getNodeDistance()}
   *   <li>{@link BPMNLayout#isHorizontal()}
   *   <li>{@link BPMNLayout#isInvert()}
   *   <li>{@link BPMNLayout#isMoveTree()}
   *   <li>{@link BPMNLayout#isResetEdges()}
   *   <li>{@link BPMNLayout#isResizeParent()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    mxGraph graph = new mxGraph();
    BPMNLayout bpmnLayout = new BPMNLayout(graph);

    // Act
    bpmnLayout.setHorizontal(true);
    bpmnLayout.setInvert(true);
    bpmnLayout.setLevelDistance(1);
    bpmnLayout.setMoveTree(true);
    bpmnLayout.setNodeDistance(1);
    bpmnLayout.setResetEdges(true);
    bpmnLayout.setResizeParent(true);
    bpmnLayout.setBpmnAutoLayout(new BpmnAutoLayout(new BpmnModel()));
    mxGraph actualGraph = bpmnLayout.getGraph();
    int actualLevelDistance = bpmnLayout.getLevelDistance();
    int actualNodeDistance = bpmnLayout.getNodeDistance();
    boolean actualIsHorizontalResult = bpmnLayout.isHorizontal();
    boolean actualIsInvertResult = bpmnLayout.isInvert();
    boolean actualIsMoveTreeResult = bpmnLayout.isMoveTree();
    boolean actualIsResetEdgesResult = bpmnLayout.isResetEdges();

    // Assert that nothing has changed
    assertEquals(1, actualLevelDistance);
    assertEquals(1, actualNodeDistance);
    assertTrue(actualIsHorizontalResult);
    assertTrue(actualIsInvertResult);
    assertTrue(actualIsMoveTreeResult);
    assertTrue(actualIsResetEdgesResult);
    assertTrue(bpmnLayout.isResizeParent());
    assertSame(graph, actualGraph);
  }

  /**
   * Test {@link BPMNLayout#isVertexIgnored(Object)}.
   * <ul>
   *   <li>Given {@link BPMNLayout#BPMNLayout(mxGraph)} with graph is
   * {@link mxGraph#mxGraph()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#isVertexIgnored(Object)}
   */
  @Test
  @DisplayName("Test isVertexIgnored(Object); given BPMNLayout(mxGraph) with graph is mxGraph(); then return 'true'")
  void testIsVertexIgnored_givenBPMNLayoutWithGraphIsMxGraph_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new BPMNLayout(new mxGraph())).isVertexIgnored("Vertex"));
  }

  /**
   * Test {@link BPMNLayout#isVertexIgnored(Object)}.
   * <ul>
   *   <li>Given {@link mxGraph#mxGraph()} addPropertyChangeListener
   * {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#isVertexIgnored(Object)}
   */
  @Test
  @DisplayName("Test isVertexIgnored(Object); given mxGraph() addPropertyChangeListener PropertyChangeListener")
  void testIsVertexIgnored_givenMxGraphAddPropertyChangeListenerPropertyChangeListener() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));

    // Act and Assert
    assertTrue((new BPMNLayout(graph)).isVertexIgnored("Vertex"));
  }

  /**
   * Test {@link BPMNLayout#execute(Object)}.
   * <ul>
   *   <li>Given {@link mxGraph} {@link mxGraph#getModel()} return
   * {@link mxGraphModel#mxGraphModel()}.</li>
   *   <li>Then calls {@link mxGraph#findTreeRoots(Object, boolean, boolean)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#execute(Object)}
   */
  @Test
  @DisplayName("Test execute(Object); given mxGraph getModel() return mxGraphModel(); then calls findTreeRoots(Object, boolean, boolean)")
  void testExecute_givenMxGraphGetModelReturnMxGraphModel_thenCallsFindTreeRoots() {
    // Arrange
    mxGraph graph = mock(mxGraph.class);
    when(graph.getModel()).thenReturn(new mxGraphModel());
    when(graph.findTreeRoots(Mockito.<Object>any(), anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());

    // Act
    (new BPMNLayout(graph)).execute("Parent");

    // Assert that nothing has changed
    verify(graph).findTreeRoots(isA(Object.class), eq(true), eq(false));
    verify(graph).getModel();
  }

  /**
   * Test {@link BPMNLayout#execute(Object)}.
   * <ul>
   *   <li>Given {@link mxGraph} {@link mxGraph#getModel()} return
   * {@link mxGraphModel#mxGraphModel()}.</li>
   *   <li>Then calls {@link mxGraph#findTreeRoots(Object, boolean, boolean)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#execute(Object)}
   */
  @Test
  @DisplayName("Test execute(Object); given mxGraph getModel() return mxGraphModel(); then calls findTreeRoots(Object, boolean, boolean)")
  void testExecute_givenMxGraphGetModelReturnMxGraphModel_thenCallsFindTreeRoots2() {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("42");
    mxGraph graph = mock(mxGraph.class);
    when(graph.getModel()).thenReturn(new mxGraphModel());
    when(graph.findTreeRoots(Mockito.<Object>any(), anyBoolean(), anyBoolean())).thenReturn(objectList);

    // Act
    (new BPMNLayout(graph)).execute("Parent");

    // Assert
    verify(graph).findTreeRoots(isA(Object.class), eq(true), eq(false));
    verify(graph, atLeast(1)).getModel();
  }

  /**
   * Test {@link BPMNLayout#execute(Object)}.
   * <ul>
   *   <li>Given {@link mxGraph} {@link mxGraph#getView()} return
   * {@link mxGraphView#mxGraphView(mxGraph)} with graph is
   * {@link mxGraph#mxGraph()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#execute(Object)}
   */
  @Test
  @DisplayName("Test execute(Object); given mxGraph getView() return mxGraphView(mxGraph) with graph is mxGraph()")
  void testExecute_givenMxGraphGetViewReturnMxGraphViewWithGraphIsMxGraph() {
    // Arrange
    mxIGraphModel mxIGraphModel = mock(mxIGraphModel.class);
    when(mxIGraphModel.getTerminal(Mockito.<Object>any(), anyBoolean())).thenReturn("Terminal");
    when(mxIGraphModel.isEdge(Mockito.<Object>any())).thenReturn(true);
    when(mxIGraphModel.setGeometry(Mockito.<Object>any(), Mockito.<mxGeometry>any())).thenReturn(new mxGeometry());
    when(mxIGraphModel.getGeometry(Mockito.<Object>any())).thenReturn(new mxGeometry());
    when(mxIGraphModel.isVertex(Mockito.<Object>any())).thenReturn(true);
    when(mxIGraphModel.getParent(Mockito.<Object>any())).thenReturn("Parent");
    doNothing().when(mxIGraphModel).beginUpdate();
    doNothing().when(mxIGraphModel).endUpdate();

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("42");
    mxGraph graph = mock(mxGraph.class);
    when(graph.isCellCollapsed(Mockito.<Object>any())).thenReturn(true);
    when(graph.getView()).thenReturn(new mxGraphView(new mxGraph()));
    when(graph.isCellMovable(Mockito.<Object>any())).thenReturn(true);
    when(graph.getStartSize(Mockito.<Object>any())).thenReturn(new mxRectangle());
    when(graph.getGridSize()).thenReturn(1);
    when(graph.getCurrentRoot()).thenReturn("Current Root");
    when(graph.getDefaultParent()).thenReturn("Default Parent");
    when(graph.getEdges(Mockito.<Object>any(), Mockito.<Object>any(), anyBoolean(), anyBoolean(), anyBoolean()))
        .thenReturn(new Object[]{"Edges"});
    when(graph.isSwimlane(Mockito.<Object>any())).thenReturn(false);
    when(graph.getConnections(Mockito.<Object>any())).thenReturn(new Object[]{"Connections"});
    when(graph.isCellVisible(Mockito.<Object>any())).thenReturn(true);
    when(graph.getModel()).thenReturn(mxIGraphModel);
    when(graph.findTreeRoots(Mockito.<Object>any(), anyBoolean(), anyBoolean())).thenReturn(objectList);

    // Act
    (new BPMNLayout(graph)).execute("Parent");

    // Assert
    verify(mxIGraphModel).beginUpdate();
    verify(mxIGraphModel).endUpdate();
    verify(mxIGraphModel, atLeast(1)).getGeometry(Mockito.<Object>any());
    verify(mxIGraphModel, atLeast(1)).getParent(isA(Object.class));
    verify(mxIGraphModel, atLeast(1)).getTerminal(isA(Object.class), anyBoolean());
    verify(mxIGraphModel).isEdge(isA(Object.class));
    verify(mxIGraphModel).isVertex(isA(Object.class));
    verify(mxIGraphModel, atLeast(1)).setGeometry(Mockito.<Object>any(), Mockito.<mxGeometry>any());
    verify(graph).findTreeRoots(isA(Object.class), eq(true), eq(false));
    verify(graph).getConnections(isA(Object.class));
    verify(graph, atLeast(1)).getCurrentRoot();
    verify(graph, atLeast(1)).getDefaultParent();
    verify(graph).getEdges(isA(Object.class), isA(Object.class), eq(false), eq(true), eq(false));
    verify(graph).getGridSize();
    verify(graph, atLeast(1)).getModel();
    verify(graph).getStartSize(isA(Object.class));
    verify(graph, atLeast(1)).getView();
    verify(graph).isCellCollapsed(isA(Object.class));
    verify(graph, atLeast(1)).isCellMovable(isA(Object.class));
    verify(graph, atLeast(1)).isCellVisible(Mockito.<Object>any());
    verify(graph).isSwimlane(isA(Object.class));
  }

  /**
   * Test {@link BPMNLayout#execute(Object)}.
   * <ul>
   *   <li>Given {@link mxGraph} {@link mxGraph#getView()} return
   * {@link mxGraphView#mxGraphView(mxGraph)} with graph is {@link mxGraph}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#execute(Object)}
   */
  @Test
  @DisplayName("Test execute(Object); given mxGraph getView() return mxGraphView(mxGraph) with graph is mxGraph")
  void testExecute_givenMxGraphGetViewReturnMxGraphViewWithGraphIsMxGraph2() {
    // Arrange
    mxIGraphModel mxIGraphModel = mock(mxIGraphModel.class);
    when(mxIGraphModel.getTerminal(Mockito.<Object>any(), anyBoolean())).thenReturn("Terminal");
    when(mxIGraphModel.isEdge(Mockito.<Object>any())).thenReturn(true);
    when(mxIGraphModel.setGeometry(Mockito.<Object>any(), Mockito.<mxGeometry>any())).thenReturn(new mxGeometry());
    when(mxIGraphModel.getGeometry(Mockito.<Object>any())).thenReturn(new mxGeometry());
    when(mxIGraphModel.isVertex(Mockito.<Object>any())).thenReturn(true);
    when(mxIGraphModel.getParent(Mockito.<Object>any())).thenReturn("Parent");
    doNothing().when(mxIGraphModel).beginUpdate();
    doNothing().when(mxIGraphModel).endUpdate();

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("42");
    mxGraph graph = mock(mxGraph.class);
    when(graph.getModel()).thenReturn(new mxGraphModel());
    mxGraphView mxGraphView = new mxGraphView(graph);
    mxGraph graph2 = mock(mxGraph.class);
    when(graph2.isCellCollapsed(Mockito.<Object>any())).thenReturn(true);
    when(graph2.getView()).thenReturn(mxGraphView);
    when(graph2.isCellMovable(Mockito.<Object>any())).thenReturn(true);
    when(graph2.getStartSize(Mockito.<Object>any())).thenReturn(new mxRectangle());
    when(graph2.getGridSize()).thenReturn(1);
    when(graph2.getCurrentRoot()).thenReturn("Current Root");
    when(graph2.getDefaultParent()).thenReturn("Default Parent");
    when(graph2.getEdges(Mockito.<Object>any(), Mockito.<Object>any(), anyBoolean(), anyBoolean(), anyBoolean()))
        .thenReturn(new Object[]{"Edges"});
    when(graph2.isSwimlane(Mockito.<Object>any())).thenReturn(false);
    when(graph2.getConnections(Mockito.<Object>any())).thenReturn(new Object[]{"Connections"});
    when(graph2.isCellVisible(Mockito.<Object>any())).thenReturn(true);
    when(graph2.getModel()).thenReturn(mxIGraphModel);
    when(graph2.findTreeRoots(Mockito.<Object>any(), anyBoolean(), anyBoolean())).thenReturn(objectList);

    // Act
    (new BPMNLayout(graph2)).execute("Parent");

    // Assert
    verify(mxIGraphModel).beginUpdate();
    verify(mxIGraphModel).endUpdate();
    verify(mxIGraphModel, atLeast(1)).getGeometry(Mockito.<Object>any());
    verify(mxIGraphModel, atLeast(1)).getParent(isA(Object.class));
    verify(mxIGraphModel, atLeast(1)).getTerminal(isA(Object.class), anyBoolean());
    verify(mxIGraphModel).isEdge(isA(Object.class));
    verify(mxIGraphModel).isVertex(isA(Object.class));
    verify(mxIGraphModel, atLeast(1)).setGeometry(Mockito.<Object>any(), Mockito.<mxGeometry>any());
    verify(graph2).findTreeRoots(isA(Object.class), eq(true), eq(false));
    verify(graph2).getConnections(isA(Object.class));
    verify(graph2, atLeast(1)).getCurrentRoot();
    verify(graph2, atLeast(1)).getDefaultParent();
    verify(graph2).getEdges(isA(Object.class), isA(Object.class), eq(false), eq(true), eq(false));
    verify(graph2).getGridSize();
    verify(graph).getModel();
    verify(graph2, atLeast(1)).getModel();
    verify(graph2).getStartSize(isA(Object.class));
    verify(graph2, atLeast(1)).getView();
    verify(graph2).isCellCollapsed(isA(Object.class));
    verify(graph2, atLeast(1)).isCellMovable(isA(Object.class));
    verify(graph2, atLeast(1)).isCellVisible(Mockito.<Object>any());
    verify(graph2).isSwimlane(isA(Object.class));
  }

  /**
   * Test {@link BPMNLayout#execute(Object)}.
   * <ul>
   *   <li>Given {@link mxGraph} {@link mxGraph#isCellCollapsed(Object)} return
   * {@code false}.</li>
   *   <li>Then calls {@link mxIGraphModel#getGeometry(Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#execute(Object)}
   */
  @Test
  @DisplayName("Test execute(Object); given mxGraph isCellCollapsed(Object) return 'false'; then calls getGeometry(Object)")
  void testExecute_givenMxGraphIsCellCollapsedReturnFalse_thenCallsGetGeometry() {
    // Arrange
    mxIGraphModel mxIGraphModel = mock(mxIGraphModel.class);
    when(mxIGraphModel.getTerminal(Mockito.<Object>any(), anyBoolean())).thenReturn("Terminal");
    when(mxIGraphModel.isEdge(Mockito.<Object>any())).thenReturn(true);
    when(mxIGraphModel.setGeometry(Mockito.<Object>any(), Mockito.<mxGeometry>any())).thenReturn(new mxGeometry());
    when(mxIGraphModel.getGeometry(Mockito.<Object>any())).thenReturn(new mxGeometry());
    when(mxIGraphModel.isVertex(Mockito.<Object>any())).thenReturn(true);
    when(mxIGraphModel.getParent(Mockito.<Object>any())).thenReturn("Parent");
    doNothing().when(mxIGraphModel).beginUpdate();
    doNothing().when(mxIGraphModel).endUpdate();

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("42");
    mxGraph graph = mock(mxGraph.class);
    when(graph.isCellCollapsed(Mockito.<Object>any())).thenReturn(false);
    when(graph.getView()).thenReturn(new mxGraphView(new mxGraph()));
    when(graph.isCellMovable(Mockito.<Object>any())).thenReturn(true);
    when(graph.getStartSize(Mockito.<Object>any())).thenReturn(new mxRectangle());
    when(graph.getGridSize()).thenReturn(1);
    when(graph.getCurrentRoot()).thenReturn("Current Root");
    when(graph.getDefaultParent()).thenReturn("Default Parent");
    when(graph.getEdges(Mockito.<Object>any(), Mockito.<Object>any(), anyBoolean(), anyBoolean(), anyBoolean()))
        .thenReturn(new Object[]{"Edges"});
    when(graph.isSwimlane(Mockito.<Object>any())).thenReturn(false);
    when(graph.getConnections(Mockito.<Object>any())).thenReturn(new Object[]{"Connections"});
    when(graph.isCellVisible(Mockito.<Object>any())).thenReturn(true);
    when(graph.getModel()).thenReturn(mxIGraphModel);
    when(graph.findTreeRoots(Mockito.<Object>any(), anyBoolean(), anyBoolean())).thenReturn(objectList);

    // Act
    (new BPMNLayout(graph)).execute("Parent");

    // Assert
    verify(mxIGraphModel).beginUpdate();
    verify(mxIGraphModel).endUpdate();
    verify(mxIGraphModel, atLeast(1)).getGeometry(Mockito.<Object>any());
    verify(mxIGraphModel, atLeast(1)).getParent(isA(Object.class));
    verify(mxIGraphModel, atLeast(1)).getTerminal(isA(Object.class), anyBoolean());
    verify(mxIGraphModel).isEdge(isA(Object.class));
    verify(mxIGraphModel).isVertex(isA(Object.class));
    verify(mxIGraphModel, atLeast(1)).setGeometry(Mockito.<Object>any(), Mockito.<mxGeometry>any());
    verify(graph).findTreeRoots(isA(Object.class), eq(true), eq(false));
    verify(graph).getConnections(isA(Object.class));
    verify(graph, atLeast(1)).getCurrentRoot();
    verify(graph, atLeast(1)).getDefaultParent();
    verify(graph).getEdges(isA(Object.class), isA(Object.class), eq(false), eq(true), eq(false));
    verify(graph).getGridSize();
    verify(graph, atLeast(1)).getModel();
    verify(graph).getStartSize(isA(Object.class));
    verify(graph, atLeast(1)).getView();
    verify(graph).isCellCollapsed(isA(Object.class));
    verify(graph, atLeast(1)).isCellMovable(isA(Object.class));
    verify(graph, atLeast(1)).isCellVisible(Mockito.<Object>any());
    verify(graph).isSwimlane(isA(Object.class));
  }

  /**
   * Test {@link BPMNLayout#execute(Object)}.
   * <ul>
   *   <li>Given {@link mxGraph} {@link mxGraph#isSwimlane(Object)} return
   * {@code true}.</li>
   *   <li>Then calls {@link mxIGraphModel#beginUpdate()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#execute(Object)}
   */
  @Test
  @DisplayName("Test execute(Object); given mxGraph isSwimlane(Object) return 'true'; then calls beginUpdate()")
  void testExecute_givenMxGraphIsSwimlaneReturnTrue_thenCallsBeginUpdate() {
    // Arrange
    mxIGraphModel mxIGraphModel = mock(mxIGraphModel.class);
    when(mxIGraphModel.isVertex(Mockito.<Object>any())).thenReturn(true);
    when(mxIGraphModel.getParent(Mockito.<Object>any())).thenReturn("Parent");
    doNothing().when(mxIGraphModel).beginUpdate();
    doNothing().when(mxIGraphModel).endUpdate();

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("42");
    mxGraph graph = mock(mxGraph.class);
    when(graph.isSwimlane(Mockito.<Object>any())).thenReturn(true);
    when(graph.isCellVisible(Mockito.<Object>any())).thenReturn(true);
    when(graph.getModel()).thenReturn(mxIGraphModel);
    when(graph.findTreeRoots(Mockito.<Object>any(), anyBoolean(), anyBoolean())).thenReturn(objectList);

    // Act
    (new BPMNLayout(graph)).execute("Parent");

    // Assert
    verify(mxIGraphModel).beginUpdate();
    verify(mxIGraphModel).endUpdate();
    verify(mxIGraphModel).getParent(isA(Object.class));
    verify(mxIGraphModel).isVertex(isA(Object.class));
    verify(graph).findTreeRoots(isA(Object.class), eq(true), eq(false));
    verify(graph, atLeast(1)).getModel();
    verify(graph).isCellVisible(isA(Object.class));
    verify(graph).isSwimlane(isA(Object.class));
  }

  /**
   * Test {@link BPMNLayout#execute(Object)}.
   * <ul>
   *   <li>Given {@link mxGraphModel#mxGraphModel()} addListener {@code 42} and
   * {@link mxIEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#execute(Object)}
   */
  @Test
  @DisplayName("Test execute(Object); given mxGraphModel() addListener '42' and mxIEventListener")
  void testExecute_givenMxGraphModelAddListener42AndMxIEventListener() {
    // Arrange
    mxGraphModel mxGraphModel = new mxGraphModel();
    mxGraphModel.addListener("42", mock(mxEventSource.mxIEventListener.class));

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("42");
    mxGraph graph = mock(mxGraph.class);
    when(graph.getModel()).thenReturn(mxGraphModel);
    when(graph.findTreeRoots(Mockito.<Object>any(), anyBoolean(), anyBoolean())).thenReturn(objectList);

    // Act
    (new BPMNLayout(graph)).execute("Parent");

    // Assert
    verify(graph).findTreeRoots(isA(Object.class), eq(true), eq(false));
    verify(graph, atLeast(1)).getModel();
  }

  /**
   * Test {@link BPMNLayout#isBoundaryEvent(Object)}.
   * <ul>
   *   <li>Given {@code boundary-event-}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#isBoundaryEvent(Object)}
   */
  @Test
  @DisplayName("Test isBoundaryEvent(Object); given 'boundary-event-'; then return 'true'")
  void testIsBoundaryEvent_givenBoundaryEvent_thenReturnTrue() {
    // Arrange
    BPMNLayout bpmnLayout = new BPMNLayout(new mxGraph());

    mxCell mxCell = new mxCell();
    mxCell.setId("boundary-event-");

    // Act and Assert
    assertTrue(bpmnLayout.isBoundaryEvent(mxCell));
  }

  /**
   * Test {@link BPMNLayout#isBoundaryEvent(Object)}.
   * <ul>
   *   <li>Given {@link mxGraph#mxGraph()} addPropertyChangeListener
   * {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#isBoundaryEvent(Object)}
   */
  @Test
  @DisplayName("Test isBoundaryEvent(Object); given mxGraph() addPropertyChangeListener PropertyChangeListener")
  void testIsBoundaryEvent_givenMxGraphAddPropertyChangeListenerPropertyChangeListener() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));

    // Act and Assert
    assertFalse((new BPMNLayout(graph)).isBoundaryEvent("Obj"));
  }

  /**
   * Test {@link BPMNLayout#isBoundaryEvent(Object)}.
   * <ul>
   *   <li>When {@code Obj}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#isBoundaryEvent(Object)}
   */
  @Test
  @DisplayName("Test isBoundaryEvent(Object); when 'Obj'; then return 'false'")
  void testIsBoundaryEvent_whenObj_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BPMNLayout(new mxGraph())).isBoundaryEvent("Obj"));
  }

  /**
   * Test {@link BPMNLayout#moveNode(TreeNode, double, double)}.
   * <ul>
   *   <li>Given {@link BPMNLayout#BPMNLayout(mxGraph)} with graph is
   * {@link mxGraph#mxGraph()}.</li>
   *   <li>Then {@link TreeNode#TreeNode(Object)} with {@code Cell}
   * {@link TreeNode#x} is ten.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNLayout#moveNode(BPMNLayout.TreeNode, double, double)}
   */
  @Test
  @DisplayName("Test moveNode(TreeNode, double, double); given BPMNLayout(mxGraph) with graph is mxGraph(); then TreeNode(Object) with 'Cell' x is ten")
  void testMoveNode_givenBPMNLayoutWithGraphIsMxGraph_thenTreeNodeWithCellXIsTen() {
    // Arrange
    BPMNLayout bpmnLayout = new BPMNLayout(new mxGraph());
    BPMNLayout.TreeNode node = new BPMNLayout.TreeNode("Cell");

    // Act
    bpmnLayout.moveNode(node, 10.0d, 10.0d);

    // Assert
    assertEquals(10.0d, node.x);
    assertEquals(10.0d, node.y);
  }

  /**
   * Test {@link BPMNLayout#moveNode(TreeNode, double, double)}.
   * <ul>
   *   <li>Given {@link mxGraph#mxGraph()} addPropertyChangeListener
   * {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNLayout#moveNode(BPMNLayout.TreeNode, double, double)}
   */
  @Test
  @DisplayName("Test moveNode(TreeNode, double, double); given mxGraph() addPropertyChangeListener PropertyChangeListener")
  void testMoveNode_givenMxGraphAddPropertyChangeListenerPropertyChangeListener() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));
    BPMNLayout bpmnLayout = new BPMNLayout(graph);
    BPMNLayout.TreeNode node = new BPMNLayout.TreeNode("Cell");

    // Act
    bpmnLayout.moveNode(node, 10.0d, 10.0d);

    // Assert
    assertEquals(10.0d, node.x);
    assertEquals(10.0d, node.y);
  }

  /**
   * Test {@link BPMNLayout#dfs(Object, Object, Set)}.
   * <ul>
   *   <li>Given {@link BPMNLayout#BPMNLayout(mxGraph)} with graph is
   * {@link mxGraph#mxGraph()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#dfs(Object, Object, Set)}
   */
  @Test
  @DisplayName("Test dfs(Object, Object, Set); given BPMNLayout(mxGraph) with graph is mxGraph(); then return 'null'")
  void testDfs_givenBPMNLayoutWithGraphIsMxGraph_thenReturnNull() {
    // Arrange
    BPMNLayout bpmnLayout = new BPMNLayout(new mxGraph());

    // Act and Assert
    assertNull(bpmnLayout.dfs("Cell", "Parent", new HashSet<>()));
  }

  /**
   * Test {@link BPMNLayout#dfs(Object, Object, Set)}.
   * <ul>
   *   <li>Given {@link mxGraph#mxGraph()} addPropertyChangeListener
   * {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#dfs(Object, Object, Set)}
   */
  @Test
  @DisplayName("Test dfs(Object, Object, Set); given mxGraph() addPropertyChangeListener PropertyChangeListener")
  void testDfs_givenMxGraphAddPropertyChangeListenerPropertyChangeListener() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));
    BPMNLayout bpmnLayout = new BPMNLayout(graph);

    // Act and Assert
    assertNull(bpmnLayout.dfs("Cell", "Parent", new HashSet<>()));
  }

  /**
   * Test {@link BPMNLayout#layout(TreeNode)}.
   * <p>
   * Method under test: {@link BPMNLayout#layout(BPMNLayout.TreeNode)}
   */
  @Test
  @DisplayName("Test layout(TreeNode)")
  void testLayout() {
    // Arrange
    BPMNLayout bpmnLayout = new BPMNLayout(new mxGraph());
    BPMNLayout.TreeNode node = new BPMNLayout.TreeNode("Cell");

    // Act
    bpmnLayout.layout(node);

    // Assert
    BPMNLayout.Polygon polygon = node.contour;
    BPMNLayout.Polyline polyline = polygon.lowerTail;
    assertNull(polyline.next);
    BPMNLayout.Polyline polyline2 = polygon.upperHead;
    assertNull(polyline2.next);
    assertEquals(-40.0d, polyline.dy);
    assertEquals(0.0d, polyline.dx);
    BPMNLayout.Polyline polyline3 = polygon.lowerHead;
    assertEquals(0.0d, polyline3.dy);
    assertEquals(0.0d, polyline2.dy);
    assertEquals(40.0d, polyline3.dx);
    assertEquals(40.0d, polyline2.dx);
    assertSame(polyline, polyline3.next);
    assertSame(polyline2, polygon.upperTail);
  }

  /**
   * Test {@link BPMNLayout#layout(TreeNode)}.
   * <ul>
   *   <li>Given {@link mxGraph#mxGraph()} addPropertyChangeListener
   * {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#layout(BPMNLayout.TreeNode)}
   */
  @Test
  @DisplayName("Test layout(TreeNode); given mxGraph() addPropertyChangeListener PropertyChangeListener")
  void testLayout_givenMxGraphAddPropertyChangeListenerPropertyChangeListener() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));
    BPMNLayout bpmnLayout = new BPMNLayout(graph);
    BPMNLayout.TreeNode node = new BPMNLayout.TreeNode("Cell");

    // Act
    bpmnLayout.layout(node);

    // Assert
    BPMNLayout.Polygon polygon = node.contour;
    BPMNLayout.Polyline polyline = polygon.lowerTail;
    assertNull(polyline.next);
    BPMNLayout.Polyline polyline2 = polygon.upperHead;
    assertNull(polyline2.next);
    assertEquals(-40.0d, polyline.dy);
    assertEquals(0.0d, polyline.dx);
    BPMNLayout.Polyline polyline3 = polygon.lowerHead;
    assertEquals(0.0d, polyline3.dy);
    assertEquals(0.0d, polyline2.dy);
    assertEquals(40.0d, polyline3.dx);
    assertEquals(40.0d, polyline2.dx);
    assertSame(polyline, polyline3.next);
    assertSame(polyline2, polygon.upperTail);
  }

  /**
   * Test {@link BPMNLayout#layoutLeaf(TreeNode)}.
   * <p>
   * Method under test: {@link BPMNLayout#layoutLeaf(BPMNLayout.TreeNode)}
   */
  @Test
  @DisplayName("Test layoutLeaf(TreeNode)")
  void testLayoutLeaf() {
    // Arrange
    BPMNLayout bpmnLayout = new BPMNLayout(new mxGraph());
    BPMNLayout.TreeNode node = new BPMNLayout.TreeNode("Cell");

    // Act
    bpmnLayout.layoutLeaf(node);

    // Assert
    BPMNLayout.Polygon polygon = node.contour;
    BPMNLayout.Polyline polyline = polygon.lowerTail;
    assertNull(polyline.next);
    BPMNLayout.Polyline polyline2 = polygon.upperHead;
    assertNull(polyline2.next);
    assertEquals(-40.0d, polyline.dy);
    assertEquals(0.0d, polyline.dx);
    BPMNLayout.Polyline polyline3 = polygon.lowerHead;
    assertEquals(0.0d, polyline3.dy);
    assertEquals(0.0d, polyline2.dy);
    assertEquals(40.0d, polyline3.dx);
    assertEquals(40.0d, polyline2.dx);
    assertSame(polyline, polyline3.next);
    assertSame(polyline2, polygon.upperTail);
  }

  /**
   * Test {@link BPMNLayout#layoutLeaf(TreeNode)}.
   * <ul>
   *   <li>Given {@link mxGraph#mxGraph()} addPropertyChangeListener
   * {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#layoutLeaf(BPMNLayout.TreeNode)}
   */
  @Test
  @DisplayName("Test layoutLeaf(TreeNode); given mxGraph() addPropertyChangeListener PropertyChangeListener")
  void testLayoutLeaf_givenMxGraphAddPropertyChangeListenerPropertyChangeListener() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));
    BPMNLayout bpmnLayout = new BPMNLayout(graph);
    BPMNLayout.TreeNode node = new BPMNLayout.TreeNode("Cell");

    // Act
    bpmnLayout.layoutLeaf(node);

    // Assert
    BPMNLayout.Polygon polygon = node.contour;
    BPMNLayout.Polyline polyline = polygon.lowerTail;
    assertNull(polyline.next);
    BPMNLayout.Polyline polyline2 = polygon.upperHead;
    assertNull(polyline2.next);
    assertEquals(-40.0d, polyline.dy);
    assertEquals(0.0d, polyline.dx);
    BPMNLayout.Polyline polyline3 = polygon.lowerHead;
    assertEquals(0.0d, polyline3.dy);
    assertEquals(0.0d, polyline2.dy);
    assertEquals(40.0d, polyline3.dx);
    assertEquals(40.0d, polyline2.dx);
    assertSame(polyline, polyline3.next);
    assertSame(polyline2, polygon.upperTail);
  }

  /**
   * Test
   * {@link BPMNLayout#offset(double, double, double, double, double, double)}.
   * <ul>
   *   <li>Given {@link mxGraph#mxGraph()} addPropertyChangeListener
   * {@link PropertyChangeListener}.</li>
   *   <li>When ten.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNLayout#offset(double, double, double, double, double, double)}
   */
  @Test
  @DisplayName("Test offset(double, double, double, double, double, double); given mxGraph() addPropertyChangeListener PropertyChangeListener; when ten")
  void testOffset_givenMxGraphAddPropertyChangeListenerPropertyChangeListener_whenTen() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));

    // Act and Assert
    assertEquals(0.0d, (new BPMNLayout(graph)).offset(10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d));
  }

  /**
   * Test
   * {@link BPMNLayout#offset(double, double, double, double, double, double)}.
   * <ul>
   *   <li>When {@code -1.0E-10}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNLayout#offset(double, double, double, double, double, double)}
   */
  @Test
  @DisplayName("Test offset(double, double, double, double, double, double); when '-1.0E-10'")
  void testOffset_when10e10() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new BPMNLayout(new mxGraph())).offset(-1.0E-10d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d));
    assertEquals(0.0d, (new BPMNLayout(new mxGraph())).offset(1.0E-10d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d));
  }

  /**
   * Test
   * {@link BPMNLayout#offset(double, double, double, double, double, double)}.
   * <ul>
   *   <li>When ten.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNLayout#offset(double, double, double, double, double, double)}
   */
  @Test
  @DisplayName("Test offset(double, double, double, double, double, double); when ten")
  void testOffset_whenTen() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new BPMNLayout(new mxGraph())).offset(10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d));
  }

  /**
   * Test
   * {@link BPMNLayout#offset(double, double, double, double, double, double)}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNLayout#offset(double, double, double, double, double, double)}
   */
  @Test
  @DisplayName("Test offset(double, double, double, double, double, double); when zero")
  void testOffset_whenZero() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new BPMNLayout(new mxGraph())).offset(0.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d));
  }

  /**
   * Test
   * {@link BPMNLayout#bridge(Polyline, double, double, Polyline, double, double)}.
   * <p>
   * Method under test:
   * {@link BPMNLayout#bridge(BPMNLayout.Polyline, double, double, BPMNLayout.Polyline, double, double)}
   */
  @Test
  @DisplayName("Test bridge(Polyline, double, double, Polyline, double, double)")
  void testBridge() {
    // Arrange
    BPMNLayout bpmnLayout = new BPMNLayout(new mxGraph());
    BPMNLayout.Polyline line1 = new BPMNLayout.Polyline(10.0d, 10.0d, mock(BPMNLayout.Polyline.class));

    // Act
    BPMNLayout.Polyline actualBridgeResult = bpmnLayout.bridge(line1, 1.0d, 3.0d,
        new BPMNLayout.Polyline(10.0d, 10.0d, mock(BPMNLayout.Polyline.class)), 10.0d, 10.0d);

    // Assert
    BPMNLayout.Polyline polyline = line1.next;
    assertEquals(-2.0d, polyline.dy);
    assertEquals(0.0d, polyline.dx);
    assertEquals(19.0d, actualBridgeResult.dx);
    assertEquals(19.0d, actualBridgeResult.dy);
  }

  /**
   * Test {@link BPMNLayout#createNode(Object)}.
   * <ul>
   *   <li>Given {@link BPMNLayout#BPMNLayout(mxGraph)} with graph is
   * {@link mxGraph}.</li>
   *   <li>Then return {@link TreeNode#height} is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#createNode(Object)}
   */
  @Test
  @DisplayName("Test createNode(Object); given BPMNLayout(mxGraph) with graph is mxGraph; then return height is ten")
  void testCreateNode_givenBPMNLayoutWithGraphIsMxGraph_thenReturnHeightIsTen() {
    // Arrange
    mxGeometry mxGeometry = mock(mxGeometry.class);
    when(mxGeometry.getX()).thenReturn(2.0d);
    when(mxGeometry.getY()).thenReturn(3.0d);
    when(mxGeometry.getHeight()).thenReturn(10.0d);
    when(mxGeometry.getWidth()).thenReturn(10.0d);
    mxGraphModel mxGraphModel = mock(mxGraphModel.class);
    when(mxGraphModel.getGeometry(Mockito.<Object>any())).thenReturn(mxGeometry);
    mxGraph graph = mock(mxGraph.class);
    when(graph.getModel()).thenReturn(mxGraphModel);

    // Act
    BPMNLayout.TreeNode actualCreateNodeResult = (new BPMNLayout(graph)).createNode("Cell");

    // Assert
    verify(mxGraphModel).getGeometry(isA(Object.class));
    verify(mxGeometry).getX();
    verify(mxGeometry).getY();
    verify(mxGeometry).getHeight();
    verify(mxGeometry).getWidth();
    verify(graph).getModel();
    assertEquals("Cell", actualCreateNodeResult.cell);
    BPMNLayout.Polygon polygon = actualCreateNodeResult.contour;
    assertNull(polygon.lowerHead);
    assertNull(polygon.lowerTail);
    assertNull(polygon.upperHead);
    assertNull(polygon.upperTail);
    assertNull(actualCreateNodeResult.child);
    assertNull(actualCreateNodeResult.next);
    assertEquals(0.0d, actualCreateNodeResult.offsetX);
    assertEquals(0.0d, actualCreateNodeResult.offsetY);
    assertEquals(0.0d, actualCreateNodeResult.x);
    assertEquals(0.0d, actualCreateNodeResult.y);
    assertEquals(10.0d, actualCreateNodeResult.height);
    assertEquals(10.0d, actualCreateNodeResult.width);
  }

  /**
   * Test {@link BPMNLayout#createNode(Object)}.
   * <ul>
   *   <li>Given {@link mxGraph} {@link mxGraph#getView()} return
   * {@link mxGraphView#mxGraphView(mxGraph)} with graph is
   * {@link mxGraph#mxGraph()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#createNode(Object)}
   */
  @Test
  @DisplayName("Test createNode(Object); given mxGraph getView() return mxGraphView(mxGraph) with graph is mxGraph()")
  void testCreateNode_givenMxGraphGetViewReturnMxGraphViewWithGraphIsMxGraph() {
    // Arrange
    mxGeometry mxGeometry = mock(mxGeometry.class);
    when(mxGeometry.getX()).thenReturn(2.0d);
    when(mxGeometry.getY()).thenReturn(3.0d);
    when(mxGeometry.getHeight()).thenReturn(10.0d);
    when(mxGeometry.getWidth()).thenReturn(10.0d);
    mxGraphModel mxGraphModel = mock(mxGraphModel.class);
    when(mxGraphModel.getGeometry(Mockito.<Object>any())).thenReturn(mxGeometry);
    mxGraph graph = mock(mxGraph.class);
    when(graph.getView()).thenReturn(new mxGraphView(new mxGraph()));
    when(graph.getModel()).thenReturn(mxGraphModel);

    BPMNLayout bpmnLayout = new BPMNLayout(graph);
    bpmnLayout.setUseBoundingBox(true);

    // Act
    BPMNLayout.TreeNode actualCreateNodeResult = bpmnLayout.createNode("Cell");

    // Assert
    verify(mxGraphModel).getGeometry(isA(Object.class));
    verify(mxGeometry).getX();
    verify(mxGeometry).getY();
    verify(mxGeometry).getHeight();
    verify(mxGeometry).getWidth();
    verify(graph).getModel();
    verify(graph).getView();
    assertEquals("Cell", actualCreateNodeResult.cell);
    BPMNLayout.Polygon polygon = actualCreateNodeResult.contour;
    assertNull(polygon.lowerHead);
    assertNull(polygon.lowerTail);
    assertNull(polygon.upperHead);
    assertNull(polygon.upperTail);
    assertNull(actualCreateNodeResult.child);
    assertNull(actualCreateNodeResult.next);
    assertEquals(0.0d, actualCreateNodeResult.offsetX);
    assertEquals(0.0d, actualCreateNodeResult.offsetY);
    assertEquals(0.0d, actualCreateNodeResult.x);
    assertEquals(0.0d, actualCreateNodeResult.y);
    assertEquals(10.0d, actualCreateNodeResult.height);
    assertEquals(10.0d, actualCreateNodeResult.width);
  }

  /**
   * Test {@link BPMNLayout#createNode(Object)}.
   * <ul>
   *   <li>Given {@link mxRectangle} {@link mxPoint#getX()} return two.</li>
   *   <li>Then calls {@link mxCellState#getBoundingBox()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#createNode(Object)}
   */
  @Test
  @DisplayName("Test createNode(Object); given mxRectangle getX() return two; then calls getBoundingBox()")
  void testCreateNode_givenMxRectangleGetXReturnTwo_thenCallsGetBoundingBox() {
    // Arrange
    mxGeometry mxGeometry = mock(mxGeometry.class);
    when(mxGeometry.getX()).thenReturn(2.0d);
    when(mxGeometry.getY()).thenReturn(3.0d);
    when(mxGeometry.getHeight()).thenReturn(10.0d);
    when(mxGeometry.getWidth()).thenReturn(10.0d);
    mxGraphModel mxGraphModel = mock(mxGraphModel.class);
    when(mxGraphModel.getGeometry(Mockito.<Object>any())).thenReturn(mxGeometry);
    mxRectangle mxRectangle = mock(mxRectangle.class);
    when(mxRectangle.getX()).thenReturn(2.0d);
    when(mxRectangle.getY()).thenReturn(3.0d);
    when(mxRectangle.getHeight()).thenReturn(10.0d);
    when(mxRectangle.getWidth()).thenReturn(10.0d);
    mxCellState mxCellState = mock(mxCellState.class);
    when(mxCellState.getX()).thenReturn(2.0d);
    when(mxCellState.getY()).thenReturn(3.0d);
    when(mxCellState.getHeight()).thenReturn(10.0d);
    when(mxCellState.getWidth()).thenReturn(10.0d);
    when(mxCellState.getBoundingBox()).thenReturn(mxRectangle);
    mxGraphView mxGraphView = mock(mxGraphView.class);
    when(mxGraphView.getScale()).thenReturn(10.0d);
    when(mxGraphView.getState(Mockito.<Object>any())).thenReturn(mxCellState);
    mxGraph graph = mock(mxGraph.class);
    when(graph.getView()).thenReturn(mxGraphView);
    when(graph.getModel()).thenReturn(mxGraphModel);

    BPMNLayout bpmnLayout = new BPMNLayout(graph);
    bpmnLayout.setUseBoundingBox(true);

    // Act
    BPMNLayout.TreeNode actualCreateNodeResult = bpmnLayout.createNode("Cell");

    // Assert
    verify(mxGraphModel).getGeometry(isA(Object.class));
    verify(mxGeometry).getX();
    verify(mxRectangle, atLeast(1)).getX();
    verify(mxCellState, atLeast(1)).getX();
    verify(mxGeometry).getY();
    verify(mxRectangle, atLeast(1)).getY();
    verify(mxCellState, atLeast(1)).getY();
    verify(mxGeometry).getHeight();
    verify(mxRectangle).getHeight();
    verify(mxCellState).getHeight();
    verify(mxGeometry).getWidth();
    verify(mxRectangle).getWidth();
    verify(mxCellState).getWidth();
    verify(mxCellState).getBoundingBox();
    verify(graph).getModel();
    verify(graph, atLeast(1)).getView();
    verify(mxGraphView).getScale();
    verify(mxGraphView).getState(isA(Object.class));
    assertEquals("Cell", actualCreateNodeResult.cell);
    BPMNLayout.Polygon polygon = actualCreateNodeResult.contour;
    assertNull(polygon.lowerHead);
    assertNull(polygon.lowerTail);
    assertNull(polygon.upperHead);
    assertNull(polygon.upperTail);
    assertNull(actualCreateNodeResult.child);
    assertNull(actualCreateNodeResult.next);
    assertEquals(0.0d, actualCreateNodeResult.offsetX);
    assertEquals(0.0d, actualCreateNodeResult.offsetY);
    assertEquals(0.0d, actualCreateNodeResult.x);
    assertEquals(0.0d, actualCreateNodeResult.y);
    assertEquals(10.0d, actualCreateNodeResult.height);
    assertEquals(10.0d, actualCreateNodeResult.width);
  }

  /**
   * Test {@link BPMNLayout#createNode(Object)}.
   * <ul>
   *   <li>Then return {@link TreeNode#height} is nine.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#createNode(Object)}
   */
  @Test
  @DisplayName("Test createNode(Object); then return height is nine")
  void testCreateNode_thenReturnHeightIsNine() {
    // Arrange
    mxGeometry mxGeometry = mock(mxGeometry.class);
    when(mxGeometry.getX()).thenReturn(2.0d);
    when(mxGeometry.getY()).thenReturn(3.0d);
    when(mxGeometry.getHeight()).thenReturn(10.0d);
    when(mxGeometry.getWidth()).thenReturn(10.0d);
    mxGraphModel mxGraphModel = mock(mxGraphModel.class);
    when(mxGraphModel.getGeometry(Mockito.<Object>any())).thenReturn(mxGeometry);
    mxCellState mxCellState = mock(mxCellState.class);
    when(mxCellState.getX()).thenReturn(2.0d);
    when(mxCellState.getY()).thenReturn(3.0d);
    when(mxCellState.getHeight()).thenReturn(10.0d);
    when(mxCellState.getWidth()).thenReturn(10.0d);
    when(mxCellState.getBoundingBox()).thenReturn(new mxRectangle());
    mxGraphView mxGraphView = mock(mxGraphView.class);
    when(mxGraphView.getScale()).thenReturn(10.0d);
    when(mxGraphView.getState(Mockito.<Object>any())).thenReturn(mxCellState);
    mxGraph graph = mock(mxGraph.class);
    when(graph.getView()).thenReturn(mxGraphView);
    when(graph.getModel()).thenReturn(mxGraphModel);

    BPMNLayout bpmnLayout = new BPMNLayout(graph);
    bpmnLayout.setUseBoundingBox(true);

    // Act
    BPMNLayout.TreeNode actualCreateNodeResult = bpmnLayout.createNode("Cell");

    // Assert
    verify(mxGraphModel).getGeometry(isA(Object.class));
    verify(mxGeometry).getX();
    verify(mxCellState, atLeast(1)).getX();
    verify(mxGeometry).getY();
    verify(mxCellState, atLeast(1)).getY();
    verify(mxGeometry).getHeight();
    verify(mxCellState).getHeight();
    verify(mxGeometry).getWidth();
    verify(mxCellState).getWidth();
    verify(mxCellState).getBoundingBox();
    verify(graph).getModel();
    verify(graph, atLeast(1)).getView();
    verify(mxGraphView).getScale();
    verify(mxGraphView).getState(isA(Object.class));
    assertEquals("Cell", actualCreateNodeResult.cell);
    BPMNLayout.Polygon polygon = actualCreateNodeResult.contour;
    assertNull(polygon.lowerHead);
    assertNull(polygon.lowerTail);
    assertNull(polygon.upperHead);
    assertNull(polygon.upperTail);
    assertNull(actualCreateNodeResult.child);
    assertNull(actualCreateNodeResult.next);
    assertEquals(0.0d, actualCreateNodeResult.offsetX);
    assertEquals(0.0d, actualCreateNodeResult.offsetY);
    assertEquals(0.0d, actualCreateNodeResult.x);
    assertEquals(0.0d, actualCreateNodeResult.y);
    assertEquals(9.0d, actualCreateNodeResult.height);
    assertEquals(9.0d, actualCreateNodeResult.width);
  }

  /**
   * Test {@link BPMNLayout#createNode(Object)}.
   * <ul>
   *   <li>Then return {@link TreeNode#height} is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#createNode(Object)}
   */
  @Test
  @DisplayName("Test createNode(Object); then return height is zero")
  void testCreateNode_thenReturnHeightIsZero() {
    // Arrange
    mxGraphModel mxGraphModel = mock(mxGraphModel.class);
    when(mxGraphModel.getGeometry(Mockito.<Object>any())).thenReturn(new mxGeometry());
    mxGraph graph = mock(mxGraph.class);
    when(graph.getModel()).thenReturn(mxGraphModel);

    // Act
    BPMNLayout.TreeNode actualCreateNodeResult = (new BPMNLayout(graph)).createNode("Cell");

    // Assert
    verify(mxGraphModel).getGeometry(isA(Object.class));
    verify(graph).getModel();
    assertEquals("Cell", actualCreateNodeResult.cell);
    BPMNLayout.Polygon polygon = actualCreateNodeResult.contour;
    assertNull(polygon.lowerHead);
    assertNull(polygon.lowerTail);
    assertNull(polygon.upperHead);
    assertNull(polygon.upperTail);
    assertNull(actualCreateNodeResult.child);
    assertNull(actualCreateNodeResult.next);
    assertEquals(0.0d, actualCreateNodeResult.height);
    assertEquals(0.0d, actualCreateNodeResult.offsetX);
    assertEquals(0.0d, actualCreateNodeResult.offsetY);
    assertEquals(0.0d, actualCreateNodeResult.width);
    assertEquals(0.0d, actualCreateNodeResult.x);
    assertEquals(0.0d, actualCreateNodeResult.y);
  }

  /**
   * Test {@link BPMNLayout#apply(TreeNode, mxRectangle)}.
   * <ul>
   *   <li>Given {@link BPMNLayout#BPMNLayout(mxGraph)} with graph is
   * {@link mxGraph#mxGraph()}.</li>
   *   <li>Then return {@link mxRectangle#mxRectangle()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#apply(BPMNLayout.TreeNode, mxRectangle)}
   */
  @Test
  @DisplayName("Test apply(TreeNode, mxRectangle); given BPMNLayout(mxGraph) with graph is mxGraph(); then return mxRectangle()")
  void testApply_givenBPMNLayoutWithGraphIsMxGraph_thenReturnMxRectangle() {
    // Arrange
    BPMNLayout bpmnLayout = new BPMNLayout(new mxGraph());
    BPMNLayout.TreeNode node = new BPMNLayout.TreeNode("Cell");
    mxRectangle bounds = new mxRectangle();

    // Act and Assert
    assertSame(bounds, bpmnLayout.apply(node, bounds));
  }

  /**
   * Test {@link BPMNLayout#apply(TreeNode, mxRectangle)}.
   * <ul>
   *   <li>Given {@link mxGraph#mxGraph()} addPropertyChangeListener
   * {@link PropertyChangeListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNLayout#apply(BPMNLayout.TreeNode, mxRectangle)}
   */
  @Test
  @DisplayName("Test apply(TreeNode, mxRectangle); given mxGraph() addPropertyChangeListener PropertyChangeListener")
  void testApply_givenMxGraphAddPropertyChangeListenerPropertyChangeListener() {
    // Arrange
    mxGraph graph = new mxGraph();
    graph.addPropertyChangeListener(mock(PropertyChangeListener.class));
    BPMNLayout bpmnLayout = new BPMNLayout(graph);
    BPMNLayout.TreeNode node = new BPMNLayout.TreeNode("Cell");
    mxRectangle bounds = new mxRectangle();

    // Act and Assert
    assertSame(bounds, bpmnLayout.apply(node, bounds));
  }

  /**
   * Test {@link BPMNLayout#createLine(double, double, Polyline)}.
   * <p>
   * Method under test:
   * {@link BPMNLayout#createLine(double, double, BPMNLayout.Polyline)}
   */
  @Test
  @DisplayName("Test createLine(double, double, Polyline)")
  void testCreateLine() {
    // Arrange
    BPMNLayout bpmnLayout = new BPMNLayout(new mxGraph());
    BPMNLayout.Polyline next = new BPMNLayout.Polyline(10.0d, 10.0d, mock(BPMNLayout.Polyline.class));

    // Act
    BPMNLayout.Polyline actualCreateLineResult = bpmnLayout.createLine(10.0d, 10.0d, next);

    // Assert
    assertEquals(10.0d, actualCreateLineResult.dx);
    BPMNLayout.Polyline polyline = actualCreateLineResult.next;
    assertEquals(10.0d, polyline.dx);
    assertEquals(10.0d, actualCreateLineResult.dy);
    assertEquals(10.0d, polyline.dy);
    assertSame(next.next, polyline.next);
  }

  /**
   * Test Polygon new {@link Polygon} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link BPMNLayout.Polygon}
   */
  @Test
  @DisplayName("Test Polygon new Polygon (default constructor)")
  void testPolygonNewPolygon() {
    // Arrange and Act
    BPMNLayout.Polygon actualPolygon = new BPMNLayout.Polygon();

    // Assert
    assertNull(actualPolygon.lowerHead);
    assertNull(actualPolygon.lowerTail);
    assertNull(actualPolygon.upperHead);
    assertNull(actualPolygon.upperTail);
  }

  /**
   * Test Polyline {@link Polyline#Polyline(double, double, Polyline)}.
   * <p>
   * Method under test:
   * {@link BPMNLayout.Polyline#Polyline(double, double, BPMNLayout.Polyline)}
   */
  @Test
  @DisplayName("Test Polyline new Polyline(double, double, Polyline)")
  void testPolylineNewPolyline() {
    // Arrange and Act
    BPMNLayout.Polyline actualPolyline = new BPMNLayout.Polyline(10.0d, 10.0d, null);

    // Assert
    assertNull(actualPolyline.next);
    assertEquals(10.0d, actualPolyline.dx);
    assertEquals(10.0d, actualPolyline.dy);
  }

  /**
   * Test TreeNode {@link TreeNode#TreeNode(Object)}.
   * <p>
   * Method under test: {@link BPMNLayout.TreeNode#TreeNode(Object)}
   */
  @Test
  @DisplayName("Test TreeNode new TreeNode(Object)")
  void testTreeNodeNewTreeNode() {
    // Arrange and Act
    BPMNLayout.TreeNode actualTreeNode = new BPMNLayout.TreeNode("Cell");

    // Assert
    assertEquals("Cell", actualTreeNode.cell);
    BPMNLayout.Polygon polygon = actualTreeNode.contour;
    assertNull(polygon.lowerHead);
    assertNull(polygon.lowerTail);
    assertNull(polygon.upperHead);
    assertNull(polygon.upperTail);
    assertNull(actualTreeNode.child);
    assertNull(actualTreeNode.next);
    assertEquals(0.0d, actualTreeNode.height);
    assertEquals(0.0d, actualTreeNode.offsetX);
    assertEquals(0.0d, actualTreeNode.offsetY);
    assertEquals(0.0d, actualTreeNode.width);
    assertEquals(0.0d, actualTreeNode.x);
    assertEquals(0.0d, actualTreeNode.y);
  }
}
