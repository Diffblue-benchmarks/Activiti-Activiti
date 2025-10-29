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
   * Method under test:
   * {@link ExecutionsByProcessInstanceIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetained() {
    // Arrange
    ExecutionsByProcessInstanceIdEntityMatcher executionsByProcessInstanceIdEntityMatcher = new ExecutionsByProcessInstanceIdEntityMatcher();

    // Act and Assert
    assertFalse(executionsByProcessInstanceIdEntityMatcher
        .isRetained(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link ExecutionsByProcessInstanceIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetained2() {
    // Arrange
    ExecutionsByProcessInstanceIdEntityMatcher executionsByProcessInstanceIdEntityMatcher = new ExecutionsByProcessInstanceIdEntityMatcher();
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setProcessInstanceId("42");

    // Act and Assert
    assertFalse(executionsByProcessInstanceIdEntityMatcher.isRetained(entity, "Parameter"));
  }

  /**
   * Method under test:
   * {@link ExecutionsByProcessInstanceIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetained3() {
    // Arrange
    ExecutionsByProcessInstanceIdEntityMatcher executionsByProcessInstanceIdEntityMatcher = new ExecutionsByProcessInstanceIdEntityMatcher();
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setProcessInstanceId("42");

    // Act and Assert
    assertFalse(executionsByProcessInstanceIdEntityMatcher.isRetained(entity, "42"));
  }
}
