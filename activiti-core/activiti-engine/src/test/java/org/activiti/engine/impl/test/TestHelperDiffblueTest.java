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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ProcessEngine;
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
import org.activiti.engine.test.mock.ActivitiMockSupport;
import org.activiti.engine.test.mock.MockServiceTask;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class TestHelperDiffblueTest {
  /**
   * Test {@link TestHelper#annotationDeploymentSetUp(ProcessEngine, Class, String)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TestHelper#annotationDeploymentSetUp(ProcessEngine, Class,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TestHelper.annotationDeploymentSetUp(ProcessEngine, Class, String)"})
  public void testAnnotationDeploymentSetUp_thenReturnNull() {
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
    ProcessEngineImpl processEngine = new ProcessEngineImpl(processEngineConfiguration);
    Class<Object> testClass = Object.class;

    // Act
    String actualAnnotationDeploymentSetUpResult =
        TestHelper.annotationDeploymentSetUp(processEngine, testClass, "Method Name");

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
   * Test {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String, Class, String)}.
   *
   * <p>Method under test: {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String,
   * Class, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TestHelper.annotationDeploymentTearDown(ProcessEngine, String, Class, String)"
  })
  public void testAnnotationDeploymentTearDown() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    RepositoryServiceImpl repositoryServiceImpl = mock(RepositoryServiceImpl.class);
    doNothing().when(repositoryServiceImpl).deleteDeployment(Mockito.<String>any(), anyBoolean());

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
    ProcessEngineImpl processEngine = new ProcessEngineImpl(processEngineConfiguration);
    Class<Object> testClass = Object.class;

    // Act
    TestHelper.annotationDeploymentTearDown(processEngine, "42", testClass, "Method Name");

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(repositoryServiceImpl).deleteDeployment("42", true);
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
   * Test {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String, Class, String)}.
   *
   * <p>Method under test: {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String,
   * Class, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TestHelper.annotationDeploymentTearDown(ProcessEngine, String, Class, String)"
  })
  public void testAnnotationDeploymentTearDown2() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    RepositoryServiceImpl repositoryServiceImpl = mock(RepositoryServiceImpl.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(repositoryServiceImpl)
        .deleteDeployment(Mockito.<String>any(), anyBoolean());

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
    ProcessEngineImpl processEngine = new ProcessEngineImpl(processEngineConfiguration);
    Class<Object> testClass = Object.class;

    // Act
    TestHelper.annotationDeploymentTearDown(processEngine, "42", testClass, "Method Name");

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(repositoryServiceImpl).deleteDeployment("42", true);
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
   * Test {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String, Class, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineImpl#getRepositoryService()}.
   * </ul>
   *
   * <p>Method under test: {@link TestHelper#annotationDeploymentTearDown(ProcessEngine, String,
   * Class, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TestHelper.annotationDeploymentTearDown(ProcessEngine, String, Class, String)"
  })
  public void testAnnotationDeploymentTearDown_thenCallsGetRepositoryService() {
    // Arrange
    ProcessEngineImpl processEngine = mock(ProcessEngineImpl.class);
    Class<Object> objectClass = Object.class;
    when(processEngine.getRepositoryService())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    Class<Object> testClass = Object.class;

    // Act
    TestHelper.annotationDeploymentTearDown(processEngine, "42", testClass, "Method Name");

    // Assert
    verify(processEngine).getRepositoryService();
  }

  /**
   * Test {@link TestHelper#handleMockServiceTaskAnnotation(ActivitiMockSupport, MockServiceTask)}
   * with {@code mockSupport}, {@code mockedServiceTask}.
   *
   * <p>Method under test: {@link TestHelper#handleMockServiceTaskAnnotation(ActivitiMockSupport,
   * MockServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TestHelper.handleMockServiceTaskAnnotation(ActivitiMockSupport, MockServiceTask)"
  })
  public void testHandleMockServiceTaskAnnotationWithMockSupportMockedServiceTask() {
    // Arrange
    MockServiceTask mockedServiceTask = mock(MockServiceTask.class);
    Class<Object> objectClass = Object.class;
    when(mockedServiceTask.originalClassName())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> TestHelper.handleMockServiceTaskAnnotation(null, mockedServiceTask));
    verify(mockedServiceTask).originalClassName();
  }

  /**
   * Test {@link TestHelper#handleMockServiceTaskAnnotation(ActivitiMockSupport, MockServiceTask)}
   * with {@code mockSupport}, {@code mockedServiceTask}.
   *
   * <p>Method under test: {@link TestHelper#handleMockServiceTaskAnnotation(ActivitiMockSupport,
   * MockServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TestHelper.handleMockServiceTaskAnnotation(ActivitiMockSupport, MockServiceTask)"
  })
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
   *
   * <ul>
   *   <li>Then calls {@link ActivitiMockSupport#reset()}.
   * </ul>
   *
   * <p>Method under test: {@link TestHelper#annotationMockSupportTeardown(ActivitiMockSupport)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TestHelper.annotationMockSupportTeardown(ActivitiMockSupport)"})
  public void testAnnotationMockSupportTeardown_thenCallsReset() {
    // Arrange
    ActivitiMockSupport mockSupport = mock(ActivitiMockSupport.class);
    doNothing().when(mockSupport).reset();

    // Act
    TestHelper.annotationMockSupportTeardown(mockSupport);

    // Assert
    verify(mockSupport).reset();
  }

  /**
   * Test {@link TestHelper#annotationMockSupportTeardown(ActivitiMockSupport)}.
   *
   * <ul>
   *   <li>Then calls {@link TestActivityBehaviorFactory#reset()}.
   * </ul>
   *
   * <p>Method under test: {@link TestHelper#annotationMockSupportTeardown(ActivitiMockSupport)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TestHelper.annotationMockSupportTeardown(ActivitiMockSupport)"})
  public void testAnnotationMockSupportTeardown_thenCallsReset2() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory =
        mock(TestActivityBehaviorFactory.class);
    doNothing().when(testActivityBehaviorFactory).reset();

    // Act
    TestHelper.annotationMockSupportTeardown(new ActivitiMockSupport(testActivityBehaviorFactory));

    // Assert
    verify(testActivityBehaviorFactory).reset();
  }

  /**
   * Test {@link TestHelper#getBpmnProcessDefinitionResource(Class, String)}.
   *
   * <ul>
   *   <li>Then return {@code java/lang/Object.Name.bpmn20.xml}.
   * </ul>
   *
   * <p>Method under test: {@link TestHelper#getBpmnProcessDefinitionResource(Class, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TestHelper.getBpmnProcessDefinitionResource(Class, String)"})
  public void testGetBpmnProcessDefinitionResource_thenReturnJavaLangObjectNameBpmn20Xml() {
    // Arrange
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals(
        "java/lang/Object.Name.bpmn20.xml",
        TestHelper.getBpmnProcessDefinitionResource(type, "Name"));
  }

  /**
   * Test {@link TestHelper#assertAndEnsureCleanDb(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getAsyncExecutor()}.
   * </ul>
   *
   * <p>Method under test: {@link TestHelper#assertAndEnsureCleanDb(ProcessEngine)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TestHelper.assertAndEnsureCleanDb(ProcessEngine)"})
  public void testAssertAndEnsureCleanDb_thenCallsGetAsyncExecutor() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = mock(ManagementServiceImpl.class);
    when(managementServiceImpl.getTableCount()).thenReturn(new HashMap<>());

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
    TestHelper.assertAndEnsureCleanDb(new ProcessEngineImpl(processEngineConfiguration));

    // Assert
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
   * Test {@link TestHelper#initializeTestActivityBehaviorFactory(ActivityBehaviorFactory)}.
   *
   * <p>Method under test: {@link
   * TestHelper#initializeTestActivityBehaviorFactory(ActivityBehaviorFactory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TestActivityBehaviorFactory TestHelper.initializeTestActivityBehaviorFactory(ActivityBehaviorFactory)"
  })
  public void testInitializeTestActivityBehaviorFactory() {
    // Arrange
    DefaultActivityBehaviorFactory existingActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory();

    // Act
    TestActivityBehaviorFactory actualInitializeTestActivityBehaviorFactoryResult =
        TestHelper.initializeTestActivityBehaviorFactory(existingActivityBehaviorFactory);

    // Assert
    assertTrue(
        actualInitializeTestActivityBehaviorFactoryResult.getMessageExecutionContextFactory()
            instanceof DefaultMessageExecutionContextFactory);
    assertTrue(
        actualInitializeTestActivityBehaviorFactoryResult.getMessagePayloadMappingProviderFactory()
            instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualInitializeTestActivityBehaviorFactoryResult.getExpressionManager());
    assertSame(
        existingActivityBehaviorFactory,
        actualInitializeTestActivityBehaviorFactoryResult.getWrappedActivityBehaviorFactory());
  }
}
