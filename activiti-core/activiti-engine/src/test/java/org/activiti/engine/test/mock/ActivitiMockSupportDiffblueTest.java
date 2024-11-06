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
package org.activiti.engine.test.mock;

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
import java.util.List;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.test.TestActivityBehaviorFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class ActivitiMockSupportDiffblueTest {
  /**
   * Test
   * {@link ActivitiMockSupport#ActivitiMockSupport(TestActivityBehaviorFactory)}.
   * <p>
   * Method under test:
   * {@link ActivitiMockSupport#ActivitiMockSupport(TestActivityBehaviorFactory)}
   */
  @Test
  public void testNewActivitiMockSupport() {
    // Arrange, Act and Assert
    TestActivityBehaviorFactory testActivityBehaviorFactory = (new ActivitiMockSupport(
        new TestActivityBehaviorFactory())).testActivityBehaviorFactory;
    assertTrue(testActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(testActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(testActivityBehaviorFactory.getWrappedActivityBehaviorFactory());
    assertNull(testActivityBehaviorFactory.getExpressionManager());
  }

  /**
   * Test {@link ActivitiMockSupport#ActivitiMockSupport(ProcessEngine)}.
   * <p>
   * Method under test:
   * {@link ActivitiMockSupport#ActivitiMockSupport(ProcessEngine)}
   */
  @Test
  public void testNewActivitiMockSupport2() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBpmnParser()).thenReturn(new BpmnParser());
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    when(processEngineConfiguration.getActivityBehaviorFactory()).thenReturn(defaultActivityBehaviorFactory);
    when(processEngineConfiguration.setActivityBehaviorFactory(Mockito.<ActivityBehaviorFactory>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
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

    // Act
    ActivitiMockSupport actualActivitiMockSupport = new ActivitiMockSupport(
        new ProcessEngineImpl(processEngineConfiguration));

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getActivityBehaviorFactory();
    verify(processEngineConfiguration).getBpmnParser();
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
    verify(processEngineConfiguration).setActivityBehaviorFactory(isA(ActivityBehaviorFactory.class));
    TestActivityBehaviorFactory testActivityBehaviorFactory = actualActivitiMockSupport.testActivityBehaviorFactory;
    assertTrue(testActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(testActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(testActivityBehaviorFactory.getExpressionManager());
    assertEquals(0, actualActivitiMockSupport.getNrOfNoOpServiceTaskExecutions());
    assertTrue(actualActivitiMockSupport.getExecutedNoOpServiceTaskDelegateClassNames().isEmpty());
    assertSame(defaultActivityBehaviorFactory, testActivityBehaviorFactory.getWrappedActivityBehaviorFactory());
  }

  /**
   * Test {@link ActivitiMockSupport#isMockSupportPossible(ProcessEngine)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiMockSupport#isMockSupportPossible(ProcessEngine)}
   */
  @Test
  public void testIsMockSupportPossible_thenReturnTrue() {
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

    // Act
    boolean actualIsMockSupportPossibleResult = ActivitiMockSupport
        .isMockSupportPossible(new ProcessEngineImpl(processEngineConfiguration));

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
    assertTrue(actualIsMockSupportPossibleResult);
  }

  /**
   * Test {@link ActivitiMockSupport#isMockSupportPossible(ProcessEngine)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiMockSupport#isMockSupportPossible(ProcessEngine)}
   */
  @Test
  public void testIsMockSupportPossible_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ActivitiMockSupport.isMockSupportPossible(null));
  }

  /**
   * Test
   * {@link ActivitiMockSupport#mockServiceTaskWithClassDelegate(String, Class)}
   * with {@code originalClassFqn}, {@code mockedClass}.
   * <p>
   * Method under test:
   * {@link ActivitiMockSupport#mockServiceTaskWithClassDelegate(String, Class)}
   */
  @Test
  public void testMockServiceTaskWithClassDelegateWithOriginalClassFqnMockedClass() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBpmnParser()).thenReturn(new BpmnParser());
    when(processEngineConfiguration.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(processEngineConfiguration.setActivityBehaviorFactory(Mockito.<ActivityBehaviorFactory>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
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
    ActivitiMockSupport activitiMockSupport = new ActivitiMockSupport(
        new ProcessEngineImpl(processEngineConfiguration));
    Class<Object> mockedClass = Object.class;

    // Act
    activitiMockSupport.mockServiceTaskWithClassDelegate("Original Class Fqn", mockedClass);

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getActivityBehaviorFactory();
    verify(processEngineConfiguration).getBpmnParser();
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
    verify(processEngineConfiguration).setActivityBehaviorFactory(isA(ActivityBehaviorFactory.class));
  }

  /**
   * Test
   * {@link ActivitiMockSupport#mockServiceTaskWithClassDelegate(String, String)}
   * with {@code originalClassFqn}, {@code mockedClassFqn}.
   * <p>
   * Method under test:
   * {@link ActivitiMockSupport#mockServiceTaskWithClassDelegate(String, String)}
   */
  @Test
  public void testMockServiceTaskWithClassDelegateWithOriginalClassFqnMockedClassFqn() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBpmnParser()).thenReturn(new BpmnParser());
    when(processEngineConfiguration.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(processEngineConfiguration.setActivityBehaviorFactory(Mockito.<ActivityBehaviorFactory>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
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

    // Act
    (new ActivitiMockSupport(new ProcessEngineImpl(processEngineConfiguration)))
        .mockServiceTaskWithClassDelegate("Original Class Fqn", "Mocked Class Fqn");

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getActivityBehaviorFactory();
    verify(processEngineConfiguration).getBpmnParser();
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
    verify(processEngineConfiguration).setActivityBehaviorFactory(isA(ActivityBehaviorFactory.class));
  }

  /**
   * Test {@link ActivitiMockSupport#setAllServiceTasksNoOp()}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiMockSupport#setAllServiceTasksNoOp()}
   */
  @Test
  public void testSetAllServiceTasksNoOp_thenCallsGetAsyncExecutor() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBpmnParser()).thenReturn(new BpmnParser());
    when(processEngineConfiguration.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(processEngineConfiguration.setActivityBehaviorFactory(Mockito.<ActivityBehaviorFactory>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
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

    // Act
    (new ActivitiMockSupport(new ProcessEngineImpl(processEngineConfiguration))).setAllServiceTasksNoOp();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getActivityBehaviorFactory();
    verify(processEngineConfiguration).getBpmnParser();
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
    verify(processEngineConfiguration).setActivityBehaviorFactory(isA(ActivityBehaviorFactory.class));
  }

  /**
   * Test {@link ActivitiMockSupport#addNoOpServiceTaskById(String)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiMockSupport#addNoOpServiceTaskById(String)}
   */
  @Test
  public void testAddNoOpServiceTaskById_thenCallsGetAsyncExecutor() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBpmnParser()).thenReturn(new BpmnParser());
    when(processEngineConfiguration.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(processEngineConfiguration.setActivityBehaviorFactory(Mockito.<ActivityBehaviorFactory>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
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

    // Act
    (new ActivitiMockSupport(new ProcessEngineImpl(processEngineConfiguration))).addNoOpServiceTaskById("42");

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getActivityBehaviorFactory();
    verify(processEngineConfiguration).getBpmnParser();
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
    verify(processEngineConfiguration).setActivityBehaviorFactory(isA(ActivityBehaviorFactory.class));
  }

  /**
   * Test {@link ActivitiMockSupport#addNoOpServiceTaskByClassName(String)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiMockSupport#addNoOpServiceTaskByClassName(String)}
   */
  @Test
  public void testAddNoOpServiceTaskByClassName_thenCallsGetAsyncExecutor() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBpmnParser()).thenReturn(new BpmnParser());
    when(processEngineConfiguration.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(processEngineConfiguration.setActivityBehaviorFactory(Mockito.<ActivityBehaviorFactory>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
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

    // Act
    (new ActivitiMockSupport(new ProcessEngineImpl(processEngineConfiguration)))
        .addNoOpServiceTaskByClassName("Class Name");

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getActivityBehaviorFactory();
    verify(processEngineConfiguration).getBpmnParser();
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
    verify(processEngineConfiguration).setActivityBehaviorFactory(isA(ActivityBehaviorFactory.class));
  }

  /**
   * Test {@link ActivitiMockSupport#getNrOfNoOpServiceTaskExecutions()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiMockSupport#getNrOfNoOpServiceTaskExecutions()}
   */
  @Test
  public void testGetNrOfNoOpServiceTaskExecutions_thenReturnZero() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBpmnParser()).thenReturn(new BpmnParser());
    when(processEngineConfiguration.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(processEngineConfiguration.setActivityBehaviorFactory(Mockito.<ActivityBehaviorFactory>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
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

    // Act
    int actualNrOfNoOpServiceTaskExecutions = (new ActivitiMockSupport(
        new ProcessEngineImpl(processEngineConfiguration))).getNrOfNoOpServiceTaskExecutions();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getActivityBehaviorFactory();
    verify(processEngineConfiguration).getBpmnParser();
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
    verify(processEngineConfiguration).setActivityBehaviorFactory(isA(ActivityBehaviorFactory.class));
    assertEquals(0, actualNrOfNoOpServiceTaskExecutions);
  }

  /**
   * Test
   * {@link ActivitiMockSupport#getExecutedNoOpServiceTaskDelegateClassNames()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiMockSupport#getExecutedNoOpServiceTaskDelegateClassNames()}
   */
  @Test
  public void testGetExecutedNoOpServiceTaskDelegateClassNames_thenReturnEmpty() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBpmnParser()).thenReturn(new BpmnParser());
    when(processEngineConfiguration.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(processEngineConfiguration.setActivityBehaviorFactory(Mockito.<ActivityBehaviorFactory>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
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

    // Act
    List<String> actualExecutedNoOpServiceTaskDelegateClassNames = (new ActivitiMockSupport(
        new ProcessEngineImpl(processEngineConfiguration))).getExecutedNoOpServiceTaskDelegateClassNames();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getActivityBehaviorFactory();
    verify(processEngineConfiguration).getBpmnParser();
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
    verify(processEngineConfiguration).setActivityBehaviorFactory(isA(ActivityBehaviorFactory.class));
    assertTrue(actualExecutedNoOpServiceTaskDelegateClassNames.isEmpty());
  }

  /**
   * Test {@link ActivitiMockSupport#reset()}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiMockSupport#reset()}
   */
  @Test
  public void testReset_thenCallsGetAsyncExecutor() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBpmnParser()).thenReturn(new BpmnParser());
    when(processEngineConfiguration.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(processEngineConfiguration.setActivityBehaviorFactory(Mockito.<ActivityBehaviorFactory>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
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

    // Act
    (new ActivitiMockSupport(new ProcessEngineImpl(processEngineConfiguration))).reset();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getActivityBehaviorFactory();
    verify(processEngineConfiguration).getBpmnParser();
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
    verify(processEngineConfiguration).setActivityBehaviorFactory(isA(ActivityBehaviorFactory.class));
  }
}
