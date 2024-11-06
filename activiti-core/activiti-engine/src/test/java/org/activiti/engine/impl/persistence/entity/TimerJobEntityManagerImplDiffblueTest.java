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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.TimerJobQueryImpl;
import org.activiti.engine.impl.calendar.BusinessCalendarManager;
import org.activiti.engine.impl.calendar.DefaultBusinessCalendar;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.TimerJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.bpmn.event.timer.TimerCustomCalendarTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimerJobEntityManagerImplDiffblueTest {
  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Mock
  private TimerJobDataManager timerJobDataManager;

  @InjectMocks
  private TimerJobEntityManagerImpl timerJobEntityManagerImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TimerJobEntityManagerImpl#TimerJobEntityManagerImpl(ProcessEngineConfigurationImpl, TimerJobDataManager)}
   *   <li>{@link TimerJobEntityManagerImpl#setJobDataManager(TimerJobDataManager)}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    TimerJobEntityManagerImpl actualTimerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        processEngineConfiguration, new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    MybatisTimerJobDataManager jobDataManager = new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration());
    actualTimerJobEntityManagerImpl.setJobDataManager(jobDataManager);

    // Assert
    assertSame(jobDataManager, actualTimerJobEntityManagerImpl.getDataManager());
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#createAndCalculateNextTimer(JobEntity, VariableScope)}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#createAndCalculateNextTimer(JobEntity, VariableScope)}
   */
  @Test
  public void testCreateAndCalculateNextTimer_thenPersistentStateReturnMap() {
    // Arrange
    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);
    when(businessCalendarManager.getBusinessCalendar(Mockito.<String>any()))
        .thenReturn(new TimerCustomCalendarTest.CustomBusinessCalendar());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(businessCalendarManager);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.isExclusive()).thenReturn(true);
    when(timerEntity.getRetries()).thenReturn(1);
    when(timerEntity.getJobHandlerType()).thenReturn("Job Handler Type");
    when(timerEntity.getExecutionId()).thenReturn("42");
    when(timerEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerEntity.getProcessInstanceId()).thenReturn("42");
    when(timerEntity.getTenantId()).thenReturn("42");
    when(timerEntity.getEndDate())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(timerEntity.getMaxIterations()).thenReturn(3);
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");
    when(timerEntity.getRepeat()).thenReturn("Repeat");

    // Act
    TimerJobEntity actualCreateAndCalculateNextTimerResult = timerJobEntityManagerImpl
        .createAndCalculateNextTimer(timerEntity, NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(businessCalendarManager, atLeast(1)).getBusinessCalendar(eq("cycle"));
    verify(timerEntity, atLeast(1)).getEndDate();
    verify(timerEntity, atLeast(1)).getJobHandlerConfiguration();
    verify(timerEntity).getJobHandlerType();
    verify(timerEntity, atLeast(1)).getMaxIterations();
    verify(timerEntity, atLeast(1)).getRepeat();
    verify(timerEntity).isExclusive();
    verify(timerEntity).getExecutionId();
    verify(timerEntity).getProcessDefinitionId();
    verify(timerEntity).getProcessInstanceId();
    verify(timerEntity).getRetries();
    verify(timerEntity).getTenantId();
    Object persistentState = actualCreateAndCalculateNextTimerResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateAndCalculateNextTimerResult instanceof TimerJobEntityImpl);
    assertEquals("42", actualCreateAndCalculateNextTimerResult.getExecutionId());
    assertEquals("42", actualCreateAndCalculateNextTimerResult.getProcessDefinitionId());
    assertEquals("42", actualCreateAndCalculateNextTimerResult.getProcessInstanceId());
    assertEquals("42", actualCreateAndCalculateNextTimerResult.getTenantId());
    assertEquals("Job Handler Configuration", actualCreateAndCalculateNextTimerResult.getJobHandlerConfiguration());
    assertEquals("Job Handler Type", actualCreateAndCalculateNextTimerResult.getJobHandlerType());
    assertEquals("Repeat", actualCreateAndCalculateNextTimerResult.getRepeat());
    assertEquals("timer", actualCreateAndCalculateNextTimerResult.getJobType());
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertNull(((Map<String, Object>) persistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Object>) persistentState).get("lockOwner"));
    assertNull(actualCreateAndCalculateNextTimerResult.getExceptionStacktrace());
    assertNull(actualCreateAndCalculateNextTimerResult.getId());
    assertNull(actualCreateAndCalculateNextTimerResult.getLockOwner());
    assertNull(actualCreateAndCalculateNextTimerResult.getExceptionMessage());
    assertNull(actualCreateAndCalculateNextTimerResult.getLockExpirationTime());
    assertNull(actualCreateAndCalculateNextTimerResult.getExceptionByteArrayRef());
    assertEquals(0, actualCreateAndCalculateNextTimerResult.getMaxIterations());
    assertEquals(1, actualCreateAndCalculateNextTimerResult.getRevision());
    assertEquals(1, actualCreateAndCalculateNextTimerResult.getRetries());
    assertEquals(2, actualCreateAndCalculateNextTimerResult.getRevisionNext());
    assertFalse(actualCreateAndCalculateNextTimerResult.isDeleted());
    assertFalse(actualCreateAndCalculateNextTimerResult.isInserted());
    assertFalse(actualCreateAndCalculateNextTimerResult.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertTrue(actualCreateAndCalculateNextTimerResult.isExclusive());
    Date expectedGetResult = actualCreateAndCalculateNextTimerResult.getDuedate();
    assertSame(expectedGetResult, ((Map<String, Object>) persistentState).get("duedate"));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findTimerJobsToExecute(Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findTimerJobsToExecute(Page)}
   */
  @Test
  public void testFindTimerJobsToExecute_thenReturnEmpty() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findTimerJobsToExecute(Mockito.<Page>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<TimerJobEntity> actualFindTimerJobsToExecuteResult = timerJobEntityManagerImpl
        .findTimerJobsToExecute(new Page(1, 3));

    // Assert
    verify(jobDataManager).findTimerJobsToExecute(isA(Page.class));
    assertTrue(actualFindTimerJobsToExecuteResult.isEmpty());
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionId(String, String)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionId(String, String)}
   */
  @Test
  public void testFindJobsByTypeAndProcessDefinitionId() {
    // Arrange
    when(timerJobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<TimerJobEntity> actualFindJobsByTypeAndProcessDefinitionIdResult = timerJobEntityManagerImpl
        .findJobsByTypeAndProcessDefinitionId("Job Handler Type", "42");

    // Assert
    verify(timerJobDataManager).findJobsByTypeAndProcessDefinitionId(eq("Job Handler Type"), eq("42"));
    assertTrue(actualFindJobsByTypeAndProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyNoTenantId(String, String)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyNoTenantId(String, String)}
   */
  @Test
  public void testFindJobsByTypeAndProcessDefinitionKeyNoTenantId() {
    // Arrange
    when(timerJobDataManager.findJobsByTypeAndProcessDefinitionKeyNoTenantId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<TimerJobEntity> actualFindJobsByTypeAndProcessDefinitionKeyNoTenantIdResult = timerJobEntityManagerImpl
        .findJobsByTypeAndProcessDefinitionKeyNoTenantId("Job Handler Type", "Process Definition Key");

    // Assert
    verify(timerJobDataManager).findJobsByTypeAndProcessDefinitionKeyNoTenantId(eq("Job Handler Type"),
        eq("Process Definition Key"));
    assertTrue(actualFindJobsByTypeAndProcessDefinitionKeyNoTenantIdResult.isEmpty());
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyAndTenantId(String, String, String)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyAndTenantId(String, String, String)}
   */
  @Test
  public void testFindJobsByTypeAndProcessDefinitionKeyAndTenantId() {
    // Arrange
    when(timerJobDataManager.findJobsByTypeAndProcessDefinitionKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<TimerJobEntity> actualFindJobsByTypeAndProcessDefinitionKeyAndTenantIdResult = timerJobEntityManagerImpl
        .findJobsByTypeAndProcessDefinitionKeyAndTenantId("Job Handler Type", "Process Definition Key", "42");

    // Assert
    verify(timerJobDataManager).findJobsByTypeAndProcessDefinitionKeyAndTenantId(eq("Job Handler Type"),
        eq("Process Definition Key"), eq("42"));
    assertTrue(actualFindJobsByTypeAndProcessDefinitionKeyAndTenantIdResult.isEmpty());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findJobsByExecutionId(String)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  public void testFindJobsByExecutionId() {
    // Arrange
    when(timerJobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<TimerJobEntity> actualFindJobsByExecutionIdResult = timerJobEntityManagerImpl.findJobsByExecutionId("42");

    // Assert
    verify(timerJobDataManager).findJobsByExecutionId(eq("42"));
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findJobsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByProcessInstanceId(String)}
   */
  @Test
  public void testFindJobsByProcessInstanceId() {
    // Arrange
    when(timerJobDataManager.findJobsByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<TimerJobEntity> actualFindJobsByProcessInstanceIdResult = timerJobEntityManagerImpl
        .findJobsByProcessInstanceId("42");

    // Assert
    verify(timerJobDataManager).findJobsByProcessInstanceId(eq("42"));
    assertTrue(actualFindJobsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#findJobsByQueryCriteria(TimerJobQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByQueryCriteria(TimerJobQueryImpl, Page)}
   */
  @Test
  public void testFindJobsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByQueryCriteria(Mockito.<TimerJobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);
    TimerJobQueryImpl jobQuery = new TimerJobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult = timerJobEntityManagerImpl.findJobsByQueryCriteria(jobQuery,
        new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsByQueryCriteria(isA(TimerJobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#findJobCountByQueryCriteria(TimerJobQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobCountByQueryCriteria(TimerJobQueryImpl)}
   */
  @Test
  public void testFindJobCountByQueryCriteria_thenReturnThree() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobCountByQueryCriteria(Mockito.<TimerJobQueryImpl>any())).thenReturn(3L);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    long actualFindJobCountByQueryCriteriaResult = timerJobEntityManagerImpl
        .findJobCountByQueryCriteria(new TimerJobQueryImpl());

    // Assert
    verify(jobDataManager).findJobCountByQueryCriteria(isA(TimerJobQueryImpl.class));
    assertEquals(3L, actualFindJobCountByQueryCriteriaResult);
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}
   */
  @Test
  public void testUpdateJobTenantIdForDeployment() {
    // Arrange
    doNothing().when(timerJobDataManager).updateJobTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act
    timerJobEntityManagerImpl.updateJobTenantIdForDeployment("42", "42");

    // Assert
    verify(timerJobDataManager).updateJobTenantIdForDeployment(eq("42"), eq("42"));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity2() {
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity3() {
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

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
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity4() {
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

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
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity_givenExecutionEntityImplGetTenantIdReturn42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

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
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("42", timerJobEntity.getTenantId());
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

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
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <ul>
   *   <li>Then calls {@link DelegateExecution#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity_thenCallsGetTenantId() {
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
    when(executionEntity.getTimerJobs()).thenReturn(new ArrayList<>());
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntity);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

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
    verify(executionEntity).getTimerJobs();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("42", timerJobEntity.getTenantId());
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity_thenReturnFalse() {
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
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        mock(TimerJobDataManager.class));

    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();
    timerJobEntity.setExecutionId("42");

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById(eq("42"));
    assertEquals("", timerJobEntity.getTenantId());
    assertFalse(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   * <ul>
   *   <li>When {@link TimerJobEntityImpl} (default constructor).</li>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity_whenTimerJobEntityImpl_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl timerJobEntity = new TimerJobEntityImpl();

    // Act
    boolean actualInsertTimerJobEntityResult = timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", timerJobEntity.getTenantId());
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity2() {
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity3() {
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

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
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity4() {
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

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
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean3() {
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean4() {
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

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
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

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
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean6() {
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

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
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean_givenExecutionDataManagerFindByIdReturnNull() {
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
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        mock(TimerJobDataManager.class));

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

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
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean_givenExecutionEntityImplGetTenantIdReturn42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

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
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link DelegateExecution#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean_thenCallsGetTenantId() {
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
    when(executionEntity.getTimerJobs()).thenReturn(new ArrayList<>());
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntity);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

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
    verify(executionEntity).getTimerJobs();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with
   * {@code TimerJobEntity}, {@code boolean}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsertWithTimerJobEntityBoolean_whenFalse() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(mock(ActivitiEventDispatcher.class));
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity_givenExecutionDataManagerFindByIdReturnNull() {
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
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        mock(TimerJobDataManager.class));

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

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
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity_givenExecutionEntityImplGetTenantIdReturn42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

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
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

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
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <ul>
   *   <li>Then calls {@link DelegateExecution#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity_thenCallsGetTenantId() {
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
    when(executionEntity.getTimerJobs()).thenReturn(new ArrayList<>());
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntity);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

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
    verify(executionEntity).getTimerJobs();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with
   * {@code TimerJobEntity}.
   * <ul>
   *   <li>When {@link TimerJobEntityImpl} (default constructor).</li>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsertWithTimerJobEntity_whenTimerJobEntityImpl_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert_givenActivitiEventDispatcherIsEnabledReturnFalse_thenCallsIsEnabled() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
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
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        mock(TimerJobDataManager.class));

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

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
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link DelegateExecution#getTenantId()}
   * return {@code 42}.</li>
   *   <li>Then calls {@link DelegateExecution#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
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
    when(executionEntity.getTimerJobs()).thenReturn(new ArrayList<>());
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any())).thenReturn(executionEntity);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(executionEntity).getTimerJobs();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert_givenExecutionEntityImplGetTenantIdReturn42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("42", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   *   <li>Then calls {@link ExecutionEntityImpl#getTenantId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert_givenExecutionEntityImplGetTenantIdReturnNull_thenCallsGetTenantId() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

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
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(eq(4));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <ul>
   *   <li>Given {@link PerformanceSettings} (default constructor)
   * EnableEagerExecutionTreeFetching is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
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
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
    jobEntity.setExecutionId("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then {@link TimerJobEntityImpl} (default constructor) TenantId is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert_whenFalse_thenTimerJobEntityImplTenantIdIsEmptyString() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(mock(ActivitiEventDispatcher.class));
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   * <ul>
   *   <li>When {@link TimerJobEntityImpl} (default constructor).</li>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert_whenTimerJobEntityImpl_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        jobDataManager);
    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertEquals("", jobEntity.getTenantId());
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}.
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}
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
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
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
    timerJobEntityManagerImpl.removeExecutionLink(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById(eq("Job Entity"));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}.
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}
   */
  @Test
  public void testRemoveExecutionLink_givenExecutionDataManagerFindByIdReturnNull() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
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
    timerJobEntityManagerImpl.removeExecutionLink(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById(eq("Job Entity"));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}.
   * <ul>
   *   <li>Then calls {@link EntityManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}
   */
  @Test
  public void testRemoveExecutionLink_thenCallsFindById() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
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
    timerJobEntityManagerImpl.removeExecutionLink(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionEntityManager).findById(eq("Job Entity"));
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#deleteExceptionByteArrayRef(TimerJobEntity)}.
   * <ul>
   *   <li>Then calls {@link AbstractJobEntityImpl#getExceptionByteArrayRef()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#deleteExceptionByteArrayRef(TimerJobEntity)}
   */
  @Test
  public void testDeleteExceptionByteArrayRef_thenCallsGetExceptionByteArrayRef() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    TimerJobEntityImpl jobEntity = mock(TimerJobEntityImpl.class);
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    timerJobEntityManagerImpl.deleteExceptionByteArrayRef(jobEntity);

    // Assert that nothing has changed
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#createTimer(JobEntity)}.
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#createTimer(JobEntity)}
   */
  @Test
  public void testCreateTimer() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    TimerJobEntity actualCreateTimerResult = timerJobEntityManagerImpl.createTimer(new JobEntityImpl());

    // Assert
    Object persistentState = actualCreateTimerResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateTimerResult instanceof TimerJobEntityImpl);
    assertEquals("", actualCreateTimerResult.getTenantId());
    assertEquals("timer", actualCreateTimerResult.getJobType());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(((Map<String, Integer>) persistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Integer>) persistentState).get("lockOwner"));
    assertNull(actualCreateTimerResult.getExceptionStacktrace());
    assertNull(actualCreateTimerResult.getJobHandlerConfiguration());
    assertNull(actualCreateTimerResult.getJobHandlerType());
    assertNull(actualCreateTimerResult.getRepeat());
    assertNull(actualCreateTimerResult.getId());
    assertNull(actualCreateTimerResult.getLockOwner());
    assertNull(actualCreateTimerResult.getExceptionMessage());
    assertNull(actualCreateTimerResult.getExecutionId());
    assertNull(actualCreateTimerResult.getProcessDefinitionId());
    assertNull(actualCreateTimerResult.getProcessInstanceId());
    assertNull(actualCreateTimerResult.getEndDate());
    assertNull(actualCreateTimerResult.getLockExpirationTime());
    assertNull(actualCreateTimerResult.getDuedate());
    assertNull(actualCreateTimerResult.getExceptionByteArrayRef());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateTimerResult.getMaxIterations());
    assertEquals(0, actualCreateTimerResult.getRetries());
    assertEquals(1, actualCreateTimerResult.getRevision());
    assertEquals(2, actualCreateTimerResult.getRevisionNext());
    assertFalse(actualCreateTimerResult.isDeleted());
    assertFalse(actualCreateTimerResult.isInserted());
    assertFalse(actualCreateTimerResult.isUpdated());
    assertTrue(actualCreateTimerResult.isExclusive());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#createTimer(JobEntity)}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#createTimer(JobEntity)}
   */
  @Test
  public void testCreateTimer_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    TimerJobEntity actualCreateTimerResult = timerJobEntityManagerImpl.createTimer(new JobEntityImpl());

    // Assert
    Object persistentState = actualCreateTimerResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateTimerResult instanceof TimerJobEntityImpl);
    assertEquals("", actualCreateTimerResult.getTenantId());
    assertEquals("timer", actualCreateTimerResult.getJobType());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(((Map<String, Integer>) persistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Integer>) persistentState).get("lockOwner"));
    assertNull(actualCreateTimerResult.getExceptionStacktrace());
    assertNull(actualCreateTimerResult.getJobHandlerConfiguration());
    assertNull(actualCreateTimerResult.getJobHandlerType());
    assertNull(actualCreateTimerResult.getRepeat());
    assertNull(actualCreateTimerResult.getId());
    assertNull(actualCreateTimerResult.getLockOwner());
    assertNull(actualCreateTimerResult.getExceptionMessage());
    assertNull(actualCreateTimerResult.getExecutionId());
    assertNull(actualCreateTimerResult.getProcessDefinitionId());
    assertNull(actualCreateTimerResult.getProcessInstanceId());
    assertNull(actualCreateTimerResult.getEndDate());
    assertNull(actualCreateTimerResult.getLockExpirationTime());
    assertNull(actualCreateTimerResult.getDuedate());
    assertNull(actualCreateTimerResult.getExceptionByteArrayRef());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateTimerResult.getMaxIterations());
    assertEquals(0, actualCreateTimerResult.getRetries());
    assertEquals(1, actualCreateTimerResult.getRevision());
    assertEquals(2, actualCreateTimerResult.getRevisionNext());
    assertFalse(actualCreateTimerResult.isDeleted());
    assertFalse(actualCreateTimerResult.isInserted());
    assertFalse(actualCreateTimerResult.isUpdated());
    assertTrue(actualCreateTimerResult.isExclusive());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#setNewRepeat(JobEntity, int)}.
   * <ul>
   *   <li>Given {@code Repeat}.</li>
   *   <li>Then calls {@link AbstractJobEntity#getRepeat()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#setNewRepeat(JobEntity, int)}
   */
  @Test
  public void testSetNewRepeat_givenRepeat_thenCallsGetRepeat() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    JobEntity timerEntity = mock(JobEntity.class);
    doNothing().when(timerEntity).setRepeat(Mockito.<String>any());
    when(timerEntity.getRepeat()).thenReturn("Repeat");

    // Act
    timerJobEntityManagerImpl.setNewRepeat(timerEntity, 42);

    // Assert
    verify(timerEntity).getRepeat();
    verify(timerEntity).setRepeat(eq("R42"));
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#isValidTime(JobEntity, Date, VariableScope)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#isValidTime(JobEntity, Date, VariableScope)}
   */
  @Test
  public void testIsValidTime_thenReturnTrue() {
    // Arrange
    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);
    when(businessCalendarManager.getBusinessCalendar(Mockito.<String>any())).thenReturn(new DefaultBusinessCalendar());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(businessCalendarManager);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.getMaxIterations()).thenReturn(3);
    when(timerEntity.getRepeat()).thenReturn("Repeat");
    when(timerEntity.getEndDate())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");
    Date newTimerDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    boolean actualIsValidTimeResult = timerJobEntityManagerImpl.isValidTime(timerEntity, newTimerDate,
        NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(businessCalendarManager).getBusinessCalendar(eq("cycle"));
    verify(timerEntity).getEndDate();
    verify(timerEntity).getJobHandlerConfiguration();
    verify(timerEntity).getMaxIterations();
    verify(timerEntity).getRepeat();
    assertTrue(actualIsValidTimeResult);
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#calculateNextTimer(JobEntity, VariableScope)}.
   * <ul>
   *   <li>Then calls
   * {@link BusinessCalendarManager#getBusinessCalendar(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#calculateNextTimer(JobEntity, VariableScope)}
   */
  @Test
  public void testCalculateNextTimer_thenCallsGetBusinessCalendar() {
    // Arrange
    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);
    when(businessCalendarManager.getBusinessCalendar(Mockito.<String>any()))
        .thenReturn(new TimerCustomCalendarTest.CustomBusinessCalendar());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(businessCalendarManager);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.getMaxIterations()).thenReturn(3);
    when(timerEntity.getRepeat()).thenReturn("Repeat");
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");

    // Act
    timerJobEntityManagerImpl.calculateNextTimer(timerEntity, NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(businessCalendarManager).getBusinessCalendar(eq("cycle"));
    verify(timerEntity).getJobHandlerConfiguration();
    verify(timerEntity).getMaxIterations();
    verify(timerEntity).getRepeat();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#calculateRepeatValue(JobEntity)}.
   * <ul>
   *   <li>Given {@code Repeat}.</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#calculateRepeatValue(JobEntity)}
   */
  @Test
  public void testCalculateRepeatValue_givenRepeat_thenReturnMinusOne() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.getRepeat()).thenReturn("Repeat");

    // Act
    int actualCalculateRepeatValueResult = timerJobEntityManagerImpl.calculateRepeatValue(timerEntity);

    // Assert
    verify(timerEntity).getRepeat();
    assertEquals(-1, actualCalculateRepeatValueResult);
  }

  /**
   * Test
   * {@link TimerJobEntityManagerImpl#getBusinessCalendarName(String, VariableScope)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code cycle}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobEntityManagerImpl#getBusinessCalendarName(String, VariableScope)}
   */
  @Test
  public void testGetBusinessCalendarName_whenEmptyString_thenReturnCycle() {
    // Arrange, Act and Assert
    assertEquals("cycle",
        timerJobEntityManagerImpl.getBusinessCalendarName("", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#getDataManager()}.
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#getDataManager()}
   */
  @Test
  public void testGetDataManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(timerJobEntityManagerImpl.jobDataManager, timerJobEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#getDataManager()}.
   * <p>
   * Method under test: {@link TimerJobEntityManagerImpl#getDataManager()}
   */
  @Test
  public void testGetDataManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(timerJobEntityManagerImpl.jobDataManager, timerJobEntityManagerImpl.getDataManager());
  }
}
