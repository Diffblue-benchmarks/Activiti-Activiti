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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
   * Test getters and setters.
   * <p>
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.<init>(ExecutionEntity)", "List ExecutionTreeNode.getChildren()",
      "ExecutionEntity ExecutionTreeNode.getExecutionEntity()", "ExecutionTreeNode ExecutionTreeNode.getParent()",
      "void ExecutionTreeNode.setChildren(List)", "void ExecutionTreeNode.setExecutionEntity(ExecutionEntity)",
      "void ExecutionTreeNode.setParent(ExecutionTreeNode)"})
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

    // Assert
    assertTrue(actualChildren.isEmpty());
    assertSame(children, actualChildren);
    assertSame(parent, actualParent);
    assertSame(executionEntity, actualExecutionEntity);
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections Ended is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_givenCreateWithEmptyRelationshipCollectionsEndedIsTrue() {
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
   * Test {@link ExecutionTreeNode#toString()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections MultiInstanceRoot is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_givenCreateWithEmptyRelationshipCollectionsMultiInstanceRootIsTrue() {
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
   * Test {@link ExecutionTreeNode#toString()}.
   * <ul>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnAString() {
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
   * Test {@link ExecutionTreeNode#toString()}.
   * <ul>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnAString2() {
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
   * Test {@link ExecutionTreeNode#toString()}.
   * <ul>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnAString3() {
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
   * Test {@link ExecutionTreeNode#toString()}.
   * <ul>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnAString4() {
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
   * Test {@link ExecutionTreeNode#toString()}.
   * <ul>
   *   <li>Then return {@code null, parent id 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnNullParentId42() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setParentId("42");

    // Act and Assert
    assertEquals("null, parent id 42\n", (new ExecutionTreeNode(executionEntity)).toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   * <ul>
   *   <li>Then return {@code null (process instance)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnNullProcessInstance() {
    // Arrange, Act and Assert
    assertEquals("null (process instance)\n",
        (new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections())).toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   * <ul>
   *   <li>Then return {@code null (process instance)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnNullProcessInstance2() {
    // Arrange
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(new ArrayList<>());

    // Act and Assert
    assertEquals("null (process instance)\n", executionTreeNode.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString() {
    // Arrange
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    executionTreeNode.setChildren(new ArrayList<>());
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
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString2() {
    // Arrange
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    executionTreeNode.setChildren(children);
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
    assertEquals("fooPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)"
        + " (ended)\n" + "Prefix    └── null : , parent id null (active) (scope)\n", strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString3() {
    // Arrange
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    children.add(new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    executionTreeNode.setChildren(children);
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
    assertEquals("fooPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)"
        + " (ended)\n" + "Prefix    ├── null : , parent id null (active) (scope)\n"
        + "Prefix    └── null : , parent id null (active) (scope)\n", strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link DelegateExecution#getCurrentFlowElement()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionEntityGetCurrentFlowElementReturnNull() {
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
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link DelegateExecution#isActive()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionEntityIsActiveReturnFalse() {
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
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link DelegateExecution#isEnded()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionEntityIsEndedReturnFalse() {
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
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link ExecutionEntity#isMultiInstanceRoot()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionEntityIsMultiInstanceRootReturnFalse() {
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
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link DelegateExecution#isScope()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionEntityIsScopeReturnFalse() {
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
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionTreeNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionTreeNode() {
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
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionTreeNode}.</li>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionTreeNode_whenFalse() {
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
   * Test {@link ExecutionTreeNode#getCurrentFlowElementId()}.
   * <ul>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeNode#getCurrentFlowElementId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionTreeNode.getCurrentFlowElementId()"})
  public void testGetCurrentFlowElementId_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", (new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .getCurrentFlowElementId());
  }
}
