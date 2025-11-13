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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class JobEntityManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JobEntityManagerImpl#JobEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       JobDataManager)}
   *   <li>{@link JobEntityManagerImpl#setJobDataManager(JobDataManager)}
   *   <li>{@link JobEntityManagerImpl#getDataManager()}
   *   <li>{@link JobEntityManagerImpl#getJobDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JobEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, JobDataManager)",
    "DataManager JobEntityManagerImpl.getDataManager()",
    "JobDataManager JobEntityManagerImpl.getJobDataManager()",
    "void JobEntityManagerImpl.setJobDataManager(JobDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    JobEntityManagerImpl actualJobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    MybatisJobDataManager jobDataManager =
        new MybatisJobDataManager(new JtaProcessEngineConfiguration());
    actualJobEntityManagerImpl.setJobDataManager(jobDataManager);
    DataManager<JobEntity> actualDataManager = actualJobEntityManagerImpl.getDataManager();

    // Assert
    assertSame(jobDataManager, actualDataManager);
    assertSame(jobDataManager, actualJobEntityManagerImpl.getJobDataManager());
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.insertJobEntity(JobEntity)"})
  public void testInsertJobEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(new JobEntityImpl());

    // Assert
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.insertJobEntity(JobEntity)"})
  public void testInsertJobEntity2() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity timerJobEntity = mock(JobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.insertJobEntity(JobEntity)"})
  public void testInsertJobEntity3() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity timerJobEntity = mock(JobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.insertJobEntity(JobEntity)"})
  public void testInsertJobEntity4() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity timerJobEntity = mock(JobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@link ExecutionEntityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.insertJobEntity(JobEntity)"})
  public void testInsertJobEntity_givenExecutionDataManagerFindByIdReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity timerJobEntity = mock(JobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.insertJobEntity(JobEntity)"})
  public void testInsertJobEntity_givenExecutionDataManagerFindByIdReturnNull_thenReturnFalse() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, mock(JobDataManager.class));

    JobEntity timerJobEntity = mock(JobEntity.class);
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById("42");
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    assertFalse(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.insertJobEntity(JobEntity)"})
  public void testInsertJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity timerJobEntity = mock(JobEntity.class);
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityManager} {@link ExecutionEntityManager#findById(String)}
   *       return {@link ExecutionEntityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.insertJobEntity(JobEntity)"})
  public void testInsertJobEntity_givenExecutionEntityManagerFindByIdReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity timerJobEntity = mock(JobEntity.class);
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insertJobEntity(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.insertJobEntity(JobEntity)"})
  public void testInsertJobEntity_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity timerJobEntity = mock(JobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertJobEntityResult = jobEntityManagerImpl.insertJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertJobEntityResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    jobEntityManagerImpl.insert(new JobEntityImpl(), true);

    // Assert
    verify(jobDataManager).insert(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean2() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean3() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean5() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean6() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(mock(ActivitiEventDispatcher.class));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean_givenExecutionDataManagerFindByIdReturnNull() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, mock(JobDataManager.class));

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById("42");
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link JobEntityManagerImpl#insert(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#insert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.insert(JobEntity, boolean)"})
  public void testInsertWithJobEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(new JobEntityImpl(), true);

    // Assert
    verify(jobDataManager).insert(isA(JobEntity.class));
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert2() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert3() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert_givenActivitiEventDispatcherIsEnabledReturnFalse_thenCallsIsEnabled() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@link ExecutionEntityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert_givenExecutionDataManagerFindByIdReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert_givenExecutionDataManagerFindByIdReturnNull_thenReturnFalse() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, mock(JobDataManager.class));

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById("42");
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertFalse(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert_givenExecutionEntityImplGetTenantIdReturnNull_thenCallsIsEnabled() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityManager} {@link ExecutionEntityManager#findById(String)}
   *       return {@link ExecutionEntityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert_givenExecutionEntityManagerFindByIdReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(mock(ActivitiEventDispatcher.class));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link PerformanceSettings} (default constructor) EnableExecutionRelationshipCounts
   *       is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert_givenPerformanceSettingsEnableExecutionRelationshipCountsIsFalse() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#doInsert(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobEntityManagerImpl.doInsert(JobEntity, boolean)"})
  public void testDoInsert_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getJobs()).thenReturn(new ArrayList<>());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = jobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).getJobs();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(JobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsToExecute(Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#findJobsToExecute(Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List JobEntityManagerImpl.findJobsToExecute(Page)"})
  public void testFindJobsToExecute_thenReturnEmpty() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsToExecute(Mockito.<Page>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<JobEntity> actualFindJobsToExecuteResult =
        jobEntityManagerImpl.findJobsToExecute(new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsToExecute(isA(Page.class));
    assertTrue(actualFindJobsToExecuteResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsByExecutionId(String)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List JobEntityManagerImpl.findJobsByExecutionId(String)"})
  public void testFindJobsByExecutionId() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<JobEntity> actualFindJobsByExecutionIdResult =
        jobEntityManagerImpl.findJobsByExecutionId("42");

    // Assert
    verify(jobDataManager).findJobsByExecutionId("42");
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsByProcessDefinitionId(String)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#findJobsByProcessDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List JobEntityManagerImpl.findJobsByProcessDefinitionId(String)"})
  public void testFindJobsByProcessDefinitionId() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<JobEntity> actualFindJobsByProcessDefinitionIdResult =
        jobEntityManagerImpl.findJobsByProcessDefinitionId("42");

    // Assert
    verify(jobDataManager).findJobsByProcessDefinitionId("42");
    assertTrue(actualFindJobsByProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsByTypeAndProcessDefinitionId(String, String)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#findJobsByTypeAndProcessDefinitionId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List JobEntityManagerImpl.findJobsByTypeAndProcessDefinitionId(String, String)"
  })
  public void testFindJobsByTypeAndProcessDefinitionId() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<JobEntity> actualFindJobsByTypeAndProcessDefinitionIdResult =
        jobEntityManagerImpl.findJobsByTypeAndProcessDefinitionId("Job Type Timer", "42");

    // Assert
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId("Job Type Timer", "42");
    assertTrue(actualFindJobsByTypeAndProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#findJobsByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List JobEntityManagerImpl.findJobsByProcessInstanceId(String)"})
  public void testFindJobsByProcessInstanceId() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<JobEntity> actualFindJobsByProcessInstanceIdResult =
        jobEntityManagerImpl.findJobsByProcessInstanceId("42");

    // Assert
    verify(jobDataManager).findJobsByProcessInstanceId("42");
    assertTrue(actualFindJobsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findExpiredJobs(Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#findExpiredJobs(Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List JobEntityManagerImpl.findExpiredJobs(Page)"})
  public void testFindExpiredJobs_thenReturnEmpty() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findExpiredJobs(Mockito.<Page>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<JobEntity> actualFindExpiredJobsResult =
        jobEntityManagerImpl.findExpiredJobs(new Page(1, 3));

    // Assert
    verify(jobDataManager).findExpiredJobs(isA(Page.class));
    assertTrue(actualFindExpiredJobsResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#resetExpiredJob(String)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#resetExpiredJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.resetExpiredJob(String)"})
  public void testResetExpiredJob() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).resetExpiredJob(Mockito.<String>any());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    jobEntityManagerImpl.resetExpiredJob("42");

    // Assert
    verify(jobDataManager).resetExpiredJob("42");
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobsByQueryCriteria(JobQueryImpl, Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#findJobsByQueryCriteria(JobQueryImpl, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List JobEntityManagerImpl.findJobsByQueryCriteria(JobQueryImpl, Page)"})
  public void testFindJobsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByQueryCriteria(Mockito.<JobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);
    JobQueryImpl jobQuery = new JobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult =
        jobEntityManagerImpl.findJobsByQueryCriteria(jobQuery, new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsByQueryCriteria(isA(JobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link JobEntityManagerImpl#findJobCountByQueryCriteria(JobQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#findJobCountByQueryCriteria(JobQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JobEntityManagerImpl.findJobCountByQueryCriteria(JobQueryImpl)"})
  public void testFindJobCountByQueryCriteria_thenReturnThree() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobCountByQueryCriteria(Mockito.<JobQueryImpl>any())).thenReturn(3L);
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    long actualFindJobCountByQueryCriteriaResult =
        jobEntityManagerImpl.findJobCountByQueryCriteria(new JobQueryImpl());

    // Assert
    verify(jobDataManager).findJobCountByQueryCriteria(isA(JobQueryImpl.class));
    assertEquals(3L, actualFindJobCountByQueryCriteriaResult);
  }

  /**
   * Test {@link JobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#updateJobTenantIdForDeployment(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.updateJobTenantIdForDeployment(String, String)"})
  public void testUpdateJobTenantIdForDeployment() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing()
        .when(jobDataManager)
        .updateJobTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    jobEntityManagerImpl.updateJobTenantIdForDeployment("42", "42");

    // Assert
    verify(jobDataManager).updateJobTenantIdForDeployment("42", "42");
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.delete(JobEntity, boolean)"})
  public void testDeleteWithJobEntityBoolean() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    jobEntityManagerImpl.delete(new JobEntityImpl(), true);

    // Assert
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.delete(JobEntity, boolean)"})
  public void testDeleteWithJobEntityBoolean2() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity entity = mock(JobEntity.class);
    when(entity.getProcessDefinitionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(executionDataManager).findById("42");
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getProcessDefinitionId();
    verify(entity).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.delete(JobEntity, boolean)"})
  public void testDeleteWithJobEntityBoolean3() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity entity = mock(JobEntity.class);
    when(entity.getProcessDefinitionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getProcessDefinitionId();
    verify(entity).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.delete(JobEntity, boolean)"})
  public void testDeleteWithJobEntityBoolean_givenActivitiEventDispatcherIsEnabledReturnFalse() {
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

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity entity = mock(JobEntity.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(entity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.delete(JobEntity, boolean)"})
  public void testDeleteWithJobEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
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
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity entity = mock(JobEntity.class);
    when(entity.getProcessDefinitionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(2);
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(executionDataManager).findById("42");
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getProcessDefinitionId();
    verify(entity).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.delete(JobEntity, boolean)"})
  public void testDeleteWithJobEntityBoolean_thenCallsDispatchEvent2() {
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

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity entity = mock(JobEntity.class);
    when(entity.getProcessDefinitionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getProcessDefinitionId();
    verify(entity).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.delete(JobEntity, boolean)"})
  public void testDeleteWithJobEntityBoolean_thenCallsFindById() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity entity = mock(JobEntity.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, false);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(entity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity, boolean)} with {@code JobEntity}, {@code
   * boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getJobCount()}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#delete(JobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.delete(JobEntity, boolean)"})
  public void testDeleteWithJobEntityBoolean_thenCallsGetJobCount() {
    // Arrange
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
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    JobEntity entity = mock(JobEntity.class);
    when(entity.getProcessDefinitionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    jobEntityManagerImpl.delete(entity, true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityImpl).getJobCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setJobCount(2);
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(executionDataManager).findById("42");
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getProcessDefinitionId();
    verify(entity).getProcessInstanceId();
  }

  /**
   * Test {@link JobEntityManagerImpl#delete(JobEntity)} with {@code JobEntity}.
   *
   * <ul>
   *   <li>Then calls {@link JobDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#delete(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.delete(JobEntity)"})
  public void testDeleteWithJobEntity_thenCallsDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    jobEntityManagerImpl.delete(new JobEntityImpl());

    // Assert
    verify(jobDataManager).delete(isA(JobEntity.class));
  }

  /**
   * Test {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}.
   *
   * <p>Method under test: {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.removeExecutionLink(JobEntity)"})
  public void testRemoveExecutionLink() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
    verify(executionDataManager).findById("Job Entity");
  }

  /**
   * Test {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.removeExecutionLink(JobEntity)"})
  public void testRemoveExecutionLink_givenExecutionDataManagerFindByIdReturnNull() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
    verify(executionDataManager).findById("Job Entity");
  }

  /**
   * Test {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#removeExecutionLink(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.removeExecutionLink(JobEntity)"})
  public void testRemoveExecutionLink_thenCallsFindById() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
    verify(executionEntityManager).findById("Job Entity");
  }

  /**
   * Test {@link JobEntityManagerImpl#deleteExceptionByteArrayRef(JobEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link JobEntity#getExceptionByteArrayRef()}.
   * </ul>
   *
   * <p>Method under test: {@link JobEntityManagerImpl#deleteExceptionByteArrayRef(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobEntityManagerImpl.deleteExceptionByteArrayRef(JobEntity)"})
  public void testDeleteExceptionByteArrayRef_thenCallsGetExceptionByteArrayRef() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    jobEntityManagerImpl.deleteExceptionByteArrayRef(jobEntity);

    // Assert
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
  }
}
