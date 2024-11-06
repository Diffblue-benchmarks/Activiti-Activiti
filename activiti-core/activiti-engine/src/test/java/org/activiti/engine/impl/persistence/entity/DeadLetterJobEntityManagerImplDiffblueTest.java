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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeadLetterJobEntityManagerImplDiffblueTest {
  @Mock
  private DeadLetterJobDataManager deadLetterJobDataManager;

  @InjectMocks
  private DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link DeadLetterJobEntityManagerImpl#DeadLetterJobEntityManagerImpl(ProcessEngineConfigurationImpl, DeadLetterJobDataManager)}
   *   <li>
   * {@link DeadLetterJobEntityManagerImpl#setJobDataManager(DeadLetterJobDataManager)}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    DeadLetterJobEntityManagerImpl actualDeadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));
    MybatisDeadLetterJobDataManager jobDataManager = new MybatisDeadLetterJobDataManager(
        new JtaProcessEngineConfiguration());
    actualDeadLetterJobEntityManagerImpl.setJobDataManager(jobDataManager);

    // Assert
    assertSame(jobDataManager, actualDeadLetterJobEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#findJobsByExecutionId(String)}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  public void testFindJobsByExecutionId() {
    // Arrange
    when(deadLetterJobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<DeadLetterJobEntity> actualFindJobsByExecutionIdResult = deadLetterJobEntityManagerImpl
        .findJobsByExecutionId("42");

    // Assert
    verify(deadLetterJobDataManager).findJobsByExecutionId(eq("42"));
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#findJobsByQueryCriteria(DeadLetterJobQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#findJobsByQueryCriteria(DeadLetterJobQueryImpl, Page)}
   */
  @Test
  public void testFindJobsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    when(jobDataManager.findJobsByQueryCriteria(Mockito.<DeadLetterJobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);
    DeadLetterJobQueryImpl jobQuery = new DeadLetterJobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult = deadLetterJobEntityManagerImpl.findJobsByQueryCriteria(jobQuery,
        new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsByQueryCriteria(isA(DeadLetterJobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#findJobCountByQueryCriteria(DeadLetterJobQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#findJobCountByQueryCriteria(DeadLetterJobQueryImpl)}
   */
  @Test
  public void testFindJobCountByQueryCriteria_thenReturnThree() {
    // Arrange
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    when(jobDataManager.findJobCountByQueryCriteria(Mockito.<DeadLetterJobQueryImpl>any())).thenReturn(3L);
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    long actualFindJobCountByQueryCriteriaResult = deadLetterJobEntityManagerImpl
        .findJobCountByQueryCriteria(new DeadLetterJobQueryImpl());

    // Assert
    verify(jobDataManager).findJobCountByQueryCriteria(isA(DeadLetterJobQueryImpl.class));
    assertEquals(3L, actualFindJobCountByQueryCriteriaResult);
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}
   */
  @Test
  public void testUpdateJobTenantIdForDeployment() {
    // Arrange
    doNothing().when(deadLetterJobDataManager)
        .updateJobTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act
    deadLetterJobEntityManagerImpl.updateJobTenantIdForDeployment("42", "42");

    // Assert
    verify(deadLetterJobDataManager).updateJobTenantIdForDeployment(eq("42"), eq("42"));
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with
   * {@code DeadLetterJobEntity}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntity() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with
   * {@code DeadLetterJobEntity}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntity2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with
   * {@code DeadLetterJobEntity}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntity3() {
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with
   * {@code DeadLetterJobEntity}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntity4() {
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

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
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   * with {@code DeadLetterJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntityBoolean() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   * with {@code DeadLetterJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntityBoolean2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   * with {@code DeadLetterJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntityBoolean3() {
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   * with {@code DeadLetterJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntityBoolean4() {
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

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
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   * with {@code DeadLetterJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntityBoolean5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   * with {@code DeadLetterJobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   * with {@code DeadLetterJobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link EntityManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntityBoolean_thenCallsFindById() {
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   * with {@code DeadLetterJobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor) TenantId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntityBoolean_thenDeadLetterJobEntityImplTenantIdIs42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   * with {@code DeadLetterJobEntity}, {@code boolean}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity, boolean)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntityBoolean_whenFalse() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(mock(ActivitiEventDispatcher.class));
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with
   * {@code DeadLetterJobEntity}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with
   * {@code DeadLetterJobEntity}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntity_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with
   * {@code DeadLetterJobEntity}.
   * <ul>
   *   <li>Then calls {@link EntityManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntity_thenCallsFindById() {
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)} with
   * {@code DeadLetterJobEntity}.
   * <ul>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor) TenantId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#insert(DeadLetterJobEntity)}
   */
  @Test
  public void testInsertWithDeadLetterJobEntity_thenDeadLetterJobEntityImplTenantIdIs42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getDeadLetterJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setDeadLetterJobCount(anyInt());
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
    DeadLetterJobDataManager jobDataManager = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<DeadLetterJobEntity>any());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    DeadLetterJobEntityImpl jobEntity = new DeadLetterJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    deadLetterJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityImpl).getDeadLetterJobCount();
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setDeadLetterJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(DeadLetterJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#deleteExceptionByteArrayRef(DeadLetterJobEntity)}.
   * <ul>
   *   <li>Then calls {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#deleteExceptionByteArrayRef(DeadLetterJobEntity)}
   */
  @Test
  public void testDeleteExceptionByteArrayRef_thenCallsGetExceptionByteArrayRef() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    DeadLetterJobEntityImpl jobEntity = mock(DeadLetterJobEntityImpl.class);
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    deadLetterJobEntityManagerImpl.deleteExceptionByteArrayRef(jobEntity);

    // Assert that nothing has changed
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#createDeadLetterJob(AbstractJobEntity)}.
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#createDeadLetterJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateDeadLetterJob() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    DeadLetterJobEntity actualCreateDeadLetterJobResult = deadLetterJobEntityManagerImpl
        .createDeadLetterJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateDeadLetterJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateDeadLetterJobResult instanceof DeadLetterJobEntityImpl);
    assertEquals("", actualCreateDeadLetterJobResult.getTenantId());
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
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
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateDeadLetterJobResult.getMaxIterations());
    assertEquals(0, actualCreateDeadLetterJobResult.getRetries());
    assertEquals(1, actualCreateDeadLetterJobResult.getRevision());
    assertEquals(2, actualCreateDeadLetterJobResult.getRevisionNext());
    assertFalse(actualCreateDeadLetterJobResult.isDeleted());
    assertFalse(actualCreateDeadLetterJobResult.isInserted());
    assertFalse(actualCreateDeadLetterJobResult.isUpdated());
    assertTrue(actualCreateDeadLetterJobResult.isExclusive());
  }

  /**
   * Test
   * {@link DeadLetterJobEntityManagerImpl#createDeadLetterJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeadLetterJobEntityManagerImpl#createDeadLetterJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateDeadLetterJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    DeadLetterJobEntity actualCreateDeadLetterJobResult = deadLetterJobEntityManagerImpl
        .createDeadLetterJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateDeadLetterJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateDeadLetterJobResult instanceof DeadLetterJobEntityImpl);
    assertEquals("", actualCreateDeadLetterJobResult.getTenantId());
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
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
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateDeadLetterJobResult.getMaxIterations());
    assertEquals(0, actualCreateDeadLetterJobResult.getRetries());
    assertEquals(1, actualCreateDeadLetterJobResult.getRevision());
    assertEquals(2, actualCreateDeadLetterJobResult.getRevisionNext());
    assertFalse(actualCreateDeadLetterJobResult.isDeleted());
    assertFalse(actualCreateDeadLetterJobResult.isInserted());
    assertFalse(actualCreateDeadLetterJobResult.isUpdated());
    assertTrue(actualCreateDeadLetterJobResult.isExclusive());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#getDataManager()}.
   * <p>
   * Method under test: {@link DeadLetterJobEntityManagerImpl#getDataManager()}
   */
  @Test
  public void testGetDataManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(deadLetterJobEntityManagerImpl.jobDataManager, deadLetterJobEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link DeadLetterJobEntityManagerImpl#getDataManager()}.
   * <p>
   * Method under test: {@link DeadLetterJobEntityManagerImpl#getDataManager()}
   */
  @Test
  public void testGetDataManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(deadLetterJobEntityManagerImpl.jobDataManager, deadLetterJobEntityManagerImpl.getDataManager());
  }
}
