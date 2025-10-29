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

public class ExecutionByProcessInstanceMatcherDiffblueTest {
  /**
   * Method under test:
   * {@link ExecutionByProcessInstanceMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetained() {
    // Arrange
    ExecutionByProcessInstanceMatcher executionByProcessInstanceMatcher = new ExecutionByProcessInstanceMatcher();

    // Act and Assert
    assertFalse(executionByProcessInstanceMatcher
        .isRetained(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link ExecutionByProcessInstanceMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetained2() {
    // Arrange
    ExecutionByProcessInstanceMatcher executionByProcessInstanceMatcher = new ExecutionByProcessInstanceMatcher();
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setProcessInstanceId("42");

    // Act and Assert
    assertFalse(executionByProcessInstanceMatcher.isRetained(entity, "Parameter"));
  }

  /**
   * Method under test:
   * {@link ExecutionByProcessInstanceMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  public void testIsRetained3() {
    // Arrange
    ExecutionByProcessInstanceMatcher executionByProcessInstanceMatcher = new ExecutionByProcessInstanceMatcher();
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setProcessInstanceId("42");

    // Act and Assert
    assertTrue(executionByProcessInstanceMatcher.isRetained(entity, "42"));
  }
}
