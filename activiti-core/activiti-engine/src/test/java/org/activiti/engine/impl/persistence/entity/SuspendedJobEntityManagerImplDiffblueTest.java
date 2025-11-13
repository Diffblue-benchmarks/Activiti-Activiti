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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class SuspendedJobEntityManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       SuspendedJobEntityManagerImpl#SuspendedJobEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       SuspendedJobDataManager)}
   *   <li>{@link SuspendedJobEntityManagerImpl#setJobDataManager(SuspendedJobDataManager)}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SuspendedJobEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, SuspendedJobDataManager)",
    "void SuspendedJobEntityManagerImpl.setJobDataManager(SuspendedJobDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    SuspendedJobEntityManagerImpl actualSuspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));
    MybatisSuspendedJobDataManager jobDataManager =
        new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration());
    actualSuspendedJobEntityManagerImpl.setJobDataManager(jobDataManager);

    // Assert
    assertSame(jobDataManager, actualSuspendedJobEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#findJobsByExecutionId(String)}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List SuspendedJobEntityManagerImpl.findJobsByExecutionId(String)"})
  public void testFindJobsByExecutionId() {
    // Arrange
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<SuspendedJobEntity> actualFindJobsByExecutionIdResult =
        suspendedJobEntityManagerImpl.findJobsByExecutionId("42");

    // Assert
    verify(jobDataManager).findJobsByExecutionId("42");
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#findJobsByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#findJobsByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List SuspendedJobEntityManagerImpl.findJobsByProcessInstanceId(String)"})
  public void testFindJobsByProcessInstanceId() {
    // Arrange
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    when(jobDataManager.findJobsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<SuspendedJobEntity> actualFindJobsByProcessInstanceIdResult =
        suspendedJobEntityManagerImpl.findJobsByProcessInstanceId("42");

    // Assert
    verify(jobDataManager).findJobsByProcessInstanceId("42");
    assertTrue(actualFindJobsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#findJobsByQueryCriteria(SuspendedJobQueryImpl,
   * Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * SuspendedJobEntityManagerImpl#findJobsByQueryCriteria(SuspendedJobQueryImpl, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List SuspendedJobEntityManagerImpl.findJobsByQueryCriteria(SuspendedJobQueryImpl, Page)"
  })
  public void testFindJobsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    when(jobDataManager.findJobsByQueryCriteria(
            Mockito.<SuspendedJobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);
    SuspendedJobQueryImpl jobQuery = new SuspendedJobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult =
        suspendedJobEntityManagerImpl.findJobsByQueryCriteria(jobQuery, new Page(1, 3));

    // Assert
    verify(jobDataManager)
        .findJobsByQueryCriteria(isA(SuspendedJobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#findJobCountByQueryCriteria(SuspendedJobQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * SuspendedJobEntityManagerImpl#findJobCountByQueryCriteria(SuspendedJobQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long SuspendedJobEntityManagerImpl.findJobCountByQueryCriteria(SuspendedJobQueryImpl)"
  })
  public void testFindJobCountByQueryCriteria_thenReturnThree() {
    // Arrange
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    when(jobDataManager.findJobCountByQueryCriteria(Mockito.<SuspendedJobQueryImpl>any()))
        .thenReturn(3L);
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    long actualFindJobCountByQueryCriteriaResult =
        suspendedJobEntityManagerImpl.findJobCountByQueryCriteria(new SuspendedJobQueryImpl());

    // Assert
    verify(jobDataManager).findJobCountByQueryCriteria(isA(SuspendedJobQueryImpl.class));
    assertEquals(3L, actualFindJobCountByQueryCriteriaResult);
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}.
   *
   * <p>Method under test: {@link
   * SuspendedJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SuspendedJobEntityManagerImpl.updateJobTenantIdForDeployment(String, String)"
  })
  public void testUpdateJobTenantIdForDeployment() {
    // Arrange
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing()
        .when(jobDataManager)
        .updateJobTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    suspendedJobEntityManagerImpl.updateJobTenantIdForDeployment("42", "42");

    // Assert
    verify(jobDataManager).updateJobTenantIdForDeployment("42", "42");
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code
   * SuspendedJobEntity}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    suspendedJobEntityManagerImpl.insert(new SuspendedJobEntityImpl());

    // Assert
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code
   * SuspendedJobEntity}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity2() {
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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code
   * SuspendedJobEntity}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity3() {
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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code
   * SuspendedJobEntity}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");

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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code
   * SuspendedJobEntity}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity5() {
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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code
   * SuspendedJobEntity}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity6() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");

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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(4);
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code
   * SuspendedJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    suspendedJobEntityManagerImpl.insert(new SuspendedJobEntityImpl(), true);

    // Assert
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code
   * SuspendedJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean2() {
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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code
   * SuspendedJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean3() {
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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code
   * SuspendedJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");

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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code
   * SuspendedJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean5() {
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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code
   * SuspendedJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean6() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");

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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(4);
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code
   * SuspendedJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean7() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);

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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(4);
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code
   * SuspendedJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean8() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");

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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(4);
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code
   * SuspendedJobEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");

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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code
   * SuspendedJobEntity}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);

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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(4);
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code
   * SuspendedJobEntity}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");

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

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    suspendedJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)} with {@code
   * SuspendedJobEntity}.
   *
   * <ul>
   *   <li>Then calls {@link SuspendedJobDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.delete(SuspendedJobEntity)"})
  public void testDeleteWithSuspendedJobEntity_thenCallsDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<SuspendedJobEntity>any());

    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    suspendedJobEntityManagerImpl.delete(new SuspendedJobEntityImpl());

    // Assert
    verify(jobDataManager).delete(isA(SuspendedJobEntity.class));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#deleteExceptionByteArrayRef(SuspendedJobEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link SuspendedJobEntity#getExceptionByteArrayRef()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SuspendedJobEntityManagerImpl#deleteExceptionByteArrayRef(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SuspendedJobEntityManagerImpl.deleteExceptionByteArrayRef(SuspendedJobEntity)"
  })
  public void testDeleteExceptionByteArrayRef_thenCallsGetExceptionByteArrayRef() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    SuspendedJobEntity jobEntity = mock(SuspendedJobEntity.class);
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    suspendedJobEntityManagerImpl.deleteExceptionByteArrayRef(jobEntity);

    // Assert
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#createSuspendedJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SuspendedJobEntityManagerImpl#createSuspendedJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SuspendedJobEntity SuspendedJobEntityManagerImpl.createSuspendedJob(AbstractJobEntity)"
  })
  public void testCreateSuspendedJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    SuspendedJobEntity actualCreateSuspendedJobResult =
        suspendedJobEntityManagerImpl.createSuspendedJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateSuspendedJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateSuspendedJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("", actualCreateSuspendedJobResult.getTenantId());
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
    assertEquals(0, actualCreateSuspendedJobResult.getMaxIterations());
    assertEquals(0, actualCreateSuspendedJobResult.getRetries());
    assertEquals(1, actualCreateSuspendedJobResult.getRevision());
    assertEquals(2, actualCreateSuspendedJobResult.getRevisionNext());
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertFalse(actualCreateSuspendedJobResult.isDeleted());
    assertFalse(actualCreateSuspendedJobResult.isInserted());
    assertFalse(actualCreateSuspendedJobResult.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(actualCreateSuspendedJobResult.isExclusive());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#getDataManager()}.
   *
   * <p>Method under test: {@link SuspendedJobEntityManagerImpl#getDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SuspendedJobDataManager SuspendedJobEntityManagerImpl.getDataManager()"})
  public void testGetDataManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl =
        new SuspendedJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    SuspendedJobDataManager actualDataManager = suspendedJobEntityManagerImpl.getDataManager();

    // Assert
    assertSame(suspendedJobEntityManagerImpl.jobDataManager, actualDataManager);
  }
}
