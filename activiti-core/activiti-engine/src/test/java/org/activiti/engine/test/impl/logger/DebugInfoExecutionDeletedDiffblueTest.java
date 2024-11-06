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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.LinkedList;
import org.activiti.engine.impl.persistence.entity.AbstractEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.helpers.SubstituteLogger;

public class DebugInfoExecutionDeletedDiffblueTest {
  /**
   * Test
   * {@link DebugInfoExecutionDeleted#DebugInfoExecutionDeleted(ExecutionEntity)}.
   * <p>
   * Method under test:
   * {@link DebugInfoExecutionDeleted#DebugInfoExecutionDeleted(ExecutionEntity)}
   */
  @Test
  public void testNewDebugInfoExecutionDeleted() {
    // Arrange, Act and Assert
    assertTrue((new DebugInfoExecutionDeleted(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .getExecutionTrees()
        .isEmpty());
  }

  /**
   * Test {@link DebugInfoExecutionDeleted#printOut(Logger)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link AbstractEntity#getId()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link AbstractEntity#getId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DebugInfoExecutionDeleted#printOut(Logger)}
   */
  @Test
  public void testPrintOut_givenExecutionEntityImplGetIdReturn42_thenCallsGetId() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenReturn("42");
    DebugInfoExecutionDeleted debugInfoExecutionDeleted = new DebugInfoExecutionDeleted(executionEntity);

    // Act
    debugInfoExecutionDeleted.printOut(new SubstituteLogger("Name", new LinkedList<>(), true));

    // Assert that nothing has changed
    verify(executionEntity).getId();
  }
}
