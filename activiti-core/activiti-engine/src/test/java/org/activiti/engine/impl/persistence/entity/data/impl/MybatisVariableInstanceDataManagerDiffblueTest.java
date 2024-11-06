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
import static org.mockito.Mockito.mock;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.VariableByExecutionIdMatcher;
import org.junit.Test;

public class MybatisVariableInstanceDataManagerDiffblueTest {
  /**
   * Test
   * {@link MybatisVariableInstanceDataManager#MybatisVariableInstanceDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisVariableInstanceDataManager#MybatisVariableInstanceDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisVariableInstanceDataManager_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    MybatisVariableInstanceDataManager actualMybatisVariableInstanceDataManager = new MybatisVariableInstanceDataManager(
        processEngineConfiguration);

    // Assert
    assertTrue(actualMybatisVariableInstanceDataManager.variableInstanceEntity instanceof VariableByExecutionIdMatcher);
    assertNull(actualMybatisVariableInstanceDataManager.getManagedEntitySubClasses());
    Class<VariableInstanceEntityImpl> expectedManagedEntityClass = VariableInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisVariableInstanceDataManager.getManagedEntityClass());
  }

  /**
   * Test
   * {@link MybatisVariableInstanceDataManager#MybatisVariableInstanceDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisVariableInstanceDataManager#MybatisVariableInstanceDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisVariableInstanceDataManager_whenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link MybatisVariableInstanceDataManager#getManagedEntityClass()}
   */
  @Test
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends VariableInstanceEntity> actualManagedEntityClass = (new MybatisVariableInstanceDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<VariableInstanceEntityImpl> expectedManagedEntityClass = VariableInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }
}
