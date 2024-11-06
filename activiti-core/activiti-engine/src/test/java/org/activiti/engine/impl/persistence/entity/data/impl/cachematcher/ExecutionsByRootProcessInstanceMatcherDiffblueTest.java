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
package org.activiti.engine.impl.persistence.entity.data.impl.cachematcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ExecutionsByRootProcessInstanceMatcherDiffblueTest {
  /**
   * Test
   * {@link ExecutionsByRootProcessInstanceMatcher#isRetained(ExecutionEntity, Object)}
   * with {@code ExecutionEntity}, {@code Object}.
   * <p>
   * Method under test:
   * {@link ExecutionsByRootProcessInstanceMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithExecutionEntityObject() {
    // Arrange
    ExecutionsByRootProcessInstanceMatcher executionsByRootProcessInstanceMatcher = new ExecutionsByRootProcessInstanceMatcher();

    // Act and Assert
    assertFalse(executionsByRootProcessInstanceMatcher
        .isRetained(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), JSONObject.NULL));
  }

  /**
   * Test
   * {@link ExecutionsByRootProcessInstanceMatcher#isRetained(ExecutionEntity, Object)}
   * with {@code ExecutionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionsByRootProcessInstanceMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithExecutionEntityObject_given42_when42_thenReturnTrue() {
    // Arrange
    ExecutionsByRootProcessInstanceMatcher executionsByRootProcessInstanceMatcher = new ExecutionsByRootProcessInstanceMatcher();
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setRootProcessInstanceId("42");

    // Act and Assert
    assertTrue(executionsByRootProcessInstanceMatcher.isRetained(entity, "42"));
  }

  /**
   * Test
   * {@link ExecutionsByRootProcessInstanceMatcher#isRetained(ExecutionEntity, Object)}
   * with {@code ExecutionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code Parameter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionsByRootProcessInstanceMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithExecutionEntityObject_given42_whenParameter_thenReturnFalse() {
    // Arrange
    ExecutionsByRootProcessInstanceMatcher executionsByRootProcessInstanceMatcher = new ExecutionsByRootProcessInstanceMatcher();
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setRootProcessInstanceId("42");

    // Act and Assert
    assertFalse(executionsByRootProcessInstanceMatcher.isRetained(entity, "Parameter"));
  }
}
