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
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.TasksByExecutionIdMatcher;
import org.junit.Test;

public class MybatisTaskDataManagerDiffblueTest {
  /**
   * Test
   * {@link MybatisTaskDataManager#MybatisTaskDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisTaskDataManager#MybatisTaskDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisTaskDataManager_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    MybatisTaskDataManager actualMybatisTaskDataManager = new MybatisTaskDataManager(processEngineConfiguration);

    // Assert
    assertTrue(actualMybatisTaskDataManager.tasksByExecutionIdMatcher instanceof TasksByExecutionIdMatcher);
    assertNull(actualMybatisTaskDataManager.getManagedEntitySubClasses());
    Class<TaskEntityImpl> expectedManagedEntityClass = TaskEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisTaskDataManager.getManagedEntityClass());
  }

  /**
   * Test
   * {@link MybatisTaskDataManager#MybatisTaskDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisTaskDataManager#MybatisTaskDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisTaskDataManager_whenJtaProcessEngineConfiguration() {
    // Arrange and Act
    MybatisTaskDataManager actualMybatisTaskDataManager = new MybatisTaskDataManager(
        new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(actualMybatisTaskDataManager.tasksByExecutionIdMatcher instanceof TasksByExecutionIdMatcher);
    assertNull(actualMybatisTaskDataManager.getManagedEntitySubClasses());
    Class<TaskEntityImpl> expectedManagedEntityClass = TaskEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisTaskDataManager.getManagedEntityClass());
  }

  /**
   * Test {@link MybatisTaskDataManager#getManagedEntityClass()}.
   * <p>
   * Method under test: {@link MybatisTaskDataManager#getManagedEntityClass()}
   */
  @Test
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends TaskEntity> actualManagedEntityClass = (new MybatisTaskDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<TaskEntityImpl> expectedManagedEntityClass = TaskEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test
   * {@link MybatisTaskDataManager#findTasksAndVariablesByQueryCriteria(TaskQueryImpl)}.
   * <ul>
   *   <li>Given minus one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisTaskDataManager#findTasksAndVariablesByQueryCriteria(TaskQueryImpl)}
   */
  @Test
  public void testFindTasksAndVariablesByQueryCriteria_givenMinusOne() {
    // Arrange
    MybatisTaskDataManager mybatisTaskDataManager = new MybatisTaskDataManager(new JtaProcessEngineConfiguration());

    TaskQueryImpl taskQuery = new TaskQueryImpl();
    taskQuery.setFirstResult(-1);
    taskQuery.setMaxResults(0);
    taskQuery.limitTaskVariables(null);

    // Act and Assert
    assertTrue(mybatisTaskDataManager.findTasksAndVariablesByQueryCriteria(taskQuery).isEmpty());
  }

  /**
   * Test
   * {@link MybatisTaskDataManager#findTasksAndVariablesByQueryCriteria(TaskQueryImpl)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisTaskDataManager#findTasksAndVariablesByQueryCriteria(TaskQueryImpl)}
   */
  @Test
  public void testFindTasksAndVariablesByQueryCriteria_givenNull_thenReturnEmpty() {
    // Arrange
    MybatisTaskDataManager mybatisTaskDataManager = new MybatisTaskDataManager(new JtaProcessEngineConfiguration());

    TaskQueryImpl taskQuery = new TaskQueryImpl();
    taskQuery.setFirstResult(0);
    taskQuery.setMaxResults(0);
    taskQuery.limitTaskVariables(null);

    // Act and Assert
    assertTrue(mybatisTaskDataManager.findTasksAndVariablesByQueryCriteria(taskQuery).isEmpty());
  }
}
