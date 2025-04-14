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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessExecutionLoggerDiffblueTest {
  /**
   * Test new {@link ProcessExecutionLogger} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ProcessExecutionLogger}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessExecutionLogger.<init>()"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.List ProcessExecutionLogger.generateExecutionTrees()"})
  public void testGenerateExecutionTrees() {
    // Arrange, Act and Assert
    assertTrue((new ProcessExecutionLogger()).generateExecutionTrees().isEmpty());
  }

  /**
   * Test {@link ProcessExecutionLogger#logDebugInfo(boolean)} with {@code boolean}.
   * <p>
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessExecutionLogger.logDebugInfo(boolean)"})
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
   * Test {@link ProcessExecutionLogger#logDebugInfo(boolean)} with {@code boolean}.
   * <ul>
   *   <li>Given {@link ProcessExecutionLogger} (default constructor).</li>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessExecutionLogger.logDebugInfo(boolean)"})
  public void testLogDebugInfoWithBoolean_givenProcessExecutionLogger_whenFalse() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();

    // Act
    processExecutionLogger.logDebugInfo(false);

    // Assert that nothing has changed
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
  }

  /**
   * Test {@link ProcessExecutionLogger#logDebugInfo(boolean)} with {@code boolean}.
   * <ul>
   *   <li>Given {@link ProcessExecutionLogger} (default constructor).</li>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExecutionLogger#logDebugInfo(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessExecutionLogger.logDebugInfo(boolean)"})
  public void testLogDebugInfoWithBoolean_givenProcessExecutionLogger_whenTrue() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();

    // Act
    processExecutionLogger.logDebugInfo(true);

    // Assert that nothing has changed
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
  }

  /**
   * Test {@link ProcessExecutionLogger#executionCreated(ExecutionEntity)}.
   * <ul>
   *   <li>Then {@link ProcessExecutionLogger} (default constructor) {@link ProcessExecutionLogger#createdExecutions} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExecutionLogger#executionCreated(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessExecutionLogger.executionCreated(ExecutionEntity)"})
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
   *   <li>Then {@link ProcessExecutionLogger} (default constructor) {@link ProcessExecutionLogger#deletedExecutions} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExecutionLogger#executionDeleted(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessExecutionLogger.executionDeleted(ExecutionEntity)"})
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
