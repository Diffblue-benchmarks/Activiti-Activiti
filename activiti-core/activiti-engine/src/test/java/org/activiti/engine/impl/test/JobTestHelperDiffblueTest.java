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
package org.activiti.engine.impl.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.JobQueryImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Test;
import org.mockito.Mockito;

public class JobTestHelperDiffblueTest {
  /**
   * Test
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobs(ActivitiRule, long, long)}
   * with {@code activitiRule}, {@code maxMillisToWait}, {@code intervalMillis}.
   * <p>
   * Method under test:
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobs(ActivitiRule, long, long)}
   */
  @Test
  public void testWaitForJobExecutorToProcessAllJobsWithActivitiRuleMaxMillisToWaitIntervalMillis() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener()).thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    ProcessEngineImpl processEngineImpl = new ProcessEngineImpl(processEngineConfiguration);
    ActivitiRule activitiRule = mock(ActivitiRule.class);
    when(activitiRule.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(activitiRule.getProcessEngine()).thenReturn(processEngineImpl);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> JobTestHelper.waitForJobExecutorToProcessAllJobs(activitiRule, 1L, 42L));
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(activitiRule).getManagementService();
    verify(activitiRule).getProcessEngine();
  }

  /**
   * Test
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobs(ProcessEngineConfiguration, ManagementService, long, long)}
   * with {@code processEngineConfiguration}, {@code managementService},
   * {@code maxMillisToWait}, {@code intervalMillis}.
   * <p>
   * Method under test:
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobs(ProcessEngineConfiguration, ManagementService, long, long)}
   */
  @Test
  public void testWaitForJobExecutorToProcessAllJobsWithProcessEngineConfigurationManagementServiceMaxMillisToWaitIntervalMillis() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> JobTestHelper
        .waitForJobExecutorToProcessAllJobs(processEngineConfiguration, new ManagementServiceImpl(), 1L, 42L));
    verify(processEngineConfiguration).getAsyncExecutor();
  }

  /**
   * Test
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobs(ProcessEngineConfiguration, ManagementService, long, long, boolean)}
   * with {@code processEngineConfiguration}, {@code managementService},
   * {@code maxMillisToWait}, {@code intervalMillis},
   * {@code shutdownExecutorWhenFinished}.
   * <p>
   * Method under test:
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobs(ProcessEngineConfiguration, ManagementService, long, long, boolean)}
   */
  @Test
  public void testWaitForJobExecutorToProcessAllJobsWithProcessEngineConfigurationManagementServiceMaxMillisToWaitIntervalMillisShutdownExecutorWhenFinished() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> JobTestHelper
        .waitForJobExecutorToProcessAllJobs(processEngineConfiguration, new ManagementServiceImpl(), 1L, 42L, true));
    verify(processEngineConfiguration).getAsyncExecutor();
  }

  /**
   * Test
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobsAndExecutableTimerJobs(ProcessEngineConfiguration, ManagementService, long, long)}
   * with {@code processEngineConfiguration}, {@code managementService},
   * {@code maxMillisToWait}, {@code intervalMillis}.
   * <p>
   * Method under test:
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobsAndExecutableTimerJobs(ProcessEngineConfiguration, ManagementService, long, long)}
   */
  @Test
  public void testWaitForJobExecutorToProcessAllJobsAndExecutableTimerJobsWithProcessEngineConfigurationManagementServiceMaxMillisToWaitIntervalMillis() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.setAsyncExecutorActivate(anyBoolean()))
        .thenReturn(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> JobTestHelper.waitForJobExecutorToProcessAllJobsAndExecutableTimerJobs(processEngineConfiguration,
            new ManagementServiceImpl(), 1L, 42L));
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).setAsyncExecutorActivate(anyBoolean());
  }

  /**
   * Test
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobsAndExecutableTimerJobs(ProcessEngineConfiguration, ManagementService, long, long, boolean)}
   * with {@code processEngineConfiguration}, {@code managementService},
   * {@code maxMillisToWait}, {@code intervalMillis},
   * {@code shutdownExecutorWhenFinished}.
   * <p>
   * Method under test:
   * {@link JobTestHelper#waitForJobExecutorToProcessAllJobsAndExecutableTimerJobs(ProcessEngineConfiguration, ManagementService, long, long, boolean)}
   */
  @Test
  public void testWaitForJobExecutorToProcessAllJobsAndExecutableTimerJobsWithProcessEngineConfigurationManagementServiceMaxMillisToWaitIntervalMillisShutdownExecutorWhenFinished() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.setAsyncExecutorActivate(anyBoolean()))
        .thenReturn(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> JobTestHelper.waitForJobExecutorToProcessAllJobsAndExecutableTimerJobs(processEngineConfiguration,
            new ManagementServiceImpl(), 1L, 42L, true));
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).setAsyncExecutorActivate(anyBoolean());
  }

  /**
   * Test
   * {@link JobTestHelper#executeJobExecutorForTime(ProcessEngineConfiguration, long, long)}
   * with {@code processEngineConfiguration}, {@code maxMillisToWait},
   * {@code intervalMillis}.
   * <p>
   * Method under test:
   * {@link JobTestHelper#executeJobExecutorForTime(ProcessEngineConfiguration, long, long)}
   */
  @Test
  public void testExecuteJobExecutorForTimeWithProcessEngineConfigurationMaxMillisToWaitIntervalMillis() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);

    // Act
    JobTestHelper.executeJobExecutorForTime(processEngineConfiguration, 1L, 42L);

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
  }

  /**
   * Test {@link JobTestHelper#areJobsAvailable(ManagementService)} with
   * {@code managementService}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobTestHelper#areJobsAvailable(ManagementService)}
   */
  @Test
  public void testAreJobsAvailableWithManagementService_thenReturnFalse() {
    // Arrange
    JobQueryImpl jobQueryImpl = mock(JobQueryImpl.class);
    when(jobQueryImpl.list()).thenReturn(new ArrayList<>());
    ManagementService managementService = mock(ManagementService.class);
    when(managementService.createJobQuery()).thenReturn(jobQueryImpl);

    // Act
    boolean actualAreJobsAvailableResult = JobTestHelper.areJobsAvailable(managementService);

    // Assert
    verify(managementService).createJobQuery();
    verify(jobQueryImpl).list();
    assertFalse(actualAreJobsAvailableResult);
  }

  /**
   * Test {@link JobTestHelper#areJobsAvailable(ManagementService)} with
   * {@code managementService}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobTestHelper#areJobsAvailable(ManagementService)}
   */
  @Test
  public void testAreJobsAvailableWithManagementService_thenReturnTrue() {
    // Arrange
    ArrayList<Job> jobList = new ArrayList<>();
    jobList.add(new DeadLetterJobEntityImpl());
    JobQueryImpl jobQueryImpl = mock(JobQueryImpl.class);
    when(jobQueryImpl.list()).thenReturn(jobList);
    ManagementService managementService = mock(ManagementService.class);
    when(managementService.createJobQuery()).thenReturn(jobQueryImpl);

    // Act
    boolean actualAreJobsAvailableResult = JobTestHelper.areJobsAvailable(managementService);

    // Assert
    verify(managementService).createJobQuery();
    verify(jobQueryImpl).list();
    assertTrue(actualAreJobsAvailableResult);
  }

  /**
   * Test
   * {@link JobTestHelper#areJobsOrExecutableTimersAvailable(ManagementService)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JobTestHelper#areJobsOrExecutableTimersAvailable(ManagementService)}
   */
  @Test
  public void testAreJobsOrExecutableTimersAvailable_thenReturnTrue() {
    // Arrange
    ArrayList<Job> jobList = new ArrayList<>();
    jobList.add(new DeadLetterJobEntityImpl());
    JobQueryImpl jobQueryImpl = mock(JobQueryImpl.class);
    when(jobQueryImpl.list()).thenReturn(jobList);
    ManagementService managementService = mock(ManagementService.class);
    when(managementService.createJobQuery()).thenReturn(jobQueryImpl);

    // Act
    boolean actualAreJobsOrExecutableTimersAvailableResult = JobTestHelper
        .areJobsOrExecutableTimersAvailable(managementService);

    // Assert
    verify(managementService).createJobQuery();
    verify(jobQueryImpl).list();
    assertTrue(actualAreJobsOrExecutableTimersAvailableResult);
  }
}
