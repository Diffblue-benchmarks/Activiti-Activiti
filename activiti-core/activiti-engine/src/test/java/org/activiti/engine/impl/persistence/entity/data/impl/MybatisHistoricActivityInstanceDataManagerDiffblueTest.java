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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Map;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.UnfinishedHistoricActivityInstanceMatcher;
import org.junit.Test;

public class MybatisHistoricActivityInstanceDataManagerDiffblueTest {
  /**
   * Test
   * {@link MybatisHistoricActivityInstanceDataManager#MybatisHistoricActivityInstanceDataManager(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricActivityInstanceDataManager#MybatisHistoricActivityInstanceDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisHistoricActivityInstanceDataManager() {
    // Arrange and Act
    MybatisHistoricActivityInstanceDataManager actualMybatisHistoricActivityInstanceDataManager = new MybatisHistoricActivityInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(
        actualMybatisHistoricActivityInstanceDataManager.unfinishedHistoricActivityInstanceMatcher instanceof UnfinishedHistoricActivityInstanceMatcher);
    assertNull(actualMybatisHistoricActivityInstanceDataManager.getManagedEntitySubClasses());
    Class<HistoricActivityInstanceEntityImpl> expectedManagedEntityClass = HistoricActivityInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisHistoricActivityInstanceDataManager.getManagedEntityClass());
  }

  /**
   * Test
   * {@link MybatisHistoricActivityInstanceDataManager#MybatisHistoricActivityInstanceDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisHistoricActivityInstanceDataManager#MybatisHistoricActivityInstanceDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisHistoricActivityInstanceDataManager_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    MybatisHistoricActivityInstanceDataManager actualMybatisHistoricActivityInstanceDataManager = new MybatisHistoricActivityInstanceDataManager(
        processEngineConfiguration);

    // Assert
    assertTrue(
        actualMybatisHistoricActivityInstanceDataManager.unfinishedHistoricActivityInstanceMatcher instanceof UnfinishedHistoricActivityInstanceMatcher);
    assertNull(actualMybatisHistoricActivityInstanceDataManager.getManagedEntitySubClasses());
    Class<HistoricActivityInstanceEntityImpl> expectedManagedEntityClass = HistoricActivityInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisHistoricActivityInstanceDataManager.getManagedEntityClass());
  }

  /**
   * Test
   * {@link MybatisHistoricActivityInstanceDataManager#getManagedEntityClass()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricActivityInstanceDataManager#getManagedEntityClass()}
   */
  @Test
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends HistoricActivityInstanceEntity> actualManagedEntityClass = (new MybatisHistoricActivityInstanceDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<HistoricActivityInstanceEntityImpl> expectedManagedEntityClass = HistoricActivityInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisHistoricActivityInstanceDataManager#create()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricActivityInstanceDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    HistoricActivityInstanceEntity actualCreateResult = (new MybatisHistoricActivityInstanceDataManager(
        new JtaProcessEngineConfiguration())).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricActivityInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getDurationInMillis());
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("assignee"));
    assertNull(((Map<String, Object>) persistentState).get("deleteReason"));
    assertNull(((Map<String, Object>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) persistentState).get("endTime"));
    assertNull(((Map<String, Object>) persistentState).get("executionId"));
    assertNull(actualCreateResult.getActivityId());
    assertNull(actualCreateResult.getActivityName());
    assertNull(actualCreateResult.getActivityType());
    assertNull(actualCreateResult.getAssignee());
    assertNull(actualCreateResult.getCalledProcessInstanceId());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getTime());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }

  /**
   * Test {@link MybatisHistoricActivityInstanceDataManager#create()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricActivityInstanceDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricActivityInstanceEntity actualCreateResult = (new MybatisHistoricActivityInstanceDataManager(
        processEngineConfiguration)).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricActivityInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getDurationInMillis());
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("assignee"));
    assertNull(((Map<String, Object>) persistentState).get("deleteReason"));
    assertNull(((Map<String, Object>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) persistentState).get("endTime"));
    assertNull(((Map<String, Object>) persistentState).get("executionId"));
    assertNull(actualCreateResult.getActivityId());
    assertNull(actualCreateResult.getActivityName());
    assertNull(actualCreateResult.getActivityType());
    assertNull(actualCreateResult.getAssignee());
    assertNull(actualCreateResult.getCalledProcessInstanceId());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getTime());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }
}
