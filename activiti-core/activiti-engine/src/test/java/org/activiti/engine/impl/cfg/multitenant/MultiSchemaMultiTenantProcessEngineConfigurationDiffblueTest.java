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
package org.activiti.engine.impl.cfg.multitenant;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import groovy.lang.GroovyClassLoader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.asyncexecutor.AcquireAsyncJobsDueRunnable;
import org.activiti.engine.impl.asyncexecutor.AcquireTimerJobsRunnable;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.ResetExpiredJobsRunnable;
import org.activiti.engine.impl.asyncexecutor.multitenant.ExecutorPerTenantAsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.multitenant.TenantAwareAsyncExecutorFactory;
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.activiti.engine.test.profiler.ActivitiProfiler;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(MockitoJUnitRunner.class)
public class MultiSchemaMultiTenantProcessEngineConfigurationDiffblueTest {
  @InjectMocks
  private MultiSchemaMultiTenantProcessEngineConfiguration
      multiSchemaMultiTenantProcessEngineConfiguration;

  /**
   * Test {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#MultiSchemaMultiTenantProcessEngineConfiguration(TenantInfoHolder)}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#MultiSchemaMultiTenantProcessEngineConfiguration(TenantInfoHolder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiSchemaMultiTenantProcessEngineConfiguration.<init>(TenantInfoHolder)"
  })
  public void testNewMultiSchemaMultiTenantProcessEngineConfiguration() throws IOException {
    // Arrange and Act
    MultiSchemaMultiTenantProcessEngineConfiguration
        actualMultiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());

    // Assert
    Collection<? extends CommandInterceptor> defaultCommandInterceptors =
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandInterceptors();
    assertEquals(1, defaultCommandInterceptors.size());
    assertTrue(defaultCommandInterceptors instanceof List);
    Collection<? extends Deployer> defaultDeployers =
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDynamicBpmnService()
            instanceof DynamicBpmnServiceImpl);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoryService()
            instanceof HistoryServiceImpl);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getManagementService()
            instanceof ManagementServiceImpl);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getRepositoryService()
            instanceof RepositoryServiceImpl);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getRuntimeService()
            instanceof RuntimeServiceImpl);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getTaskService()
            instanceof TaskServiceImpl);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDataSource()
            instanceof TenantAwareDataSource);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdGenerator()
            instanceof StrongUuidGenerator);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.tenantInfoHolder
            instanceof DummyTenantInfoHolder);
    assertEquals("", actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseCatalog());
    assertEquals(
        "", actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcPassword());
    assertEquals(
        "@class",
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getJavaClassFieldForJackson());
    assertEquals("UTF-8", actualMultiSchemaMultiTenantProcessEngineConfiguration.getXmlEncoding());
    assertEquals(
        "activiti@localhost",
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistory());
    assertEquals(
        "camelContext",
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDefaultCamelContext());
    assertEquals(
        "default", actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessEngineName());
    assertEquals(
        "jdbc:h2:tcp://localhost/~/activiti",
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcUrl());
    assertEquals(
        "localhost", actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerHost());
    assertEquals(
        "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getWsSyncFactoryClassName());
    assertEquals(
        "org.h2.Driver", actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcDriver());
    assertEquals("sa", actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcUsername());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getClassLoader());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDataSourceJndiName());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseSchema());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseType());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getDatabaseWildcardEscapeCharacter());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcPingQuery());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerPassword());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerUsername());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailSessionJndi());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomFunctionProviders());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAllConfigurators());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getConfigurators());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventListeners());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomEventHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomSessionFactories());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomJobHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPostDeployers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPreDeployers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeployers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getResolverFactories());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPostVariableTypes());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPreVariableTypes());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getSessionFactories());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getBeans());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTypedEventListeners());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJobHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomMybatisMappers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdGeneratorDataSource());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getUserGroupManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEngineAgendaFactory());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventDispatcher());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionHelper());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJobManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getListenerNotificationHelper());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getBpmnParser());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getActivityBehaviorFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getListenerFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getBusinessCalendarManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getBpmnParseFactory());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getTransactionContextFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDbSqlSessionFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getExpressionManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoryLevel());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoryManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandConfig());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getSchemaCommandConfig());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommandContextFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommandInvoker());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDelegateInterceptor());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getFailedJobCommandFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getKnowledgeBaseCache());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionCache());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeploymentManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAttachmentEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getByteArrayEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommentEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeploymentEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getExecutionEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricActivityInstanceEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricIdentityLinkEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricProcessInstanceEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricTaskInstanceEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricVariableInstanceEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJobEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getModelEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getProcessDefinitionInfoEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getPropertyEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getResourceEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTableDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTaskEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTimerJobEntityManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAttachmentDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getByteArrayDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommentDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeploymentDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventLogEntryDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getExecutionDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricActivityInstanceDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricDetailDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricIdentityLinkDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricProcessInstanceDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricTaskInstanceDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricVariableInstanceDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdentityLinkDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJobDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getModelDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getProcessDefinitionInfoDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getPropertyDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getResourceDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getSuspendedJobDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTaskDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTimerJobDataManager());
    assertNull(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getVariableInstanceDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getScriptingEngines());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessInstanceHelper());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getVariableTypes());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getClock());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessValidator());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getSqlSessionFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTransactionFactory());
    assertEquals(
        -1, actualMultiSchemaMultiTenantProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(
        -1,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(
        -1,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(
        0,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getJdbcDefaultTransactionIsolationLevel());
    assertEquals(
        0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(
        0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(
        0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(
        0,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(
        0,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(
        1,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(
        1,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(
        10, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(
        10, actualMultiSchemaMultiTenantProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(
        10, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(
        100,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorThreadPoolQueueSize());
    assertEquals(
        100,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(
        10000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        10000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(
        2, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(
        20000, actualMultiSchemaMultiTenantProcessEngineConfiguration.getExecutionQueryLimit());
    assertEquals(
        20000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getHistoricProcessInstancesQueryLimit());
    assertEquals(
        20000, actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, actualMultiSchemaMultiTenantProcessEngineConfiguration.getTaskQueryLimit());
    assertEquals(25, actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerPort());
    assertEquals(
        25, actualMultiSchemaMultiTenantProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, actualMultiSchemaMultiTenantProcessEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdBlockSize());
    assertEquals(
        3,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(
        3,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(
        30,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getDefaultBpmnParseHandlers()
            .size());
    assertEquals(
        300000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(
        300000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(4000, actualMultiSchemaMultiTenantProcessEngineConfiguration.getMaxLengthString());
    assertEquals(
        5000L,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(
        51,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getMyBatisXmlConfigurationStream()
            .read(byteArray));
    assertEquals(
        60, actualMultiSchemaMultiTenantProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(
        60000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(
        60L,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        70,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getDelegateExpressionFieldInjectionMode());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerUseSSL());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerUseTLS());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .isEnableProcessDefinitionInfoCache());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isJdbcPingEnabled());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isJpaHandleTransaction());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isTablePrefixIsSchema());
    assertFalse(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.isTransactionsExternallyManaged());
    assertFalse(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .isEnableVerboseExecutionTreeLogging());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isRollbackDeployment());
    assertFalse(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.booted);
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServers().isEmpty());
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .getWsOverridenEndpointAddresses()
            .isEmpty());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isDbHistoryUsed());
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isBulkInsertEnabled());
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableEventDispatcher());
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration
            .isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isUsingRelationalDatabase());
    assertEquals(
        Boolean.FALSE.toString(),
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseSchemaUpdate());
    assertArrayEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#registerTenant(String,
   * DataSource)}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#registerTenant(String, DataSource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiSchemaMultiTenantProcessEngineConfiguration.registerTenant(String, DataSource)"
  })
  public void testRegisterTenant() {
    // Arrange
    DataSource dataSource = mock(DataSource.class);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.registerTenant("42", dataSource);

    // Assert
    DataSource dataSource2 = multiSchemaMultiTenantProcessEngineConfiguration.getDataSource();
    assertTrue(dataSource2 instanceof TenantAwareDataSource);
    Map<Object, DataSource> dataSources = ((TenantAwareDataSource) dataSource2).getDataSources();
    assertEquals(1, dataSources.size());
    assertSame(dataSource, dataSources.get("42"));
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor()"})
  public void testInitAsyncExecutor() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    TenantInfoHolder tenantInfoHolder =
        multiSchemaMultiTenantProcessEngineConfiguration.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    AsyncExecutor asyncExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof ExecutorPerTenantAsyncExecutor);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertTrue(allTenants.isEmpty());
    assertTrue(((ExecutorPerTenantAsyncExecutor) asyncExecutor).getTenantIds().isEmpty());
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor()"})
  public void testInitAsyncExecutor2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorLockOwner(null);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    TenantInfoHolder tenantInfoHolder =
        multiSchemaMultiTenantProcessEngineConfiguration.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    AsyncExecutor asyncExecutor2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor2 instanceof DefaultAsyncJobExecutor);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
    assertSame(asyncExecutor, asyncExecutor2);
    assertSame(
        multiSchemaMultiTenantProcessEngineConfiguration,
        asyncExecutor2.getProcessEngineConfiguration());
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor()"})
  public void testInitAsyncExecutor3() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(tenantInfoHolder);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    TenantInfoHolder tenantInfoHolder2 =
        multiSchemaMultiTenantProcessEngineConfiguration.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder2.getAllTenants();
    assertEquals(1, allTenants.size());
    assertTrue(allTenants instanceof Set);
    AsyncExecutor asyncExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof ExecutorPerTenantAsyncExecutor);
    assertTrue(tenantInfoHolder2 instanceof DummyTenantInfoHolder);
    Set<String> tenantIds = ((ExecutorPerTenantAsyncExecutor) asyncExecutor).getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.ProcessEngine MultiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine()"
  })
  public void testBuildProcessEngine() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");

    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setDataSource(dataSource);
    multiSchemaMultiTenantProcessEngineConfiguration.addConfigurator(
        ActivitiProfiler.getInstance());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> multiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.ProcessEngine MultiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine()"
  })
  public void testBuildProcessEngine2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");

    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setHistoryLevel(HistoryLevel.NONE);
    multiSchemaMultiTenantProcessEngineConfiguration.setDataSource(dataSource);
    multiSchemaMultiTenantProcessEngineConfiguration.addConfigurator(
        new ProcessExecutionLoggerConfigurator());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> multiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.ProcessEngine MultiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine()"
  })
  public void testBuildProcessEngine3() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");

    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setClassLoader(new GroovyClassLoader());
    multiSchemaMultiTenantProcessEngineConfiguration.setDataSource(dataSource);
    multiSchemaMultiTenantProcessEngineConfiguration.addConfigurator(
        new ProcessExecutionLoggerConfigurator());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> multiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.ProcessEngine MultiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine()"
  })
  public void testBuildProcessEngine4() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");

    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.addConfigurator(
        new ProcessExecutionLoggerConfigurator());
    multiSchemaMultiTenantProcessEngineConfiguration.setDataSource(dataSource);
    multiSchemaMultiTenantProcessEngineConfiguration.addConfigurator(
        ActivitiProfiler.getInstance());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> multiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.ProcessEngine MultiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine()"
  })
  public void testBuildProcessEngine_thenThrowActivitiException() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");

    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setDataSource(dataSource);
    multiSchemaMultiTenantProcessEngineConfiguration.addConfigurator(
        new ProcessExecutionLoggerConfigurator());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> multiSchemaMultiTenantProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#createTenantAsyncJobExecutor(String)}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#createTenantAsyncJobExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiSchemaMultiTenantProcessEngineConfiguration.createTenantAsyncJobExecutor(String)"
  })
  public void testCreateTenantAsyncJobExecutor() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));
    ExecutorPerTenantAsyncExecutor asyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.createTenantAsyncJobExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    AsyncExecutor asyncExecutor2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor2 instanceof ExecutorPerTenantAsyncExecutor);
    Set<String> tenantIds = ((ExecutorPerTenantAsyncExecutor) asyncExecutor2).getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#createTenantAsyncJobExecutor(String)}.
   *
   * <ul>
   *   <li>Then calls {@link DefaultAsyncJobExecutor#getProcessEngineConfiguration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#createTenantAsyncJobExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiSchemaMultiTenantProcessEngineConfiguration.createTenantAsyncJobExecutor(String)"
  })
  public void testCreateTenantAsyncJobExecutor_thenCallsGetProcessEngineConfiguration() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = mock(DefaultAsyncJobExecutor.class);
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    when(defaultAsyncJobExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);
    ExecutorPerTenantAsyncExecutor asyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.createTenantAsyncJobExecutor("42");

    // Assert
    verify(defaultAsyncJobExecutor).getProcessEngineConfiguration();
    verify(defaultAsyncJobExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(defaultAsyncJobExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(defaultAsyncJobExecutor).setProcessEngineConfiguration(isNull());
    verify(defaultAsyncJobExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(defaultAsyncJobExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    AsyncExecutor asyncExecutor2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor2 instanceof ExecutorPerTenantAsyncExecutor);
    Set<String> tenantIds = ((ExecutorPerTenantAsyncExecutor) asyncExecutor2).getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#createTenantAsyncJobExecutor(String)}.
   *
   * <ul>
   *   <li>Then calls {@link DefaultAsyncJobExecutor#getProcessEngineConfiguration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#createTenantAsyncJobExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiSchemaMultiTenantProcessEngineConfiguration.createTenantAsyncJobExecutor(String)"
  })
  public void testCreateTenantAsyncJobExecutor_thenCallsGetProcessEngineConfiguration2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = mock(DefaultAsyncJobExecutor.class);
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    when(defaultAsyncJobExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(defaultAsyncJobExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);
    ExecutorPerTenantAsyncExecutor asyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorActivate(true);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.createTenantAsyncJobExecutor("42");

    // Assert
    verify(defaultAsyncJobExecutor).getProcessEngineConfiguration();
    verify(defaultAsyncJobExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(defaultAsyncJobExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(defaultAsyncJobExecutor).setProcessEngineConfiguration(isNull());
    verify(defaultAsyncJobExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(defaultAsyncJobExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    AsyncExecutor asyncExecutor2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor2 instanceof ExecutorPerTenantAsyncExecutor);
    Set<String> tenantIds = ((ExecutorPerTenantAsyncExecutor) asyncExecutor2).getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link MultiSchemaMultiTenantProcessEngineConfiguration#createTransactionInterceptor()}.
   *
   * <p>Method under test: {@link
   * MultiSchemaMultiTenantProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CommandInterceptor MultiSchemaMultiTenantProcessEngineConfiguration.createTransactionInterceptor()"
  })
  public void testCreateTransactionInterceptor() {
    // Arrange, Act and Assert
    assertNull(
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder())
            .createTransactionInterceptor());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       MultiSchemaMultiTenantProcessEngineConfiguration#postProcessEngineInitialisation()}
   *   <li>{@link MultiSchemaMultiTenantProcessEngineConfiguration#getUserGroupManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.identity.UserGroupManager MultiSchemaMultiTenantProcessEngineConfiguration.getUserGroupManager()",
    "void MultiSchemaMultiTenantProcessEngineConfiguration.postProcessEngineInitialisation()"
  })
  public void testGettersAndSetters() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.postProcessEngineInitialisation();

    // Assert
    assertNull(multiSchemaMultiTenantProcessEngineConfiguration.getUserGroupManager());
  }
}
