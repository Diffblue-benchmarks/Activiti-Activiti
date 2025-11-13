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
import org.activiti.engine.impl.DeadLetterJobQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeadLetterJobDataManager;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DeadLetterJobEntityManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       DeadLetterJobEntityManagerImpl#DeadLetterJobEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       DeadLetterJobDataManager)}
   *   <li>{@link DeadLetterJobEntityManagerImpl#setJobDataManager(DeadLetterJobDataManager)}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeadLetterJobEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, DeadLetterJobDataManager)",
    "void DeadLetterJobEntityManagerImpl.setJobDataManager(DeadLetterJobDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    DeadLetterJobEntityManagerImpl actualDeadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));
    MybatisDeadLetterJobDataManager jobDataManager =
        new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration());
    actualDeadLetterJobEntityManagerImpl.setJobDataManager(jobDataManager);

    // Assert
    assertSame(jobDataManager, actualDeadLetterJobEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#findJobsByExecutionId(String)}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DeadLetterJobEntityManagerImpl.findJobsByExecutionId(String)"})
  public void testFindJobsByExecutionId() {
    // Arrange
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<DeadLetterJobEntity> actualFindJobsByExecutionIdResult =
        deadLetterJobEntityManagerImpl.findJobsByExecutionId("42");

    // Assert
    verify(jobDataManager).findJobsByExecutionId("42");
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#findJobsByQueryCriteria(DeadLetterJobQueryImpl,
   * Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeadLetterJobEntityManagerImpl#findJobsByQueryCriteria(DeadLetterJobQueryImpl, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeadLetterJobEntityManagerImpl.findJobsByQueryCriteria(DeadLetterJobQueryImpl, Page)"
  })
  public void testFindJobsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    when(jobDataManager.findJobsByQueryCriteria(
            Mockito.<DeadLetterJobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);
    DeadLetterJobQueryImpl jobQuery = new DeadLetterJobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult =
        deadLetterJobEntityManagerImpl.findJobsByQueryCriteria(jobQuery, new Page(1, 3));

    // Assert
    verify(jobDataManager)
        .findJobsByQueryCriteria(isA(DeadLetterJobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link
   * DeadLetterJobEntityManagerImpl#findJobCountByQueryCriteria(DeadLetterJobQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeadLetterJobEntityManagerImpl#findJobCountByQueryCriteria(DeadLetterJobQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long DeadLetterJobEntityManagerImpl.findJobCountByQueryCriteria(DeadLetterJobQueryImpl)"
  })
  public void testFindJobCountByQueryCriteria_thenReturnThree() {
    // Arrange
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    when(jobDataManager.findJobCountByQueryCriteria(Mockito.<DeadLetterJobQueryImpl>any()))
        .thenReturn(3L);
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    long actualFindJobCountByQueryCriteriaResult =
        deadLetterJobEntityManagerImpl.findJobCountByQueryCriteria(new DeadLetterJobQueryImpl());

    // Assert
    verify(jobDataManager).findJobCountByQueryCriteria(isA(DeadLetterJobQueryImpl.class));
    assertEquals(3L, actualFindJobCountByQueryCriteriaResult);
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}.
   *
   * <p>Method under test: {@link
   * DeadLetterJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeadLetterJobEntityManagerImpl.updateJobTenantIdForDeployment(String, String)"
  })
  public void testUpdateJobTenantIdForDeployment() {
    // Arrange
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing()
        .when(jobDataManager)
        .updateJobTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    deadLetterJobEntityManagerImpl.updateJobTenantIdForDeployment("42", "42");

    // Assert
    verify(jobDataManager).updateJobTenantIdForDeployment("42", "42");
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity)"})
  public void testInsertWithDeadLetterJobEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    deadLetterJobEntityManagerImpl.insert(new DeadLetterJobEntityImpl());

    // Assert
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity)"})
  public void testInsertWithDeadLetterJobEntity2() {
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity)"})
  public void testInsertWithDeadLetterJobEntity3() {
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity)"})
  public void testInsertWithDeadLetterJobEntity4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity)"})
  public void testInsertWithDeadLetterJobEntity5() {
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity)"})
  public void testInsertWithDeadLetterJobEntity6() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(4);
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)} with {@code
   * DeadLetterJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity, boolean)"})
  public void testInsertWithDeadLetterJobEntityBoolean() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    deadLetterJobEntityManagerImpl.insert(new DeadLetterJobEntityImpl(), true);

    // Assert
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)} with {@code
   * DeadLetterJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity, boolean)"})
  public void testInsertWithDeadLetterJobEntityBoolean2() {
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)} with {@code
   * DeadLetterJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity, boolean)"})
  public void testInsertWithDeadLetterJobEntityBoolean3() {
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)} with {@code
   * DeadLetterJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity, boolean)"})
  public void testInsertWithDeadLetterJobEntityBoolean4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)} with {@code
   * DeadLetterJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity, boolean)"})
  public void testInsertWithDeadLetterJobEntityBoolean5() {
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)} with {@code
   * DeadLetterJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity, boolean)"})
  public void testInsertWithDeadLetterJobEntityBoolean6() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(4);
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)} with {@code
   * DeadLetterJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity, boolean)"})
  public void testInsertWithDeadLetterJobEntityBoolean7() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(4);
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)} with {@code
   * DeadLetterJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity, boolean)"})
  public void testInsertWithDeadLetterJobEntityBoolean8() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(4);
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)} with {@code
   * DeadLetterJobEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity, boolean)"})
  public void testInsertWithDeadLetterJobEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity)"})
  public void testInsertWithDeadLetterJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(4);
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.insert(DeadLetterJobEntity)"})
  public void testInsertWithDeadLetterJobEntity_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.delete(DeadLetterJobEntity)"})
  public void testDeleteWithDeadLetterJobEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    deadLetterJobEntityManagerImpl.delete(new DeadLetterJobEntityImpl());

    // Assert
    verify(jobDataManager).delete(isA(DeadLetterJobEntity.class));
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.delete(DeadLetterJobEntity)"})
  public void testDeleteWithDeadLetterJobEntity2() {
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
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    deadLetterJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
    verify(jobDataManager).delete(isA(DeadLetterJobEntity.class));
    verify(executionDataManager).findById("42");
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.delete(DeadLetterJobEntity)"})
  public void testDeleteWithDeadLetterJobEntity3() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    deadLetterJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
    verify(jobDataManager).delete(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.delete(DeadLetterJobEntity)"})
  public void testDeleteWithDeadLetterJobEntity4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    deadLetterJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).delete(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.delete(DeadLetterJobEntity)"})
  public void testDeleteWithDeadLetterJobEntity_thenCallsDispatchEvent() {
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

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    deadLetterJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).delete(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.delete(DeadLetterJobEntity)"})
  public void testDeleteWithDeadLetterJobEntity_thenCallsFindById() {
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
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    deadLetterJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).delete(isA(DeadLetterJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getDeadLetterJobCount()}.
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.delete(DeadLetterJobEntity)"})
  public void testDeleteWithDeadLetterJobEntity_thenCallsGetDeadLetterJobCount() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    deadLetterJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(2);
    verify(jobDataManager).delete(isA(DeadLetterJobEntity.class));
    verify(executionDataManager).findById("42");
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)} with {@code
   * DeadLetterJobEntity}.
   *
   * <ul>
   *   <li>When {@link DeadLetterJobEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#delete(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeadLetterJobEntityManagerImpl.delete(DeadLetterJobEntity)"})
  public void testDeleteWithDeadLetterJobEntity_whenDeadLetterJobEntityImpl() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<DeadLetterJobEntity>any());

    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    deadLetterJobEntityManagerImpl.delete(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(jobDataManager).delete(isA(DeadLetterJobEntity.class));
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#deleteExceptionByteArrayRef(DeadLetterJobEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link DeadLetterJobEntity#getExceptionByteArrayRef()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeadLetterJobEntityManagerImpl#deleteExceptionByteArrayRef(DeadLetterJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeadLetterJobEntityManagerImpl.deleteExceptionByteArrayRef(DeadLetterJobEntity)"
  })
  public void testDeleteExceptionByteArrayRef_thenCallsGetExceptionByteArrayRef() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    DeadLetterJobEntity jobEntity = mock(DeadLetterJobEntity.class);
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    deadLetterJobEntityManagerImpl.deleteExceptionByteArrayRef(jobEntity);

    // Assert
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#createDeadLetterJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeadLetterJobEntityManagerImpl#createDeadLetterJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeadLetterJobEntity DeadLetterJobEntityManagerImpl.createDeadLetterJob(AbstractJobEntity)"
  })
  public void testCreateDeadLetterJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    DeadLetterJobEntity actualCreateDeadLetterJobResult =
        deadLetterJobEntityManagerImpl.createDeadLetterJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateDeadLetterJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateDeadLetterJobResult instanceof DeadLetterJobEntityImpl);
    assertEquals("", actualCreateDeadLetterJobResult.getTenantId());
    assertNull(actualCreateDeadLetterJobResult.getExceptionStacktrace());
    assertNull(actualCreateDeadLetterJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateDeadLetterJobResult.getJobHandlerType());
    assertNull(actualCreateDeadLetterJobResult.getJobType());
    assertNull(actualCreateDeadLetterJobResult.getRepeat());
    assertNull(actualCreateDeadLetterJobResult.getId());
    assertNull(actualCreateDeadLetterJobResult.getExceptionMessage());
    assertNull(actualCreateDeadLetterJobResult.getExecutionId());
    assertNull(actualCreateDeadLetterJobResult.getProcessDefinitionId());
    assertNull(actualCreateDeadLetterJobResult.getProcessInstanceId());
    assertNull(actualCreateDeadLetterJobResult.getEndDate());
    assertNull(actualCreateDeadLetterJobResult.getDuedate());
    assertNull(actualCreateDeadLetterJobResult.getExceptionByteArrayRef());
    assertEquals(0, actualCreateDeadLetterJobResult.getMaxIterations());
    assertEquals(0, actualCreateDeadLetterJobResult.getRetries());
    assertEquals(1, actualCreateDeadLetterJobResult.getRevision());
    assertEquals(2, actualCreateDeadLetterJobResult.getRevisionNext());
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertFalse(actualCreateDeadLetterJobResult.isDeleted());
    assertFalse(actualCreateDeadLetterJobResult.isInserted());
    assertFalse(actualCreateDeadLetterJobResult.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(actualCreateDeadLetterJobResult.isExclusive());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#getDataManager()}.
   *
   * <p>Method under test: {@link DeadLetterJobEntityManagerImpl#getDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobDataManager DeadLetterJobEntityManagerImpl.getDataManager()"})
  public void testGetDataManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl =
        new DeadLetterJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    DeadLetterJobDataManager actualDataManager = deadLetterJobEntityManagerImpl.getDataManager();

    // Assert
    assertSame(deadLetterJobEntityManagerImpl.jobDataManager, actualDataManager);
  }
}
