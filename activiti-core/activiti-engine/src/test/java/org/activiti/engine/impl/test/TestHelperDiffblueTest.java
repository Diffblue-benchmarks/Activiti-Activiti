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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ActivitiObjectNotFoundException;
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
import org.activiti.engine.impl.delegate.MessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.test.TestActivityBehaviorFactory;
import org.activiti.engine.test.mock.ActivitiMockSupport;
import org.activiti.engine.test.mock.MockServiceTask;
import org.junit.Test;
import org.mockito.Mockito;

public class TestHelperDiffblueTest {
  /**
   * Test
   * {@link TestHelper#annotationDeploymentSetUp(ProcessEngine, Class, String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestHelper#annotationDeploymentSetUp(ProcessEngine, Class, String)}
   */
  @Test
  public void testAnnotationDeploymentSetUp_thenReturnNull() {
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
    Class<Object> testClass = Object.class;

    // Act
    String actualAnnotationDeploymentSetUpResult = TestHelper.annotationDeploymentSetUp(processEngine, testClass,
        "Method Name");

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
    assertNull(actualAnnotationDeploymentSetUpResult);
  }

  /**
   * Test
   * {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String, Class, String)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String, Class, String)}
   */
  @Test
  public void testAnnotationDeploymentTearDown_thenCallsGetAsyncExecutor() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    RepositoryServiceImpl repositoryServiceImpl = mock(RepositoryServiceImpl.class);
    doNothing().when(repositoryServiceImpl).deleteDeployment(Mockito.<String>any(), anyBoolean());
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
    when(processEngineConfiguration.getRepositoryService()).thenReturn(repositoryServiceImpl);
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
    Class<Object> testClass = Object.class;

    // Act
    TestHelper.annotationDeploymentTearDown(processEngine, "42", testClass, "Method Name");

