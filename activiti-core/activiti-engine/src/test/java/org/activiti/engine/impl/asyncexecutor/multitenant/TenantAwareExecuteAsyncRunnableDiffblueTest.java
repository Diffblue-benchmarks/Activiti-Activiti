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
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TenantAwareExecuteAsyncRunnableDiffblueTest {
  @Mock private Job job;

  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Mock private TenantInfoHolder tenantInfoHolder;

  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#TenantAwareExecuteAsyncRunnable(Job,
   * ProcessEngineConfigurationImpl, TenantInfoHolder, String)}.
   *
   * <p>Method under test: {@link
   * TenantAwareExecuteAsyncRunnable#TenantAwareExecuteAsyncRunnable(Job,
   * ProcessEngineConfigurationImpl, TenantInfoHolder, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TenantAwareExecuteAsyncRunnable.<init>(Job, ProcessEngineConfigurationImpl, TenantInfoHolder, String)"
  })
  public void testNewTenantAwareExecuteAsyncRunnable() throws IOException {
    // Arrange
    DeadLetterJobEntityImpl job = new DeadLetterJobEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();

    // Act
    TenantAwareExecuteAsyncRunnable actualTenantAwareExecuteAsyncRunnable =
        new TenantAwareExecuteAsyncRunnable(
            job, processEngineConfiguration, tenantInfoHolder, "42");

    // Assert
    Collection<? extends Deployer> defaultDeployers =
        processEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    Object persistentState = job.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(
        processEngineConfiguration.getDynamicBpmnService() instanceof DynamicBpmnServiceImpl);
    assertTrue(processEngineConfiguration.getHistoryService() instanceof HistoryServiceImpl);
    assertTrue(processEngineConfiguration.getManagementService() instanceof ManagementServiceImpl);
    assertTrue(processEngineConfiguration.getRepositoryService() instanceof RepositoryServiceImpl);
    assertTrue(processEngineConfiguration.getRuntimeService() instanceof RuntimeServiceImpl);
    assertTrue(processEngineConfiguration.getTaskService() instanceof TaskServiceImpl);
    assertTrue(
        processEngineConfiguration.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        processEngineConfiguration.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertTrue(
        actualTenantAwareExecuteAsyncRunnable.tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertEquals("", processEngineConfiguration.getDatabaseCatalog());
    assertEquals("", processEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", processEngineConfiguration.getJdbcPassword());
    assertEquals("", job.getTenantId());
    assertEquals("42", actualTenantAwareExecuteAsyncRunnable.tenantId);
    assertEquals("@class", processEngineConfiguration.getJavaClassFieldForJackson());
    assertEquals("UTF-8", processEngineConfiguration.getXmlEncoding());
    assertEquals("activiti@localhost", processEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", processEngineConfiguration.getHistory());
    assertEquals("camelContext", processEngineConfiguration.getDefaultCamelContext());
    assertEquals("default", processEngineConfiguration.getProcessEngineName());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", processEngineConfiguration.getJdbcUrl());
    assertEquals("localhost", processEngineConfiguration.getMailServerHost());
    assertEquals(
        "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        processEngineConfiguration.getWsSyncFactoryClassName());
    assertEquals("org.h2.Driver", processEngineConfiguration.getJdbcDriver());
    assertEquals("sa", processEngineConfiguration.getJdbcUsername());
    assertNull(processEngineConfiguration.getTransactionManager());
    assertNull(processEngineConfiguration.getClassLoader());
    assertNull(processEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(processEngineConfiguration.getDataSourceJndiName());
    assertNull(processEngineConfiguration.getDatabaseSchema());
    assertNull(processEngineConfiguration.getDatabaseType());
    assertNull(processEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertNull(processEngineConfiguration.getJdbcPingQuery());
    assertNull(processEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(processEngineConfiguration.getMailServerPassword());
    assertNull(processEngineConfiguration.getMailServerUsername());
    assertNull(processEngineConfiguration.getMailSessionJndi());
    assertNull(processEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(processEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(job.getId());
    assertNull(job.getExceptionMessage());
    assertNull(job.getExceptionStacktrace());
    assertNull(job.getExecutionId());
    assertNull(job.getJobHandlerConfiguration());
    assertNull(job.getJobHandlerType());
    assertNull(job.getJobType());
    assertNull(job.getProcessDefinitionId());
    assertNull(job.getProcessInstanceId());
    assertNull(job.getRepeat());
    assertNull(tenantInfoHolder.getCurrentTenantId());
    assertNull(tenantInfoHolder.getCurrentUserId());
    assertNull(job.getDuedate());
    assertNull(job.getEndDate());
    assertNull(processEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(processEngineConfiguration.getCustomFunctionProviders());
    assertNull(processEngineConfiguration.getAllConfigurators());
    assertNull(processEngineConfiguration.getConfigurators());
    assertNull(processEngineConfiguration.getEventListeners());
    assertNull(processEngineConfiguration.getCustomEventHandlers());
    assertNull(processEngineConfiguration.getCommandInterceptors());
    assertNull(processEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(processEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(processEngineConfiguration.getCustomSessionFactories());
    assertNull(processEngineConfiguration.getCustomJobHandlers());
    assertNull(processEngineConfiguration.getCustomPostDeployers());
    assertNull(processEngineConfiguration.getCustomPreDeployers());
    assertNull(processEngineConfiguration.getDeployers());
    assertNull(processEngineConfiguration.getResolverFactories());
    assertNull(processEngineConfiguration.getCustomPostVariableTypes());
    assertNull(processEngineConfiguration.getCustomPreVariableTypes());
    assertNull(processEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(processEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(processEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(processEngineConfiguration.getSessionFactories());
    assertNull(processEngineConfiguration.getBeans());
    assertNull(processEngineConfiguration.getTypedEventListeners());
    assertNull(processEngineConfiguration.getEventHandlers());
    assertNull(processEngineConfiguration.getJobHandlers());
    assertNull(processEngineConfiguration.getCustomMybatisMappers());
    assertNull(processEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(processEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(processEngineConfiguration.getDataSource());
    assertNull(processEngineConfiguration.getIdGeneratorDataSource());
    assertNull(processEngineConfiguration.getUserGroupManager());
    assertNull(processEngineConfiguration.getEngineAgendaFactory());
    assertNull(processEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(processEngineConfiguration.getEventDispatcher());
    assertNull(processEngineConfiguration.getProcessDefinitionHelper());
    assertNull(processEngineConfiguration.getAsyncExecutor());
    assertNull(processEngineConfiguration.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(processEngineConfiguration.getJobManager());
    assertNull(processEngineConfiguration.getListenerNotificationHelper());
    assertNull(processEngineConfiguration.getBpmnParser());
    assertNull(processEngineConfiguration.getActivityBehaviorFactory());
    assertNull(processEngineConfiguration.getListenerFactory());
    assertNull(processEngineConfiguration.getBusinessCalendarManager());
    assertNull(processEngineConfiguration.getBpmnParseFactory());
    assertNull(processEngineConfiguration.getIdGenerator());
    assertNull(processEngineConfiguration.getTransactionContextFactory());
    assertNull(processEngineConfiguration.getDbSqlSessionFactory());
    assertNull(processEngineConfiguration.getExpressionManager());
    assertNull(processEngineConfiguration.getHistoryLevel());
    assertNull(processEngineConfiguration.getHistoryManager());
    assertNull(processEngineConfiguration.getDefaultCommandConfig());
    assertNull(processEngineConfiguration.getSchemaCommandConfig());
    assertNull(processEngineConfiguration.getCommandContextFactory());
    assertNull(processEngineConfiguration.getCommandExecutor());
    assertNull(processEngineConfiguration.getCommandInvoker());
    assertNull(processEngineConfiguration.getDelegateInterceptor());
    assertNull(processEngineConfiguration.getFailedJobCommandFactory());
    assertNull(processEngineConfiguration.getKnowledgeBaseCache());
    assertNull(processEngineConfiguration.getProcessDefinitionCache());
    assertNull(processEngineConfiguration.getDeploymentManager());
    assertNull(processEngineConfiguration.getAttachmentEntityManager());
    assertNull(processEngineConfiguration.getByteArrayEntityManager());
    assertNull(job.getExceptionByteArrayRef());
    assertNull(processEngineConfiguration.getCommentEntityManager());
    assertNull(processEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(processEngineConfiguration.getDeploymentEntityManager());
    assertNull(processEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(processEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(processEngineConfiguration.getExecutionEntityManager());
    assertNull(processEngineConfiguration.getHistoricActivityInstanceEntityManager());
    assertNull(processEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(processEngineConfiguration.getHistoricIdentityLinkEntityManager());
    assertNull(processEngineConfiguration.getHistoricProcessInstanceEntityManager());
    assertNull(processEngineConfiguration.getHistoricTaskInstanceEntityManager());
    assertNull(processEngineConfiguration.getHistoricVariableInstanceEntityManager());
    assertNull(processEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(processEngineConfiguration.getJobEntityManager());
    assertNull(processEngineConfiguration.getModelEntityManager());
    assertNull(processEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(processEngineConfiguration.getProcessDefinitionInfoEntityManager());
    assertNull(processEngineConfiguration.getPropertyEntityManager());
    assertNull(processEngineConfiguration.getResourceEntityManager());
    assertNull(processEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(processEngineConfiguration.getTableDataManager());
    assertNull(processEngineConfiguration.getTaskEntityManager());
    assertNull(processEngineConfiguration.getTimerJobEntityManager());
    assertNull(processEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(processEngineConfiguration.getAttachmentDataManager());
    assertNull(processEngineConfiguration.getByteArrayDataManager());
    assertNull(processEngineConfiguration.getCommentDataManager());
    assertNull(processEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(processEngineConfiguration.getDeploymentDataManager());
    assertNull(processEngineConfiguration.getEventLogEntryDataManager());
    assertNull(processEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(processEngineConfiguration.getExecutionDataManager());
    assertNull(processEngineConfiguration.getHistoricActivityInstanceDataManager());
    assertNull(processEngineConfiguration.getHistoricDetailDataManager());
    assertNull(processEngineConfiguration.getHistoricIdentityLinkDataManager());
    assertNull(processEngineConfiguration.getHistoricProcessInstanceDataManager());
    assertNull(processEngineConfiguration.getHistoricTaskInstanceDataManager());
    assertNull(processEngineConfiguration.getHistoricVariableInstanceDataManager());
    assertNull(processEngineConfiguration.getIdentityLinkDataManager());
    assertNull(processEngineConfiguration.getJobDataManager());
    assertNull(processEngineConfiguration.getModelDataManager());
    assertNull(processEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(processEngineConfiguration.getProcessDefinitionInfoDataManager());
    assertNull(processEngineConfiguration.getPropertyDataManager());
    assertNull(processEngineConfiguration.getResourceDataManager());
    assertNull(processEngineConfiguration.getSuspendedJobDataManager());
    assertNull(processEngineConfiguration.getTaskDataManager());
    assertNull(processEngineConfiguration.getTimerJobDataManager());
    assertNull(processEngineConfiguration.getVariableInstanceDataManager());
    assertNull(processEngineConfiguration.getScriptingEngines());
    assertNull(processEngineConfiguration.getProcessInstanceHelper());
    assertNull(processEngineConfiguration.getVariableTypes());
    assertNull(processEngineConfiguration.getClock());
    assertNull(processEngineConfiguration.getProcessValidator());
    assertNull(processEngineConfiguration.getSqlSessionFactory());
    assertNull(processEngineConfiguration.getTransactionFactory());
    assertEquals(-1, processEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(-1, processEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(-1, processEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(0, processEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, processEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(0, processEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(0, processEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, processEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(0, processEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(0, processEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(0, job.getMaxIterations());
    assertEquals(0, job.getRetries());
    assertEquals(1, processEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, processEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(1, job.getRevision());
    assertEquals(10, processEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(10, processEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(10, processEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(100, processEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, processEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(
        10000, processEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        10000, processEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(2, processEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(2, job.getRevisionNext());
    assertEquals(20000, processEngineConfiguration.getExecutionQueryLimit());
    assertEquals(20000, processEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, processEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, processEngineConfiguration.getTaskQueryLimit());
    assertEquals(25, processEngineConfiguration.getMailServerPort());
    assertEquals(25, processEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, processEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, processEngineConfiguration.getIdBlockSize());
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertEquals(3, processEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(3, processEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(30, processEngineConfiguration.getDefaultBpmnParseHandlers().size());
    assertEquals(300000, processEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(300000, processEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(4000, processEngineConfiguration.getMaxLengthString());
    assertEquals(5000L, processEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(51, processEngineConfiguration.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, processEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(60000, processEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(60L, processEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        70, processEngineConfiguration.DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        processEngineConfiguration.getDelegateExpressionFieldInjectionMode());
    assertFalse(processEngineConfiguration.getMailServerUseSSL());
    assertFalse(processEngineConfiguration.getMailServerUseTLS());
    assertFalse(processEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(processEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(processEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertFalse(processEngineConfiguration.isJdbcPingEnabled());
    assertFalse(processEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(processEngineConfiguration.isJpaHandleTransaction());
    assertFalse(processEngineConfiguration.isTablePrefixIsSchema());
    assertFalse(processEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(processEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(processEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(processEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertFalse(processEngineConfiguration.isRollbackDeployment());
    assertFalse(processEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertFalse(job.isDeleted());
    assertFalse(job.isInserted());
    assertFalse(job.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(processEngineConfiguration.getMailServers().isEmpty());
    assertTrue(processEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(processEngineConfiguration.isDbHistoryUsed());
    assertTrue(processEngineConfiguration.isTransactionsExternallyManaged());
    assertTrue(processEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(processEngineConfiguration.isBulkInsertEnabled());
    assertTrue(processEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(processEngineConfiguration.isEnableEventDispatcher());
    assertTrue(processEngineConfiguration.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(processEngineConfiguration.isUsingRelationalDatabase());
    assertTrue(job.isExclusive());
    assertEquals(Boolean.FALSE.toString(), processEngineConfiguration.getDatabaseSchemaUpdate());
    assertArrayEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#TenantAwareExecuteAsyncRunnable(Job,
   * ProcessEngineConfigurationImpl, TenantInfoHolder, String)}.
   *
   * <p>Method under test: {@link
   * TenantAwareExecuteAsyncRunnable#TenantAwareExecuteAsyncRunnable(Job,
   * ProcessEngineConfigurationImpl, TenantInfoHolder, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TenantAwareExecuteAsyncRunnable.<init>(Job, ProcessEngineConfigurationImpl, TenantInfoHolder, String)"
  })
  public void testNewTenantAwareExecuteAsyncRunnable2() throws IOException {
    // Arrange
    Job job = mock(Job.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();

    // Act
    TenantAwareExecuteAsyncRunnable actualTenantAwareExecuteAsyncRunnable =
        new TenantAwareExecuteAsyncRunnable(
            job, processEngineConfiguration, tenantInfoHolder, "42");
    actualTenantAwareExecuteAsyncRunnable.run();

    // Assert
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
    Collection<? extends Deployer> defaultDeployers =
        processEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(
        processEngineConfiguration.getDynamicBpmnService() instanceof DynamicBpmnServiceImpl);
    assertTrue(processEngineConfiguration.getHistoryService() instanceof HistoryServiceImpl);
    assertTrue(processEngineConfiguration.getManagementService() instanceof ManagementServiceImpl);
    assertTrue(processEngineConfiguration.getRepositoryService() instanceof RepositoryServiceImpl);
    assertTrue(processEngineConfiguration.getRuntimeService() instanceof RuntimeServiceImpl);
    assertTrue(processEngineConfiguration.getTaskService() instanceof TaskServiceImpl);
    CommandExecutor commandExecutor2 = processEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor2 instanceof CommandExecutorImpl);
    assertTrue(
        processEngineConfiguration.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        processEngineConfiguration.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertTrue(
        actualTenantAwareExecuteAsyncRunnable.tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertEquals("", processEngineConfiguration.getDatabaseCatalog());
    assertEquals("", processEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", processEngineConfiguration.getJdbcPassword());
    assertEquals("42", actualTenantAwareExecuteAsyncRunnable.tenantId);
    assertEquals("@class", processEngineConfiguration.getJavaClassFieldForJackson());
    assertEquals("UTF-8", processEngineConfiguration.getXmlEncoding());
    assertEquals("activiti@localhost", processEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", processEngineConfiguration.getHistory());
    assertEquals("camelContext", processEngineConfiguration.getDefaultCamelContext());
    assertEquals("default", processEngineConfiguration.getProcessEngineName());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", processEngineConfiguration.getJdbcUrl());
    assertEquals("localhost", processEngineConfiguration.getMailServerHost());
    assertEquals(
        "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        processEngineConfiguration.getWsSyncFactoryClassName());
    assertEquals("org.h2.Driver", processEngineConfiguration.getJdbcDriver());
    assertEquals("sa", processEngineConfiguration.getJdbcUsername());
    assertNull(processEngineConfiguration.getTransactionManager());
    assertNull(processEngineConfiguration.getClassLoader());
    assertNull(processEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(processEngineConfiguration.getDataSourceJndiName());
    assertNull(processEngineConfiguration.getDatabaseSchema());
    assertNull(processEngineConfiguration.getDatabaseType());
    assertNull(processEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertNull(processEngineConfiguration.getJdbcPingQuery());
    assertNull(processEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(processEngineConfiguration.getMailServerPassword());
    assertNull(processEngineConfiguration.getMailServerUsername());
    assertNull(processEngineConfiguration.getMailSessionJndi());
    assertNull(processEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(processEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(tenantInfoHolder.getCurrentTenantId());
    assertNull(tenantInfoHolder.getCurrentUserId());
    assertNull(processEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(processEngineConfiguration.getCustomFunctionProviders());
    assertNull(processEngineConfiguration.getAllConfigurators());
    assertNull(processEngineConfiguration.getConfigurators());
    assertNull(processEngineConfiguration.getEventListeners());
    assertNull(processEngineConfiguration.getCustomEventHandlers());
    assertNull(processEngineConfiguration.getCommandInterceptors());
    assertNull(processEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(processEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(processEngineConfiguration.getCustomSessionFactories());
    assertNull(processEngineConfiguration.getCustomJobHandlers());
    assertNull(processEngineConfiguration.getCustomPostDeployers());
    assertNull(processEngineConfiguration.getCustomPreDeployers());
    assertNull(processEngineConfiguration.getDeployers());
    assertNull(processEngineConfiguration.getResolverFactories());
    assertNull(processEngineConfiguration.getCustomPostVariableTypes());
    assertNull(processEngineConfiguration.getCustomPreVariableTypes());
    assertNull(processEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(processEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(processEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(processEngineConfiguration.getSessionFactories());
    assertNull(processEngineConfiguration.getBeans());
    assertNull(processEngineConfiguration.getTypedEventListeners());
    assertNull(processEngineConfiguration.getEventHandlers());
    assertNull(processEngineConfiguration.getJobHandlers());
    assertNull(processEngineConfiguration.getCustomMybatisMappers());
    assertNull(processEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(processEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(processEngineConfiguration.getDataSource());
    assertNull(processEngineConfiguration.getIdGeneratorDataSource());
    assertNull(processEngineConfiguration.getUserGroupManager());
    assertNull(processEngineConfiguration.getEngineAgendaFactory());
    assertNull(processEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(processEngineConfiguration.getEventDispatcher());
    assertNull(processEngineConfiguration.getProcessDefinitionHelper());
    assertNull(processEngineConfiguration.getAsyncExecutor());
    assertNull(processEngineConfiguration.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(processEngineConfiguration.getJobManager());
    assertNull(processEngineConfiguration.getListenerNotificationHelper());
    assertNull(processEngineConfiguration.getBpmnParser());
    assertNull(processEngineConfiguration.getActivityBehaviorFactory());
    assertNull(processEngineConfiguration.getListenerFactory());
    assertNull(processEngineConfiguration.getBusinessCalendarManager());
    assertNull(processEngineConfiguration.getBpmnParseFactory());
    assertNull(processEngineConfiguration.getIdGenerator());
    assertNull(processEngineConfiguration.getTransactionContextFactory());
    assertNull(processEngineConfiguration.getDbSqlSessionFactory());
    assertNull(processEngineConfiguration.getExpressionManager());
    assertNull(processEngineConfiguration.getHistoryLevel());
    assertNull(processEngineConfiguration.getHistoryManager());
    assertNull(processEngineConfiguration.getDefaultCommandConfig());
    assertNull(processEngineConfiguration.getSchemaCommandConfig());
    assertNull(processEngineConfiguration.getCommandContextFactory());
    assertNull(processEngineConfiguration.getCommandInvoker());
    assertNull(processEngineConfiguration.getDelegateInterceptor());
    assertNull(processEngineConfiguration.getFailedJobCommandFactory());
    assertNull(processEngineConfiguration.getKnowledgeBaseCache());
    assertNull(processEngineConfiguration.getProcessDefinitionCache());
    assertNull(processEngineConfiguration.getDeploymentManager());
    assertNull(processEngineConfiguration.getAttachmentEntityManager());
    assertNull(processEngineConfiguration.getByteArrayEntityManager());
    assertNull(processEngineConfiguration.getCommentEntityManager());
    assertNull(processEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(processEngineConfiguration.getDeploymentEntityManager());
    assertNull(processEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(processEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(processEngineConfiguration.getExecutionEntityManager());
    assertNull(processEngineConfiguration.getHistoricActivityInstanceEntityManager());
    assertNull(processEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(processEngineConfiguration.getHistoricIdentityLinkEntityManager());
    assertNull(processEngineConfiguration.getHistoricProcessInstanceEntityManager());
    assertNull(processEngineConfiguration.getHistoricTaskInstanceEntityManager());
    assertNull(processEngineConfiguration.getHistoricVariableInstanceEntityManager());
    assertNull(processEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(processEngineConfiguration.getJobEntityManager());
    assertNull(processEngineConfiguration.getModelEntityManager());
    assertNull(processEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(processEngineConfiguration.getProcessDefinitionInfoEntityManager());
    assertNull(processEngineConfiguration.getPropertyEntityManager());
    assertNull(processEngineConfiguration.getResourceEntityManager());
    assertNull(processEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(processEngineConfiguration.getTableDataManager());
    assertNull(processEngineConfiguration.getTaskEntityManager());
    assertNull(processEngineConfiguration.getTimerJobEntityManager());
    assertNull(processEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(processEngineConfiguration.getAttachmentDataManager());
    assertNull(processEngineConfiguration.getByteArrayDataManager());
    assertNull(processEngineConfiguration.getCommentDataManager());
    assertNull(processEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(processEngineConfiguration.getDeploymentDataManager());
    assertNull(processEngineConfiguration.getEventLogEntryDataManager());
    assertNull(processEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(processEngineConfiguration.getExecutionDataManager());
    assertNull(processEngineConfiguration.getHistoricActivityInstanceDataManager());
    assertNull(processEngineConfiguration.getHistoricDetailDataManager());
    assertNull(processEngineConfiguration.getHistoricIdentityLinkDataManager());
    assertNull(processEngineConfiguration.getHistoricProcessInstanceDataManager());
    assertNull(processEngineConfiguration.getHistoricTaskInstanceDataManager());
    assertNull(processEngineConfiguration.getHistoricVariableInstanceDataManager());
    assertNull(processEngineConfiguration.getIdentityLinkDataManager());
    assertNull(processEngineConfiguration.getJobDataManager());
    assertNull(processEngineConfiguration.getModelDataManager());
    assertNull(processEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(processEngineConfiguration.getProcessDefinitionInfoDataManager());
    assertNull(processEngineConfiguration.getPropertyDataManager());
    assertNull(processEngineConfiguration.getResourceDataManager());
    assertNull(processEngineConfiguration.getSuspendedJobDataManager());
    assertNull(processEngineConfiguration.getTaskDataManager());
    assertNull(processEngineConfiguration.getTimerJobDataManager());
    assertNull(processEngineConfiguration.getVariableInstanceDataManager());
    assertNull(processEngineConfiguration.getScriptingEngines());
    assertNull(processEngineConfiguration.getProcessInstanceHelper());
    assertNull(processEngineConfiguration.getVariableTypes());
    assertNull(processEngineConfiguration.getClock());
    assertNull(processEngineConfiguration.getProcessValidator());
    assertNull(processEngineConfiguration.getSqlSessionFactory());
    assertNull(processEngineConfiguration.getTransactionFactory());
    assertEquals(-1, processEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(-1, processEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(-1, processEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(0, processEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, processEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(0, processEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(0, processEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, processEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(0, processEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(0, processEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(1, processEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, processEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, processEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(10, processEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(10, processEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(100, processEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, processEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(
        10000, processEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        10000, processEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(2, processEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(20000, processEngineConfiguration.getExecutionQueryLimit());
    assertEquals(20000, processEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, processEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, processEngineConfiguration.getTaskQueryLimit());
    assertEquals(25, processEngineConfiguration.getMailServerPort());
    assertEquals(25, processEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, processEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, processEngineConfiguration.getIdBlockSize());
    assertEquals(3, processEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(3, processEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(30, processEngineConfiguration.getDefaultBpmnParseHandlers().size());
    assertEquals(300000, processEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(300000, processEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(4000, processEngineConfiguration.getMaxLengthString());
    assertEquals(5000L, processEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(51, processEngineConfiguration.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, processEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(60000, processEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(60L, processEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        70, processEngineConfiguration.DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        processEngineConfiguration.getDelegateExpressionFieldInjectionMode());
    assertFalse(processEngineConfiguration.getMailServerUseSSL());
    assertFalse(processEngineConfiguration.getMailServerUseTLS());
    assertFalse(processEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(processEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(processEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertFalse(processEngineConfiguration.isJdbcPingEnabled());
    assertFalse(processEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(processEngineConfiguration.isJpaHandleTransaction());
    assertFalse(processEngineConfiguration.isTablePrefixIsSchema());
    assertFalse(processEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(processEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(processEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(processEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertFalse(processEngineConfiguration.isRollbackDeployment());
    assertFalse(processEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertTrue(processEngineConfiguration.getMailServers().isEmpty());
    assertTrue(processEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(processEngineConfiguration.isDbHistoryUsed());
    assertTrue(processEngineConfiguration.isTransactionsExternallyManaged());
    assertTrue(processEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(processEngineConfiguration.isBulkInsertEnabled());
    assertTrue(processEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(processEngineConfiguration.isEnableEventDispatcher());
    assertTrue(processEngineConfiguration.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(processEngineConfiguration.isUsingRelationalDatabase());
    assertEquals(Boolean.FALSE.toString(), processEngineConfiguration.getDatabaseSchemaUpdate());
    assertSame(commandExecutor, commandExecutor2);
    assertArrayEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#TenantAwareExecuteAsyncRunnable(Job,
   * ProcessEngineConfigurationImpl, TenantInfoHolder, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getCommandExecutor()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TenantAwareExecuteAsyncRunnable#TenantAwareExecuteAsyncRunnable(Job,
   * ProcessEngineConfigurationImpl, TenantInfoHolder, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TenantAwareExecuteAsyncRunnable.<init>(Job, ProcessEngineConfigurationImpl, TenantInfoHolder, String)"
  })
  public void testNewTenantAwareExecuteAsyncRunnable_thenCallsGetCommandExecutor() {
    // Arrange
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    when(job.isExclusive()).thenReturn(false);
    when(job.getId()).thenReturn("42");

    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);
    when(processEngineConfigurationImpl.getCommandExecutor()).thenReturn(commandExecutorImpl);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();

    // Act
    TenantAwareExecuteAsyncRunnable actualTenantAwareExecuteAsyncRunnable =
        new TenantAwareExecuteAsyncRunnable(
            job, processEngineConfigurationImpl, tenantInfoHolder, "42");
    actualTenantAwareExecuteAsyncRunnable.run();

    // Assert
    verify(processEngineConfigurationImpl).getCommandExecutor();
    verify(tenantInfoHolder).clearCurrentTenantId();
    verify(tenantInfoHolder).setCurrentTenantId("42");
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
    assertEquals("42", actualTenantAwareExecuteAsyncRunnable.tenantId);
  }

  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#run()}.
   *
   * <ul>
   *   <li>Given {@link Job} {@link Job#isExclusive()} return {@code false}.
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getCommandExecutor()}.
   * </ul>
   *
   * <p>Method under test: {@link TenantAwareExecuteAsyncRunnable#run()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TenantAwareExecuteAsyncRunnable.run()"})
  public void testRun_givenJobIsExclusiveReturnFalse_thenCallsGetCommandExecutor() {
    // Arrange
    Job job = mock(Job.class);
    when(job.isExclusive()).thenReturn(false);
    when(job.getId()).thenReturn("42");

    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    TenantAwareExecuteAsyncRunnable tenantAwareExecuteAsyncRunnable =
        new TenantAwareExecuteAsyncRunnable(
            job, processEngineConfiguration, new DummyTenantInfoHolder(), "42");

    // Act
    tenantAwareExecuteAsyncRunnable.run();

    // Assert
    verify(processEngineConfiguration).getCommandExecutor();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
  }

  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#run()}.
   *
   * <ul>
   *   <li>Given {@link Job} {@link Job#isExclusive()} return {@code true}.
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getCommandExecutor()}.
   * </ul>
   *
   * <p>Method under test: {@link TenantAwareExecuteAsyncRunnable#run()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TenantAwareExecuteAsyncRunnable.run()"})
  public void testRun_givenJobIsExclusiveReturnTrue_thenCallsGetCommandExecutor() {
    // Arrange
    Job job = mock(Job.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    TenantAwareExecuteAsyncRunnable tenantAwareExecuteAsyncRunnable =
        new TenantAwareExecuteAsyncRunnable(
            job, processEngineConfiguration, new DummyTenantInfoHolder(), "42");

    // Act
    tenantAwareExecuteAsyncRunnable.run();

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
  }
}
