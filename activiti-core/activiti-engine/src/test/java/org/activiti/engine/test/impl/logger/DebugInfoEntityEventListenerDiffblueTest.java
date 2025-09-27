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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
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
   * Test {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity DebugInfoEntityEventListener.getExecutionEntity(ActivitiEvent)"
  })
  public void testGetExecutionEntity_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    DebugInfoEntityEventListener debugInfoEntityEventListener =
        new DebugInfoEntityEventListener(new ProcessExecutionLogger());
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualExecutionEntity =
        debugInfoEntityEventListener.getExecutionEntity(
            new ActivitiEntityEventImpl(
                createWithEmptyRelationshipCollectionsResult, ActivitiEventType.ENTITY_CREATED));

    // Assert
    assertSame(createWithEmptyRelationshipCollectionsResult, actualExecutionEntity);
  }

  /**
   * Test {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity DebugInfoEntityEventListener.getExecutionEntity(ActivitiEvent)"
  })
  public void testGetExecutionEntity_thenReturnNull() {
    // Arrange
    DebugInfoEntityEventListener debugInfoEntityEventListener =
        new DebugInfoEntityEventListener(new ProcessExecutionLogger());

    // Act
    ExecutionEntity actualExecutionEntity =
        debugInfoEntityEventListener.getExecutionEntity(
            new ActivitiEntityEventImpl(1, ActivitiEventType.ENTITY_CREATED));

    // Assert
    assertNull(actualExecutionEntity);
  }
}
