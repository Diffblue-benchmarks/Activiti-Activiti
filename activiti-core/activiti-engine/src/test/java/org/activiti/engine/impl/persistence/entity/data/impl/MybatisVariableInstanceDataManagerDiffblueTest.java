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
package org.activiti.engine.impl.persistence.entity.data.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.VariableByExecutionIdMatcher;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisVariableInstanceDataManagerDiffblueTest {
  /**
   * Test {@link MybatisVariableInstanceDataManager#MybatisVariableInstanceDataManager(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test: {@link MybatisVariableInstanceDataManager#MybatisVariableInstanceDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MybatisVariableInstanceDataManager.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewMybatisVariableInstanceDataManager() {
    // Arrange and Act
    MybatisVariableInstanceDataManager actualMybatisVariableInstanceDataManager = new MybatisVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(actualMybatisVariableInstanceDataManager.variableInstanceEntity instanceof VariableByExecutionIdMatcher);
    assertNull(actualMybatisVariableInstanceDataManager.getManagedEntitySubClasses());
    Class<VariableInstanceEntityImpl> expectedManagedEntityClass = VariableInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisVariableInstanceDataManager.getManagedEntityClass());
  }

  /**
   * Test {@link MybatisVariableInstanceDataManager#getManagedEntityClass()}.
   * <p>
   * Method under test: {@link MybatisVariableInstanceDataManager#getManagedEntityClass()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class MybatisVariableInstanceDataManager.getManagedEntityClass()"})
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends VariableInstanceEntity> actualManagedEntityClass = (new MybatisVariableInstanceDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<VariableInstanceEntityImpl> expectedManagedEntityClass = VariableInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }
}
