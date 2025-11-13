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
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class VariableByExecutionIdMatcherDiffblueTest {
  /**
   * Test {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)} with
   * {@code VariableInstanceEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Parameter}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean VariableByExecutionIdMatcher.isRetained(VariableInstanceEntity, Object)"
  })
  public void testIsRetainedWithVariableInstanceEntityObject_givenParameter_thenReturnTrue() {
    // Arrange
    VariableByExecutionIdMatcher variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
    variableInstanceEntity.setExecutionId("Parameter");

    // Act and Assert
    assertTrue(variableByExecutionIdMatcher.isRetained(variableInstanceEntity, "Parameter"));
  }

  /**
   * Test {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)} with
   * {@code VariableInstanceEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Variable Instance Entity}.
   * </ul>
   *
   * <p>Method under test: {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean VariableByExecutionIdMatcher.isRetained(VariableInstanceEntity, Object)"
  })
  public void testIsRetainedWithVariableInstanceEntityObject_givenVariableInstanceEntity() {
    // Arrange
    VariableByExecutionIdMatcher variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
    variableInstanceEntity.setExecutionId("Variable Instance Entity");

    // Act and Assert
    assertFalse(variableByExecutionIdMatcher.isRetained(variableInstanceEntity, "Parameter"));
  }

  /**
   * Test {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)} with
   * {@code VariableInstanceEntity}, {@code Object}.
   *
   * <ul>
   *   <li>When {@link VariableInstanceEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean VariableByExecutionIdMatcher.isRetained(VariableInstanceEntity, Object)"
  })
  public void testIsRetainedWithVariableInstanceEntityObject_whenVariableInstanceEntityImpl() {
    // Arrange
    VariableByExecutionIdMatcher variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    // Act and Assert
    assertFalse(
        variableByExecutionIdMatcher.isRetained(new VariableInstanceEntityImpl(), JSONObject.NULL));
  }
}
