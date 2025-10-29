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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#createAndCalculateNextTimer(JobEntity, VariableScope)}
   */
  @Test
  public void testCreateAndCalculateNextTimer() {
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
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(timerEntity.getEndDate()).thenReturn(fromResult);
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
    assertSame(fromResult, actualCreateAndCalculateNextTimerResult.getEndDate());
  }

  /**
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findTimerJobsToExecute(Page)}
   */
  @Test
  public void testFindTimerJobsToExecute() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    when(jobDataManager.findTimerJobsToExecute(Mockito.<Page>any())).thenReturn(timerJobEntityList);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<TimerJobEntity> actualFindTimerJobsToExecuteResult = timerJobEntityManagerImpl
        .findTimerJobsToExecute(new Page(1, 3));

    // Assert
    verify(jobDataManager).findTimerJobsToExecute(isA(Page.class));
    assertTrue(actualFindTimerJobsToExecuteResult.isEmpty());
    assertSame(timerJobEntityList, actualFindTimerJobsToExecuteResult);
  }

  /**
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionId(String, String)}
   */
  @Test
  public void testFindJobsByTypeAndProcessDefinitionId() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    when(timerJobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(timerJobEntityList);

    // Act
    List<TimerJobEntity> actualFindJobsByTypeAndProcessDefinitionIdResult = timerJobEntityManagerImpl
        .findJobsByTypeAndProcessDefinitionId("Job Handler Type", "42");

    // Assert
    verify(timerJobDataManager).findJobsByTypeAndProcessDefinitionId(eq("Job Handler Type"), eq("42"));
    assertTrue(actualFindJobsByTypeAndProcessDefinitionIdResult.isEmpty());
    assertSame(timerJobEntityList, actualFindJobsByTypeAndProcessDefinitionIdResult);
  }

  /**
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyNoTenantId(String, String)}
   */
  @Test
  public void testFindJobsByTypeAndProcessDefinitionKeyNoTenantId() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    when(timerJobDataManager.findJobsByTypeAndProcessDefinitionKeyNoTenantId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(timerJobEntityList);

    // Act
    List<TimerJobEntity> actualFindJobsByTypeAndProcessDefinitionKeyNoTenantIdResult = timerJobEntityManagerImpl
        .findJobsByTypeAndProcessDefinitionKeyNoTenantId("Job Handler Type", "Process Definition Key");

    // Assert
    verify(timerJobDataManager).findJobsByTypeAndProcessDefinitionKeyNoTenantId(eq("Job Handler Type"),
        eq("Process Definition Key"));
    assertTrue(actualFindJobsByTypeAndProcessDefinitionKeyNoTenantIdResult.isEmpty());
    assertSame(timerJobEntityList, actualFindJobsByTypeAndProcessDefinitionKeyNoTenantIdResult);
  }

  /**
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyAndTenantId(String, String, String)}
   */
  @Test
  public void testFindJobsByTypeAndProcessDefinitionKeyAndTenantId() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    when(timerJobDataManager.findJobsByTypeAndProcessDefinitionKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(timerJobEntityList);

    // Act
    List<TimerJobEntity> actualFindJobsByTypeAndProcessDefinitionKeyAndTenantIdResult = timerJobEntityManagerImpl
        .findJobsByTypeAndProcessDefinitionKeyAndTenantId("Job Handler Type", "Process Definition Key", "42");

    // Assert
    verify(timerJobDataManager).findJobsByTypeAndProcessDefinitionKeyAndTenantId(eq("Job Handler Type"),
        eq("Process Definition Key"), eq("42"));
    assertTrue(actualFindJobsByTypeAndProcessDefinitionKeyAndTenantIdResult.isEmpty());
    assertSame(timerJobEntityList, actualFindJobsByTypeAndProcessDefinitionKeyAndTenantIdResult);
  }

  /**
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  public void testFindJobsByExecutionId() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    when(timerJobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(timerJobEntityList);

    // Act
    List<TimerJobEntity> actualFindJobsByExecutionIdResult = timerJobEntityManagerImpl.findJobsByExecutionId("42");

    // Assert
    verify(timerJobDataManager).findJobsByExecutionId(eq("42"));
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
    assertSame(timerJobEntityList, actualFindJobsByExecutionIdResult);
  }

  /**
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByProcessInstanceId(String)}
   */
  @Test
  public void testFindJobsByProcessInstanceId() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    when(timerJobDataManager.findJobsByProcessInstanceId(Mockito.<String>any())).thenReturn(timerJobEntityList);

    // Act
    List<TimerJobEntity> actualFindJobsByProcessInstanceIdResult = timerJobEntityManagerImpl
        .findJobsByProcessInstanceId("42");

    // Assert
    verify(timerJobDataManager).findJobsByProcessInstanceId(eq("42"));
    assertTrue(actualFindJobsByProcessInstanceIdResult.isEmpty());
    assertSame(timerJobEntityList, actualFindJobsByProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobsByQueryCriteria(TimerJobQueryImpl, Page)}
   */
  @Test
  public void testFindJobsByQueryCriteria() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    ArrayList<Job> jobList = new ArrayList<>();
    when(jobDataManager.findJobsByQueryCriteria(Mockito.<TimerJobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(jobList);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);
    TimerJobQueryImpl jobQuery = new TimerJobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult = timerJobEntityManagerImpl.findJobsByQueryCriteria(jobQuery,
        new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsByQueryCriteria(isA(TimerJobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
    assertSame(jobList, actualFindJobsByQueryCriteriaResult);
  }

  /**
   * Method under test:
   * {@link TimerJobEntityManagerImpl#findJobCountByQueryCriteria(TimerJobQueryImpl)}
   */
  @Test
  public void testFindJobCountByQueryCriteria() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity2() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity3() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity4() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity5() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity6() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity7() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity8() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity9() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  public void testInsertTimerJobEntity10() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsert() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsert2() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsert3() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsert6() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsert7() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsert8() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsert9() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  public void testInsert10() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert11() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert12() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert13() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert14() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert16() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert17() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert18() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert19() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert20() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  public void testInsert21() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert2() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert3() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert4() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert5() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert6() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert7() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert8() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert9() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert10() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  public void testDoInsert11() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}
   */
  @Test
  public void testRemoveExecutionLink2() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}
   */
  @Test
  public void testRemoveExecutionLink3() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#deleteExceptionByteArrayRef(TimerJobEntity)}
   */
  @Test
  public void testDeleteExceptionByteArrayRef() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#createTimer(JobEntity)}
   */
  @Test
  public void testCreateTimer() {
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
   * Method under test: {@link TimerJobEntityManagerImpl#createTimer(JobEntity)}
   */
  @Test
  public void testCreateTimer2() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#setNewRepeat(JobEntity, int)}
   */
  @Test
  public void testSetNewRepeat() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#isValidTime(JobEntity, Date, VariableScope)}
   */
  @Test
  public void testIsValidTime() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#calculateNextTimer(JobEntity, VariableScope)}
   */
  @Test
  public void testCalculateNextTimer() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#calculateRepeatValue(JobEntity)}
   */
  @Test
  public void testCalculateRepeatValue() {
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
   * Method under test:
   * {@link TimerJobEntityManagerImpl#getBusinessCalendarName(String, VariableScope)}
   */
  @Test
  public void testGetBusinessCalendarName() {
    // Arrange, Act and Assert
    assertEquals("cycle",
        timerJobEntityManagerImpl.getBusinessCalendarName("", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
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

  /**
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
}
