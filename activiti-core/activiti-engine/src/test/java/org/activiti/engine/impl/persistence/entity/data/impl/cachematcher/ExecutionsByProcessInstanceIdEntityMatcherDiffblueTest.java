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
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ExecutionsByProcessInstanceIdEntityMatcherDiffblueTest {
  /**
   * Test
   * {@link ExecutionsByProcessInstanceIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   * with {@code ExecutionEntity}, {@code Object}.
   * <p>
   * Method under test:
   * {@link ExecutionsByProcessInstanceIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithExecutionEntityObject() {
    // Arrange
    ExecutionsByProcessInstanceIdEntityMatcher executionsByProcessInstanceIdEntityMatcher = new ExecutionsByProcessInstanceIdEntityMatcher();

    // Act and Assert
    assertFalse(executionsByProcessInstanceIdEntityMatcher
        .isRetained(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), JSONObject.NULL));
  }

  /**
   * Test
   * {@link ExecutionsByProcessInstanceIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   * with {@code ExecutionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionsByProcessInstanceIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithExecutionEntityObject_given42_when42_thenReturnFalse() {
    // Arrange
    ExecutionsByProcessInstanceIdEntityMatcher executionsByProcessInstanceIdEntityMatcher = new ExecutionsByProcessInstanceIdEntityMatcher();
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setProcessInstanceId("42");

    // Act and Assert
    assertFalse(executionsByProcessInstanceIdEntityMatcher.isRetained(entity, "42"));
  }

  /**
   * Test
   * {@link ExecutionsByProcessInstanceIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   * with {@code ExecutionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code Parameter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionsByProcessInstanceIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithExecutionEntityObject_given42_whenParameter_thenReturnFalse() {
    // Arrange
    ExecutionsByProcessInstanceIdEntityMatcher executionsByProcessInstanceIdEntityMatcher = new ExecutionsByProcessInstanceIdEntityMatcher();
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setProcessInstanceId("42");

    // Act and Assert
    assertFalse(executionsByProcessInstanceIdEntityMatcher.isRetained(entity, "Parameter"));
  }
}
