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
   * Method under test:
   * {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)}
   */
  @Test
  public void testIsRetained() {
    // Arrange
    VariableByExecutionIdMatcher variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    // Act and Assert
    assertFalse(variableByExecutionIdMatcher.isRetained(new VariableInstanceEntityImpl(), JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)}
   */
  @Test
  public void testIsRetained2() {
    // Arrange
    VariableByExecutionIdMatcher variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
    variableInstanceEntity.setExecutionId("42");

    // Act and Assert
    assertFalse(variableByExecutionIdMatcher.isRetained(variableInstanceEntity, "Parameter"));
  }

  /**
   * Method under test:
   * {@link VariableByExecutionIdMatcher#isRetained(VariableInstanceEntity, Object)}
   */
  @Test
  public void testIsRetained3() {
    // Arrange
    VariableByExecutionIdMatcher variableByExecutionIdMatcher = new VariableByExecutionIdMatcher();

    VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
    variableInstanceEntity.setExecutionId("42");

    // Act and Assert
    assertTrue(variableByExecutionIdMatcher.isRetained(variableInstanceEntity, "42"));
  }
}
