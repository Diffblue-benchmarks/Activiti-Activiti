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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExecutionTreeNodeDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionTreeNode.<init>(ExecutionEntity)",
    "List ExecutionTreeNode.getChildren()",
    "ExecutionEntity ExecutionTreeNode.getExecutionEntity()",
    "ExecutionTreeNode ExecutionTreeNode.getParent()",
    "void ExecutionTreeNode.setChildren(List)",
    "void ExecutionTreeNode.setExecutionEntity(ExecutionEntity)",
    "void ExecutionTreeNode.setParent(ExecutionTreeNode)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ExecutionTreeNode actualExecutionTreeNode =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    actualExecutionTreeNode.setChildren(children);
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    actualExecutionTreeNode.setExecutionEntity(executionEntity);
    ExecutionTreeNode parent =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
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
   * Test {@link ExecutionTreeNode#iterator()}.
   *
   * <p>Method under test: {@link ExecutionTreeNode#iterator()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Iterator ExecutionTreeNode.iterator()"})
  public void testIterator() {
    // Arrange and Act
    Iterator<ExecutionTreeNode> actualIteratorResult =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .iterator();

    // Assert
    ExecutionTreeNode expectedNextResult =
        ((ExecutionTreeBfsIterator) actualIteratorResult).rootNode;
    assertTrue(actualIteratorResult instanceof ExecutionTreeBfsIterator);
    ExecutionTreeNode actualNextResult = actualIteratorResult.next();
    assertFalse(actualIteratorResult.hasNext());
    assertSame(expectedNextResult, actualNextResult);
  }

  /**
   * Test {@link ExecutionTreeNode#leafsFirstIterator()}.
   *
   * <p>Method under test: {@link ExecutionTreeNode#leafsFirstIterator()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionTreeBfsIterator ExecutionTreeNode.leafsFirstIterator()"})
  public void testLeafsFirstIterator() {
    // Arrange
    ExecutionTreeNode executionTreeNode =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    ExecutionTreeBfsIterator actualLeafsFirstIteratorResult =
        executionTreeNode.leafsFirstIterator();

    // Assert
    ExecutionTreeNode executionTreeNode2 = actualLeafsFirstIteratorResult.rootNode;
    ExecutionTreeNode actualNextResult = actualLeafsFirstIteratorResult.next();
    assertFalse(actualLeafsFirstIteratorResult.hasNext());
    assertSame(executionTreeNode2, actualNextResult);
    Iterator<ExecutionTreeNode> iteratorResult = executionTreeNode.iterator();
    assertTrue(iteratorResult instanceof ExecutionTreeBfsIterator);
    ExecutionTreeNode actualNextResult2 = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertSame(executionTreeNode2, actualNextResult2);
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections Ended is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_givenCreateWithEmptyRelationshipCollectionsEndedIsTrue() {
    // Arrange
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setEnded(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode executionTreeNode2 =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children);

    // Act and Assert
    assertEquals(
        "null (process instance)\n└── null : , parent id null (active) (scope) (ended)\n",
        executionTreeNode2.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections MultiInstanceRoot is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_givenCreateWithEmptyRelationshipCollectionsMultiInstanceRootIsTrue() {
    // Arrange
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setMultiInstanceRoot(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode executionTreeNode2 =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children);

    // Act and Assert
    assertEquals(
        "null (process instance)\n└── null : , parent id null (active) (scope) (multi instance root)\n",
        executionTreeNode2.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnAString() {
    // Arrange
    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(children);

    // Act and Assert
    assertEquals(
        "null (process instance)\n└── null : , parent id null (active) (scope)\n",
        executionTreeNode.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnAString2() {
    // Arrange
    ExecutionTreeNode executionTreeNode =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(new ArrayList<>());

    ArrayList<ExecutionTreeNode> children = new ArrayList<>();
    children.add(executionTreeNode);

    ExecutionTreeNode executionTreeNode2 =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children);

    // Act and Assert
    assertEquals(
        "null (process instance)\n└── null : , parent id null (active) (scope)\n",
        executionTreeNode2.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
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

    ExecutionTreeNode executionTreeNode2 =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children2);

    // Act and Assert
    assertEquals(
        "null (process instance)\n"
            + "└── null : , parent id null (active) (scope)\n"
            + "    └── null : , parent id null (active) (scope)\n",
        executionTreeNode2.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
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

    ExecutionTreeNode executionTreeNode2 =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode2.setChildren(children2);

    // Act and Assert
    assertEquals(
        "null (process instance)\n"
            + "└── null : , parent id null (active) (scope)\n"
            + "    ├── null : , parent id null (active) (scope)\n"
            + "    └── null : , parent id null (active) (scope)\n",
        executionTreeNode2.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   *
   * <ul>
   *   <li>Then return {@code null, parent id 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnNullParentId42() {
    // Arrange
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setParentId("42");

    // Act and Assert
    assertEquals("null, parent id 42\n", new ExecutionTreeNode(executionEntity).toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   *
   * <ul>
   *   <li>Then return {@code null (process instance)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnNullProcessInstance() {
    // Arrange, Act and Assert
    assertEquals(
        "null (process instance)\n",
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .toString());
  }

  /**
   * Test {@link ExecutionTreeNode#toString()}.
   *
   * <ul>
   *   <li>Then return {@code null (process instance)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.toString()"})
  public void testToString_thenReturnNullProcessInstance2() {
    // Arrange
    ExecutionTreeNode executionTreeNode =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executionTreeNode.setChildren(new ArrayList<>());

    // Act and Assert
    assertEquals("null (process instance)\n", executionTreeNode.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString() {
    // Arrange
    ExecutionTreeNode executionTreeNode =
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    StringBuilder strb = new StringBuilder("Str");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", true);

    // Assert
    assertEquals("StrPrefix└── null : , parent id null (active) (scope)\n", strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString2() {
    // Arrange
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setMultiInstanceRoot(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    StringBuilder strb = new StringBuilder("Str");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", true);

    // Assert
    assertEquals(
        "StrPrefix└── null : , parent id null (active) (scope) (multi instance root)\n",
        strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    executionTreeNode.setChildren(new ArrayList<>());
    StringBuilder strb = new StringBuilder("Str");

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
        "StrPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)"
            + " (ended)\n",
        strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString4() {
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
    children.add(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    executionTreeNode.setChildren(children);
    StringBuilder strb = new StringBuilder("Str");

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
        "StrPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)"
            + " (ended)\n"
            + "Prefix    └── null : , parent id null (active) (scope)\n",
        strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString5() {
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
    children.add(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    children.add(
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    executionTreeNode.setChildren(children);
    StringBuilder strb = new StringBuilder("Str");

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
        "StrPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)"
            + " (ended)\n"
            + "Prefix    ├── null : , parent id null (active) (scope)\n"
            + "Prefix    └── null : , parent id null (active) (scope)\n",
        strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections Ended is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenCreateWithEmptyRelationshipCollectionsEndedIsTrue() {
    // Arrange
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setEnded(true);
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    StringBuilder strb = new StringBuilder("Str");

    // Act
    executionTreeNode.internalToString(strb, "Prefix", true);

    // Assert
    assertEquals(
        "StrPrefix└── null : , parent id null (active) (scope) (ended)\n", strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link ExecutionEntity#isActive()} return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionEntityIsActiveReturnFalse() {
    // Arrange
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.isActive()).thenReturn(false);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    StringBuilder strb = new StringBuilder("Str");

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
        "StrPrefix└── 42 : null (AdhocSubProcess, parent id 42 (not active) (scope) (multi instance root)"
            + " (ended)\n",
        strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link ExecutionEntity#isScope()} return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionEntityIsScopeReturnFalse() {
    // Arrange
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isEnded()).thenReturn(true);
    when(executionEntity.isScope()).thenReturn(false);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    ExecutionTreeNode executionTreeNode = new ExecutionTreeNode(executionEntity);
    StringBuilder strb = new StringBuilder("Str");

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
        "StrPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (multi instance root) (ended)\n",
        strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionTreeNode#ExecutionTreeNode(ExecutionEntity)} with {@link
   *       ExecutionEntity}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionTreeNodeWithExecutionEntity() {
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
    StringBuilder strb = new StringBuilder("Str");

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
        "StrPrefix└── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)"
            + " (ended)\n",
        strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#internalToString(StringBuilder, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionTreeNode#ExecutionTreeNode(ExecutionEntity)} with {@link
   *       ExecutionEntity}.
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#internalToString(StringBuilder, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionTreeNode.internalToString(StringBuilder, String, boolean)"})
  public void testInternalToString_givenExecutionTreeNodeWithExecutionEntity_whenFalse() {
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
    StringBuilder strb = new StringBuilder("Str");

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
        "StrPrefix├── 42 : null (AdhocSubProcess, parent id 42 (active) (scope) (multi instance root)"
            + " (ended)\n",
        strb.toString());
  }

  /**
   * Test {@link ExecutionTreeNode#getCurrentFlowElementId()}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeNode#getCurrentFlowElementId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeNode.getCurrentFlowElementId()"})
  public void testGetCurrentFlowElementId_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals(
        "",
        new ExecutionTreeNode(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .getCurrentFlowElementId());
  }
}
