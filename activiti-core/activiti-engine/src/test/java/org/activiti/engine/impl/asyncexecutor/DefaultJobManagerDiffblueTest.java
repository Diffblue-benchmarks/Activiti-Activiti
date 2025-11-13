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
package org.activiti.engine.impl.asyncexecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
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
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.calendar.BusinessCalendarManager;
import org.activiti.engine.impl.calendar.DefaultBusinessCalendar;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.jobexecutor.JobHandler;
import org.activiti.engine.impl.persistence.entity.AbstractJobEntity;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntity;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.impl.persistence.entity.JobEntityManager;
import org.activiti.engine.impl.persistence.entity.JobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntity;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntity;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisSuspendedJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DefaultJobManagerDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DefaultJobManager#DefaultJobManager()}
   *   <li>{@link DefaultJobManager#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link DefaultJobManager#getProcessEngineConfiguration()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultJobManager.<init>()",
    "void DefaultJobManager.<init>(ProcessEngineConfigurationImpl)",
    "ProcessEngineConfigurationImpl DefaultJobManager.getProcessEngineConfiguration()",
    "void DefaultJobManager.setProcessEngineConfiguration(ProcessEngineConfigurationImpl)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    DefaultJobManager actualDefaultJobManager = new DefaultJobManager();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    actualDefaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Assert
    assertSame(processEngineConfiguration, actualDefaultJobManager.getProcessEngineConfiguration());
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DefaultJobManager#DefaultJobManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link DefaultJobManager#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link DefaultJobManager#getProcessEngineConfiguration()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultJobManager.<init>()",
    "void DefaultJobManager.<init>(ProcessEngineConfigurationImpl)",
    "ProcessEngineConfigurationImpl DefaultJobManager.getProcessEngineConfiguration()",
    "void DefaultJobManager.setProcessEngineConfiguration(ProcessEngineConfigurationImpl)"
  })
  public void testGettersAndSetters_whenJtaProcessEngineConfiguration() {
    // Arrange and Act
    DefaultJobManager actualDefaultJobManager =
        new DefaultJobManager(new JtaProcessEngineConfiguration());
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    actualDefaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Assert
    assertSame(processEngineConfiguration, actualDefaultJobManager.getProcessEngineConfiguration());
  }

  /**
   * Test {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}.
   *
   * <p>Method under test: {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JobEntity DefaultJobManager.createAsyncJob(ExecutionEntity, boolean)"})
  public void testCreateAsyncJob() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setJobEntityManager(jobEntityManager);
    processEngineConfiguration.setAsyncExecutor(new DefaultAsyncJobExecutor());

    DefaultJobManager defaultJobManager =
        new DefaultJobManager(new JtaProcessEngineConfiguration());
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTenantId(null);

    // Act
    JobEntity actualCreateAsyncJobResult = defaultJobManager.createAsyncJob(execution, true);

    // Assert
    Object persistentState = actualCreateAsyncJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateAsyncJobResult instanceof JobEntityImpl);
    assertEquals("", actualCreateAsyncJobResult.getTenantId());
    assertEquals("async-continuation", actualCreateAsyncJobResult.getJobHandlerType());
    assertEquals("message", actualCreateAsyncJobResult.getJobType());
    assertNull(actualCreateAsyncJobResult.getExceptionStacktrace());
    assertNull(actualCreateAsyncJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateAsyncJobResult.getRepeat());
    assertNull(actualCreateAsyncJobResult.getId());
    assertNull(actualCreateAsyncJobResult.getLockOwner());
    assertNull(actualCreateAsyncJobResult.getExceptionMessage());
    assertNull(actualCreateAsyncJobResult.getExecutionId());
    assertNull(actualCreateAsyncJobResult.getProcessDefinitionId());
    assertNull(actualCreateAsyncJobResult.getProcessInstanceId());
    assertNull(actualCreateAsyncJobResult.getEndDate());
    assertNull(actualCreateAsyncJobResult.getLockExpirationTime());
    assertNull(actualCreateAsyncJobResult.getDuedate());
    assertNull(actualCreateAsyncJobResult.getExceptionByteArrayRef());
    assertEquals(0, actualCreateAsyncJobResult.getMaxIterations());
    assertEquals(1, actualCreateAsyncJobResult.getRevision());
    assertEquals(2, actualCreateAsyncJobResult.getRevisionNext());
    assertEquals(3, actualCreateAsyncJobResult.getRetries());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertFalse(actualCreateAsyncJobResult.isDeleted());
    assertFalse(actualCreateAsyncJobResult.isInserted());
    assertFalse(actualCreateAsyncJobResult.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(actualCreateAsyncJobResult.isExclusive());
  }

  /**
   * Test {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}.
   *
   * <ul>
   *   <li>Then PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JobEntity DefaultJobManager.createAsyncJob(ExecutionEntity, boolean)"})
  public void testCreateAsyncJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setJobEntityManager(jobEntityManager);
    processEngineConfiguration.setAsyncExecutor(new DefaultAsyncJobExecutor());

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualCreateAsyncJobResult =
        defaultJobManager.createAsyncJob(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    Object persistentState = actualCreateAsyncJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateAsyncJobResult instanceof JobEntityImpl);
    assertEquals("", actualCreateAsyncJobResult.getTenantId());
    assertEquals("async-continuation", actualCreateAsyncJobResult.getJobHandlerType());
    assertEquals("message", actualCreateAsyncJobResult.getJobType());
    assertNull(actualCreateAsyncJobResult.getExceptionStacktrace());
    assertNull(actualCreateAsyncJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateAsyncJobResult.getRepeat());
    assertNull(actualCreateAsyncJobResult.getId());
    assertNull(actualCreateAsyncJobResult.getLockOwner());
    assertNull(actualCreateAsyncJobResult.getExceptionMessage());
    assertNull(actualCreateAsyncJobResult.getExecutionId());
    assertNull(actualCreateAsyncJobResult.getProcessDefinitionId());
    assertNull(actualCreateAsyncJobResult.getProcessInstanceId());
    assertNull(actualCreateAsyncJobResult.getEndDate());
    assertNull(actualCreateAsyncJobResult.getLockExpirationTime());
    assertNull(actualCreateAsyncJobResult.getDuedate());
    assertNull(actualCreateAsyncJobResult.getExceptionByteArrayRef());
    assertEquals(0, actualCreateAsyncJobResult.getMaxIterations());
    assertEquals(1, actualCreateAsyncJobResult.getRevision());
    assertEquals(2, actualCreateAsyncJobResult.getRevisionNext());
    assertEquals(3, actualCreateAsyncJobResult.getRetries());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertFalse(actualCreateAsyncJobResult.isDeleted());
    assertFalse(actualCreateAsyncJobResult.isInserted());
    assertFalse(actualCreateAsyncJobResult.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(actualCreateAsyncJobResult.isExclusive());
  }

  /**
   * Test {@link DefaultJobManager#scheduleAsyncJob(JobEntity)}.
   *
   * <p>Method under test: {@link DefaultJobManager#scheduleAsyncJob(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.scheduleAsyncJob(JobEntity)"})
  public void testScheduleAsyncJob() {
    // Arrange
    JobEntityManager jobEntityManager = mock(JobEntityManager.class);
    doNothing().when(jobEntityManager).insert(Mockito.<JobEntity>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultJobManager.scheduleAsyncJob(new JobEntityImpl());

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getJobEntityManager();
    verify(jobEntityManager).insert(isA(JobEntity.class));
  }

  /**
   * Test {@link DefaultJobManager#scheduleAsyncJob(JobEntity)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#scheduleAsyncJob(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.scheduleAsyncJob(JobEntity)"})
  public void testScheduleAsyncJob_thenThrowActivitiException() {
    // Arrange
    JobEntityManager jobEntityManager = mock(JobEntityManager.class);
    doNothing().when(jobEntityManager).insert(Mockito.<JobEntity>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor())
        .thenThrow(new ActivitiException("An error occurred"));
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> defaultJobManager.scheduleAsyncJob(new JobEntityImpl()));
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getJobEntityManager();
    verify(jobEntityManager).insert(isA(JobEntity.class));
  }

  /**
   * Test {@link DefaultJobManager#triggerExecutorIfNeeded(JobEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getAsyncExecutor()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#triggerExecutorIfNeeded(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.triggerExecutorIfNeeded(JobEntity)"})
  public void testTriggerExecutorIfNeeded_thenCallsGetAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultJobManager.triggerExecutorIfNeeded(new JobEntityImpl());

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
  }

  /**
   * Test {@link DefaultJobManager#scheduleTimerJob(TimerJobEntity)}.
   *
   * <ul>
   *   <li>Given {@link DefaultJobManager#DefaultJobManager()}.
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#scheduleTimerJob(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.scheduleTimerJob(TimerJobEntity)"})
  public void testScheduleTimerJob_givenDefaultJobManager_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> new DefaultJobManager().scheduleTimerJob(null));
  }

  /**
   * Test {@link DefaultJobManager#moveTimerJobToExecutableJob(TimerJobEntity)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#moveTimerJobToExecutableJob(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JobEntity DefaultJobManager.moveTimerJobToExecutableJob(TimerJobEntity)"})
  public void testMoveTimerJobToExecutableJob_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> new DefaultJobManager().moveTimerJobToExecutableJob(null));
  }

  /**
   * Test {@link DefaultJobManager#activateSuspendedJob(SuspendedJobEntity)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#activateSuspendedJob(SuspendedJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractJobEntity DefaultJobManager.activateSuspendedJob(SuspendedJobEntity)"
  })
  public void testActivateSuspendedJob_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setJobEntityManager(jobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    SuspendedJobEntity job = mock(SuspendedJobEntity.class);
    when(job.getJobType()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultJobManager.activateSuspendedJob(job));
    verify(job).getJobType();
  }

  /**
   * Test {@link DefaultJobManager#moveDeadLetterJobToExecutableJob(DeadLetterJobEntity, int)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultJobManager#moveDeadLetterJobToExecutableJob(DeadLetterJobEntity, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JobEntity DefaultJobManager.moveDeadLetterJobToExecutableJob(DeadLetterJobEntity, int)"
  })
  public void testMoveDeadLetterJobToExecutableJob_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DefaultJobManager().moveDeadLetterJobToExecutableJob(null, 1));
  }

  /**
   * Test {@link DefaultJobManager#execute(Job)}.
   *
   * <ul>
   *   <li>Given {@code Job Type}.
   *   <li>When {@link JobEntityImpl} (default constructor) JobType is {@code Job Type}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#execute(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.execute(Job)"})
  public void testExecute_givenJobType_whenJobEntityImplJobTypeIsJobType_thenDoesNotThrow() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobHandlers(new HashMap<>());
    DefaultJobManager defaultJobManager = new DefaultJobManager(processEngineConfiguration);

    JobEntityImpl job = new JobEntityImpl();
    job.setDeleted(true);
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setInserted(true);
    job.setJobHandlerConfiguration("Job Handler Configuration");
    job.setJobType("Job Type");
    job.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("Claimed By");
    job.setMaxIterations(3);
    job.setProcessInstanceId("42");
    job.setRetries(1);
    job.setRevision(1);
    job.setTenantId("42");
    job.setUpdated(true);
    job.setId("Job");
    job.setExecutionId("Job");
    job.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setRepeat("Job");
    job.setJobHandlerType("timer-start-event");
    job.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setProcessDefinitionId("Job");

    // Act and Assert
    defaultJobManager.execute(job);
  }

  /**
   * Test {@link DefaultJobManager#execute(Job)}.
   *
   * <ul>
   *   <li>When {@link DeadLetterJobEntityImpl} (default constructor).
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#execute(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.execute(Job)"})
  public void testExecute_whenDeadLetterJobEntityImpl_thenThrowActivitiException() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> defaultJobManager.execute(new DeadLetterJobEntityImpl()));
  }

  /**
   * Test {@link DefaultJobManager#unacquire(Job)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#unacquire(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.unacquire(Job)"})
  public void testUnacquire_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setJobEntityManager(jobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.getId()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultJobManager.unacquire(job));
    verify(job).getId();
  }

  /**
   * Test {@link DefaultJobManager#executeTimerJob(JobEntity)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#executeTimerJob(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.executeTimerJob(JobEntity)"})
  public void testExecuteTimerJob_thenThrowActivitiException() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);
    when(businessCalendarManager.getBusinessCalendar(Mockito.<String>any()))
        .thenReturn(new DefaultBusinessCalendar());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobHandlers())
        .thenThrow(new ActivitiException("An error occurred"));
    when(processEngineConfiguration.getBusinessCalendarManager())
        .thenReturn(businessCalendarManager);
    when(processEngineConfiguration.setJobHandlers(Mockito.<Map<String, JobHandler>>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getTimerJobEntityManager())
        .thenReturn(timerJobEntityManagerImpl);
    processEngineConfiguration.setJobHandlers(new HashMap<>());
    DefaultJobManager defaultJobManager = new DefaultJobManager(processEngineConfiguration);

    JobEntityImpl timerEntity = new JobEntityImpl();
    timerEntity.setDeleted(true);
    timerEntity.setExceptionMessage("An error occurred");
    timerEntity.setExclusive(true);
    timerEntity.setId("42");
    timerEntity.setInserted(true);
    timerEntity.setJobHandlerConfiguration("Job Handler Configuration");
    timerEntity.setJobType("Job Type");
    timerEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setLockOwner("Claimed By");
    timerEntity.setMaxIterations(3);
    timerEntity.setProcessInstanceId("42");
    timerEntity.setRetries(1);
    timerEntity.setRevision(1);
    timerEntity.setTenantId("42");
    timerEntity.setUpdated(true);
    timerEntity.setExecutionId("Timer Entity");
    timerEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setRepeat("Timer Entity");
    timerEntity.setJobHandlerType("timer-start-event");
    timerEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setProcessDefinitionId(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultJobManager.executeTimerJob(timerEntity));
    verify(businessCalendarManager).getBusinessCalendar("cycle");
    verify(processEngineConfiguration).getBusinessCalendarManager();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration).getJobHandlers();
    verify(processEngineConfiguration).getTimerJobEntityManager();
    verify(processEngineConfiguration).setJobHandlers(isA(Map.class));
    verify(executionDataManager, atLeast(1)).findById("Timer Entity");
  }

  /**
   * Test {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}.
   *
   * <ul>
   *   <li>Given {@code trigger-timer}.
   *   <li>Then {@link JobEntityImpl} (default constructor) MaxIterations is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.restoreExtraData(JobEntity, VariableScope)"})
  public void testRestoreExtraData_givenTriggerTimer_thenJobEntityImplMaxIterationsIsOne() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    JobEntityImpl timerEntity = new JobEntityImpl();
    timerEntity.setDeleted(true);
    timerEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setExceptionMessage("An error occurred");
    timerEntity.setExclusive(true);
    timerEntity.setExecutionId("42");
    timerEntity.setId("42");
    timerEntity.setInserted(true);
    timerEntity.setJobHandlerConfiguration("Job Handler Configuration");
    timerEntity.setJobType("Job Type");
    timerEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setLockOwner("Claimed By");
    timerEntity.setMaxIterations(3);
    timerEntity.setProcessInstanceId("42");
    timerEntity.setRepeat("Repeat");
    timerEntity.setRetries(1);
    timerEntity.setRevision(1);
    timerEntity.setTenantId("42");
    timerEntity.setUpdated(true);
    timerEntity.setJobHandlerType("trigger-timer");
    timerEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setProcessDefinitionId(null);

    // Act
    defaultJobManager.restoreExtraData(
        timerEntity, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertEquals(1, timerEntity.getMaxIterations());
  }

  /**
   * Test {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}.
   *
   * <ul>
   *   <li>Then {@link JobEntityImpl} (default constructor) MaxIterations is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.restoreExtraData(JobEntity, VariableScope)"})
  public void testRestoreExtraData_thenJobEntityImplMaxIterationsIsOne() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    JobEntityImpl timerEntity = new JobEntityImpl();
    timerEntity.setDeleted(true);
    timerEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setExceptionMessage("An error occurred");
    timerEntity.setExclusive(true);
    timerEntity.setExecutionId("42");
    timerEntity.setId("42");
    timerEntity.setInserted(true);
    timerEntity.setJobHandlerConfiguration("Job Handler Configuration");
    timerEntity.setJobType("Job Type");
    timerEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setLockOwner("Claimed By");
    timerEntity.setMaxIterations(3);
    timerEntity.setProcessInstanceId("42");
    timerEntity.setRepeat("Repeat");
    timerEntity.setRetries(1);
    timerEntity.setRevision(1);
    timerEntity.setTenantId("42");
    timerEntity.setUpdated(true);
    timerEntity.setJobHandlerType("timer-start-event");
    timerEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setProcessDefinitionId(null);

    // Act
    defaultJobManager.restoreExtraData(
        timerEntity, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertEquals(1, timerEntity.getMaxIterations());
  }

  /**
   * Test {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultJobManager.restoreExtraData(JobEntity, VariableScope)"})
  public void testRestoreExtraData_thenThrowActivitiException() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.getProcessDefinitionId())
        .thenThrow(new ActivitiException("An error occurred"));
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");
    when(timerEntity.getJobHandlerType()).thenReturn("Job Handler Type");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            defaultJobManager.restoreExtraData(
                timerEntity, NoExecutionVariableScope.getSharedInstance()));
    verify(timerEntity).getJobHandlerConfiguration();
    verify(timerEntity, atLeast(1)).getJobHandlerType();
    verify(timerEntity).getProcessDefinitionId();
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultJobManager.getMaxIterations(Process, String)"})
  public void testGetMaxIterations_givenAdhocSubProcess() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean()))
        .thenReturn(new AdhocSubProcess());

    // Act
    int actualMaxIterations = defaultJobManager.getMaxIterations(process, "42");

    // Assert
    verify(process).getFlowElement("42", true);
    assertEquals(-1, actualMaxIterations);
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultJobManager.getMaxIterations(Process, String)"})
  public void testGetMaxIterations_givenArrayListAddCancelEventDefinition() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(boundaryEvent);

    // Act
    int actualMaxIterations = defaultJobManager.getMaxIterations(process, "42");

    // Assert
    verify(process).getFlowElement("42", true);
    assertEquals(-1, actualMaxIterations);
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TimerEventDefinition} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultJobManager.getMaxIterations(Process, String)"})
  public void testGetMaxIterations_givenArrayListAddTimerEventDefinition() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new TimerEventDefinition());

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(boundaryEvent);

    // Act
    int actualMaxIterations = defaultJobManager.getMaxIterations(process, "42");

    // Assert
    verify(process).getFlowElement("42", true);
    assertEquals(-1, actualMaxIterations);
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultJobManager.getMaxIterations(Process, String)"})
  public void testGetMaxIterations_givenBoundaryEvent() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean()))
        .thenReturn(new BoundaryEvent());

    // Act
    int actualMaxIterations = defaultJobManager.getMaxIterations(process, "42");

    // Assert
    verify(process).getFlowElement("42", true);
    assertEquals(-1, actualMaxIterations);
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) EventDefinitions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultJobManager.getMaxIterations(Process, String)"})
  public void testGetMaxIterations_givenBoundaryEventEventDefinitionsIsNull() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(null);

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(boundaryEvent);

    // Act
    int actualMaxIterations = defaultJobManager.getMaxIterations(process, "42");

    // Assert
    verify(process).getFlowElement("42", true);
    assertEquals(-1, actualMaxIterations);
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultJobManager.getMaxIterations(Process, String)"})
  public void testGetMaxIterations_thenThrowActivitiException() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    when(boundaryEvent.getEventDefinitions()).thenThrow(new ActivitiException("An error occurred"));

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(boundaryEvent);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultJobManager.getMaxIterations(process, "42"));
    verify(boundaryEvent).getEventDefinitions();
    verify(process).getFlowElement("42", true);
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   *
   * <ul>
   *   <li>When createOneTaskProcessWithId {@code 42}.
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultJobManager.getMaxIterations(Process, String)"})
  public void testGetMaxIterations_whenCreateOneTaskProcessWithId42_thenReturnMinusOne() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    // Act and Assert
    assertEquals(
        -1,
        defaultJobManager.getMaxIterations(TestProcessUtil.createOneTaskProcessWithId("42"), "42"));
  }

  /**
   * Test {@link DefaultJobManager#calculateMaxIterationsValue(String)}.
   *
   * <ul>
   *   <li>When {@code foo/bar}.
   *   <li>Then return {@link Integer#MAX_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#calculateMaxIterationsValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultJobManager.calculateMaxIterationsValue(String)"})
  public void testCalculateMaxIterationsValue_whenFooBar_thenReturnMax_value() {
    // Arrange, Act and Assert
    assertEquals(Integer.MAX_VALUE, new DefaultJobManager().calculateMaxIterationsValue("foo/bar"));
  }

  /**
   * Test {@link DefaultJobManager#calculateMaxIterationsValue(String)}.
   *
   * <ul>
   *   <li>When {@code Original Expression}.
   *   <li>Then return {@link Integer#MAX_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#calculateMaxIterationsValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultJobManager.calculateMaxIterationsValue(String)"})
  public void testCalculateMaxIterationsValue_whenOriginalExpression_thenReturnMax_value() {
    // Arrange, Act and Assert
    assertEquals(
        Integer.MAX_VALUE,
        new DefaultJobManager().calculateMaxIterationsValue("Original Expression"));
  }

  /**
   * Test {@link DefaultJobManager#getBusinessCalendarName(String, VariableScope)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code cycle}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getBusinessCalendarName(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DefaultJobManager.getBusinessCalendarName(String, VariableScope)"})
  public void testGetBusinessCalendarName_whenEmptyString_thenReturnCycle() {
    // Arrange, Act and Assert
    assertEquals(
        "cycle",
        new DefaultJobManager()
            .getBusinessCalendarName("", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link DefaultJobManager#getBusinessCalendarName(String, VariableScope)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code cycle}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getBusinessCalendarName(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DefaultJobManager.getBusinessCalendarName(String, VariableScope)"})
  public void testGetBusinessCalendarName_whenNull_thenReturnCycle() {
    // Arrange, Act and Assert
    assertEquals(
        "cycle",
        new DefaultJobManager()
            .getBusinessCalendarName(null, NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link DefaultJobManager#internalCreateAsyncJob(ExecutionEntity, boolean)}.
   *
   * <p>Method under test: {@link DefaultJobManager#internalCreateAsyncJob(ExecutionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JobEntity DefaultJobManager.internalCreateAsyncJob(ExecutionEntity, boolean)"
  })
  public void testInternalCreateAsyncJob() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setJobEntityManager(jobEntityManager);

    DefaultJobManager defaultJobManager =
        new DefaultJobManager(new JtaProcessEngineConfiguration());
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTenantId(null);

    // Act
    JobEntity actualInternalCreateAsyncJobResult =
        defaultJobManager.internalCreateAsyncJob(execution, true);

    // Assert
    Object persistentState = actualInternalCreateAsyncJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualInternalCreateAsyncJobResult instanceof JobEntityImpl);
    assertEquals("", actualInternalCreateAsyncJobResult.getTenantId());
    assertEquals("async-continuation", actualInternalCreateAsyncJobResult.getJobHandlerType());
    assertEquals("message", actualInternalCreateAsyncJobResult.getJobType());
    assertNull(actualInternalCreateAsyncJobResult.getExceptionStacktrace());
    assertNull(actualInternalCreateAsyncJobResult.getJobHandlerConfiguration());
    assertNull(actualInternalCreateAsyncJobResult.getRepeat());
    assertNull(actualInternalCreateAsyncJobResult.getId());
    assertNull(actualInternalCreateAsyncJobResult.getLockOwner());
    assertNull(actualInternalCreateAsyncJobResult.getExceptionMessage());
    assertNull(actualInternalCreateAsyncJobResult.getExecutionId());
    assertNull(actualInternalCreateAsyncJobResult.getProcessDefinitionId());
    assertNull(actualInternalCreateAsyncJobResult.getProcessInstanceId());
    assertNull(actualInternalCreateAsyncJobResult.getEndDate());
    assertNull(actualInternalCreateAsyncJobResult.getLockExpirationTime());
    assertNull(actualInternalCreateAsyncJobResult.getDuedate());
    assertNull(actualInternalCreateAsyncJobResult.getExceptionByteArrayRef());
    assertEquals(0, actualInternalCreateAsyncJobResult.getMaxIterations());
    assertEquals(1, actualInternalCreateAsyncJobResult.getRevision());
    assertEquals(2, actualInternalCreateAsyncJobResult.getRevisionNext());
    assertEquals(3, actualInternalCreateAsyncJobResult.getRetries());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertFalse(actualInternalCreateAsyncJobResult.isDeleted());
    assertFalse(actualInternalCreateAsyncJobResult.isInserted());
    assertFalse(actualInternalCreateAsyncJobResult.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(actualInternalCreateAsyncJobResult.isExclusive());
  }

  /**
   * Test {@link DefaultJobManager#internalCreateAsyncJob(ExecutionEntity, boolean)}.
   *
   * <ul>
   *   <li>Then PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#internalCreateAsyncJob(ExecutionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JobEntity DefaultJobManager.internalCreateAsyncJob(ExecutionEntity, boolean)"
  })
  public void testInternalCreateAsyncJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setJobEntityManager(jobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualInternalCreateAsyncJobResult =
        defaultJobManager.internalCreateAsyncJob(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    Object persistentState = actualInternalCreateAsyncJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualInternalCreateAsyncJobResult instanceof JobEntityImpl);
    assertEquals("", actualInternalCreateAsyncJobResult.getTenantId());
    assertEquals("async-continuation", actualInternalCreateAsyncJobResult.getJobHandlerType());
    assertEquals("message", actualInternalCreateAsyncJobResult.getJobType());
    assertNull(actualInternalCreateAsyncJobResult.getExceptionStacktrace());
    assertNull(actualInternalCreateAsyncJobResult.getJobHandlerConfiguration());
    assertNull(actualInternalCreateAsyncJobResult.getRepeat());
    assertNull(actualInternalCreateAsyncJobResult.getId());
    assertNull(actualInternalCreateAsyncJobResult.getLockOwner());
    assertNull(actualInternalCreateAsyncJobResult.getExceptionMessage());
    assertNull(actualInternalCreateAsyncJobResult.getExecutionId());
    assertNull(actualInternalCreateAsyncJobResult.getProcessDefinitionId());
    assertNull(actualInternalCreateAsyncJobResult.getProcessInstanceId());
    assertNull(actualInternalCreateAsyncJobResult.getEndDate());
    assertNull(actualInternalCreateAsyncJobResult.getLockExpirationTime());
    assertNull(actualInternalCreateAsyncJobResult.getDuedate());
    assertNull(actualInternalCreateAsyncJobResult.getExceptionByteArrayRef());
    assertEquals(0, actualInternalCreateAsyncJobResult.getMaxIterations());
    assertEquals(1, actualInternalCreateAsyncJobResult.getRevision());
    assertEquals(2, actualInternalCreateAsyncJobResult.getRevisionNext());
    assertEquals(3, actualInternalCreateAsyncJobResult.getRetries());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertFalse(actualInternalCreateAsyncJobResult.isDeleted());
    assertFalse(actualInternalCreateAsyncJobResult.isInserted());
    assertFalse(actualInternalCreateAsyncJobResult.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(actualInternalCreateAsyncJobResult.isExclusive());
  }

  /**
   * Test {@link DefaultJobManager#internalCreateLockedAsyncJob(ExecutionEntity, boolean)}.
   *
   * <p>Method under test: {@link DefaultJobManager#internalCreateLockedAsyncJob(ExecutionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JobEntity DefaultJobManager.internalCreateLockedAsyncJob(ExecutionEntity, boolean)"
  })
  public void testInternalCreateLockedAsyncJob() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutorNumberOfRetries())
        .thenThrow(new ActivitiException("An error occurred"));
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManagerImpl);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            defaultJobManager.internalCreateLockedAsyncJob(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));
    verify(processEngineConfiguration).getAsyncExecutorNumberOfRetries();
    verify(processEngineConfiguration).getJobEntityManager();
  }

  /**
   * Test {@link DefaultJobManager#internalCreateLockedAsyncJob(ExecutionEntity, boolean)}.
   *
   * <p>Method under test: {@link DefaultJobManager#internalCreateLockedAsyncJob(ExecutionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JobEntity DefaultJobManager.internalCreateLockedAsyncJob(ExecutionEntity, boolean)"
  })
  public void testInternalCreateLockedAsyncJob2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor())
        .thenThrow(new ActivitiException("An error occurred"));
    when(processEngineConfiguration.getAsyncExecutorNumberOfRetries()).thenReturn(10);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManagerImpl);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            defaultJobManager.internalCreateLockedAsyncJob(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(processEngineConfiguration).getAsyncExecutorNumberOfRetries();
    verify(processEngineConfiguration).getJobEntityManager();
  }

  /**
   * Test {@link DefaultJobManager#internalCreateLockedAsyncJob(ExecutionEntity, boolean)}.
   *
   * <ul>
   *   <li>Then PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#internalCreateLockedAsyncJob(ExecutionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JobEntity DefaultJobManager.internalCreateLockedAsyncJob(ExecutionEntity, boolean)"
  })
  public void testInternalCreateLockedAsyncJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getAsyncExecutorNumberOfRetries()).thenReturn(10);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManagerImpl);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualInternalCreateLockedAsyncJobResult =
        defaultJobManager.internalCreateLockedAsyncJob(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(processEngineConfiguration).getAsyncExecutorNumberOfRetries();
    verify(processEngineConfiguration).getJobEntityManager();
    Object persistentState = actualInternalCreateLockedAsyncJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualInternalCreateLockedAsyncJobResult instanceof JobEntityImpl);
    assertEquals("", actualInternalCreateLockedAsyncJobResult.getTenantId());
    assertEquals(
        "async-continuation", actualInternalCreateLockedAsyncJobResult.getJobHandlerType());
    assertEquals("message", actualInternalCreateLockedAsyncJobResult.getJobType());
    assertNull(actualInternalCreateLockedAsyncJobResult.getExceptionStacktrace());
    assertNull(actualInternalCreateLockedAsyncJobResult.getJobHandlerConfiguration());
    assertNull(actualInternalCreateLockedAsyncJobResult.getRepeat());
    assertNull(actualInternalCreateLockedAsyncJobResult.getId());
    assertNull(actualInternalCreateLockedAsyncJobResult.getExceptionMessage());
    assertNull(actualInternalCreateLockedAsyncJobResult.getExecutionId());
    assertNull(actualInternalCreateLockedAsyncJobResult.getProcessDefinitionId());
    assertNull(actualInternalCreateLockedAsyncJobResult.getProcessInstanceId());
    assertNull(actualInternalCreateLockedAsyncJobResult.getEndDate());
    assertNull(actualInternalCreateLockedAsyncJobResult.getDuedate());
    assertNull(actualInternalCreateLockedAsyncJobResult.getExceptionByteArrayRef());
    assertEquals(0, actualInternalCreateLockedAsyncJobResult.getMaxIterations());
    assertEquals(1, actualInternalCreateLockedAsyncJobResult.getRevision());
    assertEquals(10, actualInternalCreateLockedAsyncJobResult.getRetries());
    assertEquals(2, actualInternalCreateLockedAsyncJobResult.getRevisionNext());
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertFalse(actualInternalCreateLockedAsyncJobResult.isDeleted());
    assertFalse(actualInternalCreateLockedAsyncJobResult.isInserted());
    assertFalse(actualInternalCreateLockedAsyncJobResult.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertTrue(actualInternalCreateLockedAsyncJobResult.isExclusive());
  }

  /**
   * Test {@link DefaultJobManager#fillDefaultAsyncJobInfo(JobEntity, ExecutionEntity, boolean)}.
   *
   * <p>Method under test: {@link DefaultJobManager#fillDefaultAsyncJobInfo(JobEntity,
   * ExecutionEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultJobManager.fillDefaultAsyncJobInfo(JobEntity, ExecutionEntity, boolean)"
  })
  public void testFillDefaultAsyncJobInfo() {
    // Arrange
    DefaultJobManager defaultJobManager =
        new DefaultJobManager(new JtaProcessEngineConfiguration());
    JobEntityImpl jobEntity = new JobEntityImpl();

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTenantId(null);

    // Act
    defaultJobManager.fillDefaultAsyncJobInfo(jobEntity, execution, true);

    // Assert
    Object persistentState = jobEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("async-continuation", jobEntity.getJobHandlerType());
    assertEquals("message", jobEntity.getJobType());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertEquals(3, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(3, jobEntity.getRetries());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
  }

  /**
   * Test {@link DefaultJobManager#fillDefaultAsyncJobInfo(JobEntity, ExecutionEntity, boolean)}.
   *
   * <ul>
   *   <li>Then {@link JobEntityImpl} (default constructor) PersistentState {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#fillDefaultAsyncJobInfo(JobEntity,
   * ExecutionEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultJobManager.fillDefaultAsyncJobInfo(JobEntity, ExecutionEntity, boolean)"
  })
  public void testFillDefaultAsyncJobInfo_thenJobEntityImplPersistentStateMap() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    defaultJobManager.fillDefaultAsyncJobInfo(
        jobEntity, ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    Object persistentState = jobEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("async-continuation", jobEntity.getJobHandlerType());
    assertEquals("message", jobEntity.getJobType());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertEquals(3, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(3, jobEntity.getRetries());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
  }

  /**
   * Test {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Given {@code stacktrace}.
   *   <li>Then return Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JobEntity DefaultJobManager.createExecutableJobFromOtherJob(AbstractJobEntity)"
  })
  public void testCreateExecutableJobFromOtherJob_givenStacktrace_thenReturnIdIs42() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManagerImpl);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    JobEntityImpl job = new JobEntityImpl();
    job.setDeleted(true);
    job.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("stacktrace");
    job.setJobHandlerType("stacktrace");
    job.setJobType("stacktrace");
    job.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("stacktrace");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("stacktrace");
    job.setRetries(1);
    job.setRevision(1);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    JobEntity actualCreateExecutableJobFromOtherJobResult =
        defaultJobManager.createExecutableJobFromOtherJob(job);

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getJobEntityManager();
    assertTrue(actualCreateExecutableJobFromOtherJobResult.getPersistentState() instanceof Map);
    assertTrue(actualCreateExecutableJobFromOtherJobResult instanceof JobEntityImpl);
    assertEquals("42", actualCreateExecutableJobFromOtherJobResult.getId());
    assertEquals("42", actualCreateExecutableJobFromOtherJobResult.getExecutionId());
    assertEquals("42", actualCreateExecutableJobFromOtherJobResult.getProcessDefinitionId());
    assertEquals("42", actualCreateExecutableJobFromOtherJobResult.getProcessInstanceId());
    assertEquals("42", actualCreateExecutableJobFromOtherJobResult.getTenantId());
    assertEquals(
        "An error occurred", actualCreateExecutableJobFromOtherJobResult.getExceptionMessage());
    assertEquals(
        "stacktrace", actualCreateExecutableJobFromOtherJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualCreateExecutableJobFromOtherJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualCreateExecutableJobFromOtherJobResult.getJobType());
    assertEquals("stacktrace", actualCreateExecutableJobFromOtherJobResult.getRepeat());
    assertEquals(1, actualCreateExecutableJobFromOtherJobResult.getRetries());
    assertEquals(3, actualCreateExecutableJobFromOtherJobResult.getMaxIterations());
  }

  /**
   * Test {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then return TenantId is empty string.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JobEntity DefaultJobManager.createExecutableJobFromOtherJob(AbstractJobEntity)"
  })
  public void testCreateExecutableJobFromOtherJob_thenReturnTenantIdIsEmptyString() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManagerImpl);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualCreateExecutableJobFromOtherJobResult =
        defaultJobManager.createExecutableJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getJobEntityManager();
    assertTrue(actualCreateExecutableJobFromOtherJobResult.getPersistentState() instanceof Map);
    assertTrue(actualCreateExecutableJobFromOtherJobResult instanceof JobEntityImpl);
    assertEquals("", actualCreateExecutableJobFromOtherJobResult.getTenantId());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getJobHandlerType());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getJobType());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getRepeat());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getId());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getExceptionMessage());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getExecutionId());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getProcessDefinitionId());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getProcessInstanceId());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getEndDate());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getDuedate());
    assertEquals(0, actualCreateExecutableJobFromOtherJobResult.getMaxIterations());
    assertEquals(0, actualCreateExecutableJobFromOtherJobResult.getRetries());
  }

  /**
   * Test {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JobEntity DefaultJobManager.createExecutableJobFromOtherJob(AbstractJobEntity)"
  })
  public void testCreateExecutableJobFromOtherJob_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor())
        .thenThrow(new ActivitiException("An error occurred"));
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManagerImpl);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> defaultJobManager.createExecutableJobFromOtherJob(new DeadLetterJobEntityImpl()));
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getJobEntityManager();
  }

  /**
   * Test {@link DefaultJobManager#createTimerJobFromOtherJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Given {@code stacktrace}.
   *   <li>Then return Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#createTimerJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TimerJobEntity DefaultJobManager.createTimerJobFromOtherJob(AbstractJobEntity)"
  })
  public void testCreateTimerJobFromOtherJob_givenStacktrace_thenReturnIdIs42() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManager =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setTimerJobEntityManager(timerJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    TimerJobEntityImpl otherJob = new TimerJobEntityImpl();
    otherJob.setDeleted(true);
    otherJob.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    otherJob.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    otherJob.setExceptionMessage("An error occurred");
    otherJob.setExclusive(true);
    otherJob.setExecutionId("42");
    otherJob.setId("42");
    otherJob.setInserted(true);
    otherJob.setJobHandlerConfiguration("stacktrace");
    otherJob.setJobHandlerType("stacktrace");
    otherJob.setJobType("stacktrace");
    otherJob.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    otherJob.setLockOwner("stacktrace");
    otherJob.setMaxIterations(3);
    otherJob.setProcessDefinitionId("42");
    otherJob.setProcessInstanceId("42");
    otherJob.setRepeat("stacktrace");
    otherJob.setRetries(1);
    otherJob.setRevision(1);
    otherJob.setTenantId("42");
    otherJob.setUpdated(true);

    // Act
    TimerJobEntity actualCreateTimerJobFromOtherJobResult =
        defaultJobManager.createTimerJobFromOtherJob(otherJob);

    // Assert
    assertTrue(actualCreateTimerJobFromOtherJobResult.getPersistentState() instanceof Map);
    assertTrue(actualCreateTimerJobFromOtherJobResult instanceof TimerJobEntityImpl);
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getId());
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getExecutionId());
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getProcessDefinitionId());
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getProcessInstanceId());
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getTenantId());
    assertEquals("An error occurred", actualCreateTimerJobFromOtherJobResult.getExceptionMessage());
    assertEquals("stacktrace", actualCreateTimerJobFromOtherJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualCreateTimerJobFromOtherJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualCreateTimerJobFromOtherJobResult.getJobType());
    assertEquals("stacktrace", actualCreateTimerJobFromOtherJobResult.getRepeat());
    assertEquals(1, actualCreateTimerJobFromOtherJobResult.getRetries());
    assertEquals(3, actualCreateTimerJobFromOtherJobResult.getMaxIterations());
  }

  /**
   * Test {@link DefaultJobManager#createTimerJobFromOtherJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then return TenantId is empty string.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#createTimerJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TimerJobEntity DefaultJobManager.createTimerJobFromOtherJob(AbstractJobEntity)"
  })
  public void testCreateTimerJobFromOtherJob_thenReturnTenantIdIsEmptyString() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManager =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setTimerJobEntityManager(timerJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    TimerJobEntity actualCreateTimerJobFromOtherJobResult =
        defaultJobManager.createTimerJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(actualCreateTimerJobFromOtherJobResult.getPersistentState() instanceof Map);
    assertTrue(actualCreateTimerJobFromOtherJobResult instanceof TimerJobEntityImpl);
    assertEquals("", actualCreateTimerJobFromOtherJobResult.getTenantId());
    assertNull(actualCreateTimerJobFromOtherJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateTimerJobFromOtherJobResult.getJobHandlerType());
    assertNull(actualCreateTimerJobFromOtherJobResult.getJobType());
    assertNull(actualCreateTimerJobFromOtherJobResult.getRepeat());
    assertNull(actualCreateTimerJobFromOtherJobResult.getId());
    assertNull(actualCreateTimerJobFromOtherJobResult.getExceptionMessage());
    assertNull(actualCreateTimerJobFromOtherJobResult.getExecutionId());
    assertNull(actualCreateTimerJobFromOtherJobResult.getProcessDefinitionId());
    assertNull(actualCreateTimerJobFromOtherJobResult.getProcessInstanceId());
    assertNull(actualCreateTimerJobFromOtherJobResult.getEndDate());
    assertNull(actualCreateTimerJobFromOtherJobResult.getDuedate());
    assertEquals(0, actualCreateTimerJobFromOtherJobResult.getMaxIterations());
    assertEquals(0, actualCreateTimerJobFromOtherJobResult.getRetries());
  }

  /**
   * Test {@link DefaultJobManager#createSuspendedJobFromOtherJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Given {@code stacktrace}.
   *   <li>Then return Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultJobManager#createSuspendedJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SuspendedJobEntity DefaultJobManager.createSuspendedJobFromOtherJob(AbstractJobEntity)"
  })
  public void testCreateSuspendedJobFromOtherJob_givenStacktrace_thenReturnIdIs42() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManager =
        new SuspendedJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setSuspendedJobEntityManager(suspendedJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    SuspendedJobEntityImpl otherJob = new SuspendedJobEntityImpl();
    otherJob.setDeleted(true);
    otherJob.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    otherJob.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    otherJob.setExceptionMessage("An error occurred");
    otherJob.setExclusive(true);
    otherJob.setExecutionId("42");
    otherJob.setId("42");
    otherJob.setInserted(true);
    otherJob.setJobHandlerConfiguration("stacktrace");
    otherJob.setJobHandlerType("stacktrace");
    otherJob.setJobType("stacktrace");
    otherJob.setMaxIterations(3);
    otherJob.setProcessDefinitionId("42");
    otherJob.setProcessInstanceId("42");
    otherJob.setRepeat("stacktrace");
    otherJob.setRetries(1);
    otherJob.setRevision(1);
    otherJob.setTenantId("42");
    otherJob.setUpdated(true);

    // Act
    SuspendedJobEntity actualCreateSuspendedJobFromOtherJobResult =
        defaultJobManager.createSuspendedJobFromOtherJob(otherJob);

    // Assert
    assertTrue(actualCreateSuspendedJobFromOtherJobResult.getPersistentState() instanceof Map);
    assertTrue(actualCreateSuspendedJobFromOtherJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getId());
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getExecutionId());
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getProcessDefinitionId());
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getProcessInstanceId());
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getTenantId());
    assertEquals(
        "An error occurred", actualCreateSuspendedJobFromOtherJobResult.getExceptionMessage());
    assertEquals(
        "stacktrace", actualCreateSuspendedJobFromOtherJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualCreateSuspendedJobFromOtherJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualCreateSuspendedJobFromOtherJobResult.getJobType());
    assertEquals("stacktrace", actualCreateSuspendedJobFromOtherJobResult.getRepeat());
    assertEquals(1, actualCreateSuspendedJobFromOtherJobResult.getRetries());
    assertEquals(3, actualCreateSuspendedJobFromOtherJobResult.getMaxIterations());
  }

  /**
   * Test {@link DefaultJobManager#createSuspendedJobFromOtherJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then return TenantId is empty string.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultJobManager#createSuspendedJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SuspendedJobEntity DefaultJobManager.createSuspendedJobFromOtherJob(AbstractJobEntity)"
  })
  public void testCreateSuspendedJobFromOtherJob_thenReturnTenantIdIsEmptyString() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManager =
        new SuspendedJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setSuspendedJobEntityManager(suspendedJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    SuspendedJobEntity actualCreateSuspendedJobFromOtherJobResult =
        defaultJobManager.createSuspendedJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(actualCreateSuspendedJobFromOtherJobResult.getPersistentState() instanceof Map);
    assertTrue(actualCreateSuspendedJobFromOtherJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("", actualCreateSuspendedJobFromOtherJobResult.getTenantId());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getJobHandlerType());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getJobType());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getRepeat());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getId());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getExceptionMessage());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getExecutionId());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getProcessDefinitionId());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getProcessInstanceId());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getEndDate());
    assertNull(actualCreateSuspendedJobFromOtherJobResult.getDuedate());
    assertEquals(0, actualCreateSuspendedJobFromOtherJobResult.getMaxIterations());
    assertEquals(0, actualCreateSuspendedJobFromOtherJobResult.getRetries());
  }

  /**
   * Test {@link DefaultJobManager#createDeadLetterJobFromOtherJob(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultJobManager#createDeadLetterJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeadLetterJobEntity DefaultJobManager.createDeadLetterJobFromOtherJob(AbstractJobEntity)"
  })
  public void testCreateDeadLetterJobFromOtherJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManager =
        new DeadLetterJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDeadLetterJobEntityManager(deadLetterJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    DeadLetterJobEntity actualCreateDeadLetterJobFromOtherJobResult =
        defaultJobManager.createDeadLetterJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateDeadLetterJobFromOtherJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateDeadLetterJobFromOtherJobResult instanceof DeadLetterJobEntityImpl);
    assertEquals("", actualCreateDeadLetterJobFromOtherJobResult.getTenantId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getExceptionStacktrace());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getJobHandlerType());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getJobType());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getRepeat());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getExceptionMessage());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getExecutionId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getProcessDefinitionId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getProcessInstanceId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getEndDate());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getDuedate());
    assertEquals(0, actualCreateDeadLetterJobFromOtherJobResult.getMaxIterations());
    assertEquals(0, actualCreateDeadLetterJobFromOtherJobResult.getRetries());
    assertEquals(1, actualCreateDeadLetterJobFromOtherJobResult.getRevision());
    assertEquals(2, actualCreateDeadLetterJobFromOtherJobResult.getRevisionNext());
    assertEquals(4, ((Map<String, Integer>) persistentState).size());
    assertFalse(actualCreateDeadLetterJobFromOtherJobResult.isDeleted());
    assertFalse(actualCreateDeadLetterJobFromOtherJobResult.isInserted());
    assertFalse(actualCreateDeadLetterJobFromOtherJobResult.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(actualCreateDeadLetterJobFromOtherJobResult.isExclusive());
  }

  /**
   * Test {@link DefaultJobManager#copyJobInfo(AbstractJobEntity, AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor) PersistentState {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#copyJobInfo(AbstractJobEntity,
   * AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractJobEntity DefaultJobManager.copyJobInfo(AbstractJobEntity, AbstractJobEntity)"
  })
  public void testCopyJobInfo_thenDeadLetterJobEntityImplPersistentStateMap() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    DeadLetterJobEntityImpl copyToJob = new DeadLetterJobEntityImpl();

    // Act
    AbstractJobEntity actualCopyJobInfoResult =
        defaultJobManager.copyJobInfo(copyToJob, new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = copyToJob.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(4, ((Map<String, Integer>) persistentState).size());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertSame(copyToJob, actualCopyJobInfoResult);
  }

  /**
   * Test {@link DefaultJobManager#copyJobInfo(AbstractJobEntity, AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then return {@link AbstractJobEntity}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#copyJobInfo(AbstractJobEntity,
   * AbstractJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractJobEntity DefaultJobManager.copyJobInfo(AbstractJobEntity, AbstractJobEntity)"
  })
  public void testCopyJobInfo_thenReturnAbstractJobEntity() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    AbstractJobEntity copyToJob = mock(AbstractJobEntity.class);
    doNothing().when(copyToJob).setRevision(anyInt());
    doNothing().when(copyToJob).setDuedate(Mockito.<Date>any());
    doNothing().when(copyToJob).setEndDate(Mockito.<Date>any());
    doNothing().when(copyToJob).setExceptionMessage(Mockito.<String>any());
    doNothing().when(copyToJob).setExceptionStacktrace(Mockito.<String>any());
    doNothing().when(copyToJob).setExclusive(anyBoolean());
    doNothing().when(copyToJob).setExecutionId(Mockito.<String>any());
    doNothing().when(copyToJob).setJobHandlerConfiguration(Mockito.<String>any());
    doNothing().when(copyToJob).setJobHandlerType(Mockito.<String>any());
    doNothing().when(copyToJob).setJobType(Mockito.<String>any());
    doNothing().when(copyToJob).setMaxIterations(anyInt());
    doNothing().when(copyToJob).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(copyToJob).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(copyToJob).setRepeat(Mockito.<String>any());
    doNothing().when(copyToJob).setRetries(anyInt());
    doNothing().when(copyToJob).setTenantId(Mockito.<String>any());
    doNothing().when(copyToJob).setId(Mockito.<String>any());

    // Act
    AbstractJobEntity actualCopyJobInfoResult =
        defaultJobManager.copyJobInfo(copyToJob, new DeadLetterJobEntityImpl());

    // Assert
    verify(copyToJob).setRevision(1);
    verify(copyToJob).setDuedate(isNull());
    verify(copyToJob).setEndDate(isNull());
    verify(copyToJob).setExceptionMessage(null);
    verify(copyToJob).setExceptionStacktrace(null);
    verify(copyToJob).setExclusive(true);
    verify(copyToJob).setExecutionId(null);
    verify(copyToJob).setJobHandlerConfiguration(null);
    verify(copyToJob).setJobHandlerType(null);
    verify(copyToJob).setJobType(null);
    verify(copyToJob).setMaxIterations(0);
    verify(copyToJob).setProcessDefinitionId(null);
    verify(copyToJob).setProcessInstanceId(null);
    verify(copyToJob).setRepeat(null);
    verify(copyToJob).setRetries(0);
    verify(copyToJob).setTenantId("");
    verify(copyToJob).setId(null);
    assertSame(copyToJob, actualCopyJobInfoResult);
  }

  /**
   * Test {@link DefaultJobManager#isAsyncExecutorActive()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#isAsyncExecutorActive()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultJobManager.isAsyncExecutorActive()"})
  public void testIsAsyncExecutorActive_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutor(new DefaultAsyncJobExecutor());

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertFalse(defaultJobManager.isAsyncExecutorActive());
  }

  /**
   * Test {@link DefaultJobManager#getCommandContext()}.
   *
   * <p>Method under test: {@link DefaultJobManager#getCommandContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.interceptor.CommandContext DefaultJobManager.getCommandContext()"
  })
  public void testGetCommandContext() {
    // Arrange, Act and Assert
    assertNull(new DefaultJobManager().getCommandContext());
  }

  /**
   * Test {@link DefaultJobManager#getAsyncExecutor()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.asyncexecutor.AsyncExecutor DefaultJobManager.getAsyncExecutor()"
  })
  public void testGetAsyncExecutor_thenReturnNull() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(defaultJobManager.getAsyncExecutor());
  }

  /**
   * Test {@link DefaultJobManager#getExecutionEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultJobManager#getExecutionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntityManager DefaultJobManager.getExecutionEntityManager()"})
  public void testGetExecutionEntityManager_thenReturnNull() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(defaultJobManager.getExecutionEntityManager());
  }
}