    // Assert that nothing has changed
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(repositoryServiceImpl).deleteDeployment(eq("42"), eq(true));
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
   * Test
   * {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String, Class, String)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineImpl#getRepositoryService()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String, Class, String)}
   */
  @Test
  public void testAnnotationDeploymentTearDown_thenCallsGetRepositoryService() {
    // Arrange
    ProcessEngineImpl processEngine = mock(ProcessEngineImpl.class);
    when(processEngine.getRepositoryService()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Class<Object> testClass = Object.class;

    // Act
    TestHelper.annotationDeploymentTearDown(processEngine, "42", testClass, "Method Name");

    // Assert that nothing has changed
    verify(processEngine).getRepositoryService();
  }

  /**
   * Test
   * {@link TestHelper#annotationMockSupportSetup(Class, String, ActivitiMockSupport)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestHelper#annotationMockSupportSetup(Class, String, ActivitiMockSupport)}
   */
  @Test
  public void testAnnotationMockSupportSetup_thenCallsGetAsyncExecutor() {
    // Arrange
    Class<Object> testClass = Object.class;
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
    TestHelper.annotationMockSupportSetup(testClass, "Method Name",
        new ActivitiMockSupport(new ProcessEngineImpl(processEngineConfiguration)));

    // Assert that nothing has changed
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
   * {@link TestHelper#handleMockServiceTaskAnnotation(ActivitiMockSupport, MockServiceTask)}
   * with {@code mockSupport}, {@code mockedServiceTask}.
   * <p>
   * Method under test:
   * {@link TestHelper#handleMockServiceTaskAnnotation(ActivitiMockSupport, MockServiceTask)}
   */
  @Test
  public void testHandleMockServiceTaskAnnotationWithMockSupportMockedServiceTask() {
    // Arrange
    MockServiceTask mockedServiceTask = mock(MockServiceTask.class);
    when(mockedServiceTask.originalClassName()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> TestHelper.handleMockServiceTaskAnnotation(null, mockedServiceTask));
    verify(mockedServiceTask).originalClassName();
  }

  /**
   * Test
   * {@link TestHelper#handleMockServiceTaskAnnotation(ActivitiMockSupport, MockServiceTask)}
   * with {@code mockSupport}, {@code mockedServiceTask}.
   * <p>
   * Method under test:
   * {@link TestHelper#handleMockServiceTaskAnnotation(ActivitiMockSupport, MockServiceTask)}
   */
  @Test
  public void testHandleMockServiceTaskAnnotationWithMockSupportMockedServiceTask2() {
    // Arrange
    ActivitiMockSupport mockSupport = new ActivitiMockSupport(new TestActivityBehaviorFactory());
    MockServiceTask mockedServiceTask = mock(MockServiceTask.class);
    when(mockedServiceTask.mockedClassName()).thenReturn("Mocked Class Name");
    when(mockedServiceTask.originalClassName()).thenReturn("Original Class Name");

    // Act
    TestHelper.handleMockServiceTaskAnnotation(mockSupport, mockedServiceTask);

    // Assert
    verify(mockedServiceTask).mockedClassName();
    verify(mockedServiceTask).originalClassName();
  }

  /**
   * Test {@link TestHelper#annotationMockSupportTeardown(ActivitiMockSupport)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestHelper#annotationMockSupportTeardown(ActivitiMockSupport)}
   */
  @Test
  public void testAnnotationMockSupportTeardown_thenCallsGetAsyncExecutor() {
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
    TestHelper
        .annotationMockSupportTeardown(new ActivitiMockSupport(new ProcessEngineImpl(processEngineConfiguration)));

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
   * Test {@link TestHelper#annotationMockSupportTeardown(ActivitiMockSupport)}.
   * <ul>
   *   <li>Then calls {@link ActivitiMockSupport#reset()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestHelper#annotationMockSupportTeardown(ActivitiMockSupport)}
   */
  @Test
  public void testAnnotationMockSupportTeardown_thenCallsReset() {
    // Arrange
    ActivitiMockSupport mockSupport = mock(ActivitiMockSupport.class);
    doNothing().when(mockSupport).reset();

    // Act
    TestHelper.annotationMockSupportTeardown(mockSupport);

    // Assert that nothing has changed
    verify(mockSupport).reset();
  }

  /**
   * Test {@link TestHelper#getBpmnProcessDefinitionResource(Class, String)}.
   * <ul>
   *   <li>Then return {@code java/lang/Object.Name.bpmn20.xml}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestHelper#getBpmnProcessDefinitionResource(Class, String)}
   */
  @Test
  public void testGetBpmnProcessDefinitionResource_thenReturnJavaLangObjectNameBpmn20Xml() {
    // Arrange
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("java/lang/Object.Name.bpmn20.xml", TestHelper.getBpmnProcessDefinitionResource(type, "Name"));
  }

  /**
   * Test {@link TestHelper#assertAndEnsureCleanDb(ProcessEngine)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TestHelper#assertAndEnsureCleanDb(ProcessEngine)}
   */
  @Test
  public void testAssertAndEnsureCleanDb_thenCallsGetAsyncExecutor() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = mock(ManagementServiceImpl.class);
    when(managementServiceImpl.getTableCount()).thenReturn(new HashMap<>());
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
    when(processEngineConfiguration.getManagementService()).thenReturn(managementServiceImpl);
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
    TestHelper.assertAndEnsureCleanDb(new ProcessEngineImpl(processEngineConfiguration));

    // Assert that nothing has changed
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(managementServiceImpl).getTableCount();
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
   * Test
   * {@link TestHelper#initializeTestActivityBehaviorFactory(ActivityBehaviorFactory)}.
   * <p>
   * Method under test:
   * {@link TestHelper#initializeTestActivityBehaviorFactory(ActivityBehaviorFactory)}
   */
  @Test
  public void testInitializeTestActivityBehaviorFactory() {
    // Arrange
    DefaultActivityBehaviorFactory existingActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    existingActivityBehaviorFactory
        .setMessagePayloadMappingProviderFactory(mock(MessagePayloadMappingProviderFactory.class));

    // Act
    TestActivityBehaviorFactory actualInitializeTestActivityBehaviorFactoryResult = TestHelper
        .initializeTestActivityBehaviorFactory(existingActivityBehaviorFactory);

    // Assert
    assertTrue(actualInitializeTestActivityBehaviorFactoryResult
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualInitializeTestActivityBehaviorFactoryResult
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualInitializeTestActivityBehaviorFactoryResult.getExpressionManager());
    assertSame(existingActivityBehaviorFactory,
        actualInitializeTestActivityBehaviorFactoryResult.getWrappedActivityBehaviorFactory());
  }

  /**
   * Test
   * {@link TestHelper#initializeTestActivityBehaviorFactory(ActivityBehaviorFactory)}.
   * <ul>
   *   <li>When
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestHelper#initializeTestActivityBehaviorFactory(ActivityBehaviorFactory)}
   */
  @Test
  public void testInitializeTestActivityBehaviorFactory_whenDefaultActivityBehaviorFactory() {
    // Arrange
    DefaultActivityBehaviorFactory existingActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act
    TestActivityBehaviorFactory actualInitializeTestActivityBehaviorFactoryResult = TestHelper
        .initializeTestActivityBehaviorFactory(existingActivityBehaviorFactory);

    // Assert
    assertTrue(actualInitializeTestActivityBehaviorFactoryResult
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualInitializeTestActivityBehaviorFactoryResult
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualInitializeTestActivityBehaviorFactoryResult.getExpressionManager());
    assertSame(existingActivityBehaviorFactory,
        actualInitializeTestActivityBehaviorFactoryResult.getWrappedActivityBehaviorFactory());
  }
}
