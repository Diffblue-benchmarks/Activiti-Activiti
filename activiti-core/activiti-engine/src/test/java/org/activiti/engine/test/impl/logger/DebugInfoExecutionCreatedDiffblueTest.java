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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DebugInfoExecutionCreatedDiffblueTest {
  /**
   * Test {@link DebugInfoExecutionCreated#DebugInfoExecutionCreated(ExecutionEntity)}.
   * <ul>
   *   <li>Then {@link DebugInfoExecutionCreated#executionEntity} return {@link ExecutionEntityImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DebugInfoExecutionCreated#DebugInfoExecutionCreated(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DebugInfoExecutionCreated.<init>(ExecutionEntity)"})
  public void testNewDebugInfoExecutionCreated_thenExecutionEntityReturnExecutionEntityImpl() {
    // Arrange and Act
    DebugInfoExecutionCreated actualDebugInfoExecutionCreated = new DebugInfoExecutionCreated(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualDebugInfoExecutionCreated.executionEntity instanceof ExecutionEntityImpl);
    assertNull(actualDebugInfoExecutionCreated.flowElementId);
    assertTrue(actualDebugInfoExecutionCreated.getExecutionTrees().isEmpty());
  }
}
