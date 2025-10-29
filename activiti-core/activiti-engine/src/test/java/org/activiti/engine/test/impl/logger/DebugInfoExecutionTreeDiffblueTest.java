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
package org.activiti.engine.test.impl.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.event.EventRecordingLogger;

@RunWith(MockitoJUnitRunner.class)
public class DebugInfoExecutionTreeDiffblueTest {
  @InjectMocks
  private DebugInfoExecutionTree.DebugInfoExecutionTreeNode debugInfoExecutionTreeNode;

  /**
   * Method under test:
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#getCurrentFlowElementInfo()}
   */
  @Test
  public void testDebugInfoExecutionTreeNodeGetCurrentFlowElementInfo() {
    // Arrange, Act and Assert
    assertEquals("null", (new DebugInfoExecutionTree.DebugInfoExecutionTreeNode()).getCurrentFlowElementInfo());
  }

  /**
   * Method under test:
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#getCurrentFlowElementInfo()}
   */
  @Test
  public void testDebugInfoExecutionTreeNodeGetCurrentFlowElementInfo2() {
    // Arrange
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode debugInfoExecutionTreeNode = new DebugInfoExecutionTree.DebugInfoExecutionTreeNode();
    debugInfoExecutionTreeNode.setActivityId(null);
    debugInfoExecutionTreeNode.setActivityName("foo");

    // Act and Assert
    assertEquals("null in flow element  with name foo", debugInfoExecutionTreeNode.getCurrentFlowElementInfo());
  }

  /**
   * Method under test:
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#getCurrentFlowElementInfo()}
   */
  @Test
  public void testDebugInfoExecutionTreeNodeGetCurrentFlowElementInfo3() {
    // Arrange
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode debugInfoExecutionTreeNode = new DebugInfoExecutionTree.DebugInfoExecutionTreeNode();
    debugInfoExecutionTreeNode.setActivityId("foo");
    debugInfoExecutionTreeNode.setActivityName(null);

    // Act and Assert
    assertEquals("null in flow element 'foo'", debugInfoExecutionTreeNode.getCurrentFlowElementInfo());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode}
   *   <li>
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#setActivityId(String)}
   *   <li>
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#setActivityName(String)}
   *   <li>
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#setChildNodes(List)}
   *   <li>{@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#setId(String)}
   *   <li>
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#setParentNode(DebugInfoExecutionTree.DebugInfoExecutionTreeNode)}
   *   <li>
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#setProcessDefinitionId(String)}
   *   <li>{@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#getActivityId()}
   *   <li>
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#getActivityName()}
   *   <li>{@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#getChildNodes()}
   *   <li>{@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#getId()}
   *   <li>{@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#getParentNode()}
   *   <li>
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#getProcessDefinitionId()}
   * </ul>
   */
  @Test
  public void testDebugInfoExecutionTreeNodeGettersAndSetters() {
    // Arrange and Act
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode actualDebugInfoExecutionTreeNode = new DebugInfoExecutionTree.DebugInfoExecutionTreeNode();
    actualDebugInfoExecutionTreeNode.setActivityId("42");
    actualDebugInfoExecutionTreeNode.setActivityName("Activity Name");
    ArrayList<DebugInfoExecutionTree.DebugInfoExecutionTreeNode> childNodes = new ArrayList<>();
    actualDebugInfoExecutionTreeNode.setChildNodes(childNodes);
    actualDebugInfoExecutionTreeNode.setId("42");
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode parentNode = new DebugInfoExecutionTree.DebugInfoExecutionTreeNode();
    actualDebugInfoExecutionTreeNode.setParentNode(parentNode);
    actualDebugInfoExecutionTreeNode.setProcessDefinitionId("42");
    String actualActivityId = actualDebugInfoExecutionTreeNode.getActivityId();
    String actualActivityName = actualDebugInfoExecutionTreeNode.getActivityName();
    List<DebugInfoExecutionTree.DebugInfoExecutionTreeNode> actualChildNodes = actualDebugInfoExecutionTreeNode
        .getChildNodes();
    String actualId = actualDebugInfoExecutionTreeNode.getId();
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode actualParentNode = actualDebugInfoExecutionTreeNode
        .getParentNode();

    // Assert that nothing has changed
    assertEquals("42", actualActivityId);
    assertEquals("42", actualId);
    assertEquals("42", actualDebugInfoExecutionTreeNode.getProcessDefinitionId());
    assertEquals("Activity Name", actualActivityName);
    assertEquals("null", actualParentNode.getCurrentFlowElementInfo());
    assertTrue(actualChildNodes.isEmpty());
    assertSame(childNodes, actualChildNodes);
    assertSame(parentNode, actualParentNode);
  }

  /**
   * Method under test:
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#print(Logger)}
   */
  @Test
  public void testDebugInfoExecutionTreeNodePrint() {
    // Arrange
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode debugInfoExecutionTreeNode = new DebugInfoExecutionTree.DebugInfoExecutionTreeNode();
    EventRecordingLogger logger = mock(EventRecordingLogger.class);
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    debugInfoExecutionTreeNode.print(logger);

    // Assert that nothing has changed
    verify(logger, atLeast(1)).info(Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#print(Logger)}
   */
  @Test
  public void testDebugInfoExecutionTreeNodePrint2() {
    // Arrange
    ArrayList<DebugInfoExecutionTree.DebugInfoExecutionTreeNode> childNodes = new ArrayList<>();
    childNodes.add(new DebugInfoExecutionTree.DebugInfoExecutionTreeNode());

    DebugInfoExecutionTree.DebugInfoExecutionTreeNode debugInfoExecutionTreeNode = new DebugInfoExecutionTree.DebugInfoExecutionTreeNode();
    debugInfoExecutionTreeNode.setChildNodes(childNodes);
    EventRecordingLogger logger = mock(EventRecordingLogger.class);
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    debugInfoExecutionTreeNode.print(logger);

    // Assert
    verify(logger, atLeast(1)).info(Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#print(Logger, String, boolean)}
   */
  @Test
  public void testDebugInfoExecutionTreeNodePrint3() {
    // Arrange
    EventRecordingLogger logger = mock(EventRecordingLogger.class);
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    debugInfoExecutionTreeNode.print(logger, "Prefix", true);

    // Assert
    verify(logger).info(eq("Prefix└── null"));
  }

  /**
   * Method under test:
   * {@link DebugInfoExecutionTree.DebugInfoExecutionTreeNode#print(Logger, String, boolean)}
   */
  @Test
  public void testDebugInfoExecutionTreeNodePrint4() {
    // Arrange
    EventRecordingLogger logger = mock(EventRecordingLogger.class);
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    debugInfoExecutionTreeNode.print(logger, "Prefix", false);

    // Assert
    verify(logger).info(eq("Prefix├── null"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DebugInfoExecutionTree}
   *   <li>
   * {@link DebugInfoExecutionTree#setProcessInstance(DebugInfoExecutionTree.DebugInfoExecutionTreeNode)}
   *   <li>{@link DebugInfoExecutionTree#getProcessInstance()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DebugInfoExecutionTree actualDebugInfoExecutionTree = new DebugInfoExecutionTree();
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode processInstance = new DebugInfoExecutionTree.DebugInfoExecutionTreeNode();
    actualDebugInfoExecutionTree.setProcessInstance(processInstance);

    // Assert that nothing has changed
    assertSame(processInstance, actualDebugInfoExecutionTree.getProcessInstance());
  }
}
