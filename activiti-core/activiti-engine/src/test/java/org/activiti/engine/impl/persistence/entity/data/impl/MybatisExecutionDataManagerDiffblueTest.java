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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionByProcessInstanceMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByParentExecutionIdAndActivityIdEntityMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByParentExecutionIdEntityMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByProcessInstanceIdEntityMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByRootProcessInstanceMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsWithSameRootProcessInstanceIdMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.InactiveExecutionsByProcInstMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.InactiveExecutionsInActivityAndProcInstMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.InactiveExecutionsInActivityMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ProcessInstancesByProcessDefinitionMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.SubProcessInstanceExecutionBySuperExecutionIdMatcher;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisExecutionDataManagerDiffblueTest {
  /**
   * Test {@link MybatisExecutionDataManager#MybatisExecutionDataManager(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test: {@link MybatisExecutionDataManager#MybatisExecutionDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MybatisExecutionDataManager.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewMybatisExecutionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    MybatisExecutionDataManager actualMybatisExecutionDataManager = new MybatisExecutionDataManager(
        processEngineConfiguration);

    // Assert
    assertTrue(
        actualMybatisExecutionDataManager.executionByProcessInstanceMatcher instanceof ExecutionByProcessInstanceMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsByParentExecutionIdAndActivityIdEntityMatcher instanceof ExecutionsByParentExecutionIdAndActivityIdEntityMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsByParentIdMatcher instanceof ExecutionsByParentExecutionIdEntityMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsByProcessInstanceIdMatcher instanceof ExecutionsByProcessInstanceIdEntityMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsByRootProcessInstanceMatcher instanceof ExecutionsByRootProcessInstanceMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsWithSameRootProcessInstanceIdMatcher instanceof ExecutionsWithSameRootProcessInstanceIdMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.inactiveExecutionsByProcInstMatcher instanceof InactiveExecutionsByProcInstMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.inactiveExecutionsInActivityAndProcInstMatcher instanceof InactiveExecutionsInActivityAndProcInstMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.inactiveExecutionsInActivityMatcher instanceof InactiveExecutionsInActivityMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.processInstancesByProcessDefinitionMatcher instanceof ProcessInstancesByProcessDefinitionMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.subProcessInstanceBySuperExecutionIdMatcher instanceof SubProcessInstanceExecutionBySuperExecutionIdMatcher);
    assertNull(actualMybatisExecutionDataManager.getManagedEntitySubClasses());
    Class<ExecutionEntityImpl> expectedManagedEntityClass = ExecutionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisExecutionDataManager.getManagedEntityClass());
    PerformanceSettings expectedPerformanceSettings = actualMybatisExecutionDataManager.performanceSettings;
    assertSame(expectedPerformanceSettings, processEngineConfiguration.getPerformanceSettings());
  }

  /**
   * Test {@link MybatisExecutionDataManager#getManagedEntityClass()}.
   * <p>
   * Method under test: {@link MybatisExecutionDataManager#getManagedEntityClass()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class MybatisExecutionDataManager.getManagedEntityClass()"})
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends ExecutionEntity> actualManagedEntityClass = (new MybatisExecutionDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<ExecutionEntityImpl> expectedManagedEntityClass = ExecutionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisExecutionDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisExecutionDataManager#create()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity MybatisExecutionDataManager.create()"})
  public void testCreate() {
    // Arrange and Act
    ExecutionEntity actualCreateResult = (new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()))
        .create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof ExecutionEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(((ExecutionEntityImpl) actualCreateResult).getCachedElContext());
    assertNull(actualCreateResult.getAppVersion());
    assertNull(actualCreateResult.getProcessDefinitionVersion());
    assertNull(actualCreateResult.getCurrentActivityId());
    assertNull(actualCreateResult.getEventName());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getParentId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getRootProcessInstanceId());
    assertNull(actualCreateResult.getSuperExecutionId());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getStartUserId());
    assertNull(((ExecutionEntityImpl) actualCreateResult).getActivityName());
    assertNull(actualCreateResult.getActivityId());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getParentProcessInstanceId());
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getLocalizedDescription());
    assertNull(actualCreateResult.getLocalizedName());
    assertNull(actualCreateResult.getProcessDefinitionKey());
    assertNull(actualCreateResult.getProcessDefinitionName());
    assertNull(actualCreateResult.getLockTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(((ExecutionEntityImpl) actualCreateResult).getQueryVariables());
    assertNull(actualCreateResult.getCurrentActivitiListener());
    assertNull(actualCreateResult.getCurrentFlowElement());
    assertNull(actualCreateResult.getEngineServices());
    assertNull(actualCreateResult.getParent());
    assertNull(actualCreateResult.getProcessInstance());
    assertNull(actualCreateResult.getSuperExecution());
    assertEquals(0, ((ExecutionEntityImpl) actualCreateResult).getDeadLetterJobCount());
    assertEquals(0, ((ExecutionEntityImpl) actualCreateResult).getEventSubscriptionCount());
    assertEquals(0, ((ExecutionEntityImpl) actualCreateResult).getIdentityLinkCount());
    assertEquals(0, ((ExecutionEntityImpl) actualCreateResult).getJobCount());
    assertEquals(0, ((ExecutionEntityImpl) actualCreateResult).getSuspendedJobCount());
    assertEquals(0, ((ExecutionEntityImpl) actualCreateResult).getTaskCount());
    assertEquals(0, ((ExecutionEntityImpl) actualCreateResult).getTimerJobCount());
    assertEquals(0, ((ExecutionEntityImpl) actualCreateResult).getVariableCount());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(1, actualCreateResult.getSuspensionState());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertEquals(23, ((Map<Object, Object>) persistentState).size());
    assertFalse(actualCreateResult.isConcurrent());
    assertFalse(actualCreateResult.isEnded());
    assertFalse(actualCreateResult.isRootExecution());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isEventScope());
    assertFalse(actualCreateResult.isMultiInstanceRoot());
    assertFalse(((ExecutionEntityImpl) actualCreateResult).isCountEnabled());
    assertFalse(actualCreateResult.isSuspended());
    assertTrue(actualCreateResult.getEventSubscriptions().isEmpty());
    assertTrue(actualCreateResult.getExecutions().isEmpty());
    assertTrue(actualCreateResult.getIdentityLinks().isEmpty());
    assertTrue(actualCreateResult.getJobs().isEmpty());
    assertTrue(actualCreateResult.getTasks().isEmpty());
    assertTrue(actualCreateResult.getTimerJobs().isEmpty());
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("superExecution"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
    assertTrue(actualCreateResult.getTransientVariables().isEmpty());
    assertTrue(actualCreateResult.getTransientVariablesLocal().isEmpty());
    assertTrue(actualCreateResult.getVariableInstances().isEmpty());
    assertTrue(actualCreateResult.getVariableInstancesLocal().isEmpty());
    assertTrue(actualCreateResult.getVariables().isEmpty());
    assertTrue(actualCreateResult.getVariablesLocal().isEmpty());
    assertTrue(((ExecutionEntityImpl) actualCreateResult).getUsedVariablesCache().isEmpty());
    assertTrue(((ExecutionEntityImpl) actualCreateResult).getVariableInstanceEntities().isEmpty());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
    assertTrue(actualCreateResult.getVariableNames().isEmpty());
    assertTrue(actualCreateResult.getVariableNamesLocal().isEmpty());
    assertTrue(actualCreateResult.isActive());
    assertTrue(actualCreateResult.isProcessInstanceType());
    assertTrue(actualCreateResult.isScope());
  }

  /**
   * Test {@link MybatisExecutionDataManager#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Given minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MybatisExecutionDataManager#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "java.util.List MybatisExecutionDataManager.findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)"})
  public void testFindProcessInstanceAndVariablesByQueryCriteria_givenMinusOne() {
    // Arrange
    MybatisExecutionDataManager mybatisExecutionDataManager = new MybatisExecutionDataManager(
        new JtaProcessEngineConfiguration());

    ProcessInstanceQueryImpl executionQuery = new ProcessInstanceQueryImpl();
    executionQuery.setFirstResult(-1);
    executionQuery.setMaxResults(0);
    executionQuery.limitProcessInstanceVariables(null);

    // Act and Assert
    assertTrue(mybatisExecutionDataManager.findProcessInstanceAndVariablesByQueryCriteria(executionQuery).isEmpty());
  }

  /**
   * Test {@link MybatisExecutionDataManager#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MybatisExecutionDataManager#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "java.util.List MybatisExecutionDataManager.findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)"})
  public void testFindProcessInstanceAndVariablesByQueryCriteria_givenNull_thenReturnEmpty() {
    // Arrange
    MybatisExecutionDataManager mybatisExecutionDataManager = new MybatisExecutionDataManager(
        new JtaProcessEngineConfiguration());

    ProcessInstanceQueryImpl executionQuery = new ProcessInstanceQueryImpl();
    executionQuery.setFirstResult(0);
    executionQuery.setMaxResults(0);
    executionQuery.limitProcessInstanceVariables(null);

    // Act and Assert
    assertTrue(mybatisExecutionDataManager.findProcessInstanceAndVariablesByQueryCriteria(executionQuery).isEmpty());
  }
}
