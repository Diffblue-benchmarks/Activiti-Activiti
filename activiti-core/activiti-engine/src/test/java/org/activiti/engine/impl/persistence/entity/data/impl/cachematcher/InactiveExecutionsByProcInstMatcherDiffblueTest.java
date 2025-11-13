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
import java.util.HashMap;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InactiveExecutionsByProcInstMatcherDiffblueTest {
  /**
   * Test {@link InactiveExecutionsByProcInstMatcher#isRetained(ExecutionEntity, Object)} with
   * {@code ExecutionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Execution Entity}.
   * </ul>
   *
   * <p>Method under test: {@link InactiveExecutionsByProcInstMatcher#isRetained(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InactiveExecutionsByProcInstMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject_givenExecutionEntity() {
    // Arrange
    InactiveExecutionsByProcInstMatcher inactiveExecutionsByProcInstMatcher =
        new InactiveExecutionsByProcInstMatcher();

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setProcessInstanceId("Execution Entity");
    executionEntity.setActive(false);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Parameter");

    // Act and Assert
    assertFalse(inactiveExecutionsByProcInstMatcher.isRetained(executionEntity, objectObjectMap));
  }

  /**
   * Test {@link InactiveExecutionsByProcInstMatcher#isRetained(ExecutionEntity, Object)} with
   * {@code ExecutionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InactiveExecutionsByProcInstMatcher#isRetained(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InactiveExecutionsByProcInstMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject_givenNull() {
    // Arrange
    InactiveExecutionsByProcInstMatcher inactiveExecutionsByProcInstMatcher =
        new InactiveExecutionsByProcInstMatcher();

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setProcessInstanceId(null);
    executionEntity.setActive(false);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Parameter");

    // Act and Assert
    assertFalse(inactiveExecutionsByProcInstMatcher.isRetained(executionEntity, objectObjectMap));
  }

  /**
   * Test {@link InactiveExecutionsByProcInstMatcher#isRetained(ExecutionEntity, Object)} with
   * {@code ExecutionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InactiveExecutionsByProcInstMatcher#isRetained(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InactiveExecutionsByProcInstMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject_givenTrue() {
    // Arrange
    InactiveExecutionsByProcInstMatcher inactiveExecutionsByProcInstMatcher =
        new InactiveExecutionsByProcInstMatcher();

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setProcessInstanceId("Parameter");
    executionEntity.setActive(true);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Parameter");

    // Act and Assert
    assertFalse(inactiveExecutionsByProcInstMatcher.isRetained(executionEntity, objectObjectMap));
  }

  /**
   * Test {@link InactiveExecutionsByProcInstMatcher#isRetained(ExecutionEntity, Object)} with
   * {@code ExecutionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InactiveExecutionsByProcInstMatcher#isRetained(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InactiveExecutionsByProcInstMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject_thenReturnTrue() {
    // Arrange
    InactiveExecutionsByProcInstMatcher inactiveExecutionsByProcInstMatcher =
        new InactiveExecutionsByProcInstMatcher();

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setProcessInstanceId("Parameter");
    executionEntity.setActive(false);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Parameter");

    // Act and Assert
    assertTrue(inactiveExecutionsByProcInstMatcher.isRetained(executionEntity, objectObjectMap));
  }
}
