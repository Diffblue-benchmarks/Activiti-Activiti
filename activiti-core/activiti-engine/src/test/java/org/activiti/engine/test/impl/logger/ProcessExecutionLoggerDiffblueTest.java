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
import org.activiti.engine.test.impl.logger.DebugInfoExecutionTree.DebugInfoExecutionTreeNode;
import org.junit.Test;

public class ProcessExecutionLoggerDiffblueTest {
  /**
   * Test new {@link ProcessExecutionLogger} (default constructor).
   * <p>
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

  /**
   * Test {@link ProcessExecutionLogger#generateExecutionTrees()}.
   * <p>
   * Method under test: {@link ProcessExecutionLogger#generateExecutionTrees()}
   */
  @Test
  public void testGenerateExecutionTrees() {
    // Arrange, Act and Assert
    assertTrue((new ProcessExecutionLogger()).generateExecutionTrees().isEmpty());
  }

  /**
   * Test
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTreeNode, Map)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link DebugInfoExecutionTreeNode#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTree.DebugInfoExecutionTreeNode, Map)}
   */
  @Test
  public void testInternalPopulateExecutionTree_given42_whenHashMap_thenCallsGetId() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode parentNode = mock(
        DebugInfoExecutionTree.DebugInfoExecutionTreeNode.class);
    when(parentNode.getId()).thenReturn("42");

    // Act
    processExecutionLogger.internalPopulateExecutionTree(parentNode, new HashMap<>());

    // Assert that nothing has changed
    verify(parentNode).getId();
  }

  /**
   * Test
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTreeNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link DebugInfoExecutionTreeNode#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTree.DebugInfoExecutionTreeNode, Map)}
   */
  @Test
  public void testInternalPopulateExecutionTree_givenArrayList_thenCallsGetId() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    DebugInfoExecutionTree.DebugInfoExecutionTreeNode parentNode = mock(
        DebugInfoExecutionTree.DebugInfoExecutionTreeNode.class);
    when(parentNode.getId()).thenReturn("42");

    HashMap<String, List<ExecutionEntity>> parentMapping = new HashMap<>();
    parentMapping.put("42", new ArrayList<>());
    parentMapping.put("foo", new ArrayList<>());

    // Act
    processExecutionLogger.internalPopulateExecutionTree(parentNode, parentMapping);

    // Assert that nothing has changed
    verify(parentNode, atLeast(1)).getId();
  }

  /**
   * Test
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTreeNode, Map)}.
   * <ul>
   *   <li>Then calls {@link DebugInfoExecutionTreeNode#getChildNodes()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExecutionLogger#internalPopulateExecutionTree(DebugInfoExecutionTree.DebugInfoExecutionTreeNode, Map)}
   */
  @Test
  public void testInternalPopulateExecutionTree_thenCallsGetChildNodes() {
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
  }

  /**
   * Test {@link ProcessExecutionLogger#logDebugInfo(boolean)} with
   * {@code boolean}.
   * <p>
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo(boolean)}
   */
  @Test
  public void testLogDebugInfoWithBoolean() {
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
   * Test {@link ProcessExecutionLogger#logDebugInfo(boolean)} with
   * {@code boolean}.
   * <ul>
   *   <li>Given {@link ProcessExecutionLogger} (default constructor).</li>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo(boolean)}
   */
  @Test
  public void testLogDebugInfoWithBoolean_givenProcessExecutionLogger_whenFalse() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();

    // Act
    processExecutionLogger.logDebugInfo(false);

    // Assert that nothing has changed
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
  }

  /**
   * Test {@link ProcessExecutionLogger#logDebugInfo(boolean)} with
   * {@code boolean}.
   * <ul>
   *   <li>Given {@link ProcessExecutionLogger} (default constructor).</li>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo(boolean)}
   */
  @Test
  public void testLogDebugInfoWithBoolean_givenProcessExecutionLogger_whenTrue() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();

    // Act
    processExecutionLogger.logDebugInfo(true);

    // Assert
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
  }

  /**
   * Test {@link ProcessExecutionLogger#executionCreated(ExecutionEntity)}.
   * <ul>
   *   <li>Then {@link ProcessExecutionLogger} (default constructor)
   * {@link ProcessExecutionLogger#createdExecutions} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExecutionLogger#executionCreated(ExecutionEntity)}
   */
  @Test
  public void testExecutionCreated_thenProcessExecutionLoggerCreatedExecutionsSizeIsOne() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    processExecutionLogger.executionCreated(executionEntity);

    // Assert
    Map<String, ExecutionEntity> stringExecutionEntityMap = processExecutionLogger.createdExecutions;
    assertEquals(1, stringExecutionEntityMap.size());
    assertSame(executionEntity, stringExecutionEntityMap.get(null));
  }

  /**
   * Test {@link ProcessExecutionLogger#executionDeleted(ExecutionEntity)}.
   * <ul>
   *   <li>Then {@link ProcessExecutionLogger} (default constructor)
   * {@link ProcessExecutionLogger#deletedExecutions} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExecutionLogger#executionDeleted(ExecutionEntity)}
   */
  @Test
  public void testExecutionDeleted_thenProcessExecutionLoggerDeletedExecutionsSizeIsOne() {
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
}
