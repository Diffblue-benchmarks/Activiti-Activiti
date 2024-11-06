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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class ExecutionGraphUtilDiffblueTest {
  /**
   * Test {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}.
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}
   */
  @Test
  public void testOrderFromRootToLeaf_thenReturnArrayList() {
    // Arrange
    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    executions.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    List<ExecutionEntity> actualOrderFromRootToLeafResult = ExecutionGraphUtil.orderFromRootToLeaf(executions);

    // Assert
    assertEquals(executions, actualOrderFromRootToLeafResult);
  }

  /**
   * Test {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}
   */
  @Test
  public void testOrderFromRootToLeaf_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setParentId(null);

    LinkedHashSet<ExecutionEntity> executions = new LinkedHashSet<>();
    executions.add(createWithEmptyRelationshipCollectionsResult);

    // Act
    List<ExecutionEntity> actualOrderFromRootToLeafResult = ExecutionGraphUtil.orderFromRootToLeaf(executions);

    // Assert
    assertEquals(1, actualOrderFromRootToLeafResult.size());
    assertSame(createWithEmptyRelationshipCollectionsResult, actualOrderFromRootToLeafResult.get(0));
  }

  /**
   * Test {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}
   */
  @Test
  public void testOrderFromRootToLeaf_thenReturnSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setId("42");
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult2 = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult2.setParentId("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(createWithEmptyRelationshipCollectionsResult2);
    executions.add(createWithEmptyRelationshipCollectionsResult);

    // Act
    List<ExecutionEntity> actualOrderFromRootToLeafResult = ExecutionGraphUtil.orderFromRootToLeaf(executions);

    // Assert
    assertEquals(2, actualOrderFromRootToLeafResult.size());
    ExecutionEntity getResult = actualOrderFromRootToLeafResult.get(0);
    assertTrue(getResult instanceof ExecutionEntityImpl);
    assertEquals("42", getResult.getId());
    assertSame(createWithEmptyRelationshipCollectionsResult2, actualOrderFromRootToLeafResult.get(1));
  }

  /**
   * Test {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}
   */
  @Test
  public void testOrderFromRootToLeaf_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    List<ExecutionEntity> actualOrderFromRootToLeafResult = ExecutionGraphUtil.orderFromRootToLeaf(new ArrayList<>());

    // Assert
    assertTrue(actualOrderFromRootToLeafResult.isEmpty());
  }

  /**
   * Test {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}.
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}
   */
  @Test
  public void testOrderFromLeafToRoot_thenReturnArrayList() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setId("42");
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult2 = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult2.setParentId("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(createWithEmptyRelationshipCollectionsResult2);
    executions.add(createWithEmptyRelationshipCollectionsResult);

    // Act
    List<ExecutionEntity> actualOrderFromLeafToRootResult = ExecutionGraphUtil.orderFromLeafToRoot(executions);

    // Assert
    assertEquals(executions, actualOrderFromLeafToRootResult);
  }

  /**
   * Test {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}
   */
  @Test
  public void testOrderFromLeafToRoot_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setParentId(null);

    LinkedHashSet<ExecutionEntity> executions = new LinkedHashSet<>();
    executions.add(createWithEmptyRelationshipCollectionsResult);

    // Act
    List<ExecutionEntity> actualOrderFromLeafToRootResult = ExecutionGraphUtil.orderFromLeafToRoot(executions);

    // Assert
    assertEquals(1, actualOrderFromLeafToRootResult.size());
    ExecutionEntity getResult = actualOrderFromLeafToRootResult.get(0);
    assertTrue(getResult instanceof ExecutionEntityImpl);
    assertNull(getResult.getParentId());
    assertNull(getResult.getParent());
    assertTrue(getResult.getTransientVariables().isEmpty());
    assertTrue(getResult.getVariableInstances().isEmpty());
    assertTrue(getResult.getVariables().isEmpty());
    assertTrue(getResult.getVariableNames().isEmpty());
    assertTrue(getResult.isProcessInstanceType());
  }

  /**
   * Test {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}
   */
  @Test
  public void testOrderFromLeafToRoot_thenReturnSizeIsTwo() {
    // Arrange
    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    executions.add(createWithEmptyRelationshipCollectionsResult);
    executions.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    List<ExecutionEntity> actualOrderFromLeafToRootResult = ExecutionGraphUtil.orderFromLeafToRoot(executions);

    // Assert
    assertEquals(2, actualOrderFromLeafToRootResult.size());
    ExecutionEntity getResult = actualOrderFromLeafToRootResult.get(0);
    assertTrue(getResult instanceof ExecutionEntityImpl);
    assertNull(getResult.getParentId());
    assertNull(getResult.getParent());
    assertTrue(getResult.getTransientVariables().isEmpty());
    assertTrue(getResult.getVariableInstances().isEmpty());
    assertTrue(getResult.getVariables().isEmpty());
    assertTrue(getResult.getVariableNames().isEmpty());
    assertTrue(getResult.isProcessInstanceType());
    assertSame(createWithEmptyRelationshipCollectionsResult, actualOrderFromLeafToRootResult.get(1));
  }

  /**
   * Test {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}
   */
  @Test
  public void testOrderFromLeafToRoot_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    List<ExecutionEntity> actualOrderFromLeafToRootResult = ExecutionGraphUtil.orderFromLeafToRoot(new ArrayList<>());

    // Assert
    assertTrue(actualOrderFromLeafToRootResult.isEmpty());
  }

  /**
   * Test {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   * with {@code process}, {@code sourceElement}, {@code targetElement},
   * {@code visitedElements}.
   * <p>
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachableWithProcessSourceElementTargetElementVisitedElements() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());
    AdhocSubProcess sourceElement = new AdhocSubProcess();
    AdhocSubProcess targetElement = new AdhocSubProcess();
    HashSet<String> visitedElements = new HashSet<>();

    // Act
    boolean actualIsReachableResult = ExecutionGraphUtil.isReachable(process, sourceElement, targetElement,
        visitedElements);

    // Assert
    assertEquals(1, visitedElements.size());
    assertFalse(actualIsReachableResult);
    assertTrue(visitedElements.contains(null));
  }

  /**
   * Test {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   * with {@code process}, {@code sourceElement}, {@code targetElement},
   * {@code visitedElements}.
   * <p>
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachableWithProcessSourceElementTargetElementVisitedElements2() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess sourceElement = new AdhocSubProcess();
    AdhocSubProcess targetElement = new AdhocSubProcess();

    HashSet<String> visitedElements = new HashSet<>();
    visitedElements.add("foo");

    // Act
    boolean actualIsReachableResult = ExecutionGraphUtil.isReachable(process, sourceElement, targetElement,
        visitedElements);

    // Assert
    assertEquals(2, visitedElements.size());
    assertFalse(actualIsReachableResult);
    assertTrue(visitedElements.contains("foo"));
    assertTrue(visitedElements.contains(null));
  }

  /**
   * Test {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   * with {@code process}, {@code sourceElement}, {@code targetElement},
   * {@code visitedElements}.
   * <p>
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachableWithProcessSourceElementTargetElementVisitedElements3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());
    AdhocSubProcess sourceElement = new AdhocSubProcess();
    AdhocSubProcess targetElement = new AdhocSubProcess();
    HashSet<String> visitedElements = new HashSet<>();

    // Act
    boolean actualIsReachableResult = ExecutionGraphUtil.isReachable(process, sourceElement, targetElement,
        visitedElements);

    // Assert
    assertEquals(1, visitedElements.size());
    assertFalse(actualIsReachableResult);
    assertTrue(visitedElements.contains(null));
  }

  /**
   * Test {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   * with {@code process}, {@code sourceElement}, {@code targetElement},
   * {@code visitedElements}.
   * <p>
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachableWithProcessSourceElementTargetElementVisitedElements4() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess sourceElement = new AdhocSubProcess();
    sourceElement.setId("42");
    AdhocSubProcess targetElement = new AdhocSubProcess();
    HashSet<String> visitedElements = new HashSet<>();

    // Act
    boolean actualIsReachableResult = ExecutionGraphUtil.isReachable(process, sourceElement, targetElement,
        visitedElements);

    // Assert
    assertEquals(1, visitedElements.size());
    assertFalse(actualIsReachableResult);
    assertTrue(visitedElements.contains("42"));
  }

  /**
   * Test {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   * with {@code process}, {@code sourceElement}, {@code targetElement},
   * {@code visitedElements}.
   * <p>
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachableWithProcessSourceElementTargetElementVisitedElements5() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    AdhocSubProcess sourceElement = new AdhocSubProcess();
    sourceElement.setId("42");
    AdhocSubProcess targetElement = new AdhocSubProcess();
    HashSet<String> visitedElements = new HashSet<>();

    // Act
    boolean actualIsReachableResult = ExecutionGraphUtil.isReachable(process, sourceElement, targetElement,
        visitedElements);

    // Assert
    assertEquals(1, visitedElements.size());
    assertFalse(actualIsReachableResult);
    assertTrue(visitedElements.contains("42"));
  }

  /**
   * Test {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   * with {@code process}, {@code sourceElement}, {@code targetElement},
   * {@code visitedElements}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachableWithProcessSourceElementTargetElementVisitedElements_whenProcess() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess sourceElement = new AdhocSubProcess();
    AdhocSubProcess targetElement = new AdhocSubProcess();
    HashSet<String> visitedElements = new HashSet<>();

    // Act
    boolean actualIsReachableResult = ExecutionGraphUtil.isReachable(process, sourceElement, targetElement,
        visitedElements);

    // Assert
    assertEquals(1, visitedElements.size());
    assertFalse(actualIsReachableResult);
    assertTrue(visitedElements.contains(null));
  }
}
