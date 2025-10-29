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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class ProcessEngineImplDiffblueTest {
  /**
   * Method under test: {@link ProcessEngineImpl#close()}
   */
  @Test
  public void testClose() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineClosed(Mockito.<ProcessEngine>any());
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(mock(CommandConfig.class));
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
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
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    (new ProcessEngineImpl(processEngineConfiguration)).close();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getDatabaseSchemaUpdate();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineLifecycleListener).onProcessEngineClosed(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration, atLeast(1)).getSchemaCommandConfig();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
  }

  /**
   * Method under test: {@link ProcessEngineImpl#close()}
   */
  @Test
  public void testClose2() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineClosed(Mockito.<ProcessEngine>any());
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(mock(CommandConfig.class));
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    (new ProcessEngineImpl(processEngineConfiguration)).close();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getDatabaseSchemaUpdate();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineLifecycleListener).onProcessEngineClosed(isA(ProcessEngine.class));
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration, atLeast(1)).getSchemaCommandConfig();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
  }

  /**
   * Method under test: {@link ProcessEngineImpl#close()}
   */
  @Test
  public void testClose3() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineClosed(Mockito.<ProcessEngine>any());
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(mock(CommandConfig.class));
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(null);
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    (new ProcessEngineImpl(processEngineConfiguration)).close();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getDatabaseSchemaUpdate();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineLifecycleListener).onProcessEngineClosed(isA(ProcessEngine.class));
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration, atLeast(1)).getSchemaCommandConfig();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
  }

  /**
   * Method under test: {@link ProcessEngineImpl#close()}
   */
  @Test
  public void testClose4() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineClosed(Mockito.<ProcessEngine>any());
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = mock(DefaultAsyncJobExecutor.class);
    doNothing().when(defaultAsyncJobExecutor).shutdown();
    doNothing().when(defaultAsyncJobExecutor).start();
    when(defaultAsyncJobExecutor.isActive()).thenReturn(true);
    when(defaultAsyncJobExecutor.isAutoActivate()).thenReturn(true);
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(mock(CommandConfig.class));
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    (new ProcessEngineImpl(processEngineConfiguration)).close();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getDatabaseSchemaUpdate();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineLifecycleListener).onProcessEngineClosed(isA(ProcessEngine.class));
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(defaultAsyncJobExecutor).isActive();
    verify(defaultAsyncJobExecutor).isAutoActivate();
    verify(defaultAsyncJobExecutor).shutdown();
    verify(defaultAsyncJobExecutor).start();
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration, atLeast(1)).getSchemaCommandConfig();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
  }

  /**
   * Method under test: {@link ProcessEngineImpl#getProcessEngineConfiguration()}
   */
  @Test
  public void testGetProcessEngineConfiguration() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
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
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    ProcessEngineImpl processEngineImpl = new ProcessEngineImpl(processEngineConfiguration);

    // Act
    ProcessEngineConfigurationImpl actualProcessEngineConfiguration = processEngineImpl.getProcessEngineConfiguration();

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
    assertSame(processEngineImpl.processEngineConfiguration, actualProcessEngineConfiguration);
  }

  /**
   * Method under test:
   * {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewProcessEngineImpl() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(defaultConfig, first);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(new CommandConfig());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getDynamicBpmnService()).thenReturn(dynamicBpmnServiceImpl);
    HistoryServiceImpl historyServiceImpl = new HistoryServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getHistoryService()).thenReturn(historyServiceImpl);
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    when(processEngineConfiguration.getManagementService()).thenReturn(managementServiceImpl);
    when(processEngineConfiguration.getProcessEngineLifecycleListener()).thenReturn(processEngineLifecycleListener);
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    when(processEngineConfiguration.getRepositoryService()).thenReturn(repositoryServiceImpl);
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    when(processEngineConfiguration.getRuntimeService()).thenReturn(runtimeServiceImpl);
    TaskServiceImpl taskServiceImpl = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getTaskService()).thenReturn(taskServiceImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    ProcessEngineImpl actualProcessEngineImpl = new ProcessEngineImpl(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getDatabaseSchemaUpdate();
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
    verify(processEngineConfiguration).getSchemaCommandConfig();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    AsyncExecutor asyncExecutor = actualProcessEngineImpl.asyncExecutor;
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    CommandExecutor commandExecutor = actualProcessEngineImpl.commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertEquals("Process Engine Name", actualProcessEngineImpl.getName());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecutorService());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecuteAsyncRunnableFactory());
    assertNull(asyncExecutor.getProcessEngineConfiguration());
    assertEquals(0, asyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, asyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, asyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, ((DefaultAsyncJobExecutor) asyncExecutor).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) asyncExecutor).getQueueSize());
    assertEquals(10000, asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, asyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((DefaultAsyncJobExecutor) asyncExecutor).getCorePoolSize());
    assertEquals(3, asyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, asyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, asyncExecutor.getTimerLockTimeInMillis());
    assertEquals(500, asyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) asyncExecutor).getKeepAliveTime());
    assertEquals(60000, asyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) asyncExecutor).getSecondsToWaitOnShutdown());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) asyncExecutor).isMessageQueueMode());
    assertTrue(actualProcessEngineImpl.sessionFactories.isEmpty());
    assertSame(dynamicBpmnServiceImpl, actualProcessEngineImpl.getDynamicBpmnService());
    assertSame(historyServiceImpl, actualProcessEngineImpl.getHistoryService());
    assertSame(managementServiceImpl, actualProcessEngineImpl.getManagementService());
    assertSame(repositoryServiceImpl, actualProcessEngineImpl.getRepositoryService());
    assertSame(runtimeServiceImpl, actualProcessEngineImpl.getRuntimeService());
    assertSame(taskServiceImpl, actualProcessEngineImpl.getTaskService());
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(processEngineConfiguration, actualProcessEngineImpl.getProcessEngineConfiguration());
  }

  /**
   * Method under test:
   * {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewProcessEngineImpl2() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(defaultConfig, first);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(new CommandConfig());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn(null);
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getDynamicBpmnService()).thenReturn(dynamicBpmnServiceImpl);
    HistoryServiceImpl historyServiceImpl = new HistoryServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getHistoryService()).thenReturn(historyServiceImpl);
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    when(processEngineConfiguration.getManagementService()).thenReturn(managementServiceImpl);
    when(processEngineConfiguration.getProcessEngineLifecycleListener()).thenReturn(processEngineLifecycleListener);
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    when(processEngineConfiguration.getRepositoryService()).thenReturn(repositoryServiceImpl);
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    when(processEngineConfiguration.getRuntimeService()).thenReturn(runtimeServiceImpl);
    TaskServiceImpl taskServiceImpl = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getTaskService()).thenReturn(taskServiceImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    ProcessEngineImpl actualProcessEngineImpl = new ProcessEngineImpl(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getDatabaseSchemaUpdate();
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
    verify(processEngineConfiguration).getSchemaCommandConfig();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    AsyncExecutor asyncExecutor = actualProcessEngineImpl.asyncExecutor;
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    CommandExecutor commandExecutor = actualProcessEngineImpl.commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertNull(actualProcessEngineImpl.getName());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecutorService());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecuteAsyncRunnableFactory());
    assertNull(asyncExecutor.getProcessEngineConfiguration());
    assertEquals(0, asyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, asyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, asyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, ((DefaultAsyncJobExecutor) asyncExecutor).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) asyncExecutor).getQueueSize());
    assertEquals(10000, asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, asyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((DefaultAsyncJobExecutor) asyncExecutor).getCorePoolSize());
    assertEquals(3, asyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, asyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, asyncExecutor.getTimerLockTimeInMillis());
    assertEquals(500, asyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) asyncExecutor).getKeepAliveTime());
    assertEquals(60000, asyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) asyncExecutor).getSecondsToWaitOnShutdown());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) asyncExecutor).isMessageQueueMode());
    assertTrue(actualProcessEngineImpl.sessionFactories.isEmpty());
    assertSame(dynamicBpmnServiceImpl, actualProcessEngineImpl.getDynamicBpmnService());
    assertSame(historyServiceImpl, actualProcessEngineImpl.getHistoryService());
    assertSame(managementServiceImpl, actualProcessEngineImpl.getManagementService());
    assertSame(repositoryServiceImpl, actualProcessEngineImpl.getRepositoryService());
    assertSame(runtimeServiceImpl, actualProcessEngineImpl.getRuntimeService());
    assertSame(taskServiceImpl, actualProcessEngineImpl.getTaskService());
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(processEngineConfiguration, actualProcessEngineImpl.getProcessEngineConfiguration());
  }

  /**
   * Method under test:
   * {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewProcessEngineImpl3() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(defaultConfig, first);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(new CommandConfig());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getDynamicBpmnService()).thenReturn(dynamicBpmnServiceImpl);
    HistoryServiceImpl historyServiceImpl = new HistoryServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getHistoryService()).thenReturn(historyServiceImpl);
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    when(processEngineConfiguration.getManagementService()).thenReturn(managementServiceImpl);
    when(processEngineConfiguration.getProcessEngineLifecycleListener()).thenReturn(processEngineLifecycleListener);
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    when(processEngineConfiguration.getRepositoryService()).thenReturn(repositoryServiceImpl);
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    when(processEngineConfiguration.getRuntimeService()).thenReturn(runtimeServiceImpl);
    TaskServiceImpl taskServiceImpl = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getTaskService()).thenReturn(taskServiceImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    ProcessEngineImpl actualProcessEngineImpl = new ProcessEngineImpl(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getDatabaseSchemaUpdate();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSchemaCommandConfig();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    AsyncExecutor asyncExecutor = actualProcessEngineImpl.asyncExecutor;
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    CommandExecutor commandExecutor = actualProcessEngineImpl.commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertEquals("Process Engine Name", actualProcessEngineImpl.getName());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecutorService());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecuteAsyncRunnableFactory());
    assertNull(asyncExecutor.getProcessEngineConfiguration());
    assertEquals(0, asyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, asyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, asyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, ((DefaultAsyncJobExecutor) asyncExecutor).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) asyncExecutor).getQueueSize());
    assertEquals(10000, asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, asyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((DefaultAsyncJobExecutor) asyncExecutor).getCorePoolSize());
    assertEquals(3, asyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, asyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, asyncExecutor.getTimerLockTimeInMillis());
    assertEquals(500, asyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) asyncExecutor).getKeepAliveTime());
    assertEquals(60000, asyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) asyncExecutor).getSecondsToWaitOnShutdown());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) asyncExecutor).isMessageQueueMode());
    assertTrue(actualProcessEngineImpl.sessionFactories.isEmpty());
    assertSame(dynamicBpmnServiceImpl, actualProcessEngineImpl.getDynamicBpmnService());
    assertSame(historyServiceImpl, actualProcessEngineImpl.getHistoryService());
    assertSame(managementServiceImpl, actualProcessEngineImpl.getManagementService());
    assertSame(repositoryServiceImpl, actualProcessEngineImpl.getRepositoryService());
    assertSame(runtimeServiceImpl, actualProcessEngineImpl.getRuntimeService());
    assertSame(taskServiceImpl, actualProcessEngineImpl.getTaskService());
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(processEngineConfiguration, actualProcessEngineImpl.getProcessEngineConfiguration());
  }

  /**
   * Method under test:
   * {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewProcessEngineImpl4() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = mock(DefaultAsyncJobExecutor.class);
    doNothing().when(defaultAsyncJobExecutor).start();
    when(defaultAsyncJobExecutor.isAutoActivate()).thenReturn(true);
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(defaultConfig, first);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(new CommandConfig());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getDynamicBpmnService()).thenReturn(dynamicBpmnServiceImpl);
    HistoryServiceImpl historyServiceImpl = new HistoryServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getHistoryService()).thenReturn(historyServiceImpl);
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    when(processEngineConfiguration.getManagementService()).thenReturn(managementServiceImpl);
    when(processEngineConfiguration.getProcessEngineLifecycleListener()).thenReturn(processEngineLifecycleListener);
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    when(processEngineConfiguration.getRepositoryService()).thenReturn(repositoryServiceImpl);
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    when(processEngineConfiguration.getRuntimeService()).thenReturn(runtimeServiceImpl);
    TaskServiceImpl taskServiceImpl = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getTaskService()).thenReturn(taskServiceImpl);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    ProcessEngineImpl actualProcessEngineImpl = new ProcessEngineImpl(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getDatabaseSchemaUpdate();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(defaultAsyncJobExecutor).isAutoActivate();
    verify(defaultAsyncJobExecutor).start();
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSchemaCommandConfig();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    CommandExecutor commandExecutor = actualProcessEngineImpl.commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertEquals("Process Engine Name", actualProcessEngineImpl.getName());
    assertTrue(actualProcessEngineImpl.sessionFactories.isEmpty());
    assertSame(dynamicBpmnServiceImpl, actualProcessEngineImpl.getDynamicBpmnService());
    assertSame(historyServiceImpl, actualProcessEngineImpl.getHistoryService());
    assertSame(managementServiceImpl, actualProcessEngineImpl.getManagementService());
    assertSame(repositoryServiceImpl, actualProcessEngineImpl.getRepositoryService());
    assertSame(runtimeServiceImpl, actualProcessEngineImpl.getRuntimeService());
    assertSame(taskServiceImpl, actualProcessEngineImpl.getTaskService());
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(processEngineConfiguration, actualProcessEngineImpl.getProcessEngineConfiguration());
  }
}
