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
package org.activiti.spring.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.core.common.spring.identity.ActivitiUserGroupManagerImpl;
import org.activiti.core.common.spring.identity.ExtendedInMemoryUserDetailsManager;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
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
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.integration.MybatisIntegrationContextDataManager;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManager;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextService;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringCallerRunsRejectedJobsHandler;
import org.activiti.spring.SpringRejectedJobsHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

class AbstractProcessEngineAutoConfigurationDiffblueTest {
  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#springAsyncExecutor(TaskExecutor)}.
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#springAsyncExecutor(TaskExecutor)}
   */
  @Test
  @DisplayName("Test springAsyncExecutor(TaskExecutor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SpringAsyncExecutor AbstractProcessEngineAutoConfiguration.springAsyncExecutor(TaskExecutor)"
  })
  void testSpringAsyncExecutor() {
    // Arrange
    TaskExecutor applicationTaskExecutor = mock(TaskExecutor.class);

    // Act
    SpringAsyncExecutor actualSpringAsyncExecutorResult =
        new ProcessEngineAutoConfiguration(
                new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()))
            .springAsyncExecutor(applicationTaskExecutor);

    // Assert
    assertTrue(
        actualSpringAsyncExecutorResult.getRejectedJobsHandler()
            instanceof SpringCallerRunsRejectedJobsHandler);
    assertNull(actualSpringAsyncExecutorResult.getAsyncJobAcquisitionThread());
    assertNull(actualSpringAsyncExecutorResult.getResetExpiredJobThread());
    assertNull(actualSpringAsyncExecutorResult.getTimerJobAcquisitionThread());
    assertNull(actualSpringAsyncExecutorResult.getThreadPoolQueue());
    assertNull(actualSpringAsyncExecutorResult.getExecutorService());
    assertNull(actualSpringAsyncExecutorResult.getExecuteAsyncRunnableFactory());
    assertNull(actualSpringAsyncExecutorResult.getProcessEngineConfiguration());
    assertEquals(0, actualSpringAsyncExecutorResult.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, actualSpringAsyncExecutorResult.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualSpringAsyncExecutorResult.getMaxTimerJobsPerAcquisition());
    assertEquals(10, actualSpringAsyncExecutorResult.getMaxPoolSize());
    assertEquals(100, actualSpringAsyncExecutorResult.getQueueSize());
    assertEquals(
        10000, actualSpringAsyncExecutorResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(
        10000, actualSpringAsyncExecutorResult.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, actualSpringAsyncExecutorResult.getCorePoolSize());
    assertEquals(3, actualSpringAsyncExecutorResult.getResetExpiredJobsPageSize());
    assertEquals(300000, actualSpringAsyncExecutorResult.getAsyncJobLockTimeInMillis());
    assertEquals(300000, actualSpringAsyncExecutorResult.getTimerLockTimeInMillis());
    assertEquals(500, actualSpringAsyncExecutorResult.getRetryWaitTimeInMillis());
    assertEquals(5000L, actualSpringAsyncExecutorResult.getKeepAliveTime());
    assertEquals(60000, actualSpringAsyncExecutorResult.getResetExpiredJobsInterval());
    assertEquals(60L, actualSpringAsyncExecutorResult.getSecondsToWaitOnShutdown());
    assertFalse(actualSpringAsyncExecutorResult.isActive());
    assertFalse(actualSpringAsyncExecutorResult.isAutoActivate());
    assertFalse(actualSpringAsyncExecutorResult.isMessageQueueMode());
    assertSame(applicationTaskExecutor, actualSpringAsyncExecutorResult.getTaskExecutor());
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#springRejectedJobsHandler()}.
   *
   * <ul>
   *   <li>Then calls {@link DefaultAsyncJobExecutor#getProcessEngineConfiguration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#springRejectedJobsHandler()}
   */
  @Test
  @DisplayName("Test springRejectedJobsHandler(); then calls getProcessEngineConfiguration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SpringRejectedJobsHandler AbstractProcessEngineAutoConfiguration.springRejectedJobsHandler()"
  })
  void testSpringRejectedJobsHandler_thenCallsGetProcessEngineConfiguration() {
    // Arrange and Act
    SpringRejectedJobsHandler actualSpringRejectedJobsHandlerResult =
        new ProcessEngineAutoConfiguration(
                new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()))
            .springRejectedJobsHandler();
    DefaultAsyncJobExecutor asyncExecutor = mock(DefaultAsyncJobExecutor.class);
    when(asyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");
    actualSpringRejectedJobsHandlerResult.jobRejected(asyncExecutor, job);

    // Assert
    verify(asyncExecutor).getProcessEngineConfiguration();
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
    assertTrue(
        actualSpringRejectedJobsHandlerResult instanceof SpringCallerRunsRejectedJobsHandler);
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#springRejectedJobsHandler()}.
   *
   * <ul>
   *   <li>Then return {@link SpringCallerRunsRejectedJobsHandler}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#springRejectedJobsHandler()}
   */
  @Test
  @DisplayName("Test springRejectedJobsHandler(); then return SpringCallerRunsRejectedJobsHandler")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SpringRejectedJobsHandler AbstractProcessEngineAutoConfiguration.springRejectedJobsHandler()"
  })
  void testSpringRejectedJobsHandler_thenReturnSpringCallerRunsRejectedJobsHandler() {
    // Arrange and Act
    SpringRejectedJobsHandler actualSpringRejectedJobsHandlerResult =
        new ProcessEngineAutoConfiguration(
                new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()))
            .springRejectedJobsHandler();
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    actualSpringRejectedJobsHandlerResult.jobRejected(asyncExecutor, new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(
        actualSpringRejectedJobsHandlerResult instanceof SpringCallerRunsRejectedJobsHandler);
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#springRejectedJobsHandler()}.
   *
   * <ul>
   *   <li>Then return {@link SpringCallerRunsRejectedJobsHandler}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#springRejectedJobsHandler()}
   */
  @Test
  @DisplayName("Test springRejectedJobsHandler(); then return SpringCallerRunsRejectedJobsHandler")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SpringRejectedJobsHandler AbstractProcessEngineAutoConfiguration.springRejectedJobsHandler()"
  })
  void testSpringRejectedJobsHandler_thenReturnSpringCallerRunsRejectedJobsHandler2() {
    // Arrange and Act
    SpringRejectedJobsHandler actualSpringRejectedJobsHandlerResult =
        new ProcessEngineAutoConfiguration(
                new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()))
            .springRejectedJobsHandler();
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
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
    job.setJobHandlerConfiguration("Job Handler Configuration");
    job.setJobHandlerType("Job Handler Type");
    job.setJobType("Job Type");
    job.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setLockOwner("Claimed By");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("Repeat");
    job.setRetries(1);
    job.setRevision(1);
    job.setTenantId("42");
    job.setUpdated(true);
    actualSpringRejectedJobsHandlerResult.jobRejected(asyncExecutor, job);

    // Assert
    assertTrue(
        actualSpringRejectedJobsHandlerResult instanceof SpringCallerRunsRejectedJobsHandler);
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}
   */
  @Test
  @DisplayName("Test getCustomMybatisMapperClasses(List); given '42'; when ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Set AbstractProcessEngineAutoConfiguration.getCustomMybatisMapperClasses(List)"
  })
  void testGetCustomMybatisMapperClasses_given42_whenArrayListAdd42() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ArrayList<String> customMyBatisMappers = new ArrayList<>();
    customMyBatisMappers.add("42");
    customMyBatisMappers.add("foo");

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> processEngineAutoConfiguration.getCustomMybatisMapperClasses(customMyBatisMappers));
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}.
   *
   * <ul>
   *   <li>Given {@code Custom My Batis Mappers}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}
   */
  @Test
  @DisplayName("Test getCustomMybatisMapperClasses(List); given 'Custom My Batis Mappers'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Set AbstractProcessEngineAutoConfiguration.getCustomMybatisMapperClasses(List)"
  })
  void testGetCustomMybatisMapperClasses_givenCustomMyBatisMappers() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ArrayList<String> customMyBatisMappers = new ArrayList<>();
    customMyBatisMappers.add("Custom My Batis Mappers");

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> processEngineAutoConfiguration.getCustomMybatisMapperClasses(customMyBatisMappers));
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}
   */
  @Test
  @DisplayName("Test getCustomMybatisMapperClasses(List); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Set AbstractProcessEngineAutoConfiguration.getCustomMybatisMapperClasses(List)"
  })
  void testGetCustomMybatisMapperClasses_whenArrayList_thenReturnEmpty() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    // Act and Assert
    assertTrue(
        processEngineAutoConfiguration.getCustomMybatisMapperClasses(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#runtimeServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link RuntimeServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#runtimeServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test runtimeServiceBean(ProcessEngine); then return RuntimeServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RuntimeService AbstractProcessEngineAutoConfiguration.runtimeServiceBean(ProcessEngine)"
  })
  void testRuntimeServiceBean_thenReturnRuntimeServiceImpl() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    when(processEngineConfiguration.getRuntimeService()).thenReturn(runtimeServiceImpl);
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    RuntimeService actualRuntimeServiceBeanResult =
        processEngineAutoConfiguration.runtimeServiceBean(
            new ProcessEngineImpl(processEngineConfiguration));

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
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
    assertTrue(actualRuntimeServiceBeanResult instanceof RuntimeServiceImpl);
    assertNull(((RuntimeServiceImpl) actualRuntimeServiceBeanResult).getCommandExecutor());
    assertSame(runtimeServiceImpl, actualRuntimeServiceBeanResult);
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#repositoryServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link RepositoryServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#repositoryServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test repositoryServiceBean(ProcessEngine); then return RepositoryServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RepositoryService AbstractProcessEngineAutoConfiguration.repositoryServiceBean(ProcessEngine)"
  })
  void testRepositoryServiceBean_thenReturnRepositoryServiceImpl() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    when(processEngineConfiguration.getRepositoryService()).thenReturn(repositoryServiceImpl);
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    RepositoryService actualRepositoryServiceBeanResult =
        processEngineAutoConfiguration.repositoryServiceBean(
            new ProcessEngineImpl(processEngineConfiguration));

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
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
    assertTrue(actualRepositoryServiceBeanResult instanceof RepositoryServiceImpl);
    assertNull(((RepositoryServiceImpl) actualRepositoryServiceBeanResult).getCommandExecutor());
    assertSame(repositoryServiceImpl, actualRepositoryServiceBeanResult);
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#taskServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link TaskServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#taskServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test taskServiceBean(ProcessEngine); then return TaskServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskService AbstractProcessEngineAutoConfiguration.taskServiceBean(ProcessEngine)"
  })
  void testTaskServiceBean_thenReturnTaskServiceImpl() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    TaskServiceImpl taskServiceImpl = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getTaskService()).thenReturn(taskServiceImpl);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    TaskService actualTaskServiceBeanResult =
        processEngineAutoConfiguration.taskServiceBean(
            new ProcessEngineImpl(processEngineConfiguration));

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
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
    assertTrue(actualTaskServiceBeanResult instanceof TaskServiceImpl);
    assertNull(((TaskServiceImpl) actualTaskServiceBeanResult).getCommandExecutor());
    assertSame(taskServiceImpl, actualTaskServiceBeanResult);
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#historyServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link HistoryServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#historyServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test historyServiceBean(ProcessEngine); then return HistoryServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoryService AbstractProcessEngineAutoConfiguration.historyServiceBean(ProcessEngine)"
  })
  void testHistoryServiceBean_thenReturnHistoryServiceImpl() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    HistoryServiceImpl historyServiceImpl =
        new HistoryServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getHistoryService()).thenReturn(historyServiceImpl);
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    HistoryService actualHistoryServiceBeanResult =
        processEngineAutoConfiguration.historyServiceBean(
            new ProcessEngineImpl(processEngineConfiguration));

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
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
    assertTrue(actualHistoryServiceBeanResult instanceof HistoryServiceImpl);
    assertNull(((HistoryServiceImpl) actualHistoryServiceBeanResult).getCommandExecutor());
    assertSame(historyServiceImpl, actualHistoryServiceBeanResult);
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#managementServiceBeanBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link ManagementServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#managementServiceBeanBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test managementServiceBeanBean(ProcessEngine); then return ManagementServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ManagementService AbstractProcessEngineAutoConfiguration.managementServiceBeanBean(ProcessEngine)"
  })
  void testManagementServiceBeanBean_thenReturnManagementServiceImpl() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new JtaProcessEngineConfiguration()));
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    when(processEngineConfiguration.getManagementService()).thenReturn(managementServiceImpl);
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    ManagementService actualManagementServiceBeanBeanResult =
        processEngineAutoConfiguration.managementServiceBeanBean(
            new ProcessEngineImpl(processEngineConfiguration));

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
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
    assertTrue(actualManagementServiceBeanBeanResult instanceof ManagementServiceImpl);
    assertNull(
        ((ManagementServiceImpl) actualManagementServiceBeanBeanResult).getCommandExecutor());
    assertSame(managementServiceImpl, actualManagementServiceBeanBeanResult);
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#taskExecutor()}.
   *
   * <p>Method under test: {@link AbstractProcessEngineAutoConfiguration#taskExecutor()}
   */
  @Test
  @DisplayName("Test taskExecutor()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskExecutor AbstractProcessEngineAutoConfiguration.taskExecutor()"})
  void testTaskExecutor() {
    // Arrange and Act
    TaskExecutor actualTaskExecutorResult =
        new ProcessEngineAutoConfiguration(
                new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()))
            .taskExecutor();
    actualTaskExecutorResult.execute(mock(Runnable.class));

    // Assert
    assertTrue(actualTaskExecutorResult instanceof SimpleAsyncTaskExecutor);
    assertEquals(
        "SimpleAsyncTaskExecutor-",
        ((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getThreadNamePrefix());
    assertNull(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getThreadGroup());
    assertNull(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getThreadFactory());
    assertEquals(-1, ((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getConcurrencyLimit());
    assertEquals(5, ((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getThreadPriority());
    assertFalse(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).isThrottleActive());
    assertFalse(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).isDaemon());
    assertTrue(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).isActive());
  }

  /**
   * Test {@link
   * AbstractProcessEngineAutoConfiguration#integrationContextManagerBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link IntegrationContextManagerImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#integrationContextManagerBean(ProcessEngine)}
   */
  @Test
  @DisplayName(
      "Test integrationContextManagerBean(ProcessEngine); then return IntegrationContextManagerImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "IntegrationContextManager AbstractProcessEngineAutoConfiguration.integrationContextManagerBean(ProcessEngine)"
  })
  void testIntegrationContextManagerBean_thenReturnIntegrationContextManagerImpl() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    IntegrationContextManagerImpl integrationContextManagerImpl =
        new IntegrationContextManagerImpl(
            processEngineConfiguration2,
            new MybatisIntegrationContextDataManager(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getIntegrationContextManager())
        .thenReturn(integrationContextManagerImpl);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    IntegrationContextManager actualIntegrationContextManagerBeanResult =
        processEngineAutoConfiguration.integrationContextManagerBean(
            new ProcessEngineImpl(processEngineConfiguration));

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getIntegrationContextManager();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    assertTrue(actualIntegrationContextManagerBeanResult instanceof IntegrationContextManagerImpl);
    assertSame(integrationContextManagerImpl, actualIntegrationContextManagerBeanResult);
  }

  /**
   * Test {@link
   * AbstractProcessEngineAutoConfiguration#integrationContextServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link IntegrationContextServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineAutoConfiguration#integrationContextServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName(
      "Test integrationContextServiceBean(ProcessEngine); then return IntegrationContextServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "IntegrationContextService AbstractProcessEngineAutoConfiguration.integrationContextServiceBean(ProcessEngine)"
  })
  void testIntegrationContextServiceBean_thenReturnIntegrationContextServiceImpl() {
    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration =
        new ProcessEngineAutoConfiguration(
            new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    IntegrationContextServiceImpl integrationContextServiceImpl =
        new IntegrationContextServiceImpl(commandExecutor);
    when(processEngineConfiguration.getIntegrationContextService())
        .thenReturn(integrationContextServiceImpl);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig2 = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig2, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    IntegrationContextService actualIntegrationContextServiceBeanResult =
        processEngineAutoConfiguration.integrationContextServiceBean(
            new ProcessEngineImpl(processEngineConfiguration));

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getIntegrationContextService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    assertTrue(actualIntegrationContextServiceBeanResult instanceof IntegrationContextServiceImpl);
    assertSame(integrationContextServiceImpl, actualIntegrationContextServiceBeanResult);
  }
}
