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
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.test.impl.logger.DebugInfoExecutionTree.DebugInfoExecutionTreeNode;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractDebugInfoDiffblueTest {
  /**
   * Test {@link AbstractDebugInfo#getExecutionTrees()}.
   *
   * <p>Method under test: {@link AbstractDebugInfo#getExecutionTrees()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractDebugInfo.getExecutionTrees()"})
  public void testGetExecutionTrees() {
    // Arrange, Act and Assert
    assertTrue(
        new DebugInfoExecutionCreated(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .getExecutionTrees()
            .isEmpty());
  }

  /**
   * Test {@link AbstractDebugInfo#setExecutionTrees(List)}.
   *
   * <p>Method under test: {@link AbstractDebugInfo#setExecutionTrees(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractDebugInfo.setExecutionTrees(List)"})
  public void testSetExecutionTrees() {
    // Arrange
    DebugInfoExecutionCreated debugInfoExecutionCreated =
        new DebugInfoExecutionCreated(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ArrayList<DebugInfoExecutionTree> executionTrees = new ArrayList<>();

    // Act
    debugInfoExecutionCreated.setExecutionTrees(executionTrees);

    // Assert
    assertSame(executionTrees, debugInfoExecutionCreated.getExecutionTrees());
  }

  /**
   * Test {@link AbstractDebugInfo#addExecutionTree(DebugInfoExecutionTree)}.
   *
   * <p>Method under test: {@link AbstractDebugInfo#addExecutionTree(DebugInfoExecutionTree)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractDebugInfo.addExecutionTree(DebugInfoExecutionTree)"})
  public void testAddExecutionTree() {
    // Arrange
    DebugInfoExecutionCreated debugInfoExecutionCreated =
        new DebugInfoExecutionCreated(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    DebugInfoExecutionTree executionTree = new DebugInfoExecutionTree();
    executionTree.setProcessInstance(new DebugInfoExecutionTreeNode());

    // Act
    debugInfoExecutionCreated.addExecutionTree(executionTree);

    // Assert
    List<DebugInfoExecutionTree> executionTrees = debugInfoExecutionCreated.getExecutionTrees();
    assertEquals(1, executionTrees.size());
    assertSame(executionTree, executionTrees.get(0));
  }
}
