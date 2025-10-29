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
package org.activiti.engine.impl.asyncexecutor.multitenant;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import javax.xml.namespace.QName;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.asyncexecutor.AcquireAsyncJobsDueRunnable;
import org.activiti.engine.impl.asyncexecutor.AcquireTimerJobsRunnable;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.JobManager;
import org.activiti.engine.impl.asyncexecutor.ManagedAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.ResetExpiredJobsRunnable;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeploymentHelper;
import org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.handler.BoundaryEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.BusinessRuleParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TransactionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextFactory;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.PropertyEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TableDataManagerImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisEventLogEntryDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisEventSubscriptionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricActivityInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricTaskInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisPropertyDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.activiti.engine.impl.util.ProcessInstanceHelper;
import org.activiti.engine.impl.variable.DefaultVariableTypes;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.activiti.engine.test.profiler.ActivitiProfiler;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class ExecutorPerTenantAsyncExecutorDiffblueTest {
  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds() {
    // Arrange, Act and Assert
    assertTrue((new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())).getTenantIds().isEmpty());
    assertTrue(
        (new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder(), mock(TenantAwareAsyncExecutorFactory.class)))
            .getTenantIds()
            .isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    ExecutorService executorService = ((DefaultAsyncJobExecutor) getResult).getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    ThreadFactory threadFactory = ((ThreadPoolExecutor) executorService).getThreadFactory();
    assertTrue(threadFactory instanceof BasicThreadFactory);
    assertEquals("42", ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantId);
    assertEquals("activiti-async-job-executor-thread-%d", ((BasicThreadFactory) threadFactory).getNamingPattern());
    assertNull(((BasicThreadFactory) threadFactory).getDaemonFlag());
    assertNull(((BasicThreadFactory) threadFactory).getPriority());
    assertNull(((BasicThreadFactory) threadFactory).getUncaughtExceptionHandler());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertEquals(1, getResult.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, getResult.getMaxTimerJobsPerAcquisition());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(10, ((DefaultAsyncJobExecutor) getResult).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) getResult).getQueueSize());
    assertEquals(10000, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
    assertEquals(2, ((DefaultAsyncJobExecutor) getResult).getCorePoolSize());
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
    assertEquals(300000, getResult.getAsyncJobLockTimeInMillis());
    assertEquals(300000, getResult.getTimerLockTimeInMillis());
    assertEquals(500, getResult.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) getResult).getKeepAliveTime());
    assertEquals(60000, getResult.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) getResult).getSecondsToWaitOnShutdown());
    assertFalse(getResult.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) getResult).isMessageQueueMode());
    BlockingQueue<Runnable> threadPoolQueue = ((DefaultAsyncJobExecutor) getResult).getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(getResult.isActive());
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    ExecutorService executorService = ((DefaultAsyncJobExecutor) getResult).getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    ThreadFactory threadFactory = ((ThreadPoolExecutor) executorService).getThreadFactory();
    assertTrue(threadFactory instanceof BasicThreadFactory);
    assertEquals("42", ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantId);
    assertEquals("activiti-async-job-executor-thread-%d", ((BasicThreadFactory) threadFactory).getNamingPattern());
    assertNull(((BasicThreadFactory) threadFactory).getDaemonFlag());
    assertNull(((BasicThreadFactory) threadFactory).getPriority());
    assertNull(((BasicThreadFactory) threadFactory).getUncaughtExceptionHandler());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertEquals(1, getResult.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, getResult.getMaxTimerJobsPerAcquisition());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(10, ((DefaultAsyncJobExecutor) getResult).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) getResult).getQueueSize());
    assertEquals(10000, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
    assertEquals(2, ((DefaultAsyncJobExecutor) getResult).getCorePoolSize());
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
    assertEquals(300000, getResult.getAsyncJobLockTimeInMillis());
    assertEquals(300000, getResult.getTimerLockTimeInMillis());
    assertEquals(500, getResult.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) getResult).getKeepAliveTime());
    assertEquals(60000, getResult.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) getResult).getSecondsToWaitOnShutdown());
    assertFalse(getResult.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) getResult).isMessageQueueMode());
    BlockingQueue<Runnable> threadPoolQueue = ((DefaultAsyncJobExecutor) getResult).getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(getResult.isActive());
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobHandlers(new HashMap<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    ExecutorService executorService = ((DefaultAsyncJobExecutor) getResult).getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    ThreadFactory threadFactory = ((ThreadPoolExecutor) executorService).getThreadFactory();
    assertTrue(threadFactory instanceof BasicThreadFactory);
    assertEquals("42", ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantId);
    assertEquals("activiti-async-job-executor-thread-%d", ((BasicThreadFactory) threadFactory).getNamingPattern());
    assertNull(((BasicThreadFactory) threadFactory).getDaemonFlag());
    assertNull(((BasicThreadFactory) threadFactory).getPriority());
    assertNull(((BasicThreadFactory) threadFactory).getUncaughtExceptionHandler());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertEquals(1, getResult.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, getResult.getMaxTimerJobsPerAcquisition());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(10, ((DefaultAsyncJobExecutor) getResult).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) getResult).getQueueSize());
    assertEquals(10000, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
    assertEquals(2, ((DefaultAsyncJobExecutor) getResult).getCorePoolSize());
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
    assertEquals(300000, getResult.getAsyncJobLockTimeInMillis());
    assertEquals(300000, getResult.getTimerLockTimeInMillis());
    assertEquals(500, getResult.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) getResult).getKeepAliveTime());
    assertEquals(60000, getResult.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) getResult).getSecondsToWaitOnShutdown());
    assertFalse(getResult.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) getResult).isMessageQueueMode());
    BlockingQueue<Runnable> threadPoolQueue = ((DefaultAsyncJobExecutor) getResult).getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(getResult.isActive());
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    ExecutorService executorService = ((DefaultAsyncJobExecutor) getResult).getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    ThreadFactory threadFactory = ((ThreadPoolExecutor) executorService).getThreadFactory();
    assertTrue(threadFactory instanceof BasicThreadFactory);
    assertEquals("42", ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantId);
    assertEquals("activiti-async-job-executor-thread-%d", ((BasicThreadFactory) threadFactory).getNamingPattern());
    assertNull(((BasicThreadFactory) threadFactory).getDaemonFlag());
    assertNull(((BasicThreadFactory) threadFactory).getPriority());
    assertNull(((BasicThreadFactory) threadFactory).getUncaughtExceptionHandler());
    assertNull(((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertEquals(1, getResult.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, getResult.getMaxTimerJobsPerAcquisition());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(10, ((DefaultAsyncJobExecutor) getResult).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) getResult).getQueueSize());
    assertEquals(10000, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
    assertEquals(2, ((DefaultAsyncJobExecutor) getResult).getCorePoolSize());
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
    assertEquals(300000, getResult.getAsyncJobLockTimeInMillis());
    assertEquals(300000, getResult.getTimerLockTimeInMillis());
    assertEquals(500, getResult.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) getResult).getKeepAliveTime());
    assertEquals(60000, getResult.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) getResult).getSecondsToWaitOnShutdown());
    assertFalse(getResult.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) getResult).isMessageQueueMode());
    BlockingQueue<Runnable> threadPoolQueue = ((DefaultAsyncJobExecutor) getResult).getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(getResult.isActive());
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor5() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Starting up the default async job executor [{}].");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    ExecutorService executorService = ((DefaultAsyncJobExecutor) getResult).getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    ThreadFactory threadFactory = ((ThreadPoolExecutor) executorService).getThreadFactory();
    assertTrue(threadFactory instanceof BasicThreadFactory);
    assertEquals("42", ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantId);
    assertEquals("activiti-async-job-executor-thread-%d", ((BasicThreadFactory) threadFactory).getNamingPattern());
    assertNull(((BasicThreadFactory) threadFactory).getDaemonFlag());
    assertNull(((BasicThreadFactory) threadFactory).getPriority());
    assertNull(((BasicThreadFactory) threadFactory).getUncaughtExceptionHandler());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertEquals(1, getResult.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, getResult.getMaxTimerJobsPerAcquisition());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(10, ((DefaultAsyncJobExecutor) getResult).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) getResult).getQueueSize());
    assertEquals(10000, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
    assertEquals(2, ((DefaultAsyncJobExecutor) getResult).getCorePoolSize());
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
    assertEquals(300000, getResult.getAsyncJobLockTimeInMillis());
    assertEquals(300000, getResult.getTimerLockTimeInMillis());
    assertEquals(500, getResult.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) getResult).getKeepAliveTime());
    assertEquals(60000, getResult.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) getResult).getSecondsToWaitOnShutdown());
    assertFalse(getResult.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) getResult).isMessageQueueMode());
    BlockingQueue<Runnable> threadPoolQueue = ((DefaultAsyncJobExecutor) getResult).getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(getResult.isActive());
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertSame(defaultAsyncJobExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(executorPerTenantAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor2 = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor2.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor2.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor2.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor2.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    Set<String> tenantIds = executorPerTenantAsyncExecutor2.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertSame(executorPerTenantAsyncExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertSame(sharedExecutorServiceAsyncExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(stringAsyncExecutorMap.containsKey("42"));
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(stringAsyncExecutorMap.containsKey("42"));
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor3() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    TenantInfoHolder tenantInfoHolder2 = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder2.getAllTenants();
    assertEquals(1, allTenants.size());
    assertTrue(allTenants instanceof Set);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(tenantInfoHolder2 instanceof DummyTenantInfoHolder);
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor5() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor6() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor7() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor8() {
    // Arrange
    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    doNothing().when(asyncExecutor).setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).shutdown();
    doNothing().when(asyncExecutor).start();
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(asyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).shutdown();
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor9() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor10() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor11() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof StandaloneInMemProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(processEngineConfigurationImpl.getObjectMapper().getRegisteredModuleIds().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor12() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor13() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor14() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(null);
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor15() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(mock(JobManager.class));
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor() {
    // Arrange, Act and Assert
    assertNull((new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())).determineAsyncExecutor());
    assertNull(
        (new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder(), mock(TenantAwareAsyncExecutorFactory.class)))
            .determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor3() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor4() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor6() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration = new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor7() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor8() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomDefaultBpmnParseHandlers(new ArrayList<>());
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor9() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomEventHandlers(new ArrayList<>());
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor10() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setEventLogEntryDataManager(new MybatisEventLogEntryDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor11() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricTaskInstanceDataManager(
        new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor12() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricActivityInstanceEntityManager(
        new HistoricActivityInstanceEntityManagerImpl(processEngineConfiguration2,
            new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor13() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionEntityManager(new ProcessDefinitionEntityManagerImpl(
        processEngineConfiguration2, new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor14() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcUsername("janedoe");
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor15() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setVariableTypes(new DefaultVariableTypes());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor16() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricProcessInstanceDataManager(
        new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor17() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDbHistoryUsed(true);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor18() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcPingEnabled(true);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor19() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTablePrefixIsSchema(true);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor20() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventListeners(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor21() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcMaxIdleConnections(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  public void testExecuteAsyncJob() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(true);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    boolean actualExecuteAsyncJobResult = executorPerTenantAsyncExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).executeAsyncJob(isA(Job.class));
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertTrue(stringAsyncExecutorMap.containsKey("42"));
    assertTrue(actualExecuteAsyncJobResult);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  public void testExecuteAsyncJob2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(false);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    boolean actualExecuteAsyncJobResult = executorPerTenantAsyncExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).executeAsyncJob(isA(Job.class));
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertFalse(actualExecuteAsyncJobResult);
    assertTrue(stringAsyncExecutorMap.containsKey("42"));
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  public void testGetJobManager() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> (new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())).getJobManager());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testSetProcessEngineConfiguration() throws IOException {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    Collection<? extends Deployer> defaultDeployers = processEngineConfigurationImpl.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    List<BpmnParseHandler> defaultBpmnParseHandlers = processEngineConfigurationImpl.getDefaultBpmnParseHandlers();
    assertEquals(30, defaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = defaultBpmnParseHandlers.get(0);
    assertTrue(getResult instanceof BoundaryEventParseHandler);
    BpmnParseHandler getResult2 = defaultBpmnParseHandlers.get(1);
    assertTrue(getResult2 instanceof BusinessRuleParseHandler);
    BpmnParseHandler getResult3 = defaultBpmnParseHandlers.get(28);
    assertTrue(getResult3 instanceof TransactionParseHandler);
    BpmnParseHandler getResult4 = defaultBpmnParseHandlers.get(29);
    assertTrue(getResult4 instanceof UserTaskParseHandler);
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertEquals("", processEngineConfigurationImpl.getDatabaseCatalog());
    assertEquals("", processEngineConfigurationImpl.getDatabaseTablePrefix());
    assertEquals("", processEngineConfigurationImpl.getJdbcPassword());
    assertEquals("@class", processEngineConfigurationImpl.getJavaClassFieldForJackson());
    assertEquals("UTF-8", processEngineConfigurationImpl.getXmlEncoding());
    assertEquals("activiti@localhost", processEngineConfigurationImpl.getMailServerDefaultFrom());
    assertEquals("audit", processEngineConfigurationImpl.getHistory());
    assertEquals("camelContext", processEngineConfigurationImpl.getDefaultCamelContext());
    assertEquals("default", processEngineConfigurationImpl.getProcessEngineName());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", processEngineConfigurationImpl.getJdbcUrl());
    assertEquals("localhost", processEngineConfigurationImpl.getMailServerHost());
    assertEquals("org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        processEngineConfigurationImpl.getWsSyncFactoryClassName());
    assertEquals("org.h2.Driver", processEngineConfigurationImpl.getJdbcDriver());
    assertEquals("sa", processEngineConfigurationImpl.getJdbcUsername());
    assertNull(((JtaProcessEngineConfiguration) processEngineConfigurationImpl).getTransactionManager());
    assertNull(processEngineConfigurationImpl.getClassLoader());
    assertNull(processEngineConfigurationImpl.getJpaEntityManagerFactory());
    assertNull(processEngineConfigurationImpl.getDataSourceJndiName());
    assertNull(processEngineConfigurationImpl.getDatabaseSchema());
    assertNull(processEngineConfigurationImpl.getDatabaseType());
    assertNull(processEngineConfigurationImpl.getDatabaseWildcardEscapeCharacter());
    assertNull(processEngineConfigurationImpl.getJdbcPingQuery());
    assertNull(processEngineConfigurationImpl.getJpaPersistenceUnitName());
    assertNull(processEngineConfigurationImpl.getMailServerPassword());
    assertNull(processEngineConfigurationImpl.getMailServerUsername());
    assertNull(processEngineConfigurationImpl.getMailSessionJndi());
    assertNull(processEngineConfigurationImpl.getAsyncExecutorLockOwner());
    assertNull(processEngineConfigurationImpl.getIdGeneratorDataSourceJndiName());
    assertNull(processEngineConfigurationImpl.getCustomScriptingEngineClasses());
    assertNull(processEngineConfigurationImpl.getCustomFunctionProviders());
    assertNull(processEngineConfigurationImpl.getAllConfigurators());
    assertNull(processEngineConfigurationImpl.getConfigurators());
    assertNull(processEngineConfigurationImpl.getEventListeners());
    assertNull(processEngineConfigurationImpl.getCustomEventHandlers());
    assertNull(processEngineConfigurationImpl.getCommandInterceptors());
    assertNull(processEngineConfigurationImpl.getCustomPostCommandInterceptors());
    assertNull(processEngineConfigurationImpl.getCustomPreCommandInterceptors());
    assertNull(processEngineConfigurationImpl.getCustomSessionFactories());
    assertNull(processEngineConfigurationImpl.getCustomJobHandlers());
    assertNull(processEngineConfigurationImpl.getCustomPostDeployers());
    assertNull(processEngineConfigurationImpl.getCustomPreDeployers());
    assertNull(processEngineConfigurationImpl.getDeployers());
    assertNull(processEngineConfigurationImpl.getResolverFactories());
    assertNull(processEngineConfigurationImpl.getCustomPostVariableTypes());
    assertNull(processEngineConfigurationImpl.getCustomPreVariableTypes());
    assertNull(processEngineConfigurationImpl.getCustomDefaultBpmnParseHandlers());
    assertNull(processEngineConfigurationImpl.getPostBpmnParseHandlers());
    assertNull(processEngineConfigurationImpl.getPreBpmnParseHandlers());
    assertNull(processEngineConfigurationImpl.getSessionFactories());
    assertNull(processEngineConfigurationImpl.getBeans());
    assertNull(processEngineConfigurationImpl.getTypedEventListeners());
    assertNull(processEngineConfigurationImpl.getEventHandlers());
    assertNull(processEngineConfigurationImpl.getJobHandlers());
    assertNull(processEngineConfigurationImpl.getCustomMybatisMappers());
    assertNull(processEngineConfigurationImpl.getCustomMybatisXMLMappers());
    assertNull(processEngineConfigurationImpl.getAsyncExecutorThreadPoolQueue());
    assertNull(processEngineConfigurationImpl.getDataSource());
    assertNull(processEngineConfigurationImpl.getIdGeneratorDataSource());
    assertNull(processEngineConfigurationImpl.getUserGroupManager());
    assertNull(processEngineConfigurationImpl.getEngineAgendaFactory());
    assertNull(processEngineConfigurationImpl.getProcessEngineLifecycleListener());
    assertNull(processEngineConfigurationImpl.getEventDispatcher());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionHelper());
    assertNull(processEngineConfigurationImpl.getAsyncExecutor());
    assertNull(processEngineConfigurationImpl.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(processEngineConfigurationImpl.getJobManager());
    assertNull(processEngineConfigurationImpl.getListenerNotificationHelper());
    assertNull(processEngineConfigurationImpl.getBpmnParser());
    assertNull(processEngineConfigurationImpl.getActivityBehaviorFactory());
    assertNull(processEngineConfigurationImpl.getListenerFactory());
    assertNull(processEngineConfigurationImpl.getBusinessCalendarManager());
    assertNull(processEngineConfigurationImpl.getBpmnParseFactory());
    assertNull(processEngineConfigurationImpl.getIdGenerator());
    assertNull(processEngineConfigurationImpl.getTransactionContextFactory());
    assertNull(processEngineConfigurationImpl.getDbSqlSessionFactory());
    assertNull(processEngineConfigurationImpl.getExpressionManager());
    assertNull(processEngineConfigurationImpl.getHistoryLevel());
    assertNull(processEngineConfigurationImpl.getHistoryManager());
    assertNull(processEngineConfigurationImpl.getDefaultCommandConfig());
    assertNull(processEngineConfigurationImpl.getSchemaCommandConfig());
    assertNull(processEngineConfigurationImpl.getCommandContextFactory());
    assertNull(processEngineConfigurationImpl.getCommandExecutor());
    assertNull(processEngineConfigurationImpl.getCommandInvoker());
    assertNull(processEngineConfigurationImpl.getDelegateInterceptor());
    assertNull(processEngineConfigurationImpl.getFailedJobCommandFactory());
    assertNull(processEngineConfigurationImpl.getKnowledgeBaseCache());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionCache());
    assertNull(processEngineConfigurationImpl.getDeploymentManager());
    assertNull(processEngineConfigurationImpl.getAttachmentEntityManager());
    assertNull(processEngineConfigurationImpl.getByteArrayEntityManager());
    assertNull(processEngineConfigurationImpl.getCommentEntityManager());
    assertNull(processEngineConfigurationImpl.getDeadLetterJobEntityManager());
    assertNull(processEngineConfigurationImpl.getDeploymentEntityManager());
    assertNull(processEngineConfigurationImpl.getEventLogEntryEntityManager());
    assertNull(processEngineConfigurationImpl.getEventSubscriptionEntityManager());
    assertNull(processEngineConfigurationImpl.getExecutionEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricActivityInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricDetailEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getIdentityLinkEntityManager());
    assertNull(processEngineConfigurationImpl.getJobEntityManager());
    assertNull(processEngineConfigurationImpl.getModelEntityManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionEntityManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionInfoEntityManager());
    assertNull(processEngineConfigurationImpl.getPropertyEntityManager());
    assertNull(processEngineConfigurationImpl.getResourceEntityManager());
    assertNull(processEngineConfigurationImpl.getSuspendedJobEntityManager());
    assertNull(processEngineConfigurationImpl.getTableDataManager());
    assertNull(processEngineConfigurationImpl.getTaskEntityManager());
    assertNull(processEngineConfigurationImpl.getTimerJobEntityManager());
    assertNull(processEngineConfigurationImpl.getVariableInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getAttachmentDataManager());
    assertNull(processEngineConfigurationImpl.getByteArrayDataManager());
    assertNull(processEngineConfigurationImpl.getCommentDataManager());
    assertNull(processEngineConfigurationImpl.getDeadLetterJobDataManager());
    assertNull(processEngineConfigurationImpl.getDeploymentDataManager());
    assertNull(processEngineConfigurationImpl.getEventLogEntryDataManager());
    assertNull(processEngineConfigurationImpl.getEventSubscriptionDataManager());
    assertNull(processEngineConfigurationImpl.getExecutionDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricActivityInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricDetailDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricIdentityLinkDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricProcessInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricTaskInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricVariableInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getIdentityLinkDataManager());
    assertNull(processEngineConfigurationImpl.getJobDataManager());
    assertNull(processEngineConfigurationImpl.getModelDataManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionDataManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionInfoDataManager());
    assertNull(processEngineConfigurationImpl.getPropertyDataManager());
    assertNull(processEngineConfigurationImpl.getResourceDataManager());
    assertNull(processEngineConfigurationImpl.getSuspendedJobDataManager());
    assertNull(processEngineConfigurationImpl.getTaskDataManager());
    assertNull(processEngineConfigurationImpl.getTimerJobDataManager());
    assertNull(processEngineConfigurationImpl.getVariableInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getScriptingEngines());
    assertNull(processEngineConfigurationImpl.getProcessInstanceHelper());
    assertNull(processEngineConfigurationImpl.getVariableTypes());
    assertNull(processEngineConfigurationImpl.getClock());
    assertNull(processEngineConfigurationImpl.getProcessValidator());
    assertNull(processEngineConfigurationImpl.getSqlSessionFactory());
    assertNull(processEngineConfigurationImpl.getTransactionFactory());
    assertEquals(-1, processEngineConfigurationImpl.getKnowledgeBaseCacheLimit());
    assertEquals(-1, processEngineConfigurationImpl.getMaxLengthStringVariableType());
    assertEquals(-1, processEngineConfigurationImpl.getProcessDefinitionCacheLimit());
    assertEquals(0, processEngineConfigurationImpl.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxActiveConnections());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxCheckoutTime());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxIdleConnections());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxWaitTime());
    assertEquals(0, processEngineConfigurationImpl.getJdbcPingConnectionNotUsedFor());
    assertEquals(0, processEngineConfigurationImpl.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, processEngineConfigurationImpl.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, processEngineConfigurationImpl.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, processEngineConfigurationImpl.getAsyncFailedJobWaitTime());
    assertEquals(10, processEngineConfigurationImpl.getDefaultFailedJobWaitTime());
    assertEquals(10, processEngineConfigurationImpl.getAsyncExecutorMaxPoolSize());
    assertEquals(100, processEngineConfigurationImpl.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, processEngineConfigurationImpl.getMaxNrOfStatementsInBulkInsert());
    assertEquals(10000, processEngineConfigurationImpl.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(10000, processEngineConfigurationImpl.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(2, processEngineConfigurationImpl.getAsyncExecutorCorePoolSize());
    assertEquals(20000, processEngineConfigurationImpl.getExecutionQueryLimit());
    assertEquals(20000, processEngineConfigurationImpl.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, processEngineConfigurationImpl.getHistoricTaskQueryLimit());
    assertEquals(20000, processEngineConfigurationImpl.getTaskQueryLimit());
    assertEquals(25, processEngineConfigurationImpl.getMailServerPort());
    assertEquals(25, processEngineConfigurationImpl.getBatchSizeProcessInstances());
    assertEquals(25, processEngineConfigurationImpl.getBatchSizeTasks());
    assertEquals(2500, processEngineConfigurationImpl.getIdBlockSize());
    assertEquals(3, processEngineConfigurationImpl.getAsyncExecutorNumberOfRetries());
    assertEquals(3, processEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(300000, processEngineConfigurationImpl.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(300000, processEngineConfigurationImpl.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(4000, processEngineConfigurationImpl.getMaxLengthString());
    assertEquals(5000L, processEngineConfigurationImpl.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(51, processEngineConfigurationImpl.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, processEngineConfigurationImpl.getLockTimeAsyncJobWaitTime());
    assertEquals(60000, processEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(60L, processEngineConfigurationImpl.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(70,
        ((JtaProcessEngineConfiguration) processEngineConfigurationImpl).DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(DelegateExpressionFieldInjectionMode.MIXED,
        processEngineConfigurationImpl.getDelegateExpressionFieldInjectionMode());
    assertFalse(processEngineConfigurationImpl.getMailServerUseSSL());
    assertFalse(processEngineConfigurationImpl.getMailServerUseTLS());
    assertFalse(processEngineConfigurationImpl.isAsyncExecutorActivate());
    assertFalse(processEngineConfigurationImpl.isCopyVariablesToLocalForTasks());
    assertFalse(processEngineConfigurationImpl.isEnableProcessDefinitionInfoCache());
    assertFalse(processEngineConfigurationImpl.isJdbcPingEnabled());
    assertFalse(processEngineConfigurationImpl.isJpaCloseEntityManager());
    assertFalse(processEngineConfigurationImpl.isJpaHandleTransaction());
    assertFalse(processEngineConfigurationImpl.isTablePrefixIsSchema());
    assertFalse(processEngineConfigurationImpl.isAsyncExecutorIsMessageQueueMode());
    assertFalse(processEngineConfigurationImpl.isEnableDatabaseEventLogging());
    assertFalse(processEngineConfigurationImpl.isEnableSafeBpmnXml());
    assertFalse(processEngineConfigurationImpl.isEnableVerboseExecutionTreeLogging());
    assertFalse(processEngineConfigurationImpl.isRollbackDeployment());
    assertFalse(processEngineConfigurationImpl.isSerializePOJOsInVariablesToJson());
    assertTrue(processEngineConfigurationImpl.isDbHistoryUsed());
    assertTrue(processEngineConfigurationImpl.isTransactionsExternallyManaged());
    assertTrue(processEngineConfigurationImpl.isUseClassForNameClassLoading());
    assertTrue(processEngineConfigurationImpl.isBulkInsertEnabled());
    assertTrue(processEngineConfigurationImpl.isEnableConfiguratorServiceLoader());
    assertTrue(processEngineConfigurationImpl.isEnableEventDispatcher());
    assertTrue(processEngineConfigurationImpl.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(processEngineConfigurationImpl.isUsingRelationalDatabase());
    String expectedDatabaseSchemaUpdate = Boolean.FALSE.toString();
    assertEquals(expectedDatabaseSchemaUpdate, processEngineConfigurationImpl.getDatabaseSchemaUpdate());
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testSetProcessEngineConfiguration2() throws IOException {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), mock(TenantAwareAsyncExecutorFactory.class));

    // Act
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    Collection<? extends Deployer> defaultDeployers = processEngineConfigurationImpl.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    List<BpmnParseHandler> defaultBpmnParseHandlers = processEngineConfigurationImpl.getDefaultBpmnParseHandlers();
    assertEquals(30, defaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = defaultBpmnParseHandlers.get(0);
    assertTrue(getResult instanceof BoundaryEventParseHandler);
    BpmnParseHandler getResult2 = defaultBpmnParseHandlers.get(1);
    assertTrue(getResult2 instanceof BusinessRuleParseHandler);
    BpmnParseHandler getResult3 = defaultBpmnParseHandlers.get(28);
    assertTrue(getResult3 instanceof TransactionParseHandler);
    BpmnParseHandler getResult4 = defaultBpmnParseHandlers.get(29);
    assertTrue(getResult4 instanceof UserTaskParseHandler);
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertEquals("", processEngineConfigurationImpl.getDatabaseCatalog());
    assertEquals("", processEngineConfigurationImpl.getDatabaseTablePrefix());
    assertEquals("", processEngineConfigurationImpl.getJdbcPassword());
    assertEquals("@class", processEngineConfigurationImpl.getJavaClassFieldForJackson());
    assertEquals("UTF-8", processEngineConfigurationImpl.getXmlEncoding());
    assertEquals("activiti@localhost", processEngineConfigurationImpl.getMailServerDefaultFrom());
    assertEquals("audit", processEngineConfigurationImpl.getHistory());
    assertEquals("camelContext", processEngineConfigurationImpl.getDefaultCamelContext());
    assertEquals("default", processEngineConfigurationImpl.getProcessEngineName());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", processEngineConfigurationImpl.getJdbcUrl());
    assertEquals("localhost", processEngineConfigurationImpl.getMailServerHost());
    assertEquals("org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        processEngineConfigurationImpl.getWsSyncFactoryClassName());
    assertEquals("org.h2.Driver", processEngineConfigurationImpl.getJdbcDriver());
    assertEquals("sa", processEngineConfigurationImpl.getJdbcUsername());
    assertNull(((JtaProcessEngineConfiguration) processEngineConfigurationImpl).getTransactionManager());
    assertNull(processEngineConfigurationImpl.getClassLoader());
    assertNull(processEngineConfigurationImpl.getJpaEntityManagerFactory());
    assertNull(processEngineConfigurationImpl.getDataSourceJndiName());
    assertNull(processEngineConfigurationImpl.getDatabaseSchema());
    assertNull(processEngineConfigurationImpl.getDatabaseType());
    assertNull(processEngineConfigurationImpl.getDatabaseWildcardEscapeCharacter());
    assertNull(processEngineConfigurationImpl.getJdbcPingQuery());
    assertNull(processEngineConfigurationImpl.getJpaPersistenceUnitName());
    assertNull(processEngineConfigurationImpl.getMailServerPassword());
    assertNull(processEngineConfigurationImpl.getMailServerUsername());
    assertNull(processEngineConfigurationImpl.getMailSessionJndi());
    assertNull(processEngineConfigurationImpl.getAsyncExecutorLockOwner());
    assertNull(processEngineConfigurationImpl.getIdGeneratorDataSourceJndiName());
    assertNull(processEngineConfigurationImpl.getCustomScriptingEngineClasses());
    assertNull(processEngineConfigurationImpl.getCustomFunctionProviders());
    assertNull(processEngineConfigurationImpl.getAllConfigurators());
    assertNull(processEngineConfigurationImpl.getConfigurators());
    assertNull(processEngineConfigurationImpl.getEventListeners());
    assertNull(processEngineConfigurationImpl.getCustomEventHandlers());
    assertNull(processEngineConfigurationImpl.getCommandInterceptors());
    assertNull(processEngineConfigurationImpl.getCustomPostCommandInterceptors());
    assertNull(processEngineConfigurationImpl.getCustomPreCommandInterceptors());
    assertNull(processEngineConfigurationImpl.getCustomSessionFactories());
    assertNull(processEngineConfigurationImpl.getCustomJobHandlers());
    assertNull(processEngineConfigurationImpl.getCustomPostDeployers());
    assertNull(processEngineConfigurationImpl.getCustomPreDeployers());
    assertNull(processEngineConfigurationImpl.getDeployers());
    assertNull(processEngineConfigurationImpl.getResolverFactories());
    assertNull(processEngineConfigurationImpl.getCustomPostVariableTypes());
    assertNull(processEngineConfigurationImpl.getCustomPreVariableTypes());
    assertNull(processEngineConfigurationImpl.getCustomDefaultBpmnParseHandlers());
    assertNull(processEngineConfigurationImpl.getPostBpmnParseHandlers());
    assertNull(processEngineConfigurationImpl.getPreBpmnParseHandlers());
    assertNull(processEngineConfigurationImpl.getSessionFactories());
    assertNull(processEngineConfigurationImpl.getBeans());
    assertNull(processEngineConfigurationImpl.getTypedEventListeners());
    assertNull(processEngineConfigurationImpl.getEventHandlers());
    assertNull(processEngineConfigurationImpl.getJobHandlers());
    assertNull(processEngineConfigurationImpl.getCustomMybatisMappers());
    assertNull(processEngineConfigurationImpl.getCustomMybatisXMLMappers());
    assertNull(processEngineConfigurationImpl.getAsyncExecutorThreadPoolQueue());
    assertNull(processEngineConfigurationImpl.getDataSource());
    assertNull(processEngineConfigurationImpl.getIdGeneratorDataSource());
    assertNull(processEngineConfigurationImpl.getUserGroupManager());
    assertNull(processEngineConfigurationImpl.getEngineAgendaFactory());
    assertNull(processEngineConfigurationImpl.getProcessEngineLifecycleListener());
    assertNull(processEngineConfigurationImpl.getEventDispatcher());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionHelper());
    assertNull(processEngineConfigurationImpl.getAsyncExecutor());
    assertNull(processEngineConfigurationImpl.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(processEngineConfigurationImpl.getJobManager());
    assertNull(processEngineConfigurationImpl.getListenerNotificationHelper());
    assertNull(processEngineConfigurationImpl.getBpmnParser());
    assertNull(processEngineConfigurationImpl.getActivityBehaviorFactory());
    assertNull(processEngineConfigurationImpl.getListenerFactory());
    assertNull(processEngineConfigurationImpl.getBusinessCalendarManager());
    assertNull(processEngineConfigurationImpl.getBpmnParseFactory());
    assertNull(processEngineConfigurationImpl.getIdGenerator());
    assertNull(processEngineConfigurationImpl.getTransactionContextFactory());
    assertNull(processEngineConfigurationImpl.getDbSqlSessionFactory());
    assertNull(processEngineConfigurationImpl.getExpressionManager());
    assertNull(processEngineConfigurationImpl.getHistoryLevel());
    assertNull(processEngineConfigurationImpl.getHistoryManager());
    assertNull(processEngineConfigurationImpl.getDefaultCommandConfig());
    assertNull(processEngineConfigurationImpl.getSchemaCommandConfig());
    assertNull(processEngineConfigurationImpl.getCommandContextFactory());
    assertNull(processEngineConfigurationImpl.getCommandExecutor());
    assertNull(processEngineConfigurationImpl.getCommandInvoker());
    assertNull(processEngineConfigurationImpl.getDelegateInterceptor());
    assertNull(processEngineConfigurationImpl.getFailedJobCommandFactory());
    assertNull(processEngineConfigurationImpl.getKnowledgeBaseCache());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionCache());
    assertNull(processEngineConfigurationImpl.getDeploymentManager());
    assertNull(processEngineConfigurationImpl.getAttachmentEntityManager());
    assertNull(processEngineConfigurationImpl.getByteArrayEntityManager());
    assertNull(processEngineConfigurationImpl.getCommentEntityManager());
    assertNull(processEngineConfigurationImpl.getDeadLetterJobEntityManager());
    assertNull(processEngineConfigurationImpl.getDeploymentEntityManager());
    assertNull(processEngineConfigurationImpl.getEventLogEntryEntityManager());
    assertNull(processEngineConfigurationImpl.getEventSubscriptionEntityManager());
    assertNull(processEngineConfigurationImpl.getExecutionEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricActivityInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricDetailEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getIdentityLinkEntityManager());
    assertNull(processEngineConfigurationImpl.getJobEntityManager());
    assertNull(processEngineConfigurationImpl.getModelEntityManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionEntityManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionInfoEntityManager());
    assertNull(processEngineConfigurationImpl.getPropertyEntityManager());
    assertNull(processEngineConfigurationImpl.getResourceEntityManager());
    assertNull(processEngineConfigurationImpl.getSuspendedJobEntityManager());
    assertNull(processEngineConfigurationImpl.getTableDataManager());
    assertNull(processEngineConfigurationImpl.getTaskEntityManager());
    assertNull(processEngineConfigurationImpl.getTimerJobEntityManager());
    assertNull(processEngineConfigurationImpl.getVariableInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getAttachmentDataManager());
    assertNull(processEngineConfigurationImpl.getByteArrayDataManager());
    assertNull(processEngineConfigurationImpl.getCommentDataManager());
    assertNull(processEngineConfigurationImpl.getDeadLetterJobDataManager());
    assertNull(processEngineConfigurationImpl.getDeploymentDataManager());
    assertNull(processEngineConfigurationImpl.getEventLogEntryDataManager());
    assertNull(processEngineConfigurationImpl.getEventSubscriptionDataManager());
    assertNull(processEngineConfigurationImpl.getExecutionDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricActivityInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricDetailDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricIdentityLinkDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricProcessInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricTaskInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricVariableInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getIdentityLinkDataManager());
    assertNull(processEngineConfigurationImpl.getJobDataManager());
    assertNull(processEngineConfigurationImpl.getModelDataManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionDataManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionInfoDataManager());
    assertNull(processEngineConfigurationImpl.getPropertyDataManager());
    assertNull(processEngineConfigurationImpl.getResourceDataManager());
    assertNull(processEngineConfigurationImpl.getSuspendedJobDataManager());
    assertNull(processEngineConfigurationImpl.getTaskDataManager());
    assertNull(processEngineConfigurationImpl.getTimerJobDataManager());
    assertNull(processEngineConfigurationImpl.getVariableInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getScriptingEngines());
    assertNull(processEngineConfigurationImpl.getProcessInstanceHelper());
    assertNull(processEngineConfigurationImpl.getVariableTypes());
    assertNull(processEngineConfigurationImpl.getClock());
    assertNull(processEngineConfigurationImpl.getProcessValidator());
    assertNull(processEngineConfigurationImpl.getSqlSessionFactory());
    assertNull(processEngineConfigurationImpl.getTransactionFactory());
    assertEquals(-1, processEngineConfigurationImpl.getKnowledgeBaseCacheLimit());
    assertEquals(-1, processEngineConfigurationImpl.getMaxLengthStringVariableType());
    assertEquals(-1, processEngineConfigurationImpl.getProcessDefinitionCacheLimit());
    assertEquals(0, processEngineConfigurationImpl.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxActiveConnections());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxCheckoutTime());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxIdleConnections());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxWaitTime());
    assertEquals(0, processEngineConfigurationImpl.getJdbcPingConnectionNotUsedFor());
    assertEquals(0, processEngineConfigurationImpl.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, processEngineConfigurationImpl.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, processEngineConfigurationImpl.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, processEngineConfigurationImpl.getAsyncFailedJobWaitTime());
    assertEquals(10, processEngineConfigurationImpl.getDefaultFailedJobWaitTime());
    assertEquals(10, processEngineConfigurationImpl.getAsyncExecutorMaxPoolSize());
    assertEquals(100, processEngineConfigurationImpl.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, processEngineConfigurationImpl.getMaxNrOfStatementsInBulkInsert());
    assertEquals(10000, processEngineConfigurationImpl.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(10000, processEngineConfigurationImpl.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(2, processEngineConfigurationImpl.getAsyncExecutorCorePoolSize());
    assertEquals(20000, processEngineConfigurationImpl.getExecutionQueryLimit());
    assertEquals(20000, processEngineConfigurationImpl.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, processEngineConfigurationImpl.getHistoricTaskQueryLimit());
    assertEquals(20000, processEngineConfigurationImpl.getTaskQueryLimit());
    assertEquals(25, processEngineConfigurationImpl.getMailServerPort());
    assertEquals(25, processEngineConfigurationImpl.getBatchSizeProcessInstances());
    assertEquals(25, processEngineConfigurationImpl.getBatchSizeTasks());
    assertEquals(2500, processEngineConfigurationImpl.getIdBlockSize());
    assertEquals(3, processEngineConfigurationImpl.getAsyncExecutorNumberOfRetries());
    assertEquals(3, processEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(300000, processEngineConfigurationImpl.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(300000, processEngineConfigurationImpl.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(4000, processEngineConfigurationImpl.getMaxLengthString());
    assertEquals(5000L, processEngineConfigurationImpl.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(51, processEngineConfigurationImpl.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, processEngineConfigurationImpl.getLockTimeAsyncJobWaitTime());
    assertEquals(60000, processEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(60L, processEngineConfigurationImpl.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(70,
        ((JtaProcessEngineConfiguration) processEngineConfigurationImpl).DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(DelegateExpressionFieldInjectionMode.MIXED,
        processEngineConfigurationImpl.getDelegateExpressionFieldInjectionMode());
    assertFalse(processEngineConfigurationImpl.getMailServerUseSSL());
    assertFalse(processEngineConfigurationImpl.getMailServerUseTLS());
    assertFalse(processEngineConfigurationImpl.isAsyncExecutorActivate());
    assertFalse(processEngineConfigurationImpl.isCopyVariablesToLocalForTasks());
    assertFalse(processEngineConfigurationImpl.isEnableProcessDefinitionInfoCache());
    assertFalse(processEngineConfigurationImpl.isJdbcPingEnabled());
    assertFalse(processEngineConfigurationImpl.isJpaCloseEntityManager());
    assertFalse(processEngineConfigurationImpl.isJpaHandleTransaction());
    assertFalse(processEngineConfigurationImpl.isTablePrefixIsSchema());
    assertFalse(processEngineConfigurationImpl.isAsyncExecutorIsMessageQueueMode());
    assertFalse(processEngineConfigurationImpl.isEnableDatabaseEventLogging());
    assertFalse(processEngineConfigurationImpl.isEnableSafeBpmnXml());
    assertFalse(processEngineConfigurationImpl.isEnableVerboseExecutionTreeLogging());
    assertFalse(processEngineConfigurationImpl.isRollbackDeployment());
    assertFalse(processEngineConfigurationImpl.isSerializePOJOsInVariablesToJson());
    assertTrue(processEngineConfigurationImpl.isDbHistoryUsed());
    assertTrue(processEngineConfigurationImpl.isTransactionsExternallyManaged());
    assertTrue(processEngineConfigurationImpl.isUseClassForNameClassLoading());
    assertTrue(processEngineConfigurationImpl.isBulkInsertEnabled());
    assertTrue(processEngineConfigurationImpl.isEnableConfiguratorServiceLoader());
    assertTrue(processEngineConfigurationImpl.isEnableEventDispatcher());
    assertTrue(processEngineConfigurationImpl.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(processEngineConfigurationImpl.isUsingRelationalDatabase());
    String expectedDatabaseSchemaUpdate = Boolean.FALSE.toString();
    assertEquals(expectedDatabaseSchemaUpdate, processEngineConfigurationImpl.getDatabaseSchemaUpdate());
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getProcessEngineConfiguration()}
   */
  @Test
  public void testGetProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> (new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())).getProcessEngineConfiguration());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), mock(TenantAwareAsyncExecutorFactory.class));

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart3() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart4() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart5() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setRuntimeService(new RuntimeServiceImpl());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricProcessInstancesQueryLimit(10000);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPostBpmnParseHandlers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionCache(new DefaultDeploymentCache<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setKnowledgeBaseCache(new DefaultDeploymentCache<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricProcessInstanceDataManager(
        new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart13() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart14() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcPingEnabled(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart15() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseCatalog("exception during resetting expired jobs");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart16() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCopyVariablesToLocalForTasks(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  public void testStart17() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(mock(ActivitiEngineAgendaFactory.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown3() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown4() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown5() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown7() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration = new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBpmnDeploymentHelper(new BpmnDeploymentHelper());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setVariableTypes(new DefaultVariableTypes());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSerializableVariableTypeTrackDeserializedObjects(true);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setListenerNotificationHelper(new ListenerNotificationHelper());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown13() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomSessionFactories(new ArrayList<>());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown14() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBatchSizeProcessInstances(3);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown15() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentEntityManager(new DeploymentEntityManagerImpl(processEngineConfiguration2,
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown16() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventSubscriptionEntityManager(new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration2, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown17() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTimerJobEntityManager(new TimerJobEntityManagerImpl(processEngineConfiguration2,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown18() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPropertyEntityManager(new PropertyEntityManagerImpl(processEngineConfiguration2,
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown19() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTableDataManager(new TableDataManagerImpl(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown20() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(10000);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown21() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown22() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setVariableTypes(new DefaultVariableTypes());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown23() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown24() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessEngineName("Shutting down the default async job executor [{}].");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown25() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseSchemaUpdate("2020-03-01");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  public void testShutdown26() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setListenerNotificationHelper(new ListenerNotificationHelper());
    processEngineConfiguration.addConfigurator(ActivitiProfiler.getInstance());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor3() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor6() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor7() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(defaultAsyncJobExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor8() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(defaultAsyncJobExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor9() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(executorPerTenantAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor2 = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor2.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor2.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor2.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor2.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ExecutorPerTenantAsyncExecutor);
    assertFalse(getResult.isActive());
    assertSame(executorPerTenantAsyncExecutor, getResult);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor10() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(sharedExecutorServiceAsyncExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor11() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    doNothing().when(sharedExecutorServiceAsyncExecutor).shutdown();
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert that nothing has changed
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).shutdown();
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertTrue(stringAsyncExecutorMap.containsKey("42"));
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor12() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor13() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(mock(CommandConfig.class), null));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor14() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(mock(CommandConfig.class), mock(CommandContextInterceptor.class)));
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor15() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(mock(CommandConfig.class), mock(CommandContextInterceptor.class)));
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  public void testShutdownTenantExecutor16() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class));
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert that nothing has changed
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertFalse(getResult.isActive());
  }

  /**
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getLockOwner()}
   */
  @Test
  public void testGetLockOwner() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getLockOwner()).thenReturn("Lock Owner");
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.getLockOwner();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getLockOwner();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getTimerLockTimeInMillis()}
   */
  @Test
  public void testGetTimerLockTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualTimerLockTimeInMillis = executorPerTenantAsyncExecutor.getTimerLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(300000, actualTimerLockTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getTimerLockTimeInMillis()}
   */
  @Test
  public void testGetTimerLockTimeInMillis2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getTimerLockTimeInMillis()).thenReturn(1);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualTimerLockTimeInMillis = executorPerTenantAsyncExecutor.getTimerLockTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).getTimerLockTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualTimerLockTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  public void testSetTimerLockTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert that nothing has changed
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  public void testSetTimerLockTimeInMillis2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  public void testGetAsyncJobLockTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis = executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  public void testGetAsyncJobLockTimeInMillis2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ManagedAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis = executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  public void testGetAsyncJobLockTimeInMillis3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getAsyncJobLockTimeInMillis()).thenReturn(1);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis = executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getAsyncJobLockTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setAsyncJobLockTimeInMillis(int)}
   */
  @Test
  public void testSetAsyncJobLockTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setAsyncJobLockTimeInMillis(1);

    // Assert that nothing has changed
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setAsyncJobLockTimeInMillis(int)}
   */
  @Test
  public void testSetAsyncJobLockTimeInMillis2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setAsyncJobLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getAsyncJobLockTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()).thenReturn(1);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultTimerJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert that nothing has changed
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis4() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(new CommandExecutorImpl(null, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis6() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandContextFactory commandContextFactory = new CommandContextFactory();
    processEngineConfiguration.setCommandExecutor(new CommandExecutorImpl(defaultConfig,
        new CommandContextInterceptor(commandContextFactory, new JtaProcessEngineConfiguration())));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis7() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    commandExecutor.setFirst(new CommandContextInterceptor());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   */
  @Test
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultAsyncJobAcquireWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(10000, actualDefaultAsyncJobAcquireWaitTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   */
  @Test
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultAsyncJobAcquireWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(10000, actualDefaultAsyncJobAcquireWaitTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   */
  @Test
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ManagedAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultAsyncJobAcquireWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(10000, actualDefaultAsyncJobAcquireWaitTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   */
  @Test
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()).thenReturn(1);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultAsyncJobAcquireWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultAsyncJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultAsyncJobAcquireWaitTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert that nothing has changed
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis4() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis5() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("Tenantid");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis6() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    defaultConfig.setContextReusePossible(true);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis7() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    commandExecutor.setFirst(new CommandContextInterceptor());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis8() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBpmnParser(new BpmnParser());
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis9() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessInstanceHelper(new ProcessInstanceHelper());
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis10() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomDefaultBpmnParseHandlers(new ArrayList<>());
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis11() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableEagerExecutionTreeFetching(true);
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis12() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventSubscriptionEntityManager(new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration2, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration())));
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis13() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMailServerUseSSL(true);
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getDefaultQueueSizeFullWaitTimeInMillis()}
   */
  @Test
  public void testGetDefaultQueueSizeFullWaitTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis()).thenReturn(3);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultQueueSizeFullWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultQueueSizeFullWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultQueueSizeFullWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualDefaultQueueSizeFullWaitTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultQueueSizeFullWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);

    // Assert that nothing has changed
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}
   */
  @Test
  public void testSetDefaultQueueSizeFullWaitTimeInMillis2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  public void testGetMaxAsyncJobsDuePerAcquisition() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition = executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(1, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  public void testGetMaxAsyncJobsDuePerAcquisition2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition = executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(1, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  public void testGetMaxAsyncJobsDuePerAcquisition3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()).thenReturn(3);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition = executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxAsyncJobsDuePerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  public void testSetMaxAsyncJobsDuePerAcquisition() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert that nothing has changed
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  public void testSetMaxAsyncJobsDuePerAcquisition2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  public void testGetMaxTimerJobsPerAcquisition() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getMaxTimerJobsPerAcquisition()).thenReturn(3);
    doNothing().when(asyncExecutor).setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).start();
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(asyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition = executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(asyncExecutor).getMaxTimerJobsPerAcquisition();
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setMaxTimerJobsPerAcquisition(int)}
   */
  @Test
  public void testSetMaxTimerJobsPerAcquisition() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setMaxTimerJobsPerAcquisition(3);

    // Assert that nothing has changed
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setMaxTimerJobsPerAcquisition(int)}
   */
  @Test
  public void testSetMaxTimerJobsPerAcquisition2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxTimerJobsPerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxTimerJobsPerAcquisition());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getRetryWaitTimeInMillis()}
   */
  @Test
  public void testGetRetryWaitTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getRetryWaitTimeInMillis()).thenReturn(1);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualRetryWaitTimeInMillis = executorPerTenantAsyncExecutor.getRetryWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).getRetryWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualRetryWaitTimeInMillis);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  public void testSetRetryWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert that nothing has changed
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  public void testSetRetryWaitTimeInMillis2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getResetExpiredJobsInterval()}
   */
  @Test
  public void testGetResetExpiredJobsInterval() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualResetExpiredJobsInterval = executorPerTenantAsyncExecutor.getResetExpiredJobsInterval();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(60000, actualResetExpiredJobsInterval);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getResetExpiredJobsInterval()}
   */
  @Test
  public void testGetResetExpiredJobsInterval2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualResetExpiredJobsInterval = executorPerTenantAsyncExecutor.getResetExpiredJobsInterval();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(60000, actualResetExpiredJobsInterval);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getResetExpiredJobsInterval()}
   */
  @Test
  public void testGetResetExpiredJobsInterval3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getResetExpiredJobsInterval()).thenReturn(42);
    doNothing().when(asyncExecutor).setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).start();
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(asyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualResetExpiredJobsInterval = executorPerTenantAsyncExecutor.getResetExpiredJobsInterval();

    // Assert
    verify(asyncExecutor).getResetExpiredJobsInterval();
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(42, actualResetExpiredJobsInterval);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}
   */
  @Test
  public void testSetResetExpiredJobsInterval() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsInterval(42);

    // Assert that nothing has changed
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}
   */
  @Test
  public void testSetResetExpiredJobsInterval2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsInterval(42);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(42, getResult.getResetExpiredJobsInterval());
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getResetExpiredJobsPageSize()}
   */
  @Test
  public void testGetResetExpiredJobsPageSize() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualResetExpiredJobsPageSize = executorPerTenantAsyncExecutor.getResetExpiredJobsPageSize();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(3, actualResetExpiredJobsPageSize);
  }

  /**
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#getResetExpiredJobsPageSize()}
   */
  @Test
  public void testGetResetExpiredJobsPageSize2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getResetExpiredJobsPageSize()).thenReturn(3);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualResetExpiredJobsPageSize = executorPerTenantAsyncExecutor.getResetExpiredJobsPageSize();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).getResetExpiredJobsPageSize();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualResetExpiredJobsPageSize);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ExecutorPerTenantAsyncExecutor#ExecutorPerTenantAsyncExecutor(TenantInfoHolder)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#setAutoActivate(boolean)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isActive()}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isAutoActivate()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ExecutorPerTenantAsyncExecutor actualExecutorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    actualExecutorPerTenantAsyncExecutor.setAutoActivate(true);
    boolean actualIsActiveResult = actualExecutorPerTenantAsyncExecutor.isActive();
    boolean actualIsAutoActivateResult = actualExecutorPerTenantAsyncExecutor.isAutoActivate();

    // Assert that nothing has changed
    assertFalse(actualIsActiveResult);
    assertTrue(actualExecutorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(actualIsAutoActivateResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ExecutorPerTenantAsyncExecutor#ExecutorPerTenantAsyncExecutor(TenantInfoHolder, TenantAwareAsyncExecutorFactory)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#setAutoActivate(boolean)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isActive()}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isAutoActivate()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters2() {
    // Arrange and Act
    ExecutorPerTenantAsyncExecutor actualExecutorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), mock(TenantAwareAsyncExecutorFactory.class));
    actualExecutorPerTenantAsyncExecutor.setAutoActivate(true);
    boolean actualIsActiveResult = actualExecutorPerTenantAsyncExecutor.isActive();
    boolean actualIsAutoActivateResult = actualExecutorPerTenantAsyncExecutor.isAutoActivate();

    // Assert that nothing has changed
    assertFalse(actualIsActiveResult);
    assertTrue(actualExecutorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(actualIsAutoActivateResult);
  }
}
