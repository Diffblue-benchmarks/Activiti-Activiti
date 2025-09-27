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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ParallelGatewayActivityBehaviorDiffblueTest {
  /**
   * Test {@link ParallelGatewayActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ParallelGatewayActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ParallelGatewayActivityBehavior.execute(DelegateExecution)"})
  public void testExecute_thenThrowActivitiException() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior =
        new ParallelGatewayActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            parallelGatewayActivityBehavior.execute(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelGatewayActivityBehavior#cleanJoinedExecutions(Collection,
   * DelegateExecution)}.
   *
   * <ul>
   *   <li>Then return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link ParallelGatewayActivityBehavior#cleanJoinedExecutions(Collection,
   * DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection ParallelGatewayActivityBehavior.cleanJoinedExecutions(Collection, DelegateExecution)"
  })
  public void testCleanJoinedExecutions_thenReturnList() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior =
        new ParallelGatewayActivityBehavior();

    LinkedHashSet<ExecutionEntity> joinedExecutions = new LinkedHashSet<>();
    joinedExecutions.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Collection<ExecutionEntity> actualCleanJoinedExecutionsResult =
        parallelGatewayActivityBehavior.cleanJoinedExecutions(
            joinedExecutions, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualCleanJoinedExecutionsResult instanceof List);
    assertTrue(actualCleanJoinedExecutionsResult.isEmpty());
  }

  /**
   * Test {@link ParallelGatewayActivityBehavior#cleanJoinedExecutions(Collection,
   * DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ParallelGatewayActivityBehavior#cleanJoinedExecutions(Collection,
   * DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection ParallelGatewayActivityBehavior.cleanJoinedExecutions(Collection, DelegateExecution)"
  })
  public void testCleanJoinedExecutions_whenArrayListAddCreateWithEmptyRelationshipCollections() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior =
        new ParallelGatewayActivityBehavior();

    ArrayList<ExecutionEntity> joinedExecutions = new ArrayList<>();
    joinedExecutions.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    joinedExecutions.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Collection<ExecutionEntity> actualCleanJoinedExecutionsResult =
        parallelGatewayActivityBehavior.cleanJoinedExecutions(
            joinedExecutions, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualCleanJoinedExecutionsResult instanceof List);
    assertTrue(actualCleanJoinedExecutionsResult.isEmpty());
  }

  /**
   * Test {@link ParallelGatewayActivityBehavior#cleanJoinedExecutions(Collection,
   * DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ParallelGatewayActivityBehavior#cleanJoinedExecutions(Collection,
   * DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection ParallelGatewayActivityBehavior.cleanJoinedExecutions(Collection, DelegateExecution)"
  })
  public void testCleanJoinedExecutions_whenArrayList_thenReturnArrayList() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior =
        new ParallelGatewayActivityBehavior();
    ArrayList<ExecutionEntity> joinedExecutions = new ArrayList<>();

    // Act
    Collection<ExecutionEntity> actualCleanJoinedExecutionsResult =
        parallelGatewayActivityBehavior.cleanJoinedExecutions(
            joinedExecutions, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertEquals(joinedExecutions, actualCleanJoinedExecutionsResult);
  }

  /**
   * Test {@link ParallelGatewayActivityBehavior#isChildOfMultiInstanceExecution(DelegateExecution,
   * DelegateExecution)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ParallelGatewayActivityBehavior#isChildOfMultiInstanceExecution(DelegateExecution,
   * DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ParallelGatewayActivityBehavior.isChildOfMultiInstanceExecution(DelegateExecution, DelegateExecution)"
  })
  public void testIsChildOfMultiInstanceExecution_thenReturnFalse() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior =
        new ParallelGatewayActivityBehavior();
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertFalse(
        parallelGatewayActivityBehavior.isChildOfMultiInstanceExecution(
            executionEntity, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelGatewayActivityBehavior#hasMultiInstanceParent(FlowNode)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ParallelGatewayActivityBehavior#hasMultiInstanceParent(FlowNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ParallelGatewayActivityBehavior.hasMultiInstanceParent(FlowNode)"})
  public void testHasMultiInstanceParent_givenAdhocSubProcess_thenReturnFalse() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior =
        new ParallelGatewayActivityBehavior();

    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setParentContainer(new AdhocSubProcess());

    // Act and Assert
    assertFalse(parallelGatewayActivityBehavior.hasMultiInstanceParent(flowNode));
  }

  /**
   * Test {@link ParallelGatewayActivityBehavior#hasMultiInstanceParent(FlowNode)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ParallelGatewayActivityBehavior#hasMultiInstanceParent(FlowNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ParallelGatewayActivityBehavior.hasMultiInstanceParent(FlowNode)"})
  public void testHasMultiInstanceParent_thenReturnTrue() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior =
        new ParallelGatewayActivityBehavior();

    AdhocSubProcess parentContainer = new AdhocSubProcess();
    parentContainer.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setParentContainer(parentContainer);

    // Act and Assert
    assertTrue(parallelGatewayActivityBehavior.hasMultiInstanceParent(flowNode));
  }

  /**
   * Test {@link ParallelGatewayActivityBehavior#hasMultiInstanceParent(FlowNode)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ParallelGatewayActivityBehavior#hasMultiInstanceParent(FlowNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ParallelGatewayActivityBehavior.hasMultiInstanceParent(FlowNode)"})
  public void testHasMultiInstanceParent_whenAdhocSubProcess_thenReturnFalse() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior =
        new ParallelGatewayActivityBehavior();

    // Act and Assert
    assertFalse(parallelGatewayActivityBehavior.hasMultiInstanceParent(new AdhocSubProcess()));
  }

  /**
   * Test {@link
   * ParallelGatewayActivityBehavior#findMultiInstanceParentExecution(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ParallelGatewayActivityBehavior#findMultiInstanceParentExecution(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DelegateExecution ParallelGatewayActivityBehavior.findMultiInstanceParentExecution(DelegateExecution)"
  })
  public void testFindMultiInstanceParentExecution_thenReturnNull() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior =
        new ParallelGatewayActivityBehavior();

    // Act and Assert
    assertNull(
        parallelGatewayActivityBehavior.findMultiInstanceParentExecution(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }
}
