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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExecutionByProcessInstanceMatcherDiffblueTest {
  /**
   * Test {@link ExecutionByProcessInstanceMatcher#isRetained(ExecutionEntity, Object)} with {@code
   * ExecutionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link ExecutionByProcessInstanceMatcher#isRetained(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ExecutionByProcessInstanceMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject() {
    // Arrange
    ExecutionByProcessInstanceMatcher executionByProcessInstanceMatcher =
        new ExecutionByProcessInstanceMatcher();

    // Act and Assert
    assertFalse(
        executionByProcessInstanceMatcher.isRetained(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), JSONObject.NULL));
  }

  /**
   * Test {@link ExecutionByProcessInstanceMatcher#isRetained(ExecutionEntity, Object)} with {@code
   * ExecutionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Entity}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionByProcessInstanceMatcher#isRetained(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ExecutionByProcessInstanceMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject_givenEntity() {
    // Arrange
    ExecutionByProcessInstanceMatcher executionByProcessInstanceMatcher =
        new ExecutionByProcessInstanceMatcher();

    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setProcessInstanceId("Entity");

    // Act and Assert
    assertFalse(executionByProcessInstanceMatcher.isRetained(entity, "Parameter"));
  }

  /**
   * Test {@link ExecutionByProcessInstanceMatcher#isRetained(ExecutionEntity, Object)} with {@code
   * ExecutionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Parameter}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionByProcessInstanceMatcher#isRetained(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ExecutionByProcessInstanceMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject_givenParameter_thenReturnTrue() {
    // Arrange
    ExecutionByProcessInstanceMatcher executionByProcessInstanceMatcher =
        new ExecutionByProcessInstanceMatcher();

    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setProcessInstanceId("Parameter");

    // Act and Assert
    assertTrue(executionByProcessInstanceMatcher.isRetained(entity, "Parameter"));
  }
}
