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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class ParallelGatewayActivityBehaviorDiffblueTest {
  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> parallelGatewayActivityBehavior.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#cleanJoinedExecutions(Collection, DelegateExecution)}
   */
  @Test
  public void testCleanJoinedExecutions() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();
    ArrayList<ExecutionEntity> joinedExecutions = new ArrayList<>();

    // Act
    Collection<ExecutionEntity> actualCleanJoinedExecutionsResult = parallelGatewayActivityBehavior
        .cleanJoinedExecutions(joinedExecutions, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualCleanJoinedExecutionsResult instanceof List);
    assertTrue(actualCleanJoinedExecutionsResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#cleanJoinedExecutions(Collection, DelegateExecution)}
   */
  @Test
  public void testCleanJoinedExecutions2() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();

    ArrayList<ExecutionEntity> joinedExecutions = new ArrayList<>();
    joinedExecutions.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Collection<ExecutionEntity> actualCleanJoinedExecutionsResult = parallelGatewayActivityBehavior
        .cleanJoinedExecutions(joinedExecutions, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualCleanJoinedExecutionsResult instanceof List);
    assertTrue(actualCleanJoinedExecutionsResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#cleanJoinedExecutions(Collection, DelegateExecution)}
   */
  @Test
  public void testCleanJoinedExecutions3() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();

    ArrayList<ExecutionEntity> joinedExecutions = new ArrayList<>();
    joinedExecutions.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    joinedExecutions.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Collection<ExecutionEntity> actualCleanJoinedExecutionsResult = parallelGatewayActivityBehavior
        .cleanJoinedExecutions(joinedExecutions, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualCleanJoinedExecutionsResult instanceof List);
    assertTrue(actualCleanJoinedExecutionsResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#isChildOfMultiInstanceExecution(DelegateExecution, DelegateExecution)}
   */
  @Test
  public void testIsChildOfMultiInstanceExecution() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertFalse(parallelGatewayActivityBehavior.isChildOfMultiInstanceExecution(executionEntity,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#hasMultiInstanceParent(FlowNode)}
   */
  @Test
  public void testHasMultiInstanceParent() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();

    // Act and Assert
    assertFalse(parallelGatewayActivityBehavior.hasMultiInstanceParent(new AdhocSubProcess()));
  }

  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#hasMultiInstanceParent(FlowNode)}
   */
  @Test
  public void testHasMultiInstanceParent2() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();

    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setParentContainer(new AdhocSubProcess());

    // Act and Assert
    assertFalse(parallelGatewayActivityBehavior.hasMultiInstanceParent(flowNode));
  }

  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#hasMultiInstanceParent(FlowNode)}
   */
  @Test
  public void testHasMultiInstanceParent3() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();

    AdhocSubProcess parentContainer = new AdhocSubProcess();
    parentContainer.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setParentContainer(parentContainer);

    // Act and Assert
    assertTrue(parallelGatewayActivityBehavior.hasMultiInstanceParent(flowNode));
  }

  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#findMultiInstanceParentExecution(DelegateExecution)}
   */
  @Test
  public void testFindMultiInstanceParentExecution() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();

    // Act and Assert
    assertNull(parallelGatewayActivityBehavior
        .findMultiInstanceParentExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ParallelGatewayActivityBehavior#findMultiInstanceParentExecution(DelegateExecution)}
   */
  @Test
  public void testFindMultiInstanceParentExecution2() {
    // Arrange
    ParallelGatewayActivityBehavior parallelGatewayActivityBehavior = new ParallelGatewayActivityBehavior();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setLockTime(mock(Date.class));

    // Act and Assert
    assertNull(parallelGatewayActivityBehavior.findMultiInstanceParentExecution(execution));
  }
}
