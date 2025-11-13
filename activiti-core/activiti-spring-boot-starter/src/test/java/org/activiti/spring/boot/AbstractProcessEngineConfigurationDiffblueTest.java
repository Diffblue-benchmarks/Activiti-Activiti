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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
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
import org.activiti.engine.impl.persistence.entity.data.integration.MybatisIntegrationContextDataManager;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManager;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextService;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AbstractProcessEngineConfigurationDiffblueTest {
  /**
   * Test {@link AbstractProcessEngineConfiguration#runtimeServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link RuntimeServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineConfiguration#runtimeServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test runtimeServiceBean(ProcessEngine); then return RuntimeServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RuntimeService AbstractProcessEngineConfiguration.runtimeServiceBean(ProcessEngine)"
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
   * Test {@link AbstractProcessEngineConfiguration#repositoryServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link RepositoryServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineConfiguration#repositoryServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test repositoryServiceBean(ProcessEngine); then return RepositoryServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RepositoryService AbstractProcessEngineConfiguration.repositoryServiceBean(ProcessEngine)"
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
   * Test {@link AbstractProcessEngineConfiguration#taskServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link TaskServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractProcessEngineConfiguration#taskServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test taskServiceBean(ProcessEngine); then return TaskServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskService AbstractProcessEngineConfiguration.taskServiceBean(ProcessEngine)"
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
   * Test {@link AbstractProcessEngineConfiguration#historyServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link HistoryServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineConfiguration#historyServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test historyServiceBean(ProcessEngine); then return HistoryServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoryService AbstractProcessEngineConfiguration.historyServiceBean(ProcessEngine)"
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
   * Test {@link AbstractProcessEngineConfiguration#managementServiceBeanBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link ManagementServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineConfiguration#managementServiceBeanBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test managementServiceBeanBean(ProcessEngine); then return ManagementServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ManagementService AbstractProcessEngineConfiguration.managementServiceBeanBean(ProcessEngine)"
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
   * Test {@link AbstractProcessEngineConfiguration#integrationContextManagerBean(ProcessEngine)}.
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineConfiguration#integrationContextManagerBean(ProcessEngine)}
   */
  @Test
  @DisplayName("Test integrationContextManagerBean(ProcessEngine)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "IntegrationContextManager AbstractProcessEngineConfiguration.integrationContextManagerBean(ProcessEngine)"
  })
  void testIntegrationContextManagerBean() {
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
    assertSame(integrationContextManagerImpl, actualIntegrationContextManagerBeanResult);
  }

  /**
   * Test {@link AbstractProcessEngineConfiguration#integrationContextServiceBean(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return {@link IntegrationContextServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractProcessEngineConfiguration#integrationContextServiceBean(ProcessEngine)}
   */
  @Test
  @DisplayName(
      "Test integrationContextServiceBean(ProcessEngine); then return IntegrationContextServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "IntegrationContextService AbstractProcessEngineConfiguration.integrationContextServiceBean(ProcessEngine)"
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
