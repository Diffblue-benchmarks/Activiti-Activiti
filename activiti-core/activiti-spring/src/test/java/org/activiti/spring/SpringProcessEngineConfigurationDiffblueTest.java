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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.cfg.TransactionPropagation;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.TransactionContextInterceptor;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.spring.autodeployment.DefaultAutoDeploymentStrategy;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(
    locations = {
      "/org/activiti/spring/test/transaction/SpringTransactionIntegrationTest-context.xml"
    })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpringProcessEngineConfigurationDiffblueTest {
  @Autowired private SpringProcessEngineConfiguration springProcessEngineConfiguration;

  /**
   * Test {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration()}.
   *
   * <p>Method under test: {@link
   * SpringProcessEngineConfiguration#SpringProcessEngineConfiguration()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringProcessEngineConfiguration.<init>()"})
  public void testNewSpringProcessEngineConfiguration() throws IOException {
    // Arrange and Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfiguration =
        new SpringProcessEngineConfiguration();

    // Assert
    Collection<? extends Deployer> defaultDeployers =
        actualSpringProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(
        actualSpringProcessEngineConfiguration.getDynamicBpmnService()
            instanceof DynamicBpmnServiceImpl);
    assertTrue(
        actualSpringProcessEngineConfiguration.getHistoryService() instanceof HistoryServiceImpl);
    assertTrue(
        actualSpringProcessEngineConfiguration.getManagementService()
            instanceof ManagementServiceImpl);
    assertTrue(
        actualSpringProcessEngineConfiguration.getRepositoryService()
            instanceof RepositoryServiceImpl);
    assertTrue(
        actualSpringProcessEngineConfiguration.getRuntimeService() instanceof RuntimeServiceImpl);
    assertTrue(actualSpringProcessEngineConfiguration.getTaskService() instanceof TaskServiceImpl);
    assertTrue(
        actualSpringProcessEngineConfiguration.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        actualSpringProcessEngineConfiguration.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertEquals("", actualSpringProcessEngineConfiguration.getDatabaseCatalog());
    assertEquals("", actualSpringProcessEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", actualSpringProcessEngineConfiguration.getJdbcPassword());
    assertEquals("@class", actualSpringProcessEngineConfiguration.getJavaClassFieldForJackson());
    assertEquals(
        "SpringAutoDeployment", actualSpringProcessEngineConfiguration.getDeploymentName());
    assertEquals("UTF-8", actualSpringProcessEngineConfiguration.getXmlEncoding());
    assertEquals(
        "activiti@localhost", actualSpringProcessEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", actualSpringProcessEngineConfiguration.getHistory());
    assertEquals("camelContext", actualSpringProcessEngineConfiguration.getDefaultCamelContext());
    assertEquals("default", actualSpringProcessEngineConfiguration.getProcessEngineName());
    assertEquals("default", actualSpringProcessEngineConfiguration.getDeploymentMode());
    assertEquals(
        "jdbc:h2:tcp://localhost/~/activiti", actualSpringProcessEngineConfiguration.getJdbcUrl());
    assertEquals("localhost", actualSpringProcessEngineConfiguration.getMailServerHost());
    assertEquals(
        "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        actualSpringProcessEngineConfiguration.getWsSyncFactoryClassName());
    assertEquals("org.h2.Driver", actualSpringProcessEngineConfiguration.getJdbcDriver());
    assertEquals("sa", actualSpringProcessEngineConfiguration.getJdbcUsername());
    assertNull(actualSpringProcessEngineConfiguration.getClassLoader());
    assertNull(actualSpringProcessEngineConfiguration.transactionSynchronizationAdapterOrder);
    assertNull(actualSpringProcessEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(actualSpringProcessEngineConfiguration.getDataSourceJndiName());
    assertNull(actualSpringProcessEngineConfiguration.getDatabaseSchema());
    assertNull(actualSpringProcessEngineConfiguration.getDatabaseType());
    assertNull(actualSpringProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertNull(actualSpringProcessEngineConfiguration.getJdbcPingQuery());
    assertNull(actualSpringProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(actualSpringProcessEngineConfiguration.getMailServerPassword());
    assertNull(actualSpringProcessEngineConfiguration.getMailServerUsername());
    assertNull(actualSpringProcessEngineConfiguration.getMailSessionJndi());
    assertNull(actualSpringProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(actualSpringProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(actualSpringProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(actualSpringProcessEngineConfiguration.getCustomFunctionProviders());
    assertNull(actualSpringProcessEngineConfiguration.getAllConfigurators());
    assertNull(actualSpringProcessEngineConfiguration.getConfigurators());
    assertNull(actualSpringProcessEngineConfiguration.getEventListeners());
    assertNull(actualSpringProcessEngineConfiguration.getCustomEventHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getCommandInterceptors());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(actualSpringProcessEngineConfiguration.getCustomSessionFactories());
    assertNull(actualSpringProcessEngineConfiguration.getCustomJobHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPostDeployers());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPreDeployers());
    assertNull(actualSpringProcessEngineConfiguration.getDeployers());
    assertNull(actualSpringProcessEngineConfiguration.getResolverFactories());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPostVariableTypes());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPreVariableTypes());
    assertNull(actualSpringProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getSessionFactories());
    assertNull(actualSpringProcessEngineConfiguration.getBeans());
    assertNull(actualSpringProcessEngineConfiguration.getTypedEventListeners());
    assertNull(actualSpringProcessEngineConfiguration.getEventHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getJobHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getCustomMybatisMappers());
    assertNull(actualSpringProcessEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(actualSpringProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualSpringProcessEngineConfiguration.getDataSource());
    assertNull(actualSpringProcessEngineConfiguration.getIdGeneratorDataSource());
    assertNull(actualSpringProcessEngineConfiguration.getUserGroupManager());
    assertNull(actualSpringProcessEngineConfiguration.getEngineAgendaFactory());
    assertNull(actualSpringProcessEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(actualSpringProcessEngineConfiguration.getEventDispatcher());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionHelper());
    assertNull(actualSpringProcessEngineConfiguration.getAsyncExecutor());
    assertNull(
        actualSpringProcessEngineConfiguration.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(actualSpringProcessEngineConfiguration.getJobManager());
    assertNull(actualSpringProcessEngineConfiguration.getListenerNotificationHelper());
    assertNull(actualSpringProcessEngineConfiguration.getBpmnParser());
    assertNull(actualSpringProcessEngineConfiguration.getActivityBehaviorFactory());
    assertNull(actualSpringProcessEngineConfiguration.getListenerFactory());
    assertNull(actualSpringProcessEngineConfiguration.getBusinessCalendarManager());
    assertNull(actualSpringProcessEngineConfiguration.getBpmnParseFactory());
    assertNull(actualSpringProcessEngineConfiguration.getIdGenerator());
    assertNull(actualSpringProcessEngineConfiguration.getTransactionContextFactory());
    assertNull(actualSpringProcessEngineConfiguration.getDbSqlSessionFactory());
    assertNull(actualSpringProcessEngineConfiguration.getExpressionManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoryLevel());
    assertNull(actualSpringProcessEngineConfiguration.getHistoryManager());
    assertNull(actualSpringProcessEngineConfiguration.getDefaultCommandConfig());
    assertNull(actualSpringProcessEngineConfiguration.getSchemaCommandConfig());
    assertNull(actualSpringProcessEngineConfiguration.getCommandContextFactory());
    assertNull(actualSpringProcessEngineConfiguration.getCommandExecutor());
    assertNull(actualSpringProcessEngineConfiguration.getCommandInvoker());
    assertNull(actualSpringProcessEngineConfiguration.getDelegateInterceptor());
    assertNull(actualSpringProcessEngineConfiguration.getFailedJobCommandFactory());
    assertNull(actualSpringProcessEngineConfiguration.getKnowledgeBaseCache());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionCache());
    assertNull(actualSpringProcessEngineConfiguration.getDeploymentManager());
    assertNull(actualSpringProcessEngineConfiguration.getAttachmentEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getByteArrayEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getCommentEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getDeploymentEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getExecutionEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getJobEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getModelEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionInfoEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getPropertyEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getResourceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getTableDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getTaskEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getTimerJobEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getAttachmentDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getByteArrayDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getCommentDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getDeploymentDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getEventLogEntryDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getExecutionDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricDetailDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getIdentityLinkDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getJobDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getModelDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getPropertyDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getResourceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getSuspendedJobDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getTaskDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getTimerJobDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getVariableInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getScriptingEngines());
    assertNull(actualSpringProcessEngineConfiguration.getProcessInstanceHelper());
    assertNull(actualSpringProcessEngineConfiguration.getVariableTypes());
    assertNull(actualSpringProcessEngineConfiguration.getClock());
    assertNull(actualSpringProcessEngineConfiguration.getProcessValidator());
    assertNull(actualSpringProcessEngineConfiguration.getSqlSessionFactory());
    assertNull(actualSpringProcessEngineConfiguration.getTransactionFactory());
    assertNull(actualSpringProcessEngineConfiguration.getApplicationContext());
    assertNull(actualSpringProcessEngineConfiguration.getTransactionManager());
    assertEquals(-1, actualSpringProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(-1, actualSpringProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(-1, actualSpringProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(
        0, actualSpringProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(
        0, actualSpringProcessEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(0, actualSpringProcessEngineConfiguration.getDeploymentResources().length);
    assertEquals(
        1, actualSpringProcessEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(
        1, actualSpringProcessEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, actualSpringProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(10, actualSpringProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(10, actualSpringProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(100, actualSpringProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, actualSpringProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(
        10000,
        actualSpringProcessEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        10000,
        actualSpringProcessEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(2, actualSpringProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(20000, actualSpringProcessEngineConfiguration.getExecutionQueryLimit());
    assertEquals(
        20000, actualSpringProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, actualSpringProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, actualSpringProcessEngineConfiguration.getTaskQueryLimit());
    assertEquals(25, actualSpringProcessEngineConfiguration.getMailServerPort());
    assertEquals(25, actualSpringProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, actualSpringProcessEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, actualSpringProcessEngineConfiguration.getIdBlockSize());
    assertEquals(3, actualSpringProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(
        3, actualSpringProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(30, actualSpringProcessEngineConfiguration.getDefaultBpmnParseHandlers().size());
    assertEquals(
        300000, actualSpringProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(
        300000, actualSpringProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(4000, actualSpringProcessEngineConfiguration.getMaxLengthString());
    assertEquals(
        5000L, actualSpringProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(
        51,
        actualSpringProcessEngineConfiguration.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, actualSpringProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(
        60000, actualSpringProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(
        60L, actualSpringProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        70,
        actualSpringProcessEngineConfiguration.DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        actualSpringProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
    assertFalse(actualSpringProcessEngineConfiguration.getMailServerUseSSL());
    assertFalse(actualSpringProcessEngineConfiguration.getMailServerUseTLS());
    assertFalse(actualSpringProcessEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(actualSpringProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(actualSpringProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertFalse(actualSpringProcessEngineConfiguration.isJdbcPingEnabled());
    assertFalse(actualSpringProcessEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(actualSpringProcessEngineConfiguration.isJpaHandleTransaction());
    assertFalse(actualSpringProcessEngineConfiguration.isTablePrefixIsSchema());
    assertFalse(actualSpringProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(actualSpringProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(actualSpringProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(actualSpringProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertFalse(actualSpringProcessEngineConfiguration.isRollbackDeployment());
    assertFalse(actualSpringProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertTrue(actualSpringProcessEngineConfiguration.getMailServers().isEmpty());
    assertTrue(actualSpringProcessEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(actualSpringProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(actualSpringProcessEngineConfiguration.isDbHistoryUsed());
    assertTrue(actualSpringProcessEngineConfiguration.isTransactionsExternallyManaged());
    assertTrue(actualSpringProcessEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(actualSpringProcessEngineConfiguration.isBulkInsertEnabled());
    assertTrue(actualSpringProcessEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(actualSpringProcessEngineConfiguration.isEnableEventDispatcher());
    assertTrue(
        actualSpringProcessEngineConfiguration
            .isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(actualSpringProcessEngineConfiguration.isUsingRelationalDatabase());
    assertEquals(
        Boolean.FALSE.toString(), actualSpringProcessEngineConfiguration.getDatabaseSchemaUpdate());
    assertArrayEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link
   * SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}.
   *
   * <ul>
   *   <li>Then return RollbackDeployment.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SpringProcessEngineConfiguration.<init>(ApplicationUpgradeContextService)"
  })
  public void testNewSpringProcessEngineConfiguration_thenReturnRollbackDeployment() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfiguration =
        new SpringProcessEngineConfiguration(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));

    // Assert
    Collection<? extends Deployer> defaultDeployers =
        actualSpringProcessEngineConfiguration.getDefaultDeployers();
    assertTrue(defaultDeployers instanceof List);
    assertEquals(1, defaultDeployers.size());
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfiguration.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    assertTrue(actualSpringProcessEngineConfiguration.isRollbackDeployment());
    assertSame(
        actualSpringProcessEngineConfiguration.getBpmnDeploymentHelper(),
        bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link
   * SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SpringProcessEngineConfiguration.<init>(ApplicationUpgradeContextService)"
  })
  public void testNewSpringProcessEngineConfiguration_thenThrowActivitiException() {
    // Arrange
    ApplicationUpgradeContextService applicationUpgradeContextService =
        mock(ApplicationUpgradeContextService.class);
    when(applicationUpgradeContextService.isRollbackDeployment())
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new SpringProcessEngineConfiguration(applicationUpgradeContextService));
    verify(applicationUpgradeContextService).isRollbackDeployment();
  }

  /**
   * Test {@link
   * SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return not RollbackDeployment.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SpringProcessEngineConfiguration.<init>(ApplicationUpgradeContextService)"
  })
  public void testNewSpringProcessEngineConfiguration_whenNull_thenReturnNotRollbackDeployment() {
    // Arrange and Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfiguration =
        new SpringProcessEngineConfiguration(null);

    // Assert
    Collection<? extends Deployer> defaultDeployers =
        actualSpringProcessEngineConfiguration.getDefaultDeployers();
    assertTrue(defaultDeployers instanceof List);
    assertEquals(1, defaultDeployers.size());
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfiguration.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    assertFalse(actualSpringProcessEngineConfiguration.isRollbackDeployment());
    assertSame(
        actualSpringProcessEngineConfiguration.getBpmnDeploymentHelper(),
        bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#initDefaultCommandConfig()}.
   *
   * <p>Method under test: {@link SpringProcessEngineConfiguration#initDefaultCommandConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringProcessEngineConfiguration.initDefaultCommandConfig()"})
  public void testInitDefaultCommandConfig() {
    // Arrange
    SpringProcessEngineConfiguration springProcessEngineConfiguration =
        new SpringProcessEngineConfiguration();

    // Act
    springProcessEngineConfiguration.initDefaultCommandConfig();

    // Assert
    CommandConfig defaultCommandConfig = springProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    assertTrue(defaultCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#initDefaultCommandConfig()}.
   *
   * <p>Method under test: {@link SpringProcessEngineConfiguration#initDefaultCommandConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringProcessEngineConfiguration.initDefaultCommandConfig()"})
  public void testInitDefaultCommandConfig2() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    SpringProcessEngineConfiguration springProcessEngineConfiguration =
        new SpringProcessEngineConfiguration(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    springProcessEngineConfiguration.setDefaultCommandConfig(new CommandConfig());

    // Act
    springProcessEngineConfiguration.initDefaultCommandConfig();

    // Assert that nothing has changed
    CommandConfig defaultCommandConfig = springProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    assertTrue(defaultCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#createTransactionInterceptor()}.
   *
   * <p>Method under test: {@link SpringProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CommandInterceptor SpringProcessEngineConfiguration.createTransactionInterceptor()"
  })
  public void testCreateTransactionInterceptor() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    SpringProcessEngineConfiguration springProcessEngineConfiguration =
        new SpringProcessEngineConfiguration(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    springProcessEngineConfiguration.setTransactionManager(new DataSourceTransactionManager());

    // Act
    CommandInterceptor actualCreateTransactionInterceptorResult =
        springProcessEngineConfiguration.createTransactionInterceptor();

    // Assert
    PlatformTransactionManager platformTransactionManager =
        ((SpringTransactionInterceptor) actualCreateTransactionInterceptorResult)
            .transactionManager;
    Collection<TransactionExecutionListener> transactionExecutionListeners =
        ((DataSourceTransactionManager) platformTransactionManager)
            .getTransactionExecutionListeners();
    assertTrue(transactionExecutionListeners instanceof List);
    assertTrue(actualCreateTransactionInterceptorResult instanceof SpringTransactionInterceptor);
    assertTrue(platformTransactionManager instanceof DataSourceTransactionManager);
    assertNull(((DataSourceTransactionManager) platformTransactionManager).getDataSource());
    assertNull(actualCreateTransactionInterceptorResult.getNext());
    assertEquals(
        -1, ((DataSourceTransactionManager) platformTransactionManager).getDefaultTimeout());
    assertEquals(
        0,
        ((DataSourceTransactionManager) platformTransactionManager)
            .getTransactionSynchronization());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isEnforceReadOnly());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isFailEarlyOnGlobalRollbackOnly());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager).isRollbackOnCommitFailure());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isValidateExistingTransaction());
    assertTrue(transactionExecutionListeners.isEmpty());
    assertTrue(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isGlobalRollbackOnParticipationFailure());
    assertTrue(
        ((DataSourceTransactionManager) platformTransactionManager).isNestedTransactionAllowed());
    assertSame(
        ((SpringTransactionInterceptor) actualCreateTransactionInterceptorResult)
            .transactionManager,
        springProcessEngineConfiguration.getTransactionManager());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#createTransactionInterceptor()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link SpringProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CommandInterceptor SpringProcessEngineConfiguration.createTransactionInterceptor()"
  })
  public void testCreateTransactionInterceptor_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new SpringProcessEngineConfiguration().createTransactionInterceptor());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}.
   *
   * <p>Method under test: {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringProcessEngineConfiguration.initTransactionContextFactory()"})
  public void testInitTransactionContextFactory() {
    // Arrange
    SpringProcessEngineConfiguration springProcessEngineConfiguration =
        new SpringProcessEngineConfiguration();

    // Act
    springProcessEngineConfiguration.initTransactionContextFactory();

    // Assert that nothing has changed
    assertNull(springProcessEngineConfiguration.getTransactionContextFactory());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}.
   *
   * <p>Method under test: {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringProcessEngineConfiguration.initTransactionContextFactory()"})
  public void testInitTransactionContextFactory2() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    SpringProcessEngineConfiguration springProcessEngineConfiguration =
        new SpringProcessEngineConfiguration(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    springProcessEngineConfiguration.setTransactionContextFactory(null);
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    springProcessEngineConfiguration.setTransactionManager(transactionManager);

    // Act
    springProcessEngineConfiguration.initTransactionContextFactory();

    // Assert
    Collection<? extends CommandInterceptor> defaultCommandInterceptors =
        springProcessEngineConfiguration.getDefaultCommandInterceptors();
    assertEquals(3, defaultCommandInterceptors.size());
    assertTrue(defaultCommandInterceptors instanceof List);
    CommandInterceptor getResult =
        ((List<? extends CommandInterceptor>) defaultCommandInterceptors).get(2);
    assertTrue(getResult instanceof TransactionContextInterceptor);
    TransactionContextFactory transactionContextFactory =
        springProcessEngineConfiguration.getTransactionContextFactory();
    assertTrue(transactionContextFactory instanceof SpringTransactionContextFactory);
    assertNull(
        ((SpringTransactionContextFactory) transactionContextFactory)
            .transactionSynchronizationAdapterOrder);
    assertNull(getResult.getNext());
    assertSame(
        transactionManager,
        ((SpringTransactionContextFactory) transactionContextFactory).transactionManager);
    assertSame(
        transactionContextFactory,
        ((TransactionContextInterceptor) getResult).getTransactionContextFactory());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#autoDeployResources(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringProcessEngineConfiguration#autoDeployResources(ProcessEngine)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringProcessEngineConfiguration.autoDeployResources(ProcessEngine)"})
  public void testAutoDeployResources_thenThrowActivitiException() {
    // Arrange
    SpringProcessEngineConfiguration springProcessEngineConfiguration =
        new SpringProcessEngineConfiguration();
    springProcessEngineConfiguration.setDeploymentMode("Deployment Mode");

    ProcessEngine processEngine = mock(ProcessEngine.class);
    when(processEngine.getRepositoryService())
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> springProcessEngineConfiguration.autoDeployResources(processEngine));
    verify(processEngine).getRepositoryService();
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#setDataSource(DataSource)}.
   *
   * <ul>
   *   <li>Then return DataSource TargetDataSource is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SpringProcessEngineConfiguration#setDataSource(DataSource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration SpringProcessEngineConfiguration.setDataSource(DataSource)"
  })
  public void testSetDataSource_thenReturnDataSourceTargetDataSourceIsNull() {
    // Arrange
    SpringProcessEngineConfiguration springProcessEngineConfiguration =
        new SpringProcessEngineConfiguration();
    TransactionAwareDataSourceProxy dataSource = new TransactionAwareDataSourceProxy();

    // Act
    ProcessEngineConfiguration actualSetDataSourceResult =
        springProcessEngineConfiguration.setDataSource(dataSource);

    // Assert
    Collection<? extends Deployer> defaultDeployers =
        ((SpringProcessEngineConfiguration) actualSetDataSourceResult).getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetDataSourceResult instanceof SpringProcessEngineConfiguration);
    DataSource dataSource2 = actualSetDataSourceResult.getDataSource();
    assertTrue(dataSource2 instanceof TransactionAwareDataSourceProxy);
    assertNull(((TransactionAwareDataSourceProxy) dataSource2).getTargetDataSource());
    assertSame(dataSource, springProcessEngineConfiguration.getDataSource());
    assertSame(dataSource, dataSource2);
    BpmnDeployer bpmnDeployer =
        ((SpringProcessEngineConfiguration) actualSetDataSourceResult).getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    assertSame(
        ((SpringProcessEngineConfiguration) actualSetDataSourceResult).getBpmnDeploymentHelper(),
        bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#setDataSource(DataSource)}.
   *
   * <ul>
   *   <li>When {@link DataSource}.
   *   <li>Then return DataSource LogWriter is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SpringProcessEngineConfiguration#setDataSource(DataSource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration SpringProcessEngineConfiguration.setDataSource(DataSource)"
  })
  public void testSetDataSource_whenDataSource_thenReturnDataSourceLogWriterIsNull()
      throws SQLException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);

    // Act
    ProcessEngineConfiguration actualSetDataSourceResult =
        new SpringProcessEngineConfiguration().setDataSource(dataSource);

    // Assert
    Collection<? extends Deployer> defaultDeployers =
        ((SpringProcessEngineConfiguration) actualSetDataSourceResult).getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetDataSourceResult instanceof SpringProcessEngineConfiguration);
    DataSource dataSource2 = actualSetDataSourceResult.getDataSource();
    assertTrue(dataSource2 instanceof TransactionAwareDataSourceProxy);
    assertNull(dataSource2.getLogWriter());
    assertEquals(0, dataSource2.getLoginTimeout());
    BpmnDeployer bpmnDeployer =
        ((SpringProcessEngineConfiguration) actualSetDataSourceResult).getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    assertSame(
        ((SpringProcessEngineConfiguration) actualSetDataSourceResult).getBpmnDeploymentHelper(),
        bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(dataSource, ((TransactionAwareDataSourceProxy) dataSource2).getTargetDataSource());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link SpringProcessEngineConfiguration#setApplicationContext(ApplicationContext)}
   *   <li>{@link SpringProcessEngineConfiguration#setDeploymentMode(String)}
   *   <li>{@link SpringProcessEngineConfiguration#setDeploymentName(String)}
   *   <li>{@link SpringProcessEngineConfiguration#setDeploymentResources(Resource[])}
   *   <li>{@link
   *       SpringProcessEngineConfiguration#setTransactionManager(PlatformTransactionManager)}
   *   <li>{@link
   *       SpringProcessEngineConfiguration#setTransactionSynchronizationAdapterOrder(Integer)}
   *   <li>{@link SpringProcessEngineConfiguration#getApplicationContext()}
   *   <li>{@link SpringProcessEngineConfiguration#getDeploymentMode()}
   *   <li>{@link SpringProcessEngineConfiguration#getDeploymentName()}
   *   <li>{@link SpringProcessEngineConfiguration#getDeploymentResources()}
   *   <li>{@link SpringProcessEngineConfiguration#getTransactionManager()}
   *   <li>{@link SpringProcessEngineConfiguration#getUserGroupManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ApplicationContext SpringProcessEngineConfiguration.getApplicationContext()",
    "String SpringProcessEngineConfiguration.getDeploymentMode()",
    "String SpringProcessEngineConfiguration.getDeploymentName()",
    "Resource[] SpringProcessEngineConfiguration.getDeploymentResources()",
    "PlatformTransactionManager SpringProcessEngineConfiguration.getTransactionManager()",
    "org.activiti.api.runtime.shared.identity.UserGroupManager SpringProcessEngineConfiguration.getUserGroupManager()",
    "void SpringProcessEngineConfiguration.setApplicationContext(ApplicationContext)",
    "void SpringProcessEngineConfiguration.setDeploymentMode(String)",
    "void SpringProcessEngineConfiguration.setDeploymentName(String)",
    "void SpringProcessEngineConfiguration.setDeploymentResources(Resource[])",
    "void SpringProcessEngineConfiguration.setTransactionManager(PlatformTransactionManager)",
    "void SpringProcessEngineConfiguration.setTransactionSynchronizationAdapterOrder(Integer)"
  })
  public void testGettersAndSetters() throws UnsupportedEncodingException, BeansException {
    // Arrange
    SpringProcessEngineConfiguration springProcessEngineConfiguration =
        new SpringProcessEngineConfiguration();
    ApplicationContext applicationContext = mock(ApplicationContext.class);

    // Act
    springProcessEngineConfiguration.setApplicationContext(applicationContext);
    springProcessEngineConfiguration.setDeploymentMode("Deployment Mode");
    springProcessEngineConfiguration.setDeploymentName("Deployment Name");
    Resource[] deploymentResources =
        new Resource[] {new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))};
    springProcessEngineConfiguration.setDeploymentResources(deploymentResources);
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    springProcessEngineConfiguration.setTransactionManager(transactionManager);
    springProcessEngineConfiguration.setTransactionSynchronizationAdapterOrder(1);
    ApplicationContext actualApplicationContext =
        springProcessEngineConfiguration.getApplicationContext();
    String actualDeploymentMode = springProcessEngineConfiguration.getDeploymentMode();
    String actualDeploymentName = springProcessEngineConfiguration.getDeploymentName();
    Resource[] actualDeploymentResources =
        springProcessEngineConfiguration.getDeploymentResources();
    PlatformTransactionManager actualTransactionManager =
        springProcessEngineConfiguration.getTransactionManager();

    // Assert
    assertEquals("Deployment Mode", actualDeploymentMode);
    assertEquals("Deployment Name", actualDeploymentName);
    assertNull(springProcessEngineConfiguration.getUserGroupManager());
    assertSame(transactionManager, actualTransactionManager);
    assertSame(deploymentResources, actualDeploymentResources);
    assertSame(applicationContext, actualApplicationContext);
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#getAutoDeploymentStrategy(String)}.
   *
   * <ul>
   *   <li>When {@code default}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringProcessEngineConfiguration#getAutoDeploymentStrategy(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.spring.autodeployment.AutoDeploymentStrategy SpringProcessEngineConfiguration.getAutoDeploymentStrategy(String)"
  })
  public void testGetAutoDeploymentStrategy_whenDefault() {
    // Arrange, Act and Assert
    assertTrue(
        new SpringProcessEngineConfiguration().getAutoDeploymentStrategy("default")
            instanceof DefaultAutoDeploymentStrategy);
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#getAutoDeploymentStrategy(String)}.
   *
   * <ul>
   *   <li>When {@code Mode}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringProcessEngineConfiguration#getAutoDeploymentStrategy(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.spring.autodeployment.AutoDeploymentStrategy SpringProcessEngineConfiguration.getAutoDeploymentStrategy(String)"
  })
  public void testGetAutoDeploymentStrategy_whenMode() {
    // Arrange, Act and Assert
    assertTrue(
        new SpringProcessEngineConfiguration().getAutoDeploymentStrategy("Mode")
            instanceof DefaultAutoDeploymentStrategy);
  }
}
