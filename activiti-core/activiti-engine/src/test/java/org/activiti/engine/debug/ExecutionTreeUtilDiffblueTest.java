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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class ExecutionTreeUtilDiffblueTest {
  /**
   * Test {@link ExecutionTreeUtil#buildExecutionTree(DelegateExecution)} with
   * {@code executionEntity}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeUtil#buildExecutionTree(DelegateExecution)}
   */
  @Test
  public void testBuildExecutionTreeWithExecutionEntity_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getParent()).thenThrow(new ActivitiException("An error occurred"));
    when(executionEntity.getParentId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> ExecutionTreeUtil.buildExecutionTree(executionEntity));
    verify(executionEntity).getParent();
    verify(executionEntity, atLeast(1)).getParentId();
  }

  /**
   * Test {@link ExecutionTreeUtil#buildExecutionTree(Collection)} with
   * {@code executions}.
   * <p>
   * Method under test: {@link ExecutionTreeUtil#buildExecutionTree(Collection)}
   */
  @Test
  public void testBuildExecutionTreeWithExecutions() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setParentId("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(createWithEmptyRelationshipCollectionsResult);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> ExecutionTreeUtil.buildExecutionTree(executions));
  }

  /**
   * Test {@link ExecutionTreeUtil#buildExecutionTree(Collection)} with
   * {@code executions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeUtil#buildExecutionTree(Collection)}
   */
  @Test
  public void testBuildExecutionTreeWithExecutions_whenArrayList_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> ExecutionTreeUtil.buildExecutionTree(new ArrayList<>()));
  }

  /**
   * Test {@link ExecutionTreeUtil#collectChildExecutions(ExecutionEntity, List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeUtil#collectChildExecutions(ExecutionEntity, List)}
   */
  @Test
  public void testCollectChildExecutions_givenNull_thenArrayListEmpty() {
    // Arrange
    ExecutionEntityImpl rootExecutionEntity = mock(ExecutionEntityImpl.class);
    when(rootExecutionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(rootExecutionEntity.getSubProcessInstance()).thenReturn(null);
    doNothing().when(rootExecutionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    rootExecutionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ArrayList<ExecutionEntity> allExecutions = new ArrayList<>();

    // Act
    ExecutionTreeUtil.collectChildExecutions(rootExecutionEntity, allExecutions);

    // Assert that nothing has changed
    verify(rootExecutionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(rootExecutionEntity).getExecutions();
    verify(rootExecutionEntity).getSubProcessInstance();
    assertTrue(allExecutions.isEmpty());
  }

  /**
   * Test {@link ExecutionTreeUtil#collectChildExecutions(ExecutionEntity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeUtil#collectChildExecutions(ExecutionEntity, List)}
   */
  @Test
  public void testCollectChildExecutions_thenArrayListSizeIsOne() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl.getSubProcessInstance()).thenReturn(null);
    ExecutionEntityImpl rootExecutionEntity = mock(ExecutionEntityImpl.class);
    when(rootExecutionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(rootExecutionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(rootExecutionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    rootExecutionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ArrayList<ExecutionEntity> allExecutions = new ArrayList<>();

    // Act
    ExecutionTreeUtil.collectChildExecutions(rootExecutionEntity, allExecutions);

    // Assert
    verify(rootExecutionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(rootExecutionEntity).getExecutions();
    verify(executionEntityImpl).getExecutions();
    verify(executionEntityImpl).getSubProcessInstance();
    verify(rootExecutionEntity, atLeast(1)).getSubProcessInstance();
    assertEquals(1, allExecutions.size());
  }

  /**
   * Test {@link ExecutionTreeUtil#collectChildExecutions(ExecutionEntity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeUtil#collectChildExecutions(ExecutionEntity, List)}
   */
  @Test
  public void testCollectChildExecutions_thenArrayListSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl.getSubProcessInstance()).thenReturn(null);

    ArrayList<ExecutionEntityImpl> executionEntityImplList = new ArrayList<>();
    executionEntityImplList.add(executionEntityImpl);
    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl2.getSubProcessInstance()).thenReturn(null);
    ExecutionEntityImpl rootExecutionEntity = mock(ExecutionEntityImpl.class);
    when(rootExecutionEntity.getExecutions()).thenReturn(executionEntityImplList);
    when(rootExecutionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl2);
    doNothing().when(rootExecutionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    rootExecutionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ArrayList<ExecutionEntity> allExecutions = new ArrayList<>();

    // Act
    ExecutionTreeUtil.collectChildExecutions(rootExecutionEntity, allExecutions);

    // Assert
    verify(rootExecutionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(rootExecutionEntity).getExecutions();
    verify(executionEntityImpl).getExecutions();
    verify(executionEntityImpl2).getExecutions();
    verify(executionEntityImpl).getSubProcessInstance();
    verify(executionEntityImpl2).getSubProcessInstance();
    verify(rootExecutionEntity, atLeast(1)).getSubProcessInstance();
    assertEquals(2, allExecutions.size());
  }

  /**
   * Test
   * {@link ExecutionTreeUtil#buildExecutionTreeForProcessInstance(Collection)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeUtil#buildExecutionTreeForProcessInstance(Collection)}
   */
  @Test
  public void testBuildExecutionTreeForProcessInstance_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setParentId("Executions");

    LinkedHashSet<ExecutionEntity> executions = new LinkedHashSet<>();
    executions.add(createWithEmptyRelationshipCollectionsResult);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> ExecutionTreeUtil.buildExecutionTreeForProcessInstance(executions));
  }

  /**
   * Test
   * {@link ExecutionTreeUtil#buildExecutionTreeForProcessInstance(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Root is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeUtil#buildExecutionTreeForProcessInstance(Collection)}
   */
  @Test
  public void testBuildExecutionTreeForProcessInstance_whenArrayList_thenReturnRootIsNull() {
    // Arrange and Act
    ExecutionTree actualBuildExecutionTreeForProcessInstanceResult = ExecutionTreeUtil
        .buildExecutionTreeForProcessInstance(new ArrayList<>());

    // Assert
    assertTrue(actualBuildExecutionTreeForProcessInstanceResult.iterator() instanceof ExecutionTreeBfsIterator);
    assertNull(actualBuildExecutionTreeForProcessInstanceResult.getRoot());
  }

  /**
   * Test {@link ExecutionTreeUtil#fillExecutionTree(ExecutionTree, Map)}.
   * <ul>
   *   <li>When {@link ExecutionTree} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeUtil#fillExecutionTree(ExecutionTree, Map)}
   */
  @Test
  public void testFillExecutionTree_whenExecutionTree_thenThrowActivitiException() {
    // Arrange
    ExecutionTree executionTree = new ExecutionTree();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> ExecutionTreeUtil.fillExecutionTree(executionTree, new HashMap<>()));
  }
}
