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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.Process;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.calendar.BusinessCalendarManager;
import org.activiti.engine.impl.calendar.DefaultBusinessCalendar;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.persistence.entity.AbstractEntity;
import org.activiti.engine.impl.persistence.entity.AbstractJobEntity;
import org.activiti.engine.impl.persistence.entity.ByteArrayRef;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntity;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManager;
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
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManager;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntity;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManager;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.JobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisSuspendedJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Clock;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultJobManagerDiffblueTest {
  @InjectMocks
  private DefaultJobManager defaultJobManager;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DefaultJobManager#DefaultJobManager()}
   *   <li>
   * {@link DefaultJobManager#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link DefaultJobManager#getProcessEngineConfiguration()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DefaultJobManager actualDefaultJobManager = new DefaultJobManager();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    actualDefaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Assert that nothing has changed
    assertSame(processEngineConfiguration, actualDefaultJobManager.getProcessEngineConfiguration());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link DefaultJobManager#DefaultJobManager(ProcessEngineConfigurationImpl)}
   *   <li>
   * {@link DefaultJobManager#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link DefaultJobManager#getProcessEngineConfiguration()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenJtaProcessEngineConfiguration() {
    // Arrange and Act
    DefaultJobManager actualDefaultJobManager = new DefaultJobManager(new JtaProcessEngineConfiguration());
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    actualDefaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Assert that nothing has changed
    assertSame(processEngineConfiguration, actualDefaultJobManager.getProcessEngineConfiguration());
  }

  /**
   * Test {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}.
   * <ul>
   *   <li>Then return {@code Lock Owner}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}
   */
  @Test
  public void testCreateAsyncJob_thenReturnLockOwner() {
    // Arrange
    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getAsyncJobLockTimeInMillis()).thenReturn(1);
    when(asyncExecutor.getLockOwner()).thenReturn("Lock Owner");
    when(asyncExecutor.isActive()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration.getAsyncExecutorNumberOfRetries()).thenReturn(10);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(new JobEntityManagerImpl(
        processEngineConfiguration2, new MybatisJobDataManager(new JtaProcessEngineConfiguration())));
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(asyncExecutor);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualCreateAsyncJobResult = defaultJobManager
        .createAsyncJob(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(asyncExecutor).getAsyncJobLockTimeInMillis();
    verify(asyncExecutor).getLockOwner();
    verify(asyncExecutor).isActive();
    verify(processEngineConfiguration).getAsyncExecutorNumberOfRetries();
    verify(processEngineConfiguration).getJobEntityManager();
    Object persistentState = actualCreateAsyncJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateAsyncJobResult instanceof JobEntityImpl);
    assertEquals("Lock Owner", actualCreateAsyncJobResult.getLockOwner());
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertEquals("Lock Owner", ((Map<String, Object>) persistentState).get("lockOwner"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    Date expectedGetResult = actualCreateAsyncJobResult.getLockExpirationTime();
    assertSame(expectedGetResult, ((Map<String, Object>) persistentState).get("lockExpirationTime"));
  }

  /**
   * Test {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}.
   * <ul>
   *   <li>Then return PersistentState {@code lockExpirationTime} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}
   */
  @Test
  public void testCreateAsyncJob_thenReturnPersistentStateLockExpirationTimeIsNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutorNumberOfRetries()).thenReturn(10);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(new JobEntityManagerImpl(
        processEngineConfiguration2, new MybatisJobDataManager(new JtaProcessEngineConfiguration())));
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualCreateAsyncJobResult = defaultJobManager
        .createAsyncJob(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getAsyncExecutorNumberOfRetries();
    verify(processEngineConfiguration).getJobEntityManager();
    Object persistentState = actualCreateAsyncJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateAsyncJobResult instanceof JobEntityImpl);
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Integer>) persistentState).get("lockOwner"));
    assertNull(actualCreateAsyncJobResult.getLockOwner());
    assertNull(actualCreateAsyncJobResult.getLockExpirationTime());
    assertEquals(10, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
  }

  /**
   * Test {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createAsyncJob(ExecutionEntity, boolean)}
   */
  @Test
  public void testCreateAsyncJob_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getAsyncJobLockTimeInMillis())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(asyncExecutor.isActive()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration.getAsyncExecutorNumberOfRetries()).thenReturn(10);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(new JobEntityManagerImpl(
        processEngineConfiguration2, new MybatisJobDataManager(new JtaProcessEngineConfiguration())));
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(asyncExecutor);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultJobManager.createAsyncJob(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(asyncExecutor).getAsyncJobLockTimeInMillis();
    verify(asyncExecutor).isActive();
    verify(processEngineConfiguration).getAsyncExecutorNumberOfRetries();
    verify(processEngineConfiguration).getJobEntityManager();
  }

  /**
   * Test {@link DefaultJobManager#scheduleAsyncJob(JobEntity)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#scheduleAsyncJob(JobEntity)}
   */
  @Test
  public void testScheduleAsyncJob_thenCallsGetAsyncExecutor() {
    // Arrange
    JobEntityManager jobEntityManager = mock(JobEntityManager.class);
    doNothing().when(jobEntityManager).insert(Mockito.<JobEntity>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
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
   * Test {@link DefaultJobManager#triggerExecutorIfNeeded(JobEntity)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#triggerExecutorIfNeeded(JobEntity)}
   */
  @Test
  public void testTriggerExecutorIfNeeded_thenCallsGetAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultJobManager.triggerExecutorIfNeeded(new JobEntityImpl());

    // Assert that nothing has changed
    verify(processEngineConfiguration).getAsyncExecutor();
  }

  /**
   * Test {@link DefaultJobManager#scheduleTimerJob(TimerJobEntity)}.
   * <ul>
   *   <li>Given {@link DefaultJobManager#DefaultJobManager()}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#scheduleTimerJob(TimerJobEntity)}
   */
  @Test
  public void testScheduleTimerJob_givenDefaultJobManager_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new DefaultJobManager()).scheduleTimerJob(null));
  }

  /**
   * Test {@link DefaultJobManager#moveTimerJobToExecutableJob(TimerJobEntity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveTimerJobToExecutableJob(TimerJobEntity)}
   */
  @Test
  public void testMoveTimerJobToExecutableJob_whenNull_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultJobManager.moveTimerJobToExecutableJob(null));
  }

  /**
   * Test {@link DefaultJobManager#moveJobToTimerJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getJobEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveJobToTimerJob(AbstractJobEntity)}
   */
  @Test
  public void testMoveJobToTimerJob_thenCallsGetJobEntityManager() {
    // Arrange
    TimerJobEntityManager timerJobEntityManager = mock(TimerJobEntityManager.class);
    when(timerJobEntityManager.insertTimerJobEntity(Mockito.<TimerJobEntity>any())).thenReturn(true);
    when(timerJobEntityManager.create()).thenReturn(new TimerJobEntityImpl());
    JobEntityManager jobEntityManager = mock(JobEntityManager.class);
    doNothing().when(jobEntityManager).delete(Mockito.<JobEntity>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManager);
    when(processEngineConfiguration.getTimerJobEntityManager()).thenReturn(timerJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    JobEntityImpl job = new JobEntityImpl();
    job.setDeleted(true);
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    job.setDuedate(duedate);
    job.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("stacktrace");
    job.setJobHandlerType("stacktrace");
    job.setJobType("stacktrace");
    job.setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("stacktrace");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("stacktrace");
    job.setRetries(3);
    job.setRevision(3);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    TimerJobEntity actualMoveJobToTimerJobResult = defaultJobManager.moveJobToTimerJob(job);

    // Assert
    verify(processEngineConfiguration).getJobEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getTimerJobEntityManager();
    verify(timerJobEntityManager).create();
    verify(jobEntityManager).delete(isA(JobEntity.class));
    verify(timerJobEntityManager).insertTimerJobEntity(isA(TimerJobEntity.class));
    Object persistentState = actualMoveJobToTimerJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualMoveJobToTimerJobResult instanceof TimerJobEntityImpl);
    assertEquals("42", actualMoveJobToTimerJobResult.getId());
    assertEquals("42", actualMoveJobToTimerJobResult.getExecutionId());
    assertEquals("42", actualMoveJobToTimerJobResult.getProcessDefinitionId());
    assertEquals("42", actualMoveJobToTimerJobResult.getProcessInstanceId());
    assertEquals("42", actualMoveJobToTimerJobResult.getTenantId());
    assertEquals("An error occurred", actualMoveJobToTimerJobResult.getExceptionMessage());
    assertEquals(6, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertEquals("stacktrace", actualMoveJobToTimerJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualMoveJobToTimerJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualMoveJobToTimerJobResult.getJobType());
    assertEquals("stacktrace", actualMoveJobToTimerJobResult.getRepeat());
    assertEquals(3, actualMoveJobToTimerJobResult.getRevision());
    assertEquals(3, actualMoveJobToTimerJobResult.getMaxIterations());
    assertEquals(3, actualMoveJobToTimerJobResult.getRetries());
    assertEquals(4, actualMoveJobToTimerJobResult.getRevisionNext());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
  }

  /**
   * Test {@link DefaultJobManager#moveJobToTimerJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveJobToTimerJob(AbstractJobEntity)}
   */
  @Test
  public void testMoveJobToTimerJob_thenReturnNull() {
    // Arrange
    TimerJobEntityManager timerJobEntityManager = mock(TimerJobEntityManager.class);
    when(timerJobEntityManager.insertTimerJobEntity(Mockito.<TimerJobEntity>any())).thenReturn(false);
    when(timerJobEntityManager.create()).thenReturn(new TimerJobEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getTimerJobEntityManager()).thenReturn(timerJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    TimerJobEntity actualMoveJobToTimerJobResult = defaultJobManager.moveJobToTimerJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getTimerJobEntityManager();
    verify(timerJobEntityManager).create();
    verify(timerJobEntityManager).insertTimerJobEntity(isA(TimerJobEntity.class));
    assertNull(actualMoveJobToTimerJobResult);
  }

  /**
   * Test {@link DefaultJobManager#moveJobToTimerJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then return TenantId is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveJobToTimerJob(AbstractJobEntity)}
   */
  @Test
  public void testMoveJobToTimerJob_thenReturnTenantIdIsEmptyString() {
    // Arrange
    TimerJobEntityManager timerJobEntityManager = mock(TimerJobEntityManager.class);
    when(timerJobEntityManager.insertTimerJobEntity(Mockito.<TimerJobEntity>any())).thenReturn(true);
    when(timerJobEntityManager.create()).thenReturn(new TimerJobEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getTimerJobEntityManager()).thenReturn(timerJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    TimerJobEntity actualMoveJobToTimerJobResult = defaultJobManager.moveJobToTimerJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getTimerJobEntityManager();
    verify(timerJobEntityManager).create();
    verify(timerJobEntityManager).insertTimerJobEntity(isA(TimerJobEntity.class));
    Object persistentState = actualMoveJobToTimerJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualMoveJobToTimerJobResult instanceof TimerJobEntityImpl);
    assertEquals("", actualMoveJobToTimerJobResult.getTenantId());
    assertEquals(6, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(actualMoveJobToTimerJobResult.getJobHandlerConfiguration());
    assertNull(actualMoveJobToTimerJobResult.getJobHandlerType());
    assertNull(actualMoveJobToTimerJobResult.getJobType());
    assertNull(actualMoveJobToTimerJobResult.getRepeat());
    assertNull(actualMoveJobToTimerJobResult.getId());
    assertNull(actualMoveJobToTimerJobResult.getExceptionMessage());
    assertNull(actualMoveJobToTimerJobResult.getExecutionId());
    assertNull(actualMoveJobToTimerJobResult.getProcessDefinitionId());
    assertNull(actualMoveJobToTimerJobResult.getProcessInstanceId());
    assertNull(actualMoveJobToTimerJobResult.getEndDate());
    assertNull(actualMoveJobToTimerJobResult.getDuedate());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualMoveJobToTimerJobResult.getMaxIterations());
    assertEquals(0, actualMoveJobToTimerJobResult.getRetries());
    assertEquals(1, actualMoveJobToTimerJobResult.getRevision());
    assertEquals(2, actualMoveJobToTimerJobResult.getRevisionNext());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
  }

  /**
   * Test {@link DefaultJobManager#moveJobToTimerJob(AbstractJobEntity)}.
   * <ul>
   *   <li>When {@link TimerJobEntityImpl} (default constructor) Deleted is
   * {@code true}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveJobToTimerJob(AbstractJobEntity)}
   */
  @Test
  public void testMoveJobToTimerJob_whenTimerJobEntityImplDeletedIsTrue_thenReturnIdIs42() {
    // Arrange
    TimerJobEntityManager timerJobEntityManager = mock(TimerJobEntityManager.class);
    when(timerJobEntityManager.insertTimerJobEntity(Mockito.<TimerJobEntity>any())).thenReturn(true);
    when(timerJobEntityManager.create()).thenReturn(new TimerJobEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getTimerJobEntityManager()).thenReturn(timerJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    TimerJobEntityImpl job = new TimerJobEntityImpl();
    job.setDeleted(true);
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    job.setDuedate(duedate);
    job.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("stacktrace");
    job.setJobHandlerType("stacktrace");
    job.setJobType("stacktrace");
    job.setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("stacktrace");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("stacktrace");
    job.setRetries(3);
    job.setRevision(3);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    TimerJobEntity actualMoveJobToTimerJobResult = defaultJobManager.moveJobToTimerJob(job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getTimerJobEntityManager();
    verify(timerJobEntityManager).create();
    verify(timerJobEntityManager).insertTimerJobEntity(isA(TimerJobEntity.class));
    Object persistentState = actualMoveJobToTimerJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualMoveJobToTimerJobResult instanceof TimerJobEntityImpl);
    assertEquals("42", actualMoveJobToTimerJobResult.getId());
    assertEquals("42", actualMoveJobToTimerJobResult.getExecutionId());
    assertEquals("42", actualMoveJobToTimerJobResult.getProcessDefinitionId());
    assertEquals("42", actualMoveJobToTimerJobResult.getProcessInstanceId());
    assertEquals("42", actualMoveJobToTimerJobResult.getTenantId());
    assertEquals("An error occurred", actualMoveJobToTimerJobResult.getExceptionMessage());
    assertEquals(6, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertEquals("stacktrace", actualMoveJobToTimerJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualMoveJobToTimerJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualMoveJobToTimerJobResult.getJobType());
    assertEquals("stacktrace", actualMoveJobToTimerJobResult.getRepeat());
    assertEquals(3, actualMoveJobToTimerJobResult.getRevision());
    assertEquals(3, actualMoveJobToTimerJobResult.getMaxIterations());
    assertEquals(3, actualMoveJobToTimerJobResult.getRetries());
    assertEquals(4, actualMoveJobToTimerJobResult.getRevisionNext());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
  }

  /**
   * Test {@link DefaultJobManager#moveJobToSuspendedJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveJobToSuspendedJob(AbstractJobEntity)}
   */
  @Test
  public void testMoveJobToSuspendedJob_thenCallsGetTimerJobEntityManager() {
    // Arrange
    SuspendedJobEntityManager suspendedJobEntityManager = mock(SuspendedJobEntityManager.class);
    doNothing().when(suspendedJobEntityManager).insert(Mockito.<SuspendedJobEntity>any());
    when(suspendedJobEntityManager.create()).thenReturn(new SuspendedJobEntityImpl());
    TimerJobEntityManager timerJobEntityManager = mock(TimerJobEntityManager.class);
    doNothing().when(timerJobEntityManager).delete(Mockito.<TimerJobEntity>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getTimerJobEntityManager()).thenReturn(timerJobEntityManager);
    when(processEngineConfiguration.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    TimerJobEntityImpl job = new TimerJobEntityImpl();
    job.setDeleted(true);
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    job.setDuedate(duedate);
    job.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("stacktrace");
    job.setJobHandlerType("stacktrace");
    job.setJobType("stacktrace");
    job.setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("stacktrace");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("stacktrace");
    job.setRetries(3);
    job.setRevision(3);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    SuspendedJobEntity actualMoveJobToSuspendedJobResult = defaultJobManager.moveJobToSuspendedJob(job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getSuspendedJobEntityManager();
    verify(processEngineConfiguration).getTimerJobEntityManager();
    verify(suspendedJobEntityManager).create();
    verify(timerJobEntityManager).delete(isA(TimerJobEntity.class));
    verify(suspendedJobEntityManager).insert(isA(SuspendedJobEntity.class));
    Object persistentState = actualMoveJobToSuspendedJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualMoveJobToSuspendedJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("42", actualMoveJobToSuspendedJobResult.getId());
    assertEquals("42", actualMoveJobToSuspendedJobResult.getExecutionId());
    assertEquals("42", actualMoveJobToSuspendedJobResult.getProcessDefinitionId());
    assertEquals("42", actualMoveJobToSuspendedJobResult.getProcessInstanceId());
    assertEquals("42", actualMoveJobToSuspendedJobResult.getTenantId());
    assertEquals("An error occurred", actualMoveJobToSuspendedJobResult.getExceptionMessage());
    assertEquals(4, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertEquals("stacktrace", actualMoveJobToSuspendedJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualMoveJobToSuspendedJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualMoveJobToSuspendedJobResult.getJobType());
    assertEquals("stacktrace", actualMoveJobToSuspendedJobResult.getRepeat());
    assertEquals(3, actualMoveJobToSuspendedJobResult.getRevision());
    assertEquals(3, actualMoveJobToSuspendedJobResult.getMaxIterations());
    assertEquals(3, actualMoveJobToSuspendedJobResult.getRetries());
    assertEquals(4, actualMoveJobToSuspendedJobResult.getRevisionNext());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
  }

  /**
   * Test {@link DefaultJobManager#moveJobToSuspendedJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then return TenantId is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveJobToSuspendedJob(AbstractJobEntity)}
   */
  @Test
  public void testMoveJobToSuspendedJob_thenReturnTenantIdIsEmptyString() {
    // Arrange
    SuspendedJobEntityManager suspendedJobEntityManager = mock(SuspendedJobEntityManager.class);
    doNothing().when(suspendedJobEntityManager).insert(Mockito.<SuspendedJobEntity>any());
    when(suspendedJobEntityManager.create()).thenReturn(new SuspendedJobEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    SuspendedJobEntity actualMoveJobToSuspendedJobResult = defaultJobManager
        .moveJobToSuspendedJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getSuspendedJobEntityManager();
    verify(suspendedJobEntityManager).create();
    verify(suspendedJobEntityManager).insert(isA(SuspendedJobEntity.class));
    Object persistentState = actualMoveJobToSuspendedJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualMoveJobToSuspendedJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("", actualMoveJobToSuspendedJobResult.getTenantId());
    assertEquals(4, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(actualMoveJobToSuspendedJobResult.getJobHandlerConfiguration());
    assertNull(actualMoveJobToSuspendedJobResult.getJobHandlerType());
    assertNull(actualMoveJobToSuspendedJobResult.getJobType());
    assertNull(actualMoveJobToSuspendedJobResult.getRepeat());
    assertNull(actualMoveJobToSuspendedJobResult.getId());
    assertNull(actualMoveJobToSuspendedJobResult.getExceptionMessage());
    assertNull(actualMoveJobToSuspendedJobResult.getExecutionId());
    assertNull(actualMoveJobToSuspendedJobResult.getProcessDefinitionId());
    assertNull(actualMoveJobToSuspendedJobResult.getProcessInstanceId());
    assertNull(actualMoveJobToSuspendedJobResult.getEndDate());
    assertNull(actualMoveJobToSuspendedJobResult.getDuedate());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualMoveJobToSuspendedJobResult.getMaxIterations());
    assertEquals(0, actualMoveJobToSuspendedJobResult.getRetries());
    assertEquals(1, actualMoveJobToSuspendedJobResult.getRevision());
    assertEquals(2, actualMoveJobToSuspendedJobResult.getRevisionNext());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionByteArrayId"));
  }

  /**
   * Test {@link DefaultJobManager#moveJobToSuspendedJob(AbstractJobEntity)}.
   * <ul>
   *   <li>When {@link SuspendedJobEntityImpl} (default constructor) Deleted is
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveJobToSuspendedJob(AbstractJobEntity)}
   */
  @Test
  public void testMoveJobToSuspendedJob_whenSuspendedJobEntityImplDeletedIsTrue() {
    // Arrange
    SuspendedJobEntityManager suspendedJobEntityManager = mock(SuspendedJobEntityManager.class);
    doNothing().when(suspendedJobEntityManager).insert(Mockito.<SuspendedJobEntity>any());
    when(suspendedJobEntityManager.create()).thenReturn(new SuspendedJobEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    SuspendedJobEntityImpl job = new SuspendedJobEntityImpl();
    job.setDeleted(true);
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    job.setDuedate(duedate);
    job.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("stacktrace");
    job.setJobHandlerType("stacktrace");
    job.setJobType("stacktrace");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("stacktrace");
    job.setRetries(3);
    job.setRevision(3);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    SuspendedJobEntity actualMoveJobToSuspendedJobResult = defaultJobManager.moveJobToSuspendedJob(job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getSuspendedJobEntityManager();
    verify(suspendedJobEntityManager).create();
    verify(suspendedJobEntityManager).insert(isA(SuspendedJobEntity.class));
    Object persistentState = actualMoveJobToSuspendedJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualMoveJobToSuspendedJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("42", actualMoveJobToSuspendedJobResult.getId());
    assertEquals("42", actualMoveJobToSuspendedJobResult.getExecutionId());
    assertEquals("42", actualMoveJobToSuspendedJobResult.getProcessDefinitionId());
    assertEquals("42", actualMoveJobToSuspendedJobResult.getProcessInstanceId());
    assertEquals("42", actualMoveJobToSuspendedJobResult.getTenantId());
    assertEquals("An error occurred", actualMoveJobToSuspendedJobResult.getExceptionMessage());
    assertEquals(4, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertEquals("stacktrace", actualMoveJobToSuspendedJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualMoveJobToSuspendedJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualMoveJobToSuspendedJobResult.getJobType());
    assertEquals("stacktrace", actualMoveJobToSuspendedJobResult.getRepeat());
    assertEquals(3, actualMoveJobToSuspendedJobResult.getRevision());
    assertEquals(3, actualMoveJobToSuspendedJobResult.getMaxIterations());
    assertEquals(3, actualMoveJobToSuspendedJobResult.getRetries());
    assertEquals(4, actualMoveJobToSuspendedJobResult.getRevisionNext());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
  }

  /**
   * Test {@link DefaultJobManager#activateSuspendedJob(SuspendedJobEntity)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#activateSuspendedJob(SuspendedJobEntity)}
   */
  @Test
  public void testActivateSuspendedJob_thenCallsGetAsyncExecutor() {
    // Arrange
    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getTimerLockTimeInMillis()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(asyncExecutor.isActive()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(asyncExecutor);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(new JobEntityManagerImpl(
        processEngineConfiguration2, new MybatisJobDataManager(new JtaProcessEngineConfiguration())));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultJobManager.activateSuspendedJob(new SuspendedJobEntityImpl()));
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(asyncExecutor).getTimerLockTimeInMillis();
    verify(asyncExecutor).isActive();
    verify(processEngineConfiguration).getJobEntityManager();
  }

  /**
   * Test {@link DefaultJobManager#activateSuspendedJob(SuspendedJobEntity)}.
   * <ul>
   *   <li>Then calls {@link AbstractJobEntity#getJobType()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#activateSuspendedJob(SuspendedJobEntity)}
   */
  @Test
  public void testActivateSuspendedJob_thenCallsGetJobType() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(mock(JtaProcessEngineConfiguration.class));
    SuspendedJobEntity job = mock(SuspendedJobEntity.class);
    when(job.getJobType()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultJobManager.activateSuspendedJob(job));
    verify(job).getJobType();
  }

  /**
   * Test {@link DefaultJobManager#moveJobToDeadLetterJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then return TenantId is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveJobToDeadLetterJob(AbstractJobEntity)}
   */
  @Test
  public void testMoveJobToDeadLetterJob_thenReturnTenantIdIsEmptyString() {
    // Arrange
    DeadLetterJobEntityManager deadLetterJobEntityManager = mock(DeadLetterJobEntityManager.class);
    doNothing().when(deadLetterJobEntityManager).insert(Mockito.<DeadLetterJobEntity>any());
    when(deadLetterJobEntityManager.create()).thenReturn(new DeadLetterJobEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    DeadLetterJobEntity actualMoveJobToDeadLetterJobResult = defaultJobManager
        .moveJobToDeadLetterJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getDeadLetterJobEntityManager();
    verify(deadLetterJobEntityManager).create();
    verify(deadLetterJobEntityManager).insert(isA(DeadLetterJobEntity.class));
    Object persistentState = actualMoveJobToDeadLetterJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualMoveJobToDeadLetterJobResult instanceof DeadLetterJobEntityImpl);
    assertEquals("", actualMoveJobToDeadLetterJobResult.getTenantId());
    assertEquals(4, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(actualMoveJobToDeadLetterJobResult.getJobHandlerConfiguration());
    assertNull(actualMoveJobToDeadLetterJobResult.getJobHandlerType());
    assertNull(actualMoveJobToDeadLetterJobResult.getJobType());
    assertNull(actualMoveJobToDeadLetterJobResult.getRepeat());
    assertNull(actualMoveJobToDeadLetterJobResult.getId());
    assertNull(actualMoveJobToDeadLetterJobResult.getExceptionMessage());
    assertNull(actualMoveJobToDeadLetterJobResult.getExecutionId());
    assertNull(actualMoveJobToDeadLetterJobResult.getProcessDefinitionId());
    assertNull(actualMoveJobToDeadLetterJobResult.getProcessInstanceId());
    assertNull(actualMoveJobToDeadLetterJobResult.getEndDate());
    assertNull(actualMoveJobToDeadLetterJobResult.getDuedate());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualMoveJobToDeadLetterJobResult.getMaxIterations());
    assertEquals(0, actualMoveJobToDeadLetterJobResult.getRetries());
    assertEquals(1, actualMoveJobToDeadLetterJobResult.getRevision());
    assertEquals(2, actualMoveJobToDeadLetterJobResult.getRevisionNext());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionByteArrayId"));
  }

  /**
   * Test {@link DefaultJobManager#moveJobToDeadLetterJob(AbstractJobEntity)}.
   * <ul>
   *   <li>When {@link TimerJobEntityImpl} (default constructor) Deleted is
   * {@code true}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveJobToDeadLetterJob(AbstractJobEntity)}
   */
  @Test
  public void testMoveJobToDeadLetterJob_whenTimerJobEntityImplDeletedIsTrue_thenReturnIdIs42() {
    // Arrange
    DeadLetterJobEntityManager deadLetterJobEntityManager = mock(DeadLetterJobEntityManager.class);
    doNothing().when(deadLetterJobEntityManager).insert(Mockito.<DeadLetterJobEntity>any());
    when(deadLetterJobEntityManager.create()).thenReturn(new DeadLetterJobEntityImpl());
    TimerJobEntityManager timerJobEntityManager = mock(TimerJobEntityManager.class);
    doNothing().when(timerJobEntityManager).delete(Mockito.<TimerJobEntity>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getTimerJobEntityManager()).thenReturn(timerJobEntityManager);
    when(processEngineConfiguration.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManager);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    TimerJobEntityImpl job = new TimerJobEntityImpl();
    job.setDeleted(true);
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    job.setDuedate(duedate);
    job.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("stacktrace");
    job.setJobHandlerType("stacktrace");
    job.setJobType("stacktrace");
    job.setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("stacktrace");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("stacktrace");
    job.setRetries(3);
    job.setRevision(3);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    DeadLetterJobEntity actualMoveJobToDeadLetterJobResult = defaultJobManager.moveJobToDeadLetterJob(job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getDeadLetterJobEntityManager();
    verify(processEngineConfiguration).getTimerJobEntityManager();
    verify(deadLetterJobEntityManager).create();
    verify(timerJobEntityManager).delete(isA(TimerJobEntity.class));
    verify(deadLetterJobEntityManager).insert(isA(DeadLetterJobEntity.class));
    Object persistentState = actualMoveJobToDeadLetterJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualMoveJobToDeadLetterJobResult instanceof DeadLetterJobEntityImpl);
    assertEquals("42", actualMoveJobToDeadLetterJobResult.getId());
    assertEquals("42", actualMoveJobToDeadLetterJobResult.getExecutionId());
    assertEquals("42", actualMoveJobToDeadLetterJobResult.getProcessDefinitionId());
    assertEquals("42", actualMoveJobToDeadLetterJobResult.getProcessInstanceId());
    assertEquals("42", actualMoveJobToDeadLetterJobResult.getTenantId());
    assertEquals("An error occurred", actualMoveJobToDeadLetterJobResult.getExceptionMessage());
    assertEquals(4, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertEquals("stacktrace", actualMoveJobToDeadLetterJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualMoveJobToDeadLetterJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualMoveJobToDeadLetterJobResult.getJobType());
    assertEquals("stacktrace", actualMoveJobToDeadLetterJobResult.getRepeat());
    assertEquals(3, actualMoveJobToDeadLetterJobResult.getRevision());
    assertEquals(3, actualMoveJobToDeadLetterJobResult.getMaxIterations());
    assertEquals(3, actualMoveJobToDeadLetterJobResult.getRetries());
    assertEquals(4, actualMoveJobToDeadLetterJobResult.getRevisionNext());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
  }

  /**
   * Test
   * {@link DefaultJobManager#moveDeadLetterJobToExecutableJob(DeadLetterJobEntity, int)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#moveDeadLetterJobToExecutableJob(DeadLetterJobEntity, int)}
   */
  @Test
  public void testMoveDeadLetterJobToExecutableJob_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultJobManager.moveDeadLetterJobToExecutableJob(null, 1));
  }

  /**
   * Test {@link DefaultJobManager#execute(Job)}.
   * <ul>
   *   <li>Given {@link DefaultJobManager#DefaultJobManager()}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#execute(Job)}
   */
  @Test
  public void testExecute_givenDefaultJobManager_thenThrowActivitiException() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultJobManager.execute(new DeadLetterJobEntityImpl()));
  }

  /**
   * Test {@link DefaultJobManager#unacquire(Job)}.
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#unacquire(Job)}
   */
  @Test
  public void testUnacquire_givenExecutionDataManagerFindByIdReturnNull() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.create()).thenReturn(new JobEntityImpl());
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findById(Mockito.<String>any())).thenReturn(new JobEntityImpl());
    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getJobEntityManager())
        .thenReturn(new JobEntityManagerImpl(processEngineConfiguration, jobDataManager));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration2);

    JobEntityImpl job = new JobEntityImpl();
    job.setDeleted(true);
    job.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("Job Handler Configuration");
    job.setJobHandlerType("Job Handler Type");
    job.setJobType("Job Type");
    job.setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("Claimed By");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("Repeat");
    job.setRetries(1);
    job.setRevision(1);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    defaultJobManager.unacquire(job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(jobDataManager).create();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultJobManager#unacquire(Job)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#unacquire(Job)}
   */
  @Test
  public void testUnacquire_thenCallsGetPerformanceSettings() {
    // Arrange
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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).insert(Mockito.<JobEntity>any());
    when(jobDataManager.create()).thenReturn(new JobEntityImpl());
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findById(Mockito.<String>any())).thenReturn(new JobEntityImpl());
    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getJobEntityManager())
        .thenReturn(new JobEntityManagerImpl(processEngineConfiguration, jobDataManager));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration2);

    JobEntityImpl job = new JobEntityImpl();
    job.setDeleted(true);
    job.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("Job Handler Configuration");
    job.setJobHandlerType("Job Handler Type");
    job.setJobType("Job Type");
    job.setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("Claimed By");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("Repeat");
    job.setRetries(1);
    job.setRevision(1);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    defaultJobManager.unacquire(job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobDataManager).create();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
  }

  /**
   * Test {@link DefaultJobManager#unacquire(Job)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#unacquire(Job)}
   */
  @Test
  public void testUnacquire_thenCallsIsEnabled() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
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
    when(jobDataManager.create()).thenReturn(new JobEntityImpl());
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findById(Mockito.<String>any())).thenReturn(new JobEntityImpl());
    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getJobEntityManager())
        .thenReturn(new JobEntityManagerImpl(processEngineConfiguration, jobDataManager));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration2);

    JobEntityImpl job = new JobEntityImpl();
    job.setDeleted(true);
    job.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("Job Handler Configuration");
    job.setJobHandlerType("Job Handler Type");
    job.setJobType("Job Type");
    job.setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("Claimed By");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("Repeat");
    job.setRetries(1);
    job.setRevision(1);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    defaultJobManager.unacquire(job);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(jobDataManager).create();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(executionDataManager).findById(eq("42"));
    verify(jobDataManager).findById(eq("42"));
    verify(jobDataManager).insert(isA(JobEntity.class));
  }

  /**
   * Test {@link DefaultJobManager#unacquire(Job)}.
   * <ul>
   *   <li>Then calls {@link JobDataManager#resetExpiredJob(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#unacquire(Job)}
   */
  @Test
  public void testUnacquire_thenCallsResetExpiredJob() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).resetExpiredJob(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobEntityManager())
        .thenReturn(new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultJobManager.unacquire(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration).getJobEntityManager();
    verify(jobDataManager).resetExpiredJob(isNull());
  }

  /**
   * Test {@link DefaultJobManager#executeMessageJob(JobEntity)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#executeMessageJob(JobEntity)}
   */
  @Test
  public void testExecuteMessageJob_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(mock(JtaProcessEngineConfiguration.class));
    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getExecutionId()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultJobManager.executeMessageJob(jobEntity));
    verify(jobEntity).getExecutionId();
  }

  /**
   * Test {@link DefaultJobManager#executeJobHandler(JobEntity)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#executeJobHandler(JobEntity)}
   */
  @Test
  public void testExecuteJobHandler_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(mock(JtaProcessEngineConfiguration.class));
    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getExecutionId()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultJobManager.executeJobHandler(jobEntity));
    verify(jobEntity).getExecutionId();
  }

  /**
   * Test {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}.
   * <ul>
   *   <li>Given {@code timer-start-event}.</li>
   *   <li>Then {@link JobEntityImpl} (default constructor) MaxIterations is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}
   */
  @Test
  public void testRestoreExtraData_givenTimerStartEvent_thenJobEntityImplMaxIterationsIsOne() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    JobEntityImpl timerEntity = new JobEntityImpl();
    timerEntity.setDeleted(true);
    timerEntity.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setExceptionMessage("An error occurred");
    timerEntity.setExclusive(true);
    timerEntity.setExecutionId("42");
    timerEntity.setId("42");
    timerEntity.setInserted(true);
    timerEntity.setJobHandlerConfiguration("Job Handler Configuration");
    timerEntity.setJobType("Job Type");
    timerEntity
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setLockOwner("Claimed By");
    timerEntity.setMaxIterations(3);
    timerEntity.setProcessInstanceId("42");
    timerEntity.setRepeat("Repeat");
    timerEntity.setRetries(1);
    timerEntity.setRevision(1);
    timerEntity.setTenantId("42");
    timerEntity.setUpdated(true);
    timerEntity.setJobHandlerType("timer-start-event");
    timerEntity.setEndDate(null);
    timerEntity.setProcessDefinitionId(null);

    // Act
    defaultJobManager.restoreExtraData(timerEntity, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertEquals(1, timerEntity.getMaxIterations());
  }

  /**
   * Test {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}.
   * <ul>
   *   <li>Given {@code trigger-timer}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}
   */
  @Test
  public void testRestoreExtraData_givenTriggerTimer() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();

    JobEntityImpl timerEntity = new JobEntityImpl();
    timerEntity.setDeleted(true);
    timerEntity.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setExceptionMessage("An error occurred");
    timerEntity.setExclusive(true);
    timerEntity.setExecutionId("42");
    timerEntity.setId("42");
    timerEntity.setInserted(true);
    timerEntity.setJobHandlerConfiguration("Job Handler Configuration");
    timerEntity.setJobType("Job Type");
    timerEntity
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerEntity.setLockOwner("Claimed By");
    timerEntity.setMaxIterations(3);
    timerEntity.setProcessInstanceId("42");
    timerEntity.setRepeat("Repeat");
    timerEntity.setRetries(1);
    timerEntity.setRevision(1);
    timerEntity.setTenantId("42");
    timerEntity.setUpdated(true);
    timerEntity.setJobHandlerType("trigger-timer");
    timerEntity.setEndDate(null);
    timerEntity.setProcessDefinitionId(null);

    // Act
    defaultJobManager.restoreExtraData(timerEntity, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertEquals(1, timerEntity.getMaxIterations());
  }

  /**
   * Test {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#restoreExtraData(JobEntity, VariableScope)}
   */
  @Test
  public void testRestoreExtraData_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.getProcessDefinitionId()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");
    when(timerEntity.getJobHandlerType()).thenReturn("Job Handler Type");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultJobManager.restoreExtraData(timerEntity, NoExecutionVariableScope.getSharedInstance()));
    verify(timerEntity).getJobHandlerConfiguration();
    verify(timerEntity, atLeast(1)).getJobHandlerType();
    verify(timerEntity).getProcessDefinitionId();
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  public void testGetMaxIterations_givenAdhocSubProcess() {
    // Arrange
    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(new AdhocSubProcess());

    // Act
    int actualMaxIterations = defaultJobManager.getMaxIterations(process, "42");

    // Assert
    verify(process).getFlowElement(eq("42"), eq(true));
    assertEquals(-1, actualMaxIterations);
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  public void testGetMaxIterations_givenBoundaryEvent() {
    // Arrange
    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(new BoundaryEvent());

    // Act
    int actualMaxIterations = defaultJobManager.getMaxIterations(process, "42");

    // Assert
    verify(process).getFlowElement(eq("42"), eq(true));
    assertEquals(-1, actualMaxIterations);
  }

  /**
   * Test {@link DefaultJobManager#getMaxIterations(Process, String)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#getMaxIterations(Process, String)}
   */
  @Test
  public void testGetMaxIterations_whenProcess_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1, defaultJobManager.getMaxIterations(new Process(), "42"));
  }

  /**
   * Test {@link DefaultJobManager#calculateMaxIterationsValue(String)}.
   * <ul>
   *   <li>When {@code Original Expression}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#calculateMaxIterationsValue(String)}
   */
  @Test
  public void testCalculateMaxIterationsValue_whenOriginalExpression() {
    // Arrange, Act and Assert
    assertEquals(Integer.MAX_VALUE, defaultJobManager.calculateMaxIterationsValue("Original Expression"));
    assertEquals(Integer.MAX_VALUE, defaultJobManager.calculateMaxIterationsValue("/Original Expression"));
  }

  /**
   * Test {@link DefaultJobManager#isValidTime(JobEntity, Date, VariableScope)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#isValidTime(JobEntity, Date, VariableScope)}
   */
  @Test
  public void testIsValidTime_thenReturnTrue() {
    // Arrange
    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);
    when(businessCalendarManager.getBusinessCalendar(Mockito.<String>any())).thenReturn(new DefaultBusinessCalendar());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(businessCalendarManager);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);
    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.getMaxIterations()).thenReturn(3);
    when(timerEntity.getRepeat()).thenReturn("Repeat");
    when(timerEntity.getEndDate())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");
    Date newTimerDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    boolean actualIsValidTimeResult = defaultJobManager.isValidTime(timerEntity, newTimerDate,
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
   * Test {@link DefaultJobManager#isValidTime(JobEntity, Date, VariableScope)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#isValidTime(JobEntity, Date, VariableScope)}
   */
  @Test
  public void testIsValidTime_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);
    when(businessCalendarManager.getBusinessCalendar(Mockito.<String>any())).thenReturn(new DefaultBusinessCalendar());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(businessCalendarManager);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);
    JobEntity timerEntity = mock(JobEntity.class);
    when(timerEntity.getRepeat()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(timerEntity.getJobHandlerConfiguration()).thenReturn("Job Handler Configuration");
    Date newTimerDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultJobManager.isValidTime(timerEntity, newTimerDate, NoExecutionVariableScope.getSharedInstance()));
    verify(businessCalendarManager).getBusinessCalendar(eq("cycle"));
    verify(timerEntity).getJobHandlerConfiguration();
    verify(timerEntity).getRepeat();
  }

  /**
   * Test
   * {@link DefaultJobManager#getBusinessCalendarName(String, VariableScope)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code cycle}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#getBusinessCalendarName(String, VariableScope)}
   */
  @Test
  public void testGetBusinessCalendarName_whenEmptyString_thenReturnCycle() {
    // Arrange, Act and Assert
    assertEquals("cycle", defaultJobManager.getBusinessCalendarName("", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test
   * {@link DefaultJobManager#internalCreateAsyncJob(ExecutionEntity, boolean)}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#internalCreateAsyncJob(ExecutionEntity, boolean)}
   */
  @Test
  public void testInternalCreateAsyncJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobEntityManager(new JobEntityManagerImpl(processEngineConfiguration2,
        new MybatisJobDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualInternalCreateAsyncJobResult = defaultJobManager
        .internalCreateAsyncJob(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    Object persistentState = actualInternalCreateAsyncJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualInternalCreateAsyncJobResult instanceof JobEntityImpl);
    assertEquals("", actualInternalCreateAsyncJobResult.getTenantId());
    assertEquals("async-continuation", actualInternalCreateAsyncJobResult.getJobHandlerType());
    assertEquals("message", actualInternalCreateAsyncJobResult.getJobType());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(((Map<String, Integer>) persistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Integer>) persistentState).get("lockOwner"));
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
    assertEquals(3, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(3, actualInternalCreateAsyncJobResult.getRetries());
    assertFalse(actualInternalCreateAsyncJobResult.isDeleted());
    assertFalse(actualInternalCreateAsyncJobResult.isInserted());
    assertFalse(actualInternalCreateAsyncJobResult.isUpdated());
    assertTrue(actualInternalCreateAsyncJobResult.isExclusive());
  }

  /**
   * Test
   * {@link DefaultJobManager#internalCreateLockedAsyncJob(ExecutionEntity, boolean)}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#internalCreateLockedAsyncJob(ExecutionEntity, boolean)}
   */
  @Test
  public void testInternalCreateLockedAsyncJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getAsyncExecutorNumberOfRetries()).thenReturn(10);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(new JobEntityManagerImpl(
        processEngineConfiguration2, new MybatisJobDataManager(new JtaProcessEngineConfiguration())));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualInternalCreateLockedAsyncJobResult = defaultJobManager
        .internalCreateLockedAsyncJob(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(processEngineConfiguration).getAsyncExecutorNumberOfRetries();
    verify(processEngineConfiguration).getJobEntityManager();
    Object persistentState = actualInternalCreateLockedAsyncJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualInternalCreateLockedAsyncJobResult instanceof JobEntityImpl);
    assertEquals("", actualInternalCreateLockedAsyncJobResult.getTenantId());
    assertEquals("async-continuation", actualInternalCreateLockedAsyncJobResult.getJobHandlerType());
    assertEquals("message", actualInternalCreateLockedAsyncJobResult.getJobType());
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("duedate"));
    assertNull(((Map<String, Object>) persistentState).get("exceptionMessage"));
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
    assertFalse(actualInternalCreateLockedAsyncJobResult.isDeleted());
    assertFalse(actualInternalCreateLockedAsyncJobResult.isInserted());
    assertFalse(actualInternalCreateLockedAsyncJobResult.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertTrue(actualInternalCreateLockedAsyncJobResult.isExclusive());
    Date expectedGetResult = actualInternalCreateLockedAsyncJobResult.getLockExpirationTime();
    assertSame(expectedGetResult, ((Map<String, Object>) persistentState).get("lockExpirationTime"));
  }

  /**
   * Test
   * {@link DefaultJobManager#fillDefaultAsyncJobInfo(JobEntity, ExecutionEntity, boolean)}.
   * <ul>
   *   <li>Then {@link JobEntityImpl} (default constructor) PersistentState
   * {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#fillDefaultAsyncJobInfo(JobEntity, ExecutionEntity, boolean)}
   */
  @Test
  public void testFillDefaultAsyncJobInfo_thenJobEntityImplPersistentStateMap() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    JobEntityImpl jobEntity = new JobEntityImpl();

    // Act
    defaultJobManager.fillDefaultAsyncJobInfo(jobEntity, ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        true);

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
   * Test
   * {@link DefaultJobManager#fillDefaultAsyncJobInfo(JobEntity, ExecutionEntity, boolean)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#fillDefaultAsyncJobInfo(JobEntity, ExecutionEntity, boolean)}
   */
  @Test
  public void testFillDefaultAsyncJobInfo_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    JobEntity jobEntity = mock(JobEntity.class);
    doThrow(new ActivitiIllegalArgumentException("An error occurred")).when(jobEntity).setRetries(anyInt());
    doNothing().when(jobEntity).setRevision(anyInt());
    doNothing().when(jobEntity).setJobType(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultJobManager.fillDefaultAsyncJobInfo(jobEntity,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));
    verify(jobEntity).setRevision(eq(1));
    verify(jobEntity).setJobType(eq("message"));
    verify(jobEntity).setRetries(eq(3));
  }

  /**
   * Test
   * {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}.
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateExecutableJobFromOtherJob() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(new JobEntityManagerImpl(
        processEngineConfiguration2, new MybatisJobDataManager(new JtaProcessEngineConfiguration())));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualCreateExecutableJobFromOtherJobResult = defaultJobManager
        .createExecutableJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getJobEntityManager();
    Object persistentState = actualCreateExecutableJobFromOtherJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateExecutableJobFromOtherJobResult instanceof JobEntityImpl);
    assertEquals(6, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Integer>) persistentState).get("lockOwner"));
    assertNull(actualCreateExecutableJobFromOtherJobResult.getLockOwner());
    assertNull(actualCreateExecutableJobFromOtherJobResult.getLockExpirationTime());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
  }

  /**
   * Test
   * {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then calls {@link AbstractEntity#setId(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateExecutableJobFromOtherJob_thenCallsSetId() {
    // Arrange
    JobEntityImpl jobEntityImpl = mock(JobEntityImpl.class);
    doNothing().when(jobEntityImpl).setId(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setRevision(anyInt());
    doNothing().when(jobEntityImpl).setDuedate(Mockito.<Date>any());
    doNothing().when(jobEntityImpl).setEndDate(Mockito.<Date>any());
    doNothing().when(jobEntityImpl).setExceptionMessage(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setExceptionStacktrace(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setExclusive(anyBoolean());
    doNothing().when(jobEntityImpl).setExecutionId(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setJobHandlerConfiguration(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setJobHandlerType(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setJobType(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setMaxIterations(anyInt());
    doNothing().when(jobEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setRepeat(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setRetries(anyInt());
    doNothing().when(jobEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(jobEntityImpl).setLockExpirationTime(Mockito.<Date>any());
    doNothing().when(jobEntityImpl).setLockOwner(Mockito.<String>any());
    MybatisJobDataManager jobDataManager = mock(MybatisJobDataManager.class);
    when(jobDataManager.create()).thenReturn(jobEntityImpl);
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager);

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getTimerLockTimeInMillis()).thenReturn(1);
    when(asyncExecutor.getLockOwner()).thenReturn("Lock Owner");
    when(asyncExecutor.isActive()).thenReturn(true);
    Clock clock = mock(Clock.class);
    when(clock.getCurrentTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(clock);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(asyncExecutor);
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManagerImpl);

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultJobManager.createExecutableJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(asyncExecutor).getLockOwner();
    verify(asyncExecutor).getTimerLockTimeInMillis();
    verify(asyncExecutor).isActive();
    verify(processEngineConfiguration).getJobEntityManager();
    verify(jobEntityImpl).setId(isNull());
    verify(jobEntityImpl).setRevision(eq(1));
    verify(jobEntityImpl).setDuedate(isNull());
    verify(jobEntityImpl).setEndDate(isNull());
    verify(jobEntityImpl).setExceptionMessage(isNull());
    verify(jobEntityImpl).setExceptionStacktrace(isNull());
    verify(jobEntityImpl).setExclusive(eq(true));
    verify(jobEntityImpl).setExecutionId(isNull());
    verify(jobEntityImpl).setJobHandlerConfiguration(isNull());
    verify(jobEntityImpl).setJobHandlerType(isNull());
    verify(jobEntityImpl).setJobType(isNull());
    verify(jobEntityImpl).setMaxIterations(eq(0));
    verify(jobEntityImpl).setProcessDefinitionId(isNull());
    verify(jobEntityImpl).setProcessInstanceId(isNull());
    verify(jobEntityImpl).setRepeat(isNull());
    verify(jobEntityImpl).setRetries(eq(0));
    verify(jobEntityImpl).setTenantId(eq(""));
    verify(jobEntityImpl).setLockExpirationTime(isA(Date.class));
    verify(jobEntityImpl).setLockOwner(eq("Lock Owner"));
    verify(jobDataManager).create();
    verify(clock).getCurrentTime();
  }

  /**
   * Test
   * {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then return {@code Lock Owner}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateExecutableJobFromOtherJob_thenReturnLockOwner() {
    // Arrange
    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getTimerLockTimeInMillis()).thenReturn(1);
    when(asyncExecutor.getLockOwner()).thenReturn("Lock Owner");
    when(asyncExecutor.isActive()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(asyncExecutor);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(new JobEntityManagerImpl(
        processEngineConfiguration2, new MybatisJobDataManager(new JtaProcessEngineConfiguration())));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    JobEntity actualCreateExecutableJobFromOtherJobResult = defaultJobManager
        .createExecutableJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(asyncExecutor).getLockOwner();
    verify(asyncExecutor).getTimerLockTimeInMillis();
    verify(asyncExecutor).isActive();
    verify(processEngineConfiguration).getJobEntityManager();
    Object persistentState = actualCreateExecutableJobFromOtherJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateExecutableJobFromOtherJobResult instanceof JobEntityImpl);
    assertEquals("Lock Owner", actualCreateExecutableJobFromOtherJobResult.getLockOwner());
    assertEquals(6, ((Map<String, Object>) persistentState).size());
    assertEquals("Lock Owner", ((Map<String, Object>) persistentState).get("lockOwner"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    Date expectedGetResult = actualCreateExecutableJobFromOtherJobResult.getLockExpirationTime();
    assertSame(expectedGetResult, ((Map<String, Object>) persistentState).get("lockExpirationTime"));
  }

  /**
   * Test
   * {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createExecutableJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateExecutableJobFromOtherJob_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getTimerLockTimeInMillis()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(asyncExecutor.isActive()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(asyncExecutor);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(new JobEntityManagerImpl(
        processEngineConfiguration2, new MybatisJobDataManager(new JtaProcessEngineConfiguration())));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultJobManager.createExecutableJobFromOtherJob(new DeadLetterJobEntityImpl()));
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(asyncExecutor).getTimerLockTimeInMillis();
    verify(asyncExecutor).isActive();
    verify(processEngineConfiguration).getJobEntityManager();
  }

  /**
   * Test {@link DefaultJobManager#createTimerJobFromOtherJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Given {@code stacktrace}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createTimerJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateTimerJobFromOtherJob_givenStacktrace_thenReturnIdIs42() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTimerJobEntityManager(new TimerJobEntityManagerImpl(processEngineConfiguration2,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    TimerJobEntityImpl otherJob = new TimerJobEntityImpl();
    otherJob.setDeleted(true);
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    otherJob.setDuedate(duedate);
    otherJob.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    otherJob.setExceptionMessage("An error occurred");
    otherJob.setExclusive(true);
    otherJob.setExecutionId("42");
    otherJob.setId("42");
    otherJob.setInserted(true);
    otherJob.setJobHandlerConfiguration("stacktrace");
    otherJob.setJobHandlerType("stacktrace");
    otherJob.setJobType("stacktrace");
    otherJob
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
    TimerJobEntity actualCreateTimerJobFromOtherJobResult = defaultJobManager.createTimerJobFromOtherJob(otherJob);

    // Assert
    Object persistentState = actualCreateTimerJobFromOtherJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateTimerJobFromOtherJobResult instanceof TimerJobEntityImpl);
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getId());
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getExecutionId());
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getProcessDefinitionId());
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getProcessInstanceId());
    assertEquals("42", actualCreateTimerJobFromOtherJobResult.getTenantId());
    assertEquals("An error occurred", actualCreateTimerJobFromOtherJobResult.getExceptionMessage());
    assertEquals(6, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertEquals("stacktrace", actualCreateTimerJobFromOtherJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualCreateTimerJobFromOtherJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualCreateTimerJobFromOtherJobResult.getJobType());
    assertEquals("stacktrace", actualCreateTimerJobFromOtherJobResult.getRepeat());
    assertEquals(1, actualCreateTimerJobFromOtherJobResult.getRetries());
    assertEquals(3, actualCreateTimerJobFromOtherJobResult.getMaxIterations());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
  }

  /**
   * Test {@link DefaultJobManager#createTimerJobFromOtherJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then return TenantId is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createTimerJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateTimerJobFromOtherJob_thenReturnTenantIdIsEmptyString() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTimerJobEntityManager(new TimerJobEntityManagerImpl(processEngineConfiguration2,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    TimerJobEntity actualCreateTimerJobFromOtherJobResult = defaultJobManager
        .createTimerJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateTimerJobFromOtherJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateTimerJobFromOtherJobResult instanceof TimerJobEntityImpl);
    assertEquals("", actualCreateTimerJobFromOtherJobResult.getTenantId());
    assertEquals(6, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
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
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateTimerJobFromOtherJobResult.getMaxIterations());
    assertEquals(0, actualCreateTimerJobFromOtherJobResult.getRetries());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
  }

  /**
   * Test
   * {@link DefaultJobManager#createSuspendedJobFromOtherJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Given {@code stacktrace}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createSuspendedJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateSuspendedJobFromOtherJob_givenStacktrace_thenReturnIdIs42() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSuspendedJobEntityManager(new SuspendedJobEntityManagerImpl(
        processEngineConfiguration2, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    SuspendedJobEntityImpl otherJob = new SuspendedJobEntityImpl();
    otherJob.setDeleted(true);
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    otherJob.setDuedate(duedate);
    otherJob.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
    SuspendedJobEntity actualCreateSuspendedJobFromOtherJobResult = defaultJobManager
        .createSuspendedJobFromOtherJob(otherJob);

    // Assert
    Object persistentState = actualCreateSuspendedJobFromOtherJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateSuspendedJobFromOtherJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getId());
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getExecutionId());
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getProcessDefinitionId());
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getProcessInstanceId());
    assertEquals("42", actualCreateSuspendedJobFromOtherJobResult.getTenantId());
    assertEquals("An error occurred", actualCreateSuspendedJobFromOtherJobResult.getExceptionMessage());
    assertEquals(4, ((Map<String, Object>) persistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) persistentState).get("exceptionMessage"));
    assertEquals("stacktrace", actualCreateSuspendedJobFromOtherJobResult.getJobHandlerConfiguration());
    assertEquals("stacktrace", actualCreateSuspendedJobFromOtherJobResult.getJobHandlerType());
    assertEquals("stacktrace", actualCreateSuspendedJobFromOtherJobResult.getJobType());
    assertEquals("stacktrace", actualCreateSuspendedJobFromOtherJobResult.getRepeat());
    assertEquals(1, actualCreateSuspendedJobFromOtherJobResult.getRetries());
    assertEquals(3, actualCreateSuspendedJobFromOtherJobResult.getMaxIterations());
    assertTrue(((Map<String, Object>) persistentState).containsKey("exceptionByteArrayId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
  }

  /**
   * Test
   * {@link DefaultJobManager#createSuspendedJobFromOtherJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then return TenantId is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createSuspendedJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateSuspendedJobFromOtherJob_thenReturnTenantIdIsEmptyString() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSuspendedJobEntityManager(new SuspendedJobEntityManagerImpl(
        processEngineConfiguration2, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    SuspendedJobEntity actualCreateSuspendedJobFromOtherJobResult = defaultJobManager
        .createSuspendedJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateSuspendedJobFromOtherJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateSuspendedJobFromOtherJobResult instanceof SuspendedJobEntityImpl);
    assertEquals("", actualCreateSuspendedJobFromOtherJobResult.getTenantId());
    assertEquals(4, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
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
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateSuspendedJobFromOtherJobResult.getMaxIterations());
    assertEquals(0, actualCreateSuspendedJobFromOtherJobResult.getRetries());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionByteArrayId"));
  }

  /**
   * Test
   * {@link DefaultJobManager#createDeadLetterJobFromOtherJob(AbstractJobEntity)}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#createDeadLetterJobFromOtherJob(AbstractJobEntity)}
   */
  @Test
  public void testCreateDeadLetterJobFromOtherJob_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeadLetterJobEntityManager(new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    DeadLetterJobEntity actualCreateDeadLetterJobFromOtherJobResult = defaultJobManager
        .createDeadLetterJobFromOtherJob(new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = actualCreateDeadLetterJobFromOtherJobResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateDeadLetterJobFromOtherJobResult instanceof DeadLetterJobEntityImpl);
    assertEquals("", actualCreateDeadLetterJobFromOtherJobResult.getTenantId());
    ByteArrayRef exceptionByteArrayRef = actualCreateDeadLetterJobFromOtherJobResult.getExceptionByteArrayRef();
    assertEquals("stacktrace", exceptionByteArrayRef.getName());
    assertNull(exceptionByteArrayRef.getBytes());
    assertEquals(4, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionByteArrayId"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getExceptionStacktrace());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getJobHandlerConfiguration());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getJobHandlerType());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getJobType());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getRepeat());
    assertNull(exceptionByteArrayRef.getId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getExceptionMessage());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getExecutionId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getProcessDefinitionId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getProcessInstanceId());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getEndDate());
    assertNull(actualCreateDeadLetterJobFromOtherJobResult.getDuedate());
    assertNull(exceptionByteArrayRef.getEntity());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateDeadLetterJobFromOtherJobResult.getMaxIterations());
    assertEquals(0, actualCreateDeadLetterJobFromOtherJobResult.getRetries());
    assertEquals(1, actualCreateDeadLetterJobFromOtherJobResult.getRevision());
    assertEquals(2, actualCreateDeadLetterJobFromOtherJobResult.getRevisionNext());
    assertFalse(exceptionByteArrayRef.isDeleted());
    assertFalse(actualCreateDeadLetterJobFromOtherJobResult.isDeleted());
    assertFalse(actualCreateDeadLetterJobFromOtherJobResult.isInserted());
    assertFalse(actualCreateDeadLetterJobFromOtherJobResult.isUpdated());
    assertTrue(actualCreateDeadLetterJobFromOtherJobResult.isExclusive());
  }

  /**
   * Test
   * {@link DefaultJobManager#copyJobInfo(AbstractJobEntity, AbstractJobEntity)}.
   * <ul>
   *   <li>Then {@link DeadLetterJobEntityImpl} (default constructor)
   * PersistentState {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#copyJobInfo(AbstractJobEntity, AbstractJobEntity)}
   */
  @Test
  public void testCopyJobInfo_thenDeadLetterJobEntityImplPersistentStateMap() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    DeadLetterJobEntityImpl copyToJob = new DeadLetterJobEntityImpl();

    // Act
    AbstractJobEntity actualCopyJobInfoResult = defaultJobManager.copyJobInfo(copyToJob, new DeadLetterJobEntityImpl());

    // Assert
    Object persistentState = copyToJob.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(4, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("exceptionByteArrayId"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertSame(copyToJob, actualCopyJobInfoResult);
  }

  /**
   * Test
   * {@link DefaultJobManager#copyJobInfo(AbstractJobEntity, AbstractJobEntity)}.
   * <ul>
   *   <li>Then return {@link AbstractJobEntity}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultJobManager#copyJobInfo(AbstractJobEntity, AbstractJobEntity)}
   */
  @Test
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
    AbstractJobEntity actualCopyJobInfoResult = defaultJobManager.copyJobInfo(copyToJob, new DeadLetterJobEntityImpl());

    // Assert
    verify(copyToJob).setRevision(eq(1));
    verify(copyToJob).setDuedate(isNull());
    verify(copyToJob).setEndDate(isNull());
    verify(copyToJob).setExceptionMessage(isNull());
    verify(copyToJob).setExceptionStacktrace(isNull());
    verify(copyToJob).setExclusive(eq(true));
    verify(copyToJob).setExecutionId(isNull());
    verify(copyToJob).setJobHandlerConfiguration(isNull());
    verify(copyToJob).setJobHandlerType(isNull());
    verify(copyToJob).setJobType(isNull());
    verify(copyToJob).setMaxIterations(eq(0));
    verify(copyToJob).setProcessDefinitionId(isNull());
    verify(copyToJob).setProcessInstanceId(isNull());
    verify(copyToJob).setRepeat(isNull());
    verify(copyToJob).setRetries(eq(0));
    verify(copyToJob).setTenantId(eq(""));
    verify(copyToJob).setId(isNull());
    assertSame(copyToJob, actualCopyJobInfoResult);
  }

  /**
   * Test {@link DefaultJobManager#isAsyncExecutorActive()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#isAsyncExecutorActive()}
   */
  @Test
  public void testIsAsyncExecutorActive_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutor(new DefaultAsyncJobExecutor());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertFalse(defaultJobManager.isAsyncExecutorActive());
  }

  /**
   * Test {@link DefaultJobManager#getCommandContext()}.
   * <p>
   * Method under test: {@link DefaultJobManager#getCommandContext()}
   */
  @Test
  public void testGetCommandContext() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new DefaultJobManager(processEngineConfiguration)).getCommandContext());
  }

  /**
   * Test {@link DefaultJobManager#getCommandContext()}.
   * <ul>
   *   <li>Given {@link DefaultJobManager#DefaultJobManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#getCommandContext()}
   */
  @Test
  public void testGetCommandContext_givenDefaultJobManager() {
    // Arrange, Act and Assert
    assertNull((new DefaultJobManager()).getCommandContext());
  }

  /**
   * Test {@link DefaultJobManager#getAsyncExecutor()}.
   * <p>
   * Method under test: {@link DefaultJobManager#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertNull(defaultJobManager.getAsyncExecutor());
  }

  /**
   * Test {@link DefaultJobManager#getAsyncExecutor()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor_thenReturnNull() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(defaultJobManager.getAsyncExecutor());
  }

  /**
   * Test {@link DefaultJobManager#getExecutionEntityManager()}.
   * <p>
   * Method under test: {@link DefaultJobManager#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(processEngineConfiguration);

    // Act and Assert
    assertNull(defaultJobManager.getExecutionEntityManager());
  }

  /**
   * Test {@link DefaultJobManager#getExecutionEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultJobManager#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager_thenReturnNull() {
    // Arrange
    DefaultJobManager defaultJobManager = new DefaultJobManager();
    defaultJobManager.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(defaultJobManager.getExecutionEntityManager());
  }
}
