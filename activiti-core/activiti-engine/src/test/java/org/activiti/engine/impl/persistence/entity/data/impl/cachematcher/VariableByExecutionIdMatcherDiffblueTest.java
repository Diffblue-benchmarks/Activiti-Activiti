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
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class VariableByExecutionIdMatcherDiffblueTest {
  /**
   * Test
   * {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)}
   * with {@code VariableInstanceEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)}
   */
  @Test
  public void testIsRetainedWithVariableInstanceEntityObject_given42_when42_thenReturnTrue() {
    // Arrange
    VariableByExecutionIdMatcher variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
    variableInstanceEntity.setExecutionId("42");

    // Act and Assert
    assertTrue(variableByExecutionIdMatcher.isRetained(variableInstanceEntity, "42"));
  }

  /**
   * Test
   * {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)}
   * with {@code VariableInstanceEntity}, {@code Object}.
   * <ul>
   *   <li>When {@code Parameter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)}
   */
  @Test
  public void testIsRetainedWithVariableInstanceEntityObject_whenParameter_thenReturnFalse() {
    // Arrange
    VariableByExecutionIdMatcher variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
    variableInstanceEntity.setExecutionId("42");

    // Act and Assert
    assertFalse(variableByExecutionIdMatcher.isRetained(variableInstanceEntity, "Parameter"));
  }

  /**
   * Test
   * {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)}
   * with {@code VariableInstanceEntity}, {@code Object}.
   * <ul>
   *   <li>When {@link VariableInstanceEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)}
   */
  @Test
  public void testIsRetainedWithVariableInstanceEntityObject_whenVariableInstanceEntityImpl() {
    // Arrange
    VariableByExecutionIdMatcher variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    // Act and Assert
    assertFalse(variableByExecutionIdMatcher.isRetained(new VariableInstanceEntityImpl(), JSONObject.NULL));
  }
}
