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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.SuspendedJobQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.SuspendedJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisSuspendedJobDataManager;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SuspendedJobEntityManagerImplDiffblueTest {
  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Mock
  private SuspendedJobDataManager suspendedJobDataManager;

  @InjectMocks
  private SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl;

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  public void testFindJobsByExecutionId() {
    // Arrange
    ArrayList<SuspendedJobEntity> suspendedJobEntityList = new ArrayList<>();
    when(suspendedJobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(suspendedJobEntityList);

    // Act
    List<SuspendedJobEntity> actualFindJobsByExecutionIdResult = suspendedJobEntityManagerImpl
        .findJobsByExecutionId("42");

    // Assert
    verify(suspendedJobDataManager).findJobsByExecutionId(eq("42"));
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
    assertSame(suspendedJobEntityList, actualFindJobsByExecutionIdResult);
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#findJobsByProcessInstanceId(String)}
   */
  @Test
  public void testFindJobsByProcessInstanceId() {
    // Arrange
    ArrayList<SuspendedJobEntity> suspendedJobEntityList = new ArrayList<>();
    when(suspendedJobDataManager.findJobsByProcessInstanceId(Mockito.<String>any())).thenReturn(suspendedJobEntityList);

    // Act
    List<SuspendedJobEntity> actualFindJobsByProcessInstanceIdResult = suspendedJobEntityManagerImpl
        .findJobsByProcessInstanceId("42");

    // Assert
    verify(suspendedJobDataManager).findJobsByProcessInstanceId(eq("42"));
    assertTrue(actualFindJobsByProcessInstanceIdResult.isEmpty());
    assertSame(suspendedJobEntityList, actualFindJobsByProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#findJobsByQueryCriteria(SuspendedJobQueryImpl, Page)}
   */
  @Test
  public void testFindJobsByQueryCriteria() {
    // Arrange
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    ArrayList<Job> jobList = new ArrayList<>();
    when(jobDataManager.findJobsByQueryCriteria(Mockito.<SuspendedJobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(jobList);
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);
    SuspendedJobQueryImpl jobQuery = new SuspendedJobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult = suspendedJobEntityManagerImpl.findJobsByQueryCriteria(jobQuery,
        new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsByQueryCriteria(isA(SuspendedJobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
    assertSame(jobList, actualFindJobsByQueryCriteriaResult);
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#findJobCountByQueryCriteria(SuspendedJobQueryImpl)}
   */
  @Test
  public void testFindJobCountByQueryCriteria() {
    // Arrange
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    when(jobDataManager.findJobCountByQueryCriteria(Mockito.<SuspendedJobQueryImpl>any())).thenReturn(3L);
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    long actualFindJobCountByQueryCriteriaResult = suspendedJobEntityManagerImpl
        .findJobCountByQueryCriteria(new SuspendedJobQueryImpl());

    // Assert
    verify(jobDataManager).findJobCountByQueryCriteria(isA(SuspendedJobQueryImpl.class));
    assertEquals(3L, actualFindJobCountByQueryCriteriaResult);
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}
   */
  @Test
  public void testUpdateJobTenantIdForDeployment() {
    // Arrange
    doNothing().when(suspendedJobDataManager)
        .updateJobTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act
    suspendedJobEntityManagerImpl.updateJobTenantIdForDeployment("42", "42");

    // Assert
    verify(suspendedJobDataManager).updateJobTenantIdForDeployment(eq("42"), eq("42"));
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  public void testInsert() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  public void testInsert2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  public void testInsert3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  public void testInsert4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  public void testInsert5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(false);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  public void testInsert6() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  public void testInsert7() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  public void testInsert8() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  public void testInsert9() {
    // Arrange
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getTenantId()).thenReturn("42");
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntity);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(executionEntity, atLeast(1)).getTenantId();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert10() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert11() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert12() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert13() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(mock(ActivitiEventDispatcher.class));
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert14() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert15() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(false);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert16() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert17() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert18() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  public void testInsert19() {
    // Arrange
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getTenantId()).thenReturn("42");
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntity);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(executionEntity, atLeast(1)).getTenantId();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#deleteExceptionByteArrayRef(SuspendedJobEntity)}
   */
  @Test
  public void testDeleteExceptionByteArrayRef() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    SuspendedJobEntityImpl jobEntity = mock(SuspendedJobEntityImpl.class);
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    suspendedJobEntityManagerImpl.deleteExceptionByteArrayRef(jobEntity);

    // Assert that nothing has changed
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#createSuspendedJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateSuspendedJob() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    SuspendedJobEntity actualCreateSuspendedJobResult = suspendedJobEntityManagerImpl
        .createSuspendedJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateSuspendedJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateSuspendedJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("", actualCreateSuspendedJobResult.getTenantId());
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(actualCreateSuspendedJobResult.getExceptionStacktrace());
    assertNull(actualCreateSuspendedJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateSuspendedJobResult.getJobHandlerType());
    assertNull(actualCreateSuspendedJobResult.getJobType());
    assertNull(actualCreateSuspendedJobResult.getRepeat());
    assertNull(actualCreateSuspendedJobResult.getId());
    assertNull(actualCreateSuspendedJobResult.getExceptionMessage());
    assertNull(actualCreateSuspendedJobResult.getExecutionId());
    assertNull(actualCreateSuspendedJobResult.getProcessDefinitionId());
    assertNull(actualCreateSuspendedJobResult.getProcessInstanceId());
    assertNull(actualCreateSuspendedJobResult.getEndDate());
    assertNull(actualCreateSuspendedJobResult.getDuedate());
    assertNull(actualCreateSuspendedJobResult.getExceptionByteArrayRef());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateSuspendedJobResult.getMaxIterations());
    assertEquals(0, actualCreateSuspendedJobResult.getRetries());
    assertEquals(1, actualCreateSuspendedJobResult.getRevision());
    assertEquals(2, actualCreateSuspendedJobResult.getRevisionNext());
    assertFalse(actualCreateSuspendedJobResult.isDeleted());
    assertFalse(actualCreateSuspendedJobResult.isInserted());
    assertFalse(actualCreateSuspendedJobResult.isUpdated());
    assertTrue(actualCreateSuspendedJobResult.isExclusive());
  }

  /**
   * Method under test:
   * {@link SuspendedJobEntityManagerImpl#createSuspendedJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateSuspendedJob2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    SuspendedJobEntity actualCreateSuspendedJobResult = suspendedJobEntityManagerImpl
        .createSuspendedJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateSuspendedJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateSuspendedJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("", actualCreateSuspendedJobResult.getTenantId());
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(actualCreateSuspendedJobResult.getExceptionStacktrace());
    assertNull(actualCreateSuspendedJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateSuspendedJobResult.getJobHandlerType());
    assertNull(actualCreateSuspendedJobResult.getJobType());
    assertNull(actualCreateSuspendedJobResult.getRepeat());
    assertNull(actualCreateSuspendedJobResult.getId());
    assertNull(actualCreateSuspendedJobResult.getExceptionMessage());
    assertNull(actualCreateSuspendedJobResult.getExecutionId());
    assertNull(actualCreateSuspendedJobResult.getProcessDefinitionId());
    assertNull(actualCreateSuspendedJobResult.getProcessInstanceId());
    assertNull(actualCreateSuspendedJobResult.getEndDate());
    assertNull(actualCreateSuspendedJobResult.getDuedate());
    assertNull(actualCreateSuspendedJobResult.getExceptionByteArrayRef());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateSuspendedJobResult.getMaxIterations());
    assertEquals(0, actualCreateSuspendedJobResult.getRetries());
    assertEquals(1, actualCreateSuspendedJobResult.getRevision());
    assertEquals(2, actualCreateSuspendedJobResult.getRevisionNext());
    assertFalse(actualCreateSuspendedJobResult.isDeleted());
    assertFalse(actualCreateSuspendedJobResult.isInserted());
    assertFalse(actualCreateSuspendedJobResult.isUpdated());
    assertTrue(actualCreateSuspendedJobResult.isExclusive());
  }

  /**
   * Method under test: {@link SuspendedJobEntityManagerImpl#getDataManager()}
   */
  @Test
  public void testGetDataManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(suspendedJobEntityManagerImpl.jobDataManager, suspendedJobEntityManagerImpl.getDataManager());
  }

  /**
   * Method under test: {@link SuspendedJobEntityManagerImpl#getDataManager()}
   */
  @Test
  public void testGetDataManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(suspendedJobEntityManagerImpl.jobDataManager, suspendedJobEntityManagerImpl.getDataManager());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link SuspendedJobEntityManagerImpl#SuspendedJobEntityManagerImpl(ProcessEngineConfigurationImpl, SuspendedJobDataManager)}
   *   <li>
   * {@link SuspendedJobEntityManagerImpl#setJobDataManager(SuspendedJobDataManager)}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    SuspendedJobEntityManagerImpl actualSuspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));
    MybatisSuspendedJobDataManager jobDataManager = new MybatisSuspendedJobDataManager(
        new JtaProcessEngineConfiguration());
    actualSuspendedJobEntityManagerImpl.setJobDataManager(jobDataManager);

    // Assert
    assertSame(jobDataManager, actualSuspendedJobEntityManagerImpl.getDataManager());
  }
}
