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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
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
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ProcessEngineImplDiffblueTest {
  /**
   * Test {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewProcessEngineImpl() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = mock(DefaultAsyncJobExecutor.class);
    doNothing().when(defaultAsyncJobExecutor).start();
    when(defaultAsyncJobExecutor.isAutoActivate()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn(null);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
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
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    TaskService taskService = actualProcessEngineImpl.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    assertTrue(
        ((TaskServiceImpl) taskService).processEngineConfiguration
            instanceof JtaProcessEngineConfiguration);
  }

  /**
   * Test {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then {@link ProcessEngineImpl#asyncExecutor} return {@link DefaultAsyncJobExecutor}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewProcessEngineImpl_thenAsyncExecutorReturnDefaultAsyncJobExecutor() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(new CommandConfig());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
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
    assertTrue(actualProcessEngineImpl.asyncExecutor instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then {@link ProcessEngineImpl#asyncExecutor} return {@link DefaultAsyncJobExecutor}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewProcessEngineImpl_thenAsyncExecutorReturnDefaultAsyncJobExecutor2() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(new CommandConfig());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
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
    assertTrue(actualProcessEngineImpl.asyncExecutor instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then calls {@link DefaultAsyncJobExecutor#isAutoActivate()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewProcessEngineImpl_thenCallsIsAutoActivate() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = mock(DefaultAsyncJobExecutor.class);
    doNothing().when(defaultAsyncJobExecutor).start();
    when(defaultAsyncJobExecutor.isAutoActivate()).thenReturn(true);

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(new CommandConfig());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
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
    TaskService taskService = actualProcessEngineImpl.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    assertTrue(
        ((TaskServiceImpl) taskService).processEngineConfiguration
            instanceof JtaProcessEngineConfiguration);
  }

  /**
   * Test {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then {@link ProcessEngineImpl#commandExecutor} return {@link CommandExecutorImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewProcessEngineImpl_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
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
    CommandContextInterceptor first = new CommandContextInterceptor();

    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(defaultConfig, first);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    ProcessEngineImpl actualProcessEngineImpl = new ProcessEngineImpl(processEngineConfiguration);

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
    CommandExecutor commandExecutor = actualProcessEngineImpl.commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
  }

  /**
   * Test {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then return {@link ProcessEngineImpl#asyncExecutor} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewProcessEngineImpl_thenReturnAsyncExecutorIsNull() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(new CommandConfig());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
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
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(null);
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
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
    TaskService taskService = actualProcessEngineImpl.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    assertTrue(
        ((TaskServiceImpl) taskService).processEngineConfiguration
            instanceof JtaProcessEngineConfiguration);
    assertNull(actualProcessEngineImpl.asyncExecutor);
  }

  /**
   * Test {@link ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then return Name is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineImpl#ProcessEngineImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewProcessEngineImpl_thenReturnNameIsNull() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = mock(DefaultAsyncJobExecutor.class);
    doNothing().when(defaultAsyncJobExecutor).start();
    when(defaultAsyncJobExecutor.isAutoActivate()).thenReturn(true);

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getSchemaCommandConfig()).thenReturn(new CommandConfig());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(true);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn(null);
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
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
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
    TaskService taskService = actualProcessEngineImpl.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    assertTrue(
        ((TaskServiceImpl) taskService).processEngineConfiguration
            instanceof JtaProcessEngineConfiguration);
    assertNull(actualProcessEngineImpl.getName());
  }

  /**
   * Test {@link ProcessEngineImpl#close()}.
   *
   * <p>Method under test: {@link ProcessEngineImpl#close()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.close()"})
  public void testClose() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineClosed(Mockito.<ProcessEngine>any());
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
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
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandContextInterceptor.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    new ProcessEngineImpl(processEngineConfiguration).close();

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
  }

  /**
   * Test {@link ProcessEngineImpl#close()}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} {@link DefaultAsyncJobExecutor#shutdown()} does
   *       nothing.
   *   <li>Then calls {@link DefaultAsyncJobExecutor#isActive()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineImpl#close()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.close()"})
  public void testClose_givenDefaultAsyncJobExecutorShutdownDoesNothing_thenCallsIsActive() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineClosed(Mockito.<ProcessEngine>any());
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = mock(DefaultAsyncJobExecutor.class);
    doNothing().when(defaultAsyncJobExecutor).shutdown();
    doNothing().when(defaultAsyncJobExecutor).start();
    when(defaultAsyncJobExecutor.isActive()).thenReturn(true);
    when(defaultAsyncJobExecutor.isAutoActivate()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
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
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(defaultAsyncJobExecutor);
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandContextInterceptor.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    new ProcessEngineImpl(processEngineConfiguration).close();

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
  }

  /**
   * Test {@link ProcessEngineImpl#close()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} {@link
   *       JtaProcessEngineConfiguration#getAsyncExecutor()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineImpl#close()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.close()"})
  public void testClose_givenJtaProcessEngineConfigurationGetAsyncExecutorReturnNull() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineClosed(Mockito.<ProcessEngine>any());
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
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
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(null);
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandContextInterceptor.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    new ProcessEngineImpl(processEngineConfiguration).close();

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
  }

  /**
   * Test {@link ProcessEngineImpl#close()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineImpl#close()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineImpl.close()"})
  public void testClose_thenCallsDispatchEvent() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineClosed(Mockito.<ProcessEngine>any());
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
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
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandContextInterceptor.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    new ProcessEngineImpl(processEngineConfiguration).close();

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
  }

  /**
   * Test {@link ProcessEngineImpl#getProcessEngineConfiguration()}.
   *
   * <p>Method under test: {@link ProcessEngineImpl#getProcessEngineConfiguration()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineImpl.getProcessEngineConfiguration()"
  })
  public void testGetProcessEngineConfiguration() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
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
    ProcessEngineImpl processEngineImpl = new ProcessEngineImpl(processEngineConfiguration);

    // Act
    ProcessEngineConfigurationImpl actualProcessEngineConfiguration =
        processEngineImpl.getProcessEngineConfiguration();

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
}
