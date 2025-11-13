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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DebugInfoEntityEventListenerDiffblueTest {
  /**
   * Test {@link DebugInfoEntityEventListener#DebugInfoEntityEventListener(ProcessExecutionLogger)}.
   *
   * <p>Method under test: {@link
   * DebugInfoEntityEventListener#DebugInfoEntityEventListener(ProcessExecutionLogger)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DebugInfoEntityEventListener.<init>(ProcessExecutionLogger)"})
  public void testNewDebugInfoEntityEventListener() {
    // Arrange and Act
    DebugInfoEntityEventListener actualDebugInfoEntityEventListener =
        new DebugInfoEntityEventListener(new ProcessExecutionLogger());

    // Assert
    ProcessExecutionLogger processExecutionLogger =
        actualDebugInfoEntityEventListener.processExecutionLogger;
    assertTrue(processExecutionLogger.createdExecutions.isEmpty());
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
    assertTrue(processExecutionLogger.deletedExecutions.isEmpty());
    assertTrue(actualDebugInfoEntityEventListener.isFailOnException());
  }

  /**
   * Test {@link DebugInfoEntityEventListener#onCreate(ActivitiEvent)}.
   *
   * <p>Method under test: {@link DebugInfoEntityEventListener#onCreate(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DebugInfoEntityEventListener.onCreate(ActivitiEvent)"})
  public void testOnCreate() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    DebugInfoEntityEventListener debugInfoEntityEventListener =
        new DebugInfoEntityEventListener(processExecutionLogger);

    // Act
    debugInfoEntityEventListener.onCreate(
        new ActivitiProcessCancelledEventImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    ProcessExecutionLogger processExecutionLogger2 =
        debugInfoEntityEventListener.processExecutionLogger;
    assertSame(processExecutionLogger.createdExecutions, processExecutionLogger2.createdExecutions);
    assertSame(processExecutionLogger.debugInfoMap, processExecutionLogger2.debugInfoMap);
  }

  /**
   * Test {@link DebugInfoEntityEventListener#onDelete(ActivitiEvent)}.
   *
   * <p>Method under test: {@link DebugInfoEntityEventListener#onDelete(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DebugInfoEntityEventListener.onDelete(ActivitiEvent)"})
  public void testOnDelete() {
    // Arrange
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    DebugInfoEntityEventListener debugInfoEntityEventListener =
        new DebugInfoEntityEventListener(processExecutionLogger);

    // Act
    debugInfoEntityEventListener.onDelete(
        new ActivitiProcessCancelledEventImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    ProcessExecutionLogger processExecutionLogger2 =
        debugInfoEntityEventListener.processExecutionLogger;
    assertSame(processExecutionLogger.debugInfoMap, processExecutionLogger2.debugInfoMap);
    assertSame(processExecutionLogger.deletedExecutions, processExecutionLogger2.deletedExecutions);
  }

  /**
   * Test {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}.
   *
   * <p>Method under test: {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity DebugInfoEntityEventListener.getExecutionEntity(ActivitiEvent)"
  })
  public void testGetExecutionEntity() {
    // Arrange
    DebugInfoEntityEventListener debugInfoEntityEventListener =
        new DebugInfoEntityEventListener(new ProcessExecutionLogger());
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualExecutionEntity =
        debugInfoEntityEventListener.getExecutionEntity(
            new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    assertSame(processInstance, actualExecutionEntity);
  }
}
