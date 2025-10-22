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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.core.util.COWArrayList;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import groovy.lang.GroovyClassLoader;
import jakarta.transaction.TransactionManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import javax.script.ScriptEngineManager;
import javax.sql.DataSource;
import javax.xml.namespace.QName;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.ProcessDefinitionHelper;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgendaFactory;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.JobManager;
import org.activiti.engine.impl.asyncexecutor.multitenant.ExecutorPerTenantAsyncExecutor;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeploymentHelper;
import org.activiti.engine.impl.bpmn.deployer.CachingAndArtifactsManager;
import org.activiti.engine.impl.bpmn.deployer.EventSubscriptionManager;
import org.activiti.engine.impl.bpmn.deployer.ParsedDeploymentBuilderFactory;
import org.activiti.engine.impl.bpmn.deployer.TimerManager;
import org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
import org.activiti.engine.impl.bpmn.parser.factory.ListenerFactory;
import org.activiti.engine.impl.bpmn.parser.handler.AdhocSubProcessParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.BoundaryEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.BusinessRuleParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.CallActivityParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.CancelEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.CompensateEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.EndEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.EventSubProcessParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TaskParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TimerEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TransactionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.calendar.BusinessCalendarManager;
import org.activiti.engine.impl.calendar.MapBusinessCalendarManager;
import org.activiti.engine.impl.cfg.jta.JtaTransactionContextFactory;
import org.activiti.engine.impl.cfg.multitenant.TenantAwareDataSource;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.invocation.DefaultDelegateInterceptor;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.event.CompensationEventHandler;
import org.activiti.engine.impl.event.EventHandler;
import org.activiti.engine.impl.event.EventSubscriptionPayloadMappingProvider;
import org.activiti.engine.impl.event.MessageEventHandler;
import org.activiti.engine.impl.event.SignalEventHandler;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.history.HistoryManager;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextFactory;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.CommandInvoker;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.engine.impl.interceptor.JtaTransactionInterceptor;
import org.activiti.engine.impl.interceptor.LogInterceptor;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.interceptor.TransactionContextInterceptor;
import org.activiti.engine.impl.jobexecutor.AsyncContinuationJobHandler;
import org.activiti.engine.impl.jobexecutor.DefaultFailedJobCommandFactory;
import org.activiti.engine.impl.jobexecutor.FailedJobCommandFactory;
import org.activiti.engine.impl.jobexecutor.JobHandler;
import org.activiti.engine.impl.jobexecutor.ProcessEventJobHandler;
import org.activiti.engine.impl.jobexecutor.TimerActivateProcessDefinitionHandler;
import org.activiti.engine.impl.jobexecutor.TimerStartEventJobHandler;
import org.activiti.engine.impl.jobexecutor.TimerSuspendProcessDefinitionHandler;
import org.activiti.engine.impl.jobexecutor.TriggerTimerEventJobHandler;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.deploy.ProcessDefinitionCacheEntry;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityManager;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityManager;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.CommentEntityManager;
import org.activiti.engine.impl.persistence.entity.CommentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManager;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManager;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityManager;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityManager;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricDetailEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricDetailEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.JobEntityManager;
import org.activiti.engine.impl.persistence.entity.JobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ModelEntityManager;
import org.activiti.engine.impl.persistence.entity.ModelEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.PropertyEntityManager;
import org.activiti.engine.impl.persistence.entity.PropertyEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManager;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManager;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TableDataManager;
import org.activiti.engine.impl.persistence.entity.TableDataManagerImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManager;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.AttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.ByteArrayDataManager;
import org.activiti.engine.impl.persistence.entity.data.CommentDataManager;
import org.activiti.engine.impl.persistence.entity.data.DeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.DeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.EventLogEntryDataManager;
import org.activiti.engine.impl.persistence.entity.data.EventSubscriptionDataManager;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricActivityInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricDetailDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricTaskInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricVariableInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.JobDataManager;
import org.activiti.engine.impl.persistence.entity.data.ModelDataManager;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionInfoDataManager;
import org.activiti.engine.impl.persistence.entity.data.PropertyDataManager;
import org.activiti.engine.impl.persistence.entity.data.ResourceDataManager;
import org.activiti.engine.impl.persistence.entity.data.SuspendedJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.TaskDataManager;
import org.activiti.engine.impl.persistence.entity.data.TimerJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.VariableInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisByteArrayDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisCommentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisEventLogEntryDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisEventSubscriptionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricActivityInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricDetailDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricTaskInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricVariableInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisModelDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionInfoDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisPropertyDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisSuspendedJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTaskDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisVariableInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntity;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntityImpl;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManager;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.impl.scripting.BeansResolverFactory;
import org.activiti.engine.impl.scripting.ResolverFactory;
import org.activiti.engine.impl.scripting.ScriptingEngines;
import org.activiti.engine.impl.scripting.VariableScopeResolverFactory;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.ProcessInstanceHelper;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.DefaultVariableTypes;
import org.activiti.engine.impl.variable.HistoricJPAEntityListVariableType;
import org.activiti.engine.impl.variable.HistoricJPAEntityVariableType;
import org.activiti.engine.impl.variable.VariableType;
import org.activiti.engine.impl.variable.VariableTypes;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.engine.runtime.Clock;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.LoggingCommandInvoker;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.activiti.engine.test.profiler.ActivitiProfiler;
import org.activiti.engine.test.regression.ActivitiTestCaseProcessValidator;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorImpl;
import org.activiti.validation.validator.ValidatorSet;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ProcessEngineConfigurationImplDiffblueTest {
  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.ProcessEngine ProcessEngineConfigurationImpl.buildProcessEngine()"})
  public void testBuildProcessEngine() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.buildProcessEngine());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.ProcessEngine ProcessEngineConfigurationImpl.buildProcessEngine()"})
  public void testBuildProcessEngine2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.ProcessEngine ProcessEngineConfigurationImpl.buildProcessEngine()"})
  public void testBuildProcessEngine3() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.ProcessEngine ProcessEngineConfigurationImpl.buildProcessEngine()"})
  public void testBuildProcessEngine4() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMaxLengthStringVariableType(3);
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.ProcessEngine ProcessEngineConfigurationImpl.buildProcessEngine()"})
  public void testBuildProcessEngine5() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <ul>
   *   <li>Given {@link Connection} {@link Connection#getMetaData()} throw {@link SQLException#SQLException()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.ProcessEngine ProcessEngineConfigurationImpl.buildProcessEngine()"})
  public void testBuildProcessEngine_givenConnectionGetMetaDataThrowSQLException() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);
    Connection connection2 = mock(Connection.class);
    when(connection2.getMetaData()).thenThrow(new SQLException());
    doThrow(new SQLException()).when(connection2).close();
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection2);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.buildProcessEngine());
    verify(connection2).close();
    verify(connection).close();
    verify(connection2).getMetaData();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Clock is {@link DefaultClockImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.ProcessEngine ProcessEngineConfigurationImpl.buildProcessEngine()"})
  public void testBuildProcessEngine_givenJtaProcessEngineConfigurationClockIsDefaultClockImpl() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setClock(new DefaultClockImpl());
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) DatabaseType is {@code none}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.ProcessEngine ProcessEngineConfigurationImpl.buildProcessEngine()"})
  public void testBuildProcessEngine_givenJtaProcessEngineConfigurationDatabaseTypeIsNone() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDatabaseType("none");
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.buildProcessEngine());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) HistoryLevel is {@code NONE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.ProcessEngine ProcessEngineConfigurationImpl.buildProcessEngine()"})
  public void testBuildProcessEngine_givenJtaProcessEngineConfigurationHistoryLevelIsNone() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);
    Connection connection2 = mock(Connection.class);
    when(connection2.getMetaData()).thenThrow(new SQLException());
    doThrow(new SQLException()).when(connection2).close();
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection2);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setHistoryLevel(HistoryLevel.NONE);
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.buildProcessEngine());
    verify(connection2).close();
    verify(connection).close();
    verify(connection2).getMetaData();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#init()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.init()"})
  public void testInit() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.init());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#init()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) addConfigurator Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.init()"})
  public void testInit_givenJtaProcessEngineConfigurationAddConfiguratorInstance() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.addConfigurator(ActivitiProfiler.getInstance());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.init());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#init()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) CommandInterceptors is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.init()"})
  public void testInit_givenJtaProcessEngineConfigurationCommandInterceptorsIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.init());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#init()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) HistoryLevel is {@code NONE}.</li>
   *   <li>Then calls {@link Connection#close()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.init()"})
  public void testInit_givenJtaProcessEngineConfigurationHistoryLevelIsNone_thenCallsClose() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setHistoryLevel(HistoryLevel.NONE);
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.init());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#init()}.
   * <ul>
   *   <li>Then calls {@link Connection#close()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.init()"})
  public void testInit_thenCallsClose() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.init());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#init()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) CommandInvoker {@link CommandInvoker}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.init()"})
  public void testInit_thenJtaProcessEngineConfigurationCommandInvokerCommandInvoker() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    jtaProcessEngineConfiguration.init();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getCommandInvoker() instanceof CommandInvoker);
    SqlSessionFactory sqlSessionFactory = jtaProcessEngineConfiguration.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
    Configuration configuration = sqlSessionFactory.getConfiguration();
    Map<String, XNode> sqlFragments = configuration.getSqlFragments();
    assertEquals(94, sqlFragments.size());
    List<XNode> children = sqlFragments.get("selectDeploymentsByQueryCriteriaSql").getChildren();
    assertEquals(2, children.size());
    List<ProcessEngineConfigurator> allConfigurators = jtaProcessEngineConfiguration.getAllConfigurators();
    assertTrue(allConfigurators.isEmpty());
    assertTrue(sqlFragments
        .containsKey("org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl.executionVariab"
            + "leOperator"));
    assertTrue(sqlFragments.containsKey("selectExecutionsFromSql"));
    assertEquals(allConfigurators, children.get(0).getChildren());
    assertEquals(allConfigurators, configuration.getIncompleteCacheRefs());
    assertEquals(allConfigurators, configuration.getIncompleteMethods());
    assertEquals(allConfigurators, configuration.getIncompleteResultMaps());
    assertEquals(allConfigurators, configuration.getIncompleteStatements());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#init()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) CommandInvoker {@link LoggingCommandInvoker}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.init()"})
  public void testInit_thenJtaProcessEngineConfigurationCommandInvokerLoggingCommandInvoker() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.init();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getCommandInvoker() instanceof LoggingCommandInvoker);
    List<ProcessEngineConfigurator> allConfigurators = jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(1, allConfigurators.size());
    ProcessEngineConfigurator getResult = allConfigurators.get(0);
    assertTrue(getResult instanceof ProcessExecutionLoggerConfigurator);
    assertTrue(jtaProcessEngineConfiguration.getSqlSessionFactory() instanceof DefaultSqlSessionFactory);
    assertEquals(1, jtaProcessEngineConfiguration.getConfigurators().size());
    assertEquals(10000, getResult.getPriority());
    assertSame(configurator, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initFailedJobCommandFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initFailedJobCommandFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initFailedJobCommandFactory()"})
  public void testInitFailedJobCommandFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initFailedJobCommandFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getFailedJobCommandFactory() instanceof DefaultFailedJobCommandFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    jtaProcessEngineConfiguration.initCommandExecutors();

    // Assert
    CommandExecutor commandExecutor = jtaProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    List<CommandInterceptor> commandInterceptors = jtaProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(1);
    assertTrue(getResult instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult2 = commandInterceptors.get(0);
    assertTrue(getResult2 instanceof LogInterceptor);
    assertSame(getResult, getResult2.getNext());
    assertSame(getResult2, ((CommandExecutorImpl) commandExecutor).getFirst());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initCommandExecutors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    jtaProcessEngineConfiguration.setCommandExecutor(commandExecutor);
    jtaProcessEngineConfiguration.setSchemaCommandConfig(null);
    jtaProcessEngineConfiguration.setCommandInterceptors(mock(COWArrayList.class));
    jtaProcessEngineConfiguration.setCommandContextFactory(null);
    jtaProcessEngineConfiguration.setTransactionContextFactory(null);
    jtaProcessEngineConfiguration.setDefaultCommandConfig(null);
    jtaProcessEngineConfiguration.setCommandInvoker(null);
    jtaProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);
    jtaProcessEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());
    jtaProcessEngineConfiguration.setCustomPostCommandInterceptors(null);

    // Act
    jtaProcessEngineConfiguration.initCommandExecutors();

    // Assert
    CommandExecutor commandExecutor2 = jtaProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor2 instanceof CommandExecutorImpl);
    CommandInterceptor commandInvoker = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof CommandInvoker);
    assertNull(commandInvoker.getNext());
    CommandConfig schemaCommandConfig = jtaProcessEngineConfiguration.getSchemaCommandConfig();
    assertEquals(TransactionPropagation.NOT_SUPPORTED, schemaCommandConfig.getTransactionPropagation());
    CommandConfig defaultCommandConfig = jtaProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    assertFalse(schemaCommandConfig.isContextReusePossible());
    assertTrue(defaultCommandConfig.isContextReusePossible());
    assertSame(commandExecutor, commandExecutor2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   * <ul>
   *   <li>Then calls {@link COWArrayList#isEmpty()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors_thenCallsIsEmpty() {
    // Arrange
    COWArrayList<CommandInterceptor> commandInterceptors = mock(COWArrayList.class);
    when(commandInterceptors.isEmpty()).thenThrow(new ActivitiException("An error occurred"));

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandExecutor(null);
    jtaProcessEngineConfiguration.setSchemaCommandConfig(null);
    jtaProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);
    jtaProcessEngineConfiguration.setCommandContextFactory(null);
    jtaProcessEngineConfiguration.setTransactionContextFactory(null);
    jtaProcessEngineConfiguration.setDefaultCommandConfig(null);
    jtaProcessEngineConfiguration.setCommandInvoker(null);
    jtaProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);
    jtaProcessEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());
    jtaProcessEngineConfiguration.setCustomPostCommandInterceptors(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initCommandExecutors());
    verify(commandInterceptors).isEmpty();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDefaultCommandConfig()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDefaultCommandConfig()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDefaultCommandConfig()"})
  public void testInitDefaultCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDefaultCommandConfig(new CommandConfig());

    // Act
    jtaProcessEngineConfiguration.initDefaultCommandConfig();

    // Assert that nothing has changed
    CommandConfig defaultCommandConfig = jtaProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    assertTrue(defaultCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDefaultCommandConfig()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDefaultCommandConfig()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDefaultCommandConfig()"})
  public void testInitDefaultCommandConfig_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initDefaultCommandConfig();

    // Assert
    CommandConfig defaultCommandConfig = jtaProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    assertTrue(defaultCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSchemaCommandConfig()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSchemaCommandConfig()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSchemaCommandConfig()"})
  public void testInitSchemaCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initSchemaCommandConfig();

    // Assert
    CommandConfig schemaCommandConfig = jtaProcessEngineConfiguration.getSchemaCommandConfig();
    assertEquals(TransactionPropagation.NOT_SUPPORTED, schemaCommandConfig.getTransactionPropagation());
    assertFalse(schemaCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSchemaCommandConfig()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSchemaCommandConfig()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSchemaCommandConfig()"})
  public void testInitSchemaCommandConfig2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());

    // Act
    jtaProcessEngineConfiguration.initSchemaCommandConfig();

    // Assert that nothing has changed
    CommandConfig schemaCommandConfig = jtaProcessEngineConfiguration.getSchemaCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, schemaCommandConfig.getTransactionPropagation());
    assertTrue(schemaCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInvoker()"})
  public void testInitCommandInvoker() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initCommandInvoker();

    // Assert
    CommandInterceptor commandInvoker = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof CommandInvoker);
    assertNull(commandInvoker.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInvoker()"})
  public void testInitCommandInvoker2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandContextInterceptor commandInvoker = new CommandContextInterceptor();
    jtaProcessEngineConfiguration.setCommandInvoker(commandInvoker);
    jtaProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);

    // Act
    jtaProcessEngineConfiguration.initCommandInvoker();

    // Assert that nothing has changed
    CommandInterceptor commandInvoker2 = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker2 instanceof CommandContextInterceptor);
    assertSame(commandInvoker, commandInvoker2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInvoker()"})
  public void testInitCommandInvoker3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);

    // Act
    jtaProcessEngineConfiguration.initCommandInvoker();

    // Assert
    CommandInterceptor commandInvoker = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof DebugCommandInvoker);
    assertNull(commandInvoker.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    jtaProcessEngineConfiguration.setCustomPreCommandInterceptors(null);
    jtaProcessEngineConfiguration.setCustomPostCommandInterceptors(null);
    jtaProcessEngineConfiguration.setCommandContextFactory(null);
    jtaProcessEngineConfiguration.setTransactionContextFactory(null);

    // Act
    jtaProcessEngineConfiguration.initCommandInterceptors();

    // Assert that nothing has changed
    assertTrue(jtaProcessEngineConfiguration.getCommandInterceptors().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    jtaProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors = jtaProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(1);
    assertTrue(getResult instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult2 = commandInterceptors.get(0);
    assertTrue(getResult2 instanceof LogInterceptor);
    assertNull(commandInterceptors.get(2));
    assertNull(getResult2.getNext());
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandContextFactory commandContextFactory = new CommandContextFactory();
    jtaProcessEngineConfiguration.setCommandContextFactory(commandContextFactory);
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors = jtaProcessEngineConfiguration
        .getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(3, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult = ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(2);
    ProcessEngineConfigurationImpl processEngineConfiguration = ((CommandContextInterceptor) getResult)
        .getProcessEngineConfiguration();
    assertTrue(processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    assertTrue(getResult instanceof CommandContextInterceptor);
    assertSame(jtaProcessEngineConfiguration, processEngineConfiguration);
    assertSame(commandContextFactory, ((CommandContextInterceptor) getResult).getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors_thenReturnSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors = jtaProcessEngineConfiguration
        .getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(2, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult = ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    assertTrue(getResult instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult2 = ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(0);
    assertTrue(getResult2 instanceof LogInterceptor);
    assertNull(getResult2.getNext());
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   * <ul>
   *   <li>Then third return {@link TransactionContextInterceptor}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors_thenThirdReturnTransactionContextInterceptor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors = jtaProcessEngineConfiguration
        .getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(3, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult = ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    assertTrue(getResult instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult2 = ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(0);
    assertTrue(getResult2 instanceof LogInterceptor);
    CommandInterceptor getResult3 = ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(2);
    assertTrue(getResult3 instanceof TransactionContextInterceptor);
    assertNull(getResult2.getNext());
    assertNull(getResult.getNext());
    assertNull(getResult3.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor() {
    // Arrange
    ArrayList<CommandInterceptor> commandInterceptors = new ArrayList<>();
    CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
    commandInterceptors.add(commandContextInterceptor);
    CommandContextInterceptor commandContextInterceptor2 = new CommandContextInterceptor();
    commandInterceptors.add(commandContextInterceptor2);
    CommandContextInterceptor commandContextInterceptor3 = new CommandContextInterceptor();
    commandInterceptors.add(commandContextInterceptor3);
    CommandContextInterceptor commandContextInterceptor4 = new CommandContextInterceptor();
    commandInterceptors.add(commandContextInterceptor4);
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    CommandContextInterceptor commandContextInterceptor5 = new CommandContextInterceptor();
    commandInterceptors.add(commandContextInterceptor5);
    CommandContextInterceptor commandContextInterceptor6 = new CommandContextInterceptor();
    commandInterceptors.add(commandContextInterceptor6);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandExecutor(null);
    jtaProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);

    // Act
    jtaProcessEngineConfiguration.initCommandExecutor();

    // Assert
    CommandExecutor commandExecutor = jtaProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    List<CommandInterceptor> commandInterceptors2 = jtaProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(19, commandInterceptors2.size());
    CommandInterceptor getResult = commandInterceptors2.get(0);
    assertTrue(getResult instanceof CommandContextInterceptor);
    CommandInterceptor getResult2 = commandInterceptors2.get(1);
    assertTrue(getResult2 instanceof CommandContextInterceptor);
    CommandInterceptor getResult3 = commandInterceptors2.get(17);
    assertTrue(getResult3 instanceof CommandContextInterceptor);
    CommandInterceptor getResult4 = commandInterceptors2.get(2);
    assertTrue(getResult4 instanceof CommandContextInterceptor);
    CommandInterceptor getResult5 = commandInterceptors2.get(Short.SIZE);
    assertTrue(getResult5 instanceof CommandContextInterceptor);
    CommandInterceptor next = getResult4.getNext();
    assertTrue(next instanceof CommandContextInterceptor);
    assertNull(commandExecutor.getDefaultConfig());
    assertSame(commandContextInterceptor, ((CommandExecutorImpl) commandExecutor).getFirst());
    assertSame(commandContextInterceptor2, getResult.getNext());
    assertSame(commandContextInterceptor3, getResult2.getNext());
    assertSame(commandContextInterceptor6, getResult3.getNext());
    assertSame(commandContextInterceptor4, next);
    assertSame(commandContextInterceptor5, getResult5.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    jtaProcessEngineConfiguration.setCommandExecutor(commandExecutor);
    jtaProcessEngineConfiguration.setCommandInterceptors(null);

    // Act
    jtaProcessEngineConfiguration.initCommandExecutor();

    // Assert that nothing has changed
    assertSame(commandExecutor, jtaProcessEngineConfiguration.getCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new JtaProcessEngineConfiguration()).initCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandExecutor(null);
    jtaProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is nineteen.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandInterceptor ProcessEngineConfigurationImpl.initInterceptorChain(List)"})
  public void testInitInterceptorChain_thenArrayListSizeIsNineteen() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> chain = new ArrayList<>();
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
    chain.add(commandContextInterceptor);
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    chain.add(new CommandContextInterceptor());
    CommandContextInterceptor commandContextInterceptor2 = new CommandContextInterceptor();
    chain.add(commandContextInterceptor2);
    CommandContextInterceptor commandContextInterceptor3 = new CommandContextInterceptor();
    chain.add(commandContextInterceptor3);
    CommandContextInterceptor commandContextInterceptor4 = new CommandContextInterceptor();
    chain.add(commandContextInterceptor4);
    CommandContextInterceptor commandContextInterceptor5 = new CommandContextInterceptor();
    chain.add(commandContextInterceptor5);
    CommandContextInterceptor commandContextInterceptor6 = new CommandContextInterceptor();
    chain.add(commandContextInterceptor6);

    // Act
    CommandInterceptor actualInitInterceptorChainResult = jtaProcessEngineConfiguration.initInterceptorChain(chain);

    // Assert
    assertEquals(19, chain.size());
    CommandInterceptor getResult = chain.get(13);
    assertTrue(getResult instanceof CommandContextInterceptor);
    CommandInterceptor getResult2 = chain.get(14);
    assertTrue(getResult2 instanceof CommandContextInterceptor);
    CommandInterceptor getResult3 = chain.get(15);
    assertTrue(getResult3 instanceof CommandContextInterceptor);
    CommandInterceptor getResult4 = chain.get(17);
    assertTrue(getResult4 instanceof CommandContextInterceptor);
    CommandInterceptor getResult5 = chain.get(Short.SIZE);
    assertTrue(getResult5 instanceof CommandContextInterceptor);
    assertTrue(actualInitInterceptorChainResult instanceof CommandContextInterceptor);
    CommandInterceptor next = actualInitInterceptorChainResult.getNext();
    assertTrue(next instanceof CommandContextInterceptor);
    CommandInterceptor next2 = next.getNext();
    assertTrue(next2 instanceof CommandContextInterceptor);
    assertSame(commandContextInterceptor2, getResult.getNext());
    assertSame(commandContextInterceptor3, getResult2.getNext());
    assertSame(commandContextInterceptor4, getResult3.getNext());
    assertSame(commandContextInterceptor6, getResult4.getNext());
    assertSame(commandContextInterceptor5, getResult5.getNext());
    assertSame(commandContextInterceptor, next2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}.
   * <ul>
   *   <li>Then return {@link CommandContextInterceptor#CommandContextInterceptor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandInterceptor ProcessEngineConfigurationImpl.initInterceptorChain(List)"})
  public void testInitInterceptorChain_thenReturnCommandContextInterceptor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> chain = new ArrayList<>();
    CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
    chain.add(commandContextInterceptor);

    // Act and Assert
    assertSame(commandContextInterceptor, jtaProcessEngineConfiguration.initInterceptorChain(chain));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}.
   * <ul>
   *   <li>Then return Next is {@link CommandContextInterceptor#CommandContextInterceptor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandInterceptor ProcessEngineConfigurationImpl.initInterceptorChain(List)"})
  public void testInitInterceptorChain_thenReturnNextIsCommandContextInterceptor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> chain = new ArrayList<>();
    chain.add(new CommandContextInterceptor());
    CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
    chain.add(commandContextInterceptor);

    // Act
    CommandInterceptor actualInitInterceptorChainResult = jtaProcessEngineConfiguration.initInterceptorChain(chain);

    // Assert
    assertTrue(actualInitInterceptorChainResult instanceof CommandContextInterceptor);
    assertSame(commandContextInterceptor, actualInitInterceptorChainResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandInterceptor ProcessEngineConfigurationImpl.initInterceptorChain(List)"})
  public void testInitInterceptorChain_whenArrayList_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initInterceptorChain(new ArrayList<>()));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandInterceptor ProcessEngineConfigurationImpl.initInterceptorChain(List)"})
  public void testInitInterceptorChain_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new JtaProcessEngineConfiguration()).initInterceptorChain(null));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataSource()"})
  public void testInitDataSource() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(null);
    jtaProcessEngineConfiguration.setDataSourceJndiName("Data Source Jndi Name");
    jtaProcessEngineConfiguration.setJdbcUrl(null);
    jtaProcessEngineConfiguration.setJdbcDriver(null);
    jtaProcessEngineConfiguration.setJdbcUsername(null);
    jtaProcessEngineConfiguration.setJdbcMaxActiveConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxIdleConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxCheckoutTime(0);
    jtaProcessEngineConfiguration.setJdbcMaxWaitTime(0);
    jtaProcessEngineConfiguration.setJdbcPingEnabled(false);
    jtaProcessEngineConfiguration.setJdbcPingQuery(null);
    jtaProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(0);
    jtaProcessEngineConfiguration.setDatabaseType("foo");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initDataSource());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataSource()"})
  public void testInitDataSource2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(null);
    jtaProcessEngineConfiguration.setDataSourceJndiName(null);
    jtaProcessEngineConfiguration.setJdbcUrl("https://example.org/example");
    jtaProcessEngineConfiguration.setJdbcDriver(null);
    jtaProcessEngineConfiguration.setJdbcUsername(null);
    jtaProcessEngineConfiguration.setJdbcMaxActiveConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxIdleConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxCheckoutTime(0);
    jtaProcessEngineConfiguration.setJdbcMaxWaitTime(0);
    jtaProcessEngineConfiguration.setJdbcPingEnabled(false);
    jtaProcessEngineConfiguration.setJdbcPingQuery(null);
    jtaProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(0);
    jtaProcessEngineConfiguration.setDatabaseType("foo");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initDataSource());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataSource()"})
  public void testInitDataSource3() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    TenantAwareDataSource dataSource = mock(TenantAwareDataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.setDataSourceJndiName(null);
    jtaProcessEngineConfiguration.setJdbcUrl(null);
    jtaProcessEngineConfiguration.setJdbcDriver(null);
    jtaProcessEngineConfiguration.setJdbcUsername(null);
    jtaProcessEngineConfiguration.setJdbcMaxActiveConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxIdleConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxCheckoutTime(0);
    jtaProcessEngineConfiguration.setJdbcMaxWaitTime(0);
    jtaProcessEngineConfiguration.setJdbcPingEnabled(false);
    jtaProcessEngineConfiguration.setJdbcPingQuery(null);
    jtaProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(1);
    jtaProcessEngineConfiguration.setDatabaseType(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initDataSource());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataSource()"})
  public void testInitDataSource4() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    TenantAwareDataSource dataSource = mock(TenantAwareDataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.setDataSourceJndiName(null);
    jtaProcessEngineConfiguration.setJdbcUrl(null);
    jtaProcessEngineConfiguration.setJdbcDriver(null);
    jtaProcessEngineConfiguration.setJdbcUsername(null);
    jtaProcessEngineConfiguration.setJdbcMaxActiveConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxIdleConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxCheckoutTime(0);
    jtaProcessEngineConfiguration.setJdbcMaxWaitTime(0);
    jtaProcessEngineConfiguration.setJdbcPingEnabled(false);
    jtaProcessEngineConfiguration.setJdbcPingQuery(null);
    jtaProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(1);
    jtaProcessEngineConfiguration.setDatabaseType(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initDataSource());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <ul>
   *   <li>Given {@link Connection} {@link Connection#getMetaData()} throw {@link SQLException#SQLException()}.</li>
   *   <li>Then calls {@link Connection#close()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataSource()"})
  public void testInitDataSource_givenConnectionGetMetaDataThrowSQLException_thenCallsClose() throws SQLException {
    // Arrange
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new SQLException());
    doThrow(new SQLException()).when(connection).close();
    TenantAwareDataSource dataSource = mock(TenantAwareDataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.setDataSourceJndiName(null);
    jtaProcessEngineConfiguration.setJdbcUrl(null);
    jtaProcessEngineConfiguration.setJdbcDriver(null);
    jtaProcessEngineConfiguration.setJdbcUsername(null);
    jtaProcessEngineConfiguration.setJdbcMaxActiveConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxIdleConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxCheckoutTime(0);
    jtaProcessEngineConfiguration.setJdbcMaxWaitTime(0);
    jtaProcessEngineConfiguration.setJdbcPingEnabled(false);
    jtaProcessEngineConfiguration.setJdbcPingQuery("foo");
    jtaProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(0);
    jtaProcessEngineConfiguration.setDatabaseType(null);

    // Act
    jtaProcessEngineConfiguration.initDataSource();

    // Assert
    verify(connection).close();
    verify(connection).getMetaData();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <ul>
   *   <li>Given {@link DatabaseMetaData} {@link DatabaseMetaData#getDatabaseProductName()} throw {@link SQLException#SQLException()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataSource()"})
  public void testInitDataSource_givenDatabaseMetaDataGetDatabaseProductNameThrowSQLException() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenThrow(new SQLException());
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    TenantAwareDataSource dataSource = mock(TenantAwareDataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);
    jtaProcessEngineConfiguration.setDataSourceJndiName(null);
    jtaProcessEngineConfiguration.setJdbcUrl(null);
    jtaProcessEngineConfiguration.setJdbcDriver(null);
    jtaProcessEngineConfiguration.setJdbcUsername(null);
    jtaProcessEngineConfiguration.setJdbcMaxActiveConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxIdleConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxCheckoutTime(0);
    jtaProcessEngineConfiguration.setJdbcMaxWaitTime(0);
    jtaProcessEngineConfiguration.setJdbcPingEnabled(false);
    jtaProcessEngineConfiguration.setJdbcPingQuery(null);
    jtaProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(1);
    jtaProcessEngineConfiguration.setDatabaseType(null);

    // Act
    jtaProcessEngineConfiguration.initDataSource();

    // Assert
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) DataSource is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataSource()"})
  public void testInitDataSource_thenJtaProcessEngineConfigurationDataSourceIsNull() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(null);
    jtaProcessEngineConfiguration.setDataSourceJndiName(null);
    jtaProcessEngineConfiguration.setJdbcUrl(null);
    jtaProcessEngineConfiguration.setJdbcDriver(null);
    jtaProcessEngineConfiguration.setJdbcUsername(null);
    jtaProcessEngineConfiguration.setJdbcMaxActiveConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxIdleConnections(0);
    jtaProcessEngineConfiguration.setJdbcMaxCheckoutTime(0);
    jtaProcessEngineConfiguration.setJdbcMaxWaitTime(0);
    jtaProcessEngineConfiguration.setJdbcPingEnabled(false);
    jtaProcessEngineConfiguration.setJdbcPingQuery(null);
    jtaProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(0);
    jtaProcessEngineConfiguration.setDatabaseType("foo");

    // Act
    jtaProcessEngineConfiguration.initDataSource();

    // Assert that nothing has changed
    assertNull(jtaProcessEngineConfiguration.getDataSource());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) DataSource {@link PooledDataSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataSource()"})
  public void testInitDataSource_thenJtaProcessEngineConfigurationDataSourcePooledDataSource() throws SQLException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initDataSource();

    // Assert
    DataSource dataSource = jtaProcessEngineConfiguration.getDataSource();
    assertTrue(dataSource instanceof PooledDataSource);
    assertEquals("", ((PooledDataSource) dataSource).getPassword());
    assertEquals("NO PING QUERY SET", ((PooledDataSource) dataSource).getPoolPingQuery());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", ((PooledDataSource) dataSource).getUrl());
    assertEquals("org.h2.Driver", ((PooledDataSource) dataSource).getDriver());
    assertEquals("sa", ((PooledDataSource) dataSource).getUsername());
    assertNull(dataSource.getLogWriter());
    assertNull(((PooledDataSource) dataSource).getDefaultNetworkTimeout());
    assertNull(((PooledDataSource) dataSource).getDefaultTransactionIsolationLevel());
    assertNull(((PooledDataSource) dataSource).getDriverProperties());
    assertEquals(0, dataSource.getLoginTimeout());
    assertEquals(0, ((PooledDataSource) dataSource).getPoolPingConnectionsNotUsedFor());
    assertEquals(10, ((PooledDataSource) dataSource).getPoolMaximumActiveConnections());
    assertEquals(20000, ((PooledDataSource) dataSource).getPoolMaximumCheckoutTime());
    assertEquals(20000, ((PooledDataSource) dataSource).getPoolTimeToWait());
    assertEquals(3, ((PooledDataSource) dataSource).getPoolMaximumLocalBadConnectionTolerance());
    assertEquals(5, ((PooledDataSource) dataSource).getPoolMaximumIdleConnections());
    assertFalse(((PooledDataSource) dataSource).isPoolPingEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDatabaseTypeMappings()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDatabaseTypeMappings()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Properties ProcessEngineConfigurationImpl.getDefaultDatabaseTypeMappings()"})
  public void testGetDefaultDatabaseTypeMappings() {
    // Arrange and Act
    Properties actualDefaultDatabaseTypeMappings = ProcessEngineConfigurationImpl.getDefaultDatabaseTypeMappings();

    // Assert
    assertEquals(29, actualDefaultDatabaseTypeMappings.size());
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_DB2, actualDefaultDatabaseTypeMappings.get("DB2/2"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_DB2, actualDefaultDatabaseTypeMappings.get("DB2/AIX64"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_DB2, actualDefaultDatabaseTypeMappings.get("DB2/LINUX"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_DB2,
        actualDefaultDatabaseTypeMappings.get("DB2/LINUX390"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_DB2,
        actualDefaultDatabaseTypeMappings.get("DB2/LINUXPPC64LE"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_DB2,
        actualDefaultDatabaseTypeMappings.get("DB2/LINUXZ64"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_DB2, actualDefaultDatabaseTypeMappings.get("DB2/NT"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_DB2, actualDefaultDatabaseTypeMappings.get("DB2/NT64"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_HSQL,
        actualDefaultDatabaseTypeMappings.get("HSQL Database Engine"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_MSSQL,
        actualDefaultDatabaseTypeMappings.get("Microsoft SQL Server"));
    assertEquals(ProcessEngineConfigurationImpl.DATABASE_TYPE_POSTGRES,
        actualDefaultDatabaseTypeMappings.get("PostgreSQL"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDatabaseType()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDatabaseType()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDatabaseType()"})
  public void testInitDatabaseType() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initDatabaseType());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDatabaseType()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDatabaseType()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDatabaseType()"})
  public void testInitDatabaseType2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initDatabaseType());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDatabaseType()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDatabaseType()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDatabaseType()"})
  public void testInitDatabaseType3() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenThrow(new SQLException());
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDataSource(dataSource);

    // Act
    jtaProcessEngineConfiguration.initDatabaseType();

    // Assert
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initTransactionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initTransactionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initTransactionFactory()"})
  public void testInitTransactionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initTransactionFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getTransactionFactory() instanceof ManagedTransactionFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initTransactionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initTransactionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initTransactionFactory()"})
  public void testInitTransactionFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionFactory(null);
    jtaProcessEngineConfiguration.setTransactionsExternallyManaged(false);

    // Act
    jtaProcessEngineConfiguration.initTransactionFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getTransactionFactory() instanceof JdbcTransactionFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initTransactionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initTransactionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initTransactionFactory()"})
  public void testInitTransactionFactory3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();
    jtaProcessEngineConfiguration.setTransactionFactory(transactionFactory);
    jtaProcessEngineConfiguration.setTransactionsExternallyManaged(false);

    // Act
    jtaProcessEngineConfiguration.initTransactionFactory();

    // Assert that nothing has changed
    TransactionFactory transactionFactory2 = jtaProcessEngineConfiguration.getTransactionFactory();
    assertTrue(transactionFactory2 instanceof JdbcTransactionFactory);
    assertSame(transactionFactory, transactionFactory2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSqlSessionFactory()"})
  public void testInitSqlSessionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setCustomMybatisMappers(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(null);

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert
    SqlSessionFactory sqlSessionFactory = jtaProcessEngineConfiguration.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    Map<String, XNode> sqlFragments = sqlSessionFactory.getConfiguration().getSqlFragments();
    assertEquals(94, sqlFragments.size());
    List<XNode> children = sqlFragments
        .get("org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl.executionVariab"
            + "leOperator")
        .getChildren();
    assertEquals(1, children.size());
    assertEquals(6, children.get(0).getChildren().size());
    assertTrue(sqlFragments.containsKey("selectDeploymentsByQueryCriteriaSql"));
    assertTrue(sqlFragments.containsKey("selectExecutionsFromSql"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSqlSessionFactory()"})
  public void testInitSqlSessionFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setCustomMybatisMappers(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(new HashSet<>());

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getSqlSessionFactory() instanceof DefaultSqlSessionFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSqlSessionFactory()"})
  public void testInitSqlSessionFactory3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setCustomMybatisMappers(new HashSet<>());
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(null);

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getSqlSessionFactory() instanceof DefaultSqlSessionFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSqlSessionFactory()"})
  public void testInitSqlSessionFactory4() {
    // Arrange
    LinkedHashSet<Class<?>> customMybatisMappers = new LinkedHashSet<>();
    Class<Object> forNameResult = Object.class;
    customMybatisMappers.add(forNameResult);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setCustomMybatisMappers(customMybatisMappers);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(null);

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert
    SqlSessionFactory sqlSessionFactory = jtaProcessEngineConfiguration.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    TypeHandler<Object> unknownTypeHandler = sqlSessionFactory.getConfiguration()
        .getTypeHandlerRegistry()
        .getUnknownTypeHandler();
    assertTrue(unknownTypeHandler instanceof UnknownTypeHandler);
    assertSame(forNameResult, ((UnknownTypeHandler) unknownTypeHandler).getRawType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSqlSessionFactory()"})
  public void testInitSqlSessionFactory5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(new Configuration());
    jtaProcessEngineConfiguration.setSqlSessionFactory(sqlSessionFactory);

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert that nothing has changed
    SqlSessionFactory sqlSessionFactory2 = jtaProcessEngineConfiguration.getSqlSessionFactory();
    assertTrue(sqlSessionFactory2 instanceof DefaultSqlSessionFactory);
    assertSame(sqlSessionFactory, sqlSessionFactory2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSqlSessionFactory()"})
  public void testInitSqlSessionFactory6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration
        .setDatabaseWildcardEscapeCharacter(ProcessEngineConfigurationImpl.DEFAULT_MYBATIS_MAPPING_FILE);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setCustomMybatisMappers(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(null);

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert
    SqlSessionFactory sqlSessionFactory = jtaProcessEngineConfiguration.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    Map<String, XNode> sqlFragments = sqlSessionFactory.getConfiguration().getSqlFragments();
    assertEquals(94, sqlFragments.size());
    List<XNode> children = sqlFragments
        .get("org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl.executionVariab"
            + "leOperator")
        .getChildren();
    assertEquals(1, children.size());
    assertEquals(6, children.get(0).getChildren().size());
    assertTrue(sqlFragments.containsKey("selectDeploymentsByQueryCriteriaSql"));
    assertTrue(sqlFragments.containsKey("selectExecutionsFromSql"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSqlSessionFactory()"})
  public void testInitSqlSessionFactory7() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(ProcessEngineConfiguration.NO_TENANT_ID);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setCustomMybatisMappers(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(null);

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert
    SqlSessionFactory sqlSessionFactory = jtaProcessEngineConfiguration.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    Map<String, XNode> sqlFragments = sqlSessionFactory.getConfiguration().getSqlFragments();
    assertEquals(94, sqlFragments.size());
    List<XNode> children = sqlFragments
        .get("org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl.executionVariab"
            + "leOperator")
        .getChildren();
    assertEquals(1, children.size());
    assertEquals(6, children.get(0).getChildren().size());
    assertTrue(sqlFragments.containsKey("selectDeploymentsByQueryCriteriaSql"));
    assertTrue(sqlFragments.containsKey("selectExecutionsFromSql"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSqlSessionFactory()"})
  public void testInitSqlSessionFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new JtaProcessEngineConfiguration()).initSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) DataSource is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initSqlSessionFactory()"})
  public void testInitSqlSessionFactory_givenJtaProcessEngineConfigurationDataSourceIsNull() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setCustomMybatisMappers(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(null);
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initMybatisTypeHandlers(Configuration)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initMybatisTypeHandlers(Configuration)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initMybatisTypeHandlers(Configuration)"})
  public void testInitMybatisTypeHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    Configuration configuration = new Configuration();

    // Act
    jtaProcessEngineConfiguration.initMybatisTypeHandlers(configuration);

    // Assert
    assertEquals(41, configuration.getTypeHandlerRegistry().getTypeHandlers().size());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCustomMybatisMappers(Configuration)}.
   * <ul>
   *   <li>Then calls {@link Configuration#addMapper(Class)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCustomMybatisMappers(Configuration)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCustomMybatisMappers(Configuration)"})
  public void testInitCustomMybatisMappers_thenCallsAddMapper() {
    // Arrange
    HashSet<Class<?>> customMybatisMappers = new HashSet<>();
    Class<Object> forNameResult = Object.class;
    customMybatisMappers.add(forNameResult);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomMybatisMappers(customMybatisMappers);
    Configuration configuration = mock(Configuration.class);
    doNothing().when(configuration).addMapper(Mockito.<Class<Object>>any());

    // Act
    jtaProcessEngineConfiguration.initCustomMybatisMappers(configuration);

    // Assert
    verify(configuration).addMapper(isA(Class.class));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#parseMybatisConfiguration(Configuration, XMLConfigBuilder)}.
   * <ul>
   *   <li>Given {@link Configuration#Configuration()}.</li>
   *   <li>Then calls {@link XMLConfigBuilder#parse()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#parseMybatisConfiguration(Configuration, XMLConfigBuilder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "Configuration ProcessEngineConfigurationImpl.parseMybatisConfiguration(Configuration, XMLConfigBuilder)"})
  public void testParseMybatisConfiguration_givenConfiguration_thenCallsParse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    Configuration configuration = new Configuration();
    XMLConfigBuilder parser = mock(XMLConfigBuilder.class);
    when(parser.parse()).thenReturn(new Configuration());

    // Act
    jtaProcessEngineConfiguration.parseMybatisConfiguration(configuration, parser);

    // Assert
    verify(parser).parse();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#parseMybatisConfiguration(Configuration, XMLConfigBuilder)}.
   * <ul>
   *   <li>Then KeyGeneratorNames return {@link Set}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#parseMybatisConfiguration(Configuration, XMLConfigBuilder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "Configuration ProcessEngineConfigurationImpl.parseMybatisConfiguration(Configuration, XMLConfigBuilder)"})
  public void testParseMybatisConfiguration_thenKeyGeneratorNamesReturnSet() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HashSet<String> customMybatisXMLMappers = new HashSet<>();
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(customMybatisXMLMappers);
    Configuration configuration = new Configuration();
    XMLConfigBuilder parser = mock(XMLConfigBuilder.class);
    when(parser.parse()).thenReturn(new Configuration());

    // Act
    Configuration actualParseMybatisConfigurationResult = jtaProcessEngineConfiguration
        .parseMybatisConfiguration(configuration, parser);

    // Assert
    verify(parser).parse();
    Collection<String> keyGeneratorNames = actualParseMybatisConfigurationResult.getKeyGeneratorNames();
    assertTrue(keyGeneratorNames instanceof Set);
    Collection<String> mappedStatementNames = actualParseMybatisConfigurationResult.getMappedStatementNames();
    assertTrue(mappedStatementNames instanceof Set);
    Collection<String> parameterMapNames = actualParseMybatisConfigurationResult.getParameterMapNames();
    assertTrue(parameterMapNames instanceof Set);
    Collection<String> resultMapNames = actualParseMybatisConfigurationResult.getResultMapNames();
    assertTrue(resultMapNames instanceof Set);
    assertTrue(keyGeneratorNames.isEmpty());
    assertTrue(mappedStatementNames.isEmpty());
    assertTrue(parameterMapNames.isEmpty());
    assertTrue(resultMapNames.isEmpty());
    assertEquals(customMybatisXMLMappers, actualParseMybatisConfigurationResult.getCacheNames());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#parseCustomMybatisXMLMappers(Configuration)}.
   * <ul>
   *   <li>Then KeyGeneratorNames return {@link Set}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#parseCustomMybatisXMLMappers(Configuration)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Configuration ProcessEngineConfigurationImpl.parseCustomMybatisXMLMappers(Configuration)"})
  public void testParseCustomMybatisXMLMappers_thenKeyGeneratorNamesReturnSet() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HashSet<String> customMybatisXMLMappers = new HashSet<>();
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(customMybatisXMLMappers);

    // Act
    Configuration actualParseCustomMybatisXMLMappersResult = jtaProcessEngineConfiguration
        .parseCustomMybatisXMLMappers(new Configuration());

    // Assert
    Collection<String> keyGeneratorNames = actualParseCustomMybatisXMLMappersResult.getKeyGeneratorNames();
    assertTrue(keyGeneratorNames instanceof Set);
    Collection<String> mappedStatementNames = actualParseCustomMybatisXMLMappersResult.getMappedStatementNames();
    assertTrue(mappedStatementNames instanceof Set);
    Collection<String> parameterMapNames = actualParseCustomMybatisXMLMappersResult.getParameterMapNames();
    assertTrue(parameterMapNames instanceof Set);
    Collection<String> resultMapNames = actualParseCustomMybatisXMLMappersResult.getResultMapNames();
    assertTrue(resultMapNames instanceof Set);
    assertTrue(keyGeneratorNames.isEmpty());
    assertTrue(mappedStatementNames.isEmpty());
    assertTrue(parameterMapNames.isEmpty());
    assertTrue(resultMapNames.isEmpty());
    assertEquals(customMybatisXMLMappers, actualParseCustomMybatisXMLMappersResult.getCacheNames());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResourceAsStream(String)}.
   * <ul>
   *   <li>When {@code Resource}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getResourceAsStream(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.io.InputStream ProcessEngineConfigurationImpl.getResourceAsStream(String)"})
  public void testGetResourceAsStream_whenResource_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getResourceAsStream("Resource"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMyBatisXmlConfigurationStream()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getMyBatisXmlConfigurationStream()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.io.InputStream ProcessEngineConfigurationImpl.getMyBatisXmlConfigurationStream()"})
  public void testGetMyBatisXmlConfigurationStream() throws IOException {
    // Arrange, Act and Assert
    byte[] byteArray = new byte[51];
    assertEquals(51, (new JtaProcessEngineConfiguration()).getMyBatisXmlConfigurationStream().read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set ProcessEngineConfigurationImpl.getCustomMybatisMappers()"})
  public void testGetCustomMybatisMappers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomMybatisMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}.
   * <ul>
   *   <li>Given {@code Class}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@link Class}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setCustomMybatisMappers(Set)"})
  public void testSetCustomMybatisMappers_givenJavaLangClass_whenHashSetAddClass() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    HashSet<Class<?>> customMybatisMappers = new HashSet<>();
    Class<Class> forNameResult = Class.class;
    customMybatisMappers.add(forNameResult);
    Class<Object> forNameResult2 = Object.class;
    customMybatisMappers.add(forNameResult2);

    // Act
    jtaProcessEngineConfiguration.setCustomMybatisMappers(customMybatisMappers);

    // Assert
    assertSame(customMybatisMappers, jtaProcessEngineConfiguration.getCustomMybatisMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setCustomMybatisMappers(Set)"})
  public void testSetCustomMybatisMappers_givenJavaLangObject_whenHashSetAddObject() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    HashSet<Class<?>> customMybatisMappers = new HashSet<>();
    Class<Object> forNameResult = Object.class;
    customMybatisMappers.add(forNameResult);

    // Act
    jtaProcessEngineConfiguration.setCustomMybatisMappers(customMybatisMappers);

    // Assert
    assertSame(customMybatisMappers, jtaProcessEngineConfiguration.getCustomMybatisMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setCustomMybatisMappers(Set)"})
  public void testSetCustomMybatisMappers_whenHashSet() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HashSet<Class<?>> customMybatisMappers = new HashSet<>();

    // Act
    jtaProcessEngineConfiguration.setCustomMybatisMappers(customMybatisMappers);

    // Assert
    assertSame(customMybatisMappers, jtaProcessEngineConfiguration.getCustomMybatisMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomMybatisXMLMappers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomMybatisXMLMappers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set ProcessEngineConfigurationImpl.getCustomMybatisXMLMappers()"})
  public void testGetCustomMybatisXMLMappers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomMybatisXMLMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setCustomMybatisXMLMappers(Set)"})
  public void testSetCustomMybatisXMLMappers_given42_whenHashSetAdd42() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    HashSet<String> customMybatisXMLMappers = new HashSet<>();
    customMybatisXMLMappers.add("42");
    customMybatisXMLMappers.add("foo");

    // Act
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(customMybatisXMLMappers);

    // Assert
    assertSame(customMybatisXMLMappers, jtaProcessEngineConfiguration.getCustomMybatisXMLMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setCustomMybatisXMLMappers(Set)"})
  public void testSetCustomMybatisXMLMappers_givenFoo_whenHashSetAddFoo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    HashSet<String> customMybatisXMLMappers = new HashSet<>();
    customMybatisXMLMappers.add("foo");

    // Act
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(customMybatisXMLMappers);

    // Assert
    assertSame(customMybatisXMLMappers, jtaProcessEngineConfiguration.getCustomMybatisXMLMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setCustomMybatisXMLMappers(Set)"})
  public void testSetCustomMybatisXMLMappers_whenHashSet() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HashSet<String> customMybatisXMLMappers = new HashSet<>();

    // Act
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(customMybatisXMLMappers);

    // Assert
    assertSame(customMybatisXMLMappers, jtaProcessEngineConfiguration.getCustomMybatisXMLMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisAttachmentDataManager attachmentDataManager = new MybatisAttachmentDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setAttachmentDataManager(attachmentDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    AttachmentDataManager attachmentDataManager2 = jtaProcessEngineConfiguration.getAttachmentDataManager();
    assertTrue(attachmentDataManager2 instanceof MybatisAttachmentDataManager);
    assertSame(attachmentDataManager, attachmentDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisByteArrayDataManager byteArrayDataManager = new MybatisByteArrayDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setByteArrayDataManager(byteArrayDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    ByteArrayDataManager byteArrayDataManager2 = jtaProcessEngineConfiguration.getByteArrayDataManager();
    assertTrue(byteArrayDataManager2 instanceof MybatisByteArrayDataManager);
    assertSame(byteArrayDataManager, byteArrayDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisCommentDataManager commentDataManager = new MybatisCommentDataManager(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setCommentDataManager(commentDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    CommentDataManager commentDataManager2 = jtaProcessEngineConfiguration.getCommentDataManager();
    assertTrue(commentDataManager2 instanceof MybatisCommentDataManager);
    assertSame(commentDataManager, commentDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisDeploymentDataManager deploymentDataManager = new MybatisDeploymentDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setDeploymentDataManager(deploymentDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    DeploymentDataManager deploymentDataManager2 = jtaProcessEngineConfiguration.getDeploymentDataManager();
    assertTrue(deploymentDataManager2 instanceof MybatisDeploymentDataManager);
    assertSame(deploymentDataManager, deploymentDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisEventLogEntryDataManager eventLogEntryDataManager = new MybatisEventLogEntryDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setEventLogEntryDataManager(eventLogEntryDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    EventLogEntryDataManager eventLogEntryDataManager2 = jtaProcessEngineConfiguration.getEventLogEntryDataManager();
    assertTrue(eventLogEntryDataManager2 instanceof MybatisEventLogEntryDataManager);
    assertSame(eventLogEntryDataManager, eventLogEntryDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisEventSubscriptionDataManager eventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setEventSubscriptionDataManager(eventSubscriptionDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    EventSubscriptionDataManager eventSubscriptionDataManager2 = jtaProcessEngineConfiguration
        .getEventSubscriptionDataManager();
    assertTrue(eventSubscriptionDataManager2 instanceof MybatisEventSubscriptionDataManager);
    assertSame(eventSubscriptionDataManager, eventSubscriptionDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers7() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisExecutionDataManager executionDataManager = new MybatisExecutionDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setExecutionDataManager(executionDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    ExecutionDataManager executionDataManager2 = jtaProcessEngineConfiguration.getExecutionDataManager();
    assertTrue(executionDataManager2 instanceof MybatisExecutionDataManager);
    assertSame(executionDataManager, executionDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers8() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricActivityInstanceDataManager historicActivityInstanceDataManager = new MybatisHistoricActivityInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricActivityInstanceDataManager(historicActivityInstanceDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager2 = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceDataManager();
    assertTrue(historicActivityInstanceDataManager2 instanceof MybatisHistoricActivityInstanceDataManager);
    assertSame(historicActivityInstanceDataManager, historicActivityInstanceDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers9() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricDetailDataManager historicDetailDataManager = new MybatisHistoricDetailDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricDetailDataManager(historicDetailDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    HistoricDetailDataManager historicDetailDataManager2 = jtaProcessEngineConfiguration.getHistoricDetailDataManager();
    assertTrue(historicDetailDataManager2 instanceof MybatisHistoricDetailDataManager);
    assertSame(historicDetailDataManager, historicDetailDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers10() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricIdentityLinkDataManager historicIdentityLinkDataManager = new MybatisHistoricIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricIdentityLinkDataManager(historicIdentityLinkDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager2 = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkDataManager();
    assertTrue(historicIdentityLinkDataManager2 instanceof MybatisHistoricIdentityLinkDataManager);
    assertSame(historicIdentityLinkDataManager, historicIdentityLinkDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers11() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricProcessInstanceDataManager historicProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricProcessInstanceDataManager(historicProcessInstanceDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager2 = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceDataManager();
    assertTrue(historicProcessInstanceDataManager2 instanceof MybatisHistoricProcessInstanceDataManager);
    assertSame(historicProcessInstanceDataManager, historicProcessInstanceDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers12() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricTaskInstanceDataManager historicTaskInstanceDataManager = new MybatisHistoricTaskInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricTaskInstanceDataManager(historicTaskInstanceDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager2 = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceDataManager();
    assertTrue(historicTaskInstanceDataManager2 instanceof MybatisHistoricTaskInstanceDataManager);
    assertSame(historicTaskInstanceDataManager, historicTaskInstanceDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers13() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricVariableInstanceDataManager historicVariableInstanceDataManager = new MybatisHistoricVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricVariableInstanceDataManager(historicVariableInstanceDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager2 = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceDataManager();
    assertTrue(historicVariableInstanceDataManager2 instanceof MybatisHistoricVariableInstanceDataManager);
    assertSame(historicVariableInstanceDataManager, historicVariableInstanceDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers14() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisIdentityLinkDataManager identityLinkDataManager = new MybatisIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setIdentityLinkDataManager(identityLinkDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    IdentityLinkDataManager identityLinkDataManager2 = jtaProcessEngineConfiguration.getIdentityLinkDataManager();
    assertTrue(identityLinkDataManager2 instanceof MybatisIdentityLinkDataManager);
    assertSame(identityLinkDataManager, identityLinkDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers15() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisJobDataManager jobDataManager = new MybatisJobDataManager(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setJobDataManager(jobDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    JobDataManager jobDataManager2 = jtaProcessEngineConfiguration.getJobDataManager();
    assertTrue(jobDataManager2 instanceof MybatisJobDataManager);
    assertSame(jobDataManager, jobDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers16() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisTimerJobDataManager timerJobDataManager = new MybatisTimerJobDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setTimerJobDataManager(timerJobDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    TimerJobDataManager timerJobDataManager2 = jtaProcessEngineConfiguration.getTimerJobDataManager();
    assertTrue(timerJobDataManager2 instanceof MybatisTimerJobDataManager);
    assertSame(timerJobDataManager, timerJobDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers17() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisSuspendedJobDataManager suspendedJobDataManager = new MybatisSuspendedJobDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setSuspendedJobDataManager(suspendedJobDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    SuspendedJobDataManager suspendedJobDataManager2 = jtaProcessEngineConfiguration.getSuspendedJobDataManager();
    assertTrue(suspendedJobDataManager2 instanceof MybatisSuspendedJobDataManager);
    assertSame(suspendedJobDataManager, suspendedJobDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers18() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisDeadLetterJobDataManager deadLetterJobDataManager = new MybatisDeadLetterJobDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setDeadLetterJobDataManager(deadLetterJobDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    DeadLetterJobDataManager deadLetterJobDataManager2 = jtaProcessEngineConfiguration.getDeadLetterJobDataManager();
    assertTrue(deadLetterJobDataManager2 instanceof MybatisDeadLetterJobDataManager);
    assertSame(deadLetterJobDataManager, deadLetterJobDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers19() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisModelDataManager modelDataManager = new MybatisModelDataManager(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setModelDataManager(modelDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    ModelDataManager modelDataManager2 = jtaProcessEngineConfiguration.getModelDataManager();
    assertTrue(modelDataManager2 instanceof MybatisModelDataManager);
    assertSame(modelDataManager, modelDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers20() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisProcessDefinitionDataManager processDefinitionDataManager = new MybatisProcessDefinitionDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setProcessDefinitionDataManager(processDefinitionDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    ProcessDefinitionDataManager processDefinitionDataManager2 = jtaProcessEngineConfiguration
        .getProcessDefinitionDataManager();
    assertTrue(processDefinitionDataManager2 instanceof MybatisProcessDefinitionDataManager);
    assertSame(processDefinitionDataManager, processDefinitionDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers21() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisProcessDefinitionInfoDataManager processDefinitionInfoDataManager = new MybatisProcessDefinitionInfoDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setProcessDefinitionInfoDataManager(processDefinitionInfoDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager2 = jtaProcessEngineConfiguration
        .getProcessDefinitionInfoDataManager();
    assertTrue(processDefinitionInfoDataManager2 instanceof MybatisProcessDefinitionInfoDataManager);
    assertSame(processDefinitionInfoDataManager, processDefinitionInfoDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers22() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisPropertyDataManager propertyDataManager = new MybatisPropertyDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setPropertyDataManager(propertyDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    PropertyDataManager propertyDataManager2 = jtaProcessEngineConfiguration.getPropertyDataManager();
    assertTrue(propertyDataManager2 instanceof MybatisPropertyDataManager);
    assertSame(propertyDataManager, propertyDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers23() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisResourceDataManager resourceDataManager = new MybatisResourceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setResourceDataManager(resourceDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    ResourceDataManager resourceDataManager2 = jtaProcessEngineConfiguration.getResourceDataManager();
    assertTrue(resourceDataManager2 instanceof MybatisResourceDataManager);
    assertSame(resourceDataManager, resourceDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers24() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisTaskDataManager taskDataManager = new MybatisTaskDataManager(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setTaskDataManager(taskDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    TaskDataManager taskDataManager2 = jtaProcessEngineConfiguration.getTaskDataManager();
    assertTrue(taskDataManager2 instanceof MybatisTaskDataManager);
    assertSame(taskDataManager, taskDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers25() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisVariableInstanceDataManager variableInstanceDataManager = new MybatisVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setVariableInstanceDataManager(variableInstanceDataManager);

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert that nothing has changed
    VariableInstanceDataManager variableInstanceDataManager2 = jtaProcessEngineConfiguration
        .getVariableInstanceDataManager();
    assertTrue(variableInstanceDataManager2 instanceof MybatisVariableInstanceDataManager);
    assertSame(variableInstanceDataManager, variableInstanceDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDataManagers()"})
  public void testInitDataManagers_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAttachmentDataManager() instanceof MybatisAttachmentDataManager);
    assertTrue(jtaProcessEngineConfiguration.getByteArrayDataManager() instanceof MybatisByteArrayDataManager);
    assertTrue(jtaProcessEngineConfiguration.getCommentDataManager() instanceof MybatisCommentDataManager);
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobDataManager() instanceof MybatisDeadLetterJobDataManager);
    assertTrue(jtaProcessEngineConfiguration.getDeploymentDataManager() instanceof MybatisDeploymentDataManager);
    assertTrue(jtaProcessEngineConfiguration.getEventLogEntryDataManager() instanceof MybatisEventLogEntryDataManager);
    assertTrue(
        jtaProcessEngineConfiguration.getEventSubscriptionDataManager() instanceof MybatisEventSubscriptionDataManager);
    assertTrue(jtaProcessEngineConfiguration.getExecutionDataManager() instanceof MybatisExecutionDataManager);
    assertTrue(jtaProcessEngineConfiguration
        .getHistoricActivityInstanceDataManager() instanceof MybatisHistoricActivityInstanceDataManager);
    assertTrue(
        jtaProcessEngineConfiguration.getHistoricDetailDataManager() instanceof MybatisHistoricDetailDataManager);
    assertTrue(jtaProcessEngineConfiguration
        .getHistoricIdentityLinkDataManager() instanceof MybatisHistoricIdentityLinkDataManager);
    assertTrue(jtaProcessEngineConfiguration
        .getHistoricProcessInstanceDataManager() instanceof MybatisHistoricProcessInstanceDataManager);
    assertTrue(jtaProcessEngineConfiguration
        .getHistoricTaskInstanceDataManager() instanceof MybatisHistoricTaskInstanceDataManager);
    assertTrue(jtaProcessEngineConfiguration
        .getHistoricVariableInstanceDataManager() instanceof MybatisHistoricVariableInstanceDataManager);
    assertTrue(jtaProcessEngineConfiguration.getIdentityLinkDataManager() instanceof MybatisIdentityLinkDataManager);
    assertTrue(jtaProcessEngineConfiguration.getJobDataManager() instanceof MybatisJobDataManager);
    assertTrue(jtaProcessEngineConfiguration.getModelDataManager() instanceof MybatisModelDataManager);
    assertTrue(
        jtaProcessEngineConfiguration.getProcessDefinitionDataManager() instanceof MybatisProcessDefinitionDataManager);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoDataManager() instanceof MybatisProcessDefinitionInfoDataManager);
    assertTrue(jtaProcessEngineConfiguration.getPropertyDataManager() instanceof MybatisPropertyDataManager);
    assertTrue(jtaProcessEngineConfiguration.getResourceDataManager() instanceof MybatisResourceDataManager);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobDataManager() instanceof MybatisSuspendedJobDataManager);
    assertTrue(jtaProcessEngineConfiguration.getTaskDataManager() instanceof MybatisTaskDataManager);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobDataManager() instanceof MybatisTimerJobDataManager);
    assertTrue(
        jtaProcessEngineConfiguration.getVariableInstanceDataManager() instanceof MybatisVariableInstanceDataManager);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEntityManagers()"})
  public void testInitEntityManagers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManager = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setTimerJobEntityManager(timerJobEntityManager);

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    TimerJobEntityManager timerJobEntityManager2 = jtaProcessEngineConfiguration.getTimerJobEntityManager();
    assertTrue(timerJobEntityManager2 instanceof TimerJobEntityManagerImpl);
    assertSame(timerJobEntityManager, timerJobEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEntityManagers()"})
  public void testInitEntityManagers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManager = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setSuspendedJobEntityManager(suspendedJobEntityManager);

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    SuspendedJobEntityManager suspendedJobEntityManager2 = jtaProcessEngineConfiguration.getSuspendedJobEntityManager();
    assertTrue(suspendedJobEntityManager2 instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    assertSame(suspendedJobEntityManager, suspendedJobEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEntityManagers()"})
  public void testInitEntityManagers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManager = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setDeadLetterJobEntityManager(deadLetterJobEntityManager);

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    DeadLetterJobEntityManager deadLetterJobEntityManager2 = jtaProcessEngineConfiguration
        .getDeadLetterJobEntityManager();
    assertTrue(deadLetterJobEntityManager2 instanceof DeadLetterJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    assertSame(deadLetterJobEntityManager, deadLetterJobEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEntityManagers()"})
  public void testInitEntityManagers4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManager = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfiguration, new MybatisProcessDefinitionInfoDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setProcessDefinitionInfoEntityManager(processDefinitionInfoEntityManager);

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    ProcessDefinitionInfoEntityManager processDefinitionInfoEntityManager2 = jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager();
    assertTrue(processDefinitionInfoEntityManager2 instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    assertSame(processDefinitionInfoEntityManager, processDefinitionInfoEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEntityManagers()"})
  public void testInitEntityManagers5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    PropertyEntityManagerImpl propertyEntityManager = new PropertyEntityManagerImpl(processEngineConfiguration,
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setPropertyEntityManager(propertyEntityManager);

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    PropertyEntityManager propertyEntityManager2 = jtaProcessEngineConfiguration.getPropertyEntityManager();
    assertTrue(propertyEntityManager2 instanceof PropertyEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    assertSame(propertyEntityManager, propertyEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEntityManagers()"})
  public void testInitEntityManagers6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    TableDataManagerImpl tableDataManager = new TableDataManagerImpl(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setTableDataManager(tableDataManager);

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    TableDataManager tableDataManager2 = jtaProcessEngineConfiguration.getTableDataManager();
    assertTrue(tableDataManager2 instanceof TableDataManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    assertSame(tableDataManager, tableDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEntityManagers()"})
  public void testInitEntityManagers_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initHistoryManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initHistoryManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initHistoryManager()"})
  public void testInitHistoryManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initHistoryManager();

    // Assert
    HistoryManager historyManager = jtaProcessEngineConfiguration.getHistoryManager();
    assertTrue(historyManager instanceof DefaultHistoryManager);
    assertNull(((DefaultHistoryManager) historyManager).getHistoryLevel());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initHistoryManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initHistoryManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initHistoryManager()"})
  public void testInitHistoryManager2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultHistoryManager historyManager = new DefaultHistoryManager(new JtaProcessEngineConfiguration(),
        HistoryLevel.NONE);

    jtaProcessEngineConfiguration.setHistoryManager(historyManager);

    // Act
    jtaProcessEngineConfiguration.initHistoryManager();

    // Assert that nothing has changed
    HistoryManager historyManager2 = jtaProcessEngineConfiguration.getHistoryManager();
    assertTrue(historyManager2 instanceof DefaultHistoryManager);
    assertEquals(HistoryLevel.NONE, ((DefaultHistoryManager) historyManager2).getHistoryLevel());
    assertSame(historyManager, historyManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initJobManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initJobManager()"})
  public void testInitJobManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultJobManager jobManager = new DefaultJobManager();
    jtaProcessEngineConfiguration.setJobManager(jobManager);

    // Act
    jtaProcessEngineConfiguration.initJobManager();

    // Assert
    JobManager jobManager2 = jtaProcessEngineConfiguration.getJobManager();
    assertTrue(jobManager2 instanceof DefaultJobManager);
    assertSame(jobManager, jobManager2);
    assertSame(jtaProcessEngineConfiguration, ((DefaultJobManager) jobManager2).getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initJobManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initJobManager()"})
  public void testInitJobManager_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initJobManager();

    // Assert
    JobManager jobManager = jtaProcessEngineConfiguration.getJobManager();
    assertTrue(jobManager instanceof DefaultJobManager);
    assertSame(jtaProcessEngineConfiguration, ((DefaultJobManager) jobManager).getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#createDbSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#createDbSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DbSqlSessionFactory ProcessEngineConfigurationImpl.createDbSqlSessionFactory()"})
  public void testCreateDbSqlSessionFactory() {
    // Arrange and Act
    DbSqlSessionFactory actualCreateDbSqlSessionFactoryResult = (new JtaProcessEngineConfiguration())
        .createDbSqlSessionFactory();

    // Assert
    assertEquals("", actualCreateDbSqlSessionFactoryResult.getDatabaseTablePrefix());
    assertNull(actualCreateDbSqlSessionFactoryResult.getDatabaseCatalog());
    assertNull(actualCreateDbSqlSessionFactoryResult.getDatabaseSchema());
    assertNull(actualCreateDbSqlSessionFactoryResult.getDatabaseType());
    assertNull(actualCreateDbSqlSessionFactoryResult.getStatementMappings());
    assertNull(actualCreateDbSqlSessionFactoryResult.getIdGenerator());
    assertNull(actualCreateDbSqlSessionFactoryResult.getSqlSessionFactory());
    assertEquals(100, actualCreateDbSqlSessionFactoryResult.getMaxNrOfStatementsInBulkInsert());
    assertFalse(actualCreateDbSqlSessionFactoryResult.isTablePrefixIsSchema());
    assertTrue(actualCreateDbSqlSessionFactoryResult.getBulkDeleteStatements().isEmpty());
    assertTrue(actualCreateDbSqlSessionFactoryResult.getBulkInsertStatements().isEmpty());
    assertTrue(actualCreateDbSqlSessionFactoryResult.getDeleteStatements().isEmpty());
    assertTrue(actualCreateDbSqlSessionFactoryResult.getInsertStatements().isEmpty());
    assertTrue(actualCreateDbSqlSessionFactoryResult.getSelectStatements().isEmpty());
    assertTrue(actualCreateDbSqlSessionFactoryResult.getUpdateStatements().isEmpty());
    assertTrue(actualCreateDbSqlSessionFactoryResult.isDbHistoryUsed());
    Class<DbSqlSession> expectedSessionType = DbSqlSession.class;
    assertEquals(expectedSessionType, actualCreateDbSqlSessionFactoryResult.getSessionType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setClassLoader(new GroovyClassLoader());

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
    assertTrue(jtaProcessEngineConfiguration.getAllConfigurators().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    List<ProcessEngineConfigurator> allConfigurators = jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(3, allConfigurators.size());
    assertSame(configurator, allConfigurators.get(2));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
    assertTrue(jtaProcessEngineConfiguration.getAllConfigurators().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) AllConfigurators size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_thenJtaProcessEngineConfigurationAllConfiguratorsSizeIsOne() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    List<ProcessEngineConfigurator> allConfigurators = jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(1, allConfigurators.size());
    assertEquals(1, jtaProcessEngineConfiguration.getConfigurators().size());
    assertSame(configurator, allConfigurators.get(0));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) AllConfigurators size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_thenJtaProcessEngineConfigurationAllConfiguratorsSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    List<ProcessEngineConfigurator> allConfigurators = jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(2, allConfigurators.size());
    assertSame(configurator, allConfigurators.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initProcessDefinitionCache()"})
  public void testInitProcessDefinitionCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setProcessDefinitionCache(null);
    jtaProcessEngineConfiguration.setProcessDefinitionCacheLimit(1);

    // Act
    jtaProcessEngineConfiguration.initProcessDefinitionCache();

    // Assert
    DeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache = jtaProcessEngineConfiguration
        .getProcessDefinitionCache();
    assertTrue(processDefinitionCache instanceof DefaultDeploymentCache);
    assertEquals(0, ((DefaultDeploymentCache<ProcessDefinitionCacheEntry>) processDefinitionCache).size());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initProcessDefinitionCache()"})
  public void testInitProcessDefinitionCache2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultDeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache = new DefaultDeploymentCache<>();
    jtaProcessEngineConfiguration.setProcessDefinitionCache(processDefinitionCache);
    jtaProcessEngineConfiguration.setProcessDefinitionCacheLimit(0);

    // Act
    jtaProcessEngineConfiguration.initProcessDefinitionCache();

    // Assert that nothing has changed
    DeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache2 = jtaProcessEngineConfiguration
        .getProcessDefinitionCache();
    assertTrue(processDefinitionCache2 instanceof DefaultDeploymentCache);
    assertEquals(0, ((DefaultDeploymentCache<ProcessDefinitionCacheEntry>) processDefinitionCache2).size());
    assertSame(processDefinitionCache, processDefinitionCache2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initProcessDefinitionCache()"})
  public void testInitProcessDefinitionCache_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initProcessDefinitionCache();

    // Assert
    DeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache = jtaProcessEngineConfiguration
        .getProcessDefinitionCache();
    assertTrue(processDefinitionCache instanceof DefaultDeploymentCache);
    assertEquals(0, ((DefaultDeploymentCache<ProcessDefinitionCacheEntry>) processDefinitionCache).size());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initProcessDefinitionInfoCache()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initProcessDefinitionInfoCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initProcessDefinitionInfoCache()"})
  public void testInitProcessDefinitionInfoCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initProcessDefinitionInfoCache();

    // Assert
    assertEquals(0, jtaProcessEngineConfiguration.processDefinitionInfoCache.size());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initKnowledgeBaseCache()"})
  public void testInitKnowledgeBaseCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setKnowledgeBaseCache(null);
    jtaProcessEngineConfiguration.setKnowledgeBaseCacheLimit(1);

    // Act
    jtaProcessEngineConfiguration.initKnowledgeBaseCache();

    // Assert
    DeploymentCache<Object> knowledgeBaseCache = jtaProcessEngineConfiguration.getKnowledgeBaseCache();
    assertTrue(knowledgeBaseCache instanceof DefaultDeploymentCache);
    assertEquals(0, ((DefaultDeploymentCache<Object>) knowledgeBaseCache).size());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initKnowledgeBaseCache()"})
  public void testInitKnowledgeBaseCache2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultDeploymentCache<Object> knowledgeBaseCache = new DefaultDeploymentCache<>();
    jtaProcessEngineConfiguration.setKnowledgeBaseCache(knowledgeBaseCache);
    jtaProcessEngineConfiguration.setKnowledgeBaseCacheLimit(0);

    // Act
    jtaProcessEngineConfiguration.initKnowledgeBaseCache();

    // Assert that nothing has changed
    DeploymentCache<Object> knowledgeBaseCache2 = jtaProcessEngineConfiguration.getKnowledgeBaseCache();
    assertTrue(knowledgeBaseCache2 instanceof DefaultDeploymentCache);
    assertEquals(0, ((DefaultDeploymentCache<Object>) knowledgeBaseCache2).size());
    assertSame(knowledgeBaseCache, knowledgeBaseCache2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initKnowledgeBaseCache()"})
  public void testInitKnowledgeBaseCache_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initKnowledgeBaseCache();

    // Assert
    DeploymentCache<Object> knowledgeBaseCache = jtaProcessEngineConfiguration.getKnowledgeBaseCache();
    assertTrue(knowledgeBaseCache instanceof DefaultDeploymentCache);
    assertEquals(0, ((DefaultDeploymentCache<Object>) knowledgeBaseCache).size());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeployers(null);
    jtaProcessEngineConfiguration.setCustomPreDeployers(null);
    jtaProcessEngineConfiguration.setCustomPostDeployers(null);
    jtaProcessEngineConfiguration.setDeploymentManager(null);
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(null);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(new CachingAndArtifactsManager());

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeployers(null);
    jtaProcessEngineConfiguration.setCustomPreDeployers(null);
    jtaProcessEngineConfiguration.setCustomPostDeployers(null);
    jtaProcessEngineConfiguration.setDeploymentManager(null);
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(new BpmnDeploymentHelper());
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeployers(null);
    jtaProcessEngineConfiguration.setCustomPreDeployers(null);
    jtaProcessEngineConfiguration.setCustomPostDeployers(null);
    jtaProcessEngineConfiguration.setDeploymentManager(null);
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(new EventSubscriptionManager());
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(null);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreDeployers(new ArrayList<>());

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostDeployers(new ArrayList<>());

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeploymentManager(new DeploymentManager());

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert that nothing has changed
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getDeployers());
    assertNull(deploymentManager.getProcessEngineConfiguration());
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <ul>
   *   <li>Given {@link BpmnDeploymentHelper} (default constructor) TimerManager is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers_givenBpmnDeploymentHelperTimerManagerIsNull() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    bpmnDeploymentHelper.setTimerManager(null);
    bpmnDeploymentHelper.setEventSubscriptionManager(new EventSubscriptionManager());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeployers(null);
    jtaProcessEngineConfiguration.setCustomPreDeployers(null);
    jtaProcessEngineConfiguration.setCustomPostDeployers(null);
    jtaProcessEngineConfiguration.setDeploymentManager(null);
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <ul>
   *   <li>Given {@link BpmnDeploymentHelper} (default constructor) TimerManager is {@link TimerManager} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers_givenBpmnDeploymentHelperTimerManagerIsTimerManager() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    bpmnDeploymentHelper.setTimerManager(new TimerManager());
    bpmnDeploymentHelper.setEventSubscriptionManager(null);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeployers(null);
    jtaProcessEngineConfiguration.setCustomPreDeployers(null);
    jtaProcessEngineConfiguration.setCustomPostDeployers(null);
    jtaProcessEngineConfiguration.setDeploymentManager(null);
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) BpmnDeployer is {@link BpmnDeployer} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers_givenJtaProcessEngineConfigurationBpmnDeployerIsBpmnDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnDeployer(new BpmnDeployer());

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) TimerManager is {@link TimerManager} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers_givenJtaProcessEngineConfigurationTimerManagerIsTimerManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeployers(null);
    jtaProcessEngineConfiguration.setCustomPreDeployers(null);
    jtaProcessEngineConfiguration.setCustomPostDeployers(null);
    jtaProcessEngineConfiguration.setDeploymentManager(null);
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(new TimerManager());
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(null);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <ul>
   *   <li>Given {@link ParsedDeploymentBuilderFactory} (default constructor) BpmnParser is {@link BpmnParser} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers_givenParsedDeploymentBuilderFactoryBpmnParserIsBpmnParser() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeployers(null);
    jtaProcessEngineConfiguration.setCustomPreDeployers(null);
    jtaProcessEngineConfiguration.setCustomPostDeployers(null);
    jtaProcessEngineConfiguration.setDeploymentManager(null);
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(null);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
    List<Deployer> expectedDeployers = jtaProcessEngineConfiguration.getDeployers();
    assertSame(expectedDeployers, deploymentManager.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDeployers()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) Deployers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDeployers()"})
  public void testInitDeployers_thenJtaProcessEngineConfigurationDeployersEmpty() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<Deployer> deployers = new ArrayList<>();
    jtaProcessEngineConfiguration.setDeployers(deployers);

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
    DeploymentManager deploymentManager = jtaProcessEngineConfiguration.getDeploymentManager();
    assertNull(deploymentManager.getKnowledgeBaseCache());
    assertNull(deploymentManager.getProcessDefinitionCache());
    assertNull(deploymentManager.getProcessDefinitionInfoCache());
    assertNull(deploymentManager.getDeploymentEntityManager());
    assertNull(deploymentManager.getProcessDefinitionEntityManager());
    assertTrue(jtaProcessEngineConfiguration.getDeployers().isEmpty());
    assertSame(deployers, deploymentManager.getDeployers());
    assertSame(jtaProcessEngineConfiguration, deploymentManager.getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBpmnDeployerDependencies()}.
   * <ul>
   *   <li>Then calls {@link ParsedDeploymentBuilderFactory#getBpmnParser()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBpmnDeployerDependencies()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBpmnDeployerDependencies()"})
  public void testInitBpmnDeployerDependencies_thenCallsGetBpmnParser() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = mock(ParsedDeploymentBuilderFactory.class);
    when(parsedDeploymentBuilderFactory.getBpmnParser()).thenThrow(new ActivitiException("An error occurred"));

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(null);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(new CachingAndArtifactsManager());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initBpmnDeployerDependencies());
    verify(parsedDeploymentBuilderFactory).getBpmnParser();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBpmnDeployerDependencies()}.
   * <ul>
   *   <li>Then calls {@link BpmnDeploymentHelper#getTimerManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBpmnDeployerDependencies()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBpmnDeployerDependencies()"})
  public void testInitBpmnDeployerDependencies_thenCallsGetTimerManager() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = mock(BpmnDeploymentHelper.class);
    when(bpmnDeploymentHelper.getTimerManager()).thenThrow(new ActivitiException("An error occurred"));

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initBpmnDeployerDependencies());
    verify(bpmnDeploymentHelper).getTimerManager();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(null);
    CachingAndArtifactsManager cachingAndArtifactsManager = new CachingAndArtifactsManager();
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(cachingAndArtifactsManager);

    // Act
    Collection<? extends Deployer> actualDefaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    Deployer getResult = ((List<? extends Deployer>) actualDefaultDeployers).get(0);
    assertTrue(getResult instanceof BpmnDeployer);
    assertNull(((BpmnDeployer) getResult).getExParsedDeploymentBuilderFactory().getBpmnParser());
    assertNull(((BpmnDeployer) getResult).getIdGenerator());
    assertSame(cachingAndArtifactsManager, ((BpmnDeployer) getResult).getCachingAndArtifcatsManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    Collection<? extends Deployer> actualDefaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    Deployer getResult = ((List<? extends Deployer>) actualDefaultDeployers).get(0);
    assertTrue(getResult instanceof BpmnDeployer);
    assertNull(((BpmnDeployer) getResult).getExParsedDeploymentBuilderFactory().getBpmnParser());
    assertNull(((BpmnDeployer) getResult).getIdGenerator());
    assertSame(bpmnDeploymentHelper, ((BpmnDeployer) getResult).getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    jtaProcessEngineConfiguration.setEventSubscriptionManager(eventSubscriptionManager);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(null);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    Collection<? extends Deployer> actualDefaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    Deployer getResult = ((List<? extends Deployer>) actualDefaultDeployers).get(0);
    assertTrue(getResult instanceof BpmnDeployer);
    assertNull(((BpmnDeployer) getResult).getExParsedDeploymentBuilderFactory().getBpmnParser());
    assertNull(((BpmnDeployer) getResult).getIdGenerator());
    assertSame(eventSubscriptionManager,
        ((BpmnDeployer) getResult).getBpmnDeploymentHelper().getEventSubscriptionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    TimerManager timerManager = new TimerManager();
    jtaProcessEngineConfiguration.setTimerManager(timerManager);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(null);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    Collection<? extends Deployer> actualDefaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    Deployer getResult = ((List<? extends Deployer>) actualDefaultDeployers).get(0);
    assertTrue(getResult instanceof BpmnDeployer);
    assertNull(((BpmnDeployer) getResult).getExParsedDeploymentBuilderFactory().getBpmnParser());
    assertNull(((BpmnDeployer) getResult).getIdGenerator());
    assertSame(timerManager, ((BpmnDeployer) getResult).getBpmnDeploymentHelper().getTimerManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers5() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(null);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    Collection<? extends Deployer> actualDefaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    Deployer getResult = ((List<? extends Deployer>) actualDefaultDeployers).get(0);
    assertTrue(getResult instanceof BpmnDeployer);
    assertSame(parsedDeploymentBuilderFactory, ((BpmnDeployer) getResult).getExParsedDeploymentBuilderFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   * <ul>
   *   <li>Given {@link BpmnDeploymentHelper} (default constructor) TimerManager is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers_givenBpmnDeploymentHelperTimerManagerIsNull() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    bpmnDeploymentHelper.setTimerManager(null);
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    bpmnDeploymentHelper.setEventSubscriptionManager(eventSubscriptionManager);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    Collection<? extends Deployer> actualDefaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    Deployer getResult = ((List<? extends Deployer>) actualDefaultDeployers).get(0);
    assertTrue(getResult instanceof BpmnDeployer);
    assertNull(((BpmnDeployer) getResult).getExParsedDeploymentBuilderFactory().getBpmnParser());
    assertNull(((BpmnDeployer) getResult).getIdGenerator());
    BpmnDeploymentHelper bpmnDeploymentHelper2 = ((BpmnDeployer) getResult).getBpmnDeploymentHelper();
    assertSame(bpmnDeploymentHelper, bpmnDeploymentHelper2);
    assertSame(eventSubscriptionManager, bpmnDeploymentHelper2.getEventSubscriptionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   * <ul>
   *   <li>Given {@link BpmnDeploymentHelper} (default constructor) TimerManager is {@link TimerManager} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers_givenBpmnDeploymentHelperTimerManagerIsTimerManager() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    TimerManager timerManager = new TimerManager();
    bpmnDeploymentHelper.setTimerManager(timerManager);
    bpmnDeploymentHelper.setEventSubscriptionManager(null);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnDeployer(null);
    jtaProcessEngineConfiguration.setParsedDeploymentBuilderFactory(null);
    jtaProcessEngineConfiguration.setTimerManager(null);
    jtaProcessEngineConfiguration.setEventSubscriptionManager(null);
    jtaProcessEngineConfiguration.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    jtaProcessEngineConfiguration.setCachingAndArtifactsManager(null);

    // Act
    Collection<? extends Deployer> actualDefaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    Deployer getResult = ((List<? extends Deployer>) actualDefaultDeployers).get(0);
    assertTrue(getResult instanceof BpmnDeployer);
    assertNull(((BpmnDeployer) getResult).getExParsedDeploymentBuilderFactory().getBpmnParser());
    assertNull(((BpmnDeployer) getResult).getIdGenerator());
    BpmnDeploymentHelper bpmnDeploymentHelper2 = ((BpmnDeployer) getResult).getBpmnDeploymentHelper();
    assertSame(bpmnDeploymentHelper, bpmnDeploymentHelper2);
    assertSame(timerManager, bpmnDeploymentHelper2.getTimerManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers_givenJtaProcessEngineConfiguration() {
    // Arrange and Act
    Collection<? extends Deployer> actualDefaultDeployers = (new JtaProcessEngineConfiguration()).getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    Deployer getResult = ((List<? extends Deployer>) actualDefaultDeployers).get(0);
    assertTrue(getResult instanceof BpmnDeployer);
    assertNull(((BpmnDeployer) getResult).getExParsedDeploymentBuilderFactory().getBpmnParser());
    assertNull(((BpmnDeployer) getResult).getIdGenerator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   * <ul>
   *   <li>Then return first is {@link BpmnDeployer} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers_thenReturnFirstIsBpmnDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    jtaProcessEngineConfiguration.setBpmnDeployer(bpmnDeployer);

    // Act
    Collection<? extends Deployer> actualDefaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    assertSame(bpmnDeployer, ((List<? extends Deployer>) actualDefaultDeployers).get(0));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initListenerFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initListenerFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initListenerFactory()"})
  public void testInitListenerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initListenerFactory();

    // Assert
    ListenerFactory listenerFactory = jtaProcessEngineConfiguration.getListenerFactory();
    assertTrue(listenerFactory instanceof DefaultListenerFactory);
    assertTrue(((DefaultListenerFactory) listenerFactory)
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(((DefaultListenerFactory) listenerFactory)
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(((DefaultListenerFactory) listenerFactory).getExpressionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initListenerFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initListenerFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initListenerFactory()"})
  public void testInitListenerFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultListenerFactory listenerFactory = new DefaultListenerFactory();
    jtaProcessEngineConfiguration.setListenerFactory(listenerFactory);

    // Act
    jtaProcessEngineConfiguration.initListenerFactory();

    // Assert that nothing has changed
    assertSame(listenerFactory, jtaProcessEngineConfiguration.getListenerFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initListenerFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initListenerFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initListenerFactory()"})
  public void testInitListenerFactory3() {
    // Arrange
    DefaultListenerFactory listenerFactory = new DefaultListenerFactory();
    listenerFactory.setExpressionManager(new ExpressionManager());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setListenerFactory(listenerFactory);

    // Act
    jtaProcessEngineConfiguration.initListenerFactory();

    // Assert that nothing has changed
    assertSame(listenerFactory, jtaProcessEngineConfiguration.getListenerFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBehaviorFactory()"})
  public void testInitBehaviorFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initBehaviorFactory();

    // Assert
    ActivityBehaviorFactory activityBehaviorFactory = jtaProcessEngineConfiguration.getActivityBehaviorFactory();
    assertTrue(activityBehaviorFactory instanceof DefaultActivityBehaviorFactory);
    assertTrue(((DefaultActivityBehaviorFactory) activityBehaviorFactory)
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(((DefaultActivityBehaviorFactory) activityBehaviorFactory)
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(((DefaultActivityBehaviorFactory) activityBehaviorFactory).getExpressionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBehaviorFactory()"})
  public void testInitBehaviorFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();
    jtaProcessEngineConfiguration.setActivityBehaviorFactory(activityBehaviorFactory);

    // Act
    jtaProcessEngineConfiguration.initBehaviorFactory();

    // Assert that nothing has changed
    assertSame(activityBehaviorFactory, jtaProcessEngineConfiguration.getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBehaviorFactory()"})
  public void testInitBehaviorFactory3() {
    // Arrange
    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();
    activityBehaviorFactory.setExpressionManager(new ExpressionManager());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setActivityBehaviorFactory(activityBehaviorFactory);

    // Act
    jtaProcessEngineConfiguration.initBehaviorFactory();

    // Assert that nothing has changed
    assertSame(activityBehaviorFactory, jtaProcessEngineConfiguration.getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBpmnParser()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBpmnParser()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBpmnParser()"})
  public void testInitBpmnParser_thenThrowActivitiException() {
    // Arrange
    BpmnParser bpmnParser = mock(BpmnParser.class);
    doThrow(new ActivitiException("An error occurred")).when(bpmnParser)
        .setBpmnParseFactory(Mockito.<BpmnParseFactory>any());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnParser(bpmnParser);
    jtaProcessEngineConfiguration.setBpmnParseFactory(null);
    jtaProcessEngineConfiguration.setPreBpmnParseHandlers(null);
    jtaProcessEngineConfiguration.setPostBpmnParseHandlers(null);
    jtaProcessEngineConfiguration.setCustomDefaultBpmnParseHandlers(new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initBpmnParser());
    verify(bpmnParser).setBpmnParseFactory(isA(BpmnParseFactory.class));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDefaultBpmnParseHandlers()"})
  public void testGetDefaultBpmnParseHandlers() {
    // Arrange
    ArrayList<BpmnParseHandler> customDefaultBpmnParseHandlers = new ArrayList<>();
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    customDefaultBpmnParseHandlers.add(adhocSubProcessParseHandler);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomDefaultBpmnParseHandlers(customDefaultBpmnParseHandlers);

    // Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers = jtaProcessEngineConfiguration.getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = actualDefaultBpmnParseHandlers.get(25);
    assertTrue(getResult instanceof AdhocSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(0) instanceof BoundaryEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(1) instanceof BusinessRuleParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(2) instanceof CallActivityParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(3) instanceof CancelEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(4) instanceof CompensateEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(5) instanceof EndEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(Float.PRECISION) instanceof EventSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(26) instanceof TaskParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(27) instanceof TimerEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(28) instanceof TransactionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(29) instanceof UserTaskParseHandler);
    assertSame(adhocSubProcessParseHandler, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDefaultBpmnParseHandlers()"})
  public void testGetDefaultBpmnParseHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange and Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers = (new JtaProcessEngineConfiguration())
        .getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    assertTrue(actualDefaultBpmnParseHandlers.get(25) instanceof AdhocSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(0) instanceof BoundaryEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(1) instanceof BusinessRuleParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(2) instanceof CallActivityParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(3) instanceof CancelEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(4) instanceof CompensateEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(5) instanceof EndEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(Float.PRECISION) instanceof EventSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(26) instanceof TaskParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(27) instanceof TimerEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(28) instanceof TransactionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(29) instanceof UserTaskParseHandler);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   * <ul>
   *   <li>Then return size is thirty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDefaultBpmnParseHandlers()"})
  public void testGetDefaultBpmnParseHandlers_thenReturnSizeIsThirty() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomDefaultBpmnParseHandlers(new ArrayList<>());

    // Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers = jtaProcessEngineConfiguration.getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    assertTrue(actualDefaultBpmnParseHandlers.get(25) instanceof AdhocSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(0) instanceof BoundaryEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(1) instanceof BusinessRuleParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(2) instanceof CallActivityParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(3) instanceof CancelEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(4) instanceof CompensateEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(5) instanceof EndEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(Float.PRECISION) instanceof EventSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(26) instanceof TaskParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(27) instanceof TimerEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(28) instanceof TransactionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(29) instanceof UserTaskParseHandler);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initClock()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) Clock is {@link DefaultClockImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initClock()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initClock()"})
  public void testInitClock_thenJtaProcessEngineConfigurationClockIsDefaultClockImpl() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultClockImpl clock = new DefaultClockImpl();
    jtaProcessEngineConfiguration.setClock(clock);

    // Act
    jtaProcessEngineConfiguration.initClock();

    // Assert that nothing has changed
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAgendaFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAgendaFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAgendaFactory()"})
  public void testInitAgendaFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initAgendaFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getEngineAgendaFactory() instanceof DefaultActivitiEngineAgendaFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initJobHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initJobHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initJobHandlers()"})
  public void testInitJobHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomJobHandlers(new ArrayList<>());

    // Act
    jtaProcessEngineConfiguration.initJobHandlers();

    // Assert
    Map<String, JobHandler> jobHandlers = jtaProcessEngineConfiguration.getJobHandlers();
    assertEquals(6, jobHandlers.size());
    JobHandler getResult = jobHandlers.get("async-continuation");
    assertTrue(getResult instanceof AsyncContinuationJobHandler);
    JobHandler getResult2 = jobHandlers.get("event");
    assertTrue(getResult2 instanceof ProcessEventJobHandler);
    JobHandler getResult3 = jobHandlers.get("activate-processdefinition");
    assertTrue(getResult3 instanceof TimerActivateProcessDefinitionHandler);
    JobHandler getResult4 = jobHandlers.get("timer-start-event");
    assertTrue(getResult4 instanceof TimerStartEventJobHandler);
    JobHandler getResult5 = jobHandlers.get("suspend-processdefinition");
    assertTrue(getResult5 instanceof TimerSuspendProcessDefinitionHandler);
    JobHandler getResult6 = jobHandlers.get("trigger-timer");
    assertTrue(getResult6 instanceof TriggerTimerEventJobHandler);
    assertEquals("activate-processdefinition", getResult3.getType());
    assertEquals("async-continuation", getResult.getType());
    assertEquals("event", getResult2.getType());
    assertEquals("suspend-processdefinition", getResult5.getType());
    assertEquals("timer-start-event", getResult4.getType());
    assertEquals("trigger-timer", getResult6.getType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initJobHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initJobHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initJobHandlers()"})
  public void testInitJobHandlers2() {
    // Arrange
    ArrayList<JobHandler> customJobHandlers = new ArrayList<>();
    AsyncContinuationJobHandler asyncContinuationJobHandler = new AsyncContinuationJobHandler();
    customJobHandlers.add(asyncContinuationJobHandler);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomJobHandlers(customJobHandlers);

    // Act
    jtaProcessEngineConfiguration.initJobHandlers();

    // Assert
    Map<String, JobHandler> jobHandlers = jtaProcessEngineConfiguration.getJobHandlers();
    assertEquals(6, jobHandlers.size());
    JobHandler getResult = jobHandlers.get("event");
    assertTrue(getResult instanceof ProcessEventJobHandler);
    JobHandler getResult2 = jobHandlers.get("activate-processdefinition");
    assertTrue(getResult2 instanceof TimerActivateProcessDefinitionHandler);
    JobHandler getResult3 = jobHandlers.get("timer-start-event");
    assertTrue(getResult3 instanceof TimerStartEventJobHandler);
    JobHandler getResult4 = jobHandlers.get("suspend-processdefinition");
    assertTrue(getResult4 instanceof TimerSuspendProcessDefinitionHandler);
    JobHandler getResult5 = jobHandlers.get("trigger-timer");
    assertTrue(getResult5 instanceof TriggerTimerEventJobHandler);
    assertEquals("activate-processdefinition", getResult2.getType());
    assertEquals("event", getResult.getType());
    assertEquals("suspend-processdefinition", getResult4.getType());
    assertEquals("timer-start-event", getResult3.getType());
    assertEquals("trigger-timer", getResult5.getType());
    assertSame(asyncContinuationJobHandler, jobHandlers.get("async-continuation"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initJobHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initJobHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initJobHandlers()"})
  public void testInitJobHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initJobHandlers();

    // Assert
    Map<String, JobHandler> jobHandlers = jtaProcessEngineConfiguration.getJobHandlers();
    assertEquals(6, jobHandlers.size());
    JobHandler getResult = jobHandlers.get("async-continuation");
    assertTrue(getResult instanceof AsyncContinuationJobHandler);
    JobHandler getResult2 = jobHandlers.get("event");
    assertTrue(getResult2 instanceof ProcessEventJobHandler);
    JobHandler getResult3 = jobHandlers.get("activate-processdefinition");
    assertTrue(getResult3 instanceof TimerActivateProcessDefinitionHandler);
    JobHandler getResult4 = jobHandlers.get("timer-start-event");
    assertTrue(getResult4 instanceof TimerStartEventJobHandler);
    JobHandler getResult5 = jobHandlers.get("suspend-processdefinition");
    assertTrue(getResult5 instanceof TimerSuspendProcessDefinitionHandler);
    JobHandler getResult6 = jobHandlers.get("trigger-timer");
    assertTrue(getResult6 instanceof TriggerTimerEventJobHandler);
    assertEquals("activate-processdefinition", getResult3.getType());
    assertEquals("async-continuation", getResult.getType());
    assertEquals("event", getResult2.getType());
    assertEquals("suspend-processdefinition", getResult5.getType());
    assertEquals("timer-start-event", getResult4.getType());
    assertEquals("trigger-timer", getResult6.getType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    jtaProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner(null);

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert that nothing has changed
    AsyncExecutor asyncExecutor2 = jtaProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor2 instanceof DefaultAsyncJobExecutor);
    assertSame(asyncExecutor, asyncExecutor2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorMessageQueueMode(true);

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAsyncExecutor() instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutorPerTenantAsyncExecutor asyncExecutor = new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    jtaProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert that nothing has changed
    AsyncExecutor asyncExecutor2 = jtaProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor2 instanceof ExecutorPerTenantAsyncExecutor);
    assertSame(asyncExecutor, asyncExecutor2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutor(null);
    LinkedBlockingDeque<Runnable> asyncExecutorThreadPoolQueue = new LinkedBlockingDeque<>();
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(asyncExecutorThreadPoolQueue);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor = jtaProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertEquals("foo", asyncExecutor.getLockOwner());
    assertFalse(((DefaultAsyncJobExecutor) asyncExecutor).isMessageQueueMode());
    assertSame(asyncExecutorThreadPoolQueue, ((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAsyncExecutor() instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) AsyncExecutor is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor_givenJtaProcessEngineConfigurationAsyncExecutorIsNull() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutor(null);
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAsyncExecutor() instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initHistoryLevel()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) HistoryLevel is {@code AUDIT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initHistoryLevel()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initHistoryLevel()"})
  public void testInitHistoryLevel_thenJtaProcessEngineConfigurationHistoryLevelIsAudit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initHistoryLevel();

    // Assert
    assertEquals(HistoryLevel.AUDIT, jtaProcessEngineConfiguration.getHistoryLevel());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initHistoryLevel()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) HistoryLevel is {@code NONE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initHistoryLevel()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initHistoryLevel()"})
  public void testInitHistoryLevel_thenJtaProcessEngineConfigurationHistoryLevelIsNone() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setHistoryLevel(HistoryLevel.NONE);

    // Act
    jtaProcessEngineConfiguration.initHistoryLevel();

    // Assert that nothing has changed
    assertEquals(HistoryLevel.NONE, jtaProcessEngineConfiguration.getHistoryLevel());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initIdGenerator()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initIdGenerator()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initIdGenerator()"})
  public void testInitIdGenerator() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setIdGenerator(null);
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(null);
    jtaProcessEngineConfiguration.setIdGeneratorDataSourceJndiName("foo");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initIdGenerator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initIdGenerator()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initIdGenerator()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initIdGenerator()"})
  public void testInitIdGenerator2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenReturn("Database Product Name");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setIdGenerator(null);
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setIdGeneratorDataSourceJndiName(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initIdGenerator());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initIdGenerator()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initIdGenerator()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initIdGenerator()"})
  public void testInitIdGenerator3() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseProductName()).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).close();
    DataSource idGeneratorDataSource = mock(DataSource.class);
    when(idGeneratorDataSource.getConnection()).thenReturn(connection);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setIdGenerator(null);
    jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource);
    jtaProcessEngineConfiguration.setIdGeneratorDataSourceJndiName(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initIdGenerator());
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(idGeneratorDataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandContextFactory()"})
  public void testInitCommandContextFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandContextFactory(new CommandContextFactory());

    // Act
    jtaProcessEngineConfiguration.initCommandContextFactory();

    // Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.getCommandContextFactory().getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandContextFactory()"})
  public void testInitCommandContextFactory_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initCommandContextFactory();

    // Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.getCommandContextFactory().getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initTransactionContextFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initTransactionContextFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initTransactionContextFactory()"})
  public void testInitTransactionContextFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionContextFactory(null);

    // Act
    jtaProcessEngineConfiguration.initTransactionContextFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getTransactionContextFactory() instanceof JtaTransactionContextFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initTransactionContextFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initTransactionContextFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initTransactionContextFactory()"})
  public void testInitTransactionContextFactory_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initTransactionContextFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getTransactionContextFactory() instanceof JtaTransactionContextFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultVariableTypes variableTypes = new DefaultVariableTypes();
    jtaProcessEngineConfiguration.setVariableTypes(variableTypes);

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert that nothing has changed
    VariableTypes variableTypes2 = jtaProcessEngineConfiguration.getVariableTypes();
    assertTrue(variableTypes2 instanceof DefaultVariableTypes);
    assertSame(variableTypes, variableTypes2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSerializePOJOsInVariablesToJson(true);

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreVariableTypes(new ArrayList<>());

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostVariableTypes(new ArrayList<>());

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMaxLengthStringVariableType(3);

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BigDecimalType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes_givenArrayListAddBigDecimalType() {
    // Arrange
    ArrayList<VariableType> customPreVariableTypes = new ArrayList<>();
    customPreVariableTypes.add(new BigDecimalType());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreVariableTypes(customPreVariableTypes);

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BigDecimalType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes_givenArrayListAddBigDecimalType2() {
    // Arrange
    ArrayList<VariableType> customPostVariableTypes = new ArrayList<>();
    customPostVariableTypes.add(new BigDecimalType());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostVariableTypes(customPostVariableTypes);

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link HistoricJPAEntityListVariableType} (default constructor) ForceCacheable is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes_givenHistoricJPAEntityListVariableTypeForceCacheableIsTrue() {
    // Arrange
    HistoricJPAEntityListVariableType historicJPAEntityListVariableType = new HistoricJPAEntityListVariableType();
    historicJPAEntityListVariableType.setForceCacheable(true);

    ArrayList<VariableType> customPreVariableTypes = new ArrayList<>();
    customPreVariableTypes.add(historicJPAEntityListVariableType);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreVariableTypes(customPreVariableTypes);

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link HistoricJPAEntityListVariableType} (default constructor) ForceCacheable is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes_givenHistoricJPAEntityListVariableTypeForceCacheableIsTrue2() {
    // Arrange
    HistoricJPAEntityListVariableType historicJPAEntityListVariableType = new HistoricJPAEntityListVariableType();
    historicJPAEntityListVariableType.setForceCacheable(true);

    ArrayList<VariableType> customPostVariableTypes = new ArrayList<>();
    customPostVariableTypes.add(historicJPAEntityListVariableType);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostVariableTypes(customPostVariableTypes);

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link HistoricJPAEntityVariableType} (default constructor) ForceCacheable is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes_givenHistoricJPAEntityVariableTypeForceCacheableIsTrue() {
    // Arrange
    HistoricJPAEntityVariableType historicJPAEntityVariableType = new HistoricJPAEntityVariableType();
    historicJPAEntityVariableType.setForceCacheable(true);

    ArrayList<VariableType> customPreVariableTypes = new ArrayList<>();
    customPreVariableTypes.add(historicJPAEntityVariableType);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreVariableTypes(customPreVariableTypes);

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link HistoricJPAEntityVariableType} (default constructor) ForceCacheable is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes_givenHistoricJPAEntityVariableTypeForceCacheableIsTrue2() {
    // Arrange
    HistoricJPAEntityVariableType historicJPAEntityVariableType = new HistoricJPAEntityVariableType();
    historicJPAEntityVariableType.setForceCacheable(true);

    ArrayList<VariableType> customPostVariableTypes = new ArrayList<>();
    customPostVariableTypes.add(historicJPAEntityVariableType);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostVariableTypes(customPostVariableTypes);

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initVariableTypes()"})
  public void testInitVariableTypes_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   * <ul>
   *   <li>Then return {@link ProcessEngineConfigurationImpl#DEFAULT_GENERIC_MAX_LENGTH_STRING}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthString()"})
  public void testGetMaxLengthString_thenReturnDefault_generic_max_length_string() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfigurationImpl.DEFAULT_GENERIC_MAX_LENGTH_STRING,
        (new JtaProcessEngineConfiguration()).getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   * <ul>
   *   <li>Then return {@link ProcessEngineConfigurationImpl#DEFAULT_ORACLE_MAX_LENGTH_STRING}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthString()"})
  public void testGetMaxLengthString_thenReturnDefault_oracle_max_length_string() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDatabaseType(ProcessEngineConfigurationImpl.DATABASE_TYPE_ORACLE);

    // Act and Assert
    assertEquals(ProcessEngineConfigurationImpl.DEFAULT_ORACLE_MAX_LENGTH_STRING,
        jtaProcessEngineConfiguration.getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthString()"})
  public void testGetMaxLengthString_thenReturnThree() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMaxLengthStringVariableType(3);

    // Act and Assert
    assertEquals(3, jtaProcessEngineConfiguration.getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initScriptingEngines()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initScriptingEngines()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initScriptingEngines()"})
  public void testInitScriptingEngines() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initScriptingEngines();

    // Assert
    List<ResolverFactory> resolverFactories = jtaProcessEngineConfiguration.getResolverFactories();
    assertEquals(2, resolverFactories.size());
    assertTrue(resolverFactories.get(1) instanceof BeansResolverFactory);
    assertTrue(resolverFactories.get(0) instanceof VariableScopeResolverFactory);
    ScriptingEngines scriptingEngines = jtaProcessEngineConfiguration.getScriptingEngines();
    assertTrue(scriptingEngines.isCacheScriptingEngines());
    assertSame(resolverFactories, scriptingEngines.getScriptBindingsFactory().getResolverFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initScriptingEngines()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initScriptingEngines()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initScriptingEngines()"})
  public void testInitScriptingEngines2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setResolverFactories(null);
    jtaProcessEngineConfiguration.setScriptingEngines(new ScriptingEngines(new ScriptEngineManager()));

    // Act
    jtaProcessEngineConfiguration.initScriptingEngines();

    // Assert
    List<ResolverFactory> resolverFactories = jtaProcessEngineConfiguration.getResolverFactories();
    assertEquals(2, resolverFactories.size());
    assertTrue(resolverFactories.get(1) instanceof BeansResolverFactory);
    assertTrue(resolverFactories.get(0) instanceof VariableScopeResolverFactory);
    assertTrue(jtaProcessEngineConfiguration.getScriptingEngines().isCacheScriptingEngines());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initScriptingEngines()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) ResolverFactories Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initScriptingEngines()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initScriptingEngines()"})
  public void testInitScriptingEngines_thenJtaProcessEngineConfigurationResolverFactoriesEmpty() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    jtaProcessEngineConfiguration.setResolverFactories(resolverFactories);
    jtaProcessEngineConfiguration.setScriptingEngines(null);

    // Act
    jtaProcessEngineConfiguration.initScriptingEngines();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getResolverFactories().isEmpty());
    ScriptingEngines scriptingEngines = jtaProcessEngineConfiguration.getScriptingEngines();
    assertTrue(scriptingEngines.isCacheScriptingEngines());
    assertSame(resolverFactories, scriptingEngines.getScriptBindingsFactory().getResolverFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initExpressionManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initExpressionManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initExpressionManager()"})
  public void testInitExpressionManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setExpressionManager(null);
    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    jtaProcessEngineConfiguration.setCustomFunctionProviders(customFunctionProviders);

    // Act
    jtaProcessEngineConfiguration.initExpressionManager();

    // Assert
    ExpressionManager expressionManager = jtaProcessEngineConfiguration.getExpressionManager();
    assertNull(expressionManager.getBeans());
    assertSame(customFunctionProviders, expressionManager.getCustomFunctionProviders());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initExpressionManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initExpressionManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initExpressionManager()"})
  public void testInitExpressionManager2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setExpressionManager(new ExpressionManager());
    jtaProcessEngineConfiguration.setCustomFunctionProviders(null);

    // Act
    jtaProcessEngineConfiguration.initExpressionManager();

    // Assert that nothing has changed
    ExpressionManager expressionManager = jtaProcessEngineConfiguration.getExpressionManager();
    assertNull(expressionManager.getCustomFunctionProviders());
    assertNull(expressionManager.getBeans());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initExpressionManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initExpressionManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initExpressionManager()"})
  public void testInitExpressionManager_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initExpressionManager();

    // Assert
    ExpressionManager expressionManager = jtaProcessEngineConfiguration.getExpressionManager();
    assertNull(expressionManager.getCustomFunctionProviders());
    assertNull(expressionManager.getBeans());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBusinessCalendarManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBusinessCalendarManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBusinessCalendarManager()"})
  public void testInitBusinessCalendarManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initBusinessCalendarManager();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getBusinessCalendarManager() instanceof MapBusinessCalendarManager);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDelegateInterceptor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDelegateInterceptor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDelegateInterceptor()"})
  public void testInitDelegateInterceptor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initDelegateInterceptor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getDelegateInterceptor() instanceof DefaultDelegateInterceptor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventHandlers()"})
  public void testInitEventHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventHandlers(null);
    jtaProcessEngineConfiguration.setCustomEventHandlers(new ArrayList<>());

    // Act
    jtaProcessEngineConfiguration.initEventHandlers();

    // Assert
    Map<String, EventHandler> eventHandlers = jtaProcessEngineConfiguration.getEventHandlers();
    assertEquals(3, eventHandlers.size());
    EventHandler getResult = eventHandlers.get("compensate");
    assertTrue(getResult instanceof CompensationEventHandler);
    EventHandler getResult2 = eventHandlers.get("message");
    assertTrue(getResult2 instanceof MessageEventHandler);
    EventHandler getResult3 = eventHandlers.get("signal");
    assertTrue(getResult3 instanceof SignalEventHandler);
    assertEquals("compensate", getResult.getEventHandlerType());
    assertEquals("message", getResult2.getEventHandlerType());
    assertEquals("signal", getResult3.getEventHandlerType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventHandlers()"})
  public void testInitEventHandlers2() {
    // Arrange
    ArrayList<EventHandler> customEventHandlers = new ArrayList<>();
    CompensationEventHandler compensationEventHandler = new CompensationEventHandler();
    customEventHandlers.add(compensationEventHandler);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventHandlers(null);
    jtaProcessEngineConfiguration.setCustomEventHandlers(customEventHandlers);

    // Act
    jtaProcessEngineConfiguration.initEventHandlers();

    // Assert
    Map<String, EventHandler> eventHandlers = jtaProcessEngineConfiguration.getEventHandlers();
    assertEquals(3, eventHandlers.size());
    EventHandler getResult = eventHandlers.get("message");
    assertTrue(getResult instanceof MessageEventHandler);
    EventHandler getResult2 = eventHandlers.get("signal");
    assertTrue(getResult2 instanceof SignalEventHandler);
    assertEquals("message", getResult.getEventHandlerType());
    assertEquals("signal", getResult2.getEventHandlerType());
    assertSame(compensationEventHandler, eventHandlers.get("compensate"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventHandlers()"})
  public void testInitEventHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initEventHandlers();

    // Assert
    Map<String, EventHandler> eventHandlers = jtaProcessEngineConfiguration.getEventHandlers();
    assertEquals(3, eventHandlers.size());
    EventHandler getResult = eventHandlers.get("compensate");
    assertTrue(getResult instanceof CompensationEventHandler);
    EventHandler getResult2 = eventHandlers.get("message");
    assertTrue(getResult2 instanceof MessageEventHandler);
    EventHandler getResult3 = eventHandlers.get("signal");
    assertTrue(getResult3 instanceof SignalEventHandler);
    assertEquals("compensate", getResult.getEventHandlerType());
    assertEquals("message", getResult2.getEventHandlerType());
    assertEquals("signal", getResult3.getEventHandlerType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventHandlers()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) EventHandlers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventHandlers()"})
  public void testInitEventHandlers_thenJtaProcessEngineConfigurationEventHandlersEmpty() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventHandlers(new HashMap<>());
    jtaProcessEngineConfiguration.setCustomEventHandlers(null);

    // Act
    jtaProcessEngineConfiguration.initEventHandlers();

    // Assert that nothing has changed
    assertTrue(jtaProcessEngineConfiguration.getEventHandlers().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBeans()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBeans()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBeans()"})
  public void testInitBeans_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initBeans();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getBeans().isEmpty());
    assertTrue(jtaProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBeans()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Beans is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBeans()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBeans()"})
  public void testInitBeans_givenJtaProcessEngineConfigurationBeansIsHashMap() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBeans(new HashMap<>());

    // Act
    jtaProcessEngineConfiguration.initBeans();

    // Assert that nothing has changed
    assertTrue(jtaProcessEngineConfiguration.getBeans().isEmpty());
    assertTrue(jtaProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ActivitiEventDispatcherImpl eventDispatcher = new ActivitiEventDispatcherImpl();
    jtaProcessEngineConfiguration.setEventDispatcher(eventDispatcher);

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert that nothing has changed
    ActivitiEventDispatcher eventDispatcher2 = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher2 instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher2.isEnabled());
    assertSame(eventDispatcher, eventDispatcher2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(new HashMap<>());

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventListeners(new ArrayList<>());

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher4() {
    // Arrange
    ArrayList<ActivitiEventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new BaseEntityEventListener(true));

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventListeners(eventListeners);

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code ,} is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher_givenHashMapCommaIsArrayList() {
    // Arrange
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put(",", new ArrayList<>());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code ,} is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher_givenHashMapCommaIsArrayList2() {
    // Arrange
    ArrayList<ActivitiEventListener> activitiEventListenerList = new ArrayList<>();
    activitiEventListenerList.add(new BaseEntityEventListener(true));

    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put(",", activitiEventListenerList);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} empty string is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher_givenHashMapEmptyStringIsArrayList() {
    // Arrange
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put("", new ArrayList<>());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code ENTITY_CREATED} is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher_givenHashMapEntityCreatedIsArrayList() {
    // Arrange
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put("ENTITY_CREATED", new ArrayList<>());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code ENTITY_CREATED} is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher_givenHashMapEntityCreatedIsArrayList2() {
    // Arrange
    ArrayList<ActivitiEventListener> activitiEventListenerList = new ArrayList<>();
    activitiEventListenerList.add(new BaseEntityEventListener(true));

    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put("ENTITY_CREATED", activitiEventListenerList);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code ENTITY_CREATED} is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher_givenHashMapEntityCreatedIsArrayList3() {
    // Arrange
    ArrayList<ActivitiEventListener> activitiEventListenerList = new ArrayList<>();
    activitiEventListenerList.add(new BaseEntityEventListener(true));
    activitiEventListenerList.add(new BaseEntityEventListener(true));

    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put("ENTITY_CREATED", activitiEventListenerList);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code null} is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher_givenHashMapNullIsArrayList() {
    // Arrange
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put(null, new ArrayList<>());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initEventDispatcher()"})
  public void testInitEventDispatcher_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher.isEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initProcessValidator()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initProcessValidator()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initProcessValidator()"})
  public void testInitProcessValidator() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initProcessValidator();

    // Assert
    ProcessValidator processValidator = jtaProcessEngineConfiguration.getProcessValidator();
    assertTrue(processValidator instanceof ProcessValidatorImpl);
    List<ValidatorSet> validatorSets = processValidator.getValidatorSets();
    assertEquals(1, validatorSets.size());
    ValidatorSet getResult = validatorSets.get(0);
    assertEquals("activiti-executable-process", getResult.getName());
    assertEquals(26, getResult.getValidators().size());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initProcessValidator()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initProcessValidator()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initProcessValidator()"})
  public void testInitProcessValidator2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ActivitiTestCaseProcessValidator processValidator = new ActivitiTestCaseProcessValidator();
    jtaProcessEngineConfiguration.setProcessValidator(processValidator);

    // Act
    jtaProcessEngineConfiguration.initProcessValidator();

    // Assert that nothing has changed
    ProcessValidator processValidator2 = jtaProcessEngineConfiguration.getProcessValidator();
    assertTrue(processValidator2 instanceof ActivitiTestCaseProcessValidator);
    assertSame(processValidator, processValidator2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDatabaseEventLogging()}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcherImpl#addEventListener(ActivitiEventListener)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDatabaseEventLogging()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initDatabaseEventLogging()"})
  public void testInitDatabaseEventLogging_thenCallsAddEventListener() {
    // Arrange
    ActivitiEventDispatcherImpl eventDispatcher = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(eventDispatcher).addEventListener(Mockito.<ActivitiEventListener>any());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventDispatcher(eventDispatcher);
    jtaProcessEngineConfiguration.setEnableDatabaseEventLogging(true);

    // Act
    jtaProcessEngineConfiguration.initDatabaseEventLogging();

    // Assert
    verify(eventDispatcher).addEventListener(isA(ActivitiEventListener.class));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#postProcessEngineInitialisation()}.
   * <ul>
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#postProcessEngineInitialisation()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.postProcessEngineInitialisation()"})
  public void testPostProcessEngineInitialisation_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandExecutor(commandExecutor);

    // Act
    jtaProcessEngineConfiguration.postProcessEngineInitialisation();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandConfig ProcessEngineConfigurationImpl.getDefaultCommandConfig()"})
  public void testGetDefaultCommandConfig() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDefaultCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDefaultCommandConfig(CommandConfig)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDefaultCommandConfig(CommandConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setDefaultCommandConfig(CommandConfig)"})
  public void testSetDefaultCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultCommandConfig = new CommandConfig();

    // Act
    jtaProcessEngineConfiguration.setDefaultCommandConfig(defaultCommandConfig);

    // Assert
    assertSame(defaultCommandConfig, jtaProcessEngineConfiguration.getDefaultCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSchemaCommandConfig()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getSchemaCommandConfig()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandConfig ProcessEngineConfigurationImpl.getSchemaCommandConfig()"})
  public void testGetSchemaCommandConfig() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSchemaCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setSchemaCommandConfig(CommandConfig)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setSchemaCommandConfig(CommandConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setSchemaCommandConfig(CommandConfig)"})
  public void testSetSchemaCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig schemaCommandConfig = new CommandConfig();

    // Act
    jtaProcessEngineConfiguration.setSchemaCommandConfig(schemaCommandConfig);

    // Assert
    assertSame(schemaCommandConfig, jtaProcessEngineConfiguration.getSchemaCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandInvoker()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCommandInvoker()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandInterceptor ProcessEngineConfigurationImpl.getCommandInvoker()"})
  public void testGetCommandInvoker() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommandInvoker());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCommandInvoker(CommandInterceptor)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCommandInvoker(CommandInterceptor)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setCommandInvoker(CommandInterceptor)"})
  public void testSetCommandInvoker() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandContextInterceptor commandInvoker = new CommandContextInterceptor();

    // Act
    jtaProcessEngineConfiguration.setCommandInvoker(commandInvoker);

    // Assert
    assertSame(commandInvoker, jtaProcessEngineConfiguration.getCommandInvoker());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPreCommandInterceptors()"})
  public void testGetCustomPreCommandInterceptors() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPreCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPreCommandInterceptors(List)"})
  public void testSetCustomPreCommandInterceptors() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<CommandInterceptor> customPreCommandInterceptors = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCustomPreCommandInterceptors(customPreCommandInterceptors);

    // Assert
    assertSame(customPreCommandInterceptors, jtaProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPreCommandInterceptorsResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPreCommandInterceptors(List)"})
  public void testSetCustomPreCommandInterceptors2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> customPreCommandInterceptors = new ArrayList<>();
    customPreCommandInterceptors.add(new CommandContextInterceptor());

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCustomPreCommandInterceptors(customPreCommandInterceptors);

    // Assert
    assertTrue(actualSetCustomPreCommandInterceptorsResult instanceof JtaProcessEngineConfiguration);
    assertSame(customPreCommandInterceptors,
        actualSetCustomPreCommandInterceptorsResult.getCustomPreCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPreCommandInterceptors(List)"})
  public void testSetCustomPreCommandInterceptors3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> customPreCommandInterceptors = new ArrayList<>();
    customPreCommandInterceptors.add(new CommandContextInterceptor());
    CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
    customPreCommandInterceptors.add(commandContextInterceptor);

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCustomPreCommandInterceptors(customPreCommandInterceptors);

    // Assert
    assertTrue(actualSetCustomPreCommandInterceptorsResult instanceof JtaProcessEngineConfiguration);
    List<CommandInterceptor> customPreCommandInterceptors2 = actualSetCustomPreCommandInterceptorsResult
        .getCustomPreCommandInterceptors();
    assertEquals(2, customPreCommandInterceptors2.size());
    assertSame(commandContextInterceptor, customPreCommandInterceptors2.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPostCommandInterceptors()"})
  public void testGetCustomPostCommandInterceptors() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPostCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPostCommandInterceptors(List)"})
  public void testSetCustomPostCommandInterceptors() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<CommandInterceptor> customPostCommandInterceptors = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCustomPostCommandInterceptors(customPostCommandInterceptors);

    // Assert
    assertSame(customPostCommandInterceptors, jtaProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPostCommandInterceptorsResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPostCommandInterceptors(List)"})
  public void testSetCustomPostCommandInterceptors2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> customPostCommandInterceptors = new ArrayList<>();
    customPostCommandInterceptors.add(new CommandContextInterceptor());

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCustomPostCommandInterceptors(customPostCommandInterceptors);

    // Assert
    assertTrue(actualSetCustomPostCommandInterceptorsResult instanceof JtaProcessEngineConfiguration);
    assertSame(customPostCommandInterceptors,
        actualSetCustomPostCommandInterceptorsResult.getCustomPostCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPostCommandInterceptors(List)"})
  public void testSetCustomPostCommandInterceptors3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> customPostCommandInterceptors = new ArrayList<>();
    customPostCommandInterceptors.add(new CommandContextInterceptor());
    CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
    customPostCommandInterceptors.add(commandContextInterceptor);

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCustomPostCommandInterceptors(customPostCommandInterceptors);

    // Assert
    assertTrue(actualSetCustomPostCommandInterceptorsResult instanceof JtaProcessEngineConfiguration);
    List<CommandInterceptor> customPostCommandInterceptors2 = actualSetCustomPostCommandInterceptorsResult
        .getCustomPostCommandInterceptors();
    assertEquals(2, customPostCommandInterceptors2.size());
    assertSame(commandContextInterceptor, customPostCommandInterceptors2.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandInterceptors()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCommandInterceptors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCommandInterceptors()"})
  public void testGetCommandInterceptors() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCommandInterceptors(List)"})
  public void testSetCommandInterceptors() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<CommandInterceptor> commandInterceptors = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCommandInterceptors(commandInterceptors);

    // Assert
    assertSame(commandInterceptors, jtaProcessEngineConfiguration.getCommandInterceptors());
    assertSame(jtaProcessEngineConfiguration, actualSetCommandInterceptorsResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}.
   * <ul>
   *   <li>Then return CommandInterceptors is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCommandInterceptors(List)"})
  public void testSetCommandInterceptors_thenReturnCommandInterceptorsIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> commandInterceptors = new ArrayList<>();
    commandInterceptors.add(new CommandContextInterceptor());

    // Act
    ProcessEngineConfigurationImpl actualSetCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCommandInterceptors(commandInterceptors);

    // Assert
    assertTrue(actualSetCommandInterceptorsResult instanceof JtaProcessEngineConfiguration);
    assertSame(commandInterceptors, actualSetCommandInterceptorsResult.getCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}.
   * <ul>
   *   <li>Then return CommandInterceptors size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCommandInterceptors(List)"})
  public void testSetCommandInterceptors_thenReturnCommandInterceptorsSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> commandInterceptors = new ArrayList<>();
    commandInterceptors.add(new CommandContextInterceptor());
    CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
    commandInterceptors.add(commandContextInterceptor);

    // Act
    ProcessEngineConfigurationImpl actualSetCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCommandInterceptors(commandInterceptors);

    // Assert
    assertTrue(actualSetCommandInterceptorsResult instanceof JtaProcessEngineConfiguration);
    List<CommandInterceptor> commandInterceptors2 = actualSetCommandInterceptorsResult.getCommandInterceptors();
    assertEquals(2, commandInterceptors2.size());
    assertSame(commandContextInterceptor, commandInterceptors2.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCommandExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandExecutor ProcessEngineConfigurationImpl.getCommandExecutor()"})
  public void testGetCommandExecutor() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCommandExecutor(CommandExecutor)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCommandExecutor(CommandExecutor)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCommandExecutor(CommandExecutor)"})
  public void testSetCommandExecutor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    // Act
    ProcessEngineConfigurationImpl actualSetCommandExecutorResult = jtaProcessEngineConfiguration
        .setCommandExecutor(commandExecutor);

    // Assert
    assertSame(commandExecutor, jtaProcessEngineConfiguration.getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration, actualSetCommandExecutorResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getRepositoryService()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getRepositoryService()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RepositoryService ProcessEngineConfigurationImpl.getRepositoryService()"})
  public void testGetRepositoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    RepositoryService actualRepositoryService = jtaProcessEngineConfiguration.getRepositoryService();

    // Assert
    assertTrue(actualRepositoryService instanceof RepositoryServiceImpl);
    assertNull(((RepositoryServiceImpl) actualRepositoryService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.repositoryService, actualRepositoryService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setRepositoryService(RepositoryService)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setRepositoryService(RepositoryService)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setRepositoryService(RepositoryService)"})
  public void testSetRepositoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    // Act
    ProcessEngineConfigurationImpl actualSetRepositoryServiceResult = jtaProcessEngineConfiguration
        .setRepositoryService(repositoryService);

    // Assert
    assertSame(repositoryService, jtaProcessEngineConfiguration.getRepositoryService());
    assertSame(jtaProcessEngineConfiguration, actualSetRepositoryServiceResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getRuntimeService()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getRuntimeService()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RuntimeService ProcessEngineConfigurationImpl.getRuntimeService()"})
  public void testGetRuntimeService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    RuntimeService actualRuntimeService = jtaProcessEngineConfiguration.getRuntimeService();

    // Assert
    assertTrue(actualRuntimeService instanceof RuntimeServiceImpl);
    assertNull(((RuntimeServiceImpl) actualRuntimeService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.runtimeService, actualRuntimeService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setRuntimeService(RuntimeService)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setRuntimeService(RuntimeService)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setRuntimeService(RuntimeService)"})
  public void testSetRuntimeService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();

    // Act
    ProcessEngineConfigurationImpl actualSetRuntimeServiceResult = jtaProcessEngineConfiguration
        .setRuntimeService(runtimeService);

    // Assert
    assertSame(runtimeService, jtaProcessEngineConfiguration.getRuntimeService());
    assertSame(jtaProcessEngineConfiguration, actualSetRuntimeServiceResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoryService()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoryService()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoryService ProcessEngineConfigurationImpl.getHistoryService()"})
  public void testGetHistoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoryService actualHistoryService = jtaProcessEngineConfiguration.getHistoryService();

    // Assert
    assertTrue(actualHistoryService instanceof HistoryServiceImpl);
    assertNull(((HistoryServiceImpl) actualHistoryService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.historyService, actualHistoryService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoryService(HistoryService)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoryService(HistoryService)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoryService(HistoryService)"})
  public void testSetHistoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoryServiceImpl historyService = new HistoryServiceImpl(new JtaProcessEngineConfiguration());

    // Act
    ProcessEngineConfigurationImpl actualSetHistoryServiceResult = jtaProcessEngineConfiguration
        .setHistoryService(historyService);

    // Assert
    assertSame(historyService, jtaProcessEngineConfiguration.getHistoryService());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryServiceResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskService()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTaskService()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskService ProcessEngineConfigurationImpl.getTaskService()"})
  public void testGetTaskService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    TaskService actualTaskService = jtaProcessEngineConfiguration.getTaskService();

    // Assert
    assertTrue(actualTaskService instanceof TaskServiceImpl);
    assertNull(((TaskServiceImpl) actualTaskService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.taskService, actualTaskService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTaskService(TaskService)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTaskService(TaskService)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTaskService(TaskService)"})
  public void testSetTaskService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());

    // Act
    ProcessEngineConfigurationImpl actualSetTaskServiceResult = jtaProcessEngineConfiguration
        .setTaskService(taskService);

    // Assert
    assertSame(taskService, jtaProcessEngineConfiguration.getTaskService());
    assertSame(jtaProcessEngineConfiguration, actualSetTaskServiceResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getManagementService()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getManagementService()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ManagementService ProcessEngineConfigurationImpl.getManagementService()"})
  public void testGetManagementService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ManagementService actualManagementService = jtaProcessEngineConfiguration.getManagementService();

    // Assert
    assertTrue(actualManagementService instanceof ManagementServiceImpl);
    assertNull(((ManagementServiceImpl) actualManagementService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.managementService, actualManagementService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setManagementService(ManagementService)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setManagementService(ManagementService)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setManagementService(ManagementService)"})
  public void testSetManagementService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ManagementServiceImpl managementService = new ManagementServiceImpl();

    // Act
    ProcessEngineConfigurationImpl actualSetManagementServiceResult = jtaProcessEngineConfiguration
        .setManagementService(managementService);

    // Assert
    assertSame(managementService, jtaProcessEngineConfiguration.getManagementService());
    assertSame(jtaProcessEngineConfiguration, actualSetManagementServiceResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDynamicBpmnService()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDynamicBpmnService()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DynamicBpmnService ProcessEngineConfigurationImpl.getDynamicBpmnService()"})
  public void testGetDynamicBpmnService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    DynamicBpmnService actualDynamicBpmnService = jtaProcessEngineConfiguration.getDynamicBpmnService();

    // Assert
    assertTrue(actualDynamicBpmnService instanceof DynamicBpmnServiceImpl);
    assertNull(((DynamicBpmnServiceImpl) actualDynamicBpmnService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.dynamicBpmnService, actualDynamicBpmnService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDynamicBpmnService(DynamicBpmnService)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDynamicBpmnService(DynamicBpmnService)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDynamicBpmnService(DynamicBpmnService)"})
  public void testSetDynamicBpmnService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DynamicBpmnServiceImpl dynamicBpmnService = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    // Act
    ProcessEngineConfigurationImpl actualSetDynamicBpmnServiceResult = jtaProcessEngineConfiguration
        .setDynamicBpmnService(dynamicBpmnService);

    // Assert
    assertSame(dynamicBpmnService, jtaProcessEngineConfiguration.getDynamicBpmnService());
    assertSame(jtaProcessEngineConfiguration, actualSetDynamicBpmnServiceResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setUserGroupManager(UserGroupManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setUserGroupManager(UserGroupManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setUserGroupManager(UserGroupManager)"})
  public void testSetUserGroupManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    UserGroupManager userGroupManager = mock(UserGroupManager.class);

    // Act
    jtaProcessEngineConfiguration.setUserGroupManager(userGroupManager);

    // Assert
    assertSame(userGroupManager, jtaProcessEngineConfiguration.getUserGroupManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getUserGroupManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getUserGroupManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"UserGroupManager ProcessEngineConfigurationImpl.getUserGroupManager()"})
  public void testGetUserGroupManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getUserGroupManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIntegrationContextManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getIntegrationContextManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IntegrationContextManager ProcessEngineConfigurationImpl.getIntegrationContextManager()"})
  public void testGetIntegrationContextManager() {
    // Arrange and Act
    IntegrationContextManager actualIntegrationContextManager = (new JtaProcessEngineConfiguration())
        .getIntegrationContextManager();

    // Assert
    IntegrationContextEntity createResult = actualIntegrationContextManager.create();
    Object persistentState = createResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(createResult instanceof IntegrationContextEntityImpl);
    assertTrue(actualIntegrationContextManager instanceof IntegrationContextManagerImpl);
    assertNull(createResult.getId());
    assertNull(createResult.getExecutionId());
    assertNull(createResult.getFlowNodeId());
    assertNull(createResult.getProcessDefinitionId());
    assertNull(createResult.getProcessInstanceId());
    assertNull(createResult.getCreatedDate());
    assertEquals(1, ((IntegrationContextEntityImpl) createResult).getRevision());
    assertEquals(2, ((IntegrationContextEntityImpl) createResult).getRevisionNext());
    assertFalse(createResult.isDeleted());
    assertFalse(createResult.isInserted());
    assertFalse(createResult.isUpdated());
    assertTrue(((Map<Object, Object>) persistentState).isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIntegrationContextService()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getIntegrationContextService()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.integration.IntegrationContextService ProcessEngineConfigurationImpl.getIntegrationContextService()"})
  public void testGetIntegrationContextService() {
    // Arrange, Act and Assert
    assertTrue(
        (new JtaProcessEngineConfiguration()).getIntegrationContextService() instanceof IntegrationContextServiceImpl);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.getProcessEngineConfiguration()"})
  public void testGetProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSessionFactories()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getSessionFactories()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getSessionFactories()"})
  public void testGetSessionFactories() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setSessionFactories(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setSessionFactories(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setSessionFactories(Map)"})
  public void testSetSessionFactories() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HashMap<Class<?>, SessionFactory> sessionFactories = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetSessionFactoriesResult = jtaProcessEngineConfiguration
        .setSessionFactories(sessionFactories);

    // Assert
    assertSame(sessionFactories, jtaProcessEngineConfiguration.getSessionFactories());
    assertSame(jtaProcessEngineConfiguration, actualSetSessionFactoriesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getConfigurators()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getConfigurators()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getConfigurators()"})
  public void testGetConfigurators() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addConfigurator(ProcessEngineConfigurator)"})
  public void testAddConfigurator_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator()));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Configurators is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addConfigurator(ProcessEngineConfigurator)"})
  public void testAddConfigurator_givenJtaProcessEngineConfigurationConfiguratorsIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setConfigurators(new ArrayList<>());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator()));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setConfigurators(List)}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) Configurators is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setConfigurators(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setConfigurators(List)"})
  public void testSetConfigurators_thenJtaProcessEngineConfigurationConfiguratorsIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<ProcessEngineConfigurator> configurators = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetConfiguratorsResult = jtaProcessEngineConfiguration
        .setConfigurators(configurators);

    // Assert
    assertSame(configurators, jtaProcessEngineConfiguration.getConfigurators());
    assertSame(jtaProcessEngineConfiguration, actualSetConfiguratorsResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setConfigurators(List)}.
   * <ul>
   *   <li>Then return Configurators is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setConfigurators(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setConfigurators(List)"})
  public void testSetConfigurators_thenReturnConfiguratorsIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<ProcessEngineConfigurator> configurators = new ArrayList<>();
    configurators.add(new ProcessExecutionLoggerConfigurator());

    // Act
    ProcessEngineConfigurationImpl actualSetConfiguratorsResult = jtaProcessEngineConfiguration
        .setConfigurators(configurators);

    // Assert
    assertTrue(actualSetConfiguratorsResult instanceof JtaProcessEngineConfiguration);
    assertSame(configurators, actualSetConfiguratorsResult.getConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setConfigurators(List)}.
   * <ul>
   *   <li>Then return Configurators size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setConfigurators(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setConfigurators(List)"})
  public void testSetConfigurators_thenReturnConfiguratorsSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<ProcessEngineConfigurator> configurators = new ArrayList<>();
    configurators.add(new ProcessExecutionLoggerConfigurator());
    ProcessExecutionLoggerConfigurator processExecutionLoggerConfigurator = new ProcessExecutionLoggerConfigurator();
    configurators.add(processExecutionLoggerConfigurator);

    // Act
    ProcessEngineConfigurationImpl actualSetConfiguratorsResult = jtaProcessEngineConfiguration
        .setConfigurators(configurators);

    // Assert
    assertTrue(actualSetConfiguratorsResult instanceof JtaProcessEngineConfiguration);
    List<ProcessEngineConfigurator> configurators2 = actualSetConfiguratorsResult.getConfigurators();
    assertEquals(2, configurators2.size());
    assertSame(processExecutionLoggerConfigurator, configurators2.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAllConfigurators()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAllConfigurators()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getAllConfigurators()"})
  public void testGetAllConfigurators() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAllConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnDeployer ProcessEngineConfigurationImpl.getBpmnDeployer()"})
  public void testGetBpmnDeployer() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getBpmnDeployer());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBpmnDeployer(BpmnDeployer)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBpmnDeployer(BpmnDeployer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setBpmnDeployer(BpmnDeployer)"})
  public void testSetBpmnDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    // Act
    ProcessEngineConfigurationImpl actualSetBpmnDeployerResult = jtaProcessEngineConfiguration
        .setBpmnDeployer(bpmnDeployer);

    // Assert
    assertSame(bpmnDeployer, jtaProcessEngineConfiguration.getBpmnDeployer());
    assertSame(jtaProcessEngineConfiguration, actualSetBpmnDeployerResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnParser()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBpmnParser()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParser ProcessEngineConfigurationImpl.getBpmnParser()"})
  public void testGetBpmnParser() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getBpmnParser());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBpmnParser(BpmnParser)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBpmnParser(BpmnParser)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setBpmnParser(BpmnParser)"})
  public void testSetBpmnParser() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    BpmnParser bpmnParser = new BpmnParser();

    // Act
    ProcessEngineConfigurationImpl actualSetBpmnParserResult = jtaProcessEngineConfiguration.setBpmnParser(bpmnParser);

    // Assert
    assertSame(bpmnParser, jtaProcessEngineConfiguration.getBpmnParser());
    assertSame(jtaProcessEngineConfiguration, actualSetBpmnParserResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ParsedDeploymentBuilderFactory ProcessEngineConfigurationImpl.getParsedDeploymentBuilderFactory()"})
  public void testGetParsedDeploymentBuilderFactory() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getParsedDeploymentBuilderFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)"})
  public void testSetParsedDeploymentBuilderFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    // Act
    ProcessEngineConfigurationImpl actualSetParsedDeploymentBuilderFactoryResult = jtaProcessEngineConfiguration
        .setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);

    // Assert
    assertSame(parsedDeploymentBuilderFactory, jtaProcessEngineConfiguration.getParsedDeploymentBuilderFactory());
    assertSame(jtaProcessEngineConfiguration, actualSetParsedDeploymentBuilderFactoryResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTimerManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TimerManager ProcessEngineConfigurationImpl.getTimerManager()"})
  public void testGetTimerManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTimerManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"EventSubscriptionManager ProcessEngineConfigurationImpl.getEventSubscriptionManager()"})
  public void testGetEventSubscriptionManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventSubscriptionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnDeploymentHelper ProcessEngineConfigurationImpl.getBpmnDeploymentHelper()"})
  public void testGetBpmnDeploymentHelper() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBpmnDeploymentHelper(BpmnDeploymentHelper)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBpmnDeploymentHelper(BpmnDeploymentHelper)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setBpmnDeploymentHelper(BpmnDeploymentHelper)"})
  public void testSetBpmnDeploymentHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    // Act
    ProcessEngineConfigurationImpl actualSetBpmnDeploymentHelperResult = jtaProcessEngineConfiguration
        .setBpmnDeploymentHelper(bpmnDeploymentHelper);

    // Assert
    assertSame(bpmnDeploymentHelper, jtaProcessEngineConfiguration.getBpmnDeploymentHelper());
    assertSame(jtaProcessEngineConfiguration, actualSetBpmnDeploymentHelperResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCachingAndArtifactsManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCachingAndArtifactsManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CachingAndArtifactsManager ProcessEngineConfigurationImpl.getCachingAndArtifactsManager()"})
  public void testGetCachingAndArtifactsManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCachingAndArtifactsManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDeployers()"})
  public void testGetDeployers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDeployers(List)}.
   * <ul>
   *   <li>Given {@link Deployer}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Deployer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeployers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDeployers(List)"})
  public void testSetDeployers_givenDeployer_whenArrayListAddDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<Deployer> deployers = new ArrayList<>();
    deployers.add(mock(Deployer.class));

    // Act
    ProcessEngineConfigurationImpl actualSetDeployersResult = jtaProcessEngineConfiguration.setDeployers(deployers);

    // Assert
    assertSame(deployers, jtaProcessEngineConfiguration.getDeployers());
    assertSame(jtaProcessEngineConfiguration, actualSetDeployersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDeployers(List)}.
   * <ul>
   *   <li>Given {@link Deployer}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Deployer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeployers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDeployers(List)"})
  public void testSetDeployers_givenDeployer_whenArrayListAddDeployer2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<Deployer> deployers = new ArrayList<>();
    deployers.add(mock(Deployer.class));
    deployers.add(mock(Deployer.class));

    // Act
    ProcessEngineConfigurationImpl actualSetDeployersResult = jtaProcessEngineConfiguration.setDeployers(deployers);

    // Assert
    assertSame(deployers, jtaProcessEngineConfiguration.getDeployers());
    assertSame(jtaProcessEngineConfiguration, actualSetDeployersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDeployers(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeployers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDeployers(List)"})
  public void testSetDeployers_whenArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<Deployer> deployers = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetDeployersResult = jtaProcessEngineConfiguration.setDeployers(deployers);

    // Assert
    assertSame(deployers, jtaProcessEngineConfiguration.getDeployers());
    assertSame(jtaProcessEngineConfiguration, actualSetDeployersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setIdGenerator(IdGenerator)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setIdGenerator(IdGenerator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setIdGenerator(IdGenerator)"})
  public void testSetIdGenerator() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    IdGenerator idGenerator = mock(IdGenerator.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setIdGenerator(idGenerator));
    assertSame(idGenerator, jtaProcessEngineConfiguration.getIdGenerator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getWsSyncFactoryClassName()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getWsSyncFactoryClassName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getWsSyncFactoryClassName()"})
  public void testGetWsSyncFactoryClassName() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfigurationImpl.DEFAULT_WS_SYNC_FACTORY,
        (new JtaProcessEngineConfiguration()).getWsSyncFactoryClassName());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setWsSyncFactoryClassName(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setWsSyncFactoryClassName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setWsSyncFactoryClassName(String)"})
  public void testSetWsSyncFactoryClassName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetWsSyncFactoryClassNameResult = jtaProcessEngineConfiguration
        .setWsSyncFactoryClassName("Ws Sync Factory Class Name");

    // Assert
    assertEquals("Ws Sync Factory Class Name", jtaProcessEngineConfiguration.getWsSyncFactoryClassName());
    assertSame(jtaProcessEngineConfiguration, actualSetWsSyncFactoryClassNameResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}.
   * <ul>
   *   <li>When valueOf {@code foo}.</li>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfigurationImpl.addWsEndpointAddress(QName, URL)"})
  public void testAddWsEndpointAddress_whenValueOfFoo_thenReturnJtaProcessEngineConfiguration()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.addWsEndpointAddress(QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#removeWsEndpointAddress(QName)}.
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#removeWsEndpointAddress(QName)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfigurationImpl.removeWsEndpointAddress(QName)"})
  public void testRemoveWsEndpointAddress_thenReturnJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.removeWsEndpointAddress(QName.valueOf("foo")));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ConcurrentMap ProcessEngineConfigurationImpl.getWsOverridenEndpointAddresses()"})
  public void testGetWsOverridenEndpointAddresses() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ConcurrentMap<QName, URL> actualWsOverridenEndpointAddresses = jtaProcessEngineConfiguration
        .getWsOverridenEndpointAddresses();

    // Assert
    assertTrue(actualWsOverridenEndpointAddresses.isEmpty());
    assertSame(jtaProcessEngineConfiguration.wsOverridenEndpointAddresses, actualWsOverridenEndpointAddresses);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setWsOverridenEndpointAddresses(ConcurrentMap)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setWsOverridenEndpointAddresses(ConcurrentMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfiguration ProcessEngineConfigurationImpl.setWsOverridenEndpointAddresses(ConcurrentMap)"})
  public void testSetWsOverridenEndpointAddresses() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setWsOverridenEndpointAddresses(new ConcurrentHashMap<>()));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getScriptingEngines()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getScriptingEngines()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ScriptingEngines ProcessEngineConfigurationImpl.getScriptingEngines()"})
  public void testGetScriptingEngines() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getScriptingEngines());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setScriptingEngines(ScriptingEngines)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setScriptingEngines(ScriptingEngines)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setScriptingEngines(ScriptingEngines)"})
  public void testSetScriptingEngines() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setScriptingEngines(scriptingEngines));
    assertSame(scriptingEngines, jtaProcessEngineConfiguration.getScriptingEngines());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableTypes()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableTypes ProcessEngineConfigurationImpl.getVariableTypes()"})
  public void testGetVariableTypes() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setVariableTypes(VariableTypes)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setVariableTypes(VariableTypes)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setVariableTypes(VariableTypes)"})
  public void testSetVariableTypes() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultVariableTypes variableTypes = new DefaultVariableTypes();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setVariableTypes(variableTypes));
    assertSame(variableTypes, jtaProcessEngineConfiguration.getVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isSerializableVariableTypeTrackDeserializedObjects()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isSerializableVariableTypeTrackDeserializedObjects()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isSerializableVariableTypeTrackDeserializedObjects()"})
  public void testIsSerializableVariableTypeTrackDeserializedObjects() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isSerializableVariableTypeTrackDeserializedObjects());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isSerializePOJOsInVariablesToJson()"})
  public void testIsSerializePOJOsInVariablesToJson_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isSerializePOJOsInVariablesToJson());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isSerializePOJOsInVariablesToJson()"})
  public void testIsSerializePOJOsInVariablesToJson_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSerializePOJOsInVariablesToJson(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setSerializePOJOsInVariablesToJson(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setSerializePOJOsInVariablesToJson(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setSerializePOJOsInVariablesToJson(boolean)"})
  public void testSetSerializePOJOsInVariablesToJson() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.setSerializePOJOsInVariablesToJson(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJavaClassFieldForJackson()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getJavaClassFieldForJackson()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getJavaClassFieldForJackson()"})
  public void testGetJavaClassFieldForJackson() {
    // Arrange, Act and Assert
    assertEquals("@class", (new JtaProcessEngineConfiguration()).getJavaClassFieldForJackson());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setJavaClassFieldForJackson(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setJavaClassFieldForJackson(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setJavaClassFieldForJackson(String)"})
  public void testSetJavaClassFieldForJackson() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.setJavaClassFieldForJackson("Java Class Field For Jackson");

    // Assert
    assertEquals("Java Class Field For Jackson", jtaProcessEngineConfiguration.getJavaClassFieldForJackson());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExpressionManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getExpressionManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExpressionManager ProcessEngineConfigurationImpl.getExpressionManager()"})
  public void testGetExpressionManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getExpressionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setExpressionManager(ExpressionManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setExpressionManager(ExpressionManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setExpressionManager(ExpressionManager)"})
  public void testSetExpressionManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ExpressionManager expressionManager = new ExpressionManager();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setExpressionManager(expressionManager));
    assertSame(expressionManager, jtaProcessEngineConfiguration.getExpressionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBusinessCalendarManager(BusinessCalendarManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBusinessCalendarManager(BusinessCalendarManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setBusinessCalendarManager(BusinessCalendarManager)"})
  public void testSetBusinessCalendarManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    BusinessCalendarManager businessCalendarManager = mock(BusinessCalendarManager.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setBusinessCalendarManager(businessCalendarManager));
    assertSame(businessCalendarManager, jtaProcessEngineConfiguration.getBusinessCalendarManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionQueryLimit()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getExecutionQueryLimit()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getExecutionQueryLimit()"})
  public void testGetExecutionQueryLimit() {
    // Arrange, Act and Assert
    assertEquals(20000, (new JtaProcessEngineConfiguration()).getExecutionQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setExecutionQueryLimit(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setExecutionQueryLimit(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setExecutionQueryLimit(int)"})
  public void testSetExecutionQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetExecutionQueryLimitResult = jtaProcessEngineConfiguration
        .setExecutionQueryLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getExecutionQueryLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetExecutionQueryLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskQueryLimit()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTaskQueryLimit()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getTaskQueryLimit()"})
  public void testGetTaskQueryLimit() {
    // Arrange, Act and Assert
    assertEquals(20000, (new JtaProcessEngineConfiguration()).getTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTaskQueryLimit(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTaskQueryLimit(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTaskQueryLimit(int)"})
  public void testSetTaskQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetTaskQueryLimitResult = jtaProcessEngineConfiguration.setTaskQueryLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getTaskQueryLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetTaskQueryLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskQueryLimit()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricTaskQueryLimit()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getHistoricTaskQueryLimit()"})
  public void testGetHistoricTaskQueryLimit() {
    // Arrange, Act and Assert
    assertEquals(20000, (new JtaProcessEngineConfiguration()).getHistoricTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricTaskQueryLimit(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricTaskQueryLimit(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricTaskQueryLimit(int)"})
  public void testSetHistoricTaskQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetHistoricTaskQueryLimitResult = jtaProcessEngineConfiguration
        .setHistoricTaskQueryLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoricTaskQueryLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getHistoricProcessInstancesQueryLimit()"})
  public void testGetHistoricProcessInstancesQueryLimit() {
    // Arrange, Act and Assert
    assertEquals(20000, (new JtaProcessEngineConfiguration()).getHistoricProcessInstancesQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricProcessInstancesQueryLimit(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricProcessInstancesQueryLimit(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricProcessInstancesQueryLimit(int)"})
  public void testSetHistoricProcessInstancesQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetHistoricProcessInstancesQueryLimitResult = jtaProcessEngineConfiguration
        .setHistoricProcessInstancesQueryLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoricProcessInstancesQueryLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandContextFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCommandContextFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandContextFactory ProcessEngineConfigurationImpl.getCommandContextFactory()"})
  public void testGetCommandContextFactory() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCommandContextFactory(CommandContextFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCommandContextFactory(CommandContextFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCommandContextFactory(CommandContextFactory)"})
  public void testSetCommandContextFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandContextFactory commandContextFactory = new CommandContextFactory();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setCommandContextFactory(commandContextFactory));
    assertSame(commandContextFactory, jtaProcessEngineConfiguration.getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTransactionContextFactory(TransactionContextFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTransactionContextFactory(TransactionContextFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTransactionContextFactory(TransactionContextFactory)"})
  public void testSetTransactionContextFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    TransactionContextFactory transactionContextFactory = mock(TransactionContextFactory.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setTransactionContextFactory(transactionContextFactory));
    assertSame(transactionContextFactory, jtaProcessEngineConfiguration.getTransactionContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomPreDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPreDeployers()"})
  public void testGetCustomPreDeployers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPreDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}.
   * <ul>
   *   <li>Given {@link Deployer}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Deployer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPreDeployers(List)"})
  public void testSetCustomPreDeployers_givenDeployer_whenArrayListAddDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<Deployer> customPreDeployers = new ArrayList<>();
    customPreDeployers.add(mock(Deployer.class));

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreDeployersResult = jtaProcessEngineConfiguration
        .setCustomPreDeployers(customPreDeployers);

    // Assert
    assertSame(customPreDeployers, jtaProcessEngineConfiguration.getCustomPreDeployers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPreDeployersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}.
   * <ul>
   *   <li>Given {@link Deployer}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Deployer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPreDeployers(List)"})
  public void testSetCustomPreDeployers_givenDeployer_whenArrayListAddDeployer2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<Deployer> customPreDeployers = new ArrayList<>();
    customPreDeployers.add(mock(Deployer.class));
    customPreDeployers.add(mock(Deployer.class));

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreDeployersResult = jtaProcessEngineConfiguration
        .setCustomPreDeployers(customPreDeployers);

    // Assert
    assertSame(customPreDeployers, jtaProcessEngineConfiguration.getCustomPreDeployers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPreDeployersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPreDeployers(List)"})
  public void testSetCustomPreDeployers_whenArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<Deployer> customPreDeployers = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreDeployersResult = jtaProcessEngineConfiguration
        .setCustomPreDeployers(customPreDeployers);

    // Assert
    assertSame(customPreDeployers, jtaProcessEngineConfiguration.getCustomPreDeployers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPreDeployersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomPostDeployers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPostDeployers()"})
  public void testGetCustomPostDeployers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPostDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}.
   * <ul>
   *   <li>Given {@link Deployer}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Deployer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPostDeployers(List)"})
  public void testSetCustomPostDeployers_givenDeployer_whenArrayListAddDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<Deployer> customPostDeployers = new ArrayList<>();
    customPostDeployers.add(mock(Deployer.class));

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostDeployersResult = jtaProcessEngineConfiguration
        .setCustomPostDeployers(customPostDeployers);

    // Assert
    assertSame(customPostDeployers, jtaProcessEngineConfiguration.getCustomPostDeployers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPostDeployersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}.
   * <ul>
   *   <li>Given {@link Deployer}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Deployer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPostDeployers(List)"})
  public void testSetCustomPostDeployers_givenDeployer_whenArrayListAddDeployer2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<Deployer> customPostDeployers = new ArrayList<>();
    customPostDeployers.add(mock(Deployer.class));
    customPostDeployers.add(mock(Deployer.class));

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostDeployersResult = jtaProcessEngineConfiguration
        .setCustomPostDeployers(customPostDeployers);

    // Assert
    assertSame(customPostDeployers, jtaProcessEngineConfiguration.getCustomPostDeployers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPostDeployersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPostDeployers(List)"})
  public void testSetCustomPostDeployers_whenArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<Deployer> customPostDeployers = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostDeployersResult = jtaProcessEngineConfiguration
        .setCustomPostDeployers(customPostDeployers);

    // Assert
    assertSame(customPostDeployers, jtaProcessEngineConfiguration.getCustomPostDeployers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPostDeployersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getJobHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getJobHandlers()"})
  public void testGetJobHandlers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setJobHandlers(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setJobHandlers(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setJobHandlers(Map)"})
  public void testSetJobHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HashMap<String, JobHandler> jobHandlers = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetJobHandlersResult = jtaProcessEngineConfiguration
        .setJobHandlers(jobHandlers);

    // Assert
    assertSame(jobHandlers, jtaProcessEngineConfiguration.getJobHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetJobHandlersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessInstanceHelper()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessInstanceHelper()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceHelper ProcessEngineConfigurationImpl.getProcessInstanceHelper()"})
  public void testGetProcessInstanceHelper() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessInstanceHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setProcessInstanceHelper(ProcessInstanceHelper)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setProcessInstanceHelper(ProcessInstanceHelper)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setProcessInstanceHelper(ProcessInstanceHelper)"})
  public void testSetProcessInstanceHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessInstanceHelper processInstanceHelper = new ProcessInstanceHelper();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessInstanceHelper(processInstanceHelper));
    assertSame(processInstanceHelper, jtaProcessEngineConfiguration.getProcessInstanceHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getListenerNotificationHelper()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getListenerNotificationHelper()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ListenerNotificationHelper ProcessEngineConfigurationImpl.getListenerNotificationHelper()"})
  public void testGetListenerNotificationHelper() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getListenerNotificationHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setListenerNotificationHelper(ListenerNotificationHelper)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setListenerNotificationHelper(ListenerNotificationHelper)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setListenerNotificationHelper(ListenerNotificationHelper)"})
  public void testSetListenerNotificationHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ListenerNotificationHelper listenerNotificationHelper = new ListenerNotificationHelper();

    // Act
    ProcessEngineConfigurationImpl actualSetListenerNotificationHelperResult = jtaProcessEngineConfiguration
        .setListenerNotificationHelper(listenerNotificationHelper);

    // Assert
    assertSame(listenerNotificationHelper, jtaProcessEngineConfiguration.getListenerNotificationHelper());
    assertSame(jtaProcessEngineConfiguration, actualSetListenerNotificationHelperResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SqlSessionFactory ProcessEngineConfigurationImpl.getSqlSessionFactory()"})
  public void testGetSqlSessionFactory() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setSqlSessionFactory(SqlSessionFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setSqlSessionFactory(SqlSessionFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setSqlSessionFactory(SqlSessionFactory)"})
  public void testSetSqlSessionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(new Configuration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setSqlSessionFactory(sqlSessionFactory));
    assertSame(sqlSessionFactory, jtaProcessEngineConfiguration.getSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDbSqlSessionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDbSqlSessionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DbSqlSessionFactory ProcessEngineConfigurationImpl.getDbSqlSessionFactory()"})
  public void testGetDbSqlSessionFactory() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDbSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDbSqlSessionFactory(DbSqlSessionFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDbSqlSessionFactory(DbSqlSessionFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDbSqlSessionFactory(DbSqlSessionFactory)"})
  public void testSetDbSqlSessionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDbSqlSessionFactory(dbSqlSessionFactory));
    assertSame(dbSqlSessionFactory, jtaProcessEngineConfiguration.getDbSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTransactionFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTransactionFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TransactionFactory ProcessEngineConfigurationImpl.getTransactionFactory()"})
  public void testGetTransactionFactory() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTransactionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTransactionFactory(TransactionFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTransactionFactory(TransactionFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTransactionFactory(TransactionFactory)"})
  public void testSetTransactionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTransactionFactory(transactionFactory));
    assertSame(transactionFactory, jtaProcessEngineConfiguration.getTransactionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomSessionFactories()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomSessionFactories()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomSessionFactories()"})
  public void testGetCustomSessionFactories() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomSessionFactories(List)"})
  public void testSetCustomSessionFactories() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<SessionFactory> customSessionFactories = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomSessionFactoriesResult = jtaProcessEngineConfiguration
        .setCustomSessionFactories(customSessionFactories);

    // Assert
    assertSame(customSessionFactories, jtaProcessEngineConfiguration.getCustomSessionFactories());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomSessionFactoriesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}.
   * <ul>
   *   <li>Then return CustomSessionFactories is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomSessionFactories(List)"})
  public void testSetCustomSessionFactories_thenReturnCustomSessionFactoriesIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<SessionFactory> customSessionFactories = new ArrayList<>();
    customSessionFactories.add(new DbSqlSessionFactory());

    // Act
    ProcessEngineConfigurationImpl actualSetCustomSessionFactoriesResult = jtaProcessEngineConfiguration
        .setCustomSessionFactories(customSessionFactories);

    // Assert
    assertTrue(actualSetCustomSessionFactoriesResult instanceof JtaProcessEngineConfiguration);
    assertSame(customSessionFactories, actualSetCustomSessionFactoriesResult.getCustomSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}.
   * <ul>
   *   <li>Then return CustomSessionFactories size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomSessionFactories(List)"})
  public void testSetCustomSessionFactories_thenReturnCustomSessionFactoriesSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<SessionFactory> customSessionFactories = new ArrayList<>();
    customSessionFactories.add(new DbSqlSessionFactory());
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    customSessionFactories.add(dbSqlSessionFactory);

    // Act
    ProcessEngineConfigurationImpl actualSetCustomSessionFactoriesResult = jtaProcessEngineConfiguration
        .setCustomSessionFactories(customSessionFactories);

    // Assert
    assertTrue(actualSetCustomSessionFactoriesResult instanceof JtaProcessEngineConfiguration);
    List<SessionFactory> customSessionFactories2 = actualSetCustomSessionFactoriesResult.getCustomSessionFactories();
    assertEquals(2, customSessionFactories2.size());
    assertSame(dbSqlSessionFactory, customSessionFactories2.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomJobHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomJobHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomJobHandlers()"})
  public void testGetCustomJobHandlers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomJobHandlers(List)"})
  public void testSetCustomJobHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<JobHandler> customJobHandlers = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomJobHandlersResult = jtaProcessEngineConfiguration
        .setCustomJobHandlers(customJobHandlers);

    // Assert
    assertSame(customJobHandlers, jtaProcessEngineConfiguration.getCustomJobHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomJobHandlersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}.
   * <ul>
   *   <li>Then return CustomJobHandlers is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomJobHandlers(List)"})
  public void testSetCustomJobHandlers_thenReturnCustomJobHandlersIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<JobHandler> customJobHandlers = new ArrayList<>();
    customJobHandlers.add(new AsyncContinuationJobHandler());

    // Act
    ProcessEngineConfigurationImpl actualSetCustomJobHandlersResult = jtaProcessEngineConfiguration
        .setCustomJobHandlers(customJobHandlers);

    // Assert
    assertTrue(actualSetCustomJobHandlersResult instanceof JtaProcessEngineConfiguration);
    assertSame(customJobHandlers, actualSetCustomJobHandlersResult.getCustomJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}.
   * <ul>
   *   <li>Then return CustomJobHandlers size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomJobHandlers(List)"})
  public void testSetCustomJobHandlers_thenReturnCustomJobHandlersSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<JobHandler> customJobHandlers = new ArrayList<>();
    customJobHandlers.add(new AsyncContinuationJobHandler());
    AsyncContinuationJobHandler asyncContinuationJobHandler = new AsyncContinuationJobHandler();
    customJobHandlers.add(asyncContinuationJobHandler);

    // Act
    ProcessEngineConfigurationImpl actualSetCustomJobHandlersResult = jtaProcessEngineConfiguration
        .setCustomJobHandlers(customJobHandlers);

    // Assert
    assertTrue(actualSetCustomJobHandlersResult instanceof JtaProcessEngineConfiguration);
    List<JobHandler> customJobHandlers2 = actualSetCustomJobHandlersResult.getCustomJobHandlers();
    assertEquals(2, customJobHandlers2.size());
    JobHandler getResult = customJobHandlers2.get(1);
    assertTrue(getResult instanceof AsyncContinuationJobHandler);
    assertEquals("async-continuation", getResult.getType());
    assertSame(asyncContinuationJobHandler, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomScriptingEngineClasses()"})
  public void testGetCustomScriptingEngineClasses() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomScriptingEngineClasses());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomScriptingEngineClasses(List)"})
  public void testSetCustomScriptingEngineClasses_given42_whenArrayListAdd42() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<String> customScriptingEngineClasses = new ArrayList<>();
    customScriptingEngineClasses.add("42");
    customScriptingEngineClasses.add("foo");

    // Act
    ProcessEngineConfigurationImpl actualSetCustomScriptingEngineClassesResult = jtaProcessEngineConfiguration
        .setCustomScriptingEngineClasses(customScriptingEngineClasses);

    // Assert
    assertSame(customScriptingEngineClasses, jtaProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomScriptingEngineClassesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomScriptingEngineClasses(List)"})
  public void testSetCustomScriptingEngineClasses_givenFoo_whenArrayListAddFoo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<String> customScriptingEngineClasses = new ArrayList<>();
    customScriptingEngineClasses.add("foo");

    // Act
    ProcessEngineConfigurationImpl actualSetCustomScriptingEngineClassesResult = jtaProcessEngineConfiguration
        .setCustomScriptingEngineClasses(customScriptingEngineClasses);

    // Assert
    assertSame(customScriptingEngineClasses, jtaProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomScriptingEngineClassesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomScriptingEngineClasses(List)"})
  public void testSetCustomScriptingEngineClasses_whenArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<String> customScriptingEngineClasses = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomScriptingEngineClassesResult = jtaProcessEngineConfiguration
        .setCustomScriptingEngineClasses(customScriptingEngineClasses);

    // Assert
    assertSame(customScriptingEngineClasses, jtaProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomScriptingEngineClassesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreVariableTypes()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomPreVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPreVariableTypes()"})
  public void testGetCustomPreVariableTypes() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPreVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPreVariableTypes(List)"})
  public void testSetCustomPreVariableTypes() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<VariableType> customPreVariableTypes = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreVariableTypesResult = jtaProcessEngineConfiguration
        .setCustomPreVariableTypes(customPreVariableTypes);

    // Assert
    assertSame(customPreVariableTypes, jtaProcessEngineConfiguration.getCustomPreVariableTypes());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPreVariableTypesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}.
   * <ul>
   *   <li>Then return CustomPreVariableTypes is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPreVariableTypes(List)"})
  public void testSetCustomPreVariableTypes_thenReturnCustomPreVariableTypesIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<VariableType> customPreVariableTypes = new ArrayList<>();
    customPreVariableTypes.add(new BigDecimalType());

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreVariableTypesResult = jtaProcessEngineConfiguration
        .setCustomPreVariableTypes(customPreVariableTypes);

    // Assert
    assertTrue(actualSetCustomPreVariableTypesResult instanceof JtaProcessEngineConfiguration);
    assertSame(customPreVariableTypes, actualSetCustomPreVariableTypesResult.getCustomPreVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}.
   * <ul>
   *   <li>Then return CustomPreVariableTypes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPreVariableTypes(List)"})
  public void testSetCustomPreVariableTypes_thenReturnCustomPreVariableTypesSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<VariableType> customPreVariableTypes = new ArrayList<>();
    customPreVariableTypes.add(new BigDecimalType());
    BigDecimalType bigDecimalType = new BigDecimalType();
    customPreVariableTypes.add(bigDecimalType);

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreVariableTypesResult = jtaProcessEngineConfiguration
        .setCustomPreVariableTypes(customPreVariableTypes);

    // Assert
    assertTrue(actualSetCustomPreVariableTypesResult instanceof JtaProcessEngineConfiguration);
    List<VariableType> customPreVariableTypes2 = actualSetCustomPreVariableTypesResult.getCustomPreVariableTypes();
    assertEquals(2, customPreVariableTypes2.size());
    assertSame(bigDecimalType, customPreVariableTypes2.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostVariableTypes()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomPostVariableTypes()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPostVariableTypes()"})
  public void testGetCustomPostVariableTypes() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPostVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPostVariableTypes(List)"})
  public void testSetCustomPostVariableTypes() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<VariableType> customPostVariableTypes = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostVariableTypesResult = jtaProcessEngineConfiguration
        .setCustomPostVariableTypes(customPostVariableTypes);

    // Assert
    assertSame(customPostVariableTypes, jtaProcessEngineConfiguration.getCustomPostVariableTypes());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPostVariableTypesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}.
   * <ul>
   *   <li>Then return CustomPostVariableTypes is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPostVariableTypes(List)"})
  public void testSetCustomPostVariableTypes_thenReturnCustomPostVariableTypesIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<VariableType> customPostVariableTypes = new ArrayList<>();
    customPostVariableTypes.add(new BigDecimalType());

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostVariableTypesResult = jtaProcessEngineConfiguration
        .setCustomPostVariableTypes(customPostVariableTypes);

    // Assert
    assertTrue(actualSetCustomPostVariableTypesResult instanceof JtaProcessEngineConfiguration);
    assertSame(customPostVariableTypes, actualSetCustomPostVariableTypesResult.getCustomPostVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}.
   * <ul>
   *   <li>Then return CustomPostVariableTypes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomPostVariableTypes(List)"})
  public void testSetCustomPostVariableTypes_thenReturnCustomPostVariableTypesSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<VariableType> customPostVariableTypes = new ArrayList<>();
    customPostVariableTypes.add(new BigDecimalType());
    BigDecimalType bigDecimalType = new BigDecimalType();
    customPostVariableTypes.add(bigDecimalType);

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostVariableTypesResult = jtaProcessEngineConfiguration
        .setCustomPostVariableTypes(customPostVariableTypes);

    // Assert
    assertTrue(actualSetCustomPostVariableTypesResult instanceof JtaProcessEngineConfiguration);
    List<VariableType> customPostVariableTypes2 = actualSetCustomPostVariableTypesResult.getCustomPostVariableTypes();
    assertEquals(2, customPostVariableTypes2.size());
    assertSame(bigDecimalType, customPostVariableTypes2.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPreBpmnParseHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getPreBpmnParseHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getPreBpmnParseHandlers()"})
  public void testGetPreBpmnParseHandlers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getPreBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setPreBpmnParseHandlers(List)"})
  public void testSetPreBpmnParseHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<BpmnParseHandler> preBpmnParseHandlers = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetPreBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setPreBpmnParseHandlers(preBpmnParseHandlers);

    // Assert
    assertSame(preBpmnParseHandlers, jtaProcessEngineConfiguration.getPreBpmnParseHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetPreBpmnParseHandlersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}.
   * <ul>
   *   <li>Then return PreBpmnParseHandlers is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setPreBpmnParseHandlers(List)"})
  public void testSetPreBpmnParseHandlers_thenReturnPreBpmnParseHandlersIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<BpmnParseHandler> preBpmnParseHandlers = new ArrayList<>();
    preBpmnParseHandlers.add(new AdhocSubProcessParseHandler());

    // Act
    ProcessEngineConfigurationImpl actualSetPreBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setPreBpmnParseHandlers(preBpmnParseHandlers);

    // Assert
    assertTrue(actualSetPreBpmnParseHandlersResult instanceof JtaProcessEngineConfiguration);
    assertSame(preBpmnParseHandlers, actualSetPreBpmnParseHandlersResult.getPreBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}.
   * <ul>
   *   <li>Then return PreBpmnParseHandlers size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setPreBpmnParseHandlers(List)"})
  public void testSetPreBpmnParseHandlers_thenReturnPreBpmnParseHandlersSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<BpmnParseHandler> preBpmnParseHandlers = new ArrayList<>();
    preBpmnParseHandlers.add(new AdhocSubProcessParseHandler());
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    preBpmnParseHandlers.add(adhocSubProcessParseHandler);

    // Act
    ProcessEngineConfigurationImpl actualSetPreBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setPreBpmnParseHandlers(preBpmnParseHandlers);

    // Assert
    List<BpmnParseHandler> preBpmnParseHandlers2 = actualSetPreBpmnParseHandlersResult.getPreBpmnParseHandlers();
    assertEquals(2, preBpmnParseHandlers2.size());
    BpmnParseHandler getResult = preBpmnParseHandlers2.get(1);
    assertTrue(getResult instanceof AdhocSubProcessParseHandler);
    assertTrue(actualSetPreBpmnParseHandlersResult instanceof JtaProcessEngineConfiguration);
    assertSame(adhocSubProcessParseHandler, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomDefaultBpmnParseHandlers()"})
  public void testGetCustomDefaultBpmnParseHandlers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomDefaultBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomDefaultBpmnParseHandlers(List)"})
  public void testSetCustomDefaultBpmnParseHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<BpmnParseHandler> customDefaultBpmnParseHandlers = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomDefaultBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setCustomDefaultBpmnParseHandlers(customDefaultBpmnParseHandlers);

    // Assert
    assertSame(customDefaultBpmnParseHandlers, jtaProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomDefaultBpmnParseHandlersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomDefaultBpmnParseHandlers(List)"})
  public void testSetCustomDefaultBpmnParseHandlers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<BpmnParseHandler> customDefaultBpmnParseHandlers = new ArrayList<>();
    customDefaultBpmnParseHandlers.add(new AdhocSubProcessParseHandler());

    // Act
    ProcessEngineConfigurationImpl actualSetCustomDefaultBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setCustomDefaultBpmnParseHandlers(customDefaultBpmnParseHandlers);

    // Assert
    assertTrue(actualSetCustomDefaultBpmnParseHandlersResult instanceof JtaProcessEngineConfiguration);
    assertSame(customDefaultBpmnParseHandlers,
        actualSetCustomDefaultBpmnParseHandlersResult.getCustomDefaultBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomDefaultBpmnParseHandlers(List)"})
  public void testSetCustomDefaultBpmnParseHandlers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<BpmnParseHandler> customDefaultBpmnParseHandlers = new ArrayList<>();
    customDefaultBpmnParseHandlers.add(new AdhocSubProcessParseHandler());
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    customDefaultBpmnParseHandlers.add(adhocSubProcessParseHandler);

    // Act
    ProcessEngineConfigurationImpl actualSetCustomDefaultBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setCustomDefaultBpmnParseHandlers(customDefaultBpmnParseHandlers);

    // Assert
    List<BpmnParseHandler> customDefaultBpmnParseHandlers2 = actualSetCustomDefaultBpmnParseHandlersResult
        .getCustomDefaultBpmnParseHandlers();
    assertEquals(2, customDefaultBpmnParseHandlers2.size());
    BpmnParseHandler getResult = customDefaultBpmnParseHandlers2.get(1);
    assertTrue(getResult instanceof AdhocSubProcessParseHandler);
    assertTrue(actualSetCustomDefaultBpmnParseHandlersResult instanceof JtaProcessEngineConfiguration);
    assertSame(adhocSubProcessParseHandler, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPostBpmnParseHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getPostBpmnParseHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getPostBpmnParseHandlers()"})
  public void testGetPostBpmnParseHandlers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getPostBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setPostBpmnParseHandlers(List)"})
  public void testSetPostBpmnParseHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<BpmnParseHandler> postBpmnParseHandlers = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetPostBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setPostBpmnParseHandlers(postBpmnParseHandlers);

    // Assert
    assertSame(postBpmnParseHandlers, jtaProcessEngineConfiguration.getPostBpmnParseHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetPostBpmnParseHandlersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}.
   * <ul>
   *   <li>Then return PostBpmnParseHandlers is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setPostBpmnParseHandlers(List)"})
  public void testSetPostBpmnParseHandlers_thenReturnPostBpmnParseHandlersIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<BpmnParseHandler> postBpmnParseHandlers = new ArrayList<>();
    postBpmnParseHandlers.add(new AdhocSubProcessParseHandler());

    // Act
    ProcessEngineConfigurationImpl actualSetPostBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setPostBpmnParseHandlers(postBpmnParseHandlers);

    // Assert
    assertTrue(actualSetPostBpmnParseHandlersResult instanceof JtaProcessEngineConfiguration);
    assertSame(postBpmnParseHandlers, actualSetPostBpmnParseHandlersResult.getPostBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}.
   * <ul>
   *   <li>Then return PostBpmnParseHandlers size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setPostBpmnParseHandlers(List)"})
  public void testSetPostBpmnParseHandlers_thenReturnPostBpmnParseHandlersSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<BpmnParseHandler> postBpmnParseHandlers = new ArrayList<>();
    postBpmnParseHandlers.add(new AdhocSubProcessParseHandler());
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    postBpmnParseHandlers.add(adhocSubProcessParseHandler);

    // Act
    ProcessEngineConfigurationImpl actualSetPostBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setPostBpmnParseHandlers(postBpmnParseHandlers);

    // Assert
    List<BpmnParseHandler> postBpmnParseHandlers2 = actualSetPostBpmnParseHandlersResult.getPostBpmnParseHandlers();
    assertEquals(2, postBpmnParseHandlers2.size());
    BpmnParseHandler getResult = postBpmnParseHandlers2.get(1);
    assertTrue(getResult instanceof AdhocSubProcessParseHandler);
    assertTrue(actualSetPostBpmnParseHandlersResult instanceof JtaProcessEngineConfiguration);
    assertSame(adhocSubProcessParseHandler, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getActivityBehaviorFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getActivityBehaviorFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehaviorFactory ProcessEngineConfigurationImpl.getActivityBehaviorFactory()"})
  public void testGetActivityBehaviorFactory() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setActivityBehaviorFactory(ActivityBehaviorFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setActivityBehaviorFactory(ActivityBehaviorFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setActivityBehaviorFactory(ActivityBehaviorFactory)"})
  public void testSetActivityBehaviorFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act
    ProcessEngineConfigurationImpl actualSetActivityBehaviorFactoryResult = jtaProcessEngineConfiguration
        .setActivityBehaviorFactory(activityBehaviorFactory);

    // Assert
    assertSame(activityBehaviorFactory, jtaProcessEngineConfiguration.getActivityBehaviorFactory());
    assertSame(jtaProcessEngineConfiguration, actualSetActivityBehaviorFactoryResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getListenerFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getListenerFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ListenerFactory ProcessEngineConfigurationImpl.getListenerFactory()"})
  public void testGetListenerFactory() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getListenerFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setListenerFactory(ListenerFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setListenerFactory(ListenerFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setListenerFactory(ListenerFactory)"})
  public void testSetListenerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultListenerFactory listenerFactory = new DefaultListenerFactory();

    // Act
    ProcessEngineConfigurationImpl actualSetListenerFactoryResult = jtaProcessEngineConfiguration
        .setListenerFactory(listenerFactory);

    // Assert
    assertSame(listenerFactory, jtaProcessEngineConfiguration.getListenerFactory());
    assertSame(jtaProcessEngineConfiguration, actualSetListenerFactoryResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBpmnParseFactory(BpmnParseFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBpmnParseFactory(BpmnParseFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setBpmnParseFactory(BpmnParseFactory)"})
  public void testSetBpmnParseFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    BpmnParseFactory bpmnParseFactory = mock(BpmnParseFactory.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setBpmnParseFactory(bpmnParseFactory));
    assertSame(bpmnParseFactory, jtaProcessEngineConfiguration.getBpmnParseFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBeans()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBeans()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getBeans()"})
  public void testGetBeans() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getBeans());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBeans(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBeans(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setBeans(Map)"})
  public void testSetBeans() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HashMap<Object, Object> beans = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetBeansResult = jtaProcessEngineConfiguration.setBeans(beans);

    // Assert
    assertSame(beans, jtaProcessEngineConfiguration.getBeans());
    assertSame(jtaProcessEngineConfiguration, actualSetBeansResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResolverFactories()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getResolverFactories()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getResolverFactories()"})
  public void testGetResolverFactories() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getResolverFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}.
   * <ul>
   *   <li>Given {@link ResolverFactory}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link ResolverFactory}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setResolverFactories(List)"})
  public void testSetResolverFactories_givenResolverFactory_whenArrayListAddResolverFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(mock(ResolverFactory.class));

    // Act
    ProcessEngineConfigurationImpl actualSetResolverFactoriesResult = jtaProcessEngineConfiguration
        .setResolverFactories(resolverFactories);

    // Assert
    assertSame(resolverFactories, jtaProcessEngineConfiguration.getResolverFactories());
    assertSame(jtaProcessEngineConfiguration, actualSetResolverFactoriesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}.
   * <ul>
   *   <li>Given {@link ResolverFactory}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link ResolverFactory}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setResolverFactories(List)"})
  public void testSetResolverFactories_givenResolverFactory_whenArrayListAddResolverFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(mock(ResolverFactory.class));
    resolverFactories.add(mock(ResolverFactory.class));

    // Act
    ProcessEngineConfigurationImpl actualSetResolverFactoriesResult = jtaProcessEngineConfiguration
        .setResolverFactories(resolverFactories);

    // Assert
    assertSame(resolverFactories, jtaProcessEngineConfiguration.getResolverFactories());
    assertSame(jtaProcessEngineConfiguration, actualSetResolverFactoriesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setResolverFactories(List)"})
  public void testSetResolverFactories_whenArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetResolverFactoriesResult = jtaProcessEngineConfiguration
        .setResolverFactories(resolverFactories);

    // Assert
    assertSame(resolverFactories, jtaProcessEngineConfiguration.getResolverFactories());
    assertSame(jtaProcessEngineConfiguration, actualSetResolverFactoriesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomFunctionProviders()"})
  public void testGetCustomFunctionProviders() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomFunctionProviders());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomFunctionProviders(List)"})
  public void testSetCustomFunctionProviders_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetCustomFunctionProvidersResult = jtaProcessEngineConfiguration
        .setCustomFunctionProviders(customFunctionProviders);

    // Assert
    assertSame(customFunctionProviders, jtaProcessEngineConfiguration.getCustomFunctionProviders());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomFunctionProvidersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomFunctionProviders(List)"})
  public void testSetCustomFunctionProviders_givenCustomFunctionProvider2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));
    customFunctionProviders.add(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetCustomFunctionProvidersResult = jtaProcessEngineConfiguration
        .setCustomFunctionProviders(customFunctionProviders);

    // Assert
    assertSame(customFunctionProviders, jtaProcessEngineConfiguration.getCustomFunctionProviders());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomFunctionProvidersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomFunctionProviders(List)"})
  public void testSetCustomFunctionProviders_whenArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomFunctionProvidersResult = jtaProcessEngineConfiguration
        .setCustomFunctionProviders(customFunctionProviders);

    // Assert
    assertSame(customFunctionProviders, jtaProcessEngineConfiguration.getCustomFunctionProviders());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomFunctionProvidersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addCustomFunctionProvider(CustomFunctionProvider)"})
  public void testAddCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomFunctionProviders(new ArrayList<>());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class)));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addCustomFunctionProvider(CustomFunctionProvider)"})
  public void testAddCustomFunctionProvider_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class)));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDeploymentManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentManager ProcessEngineConfigurationImpl.getDeploymentManager()"})
  public void testGetDeploymentManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeploymentManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDeploymentManager(DeploymentManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeploymentManager(DeploymentManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDeploymentManager(DeploymentManager)"})
  public void testSetDeploymentManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentManager deploymentManager = new DeploymentManager();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDeploymentManager(deploymentManager));
    assertSame(deploymentManager, jtaProcessEngineConfiguration.getDeploymentManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDelegateInterceptor(DelegateInterceptor)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDelegateInterceptor(DelegateInterceptor)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDelegateInterceptor(DelegateInterceptor)"})
  public void testSetDelegateInterceptor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DelegateInterceptor delegateInterceptor = mock(DelegateInterceptor.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDelegateInterceptor(delegateInterceptor));
    assertSame(delegateInterceptor, jtaProcessEngineConfiguration.getDelegateInterceptor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventHandler(String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventHandler(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"EventHandler ProcessEngineConfigurationImpl.getEventHandler(String)"})
  public void testGetEventHandler_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventHandlers(new HashMap<>());

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventHandler("Event Type"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventHandlers(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventHandlers(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEventHandlers(Map)"})
  public void testSetEventHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HashMap<String, EventHandler> eventHandlers = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetEventHandlersResult = jtaProcessEngineConfiguration
        .setEventHandlers(eventHandlers);

    // Assert
    assertSame(eventHandlers, jtaProcessEngineConfiguration.getEventHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetEventHandlersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getEventHandlers()"})
  public void testGetEventHandlers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomEventHandlers()"})
  public void testGetCustomEventHandlers() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomEventHandlers(List)"})
  public void testSetCustomEventHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<EventHandler> customEventHandlers = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomEventHandlersResult = jtaProcessEngineConfiguration
        .setCustomEventHandlers(customEventHandlers);

    // Assert
    assertSame(customEventHandlers, jtaProcessEngineConfiguration.getCustomEventHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomEventHandlersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}.
   * <ul>
   *   <li>Then return CustomEventHandlers is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomEventHandlers(List)"})
  public void testSetCustomEventHandlers_thenReturnCustomEventHandlersIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<EventHandler> customEventHandlers = new ArrayList<>();
    customEventHandlers.add(new CompensationEventHandler());

    // Act
    ProcessEngineConfigurationImpl actualSetCustomEventHandlersResult = jtaProcessEngineConfiguration
        .setCustomEventHandlers(customEventHandlers);

    // Assert
    assertTrue(actualSetCustomEventHandlersResult instanceof JtaProcessEngineConfiguration);
    assertSame(customEventHandlers, actualSetCustomEventHandlersResult.getCustomEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}.
   * <ul>
   *   <li>Then return CustomEventHandlers size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCustomEventHandlers(List)"})
  public void testSetCustomEventHandlers_thenReturnCustomEventHandlersSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<EventHandler> customEventHandlers = new ArrayList<>();
    customEventHandlers.add(new CompensationEventHandler());
    CompensationEventHandler compensationEventHandler = new CompensationEventHandler();
    customEventHandlers.add(compensationEventHandler);

    // Act
    ProcessEngineConfigurationImpl actualSetCustomEventHandlersResult = jtaProcessEngineConfiguration
        .setCustomEventHandlers(customEventHandlers);

    // Assert
    assertTrue(actualSetCustomEventHandlersResult instanceof JtaProcessEngineConfiguration);
    List<EventHandler> customEventHandlers2 = actualSetCustomEventHandlersResult.getCustomEventHandlers();
    assertEquals(2, customEventHandlers2.size());
    EventHandler getResult = customEventHandlers2.get(1);
    assertTrue(getResult instanceof CompensationEventHandler);
    assertEquals("compensate", getResult.getEventHandlerType());
    assertSame(compensationEventHandler, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setFailedJobCommandFactory(FailedJobCommandFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setFailedJobCommandFactory(FailedJobCommandFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setFailedJobCommandFactory(FailedJobCommandFactory)"})
  public void testSetFailedJobCommandFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    FailedJobCommandFactory failedJobCommandFactory = mock(FailedJobCommandFactory.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setFailedJobCommandFactory(failedJobCommandFactory));
    assertSame(failedJobCommandFactory, jtaProcessEngineConfiguration.getFailedJobCommandFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSource()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DataSource ProcessEngineConfigurationImpl.getIdGeneratorDataSource()"})
  public void testGetIdGeneratorDataSource() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getIdGeneratorDataSource());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setIdGeneratorDataSource(DataSource)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setIdGeneratorDataSource(DataSource)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setIdGeneratorDataSource(DataSource)"})
  public void testSetIdGeneratorDataSource() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DataSource idGeneratorDataSource = mock(DataSource.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setIdGeneratorDataSource(idGeneratorDataSource));
    assertSame(idGeneratorDataSource, jtaProcessEngineConfiguration.getIdGeneratorDataSource());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getIdGeneratorDataSourceJndiName()"})
  public void testGetIdGeneratorDataSourceJndiName() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getIdGeneratorDataSourceJndiName());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setIdGeneratorDataSourceJndiName(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setIdGeneratorDataSourceJndiName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setIdGeneratorDataSourceJndiName(String)"})
  public void testSetIdGeneratorDataSourceJndiName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetIdGeneratorDataSourceJndiNameResult = jtaProcessEngineConfiguration
        .setIdGeneratorDataSourceJndiName("Id Generator Data Source Jndi Name");

    // Assert
    assertEquals("Id Generator Data Source Jndi Name",
        jtaProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertSame(jtaProcessEngineConfiguration, actualSetIdGeneratorDataSourceJndiNameResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBatchSizeProcessInstances()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBatchSizeProcessInstances()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getBatchSizeProcessInstances()"})
  public void testGetBatchSizeProcessInstances() {
    // Arrange, Act and Assert
    assertEquals(25, (new JtaProcessEngineConfiguration()).getBatchSizeProcessInstances());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBatchSizeProcessInstances(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBatchSizeProcessInstances(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setBatchSizeProcessInstances(int)"})
  public void testSetBatchSizeProcessInstances() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetBatchSizeProcessInstancesResult = jtaProcessEngineConfiguration
        .setBatchSizeProcessInstances(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertSame(jtaProcessEngineConfiguration, actualSetBatchSizeProcessInstancesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBatchSizeTasks()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBatchSizeTasks()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getBatchSizeTasks()"})
  public void testGetBatchSizeTasks() {
    // Arrange, Act and Assert
    assertEquals(25, (new JtaProcessEngineConfiguration()).getBatchSizeTasks());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBatchSizeTasks(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBatchSizeTasks(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setBatchSizeTasks(int)"})
  public void testSetBatchSizeTasks() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetBatchSizeTasksResult = jtaProcessEngineConfiguration.setBatchSizeTasks(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getBatchSizeTasks());
    assertSame(jtaProcessEngineConfiguration, actualSetBatchSizeTasksResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionCacheLimit()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionCacheLimit()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getProcessDefinitionCacheLimit()"})
  public void testGetProcessDefinitionCacheLimit() {
    // Arrange, Act and Assert
    assertEquals(-1, (new JtaProcessEngineConfiguration()).getProcessDefinitionCacheLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setProcessDefinitionCacheLimit(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setProcessDefinitionCacheLimit(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setProcessDefinitionCacheLimit(int)"})
  public void testSetProcessDefinitionCacheLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetProcessDefinitionCacheLimitResult = jtaProcessEngineConfiguration
        .setProcessDefinitionCacheLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetProcessDefinitionCacheLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionCache()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentCache ProcessEngineConfigurationImpl.getProcessDefinitionCache()"})
  public void testGetProcessDefinitionCache() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setProcessDefinitionCache(DeploymentCache)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setProcessDefinitionCache(DeploymentCache)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setProcessDefinitionCache(DeploymentCache)"})
  public void testSetProcessDefinitionCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultDeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache = new DefaultDeploymentCache<>();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionCache(processDefinitionCache));
    assertSame(processDefinitionCache, jtaProcessEngineConfiguration.getProcessDefinitionCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCacheLimit()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCacheLimit()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getKnowledgeBaseCacheLimit()"})
  public void testGetKnowledgeBaseCacheLimit() {
    // Arrange, Act and Assert
    assertEquals(-1, (new JtaProcessEngineConfiguration()).getKnowledgeBaseCacheLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCacheLimit(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCacheLimit(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setKnowledgeBaseCacheLimit(int)"})
  public void testSetKnowledgeBaseCacheLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetKnowledgeBaseCacheLimitResult = jtaProcessEngineConfiguration
        .setKnowledgeBaseCacheLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetKnowledgeBaseCacheLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCache()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentCache ProcessEngineConfigurationImpl.getKnowledgeBaseCache()"})
  public void testGetKnowledgeBaseCache() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getKnowledgeBaseCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCache(DeploymentCache)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCache(DeploymentCache)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setKnowledgeBaseCache(DeploymentCache)"})
  public void testSetKnowledgeBaseCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultDeploymentCache<Object> knowledgeBaseCache = new DefaultDeploymentCache<>();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setKnowledgeBaseCache(knowledgeBaseCache));
    assertSame(knowledgeBaseCache, jtaProcessEngineConfiguration.getKnowledgeBaseCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableSafeBpmnXml()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isEnableSafeBpmnXml()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isEnableSafeBpmnXml()"})
  public void testIsEnableSafeBpmnXml_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isEnableSafeBpmnXml());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableSafeBpmnXml()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isEnableSafeBpmnXml()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isEnableSafeBpmnXml()"})
  public void testIsEnableSafeBpmnXml_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableSafeBpmnXml(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableSafeBpmnXml());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableSafeBpmnXml(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEnableSafeBpmnXml(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEnableSafeBpmnXml(boolean)"})
  public void testSetEnableSafeBpmnXml() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetEnableSafeBpmnXmlResult = jtaProcessEngineConfiguration
        .setEnableSafeBpmnXml(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableSafeBpmnXmlResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivitiEventDispatcher ProcessEngineConfigurationImpl.getEventDispatcher()"})
  public void testGetEventDispatcher() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventDispatcher());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventDispatcher(ActivitiEventDispatcher)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventDispatcher(ActivitiEventDispatcher)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEventDispatcher(ActivitiEventDispatcher)"})
  public void testSetEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ActivitiEventDispatcherImpl eventDispatcher = new ActivitiEventDispatcherImpl();

    // Act
    ProcessEngineConfigurationImpl actualSetEventDispatcherResult = jtaProcessEngineConfiguration
        .setEventDispatcher(eventDispatcher);

    // Assert
    assertSame(eventDispatcher, jtaProcessEngineConfiguration.getEventDispatcher());
    assertSame(jtaProcessEngineConfiguration, actualSetEventDispatcherResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableEventDispatcher(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEnableEventDispatcher(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEnableEventDispatcher(boolean)"})
  public void testSetEnableEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableEventDispatcher(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getTypedEventListeners()"})
  public void testGetTypedEventListeners() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTypedEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTypedEventListeners(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTypedEventListeners(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTypedEventListeners(Map)"})
  public void testSetTypedEventListeners() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetTypedEventListenersResult = jtaProcessEngineConfiguration
        .setTypedEventListeners(typedListeners);

    // Assert
    assertSame(typedListeners, jtaProcessEngineConfiguration.getTypedEventListeners());
    assertSame(jtaProcessEngineConfiguration, actualSetTypedEventListenersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventListeners()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventListeners()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getEventListeners()"})
  public void testGetEventListeners() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventListeners(List)}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) EventListeners is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventListeners(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEventListeners(List)"})
  public void testSetEventListeners_thenJtaProcessEngineConfigurationEventListenersIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<ActivitiEventListener> eventListeners = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetEventListenersResult = jtaProcessEngineConfiguration
        .setEventListeners(eventListeners);

    // Assert
    assertSame(eventListeners, jtaProcessEngineConfiguration.getEventListeners());
    assertSame(jtaProcessEngineConfiguration, actualSetEventListenersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventListeners(List)}.
   * <ul>
   *   <li>Then return EventListeners is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventListeners(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEventListeners(List)"})
  public void testSetEventListeners_thenReturnEventListenersIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<ActivitiEventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new BaseEntityEventListener(true));

    // Act
    ProcessEngineConfigurationImpl actualSetEventListenersResult = jtaProcessEngineConfiguration
        .setEventListeners(eventListeners);

    // Assert
    assertTrue(actualSetEventListenersResult instanceof JtaProcessEngineConfiguration);
    assertSame(eventListeners, actualSetEventListenersResult.getEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventListeners(List)}.
   * <ul>
   *   <li>Then return EventListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventListeners(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEventListeners(List)"})
  public void testSetEventListeners_thenReturnEventListenersSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<ActivitiEventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new BaseEntityEventListener(true));
    BaseEntityEventListener baseEntityEventListener = new BaseEntityEventListener(true);
    eventListeners.add(baseEntityEventListener);

    // Act
    ProcessEngineConfigurationImpl actualSetEventListenersResult = jtaProcessEngineConfiguration
        .setEventListeners(eventListeners);

    // Assert
    List<ActivitiEventListener> eventListeners2 = actualSetEventListenersResult.getEventListeners();
    assertEquals(2, eventListeners2.size());
    ActivitiEventListener getResult = eventListeners2.get(1);
    assertTrue(getResult instanceof BaseEntityEventListener);
    assertTrue(actualSetEventListenersResult instanceof JtaProcessEngineConfiguration);
    assertTrue(getResult.isFailOnException());
    assertSame(baseEntityEventListener, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessValidator()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessValidator()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessValidator ProcessEngineConfigurationImpl.getProcessValidator()"})
  public void testGetProcessValidator() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessValidator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setProcessValidator(ProcessValidator)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setProcessValidator(ProcessValidator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setProcessValidator(ProcessValidator)"})
  public void testSetProcessValidator() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ActivitiTestCaseProcessValidator processValidator = new ActivitiTestCaseProcessValidator();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setProcessValidator(processValidator));
    assertSame(processValidator, jtaProcessEngineConfiguration.getProcessValidator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableEventDispatcher()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isEnableEventDispatcher()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isEnableEventDispatcher()"})
  public void testIsEnableEventDispatcher() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isEnableEventDispatcher());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isEnableDatabaseEventLogging()"})
  public void testIsEnableDatabaseEventLogging_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isEnableDatabaseEventLogging());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isEnableDatabaseEventLogging()"})
  public void testIsEnableDatabaseEventLogging_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableDatabaseEventLogging(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableDatabaseEventLogging());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableDatabaseEventLogging(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEnableDatabaseEventLogging(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEnableDatabaseEventLogging(boolean)"})
  public void testSetEnableDatabaseEventLogging() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetEnableDatabaseEventLoggingResult = jtaProcessEngineConfiguration
        .setEnableDatabaseEventLogging(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableDatabaseEventLoggingResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthStringVariableType()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthStringVariableType()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthStringVariableType()"})
  public void testGetMaxLengthStringVariableType() {
    // Arrange, Act and Assert
    assertEquals(-1, (new JtaProcessEngineConfiguration()).getMaxLengthStringVariableType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setMaxLengthStringVariableType(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setMaxLengthStringVariableType(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setMaxLengthStringVariableType(int)"})
  public void testSetMaxLengthStringVariableType() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetMaxLengthStringVariableTypeResult = jtaProcessEngineConfiguration
        .setMaxLengthStringVariableType(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getMaxLengthString());
    assertEquals(3, jtaProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertSame(jtaProcessEngineConfiguration, actualSetMaxLengthStringVariableTypeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isBulkInsertEnabled()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isBulkInsertEnabled()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isBulkInsertEnabled()"})
  public void testIsBulkInsertEnabled() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isBulkInsertEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBulkInsertEnabled(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBulkInsertEnabled(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setBulkInsertEnabled(boolean)"})
  public void testSetBulkInsertEnabled() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setBulkInsertEnabled(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxNrOfStatementsInBulkInsert()"})
  public void testGetMaxNrOfStatementsInBulkInsert() {
    // Arrange, Act and Assert
    assertEquals(100, (new JtaProcessEngineConfiguration()).getMaxNrOfStatementsInBulkInsert());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setMaxNrOfStatementsInBulkInsert(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setMaxNrOfStatementsInBulkInsert(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setMaxNrOfStatementsInBulkInsert(int)"})
  public void testSetMaxNrOfStatementsInBulkInsert() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetMaxNrOfStatementsInBulkInsertResult = jtaProcessEngineConfiguration
        .setMaxNrOfStatementsInBulkInsert(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertSame(jtaProcessEngineConfiguration, actualSetMaxNrOfStatementsInBulkInsertResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isUsingRelationalDatabase()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isUsingRelationalDatabase()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isUsingRelationalDatabase()"})
  public void testIsUsingRelationalDatabase() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isUsingRelationalDatabase());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setUsingRelationalDatabase(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setUsingRelationalDatabase(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setUsingRelationalDatabase(boolean)"})
  public void testSetUsingRelationalDatabase() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setUsingRelationalDatabase(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isEnableVerboseExecutionTreeLogging()"})
  public void testIsEnableVerboseExecutionTreeLogging_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isEnableVerboseExecutionTreeLogging());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isEnableVerboseExecutionTreeLogging()"})
  public void testIsEnableVerboseExecutionTreeLogging_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableVerboseExecutionTreeLogging(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEnableVerboseExecutionTreeLogging(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEnableVerboseExecutionTreeLogging(boolean)"})
  public void testSetEnableVerboseExecutionTreeLogging() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetEnableVerboseExecutionTreeLoggingResult = jtaProcessEngineConfiguration
        .setEnableVerboseExecutionTreeLogging(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableVerboseExecutionTreeLoggingResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableEagerExecutionTreeFetching(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEnableEagerExecutionTreeFetching(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEnableEagerExecutionTreeFetching(boolean)"})
  public void testSetEnableEagerExecutionTreeFetching() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableEagerExecutionTreeFetching(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableExecutionRelationshipCounts(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEnableExecutionRelationshipCounts(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEnableExecutionRelationshipCounts(boolean)"})
  public void testSetEnableExecutionRelationshipCounts() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableExecutionRelationshipCounts(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"PerformanceSettings ProcessEngineConfigurationImpl.getPerformanceSettings()"})
  public void testGetPerformanceSettings() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration.performanceSettings,
        jtaProcessEngineConfiguration.getPerformanceSettings());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPerformanceSettings(PerformanceSettings)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setPerformanceSettings(PerformanceSettings)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setPerformanceSettings(PerformanceSettings)"})
  public void testSetPerformanceSettings() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    // Act
    jtaProcessEngineConfiguration.setPerformanceSettings(performanceSettings);

    // Assert
    assertSame(performanceSettings, jtaProcessEngineConfiguration.getPerformanceSettings());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableLocalization(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEnableLocalization(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEnableLocalization(boolean)"})
  public void testSetEnableLocalization() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableLocalization(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AttachmentDataManager ProcessEngineConfigurationImpl.getAttachmentDataManager()"})
  public void testGetAttachmentDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAttachmentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAttachmentDataManager(AttachmentDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAttachmentDataManager(AttachmentDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAttachmentDataManager(AttachmentDataManager)"})
  public void testSetAttachmentDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisAttachmentDataManager attachmentDataManager = new MybatisAttachmentDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAttachmentDataManager(attachmentDataManager));
    assertSame(attachmentDataManager, jtaProcessEngineConfiguration.getAttachmentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getByteArrayDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getByteArrayDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ByteArrayDataManager ProcessEngineConfigurationImpl.getByteArrayDataManager()"})
  public void testGetByteArrayDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getByteArrayDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setByteArrayDataManager(ByteArrayDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setByteArrayDataManager(ByteArrayDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setByteArrayDataManager(ByteArrayDataManager)"})
  public void testSetByteArrayDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisByteArrayDataManager byteArrayDataManager = new MybatisByteArrayDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setByteArrayDataManager(byteArrayDataManager));
    assertSame(byteArrayDataManager, jtaProcessEngineConfiguration.getByteArrayDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommentDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCommentDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommentDataManager ProcessEngineConfigurationImpl.getCommentDataManager()"})
  public void testGetCommentDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCommentDataManager(CommentDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCommentDataManager(CommentDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCommentDataManager(CommentDataManager)"})
  public void testSetCommentDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisCommentDataManager commentDataManager = new MybatisCommentDataManager(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setCommentDataManager(commentDataManager));
    assertSame(commentDataManager, jtaProcessEngineConfiguration.getCommentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDeploymentDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentDataManager ProcessEngineConfigurationImpl.getDeploymentDataManager()"})
  public void testGetDeploymentDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeploymentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDeploymentDataManager(DeploymentDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeploymentDataManager(DeploymentDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDeploymentDataManager(DeploymentDataManager)"})
  public void testSetDeploymentDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisDeploymentDataManager deploymentDataManager = new MybatisDeploymentDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDeploymentDataManager(deploymentDataManager));
    assertSame(deploymentDataManager, jtaProcessEngineConfiguration.getDeploymentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventLogEntryDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventLogEntryDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"EventLogEntryDataManager ProcessEngineConfigurationImpl.getEventLogEntryDataManager()"})
  public void testGetEventLogEntryDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventLogEntryDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventLogEntryDataManager(EventLogEntryDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventLogEntryDataManager(EventLogEntryDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEventLogEntryDataManager(EventLogEntryDataManager)"})
  public void testSetEventLogEntryDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisEventLogEntryDataManager eventLogEntryDataManager = new MybatisEventLogEntryDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setEventLogEntryDataManager(eventLogEntryDataManager));
    assertSame(eventLogEntryDataManager, jtaProcessEngineConfiguration.getEventLogEntryDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"EventSubscriptionDataManager ProcessEngineConfigurationImpl.getEventSubscriptionDataManager()"})
  public void testGetEventSubscriptionDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventSubscriptionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventSubscriptionDataManager(EventSubscriptionDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventSubscriptionDataManager(EventSubscriptionDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEventSubscriptionDataManager(EventSubscriptionDataManager)"})
  public void testSetEventSubscriptionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisEventSubscriptionDataManager eventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setEventSubscriptionDataManager(eventSubscriptionDataManager));
    assertSame(eventSubscriptionDataManager, jtaProcessEngineConfiguration.getEventSubscriptionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getExecutionDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionDataManager ProcessEngineConfigurationImpl.getExecutionDataManager()"})
  public void testGetExecutionDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getExecutionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setExecutionDataManager(ExecutionDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setExecutionDataManager(ExecutionDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setExecutionDataManager(ExecutionDataManager)"})
  public void testSetExecutionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisExecutionDataManager executionDataManager = new MybatisExecutionDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setExecutionDataManager(executionDataManager));
    assertSame(executionDataManager, jtaProcessEngineConfiguration.getExecutionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricActivityInstanceDataManager ProcessEngineConfigurationImpl.getHistoricActivityInstanceDataManager()"})
  public void testGetHistoricActivityInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricActivityInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)"})
  public void testSetHistoricActivityInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricActivityInstanceDataManager historicActivityInstanceDataManager = new MybatisHistoricActivityInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricActivityInstanceDataManager(historicActivityInstanceDataManager));
    assertSame(historicActivityInstanceDataManager,
        jtaProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricDetailDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricDetailDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricDetailDataManager ProcessEngineConfigurationImpl.getHistoricDetailDataManager()"})
  public void testGetHistoricDetailDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricDetailDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricDetailDataManager(HistoricDetailDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricDetailDataManager(HistoricDetailDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricDetailDataManager(HistoricDetailDataManager)"})
  public void testSetHistoricDetailDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricDetailDataManager historicDetailDataManager = new MybatisHistoricDetailDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricDetailDataManager(historicDetailDataManager));
    assertSame(historicDetailDataManager, jtaProcessEngineConfiguration.getHistoricDetailDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricIdentityLinkDataManager ProcessEngineConfigurationImpl.getHistoricIdentityLinkDataManager()"})
  public void testGetHistoricIdentityLinkDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)"})
  public void testSetHistoricIdentityLinkDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricIdentityLinkDataManager historicIdentityLinkDataManager = new MybatisHistoricIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricIdentityLinkDataManager(historicIdentityLinkDataManager));
    assertSame(historicIdentityLinkDataManager, jtaProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricProcessInstanceDataManager ProcessEngineConfigurationImpl.getHistoricProcessInstanceDataManager()"})
  public void testGetHistoricProcessInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricProcessInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)"})
  public void testSetHistoricProcessInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricProcessInstanceDataManager historicProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricProcessInstanceDataManager(historicProcessInstanceDataManager));
    assertSame(historicProcessInstanceDataManager,
        jtaProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceDataManager ProcessEngineConfigurationImpl.getHistoricTaskInstanceDataManager()"})
  public void testGetHistoricTaskInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricTaskInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)"})
  public void testSetHistoricTaskInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricTaskInstanceDataManager historicTaskInstanceDataManager = new MybatisHistoricTaskInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricTaskInstanceDataManager(historicTaskInstanceDataManager));
    assertSame(historicTaskInstanceDataManager, jtaProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricVariableInstanceDataManager ProcessEngineConfigurationImpl.getHistoricVariableInstanceDataManager()"})
  public void testGetHistoricVariableInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)"})
  public void testSetHistoricVariableInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricVariableInstanceDataManager historicVariableInstanceDataManager = new MybatisHistoricVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricVariableInstanceDataManager(historicVariableInstanceDataManager));
    assertSame(historicVariableInstanceDataManager,
        jtaProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdentityLinkDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getIdentityLinkDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IdentityLinkDataManager ProcessEngineConfigurationImpl.getIdentityLinkDataManager()"})
  public void testGetIdentityLinkDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setIdentityLinkDataManager(IdentityLinkDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setIdentityLinkDataManager(IdentityLinkDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setIdentityLinkDataManager(IdentityLinkDataManager)"})
  public void testSetIdentityLinkDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisIdentityLinkDataManager identityLinkDataManager = new MybatisIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setIdentityLinkDataManager(identityLinkDataManager));
    assertSame(identityLinkDataManager, jtaProcessEngineConfiguration.getIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getJobDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JobDataManager ProcessEngineConfigurationImpl.getJobDataManager()"})
  public void testGetJobDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setJobDataManager(JobDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setJobDataManager(JobDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setJobDataManager(JobDataManager)"})
  public void testSetJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisJobDataManager jobDataManager = new MybatisJobDataManager(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setJobDataManager(jobDataManager));
    assertSame(jobDataManager, jtaProcessEngineConfiguration.getJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerJobDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTimerJobDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TimerJobDataManager ProcessEngineConfigurationImpl.getTimerJobDataManager()"})
  public void testGetTimerJobDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTimerJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTimerJobDataManager(TimerJobDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTimerJobDataManager(TimerJobDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTimerJobDataManager(TimerJobDataManager)"})
  public void testSetTimerJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisTimerJobDataManager timerJobDataManager = new MybatisTimerJobDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setTimerJobDataManager(timerJobDataManager));
    assertSame(timerJobDataManager, jtaProcessEngineConfiguration.getTimerJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSuspendedJobDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getSuspendedJobDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobDataManager ProcessEngineConfigurationImpl.getSuspendedJobDataManager()"})
  public void testGetSuspendedJobDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSuspendedJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setSuspendedJobDataManager(SuspendedJobDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setSuspendedJobDataManager(SuspendedJobDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setSuspendedJobDataManager(SuspendedJobDataManager)"})
  public void testSetSuspendedJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisSuspendedJobDataManager suspendedJobDataManager = new MybatisSuspendedJobDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setSuspendedJobDataManager(suspendedJobDataManager));
    assertSame(suspendedJobDataManager, jtaProcessEngineConfiguration.getSuspendedJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeadLetterJobDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDeadLetterJobDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeadLetterJobDataManager ProcessEngineConfigurationImpl.getDeadLetterJobDataManager()"})
  public void testGetDeadLetterJobDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeadLetterJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDeadLetterJobDataManager(DeadLetterJobDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeadLetterJobDataManager(DeadLetterJobDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDeadLetterJobDataManager(DeadLetterJobDataManager)"})
  public void testSetDeadLetterJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisDeadLetterJobDataManager deadLetterJobDataManager = new MybatisDeadLetterJobDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDeadLetterJobDataManager(deadLetterJobDataManager));
    assertSame(deadLetterJobDataManager, jtaProcessEngineConfiguration.getDeadLetterJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getModelDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getModelDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ModelDataManager ProcessEngineConfigurationImpl.getModelDataManager()"})
  public void testGetModelDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getModelDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setModelDataManager(ModelDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setModelDataManager(ModelDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setModelDataManager(ModelDataManager)"})
  public void testSetModelDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisModelDataManager modelDataManager = new MybatisModelDataManager(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setModelDataManager(modelDataManager));
    assertSame(modelDataManager, jtaProcessEngineConfiguration.getModelDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionDataManager ProcessEngineConfigurationImpl.getProcessDefinitionDataManager()"})
  public void testGetProcessDefinitionDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setProcessDefinitionDataManager(ProcessDefinitionDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setProcessDefinitionDataManager(ProcessDefinitionDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setProcessDefinitionDataManager(ProcessDefinitionDataManager)"})
  public void testSetProcessDefinitionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisProcessDefinitionDataManager processDefinitionDataManager = new MybatisProcessDefinitionDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionDataManager(processDefinitionDataManager));
    assertSame(processDefinitionDataManager, jtaProcessEngineConfiguration.getProcessDefinitionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionInfoDataManager ProcessEngineConfigurationImpl.getProcessDefinitionInfoDataManager()"})
  public void testGetProcessDefinitionInfoDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionInfoDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoDataManager(ProcessDefinitionInfoDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoDataManager(ProcessDefinitionInfoDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setProcessDefinitionInfoDataManager(ProcessDefinitionInfoDataManager)"})
  public void testSetProcessDefinitionInfoDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisProcessDefinitionInfoDataManager processDefinitionInfoDataManager = new MybatisProcessDefinitionInfoDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionInfoDataManager(processDefinitionInfoDataManager));
    assertSame(processDefinitionInfoDataManager, jtaProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPropertyDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getPropertyDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"PropertyDataManager ProcessEngineConfigurationImpl.getPropertyDataManager()"})
  public void testGetPropertyDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getPropertyDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPropertyDataManager(PropertyDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setPropertyDataManager(PropertyDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setPropertyDataManager(PropertyDataManager)"})
  public void testSetPropertyDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisPropertyDataManager propertyDataManager = new MybatisPropertyDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setPropertyDataManager(propertyDataManager));
    assertSame(propertyDataManager, jtaProcessEngineConfiguration.getPropertyDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResourceDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getResourceDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ResourceDataManager ProcessEngineConfigurationImpl.getResourceDataManager()"})
  public void testGetResourceDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getResourceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setResourceDataManager(ResourceDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setResourceDataManager(ResourceDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setResourceDataManager(ResourceDataManager)"})
  public void testSetResourceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisResourceDataManager resourceDataManager = new MybatisResourceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setResourceDataManager(resourceDataManager));
    assertSame(resourceDataManager, jtaProcessEngineConfiguration.getResourceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTaskDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskDataManager ProcessEngineConfigurationImpl.getTaskDataManager()"})
  public void testGetTaskDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTaskDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTaskDataManager(TaskDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTaskDataManager(TaskDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTaskDataManager(TaskDataManager)"})
  public void testSetTaskDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisTaskDataManager taskDataManager = new MybatisTaskDataManager(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTaskDataManager(taskDataManager));
    assertSame(taskDataManager, jtaProcessEngineConfiguration.getTaskDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableInstanceDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getVariableInstanceDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstanceDataManager ProcessEngineConfigurationImpl.getVariableInstanceDataManager()"})
  public void testGetVariableInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setVariableInstanceDataManager(VariableInstanceDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setVariableInstanceDataManager(VariableInstanceDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setVariableInstanceDataManager(VariableInstanceDataManager)"})
  public void testSetVariableInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisVariableInstanceDataManager variableInstanceDataManager = new MybatisVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setVariableInstanceDataManager(variableInstanceDataManager));
    assertSame(variableInstanceDataManager, jtaProcessEngineConfiguration.getVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableConfiguratorServiceLoader()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isEnableConfiguratorServiceLoader()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isEnableConfiguratorServiceLoader()"})
  public void testIsEnableConfiguratorServiceLoader() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isEnableConfiguratorServiceLoader());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AttachmentEntityManager ProcessEngineConfigurationImpl.getAttachmentEntityManager()"})
  public void testGetAttachmentEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAttachmentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAttachmentEntityManager(AttachmentEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAttachmentEntityManager(AttachmentEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAttachmentEntityManager(AttachmentEntityManager)"})
  public void testSetAttachmentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManager = new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAttachmentEntityManager(attachmentEntityManager));
    assertSame(attachmentEntityManager, jtaProcessEngineConfiguration.getAttachmentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getByteArrayEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getByteArrayEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ByteArrayEntityManager ProcessEngineConfigurationImpl.getByteArrayEntityManager()"})
  public void testGetByteArrayEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getByteArrayEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setByteArrayEntityManager(ByteArrayEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setByteArrayEntityManager(ByteArrayEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setByteArrayEntityManager(ByteArrayEntityManager)"})
  public void testSetByteArrayEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ByteArrayEntityManagerImpl byteArrayEntityManager = new ByteArrayEntityManagerImpl(processEngineConfiguration,
        new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setByteArrayEntityManager(byteArrayEntityManager));
    assertSame(byteArrayEntityManager, jtaProcessEngineConfiguration.getByteArrayEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommentEntityManager ProcessEngineConfigurationImpl.getCommentEntityManager()"})
  public void testGetCommentEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCommentEntityManager(CommentEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setCommentEntityManager(CommentEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setCommentEntityManager(CommentEntityManager)"})
  public void testSetCommentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommentEntityManagerImpl commentEntityManager = new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setCommentEntityManager(commentEntityManager));
    assertSame(commentEntityManager, jtaProcessEngineConfiguration.getCommentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDeploymentEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentEntityManager ProcessEngineConfigurationImpl.getDeploymentEntityManager()"})
  public void testGetDeploymentEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeploymentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDeploymentEntityManager(DeploymentEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeploymentEntityManager(DeploymentEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDeploymentEntityManager(DeploymentEntityManager)"})
  public void testSetDeploymentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentEntityManagerImpl deploymentEntityManager = new DeploymentEntityManagerImpl(processEngineConfiguration,
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDeploymentEntityManager(deploymentEntityManager));
    assertSame(deploymentEntityManager, jtaProcessEngineConfiguration.getDeploymentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventLogEntryEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventLogEntryEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"EventLogEntryEntityManager ProcessEngineConfigurationImpl.getEventLogEntryEntityManager()"})
  public void testGetEventLogEntryEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventLogEntryEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventLogEntryEntityManager(EventLogEntryEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventLogEntryEntityManager(EventLogEntryEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEventLogEntryEntityManager(EventLogEntryEntityManager)"})
  public void testSetEventLogEntryEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventLogEntryEntityManagerImpl eventLogEntryEntityManager = new EventLogEntryEntityManagerImpl(
        processEngineConfiguration, new MybatisEventLogEntryDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setEventLogEntryEntityManager(eventLogEntryEntityManager));
    assertSame(eventLogEntryEntityManager, jtaProcessEngineConfiguration.getEventLogEntryEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "EventSubscriptionEntityManager ProcessEngineConfigurationImpl.getEventSubscriptionEntityManager()"})
  public void testGetEventSubscriptionEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventSubscriptionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventSubscriptionEntityManager(EventSubscriptionEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventSubscriptionEntityManager(EventSubscriptionEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setEventSubscriptionEntityManager(EventSubscriptionEntityManager)"})
  public void testSetEventSubscriptionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManager = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setEventSubscriptionEntityManager(eventSubscriptionEntityManager));
    assertSame(eventSubscriptionEntityManager, jtaProcessEngineConfiguration.getEventSubscriptionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getExecutionEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntityManager ProcessEngineConfigurationImpl.getExecutionEntityManager()"})
  public void testGetExecutionEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getExecutionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setExecutionEntityManager(ExecutionEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setExecutionEntityManager(ExecutionEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setExecutionEntityManager(ExecutionEntityManager)"})
  public void testSetExecutionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManager = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setExecutionEntityManager(executionEntityManager));
    assertSame(executionEntityManager, jtaProcessEngineConfiguration.getExecutionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricActivityInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricActivityInstanceEntityManager()"})
  public void testGetHistoricActivityInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceEntityManager(HistoricActivityInstanceEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceEntityManager(HistoricActivityInstanceEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricActivityInstanceEntityManager(HistoricActivityInstanceEntityManager)"})
  public void testSetHistoricActivityInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManager = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfiguration,
        new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricActivityInstanceEntityManager(historicActivityInstanceEntityManager));
    assertSame(historicActivityInstanceEntityManager,
        jtaProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricDetailEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricDetailEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricDetailEntityManager ProcessEngineConfigurationImpl.getHistoricDetailEntityManager()"})
  public void testGetHistoricDetailEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricDetailEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricDetailEntityManager(HistoricDetailEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricDetailEntityManager(HistoricDetailEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricDetailEntityManager(HistoricDetailEntityManager)"})
  public void testSetHistoricDetailEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricDetailEntityManagerImpl historicDetailEntityManager = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricDetailDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricDetailEntityManager(historicDetailEntityManager));
    assertSame(historicDetailEntityManager, jtaProcessEngineConfiguration.getHistoricDetailEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricIdentityLinkEntityManager ProcessEngineConfigurationImpl.getHistoricIdentityLinkEntityManager()"})
  public void testGetHistoricIdentityLinkEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkEntityManager(HistoricIdentityLinkEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkEntityManager(HistoricIdentityLinkEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricIdentityLinkEntityManager(HistoricIdentityLinkEntityManager)"})
  public void testSetHistoricIdentityLinkEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManager = new HistoricIdentityLinkEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricIdentityLinkDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricIdentityLinkEntityManager(historicIdentityLinkEntityManager));
    assertSame(historicIdentityLinkEntityManager, jtaProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricProcessInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricProcessInstanceEntityManager()"})
  public void testGetHistoricProcessInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceEntityManager(HistoricProcessInstanceEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceEntityManager(HistoricProcessInstanceEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricProcessInstanceEntityManager(HistoricProcessInstanceEntityManager)"})
  public void testSetHistoricProcessInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManager = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricProcessInstanceEntityManager(historicProcessInstanceEntityManager));
    assertSame(historicProcessInstanceEntityManager,
        jtaProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricTaskInstanceEntityManager()"})
  public void testGetHistoricTaskInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceEntityManager(HistoricTaskInstanceEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceEntityManager(HistoricTaskInstanceEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricTaskInstanceEntityManager(HistoricTaskInstanceEntityManager)"})
  public void testSetHistoricTaskInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManager = new HistoricTaskInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricTaskInstanceEntityManager(historicTaskInstanceEntityManager));
    assertSame(historicTaskInstanceEntityManager, jtaProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricVariableInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricVariableInstanceEntityManager()"})
  public void testGetHistoricVariableInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceEntityManager(HistoricVariableInstanceEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceEntityManager(HistoricVariableInstanceEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoricVariableInstanceEntityManager(HistoricVariableInstanceEntityManager)"})
  public void testSetHistoricVariableInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManager = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration,
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricVariableInstanceEntityManager(historicVariableInstanceEntityManager));
    assertSame(historicVariableInstanceEntityManager,
        jtaProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IdentityLinkEntityManager ProcessEngineConfigurationImpl.getIdentityLinkEntityManager()"})
  public void testGetIdentityLinkEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setIdentityLinkEntityManager(IdentityLinkEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setIdentityLinkEntityManager(IdentityLinkEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setIdentityLinkEntityManager(IdentityLinkEntityManager)"})
  public void testSetIdentityLinkEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    IdentityLinkEntityManagerImpl identityLinkEntityManager = new IdentityLinkEntityManagerImpl(
        processEngineConfiguration, new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setIdentityLinkEntityManager(identityLinkEntityManager));
    assertSame(identityLinkEntityManager, jtaProcessEngineConfiguration.getIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getJobEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JobEntityManager ProcessEngineConfigurationImpl.getJobEntityManager()"})
  public void testGetJobEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setJobEntityManager(JobEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setJobEntityManager(JobEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setJobEntityManager(JobEntityManager)"})
  public void testSetJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager = new JobEntityManagerImpl(processEngineConfiguration,
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setJobEntityManager(jobEntityManager));
    assertSame(jobEntityManager, jtaProcessEngineConfiguration.getJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TimerJobEntityManager ProcessEngineConfigurationImpl.getTimerJobEntityManager()"})
  public void testGetTimerJobEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTimerJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTimerJobEntityManager(TimerJobEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTimerJobEntityManager(TimerJobEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTimerJobEntityManager(TimerJobEntityManager)"})
  public void testSetTimerJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManager = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setTimerJobEntityManager(timerJobEntityManager));
    assertSame(timerJobEntityManager, jtaProcessEngineConfiguration.getTimerJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSuspendedJobEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getSuspendedJobEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SuspendedJobEntityManager ProcessEngineConfigurationImpl.getSuspendedJobEntityManager()"})
  public void testGetSuspendedJobEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSuspendedJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setSuspendedJobEntityManager(SuspendedJobEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setSuspendedJobEntityManager(SuspendedJobEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setSuspendedJobEntityManager(SuspendedJobEntityManager)"})
  public void testSetSuspendedJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManager = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setSuspendedJobEntityManager(suspendedJobEntityManager));
    assertSame(suspendedJobEntityManager, jtaProcessEngineConfiguration.getSuspendedJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeadLetterJobEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDeadLetterJobEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeadLetterJobEntityManager ProcessEngineConfigurationImpl.getDeadLetterJobEntityManager()"})
  public void testGetDeadLetterJobEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeadLetterJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDeadLetterJobEntityManager(DeadLetterJobEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeadLetterJobEntityManager(DeadLetterJobEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDeadLetterJobEntityManager(DeadLetterJobEntityManager)"})
  public void testSetDeadLetterJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManager = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDeadLetterJobEntityManager(deadLetterJobEntityManager));
    assertSame(deadLetterJobEntityManager, jtaProcessEngineConfiguration.getDeadLetterJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getModelEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getModelEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ModelEntityManager ProcessEngineConfigurationImpl.getModelEntityManager()"})
  public void testGetModelEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getModelEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setModelEntityManager(ModelEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setModelEntityManager(ModelEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setModelEntityManager(ModelEntityManager)"})
  public void testSetModelEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ModelEntityManagerImpl modelEntityManager = new ModelEntityManagerImpl(processEngineConfiguration,
        new MybatisModelDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setModelEntityManager(modelEntityManager));
    assertSame(modelEntityManager, jtaProcessEngineConfiguration.getModelEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionEntityManager ProcessEngineConfigurationImpl.getProcessDefinitionEntityManager()"})
  public void testGetProcessDefinitionEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setProcessDefinitionEntityManager(ProcessDefinitionEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setProcessDefinitionEntityManager(ProcessDefinitionEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setProcessDefinitionEntityManager(ProcessDefinitionEntityManager)"})
  public void testSetProcessDefinitionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager = new ProcessDefinitionEntityManagerImpl(
        processEngineConfiguration, new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionEntityManager(processDefinitionEntityManager));
    assertSame(processDefinitionEntityManager, jtaProcessEngineConfiguration.getProcessDefinitionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionInfoEntityManager ProcessEngineConfigurationImpl.getProcessDefinitionInfoEntityManager()"})
  public void testGetProcessDefinitionInfoEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionInfoEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoEntityManager(ProcessDefinitionInfoEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoEntityManager(ProcessDefinitionInfoEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setProcessDefinitionInfoEntityManager(ProcessDefinitionInfoEntityManager)"})
  public void testSetProcessDefinitionInfoEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManager = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfiguration, new MybatisProcessDefinitionInfoDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionInfoEntityManager(processDefinitionInfoEntityManager));
    assertSame(processDefinitionInfoEntityManager,
        jtaProcessEngineConfiguration.getProcessDefinitionInfoEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPropertyEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getPropertyEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"PropertyEntityManager ProcessEngineConfigurationImpl.getPropertyEntityManager()"})
  public void testGetPropertyEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getPropertyEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPropertyEntityManager(PropertyEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setPropertyEntityManager(PropertyEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setPropertyEntityManager(PropertyEntityManager)"})
  public void testSetPropertyEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    PropertyEntityManagerImpl propertyEntityManager = new PropertyEntityManagerImpl(processEngineConfiguration,
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setPropertyEntityManager(propertyEntityManager));
    assertSame(propertyEntityManager, jtaProcessEngineConfiguration.getPropertyEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResourceEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getResourceEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ResourceEntityManager ProcessEngineConfigurationImpl.getResourceEntityManager()"})
  public void testGetResourceEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getResourceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setResourceEntityManager(ResourceEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setResourceEntityManager(ResourceEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setResourceEntityManager(ResourceEntityManager)"})
  public void testSetResourceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager = new ResourceEntityManagerImpl(processEngineConfiguration,
        new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setResourceEntityManager(resourceEntityManager));
    assertSame(resourceEntityManager, jtaProcessEngineConfiguration.getResourceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskEntityManager ProcessEngineConfigurationImpl.getTaskEntityManager()"})
  public void testGetTaskEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTaskEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTaskEntityManager(TaskEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTaskEntityManager(TaskEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTaskEntityManager(TaskEntityManager)"})
  public void testSetTaskEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TaskEntityManagerImpl taskEntityManager = new TaskEntityManagerImpl(processEngineConfiguration,
        new MybatisTaskDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTaskEntityManager(taskEntityManager));
    assertSame(taskEntityManager, jtaProcessEngineConfiguration.getTaskEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstanceEntityManager ProcessEngineConfigurationImpl.getVariableInstanceEntityManager()"})
  public void testGetVariableInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setVariableInstanceEntityManager(VariableInstanceEntityManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setVariableInstanceEntityManager(VariableInstanceEntityManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setVariableInstanceEntityManager(VariableInstanceEntityManager)"})
  public void testSetVariableInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManager = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setVariableInstanceEntityManager(variableInstanceEntityManager));
    assertSame(variableInstanceEntityManager, jtaProcessEngineConfiguration.getVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTableDataManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTableDataManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TableDataManager ProcessEngineConfigurationImpl.getTableDataManager()"})
  public void testGetTableDataManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTableDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTableDataManager(TableDataManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setTableDataManager(TableDataManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setTableDataManager(TableDataManager)"})
  public void testSetTableDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    TableDataManagerImpl tableDataManager = new TableDataManagerImpl(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTableDataManager(tableDataManager));
    assertSame(tableDataManager, jtaProcessEngineConfiguration.getTableDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoryManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoryManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoryManager ProcessEngineConfigurationImpl.getHistoryManager()"})
  public void testGetHistoryManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoryManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoryManager(HistoryManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setHistoryManager(HistoryManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setHistoryManager(HistoryManager)"})
  public void testSetHistoryManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultHistoryManager historyManager = new DefaultHistoryManager(new JtaProcessEngineConfiguration(),
        HistoryLevel.NONE);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setHistoryManager(historyManager));
    assertSame(historyManager, jtaProcessEngineConfiguration.getHistoryManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JobManager ProcessEngineConfigurationImpl.getJobManager()"})
  public void testGetJobManager() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJobManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setJobManager(JobManager)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setJobManager(JobManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setJobManager(JobManager)"})
  public void testSetJobManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultJobManager jobManager = new DefaultJobManager();

    // Act
    ProcessEngineConfigurationImpl actualSetJobManagerResult = jtaProcessEngineConfiguration.setJobManager(jobManager);

    // Assert
    assertSame(jobManager, jtaProcessEngineConfiguration.getJobManager());
    assertSame(jtaProcessEngineConfiguration, actualSetJobManagerResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setClock(Clock)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setClock(Clock)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setClock(Clock)"})
  public void testSetClock_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setClock(clock));
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setClock(Clock)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Clock is {@link DefaultClockImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setClock(Clock)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setClock(Clock)"})
  public void testSetClock_givenJtaProcessEngineConfigurationClockIsDefaultClockImpl() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultClockImpl clock = new DefaultClockImpl();
    jtaProcessEngineConfiguration.setClock(clock);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setClock(new DefaultClockImpl()));
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#resetClock()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#resetClock()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.resetClock()"})
  public void testResetClock_thenThrowActivitiException() {
    // Arrange
    DefaultClockImpl clock = mock(DefaultClockImpl.class);
    doThrow(new ActivitiException("An error occurred")).when(clock).reset();

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setClock(clock);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.resetClock());
    verify(clock).reset();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "DelegateExpressionFieldInjectionMode ProcessEngineConfigurationImpl.getDelegateExpressionFieldInjectionMode()"})
  public void testGetDelegateExpressionFieldInjectionMode() {
    // Arrange, Act and Assert
    assertEquals(DelegateExpressionFieldInjectionMode.MIXED,
        (new JtaProcessEngineConfiguration()).getDelegateExpressionFieldInjectionMode());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDelegateExpressionFieldInjectionMode(DelegateExpressionFieldInjectionMode)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDelegateExpressionFieldInjectionMode(DelegateExpressionFieldInjectionMode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setDelegateExpressionFieldInjectionMode(DelegateExpressionFieldInjectionMode)"})
  public void testSetDelegateExpressionFieldInjectionMode() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetDelegateExpressionFieldInjectionModeResult = jtaProcessEngineConfiguration
        .setDelegateExpressionFieldInjectionMode(DelegateExpressionFieldInjectionMode.COMPATIBILITY);

    // Assert
    assertEquals(DelegateExpressionFieldInjectionMode.COMPATIBILITY,
        jtaProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
    assertSame(jtaProcessEngineConfiguration, actualSetDelegateExpressionFieldInjectionModeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getObjectMapper()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectMapper ProcessEngineConfigurationImpl.getObjectMapper()"})
  public void testGetObjectMapper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration.objectMapper, jtaProcessEngineConfiguration.getObjectMapper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setObjectMapper(ObjectMapper)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setObjectMapper(ObjectMapper)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setObjectMapper(ObjectMapper)"})
  public void testSetObjectMapper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setObjectMapper(objectMapper));
    assertSame(objectMapper, jtaProcessEngineConfiguration.getObjectMapper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorCorePoolSize()"})
  public void testGetAsyncExecutorCorePoolSize() {
    // Arrange, Act and Assert
    assertEquals(2, (new JtaProcessEngineConfiguration()).getAsyncExecutorCorePoolSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorCorePoolSize(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorCorePoolSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorCorePoolSize(int)"})
  public void testSetAsyncExecutorCorePoolSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorCorePoolSizeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorCorePoolSize(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorCorePoolSizeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorNumberOfRetries()"})
  public void testGetAsyncExecutorNumberOfRetries() {
    // Arrange, Act and Assert
    assertEquals(3, (new JtaProcessEngineConfiguration()).getAsyncExecutorNumberOfRetries());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorNumberOfRetries(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorNumberOfRetries(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorNumberOfRetries(int)"})
  public void testSetAsyncExecutorNumberOfRetries() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorNumberOfRetriesResult = jtaProcessEngineConfiguration
        .setAsyncExecutorNumberOfRetries(10);

    // Assert
    assertEquals(10, jtaProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorNumberOfRetriesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxPoolSize()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxPoolSize()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorMaxPoolSize()"})
  public void testGetAsyncExecutorMaxPoolSize() {
    // Arrange, Act and Assert
    assertEquals(10, (new JtaProcessEngineConfiguration()).getAsyncExecutorMaxPoolSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxPoolSize(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxPoolSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorMaxPoolSize(int)"})
  public void testSetAsyncExecutorMaxPoolSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorMaxPoolSizeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorMaxPoolSize(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorMaxPoolSizeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long ProcessEngineConfigurationImpl.getAsyncExecutorThreadKeepAliveTime()"})
  public void testGetAsyncExecutorThreadKeepAliveTime() {
    // Arrange, Act and Assert
    assertEquals(5000L, (new JtaProcessEngineConfiguration()).getAsyncExecutorThreadKeepAliveTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadKeepAliveTime(long)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadKeepAliveTime(long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorThreadKeepAliveTime(long)"})
  public void testSetAsyncExecutorThreadKeepAliveTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadKeepAliveTimeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadKeepAliveTime(1L);

    // Assert
    assertEquals(1L, jtaProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorThreadKeepAliveTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorThreadPoolQueueSize()"})
  public void testGetAsyncExecutorThreadPoolQueueSize() {
    // Arrange, Act and Assert
    assertEquals(100, (new JtaProcessEngineConfiguration()).getAsyncExecutorThreadPoolQueueSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueueSize(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueueSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorThreadPoolQueueSize(int)"})
  public void testSetAsyncExecutorThreadPoolQueueSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueSizeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueueSize(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorThreadPoolQueueSizeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BlockingQueue ProcessEngineConfigurationImpl.getAsyncExecutorThreadPoolQueue()"})
  public void testGetAsyncExecutorThreadPoolQueue() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAsyncExecutorThreadPoolQueue());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorThreadPoolQueue(BlockingQueue)"})
  public void testSetAsyncExecutorThreadPoolQueue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueue(null);

    // Assert
    Collection<? extends Deployer> defaultDeployers = actualSetAsyncExecutorThreadPoolQueueResult.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetAsyncExecutorThreadPoolQueueResult instanceof JtaProcessEngineConfiguration);
    assertNull(jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualSetAsyncExecutorThreadPoolQueueResult.getAsyncExecutorThreadPoolQueue());
    BpmnDeployer bpmnDeployer = actualSetAsyncExecutorThreadPoolQueueResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSetAsyncExecutorThreadPoolQueueResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}.
   * <ul>
   *   <li>Given {@link Runnable}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorThreadPoolQueue(BlockingQueue)"})
  public void testSetAsyncExecutorThreadPoolQueue_givenRunnable() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    LinkedBlockingDeque<Runnable> asyncExecutorThreadPoolQueue = new LinkedBlockingDeque<>();
    asyncExecutorThreadPoolQueue.add(mock(Runnable.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueue(asyncExecutorThreadPoolQueue);

    // Assert
    Collection<? extends Deployer> defaultDeployers = actualSetAsyncExecutorThreadPoolQueueResult.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetAsyncExecutorThreadPoolQueueResult instanceof JtaProcessEngineConfiguration);
    assertSame(asyncExecutorThreadPoolQueue, jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertSame(asyncExecutorThreadPoolQueue,
        actualSetAsyncExecutorThreadPoolQueueResult.getAsyncExecutorThreadPoolQueue());
    BpmnDeployer bpmnDeployer = actualSetAsyncExecutorThreadPoolQueueResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSetAsyncExecutorThreadPoolQueueResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}.
   * <ul>
   *   <li>Given {@link Runnable}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorThreadPoolQueue(BlockingQueue)"})
  public void testSetAsyncExecutorThreadPoolQueue_givenRunnable2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    LinkedBlockingDeque<Runnable> asyncExecutorThreadPoolQueue = new LinkedBlockingDeque<>();
    asyncExecutorThreadPoolQueue.add(mock(Runnable.class));
    asyncExecutorThreadPoolQueue.add(mock(Runnable.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueue(asyncExecutorThreadPoolQueue);

    // Assert
    Collection<? extends Deployer> defaultDeployers = actualSetAsyncExecutorThreadPoolQueueResult.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetAsyncExecutorThreadPoolQueueResult instanceof JtaProcessEngineConfiguration);
    assertSame(asyncExecutorThreadPoolQueue, jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertSame(asyncExecutorThreadPoolQueue,
        actualSetAsyncExecutorThreadPoolQueueResult.getAsyncExecutorThreadPoolQueue());
    BpmnDeployer bpmnDeployer = actualSetAsyncExecutorThreadPoolQueueResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSetAsyncExecutorThreadPoolQueueResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}.
   * <ul>
   *   <li>When {@link LinkedBlockingDeque#LinkedBlockingDeque()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorThreadPoolQueue(BlockingQueue)"})
  public void testSetAsyncExecutorThreadPoolQueue_whenLinkedBlockingDeque() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    LinkedBlockingDeque<Runnable> asyncExecutorThreadPoolQueue = new LinkedBlockingDeque<>();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueue(asyncExecutorThreadPoolQueue);

    // Assert
    Collection<? extends Deployer> defaultDeployers = actualSetAsyncExecutorThreadPoolQueueResult.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetAsyncExecutorThreadPoolQueueResult instanceof JtaProcessEngineConfiguration);
    assertSame(asyncExecutorThreadPoolQueue, jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertSame(asyncExecutorThreadPoolQueue,
        actualSetAsyncExecutorThreadPoolQueueResult.getAsyncExecutorThreadPoolQueue());
    BpmnDeployer bpmnDeployer = actualSetAsyncExecutorThreadPoolQueueResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSetAsyncExecutorThreadPoolQueueResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long ProcessEngineConfigurationImpl.getAsyncExecutorSecondsToWaitOnShutdown()"})
  public void testGetAsyncExecutorSecondsToWaitOnShutdown() {
    // Arrange, Act and Assert
    assertEquals(60L, (new JtaProcessEngineConfiguration()).getAsyncExecutorSecondsToWaitOnShutdown());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorSecondsToWaitOnShutdown(long)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorSecondsToWaitOnShutdown(long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorSecondsToWaitOnShutdown(long)"})
  public void testSetAsyncExecutorSecondsToWaitOnShutdown() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorSecondsToWaitOnShutdownResult = jtaProcessEngineConfiguration
        .setAsyncExecutorSecondsToWaitOnShutdown(1L);

    // Assert
    assertEquals(1L, jtaProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorSecondsToWaitOnShutdownResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorMaxTimerJobsPerAcquisition()"})
  public void testGetAsyncExecutorMaxTimerJobsPerAcquisition() {
    // Arrange, Act and Assert
    assertEquals(1, (new JtaProcessEngineConfiguration()).getAsyncExecutorMaxTimerJobsPerAcquisition());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxTimerJobsPerAcquisition(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxTimerJobsPerAcquisition(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorMaxTimerJobsPerAcquisition(int)"})
  public void testSetAsyncExecutorMaxTimerJobsPerAcquisition() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAsyncExecutorMaxTimerJobsPerAcquisition(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorMaxAsyncJobsDuePerAcquisition()"})
  public void testGetAsyncExecutorMaxAsyncJobsDuePerAcquisition() {
    // Arrange, Act and Assert
    assertEquals(1, (new JtaProcessEngineConfiguration()).getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxAsyncJobsDuePerAcquisition(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorMaxAsyncJobsDuePerAcquisition(int)"})
  public void testSetAsyncExecutorMaxAsyncJobsDuePerAcquisition() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAsyncExecutorMaxAsyncJobsDuePerAcquisition(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorDefaultTimerJobAcquireWaitTime()"})
  public void testGetAsyncExecutorDefaultTimerJobAcquireWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10000, (new JtaProcessEngineConfiguration()).getAsyncExecutorDefaultTimerJobAcquireWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultTimerJobAcquireWaitTime(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultTimerJobAcquireWaitTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorDefaultTimerJobAcquireWaitTime(int)"})
  public void testSetAsyncExecutorDefaultTimerJobAcquireWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorDefaultTimerJobAcquireWaitTimeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorDefaultTimerJobAcquireWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorDefaultTimerJobAcquireWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorDefaultAsyncJobAcquireWaitTime()"})
  public void testGetAsyncExecutorDefaultAsyncJobAcquireWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10000, (new JtaProcessEngineConfiguration()).getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultAsyncJobAcquireWaitTime(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultAsyncJobAcquireWaitTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorDefaultAsyncJobAcquireWaitTime(int)"})
  public void testSetAsyncExecutorDefaultAsyncJobAcquireWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorDefaultAsyncJobAcquireWaitTimeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorDefaultAsyncJobAcquireWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorDefaultAsyncJobAcquireWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorDefaultQueueSizeFullWaitTime()"})
  public void testGetAsyncExecutorDefaultQueueSizeFullWaitTime() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getAsyncExecutorDefaultQueueSizeFullWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultQueueSizeFullWaitTime(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultQueueSizeFullWaitTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorDefaultQueueSizeFullWaitTime(int)"})
  public void testSetAsyncExecutorDefaultQueueSizeFullWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorDefaultQueueSizeFullWaitTimeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorDefaultQueueSizeFullWaitTime(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorDefaultQueueSizeFullWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorLockOwner()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorLockOwner()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getAsyncExecutorLockOwner()"})
  public void testGetAsyncExecutorLockOwner() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAsyncExecutorLockOwner());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorLockOwner(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorLockOwner(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorLockOwner(String)"})
  public void testSetAsyncExecutorLockOwner() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorLockOwnerResult = jtaProcessEngineConfiguration
        .setAsyncExecutorLockOwner("Async Executor Lock Owner");

    // Assert
    assertEquals("Async Executor Lock Owner", jtaProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorLockOwnerResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorTimerLockTimeInMillis()"})
  public void testGetAsyncExecutorTimerLockTimeInMillis() {
    // Arrange, Act and Assert
    assertEquals(300000, (new JtaProcessEngineConfiguration()).getAsyncExecutorTimerLockTimeInMillis());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorTimerLockTimeInMillis(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorTimerLockTimeInMillis(int)"})
  public void testSetAsyncExecutorTimerLockTimeInMillis() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorTimerLockTimeInMillisResult = jtaProcessEngineConfiguration
        .setAsyncExecutorTimerLockTimeInMillis(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorTimerLockTimeInMillisResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorAsyncJobLockTimeInMillis()"})
  public void testGetAsyncExecutorAsyncJobLockTimeInMillis() {
    // Arrange, Act and Assert
    assertEquals(300000, (new JtaProcessEngineConfiguration()).getAsyncExecutorAsyncJobLockTimeInMillis());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorAsyncJobLockTimeInMillis(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorAsyncJobLockTimeInMillis(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorAsyncJobLockTimeInMillis(int)"})
  public void testSetAsyncExecutorAsyncJobLockTimeInMillis() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorAsyncJobLockTimeInMillisResult = jtaProcessEngineConfiguration
        .setAsyncExecutorAsyncJobLockTimeInMillis(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorAsyncJobLockTimeInMillisResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsInterval()"})
  public void testGetAsyncExecutorResetExpiredJobsInterval() {
    // Arrange, Act and Assert
    assertEquals(60000, (new JtaProcessEngineConfiguration()).getAsyncExecutorResetExpiredJobsInterval());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsInterval(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsInterval(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorResetExpiredJobsInterval(int)"})
  public void testSetAsyncExecutorResetExpiredJobsInterval() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorResetExpiredJobsIntervalResult = jtaProcessEngineConfiguration
        .setAsyncExecutorResetExpiredJobsInterval(42);

    // Assert
    assertEquals(42, jtaProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorResetExpiredJobsIntervalResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)"})
  public void testSetAsyncExecutorExecuteAsyncRunnableFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecuteAsyncRunnableFactory asyncExecutorExecuteAsyncRunnableFactory = mock(ExecuteAsyncRunnableFactory.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration
        .setAsyncExecutorExecuteAsyncRunnableFactory(asyncExecutorExecuteAsyncRunnableFactory));
    assertSame(asyncExecutorExecuteAsyncRunnableFactory,
        jtaProcessEngineConfiguration.getAsyncExecutorExecuteAsyncRunnableFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsPageSize()"})
  public void testGetAsyncExecutorResetExpiredJobsPageSize() {
    // Arrange, Act and Assert
    assertEquals(3, (new JtaProcessEngineConfiguration()).getAsyncExecutorResetExpiredJobsPageSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsPageSize(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorResetExpiredJobsPageSize(int)"})
  public void testSetAsyncExecutorResetExpiredJobsPageSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAsyncExecutorResetExpiredJobsPageSize(3));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isAsyncExecutorIsMessageQueueMode()"})
  public void testIsAsyncExecutorIsMessageQueueMode_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isAsyncExecutorIsMessageQueueMode());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isAsyncExecutorIsMessageQueueMode()"})
  public void testIsAsyncExecutorIsMessageQueueMode_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorMessageQueueMode(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorMessageQueueMode(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setAsyncExecutorMessageQueueMode(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setAsyncExecutorMessageQueueMode(boolean)"})
  public void testSetAsyncExecutorMessageQueueMode() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorMessageQueueModeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorMessageQueueMode(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorMessageQueueModeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isRollbackDeployment()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isRollbackDeployment()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isRollbackDeployment()"})
  public void testIsRollbackDeployment_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isRollbackDeployment());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isRollbackDeployment()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#isRollbackDeployment()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfigurationImpl.isRollbackDeployment()"})
  public void testIsRollbackDeployment_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setRollbackDeployment(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isRollbackDeployment());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setRollbackDeployment(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setRollbackDeployment(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.setRollbackDeployment(boolean)"})
  public void testSetRollbackDeployment() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.setRollbackDeployment(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isRollbackDeployment());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventSubscriptionPayloadMappingProvider(EventSubscriptionPayloadMappingProvider)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setEventSubscriptionPayloadMappingProvider(EventSubscriptionPayloadMappingProvider)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ProcessEngineConfigurationImpl.setEventSubscriptionPayloadMappingProvider(EventSubscriptionPayloadMappingProvider)"})
  public void testSetEventSubscriptionPayloadMappingProvider() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionPayloadMappingProvider eventSubscriptionPayloadMappingProvider = mock(
        EventSubscriptionPayloadMappingProvider.class);

    // Act
    jtaProcessEngineConfiguration.setEventSubscriptionPayloadMappingProvider(eventSubscriptionPayloadMappingProvider);

    // Assert
    assertSame(eventSubscriptionPayloadMappingProvider,
        jtaProcessEngineConfiguration.getEventSubscriptionPayloadMappingProvider());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionHelper()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionHelper()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionHelper ProcessEngineConfigurationImpl.getProcessDefinitionHelper()"})
  public void testGetProcessDefinitionHelper() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setProcessDefinitionHelper(ProcessDefinitionHelper)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setProcessDefinitionHelper(ProcessDefinitionHelper)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.setProcessDefinitionHelper(ProcessDefinitionHelper)"})
  public void testSetProcessDefinitionHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessDefinitionHelper processDefinitionHelper = mock(ProcessDefinitionHelper.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionHelper(processDefinitionHelper));
    assertSame(processDefinitionHelper, jtaProcessEngineConfiguration.getProcessDefinitionHelper());
  }
}
