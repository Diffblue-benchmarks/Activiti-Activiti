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
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.TasksByExecutionIdMatcher;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisTaskDataManagerDiffblueTest {
  /**
   * Test {@link MybatisTaskDataManager#MybatisTaskDataManager(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test: {@link MybatisTaskDataManager#MybatisTaskDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MybatisTaskDataManager.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewMybatisTaskDataManager() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class MybatisTaskDataManager.getManagedEntityClass()"})
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends TaskEntity> actualManagedEntityClass = (new MybatisTaskDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<TaskEntityImpl> expectedManagedEntityClass = TaskEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }
}
