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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.JobQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.JobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisJobDataManager;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JobEntityManagerImplDiffblueTest {
  @Mock
  private JobDataManager jobDataManager;

  @InjectMocks
  private JobEntityManagerImpl jobEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link JobEntityManagerImpl#JobEntityManagerImpl(ProcessEngineConfigurationImpl, JobDataManager)}
   *   <li>{@link JobEntityManagerImpl#setJobDataManager(JobDataManager)}
   *   <li>{@link JobEntityManagerImpl#getDataManager()}
   *   <li>{@link JobEntityManagerImpl#getJobDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    JobEntityManagerImpl actualJobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration,
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    MybatisJobDataManager jobDataManager = new MybatisJobDataManager(new JtaProcessEngineConfiguration());
    actualJobEntityManagerImpl.setJobDataManager(jobDataManager);
    DataManager<JobEntity> actualDataManager = actualJobEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(jobDataManager, actualDataManager);
    assertSame(jobDataManager, actualJobEntityManagerImpl.getJobDataManager());
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl timerJobEntity = new JobEntityImpl();

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity2() {
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl timerJobEntity = new JobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity3() {
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl timerJobEntity = new JobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

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
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity4() {
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl timerJobEntity = new JobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

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
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl timerJobEntity = new JobEntityImpl();

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity_givenExecutionDataManagerFindByIdReturnNull_thenReturnFalse() {
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
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration,
        mock(JobDataManager.class));

    JobEntityImpl timerJobEntity = new JobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById(eq("42"));
    assertEquals("", timerJobEntity.getTenantId());
    assertFalse(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link DelegateExecution#getTenantId()}
   * return {@code 42}.</li>
   *   <li>Then calls {@link DelegateExecution#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity_givenExecutionEntityGetTenantIdReturn42_thenCallsGetTenantId() {
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
    when(executionEntity.getJobs()).thenReturn(new ArrayList<>());
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntity);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl timerJobEntity = new JobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

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
    verify(executionEntity).getJobs();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("42", timerJobEntity.getTenantId());
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity_givenExecutionEntityImplGetTenantIdReturn42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl timerJobEntity = new JobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

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
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("42", timerJobEntity.getTenantId());
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl timerJobEntity = new JobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

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
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   * <ul>
   *   <li>When {@link JobEntityImpl} (default constructor).</li>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  public void testInsertJobEntity_whenJobEntityImpl_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl timerJobEntity = new JobEntityImpl();

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean2() {
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean3() {
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

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
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean4() {
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

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
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean_givenExecutionDataManagerFindByIdReturnNull() {
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
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration,
        mock(JobDataManager.class));

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById(eq("42"));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean_givenExecutionEntityImplGetTenantIdReturn42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

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
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

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
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link DelegateExecution#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean_thenCallsGetTenantId() {
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
    when(executionEntity.getJobs()).thenReturn(new ArrayList<>());
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntity);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

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
    verify(executionEntity).getJobs();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then {@link JobEntityImpl} (default constructor) TenantId is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean_whenFalse_thenJobEntityImplTenantIdIsEmptyString() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(mock(ActivitiEventDispatcher.class));
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    jobEntityManagerImpl.insert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>When {@link JobEntityImpl} (default constructor).</li>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  public void testInsertWithJobEntityBoolean_whenJobEntityImpl_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert2() {
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert3() {
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert_givenActivitiEventDispatcherIsEnabledReturnFalse_thenCallsIsEnabled() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert_givenExecutionDataManagerFindByIdReturnNull_thenReturnFalse() {
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
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration,
        mock(JobDataManager.class));

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById(eq("42"));
    assertEquals("", jobEntity.getTenantId());
    assertFalse(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link DelegateExecution#getTenantId()}
   * return {@code 42}.</li>
   *   <li>Then calls {@link DelegateExecution#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert_givenExecutionEntityGetTenantIdReturn42_thenCallsGetTenantId() {
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
    when(executionEntity.getJobs()).thenReturn(new ArrayList<>());
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntity);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(executionEntity).getJobs();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert_givenExecutionEntityImplGetTenantIdReturn42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   *   <li>Then calls {@link ExecutionEntityImpl#getJobCount()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert_givenExecutionEntityImplGetTenantIdReturnNull_thenCallsGetJobCount() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link PerformanceSettings} (default constructor)
   * EnableEagerExecutionTreeFetching is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert_givenPerformanceSettingsEnableEagerExecutionTreeFetchingIsTrue() {
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
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then {@link JobEntityImpl} (default constructor) TenantId is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert_whenFalse_thenJobEntityImplTenantIdIsEmptyString() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(mock(ActivitiEventDispatcher.class));
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   * <ul>
   *   <li>When {@link JobEntityImpl} (default constructor).</li>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  public void testDoInsert_whenJobEntityImpl_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsToExecute(Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#findJobsToExecute(Page)}
   */
  @Test
  public void testFindJobsToExecute_thenReturnEmpty() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsToExecute(Mockito.<Page>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager);

    // Act
    List<JobEntity> actualFindJobsToExecuteResult = jobEntityManagerImpl.findJobsToExecute(new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsToExecute(isA(Page.class));
    assertTrue(actualFindJobsToExecuteResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsByExecutionId(String)}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  public void testFindJobsByExecutionId() {
    // Arrange
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<JobEntity> actualFindJobsByExecutionIdResult = jobEntityManagerImpl.findJobsByExecutionId("42");

    // Assert
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsByProcessDefinitionId(String)}.
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#findJobsByProcessDefinitionId(String)}
   */
  @Test
  public void testFindJobsByProcessDefinitionId() {
    // Arrange
    when(jobDataManager.findJobsByProcessDefinitionId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<JobEntity> actualFindJobsByProcessDefinitionIdResult = jobEntityManagerImpl
        .findJobsByProcessDefinitionId("42");

    // Assert
    verify(jobDataManager).findJobsByProcessDefinitionId(eq("42"));
    assertTrue(actualFindJobsByProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test
   * {@link JobEntityManagerImpl#findJobsByTypeAndProcessDefinitionId(String, String)}.
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#findJobsByTypeAndProcessDefinitionId(String, String)}
   */
  @Test
  public void testFindJobsByTypeAndProcessDefinitionId() {
    // Arrange
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<JobEntity> actualFindJobsByTypeAndProcessDefinitionIdResult = jobEntityManagerImpl
        .findJobsByTypeAndProcessDefinitionId("Job Type Timer", "42");

    // Assert
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId(eq("Job Type Timer"), eq("42"));
    assertTrue(actualFindJobsByTypeAndProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#findJobsByProcessInstanceId(String)}
   */
  @Test
  public void testFindJobsByProcessInstanceId() {
    // Arrange
    when(jobDataManager.findJobsByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<JobEntity> actualFindJobsByProcessInstanceIdResult = jobEntityManagerImpl.findJobsByProcessInstanceId("42");

    // Assert
    verify(jobDataManager).findJobsByProcessInstanceId(eq("42"));
    assertTrue(actualFindJobsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findExpiredJobs(Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#findExpiredJobs(Page)}
   */
  @Test
  public void testFindExpiredJobs_thenReturnEmpty() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findExpiredJobs(Mockito.<Page>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager);

    // Act
    List<JobEntity> actualFindExpiredJobsResult = jobEntityManagerImpl.findExpiredJobs(new Page(1, 3));

    // Assert
    verify(jobDataManager).findExpiredJobs(isA(Page.class));
    assertTrue(actualFindExpiredJobsResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#resetExpiredJob(String)}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#resetExpiredJob(String)}
   */
  @Test
  public void testResetExpiredJob() {
    // Arrange
    doNothing().when(jobDataManager).resetExpiredJob(Mockito.<String>any());

    // Act
    jobEntityManagerImpl.resetExpiredJob("42");

    // Assert
    verify(jobDataManager).resetExpiredJob(eq("42"));
  }

  /**
   * Test
   * {@link JobEntityManagerImpl#findJobsByQueryCriteria(JobQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#findJobsByQueryCriteria(JobQueryImpl, Page)}
   */
  @Test
  public void testFindJobsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByQueryCriteria(Mockito.<JobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager);
    JobQueryImpl jobQuery = new JobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult = jobEntityManagerImpl.findJobsByQueryCriteria(jobQuery,
        new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsByQueryCriteria(isA(JobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobCountByQueryCriteria(JobQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#findJobCountByQueryCriteria(JobQueryImpl)}
   */
  @Test
  public void testFindJobCountByQueryCriteria_thenReturnThree() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobCountByQueryCriteria(Mockito.<JobQueryImpl>any())).thenReturn(3L);
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager);

    // Act
    long actualFindJobCountByQueryCriteriaResult = jobEntityManagerImpl.findJobCountByQueryCriteria(new JobQueryImpl());

    // Assert
    verify(jobDataManager).findJobCountByQueryCriteria(isA(JobQueryImpl.class));
    assertEquals(3L, actualFindJobCountByQueryCriteriaResult);
  }

  /**
   * Test
   * {@link JobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}.
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}
   */
  @Test
  public void testUpdateJobTenantIdForDeployment() {
    // Arrange
    doNothing().when(jobDataManager).updateJobTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act
    jobEntityManagerImpl.updateJobTenantIdForDeployment("42", "42");

    // Assert
    verify(jobDataManager).updateJobTenantIdForDeployment(eq("42"), eq("42"));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity)} with {@code JobEntity}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity)}
   */
  @Test
  public void testDeleteWithJobEntity() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setLockOwner("Claimed By");
    jobEntity.setMaxIterations(3);
    jobEntity.setProcessDefinitionId("42");
    jobEntity.setProcessInstanceId("42");
    jobEntity.setRepeat("Repeat");
    jobEntity.setRetries(1);
    jobEntity.setRevision(1);
    jobEntity.setTenantId("42");
    jobEntity.setUpdated(true);
    jobEntity.setExecutionId("Job Entity");
    jobEntity.setExceptionStacktrace(null);

    // Act
    jobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager, atLeast(1)).findById(eq("Job Entity"));
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  public void testDeleteWithJobEntityBoolean() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    jobEntityManagerImpl.delete(new JobEntityImpl(), true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  public void testDeleteWithJobEntityBoolean2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl entity = mock(JobEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getProcessDefinitionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getProcessDefinitionId();
    verify(entity).getProcessInstanceId();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(executionDataManager).findById(eq("42"));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  public void testDeleteWithJobEntityBoolean_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    jobEntityManagerImpl.delete(new JobEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  public void testDeleteWithJobEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    jobEntityManagerImpl.delete(new JobEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link EntityManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  public void testDeleteWithJobEntityBoolean_thenCallsFindById() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl entity = mock(JobEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getProcessDefinitionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getProcessDefinitionId();
    verify(entity).getProcessInstanceId();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link EntityManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  public void testDeleteWithJobEntityBoolean_thenCallsFindById2() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl entity = mock(JobEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, false);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity, atLeast(1)).getExecutionId();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with
   * {@code JobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getJobCount()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  public void testDeleteWithJobEntityBoolean_thenCallsGetJobCount() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);
    JobEntityImpl entity = mock(JobEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getProcessDefinitionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getProcessDefinitionId();
    verify(entity).getProcessInstanceId();
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(eq(2));
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(executionDataManager).findById(eq("42"));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity)} with {@code JobEntity}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity)}
   */
  @Test
  public void testDeleteWithJobEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setLockOwner("Claimed By");
    jobEntity.setMaxIterations(3);
    jobEntity.setProcessDefinitionId("42");
    jobEntity.setProcessInstanceId("42");
    jobEntity.setRepeat("Repeat");
    jobEntity.setRetries(1);
    jobEntity.setRevision(1);
    jobEntity.setTenantId("42");
    jobEntity.setUpdated(true);
    jobEntity.setExecutionId("Job Entity");
    jobEntity.setExceptionStacktrace(null);

    // Act
    jobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager, atLeast(1)).findById(eq("Job Entity"));
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity)} with {@code JobEntity}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity)}
   */
  @Test
  public void testDeleteWithJobEntity_thenCallsDispatchEvent() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setLockOwner("Claimed By");
    jobEntity.setMaxIterations(3);
    jobEntity.setProcessDefinitionId("42");
    jobEntity.setProcessInstanceId("42");
    jobEntity.setRepeat("Repeat");
    jobEntity.setRetries(1);
    jobEntity.setRevision(1);
    jobEntity.setTenantId("42");
    jobEntity.setUpdated(true);
    jobEntity.setExecutionId("Job Entity");
    jobEntity.setExceptionStacktrace(null);

    // Act
    jobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager, atLeast(1)).findById(eq("Job Entity"));
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity)} with {@code JobEntity}.
   * <ul>
   *   <li>When {@link JobEntityImpl} (default constructor) ExecutionId is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobEntityManagerImpl#delete(JobEntity)}
   */
  @Test
  public void testDeleteWithJobEntity_whenJobEntityImplExecutionIdIsNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setLockOwner("Claimed By");
    jobEntity.setMaxIterations(3);
    jobEntity.setProcessDefinitionId("42");
    jobEntity.setProcessInstanceId("42");
    jobEntity.setRepeat("Repeat");
    jobEntity.setRetries(1);
    jobEntity.setRevision(1);
    jobEntity.setTenantId("42");
    jobEntity.setUpdated(true);
    jobEntity.setExecutionId(null);
    jobEntity.setExceptionStacktrace(null);

    // Act
    jobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}.
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}
   */
  @Test
  public void testRemoveExecutionLink() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration,
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setLockOwner("Claimed By");
    jobEntity.setMaxIterations(3);
    jobEntity.setProcessDefinitionId("42");
    jobEntity.setProcessInstanceId("42");
    jobEntity.setRepeat("Repeat");
    jobEntity.setRetries(1);
    jobEntity.setRevision(1);
    jobEntity.setTenantId("42");
    jobEntity.setUpdated(true);
    jobEntity.setExecutionId("Job Entity");

    // Act
    jobEntityManagerImpl.removeExecutionLink(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById(eq("Job Entity"));
  }

  /**
   * Test {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}.
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}
   */
  @Test
  public void testRemoveExecutionLink_givenExecutionDataManagerFindByIdReturnNull() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration,
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setLockOwner("Claimed By");
    jobEntity.setMaxIterations(3);
    jobEntity.setProcessDefinitionId("42");
    jobEntity.setProcessInstanceId("42");
    jobEntity.setRepeat("Repeat");
    jobEntity.setRetries(1);
    jobEntity.setRevision(1);
    jobEntity.setTenantId("42");
    jobEntity.setUpdated(true);
    jobEntity.setExecutionId("Job Entity");

    // Act
    jobEntityManagerImpl.removeExecutionLink(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById(eq("Job Entity"));
  }

  /**
   * Test {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}.
   * <ul>
   *   <li>Then calls {@link EntityManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}
   */
  @Test
  public void testRemoveExecutionLink_thenCallsFindById() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration,
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setLockOwner("Claimed By");
    jobEntity.setMaxIterations(3);
    jobEntity.setProcessDefinitionId("42");
    jobEntity.setProcessInstanceId("42");
    jobEntity.setRepeat("Repeat");
    jobEntity.setRetries(1);
    jobEntity.setRevision(1);
    jobEntity.setTenantId("42");
    jobEntity.setUpdated(true);
    jobEntity.setExecutionId("Job Entity");

    // Act
    jobEntityManagerImpl.removeExecutionLink(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionEntityManager).findById(eq("Job Entity"));
  }

  /**
   * Test {@link JobEntityManagerImpl#deleteExceptionByteArrayRef(JobEntity)}.
   * <ul>
   *   <li>Then calls {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JobEntityManagerImpl#deleteExceptionByteArrayRef(JobEntity)}
   */
  @Test
  public void testDeleteExceptionByteArrayRef_thenCallsGetExceptionByteArrayRef() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration,
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    JobEntityImpl jobEntity = mock(JobEntityImpl.class);
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    jobEntityManagerImpl.deleteExceptionByteArrayRef(jobEntity);

    // Assert that nothing has changed
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
  }
}
