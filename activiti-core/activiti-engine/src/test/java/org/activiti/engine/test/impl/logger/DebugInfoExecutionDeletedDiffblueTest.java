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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DebugInfoExecutionDeletedDiffblueTest {
  /**
   * Test {@link DebugInfoExecutionDeleted#DebugInfoExecutionDeleted(ExecutionEntity)}.
   *
   * <p>Method under test: {@link
   * DebugInfoExecutionDeleted#DebugInfoExecutionDeleted(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DebugInfoExecutionDeleted.<init>(ExecutionEntity)"})
  public void testNewDebugInfoExecutionDeleted() {
    // Arrange, Act and Assert
    assertTrue(
        new DebugInfoExecutionDeleted(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .getExecutionTrees()
            .isEmpty());
  }
}
