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
   * Method under test: {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}
   */
  @Test
  public void testOrderFromRootToLeaf() {
    // Arrange and Act
    List<ExecutionEntity> actualOrderFromRootToLeafResult = ExecutionGraphUtil.orderFromRootToLeaf(new ArrayList<>());

    // Assert
    assertTrue(actualOrderFromRootToLeafResult.isEmpty());
  }

  /**
   * Method under test: {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}
   */
  @Test
  public void testOrderFromRootToLeaf2() {
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
   * Method under test: {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}
   */
  @Test
  public void testOrderFromRootToLeaf3() {
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
   * Method under test: {@link ExecutionGraphUtil#orderFromRootToLeaf(Collection)}
   */
  @Test
  public void testOrderFromRootToLeaf4() {
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
    assertSame(createWithEmptyRelationshipCollectionsResult, actualOrderFromRootToLeafResult.get(0));
    assertSame(createWithEmptyRelationshipCollectionsResult2, actualOrderFromRootToLeafResult.get(1));
  }

  /**
   * Method under test: {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}
   */
  @Test
  public void testOrderFromLeafToRoot() {
    // Arrange and Act
    List<ExecutionEntity> actualOrderFromLeafToRootResult = ExecutionGraphUtil.orderFromLeafToRoot(new ArrayList<>());

    // Assert
    assertTrue(actualOrderFromLeafToRootResult.isEmpty());
  }

  /**
   * Method under test: {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}
   */
  @Test
  public void testOrderFromLeafToRoot2() {
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
    assertSame(createWithEmptyRelationshipCollectionsResult, actualOrderFromLeafToRootResult.get(0));
  }

  /**
   * Method under test: {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}
   */
  @Test
  public void testOrderFromLeafToRoot3() {
    // Arrange
    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    executions.add(createWithEmptyRelationshipCollectionsResult);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult2 = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    executions.add(createWithEmptyRelationshipCollectionsResult2);

    // Act
    List<ExecutionEntity> actualOrderFromLeafToRootResult = ExecutionGraphUtil.orderFromLeafToRoot(executions);

    // Assert
    assertEquals(2, actualOrderFromLeafToRootResult.size());
    assertSame(createWithEmptyRelationshipCollectionsResult2, actualOrderFromLeafToRootResult.get(0));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualOrderFromLeafToRootResult.get(1));
  }

  /**
   * Method under test: {@link ExecutionGraphUtil#orderFromLeafToRoot(Collection)}
   */
  @Test
  public void testOrderFromLeafToRoot4() {
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
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachable() {
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

  /**
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachable2() {
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
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachable3() {
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
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachable4() {
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
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachable5() {
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
   * Method under test:
   * {@link ExecutionGraphUtil#isReachable(Process, FlowNode, FlowNode, Set)}
   */
  @Test
  public void testIsReachable6() {
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
}
