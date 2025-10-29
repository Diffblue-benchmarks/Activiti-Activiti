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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExecutionTreeNodeDiffblueTest {
  @Mock
  private ExecutionEntity executionEntity;

  @InjectMocks
  private ExecutionTreeNode executionTreeNode;

  /**
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("null (process instance)\n",
        (new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections())).toString());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  public void testToString2() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setParentId("42");

    // Act and Assert
    assertEquals("null, parent id 42\n", (new ExecutionTreeNode(executionEntity)).toString());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  public void testToString3() {
    // Arrange
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(new ArrayList<>());

    // Act and Assert
    assertEquals("null (process instance)\n", executionTreeNode.toString());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  public void testToString4() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(children);

    // Act and Assert
    assertEquals("null (process instance)\n└── null : , parent id null (active) (scope)\n",
        executionTreeNode.toString());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  public void testToString5() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setEnded(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode executionTreeNode2 = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children);

    // Act and Assert
    assertEquals("null (process instance)\n└── null : , parent id null (active) (scope) (ended)\n",
        executionTreeNode2.toString());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  public void testToString6() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setMultiInstanceRoot(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode executionTreeNode2 = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children);

    // Act and Assert
    assertEquals("null (process instance)\n└── null : , parent id null (active) (scope) (multi instance root)\n",
        executionTreeNode2.toString());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  public void testToString7() {
    // Arrange
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(new ArrayList<>());

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode executionTreeNode2 = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children);

    // Act and Assert
    assertEquals("null (process instance)\n└── null : , parent id null (active) (scope)\n",
        executionTreeNode2.toString());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  public void testToString8() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(children);

    ArrayList<ExecutionTreeNode> children2 = new ArrayList<>();
    children2.add(executionTreeNode);

    ExecutionTreeNode executionTreeNode2 = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children2);

    // Act and Assert
    assertEquals("null (process instance)\n" + "└── null : , parent id null (active) (scope)\n"
        + "    └── null : , parent id null (active) (scope)\n", executionTreeNode2.toString());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  public void testToString9() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(children);

    ArrayList<ExecutionTreeNode> children2 = new ArrayList<>();
    children2.add(executionTreeNode);

    ExecutionTreeNode executionTreeNode2 = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children2);

    // Act and Assert
    assertEquals("null (process instance)\n" + "└── null : , parent id null (active) (scope)\n"
        + "    ├── null : , parent id null (active) (scope)\n" + "    └── null : , parent id null (active) (scope)\n",
        executionTreeNode2.toString());
  }

  /**
   * Method under test:
   * {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString() {
    // Arrange
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    StringBuilder strb = new StringBuilder("foo");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", true);

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionEntity).getId();
    verify(executionEntity).getParentId();
    verify(executionEntity).isActive();
    verify(executionEntity).isEnded();
    verify(executionEntity).isScope();
    verify(executionEntity).isMultiInstanceRoot();
    assertEquals(
        "fooPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)" + " (ended)\n",
        strb.toString());
  }

  /**
   * Method under test:
   * {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString2() {
    // Arrange
    when(executionEntity.isActive()).thenReturn(false);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    StringBuilder strb = new StringBuilder("foo");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", true);

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionEntity).getId();
    verify(executionEntity).getParentId();
    verify(executionEntity).isActive();
    verify(executionEntity).isEnded();
    verify(executionEntity).isScope();
    verify(executionEntity).isMultiInstanceRoot();
    assertEquals("fooPrefix└── 42 : null (AdhocSubProcess, parent id 42 (not active) (scope) (multi instance root)"
        + " (ended)\n", strb.toString());
  }

  /**
   * Method under test:
   * {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString3() {
    // Arrange
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(false);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    StringBuilder strb = new StringBuilder("foo");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", true);

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionEntity).getId();
    verify(executionEntity).getParentId();
    verify(executionEntity).isActive();
    verify(executionEntity).isEnded();
    verify(executionEntity).isScope();
    verify(executionEntity).isMultiInstanceRoot();
    assertEquals("fooPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)\n",
        strb.toString());
  }

  /**
   * Method under test:
   * {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString4() {
    // Arrange
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(false);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    StringBuilder strb = new StringBuilder("foo");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", true);

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionEntity).getId();
    verify(executionEntity).getParentId();
    verify(executionEntity).isActive();
    verify(executionEntity).isEnded();
    verify(executionEntity).isScope();
    verify(executionEntity).isMultiInstanceRoot();
    assertEquals("fooPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (multi instance root) (ended)\n",
        strb.toString());
  }

  /**
   * Method under test:
   * {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString5() {
    // Arrange
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(false);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    StringBuilder strb = new StringBuilder("foo");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", true);

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionEntity).getId();
    verify(executionEntity).getParentId();
    verify(executionEntity).isActive();
    verify(executionEntity).isEnded();
    verify(executionEntity).isScope();
    verify(executionEntity).isMultiInstanceRoot();
    assertEquals("fooPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (ended)\n", strb.toString());
  }

  /**
   * Method under test:
   * {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString6() {
    // Arrange
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(null);
    StringBuilder strb = new StringBuilder("foo");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", true);

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionEntity).getId();
    verify(executionEntity).getParentId();
    verify(executionEntity).isActive();
    verify(executionEntity).isEnded();
    verify(executionEntity).isScope();
    verify(executionEntity).isMultiInstanceRoot();
    assertEquals("fooPrefix└── 42 : , parent id 42 (active) (scope) (multi instance root) (ended)\n", strb.toString());
  }

  /**
   * Method under test:
   * {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString7() {
    // Arrange
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    StringBuilder strb = new StringBuilder("foo");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", false);

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionEntity).getId();
    verify(executionEntity).getParentId();
    verify(executionEntity).isActive();
    verify(executionEntity).isEnded();
    verify(executionEntity).isScope();
    verify(executionEntity).isMultiInstanceRoot();
    assertEquals(
        "fooPrefix├── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)" + " (ended)\n",
        strb.toString());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#getCurrentFlowElementId()}
   */
  @Test
  public void testGetCurrentFlowElementId() {
    // Arrange, Act and Assert
    assertEquals("", (new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .getCurrentFlowElementId());
  }

  /**
   * Method under test: {@link ExecutionTreeNode#getCurrentFlowElementId()}
   */
  @Test
  public void testGetCurrentFlowElementId2() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setLockTime(mock(Date.class));

    // Act and Assert
    assertEquals("", (new ExecutionTreeNode(executionEntity)).getCurrentFlowElementId());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionTreeNode#ExecutionTreeNode(ExecutionEntity)}
   *   <li>{@link ExecutionTreeNode#setChildren(List)}
   *   <li>{@link ExecutionTreeNode#setExecutionEntity(ExecutionEntity)}
   *   <li>{@link ExecutionTreeNode#setParent(ExecutionTreeNode)}
   *   <li>{@link ExecutionTreeNode#getChildren()}
   *   <li>{@link ExecutionTreeNode#getExecutionEntity()}
   *   <li>{@link ExecutionTreeNode#getParent()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ExecutionTreeNode actualExecutionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    actualExecutionTreeNode.setChildren(children);
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    actualExecutionTreeNode.setExecutionEntity(executionEntity);
    ExecutionTreeNode parent = new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    actualExecutionTreeNode.setParent(parent);
    List<ExecutionTreeNode> actualChildren = actualExecutionTreeNode.getChildren();
    ExecutionEntity actualExecutionEntity = actualExecutionTreeNode.getExecutionEntity();
    ExecutionTreeNode actualParent = actualExecutionTreeNode.getParent();

    // Assert that nothing has changed
    assertTrue(actualChildren.isEmpty());
    assertSame(children, actualChildren);
    assertSame(parent, actualParent);
    assertSame(executionEntity, actualExecutionEntity);
  }
}
