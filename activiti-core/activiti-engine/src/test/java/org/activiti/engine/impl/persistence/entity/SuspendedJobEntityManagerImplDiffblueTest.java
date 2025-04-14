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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.activiti.engine.delegate.DelegateExecution;
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
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SuspendedJobEntityManagerImpl#SuspendedJobEntityManagerImpl(ProcessEngineConfigurationImpl, SuspendedJobDataManager)}
   *   <li>{@link SuspendedJobEntityManagerImpl#setJobDataManager(SuspendedJobDataManager)}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void SuspendedJobEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, SuspendedJobDataManager)",
      "void SuspendedJobEntityManagerImpl.setJobDataManager(SuspendedJobDataManager)"})
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

  /**
   * Test {@link SuspendedJobEntityManagerImpl#findJobsByExecutionId(String)}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List SuspendedJobEntityManagerImpl.findJobsByExecutionId(String)"})
  public void testFindJobsByExecutionId() {
    // Arrange
    when(suspendedJobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<SuspendedJobEntity> actualFindJobsByExecutionIdResult = suspendedJobEntityManagerImpl
        .findJobsByExecutionId("42");

    // Assert
    verify(suspendedJobDataManager).findJobsByExecutionId(eq("42"));
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#findJobsByProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#findJobsByProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List SuspendedJobEntityManagerImpl.findJobsByProcessInstanceId(String)"})
  public void testFindJobsByProcessInstanceId() {
    // Arrange
    when(suspendedJobDataManager.findJobsByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<SuspendedJobEntity> actualFindJobsByProcessInstanceIdResult = suspendedJobEntityManagerImpl
        .findJobsByProcessInstanceId("42");

    // Assert
    verify(suspendedJobDataManager).findJobsByProcessInstanceId(eq("42"));
    assertTrue(actualFindJobsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#findJobsByQueryCriteria(SuspendedJobQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#findJobsByQueryCriteria(SuspendedJobQueryImpl, Page)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List SuspendedJobEntityManagerImpl.findJobsByQueryCriteria(SuspendedJobQueryImpl, Page)"})
  public void testFindJobsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    when(jobDataManager.findJobsByQueryCriteria(Mockito.<SuspendedJobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);
    SuspendedJobQueryImpl jobQuery = new SuspendedJobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult = suspendedJobEntityManagerImpl.findJobsByQueryCriteria(jobQuery,
        new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsByQueryCriteria(isA(SuspendedJobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#findJobCountByQueryCriteria(SuspendedJobQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#findJobCountByQueryCriteria(SuspendedJobQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long SuspendedJobEntityManagerImpl.findJobCountByQueryCriteria(SuspendedJobQueryImpl)"})
  public void testFindJobCountByQueryCriteria_thenReturnThree() {
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
   * Test {@link SuspendedJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.updateJobTenantIdForDeployment(String, String)"})
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity() {
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

    // Assert that nothing has changed
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity2() {
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

    // Assert that nothing has changed
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity3() {
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

    // Assert that nothing has changed
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity4() {
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

    // Assert that nothing has changed
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity5() {
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

    // Assert that nothing has changed
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean() {
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

    // Assert that nothing has changed
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean2() {
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

    // Assert that nothing has changed
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean3() {
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

    // Assert that nothing has changed
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean4() {
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

    // Assert that nothing has changed
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean5() {
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean6() {
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

    // Assert that nothing has changed
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean7() {
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

    // Assert that nothing has changed
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean_thenCallsDispatchEvent() {
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

    // Assert that nothing has changed
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link DelegateExecution#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean_thenCallsGetTenantId() {
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)} with {@code SuspendedJobEntity}, {@code boolean}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity, boolean)"})
  public void testInsertWithSuspendedJobEntityBoolean_whenFalse() {
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

    // Assert that nothing has changed
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity_givenExecutionEntityImplGetTenantIdReturn42() {
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
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

    // Assert that nothing has changed
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <ul>
   *   <li>Then calls {@link DelegateExecution#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity_thenCallsGetTenantId() {
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
   * Test {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <ul>
   *   <li>When {@link SuspendedJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#insert(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.insert(SuspendedJobEntity)"})
  public void testInsertWithSuspendedJobEntity_whenSuspendedJobEntityImpl() {
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

    // Assert that nothing has changed
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(SuspendedJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.delete(SuspendedJobEntity)"})
  public void testDeleteWithSuspendedJobEntity() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    // Act
    suspendedJobEntityManagerImpl.delete(new SuspendedJobEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(jobDataManager).delete(isA(SuspendedJobEntity.class));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.delete(SuspendedJobEntity)"})
  public void testDeleteWithSuspendedJobEntity2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    // Act
    suspendedJobEntityManagerImpl.delete(new SuspendedJobEntityImpl());

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(jobDataManager).delete(isA(SuspendedJobEntity.class));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.delete(SuspendedJobEntity)"})
  public void testDeleteWithSuspendedJobEntity3() {
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
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = mock(SuspendedJobEntityImpl.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(new ByteArrayRef());

    // Act
    suspendedJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
    verify(jobDataManager).delete(isA(SuspendedJobEntity.class));
    verify(executionDataManager).findById(eq("42"));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.delete(SuspendedJobEntity)"})
  public void testDeleteWithSuspendedJobEntity4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = mock(SuspendedJobEntityImpl.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(new ByteArrayRef());

    // Act
    suspendedJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
    verify(jobDataManager).delete(isA(SuspendedJobEntity.class));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.delete(SuspendedJobEntity)"})
  public void testDeleteWithSuspendedJobEntity_thenCallsDelete() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
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
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    SuspendedJobEntityImpl jobEntity = mock(SuspendedJobEntityImpl.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    suspendedJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
    verify(byteArrayRef).delete();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).delete(isA(SuspendedJobEntity.class));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <ul>
   *   <li>Then calls {@link EntityManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.delete(SuspendedJobEntity)"})
  public void testDeleteWithSuspendedJobEntity_thenCallsFindById() {
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
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = mock(SuspendedJobEntityImpl.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(new ByteArrayRef());

    // Act
    suspendedJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
    verify(executionEntityManager).findById(eq("42"));
    verify(jobDataManager).delete(isA(SuspendedJobEntity.class));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getSuspendedJobCount()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.delete(SuspendedJobEntity)"})
  public void testDeleteWithSuspendedJobEntity_thenCallsGetSuspendedJobCount() {
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
    when(executionEntityImpl.getSuspendedJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setSuspendedJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);
    SuspendedJobEntityImpl jobEntity = mock(SuspendedJobEntityImpl.class);
    when(jobEntity.getExecutionId()).thenReturn("42");
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(new ByteArrayRef());

    // Act
    suspendedJobEntityManagerImpl.delete(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(jobEntity).getExceptionByteArrayRef();
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity).getProcessDefinitionId();
    verify(jobEntity).getProcessInstanceId();
    verify(executionEntityImpl).getSuspendedJobCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setSuspendedJobCount(eq(2));
    verify(jobDataManager).delete(isA(SuspendedJobEntity.class));
    verify(executionDataManager).findById(eq("42"));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)} with {@code SuspendedJobEntity}.
   * <ul>
   *   <li>When {@link SuspendedJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#delete(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.delete(SuspendedJobEntity)"})
  public void testDeleteWithSuspendedJobEntity_whenSuspendedJobEntityImpl() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SuspendedJobDataManager jobDataManager = mock(SuspendedJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<SuspendedJobEntity>any());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager);

    // Act
    suspendedJobEntityManagerImpl.delete(new SuspendedJobEntityImpl());

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(jobDataManager).delete(isA(SuspendedJobEntity.class));
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#deleteExceptionByteArrayRef(SuspendedJobEntity)}.
   * <ul>
   *   <li>Then calls {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#deleteExceptionByteArrayRef(SuspendedJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SuspendedJobEntityManagerImpl.deleteExceptionByteArrayRef(SuspendedJobEntity)"})
  public void testDeleteExceptionByteArrayRef_thenCallsGetExceptionByteArrayRef() {
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

    // Assert
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
  }

  /**
   * Test {@link SuspendedJobEntityManagerImpl#createSuspendedJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#createSuspendedJob(AbstractJobEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobEntity SuspendedJobEntityManagerImpl.createSuspendedJob(AbstractJobEntity)"})
  public void testCreateSuspendedJob_thenPersistentStateReturnMap() {
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
   * <p>
   * Method under test: {@link SuspendedJobEntityManagerImpl#getDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobDataManager SuspendedJobEntityManagerImpl.getDataManager()"})
  public void testGetDataManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(suspendedJobEntityManagerImpl.jobDataManager, suspendedJobEntityManagerImpl.getDataManager());
  }
}
