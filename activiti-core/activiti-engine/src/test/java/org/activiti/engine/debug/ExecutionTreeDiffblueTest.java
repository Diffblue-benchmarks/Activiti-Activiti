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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExecutionTreeDiffblueTest {
  @InjectMocks
  private ExecutionTree executionTree;

  /**
   * Method under test: {@link ExecutionTree#getTreeNode(String)}
   */
  @Test
  public void testGetTreeNode() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenReturn("42");
    ExecutionTreeNode root = new ExecutionTreeNode(executionEntity);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("42");

    // Assert
    verify(executionEntity).getId();
    assertSame(root, actualTreeNode);
  }

  /**
   * Method under test: {@link ExecutionTree#getTreeNode(String)}
   */
  @Test
  public void testGetTreeNode2() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenReturn("foo");
    ExecutionTreeNode root = new ExecutionTreeNode(executionEntity);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("42");

    // Assert
    verify(executionEntity).getId();
    assertNull(actualTreeNode);
  }

  /**
   * Method under test:
   * {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)}
   */
  @Test
  public void testGetTreeNode3() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenReturn("42");
    ExecutionTreeNode currentNode = new ExecutionTreeNode(executionEntity);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("42", currentNode);

    // Assert
    verify(executionEntity).getId();
    assertSame(currentNode, actualTreeNode);
  }

  /**
   * Method under test:
   * {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)}
   */
  @Test
  public void testGetTreeNode4() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenReturn("42");

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("Execution Id",
        new ExecutionTreeNode(executionEntity));

    // Assert
    verify(executionEntity).getId();
    assertNull(actualTreeNode);
  }

  /**
   * Method under test:
   * {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)}
   */
  @Test
  public void testGetTreeNode5() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setId("42");
    ExecutionTreeNode currentNode = mock(ExecutionTreeNode.class);
    when(currentNode.getChildren()).thenReturn(new ArrayList<>());
    when(currentNode.getExecutionEntity()).thenReturn(createWithEmptyRelationshipCollectionsResult);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("Execution Id", currentNode);

    // Assert
    verify(currentNode, atLeast(1)).getChildren();
    verify(currentNode).getExecutionEntity();
    assertNull(actualTreeNode);
  }

  /**
   * Method under test: {@link ExecutionTree#iterator()}
   */
  @Test
  public void testIterator() {
    // Arrange, Act and Assert
    assertTrue((new ExecutionTree()).iterator() instanceof ExecutionTreeBfsIterator);
  }

  /**
   * Method under test: {@link ExecutionTree#bfsIterator()}
   */
  @Test
  public void testBfsIterator() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();

    // Act
    executionTree.bfsIterator();

    // Assert
    assertTrue(executionTree.iterator() instanceof ExecutionTreeBfsIterator);
    assertNull(executionTree.getRoot());
  }

  /**
   * Method under test: {@link ExecutionTree#leafsFirstIterator()}
   */
  @Test
  public void testLeafsFirstIterator() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();

    // Act
    executionTree.leafsFirstIterator();

    // Assert
    assertTrue(executionTree.iterator() instanceof ExecutionTreeBfsIterator);
    assertNull(executionTree.getRoot());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("", (new ExecutionTree()).toString());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString2() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Act and Assert
    assertEquals("null (process instance)\n", executionTree.toString());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString3() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setParentId("42");
    ExecutionTreeNode root = new ExecutionTreeNode(executionEntity);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null, parent id 42\n", executionTree.toString());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString4() {
    // Arrange
    ExecutionTreeNode root = new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(new ArrayList<>());

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null (process instance)\n", executionTree.toString());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString5() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode root = new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null (process instance)\n└── null : , parent id null (active) (scope)\n", executionTree.toString());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString6() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setEnded(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode root = new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null (process instance)\n└── null : , parent id null (active) (scope) (ended)\n",
        executionTree.toString());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString7() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setMultiInstanceRoot(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode root = new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null (process instance)\n└── null : , parent id null (active) (scope) (multi instance root)\n",
        executionTree.toString());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString8() {
    // Arrange
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(new ArrayList<>());

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode root = new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null (process instance)\n└── null : , parent id null (active) (scope)\n", executionTree.toString());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString9() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(children);

    ArrayList<ExecutionTreeNode> children2 = new ArrayList<>();
    children2.add(executionTreeNode);

    ExecutionTreeNode root = new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children2);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null (process instance)\n" + "└── null : , parent id null (active) (scope)\n"
        + "    └── null : , parent id null (active) (scope)\n", executionTree.toString());
  }

  /**
   * Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  public void testToString10() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(children);

    ArrayList<ExecutionTreeNode> children2 = new ArrayList<>();
    children2.add(executionTreeNode);

    ExecutionTreeNode root = new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children2);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null (process instance)\n" + "└── null : , parent id null (active) (scope)\n"
        + "    ├── null : , parent id null (active) (scope)\n" + "    └── null : , parent id null (active) (scope)\n",
        executionTree.toString());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ExecutionTree}
   *   <li>{@link ExecutionTree#setRoot(ExecutionTreeNode)}
   *   <li>{@link ExecutionTree#getRoot()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ExecutionTree actualExecutionTree = new ExecutionTree();
    ExecutionTreeNode root = new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    actualExecutionTree.setRoot(root);

    // Assert that nothing has changed
    assertSame(root, actualExecutionTree.getRoot());
  }
}
