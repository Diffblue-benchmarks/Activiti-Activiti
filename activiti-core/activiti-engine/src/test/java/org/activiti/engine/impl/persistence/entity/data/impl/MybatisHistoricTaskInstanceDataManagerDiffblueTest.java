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
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.junit.Test;

public class MybatisHistoricTaskInstanceDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MybatisHistoricTaskInstanceDataManager#MybatisHistoricTaskInstanceDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisHistoricTaskInstanceDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends HistoricTaskInstanceEntity> actualManagedEntityClass = (new MybatisHistoricTaskInstanceDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<HistoricTaskInstanceEntityImpl> expectedManagedEntityClass = HistoricTaskInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisHistoricTaskInstanceDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisHistoricTaskInstanceDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    HistoricTaskInstanceEntity actualCreateResult = (new MybatisHistoricTaskInstanceDataManager(
        new JtaProcessEngineConfiguration())).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricTaskInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertEquals(12, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Integer>) persistentState).get("formKey"));
    assertNull(((Map<String, Integer>) persistentState).get("name"));
    assertNull(((Map<String, Integer>) persistentState).get("owner"));
    assertNull(((Map<String, Integer>) persistentState).get("processDefinitionId"));
    assertNull(((Map<String, Integer>) persistentState).get("taskDefinitionKey"));
    assertNull(actualCreateResult.getDurationInMillis());
    assertNull(actualCreateResult.getWorkTimeInMillis());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getAssignee());
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getCategory());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getFormKey());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getOwner());
    assertNull(actualCreateResult.getParentTaskId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskDefinitionKey());
    assertNull(actualCreateResult.getTime());
    assertNull(actualCreateResult.getClaimTime());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getCreateTime());
    assertNull(actualCreateResult.getDueDate());
    assertNull(actualCreateResult.getQueryVariables());
    assertEquals(0, actualCreateResult.getPriority());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
    assertTrue(actualCreateResult.getTaskLocalVariables().isEmpty());
  }

  /**
   * Test {@link MybatisHistoricTaskInstanceDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisHistoricTaskInstanceDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricTaskInstanceEntity actualCreateResult = (new MybatisHistoricTaskInstanceDataManager(
        processEngineConfiguration)).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricTaskInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertEquals(12, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Integer>) persistentState).get("formKey"));
    assertNull(((Map<String, Integer>) persistentState).get("name"));
    assertNull(((Map<String, Integer>) persistentState).get("owner"));
    assertNull(((Map<String, Integer>) persistentState).get("processDefinitionId"));
    assertNull(((Map<String, Integer>) persistentState).get("taskDefinitionKey"));
    assertNull(actualCreateResult.getDurationInMillis());
    assertNull(actualCreateResult.getWorkTimeInMillis());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getAssignee());
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getCategory());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getFormKey());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getOwner());
    assertNull(actualCreateResult.getParentTaskId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskDefinitionKey());
    assertNull(actualCreateResult.getTime());
    assertNull(actualCreateResult.getClaimTime());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getCreateTime());
    assertNull(actualCreateResult.getDueDate());
    assertNull(actualCreateResult.getQueryVariables());
    assertEquals(0, actualCreateResult.getPriority());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
    assertTrue(actualCreateResult.getTaskLocalVariables().isEmpty());
  }

  /**
   * Test
   * {@link MybatisHistoricTaskInstanceDataManager#findHistoricTaskInstancesAndVariablesByQueryCriteria(HistoricTaskInstanceQueryImpl)}.
   * <ul>
   *   <li>Given minus one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisHistoricTaskInstanceDataManager#findHistoricTaskInstancesAndVariablesByQueryCriteria(HistoricTaskInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricTaskInstancesAndVariablesByQueryCriteria_givenMinusOne() {
    // Arrange
    MybatisHistoricTaskInstanceDataManager mybatisHistoricTaskInstanceDataManager = new MybatisHistoricTaskInstanceDataManager(
        new JtaProcessEngineConfiguration());

    HistoricTaskInstanceQueryImpl historicTaskInstanceQuery = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQuery.setFirstResult(-1);
    historicTaskInstanceQuery.setMaxResults(0);
    historicTaskInstanceQuery.limitTaskVariables(null);

    // Act and Assert
    assertTrue(mybatisHistoricTaskInstanceDataManager
        .findHistoricTaskInstancesAndVariablesByQueryCriteria(historicTaskInstanceQuery)
        .isEmpty());
  }

  /**
   * Test
   * {@link MybatisHistoricTaskInstanceDataManager#findHistoricTaskInstancesAndVariablesByQueryCriteria(HistoricTaskInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisHistoricTaskInstanceDataManager#findHistoricTaskInstancesAndVariablesByQueryCriteria(HistoricTaskInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricTaskInstancesAndVariablesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    MybatisHistoricTaskInstanceDataManager mybatisHistoricTaskInstanceDataManager = new MybatisHistoricTaskInstanceDataManager(
        new JtaProcessEngineConfiguration());

    HistoricTaskInstanceQueryImpl historicTaskInstanceQuery = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQuery.setFirstResult(0);
    historicTaskInstanceQuery.setMaxResults(0);
    historicTaskInstanceQuery.limitTaskVariables(null);

    // Act and Assert
    assertTrue(mybatisHistoricTaskInstanceDataManager
        .findHistoricTaskInstancesAndVariablesByQueryCriteria(historicTaskInstanceQuery)
        .isEmpty());
  }
}
