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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExecutionTreeDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ExecutionTree}
   *   <li>{@link ExecutionTree#setRoot(ExecutionTreeNode)}
   *   <li>{@link ExecutionTree#getRoot()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionTree.<init>()",
    "ExecutionTreeNode ExecutionTree.getRoot()",
    "void ExecutionTree.setRoot(ExecutionTreeNode)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ExecutionTree actualExecutionTree = new ExecutionTree();
    ExecutionTreeNode root =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    actualExecutionTree.setRoot(root);

    // Assert
    assertSame(root, actualExecutionTree.getRoot());
  }

  /**
   * Test {@link ExecutionTree#getTreeNode(String)} with {@code executionId}.
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String)"})
  public void testGetTreeNodeWithExecutionId() {
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
   * Test {@link ExecutionTree#getTreeNode(String)} with {@code executionId}.
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String)"})
  public void testGetTreeNodeWithExecutionId2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("foo");

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setId("42");
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> executionTreeNodeList = new ArrayList<>();
    executionTreeNodeList.add(executionTreeNode);

    ExecutionTreeNode root = mock(ExecutionTreeNode.class);
    when(root.getChildren()).thenReturn(executionTreeNodeList);
    when(root.getExecutionEntity()).thenReturn(executionEntityImpl);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("42");

    // Assert
    verify(root, atLeast(1)).getChildren();
    verify(root).getExecutionEntity();
    verify(executionEntityImpl).getId();
    assertSame(executionTreeNode, actualTreeNode);
  }

  /**
   * Test {@link ExecutionTree#getTreeNode(String)} with {@code executionId}.
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String)"})
  public void testGetTreeNodeWithExecutionId3() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("foo");

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setId("foo");
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> executionTreeNodeList = new ArrayList<>();
    executionTreeNodeList.add(executionTreeNode);

    ExecutionTreeNode root = mock(ExecutionTreeNode.class);
    when(root.getChildren()).thenReturn(executionTreeNodeList);
    when(root.getExecutionEntity()).thenReturn(executionEntityImpl);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("42");

    // Assert
    verify(root, atLeast(1)).getChildren();
    verify(root).getExecutionEntity();
    verify(executionEntityImpl).getId();
    assertNull(actualTreeNode);
  }

  /**
   * Test {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)} with {@code executionId},
   * {@code currentNode}.
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String, ExecutionTreeNode)"})
  public void testGetTreeNodeWithExecutionIdCurrentNode() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();

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
   * Test {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)} with {@code executionId},
   * {@code currentNode}.
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String, ExecutionTreeNode)"})
  public void testGetTreeNodeWithExecutionIdCurrentNode2() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setId("42");
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> executionTreeNodeList = new ArrayList<>();
    executionTreeNodeList.add(executionTreeNode);

    ExecutionTreeNode currentNode = mock(ExecutionTreeNode.class);
    when(currentNode.getChildren()).thenReturn(executionTreeNodeList);
    when(currentNode.getExecutionEntity()).thenReturn(executionEntityImpl);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("Execution Id", currentNode);

    // Assert
    verify(currentNode, atLeast(1)).getChildren();
    verify(currentNode).getExecutionEntity();
    verify(executionEntityImpl).getId();
    assertNull(actualTreeNode);
  }

  /**
   * Test {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)} with {@code executionId},
   * {@code currentNode}.
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String, ExecutionTreeNode)"})
  public void testGetTreeNodeWithExecutionIdCurrentNode3() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setId("Execution Id");
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> executionTreeNodeList = new ArrayList<>();
    executionTreeNodeList.add(executionTreeNode);

    ExecutionTreeNode currentNode = mock(ExecutionTreeNode.class);
    when(currentNode.getChildren()).thenReturn(executionTreeNodeList);
    when(currentNode.getExecutionEntity()).thenReturn(executionEntityImpl);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("Execution Id", currentNode);

    // Assert
    verify(currentNode, atLeast(1)).getChildren();
    verify(currentNode).getExecutionEntity();
    verify(executionEntityImpl).getId();
    assertSame(executionTreeNode, actualTreeNode);
  }

  /**
   * Test {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)} with {@code executionId},
   * {@code currentNode}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String, ExecutionTreeNode)"})
  public void testGetTreeNodeWithExecutionIdCurrentNode_givenArrayList_thenReturnNull() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");

    ExecutionTreeNode currentNode = mock(ExecutionTreeNode.class);
    when(currentNode.getChildren()).thenReturn(new ArrayList<>());
    when(currentNode.getExecutionEntity()).thenReturn(executionEntityImpl);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("Execution Id", currentNode);

    // Assert
    verify(currentNode, atLeast(1)).getChildren();
    verify(currentNode).getExecutionEntity();
    verify(executionEntityImpl).getId();
    assertNull(actualTreeNode);
  }

  /**
   * Test {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)} with {@code executionId},
   * {@code currentNode}.
   *
   * <ul>
   *   <li>Then return {@link ExecutionTreeNode}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String, ExecutionTreeNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String, ExecutionTreeNode)"})
  public void testGetTreeNodeWithExecutionIdCurrentNode_thenReturnExecutionTreeNode() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");

    ExecutionTreeNode currentNode = mock(ExecutionTreeNode.class);
    when(currentNode.getExecutionEntity()).thenReturn(executionEntityImpl);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("42", currentNode);

    // Assert
    verify(currentNode).getExecutionEntity();
    verify(executionEntityImpl).getId();
    assertSame(currentNode, actualTreeNode);
  }

  /**
   * Test {@link ExecutionTree#getTreeNode(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getId()} return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String)"})
  public void testGetTreeNodeWithExecutionId_givenExecutionEntityImplGetIdReturn42() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");

    ExecutionTreeNode root = mock(ExecutionTreeNode.class);
    when(root.getExecutionEntity()).thenReturn(executionEntityImpl);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act
    executionTree.getTreeNode("42");

    // Assert
    verify(root).getExecutionEntity();
    verify(executionEntityImpl).getId();
  }

  /**
   * Test {@link ExecutionTree#getTreeNode(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#getTreeNode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeNode ExecutionTree.getTreeNode(String)"})
  public void testGetTreeNodeWithExecutionId_thenReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("foo");

    ExecutionTreeNode root = mock(ExecutionTreeNode.class);
    when(root.getChildren()).thenReturn(new ArrayList<>());
    when(root.getExecutionEntity()).thenReturn(executionEntityImpl);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act
    ExecutionTreeNode actualTreeNode = executionTree.getTreeNode("42");

    // Assert
    verify(root, atLeast(1)).getChildren();
    verify(root).getExecutionEntity();
    verify(executionEntityImpl).getId();
    assertNull(actualTreeNode);
  }

  /**
   * Test {@link ExecutionTree#iterator()}.
   *
   * <p>Method under test: {@link ExecutionTree#iterator()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Iterator ExecutionTree.iterator()"})
  public void testIterator() {
    // Arrange, Act and Assert
    assertTrue(new ExecutionTree().iterator() instanceof ExecutionTreeBfsIterator);
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections Ended is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_givenCreateWithEmptyRelationshipCollectionsEndedIsTrue() {
    // Arrange
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setEnded(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode root =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals(
        "null (process instance)\n└── null : , parent id null (active) (scope) (ended)\n",
        executionTree.toString());
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections MultiInstanceRoot is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_givenCreateWithEmptyRelationshipCollectionsMultiInstanceRootIsTrue() {
    // Arrange
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setMultiInstanceRoot(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode root =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals(
        "null (process instance)\n└── null : , parent id null (active) (scope) (multi instance root)\n",
        executionTree.toString());
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Given {@link ExecutionTree} (default constructor).
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_givenExecutionTree_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", new ExecutionTree().toString());
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_thenReturnAString() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode root =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals(
        "null (process instance)\n└── null : , parent id null (active) (scope)\n",
        executionTree.toString());
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_thenReturnAString2() {
    // Arrange
    ExecutionTreeNode executionTreeNode =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(new ArrayList<>());

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode root =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals(
        "null (process instance)\n└── null : , parent id null (active) (scope)\n",
        executionTree.toString());
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_thenReturnAString3() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(children);

    ArrayList<ExecutionTreeNode> children2 = new ArrayList<>();
    children2.add(executionTreeNode);

    ExecutionTreeNode root =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children2);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals(
        "null (process instance)\n"
            + "└── null : , parent id null (active) (scope)\n"
            + "    └── null : , parent id null (active) (scope)\n",
        executionTree.toString());
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_thenReturnAString4() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    children.add(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(children);

    ArrayList<ExecutionTreeNode> children2 = new ArrayList<>();
    children2.add(executionTreeNode);

    ExecutionTreeNode root =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(children2);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals(
        "null (process instance)\n"
            + "└── null : , parent id null (active) (scope)\n"
            + "    ├── null : , parent id null (active) (scope)\n"
            + "    └── null : , parent id null (active) (scope)\n",
        executionTree.toString());
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Then return {@code null, parent id 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_thenReturnNullParentId42() {
    // Arrange
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setParentId("42");
    ExecutionTreeNode root = new ExecutionTreeNode(executionEntity);

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null, parent id 42\n", executionTree.toString());
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Then return {@code null (process instance)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_thenReturnNullProcessInstance() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Act and Assert
    assertEquals("null (process instance)\n", executionTree.toString());
  }

  /**
   * Test {@link ExecutionTree#toString()}.
   *
   * <ul>
   *   <li>Then return {@code null (process instance)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTree#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTree.toString()"})
  public void testToString_thenReturnNullProcessInstance2() {
    // Arrange
    ExecutionTreeNode root =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    root.setChildren(new ArrayList<>());

    ExecutionTree executionTree = new ExecutionTree();
    executionTree.setRoot(root);

    // Act and Assert
    assertEquals("null (process instance)\n", executionTree.toString());
  }
}
