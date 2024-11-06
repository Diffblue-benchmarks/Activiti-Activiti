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
package org.activiti.engine.impl.scripting;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.bpmn.parser.handler.BoundaryEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.BusinessRuleParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TransactionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.parse.BpmnParseHandler;
import org.junit.Test;

public class BeansResolverFactoryDiffblueTest {
  /**
   * Test
   * {@link BeansResolverFactory#createResolver(ProcessEngineConfigurationImpl, VariableScope)}.
   * <p>
   * Method under test:
   * {@link BeansResolverFactory#createResolver(ProcessEngineConfigurationImpl, VariableScope)}
   */
  @Test
  public void testCreateResolver() throws IOException {
    // Arrange
    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    Resolver actualCreateResolverResult = beansResolverFactory.createResolver(processEngineConfiguration,
        NoExecutionVariableScope.getSharedInstance());

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = beansResolverFactory.processEngineConfiguration;
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
    assertSame(beansResolverFactory, actualCreateResolverResult);
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link BeansResolverFactory#createResolver(ProcessEngineConfigurationImpl, VariableScope)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BeansResolverFactory#createResolver(ProcessEngineConfigurationImpl, VariableScope)}
   */
  @Test
  public void testCreateResolver_givenCustomFunctionProvider() throws IOException {
    // Arrange
    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    Resolver actualCreateResolverResult = beansResolverFactory.createResolver(processEngineConfiguration,
        NoExecutionVariableScope.getSharedInstance());

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = beansResolverFactory.processEngineConfiguration;
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
    assertSame(beansResolverFactory, actualCreateResolverResult);
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link BeansResolverFactory#containsKey(Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BeansResolverFactory#containsKey(Object)}
   */
  @Test
  public void testContainsKey_givenHashMapNullIsNull_thenReturnTrue() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    beans.put(JSONObject.NULL, JSONObject.NULL);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(beans);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    beansResolverFactory.createResolver(processEngineConfiguration, NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertTrue(beansResolverFactory.containsKey(JSONObject.NULL));
  }

  /**
   * Test {@link BeansResolverFactory#containsKey(Object)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Beans
   * is {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BeansResolverFactory#containsKey(Object)}
   */
  @Test
  public void testContainsKey_givenJtaProcessEngineConfigurationBeansIsHashMap_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(new HashMap<>());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    beansResolverFactory.createResolver(processEngineConfiguration, NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertFalse(beansResolverFactory.containsKey(JSONObject.NULL));
  }

  /**
   * Test {@link BeansResolverFactory#get(Object)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Beans
   * is {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BeansResolverFactory#get(Object)}
   */
  @Test
  public void testGet_givenJtaProcessEngineConfigurationBeansIsHashMap_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(new HashMap<>());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    beansResolverFactory.createResolver(processEngineConfiguration, NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertNull(beansResolverFactory.get(JSONObject.NULL));
  }

  /**
   * Test new {@link BeansResolverFactory} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link BeansResolverFactory}
   */
  @Test
  public void testNewBeansResolverFactory() {
    // Arrange, Act and Assert
    assertNull((new BeansResolverFactory()).processEngineConfiguration);
  }
}
