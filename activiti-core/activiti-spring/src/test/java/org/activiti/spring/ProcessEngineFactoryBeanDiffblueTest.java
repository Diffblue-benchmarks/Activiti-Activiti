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
package org.activiti.spring;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.transaction.TransactionManager;
import java.util.ArrayList;
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
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.PlatformTransactionManager;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProcessEngineFactoryBeanDiffblueTest {
  /**
   * Test {@link ProcessEngineFactoryBean#getObject()}.
   *
   * <ul>
   *   <li>Then calls {@link SpringProcessEngineConfiguration#buildProcessEngine()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineFactoryBean#getObject()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngine ProcessEngineFactoryBean.getObject()"})
  public void testGetObject_thenCallsBuildProcessEngine() throws Exception {
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
        .thenReturn(new DynamicBpmnServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(
            defaultConfig, new SpringTransactionInterceptor(new DataSourceTransactionManager()));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    ProcessEngineImpl processEngineImpl = new ProcessEngineImpl(processEngineConfiguration);

    SpringProcessEngineConfiguration processEngineConfiguration2 =
        mock(SpringProcessEngineConfiguration.class);
    when(processEngineConfiguration2.setTransactionsExternallyManaged(anyBoolean()))
        .thenReturn(new SpringProcessEngineConfiguration());
    when(processEngineConfiguration2.getBeans()).thenReturn(new HashMap<>());
    when(processEngineConfiguration2.buildProcessEngine()).thenReturn(processEngineImpl);
    when(processEngineConfiguration2.getExpressionManager()).thenReturn(new ExpressionManager());
    when(processEngineConfiguration2.getTransactionManager())
        .thenReturn(new DataSourceTransactionManager());
    doNothing()
        .when(processEngineConfiguration2)
        .setTransactionManager(Mockito.<PlatformTransactionManager>any());
    processEngineConfiguration2.setTransactionManager(null);

    ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration2);
    processEngineFactoryBean.setApplicationContext(null);

    // Act
    ProcessEngine actualObject = processEngineFactoryBean.getObject();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineConfiguration2).setTransactionsExternallyManaged(true);
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration2).getBeans();
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2).getExpressionManager();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    verify(processEngineConfiguration2).buildProcessEngine();
    verify(processEngineConfiguration2).getTransactionManager();
    verify(processEngineConfiguration2).setTransactionManager(isNull());
    assertSame(processEngineImpl, actualObject);
  }

  /**
   * Test {@link ProcessEngineFactoryBean#getObject()}.
   *
   * <ul>
   *   <li>Then calls {@link SpringProcessEngineConfiguration#getCustomFunctionProviders()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineFactoryBean#getObject()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngine ProcessEngineFactoryBean.getObject()"})
  public void testGetObject_thenCallsGetCustomFunctionProviders() throws Exception {
    // Arrange
    SpringProcessEngineConfiguration processEngineConfiguration =
        mock(SpringProcessEngineConfiguration.class);
    when(processEngineConfiguration.setTransactionsExternallyManaged(anyBoolean()))
        .thenThrow(new FactoryBeanNotInitializedException("Msg"));
    when(processEngineConfiguration.getCustomFunctionProviders()).thenReturn(new ArrayList<>());
    when(processEngineConfiguration.getBeans()).thenReturn(new HashMap<>());
    when(processEngineConfiguration.setExpressionManager(Mockito.<ExpressionManager>any()))
        .thenReturn(new SpringProcessEngineConfiguration());
    when(processEngineConfiguration.getExpressionManager()).thenReturn(null);
    when(processEngineConfiguration.getTransactionManager())
        .thenReturn(new DataSourceTransactionManager());
    doNothing()
        .when(processEngineConfiguration)
        .setTransactionManager(Mockito.<PlatformTransactionManager>any());
    processEngineConfiguration.setTransactionManager(null);

    ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration);
    processEngineFactoryBean.setApplicationContext(mock(ApplicationContext.class));

    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration2.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration2.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration2.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration2.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration2.getManagementService())
        .thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration2.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration2.getRepositoryService())
        .thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration2.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration2.getTaskService())
        .thenReturn(new TaskServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration2.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration2.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration2.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(
            defaultConfig, new SpringTransactionInterceptor(new DataSourceTransactionManager()));
    when(processEngineConfiguration2.getCommandExecutor()).thenReturn(commandExecutorImpl);
    new ProcessEngineImpl(processEngineConfiguration2);

    // Act and Assert
    assertThrows(
        FactoryBeanNotInitializedException.class, () -> processEngineFactoryBean.getObject());
    verify(processEngineConfiguration2).getAsyncExecutor();
    verify(processEngineConfiguration2, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration2).getProcessEngineName();
    verify(processEngineConfiguration).setTransactionsExternallyManaged(true);
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getBeans();
    verify(processEngineConfiguration2).getCommandExecutor();
    verify(processEngineConfiguration).getCustomFunctionProviders();
    verify(processEngineConfiguration2).getDynamicBpmnService();
    verify(processEngineConfiguration2).getEventDispatcher();
    verify(processEngineConfiguration).getExpressionManager();
    verify(processEngineConfiguration2).getHistoryService();
    verify(processEngineConfiguration2).getManagementService();
    verify(processEngineConfiguration2).getRepositoryService();
    verify(processEngineConfiguration2).getRuntimeService();
    verify(processEngineConfiguration2).getSessionFactories();
    verify(processEngineConfiguration2).getTaskService();
    verify(processEngineConfiguration2).getTransactionContextFactory();
    verify(processEngineConfiguration2).isUsingRelationalDatabase();
    verify(processEngineConfiguration).setExpressionManager(isA(ExpressionManager.class));
    verify(processEngineConfiguration).getTransactionManager();
    verify(processEngineConfiguration).setTransactionManager(isNull());
  }

  /**
   * Test {@link ProcessEngineFactoryBean#getObject()}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       JtaProcessEngineConfiguration#setTransactionManager(TransactionManager)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineFactoryBean#getObject()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngine ProcessEngineFactoryBean.getObject()"})
  public void testGetObject_thenCallsSetTransactionManager() throws Exception {
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
        .thenReturn(new DynamicBpmnServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(
            defaultConfig, new SpringTransactionInterceptor(new DataSourceTransactionManager()));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    ProcessEngineImpl processEngineImpl = new ProcessEngineImpl(processEngineConfiguration);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getBeans()).thenReturn(new HashMap<>());
    when(processEngineConfiguration2.buildProcessEngine()).thenReturn(processEngineImpl);
    when(processEngineConfiguration2.getExpressionManager()).thenReturn(new ExpressionManager());
    doNothing()
        .when(processEngineConfiguration2)
        .setTransactionManager(Mockito.<TransactionManager>any());
    processEngineConfiguration2.setTransactionManager(mock(TransactionManager.class));

    ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration2);

    // Act
    ProcessEngine actualObject = processEngineFactoryBean.getObject();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration2).setTransactionManager(isA(TransactionManager.class));
    verify(processEngineConfiguration2).buildProcessEngine();
    verify(processEngineConfiguration2).getBeans();
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2).getExpressionManager();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    assertSame(processEngineImpl, actualObject);
  }

  /**
   * Test {@link ProcessEngineFactoryBean#getObject()}.
   *
   * <ul>
   *   <li>Then throw {@link FactoryBeanNotInitializedException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineFactoryBean#getObject()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngine ProcessEngineFactoryBean.getObject()"})
  public void testGetObject_thenThrowFactoryBeanNotInitializedException() throws Exception {
    // Arrange
    SpringProcessEngineConfiguration processEngineConfiguration =
        mock(SpringProcessEngineConfiguration.class);
    when(processEngineConfiguration.setTransactionsExternallyManaged(anyBoolean()))
        .thenThrow(new FactoryBeanNotInitializedException("Msg"));
    when(processEngineConfiguration.getExpressionManager()).thenReturn(new ExpressionManager());
    when(processEngineConfiguration.getTransactionManager())
        .thenReturn(new DataSourceTransactionManager());
    doNothing()
        .when(processEngineConfiguration)
        .setTransactionManager(Mockito.<PlatformTransactionManager>any());
    processEngineConfiguration.setTransactionManager(null);

    ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration);
    processEngineFactoryBean.setApplicationContext(mock(ApplicationContext.class));

    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration2.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration2.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration2.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration2.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration2.getManagementService())
        .thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration2.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration2.getRepositoryService())
        .thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration2.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration2.getTaskService())
        .thenReturn(new TaskServiceImpl(new SpringProcessEngineConfiguration()));
    when(processEngineConfiguration2.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration2.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration2.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(
            defaultConfig, new SpringTransactionInterceptor(new DataSourceTransactionManager()));
    when(processEngineConfiguration2.getCommandExecutor()).thenReturn(commandExecutorImpl);
    new ProcessEngineImpl(processEngineConfiguration2);

    // Act and Assert
    assertThrows(
        FactoryBeanNotInitializedException.class, () -> processEngineFactoryBean.getObject());
    verify(processEngineConfiguration2).getAsyncExecutor();
    verify(processEngineConfiguration2, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration2).getProcessEngineName();
    verify(processEngineConfiguration).setTransactionsExternallyManaged(true);
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration2).getCommandExecutor();
    verify(processEngineConfiguration2).getDynamicBpmnService();
    verify(processEngineConfiguration2).getEventDispatcher();
    verify(processEngineConfiguration).getExpressionManager();
    verify(processEngineConfiguration2).getHistoryService();
    verify(processEngineConfiguration2).getManagementService();
    verify(processEngineConfiguration2).getRepositoryService();
    verify(processEngineConfiguration2).getRuntimeService();
    verify(processEngineConfiguration2).getSessionFactories();
    verify(processEngineConfiguration2).getTaskService();
    verify(processEngineConfiguration2).getTransactionContextFactory();
    verify(processEngineConfiguration2).isUsingRelationalDatabase();
    verify(processEngineConfiguration).getTransactionManager();
    verify(processEngineConfiguration).setTransactionManager(isNull());
  }
}
