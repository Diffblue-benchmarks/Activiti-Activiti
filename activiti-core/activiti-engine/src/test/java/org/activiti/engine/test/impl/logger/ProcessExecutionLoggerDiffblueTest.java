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
package org.activiti.engine.test.impl.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class ProcessExecutionLoggerDiffblueTest {
  /**
   * Method under test: {@link ProcessExecutionLogger#generateExecutionTrees()}
   */
  @Test
  public void testGenerateExecutionTrees() {
    // Arrange, Act and Assert
    assertTrue((new ProcessExecutionLogger()).generateExecutionTrees().isEmpty());
  }

  /**
   * Method under test:
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTree.DebugInfoExecutionTreeNode, Map)}
   */
  @Test
  public void testInternalPopulateExecutionTree() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode parentNode = new DebugInfoExecutionTree.DebugInfoExecutionTreeNode();
    HashMap<String, List<ExecutionEntity>> parentMapping = new HashMap<>();

    // Act
    processExecutionLogger.internalPopulateExecutionTree(parentNode, parentMapping);

    // Assert that nothing has changed
    assertTrue(parentMapping.isEmpty());
  }

  /**
   * Method under test:
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTree.DebugInfoExecutionTreeNode, Map)}
   */
  @Test
  public void testInternalPopulateExecutionTree2() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode parentNode = mock(
        DebugInfoExecutionTree.DebugInfoExecutionTreeNode.class);
    when(parentNode.getId()).thenReturn("42");
    HashMap<String, List<ExecutionEntity>> parentMapping = new HashMap<>();

    // Act
    processExecutionLogger.internalPopulateExecutionTree(parentNode, parentMapping);

    // Assert that nothing has changed
    verify(parentNode).getId();
    assertTrue(parentMapping.isEmpty());
  }

  /**
   * Method under test:
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTree.DebugInfoExecutionTreeNode, Map)}
   */
  @Test
  public void testInternalPopulateExecutionTree3() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode parentNode = mock(
        DebugInfoExecutionTree.DebugInfoExecutionTreeNode.class);
    when(parentNode.getId()).thenReturn("42");

    HashMap<String, List<ExecutionEntity>> parentMapping = new HashMap<>();
    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    parentMapping.put("42", executionEntityList);
    parentMapping.put("foo", new ArrayList<>());

    // Act
    processExecutionLogger.internalPopulateExecutionTree(parentNode, parentMapping);

    // Assert that nothing has changed
    verify(parentNode, atLeast(1)).getId();
    assertEquals(2, parentMapping.size());
    assertTrue(parentMapping.containsKey("foo"));
    List<ExecutionEntity> getResult = parentMapping.get("42");
    assertTrue(getResult.isEmpty());
    assertSame(executionEntityList, getResult);
  }

  /**
   * Method under test:
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTree.DebugInfoExecutionTreeNode, Map)}
   */
  @Test
  public void testInternalPopulateExecutionTree4() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode parentNode = mock(
        DebugInfoExecutionTree.DebugInfoExecutionTreeNode.class);
    when(parentNode.getChildNodes()).thenReturn(new ArrayList<>());
    when(parentNode.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    HashMap<String, List<ExecutionEntity>> parentMapping = new HashMap<>();
    parentMapping.put("42", executionEntityList);
    parentMapping.put("foo", new ArrayList<>());

    // Act
    processExecutionLogger.internalPopulateExecutionTree(parentNode, parentMapping);

    // Assert
    verify(parentNode).getChildNodes();
    verify(parentNode, atLeast(1)).getId();
    assertEquals(2, parentMapping.size());
    assertTrue(parentMapping.containsKey("foo"));
    assertSame(executionEntityList, parentMapping.get("42"));
  }

  /**
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo()}
   */
  @Test
  public void testLogDebugInfo() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();

    // Act
    processExecutionLogger.logDebugInfo();

    // Assert that nothing has changed
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
  }

  /**
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo(boolean)}
   */
  @Test
  public void testLogDebugInfo2() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();

    // Act
    processExecutionLogger.logDebugInfo(true);

    // Assert
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
  }

  /**
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo(boolean)}
   */
  @Test
  public void testLogDebugInfo3() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    processExecutionLogger
        .addDebugInfo(new DebugInfoExecutionCreated(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Act
    processExecutionLogger.logDebugInfo(true);

    // Assert
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
  }

  /**
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo(boolean)}
   */
  @Test
  public void testLogDebugInfo4() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();

    // Act
    processExecutionLogger.logDebugInfo(false);

    // Assert that nothing has changed
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
  }

  /**
   * Method under test:
   * {@link ProcessExecutionLogger#executionCreated(ExecutionEntity)}
   */
  @Test
  public void testExecutionCreated() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    processExecutionLogger.executionCreated(executionEntity);

    // Assert
    Map<String, ExecutionEntity> stringExecutionEntityMap = processExecutionLogger.createdExecutions;
    assertEquals(1, stringExecutionEntityMap.size());
    assertTrue(executionEntity.getProcessVariables().isEmpty());
    assertTrue(executionEntity.getTransientVariables().isEmpty());
    assertTrue(executionEntity.getTransientVariablesLocal().isEmpty());
    assertTrue(executionEntity.getUsedVariablesCache().isEmpty());
    assertTrue(executionEntity.getVariableInstanceEntities().isEmpty());
    assertTrue(executionEntity.getVariableInstances().isEmpty());
    assertTrue(executionEntity.getVariableInstancesLocal().isEmpty());
    assertTrue(executionEntity.getVariables().isEmpty());
    assertTrue(executionEntity.getVariablesLocal().isEmpty());
    assertTrue(processExecutionLogger.deletedExecutions.isEmpty());
    assertSame(executionEntity, stringExecutionEntityMap.get(null));
  }

  /**
   * Method under test:
   * {@link ProcessExecutionLogger#executionDeleted(ExecutionEntity)}
   */
  @Test
  public void testExecutionDeleted() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    processExecutionLogger.executionDeleted(executionEntity);

    // Assert
    Map<String, ExecutionEntity> stringExecutionEntityMap = processExecutionLogger.deletedExecutions;
    assertEquals(1, stringExecutionEntityMap.size());
    assertSame(executionEntity, stringExecutionEntityMap.get(null));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ProcessExecutionLogger}
   */
  @Test
  public void testNewProcessExecutionLogger() {
    // Arrange and Act
    ProcessExecutionLogger actualProcessExecutionLogger = new ProcessExecutionLogger();

    // Assert
    assertTrue(actualProcessExecutionLogger.createdExecutions.isEmpty());
    assertTrue(actualProcessExecutionLogger.debugInfoMap.isEmpty());
    assertTrue(actualProcessExecutionLogger.deletedExecutions.isEmpty());
  }
}
