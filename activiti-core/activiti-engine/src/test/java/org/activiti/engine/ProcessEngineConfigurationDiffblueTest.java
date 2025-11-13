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
package org.activiti.engine;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import groovy.lang.GroovyClassLoader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.runtime.Clock;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProcessEngineConfigurationDiffblueTest {
  /**
   * Test {@link ProcessEngineConfiguration#createStandaloneProcessEngineConfiguration()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#createStandaloneProcessEngineConfiguration()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()"
  })
  public void testCreateStandaloneProcessEngineConfiguration() throws IOException {
    // Arrange and Act
    ProcessEngineConfiguration actualCreateStandaloneProcessEngineConfigurationResult =
        ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();

    // Assert
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult
            instanceof StandaloneProcessEngineConfiguration);
    assertEquals(
        70,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getActivityBehaviorFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAllConfigurators());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getAsyncExecutor());
    assertEquals(
        300000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(
        2,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorCorePoolSize());
    assertEquals(
        10000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        0,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(
        10000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorLockOwner());
    assertEquals(
        1,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(
        10,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorMaxPoolSize());
    assertEquals(
        1,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(
        3,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorNumberOfRetries());
    assertEquals(
        60000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(
        3,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(
        60L,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        5000L,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorThreadKeepAliveTime());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorThreadPoolQueue());
    assertEquals(
        100,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorThreadPoolQueueSize());
    assertEquals(
        300000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(
        10, actualCreateStandaloneProcessEngineConfigurationResult.getAsyncFailedJobWaitTime());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAttachmentDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getAttachmentEntityManager());
    assertEquals(
        25,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getBatchSizeProcessInstances());
    assertEquals(
        25,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getBatchSizeTasks());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getBeans());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getBpmnDeployer());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getBpmnDeploymentHelper());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getBpmnParseFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getBpmnParser());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getBusinessCalendarManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getByteArrayDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getByteArrayEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCachingAndArtifactsManager());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getClassLoader());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getClock());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCommandContextFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCommandExecutor());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCommandInterceptors());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCommandInvoker());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCommentDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCommentEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getConfigurators());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomDefaultBpmnParseHandlers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomEventHandlers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomFunctionProviders());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomJobHandlers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomMybatisMappers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomMybatisXMLMappers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomPostCommandInterceptors());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomPostDeployers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomPostVariableTypes());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomPreCommandInterceptors());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomPreDeployers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomPreVariableTypes());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomScriptingEngineClasses());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getCustomSessionFactories());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getDataSource());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getDataSourceJndiName());
    assertEquals(
        ProcessEngineConfiguration.NO_TENANT_ID,
        actualCreateStandaloneProcessEngineConfigurationResult.getDatabaseCatalog());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getDatabaseSchema());
    assertEquals(
        Boolean.FALSE.toString(),
        actualCreateStandaloneProcessEngineConfigurationResult.getDatabaseSchemaUpdate());
    assertEquals(
        ProcessEngineConfiguration.NO_TENANT_ID,
        actualCreateStandaloneProcessEngineConfigurationResult.getDatabaseTablePrefix());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getDatabaseType());
    assertNull(
        actualCreateStandaloneProcessEngineConfigurationResult
            .getDatabaseWildcardEscapeCharacter());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDbSqlSessionFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDeadLetterJobDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDeadLetterJobEntityManager());
    assertEquals(
        30,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDefaultBpmnParseHandlers()
            .size());
    assertEquals(
        "camelContext",
        actualCreateStandaloneProcessEngineConfigurationResult.getDefaultCamelContext());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDefaultCommandConfig());
    Collection<? extends CommandInterceptor> defaultCommandInterceptors =
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDefaultCommandInterceptors();
    assertTrue(defaultCommandInterceptors instanceof List);
    assertEquals(1, defaultCommandInterceptors.size());
    Collection<? extends Deployer> defaultDeployers =
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDefaultDeployers();
    assertTrue(defaultDeployers instanceof List);
    assertEquals(1, defaultDeployers.size());
    assertEquals(
        10, actualCreateStandaloneProcessEngineConfigurationResult.getDefaultFailedJobWaitTime());
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDelegateExpressionFieldInjectionMode());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDelegateInterceptor());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDeployers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDeploymentDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDeploymentEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getDeploymentManager());
    assertTrue(
        ((StandaloneProcessEngineConfiguration)
                    actualCreateStandaloneProcessEngineConfigurationResult)
                .getDynamicBpmnService()
            instanceof DynamicBpmnServiceImpl);
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getEngineAgendaFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getEventDispatcher());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getEventHandlers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getEventListeners());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getEventLogEntryDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getEventLogEntryEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getEventSubscriptionDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getEventSubscriptionEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getExecutionDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getExecutionEntityManager());
    assertEquals(
        20000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getExecutionQueryLimit());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getExpressionManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getFailedJobCommandFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricActivityInstanceDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricActivityInstanceEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricDetailDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricDetailEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricIdentityLinkDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricIdentityLinkEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricProcessInstanceDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricProcessInstanceEntityManager());
    assertEquals(
        20000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricProcessInstancesQueryLimit());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricTaskInstanceDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricTaskInstanceEntityManager());
    assertEquals(
        20000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricTaskQueryLimit());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricVariableInstanceDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoricVariableInstanceEntityManager());
    assertEquals("audit", actualCreateStandaloneProcessEngineConfigurationResult.getHistory());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getHistoryLevel());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getHistoryManager());
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult.getHistoryService()
            instanceof HistoryServiceImpl);
    assertEquals(2500, actualCreateStandaloneProcessEngineConfigurationResult.getIdBlockSize());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getIdGenerator());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getIdGeneratorDataSource());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getIdGeneratorDataSourceJndiName());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getIdentityLinkDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getIdentityLinkEntityManager());
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertEquals(
        "@class",
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getJavaClassFieldForJackson());
    assertEquals(
        0,
        actualCreateStandaloneProcessEngineConfigurationResult
            .getJdbcDefaultTransactionIsolationLevel());
    assertEquals(
        "org.h2.Driver", actualCreateStandaloneProcessEngineConfigurationResult.getJdbcDriver());
    assertEquals(
        0, actualCreateStandaloneProcessEngineConfigurationResult.getJdbcMaxActiveConnections());
    assertEquals(
        0, actualCreateStandaloneProcessEngineConfigurationResult.getJdbcMaxCheckoutTime());
    assertEquals(
        0, actualCreateStandaloneProcessEngineConfigurationResult.getJdbcMaxIdleConnections());
    assertEquals(0, actualCreateStandaloneProcessEngineConfigurationResult.getJdbcMaxWaitTime());
    assertEquals(
        ProcessEngineConfiguration.NO_TENANT_ID,
        actualCreateStandaloneProcessEngineConfigurationResult.getJdbcPassword());
    assertEquals(
        0,
        actualCreateStandaloneProcessEngineConfigurationResult.getJdbcPingConnectionNotUsedFor());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getJdbcPingQuery());
    assertEquals(
        "jdbc:h2:tcp://localhost/~/activiti",
        actualCreateStandaloneProcessEngineConfigurationResult.getJdbcUrl());
    assertEquals("sa", actualCreateStandaloneProcessEngineConfigurationResult.getJdbcUsername());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getJobDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getJobEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getJobHandlers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getJobManager());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getJpaEntityManagerFactory());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getJpaPersistenceUnitName());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getKnowledgeBaseCache());
    assertEquals(
        -1,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getKnowledgeBaseCacheLimit());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getListenerFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getListenerNotificationHelper());
    assertEquals(
        60, actualCreateStandaloneProcessEngineConfigurationResult.getLockTimeAsyncJobWaitTime());
    assertEquals(
        "activiti@localhost",
        actualCreateStandaloneProcessEngineConfigurationResult.getMailServerDefaultFrom());
    assertEquals(
        "localhost", actualCreateStandaloneProcessEngineConfigurationResult.getMailServerHost());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getMailServerPassword());
    assertEquals(25, actualCreateStandaloneProcessEngineConfigurationResult.getMailServerPort());
    assertFalse(actualCreateStandaloneProcessEngineConfigurationResult.getMailServerUseSSL());
    assertFalse(actualCreateStandaloneProcessEngineConfigurationResult.getMailServerUseTLS());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getMailServerUsername());
    assertTrue(actualCreateStandaloneProcessEngineConfigurationResult.getMailServers().isEmpty());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getMailSessionJndi());
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult.getMailSessionsJndi().isEmpty());
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult.getManagementService()
            instanceof ManagementServiceImpl);
    assertEquals(
        4000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getMaxLengthString());
    assertEquals(
        -1,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getMaxLengthStringVariableType());
    assertEquals(
        100,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getMaxNrOfStatementsInBulkInsert());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getModelDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getModelEntityManager());
    byte[] byteArray = new byte[51];
    assertEquals(
        51,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getMyBatisXmlConfigurationStream()
            .read(byteArray));
    assertArrayEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getPostBpmnParseHandlers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getPreBpmnParseHandlers());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getProcessDefinitionCache());
    assertEquals(
        -1,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getProcessDefinitionCacheLimit());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getProcessDefinitionDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getProcessDefinitionEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getProcessDefinitionHelper());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getProcessDefinitionInfoDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getProcessDefinitionInfoEntityManager());
    assertNull(
        actualCreateStandaloneProcessEngineConfigurationResult.getProcessEngineLifecycleListener());
    assertEquals(
        ProcessEngines.NAME_DEFAULT,
        actualCreateStandaloneProcessEngineConfigurationResult.getProcessEngineName());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getProcessInstanceHelper());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getProcessValidator());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getPropertyDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getPropertyEntityManager());
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult.getRepositoryService()
            instanceof RepositoryServiceImpl);
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getResolverFactories());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getResourceDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getResourceEntityManager());
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult.getRuntimeService()
            instanceof RuntimeServiceImpl);
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getSchemaCommandConfig());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getScriptingEngines());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getSessionFactories());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getSqlSessionFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getSuspendedJobDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getSuspendedJobEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getTableDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getTaskDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getTaskEntityManager());
    assertEquals(
        20000,
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getTaskQueryLimit());
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult.getTaskService()
            instanceof TaskServiceImpl);
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getTimerJobDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getTimerJobEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getTransactionContextFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getTransactionFactory());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getTypedEventListeners());
    assertNull(actualCreateStandaloneProcessEngineConfigurationResult.getUserGroupManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getVariableInstanceDataManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getVariableInstanceEntityManager());
    assertNull(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getVariableTypes());
    assertTrue(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getWsOverridenEndpointAddresses()
            .isEmpty());
    assertEquals(
        "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .getWsSyncFactoryClassName());
    assertEquals("UTF-8", actualCreateStandaloneProcessEngineConfigurationResult.getXmlEncoding());
    assertFalse(actualCreateStandaloneProcessEngineConfigurationResult.isAsyncExecutorActivate());
    assertFalse(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isAsyncExecutorIsMessageQueueMode());
    assertTrue(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isBulkInsertEnabled());
    assertFalse(
        actualCreateStandaloneProcessEngineConfigurationResult.isCopyVariablesToLocalForTasks());
    assertTrue(actualCreateStandaloneProcessEngineConfigurationResult.isDbHistoryUsed());
    assertTrue(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isEnableConfiguratorServiceLoader());
    assertFalse(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isEnableDatabaseEventLogging());
    assertTrue(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isEnableEventDispatcher());
    assertFalse(
        actualCreateStandaloneProcessEngineConfigurationResult
            .isEnableProcessDefinitionInfoCache());
    assertFalse(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isEnableSafeBpmnXml());
    assertFalse(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isEnableVerboseExecutionTreeLogging());
    assertFalse(actualCreateStandaloneProcessEngineConfigurationResult.isJdbcPingEnabled());
    assertFalse(actualCreateStandaloneProcessEngineConfigurationResult.isJpaCloseEntityManager());
    assertFalse(actualCreateStandaloneProcessEngineConfigurationResult.isJpaHandleTransaction());
    assertFalse(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isRollbackDeployment());
    assertTrue(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isSerializableVariableTypeTrackDeserializedObjects());
    assertFalse(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isSerializePOJOsInVariablesToJson());
    assertFalse(actualCreateStandaloneProcessEngineConfigurationResult.isTablePrefixIsSchema());
    assertFalse(
        actualCreateStandaloneProcessEngineConfigurationResult.isTransactionsExternallyManaged());
    assertTrue(
        actualCreateStandaloneProcessEngineConfigurationResult.isUseClassForNameClassLoading());
    assertTrue(
        ((StandaloneProcessEngineConfiguration)
                actualCreateStandaloneProcessEngineConfigurationResult)
            .isUsingRelationalDatabase());
  }

  /**
   * Test {@link ProcessEngineConfiguration#createStandaloneInMemProcessEngineConfiguration()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#createStandaloneInMemProcessEngineConfiguration()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()"
  })
  public void testCreateStandaloneInMemProcessEngineConfiguration() throws IOException {
    // Arrange and Act
    ProcessEngineConfiguration actualCreateStandaloneInMemProcessEngineConfigurationResult =
        ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();

    // Assert
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            instanceof StandaloneInMemProcessEngineConfiguration);
    assertEquals(
        70,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getActivityBehaviorFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAllConfigurators());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getAsyncExecutor());
    assertEquals(
        300000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(
        2,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorCorePoolSize());
    assertEquals(
        10000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        0,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(
        10000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorLockOwner());
    assertEquals(
        1,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(
        10,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorMaxPoolSize());
    assertEquals(
        1,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(
        3,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorNumberOfRetries());
    assertEquals(
        60000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(
        3,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(
        60L,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        5000L,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorThreadKeepAliveTime());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorThreadPoolQueue());
    assertEquals(
        100,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorThreadPoolQueueSize());
    assertEquals(
        300000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(
        10,
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getAsyncFailedJobWaitTime());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAttachmentDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getAttachmentEntityManager());
    assertEquals(
        25,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getBatchSizeProcessInstances());
    assertEquals(
        25,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getBatchSizeTasks());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getBeans());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getBpmnDeployer());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getBpmnDeploymentHelper());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getBpmnParseFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getBpmnParser());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getBusinessCalendarManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getByteArrayDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getByteArrayEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCachingAndArtifactsManager());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getClassLoader());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getClock());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCommandContextFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCommandExecutor());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCommandInterceptors());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCommandInvoker());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCommentDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCommentEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getConfigurators());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomDefaultBpmnParseHandlers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomEventHandlers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomFunctionProviders());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomJobHandlers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomMybatisMappers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomMybatisXMLMappers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomPostCommandInterceptors());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomPostDeployers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomPostVariableTypes());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomPreCommandInterceptors());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomPreDeployers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomPreVariableTypes());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomScriptingEngineClasses());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getCustomSessionFactories());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getDataSource());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getDataSourceJndiName());
    assertEquals(
        ProcessEngineConfiguration.NO_TENANT_ID,
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getDatabaseCatalog());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getDatabaseSchema());
    assertEquals(
        ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP,
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getDatabaseSchemaUpdate());
    assertEquals(
        ProcessEngineConfiguration.NO_TENANT_ID,
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getDatabaseTablePrefix());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getDatabaseType());
    assertNull(
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            .getDatabaseWildcardEscapeCharacter());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDbSqlSessionFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDeadLetterJobDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDeadLetterJobEntityManager());
    assertEquals(
        30,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDefaultBpmnParseHandlers()
            .size());
    assertEquals(
        "camelContext",
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getDefaultCamelContext());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDefaultCommandConfig());
    Collection<? extends CommandInterceptor> defaultCommandInterceptors =
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDefaultCommandInterceptors();
    assertTrue(defaultCommandInterceptors instanceof List);
    assertEquals(1, defaultCommandInterceptors.size());
    Collection<? extends Deployer> defaultDeployers =
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDefaultDeployers();
    assertTrue(defaultDeployers instanceof List);
    assertEquals(1, defaultDeployers.size());
    assertEquals(
        10,
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getDefaultFailedJobWaitTime());
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDelegateExpressionFieldInjectionMode());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDelegateInterceptor());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDeployers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDeploymentDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDeploymentEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getDeploymentManager());
    assertTrue(
        ((StandaloneInMemProcessEngineConfiguration)
                    actualCreateStandaloneInMemProcessEngineConfigurationResult)
                .getDynamicBpmnService()
            instanceof DynamicBpmnServiceImpl);
    assertNull(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getEngineAgendaFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getEventDispatcher());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getEventHandlers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getEventListeners());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getEventLogEntryDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getEventLogEntryEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getEventSubscriptionDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getEventSubscriptionEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getExecutionDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getExecutionEntityManager());
    assertEquals(
        20000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getExecutionQueryLimit());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getExpressionManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getFailedJobCommandFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricActivityInstanceDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricActivityInstanceEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricDetailDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricDetailEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricIdentityLinkDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricIdentityLinkEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricProcessInstanceDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricProcessInstanceEntityManager());
    assertEquals(
        20000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricProcessInstancesQueryLimit());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricTaskInstanceDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricTaskInstanceEntityManager());
    assertEquals(
        20000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricTaskQueryLimit());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricVariableInstanceDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoricVariableInstanceEntityManager());
    assertEquals("audit", actualCreateStandaloneInMemProcessEngineConfigurationResult.getHistory());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getHistoryLevel());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getHistoryManager());
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getHistoryService()
            instanceof HistoryServiceImpl);
    assertEquals(
        2500, actualCreateStandaloneInMemProcessEngineConfigurationResult.getIdBlockSize());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getIdGenerator());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getIdGeneratorDataSource());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getIdGeneratorDataSourceJndiName());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getIdentityLinkDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getIdentityLinkEntityManager());
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertEquals(
        "@class",
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getJavaClassFieldForJackson());
    assertEquals(
        0,
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            .getJdbcDefaultTransactionIsolationLevel());
    assertEquals(
        "org.h2.Driver",
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getJdbcDriver());
    assertEquals(
        0,
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getJdbcMaxActiveConnections());
    assertEquals(
        0, actualCreateStandaloneInMemProcessEngineConfigurationResult.getJdbcMaxCheckoutTime());
    assertEquals(
        0, actualCreateStandaloneInMemProcessEngineConfigurationResult.getJdbcMaxIdleConnections());
    assertEquals(
        0, actualCreateStandaloneInMemProcessEngineConfigurationResult.getJdbcMaxWaitTime());
    assertEquals(
        ProcessEngineConfiguration.NO_TENANT_ID,
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getJdbcPassword());
    assertEquals(
        0,
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            .getJdbcPingConnectionNotUsedFor());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getJdbcPingQuery());
    assertEquals(
        "jdbc:h2:mem:activiti",
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getJdbcUrl());
    assertEquals(
        "sa", actualCreateStandaloneInMemProcessEngineConfigurationResult.getJdbcUsername());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getJobDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getJobEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getJobHandlers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getJobManager());
    assertNull(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getJpaEntityManagerFactory());
    assertNull(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getJpaPersistenceUnitName());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getKnowledgeBaseCache());
    assertEquals(
        -1,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getKnowledgeBaseCacheLimit());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getListenerFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getListenerNotificationHelper());
    assertEquals(
        60,
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getLockTimeAsyncJobWaitTime());
    assertEquals(
        "activiti@localhost",
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getMailServerDefaultFrom());
    assertEquals(
        "localhost",
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getMailServerHost());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getMailServerPassword());
    assertEquals(
        25, actualCreateStandaloneInMemProcessEngineConfigurationResult.getMailServerPort());
    assertFalse(actualCreateStandaloneInMemProcessEngineConfigurationResult.getMailServerUseSSL());
    assertFalse(actualCreateStandaloneInMemProcessEngineConfigurationResult.getMailServerUseTLS());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getMailServerUsername());
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getMailServers().isEmpty());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getMailSessionJndi());
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            .getMailSessionsJndi()
            .isEmpty());
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getManagementService()
            instanceof ManagementServiceImpl);
    assertEquals(
        4000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getMaxLengthString());
    assertEquals(
        -1,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getMaxLengthStringVariableType());
    assertEquals(
        100,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getMaxNrOfStatementsInBulkInsert());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getModelDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getModelEntityManager());
    byte[] byteArray = new byte[51];
    assertEquals(
        51,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getMyBatisXmlConfigurationStream()
            .read(byteArray));
    assertArrayEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getPostBpmnParseHandlers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getPreBpmnParseHandlers());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getProcessDefinitionCache());
    assertEquals(
        -1,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getProcessDefinitionCacheLimit());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getProcessDefinitionDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getProcessDefinitionEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getProcessDefinitionHelper());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getProcessDefinitionInfoDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getProcessDefinitionInfoEntityManager());
    assertNull(
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            .getProcessEngineLifecycleListener());
    assertEquals(
        ProcessEngines.NAME_DEFAULT,
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getProcessEngineName());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getProcessInstanceHelper());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getProcessValidator());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getPropertyDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getPropertyEntityManager());
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getRepositoryService()
            instanceof RepositoryServiceImpl);
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getResolverFactories());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getResourceDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getResourceEntityManager());
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getRuntimeService()
            instanceof RuntimeServiceImpl);
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getSchemaCommandConfig());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getScriptingEngines());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getSessionFactories());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getSqlSessionFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getSuspendedJobDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getSuspendedJobEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getTableDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getTaskDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getTaskEntityManager());
    assertEquals(
        20000,
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getTaskQueryLimit());
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.getTaskService()
            instanceof TaskServiceImpl);
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getTimerJobDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getTimerJobEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getTransactionContextFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getTransactionFactory());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getTypedEventListeners());
    assertNull(actualCreateStandaloneInMemProcessEngineConfigurationResult.getUserGroupManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getVariableInstanceDataManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getVariableInstanceEntityManager());
    assertNull(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getVariableTypes());
    assertTrue(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getWsOverridenEndpointAddresses()
            .isEmpty());
    assertEquals(
        "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .getWsSyncFactoryClassName());
    assertEquals(
        "UTF-8", actualCreateStandaloneInMemProcessEngineConfigurationResult.getXmlEncoding());
    assertFalse(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.isAsyncExecutorActivate());
    assertFalse(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isAsyncExecutorIsMessageQueueMode());
    assertTrue(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isBulkInsertEnabled());
    assertFalse(
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            .isCopyVariablesToLocalForTasks());
    assertTrue(actualCreateStandaloneInMemProcessEngineConfigurationResult.isDbHistoryUsed());
    assertTrue(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isEnableConfiguratorServiceLoader());
    assertFalse(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isEnableDatabaseEventLogging());
    assertTrue(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isEnableEventDispatcher());
    assertFalse(
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            .isEnableProcessDefinitionInfoCache());
    assertFalse(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isEnableSafeBpmnXml());
    assertFalse(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isEnableVerboseExecutionTreeLogging());
    assertFalse(actualCreateStandaloneInMemProcessEngineConfigurationResult.isJdbcPingEnabled());
    assertFalse(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.isJpaCloseEntityManager());
    assertFalse(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.isJpaHandleTransaction());
    assertFalse(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isRollbackDeployment());
    assertTrue(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isSerializableVariableTypeTrackDeserializedObjects());
    assertFalse(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isSerializePOJOsInVariablesToJson());
    assertFalse(
        actualCreateStandaloneInMemProcessEngineConfigurationResult.isTablePrefixIsSchema());
    assertFalse(
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            .isTransactionsExternallyManaged());
    assertTrue(
        actualCreateStandaloneInMemProcessEngineConfigurationResult
            .isUseClassForNameClassLoading());
    assertTrue(
        ((StandaloneInMemProcessEngineConfiguration)
                actualCreateStandaloneInMemProcessEngineConfigurationResult)
            .isUsingRelationalDatabase());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getProcessEngineName()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getProcessEngineName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getProcessEngineName()"})
  public void testGetProcessEngineName() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessEngines.NAME_DEFAULT, new JtaProcessEngineConfiguration().getProcessEngineName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setProcessEngineName(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setProcessEngineName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setProcessEngineName(String)"
  })
  public void testSetProcessEngineName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetProcessEngineNameResult =
        jtaProcessEngineConfiguration.setProcessEngineName("Process Engine Name");

    // Assert
    assertEquals("Process Engine Name", jtaProcessEngineConfiguration.getProcessEngineName());
    assertSame(jtaProcessEngineConfiguration, actualSetProcessEngineNameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getIdBlockSize()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getIdBlockSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getIdBlockSize()"})
  public void testGetIdBlockSize() {
    // Arrange, Act and Assert
    assertEquals(2500, new JtaProcessEngineConfiguration().getIdBlockSize());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setIdBlockSize(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setIdBlockSize(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setIdBlockSize(int)"})
  public void testSetIdBlockSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetIdBlockSizeResult =
        jtaProcessEngineConfiguration.setIdBlockSize(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getIdBlockSize());
    assertSame(jtaProcessEngineConfiguration, actualSetIdBlockSizeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getHistory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getHistory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getHistory()"})
  public void testGetHistory() {
    // Arrange, Act and Assert
    assertEquals("audit", new JtaProcessEngineConfiguration().getHistory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setHistory(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setHistory(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setHistory(String)"})
  public void testSetHistory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetHistoryResult =
        jtaProcessEngineConfiguration.setHistory("History");

    // Assert
    assertEquals("History", jtaProcessEngineConfiguration.getHistory());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerHost()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServerHost()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailServerHost()"})
  public void testGetMailServerHost() {
    // Arrange, Act and Assert
    assertEquals("localhost", new JtaProcessEngineConfiguration().getMailServerHost());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerHost(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailServerHost(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerHost(String)"
  })
  public void testSetMailServerHost() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerHostResult =
        jtaProcessEngineConfiguration.setMailServerHost("localhost");

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerHostResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUsername()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServerUsername()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailServerUsername()"})
  public void testGetMailServerUsername() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getMailServerUsername());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUsername(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailServerUsername(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerUsername(String)"
  })
  public void testSetMailServerUsername() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerUsernameResult =
        jtaProcessEngineConfiguration.setMailServerUsername("janedoe");

    // Assert
    assertEquals("janedoe", jtaProcessEngineConfiguration.getMailServerUsername());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUsernameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerPassword()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServerPassword()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailServerPassword()"})
  public void testGetMailServerPassword() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getMailServerPassword());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerPassword(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailServerPassword(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerPassword(String)"
  })
  public void testSetMailServerPassword() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerPasswordResult =
        jtaProcessEngineConfiguration.setMailServerPassword("iloveyou");

    // Assert
    assertEquals("iloveyou", jtaProcessEngineConfiguration.getMailServerPassword());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerPasswordResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionJndi()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailSessionJndi()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailSessionJndi()"})
  public void testGetMailSessionJndi() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getMailSessionJndi());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionJndi(String)} with {@code String}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailSessionJndi(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailSessionJndi(String)"})
  public void testGetMailSessionJndiWithString() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getMailSessionJndi("42"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailSessionJndi(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailSessionJndi(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setMailSessionJndi(String)"
  })
  public void testSetMailSessionJndi() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailSessionJndiResult =
        jtaProcessEngineConfiguration.setMailSessionJndi("Mail Session Jndi");

    // Assert
    assertEquals("Mail Session Jndi", jtaProcessEngineConfiguration.getMailSessionJndi());
    assertSame(jtaProcessEngineConfiguration, actualSetMailSessionJndiResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionsJndi()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailSessionsJndi()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfiguration.getMailSessionsJndi()"})
  public void testGetMailSessionsJndi() {
    // Arrange, Act and Assert
    assertTrue(new JtaProcessEngineConfiguration().getMailSessionsJndi().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setMailSessionsJndi(Map)"
  })
  public void testSetMailSessionsJndi() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailSessionsJndiResult =
        jtaProcessEngineConfiguration.setMailSessionsJndi(new HashMap<>());

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetMailSessionsJndiResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerPort()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServerPort()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getMailServerPort()"})
  public void testGetMailServerPort() {
    // Arrange, Act and Assert
    assertEquals(25, new JtaProcessEngineConfiguration().getMailServerPort());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerPort(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailServerPort(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerPort(int)"
  })
  public void testSetMailServerPort() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerPortResult =
        jtaProcessEngineConfiguration.setMailServerPort(8080);

    // Assert
    assertEquals(8080, jtaProcessEngineConfiguration.getMailServerPort());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerPortResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseSSL()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.getMailServerUseSSL()"})
  public void testGetMailServerUseSSL_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JtaProcessEngineConfiguration().getMailServerUseSSL());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseSSL()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.getMailServerUseSSL()"})
  public void testGetMailServerUseSSL_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMailServerUseSSL(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseSSL());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerUseSSL(boolean)"
  })
  public void testSetMailServerUseSSL() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerUseSSLResult =
        jtaProcessEngineConfiguration.setMailServerUseSSL(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseSSL());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseSSLResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseTLS()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.getMailServerUseTLS()"})
  public void testGetMailServerUseTLS_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JtaProcessEngineConfiguration().getMailServerUseTLS());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseTLS()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.getMailServerUseTLS()"})
  public void testGetMailServerUseTLS_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMailServerUseTLS(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseTLS());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerUseTLS(boolean)"
  })
  public void testSetMailServerUseTLS() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerUseTLSResult =
        jtaProcessEngineConfiguration.setMailServerUseTLS(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseTLS());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseTLSResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailServerDefaultFrom()"})
  public void testGetMailServerDefaultFrom() {
    // Arrange, Act and Assert
    assertEquals(
        "activiti@localhost", new JtaProcessEngineConfiguration().getMailServerDefaultFrom());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerDefaultFrom(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailServerDefaultFrom(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerDefaultFrom(String)"
  })
  public void testSetMailServerDefaultFrom() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerDefaultFromResult =
        jtaProcessEngineConfiguration.setMailServerDefaultFrom("jane.doe@example.org");

    // Assert
    assertEquals("jane.doe@example.org", jtaProcessEngineConfiguration.getMailServerDefaultFrom());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerDefaultFromResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServer(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.cfg.MailServerInfo ProcessEngineConfiguration.getMailServer(String)"
  })
  public void testGetMailServer() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getMailServer("42"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getMailServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfiguration.getMailServers()"})
  public void testGetMailServers() {
    // Arrange, Act and Assert
    assertTrue(new JtaProcessEngineConfiguration().getMailServers().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServers(Map)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setMailServers(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailServers(Map)"})
  public void testSetMailServers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServersResult =
        jtaProcessEngineConfiguration.setMailServers(new HashMap<>());

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetMailServersResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseType()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDatabaseType()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseType()"})
  public void testGetDatabaseType() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDatabaseType());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseType(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDatabaseType(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseType(String)"
  })
  public void testSetDatabaseType() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseTypeResult =
        jtaProcessEngineConfiguration.setDatabaseType("Database Type");

    // Assert
    assertEquals("Database Type", jtaProcessEngineConfiguration.getDatabaseType());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseTypeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseSchemaUpdate()"})
  public void testGetDatabaseSchemaUpdate() {
    // Arrange, Act and Assert
    assertEquals(
        Boolean.FALSE.toString(), new JtaProcessEngineConfiguration().getDatabaseSchemaUpdate());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseSchemaUpdate(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDatabaseSchemaUpdate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseSchemaUpdate(String)"
  })
  public void testSetDatabaseSchemaUpdate() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseSchemaUpdateResult =
        jtaProcessEngineConfiguration.setDatabaseSchemaUpdate("2020-03-01");

    // Assert
    assertEquals("2020-03-01", jtaProcessEngineConfiguration.getDatabaseSchemaUpdate());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseSchemaUpdateResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDataSource()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDataSource()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DataSource ProcessEngineConfiguration.getDataSource()"})
  public void testGetDataSource() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDataSource());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDataSource(DataSource)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDataSource(DataSource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDataSource(DataSource)"
  })
  public void testSetDataSource() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    DataSource dataSource = mock(DataSource.class);

    // Act
    ProcessEngineConfiguration actualSetDataSourceResult =
        jtaProcessEngineConfiguration.setDataSource(dataSource);

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetDataSourceResult);
    assertSame(dataSource, jtaProcessEngineConfiguration.getDataSource());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcDriver()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcDriver()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcDriver()"})
  public void testGetJdbcDriver() {
    // Arrange, Act and Assert
    assertEquals("org.h2.Driver", new JtaProcessEngineConfiguration().getJdbcDriver());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcDriver(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcDriver(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcDriver(String)"})
  public void testSetJdbcDriver() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcDriverResult =
        jtaProcessEngineConfiguration.setJdbcDriver("Jdbc Driver");

    // Assert
    assertEquals("Jdbc Driver", jtaProcessEngineConfiguration.getJdbcDriver());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcDriverResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcUrl()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcUrl()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcUrl()"})
  public void testGetJdbcUrl() {
    // Arrange, Act and Assert
    assertEquals(
        "jdbc:h2:tcp://localhost/~/activiti", new JtaProcessEngineConfiguration().getJdbcUrl());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcUrl(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcUrl(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcUrl(String)"})
  public void testSetJdbcUrl() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcUrlResult =
        jtaProcessEngineConfiguration.setJdbcUrl("https://example.org/example");

    // Assert
    assertEquals("https://example.org/example", jtaProcessEngineConfiguration.getJdbcUrl());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcUrlResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcUsername()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcUsername()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcUsername()"})
  public void testGetJdbcUsername() {
    // Arrange, Act and Assert
    assertEquals("sa", new JtaProcessEngineConfiguration().getJdbcUsername());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcUsername(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcUsername(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcUsername(String)"
  })
  public void testSetJdbcUsername() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcUsernameResult =
        jtaProcessEngineConfiguration.setJdbcUsername("janedoe");

    // Assert
    assertEquals("janedoe", jtaProcessEngineConfiguration.getJdbcUsername());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcUsernameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPassword()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcPassword()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcPassword()"})
  public void testGetJdbcPassword() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessEngineConfiguration.NO_TENANT_ID,
        new JtaProcessEngineConfiguration().getJdbcPassword());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPassword(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcPassword(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcPassword(String)"
  })
  public void testSetJdbcPassword() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPasswordResult =
        jtaProcessEngineConfiguration.setJdbcPassword("iloveyou");

    // Assert
    assertEquals("iloveyou", jtaProcessEngineConfiguration.getJdbcPassword());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPasswordResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isTransactionsExternallyManaged()"})
  public void testIsTransactionsExternallyManaged() {
    // Arrange, Act and Assert
    assertTrue(new JtaProcessEngineConfiguration().isTransactionsExternallyManaged());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setTransactionsExternallyManaged(boolean)"
  })
  public void testSetTransactionsExternallyManaged() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetTransactionsExternallyManagedResult =
        jtaProcessEngineConfiguration.setTransactionsExternallyManaged(true);

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetTransactionsExternallyManagedResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getHistoryLevel()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getHistoryLevel()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoryLevel ProcessEngineConfiguration.getHistoryLevel()"})
  public void testGetHistoryLevel() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoryLevel());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setHistoryLevel(HistoryLevel)"
  })
  public void testSetHistoryLevel() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetHistoryLevelResult =
        jtaProcessEngineConfiguration.setHistoryLevel(HistoryLevel.NONE);

    // Assert
    assertEquals(HistoryLevel.NONE, jtaProcessEngineConfiguration.getHistoryLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryLevelResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isDbHistoryUsed()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isDbHistoryUsed()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isDbHistoryUsed()"})
  public void testIsDbHistoryUsed() {
    // Arrange, Act and Assert
    assertTrue(new JtaProcessEngineConfiguration().isDbHistoryUsed());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDbHistoryUsed(boolean)"
  })
  public void testSetDbHistoryUsed() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDbHistoryUsedResult =
        jtaProcessEngineConfiguration.setDbHistoryUsed(true);

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetDbHistoryUsedResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcMaxActiveConnections()"})
  public void testGetJdbcMaxActiveConnections() {
    // Arrange, Act and Assert
    assertEquals(0, new JtaProcessEngineConfiguration().getJdbcMaxActiveConnections());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcMaxActiveConnections(int)"
  })
  public void testSetJdbcMaxActiveConnections() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxActiveConnectionsResult =
        jtaProcessEngineConfiguration.setJdbcMaxActiveConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxActiveConnectionsResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcMaxIdleConnections()"})
  public void testGetJdbcMaxIdleConnections() {
    // Arrange, Act and Assert
    assertEquals(0, new JtaProcessEngineConfiguration().getJdbcMaxIdleConnections());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcMaxIdleConnections(int)"
  })
  public void testSetJdbcMaxIdleConnections() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxIdleConnectionsResult =
        jtaProcessEngineConfiguration.setJdbcMaxIdleConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxIdleConnectionsResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcMaxCheckoutTime()"})
  public void testGetJdbcMaxCheckoutTime() {
    // Arrange, Act and Assert
    assertEquals(0, new JtaProcessEngineConfiguration().getJdbcMaxCheckoutTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcMaxCheckoutTime(int)"
  })
  public void testSetJdbcMaxCheckoutTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxCheckoutTimeResult =
        jtaProcessEngineConfiguration.setJdbcMaxCheckoutTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxCheckoutTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcMaxWaitTime()"})
  public void testGetJdbcMaxWaitTime() {
    // Arrange, Act and Assert
    assertEquals(0, new JtaProcessEngineConfiguration().getJdbcMaxWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcMaxWaitTime(int)"
  })
  public void testSetJdbcMaxWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxWaitTimeResult =
        jtaProcessEngineConfiguration.setJdbcMaxWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJdbcPingEnabled()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJdbcPingEnabled()"})
  public void testIsJdbcPingEnabled_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JtaProcessEngineConfiguration().isJdbcPingEnabled());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJdbcPingEnabled()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJdbcPingEnabled()"})
  public void testIsJdbcPingEnabled_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJdbcPingEnabled(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJdbcPingEnabled());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcPingEnabled(boolean)"
  })
  public void testSetJdbcPingEnabled() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPingEnabledResult =
        jtaProcessEngineConfiguration.setJdbcPingEnabled(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJdbcPingEnabled());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingEnabledResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPingQuery()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcPingQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcPingQuery()"})
  public void testGetJdbcPingQuery() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJdbcPingQuery());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingQuery(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcPingQuery(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcPingQuery(String)"
  })
  public void testSetJdbcPingQuery() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPingQueryResult =
        jtaProcessEngineConfiguration.setJdbcPingQuery("Jdbc Ping Query");

    // Assert
    assertEquals("Jdbc Ping Query", jtaProcessEngineConfiguration.getJdbcPingQuery());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingQueryResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor()"})
  public void testGetJdbcPingConnectionNotUsedFor() {
    // Arrange, Act and Assert
    assertEquals(0, new JtaProcessEngineConfiguration().getJdbcPingConnectionNotUsedFor());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcPingConnectionNotUsedFor(int)"
  })
  public void testSetJdbcPingConnectionNotUsedFor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPingConnectionNotUsedForResult =
        jtaProcessEngineConfiguration.setJdbcPingConnectionNotUsedFor(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingConnectionNotUsedForResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel()"})
  public void testGetJdbcDefaultTransactionIsolationLevel() {
    // Arrange, Act and Assert
    assertEquals(0, new JtaProcessEngineConfiguration().getJdbcDefaultTransactionIsolationLevel());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(int)"
  })
  public void testSetJdbcDefaultTransactionIsolationLevel() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcDefaultTransactionIsolationLevelResult =
        jtaProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcDefaultTransactionIsolationLevelResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isAsyncExecutorActivate()"})
  public void testIsAsyncExecutorActivate_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JtaProcessEngineConfiguration().isAsyncExecutorActivate());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isAsyncExecutorActivate()"})
  public void testIsAsyncExecutorActivate_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorActivate(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setAsyncExecutorActivate(boolean)"
  })
  public void testSetAsyncExecutorActivate() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorActivateResult =
        jtaProcessEngineConfiguration.setAsyncExecutorActivate(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorActivateResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getClassLoader()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getClassLoader()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ClassLoader ProcessEngineConfiguration.getClassLoader()"})
  public void testGetClassLoader() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getClassLoader());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setClassLoader(ClassLoader)"
  })
  public void testSetClassLoader() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    GroovyClassLoader classLoader = new GroovyClassLoader();

    // Act
    ProcessEngineConfiguration actualSetClassLoaderResult =
        jtaProcessEngineConfiguration.setClassLoader(classLoader);

    // Assert
    assertSame(classLoader, jtaProcessEngineConfiguration.getClassLoader());
    assertSame(jtaProcessEngineConfiguration, actualSetClassLoaderResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isUseClassForNameClassLoading()"})
  public void testIsUseClassForNameClassLoading() {
    // Arrange, Act and Assert
    assertTrue(new JtaProcessEngineConfiguration().isUseClassForNameClassLoading());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setUseClassForNameClassLoading(boolean)"
  })
  public void testSetUseClassForNameClassLoading() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetUseClassForNameClassLoadingResult =
        jtaProcessEngineConfiguration.setUseClassForNameClassLoading(true);

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetUseClassForNameClassLoadingResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ProcessEngineConfiguration.getJpaEntityManagerFactory()"})
  public void testGetJpaEntityManagerFactory() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJpaEntityManagerFactory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJpaEntityManagerFactory(Object)"
  })
  public void testSetJpaEntityManagerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    Object object = JSONObject.NULL;

    // Act
    ProcessEngineConfiguration actualSetJpaEntityManagerFactoryResult =
        jtaProcessEngineConfiguration.setJpaEntityManagerFactory(object);

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetJpaEntityManagerFactoryResult);
    assertSame(object, jtaProcessEngineConfiguration.getJpaEntityManagerFactory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaHandleTransaction()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJpaHandleTransaction()"})
  public void testIsJpaHandleTransaction_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JtaProcessEngineConfiguration().isJpaHandleTransaction());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaHandleTransaction()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJpaHandleTransaction()"})
  public void testIsJpaHandleTransaction_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJpaHandleTransaction(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaHandleTransaction());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJpaHandleTransaction(boolean)"
  })
  public void testSetJpaHandleTransaction() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJpaHandleTransactionResult =
        jtaProcessEngineConfiguration.setJpaHandleTransaction(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaHandleTransaction());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaHandleTransactionResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJpaCloseEntityManager()"})
  public void testIsJpaCloseEntityManager_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JtaProcessEngineConfiguration().isJpaCloseEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJpaCloseEntityManager()"})
  public void testIsJpaCloseEntityManager_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJpaCloseEntityManager(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJpaCloseEntityManager(boolean)"
  })
  public void testSetJpaCloseEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJpaCloseEntityManagerResult =
        jtaProcessEngineConfiguration.setJpaCloseEntityManager(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaCloseEntityManagerResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJpaPersistenceUnitName()"})
  public void testGetJpaPersistenceUnitName() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJpaPersistenceUnitName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaPersistenceUnitName(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setJpaPersistenceUnitName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setJpaPersistenceUnitName(String)"
  })
  public void testSetJpaPersistenceUnitName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJpaPersistenceUnitNameResult =
        jtaProcessEngineConfiguration.setJpaPersistenceUnitName("Jpa Persistence Unit Name");

    // Assert
    assertEquals(
        "Jpa Persistence Unit Name", jtaProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaPersistenceUnitNameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDataSourceJndiName()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDataSourceJndiName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDataSourceJndiName()"})
  public void testGetDataSourceJndiName() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDataSourceJndiName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDataSourceJndiName(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDataSourceJndiName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDataSourceJndiName(String)"
  })
  public void testSetDataSourceJndiName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDataSourceJndiNameResult =
        jtaProcessEngineConfiguration.setDataSourceJndiName("Data Source Jndi Name");

    // Assert
    assertEquals("Data Source Jndi Name", jtaProcessEngineConfiguration.getDataSourceJndiName());
    assertSame(jtaProcessEngineConfiguration, actualSetDataSourceJndiNameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDefaultCamelContext()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDefaultCamelContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDefaultCamelContext()"})
  public void testGetDefaultCamelContext() {
    // Arrange, Act and Assert
    assertEquals("camelContext", new JtaProcessEngineConfiguration().getDefaultCamelContext());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDefaultCamelContext(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDefaultCamelContext(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDefaultCamelContext(String)"
  })
  public void testSetDefaultCamelContext() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDefaultCamelContextResult =
        jtaProcessEngineConfiguration.setDefaultCamelContext("Default Camel Context");

    // Assert
    assertEquals("Default Camel Context", jtaProcessEngineConfiguration.getDefaultCamelContext());
    assertSame(jtaProcessEngineConfiguration, actualSetDefaultCamelContextResult);
  }

  /**
   * Test {@link
   * ProcessEngineConfiguration#setProcessEngineLifecycleListener(ProcessEngineLifecycleListener)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#setProcessEngineLifecycleListener(ProcessEngineLifecycleListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setProcessEngineLifecycleListener(ProcessEngineLifecycleListener)"
  })
  public void testSetProcessEngineLifecycleListener() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);

    // Act
    ProcessEngineConfiguration actualSetProcessEngineLifecycleListenerResult =
        jtaProcessEngineConfiguration.setProcessEngineLifecycleListener(
            processEngineLifecycleListener);

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetProcessEngineLifecycleListenerResult);
    assertSame(
        processEngineLifecycleListener,
        jtaProcessEngineConfiguration.getProcessEngineLifecycleListener());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineLifecycleListener ProcessEngineConfiguration.getProcessEngineLifecycleListener()"
  })
  public void testGetProcessEngineLifecycleListener() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessEngineLifecycleListener());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseTablePrefix()"})
  public void testGetDatabaseTablePrefix() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessEngineConfiguration.NO_TENANT_ID,
        new JtaProcessEngineConfiguration().getDatabaseTablePrefix());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseTablePrefix(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDatabaseTablePrefix(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseTablePrefix(String)"
  })
  public void testSetDatabaseTablePrefix() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseTablePrefixResult =
        jtaProcessEngineConfiguration.setDatabaseTablePrefix("Database Table Prefix");

    // Assert
    assertEquals("Database Table Prefix", jtaProcessEngineConfiguration.getDatabaseTablePrefix());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseTablePrefixResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setTablePrefixIsSchema(boolean)"
  })
  public void testSetTablePrefixIsSchema() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetTablePrefixIsSchemaResult =
        jtaProcessEngineConfiguration.setTablePrefixIsSchema(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
    assertSame(jtaProcessEngineConfiguration, actualSetTablePrefixIsSchemaResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isTablePrefixIsSchema()"})
  public void testIsTablePrefixIsSchema_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JtaProcessEngineConfiguration().isTablePrefixIsSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isTablePrefixIsSchema()"})
  public void testIsTablePrefixIsSchema_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTablePrefixIsSchema(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter()"})
  public void testGetDatabaseWildcardEscapeCharacter() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDatabaseWildcardEscapeCharacter());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseWildcardEscapeCharacter(String)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#setDatabaseWildcardEscapeCharacter(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(String)"
  })
  public void testSetDatabaseWildcardEscapeCharacter() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseWildcardEscapeCharacterResult =
        jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(
            "Database Wildcard Escape Character");

    // Assert
    assertEquals(
        "Database Wildcard Escape Character",
        jtaProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseWildcardEscapeCharacterResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseCatalog()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDatabaseCatalog()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseCatalog()"})
  public void testGetDatabaseCatalog() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessEngineConfiguration.NO_TENANT_ID,
        new JtaProcessEngineConfiguration().getDatabaseCatalog());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseCatalog(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDatabaseCatalog(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseCatalog(String)"
  })
  public void testSetDatabaseCatalog() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseCatalogResult =
        jtaProcessEngineConfiguration.setDatabaseCatalog("Database Catalog");

    // Assert
    assertEquals("Database Catalog", jtaProcessEngineConfiguration.getDatabaseCatalog());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseCatalogResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseSchema()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDatabaseSchema()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseSchema()"})
  public void testGetDatabaseSchema() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDatabaseSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseSchema(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDatabaseSchema(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseSchema(String)"
  })
  public void testSetDatabaseSchema() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseSchemaResult =
        jtaProcessEngineConfiguration.setDatabaseSchema("Database Schema");

    // Assert
    assertEquals("Database Schema", jtaProcessEngineConfiguration.getDatabaseSchema());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseSchemaResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getXmlEncoding()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getXmlEncoding()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfiguration.getXmlEncoding()"})
  public void testGetXmlEncoding() {
    // Arrange, Act and Assert
    assertEquals("UTF-8", new JtaProcessEngineConfiguration().getXmlEncoding());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setXmlEncoding(String)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setXmlEncoding(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setXmlEncoding(String)"
  })
  public void testSetXmlEncoding() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetXmlEncodingResult =
        jtaProcessEngineConfiguration.setXmlEncoding("UTF-8");

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetXmlEncodingResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getClock()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getClock()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Clock ProcessEngineConfiguration.getClock()"})
  public void testGetClock() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getClock());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setClock(Clock)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setClock(Clock)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setClock(Clock)"})
  public void testSetClock() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act
    ProcessEngineConfigurationImpl actualSetClockResult =
        jtaProcessEngineConfiguration.setClock(clock);

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualSetClockResult);
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ProcessEngineConfiguration.getAsyncExecutor()"})
  public void testGetAsyncExecutor() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAsyncExecutor());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setAsyncExecutor(AsyncExecutor)"
  })
  public void testSetAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorResult =
        jtaProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);

    // Assert
    assertSame(asyncExecutor, jtaProcessEngineConfiguration.getAsyncExecutor());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getLockTimeAsyncJobWaitTime()"})
  public void testGetLockTimeAsyncJobWaitTime() {
    // Arrange, Act and Assert
    assertEquals(60, new JtaProcessEngineConfiguration().getLockTimeAsyncJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setLockTimeAsyncJobWaitTime(int)"
  })
  public void testSetLockTimeAsyncJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetLockTimeAsyncJobWaitTimeResult =
        jtaProcessEngineConfiguration.setLockTimeAsyncJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetLockTimeAsyncJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getDefaultFailedJobWaitTime()"})
  public void testGetDefaultFailedJobWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10, new JtaProcessEngineConfiguration().getDefaultFailedJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setDefaultFailedJobWaitTime(int)"
  })
  public void testSetDefaultFailedJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDefaultFailedJobWaitTimeResult =
        jtaProcessEngineConfiguration.setDefaultFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetDefaultFailedJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfiguration.getAsyncFailedJobWaitTime()"})
  public void testGetAsyncFailedJobWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10, new JtaProcessEngineConfiguration().getAsyncFailedJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}.
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setAsyncFailedJobWaitTime(int)"
  })
  public void testSetAsyncFailedJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetAsyncFailedJobWaitTimeResult =
        jtaProcessEngineConfiguration.setAsyncFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncFailedJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isEnableProcessDefinitionInfoCache()"})
  public void testIsEnableProcessDefinitionInfoCache_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JtaProcessEngineConfiguration().isEnableProcessDefinitionInfoCache());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isEnableProcessDefinitionInfoCache()"})
  public void testIsEnableProcessDefinitionInfoCache_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableProcessDefinitionInfoCache(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setEnableProcessDefinitionInfoCache(boolean)"
  })
  public void testSetEnableProcessDefinitionInfoCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetEnableProcessDefinitionInfoCacheResult =
        jtaProcessEngineConfiguration.setEnableProcessDefinitionInfoCache(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableProcessDefinitionInfoCacheResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfiguration.setCopyVariablesToLocalForTasks(boolean)"
  })
  public void testSetCopyVariablesToLocalForTasks() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetCopyVariablesToLocalForTasksResult =
        jtaProcessEngineConfiguration.setCopyVariablesToLocalForTasks(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertSame(jtaProcessEngineConfiguration, actualSetCopyVariablesToLocalForTasksResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isCopyVariablesToLocalForTasks()"})
  public void testIsCopyVariablesToLocalForTasks_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JtaProcessEngineConfiguration().isCopyVariablesToLocalForTasks());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isCopyVariablesToLocalForTasks()"})
  public void testIsCopyVariablesToLocalForTasks_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCopyVariablesToLocalForTasks(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setEngineAgendaFactory(ActivitiEngineAgendaFactory)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfiguration#setEngineAgendaFactory(ActivitiEngineAgendaFactory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessEngineConfiguration.setEngineAgendaFactory(ActivitiEngineAgendaFactory)"
  })
  public void testSetEngineAgendaFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);

    // Act
    jtaProcessEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Assert
    assertSame(engineAgendaFactory, jtaProcessEngineConfiguration.getEngineAgendaFactory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getEngineAgendaFactory()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfiguration#getEngineAgendaFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEngineAgendaFactory ProcessEngineConfiguration.getEngineAgendaFactory()"
  })
  public void testGetEngineAgendaFactory_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEngineAgendaFactory());
  }
}
