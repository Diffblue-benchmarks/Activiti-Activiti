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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.test.impl.logger.DebugInfoExecutionTree.DebugInfoExecutionTreeNode;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DebugInfoExecutionTreeDiffblueTest {
  /**
   * Test DebugInfoExecutionTreeNode {@link DebugInfoExecutionTreeNode#getCurrentFlowElementInfo()}.
   *
   * <p>Method under test: {@link DebugInfoExecutionTreeNode#getCurrentFlowElementInfo()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DebugInfoExecutionTreeNode.getCurrentFlowElementInfo()"})
  public void testDebugInfoExecutionTreeNodeGetCurrentFlowElementInfo() {
    // Arrange
    DebugInfoExecutionTreeNode debugInfoExecutionTreeNode = new DebugInfoExecutionTreeNode();
    debugInfoExecutionTreeNode.setActivityId(null);
    debugInfoExecutionTreeNode.setActivityName("foo");

    // Act and Assert
    assertEquals(
        "null in flow element  with name foo",
        debugInfoExecutionTreeNode.getCurrentFlowElementInfo());
  }

  /**
   * Test DebugInfoExecutionTreeNode {@link DebugInfoExecutionTreeNode#getCurrentFlowElementInfo()}.
   *
   * <p>Method under test: {@link DebugInfoExecutionTreeNode#getCurrentFlowElementInfo()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DebugInfoExecutionTreeNode.getCurrentFlowElementInfo()"})
  public void testDebugInfoExecutionTreeNodeGetCurrentFlowElementInfo2() {
    // Arrange
    DebugInfoExecutionTreeNode debugInfoExecutionTreeNode = new DebugInfoExecutionTreeNode();
    debugInfoExecutionTreeNode.setActivityId("foo");
    debugInfoExecutionTreeNode.setActivityName(null);

    // Act and Assert
    assertEquals(
        "null in flow element 'foo'", debugInfoExecutionTreeNode.getCurrentFlowElementInfo());
  }

  /**
   * Test DebugInfoExecutionTreeNode {@link DebugInfoExecutionTreeNode#getCurrentFlowElementInfo()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DebugInfoExecutionTreeNode#getCurrentFlowElementInfo()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DebugInfoExecutionTreeNode.getCurrentFlowElementInfo()"})
  public void testDebugInfoExecutionTreeNodeGetCurrentFlowElementInfo_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null", new DebugInfoExecutionTreeNode().getCurrentFlowElementInfo());
  }

  /**
   * Test DebugInfoExecutionTreeNode getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link DebugInfoExecutionTreeNode}
   *   <li>{@link DebugInfoExecutionTreeNode#setActivityId(String)}
   *   <li>{@link DebugInfoExecutionTreeNode#setActivityName(String)}
   *   <li>{@link DebugInfoExecutionTreeNode#setChildNodes(List)}
   *   <li>{@link DebugInfoExecutionTreeNode#setId(String)}
   *   <li>{@link DebugInfoExecutionTreeNode#setParentNode(DebugInfoExecutionTreeNode)}
   *   <li>{@link DebugInfoExecutionTreeNode#setProcessDefinitionId(String)}
   *   <li>{@link DebugInfoExecutionTreeNode#getActivityId()}
   *   <li>{@link DebugInfoExecutionTreeNode#getActivityName()}
   *   <li>{@link DebugInfoExecutionTreeNode#getChildNodes()}
   *   <li>{@link DebugInfoExecutionTreeNode#getId()}
   *   <li>{@link DebugInfoExecutionTreeNode#getParentNode()}
   *   <li>{@link DebugInfoExecutionTreeNode#getProcessDefinitionId()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DebugInfoExecutionTreeNode.<init>()",
    "String DebugInfoExecutionTreeNode.getActivityId()",
    "String DebugInfoExecutionTreeNode.getActivityName()",
    "List DebugInfoExecutionTreeNode.getChildNodes()",
    "String DebugInfoExecutionTreeNode.getId()",
    "DebugInfoExecutionTreeNode DebugInfoExecutionTreeNode.getParentNode()",
    "String DebugInfoExecutionTreeNode.getProcessDefinitionId()",
    "void DebugInfoExecutionTreeNode.setActivityId(String)",
    "void DebugInfoExecutionTreeNode.setActivityName(String)",
    "void DebugInfoExecutionTreeNode.setChildNodes(List)",
    "void DebugInfoExecutionTreeNode.setId(String)",
    "void DebugInfoExecutionTreeNode.setParentNode(DebugInfoExecutionTreeNode)",
    "void DebugInfoExecutionTreeNode.setProcessDefinitionId(String)"
  })
  public void testDebugInfoExecutionTreeNodeGettersAndSetters() {
    // Arrange and Act
    DebugInfoExecutionTreeNode actualDebugInfoExecutionTreeNode = new DebugInfoExecutionTreeNode();
    actualDebugInfoExecutionTreeNode.setActivityId("42");
    actualDebugInfoExecutionTreeNode.setActivityName("Activity Name");
    ArrayList<DebugInfoExecutionTreeNode> childNodes = new ArrayList<>();
    actualDebugInfoExecutionTreeNode.setChildNodes(childNodes);
    actualDebugInfoExecutionTreeNode.setId("42");
    DebugInfoExecutionTreeNode parentNode = new DebugInfoExecutionTreeNode();
    actualDebugInfoExecutionTreeNode.setParentNode(parentNode);
    actualDebugInfoExecutionTreeNode.setProcessDefinitionId("42");
    String actualActivityId = actualDebugInfoExecutionTreeNode.getActivityId();
    String actualActivityName = actualDebugInfoExecutionTreeNode.getActivityName();
    List<DebugInfoExecutionTreeNode> actualChildNodes =
        actualDebugInfoExecutionTreeNode.getChildNodes();
    String actualId = actualDebugInfoExecutionTreeNode.getId();
    DebugInfoExecutionTreeNode actualParentNode = actualDebugInfoExecutionTreeNode.getParentNode();

    // Assert
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
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link DebugInfoExecutionTree}
   *   <li>{@link DebugInfoExecutionTree#setProcessInstance(DebugInfoExecutionTreeNode)}
   *   <li>{@link DebugInfoExecutionTree#getProcessInstance()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DebugInfoExecutionTree.<init>()",
    "DebugInfoExecutionTreeNode DebugInfoExecutionTree.getProcessInstance()",
    "void DebugInfoExecutionTree.setProcessInstance(DebugInfoExecutionTreeNode)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    DebugInfoExecutionTree actualDebugInfoExecutionTree = new DebugInfoExecutionTree();
    DebugInfoExecutionTreeNode processInstance = new DebugInfoExecutionTreeNode();
    actualDebugInfoExecutionTree.setProcessInstance(processInstance);

    // Assert
    assertSame(processInstance, actualDebugInfoExecutionTree.getProcessInstance());
  }
}
