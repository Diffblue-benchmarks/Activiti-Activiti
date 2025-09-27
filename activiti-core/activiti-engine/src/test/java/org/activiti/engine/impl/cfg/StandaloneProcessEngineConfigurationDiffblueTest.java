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

public class StandaloneProcessEngineConfigurationDiffblueTest {
  /**
   * Test {@link StandaloneProcessEngineConfiguration#createTransactionInterceptor()}.
   *
   * <p>Method under test: {@link
   * StandaloneProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CommandInterceptor StandaloneProcessEngineConfiguration.createTransactionInterceptor()"
  })
  public void testCreateTransactionInterceptor() {
    // Arrange, Act and Assert
    assertNull(new StandaloneProcessEngineConfiguration().createTransactionInterceptor());
  }

  /**
   * Test new {@link StandaloneProcessEngineConfiguration} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * StandaloneProcessEngineConfiguration}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StandaloneProcessEngineConfiguration.<init>()"})
  public void testNewStandaloneProcessEngineConfiguration() throws IOException {
    // Arrange and Act
    StandaloneProcessEngineConfiguration actualStandaloneProcessEngineConfiguration =
        new StandaloneProcessEngineConfiguration();

    // Assert
    Collection<? extends CommandInterceptor> defaultCommandInterceptors =
        actualStandaloneProcessEngineConfiguration.getDefaultCommandInterceptors();
    assertEquals(1, defaultCommandInterceptors.size());
    assertTrue(defaultCommandInterceptors instanceof List);
    Collection<? extends Deployer> defaultDeployers =
        actualStandaloneProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(
        actualStandaloneProcessEngineConfiguration.getDynamicBpmnService()
            instanceof DynamicBpmnServiceImpl);
    assertTrue(
        actualStandaloneProcessEngineConfiguration.getHistoryService()
            instanceof HistoryServiceImpl);
    assertTrue(
        actualStandaloneProcessEngineConfiguration.getManagementService()
            instanceof ManagementServiceImpl);
    assertTrue(
        actualStandaloneProcessEngineConfiguration.getRepositoryService()
            instanceof RepositoryServiceImpl);
    assertTrue(
        actualStandaloneProcessEngineConfiguration.getRuntimeService()
            instanceof RuntimeServiceImpl);
    assertTrue(
        actualStandaloneProcessEngineConfiguration.getTaskService() instanceof TaskServiceImpl);
    assertTrue(
        actualStandaloneProcessEngineConfiguration.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        actualStandaloneProcessEngineConfiguration.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertEquals("", actualStandaloneProcessEngineConfiguration.getDatabaseCatalog());
    assertEquals("", actualStandaloneProcessEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", actualStandaloneProcessEngineConfiguration.getJdbcPassword());
    assertEquals(
        "@class", actualStandaloneProcessEngineConfiguration.getJavaClassFieldForJackson());
    assertEquals("UTF-8", actualStandaloneProcessEngineConfiguration.getXmlEncoding());
    assertEquals(
        "activiti@localhost",
        actualStandaloneProcessEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", actualStandaloneProcessEngineConfiguration.getHistory());
    assertEquals(
        "camelContext", actualStandaloneProcessEngineConfiguration.getDefaultCamelContext());
    assertEquals("default", actualStandaloneProcessEngineConfiguration.getProcessEngineName());
    assertEquals(
        "jdbc:h2:tcp://localhost/~/activiti",
        actualStandaloneProcessEngineConfiguration.getJdbcUrl());
    assertEquals("localhost", actualStandaloneProcessEngineConfiguration.getMailServerHost());
    assertEquals("org.h2.Driver", actualStandaloneProcessEngineConfiguration.getJdbcDriver());
    assertEquals("sa", actualStandaloneProcessEngineConfiguration.getJdbcUsername());
    assertNull(actualStandaloneProcessEngineConfiguration.getClassLoader());
    assertNull(actualStandaloneProcessEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getDataSourceJndiName());
    assertNull(actualStandaloneProcessEngineConfiguration.getDatabaseSchema());
    assertNull(actualStandaloneProcessEngineConfiguration.getDatabaseType());
    assertNull(actualStandaloneProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertNull(actualStandaloneProcessEngineConfiguration.getJdbcPingQuery());
    assertNull(actualStandaloneProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(actualStandaloneProcessEngineConfiguration.getMailServerPassword());
    assertNull(actualStandaloneProcessEngineConfiguration.getMailServerUsername());
    assertNull(actualStandaloneProcessEngineConfiguration.getMailSessionJndi());
    assertNull(actualStandaloneProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(actualStandaloneProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomFunctionProviders());
    assertNull(actualStandaloneProcessEngineConfiguration.getAllConfigurators());
    assertNull(actualStandaloneProcessEngineConfiguration.getConfigurators());
    assertNull(actualStandaloneProcessEngineConfiguration.getEventListeners());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomEventHandlers());
    assertNull(actualStandaloneProcessEngineConfiguration.getCommandInterceptors());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomSessionFactories());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomJobHandlers());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomPostDeployers());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomPreDeployers());
    assertNull(actualStandaloneProcessEngineConfiguration.getDeployers());
    assertNull(actualStandaloneProcessEngineConfiguration.getResolverFactories());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomPostVariableTypes());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomPreVariableTypes());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(actualStandaloneProcessEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(actualStandaloneProcessEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(actualStandaloneProcessEngineConfiguration.getSessionFactories());
    assertNull(actualStandaloneProcessEngineConfiguration.getBeans());
    assertNull(actualStandaloneProcessEngineConfiguration.getTypedEventListeners());
    assertNull(actualStandaloneProcessEngineConfiguration.getEventHandlers());
    assertNull(actualStandaloneProcessEngineConfiguration.getJobHandlers());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomMybatisMappers());
    assertNull(actualStandaloneProcessEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(actualStandaloneProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualStandaloneProcessEngineConfiguration.getDataSource());
    assertNull(actualStandaloneProcessEngineConfiguration.getIdGeneratorDataSource());
    assertNull(actualStandaloneProcessEngineConfiguration.getUserGroupManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getEngineAgendaFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(actualStandaloneProcessEngineConfiguration.getEventDispatcher());
    assertNull(actualStandaloneProcessEngineConfiguration.getProcessDefinitionHelper());
    assertNull(actualStandaloneProcessEngineConfiguration.getAsyncExecutor());
    assertNull(
        actualStandaloneProcessEngineConfiguration.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getJobManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getListenerNotificationHelper());
    assertNull(actualStandaloneProcessEngineConfiguration.getBpmnParser());
    assertNull(actualStandaloneProcessEngineConfiguration.getActivityBehaviorFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getListenerFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getBusinessCalendarManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getBpmnParseFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getIdGenerator());
    assertNull(actualStandaloneProcessEngineConfiguration.getTransactionContextFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getDbSqlSessionFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getExpressionManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoryLevel());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoryManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getDefaultCommandConfig());
    assertNull(actualStandaloneProcessEngineConfiguration.getSchemaCommandConfig());
    assertNull(actualStandaloneProcessEngineConfiguration.getCommandContextFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getCommandExecutor());
    assertNull(actualStandaloneProcessEngineConfiguration.getCommandInvoker());
    assertNull(actualStandaloneProcessEngineConfiguration.getDelegateInterceptor());
    assertNull(actualStandaloneProcessEngineConfiguration.getFailedJobCommandFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getKnowledgeBaseCache());
    assertNull(actualStandaloneProcessEngineConfiguration.getProcessDefinitionCache());
    assertNull(actualStandaloneProcessEngineConfiguration.getDeploymentManager());
    assertNull(actualStandaloneProcessEngineConfiguration.processDefinitionInfoCache);
    assertNull(actualStandaloneProcessEngineConfiguration.getAttachmentEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getByteArrayEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getCommentEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getDeploymentEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getExecutionEntityManager());
    assertNull(
        actualStandaloneProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
    assertNull(
        actualStandaloneProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
    assertNull(
        actualStandaloneProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getJobEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getModelEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getProcessDefinitionInfoEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getPropertyEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getResourceEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getTableDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getTaskEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getTimerJobEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getAttachmentDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getByteArrayDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getCommentDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getDeploymentDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getEventLogEntryDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getExecutionDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoricDetailDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getIdentityLinkDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getJobDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getModelDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getPropertyDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getResourceDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getSuspendedJobDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getTaskDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getTimerJobDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getVariableInstanceDataManager());
    assertNull(actualStandaloneProcessEngineConfiguration.getScriptingEngines());
    assertNull(actualStandaloneProcessEngineConfiguration.getProcessInstanceHelper());
    assertNull(actualStandaloneProcessEngineConfiguration.getVariableTypes());
    assertNull(actualStandaloneProcessEngineConfiguration.getClock());
    assertNull(actualStandaloneProcessEngineConfiguration.getProcessValidator());
    assertNull(actualStandaloneProcessEngineConfiguration.getSqlSessionFactory());
    assertNull(actualStandaloneProcessEngineConfiguration.getTransactionFactory());
    assertEquals(-1, actualStandaloneProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(-1, actualStandaloneProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(-1, actualStandaloneProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(-1, actualStandaloneProcessEngineConfiguration.processDefinitionInfoCacheLimit);
    assertEquals(
        0, actualStandaloneProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, actualStandaloneProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(0, actualStandaloneProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(0, actualStandaloneProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, actualStandaloneProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(0, actualStandaloneProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(
        0,
        actualStandaloneProcessEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(
        1,
        actualStandaloneProcessEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(
        1, actualStandaloneProcessEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, actualStandaloneProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(10, actualStandaloneProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(10, actualStandaloneProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(
        100, actualStandaloneProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(
        100, actualStandaloneProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(
        10000,
        actualStandaloneProcessEngineConfiguration
            .getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        10000,
        actualStandaloneProcessEngineConfiguration
            .getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(2, actualStandaloneProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(20000, actualStandaloneProcessEngineConfiguration.getExecutionQueryLimit());
    assertEquals(
        20000, actualStandaloneProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, actualStandaloneProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, actualStandaloneProcessEngineConfiguration.getTaskQueryLimit());
    assertEquals(25, actualStandaloneProcessEngineConfiguration.getMailServerPort());
    assertEquals(25, actualStandaloneProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, actualStandaloneProcessEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, actualStandaloneProcessEngineConfiguration.getIdBlockSize());
    assertEquals(3, actualStandaloneProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(
        3, actualStandaloneProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(
        30, actualStandaloneProcessEngineConfiguration.getDefaultBpmnParseHandlers().size());
    assertEquals(
        300000,
        actualStandaloneProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(
        300000, actualStandaloneProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(
        5000L, actualStandaloneProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(
        51,
        actualStandaloneProcessEngineConfiguration
            .getMyBatisXmlConfigurationStream()
            .read(byteArray));
    assertEquals(60, actualStandaloneProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(
        60000,
        actualStandaloneProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(
        60L, actualStandaloneProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        70,
        actualStandaloneProcessEngineConfiguration
            .DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        actualStandaloneProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
    assertFalse(actualStandaloneProcessEngineConfiguration.getMailServerUseSSL());
    assertFalse(actualStandaloneProcessEngineConfiguration.getMailServerUseTLS());
    assertFalse(actualStandaloneProcessEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(actualStandaloneProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(actualStandaloneProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertFalse(actualStandaloneProcessEngineConfiguration.isJdbcPingEnabled());
    assertFalse(actualStandaloneProcessEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(actualStandaloneProcessEngineConfiguration.isJpaHandleTransaction());
    assertFalse(actualStandaloneProcessEngineConfiguration.isTablePrefixIsSchema());
    assertFalse(actualStandaloneProcessEngineConfiguration.isTransactionsExternallyManaged());
    assertFalse(actualStandaloneProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(actualStandaloneProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(actualStandaloneProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(actualStandaloneProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertFalse(actualStandaloneProcessEngineConfiguration.isRollbackDeployment());
    assertFalse(actualStandaloneProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertTrue(actualStandaloneProcessEngineConfiguration.getMailServers().isEmpty());
    assertTrue(actualStandaloneProcessEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(
        actualStandaloneProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(actualStandaloneProcessEngineConfiguration.isDbHistoryUsed());
    assertTrue(actualStandaloneProcessEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(actualStandaloneProcessEngineConfiguration.isBulkInsertEnabled());
    assertTrue(actualStandaloneProcessEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(actualStandaloneProcessEngineConfiguration.isEnableEventDispatcher());
    assertTrue(
        actualStandaloneProcessEngineConfiguration
            .isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(actualStandaloneProcessEngineConfiguration.isUsingRelationalDatabase());
    assertEquals(
        Boolean.FALSE.toString(),
        actualStandaloneProcessEngineConfiguration.getDatabaseSchemaUpdate());
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_GENERIC_MAX_LENGTH_STRING,
        actualStandaloneProcessEngineConfiguration.getMaxLengthString());
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_WS_SYNC_FACTORY,
        actualStandaloneProcessEngineConfiguration.getWsSyncFactoryClassName());
    assertArrayEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }
}
