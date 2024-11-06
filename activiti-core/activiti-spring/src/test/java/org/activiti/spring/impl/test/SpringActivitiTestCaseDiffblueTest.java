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
package org.activiti.spring.impl.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.SpringTransactionInterceptor;
import org.activiti.spring.test.autodeployment.FailOnNoProcessAutoDeploymentStrategyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class SpringActivitiTestCaseDiffblueTest {
  @Mock
  private ApplicationContext applicationContext;

  @InjectMocks
  private FailOnNoProcessAutoDeploymentStrategyTest failOnNoProcessAutoDeploymentStrategyTest;

  /**
   * Test {@link SpringActivitiTestCase#initializeProcessEngine()}.
   * <p>
   * Method under test: {@link SpringActivitiTestCase#initializeProcessEngine()}
   */
  @Test
  public void testInitializeProcessEngine() throws BeansException {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);
    doNothing().when(processEngineLifecycleListener).onProcessEngineBuilt(Mockito.<ProcessEngine>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener()).thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new SpringAsyncExecutor());
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(
        new CommandExecutorImpl(defaultConfig, new SpringTransactionInterceptor(new DataSourceTransactionManager())));
    ProcessEngineImpl processEngineImpl = new ProcessEngineImpl(processEngineConfiguration);
    when(applicationContext.getBean(Mockito.<Class<ProcessEngine>>any())).thenReturn(processEngineImpl);

    // Act
    failOnNoProcessAutoDeploymentStrategyTest.initializeProcessEngine();

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
    verify(applicationContext).getBean(isA(Class.class));
    assertEquals(1, failOnNoProcessAutoDeploymentStrategyTest.cachedProcessEngines.size());
  }

  /**
   * Test
   * {@link SpringActivitiTestCase#setApplicationContext(ApplicationContext)}.
   * <p>
   * Method under test:
   * {@link SpringActivitiTestCase#setApplicationContext(ApplicationContext)}
   */
  @Test
  public void testSetApplicationContext() {
    // Arrange
    FailOnNoProcessAutoDeploymentStrategyTest failOnNoProcessAutoDeploymentStrategyTest = new FailOnNoProcessAutoDeploymentStrategyTest();
    ReflectionTestUtils.setField(failOnNoProcessAutoDeploymentStrategyTest, "applicationContext",
        mock(ApplicationContext.class));

    // Act
    failOnNoProcessAutoDeploymentStrategyTest.setApplicationContext(new AnnotationConfigApplicationContext());

    // Assert
    ApplicationContext applicationContext = failOnNoProcessAutoDeploymentStrategyTest.applicationContext;
    assertTrue(applicationContext instanceof AnnotationConfigApplicationContext);
    assertEquals("", applicationContext.getApplicationName());
    assertNull(applicationContext.getParentBeanFactory());
    assertNull(applicationContext.getParent());
    assertEquals(0L, applicationContext.getStartupDate());
    assertEquals(5, applicationContext.getBeanDefinitionCount());
    assertFalse(((AnnotationConfigApplicationContext) applicationContext).isActive());
    assertFalse(((AnnotationConfigApplicationContext) applicationContext).isRunning());
    assertArrayEquals(
        new String[]{"org.springframework.context.annotation.internalConfigurationAnnotationProcessor",
            "org.springframework.context.annotation.internalAutowiredAnnotationProcessor",
            "org.springframework.context.annotation.internalPersistenceAnnotationProcessor",
            "org.springframework.context.event.internalEventListenerProcessor",
            "org.springframework.context.event.internalEventListenerFactory"},
        applicationContext.getBeanDefinitionNames());
  }
}
