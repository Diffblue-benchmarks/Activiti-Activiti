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
import org.activiti.engine.impl.HistoricProcessInstanceQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.junit.Test;

public class MybatisHistoricProcessInstanceDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MybatisHistoricProcessInstanceDataManager#MybatisHistoricProcessInstanceDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisHistoricProcessInstanceDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends HistoricProcessInstanceEntity> actualManagedEntityClass = (new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<HistoricProcessInstanceEntityImpl> expectedManagedEntityClass = HistoricProcessInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisHistoricProcessInstanceDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisHistoricProcessInstanceDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    HistoricProcessInstanceEntity actualCreateResult = (new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration())).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricProcessInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getProcessDefinitionVersion());
    assertNull(actualCreateResult.getDurationInMillis());
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("businessKey"));
    assertNull(((Map<String, Object>) persistentState).get("deploymentId"));
    assertNull(((Map<String, Object>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) persistentState).get("endStateName"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(((Map<String, Object>) persistentState).get("processDefinitionId"));
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getEndActivityId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getStartActivityId());
    assertNull(actualCreateResult.getStartUserId());
    assertNull(actualCreateResult.getSuperProcessInstanceId());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getProcessDefinitionKey());
    assertNull(actualCreateResult.getProcessDefinitionName());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedDescription());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getQueryVariables());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link MybatisHistoricProcessInstanceDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisHistoricProcessInstanceDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricProcessInstanceEntity actualCreateResult = (new MybatisHistoricProcessInstanceDataManager(
        processEngineConfiguration)).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricProcessInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getProcessDefinitionVersion());
    assertNull(actualCreateResult.getDurationInMillis());
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("businessKey"));
    assertNull(((Map<String, Object>) persistentState).get("deploymentId"));
    assertNull(((Map<String, Object>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) persistentState).get("endStateName"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(((Map<String, Object>) persistentState).get("processDefinitionId"));
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getEndActivityId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getStartActivityId());
    assertNull(actualCreateResult.getStartUserId());
    assertNull(actualCreateResult.getSuperProcessInstanceId());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getProcessDefinitionKey());
    assertNull(actualCreateResult.getProcessDefinitionName());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedDescription());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getQueryVariables());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
  }

  /**
   * Test
   * {@link MybatisHistoricProcessInstanceDataManager#create(ExecutionEntity)}
   * with {@code ExecutionEntity}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricProcessInstanceDataManager#create(ExecutionEntity)}
   */
  @Test
  public void testCreateWithExecutionEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisHistoricProcessInstanceDataManager mybatisHistoricProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        processEngineConfiguration);

    // Act
    HistoricProcessInstanceEntity actualCreateResult = mybatisHistoricProcessInstanceDataManager
        .create(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricProcessInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getProcessDefinitionVersion());
    assertNull(actualCreateResult.getDurationInMillis());
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("businessKey"));
    assertNull(((Map<String, Object>) persistentState).get("deploymentId"));
    assertNull(((Map<String, Object>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) persistentState).get("endStateName"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(((Map<String, Object>) persistentState).get("processDefinitionId"));
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getEndActivityId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getStartActivityId());
    assertNull(actualCreateResult.getStartUserId());
    assertNull(actualCreateResult.getSuperProcessInstanceId());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getProcessDefinitionKey());
    assertNull(actualCreateResult.getProcessDefinitionName());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedDescription());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getQueryVariables());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
  }

  /**
   * Test
   * {@link MybatisHistoricProcessInstanceDataManager#create(ExecutionEntity)}
   * with {@code ExecutionEntity}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisHistoricProcessInstanceDataManager#create(ExecutionEntity)}
   */
  @Test
  public void testCreateWithExecutionEntity_givenNull() {
    // Arrange
    MybatisHistoricProcessInstanceDataManager mybatisHistoricProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration());
    ExecutionEntityImpl processInstanceExecutionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    processInstanceExecutionEntity.setTenantId(null);

    // Act
    HistoricProcessInstanceEntity actualCreateResult = mybatisHistoricProcessInstanceDataManager
        .create(processInstanceExecutionEntity);

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricProcessInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getProcessDefinitionVersion());
    assertNull(actualCreateResult.getDurationInMillis());
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("businessKey"));
    assertNull(((Map<String, Object>) persistentState).get("deploymentId"));
    assertNull(((Map<String, Object>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) persistentState).get("endStateName"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(((Map<String, Object>) persistentState).get("processDefinitionId"));
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getEndActivityId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getStartActivityId());
    assertNull(actualCreateResult.getStartUserId());
    assertNull(actualCreateResult.getSuperProcessInstanceId());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getProcessDefinitionKey());
    assertNull(actualCreateResult.getProcessDefinitionName());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedDescription());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getQueryVariables());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
  }

  /**
   * Test
   * {@link MybatisHistoricProcessInstanceDataManager#create(ExecutionEntity)}
   * with {@code ExecutionEntity}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisHistoricProcessInstanceDataManager#create(ExecutionEntity)}
   */
  @Test
  public void testCreateWithExecutionEntity_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    MybatisHistoricProcessInstanceDataManager mybatisHistoricProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act
    HistoricProcessInstanceEntity actualCreateResult = mybatisHistoricProcessInstanceDataManager
        .create(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricProcessInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getProcessDefinitionVersion());
    assertNull(actualCreateResult.getDurationInMillis());
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("businessKey"));
    assertNull(((Map<String, Object>) persistentState).get("deploymentId"));
    assertNull(((Map<String, Object>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) persistentState).get("endStateName"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(((Map<String, Object>) persistentState).get("processDefinitionId"));
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getEndActivityId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getStartActivityId());
    assertNull(actualCreateResult.getStartUserId());
    assertNull(actualCreateResult.getSuperProcessInstanceId());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getProcessDefinitionKey());
    assertNull(actualCreateResult.getProcessDefinitionName());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedDescription());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getQueryVariables());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
  }

  /**
   * Test
   * {@link MybatisHistoricProcessInstanceDataManager#findHistoricProcessInstancesAndVariablesByQueryCriteria(HistoricProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Given minus one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisHistoricProcessInstanceDataManager#findHistoricProcessInstancesAndVariablesByQueryCriteria(HistoricProcessInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricProcessInstancesAndVariablesByQueryCriteria_givenMinusOne() {
    // Arrange
    MybatisHistoricProcessInstanceDataManager mybatisHistoricProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQuery = new HistoricProcessInstanceQueryImpl();
    historicProcessInstanceQuery.setFirstResult(-1);
    historicProcessInstanceQuery.setMaxResults(0);
    historicProcessInstanceQuery.limitProcessInstanceVariables(null);

    // Act and Assert
    assertTrue(mybatisHistoricProcessInstanceDataManager
        .findHistoricProcessInstancesAndVariablesByQueryCriteria(historicProcessInstanceQuery)
        .isEmpty());
  }

  /**
   * Test
   * {@link MybatisHistoricProcessInstanceDataManager#findHistoricProcessInstancesAndVariablesByQueryCriteria(HistoricProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisHistoricProcessInstanceDataManager#findHistoricProcessInstancesAndVariablesByQueryCriteria(HistoricProcessInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricProcessInstancesAndVariablesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    MybatisHistoricProcessInstanceDataManager mybatisHistoricProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQuery = new HistoricProcessInstanceQueryImpl();
    historicProcessInstanceQuery.setFirstResult(0);
    historicProcessInstanceQuery.setMaxResults(0);
    historicProcessInstanceQuery.limitProcessInstanceVariables(null);

    // Act and Assert
    assertTrue(mybatisHistoricProcessInstanceDataManager
        .findHistoricProcessInstancesAndVariablesByQueryCriteria(historicProcessInstanceQuery)
        .isEmpty());
  }
}
