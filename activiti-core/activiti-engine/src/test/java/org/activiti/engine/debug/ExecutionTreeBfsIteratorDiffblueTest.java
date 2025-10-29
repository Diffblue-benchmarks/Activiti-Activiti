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
package org.activiti.engine.debug;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class ExecutionTreeBfsIteratorDiffblueTest {
  /**
   * Method under test: {@link ExecutionTreeBfsIterator#flattenTree()}
   */
  @Test
  public void testFlattenTree() {
    // Arrange
    ExecutionTreeNode executionTree = mock(ExecutionTreeNode.class);
    when(executionTree.getChildren()).thenReturn(new ArrayList<>());

    // Act
    (new ExecutionTreeBfsIterator(executionTree)).flattenTree();

    // Assert
    verify(executionTree, atLeast(1)).getChildren();
  }

  /**
   * Method under test: {@link ExecutionTreeBfsIterator#flattenTree()}
   */
  @Test
  public void testFlattenTree2() {
    // Arrange
    ArrayList<ExecutionTreeNode> executionTreeNodeList = new ArrayList<>();
    executionTreeNodeList.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    ExecutionTreeNode executionTree = mock(ExecutionTreeNode.class);
    when(executionTree.getChildren()).thenReturn(executionTreeNodeList);

    // Act
    (new ExecutionTreeBfsIterator(executionTree)).flattenTree();

    // Assert
    verify(executionTree, atLeast(1)).getChildren();
  }

  /**
   * Method under test: {@link ExecutionTreeBfsIterator#hasNext()}
   */
  @Test
  public void testHasNext() {
    // Arrange, Act and Assert
    assertTrue((new ExecutionTreeBfsIterator(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))).hasNext());
    assertTrue((new ExecutionTreeBfsIterator(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()), true)).hasNext());
  }

  /**
   * Method under test: {@link ExecutionTreeBfsIterator#hasNext()}
   */
  @Test
  public void testHasNext2() {
    // Arrange
    ExecutionTreeNode executionTree = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTree.setChildren(new ArrayList<>());

    // Act and Assert
    assertTrue((new ExecutionTreeBfsIterator(executionTree)).hasNext());
  }

  /**
   * Method under test: {@link ExecutionTreeBfsIterator#hasNext()}
   */
  @Test
  public void testHasNext3() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTree = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTree.setChildren(children);

    // Act and Assert
    assertTrue((new ExecutionTreeBfsIterator(executionTree)).hasNext());
  }

  /**
   * Method under test: {@link ExecutionTreeBfsIterator#next()}
   */
  @Test
  public void testNext() {
    // Arrange
    ExecutionTreeBfsIterator executionTreeBfsIterator = new ExecutionTreeBfsIterator(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Act
    ExecutionTreeNode actualNextResult = executionTreeBfsIterator.next();

    // Assert
    assertFalse(executionTreeBfsIterator.hasNext());
    assertSame(executionTreeBfsIterator.rootNode, actualNextResult);
  }

  /**
   * Method under test: {@link ExecutionTreeBfsIterator#next()}
   */
  @Test
  public void testNext2() {
    // Arrange
    ExecutionTreeBfsIterator executionTreeBfsIterator = new ExecutionTreeBfsIterator(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()), true);

    // Act
    ExecutionTreeNode actualNextResult = executionTreeBfsIterator.next();

    // Assert
    assertFalse(executionTreeBfsIterator.hasNext());
    assertSame(executionTreeBfsIterator.rootNode, actualNextResult);
  }

  /**
   * Method under test: {@link ExecutionTreeBfsIterator#next()}
   */
  @Test
  public void testNext3() {
    // Arrange
    ExecutionTreeNode executionTree = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTree.setChildren(new ArrayList<>());
    ExecutionTreeBfsIterator executionTreeBfsIterator = new ExecutionTreeBfsIterator(executionTree);

    // Act
    ExecutionTreeNode actualNextResult = executionTreeBfsIterator.next();

    // Assert
    assertFalse(executionTreeBfsIterator.hasNext());
    assertSame(executionTreeBfsIterator.rootNode, actualNextResult);
  }

  /**
   * Method under test:
   * {@link ExecutionTreeBfsIterator#ExecutionTreeBfsIterator(ExecutionTreeNode)}
   */
  @Test
  public void testNewExecutionTreeBfsIterator() {
    // Arrange and Act
    ExecutionTreeBfsIterator actualExecutionTreeBfsIterator = new ExecutionTreeBfsIterator(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    ExecutionTreeNode expectedNextResult = actualExecutionTreeBfsIterator.rootNode;
    ExecutionTreeNode actualNextResult = actualExecutionTreeBfsIterator.next();
    assertFalse(actualExecutionTreeBfsIterator.hasNext());
    assertSame(expectedNextResult, actualNextResult);
  }

  /**
   * Method under test:
   * {@link ExecutionTreeBfsIterator#ExecutionTreeBfsIterator(ExecutionTreeNode, boolean)}
   */
  @Test
  public void testNewExecutionTreeBfsIterator2() {
    // Arrange and Act
    ExecutionTreeBfsIterator actualExecutionTreeBfsIterator = new ExecutionTreeBfsIterator(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()), true);

    // Assert
    ExecutionTreeNode expectedNextResult = actualExecutionTreeBfsIterator.rootNode;
    ExecutionTreeNode actualNextResult = actualExecutionTreeBfsIterator.next();
    assertFalse(actualExecutionTreeBfsIterator.hasNext());
    assertSame(expectedNextResult, actualNextResult);
  }
}
