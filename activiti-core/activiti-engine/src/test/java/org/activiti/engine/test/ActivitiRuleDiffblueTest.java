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
package org.activiti.engine.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
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
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.test.mock.ActivitiMockSupport;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

public class ActivitiRuleDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiRule#ActivitiRule()}
   *   <li>{@link ActivitiRule#setConfigurationResource(String)}
   *   <li>{@link ActivitiRule#setHistoricDataService(HistoryService)}
   *   <li>{@link ActivitiRule#setManagementService(ManagementService)}
   *   <li>
   * {@link ActivitiRule#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link ActivitiRule#setRepositoryService(RepositoryService)}
   *   <li>{@link ActivitiRule#setRuntimeService(RuntimeService)}
   *   <li>{@link ActivitiRule#setTaskService(TaskService)}
   *   <li>{@link ActivitiRule#configureProcessEngine()}
   *   <li>{@link ActivitiRule#failed(Throwable, Description)}
   *   <li>{@link ActivitiRule#skipped(AssumptionViolatedException, Description)}
   *   <li>{@link ActivitiRule#succeeded(Description)}
   *   <li>{@link ActivitiRule#getConfigurationResource()}
   *   <li>{@link ActivitiRule#getHistoryService()}
   *   <li>{@link ActivitiRule#getManagementService()}
   *   <li>{@link ActivitiRule#getMockSupport()}
   *   <li>{@link ActivitiRule#getProcessEngine()}
   *   <li>{@link ActivitiRule#getRepositoryService()}
   *   <li>{@link ActivitiRule#getRuntimeService()}
   *   <li>{@link ActivitiRule#getTaskService()}
   *   <li>{@link ActivitiRule#mockSupport()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ActivitiRule actualActivitiRule = new ActivitiRule();
    actualActivitiRule.setConfigurationResource("Configuration Resource");
    HistoryServiceImpl historicDataService = new HistoryServiceImpl(new JtaProcessEngineConfiguration());
    actualActivitiRule.setHistoricDataService(historicDataService);
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    actualActivitiRule.setManagementService(managementService);
    actualActivitiRule.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    actualActivitiRule.setRepositoryService(repositoryService);
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    actualActivitiRule.setRuntimeService(runtimeService);
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    actualActivitiRule.setTaskService(taskService);
    actualActivitiRule.configureProcessEngine();
    actualActivitiRule.failed(new Throwable(), null);
    actualActivitiRule.skipped(new AssumptionViolatedException("Assumption"), null);
    actualActivitiRule.succeeded(null);
    String actualConfigurationResource = actualActivitiRule.getConfigurationResource();
    HistoryService actualHistoryService = actualActivitiRule.getHistoryService();
    ManagementService actualManagementService = actualActivitiRule.getManagementService();
    actualActivitiRule.getMockSupport();
    actualActivitiRule.getProcessEngine();
    RepositoryService actualRepositoryService = actualActivitiRule.getRepositoryService();
    RuntimeService actualRuntimeService = actualActivitiRule.getRuntimeService();
    TaskService actualTaskService = actualActivitiRule.getTaskService();
    actualActivitiRule.mockSupport();

    // Assert that nothing has changed
    assertTrue(actualHistoryService instanceof HistoryServiceImpl);
    assertTrue(actualManagementService instanceof ManagementServiceImpl);
    assertTrue(actualRepositoryService instanceof RepositoryServiceImpl);
    assertTrue(actualRuntimeService instanceof RuntimeServiceImpl);
    assertTrue(actualTaskService instanceof TaskServiceImpl);
    assertEquals("Configuration Resource", actualConfigurationResource);
    assertSame(historicDataService, actualHistoryService);
    assertSame(managementService, actualManagementService);
    assertSame(repositoryService, actualRepositoryService);
    assertSame(runtimeService, actualRuntimeService);
    assertSame(taskService, actualTaskService);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Configuration Resource}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiRule#ActivitiRule(String)}
   *   <li>{@link ActivitiRule#setConfigurationResource(String)}
   *   <li>{@link ActivitiRule#setHistoricDataService(HistoryService)}
   *   <li>{@link ActivitiRule#setManagementService(ManagementService)}
   *   <li>
   * {@link ActivitiRule#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link ActivitiRule#setRepositoryService(RepositoryService)}
   *   <li>{@link ActivitiRule#setRuntimeService(RuntimeService)}
   *   <li>{@link ActivitiRule#setTaskService(TaskService)}
   *   <li>{@link ActivitiRule#configureProcessEngine()}
   *   <li>{@link ActivitiRule#failed(Throwable, Description)}
   *   <li>{@link ActivitiRule#skipped(AssumptionViolatedException, Description)}
   *   <li>{@link ActivitiRule#succeeded(Description)}
   *   <li>{@link ActivitiRule#getConfigurationResource()}
   *   <li>{@link ActivitiRule#getHistoryService()}
   *   <li>{@link ActivitiRule#getManagementService()}
   *   <li>{@link ActivitiRule#getMockSupport()}
   *   <li>{@link ActivitiRule#getProcessEngine()}
   *   <li>{@link ActivitiRule#getRepositoryService()}
   *   <li>{@link ActivitiRule#getRuntimeService()}
   *   <li>{@link ActivitiRule#getTaskService()}
   *   <li>{@link ActivitiRule#mockSupport()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenConfigurationResource() {
    // Arrange and Act
    ActivitiRule actualActivitiRule = new ActivitiRule("Configuration Resource");
    actualActivitiRule.setConfigurationResource("Configuration Resource");
    HistoryServiceImpl historicDataService = new HistoryServiceImpl(new JtaProcessEngineConfiguration());
    actualActivitiRule.setHistoricDataService(historicDataService);
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    actualActivitiRule.setManagementService(managementService);
    actualActivitiRule.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    actualActivitiRule.setRepositoryService(repositoryService);
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    actualActivitiRule.setRuntimeService(runtimeService);
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    actualActivitiRule.setTaskService(taskService);
    actualActivitiRule.configureProcessEngine();
    actualActivitiRule.failed(new Throwable(), null);
    actualActivitiRule.skipped(new AssumptionViolatedException("Assumption"), null);
    actualActivitiRule.succeeded(null);
    String actualConfigurationResource = actualActivitiRule.getConfigurationResource();
    HistoryService actualHistoryService = actualActivitiRule.getHistoryService();
    ManagementService actualManagementService = actualActivitiRule.getManagementService();
    actualActivitiRule.getMockSupport();
    actualActivitiRule.getProcessEngine();
    RepositoryService actualRepositoryService = actualActivitiRule.getRepositoryService();
    RuntimeService actualRuntimeService = actualActivitiRule.getRuntimeService();
    TaskService actualTaskService = actualActivitiRule.getTaskService();
    actualActivitiRule.mockSupport();

    // Assert that nothing has changed
    assertTrue(actualHistoryService instanceof HistoryServiceImpl);
    assertTrue(actualManagementService instanceof ManagementServiceImpl);
    assertTrue(actualRepositoryService instanceof RepositoryServiceImpl);
    assertTrue(actualRuntimeService instanceof RuntimeServiceImpl);
    assertTrue(actualTaskService instanceof TaskServiceImpl);
    assertEquals("Configuration Resource", actualConfigurationResource);
    assertSame(historicDataService, actualHistoryService);
    assertSame(managementService, actualManagementService);
    assertSame(repositoryService, actualRepositoryService);
    assertSame(runtimeService, actualRuntimeService);
    assertSame(taskService, actualTaskService);
  }

  /**
   * Test {@link ActivitiRule#ActivitiRule(ProcessEngine)}.
   * <ul>
   *   <li>Then HistoryService return {@link HistoryServiceImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiRule#ActivitiRule(ProcessEngine)}
   */
  @Test
  public void testNewActivitiRule_thenHistoryServiceReturnHistoryServiceImpl() {
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
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    ProcessEngineImpl processEngine = new ProcessEngineImpl(processEngineConfiguration);

    // Act
    ActivitiRule actualActivitiRule = new ActivitiRule(processEngine);

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
    HistoryService historyService = actualActivitiRule.getHistoryService();
    assertTrue(historyService instanceof HistoryServiceImpl);
    ManagementService managementService = actualActivitiRule.getManagementService();
    assertTrue(managementService instanceof ManagementServiceImpl);
    RepositoryService repositoryService = actualActivitiRule.getRepositoryService();
    assertTrue(repositoryService instanceof RepositoryServiceImpl);
    RuntimeService runtimeService = actualActivitiRule.getRuntimeService();
    assertTrue(runtimeService instanceof RuntimeServiceImpl);
    TaskService taskService = actualActivitiRule.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    assertEquals("activiti.cfg.xml", actualActivitiRule.getConfigurationResource());
    assertNull(actualActivitiRule.deploymentId);
    assertNull(((HistoryServiceImpl) historyService).getCommandExecutor());
    assertNull(((ManagementServiceImpl) managementService).getCommandExecutor());
    assertNull(((RepositoryServiceImpl) repositoryService).getCommandExecutor());
    assertNull(((RuntimeServiceImpl) runtimeService).getCommandExecutor());
    assertNull(((TaskServiceImpl) taskService).getCommandExecutor());
    assertNull(actualActivitiRule.getMockSupport());
    assertNull(actualActivitiRule.mockSupport());
    assertSame(historyServiceImpl, historyService);
    assertSame(managementServiceImpl, managementService);
    assertSame(processEngine, actualActivitiRule.getProcessEngine());
    assertSame(repositoryServiceImpl, repositoryService);
    assertSame(runtimeServiceImpl, runtimeService);
    assertSame(taskServiceImpl, taskService);
    ProcessEngineConfiguration expectedProcessEngineConfiguration = actualActivitiRule.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, processEngine.getProcessEngineConfiguration());
  }

  /**
   * Test {@link ActivitiRule#apply(Statement, Description)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiRule#apply(Statement, Description)}
   */
  @Test
  public void testApply_thenCallsGetAsyncExecutor() {
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
    ActivitiRule activitiRule = new ActivitiRule(new ProcessEngineImpl(processEngineConfiguration));

    // Act
    activitiRule.apply(new Fail(new Throwable()), null);

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
  }

  /**
   * Test {@link ActivitiRule#starting(Description)}.
   * <p>
   * Method under test: {@link ActivitiRule#starting(Description)}
   */
  @Test
  public void testStarting() {
    // Arrange
    ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.xml");
    activitiRule.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    Description description = mock(Description.class);
    when(description.getClassName()).thenReturn("Class Name");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> activitiRule.starting(description));
    verify(description, atLeast(1)).getClassName();
  }

  /**
   * Test {@link ActivitiRule#starting(Description)}.
   * <ul>
   *   <li>Given {@link ActivitiException#ActivitiException(String)} with message is
   * {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiRule#starting(Description)}
   */
  @Test
  public void testStarting_givenActivitiExceptionWithMessageIsAnErrorOccurred() {
    // Arrange
    ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.xml");
    Description description = mock(Description.class);
    when(description.getClassName()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> activitiRule.starting(description));
    verify(description).getClassName();
  }

  /**
   * Test {@link ActivitiRule#starting(Description)}.
   * <ul>
   *   <li>Given {@code Class Name}.</li>
   *   <li>When {@link Description} {@link Description#getClassName()} return
   * {@code Class Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiRule#starting(Description)}
   */
  @Test
  public void testStarting_givenClassName_whenDescriptionGetClassNameReturnClassName() {
    // Arrange
    ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.xml");
    Description description = mock(Description.class);
    when(description.getClassName()).thenReturn("Class Name");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> activitiRule.starting(description));
    verify(description, atLeast(1)).getClassName();
  }

  /**
   * Test {@link ActivitiRule#initializeServices()}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiRule#initializeServices()}
   */
  @Test
  public void testInitializeServices_thenCallsGetAsyncExecutor() {
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
    ProcessEngineImpl processEngine = new ProcessEngineImpl(processEngineConfiguration);

    ActivitiRule activitiRule = new ActivitiRule("Configuration Resource");
    activitiRule.setProcessEngine(processEngine);

    // Act
    activitiRule.initializeServices();

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
  }

  /**
   * Test {@link ActivitiRule#initializeMockSupport()}.
   * <p>
   * Method under test: {@link ActivitiRule#initializeMockSupport()}
   */
  @Test
  public void testInitializeMockSupport() {
    // Arrange
    ActivitiRule activitiRule = new ActivitiRule("Configuration Resource");

    // Act
    activitiRule.initializeMockSupport();

    // Assert that nothing has changed
    assertNull(activitiRule.mockSupport());
  }

  /**
   * Test {@link ActivitiRule#initializeMockSupport()}.
   * <p>
   * Method under test: {@link ActivitiRule#initializeMockSupport()}
   */
  @Test
  public void testInitializeMockSupport2() {
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
    ProcessEngineImpl processEngine = new ProcessEngineImpl(processEngineConfiguration);

    ActivitiRule activitiRule = new ActivitiRule("Configuration Resource");
    activitiRule.setProcessEngine(processEngine);

    // Act
    activitiRule.initializeMockSupport();

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
    ActivitiMockSupport mockSupport = activitiRule.getMockSupport();
    assertEquals(0, mockSupport.getNrOfNoOpServiceTaskExecutions());
    assertTrue(mockSupport.getExecutedNoOpServiceTaskDelegateClassNames().isEmpty());
    assertSame(mockSupport, activitiRule.mockSupport());
  }

  /**
   * Test {@link ActivitiRule#finished(Description)}.
   * <ul>
   *   <li>Given {@link ActivitiException#ActivitiException(String)} with message is
   * {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiRule#finished(Description)}
   */
  @Test
  public void testFinished_givenActivitiExceptionWithMessageIsAnErrorOccurred() {
    // Arrange
    ActivitiRule activitiRule = new ActivitiRule("Configuration Resource");
    Description description = mock(Description.class);
    when(description.getClassName()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> activitiRule.finished(description));
    verify(description).getClassName();
  }

  /**
   * Test {@link ActivitiRule#finished(Description)}.
   * <ul>
   *   <li>Given {@code Class Name}.</li>
   *   <li>When {@link Description} {@link Description#getClassName()} return
   * {@code Class Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiRule#finished(Description)}
   */
  @Test
  public void testFinished_givenClassName_whenDescriptionGetClassNameReturnClassName() {
    // Arrange
    ActivitiRule activitiRule = new ActivitiRule("Configuration Resource");
    Description description = mock(Description.class);
    when(description.getClassName()).thenReturn("Class Name");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> activitiRule.finished(description));
    verify(description, atLeast(1)).getClassName();
  }

  /**
   * Test {@link ActivitiRule#setCurrentTime(Date)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getClock()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiRule#setCurrentTime(Date)}
   */
  @Test
  public void testSetCurrentTime_thenCallsGetClock() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());

    ActivitiRule activitiRule = new ActivitiRule("Configuration Resource");
    activitiRule.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    activitiRule.setCurrentTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(processEngineConfiguration).getClock();
  }

  /**
   * Test {@link ActivitiRule#setProcessEngine(ProcessEngine)}.
   * <p>
   * Method under test: {@link ActivitiRule#setProcessEngine(ProcessEngine)}
   */
  @Test
  public void testSetProcessEngine() {
    // Arrange
    ActivitiRule activitiRule = new ActivitiRule();
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
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
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    ProcessEngineImpl processEngine = new ProcessEngineImpl(processEngineConfiguration);

    // Act
    activitiRule.setProcessEngine(processEngine);

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
    assertSame(historyServiceImpl, activitiRule.getHistoryService());
    assertSame(managementServiceImpl, activitiRule.getManagementService());
    assertSame(processEngine, activitiRule.getProcessEngine());
    assertSame(repositoryServiceImpl, activitiRule.getRepositoryService());
    assertSame(runtimeServiceImpl, activitiRule.getRuntimeService());
    assertSame(taskServiceImpl, activitiRule.getTaskService());
    ProcessEngineConfiguration expectedProcessEngineConfiguration = activitiRule.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, processEngine.getProcessEngineConfiguration());
  }
}
