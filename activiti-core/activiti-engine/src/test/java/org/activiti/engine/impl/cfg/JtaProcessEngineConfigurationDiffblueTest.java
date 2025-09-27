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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.transaction.TransactionManager;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.jta.JtaTransactionContextFactory;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.JtaTransactionInterceptor;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JtaProcessEngineConfigurationDiffblueTest {
  /**
   * Test new {@link JtaProcessEngineConfiguration} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * JtaProcessEngineConfiguration}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JtaProcessEngineConfiguration.<init>()"})
  public void testNewJtaProcessEngineConfiguration() throws IOException {
    // Arrange and Act
    JtaProcessEngineConfiguration actualJtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Assert
    Collection<? extends Deployer> defaultDeployers =
        actualJtaProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(
        actualJtaProcessEngineConfiguration.getDynamicBpmnService()
            instanceof DynamicBpmnServiceImpl);
    assertTrue(
        actualJtaProcessEngineConfiguration.getHistoryService() instanceof HistoryServiceImpl);
    assertTrue(
        actualJtaProcessEngineConfiguration.getManagementService()
            instanceof ManagementServiceImpl);
    assertTrue(
        actualJtaProcessEngineConfiguration.getRepositoryService()
            instanceof RepositoryServiceImpl);
    assertTrue(
        actualJtaProcessEngineConfiguration.getRuntimeService() instanceof RuntimeServiceImpl);
    assertTrue(actualJtaProcessEngineConfiguration.getTaskService() instanceof TaskServiceImpl);
    assertTrue(
        actualJtaProcessEngineConfiguration.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        actualJtaProcessEngineConfiguration.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertEquals("", actualJtaProcessEngineConfiguration.getDatabaseCatalog());
    assertEquals("", actualJtaProcessEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", actualJtaProcessEngineConfiguration.getJdbcPassword());
    assertEquals("@class", actualJtaProcessEngineConfiguration.getJavaClassFieldForJackson());
    assertEquals("UTF-8", actualJtaProcessEngineConfiguration.getXmlEncoding());
    assertEquals(
        "activiti@localhost", actualJtaProcessEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", actualJtaProcessEngineConfiguration.getHistory());
    assertEquals("camelContext", actualJtaProcessEngineConfiguration.getDefaultCamelContext());
    assertEquals("default", actualJtaProcessEngineConfiguration.getProcessEngineName());
    assertEquals(
        "jdbc:h2:tcp://localhost/~/activiti", actualJtaProcessEngineConfiguration.getJdbcUrl());
    assertEquals("localhost", actualJtaProcessEngineConfiguration.getMailServerHost());
    assertEquals("org.h2.Driver", actualJtaProcessEngineConfiguration.getJdbcDriver());
    assertEquals("sa", actualJtaProcessEngineConfiguration.getJdbcUsername());
    assertNull(actualJtaProcessEngineConfiguration.getTransactionManager());
    assertNull(actualJtaProcessEngineConfiguration.getClassLoader());
    assertNull(actualJtaProcessEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(actualJtaProcessEngineConfiguration.getDataSourceJndiName());
    assertNull(actualJtaProcessEngineConfiguration.getDatabaseSchema());
    assertNull(actualJtaProcessEngineConfiguration.getDatabaseType());
    assertNull(actualJtaProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertNull(actualJtaProcessEngineConfiguration.getJdbcPingQuery());
    assertNull(actualJtaProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(actualJtaProcessEngineConfiguration.getMailServerPassword());
    assertNull(actualJtaProcessEngineConfiguration.getMailServerUsername());
    assertNull(actualJtaProcessEngineConfiguration.getMailSessionJndi());
    assertNull(actualJtaProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(actualJtaProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(actualJtaProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(actualJtaProcessEngineConfiguration.getCustomFunctionProviders());
    assertNull(actualJtaProcessEngineConfiguration.getAllConfigurators());
    assertNull(actualJtaProcessEngineConfiguration.getConfigurators());
    assertNull(actualJtaProcessEngineConfiguration.getEventListeners());
    assertNull(actualJtaProcessEngineConfiguration.getCustomEventHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getCommandInterceptors());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(actualJtaProcessEngineConfiguration.getCustomSessionFactories());
    assertNull(actualJtaProcessEngineConfiguration.getCustomJobHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPostDeployers());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPreDeployers());
    assertNull(actualJtaProcessEngineConfiguration.getDeployers());
    assertNull(actualJtaProcessEngineConfiguration.getResolverFactories());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPostVariableTypes());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPreVariableTypes());
    assertNull(actualJtaProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getSessionFactories());
    assertNull(actualJtaProcessEngineConfiguration.getBeans());
    assertNull(actualJtaProcessEngineConfiguration.getTypedEventListeners());
    assertNull(actualJtaProcessEngineConfiguration.getEventHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getJobHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getCustomMybatisMappers());
    assertNull(actualJtaProcessEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(actualJtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualJtaProcessEngineConfiguration.getDataSource());
    assertNull(actualJtaProcessEngineConfiguration.getIdGeneratorDataSource());
    assertNull(actualJtaProcessEngineConfiguration.getUserGroupManager());
    assertNull(actualJtaProcessEngineConfiguration.getEngineAgendaFactory());
    assertNull(actualJtaProcessEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(actualJtaProcessEngineConfiguration.getEventDispatcher());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionHelper());
    assertNull(actualJtaProcessEngineConfiguration.getAsyncExecutor());
    assertNull(actualJtaProcessEngineConfiguration.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(actualJtaProcessEngineConfiguration.getJobManager());
    assertNull(actualJtaProcessEngineConfiguration.getListenerNotificationHelper());
    assertNull(actualJtaProcessEngineConfiguration.getBpmnParser());
    assertNull(actualJtaProcessEngineConfiguration.getActivityBehaviorFactory());
    assertNull(actualJtaProcessEngineConfiguration.getListenerFactory());
    assertNull(actualJtaProcessEngineConfiguration.getBusinessCalendarManager());
    assertNull(actualJtaProcessEngineConfiguration.getBpmnParseFactory());
    assertNull(actualJtaProcessEngineConfiguration.getIdGenerator());
    assertNull(actualJtaProcessEngineConfiguration.getTransactionContextFactory());
    assertNull(actualJtaProcessEngineConfiguration.getDbSqlSessionFactory());
    assertNull(actualJtaProcessEngineConfiguration.getExpressionManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoryLevel());
    assertNull(actualJtaProcessEngineConfiguration.getHistoryManager());
    assertNull(actualJtaProcessEngineConfiguration.getDefaultCommandConfig());
    assertNull(actualJtaProcessEngineConfiguration.getSchemaCommandConfig());
    assertNull(actualJtaProcessEngineConfiguration.getCommandContextFactory());
    assertNull(actualJtaProcessEngineConfiguration.getCommandExecutor());
    assertNull(actualJtaProcessEngineConfiguration.getCommandInvoker());
    assertNull(actualJtaProcessEngineConfiguration.getDelegateInterceptor());
    assertNull(actualJtaProcessEngineConfiguration.getFailedJobCommandFactory());
    assertNull(actualJtaProcessEngineConfiguration.getKnowledgeBaseCache());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionCache());
    assertNull(actualJtaProcessEngineConfiguration.getDeploymentManager());
    assertNull(actualJtaProcessEngineConfiguration.processDefinitionInfoCache);
    assertNull(actualJtaProcessEngineConfiguration.getAttachmentEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getByteArrayEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getCommentEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getDeploymentEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getExecutionEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getJobEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getModelEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionInfoEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getPropertyEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getResourceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getTableDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getTaskEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getTimerJobEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getAttachmentDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getByteArrayDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getCommentDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getDeploymentDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getEventLogEntryDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getExecutionDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricDetailDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getIdentityLinkDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getJobDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getModelDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getPropertyDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getResourceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getSuspendedJobDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getTaskDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getTimerJobDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getVariableInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getScriptingEngines());
    assertNull(actualJtaProcessEngineConfiguration.getProcessInstanceHelper());
    assertNull(actualJtaProcessEngineConfiguration.getVariableTypes());
    assertNull(actualJtaProcessEngineConfiguration.getClock());
    assertNull(actualJtaProcessEngineConfiguration.getProcessValidator());
    assertNull(actualJtaProcessEngineConfiguration.getSqlSessionFactory());
    assertNull(actualJtaProcessEngineConfiguration.getTransactionFactory());
    assertEquals(-1, actualJtaProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(-1, actualJtaProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(-1, actualJtaProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(-1, actualJtaProcessEngineConfiguration.processDefinitionInfoCacheLimit);
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(
        0, actualJtaProcessEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(
        1, actualJtaProcessEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(
        1, actualJtaProcessEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, actualJtaProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(10, actualJtaProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(10, actualJtaProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(100, actualJtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, actualJtaProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(
        10000,
        actualJtaProcessEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        10000,
        actualJtaProcessEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(2, actualJtaProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(20000, actualJtaProcessEngineConfiguration.getExecutionQueryLimit());
    assertEquals(
        20000, actualJtaProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, actualJtaProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, actualJtaProcessEngineConfiguration.getTaskQueryLimit());
    assertEquals(25, actualJtaProcessEngineConfiguration.getMailServerPort());
    assertEquals(25, actualJtaProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, actualJtaProcessEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, actualJtaProcessEngineConfiguration.getIdBlockSize());
    assertEquals(3, actualJtaProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(3, actualJtaProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(30, actualJtaProcessEngineConfiguration.getDefaultBpmnParseHandlers().size());
    assertEquals(
        300000, actualJtaProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(
        300000, actualJtaProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(5000L, actualJtaProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(
        51, actualJtaProcessEngineConfiguration.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, actualJtaProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(
        60000, actualJtaProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(
        60L, actualJtaProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        70,
        actualJtaProcessEngineConfiguration.DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        actualJtaProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
    assertFalse(actualJtaProcessEngineConfiguration.getMailServerUseSSL());
    assertFalse(actualJtaProcessEngineConfiguration.getMailServerUseTLS());
    assertFalse(actualJtaProcessEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(actualJtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(actualJtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertFalse(actualJtaProcessEngineConfiguration.isJdbcPingEnabled());
    assertFalse(actualJtaProcessEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(actualJtaProcessEngineConfiguration.isJpaHandleTransaction());
    assertFalse(actualJtaProcessEngineConfiguration.isTablePrefixIsSchema());
    assertFalse(actualJtaProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(actualJtaProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(actualJtaProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(actualJtaProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertFalse(actualJtaProcessEngineConfiguration.isRollbackDeployment());
    assertFalse(actualJtaProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertTrue(actualJtaProcessEngineConfiguration.getMailServers().isEmpty());
    assertTrue(actualJtaProcessEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(actualJtaProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(actualJtaProcessEngineConfiguration.isDbHistoryUsed());
    assertTrue(actualJtaProcessEngineConfiguration.isTransactionsExternallyManaged());
    assertTrue(actualJtaProcessEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(actualJtaProcessEngineConfiguration.isBulkInsertEnabled());
    assertTrue(actualJtaProcessEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(actualJtaProcessEngineConfiguration.isEnableEventDispatcher());
    assertTrue(
        actualJtaProcessEngineConfiguration.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(actualJtaProcessEngineConfiguration.isUsingRelationalDatabase());
    assertEquals(
        Boolean.FALSE.toString(), actualJtaProcessEngineConfiguration.getDatabaseSchemaUpdate());
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_GENERIC_MAX_LENGTH_STRING,
        actualJtaProcessEngineConfiguration.getMaxLengthString());
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_WS_SYNC_FACTORY,
        actualJtaProcessEngineConfiguration.getWsSyncFactoryClassName());
    assertArrayEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link JtaProcessEngineConfiguration#createTransactionInterceptor()}.
   *
   * <ul>
   *   <li>Then return {@link JtaTransactionInterceptor}.
   * </ul>
   *
   * <p>Method under test: {@link JtaProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CommandInterceptor JtaProcessEngineConfiguration.createTransactionInterceptor()"
  })
  public void testCreateTransactionInterceptor_thenReturnJtaTransactionInterceptor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    CommandInterceptor actualCreateTransactionInterceptorResult =
        jtaProcessEngineConfiguration.createTransactionInterceptor();

    // Assert
    assertTrue(actualCreateTransactionInterceptorResult instanceof JtaTransactionInterceptor);
    assertNull(actualCreateTransactionInterceptorResult.getNext());
  }

  /**
   * Test {@link JtaProcessEngineConfiguration#createTransactionInterceptor()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link JtaProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CommandInterceptor JtaProcessEngineConfiguration.createTransactionInterceptor()"
  })
  public void testCreateTransactionInterceptor_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new JtaProcessEngineConfiguration().createTransactionInterceptor());
  }

  /**
   * Test {@link JtaProcessEngineConfiguration#initTransactionContextFactory()}.
   *
   * <p>Method under test: {@link JtaProcessEngineConfiguration#initTransactionContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JtaProcessEngineConfiguration.initTransactionContextFactory()"})
  public void testInitTransactionContextFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initTransactionContextFactory();

    // Assert
    assertTrue(
        jtaProcessEngineConfiguration.getTransactionContextFactory()
            instanceof JtaTransactionContextFactory);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JtaProcessEngineConfiguration#setTransactionManager(TransactionManager)}
   *   <li>{@link JtaProcessEngineConfiguration#getTransactionManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TransactionManager JtaProcessEngineConfiguration.getTransactionManager()",
    "void JtaProcessEngineConfiguration.setTransactionManager(TransactionManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    TransactionManager transactionManager = mock(TransactionManager.class);

    // Act
    jtaProcessEngineConfiguration.setTransactionManager(transactionManager);

    // Assert
    assertSame(transactionManager, jtaProcessEngineConfiguration.getTransactionManager());
  }
}
