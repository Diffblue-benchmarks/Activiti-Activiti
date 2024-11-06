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
import static org.mockito.Mockito.mock;
import org.activiti.core.el.CustomFunctionProvider;
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

public class MybatisExecutionDataManagerDiffblueTest {
  /**
   * Test
   * {@link MybatisExecutionDataManager#MybatisExecutionDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisExecutionDataManager#MybatisExecutionDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisExecutionDataManager_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
    PerformanceSettings performanceSettings = actualMybatisExecutionDataManager.performanceSettings;
    assertFalse(performanceSettings.isEnableEagerExecutionTreeFetching());
    assertFalse(performanceSettings.isEnableExecutionRelationshipCounts());
    assertTrue(performanceSettings.isEnableLocalization());
    assertTrue(performanceSettings.isValidateExecutionRelationshipCountConfigOnBoot());
    Class<ExecutionEntityImpl> expectedManagedEntityClass = ExecutionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisExecutionDataManager.getManagedEntityClass());
    PerformanceSettings expectedPerformanceSettings = actualMybatisExecutionDataManager.performanceSettings;
    assertSame(expectedPerformanceSettings, processEngineConfiguration.getPerformanceSettings());
  }

  /**
   * Test
   * {@link MybatisExecutionDataManager#MybatisExecutionDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisExecutionDataManager#MybatisExecutionDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisExecutionDataManager_whenJtaProcessEngineConfiguration() {
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
    PerformanceSettings performanceSettings = actualMybatisExecutionDataManager.performanceSettings;
    assertFalse(performanceSettings.isEnableEagerExecutionTreeFetching());
    assertFalse(performanceSettings.isEnableExecutionRelationshipCounts());
    assertTrue(performanceSettings.isEnableLocalization());
    assertTrue(performanceSettings.isValidateExecutionRelationshipCountConfigOnBoot());
    Class<ExecutionEntityImpl> expectedManagedEntityClass = ExecutionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisExecutionDataManager.getManagedEntityClass());
    PerformanceSettings expectedPerformanceSettings = actualMybatisExecutionDataManager.performanceSettings;
    assertSame(expectedPerformanceSettings, processEngineConfiguration.getPerformanceSettings());
  }

  /**
   * Test {@link MybatisExecutionDataManager#getManagedEntityClass()}.
   * <p>
   * Method under test:
   * {@link MybatisExecutionDataManager#getManagedEntityClass()}
   */
  @Test
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends ExecutionEntity> actualManagedEntityClass = (new MybatisExecutionDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<ExecutionEntityImpl> expectedManagedEntityClass = ExecutionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test
   * {@link MybatisExecutionDataManager#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Given minus one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisExecutionDataManager#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
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
   * Test
   * {@link MybatisExecutionDataManager#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisExecutionDataManager#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
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
