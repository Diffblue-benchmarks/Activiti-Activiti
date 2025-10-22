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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class ProcessEngineFactoryBeanDiffblueTest {
  /**
   * Test {@link ProcessEngineFactoryBean#getObject()}.
   * <p>
   * Method under test: {@link ProcessEngineFactoryBean#getObject()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngine ProcessEngineFactoryBean.getObject()"})
  public void testGetObject() throws Exception {
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
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getBeans()).thenReturn(new HashMap<>());
    when(processEngineConfiguration2.buildProcessEngine()).thenReturn(processEngineImpl);
    when(processEngineConfiguration2.getExpressionManager()).thenReturn(new ExpressionManager());
    when(processEngineConfiguration2.setClassLoader(Mockito.<ClassLoader>any()))
        .thenReturn(new SpringProcessEngineConfiguration());
    when(processEngineConfiguration2.setDatabaseSchemaUpdate(Mockito.<String>any()))
        .thenReturn(new SpringProcessEngineConfiguration());
    when(processEngineConfiguration2.setProcessEngineLifecycleListener(Mockito.<ProcessEngineLifecycleListener>any()))
        .thenReturn(new SpringProcessEngineConfiguration());
    when(processEngineConfiguration2.setUsingRelationalDatabase(anyBoolean()))
        .thenReturn(new SpringProcessEngineConfiguration());
    processEngineConfiguration2.setClassLoader(null);
    processEngineConfiguration2.setUsingRelationalDatabase(false);
    processEngineConfiguration2.setDatabaseSchemaUpdate(null);
    processEngineConfiguration2.setProcessEngineLifecycleListener(null);

    ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration2);
    processEngineFactoryBean.setApplicationContext(null);

    // Act
    ProcessEngine actualObject = processEngineFactoryBean.getObject();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineConfiguration2).setClassLoader(isNull());
    verify(processEngineConfiguration2).setDatabaseSchemaUpdate(isNull());
    verify(processEngineConfiguration2).setProcessEngineLifecycleListener(isNull());
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
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
    verify(processEngineConfiguration2).setUsingRelationalDatabase(eq(false));
    assertSame(processEngineImpl, actualObject);
  }
}
