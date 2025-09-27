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
package org.activiti.engine.impl.cfg;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class StandaloneInMemProcessEngineConfigurationDiffblueTest {
  /**
   * Test new {@link StandaloneInMemProcessEngineConfiguration} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * StandaloneInMemProcessEngineConfiguration}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StandaloneInMemProcessEngineConfiguration.<init>()"})
  public void testNewStandaloneInMemProcessEngineConfiguration() throws IOException {
    // Arrange and Act
    StandaloneInMemProcessEngineConfiguration actualStandaloneInMemProcessEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();

    // Assert
    Collection<? extends CommandInterceptor> defaultCommandInterceptors =
        actualStandaloneInMemProcessEngineConfiguration.getDefaultCommandInterceptors();
    assertEquals(1, defaultCommandInterceptors.size());
    assertTrue(defaultCommandInterceptors instanceof List);
    Collection<? extends Deployer> defaultDeployers =
        actualStandaloneInMemProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration.getDynamicBpmnService()
            instanceof DynamicBpmnServiceImpl);
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration.getHistoryService()
            instanceof HistoryServiceImpl);
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration.getManagementService()
            instanceof ManagementServiceImpl);
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration.getRepositoryService()
            instanceof RepositoryServiceImpl);
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration.getRuntimeService()
            instanceof RuntimeServiceImpl);
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration.getTaskService()
            instanceof TaskServiceImpl);
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertEquals("", actualStandaloneInMemProcessEngineConfiguration.getDatabaseCatalog());
    assertEquals("", actualStandaloneInMemProcessEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", actualStandaloneInMemProcessEngineConfiguration.getJdbcPassword());
    assertEquals(
        "@class", actualStandaloneInMemProcessEngineConfiguration.getJavaClassFieldForJackson());
    assertEquals("UTF-8", actualStandaloneInMemProcessEngineConfiguration.getXmlEncoding());
    assertEquals(
        "activiti@localhost",
        actualStandaloneInMemProcessEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", actualStandaloneInMemProcessEngineConfiguration.getHistory());
    assertEquals(
        "camelContext", actualStandaloneInMemProcessEngineConfiguration.getDefaultCamelContext());
    assertEquals(
        "create-drop", actualStandaloneInMemProcessEngineConfiguration.getDatabaseSchemaUpdate());
    assertEquals("default", actualStandaloneInMemProcessEngineConfiguration.getProcessEngineName());
    assertEquals(
        "jdbc:h2:mem:activiti", actualStandaloneInMemProcessEngineConfiguration.getJdbcUrl());
    assertEquals("localhost", actualStandaloneInMemProcessEngineConfiguration.getMailServerHost());
    assertEquals("org.h2.Driver", actualStandaloneInMemProcessEngineConfiguration.getJdbcDriver());
    assertEquals("sa", actualStandaloneInMemProcessEngineConfiguration.getJdbcUsername());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getClassLoader());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDataSourceJndiName());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDatabaseSchema());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDatabaseType());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getJdbcPingQuery());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getMailServerPassword());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getMailServerUsername());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getMailSessionJndi());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomFunctionProviders());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getAllConfigurators());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getConfigurators());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getEventListeners());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomEventHandlers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCommandInterceptors());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomSessionFactories());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomJobHandlers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomPostDeployers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomPreDeployers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDeployers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getResolverFactories());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomPostVariableTypes());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomPreVariableTypes());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getSessionFactories());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getBeans());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getTypedEventListeners());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getEventHandlers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getJobHandlers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomMybatisMappers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDataSource());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getIdGeneratorDataSource());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getUserGroupManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getEngineAgendaFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getEventDispatcher());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getProcessDefinitionHelper());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutor());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration
            .getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getJobManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getListenerNotificationHelper());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getBpmnParser());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getActivityBehaviorFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getListenerFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getBusinessCalendarManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getBpmnParseFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getIdGenerator());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getTransactionContextFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDbSqlSessionFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getExpressionManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getHistoryLevel());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getHistoryManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDefaultCommandConfig());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getSchemaCommandConfig());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCommandContextFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCommandExecutor());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCommandInvoker());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDelegateInterceptor());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getFailedJobCommandFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getKnowledgeBaseCache());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getProcessDefinitionCache());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDeploymentManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.processDefinitionInfoCache);
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getAttachmentEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getByteArrayEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCommentEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDeploymentEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getExecutionEntityManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getJobEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getModelEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getProcessDefinitionInfoEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getPropertyEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getResourceEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getTableDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getTaskEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getTimerJobEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getAttachmentDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getByteArrayDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getCommentDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getDeploymentDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getEventLogEntryDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getExecutionDataManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getHistoricDetailDataManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getIdentityLinkDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getJobDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getModelDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(
        actualStandaloneInMemProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getPropertyDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getResourceDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getSuspendedJobDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getTaskDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getTimerJobDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getVariableInstanceDataManager());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getScriptingEngines());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getProcessInstanceHelper());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getVariableTypes());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getClock());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getProcessValidator());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getSqlSessionFactory());
    assertNull(actualStandaloneInMemProcessEngineConfiguration.getTransactionFactory());
    assertEquals(-1, actualStandaloneInMemProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(
        -1, actualStandaloneInMemProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(
        -1, actualStandaloneInMemProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(
        -1, actualStandaloneInMemProcessEngineConfiguration.processDefinitionInfoCacheLimit);
    assertEquals(
        0,
        actualStandaloneInMemProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, actualStandaloneInMemProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(0, actualStandaloneInMemProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(0, actualStandaloneInMemProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, actualStandaloneInMemProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(
        0, actualStandaloneInMemProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(
        0,
        actualStandaloneInMemProcessEngineConfiguration
            .getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(
        1,
        actualStandaloneInMemProcessEngineConfiguration
            .getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(
        1,
        actualStandaloneInMemProcessEngineConfiguration
            .getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, actualStandaloneInMemProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(10, actualStandaloneInMemProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(10, actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(
        100, actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(
        100, actualStandaloneInMemProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(
        10000,
        actualStandaloneInMemProcessEngineConfiguration
            .getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        10000,
        actualStandaloneInMemProcessEngineConfiguration
            .getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(2, actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(20000, actualStandaloneInMemProcessEngineConfiguration.getExecutionQueryLimit());
    assertEquals(
        20000,
        actualStandaloneInMemProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertEquals(
        20000, actualStandaloneInMemProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, actualStandaloneInMemProcessEngineConfiguration.getTaskQueryLimit());
    assertEquals(25, actualStandaloneInMemProcessEngineConfiguration.getMailServerPort());
    assertEquals(
        25, actualStandaloneInMemProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, actualStandaloneInMemProcessEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, actualStandaloneInMemProcessEngineConfiguration.getIdBlockSize());
    assertEquals(
        3, actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(
        3,
        actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(
        30, actualStandaloneInMemProcessEngineConfiguration.getDefaultBpmnParseHandlers().size());
    assertEquals(
        300000,
        actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(
        300000,
        actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(
        5000L,
        actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(
        51,
        actualStandaloneInMemProcessEngineConfiguration
            .getMyBatisXmlConfigurationStream()
            .read(byteArray));
    assertEquals(60, actualStandaloneInMemProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(
        60000,
        actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(
        60L,
        actualStandaloneInMemProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        70,
        actualStandaloneInMemProcessEngineConfiguration
            .DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        actualStandaloneInMemProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.getMailServerUseSSL());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.getMailServerUseTLS());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(
        actualStandaloneInMemProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isJdbcPingEnabled());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isJpaHandleTransaction());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isTablePrefixIsSchema());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isTransactionsExternallyManaged());
    assertFalse(
        actualStandaloneInMemProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(
        actualStandaloneInMemProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertFalse(actualStandaloneInMemProcessEngineConfiguration.isRollbackDeployment());
    assertFalse(
        actualStandaloneInMemProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertTrue(actualStandaloneInMemProcessEngineConfiguration.getMailServers().isEmpty());
    assertTrue(actualStandaloneInMemProcessEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration
            .getWsOverridenEndpointAddresses()
            .isEmpty());
    assertTrue(actualStandaloneInMemProcessEngineConfiguration.isDbHistoryUsed());
    assertTrue(actualStandaloneInMemProcessEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(actualStandaloneInMemProcessEngineConfiguration.isBulkInsertEnabled());
    assertTrue(actualStandaloneInMemProcessEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(actualStandaloneInMemProcessEngineConfiguration.isEnableEventDispatcher());
    assertTrue(
        actualStandaloneInMemProcessEngineConfiguration
            .isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(actualStandaloneInMemProcessEngineConfiguration.isUsingRelationalDatabase());
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_GENERIC_MAX_LENGTH_STRING,
        actualStandaloneInMemProcessEngineConfiguration.getMaxLengthString());
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_WS_SYNC_FACTORY,
        actualStandaloneInMemProcessEngineConfiguration.getWsSyncFactoryClassName());
    assertArrayEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }
}
