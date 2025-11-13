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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.activiti.engine.test.bpmn.event.timer.TimerCustomCalendarTest.CustomBusinessCalendar;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class TimerJobEntityManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       TimerJobEntityManagerImpl#TimerJobEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       TimerJobDataManager)}
   *   <li>{@link TimerJobEntityManagerImpl#setJobDataManager(TimerJobDataManager)}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TimerJobEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, TimerJobDataManager)",
    "void TimerJobEntityManagerImpl.setJobDataManager(TimerJobDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    TimerJobEntityManagerImpl actualTimerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    MybatisTimerJobDataManager jobDataManager =
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration());
    actualTimerJobEntityManagerImpl.setJobDataManager(jobDataManager);

    // Assert
    assertSame(jobDataManager, actualTimerJobEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#createAndCalculateNextTimer(JobEntity, VariableScope)}.
   *
   * <ul>
   *   <li>Then PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#createAndCalculateNextTimer(JobEntity,
   * VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TimerJobEntity TimerJobEntityManagerImpl.createAndCalculateNextTimer(JobEntity, VariableScope)"
  })
  public void testCreateAndCalculateNextTimer_thenPersistentStateReturnMap() {
    // Arrange
    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);
    when(businessCalendarManager.getBusinessCalendar(Mockito.<String>any()))
        .thenReturn(new CustomBusinessCalendar());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(businessCalendarManager);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
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
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(timerEntity.getMaxIterations()).thenReturn(3);
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");
    when(timerEntity.getRepeat()).thenReturn("Repeat");

    // Act
    TimerJobEntity actualCreateAndCalculateNextTimerResult =
        timerJobEntityManagerImpl.createAndCalculateNextTimer(
            timerEntity, NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(businessCalendarManager, atLeast(1)).getBusinessCalendar("cycle");
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
    assertEquals(
        "Job Handler Configuration",
        actualCreateAndCalculateNextTimerResult.getJobHandlerConfiguration());
    assertEquals("Job Handler Type", actualCreateAndCalculateNextTimerResult.getJobHandlerType());
    assertEquals("Repeat", actualCreateAndCalculateNextTimerResult.getRepeat());
    assertEquals("timer", actualCreateAndCalculateNextTimerResult.getJobType());
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
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertFalse(actualCreateAndCalculateNextTimerResult.isDeleted());
    assertFalse(actualCreateAndCalculateNextTimerResult.isInserted());
    assertFalse(actualCreateAndCalculateNextTimerResult.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertTrue(actualCreateAndCalculateNextTimerResult.isExclusive());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findTimerJobsToExecute(Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#findTimerJobsToExecute(Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TimerJobEntityManagerImpl.findTimerJobsToExecute(Page)"})
  public void testFindTimerJobsToExecute_thenReturnEmpty() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findTimerJobsToExecute(Mockito.<Page>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<TimerJobEntity> actualFindTimerJobsToExecuteResult =
        timerJobEntityManagerImpl.findTimerJobsToExecute(new Page(1, 3));

    // Assert
    verify(jobDataManager).findTimerJobsToExecute(isA(Page.class));
    assertTrue(actualFindTimerJobsToExecuteResult.isEmpty());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionId(String, String)}.
   *
   * <p>Method under test: {@link
   * TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionId(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List TimerJobEntityManagerImpl.findJobsByTypeAndProcessDefinitionId(String, String)"
  })
  public void testFindJobsByTypeAndProcessDefinitionId() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<TimerJobEntity> actualFindJobsByTypeAndProcessDefinitionIdResult =
        timerJobEntityManagerImpl.findJobsByTypeAndProcessDefinitionId("Job Handler Type", "42");

    // Assert
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId("Job Handler Type", "42");
    assertTrue(actualFindJobsByTypeAndProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyNoTenantId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyNoTenantId(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List TimerJobEntityManagerImpl.findJobsByTypeAndProcessDefinitionKeyNoTenantId(String, String)"
  })
  public void testFindJobsByTypeAndProcessDefinitionKeyNoTenantId() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByTypeAndProcessDefinitionKeyNoTenantId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<TimerJobEntity> actualFindJobsByTypeAndProcessDefinitionKeyNoTenantIdResult =
        timerJobEntityManagerImpl.findJobsByTypeAndProcessDefinitionKeyNoTenantId(
            "Job Handler Type", "Process Definition Key");

    // Assert
    verify(jobDataManager)
        .findJobsByTypeAndProcessDefinitionKeyNoTenantId(
            "Job Handler Type", "Process Definition Key");
    assertTrue(actualFindJobsByTypeAndProcessDefinitionKeyNoTenantIdResult.isEmpty());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyAndTenantId(String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * TimerJobEntityManagerImpl#findJobsByTypeAndProcessDefinitionKeyAndTenantId(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List TimerJobEntityManagerImpl.findJobsByTypeAndProcessDefinitionKeyAndTenantId(String, String, String)"
  })
  public void testFindJobsByTypeAndProcessDefinitionKeyAndTenantId() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByTypeAndProcessDefinitionKeyAndTenantId(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<TimerJobEntity> actualFindJobsByTypeAndProcessDefinitionKeyAndTenantIdResult =
        timerJobEntityManagerImpl.findJobsByTypeAndProcessDefinitionKeyAndTenantId(
            "Job Handler Type", "Process Definition Key", "42");

    // Assert
    verify(jobDataManager)
        .findJobsByTypeAndProcessDefinitionKeyAndTenantId(
            "Job Handler Type", "Process Definition Key", "42");
    assertTrue(actualFindJobsByTypeAndProcessDefinitionKeyAndTenantIdResult.isEmpty());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findJobsByExecutionId(String)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#findJobsByExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TimerJobEntityManagerImpl.findJobsByExecutionId(String)"})
  public void testFindJobsByExecutionId() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<TimerJobEntity> actualFindJobsByExecutionIdResult =
        timerJobEntityManagerImpl.findJobsByExecutionId("42");

    // Assert
    verify(jobDataManager).findJobsByExecutionId("42");
    assertTrue(actualFindJobsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findJobsByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#findJobsByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TimerJobEntityManagerImpl.findJobsByProcessInstanceId(String)"})
  public void testFindJobsByProcessInstanceId() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    List<TimerJobEntity> actualFindJobsByProcessInstanceIdResult =
        timerJobEntityManagerImpl.findJobsByProcessInstanceId("42");

    // Assert
    verify(jobDataManager).findJobsByProcessInstanceId("42");
    assertTrue(actualFindJobsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findJobsByQueryCriteria(TimerJobQueryImpl, Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TimerJobEntityManagerImpl#findJobsByQueryCriteria(TimerJobQueryImpl, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List TimerJobEntityManagerImpl.findJobsByQueryCriteria(TimerJobQueryImpl, Page)"
  })
  public void testFindJobsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByQueryCriteria(
            Mockito.<TimerJobQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);
    TimerJobQueryImpl jobQuery = new TimerJobQueryImpl();

    // Act
    List<Job> actualFindJobsByQueryCriteriaResult =
        timerJobEntityManagerImpl.findJobsByQueryCriteria(jobQuery, new Page(1, 3));

    // Assert
    verify(jobDataManager).findJobsByQueryCriteria(isA(TimerJobQueryImpl.class), isA(Page.class));
    assertTrue(actualFindJobsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#findJobCountByQueryCriteria(TimerJobQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * TimerJobEntityManagerImpl#findJobCountByQueryCriteria(TimerJobQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long TimerJobEntityManagerImpl.findJobCountByQueryCriteria(TimerJobQueryImpl)"
  })
  public void testFindJobCountByQueryCriteria_thenReturnThree() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobCountByQueryCriteria(Mockito.<TimerJobQueryImpl>any()))
        .thenReturn(3L);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    long actualFindJobCountByQueryCriteriaResult =
        timerJobEntityManagerImpl.findJobCountByQueryCriteria(new TimerJobQueryImpl());

    // Assert
    verify(jobDataManager).findJobCountByQueryCriteria(isA(TimerJobQueryImpl.class));
    assertEquals(3L, actualFindJobCountByQueryCriteriaResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#updateJobTenantIdForDeployment(String, String)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#updateJobTenantIdForDeployment(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TimerJobEntityManagerImpl.updateJobTenantIdForDeployment(String, String)"
  })
  public void testUpdateJobTenantIdForDeployment() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing()
        .when(jobDataManager)
        .updateJobTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    // Act
    timerJobEntityManagerImpl.updateJobTenantIdForDeployment("42", "42");

    // Assert
    verify(jobDataManager).updateJobTenantIdForDeployment("42", "42");
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.insertTimerJobEntity(TimerJobEntity)"})
  public void testInsertTimerJobEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    boolean actualInsertTimerJobEntityResult =
        timerJobEntityManagerImpl.insertTimerJobEntity(new TimerJobEntityImpl());

    // Assert
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.insertTimerJobEntity(TimerJobEntity)"})
  public void testInsertTimerJobEntity2() {
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity timerJobEntity = mock(TimerJobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertTimerJobEntityResult =
        timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.insertTimerJobEntity(TimerJobEntity)"})
  public void testInsertTimerJobEntity3() {
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity timerJobEntity = mock(TimerJobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertTimerJobEntityResult =
        timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.insertTimerJobEntity(TimerJobEntity)"})
  public void testInsertTimerJobEntity4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity timerJobEntity = mock(TimerJobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertTimerJobEntityResult =
        timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.insertTimerJobEntity(TimerJobEntity)"})
  public void testInsertTimerJobEntity5() {
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity timerJobEntity = mock(TimerJobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertTimerJobEntityResult =
        timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.insertTimerJobEntity(TimerJobEntity)"})
  public void testInsertTimerJobEntity6() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity timerJobEntity = mock(TimerJobEntity.class);
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertTimerJobEntityResult =
        timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.insertTimerJobEntity(TimerJobEntity)"})
  public void testInsertTimerJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity timerJobEntity = mock(TimerJobEntity.class);
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertTimerJobEntityResult =
        timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.insertTimerJobEntity(TimerJobEntity)"})
  public void testInsertTimerJobEntity_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity timerJobEntity = mock(TimerJobEntity.class);
    when(timerJobEntity.getProcessDefinitionId()).thenReturn("42");
    when(timerJobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(timerJobEntity).setTenantId(Mockito.<String>any());
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertTimerJobEntityResult =
        timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(timerJobEntity).setTenantId("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    verify(timerJobEntity, atLeast(1)).getProcessDefinitionId();
    verify(timerJobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insertTimerJobEntity(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.insertTimerJobEntity(TimerJobEntity)"})
  public void testInsertTimerJobEntity_thenReturnFalse() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, mock(TimerJobDataManager.class));

    TimerJobEntity timerJobEntity = mock(TimerJobEntity.class);
    when(timerJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualInsertTimerJobEntityResult =
        timerJobEntityManagerImpl.insertTimerJobEntity(timerJobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById("42");
    verify(timerJobEntity, atLeast(1)).getExecutionId();
    assertFalse(actualInsertTimerJobEntityResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity)"})
  public void testInsertWithTimerJobEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    timerJobEntityManagerImpl.insert(new TimerJobEntityImpl());

    // Assert
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity)"})
  public void testInsertWithTimerJobEntity2() {
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity)"})
  public void testInsertWithTimerJobEntity3() {
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity)"})
  public void testInsertWithTimerJobEntity4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity)"})
  public void testInsertWithTimerJobEntity5() {
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity)"})
  public void testInsertWithTimerJobEntity6() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    timerJobEntityManagerImpl.insert(new TimerJobEntityImpl(), true);

    // Assert
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean2() {
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean3() {
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean5() {
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean6() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean7() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean8() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean_givenExecutionDataManagerFindByIdReturnNull() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, mock(TimerJobDataManager.class));

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById("42");
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)} with {@code
   * TimerJobEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity, boolean)"})
  public void testInsertWithTimerJobEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity)"})
  public void testInsertWithTimerJobEntity_givenExecutionDataManagerFindByIdReturnNull() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, mock(TimerJobDataManager.class));

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById("42");
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity)"})
  public void testInsertWithTimerJobEntity_givenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#insert(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.insert(TimerJobEntity)"})
  public void testInsertWithTimerJobEntity_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    timerJobEntityManagerImpl.insert(jobEntity);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
  public void testDoInsert() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    boolean actualDoInsertResult =
        timerJobEntityManagerImpl.doInsert(new TimerJobEntityImpl(), true);

    // Assert
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionEntityManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
  public void testDoInsert_givenActivitiEventDispatcherIsEnabledReturnFalse_thenCallsIsEnabled() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@link ExecutionEntityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
  public void testDoInsert_givenExecutionDataManagerFindByIdReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
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
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, mock(TimerJobDataManager.class));

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById("42");
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertFalse(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
  public void testDoInsert_givenExecutionEntityImplGetTenantIdReturnNull_thenCallsIsEnabled() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn(null);
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityManager} {@link ExecutionEntityManager#findById(String)}
   *       return {@link ExecutionEntityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
  public void testDoInsert_givenExecutionEntityManagerFindByIdReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityManager).findById("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <ul>
   *   <li>Given {@link PerformanceSettings} (default constructor) EnableExecutionRelationshipCounts
   *       is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("");
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#doInsert(TimerJobEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TimerJobEntityManagerImpl.doInsert(TimerJobEntity, boolean)"})
  public void testDoInsert_thenCallsDispatchEvent() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getTimerJobCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setTimerJobCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getTenantId()).thenReturn("42");
    when(executionEntityImpl.getTimerJobs()).thenReturn(new ArrayList<>());

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

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getProcessDefinitionId()).thenReturn("42");
    when(jobEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(jobEntity).setTenantId(Mockito.<String>any());
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualDoInsertResult = timerJobEntityManagerImpl.doInsert(jobEntity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobEntity).setTenantId("42");
    verify(executionEntityImpl, atLeast(1)).getTenantId();
    verify(executionEntityImpl).getTimerJobCount();
    verify(executionEntityImpl).getTimerJobs();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setTimerJobCount(4);
    verify(executionDataManager).findById("42");
    verify(jobDataManager).insert(isA(TimerJobEntity.class));
    verify(jobEntity, atLeast(1)).getExecutionId();
    verify(jobEntity, atLeast(1)).getProcessDefinitionId();
    verify(jobEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualDoInsertResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#delete(TimerJobEntity)} with {@code TimerJobEntity}.
   *
   * <ul>
   *   <li>Then calls {@link TimerJobDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#delete(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.delete(TimerJobEntity)"})
  public void testDeleteWithTimerJobEntity_thenCallsDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<TimerJobEntity>any());

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    // Act
    timerJobEntityManagerImpl.delete(new TimerJobEntityImpl());

    // Assert
    verify(jobDataManager).delete(isA(TimerJobEntity.class));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.removeExecutionLink(TimerJobEntity)"})
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
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
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
    timerJobEntityManagerImpl.removeExecutionLink(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById("Job Entity");
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.removeExecutionLink(TimerJobEntity)"})
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
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
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
    timerJobEntityManagerImpl.removeExecutionLink(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionDataManager).findById("Job Entity");
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#removeExecutionLink(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.removeExecutionLink(TimerJobEntity)"})
  public void testRemoveExecutionLink_thenCallsFindById() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    TimerJobEntityImpl jobEntity = new TimerJobEntityImpl();
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
    timerJobEntityManagerImpl.removeExecutionLink(jobEntity);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionEntityManager).findById("Job Entity");
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#deleteExceptionByteArrayRef(TimerJobEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link TimerJobEntity#getExceptionByteArrayRef()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TimerJobEntityManagerImpl#deleteExceptionByteArrayRef(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.deleteExceptionByteArrayRef(TimerJobEntity)"})
  public void testDeleteExceptionByteArrayRef_thenCallsGetExceptionByteArrayRef() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getExceptionByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    timerJobEntityManagerImpl.deleteExceptionByteArrayRef(jobEntity);

    // Assert
    verify(jobEntity).getExceptionByteArrayRef();
    verify(byteArrayRef).delete();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#createTimer(JobEntity)}.
   *
   * <ul>
   *   <li>Then PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#createTimer(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobEntity TimerJobEntityManagerImpl.createTimer(JobEntity)"})
  public void testCreateTimer_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    TimerJobEntity actualCreateTimerResult =
        timerJobEntityManagerImpl.createTimer(new JobEntityImpl());

    // Assert
    Object persistentState = actualCreateTimerResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateTimerResult instanceof TimerJobEntityImpl);
    assertEquals("", actualCreateTimerResult.getTenantId());
    assertEquals("timer", actualCreateTimerResult.getJobType());
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
    assertEquals(0, actualCreateTimerResult.getMaxIterations());
    assertEquals(0, actualCreateTimerResult.getRetries());
    assertEquals(1, actualCreateTimerResult.getRevision());
    assertEquals(2, actualCreateTimerResult.getRevisionNext());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertFalse(actualCreateTimerResult.isDeleted());
    assertFalse(actualCreateTimerResult.isInserted());
    assertFalse(actualCreateTimerResult.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(actualCreateTimerResult.isExclusive());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#setNewRepeat(JobEntity, int)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>Then {@link JobEntityImpl} (default constructor) Repeat is {@code R42}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#setNewRepeat(JobEntity, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.setNewRepeat(JobEntity, int)"})
  public void testSetNewRepeat_givenEmptyString_thenJobEntityImplRepeatIsR42() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl timerEntity = new JobEntityImpl();
    timerEntity.setDeleted(true);
    timerEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setExceptionMessage("An error occurred");
    timerEntity.setExclusive(true);
    timerEntity.setExecutionId("42");
    timerEntity.setId("42");
    timerEntity.setInserted(true);
    timerEntity.setJobHandlerConfiguration("Job Handler Configuration");
    timerEntity.setJobHandlerType("Job Handler Type");
    timerEntity.setJobType("Job Type");
    timerEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setLockOwner("Claimed By");
    timerEntity.setMaxIterations(3);
    timerEntity.setProcessDefinitionId("42");
    timerEntity.setProcessInstanceId("42");
    timerEntity.setRetries(1);
    timerEntity.setRevision(1);
    timerEntity.setTenantId("42");
    timerEntity.setUpdated(true);
    timerEntity.setRepeat("");

    // Act
    timerJobEntityManagerImpl.setNewRepeat(timerEntity, 42);

    // Assert
    assertEquals("R42", timerEntity.getRepeat());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#setNewRepeat(JobEntity, int)}.
   *
   * <ul>
   *   <li>Given {@code foo/bar}.
   *   <li>Then {@link JobEntityImpl} (default constructor) Repeat is {@code R42/bar}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#setNewRepeat(JobEntity, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerJobEntityManagerImpl.setNewRepeat(JobEntity, int)"})
  public void testSetNewRepeat_givenFooBar_thenJobEntityImplRepeatIsR42Bar() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl timerEntity = new JobEntityImpl();
    timerEntity.setDeleted(true);
    timerEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setExceptionMessage("An error occurred");
    timerEntity.setExclusive(true);
    timerEntity.setExecutionId("42");
    timerEntity.setId("42");
    timerEntity.setInserted(true);
    timerEntity.setJobHandlerConfiguration("Job Handler Configuration");
    timerEntity.setJobHandlerType("Job Handler Type");
    timerEntity.setJobType("Job Type");
    timerEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setLockOwner("Claimed By");
    timerEntity.setMaxIterations(3);
    timerEntity.setProcessDefinitionId("42");
    timerEntity.setProcessInstanceId("42");
    timerEntity.setRetries(1);
    timerEntity.setRevision(1);
    timerEntity.setTenantId("42");
    timerEntity.setUpdated(true);
    timerEntity.setRepeat("foo/bar");

    // Act
    timerJobEntityManagerImpl.setNewRepeat(timerEntity, 42);

    // Assert
    assertEquals("R42/bar", timerEntity.getRepeat());
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#isValidTime(JobEntity, Date, VariableScope)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#isValidTime(JobEntity, Date,
   * VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TimerJobEntityManagerImpl.isValidTime(JobEntity, Date, VariableScope)"
  })
  public void testIsValidTime_thenReturnTrue() {
    // Arrange
    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);
    when(businessCalendarManager.getBusinessCalendar(Mockito.<String>any()))
        .thenReturn(new DefaultBusinessCalendar());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(businessCalendarManager);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.getMaxIterations()).thenReturn(3);
    when(timerEntity.getRepeat()).thenReturn("Repeat");
    when(timerEntity.getEndDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");

    // Act
    boolean actualIsValidTimeResult =
        timerJobEntityManagerImpl.isValidTime(
            timerEntity,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()),
            NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(businessCalendarManager).getBusinessCalendar("cycle");
    verify(timerEntity).getEndDate();
    verify(timerEntity).getJobHandlerConfiguration();
    verify(timerEntity).getMaxIterations();
    verify(timerEntity).getRepeat();
    assertTrue(actualIsValidTimeResult);
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#calculateNextTimer(JobEntity, VariableScope)}.
   *
   * <ul>
   *   <li>Then calls {@link BusinessCalendarManager#getBusinessCalendar(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#calculateNextTimer(JobEntity,
   * VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date TimerJobEntityManagerImpl.calculateNextTimer(JobEntity, VariableScope)"})
  public void testCalculateNextTimer_thenCallsGetBusinessCalendar() {
    // Arrange
    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);
    when(businessCalendarManager.getBusinessCalendar(Mockito.<String>any()))
        .thenReturn(new CustomBusinessCalendar());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(businessCalendarManager);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.getMaxIterations()).thenReturn(3);
    when(timerEntity.getRepeat()).thenReturn("Repeat");
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");

    // Act
    timerJobEntityManagerImpl.calculateNextTimer(
        timerEntity, NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(businessCalendarManager).getBusinessCalendar("cycle");
    verify(timerEntity).getJobHandlerConfiguration();
    verify(timerEntity).getMaxIterations();
    verify(timerEntity).getRepeat();
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#calculateRepeatValue(JobEntity)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link JobEntityImpl} (default constructor) Repeat is empty string.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#calculateRepeatValue(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int TimerJobEntityManagerImpl.calculateRepeatValue(JobEntity)"})
  public void testCalculateRepeatValue_givenEmptyString_whenJobEntityImplRepeatIsEmptyString() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl timerEntity = new JobEntityImpl();
    timerEntity.setDeleted(true);
    timerEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setExceptionMessage("An error occurred");
    timerEntity.setExclusive(true);
    timerEntity.setExecutionId("42");
    timerEntity.setId("42");
    timerEntity.setInserted(true);
    timerEntity.setJobHandlerConfiguration("Job Handler Configuration");
    timerEntity.setJobHandlerType("Job Handler Type");
    timerEntity.setJobType("Job Type");
    timerEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setLockOwner("Claimed By");
    timerEntity.setMaxIterations(3);
    timerEntity.setProcessDefinitionId("42");
    timerEntity.setProcessInstanceId("42");
    timerEntity.setRetries(1);
    timerEntity.setRevision(1);
    timerEntity.setTenantId("42");
    timerEntity.setUpdated(true);
    timerEntity.setRepeat("");

    // Act and Assert
    assertEquals(-1, timerJobEntityManagerImpl.calculateRepeatValue(timerEntity));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#calculateRepeatValue(JobEntity)}.
   *
   * <ul>
   *   <li>Given {@code foo/bar}.
   *   <li>When {@link JobEntityImpl} (default constructor) Repeat is {@code foo/bar}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#calculateRepeatValue(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int TimerJobEntityManagerImpl.calculateRepeatValue(JobEntity)"})
  public void testCalculateRepeatValue_givenFooBar_whenJobEntityImplRepeatIsFooBar() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    JobEntityImpl timerEntity = new JobEntityImpl();
    timerEntity.setDeleted(true);
    timerEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setExceptionMessage("An error occurred");
    timerEntity.setExclusive(true);
    timerEntity.setExecutionId("42");
    timerEntity.setId("42");
    timerEntity.setInserted(true);
    timerEntity.setJobHandlerConfiguration("Job Handler Configuration");
    timerEntity.setJobHandlerType("Job Handler Type");
    timerEntity.setJobType("Job Type");
    timerEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setLockOwner("Claimed By");
    timerEntity.setMaxIterations(3);
    timerEntity.setProcessDefinitionId("42");
    timerEntity.setProcessInstanceId("42");
    timerEntity.setRetries(1);
    timerEntity.setRevision(1);
    timerEntity.setTenantId("42");
    timerEntity.setUpdated(true);
    timerEntity.setRepeat("foo/bar");

    // Act and Assert
    assertEquals(-1, timerJobEntityManagerImpl.calculateRepeatValue(timerEntity));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#getBusinessCalendarName(String, VariableScope)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code cycle}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#getBusinessCalendarName(String,
   * VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String TimerJobEntityManagerImpl.getBusinessCalendarName(String, VariableScope)"
  })
  public void testGetBusinessCalendarName_whenEmptyString_thenReturnCycle() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertEquals(
        "cycle",
        timerJobEntityManagerImpl.getBusinessCalendarName(
            "", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#getBusinessCalendarName(String, VariableScope)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code cycle}.
   * </ul>
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#getBusinessCalendarName(String,
   * VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String TimerJobEntityManagerImpl.getBusinessCalendarName(String, VariableScope)"
  })
  public void testGetBusinessCalendarName_whenNull_thenReturnCycle() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertEquals(
        "cycle",
        timerJobEntityManagerImpl.getBusinessCalendarName(
            null, NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link TimerJobEntityManagerImpl#getDataManager()}.
   *
   * <p>Method under test: {@link TimerJobEntityManagerImpl#getDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobDataManager TimerJobEntityManagerImpl.getDataManager()"})
  public void testGetDataManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act
    TimerJobDataManager actualDataManager = timerJobEntityManagerImpl.getDataManager();

    // Assert
    assertSame(timerJobEntityManagerImpl.jobDataManager, actualDataManager);
  }
}
