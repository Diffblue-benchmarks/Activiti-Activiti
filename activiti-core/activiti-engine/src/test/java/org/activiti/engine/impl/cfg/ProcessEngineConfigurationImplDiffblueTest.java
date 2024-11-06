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
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
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
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngineManager;
import javax.sql.DataSource;
import javax.xml.namespace.QName;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.Transaction;
import org.activiti.bpmn.model.UserTask;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
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
import org.activiti.engine.impl.cfg.multitenant.TenantAwareDataSource;
import org.activiti.engine.impl.cfg.standalone.StandaloneMybatisTransactionContextFactory;
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
import org.activiti.engine.impl.persistence.entity.AttachmentEntity;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityManager;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntity;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityImpl;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityManager;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.CommentEntityImpl;
import org.activiti.engine.impl.persistence.entity.CommentEntityManager;
import org.activiti.engine.impl.persistence.entity.CommentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.CompensateEventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntity;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManager;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManager;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntity;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityImpl;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityManager;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityManager;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricDetailEntity;
import org.activiti.engine.impl.persistence.entity.HistoricDetailEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricDetailEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricDetailEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.impl.persistence.entity.JobEntityManager;
import org.activiti.engine.impl.persistence.entity.JobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.MessageEventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.activiti.engine.impl.persistence.entity.ModelEntityManager;
import org.activiti.engine.impl.persistence.entity.ModelEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.PropertyEntity;
import org.activiti.engine.impl.persistence.entity.PropertyEntityImpl;
import org.activiti.engine.impl.persistence.entity.PropertyEntityManager;
import org.activiti.engine.impl.persistence.entity.PropertyEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManager;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.SignalEventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntity;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManager;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TableDataManager;
import org.activiti.engine.impl.persistence.entity.TableDataManagerImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntity;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManager;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
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
import org.apache.ibatis.datasource.pooled.PoolState;
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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessEngineConfigurationImplDiffblueTest {
  @InjectMocks
  private JtaProcessEngineConfiguration jtaProcessEngineConfiguration;

  @InjectMocks
  private StandaloneInMemProcessEngineConfiguration standaloneInMemProcessEngineConfiguration;

  /**
   * Test {@link ProcessEngineConfigurationImpl#buildProcessEngine()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
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
   *   <li>Given {@link Connection} {@link Connection#getMetaData()} throw
   * {@link SQLException#SQLException()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Clock
   * is {@link DefaultClockImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * DatabaseType is {@code none}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * HistoryLevel is {@code NONE}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#buildProcessEngine()}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * addConfigurator Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * CommandInterceptors is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * HistoryLevel is {@code NONE}.</li>
   *   <li>Then calls {@link Connection#close()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
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
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * CommandInvoker {@link CommandInvoker}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
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
    List<XNode> children2 = sqlFragments.get("selectExecutionsFromSql").getParent().getChildren();
    assertEquals(40, children2.size());
    List<ProcessEngineConfigurator> allConfigurators = jtaProcessEngineConfiguration.getAllConfigurators();
    assertTrue(allConfigurators.isEmpty());
    assertTrue(sqlFragments
        .containsKey("org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl.executionVariab"
            + "leOperator"));
    assertEquals(allConfigurators, children.get(0).getChildren());
    assertEquals(allConfigurators, children2.get(0).getChildren());
    assertEquals(allConfigurators, configuration.getIncompleteCacheRefs());
    assertEquals(allConfigurators, configuration.getIncompleteMethods());
    assertEquals(allConfigurators, configuration.getIncompleteResultMaps());
    assertEquals(allConfigurators, configuration.getIncompleteStatements());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#init()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * CommandInvoker {@link LoggingCommandInvoker}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#init()}
   */
  @Test
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
    SqlSessionFactory sqlSessionFactory = jtaProcessEngineConfiguration.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    assertEquals(10000, getResult.getPriority());
    Map<String, XNode> sqlFragments = sqlSessionFactory.getConfiguration().getSqlFragments();
    assertEquals(94, sqlFragments.size());
    assertEquals(2, sqlFragments.get("selectDeploymentsByQueryCriteriaSql").getChildren().size());
    assertEquals(40, sqlFragments.get("selectExecutionsFromSql").getParent().getChildren().size());
    assertTrue(sqlFragments
        .containsKey("org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl.executionVariab"
            + "leOperator"));
    assertEquals(allConfigurators, jtaProcessEngineConfiguration.getConfigurators());
    assertSame(configurator, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initFailedJobCommandFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initFailedJobCommandFactory()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initDefaultCommandConfig()}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initDefaultCommandConfig()}
   */
  @Test
  public void testInitDefaultCommandConfig2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDefaultCommandConfig();

    // Assert
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initDefaultCommandConfig()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSchemaCommandConfig()}
   */
  @Test
  public void testInitSchemaCommandConfig() {
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
   * Test {@link ProcessEngineConfigurationImpl#initSchemaCommandConfig()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSchemaCommandConfig()}
   */
  @Test
  public void testInitSchemaCommandConfig2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initSchemaCommandConfig();

    // Assert
    CommandConfig schemaCommandConfig = jtaProcessEngineConfiguration.getSchemaCommandConfig();
    assertEquals(TransactionPropagation.NOT_SUPPORTED, schemaCommandConfig.getTransactionPropagation());
    assertFalse(schemaCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSchemaCommandConfig()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSchemaCommandConfig()}
   */
  @Test
  public void testInitSchemaCommandConfig_givenJtaProcessEngineConfiguration() {
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
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  public void testInitCommandInvoker() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  public void testInitCommandInvoker2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  public void testInitCommandInvoker3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initCommandInvoker();

    // Assert
    CommandInterceptor commandInvoker = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof DebugCommandInvoker);
    assertNull(commandInvoker.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  public void testInitCommandInvoker_givenJtaProcessEngineConfiguration() {
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
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
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
   * <ul>
   *   <li>Then return size is four.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  public void testGetDefaultCommandInterceptors_thenReturnSizeIsFour() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));
    CommandContextFactory commandContextFactory = new CommandContextFactory();
    jtaProcessEngineConfiguration.setCommandContextFactory(commandContextFactory);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors = jtaProcessEngineConfiguration
        .getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(4, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult = ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(2);
    assertTrue(getResult instanceof CommandContextInterceptor);
    CommandInterceptor getResult2 = ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(3);
    assertTrue(getResult2 instanceof TransactionContextInterceptor);
    CommandContextFactory commandContextFactory2 = ((CommandContextInterceptor) getResult).getCommandContextFactory();
    assertNull(commandContextFactory2.getProcessEngineConfiguration());
    assertNull(getResult2.getNext());
    assertSame(jtaProcessEngineConfiguration, ((CommandContextInterceptor) getResult).getProcessEngineConfiguration());
    assertSame(commandContextFactory, commandContextFactory2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   * <ul>
   *   <li>Then return size is three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  public void testGetDefaultCommandInterceptors_thenReturnSizeIsThree() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));
    jtaProcessEngineConfiguration.setCommandContextFactory(null);
    jtaProcessEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));

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
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
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
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
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
    assertNull(commandExecutor.getDefaultConfig());
    assertSame(commandContextInterceptor, ((CommandExecutorImpl) commandExecutor).getFirst());
    assertSame(commandContextInterceptor2, getResult.getNext());
    assertSame(commandContextInterceptor3, getResult2.getNext());
    assertSame(commandContextInterceptor6, getResult3.getNext());
    assertSame(commandContextInterceptor4, getResult4.getNext());
    assertSame(commandContextInterceptor5, getResult5.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  public void testInitCommandExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    jtaProcessEngineConfiguration.setCommandExecutor(commandExecutor);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}
   */
  @Test
  public void testInitInterceptorChain_thenArrayListSizeIsNineteen() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    ArrayList<CommandInterceptor> chain = new ArrayList<>();
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
    assertSame(commandContextInterceptor, actualInitInterceptorChainResult);
    assertSame(commandContextInterceptor2, getResult.getNext());
    assertSame(commandContextInterceptor3, getResult2.getNext());
    assertSame(commandContextInterceptor4, getResult3.getNext());
    assertSame(commandContextInterceptor6, getResult4.getNext());
    assertSame(commandContextInterceptor5, getResult5.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initInterceptorChain(List)}
   */
  @Test
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
  public void testInitDataSource() throws SQLException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataSource();

    // Assert
    DataSource dataSource = jtaProcessEngineConfiguration.getDataSource();
    assertTrue(dataSource instanceof PooledDataSource);
    Logger parentLogger = dataSource.getParentLogger();
    Logger parent = parentLogger.getParent();
    assertEquals("", parent.getName());
    assertEquals("", ((PooledDataSource) dataSource).getPassword());
    Level level = parent.getLevel();
    assertEquals("INFO", level.getLocalizedName());
    assertEquals("INFO", level.getName());
    assertEquals("INFO", level.toString());
    assertEquals("NO PING QUERY SET", ((PooledDataSource) dataSource).getPoolPingQuery());
    assertEquals("global", parentLogger.getName());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", ((PooledDataSource) dataSource).getUrl());
    assertEquals("org.h2.Driver", ((PooledDataSource) dataSource).getDriver());
    assertEquals("sa", ((PooledDataSource) dataSource).getUsername());
    assertEquals("sun.util.logging.resources.logging", level.getResourceBundleName());
    assertNull(dataSource.getLogWriter());
    assertNull(((PooledDataSource) dataSource).getDefaultNetworkTimeout());
    assertNull(((PooledDataSource) dataSource).getDefaultTransactionIsolationLevel());
    Handler[] handlers = parent.getHandlers();
    Handler handler = handlers[0];
    assertNull(handler.getEncoding());
    assertNull(parent.getResourceBundleName());
    assertNull(parentLogger.getResourceBundleName());
    assertNull(((PooledDataSource) dataSource).getDriverProperties());
    assertNull(parent.getResourceBundle());
    assertNull(parentLogger.getResourceBundle());
    assertNull(handler.getFilter());
    assertNull(parent.getFilter());
    assertNull(parentLogger.getFilter());
    assertNull(parentLogger.getLevel());
    assertNull(parent.getParent());
    assertEquals(0, dataSource.getLoginTimeout());
    PoolState poolState = ((PooledDataSource) dataSource).getPoolState();
    assertEquals(0, poolState.getActiveConnectionCount());
    assertEquals(0, poolState.getIdleConnectionCount());
    assertEquals(0, ((PooledDataSource) dataSource).getPoolPingConnectionsNotUsedFor());
    assertEquals(0, parentLogger.getHandlers().length);
    assertEquals(0L, poolState.getAverageCheckoutTime());
    assertEquals(0L, poolState.getAverageOverdueCheckoutTime());
    assertEquals(0L, poolState.getAverageRequestTime());
    assertEquals(0L, poolState.getAverageWaitTime());
    assertEquals(0L, poolState.getBadConnectionCount());
    assertEquals(0L, poolState.getClaimedOverdueConnectionCount());
    assertEquals(0L, poolState.getHadToWaitCount());
    assertEquals(0L, poolState.getRequestCount());
    assertEquals(1, handlers.length);
    assertEquals(10, ((PooledDataSource) dataSource).getPoolMaximumActiveConnections());
    assertEquals(20000, ((PooledDataSource) dataSource).getPoolMaximumCheckoutTime());
    assertEquals(20000, ((PooledDataSource) dataSource).getPoolTimeToWait());
    assertEquals(3, ((PooledDataSource) dataSource).getPoolMaximumLocalBadConnectionTolerance());
    assertEquals(5, ((PooledDataSource) dataSource).getPoolMaximumIdleConnections());
    assertFalse(((PooledDataSource) dataSource).isPoolPingEnabled());
    assertTrue(parent.getUseParentHandlers());
    assertTrue(parentLogger.getUseParentHandlers());
    assertSame(level, handler.getLevel());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  public void testInitDataSource2() {
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
  public void testInitDataSource3() {
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
  public void testInitDataSource4() throws SQLException {
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
  public void testInitDataSource5() throws SQLException {
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
   *   <li>Given {@link Connection} {@link Connection#getMetaData()} throw
   * {@link SQLException#SQLException()}.</li>
   *   <li>Then calls {@link Connection#close()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
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

    // Assert that nothing has changed
    verify(connection).close();
    verify(connection).getMetaData();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <ul>
   *   <li>Given {@link DatabaseMetaData}
   * {@link DatabaseMetaData#getDatabaseProductName()} throw
   * {@link SQLException#SQLException()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
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

    // Assert that nothing has changed
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
  public void testInitDataSource_givenJtaProcessEngineConfiguration() throws SQLException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initDataSource();

    // Assert
    DataSource dataSource = jtaProcessEngineConfiguration.getDataSource();
    assertTrue(dataSource instanceof PooledDataSource);
    Logger parentLogger = dataSource.getParentLogger();
    Logger parent = parentLogger.getParent();
    assertEquals("", parent.getName());
    assertEquals("", ((PooledDataSource) dataSource).getPassword());
    Level level = parent.getLevel();
    assertEquals("INFO", level.getLocalizedName());
    assertEquals("INFO", level.getName());
    assertEquals("INFO", level.toString());
    assertEquals("NO PING QUERY SET", ((PooledDataSource) dataSource).getPoolPingQuery());
    assertEquals("global", parentLogger.getName());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", ((PooledDataSource) dataSource).getUrl());
    assertEquals("org.h2.Driver", ((PooledDataSource) dataSource).getDriver());
    assertEquals("sa", ((PooledDataSource) dataSource).getUsername());
    assertEquals("sun.util.logging.resources.logging", level.getResourceBundleName());
    assertNull(dataSource.getLogWriter());
    assertNull(((PooledDataSource) dataSource).getDefaultNetworkTimeout());
    assertNull(((PooledDataSource) dataSource).getDefaultTransactionIsolationLevel());
    Handler[] handlers = parent.getHandlers();
    Handler handler = handlers[0];
    assertNull(handler.getEncoding());
    assertNull(parent.getResourceBundleName());
    assertNull(parentLogger.getResourceBundleName());
    assertNull(((PooledDataSource) dataSource).getDriverProperties());
    assertNull(parent.getResourceBundle());
    assertNull(parentLogger.getResourceBundle());
    assertNull(handler.getFilter());
    assertNull(parent.getFilter());
    assertNull(parentLogger.getFilter());
    assertNull(parentLogger.getLevel());
    assertNull(parent.getParent());
    assertEquals(0, dataSource.getLoginTimeout());
    PoolState poolState = ((PooledDataSource) dataSource).getPoolState();
    assertEquals(0, poolState.getActiveConnectionCount());
    assertEquals(0, poolState.getIdleConnectionCount());
    assertEquals(0, ((PooledDataSource) dataSource).getPoolPingConnectionsNotUsedFor());
    assertEquals(0, parentLogger.getHandlers().length);
    assertEquals(0L, poolState.getAverageCheckoutTime());
    assertEquals(0L, poolState.getAverageOverdueCheckoutTime());
    assertEquals(0L, poolState.getAverageRequestTime());
    assertEquals(0L, poolState.getAverageWaitTime());
    assertEquals(0L, poolState.getBadConnectionCount());
    assertEquals(0L, poolState.getClaimedOverdueConnectionCount());
    assertEquals(0L, poolState.getHadToWaitCount());
    assertEquals(0L, poolState.getRequestCount());
    assertEquals(1, handlers.length);
    assertEquals(10, ((PooledDataSource) dataSource).getPoolMaximumActiveConnections());
    assertEquals(20000, ((PooledDataSource) dataSource).getPoolMaximumCheckoutTime());
    assertEquals(20000, ((PooledDataSource) dataSource).getPoolTimeToWait());
    assertEquals(3, ((PooledDataSource) dataSource).getPoolMaximumLocalBadConnectionTolerance());
    assertEquals(5, ((PooledDataSource) dataSource).getPoolMaximumIdleConnections());
    assertFalse(((PooledDataSource) dataSource).isPoolPingEnabled());
    assertTrue(parent.getUseParentHandlers());
    assertTrue(parentLogger.getUseParentHandlers());
    assertSame(level, handler.getLevel());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataSource()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * DataSource is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataSource()}
   */
  @Test
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
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDatabaseTypeMappings()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDatabaseTypeMappings()}
   */
  @Test
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
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDatabaseType();

    // Assert that nothing has changed
    verify(connection).close();
    verify(connection).getMetaData();
    verify(databaseMetaData).getDatabaseProductName();
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initTransactionFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initTransactionFactory()}
   */
  @Test
  public void testInitTransactionFactory() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initTransactionFactory()}
   */
  @Test
  public void testInitTransactionFactory2() {
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
   * Test {@link ProcessEngineConfigurationImpl#initTransactionFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initTransactionFactory()}
   */
  @Test
  public void testInitTransactionFactory3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initTransactionFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getTransactionFactory() instanceof ManagedTransactionFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initTransactionFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initTransactionFactory()}
   */
  @Test
  public void testInitTransactionFactory_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initTransactionFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getTransactionFactory() instanceof ManagedTransactionFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  public void testInitSqlSessionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisMappers(null);
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  public void testInitSqlSessionFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisMappers(null);
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(new HashSet<>());

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getSqlSessionFactory() instanceof DefaultSqlSessionFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  public void testInitSqlSessionFactory3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisMappers(new HashSet<>());
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(null);

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getSqlSessionFactory() instanceof DefaultSqlSessionFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  public void testInitSqlSessionFactory4() {
    // Arrange
    LinkedHashSet<Class<?>> customMybatisMappers = new LinkedHashSet<>();
    Class<Object> forNameResult = Object.class;
    customMybatisMappers.add(forNameResult);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisMappers(customMybatisMappers);
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  public void testInitSqlSessionFactory5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(new Configuration());
    jtaProcessEngineConfiguration.setSqlSessionFactory(sqlSessionFactory);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setTransactionFactory(null);
    jtaProcessEngineConfiguration.setDataSource(mock(DataSource.class));
    jtaProcessEngineConfiguration.setCustomMybatisMappers(null);
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(null);

    // Act
    jtaProcessEngineConfiguration.initSqlSessionFactory();

    // Assert that nothing has changed
    assertSame(sqlSessionFactory, jtaProcessEngineConfiguration.getSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  public void testInitSqlSessionFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new JtaProcessEngineConfiguration()).initSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * DataSource is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initSqlSessionFactory()}
   */
  @Test
  public void testInitSqlSessionFactory_givenJtaProcessEngineConfigurationDataSourceIsNull() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSqlSessionFactory(null);
    jtaProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(null);
    jtaProcessEngineConfiguration.setDatabaseType(null);
    jtaProcessEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    jtaProcessEngineConfiguration.setDataSource(null);
    jtaProcessEngineConfiguration.setCustomMybatisMappers(null);
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jtaProcessEngineConfiguration.initSqlSessionFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#initMybatisTypeHandlers(Configuration)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initMybatisTypeHandlers(Configuration)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#initMybatisTypeHandlers(Configuration)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initMybatisTypeHandlers(Configuration)}
   */
  @Test
  public void testInitMybatisTypeHandlers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    Configuration configuration = new Configuration();

    // Act
    jtaProcessEngineConfiguration.initMybatisTypeHandlers(configuration);

    // Assert
    assertEquals(41, configuration.getTypeHandlerRegistry().getTypeHandlers().size());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#parseCustomMybatisXMLMappers(Configuration)}.
   * <ul>
   *   <li>Then KeyGeneratorNames return {@link Set}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#parseCustomMybatisXMLMappers(Configuration)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getResourceAsStream(String)}
   */
  @Test
  public void testGetResourceAsStream_whenResource_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(jtaProcessEngineConfiguration.getResourceAsStream("Resource"));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getMyBatisXmlConfigurationStream()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMyBatisXmlConfigurationStream()}
   */
  @Test
  public void testGetMyBatisXmlConfigurationStream() throws IOException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    byte[] byteArray = new byte[51];
    assertEquals(51, jtaProcessEngineConfiguration.getMyBatisXmlConfigurationStream().read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getMyBatisXmlConfigurationStream()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMyBatisXmlConfigurationStream()}
   */
  @Test
  public void testGetMyBatisXmlConfigurationStream_givenJtaProcessEngineConfiguration() throws IOException {
    // Arrange, Act and Assert
    byte[] byteArray = new byte[51];
    assertEquals(51, (new JtaProcessEngineConfiguration()).getMyBatisXmlConfigurationStream().read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}
   */
  @Test
  public void testGetCustomMybatisMappers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomMybatisMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}
   */
  @Test
  public void testGetCustomMybatisMappers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomMybatisMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}
   */
  @Test
  public void testSetCustomMybatisMappers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HashSet<Class<?>> customMybatisMappers = new HashSet<>();

    // Act
    jtaProcessEngineConfiguration.setCustomMybatisMappers(customMybatisMappers);

    // Assert
    assertSame(customMybatisMappers, jtaProcessEngineConfiguration.getCustomMybatisMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}.
   * <ul>
   *   <li>Given {@code java.lang.Class}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@link Class}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}
   */
  @Test
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
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>When {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomMybatisMappers(Set)}
   */
  @Test
  public void testSetCustomMybatisMappers_givenJtaProcessEngineConfiguration_whenHashSet() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomMybatisXMLMappers()}
   */
  @Test
  public void testGetCustomMybatisXMLMappers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomMybatisXMLMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomMybatisXMLMappers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomMybatisXMLMappers()}
   */
  @Test
  public void testGetCustomMybatisXMLMappers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomMybatisXMLMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}
   */
  @Test
  public void testSetCustomMybatisXMLMappers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HashSet<String> customMybatisXMLMappers = new HashSet<>();

    // Act
    jtaProcessEngineConfiguration.setCustomMybatisXMLMappers(customMybatisXMLMappers);

    // Assert
    assertSame(customMybatisXMLMappers, jtaProcessEngineConfiguration.getCustomMybatisXMLMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>When {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomMybatisXMLMappers(Set)}
   */
  @Test
  public void testSetCustomMybatisXMLMappers_givenJtaProcessEngineConfiguration_whenHashSet() {
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
  public void testInitDataManagers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    AttachmentDataManager attachmentDataManager = jtaProcessEngineConfiguration.getAttachmentDataManager();
    assertTrue(attachmentDataManager instanceof MybatisAttachmentDataManager);
    ByteArrayDataManager byteArrayDataManager = jtaProcessEngineConfiguration.getByteArrayDataManager();
    assertTrue(byteArrayDataManager instanceof MybatisByteArrayDataManager);
    CommentDataManager commentDataManager = jtaProcessEngineConfiguration.getCommentDataManager();
    assertTrue(commentDataManager instanceof MybatisCommentDataManager);
    DeadLetterJobDataManager deadLetterJobDataManager = jtaProcessEngineConfiguration.getDeadLetterJobDataManager();
    assertTrue(deadLetterJobDataManager instanceof MybatisDeadLetterJobDataManager);
    DeploymentDataManager deploymentDataManager = jtaProcessEngineConfiguration.getDeploymentDataManager();
    assertTrue(deploymentDataManager instanceof MybatisDeploymentDataManager);
    EventLogEntryDataManager eventLogEntryDataManager = jtaProcessEngineConfiguration.getEventLogEntryDataManager();
    assertTrue(eventLogEntryDataManager instanceof MybatisEventLogEntryDataManager);
    EventSubscriptionDataManager eventSubscriptionDataManager = jtaProcessEngineConfiguration
        .getEventSubscriptionDataManager();
    assertTrue(eventSubscriptionDataManager instanceof MybatisEventSubscriptionDataManager);
    ExecutionDataManager executionDataManager = jtaProcessEngineConfiguration.getExecutionDataManager();
    assertTrue(executionDataManager instanceof MybatisExecutionDataManager);
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceDataManager();
    assertTrue(historicActivityInstanceDataManager instanceof MybatisHistoricActivityInstanceDataManager);
    HistoricDetailDataManager historicDetailDataManager = jtaProcessEngineConfiguration.getHistoricDetailDataManager();
    assertTrue(historicDetailDataManager instanceof MybatisHistoricDetailDataManager);
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkDataManager();
    assertTrue(historicIdentityLinkDataManager instanceof MybatisHistoricIdentityLinkDataManager);
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceDataManager();
    assertTrue(historicProcessInstanceDataManager instanceof MybatisHistoricProcessInstanceDataManager);
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceDataManager();
    assertTrue(historicTaskInstanceDataManager instanceof MybatisHistoricTaskInstanceDataManager);
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceDataManager();
    assertTrue(historicVariableInstanceDataManager instanceof MybatisHistoricVariableInstanceDataManager);
    IdentityLinkDataManager identityLinkDataManager = jtaProcessEngineConfiguration.getIdentityLinkDataManager();
    assertTrue(identityLinkDataManager instanceof MybatisIdentityLinkDataManager);
    JobDataManager jobDataManager = jtaProcessEngineConfiguration.getJobDataManager();
    assertTrue(jobDataManager instanceof MybatisJobDataManager);
    ModelDataManager modelDataManager = jtaProcessEngineConfiguration.getModelDataManager();
    assertTrue(modelDataManager instanceof MybatisModelDataManager);
    ProcessDefinitionDataManager processDefinitionDataManager = jtaProcessEngineConfiguration
        .getProcessDefinitionDataManager();
    assertTrue(processDefinitionDataManager instanceof MybatisProcessDefinitionDataManager);
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = jtaProcessEngineConfiguration
        .getProcessDefinitionInfoDataManager();
    assertTrue(processDefinitionInfoDataManager instanceof MybatisProcessDefinitionInfoDataManager);
    PropertyDataManager propertyDataManager = jtaProcessEngineConfiguration.getPropertyDataManager();
    assertTrue(propertyDataManager instanceof MybatisPropertyDataManager);
    ResourceDataManager resourceDataManager = jtaProcessEngineConfiguration.getResourceDataManager();
    assertTrue(resourceDataManager instanceof MybatisResourceDataManager);
    SuspendedJobDataManager suspendedJobDataManager = jtaProcessEngineConfiguration.getSuspendedJobDataManager();
    assertTrue(suspendedJobDataManager instanceof MybatisSuspendedJobDataManager);
    TaskDataManager taskDataManager = jtaProcessEngineConfiguration.getTaskDataManager();
    assertTrue(taskDataManager instanceof MybatisTaskDataManager);
    TimerJobDataManager timerJobDataManager = jtaProcessEngineConfiguration.getTimerJobDataManager();
    assertTrue(timerJobDataManager instanceof MybatisTimerJobDataManager);
    VariableInstanceDataManager variableInstanceDataManager = jtaProcessEngineConfiguration
        .getVariableInstanceDataManager();
    assertTrue(variableInstanceDataManager instanceof MybatisVariableInstanceDataManager);
    assertNull(((MybatisAttachmentDataManager) attachmentDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisByteArrayDataManager) byteArrayDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisCommentDataManager) commentDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisDeadLetterJobDataManager) deadLetterJobDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisDeploymentDataManager) deploymentDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisEventLogEntryDataManager) eventLogEntryDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisExecutionDataManager) executionDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisHistoricActivityInstanceDataManager) historicActivityInstanceDataManager)
        .getManagedEntitySubClasses());
    assertNull(((MybatisHistoricDetailDataManager) historicDetailDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisHistoricIdentityLinkDataManager) historicIdentityLinkDataManager).getManagedEntitySubClasses());
    assertNull(
        ((MybatisHistoricProcessInstanceDataManager) historicProcessInstanceDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisHistoricTaskInstanceDataManager) historicTaskInstanceDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisHistoricVariableInstanceDataManager) historicVariableInstanceDataManager)
        .getManagedEntitySubClasses());
    assertNull(((MybatisIdentityLinkDataManager) identityLinkDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisJobDataManager) jobDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisModelDataManager) modelDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisProcessDefinitionDataManager) processDefinitionDataManager).getManagedEntitySubClasses());
    assertNull(
        ((MybatisProcessDefinitionInfoDataManager) processDefinitionInfoDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisPropertyDataManager) propertyDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisResourceDataManager) resourceDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisSuspendedJobDataManager) suspendedJobDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisTaskDataManager) taskDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisTimerJobDataManager) timerJobDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisVariableInstanceDataManager) variableInstanceDataManager).getManagedEntitySubClasses());
    List<Class<? extends EventSubscriptionEntity>> managedEntitySubClasses = ((MybatisEventSubscriptionDataManager) eventSubscriptionDataManager)
        .getManagedEntitySubClasses();
    assertEquals(3, managedEntitySubClasses.size());
    Class<AttachmentEntityImpl> expectedManagedEntityClass = AttachmentEntityImpl.class;
    assertEquals(expectedManagedEntityClass,
        ((MybatisAttachmentDataManager) attachmentDataManager).getManagedEntityClass());
    Class<ByteArrayEntityImpl> expectedManagedEntityClass2 = ByteArrayEntityImpl.class;
    assertEquals(expectedManagedEntityClass2,
        ((MybatisByteArrayDataManager) byteArrayDataManager).getManagedEntityClass());
    Class<CommentEntityImpl> expectedManagedEntityClass3 = CommentEntityImpl.class;
    assertEquals(expectedManagedEntityClass3, ((MybatisCommentDataManager) commentDataManager).getManagedEntityClass());
    Class<CompensateEventSubscriptionEntityImpl> expectedGetResult = CompensateEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult, managedEntitySubClasses.get(2));
    Class<DeadLetterJobEntityImpl> expectedManagedEntityClass4 = DeadLetterJobEntityImpl.class;
    assertEquals(expectedManagedEntityClass4,
        ((MybatisDeadLetterJobDataManager) deadLetterJobDataManager).getManagedEntityClass());
    Class<DeploymentEntityImpl> expectedManagedEntityClass5 = DeploymentEntityImpl.class;
    assertEquals(expectedManagedEntityClass5,
        ((MybatisDeploymentDataManager) deploymentDataManager).getManagedEntityClass());
    Class<EventLogEntryEntityImpl> expectedManagedEntityClass6 = EventLogEntryEntityImpl.class;
    assertEquals(expectedManagedEntityClass6,
        ((MybatisEventLogEntryDataManager) eventLogEntryDataManager).getManagedEntityClass());
    Class<EventSubscriptionEntityImpl> expectedManagedEntityClass7 = EventSubscriptionEntityImpl.class;
    assertEquals(expectedManagedEntityClass7,
        ((MybatisEventSubscriptionDataManager) eventSubscriptionDataManager).getManagedEntityClass());
    Class<ExecutionEntityImpl> expectedManagedEntityClass8 = ExecutionEntityImpl.class;
    assertEquals(expectedManagedEntityClass8,
        ((MybatisExecutionDataManager) executionDataManager).getManagedEntityClass());
    Class<HistoricActivityInstanceEntityImpl> expectedManagedEntityClass9 = HistoricActivityInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass9,
        ((MybatisHistoricActivityInstanceDataManager) historicActivityInstanceDataManager).getManagedEntityClass());
    Class<HistoricDetailEntityImpl> expectedManagedEntityClass10 = HistoricDetailEntityImpl.class;
    assertEquals(expectedManagedEntityClass10,
        ((MybatisHistoricDetailDataManager) historicDetailDataManager).getManagedEntityClass());
    Class<HistoricIdentityLinkEntityImpl> expectedManagedEntityClass11 = HistoricIdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass11,
        ((MybatisHistoricIdentityLinkDataManager) historicIdentityLinkDataManager).getManagedEntityClass());
    Class<HistoricProcessInstanceEntityImpl> expectedManagedEntityClass12 = HistoricProcessInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass12,
        ((MybatisHistoricProcessInstanceDataManager) historicProcessInstanceDataManager).getManagedEntityClass());
    Class<HistoricTaskInstanceEntityImpl> expectedManagedEntityClass13 = HistoricTaskInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass13,
        ((MybatisHistoricTaskInstanceDataManager) historicTaskInstanceDataManager).getManagedEntityClass());
    Class<HistoricVariableInstanceEntityImpl> expectedManagedEntityClass14 = HistoricVariableInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass14,
        ((MybatisHistoricVariableInstanceDataManager) historicVariableInstanceDataManager).getManagedEntityClass());
    Class<IdentityLinkEntityImpl> expectedManagedEntityClass15 = IdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass15,
        ((MybatisIdentityLinkDataManager) identityLinkDataManager).getManagedEntityClass());
    Class<JobEntityImpl> expectedManagedEntityClass16 = JobEntityImpl.class;
    assertEquals(expectedManagedEntityClass16, ((MybatisJobDataManager) jobDataManager).getManagedEntityClass());
    Class<MessageEventSubscriptionEntityImpl> expectedGetResult2 = MessageEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult2, managedEntitySubClasses.get(0));
    Class<ModelEntityImpl> expectedManagedEntityClass17 = ModelEntityImpl.class;
    assertEquals(expectedManagedEntityClass17, ((MybatisModelDataManager) modelDataManager).getManagedEntityClass());
    Class<ProcessDefinitionEntityImpl> expectedManagedEntityClass18 = ProcessDefinitionEntityImpl.class;
    assertEquals(expectedManagedEntityClass18,
        ((MybatisProcessDefinitionDataManager) processDefinitionDataManager).getManagedEntityClass());
    Class<ProcessDefinitionInfoEntityImpl> expectedManagedEntityClass19 = ProcessDefinitionInfoEntityImpl.class;
    assertEquals(expectedManagedEntityClass19,
        ((MybatisProcessDefinitionInfoDataManager) processDefinitionInfoDataManager).getManagedEntityClass());
    Class<PropertyEntityImpl> expectedManagedEntityClass20 = PropertyEntityImpl.class;
    assertEquals(expectedManagedEntityClass20,
        ((MybatisPropertyDataManager) propertyDataManager).getManagedEntityClass());
    Class<ResourceEntityImpl> expectedManagedEntityClass21 = ResourceEntityImpl.class;
    assertEquals(expectedManagedEntityClass21,
        ((MybatisResourceDataManager) resourceDataManager).getManagedEntityClass());
    Class<SignalEventSubscriptionEntityImpl> expectedGetResult3 = SignalEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult3, managedEntitySubClasses.get(1));
    Class<SuspendedJobEntityImpl> expectedManagedEntityClass22 = SuspendedJobEntityImpl.class;
    assertEquals(expectedManagedEntityClass22,
        ((MybatisSuspendedJobDataManager) suspendedJobDataManager).getManagedEntityClass());
    Class<TaskEntityImpl> expectedManagedEntityClass23 = TaskEntityImpl.class;
    assertEquals(expectedManagedEntityClass23, ((MybatisTaskDataManager) taskDataManager).getManagedEntityClass());
    Class<TimerJobEntityImpl> expectedManagedEntityClass24 = TimerJobEntityImpl.class;
    assertEquals(expectedManagedEntityClass24,
        ((MybatisTimerJobDataManager) timerJobDataManager).getManagedEntityClass());
    Class<VariableInstanceEntityImpl> expectedManagedEntityClass25 = VariableInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass25,
        ((MybatisVariableInstanceDataManager) variableInstanceDataManager).getManagedEntityClass());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisAttachmentDataManager attachmentDataManager = new MybatisAttachmentDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setAttachmentDataManager(attachmentDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(attachmentDataManager, jtaProcessEngineConfiguration.getAttachmentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisByteArrayDataManager byteArrayDataManager = new MybatisByteArrayDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setByteArrayDataManager(byteArrayDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(byteArrayDataManager, jtaProcessEngineConfiguration.getByteArrayDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisCommentDataManager commentDataManager = new MybatisCommentDataManager(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setCommentDataManager(commentDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(commentDataManager, jtaProcessEngineConfiguration.getCommentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisDeploymentDataManager deploymentDataManager = new MybatisDeploymentDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setDeploymentDataManager(deploymentDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(deploymentDataManager, jtaProcessEngineConfiguration.getDeploymentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisEventLogEntryDataManager eventLogEntryDataManager = new MybatisEventLogEntryDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setEventLogEntryDataManager(eventLogEntryDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(eventLogEntryDataManager, jtaProcessEngineConfiguration.getEventLogEntryDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers7() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisEventSubscriptionDataManager eventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setEventSubscriptionDataManager(eventSubscriptionDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(eventSubscriptionDataManager, jtaProcessEngineConfiguration.getEventSubscriptionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers8() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisExecutionDataManager executionDataManager = new MybatisExecutionDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setExecutionDataManager(executionDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(executionDataManager, jtaProcessEngineConfiguration.getExecutionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers9() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricActivityInstanceDataManager historicActivityInstanceDataManager = new MybatisHistoricActivityInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricActivityInstanceDataManager(historicActivityInstanceDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(historicActivityInstanceDataManager,
        jtaProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers10() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricDetailDataManager historicDetailDataManager = new MybatisHistoricDetailDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricDetailDataManager(historicDetailDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(historicDetailDataManager, jtaProcessEngineConfiguration.getHistoricDetailDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers11() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricIdentityLinkDataManager historicIdentityLinkDataManager = new MybatisHistoricIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricIdentityLinkDataManager(historicIdentityLinkDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(historicIdentityLinkDataManager, jtaProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers12() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricProcessInstanceDataManager historicProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricProcessInstanceDataManager(historicProcessInstanceDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(historicProcessInstanceDataManager,
        jtaProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers13() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricTaskInstanceDataManager historicTaskInstanceDataManager = new MybatisHistoricTaskInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricTaskInstanceDataManager(historicTaskInstanceDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(historicTaskInstanceDataManager, jtaProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers14() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisHistoricVariableInstanceDataManager historicVariableInstanceDataManager = new MybatisHistoricVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setHistoricVariableInstanceDataManager(historicVariableInstanceDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(historicVariableInstanceDataManager,
        jtaProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers15() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisIdentityLinkDataManager identityLinkDataManager = new MybatisIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setIdentityLinkDataManager(identityLinkDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(identityLinkDataManager, jtaProcessEngineConfiguration.getIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers16() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisJobDataManager jobDataManager = new MybatisJobDataManager(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setJobDataManager(jobDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(jobDataManager, jtaProcessEngineConfiguration.getJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers17() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisTimerJobDataManager timerJobDataManager = new MybatisTimerJobDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setTimerJobDataManager(timerJobDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(timerJobDataManager, jtaProcessEngineConfiguration.getTimerJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers18() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisSuspendedJobDataManager suspendedJobDataManager = new MybatisSuspendedJobDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setSuspendedJobDataManager(suspendedJobDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(suspendedJobDataManager, jtaProcessEngineConfiguration.getSuspendedJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers19() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisDeadLetterJobDataManager deadLetterJobDataManager = new MybatisDeadLetterJobDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setDeadLetterJobDataManager(deadLetterJobDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(deadLetterJobDataManager, jtaProcessEngineConfiguration.getDeadLetterJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers20() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisModelDataManager modelDataManager = new MybatisModelDataManager(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setModelDataManager(modelDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(modelDataManager, jtaProcessEngineConfiguration.getModelDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers21() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisProcessDefinitionDataManager processDefinitionDataManager = new MybatisProcessDefinitionDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setProcessDefinitionDataManager(processDefinitionDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(processDefinitionDataManager, jtaProcessEngineConfiguration.getProcessDefinitionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers22() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisProcessDefinitionInfoDataManager processDefinitionInfoDataManager = new MybatisProcessDefinitionInfoDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setProcessDefinitionInfoDataManager(processDefinitionInfoDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(processDefinitionInfoDataManager, jtaProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers23() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisPropertyDataManager propertyDataManager = new MybatisPropertyDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setPropertyDataManager(propertyDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(propertyDataManager, jtaProcessEngineConfiguration.getPropertyDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers24() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisResourceDataManager resourceDataManager = new MybatisResourceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setResourceDataManager(resourceDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(resourceDataManager, jtaProcessEngineConfiguration.getResourceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers25() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisTaskDataManager taskDataManager = new MybatisTaskDataManager(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setTaskDataManager(taskDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(taskDataManager, jtaProcessEngineConfiguration.getTaskDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initDataManagers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDataManagers()}
   */
  @Test
  public void testInitDataManagers26() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisVariableInstanceDataManager variableInstanceDataManager = new MybatisVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setVariableInstanceDataManager(variableInstanceDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    assertSame(variableInstanceDataManager, jtaProcessEngineConfiguration.getVariableInstanceDataManager());
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
  public void testInitDataManagers_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initDataManagers();

    // Assert
    AttachmentDataManager attachmentDataManager = jtaProcessEngineConfiguration.getAttachmentDataManager();
    assertTrue(attachmentDataManager instanceof MybatisAttachmentDataManager);
    ByteArrayDataManager byteArrayDataManager = jtaProcessEngineConfiguration.getByteArrayDataManager();
    assertTrue(byteArrayDataManager instanceof MybatisByteArrayDataManager);
    CommentDataManager commentDataManager = jtaProcessEngineConfiguration.getCommentDataManager();
    assertTrue(commentDataManager instanceof MybatisCommentDataManager);
    DeadLetterJobDataManager deadLetterJobDataManager = jtaProcessEngineConfiguration.getDeadLetterJobDataManager();
    assertTrue(deadLetterJobDataManager instanceof MybatisDeadLetterJobDataManager);
    DeploymentDataManager deploymentDataManager = jtaProcessEngineConfiguration.getDeploymentDataManager();
    assertTrue(deploymentDataManager instanceof MybatisDeploymentDataManager);
    EventLogEntryDataManager eventLogEntryDataManager = jtaProcessEngineConfiguration.getEventLogEntryDataManager();
    assertTrue(eventLogEntryDataManager instanceof MybatisEventLogEntryDataManager);
    EventSubscriptionDataManager eventSubscriptionDataManager = jtaProcessEngineConfiguration
        .getEventSubscriptionDataManager();
    assertTrue(eventSubscriptionDataManager instanceof MybatisEventSubscriptionDataManager);
    ExecutionDataManager executionDataManager = jtaProcessEngineConfiguration.getExecutionDataManager();
    assertTrue(executionDataManager instanceof MybatisExecutionDataManager);
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceDataManager();
    assertTrue(historicActivityInstanceDataManager instanceof MybatisHistoricActivityInstanceDataManager);
    HistoricDetailDataManager historicDetailDataManager = jtaProcessEngineConfiguration.getHistoricDetailDataManager();
    assertTrue(historicDetailDataManager instanceof MybatisHistoricDetailDataManager);
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkDataManager();
    assertTrue(historicIdentityLinkDataManager instanceof MybatisHistoricIdentityLinkDataManager);
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceDataManager();
    assertTrue(historicProcessInstanceDataManager instanceof MybatisHistoricProcessInstanceDataManager);
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceDataManager();
    assertTrue(historicTaskInstanceDataManager instanceof MybatisHistoricTaskInstanceDataManager);
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceDataManager();
    assertTrue(historicVariableInstanceDataManager instanceof MybatisHistoricVariableInstanceDataManager);
    IdentityLinkDataManager identityLinkDataManager = jtaProcessEngineConfiguration.getIdentityLinkDataManager();
    assertTrue(identityLinkDataManager instanceof MybatisIdentityLinkDataManager);
    JobDataManager jobDataManager = jtaProcessEngineConfiguration.getJobDataManager();
    assertTrue(jobDataManager instanceof MybatisJobDataManager);
    ModelDataManager modelDataManager = jtaProcessEngineConfiguration.getModelDataManager();
    assertTrue(modelDataManager instanceof MybatisModelDataManager);
    ProcessDefinitionDataManager processDefinitionDataManager = jtaProcessEngineConfiguration
        .getProcessDefinitionDataManager();
    assertTrue(processDefinitionDataManager instanceof MybatisProcessDefinitionDataManager);
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = jtaProcessEngineConfiguration
        .getProcessDefinitionInfoDataManager();
    assertTrue(processDefinitionInfoDataManager instanceof MybatisProcessDefinitionInfoDataManager);
    PropertyDataManager propertyDataManager = jtaProcessEngineConfiguration.getPropertyDataManager();
    assertTrue(propertyDataManager instanceof MybatisPropertyDataManager);
    ResourceDataManager resourceDataManager = jtaProcessEngineConfiguration.getResourceDataManager();
    assertTrue(resourceDataManager instanceof MybatisResourceDataManager);
    SuspendedJobDataManager suspendedJobDataManager = jtaProcessEngineConfiguration.getSuspendedJobDataManager();
    assertTrue(suspendedJobDataManager instanceof MybatisSuspendedJobDataManager);
    TaskDataManager taskDataManager = jtaProcessEngineConfiguration.getTaskDataManager();
    assertTrue(taskDataManager instanceof MybatisTaskDataManager);
    TimerJobDataManager timerJobDataManager = jtaProcessEngineConfiguration.getTimerJobDataManager();
    assertTrue(timerJobDataManager instanceof MybatisTimerJobDataManager);
    VariableInstanceDataManager variableInstanceDataManager = jtaProcessEngineConfiguration
        .getVariableInstanceDataManager();
    assertTrue(variableInstanceDataManager instanceof MybatisVariableInstanceDataManager);
    assertNull(((MybatisAttachmentDataManager) attachmentDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisByteArrayDataManager) byteArrayDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisCommentDataManager) commentDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisDeadLetterJobDataManager) deadLetterJobDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisDeploymentDataManager) deploymentDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisEventLogEntryDataManager) eventLogEntryDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisExecutionDataManager) executionDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisHistoricActivityInstanceDataManager) historicActivityInstanceDataManager)
        .getManagedEntitySubClasses());
    assertNull(((MybatisHistoricDetailDataManager) historicDetailDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisHistoricIdentityLinkDataManager) historicIdentityLinkDataManager).getManagedEntitySubClasses());
    assertNull(
        ((MybatisHistoricProcessInstanceDataManager) historicProcessInstanceDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisHistoricTaskInstanceDataManager) historicTaskInstanceDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisHistoricVariableInstanceDataManager) historicVariableInstanceDataManager)
        .getManagedEntitySubClasses());
    assertNull(((MybatisIdentityLinkDataManager) identityLinkDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisJobDataManager) jobDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisModelDataManager) modelDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisProcessDefinitionDataManager) processDefinitionDataManager).getManagedEntitySubClasses());
    assertNull(
        ((MybatisProcessDefinitionInfoDataManager) processDefinitionInfoDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisPropertyDataManager) propertyDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisResourceDataManager) resourceDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisSuspendedJobDataManager) suspendedJobDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisTaskDataManager) taskDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisTimerJobDataManager) timerJobDataManager).getManagedEntitySubClasses());
    assertNull(((MybatisVariableInstanceDataManager) variableInstanceDataManager).getManagedEntitySubClasses());
    List<Class<? extends EventSubscriptionEntity>> managedEntitySubClasses = ((MybatisEventSubscriptionDataManager) eventSubscriptionDataManager)
        .getManagedEntitySubClasses();
    assertEquals(3, managedEntitySubClasses.size());
    Class<AttachmentEntityImpl> expectedManagedEntityClass = AttachmentEntityImpl.class;
    assertEquals(expectedManagedEntityClass,
        ((MybatisAttachmentDataManager) attachmentDataManager).getManagedEntityClass());
    Class<ByteArrayEntityImpl> expectedManagedEntityClass2 = ByteArrayEntityImpl.class;
    assertEquals(expectedManagedEntityClass2,
        ((MybatisByteArrayDataManager) byteArrayDataManager).getManagedEntityClass());
    Class<CommentEntityImpl> expectedManagedEntityClass3 = CommentEntityImpl.class;
    assertEquals(expectedManagedEntityClass3, ((MybatisCommentDataManager) commentDataManager).getManagedEntityClass());
    Class<CompensateEventSubscriptionEntityImpl> expectedGetResult = CompensateEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult, managedEntitySubClasses.get(2));
    Class<DeadLetterJobEntityImpl> expectedManagedEntityClass4 = DeadLetterJobEntityImpl.class;
    assertEquals(expectedManagedEntityClass4,
        ((MybatisDeadLetterJobDataManager) deadLetterJobDataManager).getManagedEntityClass());
    Class<DeploymentEntityImpl> expectedManagedEntityClass5 = DeploymentEntityImpl.class;
    assertEquals(expectedManagedEntityClass5,
        ((MybatisDeploymentDataManager) deploymentDataManager).getManagedEntityClass());
    Class<EventLogEntryEntityImpl> expectedManagedEntityClass6 = EventLogEntryEntityImpl.class;
    assertEquals(expectedManagedEntityClass6,
        ((MybatisEventLogEntryDataManager) eventLogEntryDataManager).getManagedEntityClass());
    Class<EventSubscriptionEntityImpl> expectedManagedEntityClass7 = EventSubscriptionEntityImpl.class;
    assertEquals(expectedManagedEntityClass7,
        ((MybatisEventSubscriptionDataManager) eventSubscriptionDataManager).getManagedEntityClass());
    Class<ExecutionEntityImpl> expectedManagedEntityClass8 = ExecutionEntityImpl.class;
    assertEquals(expectedManagedEntityClass8,
        ((MybatisExecutionDataManager) executionDataManager).getManagedEntityClass());
    Class<HistoricActivityInstanceEntityImpl> expectedManagedEntityClass9 = HistoricActivityInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass9,
        ((MybatisHistoricActivityInstanceDataManager) historicActivityInstanceDataManager).getManagedEntityClass());
    Class<HistoricDetailEntityImpl> expectedManagedEntityClass10 = HistoricDetailEntityImpl.class;
    assertEquals(expectedManagedEntityClass10,
        ((MybatisHistoricDetailDataManager) historicDetailDataManager).getManagedEntityClass());
    Class<HistoricIdentityLinkEntityImpl> expectedManagedEntityClass11 = HistoricIdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass11,
        ((MybatisHistoricIdentityLinkDataManager) historicIdentityLinkDataManager).getManagedEntityClass());
    Class<HistoricProcessInstanceEntityImpl> expectedManagedEntityClass12 = HistoricProcessInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass12,
        ((MybatisHistoricProcessInstanceDataManager) historicProcessInstanceDataManager).getManagedEntityClass());
    Class<HistoricTaskInstanceEntityImpl> expectedManagedEntityClass13 = HistoricTaskInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass13,
        ((MybatisHistoricTaskInstanceDataManager) historicTaskInstanceDataManager).getManagedEntityClass());
    Class<HistoricVariableInstanceEntityImpl> expectedManagedEntityClass14 = HistoricVariableInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass14,
        ((MybatisHistoricVariableInstanceDataManager) historicVariableInstanceDataManager).getManagedEntityClass());
    Class<IdentityLinkEntityImpl> expectedManagedEntityClass15 = IdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass15,
        ((MybatisIdentityLinkDataManager) identityLinkDataManager).getManagedEntityClass());
    Class<JobEntityImpl> expectedManagedEntityClass16 = JobEntityImpl.class;
    assertEquals(expectedManagedEntityClass16, ((MybatisJobDataManager) jobDataManager).getManagedEntityClass());
    Class<MessageEventSubscriptionEntityImpl> expectedGetResult2 = MessageEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult2, managedEntitySubClasses.get(0));
    Class<ModelEntityImpl> expectedManagedEntityClass17 = ModelEntityImpl.class;
    assertEquals(expectedManagedEntityClass17, ((MybatisModelDataManager) modelDataManager).getManagedEntityClass());
    Class<ProcessDefinitionEntityImpl> expectedManagedEntityClass18 = ProcessDefinitionEntityImpl.class;
    assertEquals(expectedManagedEntityClass18,
        ((MybatisProcessDefinitionDataManager) processDefinitionDataManager).getManagedEntityClass());
    Class<ProcessDefinitionInfoEntityImpl> expectedManagedEntityClass19 = ProcessDefinitionInfoEntityImpl.class;
    assertEquals(expectedManagedEntityClass19,
        ((MybatisProcessDefinitionInfoDataManager) processDefinitionInfoDataManager).getManagedEntityClass());
    Class<PropertyEntityImpl> expectedManagedEntityClass20 = PropertyEntityImpl.class;
    assertEquals(expectedManagedEntityClass20,
        ((MybatisPropertyDataManager) propertyDataManager).getManagedEntityClass());
    Class<ResourceEntityImpl> expectedManagedEntityClass21 = ResourceEntityImpl.class;
    assertEquals(expectedManagedEntityClass21,
        ((MybatisResourceDataManager) resourceDataManager).getManagedEntityClass());
    Class<SignalEventSubscriptionEntityImpl> expectedGetResult3 = SignalEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult3, managedEntitySubClasses.get(1));
    Class<SuspendedJobEntityImpl> expectedManagedEntityClass22 = SuspendedJobEntityImpl.class;
    assertEquals(expectedManagedEntityClass22,
        ((MybatisSuspendedJobDataManager) suspendedJobDataManager).getManagedEntityClass());
    Class<TaskEntityImpl> expectedManagedEntityClass23 = TaskEntityImpl.class;
    assertEquals(expectedManagedEntityClass23, ((MybatisTaskDataManager) taskDataManager).getManagedEntityClass());
    Class<TimerJobEntityImpl> expectedManagedEntityClass24 = TimerJobEntityImpl.class;
    assertEquals(expectedManagedEntityClass24,
        ((MybatisTimerJobDataManager) timerJobDataManager).getManagedEntityClass());
    Class<VariableInstanceEntityImpl> expectedManagedEntityClass25 = VariableInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass25,
        ((MybatisVariableInstanceDataManager) variableInstanceDataManager).getManagedEntityClass());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    AttachmentEntityManager attachmentEntityManager = jtaProcessEngineConfiguration.getAttachmentEntityManager();
    assertTrue(attachmentEntityManager instanceof AttachmentEntityManagerImpl);
    ByteArrayEntityManager byteArrayEntityManager = jtaProcessEngineConfiguration.getByteArrayEntityManager();
    assertTrue(byteArrayEntityManager instanceof ByteArrayEntityManagerImpl);
    CommentEntityManager commentEntityManager = jtaProcessEngineConfiguration.getCommentEntityManager();
    assertTrue(commentEntityManager instanceof CommentEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    DeploymentEntityManager deploymentEntityManager = jtaProcessEngineConfiguration.getDeploymentEntityManager();
    assertTrue(deploymentEntityManager instanceof DeploymentEntityManagerImpl);
    EventLogEntryEntityManager eventLogEntryEntityManager = jtaProcessEngineConfiguration
        .getEventLogEntryEntityManager();
    assertTrue(eventLogEntryEntityManager instanceof EventLogEntryEntityManagerImpl);
    EventSubscriptionEntityManager eventSubscriptionEntityManager = jtaProcessEngineConfiguration
        .getEventSubscriptionEntityManager();
    assertTrue(eventSubscriptionEntityManager instanceof EventSubscriptionEntityManagerImpl);
    ExecutionEntityManager executionEntityManager = jtaProcessEngineConfiguration.getExecutionEntityManager();
    assertTrue(executionEntityManager instanceof ExecutionEntityManagerImpl);
    HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceEntityManager();
    assertTrue(historicActivityInstanceEntityManager instanceof HistoricActivityInstanceEntityManagerImpl);
    HistoricDetailEntityManager historicDetailEntityManager = jtaProcessEngineConfiguration
        .getHistoricDetailEntityManager();
    assertTrue(historicDetailEntityManager instanceof HistoricDetailEntityManagerImpl);
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkEntityManager();
    assertTrue(historicIdentityLinkEntityManager instanceof HistoricIdentityLinkEntityManagerImpl);
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceEntityManager();
    assertTrue(historicProcessInstanceEntityManager instanceof HistoricProcessInstanceEntityManagerImpl);
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceEntityManager();
    assertTrue(historicTaskInstanceEntityManager instanceof HistoricTaskInstanceEntityManagerImpl);
    HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceEntityManager();
    assertTrue(historicVariableInstanceEntityManager instanceof HistoricVariableInstanceEntityManagerImpl);
    IdentityLinkEntityManager identityLinkEntityManager = jtaProcessEngineConfiguration.getIdentityLinkEntityManager();
    assertTrue(identityLinkEntityManager instanceof IdentityLinkEntityManagerImpl);
    JobEntityManager jobEntityManager = jtaProcessEngineConfiguration.getJobEntityManager();
    assertTrue(jobEntityManager instanceof JobEntityManagerImpl);
    ModelEntityManager modelEntityManager = jtaProcessEngineConfiguration.getModelEntityManager();
    assertTrue(modelEntityManager instanceof ModelEntityManagerImpl);
    ProcessDefinitionEntityManager processDefinitionEntityManager = jtaProcessEngineConfiguration
        .getProcessDefinitionEntityManager();
    assertTrue(processDefinitionEntityManager instanceof ProcessDefinitionEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    ResourceEntityManager resourceEntityManager = jtaProcessEngineConfiguration.getResourceEntityManager();
    assertTrue(resourceEntityManager instanceof ResourceEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    TaskEntityManager taskEntityManager = jtaProcessEngineConfiguration.getTaskEntityManager();
    assertTrue(taskEntityManager instanceof TaskEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    VariableInstanceEntityManager variableInstanceEntityManager = jtaProcessEngineConfiguration
        .getVariableInstanceEntityManager();
    assertTrue(variableInstanceEntityManager instanceof VariableInstanceEntityManagerImpl);
    assertNull(((AttachmentEntityManagerImpl) attachmentEntityManager).getAttachmentDataManager());
    assertNull(((ByteArrayEntityManagerImpl) byteArrayEntityManager).getByteArrayDataManager());
    assertNull(((CommentEntityManagerImpl) commentEntityManager).getCommentDataManager());
    assertNull(((DeploymentEntityManagerImpl) deploymentEntityManager).getDeploymentDataManager());
    assertNull(((EventLogEntryEntityManagerImpl) eventLogEntryEntityManager).getEventLogEntryDataManager());
    assertNull(((EventSubscriptionEntityManagerImpl) eventSubscriptionEntityManager).getEventSubscriptionDataManager());
    assertNull(((ExecutionEntityManagerImpl) executionEntityManager).getExecutionDataManager());
    assertNull(((HistoricActivityInstanceEntityManagerImpl) historicActivityInstanceEntityManager)
        .getHistoricActivityInstanceDataManager());
    assertNull(((HistoricDetailEntityManagerImpl) historicDetailEntityManager).getHistoricDetailDataManager());
    assertNull(((HistoricIdentityLinkEntityManagerImpl) historicIdentityLinkEntityManager)
        .getHistoricIdentityLinkDataManager());
    assertNull(((HistoricProcessInstanceEntityManagerImpl) historicProcessInstanceEntityManager)
        .getHistoricProcessInstanceDataManager());
    assertNull(((HistoricTaskInstanceEntityManagerImpl) historicTaskInstanceEntityManager)
        .getHistoricTaskInstanceDataManager());
    assertNull(((HistoricVariableInstanceEntityManagerImpl) historicVariableInstanceEntityManager)
        .getHistoricVariableInstanceDataManager());
    assertNull(((IdentityLinkEntityManagerImpl) identityLinkEntityManager).getIdentityLinkDataManager());
    assertNull(((JobEntityManagerImpl) jobEntityManager).getJobDataManager());
    assertNull(((ModelEntityManagerImpl) modelEntityManager).getModelDataManager());
    assertNull(((ProcessDefinitionEntityManagerImpl) processDefinitionEntityManager).getProcessDefinitionDataManager());
    assertNull(((ResourceEntityManagerImpl) resourceEntityManager).getResourceDataManager());
    assertNull(((TaskEntityManagerImpl) taskEntityManager).getTaskDataManager());
    assertNull(((VariableInstanceEntityManagerImpl) variableInstanceEntityManager).getVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManager = new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setAttachmentEntityManager(attachmentEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(attachmentEntityManager, jtaProcessEngineConfiguration.getAttachmentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ByteArrayEntityManagerImpl byteArrayEntityManager = new ByteArrayEntityManagerImpl(processEngineConfiguration,
        new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setByteArrayEntityManager(byteArrayEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(byteArrayEntityManager, jtaProcessEngineConfiguration.getByteArrayEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommentEntityManagerImpl commentEntityManager = new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setCommentEntityManager(commentEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(commentEntityManager, jtaProcessEngineConfiguration.getCommentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentEntityManagerImpl deploymentEntityManager = new DeploymentEntityManagerImpl(processEngineConfiguration,
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setDeploymentEntityManager(deploymentEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(deploymentEntityManager, jtaProcessEngineConfiguration.getDeploymentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventLogEntryEntityManagerImpl eventLogEntryEntityManager = new EventLogEntryEntityManagerImpl(
        processEngineConfiguration, new MybatisEventLogEntryDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setEventLogEntryEntityManager(eventLogEntryEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(eventLogEntryEntityManager, jtaProcessEngineConfiguration.getEventLogEntryEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers7() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManager = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setEventSubscriptionEntityManager(eventSubscriptionEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(eventSubscriptionEntityManager, jtaProcessEngineConfiguration.getEventSubscriptionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers8() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManager = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setExecutionEntityManager(executionEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(executionEntityManager, jtaProcessEngineConfiguration.getExecutionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers9() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManager = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfiguration,
        new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setHistoricActivityInstanceEntityManager(historicActivityInstanceEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(historicActivityInstanceEntityManager,
        jtaProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers10() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricDetailEntityManagerImpl historicDetailEntityManager = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricDetailDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setHistoricDetailEntityManager(historicDetailEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(historicDetailEntityManager, jtaProcessEngineConfiguration.getHistoricDetailEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers11() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManager = new HistoricIdentityLinkEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricIdentityLinkDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setHistoricIdentityLinkEntityManager(historicIdentityLinkEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(historicIdentityLinkEntityManager, jtaProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers12() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManager = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setHistoricProcessInstanceEntityManager(historicProcessInstanceEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(historicProcessInstanceEntityManager,
        jtaProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers13() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManager = new HistoricTaskInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setHistoricTaskInstanceEntityManager(historicTaskInstanceEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(historicTaskInstanceEntityManager, jtaProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers14() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManager = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration,
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setHistoricVariableInstanceEntityManager(historicVariableInstanceEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(historicVariableInstanceEntityManager,
        jtaProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers15() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    IdentityLinkEntityManagerImpl identityLinkEntityManager = new IdentityLinkEntityManagerImpl(
        processEngineConfiguration, new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setIdentityLinkEntityManager(identityLinkEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(identityLinkEntityManager, jtaProcessEngineConfiguration.getIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers16() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager = new JobEntityManagerImpl(processEngineConfiguration,
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setJobEntityManager(jobEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(jobEntityManager, jtaProcessEngineConfiguration.getJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers17() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManager = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setTimerJobEntityManager(timerJobEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    AttachmentEntityManager attachmentEntityManager = jtaProcessEngineConfiguration.getAttachmentEntityManager();
    assertTrue(attachmentEntityManager instanceof AttachmentEntityManagerImpl);
    ByteArrayEntityManager byteArrayEntityManager = jtaProcessEngineConfiguration.getByteArrayEntityManager();
    assertTrue(byteArrayEntityManager instanceof ByteArrayEntityManagerImpl);
    CommentEntityManager commentEntityManager = jtaProcessEngineConfiguration.getCommentEntityManager();
    assertTrue(commentEntityManager instanceof CommentEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    DeploymentEntityManager deploymentEntityManager = jtaProcessEngineConfiguration.getDeploymentEntityManager();
    assertTrue(deploymentEntityManager instanceof DeploymentEntityManagerImpl);
    EventLogEntryEntityManager eventLogEntryEntityManager = jtaProcessEngineConfiguration
        .getEventLogEntryEntityManager();
    assertTrue(eventLogEntryEntityManager instanceof EventLogEntryEntityManagerImpl);
    EventSubscriptionEntityManager eventSubscriptionEntityManager = jtaProcessEngineConfiguration
        .getEventSubscriptionEntityManager();
    assertTrue(eventSubscriptionEntityManager instanceof EventSubscriptionEntityManagerImpl);
    ExecutionEntityManager executionEntityManager = jtaProcessEngineConfiguration.getExecutionEntityManager();
    assertTrue(executionEntityManager instanceof ExecutionEntityManagerImpl);
    HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceEntityManager();
    assertTrue(historicActivityInstanceEntityManager instanceof HistoricActivityInstanceEntityManagerImpl);
    HistoricDetailEntityManager historicDetailEntityManager = jtaProcessEngineConfiguration
        .getHistoricDetailEntityManager();
    assertTrue(historicDetailEntityManager instanceof HistoricDetailEntityManagerImpl);
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkEntityManager();
    assertTrue(historicIdentityLinkEntityManager instanceof HistoricIdentityLinkEntityManagerImpl);
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceEntityManager();
    assertTrue(historicProcessInstanceEntityManager instanceof HistoricProcessInstanceEntityManagerImpl);
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceEntityManager();
    assertTrue(historicTaskInstanceEntityManager instanceof HistoricTaskInstanceEntityManagerImpl);
    HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceEntityManager();
    assertTrue(historicVariableInstanceEntityManager instanceof HistoricVariableInstanceEntityManagerImpl);
    IdentityLinkEntityManager identityLinkEntityManager = jtaProcessEngineConfiguration.getIdentityLinkEntityManager();
    assertTrue(identityLinkEntityManager instanceof IdentityLinkEntityManagerImpl);
    JobEntityManager jobEntityManager = jtaProcessEngineConfiguration.getJobEntityManager();
    assertTrue(jobEntityManager instanceof JobEntityManagerImpl);
    ModelEntityManager modelEntityManager = jtaProcessEngineConfiguration.getModelEntityManager();
    assertTrue(modelEntityManager instanceof ModelEntityManagerImpl);
    ProcessDefinitionEntityManager processDefinitionEntityManager = jtaProcessEngineConfiguration
        .getProcessDefinitionEntityManager();
    assertTrue(processDefinitionEntityManager instanceof ProcessDefinitionEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    ResourceEntityManager resourceEntityManager = jtaProcessEngineConfiguration.getResourceEntityManager();
    assertTrue(resourceEntityManager instanceof ResourceEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    TaskEntityManager taskEntityManager = jtaProcessEngineConfiguration.getTaskEntityManager();
    assertTrue(taskEntityManager instanceof TaskEntityManagerImpl);
    TimerJobEntityManager timerJobEntityManager2 = jtaProcessEngineConfiguration.getTimerJobEntityManager();
    assertTrue(timerJobEntityManager2 instanceof TimerJobEntityManagerImpl);
    VariableInstanceEntityManager variableInstanceEntityManager = jtaProcessEngineConfiguration
        .getVariableInstanceEntityManager();
    assertTrue(variableInstanceEntityManager instanceof VariableInstanceEntityManagerImpl);
    assertNull(((AttachmentEntityManagerImpl) attachmentEntityManager).getAttachmentDataManager());
    assertNull(((ByteArrayEntityManagerImpl) byteArrayEntityManager).getByteArrayDataManager());
    assertNull(((CommentEntityManagerImpl) commentEntityManager).getCommentDataManager());
    assertNull(((DeploymentEntityManagerImpl) deploymentEntityManager).getDeploymentDataManager());
    assertNull(((EventLogEntryEntityManagerImpl) eventLogEntryEntityManager).getEventLogEntryDataManager());
    assertNull(((EventSubscriptionEntityManagerImpl) eventSubscriptionEntityManager).getEventSubscriptionDataManager());
    assertNull(((ExecutionEntityManagerImpl) executionEntityManager).getExecutionDataManager());
    assertNull(((HistoricActivityInstanceEntityManagerImpl) historicActivityInstanceEntityManager)
        .getHistoricActivityInstanceDataManager());
    assertNull(((HistoricDetailEntityManagerImpl) historicDetailEntityManager).getHistoricDetailDataManager());
    assertNull(((HistoricIdentityLinkEntityManagerImpl) historicIdentityLinkEntityManager)
        .getHistoricIdentityLinkDataManager());
    assertNull(((HistoricProcessInstanceEntityManagerImpl) historicProcessInstanceEntityManager)
        .getHistoricProcessInstanceDataManager());
    assertNull(((HistoricTaskInstanceEntityManagerImpl) historicTaskInstanceEntityManager)
        .getHistoricTaskInstanceDataManager());
    assertNull(((HistoricVariableInstanceEntityManagerImpl) historicVariableInstanceEntityManager)
        .getHistoricVariableInstanceDataManager());
    assertNull(((IdentityLinkEntityManagerImpl) identityLinkEntityManager).getIdentityLinkDataManager());
    assertNull(((JobEntityManagerImpl) jobEntityManager).getJobDataManager());
    assertNull(((ModelEntityManagerImpl) modelEntityManager).getModelDataManager());
    assertNull(((ProcessDefinitionEntityManagerImpl) processDefinitionEntityManager).getProcessDefinitionDataManager());
    assertNull(((ResourceEntityManagerImpl) resourceEntityManager).getResourceDataManager());
    assertNull(((TaskEntityManagerImpl) taskEntityManager).getTaskDataManager());
    assertNull(((VariableInstanceEntityManagerImpl) variableInstanceEntityManager).getVariableInstanceDataManager());
    assertSame(timerJobEntityManager, timerJobEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers18() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManager = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setSuspendedJobEntityManager(suspendedJobEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    AttachmentEntityManager attachmentEntityManager = jtaProcessEngineConfiguration.getAttachmentEntityManager();
    assertTrue(attachmentEntityManager instanceof AttachmentEntityManagerImpl);
    ByteArrayEntityManager byteArrayEntityManager = jtaProcessEngineConfiguration.getByteArrayEntityManager();
    assertTrue(byteArrayEntityManager instanceof ByteArrayEntityManagerImpl);
    CommentEntityManager commentEntityManager = jtaProcessEngineConfiguration.getCommentEntityManager();
    assertTrue(commentEntityManager instanceof CommentEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    DeploymentEntityManager deploymentEntityManager = jtaProcessEngineConfiguration.getDeploymentEntityManager();
    assertTrue(deploymentEntityManager instanceof DeploymentEntityManagerImpl);
    EventLogEntryEntityManager eventLogEntryEntityManager = jtaProcessEngineConfiguration
        .getEventLogEntryEntityManager();
    assertTrue(eventLogEntryEntityManager instanceof EventLogEntryEntityManagerImpl);
    EventSubscriptionEntityManager eventSubscriptionEntityManager = jtaProcessEngineConfiguration
        .getEventSubscriptionEntityManager();
    assertTrue(eventSubscriptionEntityManager instanceof EventSubscriptionEntityManagerImpl);
    ExecutionEntityManager executionEntityManager = jtaProcessEngineConfiguration.getExecutionEntityManager();
    assertTrue(executionEntityManager instanceof ExecutionEntityManagerImpl);
    HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceEntityManager();
    assertTrue(historicActivityInstanceEntityManager instanceof HistoricActivityInstanceEntityManagerImpl);
    HistoricDetailEntityManager historicDetailEntityManager = jtaProcessEngineConfiguration
        .getHistoricDetailEntityManager();
    assertTrue(historicDetailEntityManager instanceof HistoricDetailEntityManagerImpl);
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkEntityManager();
    assertTrue(historicIdentityLinkEntityManager instanceof HistoricIdentityLinkEntityManagerImpl);
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceEntityManager();
    assertTrue(historicProcessInstanceEntityManager instanceof HistoricProcessInstanceEntityManagerImpl);
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceEntityManager();
    assertTrue(historicTaskInstanceEntityManager instanceof HistoricTaskInstanceEntityManagerImpl);
    HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceEntityManager();
    assertTrue(historicVariableInstanceEntityManager instanceof HistoricVariableInstanceEntityManagerImpl);
    IdentityLinkEntityManager identityLinkEntityManager = jtaProcessEngineConfiguration.getIdentityLinkEntityManager();
    assertTrue(identityLinkEntityManager instanceof IdentityLinkEntityManagerImpl);
    JobEntityManager jobEntityManager = jtaProcessEngineConfiguration.getJobEntityManager();
    assertTrue(jobEntityManager instanceof JobEntityManagerImpl);
    ModelEntityManager modelEntityManager = jtaProcessEngineConfiguration.getModelEntityManager();
    assertTrue(modelEntityManager instanceof ModelEntityManagerImpl);
    ProcessDefinitionEntityManager processDefinitionEntityManager = jtaProcessEngineConfiguration
        .getProcessDefinitionEntityManager();
    assertTrue(processDefinitionEntityManager instanceof ProcessDefinitionEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    ResourceEntityManager resourceEntityManager = jtaProcessEngineConfiguration.getResourceEntityManager();
    assertTrue(resourceEntityManager instanceof ResourceEntityManagerImpl);
    SuspendedJobEntityManager suspendedJobEntityManager2 = jtaProcessEngineConfiguration.getSuspendedJobEntityManager();
    assertTrue(suspendedJobEntityManager2 instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    TaskEntityManager taskEntityManager = jtaProcessEngineConfiguration.getTaskEntityManager();
    assertTrue(taskEntityManager instanceof TaskEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    VariableInstanceEntityManager variableInstanceEntityManager = jtaProcessEngineConfiguration
        .getVariableInstanceEntityManager();
    assertTrue(variableInstanceEntityManager instanceof VariableInstanceEntityManagerImpl);
    assertNull(((AttachmentEntityManagerImpl) attachmentEntityManager).getAttachmentDataManager());
    assertNull(((ByteArrayEntityManagerImpl) byteArrayEntityManager).getByteArrayDataManager());
    assertNull(((CommentEntityManagerImpl) commentEntityManager).getCommentDataManager());
    assertNull(((DeploymentEntityManagerImpl) deploymentEntityManager).getDeploymentDataManager());
    assertNull(((EventLogEntryEntityManagerImpl) eventLogEntryEntityManager).getEventLogEntryDataManager());
    assertNull(((EventSubscriptionEntityManagerImpl) eventSubscriptionEntityManager).getEventSubscriptionDataManager());
    assertNull(((ExecutionEntityManagerImpl) executionEntityManager).getExecutionDataManager());
    assertNull(((HistoricActivityInstanceEntityManagerImpl) historicActivityInstanceEntityManager)
        .getHistoricActivityInstanceDataManager());
    assertNull(((HistoricDetailEntityManagerImpl) historicDetailEntityManager).getHistoricDetailDataManager());
    assertNull(((HistoricIdentityLinkEntityManagerImpl) historicIdentityLinkEntityManager)
        .getHistoricIdentityLinkDataManager());
    assertNull(((HistoricProcessInstanceEntityManagerImpl) historicProcessInstanceEntityManager)
        .getHistoricProcessInstanceDataManager());
    assertNull(((HistoricTaskInstanceEntityManagerImpl) historicTaskInstanceEntityManager)
        .getHistoricTaskInstanceDataManager());
    assertNull(((HistoricVariableInstanceEntityManagerImpl) historicVariableInstanceEntityManager)
        .getHistoricVariableInstanceDataManager());
    assertNull(((IdentityLinkEntityManagerImpl) identityLinkEntityManager).getIdentityLinkDataManager());
    assertNull(((JobEntityManagerImpl) jobEntityManager).getJobDataManager());
    assertNull(((ModelEntityManagerImpl) modelEntityManager).getModelDataManager());
    assertNull(((ProcessDefinitionEntityManagerImpl) processDefinitionEntityManager).getProcessDefinitionDataManager());
    assertNull(((ResourceEntityManagerImpl) resourceEntityManager).getResourceDataManager());
    assertNull(((TaskEntityManagerImpl) taskEntityManager).getTaskDataManager());
    assertNull(((VariableInstanceEntityManagerImpl) variableInstanceEntityManager).getVariableInstanceDataManager());
    assertSame(suspendedJobEntityManager, suspendedJobEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers19() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManager = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setDeadLetterJobEntityManager(deadLetterJobEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    AttachmentEntityManager attachmentEntityManager = jtaProcessEngineConfiguration.getAttachmentEntityManager();
    assertTrue(attachmentEntityManager instanceof AttachmentEntityManagerImpl);
    ByteArrayEntityManager byteArrayEntityManager = jtaProcessEngineConfiguration.getByteArrayEntityManager();
    assertTrue(byteArrayEntityManager instanceof ByteArrayEntityManagerImpl);
    CommentEntityManager commentEntityManager = jtaProcessEngineConfiguration.getCommentEntityManager();
    assertTrue(commentEntityManager instanceof CommentEntityManagerImpl);
    DeadLetterJobEntityManager deadLetterJobEntityManager2 = jtaProcessEngineConfiguration
        .getDeadLetterJobEntityManager();
    assertTrue(deadLetterJobEntityManager2 instanceof DeadLetterJobEntityManagerImpl);
    DeploymentEntityManager deploymentEntityManager = jtaProcessEngineConfiguration.getDeploymentEntityManager();
    assertTrue(deploymentEntityManager instanceof DeploymentEntityManagerImpl);
    EventLogEntryEntityManager eventLogEntryEntityManager = jtaProcessEngineConfiguration
        .getEventLogEntryEntityManager();
    assertTrue(eventLogEntryEntityManager instanceof EventLogEntryEntityManagerImpl);
    EventSubscriptionEntityManager eventSubscriptionEntityManager = jtaProcessEngineConfiguration
        .getEventSubscriptionEntityManager();
    assertTrue(eventSubscriptionEntityManager instanceof EventSubscriptionEntityManagerImpl);
    ExecutionEntityManager executionEntityManager = jtaProcessEngineConfiguration.getExecutionEntityManager();
    assertTrue(executionEntityManager instanceof ExecutionEntityManagerImpl);
    HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceEntityManager();
    assertTrue(historicActivityInstanceEntityManager instanceof HistoricActivityInstanceEntityManagerImpl);
    HistoricDetailEntityManager historicDetailEntityManager = jtaProcessEngineConfiguration
        .getHistoricDetailEntityManager();
    assertTrue(historicDetailEntityManager instanceof HistoricDetailEntityManagerImpl);
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkEntityManager();
    assertTrue(historicIdentityLinkEntityManager instanceof HistoricIdentityLinkEntityManagerImpl);
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceEntityManager();
    assertTrue(historicProcessInstanceEntityManager instanceof HistoricProcessInstanceEntityManagerImpl);
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceEntityManager();
    assertTrue(historicTaskInstanceEntityManager instanceof HistoricTaskInstanceEntityManagerImpl);
    HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceEntityManager();
    assertTrue(historicVariableInstanceEntityManager instanceof HistoricVariableInstanceEntityManagerImpl);
    IdentityLinkEntityManager identityLinkEntityManager = jtaProcessEngineConfiguration.getIdentityLinkEntityManager();
    assertTrue(identityLinkEntityManager instanceof IdentityLinkEntityManagerImpl);
    JobEntityManager jobEntityManager = jtaProcessEngineConfiguration.getJobEntityManager();
    assertTrue(jobEntityManager instanceof JobEntityManagerImpl);
    ModelEntityManager modelEntityManager = jtaProcessEngineConfiguration.getModelEntityManager();
    assertTrue(modelEntityManager instanceof ModelEntityManagerImpl);
    ProcessDefinitionEntityManager processDefinitionEntityManager = jtaProcessEngineConfiguration
        .getProcessDefinitionEntityManager();
    assertTrue(processDefinitionEntityManager instanceof ProcessDefinitionEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    ResourceEntityManager resourceEntityManager = jtaProcessEngineConfiguration.getResourceEntityManager();
    assertTrue(resourceEntityManager instanceof ResourceEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    TaskEntityManager taskEntityManager = jtaProcessEngineConfiguration.getTaskEntityManager();
    assertTrue(taskEntityManager instanceof TaskEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    VariableInstanceEntityManager variableInstanceEntityManager = jtaProcessEngineConfiguration
        .getVariableInstanceEntityManager();
    assertTrue(variableInstanceEntityManager instanceof VariableInstanceEntityManagerImpl);
    assertNull(((AttachmentEntityManagerImpl) attachmentEntityManager).getAttachmentDataManager());
    assertNull(((ByteArrayEntityManagerImpl) byteArrayEntityManager).getByteArrayDataManager());
    assertNull(((CommentEntityManagerImpl) commentEntityManager).getCommentDataManager());
    assertNull(((DeploymentEntityManagerImpl) deploymentEntityManager).getDeploymentDataManager());
    assertNull(((EventLogEntryEntityManagerImpl) eventLogEntryEntityManager).getEventLogEntryDataManager());
    assertNull(((EventSubscriptionEntityManagerImpl) eventSubscriptionEntityManager).getEventSubscriptionDataManager());
    assertNull(((ExecutionEntityManagerImpl) executionEntityManager).getExecutionDataManager());
    assertNull(((HistoricActivityInstanceEntityManagerImpl) historicActivityInstanceEntityManager)
        .getHistoricActivityInstanceDataManager());
    assertNull(((HistoricDetailEntityManagerImpl) historicDetailEntityManager).getHistoricDetailDataManager());
    assertNull(((HistoricIdentityLinkEntityManagerImpl) historicIdentityLinkEntityManager)
        .getHistoricIdentityLinkDataManager());
    assertNull(((HistoricProcessInstanceEntityManagerImpl) historicProcessInstanceEntityManager)
        .getHistoricProcessInstanceDataManager());
    assertNull(((HistoricTaskInstanceEntityManagerImpl) historicTaskInstanceEntityManager)
        .getHistoricTaskInstanceDataManager());
    assertNull(((HistoricVariableInstanceEntityManagerImpl) historicVariableInstanceEntityManager)
        .getHistoricVariableInstanceDataManager());
    assertNull(((IdentityLinkEntityManagerImpl) identityLinkEntityManager).getIdentityLinkDataManager());
    assertNull(((JobEntityManagerImpl) jobEntityManager).getJobDataManager());
    assertNull(((ModelEntityManagerImpl) modelEntityManager).getModelDataManager());
    assertNull(((ProcessDefinitionEntityManagerImpl) processDefinitionEntityManager).getProcessDefinitionDataManager());
    assertNull(((ResourceEntityManagerImpl) resourceEntityManager).getResourceDataManager());
    assertNull(((TaskEntityManagerImpl) taskEntityManager).getTaskDataManager());
    assertNull(((VariableInstanceEntityManagerImpl) variableInstanceEntityManager).getVariableInstanceDataManager());
    assertSame(deadLetterJobEntityManager, deadLetterJobEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers20() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ModelEntityManagerImpl modelEntityManager = new ModelEntityManagerImpl(processEngineConfiguration,
        new MybatisModelDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setModelEntityManager(modelEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(modelEntityManager, jtaProcessEngineConfiguration.getModelEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers21() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager = new ProcessDefinitionEntityManagerImpl(
        processEngineConfiguration, new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setProcessDefinitionEntityManager(processDefinitionEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(processDefinitionEntityManager, jtaProcessEngineConfiguration.getProcessDefinitionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers22() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManager = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfiguration, new MybatisProcessDefinitionInfoDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setProcessDefinitionInfoEntityManager(processDefinitionInfoEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    AttachmentEntityManager attachmentEntityManager = jtaProcessEngineConfiguration.getAttachmentEntityManager();
    assertTrue(attachmentEntityManager instanceof AttachmentEntityManagerImpl);
    ByteArrayEntityManager byteArrayEntityManager = jtaProcessEngineConfiguration.getByteArrayEntityManager();
    assertTrue(byteArrayEntityManager instanceof ByteArrayEntityManagerImpl);
    CommentEntityManager commentEntityManager = jtaProcessEngineConfiguration.getCommentEntityManager();
    assertTrue(commentEntityManager instanceof CommentEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    DeploymentEntityManager deploymentEntityManager = jtaProcessEngineConfiguration.getDeploymentEntityManager();
    assertTrue(deploymentEntityManager instanceof DeploymentEntityManagerImpl);
    EventLogEntryEntityManager eventLogEntryEntityManager = jtaProcessEngineConfiguration
        .getEventLogEntryEntityManager();
    assertTrue(eventLogEntryEntityManager instanceof EventLogEntryEntityManagerImpl);
    EventSubscriptionEntityManager eventSubscriptionEntityManager = jtaProcessEngineConfiguration
        .getEventSubscriptionEntityManager();
    assertTrue(eventSubscriptionEntityManager instanceof EventSubscriptionEntityManagerImpl);
    ExecutionEntityManager executionEntityManager = jtaProcessEngineConfiguration.getExecutionEntityManager();
    assertTrue(executionEntityManager instanceof ExecutionEntityManagerImpl);
    HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceEntityManager();
    assertTrue(historicActivityInstanceEntityManager instanceof HistoricActivityInstanceEntityManagerImpl);
    HistoricDetailEntityManager historicDetailEntityManager = jtaProcessEngineConfiguration
        .getHistoricDetailEntityManager();
    assertTrue(historicDetailEntityManager instanceof HistoricDetailEntityManagerImpl);
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkEntityManager();
    assertTrue(historicIdentityLinkEntityManager instanceof HistoricIdentityLinkEntityManagerImpl);
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceEntityManager();
    assertTrue(historicProcessInstanceEntityManager instanceof HistoricProcessInstanceEntityManagerImpl);
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceEntityManager();
    assertTrue(historicTaskInstanceEntityManager instanceof HistoricTaskInstanceEntityManagerImpl);
    HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceEntityManager();
    assertTrue(historicVariableInstanceEntityManager instanceof HistoricVariableInstanceEntityManagerImpl);
    IdentityLinkEntityManager identityLinkEntityManager = jtaProcessEngineConfiguration.getIdentityLinkEntityManager();
    assertTrue(identityLinkEntityManager instanceof IdentityLinkEntityManagerImpl);
    JobEntityManager jobEntityManager = jtaProcessEngineConfiguration.getJobEntityManager();
    assertTrue(jobEntityManager instanceof JobEntityManagerImpl);
    ModelEntityManager modelEntityManager = jtaProcessEngineConfiguration.getModelEntityManager();
    assertTrue(modelEntityManager instanceof ModelEntityManagerImpl);
    ProcessDefinitionEntityManager processDefinitionEntityManager = jtaProcessEngineConfiguration
        .getProcessDefinitionEntityManager();
    assertTrue(processDefinitionEntityManager instanceof ProcessDefinitionEntityManagerImpl);
    ProcessDefinitionInfoEntityManager processDefinitionInfoEntityManager2 = jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager();
    assertTrue(processDefinitionInfoEntityManager2 instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    ResourceEntityManager resourceEntityManager = jtaProcessEngineConfiguration.getResourceEntityManager();
    assertTrue(resourceEntityManager instanceof ResourceEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    TaskEntityManager taskEntityManager = jtaProcessEngineConfiguration.getTaskEntityManager();
    assertTrue(taskEntityManager instanceof TaskEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    VariableInstanceEntityManager variableInstanceEntityManager = jtaProcessEngineConfiguration
        .getVariableInstanceEntityManager();
    assertTrue(variableInstanceEntityManager instanceof VariableInstanceEntityManagerImpl);
    assertNull(((AttachmentEntityManagerImpl) attachmentEntityManager).getAttachmentDataManager());
    assertNull(((ByteArrayEntityManagerImpl) byteArrayEntityManager).getByteArrayDataManager());
    assertNull(((CommentEntityManagerImpl) commentEntityManager).getCommentDataManager());
    assertNull(((DeploymentEntityManagerImpl) deploymentEntityManager).getDeploymentDataManager());
    assertNull(((EventLogEntryEntityManagerImpl) eventLogEntryEntityManager).getEventLogEntryDataManager());
    assertNull(((EventSubscriptionEntityManagerImpl) eventSubscriptionEntityManager).getEventSubscriptionDataManager());
    assertNull(((ExecutionEntityManagerImpl) executionEntityManager).getExecutionDataManager());
    assertNull(((HistoricActivityInstanceEntityManagerImpl) historicActivityInstanceEntityManager)
        .getHistoricActivityInstanceDataManager());
    assertNull(((HistoricDetailEntityManagerImpl) historicDetailEntityManager).getHistoricDetailDataManager());
    assertNull(((HistoricIdentityLinkEntityManagerImpl) historicIdentityLinkEntityManager)
        .getHistoricIdentityLinkDataManager());
    assertNull(((HistoricProcessInstanceEntityManagerImpl) historicProcessInstanceEntityManager)
        .getHistoricProcessInstanceDataManager());
    assertNull(((HistoricTaskInstanceEntityManagerImpl) historicTaskInstanceEntityManager)
        .getHistoricTaskInstanceDataManager());
    assertNull(((HistoricVariableInstanceEntityManagerImpl) historicVariableInstanceEntityManager)
        .getHistoricVariableInstanceDataManager());
    assertNull(((IdentityLinkEntityManagerImpl) identityLinkEntityManager).getIdentityLinkDataManager());
    assertNull(((JobEntityManagerImpl) jobEntityManager).getJobDataManager());
    assertNull(((ModelEntityManagerImpl) modelEntityManager).getModelDataManager());
    assertNull(((ProcessDefinitionEntityManagerImpl) processDefinitionEntityManager).getProcessDefinitionDataManager());
    assertNull(((ResourceEntityManagerImpl) resourceEntityManager).getResourceDataManager());
    assertNull(((TaskEntityManagerImpl) taskEntityManager).getTaskDataManager());
    assertNull(((VariableInstanceEntityManagerImpl) variableInstanceEntityManager).getVariableInstanceDataManager());
    assertSame(processDefinitionInfoEntityManager, processDefinitionInfoEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers23() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    PropertyEntityManagerImpl propertyEntityManager = new PropertyEntityManagerImpl(processEngineConfiguration,
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setPropertyEntityManager(propertyEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    AttachmentEntityManager attachmentEntityManager = jtaProcessEngineConfiguration.getAttachmentEntityManager();
    assertTrue(attachmentEntityManager instanceof AttachmentEntityManagerImpl);
    ByteArrayEntityManager byteArrayEntityManager = jtaProcessEngineConfiguration.getByteArrayEntityManager();
    assertTrue(byteArrayEntityManager instanceof ByteArrayEntityManagerImpl);
    CommentEntityManager commentEntityManager = jtaProcessEngineConfiguration.getCommentEntityManager();
    assertTrue(commentEntityManager instanceof CommentEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    DeploymentEntityManager deploymentEntityManager = jtaProcessEngineConfiguration.getDeploymentEntityManager();
    assertTrue(deploymentEntityManager instanceof DeploymentEntityManagerImpl);
    EventLogEntryEntityManager eventLogEntryEntityManager = jtaProcessEngineConfiguration
        .getEventLogEntryEntityManager();
    assertTrue(eventLogEntryEntityManager instanceof EventLogEntryEntityManagerImpl);
    EventSubscriptionEntityManager eventSubscriptionEntityManager = jtaProcessEngineConfiguration
        .getEventSubscriptionEntityManager();
    assertTrue(eventSubscriptionEntityManager instanceof EventSubscriptionEntityManagerImpl);
    ExecutionEntityManager executionEntityManager = jtaProcessEngineConfiguration.getExecutionEntityManager();
    assertTrue(executionEntityManager instanceof ExecutionEntityManagerImpl);
    HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceEntityManager();
    assertTrue(historicActivityInstanceEntityManager instanceof HistoricActivityInstanceEntityManagerImpl);
    HistoricDetailEntityManager historicDetailEntityManager = jtaProcessEngineConfiguration
        .getHistoricDetailEntityManager();
    assertTrue(historicDetailEntityManager instanceof HistoricDetailEntityManagerImpl);
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkEntityManager();
    assertTrue(historicIdentityLinkEntityManager instanceof HistoricIdentityLinkEntityManagerImpl);
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceEntityManager();
    assertTrue(historicProcessInstanceEntityManager instanceof HistoricProcessInstanceEntityManagerImpl);
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceEntityManager();
    assertTrue(historicTaskInstanceEntityManager instanceof HistoricTaskInstanceEntityManagerImpl);
    HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceEntityManager();
    assertTrue(historicVariableInstanceEntityManager instanceof HistoricVariableInstanceEntityManagerImpl);
    IdentityLinkEntityManager identityLinkEntityManager = jtaProcessEngineConfiguration.getIdentityLinkEntityManager();
    assertTrue(identityLinkEntityManager instanceof IdentityLinkEntityManagerImpl);
    JobEntityManager jobEntityManager = jtaProcessEngineConfiguration.getJobEntityManager();
    assertTrue(jobEntityManager instanceof JobEntityManagerImpl);
    ModelEntityManager modelEntityManager = jtaProcessEngineConfiguration.getModelEntityManager();
    assertTrue(modelEntityManager instanceof ModelEntityManagerImpl);
    ProcessDefinitionEntityManager processDefinitionEntityManager = jtaProcessEngineConfiguration
        .getProcessDefinitionEntityManager();
    assertTrue(processDefinitionEntityManager instanceof ProcessDefinitionEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    PropertyEntityManager propertyEntityManager2 = jtaProcessEngineConfiguration.getPropertyEntityManager();
    assertTrue(propertyEntityManager2 instanceof PropertyEntityManagerImpl);
    ResourceEntityManager resourceEntityManager = jtaProcessEngineConfiguration.getResourceEntityManager();
    assertTrue(resourceEntityManager instanceof ResourceEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    TaskEntityManager taskEntityManager = jtaProcessEngineConfiguration.getTaskEntityManager();
    assertTrue(taskEntityManager instanceof TaskEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    VariableInstanceEntityManager variableInstanceEntityManager = jtaProcessEngineConfiguration
        .getVariableInstanceEntityManager();
    assertTrue(variableInstanceEntityManager instanceof VariableInstanceEntityManagerImpl);
    assertNull(((AttachmentEntityManagerImpl) attachmentEntityManager).getAttachmentDataManager());
    assertNull(((ByteArrayEntityManagerImpl) byteArrayEntityManager).getByteArrayDataManager());
    assertNull(((CommentEntityManagerImpl) commentEntityManager).getCommentDataManager());
    assertNull(((DeploymentEntityManagerImpl) deploymentEntityManager).getDeploymentDataManager());
    assertNull(((EventLogEntryEntityManagerImpl) eventLogEntryEntityManager).getEventLogEntryDataManager());
    assertNull(((EventSubscriptionEntityManagerImpl) eventSubscriptionEntityManager).getEventSubscriptionDataManager());
    assertNull(((ExecutionEntityManagerImpl) executionEntityManager).getExecutionDataManager());
    assertNull(((HistoricActivityInstanceEntityManagerImpl) historicActivityInstanceEntityManager)
        .getHistoricActivityInstanceDataManager());
    assertNull(((HistoricDetailEntityManagerImpl) historicDetailEntityManager).getHistoricDetailDataManager());
    assertNull(((HistoricIdentityLinkEntityManagerImpl) historicIdentityLinkEntityManager)
        .getHistoricIdentityLinkDataManager());
    assertNull(((HistoricProcessInstanceEntityManagerImpl) historicProcessInstanceEntityManager)
        .getHistoricProcessInstanceDataManager());
    assertNull(((HistoricTaskInstanceEntityManagerImpl) historicTaskInstanceEntityManager)
        .getHistoricTaskInstanceDataManager());
    assertNull(((HistoricVariableInstanceEntityManagerImpl) historicVariableInstanceEntityManager)
        .getHistoricVariableInstanceDataManager());
    assertNull(((IdentityLinkEntityManagerImpl) identityLinkEntityManager).getIdentityLinkDataManager());
    assertNull(((JobEntityManagerImpl) jobEntityManager).getJobDataManager());
    assertNull(((ModelEntityManagerImpl) modelEntityManager).getModelDataManager());
    assertNull(((ProcessDefinitionEntityManagerImpl) processDefinitionEntityManager).getProcessDefinitionDataManager());
    assertNull(((ResourceEntityManagerImpl) resourceEntityManager).getResourceDataManager());
    assertNull(((TaskEntityManagerImpl) taskEntityManager).getTaskDataManager());
    assertNull(((VariableInstanceEntityManagerImpl) variableInstanceEntityManager).getVariableInstanceDataManager());
    assertSame(propertyEntityManager, propertyEntityManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers24() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager = new ResourceEntityManagerImpl(processEngineConfiguration,
        new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setResourceEntityManager(resourceEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(resourceEntityManager, jtaProcessEngineConfiguration.getResourceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers25() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TaskEntityManagerImpl taskEntityManager = new TaskEntityManagerImpl(processEngineConfiguration,
        new MybatisTaskDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setTaskEntityManager(taskEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(taskEntityManager, jtaProcessEngineConfiguration.getTaskEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers26() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManager = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    jtaProcessEngineConfiguration.setVariableInstanceEntityManager(variableInstanceEntityManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    assertSame(variableInstanceEntityManager, jtaProcessEngineConfiguration.getVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers27() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    TableDataManagerImpl tableDataManager = new TableDataManagerImpl(new JtaProcessEngineConfiguration());
    jtaProcessEngineConfiguration.setTableDataManager(tableDataManager);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    AttachmentEntityManager attachmentEntityManager = jtaProcessEngineConfiguration.getAttachmentEntityManager();
    assertTrue(attachmentEntityManager instanceof AttachmentEntityManagerImpl);
    ByteArrayEntityManager byteArrayEntityManager = jtaProcessEngineConfiguration.getByteArrayEntityManager();
    assertTrue(byteArrayEntityManager instanceof ByteArrayEntityManagerImpl);
    CommentEntityManager commentEntityManager = jtaProcessEngineConfiguration.getCommentEntityManager();
    assertTrue(commentEntityManager instanceof CommentEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    DeploymentEntityManager deploymentEntityManager = jtaProcessEngineConfiguration.getDeploymentEntityManager();
    assertTrue(deploymentEntityManager instanceof DeploymentEntityManagerImpl);
    EventLogEntryEntityManager eventLogEntryEntityManager = jtaProcessEngineConfiguration
        .getEventLogEntryEntityManager();
    assertTrue(eventLogEntryEntityManager instanceof EventLogEntryEntityManagerImpl);
    EventSubscriptionEntityManager eventSubscriptionEntityManager = jtaProcessEngineConfiguration
        .getEventSubscriptionEntityManager();
    assertTrue(eventSubscriptionEntityManager instanceof EventSubscriptionEntityManagerImpl);
    ExecutionEntityManager executionEntityManager = jtaProcessEngineConfiguration.getExecutionEntityManager();
    assertTrue(executionEntityManager instanceof ExecutionEntityManagerImpl);
    HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceEntityManager();
    assertTrue(historicActivityInstanceEntityManager instanceof HistoricActivityInstanceEntityManagerImpl);
    HistoricDetailEntityManager historicDetailEntityManager = jtaProcessEngineConfiguration
        .getHistoricDetailEntityManager();
    assertTrue(historicDetailEntityManager instanceof HistoricDetailEntityManagerImpl);
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkEntityManager();
    assertTrue(historicIdentityLinkEntityManager instanceof HistoricIdentityLinkEntityManagerImpl);
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceEntityManager();
    assertTrue(historicProcessInstanceEntityManager instanceof HistoricProcessInstanceEntityManagerImpl);
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceEntityManager();
    assertTrue(historicTaskInstanceEntityManager instanceof HistoricTaskInstanceEntityManagerImpl);
    HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceEntityManager();
    assertTrue(historicVariableInstanceEntityManager instanceof HistoricVariableInstanceEntityManagerImpl);
    IdentityLinkEntityManager identityLinkEntityManager = jtaProcessEngineConfiguration.getIdentityLinkEntityManager();
    assertTrue(identityLinkEntityManager instanceof IdentityLinkEntityManagerImpl);
    JobEntityManager jobEntityManager = jtaProcessEngineConfiguration.getJobEntityManager();
    assertTrue(jobEntityManager instanceof JobEntityManagerImpl);
    ModelEntityManager modelEntityManager = jtaProcessEngineConfiguration.getModelEntityManager();
    assertTrue(modelEntityManager instanceof ModelEntityManagerImpl);
    ProcessDefinitionEntityManager processDefinitionEntityManager = jtaProcessEngineConfiguration
        .getProcessDefinitionEntityManager();
    assertTrue(processDefinitionEntityManager instanceof ProcessDefinitionEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    ResourceEntityManager resourceEntityManager = jtaProcessEngineConfiguration.getResourceEntityManager();
    assertTrue(resourceEntityManager instanceof ResourceEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    TableDataManager tableDataManager2 = jtaProcessEngineConfiguration.getTableDataManager();
    assertTrue(tableDataManager2 instanceof TableDataManagerImpl);
    TaskEntityManager taskEntityManager = jtaProcessEngineConfiguration.getTaskEntityManager();
    assertTrue(taskEntityManager instanceof TaskEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    VariableInstanceEntityManager variableInstanceEntityManager = jtaProcessEngineConfiguration
        .getVariableInstanceEntityManager();
    assertTrue(variableInstanceEntityManager instanceof VariableInstanceEntityManagerImpl);
    assertNull(((AttachmentEntityManagerImpl) attachmentEntityManager).getAttachmentDataManager());
    assertNull(((ByteArrayEntityManagerImpl) byteArrayEntityManager).getByteArrayDataManager());
    assertNull(((CommentEntityManagerImpl) commentEntityManager).getCommentDataManager());
    assertNull(((DeploymentEntityManagerImpl) deploymentEntityManager).getDeploymentDataManager());
    assertNull(((EventLogEntryEntityManagerImpl) eventLogEntryEntityManager).getEventLogEntryDataManager());
    assertNull(((EventSubscriptionEntityManagerImpl) eventSubscriptionEntityManager).getEventSubscriptionDataManager());
    assertNull(((ExecutionEntityManagerImpl) executionEntityManager).getExecutionDataManager());
    assertNull(((HistoricActivityInstanceEntityManagerImpl) historicActivityInstanceEntityManager)
        .getHistoricActivityInstanceDataManager());
    assertNull(((HistoricDetailEntityManagerImpl) historicDetailEntityManager).getHistoricDetailDataManager());
    assertNull(((HistoricIdentityLinkEntityManagerImpl) historicIdentityLinkEntityManager)
        .getHistoricIdentityLinkDataManager());
    assertNull(((HistoricProcessInstanceEntityManagerImpl) historicProcessInstanceEntityManager)
        .getHistoricProcessInstanceDataManager());
    assertNull(((HistoricTaskInstanceEntityManagerImpl) historicTaskInstanceEntityManager)
        .getHistoricTaskInstanceDataManager());
    assertNull(((HistoricVariableInstanceEntityManagerImpl) historicVariableInstanceEntityManager)
        .getHistoricVariableInstanceDataManager());
    assertNull(((IdentityLinkEntityManagerImpl) identityLinkEntityManager).getIdentityLinkDataManager());
    assertNull(((JobEntityManagerImpl) jobEntityManager).getJobDataManager());
    assertNull(((ModelEntityManagerImpl) modelEntityManager).getModelDataManager());
    assertNull(((ProcessDefinitionEntityManagerImpl) processDefinitionEntityManager).getProcessDefinitionDataManager());
    assertNull(((ResourceEntityManagerImpl) resourceEntityManager).getResourceDataManager());
    assertNull(((TaskEntityManagerImpl) taskEntityManager).getTaskDataManager());
    assertNull(((VariableInstanceEntityManagerImpl) variableInstanceEntityManager).getVariableInstanceDataManager());
    assertSame(tableDataManager, tableDataManager2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEntityManagers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEntityManagers()}
   */
  @Test
  public void testInitEntityManagers_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initEntityManagers();

    // Assert
    AttachmentEntityManager attachmentEntityManager = jtaProcessEngineConfiguration.getAttachmentEntityManager();
    assertTrue(attachmentEntityManager instanceof AttachmentEntityManagerImpl);
    ByteArrayEntityManager byteArrayEntityManager = jtaProcessEngineConfiguration.getByteArrayEntityManager();
    assertTrue(byteArrayEntityManager instanceof ByteArrayEntityManagerImpl);
    CommentEntityManager commentEntityManager = jtaProcessEngineConfiguration.getCommentEntityManager();
    assertTrue(commentEntityManager instanceof CommentEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager() instanceof DeadLetterJobEntityManagerImpl);
    DeploymentEntityManager deploymentEntityManager = jtaProcessEngineConfiguration.getDeploymentEntityManager();
    assertTrue(deploymentEntityManager instanceof DeploymentEntityManagerImpl);
    EventLogEntryEntityManager eventLogEntryEntityManager = jtaProcessEngineConfiguration
        .getEventLogEntryEntityManager();
    assertTrue(eventLogEntryEntityManager instanceof EventLogEntryEntityManagerImpl);
    EventSubscriptionEntityManager eventSubscriptionEntityManager = jtaProcessEngineConfiguration
        .getEventSubscriptionEntityManager();
    assertTrue(eventSubscriptionEntityManager instanceof EventSubscriptionEntityManagerImpl);
    ExecutionEntityManager executionEntityManager = jtaProcessEngineConfiguration.getExecutionEntityManager();
    assertTrue(executionEntityManager instanceof ExecutionEntityManagerImpl);
    HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricActivityInstanceEntityManager();
    assertTrue(historicActivityInstanceEntityManager instanceof HistoricActivityInstanceEntityManagerImpl);
    HistoricDetailEntityManager historicDetailEntityManager = jtaProcessEngineConfiguration
        .getHistoricDetailEntityManager();
    assertTrue(historicDetailEntityManager instanceof HistoricDetailEntityManagerImpl);
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = jtaProcessEngineConfiguration
        .getHistoricIdentityLinkEntityManager();
    assertTrue(historicIdentityLinkEntityManager instanceof HistoricIdentityLinkEntityManagerImpl);
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricProcessInstanceEntityManager();
    assertTrue(historicProcessInstanceEntityManager instanceof HistoricProcessInstanceEntityManagerImpl);
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricTaskInstanceEntityManager();
    assertTrue(historicTaskInstanceEntityManager instanceof HistoricTaskInstanceEntityManagerImpl);
    HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = jtaProcessEngineConfiguration
        .getHistoricVariableInstanceEntityManager();
    assertTrue(historicVariableInstanceEntityManager instanceof HistoricVariableInstanceEntityManagerImpl);
    IdentityLinkEntityManager identityLinkEntityManager = jtaProcessEngineConfiguration.getIdentityLinkEntityManager();
    assertTrue(identityLinkEntityManager instanceof IdentityLinkEntityManagerImpl);
    JobEntityManager jobEntityManager = jtaProcessEngineConfiguration.getJobEntityManager();
    assertTrue(jobEntityManager instanceof JobEntityManagerImpl);
    ModelEntityManager modelEntityManager = jtaProcessEngineConfiguration.getModelEntityManager();
    assertTrue(modelEntityManager instanceof ModelEntityManagerImpl);
    ProcessDefinitionEntityManager processDefinitionEntityManager = jtaProcessEngineConfiguration
        .getProcessDefinitionEntityManager();
    assertTrue(processDefinitionEntityManager instanceof ProcessDefinitionEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration
        .getProcessDefinitionInfoEntityManager() instanceof ProcessDefinitionInfoEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getPropertyEntityManager() instanceof PropertyEntityManagerImpl);
    ResourceEntityManager resourceEntityManager = jtaProcessEngineConfiguration.getResourceEntityManager();
    assertTrue(resourceEntityManager instanceof ResourceEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getSuspendedJobEntityManager() instanceof SuspendedJobEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTableDataManager() instanceof TableDataManagerImpl);
    TaskEntityManager taskEntityManager = jtaProcessEngineConfiguration.getTaskEntityManager();
    assertTrue(taskEntityManager instanceof TaskEntityManagerImpl);
    assertTrue(jtaProcessEngineConfiguration.getTimerJobEntityManager() instanceof TimerJobEntityManagerImpl);
    VariableInstanceEntityManager variableInstanceEntityManager = jtaProcessEngineConfiguration
        .getVariableInstanceEntityManager();
    assertTrue(variableInstanceEntityManager instanceof VariableInstanceEntityManagerImpl);
    assertNull(((AttachmentEntityManagerImpl) attachmentEntityManager).getAttachmentDataManager());
    assertNull(((ByteArrayEntityManagerImpl) byteArrayEntityManager).getByteArrayDataManager());
    assertNull(((CommentEntityManagerImpl) commentEntityManager).getCommentDataManager());
    assertNull(((DeploymentEntityManagerImpl) deploymentEntityManager).getDeploymentDataManager());
    assertNull(((EventLogEntryEntityManagerImpl) eventLogEntryEntityManager).getEventLogEntryDataManager());
    assertNull(((EventSubscriptionEntityManagerImpl) eventSubscriptionEntityManager).getEventSubscriptionDataManager());
    assertNull(((ExecutionEntityManagerImpl) executionEntityManager).getExecutionDataManager());
    assertNull(((HistoricActivityInstanceEntityManagerImpl) historicActivityInstanceEntityManager)
        .getHistoricActivityInstanceDataManager());
    assertNull(((HistoricDetailEntityManagerImpl) historicDetailEntityManager).getHistoricDetailDataManager());
    assertNull(((HistoricIdentityLinkEntityManagerImpl) historicIdentityLinkEntityManager)
        .getHistoricIdentityLinkDataManager());
    assertNull(((HistoricProcessInstanceEntityManagerImpl) historicProcessInstanceEntityManager)
        .getHistoricProcessInstanceDataManager());
    assertNull(((HistoricTaskInstanceEntityManagerImpl) historicTaskInstanceEntityManager)
        .getHistoricTaskInstanceDataManager());
    assertNull(((HistoricVariableInstanceEntityManagerImpl) historicVariableInstanceEntityManager)
        .getHistoricVariableInstanceDataManager());
    assertNull(((IdentityLinkEntityManagerImpl) identityLinkEntityManager).getIdentityLinkDataManager());
    assertNull(((JobEntityManagerImpl) jobEntityManager).getJobDataManager());
    assertNull(((ModelEntityManagerImpl) modelEntityManager).getModelDataManager());
    assertNull(((ProcessDefinitionEntityManagerImpl) processDefinitionEntityManager).getProcessDefinitionDataManager());
    assertNull(((ResourceEntityManagerImpl) resourceEntityManager).getResourceDataManager());
    assertNull(((TaskEntityManagerImpl) taskEntityManager).getTaskDataManager());
    assertNull(((VariableInstanceEntityManagerImpl) variableInstanceEntityManager).getVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initHistoryManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initHistoryManager()}
   */
  @Test
  public void testInitHistoryManager() {
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
   * Test {@link ProcessEngineConfigurationImpl#initHistoryManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initHistoryManager()}
   */
  @Test
  public void testInitHistoryManager2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initHistoryManager();

    // Assert
    HistoryManager historyManager = jtaProcessEngineConfiguration.getHistoryManager();
    assertTrue(historyManager instanceof DefaultHistoryManager);
    assertNull(((DefaultHistoryManager) historyManager).getHistoryLevel());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initHistoryManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initHistoryManager()}
   */
  @Test
  public void testInitHistoryManager_givenJtaProcessEngineConfiguration() {
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
   * Test {@link ProcessEngineConfigurationImpl#initJobManager()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initJobManager()}
   */
  @Test
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
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initJobManager()}
   */
  @Test
  public void testInitJobManager2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initJobManager();

    // Assert
    JobManager jobManager = jtaProcessEngineConfiguration.getJobManager();
    assertTrue(jobManager instanceof DefaultJobManager);
    assertSame(jtaProcessEngineConfiguration, ((DefaultJobManager) jobManager).getProcessEngineConfiguration());
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#createDbSqlSessionFactory()}
   */
  @Test
  public void testCreateDbSqlSessionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    DbSqlSessionFactory actualCreateDbSqlSessionFactoryResult = jtaProcessEngineConfiguration
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
   * Test {@link ProcessEngineConfigurationImpl#createDbSqlSessionFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#createDbSqlSessionFactory()}
   */
  @Test
  public void testCreateDbSqlSessionFactory_givenJtaProcessEngineConfiguration() {
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
  public void testInitConfigurators2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testInitConfigurators3() {
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
    List<ProcessEngineConfigurator> configurators = jtaProcessEngineConfiguration.getConfigurators();
    assertEquals(3, configurators.size());
    assertSame(configurator, allConfigurators.get(2));
    assertSame(configurator, configurators.get(2));
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
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * AllConfigurators size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
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
    assertEquals(allConfigurators, jtaProcessEngineConfiguration.getConfigurators());
    assertSame(configurator, allConfigurators.get(0));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * AllConfigurators size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
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
    List<ProcessEngineConfigurator> configurators = jtaProcessEngineConfiguration.getConfigurators();
    assertEquals(2, configurators.size());
    assertSame(configurator, allConfigurators.get(1));
    assertSame(configurator, configurators.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}
   */
  @Test
  public void testInitProcessDefinitionCache3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initProcessDefinitionCache()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initProcessDefinitionInfoCache()}
   */
  @Test
  public void testInitProcessDefinitionInfoCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initProcessDefinitionInfoCache();

    // Assert
    assertEquals(0, jtaProcessEngineConfiguration.processDefinitionInfoCache.size());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initProcessDefinitionInfoCache()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initProcessDefinitionInfoCache()}
   */
  @Test
  public void testInitProcessDefinitionInfoCache_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}
   */
  @Test
  public void testInitKnowledgeBaseCache3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initKnowledgeBaseCache();

    // Assert
    DeploymentCache<Object> knowledgeBaseCache = jtaProcessEngineConfiguration.getKnowledgeBaseCache();
    assertTrue(knowledgeBaseCache instanceof DefaultDeploymentCache);
    assertEquals(0, ((DefaultDeploymentCache<Object>) knowledgeBaseCache).size());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initKnowledgeBaseCache()}
   */
  @Test
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
  public void testInitDeployers4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testInitDeployers5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreDeployers(new ArrayList<>());
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testInitDeployers6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostDeployers(new ArrayList<>());
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testInitDeployers7() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeploymentManager(new DeploymentManager());
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDeployers();

    // Assert
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
   *   <li>Given {@link BpmnDeploymentHelper} (default constructor) TimerManager is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
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
   *   <li>Given {@link BpmnDeploymentHelper} (default constructor) TimerManager is
   * {@link TimerManager} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * BpmnDeployer is {@link BpmnDeployer} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  public void testInitDeployers_givenJtaProcessEngineConfigurationBpmnDeployerIsBpmnDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setBpmnDeployer(new BpmnDeployer());
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * TimerManager is {@link TimerManager} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
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
   *   <li>Given {@link ParsedDeploymentBuilderFactory} (default constructor)
   * BpmnParser is {@link BpmnParser} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
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
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * Deployers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initDeployers()}
   */
  @Test
  public void testInitDeployers_thenJtaProcessEngineConfigurationDeployersEmpty() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ArrayList<Deployer> deployers = new ArrayList<>();
    jtaProcessEngineConfiguration.setDeployers(deployers);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initBpmnDeployerDependencies()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initBpmnDeployerDependencies()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  public void testGetDefaultDeployers6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    Collection<? extends Deployer> actualDefaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();

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
   *   <li>Given {@link BpmnDeploymentHelper} (default constructor) TimerManager is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
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
   *   <li>Given {@link BpmnDeploymentHelper} (default constructor) TimerManager is
   * {@link TimerManager} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  public void testGetDefaultDeployers_thenReturnFirstIsBpmnDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    jtaProcessEngineConfiguration.setBpmnDeployer(bpmnDeployer);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initListenerFactory()}
   */
  @Test
  public void testInitListenerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultListenerFactory listenerFactory = new DefaultListenerFactory();
    jtaProcessEngineConfiguration.setListenerFactory(listenerFactory);

    // Act
    jtaProcessEngineConfiguration.initListenerFactory();

    // Assert
    assertSame(listenerFactory, jtaProcessEngineConfiguration.getListenerFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initListenerFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initListenerFactory()}
   */
  @Test
  public void testInitListenerFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initListenerFactory()}
   */
  @Test
  public void testInitListenerFactory_givenJtaProcessEngineConfiguration() {
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
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  public void testInitBehaviorFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();
    jtaProcessEngineConfiguration.setActivityBehaviorFactory(activityBehaviorFactory);

    // Act
    jtaProcessEngineConfiguration.initBehaviorFactory();

    // Assert
    assertSame(activityBehaviorFactory, jtaProcessEngineConfiguration.getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  public void testInitBehaviorFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  public void testInitBehaviorFactory_givenJtaProcessEngineConfiguration() {
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
   * Test {@link ProcessEngineConfigurationImpl#initBpmnParser()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBpmnParser()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  public void testGetDefaultBpmnParseHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomDefaultBpmnParseHandlers(new ArrayList<>());

    // Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers = jtaProcessEngineConfiguration.getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = actualDefaultBpmnParseHandlers.get(25);
    assertTrue(getResult instanceof AdhocSubProcessParseHandler);
    BpmnParseHandler getResult2 = actualDefaultBpmnParseHandlers.get(0);
    assertTrue(getResult2 instanceof BoundaryEventParseHandler);
    BpmnParseHandler getResult3 = actualDefaultBpmnParseHandlers.get(1);
    assertTrue(getResult3 instanceof BusinessRuleParseHandler);
    BpmnParseHandler getResult4 = actualDefaultBpmnParseHandlers.get(2);
    assertTrue(getResult4 instanceof CallActivityParseHandler);
    BpmnParseHandler getResult5 = actualDefaultBpmnParseHandlers.get(3);
    assertTrue(getResult5 instanceof CancelEventDefinitionParseHandler);
    BpmnParseHandler getResult6 = actualDefaultBpmnParseHandlers.get(4);
    assertTrue(getResult6 instanceof CompensateEventDefinitionParseHandler);
    BpmnParseHandler getResult7 = actualDefaultBpmnParseHandlers.get(5);
    assertTrue(getResult7 instanceof EndEventParseHandler);
    BpmnParseHandler getResult8 = actualDefaultBpmnParseHandlers.get(Float.PRECISION);
    assertTrue(getResult8 instanceof EventSubProcessParseHandler);
    BpmnParseHandler getResult9 = actualDefaultBpmnParseHandlers.get(26);
    assertTrue(getResult9 instanceof TaskParseHandler);
    BpmnParseHandler getResult10 = actualDefaultBpmnParseHandlers.get(27);
    assertTrue(getResult10 instanceof TimerEventDefinitionParseHandler);
    BpmnParseHandler getResult11 = actualDefaultBpmnParseHandlers.get(28);
    assertTrue(getResult11 instanceof TransactionParseHandler);
    BpmnParseHandler getResult12 = actualDefaultBpmnParseHandlers.get(29);
    assertTrue(getResult12 instanceof UserTaskParseHandler);
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult9.getHandledTypes().size());
    assertEquals(1, getResult10.getHandledTypes().size());
    assertEquals(1, getResult11.getHandledTypes().size());
    assertEquals(1, getResult12.getHandledTypes().size());
    assertEquals(1, getResult5.getHandledTypes().size());
    assertEquals(1, getResult6.getHandledTypes().size());
    assertEquals(1, getResult7.getHandledTypes().size());
    assertEquals(1, getResult8.getHandledTypes().size());
    Class<BoundaryEvent> expectedHandledType = BoundaryEvent.class;
    assertEquals(expectedHandledType, ((BoundaryEventParseHandler) getResult2).getHandledType());
    Class<BusinessRuleTask> expectedHandledType2 = BusinessRuleTask.class;
    assertEquals(expectedHandledType2, ((BusinessRuleParseHandler) getResult3).getHandledType());
    Class<CallActivity> expectedHandledType3 = CallActivity.class;
    assertEquals(expectedHandledType3, ((CallActivityParseHandler) getResult4).getHandledType());
    Class<CancelEventDefinition> expectedHandledType4 = CancelEventDefinition.class;
    assertEquals(expectedHandledType4, ((CancelEventDefinitionParseHandler) getResult5).getHandledType());
    Class<CompensateEventDefinition> expectedHandledType5 = CompensateEventDefinition.class;
    assertEquals(expectedHandledType5, ((CompensateEventDefinitionParseHandler) getResult6).getHandledType());
    Class<EndEvent> expectedHandledType6 = EndEvent.class;
    assertEquals(expectedHandledType6, ((EndEventParseHandler) getResult7).getHandledType());
    Class<Task> expectedHandledType7 = Task.class;
    assertEquals(expectedHandledType7, ((TaskParseHandler) getResult9).getHandledType());
    Class<TimerEventDefinition> expectedHandledType8 = TimerEventDefinition.class;
    assertEquals(expectedHandledType8, ((TimerEventDefinitionParseHandler) getResult10).getHandledType());
    Class<Transaction> expectedHandledType9 = Transaction.class;
    assertEquals(expectedHandledType9, ((TransactionParseHandler) getResult11).getHandledType());
    Class<UserTask> expectedHandledType10 = UserTask.class;
    assertEquals(expectedHandledType10, ((UserTaskParseHandler) getResult12).getHandledType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  public void testGetDefaultBpmnParseHandlers2() {
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
    BpmnParseHandler getResult2 = actualDefaultBpmnParseHandlers.get(0);
    assertTrue(getResult2 instanceof BoundaryEventParseHandler);
    BpmnParseHandler getResult3 = actualDefaultBpmnParseHandlers.get(1);
    assertTrue(getResult3 instanceof BusinessRuleParseHandler);
    BpmnParseHandler getResult4 = actualDefaultBpmnParseHandlers.get(2);
    assertTrue(getResult4 instanceof CallActivityParseHandler);
    BpmnParseHandler getResult5 = actualDefaultBpmnParseHandlers.get(3);
    assertTrue(getResult5 instanceof CancelEventDefinitionParseHandler);
    BpmnParseHandler getResult6 = actualDefaultBpmnParseHandlers.get(4);
    assertTrue(getResult6 instanceof CompensateEventDefinitionParseHandler);
    BpmnParseHandler getResult7 = actualDefaultBpmnParseHandlers.get(5);
    assertTrue(getResult7 instanceof EndEventParseHandler);
    BpmnParseHandler getResult8 = actualDefaultBpmnParseHandlers.get(Float.PRECISION);
    assertTrue(getResult8 instanceof EventSubProcessParseHandler);
    BpmnParseHandler getResult9 = actualDefaultBpmnParseHandlers.get(26);
    assertTrue(getResult9 instanceof TaskParseHandler);
    BpmnParseHandler getResult10 = actualDefaultBpmnParseHandlers.get(27);
    assertTrue(getResult10 instanceof TimerEventDefinitionParseHandler);
    BpmnParseHandler getResult11 = actualDefaultBpmnParseHandlers.get(28);
    assertTrue(getResult11 instanceof TransactionParseHandler);
    BpmnParseHandler getResult12 = actualDefaultBpmnParseHandlers.get(29);
    assertTrue(getResult12 instanceof UserTaskParseHandler);
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult9.getHandledTypes().size());
    assertEquals(1, getResult10.getHandledTypes().size());
    assertEquals(1, getResult11.getHandledTypes().size());
    assertEquals(1, getResult12.getHandledTypes().size());
    assertEquals(1, getResult5.getHandledTypes().size());
    assertEquals(1, getResult6.getHandledTypes().size());
    assertEquals(1, getResult7.getHandledTypes().size());
    assertEquals(1, getResult8.getHandledTypes().size());
    Class<BoundaryEvent> expectedHandledType = BoundaryEvent.class;
    assertEquals(expectedHandledType, ((BoundaryEventParseHandler) getResult2).getHandledType());
    Class<BusinessRuleTask> expectedHandledType2 = BusinessRuleTask.class;
    assertEquals(expectedHandledType2, ((BusinessRuleParseHandler) getResult3).getHandledType());
    Class<CallActivity> expectedHandledType3 = CallActivity.class;
    assertEquals(expectedHandledType3, ((CallActivityParseHandler) getResult4).getHandledType());
    Class<CancelEventDefinition> expectedHandledType4 = CancelEventDefinition.class;
    assertEquals(expectedHandledType4, ((CancelEventDefinitionParseHandler) getResult5).getHandledType());
    Class<CompensateEventDefinition> expectedHandledType5 = CompensateEventDefinition.class;
    assertEquals(expectedHandledType5, ((CompensateEventDefinitionParseHandler) getResult6).getHandledType());
    Class<EndEvent> expectedHandledType6 = EndEvent.class;
    assertEquals(expectedHandledType6, ((EndEventParseHandler) getResult7).getHandledType());
    Class<Task> expectedHandledType7 = Task.class;
    assertEquals(expectedHandledType7, ((TaskParseHandler) getResult9).getHandledType());
    Class<TimerEventDefinition> expectedHandledType8 = TimerEventDefinition.class;
    assertEquals(expectedHandledType8, ((TimerEventDefinitionParseHandler) getResult10).getHandledType());
    Class<Transaction> expectedHandledType9 = Transaction.class;
    assertEquals(expectedHandledType9, ((TransactionParseHandler) getResult11).getHandledType());
    Class<UserTask> expectedHandledType10 = UserTask.class;
    assertEquals(expectedHandledType10, ((UserTaskParseHandler) getResult12).getHandledType());
    assertSame(adhocSubProcessParseHandler, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  public void testGetDefaultBpmnParseHandlers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers = jtaProcessEngineConfiguration.getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = actualDefaultBpmnParseHandlers.get(25);
    assertTrue(getResult instanceof AdhocSubProcessParseHandler);
    BpmnParseHandler getResult2 = actualDefaultBpmnParseHandlers.get(0);
    assertTrue(getResult2 instanceof BoundaryEventParseHandler);
    BpmnParseHandler getResult3 = actualDefaultBpmnParseHandlers.get(1);
    assertTrue(getResult3 instanceof BusinessRuleParseHandler);
    BpmnParseHandler getResult4 = actualDefaultBpmnParseHandlers.get(2);
    assertTrue(getResult4 instanceof CallActivityParseHandler);
    BpmnParseHandler getResult5 = actualDefaultBpmnParseHandlers.get(3);
    assertTrue(getResult5 instanceof CancelEventDefinitionParseHandler);
    BpmnParseHandler getResult6 = actualDefaultBpmnParseHandlers.get(4);
    assertTrue(getResult6 instanceof CompensateEventDefinitionParseHandler);
    BpmnParseHandler getResult7 = actualDefaultBpmnParseHandlers.get(5);
    assertTrue(getResult7 instanceof EndEventParseHandler);
    BpmnParseHandler getResult8 = actualDefaultBpmnParseHandlers.get(Float.PRECISION);
    assertTrue(getResult8 instanceof EventSubProcessParseHandler);
    BpmnParseHandler getResult9 = actualDefaultBpmnParseHandlers.get(26);
    assertTrue(getResult9 instanceof TaskParseHandler);
    BpmnParseHandler getResult10 = actualDefaultBpmnParseHandlers.get(27);
    assertTrue(getResult10 instanceof TimerEventDefinitionParseHandler);
    BpmnParseHandler getResult11 = actualDefaultBpmnParseHandlers.get(28);
    assertTrue(getResult11 instanceof TransactionParseHandler);
    BpmnParseHandler getResult12 = actualDefaultBpmnParseHandlers.get(29);
    assertTrue(getResult12 instanceof UserTaskParseHandler);
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult9.getHandledTypes().size());
    assertEquals(1, getResult10.getHandledTypes().size());
    assertEquals(1, getResult11.getHandledTypes().size());
    assertEquals(1, getResult12.getHandledTypes().size());
    assertEquals(1, getResult5.getHandledTypes().size());
    assertEquals(1, getResult6.getHandledTypes().size());
    assertEquals(1, getResult7.getHandledTypes().size());
    assertEquals(1, getResult8.getHandledTypes().size());
    Class<BoundaryEvent> expectedHandledType = BoundaryEvent.class;
    assertEquals(expectedHandledType, ((BoundaryEventParseHandler) getResult2).getHandledType());
    Class<BusinessRuleTask> expectedHandledType2 = BusinessRuleTask.class;
    assertEquals(expectedHandledType2, ((BusinessRuleParseHandler) getResult3).getHandledType());
    Class<CallActivity> expectedHandledType3 = CallActivity.class;
    assertEquals(expectedHandledType3, ((CallActivityParseHandler) getResult4).getHandledType());
    Class<CancelEventDefinition> expectedHandledType4 = CancelEventDefinition.class;
    assertEquals(expectedHandledType4, ((CancelEventDefinitionParseHandler) getResult5).getHandledType());
    Class<CompensateEventDefinition> expectedHandledType5 = CompensateEventDefinition.class;
    assertEquals(expectedHandledType5, ((CompensateEventDefinitionParseHandler) getResult6).getHandledType());
    Class<EndEvent> expectedHandledType6 = EndEvent.class;
    assertEquals(expectedHandledType6, ((EndEventParseHandler) getResult7).getHandledType());
    Class<Task> expectedHandledType7 = Task.class;
    assertEquals(expectedHandledType7, ((TaskParseHandler) getResult9).getHandledType());
    Class<TimerEventDefinition> expectedHandledType8 = TimerEventDefinition.class;
    assertEquals(expectedHandledType8, ((TimerEventDefinitionParseHandler) getResult10).getHandledType());
    Class<Transaction> expectedHandledType9 = Transaction.class;
    assertEquals(expectedHandledType9, ((TransactionParseHandler) getResult11).getHandledType());
    Class<UserTask> expectedHandledType10 = UserTask.class;
    assertEquals(expectedHandledType10, ((UserTaskParseHandler) getResult12).getHandledType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  public void testGetDefaultBpmnParseHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange and Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers = (new JtaProcessEngineConfiguration())
        .getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = actualDefaultBpmnParseHandlers.get(25);
    assertTrue(getResult instanceof AdhocSubProcessParseHandler);
    BpmnParseHandler getResult2 = actualDefaultBpmnParseHandlers.get(0);
    assertTrue(getResult2 instanceof BoundaryEventParseHandler);
    BpmnParseHandler getResult3 = actualDefaultBpmnParseHandlers.get(1);
    assertTrue(getResult3 instanceof BusinessRuleParseHandler);
    BpmnParseHandler getResult4 = actualDefaultBpmnParseHandlers.get(2);
    assertTrue(getResult4 instanceof CallActivityParseHandler);
    BpmnParseHandler getResult5 = actualDefaultBpmnParseHandlers.get(3);
    assertTrue(getResult5 instanceof CancelEventDefinitionParseHandler);
    BpmnParseHandler getResult6 = actualDefaultBpmnParseHandlers.get(4);
    assertTrue(getResult6 instanceof CompensateEventDefinitionParseHandler);
    BpmnParseHandler getResult7 = actualDefaultBpmnParseHandlers.get(5);
    assertTrue(getResult7 instanceof EndEventParseHandler);
    BpmnParseHandler getResult8 = actualDefaultBpmnParseHandlers.get(Float.PRECISION);
    assertTrue(getResult8 instanceof EventSubProcessParseHandler);
    BpmnParseHandler getResult9 = actualDefaultBpmnParseHandlers.get(26);
    assertTrue(getResult9 instanceof TaskParseHandler);
    BpmnParseHandler getResult10 = actualDefaultBpmnParseHandlers.get(27);
    assertTrue(getResult10 instanceof TimerEventDefinitionParseHandler);
    BpmnParseHandler getResult11 = actualDefaultBpmnParseHandlers.get(28);
    assertTrue(getResult11 instanceof TransactionParseHandler);
    BpmnParseHandler getResult12 = actualDefaultBpmnParseHandlers.get(29);
    assertTrue(getResult12 instanceof UserTaskParseHandler);
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult9.getHandledTypes().size());
    assertEquals(1, getResult10.getHandledTypes().size());
    assertEquals(1, getResult11.getHandledTypes().size());
    assertEquals(1, getResult12.getHandledTypes().size());
    assertEquals(1, getResult5.getHandledTypes().size());
    assertEquals(1, getResult6.getHandledTypes().size());
    assertEquals(1, getResult7.getHandledTypes().size());
    assertEquals(1, getResult8.getHandledTypes().size());
    Class<BoundaryEvent> expectedHandledType = BoundaryEvent.class;
    assertEquals(expectedHandledType, ((BoundaryEventParseHandler) getResult2).getHandledType());
    Class<BusinessRuleTask> expectedHandledType2 = BusinessRuleTask.class;
    assertEquals(expectedHandledType2, ((BusinessRuleParseHandler) getResult3).getHandledType());
    Class<CallActivity> expectedHandledType3 = CallActivity.class;
    assertEquals(expectedHandledType3, ((CallActivityParseHandler) getResult4).getHandledType());
    Class<CancelEventDefinition> expectedHandledType4 = CancelEventDefinition.class;
    assertEquals(expectedHandledType4, ((CancelEventDefinitionParseHandler) getResult5).getHandledType());
    Class<CompensateEventDefinition> expectedHandledType5 = CompensateEventDefinition.class;
    assertEquals(expectedHandledType5, ((CompensateEventDefinitionParseHandler) getResult6).getHandledType());
    Class<EndEvent> expectedHandledType6 = EndEvent.class;
    assertEquals(expectedHandledType6, ((EndEventParseHandler) getResult7).getHandledType());
    Class<Task> expectedHandledType7 = Task.class;
    assertEquals(expectedHandledType7, ((TaskParseHandler) getResult9).getHandledType());
    Class<TimerEventDefinition> expectedHandledType8 = TimerEventDefinition.class;
    assertEquals(expectedHandledType8, ((TimerEventDefinitionParseHandler) getResult10).getHandledType());
    Class<Transaction> expectedHandledType9 = Transaction.class;
    assertEquals(expectedHandledType9, ((TransactionParseHandler) getResult11).getHandledType());
    Class<UserTask> expectedHandledType10 = UserTask.class;
    assertEquals(expectedHandledType10, ((UserTaskParseHandler) getResult12).getHandledType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initClock()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initClock()}
   */
  @Test
  public void testInitClock() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initClock();

    // Assert
    Clock clock = jtaProcessEngineConfiguration.getClock();
    Calendar currentCalendar = clock.getCurrentCalendar();
    assertTrue(currentCalendar instanceof GregorianCalendar);
    assertTrue(clock instanceof DefaultClockImpl);
    assertEquals("gregory", currentCalendar.getCalendarType());
    assertEquals(2, currentCalendar.getFirstDayOfWeek());
    assertEquals(4, currentCalendar.getMinimalDaysInFirstWeek());
    assertTrue(currentCalendar.isLenient());
    assertTrue(currentCalendar.isWeekDateSupported());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initClock()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initClock()}
   */
  @Test
  public void testInitClock_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initClock();

    // Assert
    Clock clock = jtaProcessEngineConfiguration.getClock();
    Calendar currentCalendar = clock.getCurrentCalendar();
    assertTrue(currentCalendar instanceof GregorianCalendar);
    assertTrue(clock instanceof DefaultClockImpl);
    assertEquals("gregory", currentCalendar.getCalendarType());
    assertEquals(2, currentCalendar.getFirstDayOfWeek());
    assertEquals(4, currentCalendar.getMinimalDaysInFirstWeek());
    assertTrue(currentCalendar.isLenient());
    assertTrue(currentCalendar.isWeekDateSupported());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initClock()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) Clock is
   * {@link DefaultClockImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initClock()}
   */
  @Test
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
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initJobHandlers()}
   */
  @Test
  public void testInitJobHandlers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initJobHandlers()}
   */
  @Test
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
  public void testInitAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutor(null);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor = jtaProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertEquals("foo", asyncExecutor.getLockOwner());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecutorService());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecuteAsyncRunnableFactory());
    assertEquals(0, asyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, asyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, asyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, asyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(10, ((DefaultAsyncJobExecutor) asyncExecutor).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) asyncExecutor).getQueueSize());
    assertEquals(10000, asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, asyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((DefaultAsyncJobExecutor) asyncExecutor).getCorePoolSize());
    assertEquals(3, asyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, asyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, asyncExecutor.getTimerLockTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) asyncExecutor).getKeepAliveTime());
    assertEquals(60000, asyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) asyncExecutor).getSecondsToWaitOnShutdown());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) asyncExecutor).isMessageQueueMode());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  public void testInitAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    jtaProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner(null);
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertSame(asyncExecutor, jtaProcessEngineConfiguration.getAsyncExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  public void testInitAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor = jtaProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecutorService());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecuteAsyncRunnableFactory());
    assertEquals(0, asyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, asyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, asyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, asyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(10, ((DefaultAsyncJobExecutor) asyncExecutor).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) asyncExecutor).getQueueSize());
    assertEquals(10000, asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, asyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((DefaultAsyncJobExecutor) asyncExecutor).getCorePoolSize());
    assertEquals(3, asyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, asyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, asyncExecutor.getTimerLockTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) asyncExecutor).getKeepAliveTime());
    assertEquals(60000, asyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) asyncExecutor).getSecondsToWaitOnShutdown());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) asyncExecutor).isMessageQueueMode());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  public void testInitAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorMessageQueueMode(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor = jtaProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecutorService());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecuteAsyncRunnableFactory());
    assertEquals(0, asyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, asyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, asyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, asyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(10, ((DefaultAsyncJobExecutor) asyncExecutor).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) asyncExecutor).getQueueSize());
    assertEquals(10000, asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, asyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((DefaultAsyncJobExecutor) asyncExecutor).getCorePoolSize());
    assertEquals(3, asyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, asyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, asyncExecutor.getTimerLockTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) asyncExecutor).getKeepAliveTime());
    assertEquals(60000, asyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) asyncExecutor).getSecondsToWaitOnShutdown());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertTrue(((DefaultAsyncJobExecutor) asyncExecutor).isMessageQueueMode());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  public void testInitAsyncExecutor5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutorPerTenantAsyncExecutor asyncExecutor = new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    jtaProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
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
  public void testInitAsyncExecutor6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutor(null);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");
    LinkedBlockingDeque<Runnable> asyncExecutorThreadPoolQueue = new LinkedBlockingDeque<>();
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(asyncExecutorThreadPoolQueue);

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
  public void testInitAsyncExecutor_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor = jtaProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecutorService());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecuteAsyncRunnableFactory());
    assertEquals(0, asyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, asyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, asyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, asyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(10, ((DefaultAsyncJobExecutor) asyncExecutor).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) asyncExecutor).getQueueSize());
    assertEquals(10000, asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, asyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((DefaultAsyncJobExecutor) asyncExecutor).getCorePoolSize());
    assertEquals(3, asyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, asyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, asyncExecutor.getTimerLockTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) asyncExecutor).getKeepAliveTime());
    assertEquals(60000, asyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) asyncExecutor).getSecondsToWaitOnShutdown());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) asyncExecutor).isMessageQueueMode());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initHistoryLevel()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initHistoryLevel()}
   */
  @Test
  public void testInitHistoryLevel_givenJtaProcessEngineConfiguration() {
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
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * HistoryLevel is {@code AUDIT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initHistoryLevel()}
   */
  @Test
  public void testInitHistoryLevel_thenJtaProcessEngineConfigurationHistoryLevelIsAudit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initHistoryLevel();

    // Assert
    assertEquals(HistoryLevel.AUDIT, jtaProcessEngineConfiguration.getHistoryLevel());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initHistoryLevel()}.
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * HistoryLevel is {@code NONE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initHistoryLevel()}
   */
  @Test
  public void testInitHistoryLevel_thenJtaProcessEngineConfigurationHistoryLevelIsNone() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setHistoryLevel(HistoryLevel.NONE);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}
   */
  @Test
  public void testInitCommandContextFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initTransactionContextFactory()}
   */
  @Test
  public void testInitTransactionContextFactory() {
    // Arrange and Act
    standaloneInMemProcessEngineConfiguration.initTransactionContextFactory();

    // Assert
    Collection<? extends CommandInterceptor> defaultCommandInterceptors = standaloneInMemProcessEngineConfiguration
        .getDefaultCommandInterceptors();
    assertEquals(2, defaultCommandInterceptors.size());
    assertTrue(defaultCommandInterceptors instanceof List);
    TransactionContextFactory transactionContextFactory = standaloneInMemProcessEngineConfiguration
        .getTransactionContextFactory();
    assertTrue(transactionContextFactory instanceof StandaloneMybatisTransactionContextFactory);
    CommandInterceptor getResult = ((List<? extends CommandInterceptor>) defaultCommandInterceptors).get(1);
    assertTrue(getResult instanceof TransactionContextInterceptor);
    assertNull(getResult.getNext());
    assertSame(transactionContextFactory, ((TransactionContextInterceptor) getResult).getTransactionContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  public void testInitVariableTypes() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testInitVariableTypes2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultVariableTypes variableTypes = new DefaultVariableTypes();
    jtaProcessEngineConfiguration.setVariableTypes(variableTypes);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testInitVariableTypes3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSerializePOJOsInVariablesToJson(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testInitVariableTypes4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreVariableTypes(new ArrayList<>());
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testInitVariableTypes5() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostVariableTypes(new ArrayList<>());
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testInitVariableTypes6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMaxLengthStringVariableType(3);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BigDecimalType} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  public void testInitVariableTypes_givenArrayListAddBigDecimalType() {
    // Arrange
    ArrayList<VariableType> customPreVariableTypes = new ArrayList<>();
    customPreVariableTypes.add(new BigDecimalType());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreVariableTypes(customPreVariableTypes);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BigDecimalType} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  public void testInitVariableTypes_givenArrayListAddBigDecimalType2() {
    // Arrange
    ArrayList<VariableType> customPostVariableTypes = new ArrayList<>();
    customPostVariableTypes.add(new BigDecimalType());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostVariableTypes(customPostVariableTypes);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link HistoricJPAEntityListVariableType} (default constructor)
   * ForceCacheable is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  public void testInitVariableTypes_givenHistoricJPAEntityListVariableTypeForceCacheableIsTrue() {
    // Arrange
    HistoricJPAEntityListVariableType historicJPAEntityListVariableType = new HistoricJPAEntityListVariableType();
    historicJPAEntityListVariableType.setForceCacheable(true);

    ArrayList<VariableType> customPreVariableTypes = new ArrayList<>();
    customPreVariableTypes.add(historicJPAEntityListVariableType);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreVariableTypes(customPreVariableTypes);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link HistoricJPAEntityListVariableType} (default constructor)
   * ForceCacheable is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  public void testInitVariableTypes_givenHistoricJPAEntityListVariableTypeForceCacheableIsTrue2() {
    // Arrange
    HistoricJPAEntityListVariableType historicJPAEntityListVariableType = new HistoricJPAEntityListVariableType();
    historicJPAEntityListVariableType.setForceCacheable(true);

    ArrayList<VariableType> customPostVariableTypes = new ArrayList<>();
    customPostVariableTypes.add(historicJPAEntityListVariableType);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostVariableTypes(customPostVariableTypes);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link HistoricJPAEntityVariableType} (default constructor)
   * ForceCacheable is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  public void testInitVariableTypes_givenHistoricJPAEntityVariableTypeForceCacheableIsTrue() {
    // Arrange
    HistoricJPAEntityVariableType historicJPAEntityVariableType = new HistoricJPAEntityVariableType();
    historicJPAEntityVariableType.setForceCacheable(true);

    ArrayList<VariableType> customPreVariableTypes = new ArrayList<>();
    customPreVariableTypes.add(historicJPAEntityVariableType);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPreVariableTypes(customPreVariableTypes);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initVariableTypes();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getVariableTypes() instanceof DefaultVariableTypes);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initVariableTypes()}.
   * <ul>
   *   <li>Given {@link HistoricJPAEntityVariableType} (default constructor)
   * ForceCacheable is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initVariableTypes()}
   */
  @Test
  public void testInitVariableTypes_givenHistoricJPAEntityVariableTypeForceCacheableIsTrue2() {
    // Arrange
    HistoricJPAEntityVariableType historicJPAEntityVariableType = new HistoricJPAEntityVariableType();
    historicJPAEntityVariableType.setForceCacheable(true);

    ArrayList<VariableType> customPostVariableTypes = new ArrayList<>();
    customPostVariableTypes.add(historicJPAEntityVariableType);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostVariableTypes(customPostVariableTypes);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  public void testGetMaxLengthString_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfigurationImpl.DEFAULT_GENERIC_MAX_LENGTH_STRING,
        (new JtaProcessEngineConfiguration()).getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   * <ul>
   *   <li>Then return
   * {@link ProcessEngineConfigurationImpl#DEFAULT_GENERIC_MAX_LENGTH_STRING}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  public void testGetMaxLengthString_thenReturnDefault_generic_max_length_string() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngineConfigurationImpl.DEFAULT_GENERIC_MAX_LENGTH_STRING,
        jtaProcessEngineConfiguration.getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   * <ul>
   *   <li>Then return
   * {@link ProcessEngineConfigurationImpl#DEFAULT_ORACLE_MAX_LENGTH_STRING}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  public void testGetMaxLengthString_thenReturnDefault_oracle_max_length_string() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDatabaseType(ProcessEngineConfigurationImpl.DATABASE_TYPE_ORACLE);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  public void testGetMaxLengthString_thenReturnThree() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMaxLengthStringVariableType(3);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(3, jtaProcessEngineConfiguration.getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initScriptingEngines()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initScriptingEngines()}
   */
  @Test
  public void testInitScriptingEngines() {
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initScriptingEngines()}
   */
  @Test
  public void testInitScriptingEngines2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initScriptingEngines()}
   */
  @Test
  public void testInitScriptingEngines_givenJtaProcessEngineConfiguration() {
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
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * ResolverFactories Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initScriptingEngines()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initExpressionManager()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initExpressionManager()}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initExpressionManager()}
   */
  @Test
  public void testInitExpressionManager3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initExpressionManager();

    // Assert
    assertNull(jtaProcessEngineConfiguration.getExpressionManager().getBeans());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initExpressionManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initExpressionManager()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initBusinessCalendarManager()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initDelegateInterceptor()}
   */
  @Test
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
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventHandlers()}
   */
  @Test
  public void testInitEventHandlers3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventHandlers()}
   */
  @Test
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
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * EventHandlers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initEventHandlers()}
   */
  @Test
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
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBeans()}
   */
  @Test
  public void testInitBeans() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initBeans();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getBeans().isEmpty());
    assertTrue(jtaProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Beans
   * is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#initBeans()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ActivitiEventDispatcherImpl eventDispatcher = new ActivitiEventDispatcherImpl();
    jtaProcessEngineConfiguration.setEventDispatcher(eventDispatcher);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initEventDispatcher();

    // Assert
    ActivitiEventDispatcher eventDispatcher2 = jtaProcessEngineConfiguration.getEventDispatcher();
    assertTrue(eventDispatcher2 instanceof ActivitiEventDispatcherImpl);
    assertTrue(eventDispatcher2.isEnabled());
    assertSame(eventDispatcher, eventDispatcher2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initEventDispatcher()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(new HashMap<>());
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventListeners(new ArrayList<>());
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher5() {
    // Arrange
    ArrayList<ActivitiEventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new BaseEntityEventListener(true));

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventListeners(eventListeners);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link HashMap#HashMap()} {@code ,} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher_givenHashMapCommaIsArrayList() {
    // Arrange
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put(",", new ArrayList<>());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link HashMap#HashMap()} {@code ,} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher_givenHashMapCommaIsArrayList2() {
    // Arrange
    ArrayList<ActivitiEventListener> activitiEventListenerList = new ArrayList<>();
    activitiEventListenerList.add(new BaseEntityEventListener(true));

    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put(",", activitiEventListenerList);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link HashMap#HashMap()} empty string is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher_givenHashMapEmptyStringIsArrayList() {
    // Arrange
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put("", new ArrayList<>());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link HashMap#HashMap()} {@code ENTITY_CREATED} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher_givenHashMapEntityCreatedIsArrayList() {
    // Arrange
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put("ENTITY_CREATED", new ArrayList<>());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link HashMap#HashMap()} {@code ENTITY_CREATED} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher_givenHashMapEntityCreatedIsArrayList2() {
    // Arrange
    ArrayList<ActivitiEventListener> activitiEventListenerList = new ArrayList<>();
    activitiEventListenerList.add(new BaseEntityEventListener(true));

    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put("ENTITY_CREATED", activitiEventListenerList);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link HashMap#HashMap()} {@code ENTITY_CREATED} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher_givenHashMapEntityCreatedIsArrayList3() {
    // Arrange
    ArrayList<ActivitiEventListener> activitiEventListenerList = new ArrayList<>();
    activitiEventListenerList.add(new BaseEntityEventListener(true));
    activitiEventListenerList.add(new BaseEntityEventListener(true));

    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put("ENTITY_CREATED", activitiEventListenerList);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link HashMap#HashMap()} {@code null} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
  public void testInitEventDispatcher_givenHashMapNullIsArrayList() {
    // Arrange
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();
    typedListeners.put(null, new ArrayList<>());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(typedListeners);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initEventDispatcher()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initProcessValidator()}
   */
  @Test
  public void testInitProcessValidator() {
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
   * Test {@link ProcessEngineConfigurationImpl#initProcessValidator()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initProcessValidator()}
   */
  @Test
  public void testInitProcessValidator2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initProcessValidator()}
   */
  @Test
  public void testInitProcessValidator_givenJtaProcessEngineConfiguration() {
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
   * Test {@link ProcessEngineConfigurationImpl#initDatabaseEventLogging()}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcherImpl#addEventListener(ActivitiEventListener)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#initDatabaseEventLogging()}
   */
  @Test
  public void testInitDatabaseEventLogging_thenCallsAddEventListener() {
    // Arrange
    ActivitiEventDispatcherImpl eventDispatcher = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(eventDispatcher).addEventListener(Mockito.<ActivitiEventListener>any());

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventDispatcher(eventDispatcher);
    jtaProcessEngineConfiguration.setEnableDatabaseEventLogging(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.initDatabaseEventLogging();

    // Assert
    verify(eventDispatcher).addEventListener(isA(ActivitiEventListener.class));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#postProcessEngineInitialisation()}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#postProcessEngineInitialisation()}
   */
  @Test
  public void testPostProcessEngineInitialisation_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCommandExecutor(commandExecutor);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.postProcessEngineInitialisation();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}
   */
  @Test
  public void testGetDefaultCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDefaultCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}
   */
  @Test
  public void testGetDefaultCommandConfig_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDefaultCommandConfig());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDefaultCommandConfig(CommandConfig)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDefaultCommandConfig(CommandConfig)}
   */
  @Test
  public void testSetDefaultCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    CommandConfig defaultCommandConfig = new CommandConfig();

    // Act
    jtaProcessEngineConfiguration.setDefaultCommandConfig(defaultCommandConfig);

    // Assert
    assertSame(defaultCommandConfig, jtaProcessEngineConfiguration.getDefaultCommandConfig());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDefaultCommandConfig(CommandConfig)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDefaultCommandConfig(CommandConfig)}
   */
  @Test
  public void testSetDefaultCommandConfig_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSchemaCommandConfig()}
   */
  @Test
  public void testGetSchemaCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getSchemaCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSchemaCommandConfig()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSchemaCommandConfig()}
   */
  @Test
  public void testGetSchemaCommandConfig_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSchemaCommandConfig());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSchemaCommandConfig(CommandConfig)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSchemaCommandConfig(CommandConfig)}
   */
  @Test
  public void testSetSchemaCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    CommandConfig schemaCommandConfig = new CommandConfig();

    // Act
    jtaProcessEngineConfiguration.setSchemaCommandConfig(schemaCommandConfig);

    // Assert
    assertSame(schemaCommandConfig, jtaProcessEngineConfiguration.getSchemaCommandConfig());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSchemaCommandConfig(CommandConfig)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSchemaCommandConfig(CommandConfig)}
   */
  @Test
  public void testSetSchemaCommandConfig_givenJtaProcessEngineConfiguration() {
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
  public void testGetCommandInvoker() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCommandInvoker());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandInvoker()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getCommandInvoker()}
   */
  @Test
  public void testGetCommandInvoker_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommandInvoker());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommandInvoker(CommandInterceptor)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandInvoker(CommandInterceptor)}
   */
  @Test
  public void testSetCommandInvoker() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    CommandContextInterceptor commandInvoker = new CommandContextInterceptor();

    // Act
    jtaProcessEngineConfiguration.setCommandInvoker(commandInvoker);

    // Assert
    assertSame(commandInvoker, jtaProcessEngineConfiguration.getCommandInvoker());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommandInvoker(CommandInterceptor)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandInvoker(CommandInterceptor)}
   */
  @Test
  public void testSetCommandInvoker_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandContextInterceptor commandInvoker = new CommandContextInterceptor();

    // Act
    jtaProcessEngineConfiguration.setCommandInvoker(commandInvoker);

    // Assert
    assertSame(commandInvoker, jtaProcessEngineConfiguration.getCommandInvoker());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}
   */
  @Test
  public void testGetCustomPreCommandInterceptors() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomPreCommandInterceptors());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}
   */
  @Test
  public void testGetCustomPreCommandInterceptors_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPreCommandInterceptors());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}
   */
  @Test
  public void testSetCustomPreCommandInterceptors2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ArrayList<CommandInterceptor> customPreCommandInterceptors = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPreCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCustomPreCommandInterceptors(customPreCommandInterceptors);

    // Assert
    assertSame(customPreCommandInterceptors, jtaProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPreCommandInterceptorsResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}
   */
  @Test
  public void testSetCustomPreCommandInterceptors3() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreCommandInterceptors(List)}
   */
  @Test
  public void testSetCustomPreCommandInterceptors4() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}
   */
  @Test
  public void testGetCustomPostCommandInterceptors() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomPostCommandInterceptors());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}
   */
  @Test
  public void testGetCustomPostCommandInterceptors_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPostCommandInterceptors());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}
   */
  @Test
  public void testSetCustomPostCommandInterceptors2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ArrayList<CommandInterceptor> customPostCommandInterceptors = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomPostCommandInterceptorsResult = jtaProcessEngineConfiguration
        .setCustomPostCommandInterceptors(customPostCommandInterceptors);

    // Assert
    assertSame(customPostCommandInterceptors, jtaProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomPostCommandInterceptorsResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}
   */
  @Test
  public void testSetCustomPostCommandInterceptors3() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostCommandInterceptors(List)}
   */
  @Test
  public void testSetCustomPostCommandInterceptors4() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommandInterceptors()}
   */
  @Test
  public void testGetCommandInterceptors() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandInterceptors()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommandInterceptors()}
   */
  @Test
  public void testGetCommandInterceptors_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}
   */
  @Test
  public void testSetCommandInterceptors2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandInterceptors(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommandExecutor()}
   */
  @Test
  public void testGetCommandExecutor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandExecutor()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommandExecutor()}
   */
  @Test
  public void testGetCommandExecutor_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommandExecutor());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommandExecutor(CommandExecutor)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandExecutor(CommandExecutor)}
   */
  @Test
  public void testSetCommandExecutor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommandExecutor(CommandExecutor)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandExecutor(CommandExecutor)}
   */
  @Test
  public void testSetCommandExecutor_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getRepositoryService()}
   */
  @Test
  public void testGetRepositoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    RepositoryService actualRepositoryService = jtaProcessEngineConfiguration.getRepositoryService();

    // Assert
    assertTrue(actualRepositoryService instanceof RepositoryServiceImpl);
    assertNull(((RepositoryServiceImpl) actualRepositoryService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.repositoryService, actualRepositoryService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getRepositoryService()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getRepositoryService()}
   */
  @Test
  public void testGetRepositoryService_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setRepositoryService(RepositoryService)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setRepositoryService(RepositoryService)}
   */
  @Test
  public void testSetRepositoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    // Act
    ProcessEngineConfigurationImpl actualSetRepositoryServiceResult = jtaProcessEngineConfiguration
        .setRepositoryService(repositoryService);

    // Assert
    assertSame(repositoryService, jtaProcessEngineConfiguration.getRepositoryService());
    assertSame(jtaProcessEngineConfiguration, actualSetRepositoryServiceResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setRepositoryService(RepositoryService)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setRepositoryService(RepositoryService)}
   */
  @Test
  public void testSetRepositoryService_givenJtaProcessEngineConfiguration() {
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
  public void testGetRuntimeService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    RuntimeService actualRuntimeService = jtaProcessEngineConfiguration.getRuntimeService();

    // Assert
    assertTrue(actualRuntimeService instanceof RuntimeServiceImpl);
    assertNull(((RuntimeServiceImpl) actualRuntimeService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.runtimeService, actualRuntimeService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getRuntimeService()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getRuntimeService()}
   */
  @Test
  public void testGetRuntimeService_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setRuntimeService(RuntimeService)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setRuntimeService(RuntimeService)}
   */
  @Test
  public void testSetRuntimeService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();

    // Act
    ProcessEngineConfigurationImpl actualSetRuntimeServiceResult = jtaProcessEngineConfiguration
        .setRuntimeService(runtimeService);

    // Assert
    assertSame(runtimeService, jtaProcessEngineConfiguration.getRuntimeService());
    assertSame(jtaProcessEngineConfiguration, actualSetRuntimeServiceResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setRuntimeService(RuntimeService)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setRuntimeService(RuntimeService)}
   */
  @Test
  public void testSetRuntimeService_givenJtaProcessEngineConfiguration() {
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
  public void testGetHistoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoryService actualHistoryService = jtaProcessEngineConfiguration.getHistoryService();

    // Assert
    assertTrue(actualHistoryService instanceof HistoryServiceImpl);
    assertNull(((HistoryServiceImpl) actualHistoryService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.historyService, actualHistoryService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoryService()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoryService()}
   */
  @Test
  public void testGetHistoryService_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoryService(HistoryService)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoryService(HistoryService)}
   */
  @Test
  public void testSetHistoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HistoryServiceImpl historyService = new HistoryServiceImpl(new JtaProcessEngineConfiguration());

    // Act
    ProcessEngineConfigurationImpl actualSetHistoryServiceResult = jtaProcessEngineConfiguration
        .setHistoryService(historyService);

    // Assert
    assertSame(historyService, jtaProcessEngineConfiguration.getHistoryService());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryServiceResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoryService(HistoryService)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoryService(HistoryService)}
   */
  @Test
  public void testSetHistoryService_givenJtaProcessEngineConfiguration() {
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
  public void testGetTaskService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    TaskService actualTaskService = jtaProcessEngineConfiguration.getTaskService();

    // Assert
    assertTrue(actualTaskService instanceof TaskServiceImpl);
    assertNull(((TaskServiceImpl) actualTaskService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.taskService, actualTaskService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskService()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTaskService()}
   */
  @Test
  public void testGetTaskService_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTaskService(TaskService)}
   */
  @Test
  public void testSetTaskService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());

    // Act
    ProcessEngineConfigurationImpl actualSetTaskServiceResult = jtaProcessEngineConfiguration
        .setTaskService(taskService);

    // Assert
    assertSame(taskService, jtaProcessEngineConfiguration.getTaskService());
    assertSame(jtaProcessEngineConfiguration, actualSetTaskServiceResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTaskService(TaskService)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTaskService(TaskService)}
   */
  @Test
  public void testSetTaskService_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getManagementService()}
   */
  @Test
  public void testGetManagementService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ManagementService actualManagementService = jtaProcessEngineConfiguration.getManagementService();

    // Assert
    assertTrue(actualManagementService instanceof ManagementServiceImpl);
    assertNull(((ManagementServiceImpl) actualManagementService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.managementService, actualManagementService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getManagementService()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getManagementService()}
   */
  @Test
  public void testGetManagementService_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setManagementService(ManagementService)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setManagementService(ManagementService)}
   */
  @Test
  public void testSetManagementService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ManagementServiceImpl managementService = new ManagementServiceImpl();

    // Act
    ProcessEngineConfigurationImpl actualSetManagementServiceResult = jtaProcessEngineConfiguration
        .setManagementService(managementService);

    // Assert
    assertSame(managementService, jtaProcessEngineConfiguration.getManagementService());
    assertSame(jtaProcessEngineConfiguration, actualSetManagementServiceResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setManagementService(ManagementService)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setManagementService(ManagementService)}
   */
  @Test
  public void testSetManagementService_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDynamicBpmnService()}
   */
  @Test
  public void testGetDynamicBpmnService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    DynamicBpmnService actualDynamicBpmnService = jtaProcessEngineConfiguration.getDynamicBpmnService();

    // Assert
    assertTrue(actualDynamicBpmnService instanceof DynamicBpmnServiceImpl);
    assertNull(((DynamicBpmnServiceImpl) actualDynamicBpmnService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.dynamicBpmnService, actualDynamicBpmnService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDynamicBpmnService()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDynamicBpmnService()}
   */
  @Test
  public void testGetDynamicBpmnService_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setDynamicBpmnService(DynamicBpmnService)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDynamicBpmnService(DynamicBpmnService)}
   */
  @Test
  public void testSetDynamicBpmnService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DynamicBpmnServiceImpl dynamicBpmnService = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    // Act
    ProcessEngineConfigurationImpl actualSetDynamicBpmnServiceResult = jtaProcessEngineConfiguration
        .setDynamicBpmnService(dynamicBpmnService);

    // Assert
    assertSame(dynamicBpmnService, jtaProcessEngineConfiguration.getDynamicBpmnService());
    assertSame(jtaProcessEngineConfiguration, actualSetDynamicBpmnServiceResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDynamicBpmnService(DynamicBpmnService)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDynamicBpmnService(DynamicBpmnService)}
   */
  @Test
  public void testSetDynamicBpmnService_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setUserGroupManager(UserGroupManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setUserGroupManager(UserGroupManager)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getUserGroupManager()}
   */
  @Test
  public void testGetUserGroupManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getUserGroupManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getUserGroupManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getUserGroupManager()}
   */
  @Test
  public void testGetUserGroupManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getUserGroupManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIntegrationContextManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIntegrationContextManager()}
   */
  @Test
  public void testGetIntegrationContextManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    IntegrationContextManager actualIntegrationContextManager = jtaProcessEngineConfiguration
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
   * Test {@link ProcessEngineConfigurationImpl#getIntegrationContextManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIntegrationContextManager()}
   */
  @Test
  public void testGetIntegrationContextManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIntegrationContextService()}
   */
  @Test
  public void testGetIntegrationContextService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getIntegrationContextService() instanceof IntegrationContextServiceImpl);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIntegrationContextService()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIntegrationContextService()}
   */
  @Test
  public void testGetIntegrationContextService_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue(
        (new JtaProcessEngineConfiguration()).getIntegrationContextService() instanceof IntegrationContextServiceImpl);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}
   */
  @Test
  public void testGetProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}
   */
  @Test
  public void testGetProcessEngineConfiguration_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSessionFactories()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSessionFactories()}
   */
  @Test
  public void testGetSessionFactories() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSessionFactories()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSessionFactories()}
   */
  @Test
  public void testGetSessionFactories_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setSessionFactories(Map)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSessionFactories(Map)}
   */
  @Test
  public void testSetSessionFactories() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HashMap<Class<?>, SessionFactory> sessionFactories = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetSessionFactoriesResult = jtaProcessEngineConfiguration
        .setSessionFactories(sessionFactories);

    // Assert
    assertSame(sessionFactories, jtaProcessEngineConfiguration.getSessionFactories());
    assertSame(jtaProcessEngineConfiguration, actualSetSessionFactoriesResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setSessionFactories(Map)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSessionFactories(Map)}
   */
  @Test
  public void testSetSessionFactories_givenJtaProcessEngineConfiguration() {
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
  public void testGetConfigurators() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getConfigurators()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getConfigurators()}
   */
  @Test
  public void testGetConfigurators_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getConfigurators());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  public void testAddConfigurator() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator()));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  public void testAddConfigurator_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator()));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * Configurators is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setConfigurators(List)}
   */
  @Test
  public void testSetConfigurators() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * Configurators is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setConfigurators(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setConfigurators(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setConfigurators(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAllConfigurators()}
   */
  @Test
  public void testGetAllConfigurators() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getAllConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAllConfigurators()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAllConfigurators()}
   */
  @Test
  public void testGetAllConfigurators_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAllConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}
   */
  @Test
  public void testGetBpmnDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getBpmnDeployer());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}
   */
  @Test
  public void testGetBpmnDeployer_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getBpmnDeployer());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBpmnDeployer(BpmnDeployer)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBpmnDeployer(BpmnDeployer)}
   */
  @Test
  public void testSetBpmnDeployer() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    // Act
    ProcessEngineConfigurationImpl actualSetBpmnDeployerResult = jtaProcessEngineConfiguration
        .setBpmnDeployer(bpmnDeployer);

    // Assert
    Collection<? extends Deployer> defaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    assertSame(bpmnDeployer, jtaProcessEngineConfiguration.getBpmnDeployer());
    assertSame(jtaProcessEngineConfiguration, actualSetBpmnDeployerResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBpmnDeployer(BpmnDeployer)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBpmnDeployer(BpmnDeployer)}
   */
  @Test
  public void testSetBpmnDeployer_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    BpmnDeployer bpmnDeployer = new BpmnDeployer();

    // Act
    ProcessEngineConfigurationImpl actualSetBpmnDeployerResult = jtaProcessEngineConfiguration
        .setBpmnDeployer(bpmnDeployer);

    // Assert
    Collection<? extends Deployer> defaultDeployers = jtaProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    assertSame(bpmnDeployer, jtaProcessEngineConfiguration.getBpmnDeployer());
    assertSame(jtaProcessEngineConfiguration, actualSetBpmnDeployerResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnParser()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBpmnParser()}
   */
  @Test
  public void testGetBpmnParser() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getBpmnParser());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnParser()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBpmnParser()}
   */
  @Test
  public void testGetBpmnParser_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getBpmnParser());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBpmnParser(BpmnParser)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBpmnParser(BpmnParser)}
   */
  @Test
  public void testSetBpmnParser() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    BpmnParser bpmnParser = new BpmnParser();

    // Act
    ProcessEngineConfigurationImpl actualSetBpmnParserResult = jtaProcessEngineConfiguration.setBpmnParser(bpmnParser);

    // Assert
    assertSame(bpmnParser, jtaProcessEngineConfiguration.getBpmnParser());
    assertSame(jtaProcessEngineConfiguration, actualSetBpmnParserResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBpmnParser(BpmnParser)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBpmnParser(BpmnParser)}
   */
  @Test
  public void testSetBpmnParser_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}
   */
  @Test
  public void testGetParsedDeploymentBuilderFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getParsedDeploymentBuilderFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}
   */
  @Test
  public void testGetParsedDeploymentBuilderFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getParsedDeploymentBuilderFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)}
   */
  @Test
  public void testSetParsedDeploymentBuilderFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Test
   * {@link ProcessEngineConfigurationImpl#setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setParsedDeploymentBuilderFactory(ParsedDeploymentBuilderFactory)}
   */
  @Test
  public void testSetParsedDeploymentBuilderFactory_givenJtaProcessEngineConfiguration() {
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
  public void testGetTimerManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getTimerManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTimerManager()}
   */
  @Test
  public void testGetTimerManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTimerManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}
   */
  @Test
  public void testGetEventSubscriptionManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventSubscriptionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}
   */
  @Test
  public void testGetEventSubscriptionManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventSubscriptionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}
   */
  @Test
  public void testGetBpmnDeploymentHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}
   */
  @Test
  public void testGetBpmnDeploymentHelper_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getBpmnDeploymentHelper());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setBpmnDeploymentHelper(BpmnDeploymentHelper)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBpmnDeploymentHelper(BpmnDeploymentHelper)}
   */
  @Test
  public void testSetBpmnDeploymentHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    // Act
    ProcessEngineConfigurationImpl actualSetBpmnDeploymentHelperResult = jtaProcessEngineConfiguration
        .setBpmnDeploymentHelper(bpmnDeploymentHelper);

    // Assert
    assertSame(bpmnDeploymentHelper, jtaProcessEngineConfiguration.getBpmnDeploymentHelper());
    assertSame(jtaProcessEngineConfiguration, actualSetBpmnDeploymentHelperResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setBpmnDeploymentHelper(BpmnDeploymentHelper)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBpmnDeploymentHelper(BpmnDeploymentHelper)}
   */
  @Test
  public void testSetBpmnDeploymentHelper_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCachingAndArtifactsManager()}
   */
  @Test
  public void testGetCachingAndArtifactsManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCachingAndArtifactsManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCachingAndArtifactsManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCachingAndArtifactsManager()}
   */
  @Test
  public void testGetCachingAndArtifactsManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCachingAndArtifactsManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeployers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDeployers()}
   */
  @Test
  public void testGetDeployers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeployers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getDeployers()}
   */
  @Test
  public void testGetDeployers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setDeployers(List)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeployers(List)}
   */
  @Test
  public void testSetDeployers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ArrayList<Deployer> deployers = new ArrayList<>();

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setDeployers(List)}
   */
  @Test
  public void testSetDeployers_givenJtaProcessEngineConfiguration_whenArrayList() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setIdGenerator(IdGenerator)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getWsSyncFactoryClassName()}
   */
  @Test
  public void testGetWsSyncFactoryClassName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngineConfigurationImpl.DEFAULT_WS_SYNC_FACTORY,
        jtaProcessEngineConfiguration.getWsSyncFactoryClassName());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getWsSyncFactoryClassName()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getWsSyncFactoryClassName()}
   */
  @Test
  public void testGetWsSyncFactoryClassName_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfigurationImpl.DEFAULT_WS_SYNC_FACTORY,
        (new JtaProcessEngineConfiguration()).getWsSyncFactoryClassName());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setWsSyncFactoryClassName(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setWsSyncFactoryClassName(String)}
   */
  @Test
  public void testSetWsSyncFactoryClassName() {
    // Arrange and Act
    ProcessEngineConfigurationImpl actualSetWsSyncFactoryClassNameResult = jtaProcessEngineConfiguration
        .setWsSyncFactoryClassName("Ws Sync Factory Class Name");

    // Assert
    assertEquals("Ws Sync Factory Class Name", jtaProcessEngineConfiguration.getWsSyncFactoryClassName());
    assertSame(jtaProcessEngineConfiguration, actualSetWsSyncFactoryClassNameResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}
   */
  @Test
  public void testAddWsEndpointAddress() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.addWsEndpointAddress(QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}.
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}
   */
  @Test
  public void testAddWsEndpointAddress_thenReturnJtaProcessEngineConfiguration() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.addWsEndpointAddress(QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#removeWsEndpointAddress(QName)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#removeWsEndpointAddress(QName)}
   */
  @Test
  public void testRemoveWsEndpointAddress() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.removeWsEndpointAddress(QName.valueOf("foo")));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#removeWsEndpointAddress(QName)}.
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#removeWsEndpointAddress(QName)}
   */
  @Test
  public void testRemoveWsEndpointAddress_thenReturnJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.removeWsEndpointAddress(QName.valueOf("foo")));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}
   */
  @Test
  public void testGetWsOverridenEndpointAddresses() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ConcurrentMap<QName, URL> actualWsOverridenEndpointAddresses = jtaProcessEngineConfiguration
        .getWsOverridenEndpointAddresses();

    // Assert
    assertTrue(actualWsOverridenEndpointAddresses.isEmpty());
    assertSame(jtaProcessEngineConfiguration.wsOverridenEndpointAddresses, actualWsOverridenEndpointAddresses);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}
   */
  @Test
  public void testGetWsOverridenEndpointAddresses_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setWsOverridenEndpointAddresses(ConcurrentMap)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setWsOverridenEndpointAddresses(ConcurrentMap)}
   */
  @Test
  public void testSetWsOverridenEndpointAddresses() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setWsOverridenEndpointAddresses(new ConcurrentHashMap<>()));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setWsOverridenEndpointAddresses(ConcurrentMap)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setWsOverridenEndpointAddresses(ConcurrentMap)}
   */
  @Test
  public void testSetWsOverridenEndpointAddresses_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setWsOverridenEndpointAddresses(new ConcurrentHashMap<>()));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getScriptingEngines()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getScriptingEngines()}
   */
  @Test
  public void testGetScriptingEngines() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getScriptingEngines());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getScriptingEngines()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getScriptingEngines()}
   */
  @Test
  public void testGetScriptingEngines_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getScriptingEngines());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setScriptingEngines(ScriptingEngines)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setScriptingEngines(ScriptingEngines)}
   */
  @Test
  public void testSetScriptingEngines() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setScriptingEngines(scriptingEngines));
    assertSame(scriptingEngines, jtaProcessEngineConfiguration.getScriptingEngines());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setScriptingEngines(ScriptingEngines)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setScriptingEngines(ScriptingEngines)}
   */
  @Test
  public void testSetScriptingEngines_givenJtaProcessEngineConfiguration() {
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
  public void testGetVariableTypes() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableTypes()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getVariableTypes()}
   */
  @Test
  public void testGetVariableTypes_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setVariableTypes(VariableTypes)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setVariableTypes(VariableTypes)}
   */
  @Test
  public void testSetVariableTypes() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultVariableTypes variableTypes = new DefaultVariableTypes();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setVariableTypes(variableTypes));
    assertSame(variableTypes, jtaProcessEngineConfiguration.getVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setVariableTypes(VariableTypes)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setVariableTypes(VariableTypes)}
   */
  @Test
  public void testSetVariableTypes_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultVariableTypes variableTypes = new DefaultVariableTypes();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setVariableTypes(variableTypes));
    assertSame(variableTypes, jtaProcessEngineConfiguration.getVariableTypes());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isSerializableVariableTypeTrackDeserializedObjects()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isSerializableVariableTypeTrackDeserializedObjects()}
   */
  @Test
  public void testIsSerializableVariableTypeTrackDeserializedObjects() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isSerializableVariableTypeTrackDeserializedObjects());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isSerializableVariableTypeTrackDeserializedObjects()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isSerializableVariableTypeTrackDeserializedObjects()}
   */
  @Test
  public void testIsSerializableVariableTypeTrackDeserializedObjects2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isSerializableVariableTypeTrackDeserializedObjects());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}
   */
  @Test
  public void testIsSerializePOJOsInVariablesToJson_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isSerializePOJOsInVariablesToJson());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}
   */
  @Test
  public void testIsSerializePOJOsInVariablesToJson_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isSerializePOJOsInVariablesToJson()}
   */
  @Test
  public void testIsSerializePOJOsInVariablesToJson_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSerializePOJOsInVariablesToJson(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSerializePOJOsInVariablesToJson(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSerializePOJOsInVariablesToJson(boolean)}
   */
  @Test
  public void testSetSerializePOJOsInVariablesToJson() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.setSerializePOJOsInVariablesToJson(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSerializePOJOsInVariablesToJson(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSerializePOJOsInVariablesToJson(boolean)}
   */
  @Test
  public void testSetSerializePOJOsInVariablesToJson_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getJavaClassFieldForJackson()}
   */
  @Test
  public void testGetJavaClassFieldForJackson() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("@class", jtaProcessEngineConfiguration.getJavaClassFieldForJackson());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJavaClassFieldForJackson()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getJavaClassFieldForJackson()}
   */
  @Test
  public void testGetJavaClassFieldForJackson_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals("@class", (new JtaProcessEngineConfiguration()).getJavaClassFieldForJackson());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setJavaClassFieldForJackson(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setJavaClassFieldForJackson(String)}
   */
  @Test
  public void testSetJavaClassFieldForJackson() {
    // Arrange and Act
    jtaProcessEngineConfiguration.setJavaClassFieldForJackson("Java Class Field For Jackson");

    // Assert
    assertEquals("Java Class Field For Jackson", jtaProcessEngineConfiguration.getJavaClassFieldForJackson());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExpressionManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getExpressionManager()}
   */
  @Test
  public void testGetExpressionManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getExpressionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExpressionManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getExpressionManager()}
   */
  @Test
  public void testGetExpressionManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getExpressionManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setExpressionManager(ExpressionManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setExpressionManager(ExpressionManager)}
   */
  @Test
  public void testSetExpressionManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ExpressionManager expressionManager = new ExpressionManager();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setExpressionManager(expressionManager));
    assertSame(expressionManager, jtaProcessEngineConfiguration.getExpressionManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setExpressionManager(ExpressionManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setExpressionManager(ExpressionManager)}
   */
  @Test
  public void testSetExpressionManager_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ExpressionManager expressionManager = new ExpressionManager();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setExpressionManager(expressionManager));
    assertSame(expressionManager, jtaProcessEngineConfiguration.getExpressionManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setBusinessCalendarManager(BusinessCalendarManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBusinessCalendarManager(BusinessCalendarManager)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getExecutionQueryLimit()}
   */
  @Test
  public void testGetExecutionQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(20000, jtaProcessEngineConfiguration.getExecutionQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionQueryLimit()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getExecutionQueryLimit()}
   */
  @Test
  public void testGetExecutionQueryLimit_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(20000, (new JtaProcessEngineConfiguration()).getExecutionQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setExecutionQueryLimit(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setExecutionQueryLimit(int)}
   */
  @Test
  public void testSetExecutionQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetExecutionQueryLimitResult = jtaProcessEngineConfiguration
        .setExecutionQueryLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getExecutionQueryLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetExecutionQueryLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setExecutionQueryLimit(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setExecutionQueryLimit(int)}
   */
  @Test
  public void testSetExecutionQueryLimit_givenJtaProcessEngineConfiguration() {
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
  public void testGetTaskQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(20000, jtaProcessEngineConfiguration.getTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskQueryLimit()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getTaskQueryLimit()}
   */
  @Test
  public void testGetTaskQueryLimit_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(20000, (new JtaProcessEngineConfiguration()).getTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTaskQueryLimit(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTaskQueryLimit(int)}
   */
  @Test
  public void testSetTaskQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetTaskQueryLimitResult = jtaProcessEngineConfiguration.setTaskQueryLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getTaskQueryLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetTaskQueryLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTaskQueryLimit(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTaskQueryLimit(int)}
   */
  @Test
  public void testSetTaskQueryLimit_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskQueryLimit()}
   */
  @Test
  public void testGetHistoricTaskQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(20000, jtaProcessEngineConfiguration.getHistoricTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskQueryLimit()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskQueryLimit()}
   */
  @Test
  public void testGetHistoricTaskQueryLimit_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(20000, (new JtaProcessEngineConfiguration()).getHistoricTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricTaskQueryLimit(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskQueryLimit(int)}
   */
  @Test
  public void testSetHistoricTaskQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetHistoricTaskQueryLimitResult = jtaProcessEngineConfiguration
        .setHistoricTaskQueryLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoricTaskQueryLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setHistoricTaskQueryLimit(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskQueryLimit(int)}
   */
  @Test
  public void testSetHistoricTaskQueryLimit_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}
   */
  @Test
  public void testGetHistoricProcessInstancesQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(20000, jtaProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}
   */
  @Test
  public void testGetHistoricProcessInstancesQueryLimit_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(20000, (new JtaProcessEngineConfiguration()).getHistoricProcessInstancesQueryLimit());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstancesQueryLimit(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstancesQueryLimit(int)}
   */
  @Test
  public void testSetHistoricProcessInstancesQueryLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetHistoricProcessInstancesQueryLimitResult = jtaProcessEngineConfiguration
        .setHistoricProcessInstancesQueryLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoricProcessInstancesQueryLimitResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstancesQueryLimit(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstancesQueryLimit(int)}
   */
  @Test
  public void testSetHistoricProcessInstancesQueryLimit_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommandContextFactory()}
   */
  @Test
  public void testGetCommandContextFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandContextFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommandContextFactory()}
   */
  @Test
  public void testGetCommandContextFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommandContextFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommandContextFactory(CommandContextFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandContextFactory(CommandContextFactory)}
   */
  @Test
  public void testSetCommandContextFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    CommandContextFactory commandContextFactory = new CommandContextFactory();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setCommandContextFactory(commandContextFactory));
    assertSame(commandContextFactory, jtaProcessEngineConfiguration.getCommandContextFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommandContextFactory(CommandContextFactory)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommandContextFactory(CommandContextFactory)}
   */
  @Test
  public void testSetCommandContextFactory_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandContextFactory commandContextFactory = new CommandContextFactory();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setCommandContextFactory(commandContextFactory));
    assertSame(commandContextFactory, jtaProcessEngineConfiguration.getCommandContextFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTransactionContextFactory(TransactionContextFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTransactionContextFactory(TransactionContextFactory)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPreDeployers()}
   */
  @Test
  public void testGetCustomPreDeployers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomPreDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreDeployers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPreDeployers()}
   */
  @Test
  public void testGetCustomPreDeployers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPreDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}
   */
  @Test
  public void testSetCustomPreDeployers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ArrayList<Deployer> customPreDeployers = new ArrayList<>();

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreDeployers(List)}
   */
  @Test
  public void testSetCustomPreDeployers_givenJtaProcessEngineConfiguration_whenArrayList() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPostDeployers()}
   */
  @Test
  public void testGetCustomPostDeployers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomPostDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostDeployers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPostDeployers()}
   */
  @Test
  public void testGetCustomPostDeployers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPostDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}
   */
  @Test
  public void testSetCustomPostDeployers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ArrayList<Deployer> customPostDeployers = new ArrayList<>();

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostDeployers(List)}
   */
  @Test
  public void testSetCustomPostDeployers_givenJtaProcessEngineConfiguration_whenArrayList() {
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
  public void testGetJobHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getJobHandlers()}
   */
  @Test
  public void testGetJobHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setJobHandlers(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setJobHandlers(Map)}
   */
  @Test
  public void testSetJobHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HashMap<String, JobHandler> jobHandlers = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetJobHandlersResult = jtaProcessEngineConfiguration
        .setJobHandlers(jobHandlers);

    // Assert
    assertSame(jobHandlers, jtaProcessEngineConfiguration.getJobHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetJobHandlersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setJobHandlers(Map)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setJobHandlers(Map)}
   */
  @Test
  public void testSetJobHandlers_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessInstanceHelper()}
   */
  @Test
  public void testGetProcessInstanceHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessInstanceHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessInstanceHelper()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessInstanceHelper()}
   */
  @Test
  public void testGetProcessInstanceHelper_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessInstanceHelper());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessInstanceHelper(ProcessInstanceHelper)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessInstanceHelper(ProcessInstanceHelper)}
   */
  @Test
  public void testSetProcessInstanceHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ProcessInstanceHelper processInstanceHelper = new ProcessInstanceHelper();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessInstanceHelper(processInstanceHelper));
    assertSame(processInstanceHelper, jtaProcessEngineConfiguration.getProcessInstanceHelper());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessInstanceHelper(ProcessInstanceHelper)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessInstanceHelper(ProcessInstanceHelper)}
   */
  @Test
  public void testSetProcessInstanceHelper_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getListenerNotificationHelper()}
   */
  @Test
  public void testGetListenerNotificationHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getListenerNotificationHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getListenerNotificationHelper()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getListenerNotificationHelper()}
   */
  @Test
  public void testGetListenerNotificationHelper_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getListenerNotificationHelper());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setListenerNotificationHelper(ListenerNotificationHelper)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setListenerNotificationHelper(ListenerNotificationHelper)}
   */
  @Test
  public void testSetListenerNotificationHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ListenerNotificationHelper listenerNotificationHelper = new ListenerNotificationHelper();

    // Act
    ProcessEngineConfigurationImpl actualSetListenerNotificationHelperResult = jtaProcessEngineConfiguration
        .setListenerNotificationHelper(listenerNotificationHelper);

    // Assert
    assertSame(listenerNotificationHelper, jtaProcessEngineConfiguration.getListenerNotificationHelper());
    assertSame(jtaProcessEngineConfiguration, actualSetListenerNotificationHelperResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setListenerNotificationHelper(ListenerNotificationHelper)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setListenerNotificationHelper(ListenerNotificationHelper)}
   */
  @Test
  public void testSetListenerNotificationHelper_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSqlSessionFactory()}
   */
  @Test
  public void testGetSqlSessionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSqlSessionFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSqlSessionFactory()}
   */
  @Test
  public void testGetSqlSessionFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSqlSessionFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSqlSessionFactory(SqlSessionFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSqlSessionFactory(SqlSessionFactory)}
   */
  @Test
  public void testSetSqlSessionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(new Configuration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setSqlSessionFactory(sqlSessionFactory));
    assertSame(sqlSessionFactory, jtaProcessEngineConfiguration.getSqlSessionFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSqlSessionFactory(SqlSessionFactory)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSqlSessionFactory(SqlSessionFactory)}
   */
  @Test
  public void testSetSqlSessionFactory_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDbSqlSessionFactory()}
   */
  @Test
  public void testGetDbSqlSessionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDbSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDbSqlSessionFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDbSqlSessionFactory()}
   */
  @Test
  public void testGetDbSqlSessionFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDbSqlSessionFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDbSqlSessionFactory(DbSqlSessionFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDbSqlSessionFactory(DbSqlSessionFactory)}
   */
  @Test
  public void testSetDbSqlSessionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDbSqlSessionFactory(dbSqlSessionFactory));
    assertSame(dbSqlSessionFactory, jtaProcessEngineConfiguration.getDbSqlSessionFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDbSqlSessionFactory(DbSqlSessionFactory)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDbSqlSessionFactory(DbSqlSessionFactory)}
   */
  @Test
  public void testSetDbSqlSessionFactory_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTransactionFactory()}
   */
  @Test
  public void testGetTransactionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getTransactionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTransactionFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTransactionFactory()}
   */
  @Test
  public void testGetTransactionFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTransactionFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTransactionFactory(TransactionFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTransactionFactory(TransactionFactory)}
   */
  @Test
  public void testSetTransactionFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTransactionFactory(transactionFactory));
    assertSame(transactionFactory, jtaProcessEngineConfiguration.getTransactionFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTransactionFactory(TransactionFactory)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTransactionFactory(TransactionFactory)}
   */
  @Test
  public void testSetTransactionFactory_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomSessionFactories()}
   */
  @Test
  public void testGetCustomSessionFactories() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomSessionFactories()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomSessionFactories()}
   */
  @Test
  public void testGetCustomSessionFactories_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}
   */
  @Test
  public void testSetCustomSessionFactories2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomSessionFactories(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomJobHandlers()}
   */
  @Test
  public void testGetCustomJobHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomJobHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomJobHandlers()}
   */
  @Test
  public void testGetCustomJobHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}
   */
  @Test
  public void testSetCustomJobHandlers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomJobHandlers(List)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}
   */
  @Test
  public void testGetCustomScriptingEngineClasses() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomScriptingEngineClasses());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}
   */
  @Test
  public void testGetCustomScriptingEngineClasses_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomScriptingEngineClasses());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}
   */
  @Test
  public void testSetCustomScriptingEngineClasses() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ArrayList<String> customScriptingEngineClasses = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomScriptingEngineClassesResult = jtaProcessEngineConfiguration
        .setCustomScriptingEngineClasses(customScriptingEngineClasses);

    // Assert
    assertSame(customScriptingEngineClasses, jtaProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomScriptingEngineClassesResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomScriptingEngineClasses(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPreVariableTypes()}
   */
  @Test
  public void testGetCustomPreVariableTypes() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomPreVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreVariableTypes()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPreVariableTypes()}
   */
  @Test
  public void testGetCustomPreVariableTypes_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPreVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}
   */
  @Test
  public void testSetCustomPreVariableTypes2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPreVariableTypes(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPostVariableTypes()}
   */
  @Test
  public void testGetCustomPostVariableTypes() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomPostVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostVariableTypes()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomPostVariableTypes()}
   */
  @Test
  public void testGetCustomPostVariableTypes_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomPostVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}
   */
  @Test
  public void testSetCustomPostVariableTypes2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   *   <li>Then return CustomPostVariableTypes is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomPostVariableTypes(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPreBpmnParseHandlers()}
   */
  @Test
  public void testGetPreBpmnParseHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getPreBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPreBpmnParseHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPreBpmnParseHandlers()}
   */
  @Test
  public void testGetPreBpmnParseHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getPreBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}
   */
  @Test
  public void testSetPreBpmnParseHandlers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPreBpmnParseHandlers(List)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}
   */
  @Test
  public void testGetCustomDefaultBpmnParseHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}
   */
  @Test
  public void testGetCustomDefaultBpmnParseHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomDefaultBpmnParseHandlers());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}
   */
  @Test
  public void testSetCustomDefaultBpmnParseHandlers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ArrayList<BpmnParseHandler> customDefaultBpmnParseHandlers = new ArrayList<>();

    // Act
    ProcessEngineConfigurationImpl actualSetCustomDefaultBpmnParseHandlersResult = jtaProcessEngineConfiguration
        .setCustomDefaultBpmnParseHandlers(customDefaultBpmnParseHandlers);

    // Assert
    assertSame(customDefaultBpmnParseHandlers, jtaProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetCustomDefaultBpmnParseHandlersResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}
   */
  @Test
  public void testSetCustomDefaultBpmnParseHandlers3() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomDefaultBpmnParseHandlers(List)}
   */
  @Test
  public void testSetCustomDefaultBpmnParseHandlers4() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPostBpmnParseHandlers()}
   */
  @Test
  public void testGetPostBpmnParseHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getPostBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPostBpmnParseHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPostBpmnParseHandlers()}
   */
  @Test
  public void testGetPostBpmnParseHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getPostBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}
   */
  @Test
  public void testSetPostBpmnParseHandlers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPostBpmnParseHandlers(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getActivityBehaviorFactory()}
   */
  @Test
  public void testGetActivityBehaviorFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getActivityBehaviorFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getActivityBehaviorFactory()}
   */
  @Test
  public void testGetActivityBehaviorFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getActivityBehaviorFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setActivityBehaviorFactory(ActivityBehaviorFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setActivityBehaviorFactory(ActivityBehaviorFactory)}
   */
  @Test
  public void testSetActivityBehaviorFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act
    ProcessEngineConfigurationImpl actualSetActivityBehaviorFactoryResult = jtaProcessEngineConfiguration
        .setActivityBehaviorFactory(activityBehaviorFactory);

    // Assert
    assertSame(activityBehaviorFactory, jtaProcessEngineConfiguration.getActivityBehaviorFactory());
    assertSame(jtaProcessEngineConfiguration, actualSetActivityBehaviorFactoryResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setActivityBehaviorFactory(ActivityBehaviorFactory)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setActivityBehaviorFactory(ActivityBehaviorFactory)}
   */
  @Test
  public void testSetActivityBehaviorFactory_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getListenerFactory()}
   */
  @Test
  public void testGetListenerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getListenerFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getListenerFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getListenerFactory()}
   */
  @Test
  public void testGetListenerFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getListenerFactory());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setListenerFactory(ListenerFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setListenerFactory(ListenerFactory)}
   */
  @Test
  public void testSetListenerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultListenerFactory listenerFactory = new DefaultListenerFactory();

    // Act
    ProcessEngineConfigurationImpl actualSetListenerFactoryResult = jtaProcessEngineConfiguration
        .setListenerFactory(listenerFactory);

    // Assert
    assertSame(listenerFactory, jtaProcessEngineConfiguration.getListenerFactory());
    assertSame(jtaProcessEngineConfiguration, actualSetListenerFactoryResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setListenerFactory(ListenerFactory)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setListenerFactory(ListenerFactory)}
   */
  @Test
  public void testSetListenerFactory_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setBpmnParseFactory(BpmnParseFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBpmnParseFactory(BpmnParseFactory)}
   */
  @Test
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
  public void testGetBeans() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getBeans());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBeans()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBeans()}
   */
  @Test
  public void testGetBeans_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getBeans());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBeans(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBeans(Map)}
   */
  @Test
  public void testSetBeans() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HashMap<Object, Object> beans = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetBeansResult = jtaProcessEngineConfiguration.setBeans(beans);

    // Assert
    assertSame(beans, jtaProcessEngineConfiguration.getBeans());
    assertSame(jtaProcessEngineConfiguration, actualSetBeansResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBeans(Map)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setBeans(Map)}
   */
  @Test
  public void testSetBeans_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getResolverFactories()}
   */
  @Test
  public void testGetResolverFactories() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getResolverFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResolverFactories()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getResolverFactories()}
   */
  @Test
  public void testGetResolverFactories_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getResolverFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}
   */
  @Test
  public void testSetResolverFactories() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}
   */
  @Test
  public void testSetResolverFactories_givenJtaProcessEngineConfiguration_whenArrayList() {
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
   * Test {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}.
   * <ul>
   *   <li>Given {@link ResolverFactory}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link ResolverFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setResolverFactories(List)}
   */
  @Test
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
   * Test {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}
   */
  @Test
  public void testGetCustomFunctionProviders_givenJtaProcessEngineConfiguration_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomFunctionProviders());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}
   */
  @Test
  public void testGetCustomFunctionProviders_thenReturnSizeIsOne() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    List<CustomFunctionProvider> actualCustomFunctionProviders = jtaProcessEngineConfiguration
        .getCustomFunctionProviders();

    // Assert
    assertEquals(1, actualCustomFunctionProviders.size());
    assertSame(jtaProcessEngineConfiguration.customFunctionProviders, actualCustomFunctionProviders);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}
   */
  @Test
  public void testSetCustomFunctionProviders() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}
   */
  @Test
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomFunctionProviders(List)}
   */
  @Test
  public void testSetCustomFunctionProviders_givenJtaProcessEngineConfiguration_whenArrayList() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
  public void testAddCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomFunctionProviders(new ArrayList<>());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class)));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeploymentManager()}
   */
  @Test
  public void testGetDeploymentManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDeploymentManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeploymentManager()}
   */
  @Test
  public void testGetDeploymentManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeploymentManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeploymentManager(DeploymentManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeploymentManager(DeploymentManager)}
   */
  @Test
  public void testSetDeploymentManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DeploymentManager deploymentManager = new DeploymentManager();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDeploymentManager(deploymentManager));
    assertSame(deploymentManager, jtaProcessEngineConfiguration.getDeploymentManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeploymentManager(DeploymentManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeploymentManager(DeploymentManager)}
   */
  @Test
  public void testSetDeploymentManager_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentManager deploymentManager = new DeploymentManager();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDeploymentManager(deploymentManager));
    assertSame(deploymentManager, jtaProcessEngineConfiguration.getDeploymentManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDelegateInterceptor(DelegateInterceptor)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDelegateInterceptor(DelegateInterceptor)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventHandler(String)}
   */
  @Test
  public void testGetEventHandler_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEventHandlers(new HashMap<>());
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventHandler("Event Type"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventHandlers(Map)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventHandlers(Map)}
   */
  @Test
  public void testSetEventHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HashMap<String, EventHandler> eventHandlers = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetEventHandlersResult = jtaProcessEngineConfiguration
        .setEventHandlers(eventHandlers);

    // Assert
    assertSame(eventHandlers, jtaProcessEngineConfiguration.getEventHandlers());
    assertSame(jtaProcessEngineConfiguration, actualSetEventHandlersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventHandlers(Map)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventHandlers(Map)}
   */
  @Test
  public void testSetEventHandlers_givenJtaProcessEngineConfiguration() {
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
  public void testGetEventHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventHandlers()}
   */
  @Test
  public void testGetEventHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}
   */
  @Test
  public void testGetCustomEventHandlers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCustomEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}
   */
  @Test
  public void testGetCustomEventHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCustomEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}
   */
  @Test
  public void testSetCustomEventHandlers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCustomEventHandlers(List)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setFailedJobCommandFactory(FailedJobCommandFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setFailedJobCommandFactory(FailedJobCommandFactory)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSource()}
   */
  @Test
  public void testGetIdGeneratorDataSource() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getIdGeneratorDataSource());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSource()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSource()}
   */
  @Test
  public void testGetIdGeneratorDataSource_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getIdGeneratorDataSource());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setIdGeneratorDataSource(DataSource)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setIdGeneratorDataSource(DataSource)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}
   */
  @Test
  public void testGetIdGeneratorDataSourceJndiName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}
   */
  @Test
  public void testGetIdGeneratorDataSourceJndiName_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getIdGeneratorDataSourceJndiName());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setIdGeneratorDataSourceJndiName(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setIdGeneratorDataSourceJndiName(String)}
   */
  @Test
  public void testSetIdGeneratorDataSourceJndiName() {
    // Arrange and Act
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getBatchSizeProcessInstances()}
   */
  @Test
  public void testGetBatchSizeProcessInstances() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(25, jtaProcessEngineConfiguration.getBatchSizeProcessInstances());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBatchSizeProcessInstances()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getBatchSizeProcessInstances()}
   */
  @Test
  public void testGetBatchSizeProcessInstances_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(25, (new JtaProcessEngineConfiguration()).getBatchSizeProcessInstances());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setBatchSizeProcessInstances(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBatchSizeProcessInstances(int)}
   */
  @Test
  public void testSetBatchSizeProcessInstances() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetBatchSizeProcessInstancesResult = jtaProcessEngineConfiguration
        .setBatchSizeProcessInstances(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertSame(jtaProcessEngineConfiguration, actualSetBatchSizeProcessInstancesResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setBatchSizeProcessInstances(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBatchSizeProcessInstances(int)}
   */
  @Test
  public void testSetBatchSizeProcessInstances_givenJtaProcessEngineConfiguration() {
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
  public void testGetBatchSizeTasks() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(25, jtaProcessEngineConfiguration.getBatchSizeTasks());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBatchSizeTasks()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getBatchSizeTasks()}
   */
  @Test
  public void testGetBatchSizeTasks_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(25, (new JtaProcessEngineConfiguration()).getBatchSizeTasks());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBatchSizeTasks(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBatchSizeTasks(int)}
   */
  @Test
  public void testSetBatchSizeTasks() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetBatchSizeTasksResult = jtaProcessEngineConfiguration.setBatchSizeTasks(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getBatchSizeTasks());
    assertSame(jtaProcessEngineConfiguration, actualSetBatchSizeTasksResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBatchSizeTasks(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBatchSizeTasks(int)}
   */
  @Test
  public void testSetBatchSizeTasks_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionCacheLimit()}
   */
  @Test
  public void testGetProcessDefinitionCacheLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(-1, jtaProcessEngineConfiguration.getProcessDefinitionCacheLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionCacheLimit()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionCacheLimit()}
   */
  @Test
  public void testGetProcessDefinitionCacheLimit_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(-1, (new JtaProcessEngineConfiguration()).getProcessDefinitionCacheLimit());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionCacheLimit(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionCacheLimit(int)}
   */
  @Test
  public void testSetProcessDefinitionCacheLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetProcessDefinitionCacheLimitResult = jtaProcessEngineConfiguration
        .setProcessDefinitionCacheLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetProcessDefinitionCacheLimitResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionCacheLimit(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionCacheLimit(int)}
   */
  @Test
  public void testSetProcessDefinitionCacheLimit_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionCache()}
   */
  @Test
  public void testGetProcessDefinitionCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessDefinitionCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionCache()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionCache()}
   */
  @Test
  public void testGetProcessDefinitionCache_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionCache());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionCache(DeploymentCache)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionCache(DeploymentCache)}
   */
  @Test
  public void testSetProcessDefinitionCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultDeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache = new DefaultDeploymentCache<>();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionCache(processDefinitionCache));
    assertSame(processDefinitionCache, jtaProcessEngineConfiguration.getProcessDefinitionCache());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionCache(DeploymentCache)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionCache(DeploymentCache)}
   */
  @Test
  public void testSetProcessDefinitionCache_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCacheLimit()}
   */
  @Test
  public void testGetKnowledgeBaseCacheLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(-1, jtaProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCacheLimit()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCacheLimit()}
   */
  @Test
  public void testGetKnowledgeBaseCacheLimit_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(-1, (new JtaProcessEngineConfiguration()).getKnowledgeBaseCacheLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCacheLimit(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCacheLimit(int)}
   */
  @Test
  public void testSetKnowledgeBaseCacheLimit() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetKnowledgeBaseCacheLimitResult = jtaProcessEngineConfiguration
        .setKnowledgeBaseCacheLimit(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertSame(jtaProcessEngineConfiguration, actualSetKnowledgeBaseCacheLimitResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCacheLimit(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCacheLimit(int)}
   */
  @Test
  public void testSetKnowledgeBaseCacheLimit_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCache()}
   */
  @Test
  public void testGetKnowledgeBaseCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getKnowledgeBaseCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCache()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCache()}
   */
  @Test
  public void testGetKnowledgeBaseCache_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getKnowledgeBaseCache());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCache(DeploymentCache)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCache(DeploymentCache)}
   */
  @Test
  public void testSetKnowledgeBaseCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultDeploymentCache<Object> knowledgeBaseCache = new DefaultDeploymentCache<>();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setKnowledgeBaseCache(knowledgeBaseCache));
    assertSame(knowledgeBaseCache, jtaProcessEngineConfiguration.getKnowledgeBaseCache());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCache(DeploymentCache)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setKnowledgeBaseCache(DeploymentCache)}
   */
  @Test
  public void testSetKnowledgeBaseCache_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableSafeBpmnXml()}
   */
  @Test
  public void testIsEnableSafeBpmnXml_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isEnableSafeBpmnXml());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableSafeBpmnXml()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableSafeBpmnXml()}
   */
  @Test
  public void testIsEnableSafeBpmnXml_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isEnableSafeBpmnXml());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableSafeBpmnXml()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableSafeBpmnXml()}
   */
  @Test
  public void testIsEnableSafeBpmnXml_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableSafeBpmnXml(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableSafeBpmnXml());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableSafeBpmnXml(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableSafeBpmnXml(boolean)}
   */
  @Test
  public void testSetEnableSafeBpmnXml() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetEnableSafeBpmnXmlResult = jtaProcessEngineConfiguration
        .setEnableSafeBpmnXml(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableSafeBpmnXmlResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableSafeBpmnXml(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableSafeBpmnXml(boolean)}
   */
  @Test
  public void testSetEnableSafeBpmnXml_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}
   */
  @Test
  public void testGetEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventDispatcher());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}
   */
  @Test
  public void testGetEventDispatcher_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventDispatcher());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventDispatcher(ActivitiEventDispatcher)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventDispatcher(ActivitiEventDispatcher)}
   */
  @Test
  public void testSetEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ActivitiEventDispatcherImpl eventDispatcher = new ActivitiEventDispatcherImpl();

    // Act
    ProcessEngineConfigurationImpl actualSetEventDispatcherResult = jtaProcessEngineConfiguration
        .setEventDispatcher(eventDispatcher);

    // Assert
    assertSame(eventDispatcher, jtaProcessEngineConfiguration.getEventDispatcher());
    assertSame(jtaProcessEngineConfiguration, actualSetEventDispatcherResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventDispatcher(ActivitiEventDispatcher)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventDispatcher(ActivitiEventDispatcher)}
   */
  @Test
  public void testSetEventDispatcher_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableEventDispatcher(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableEventDispatcher(boolean)}
   */
  @Test
  public void testSetEnableEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableEventDispatcher(true));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableEventDispatcher(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableEventDispatcher(boolean)}
   */
  @Test
  public void testSetEnableEventDispatcher_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableEventDispatcher(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}
   */
  @Test
  public void testGetTypedEventListeners() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getTypedEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}
   */
  @Test
  public void testGetTypedEventListeners_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTypedEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTypedEventListeners(Map)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTypedEventListeners(Map)}
   */
  @Test
  public void testSetTypedEventListeners() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HashMap<String, List<ActivitiEventListener>> typedListeners = new HashMap<>();

    // Act
    ProcessEngineConfigurationImpl actualSetTypedEventListenersResult = jtaProcessEngineConfiguration
        .setTypedEventListeners(typedListeners);

    // Assert
    assertSame(typedListeners, jtaProcessEngineConfiguration.getTypedEventListeners());
    assertSame(jtaProcessEngineConfiguration, actualSetTypedEventListenersResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setTypedEventListeners(Map)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTypedEventListeners(Map)}
   */
  @Test
  public void testSetTypedEventListeners_givenJtaProcessEngineConfiguration() {
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
  public void testGetEventListeners() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventListeners()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getEventListeners()}
   */
  @Test
  public void testGetEventListeners_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEventListeners(List)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventListeners(List)}
   */
  @Test
  public void testSetEventListeners() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor)
   * EventListeners is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventListeners(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventListeners(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventListeners(List)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessValidator()}
   */
  @Test
  public void testGetProcessValidator() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessValidator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessValidator()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessValidator()}
   */
  @Test
  public void testGetProcessValidator_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessValidator());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessValidator(ProcessValidator)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessValidator(ProcessValidator)}
   */
  @Test
  public void testSetProcessValidator() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ActivitiTestCaseProcessValidator processValidator = new ActivitiTestCaseProcessValidator();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setProcessValidator(processValidator));
    assertSame(processValidator, jtaProcessEngineConfiguration.getProcessValidator());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessValidator(ProcessValidator)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessValidator(ProcessValidator)}
   */
  @Test
  public void testSetProcessValidator_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableEventDispatcher()}
   */
  @Test
  public void testIsEnableEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableEventDispatcher());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableEventDispatcher()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableEventDispatcher()}
   */
  @Test
  public void testIsEnableEventDispatcher_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isEnableEventDispatcher());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}
   */
  @Test
  public void testIsEnableDatabaseEventLogging_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isEnableDatabaseEventLogging());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}
   */
  @Test
  public void testIsEnableDatabaseEventLogging_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isEnableDatabaseEventLogging());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableDatabaseEventLogging()}
   */
  @Test
  public void testIsEnableDatabaseEventLogging_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableDatabaseEventLogging(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableDatabaseEventLogging());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableDatabaseEventLogging(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableDatabaseEventLogging(boolean)}
   */
  @Test
  public void testSetEnableDatabaseEventLogging() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetEnableDatabaseEventLoggingResult = jtaProcessEngineConfiguration
        .setEnableDatabaseEventLogging(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableDatabaseEventLoggingResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableDatabaseEventLogging(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableDatabaseEventLogging(boolean)}
   */
  @Test
  public void testSetEnableDatabaseEventLogging_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMaxLengthStringVariableType()}
   */
  @Test
  public void testGetMaxLengthStringVariableType() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(-1, jtaProcessEngineConfiguration.getMaxLengthStringVariableType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthStringVariableType()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMaxLengthStringVariableType()}
   */
  @Test
  public void testGetMaxLengthStringVariableType_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(-1, (new JtaProcessEngineConfiguration()).getMaxLengthStringVariableType());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setMaxLengthStringVariableType(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setMaxLengthStringVariableType(int)}
   */
  @Test
  public void testSetMaxLengthStringVariableType() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetMaxLengthStringVariableTypeResult = jtaProcessEngineConfiguration
        .setMaxLengthStringVariableType(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getMaxLengthString());
    assertEquals(3, jtaProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertSame(jtaProcessEngineConfiguration, actualSetMaxLengthStringVariableTypeResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setMaxLengthStringVariableType(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setMaxLengthStringVariableType(int)}
   */
  @Test
  public void testSetMaxLengthStringVariableType_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isBulkInsertEnabled()}
   */
  @Test
  public void testIsBulkInsertEnabled() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isBulkInsertEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isBulkInsertEnabled()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isBulkInsertEnabled()}
   */
  @Test
  public void testIsBulkInsertEnabled_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isBulkInsertEnabled());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBulkInsertEnabled(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBulkInsertEnabled(boolean)}
   */
  @Test
  public void testSetBulkInsertEnabled() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setBulkInsertEnabled(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setBulkInsertEnabled(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setBulkInsertEnabled(boolean)}
   */
  @Test
  public void testSetBulkInsertEnabled_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setBulkInsertEnabled(true));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}
   */
  @Test
  public void testGetMaxNrOfStatementsInBulkInsert() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(100, jtaProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}
   */
  @Test
  public void testGetMaxNrOfStatementsInBulkInsert_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(100, (new JtaProcessEngineConfiguration()).getMaxNrOfStatementsInBulkInsert());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setMaxNrOfStatementsInBulkInsert(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setMaxNrOfStatementsInBulkInsert(int)}
   */
  @Test
  public void testSetMaxNrOfStatementsInBulkInsert() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetMaxNrOfStatementsInBulkInsertResult = jtaProcessEngineConfiguration
        .setMaxNrOfStatementsInBulkInsert(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertSame(jtaProcessEngineConfiguration, actualSetMaxNrOfStatementsInBulkInsertResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setMaxNrOfStatementsInBulkInsert(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setMaxNrOfStatementsInBulkInsert(int)}
   */
  @Test
  public void testSetMaxNrOfStatementsInBulkInsert_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isUsingRelationalDatabase()}
   */
  @Test
  public void testIsUsingRelationalDatabase() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isUsingRelationalDatabase());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isUsingRelationalDatabase()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isUsingRelationalDatabase()}
   */
  @Test
  public void testIsUsingRelationalDatabase_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isUsingRelationalDatabase());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setUsingRelationalDatabase(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setUsingRelationalDatabase(boolean)}
   */
  @Test
  public void testSetUsingRelationalDatabase() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setUsingRelationalDatabase(true));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setUsingRelationalDatabase(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setUsingRelationalDatabase(boolean)}
   */
  @Test
  public void testSetUsingRelationalDatabase_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setUsingRelationalDatabase(true));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}
   */
  @Test
  public void testIsEnableVerboseExecutionTreeLogging_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isEnableVerboseExecutionTreeLogging());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}
   */
  @Test
  public void testIsEnableVerboseExecutionTreeLogging_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableVerboseExecutionTreeLogging()}
   */
  @Test
  public void testIsEnableVerboseExecutionTreeLogging_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableVerboseExecutionTreeLogging(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableVerboseExecutionTreeLogging(boolean)}
   */
  @Test
  public void testSetEnableVerboseExecutionTreeLogging() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetEnableVerboseExecutionTreeLoggingResult = jtaProcessEngineConfiguration
        .setEnableVerboseExecutionTreeLogging(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableVerboseExecutionTreeLoggingResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableVerboseExecutionTreeLogging(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableVerboseExecutionTreeLogging(boolean)}
   */
  @Test
  public void testSetEnableVerboseExecutionTreeLogging_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableEagerExecutionTreeFetching(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableEagerExecutionTreeFetching(boolean)}
   */
  @Test
  public void testSetEnableEagerExecutionTreeFetching() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableEagerExecutionTreeFetching(true));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableEagerExecutionTreeFetching(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableEagerExecutionTreeFetching(boolean)}
   */
  @Test
  public void testSetEnableEagerExecutionTreeFetching_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableEagerExecutionTreeFetching(true));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableExecutionRelationshipCounts(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableExecutionRelationshipCounts(boolean)}
   */
  @Test
  public void testSetEnableExecutionRelationshipCounts() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableExecutionRelationshipCounts(true));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEnableExecutionRelationshipCounts(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableExecutionRelationshipCounts(boolean)}
   */
  @Test
  public void testSetEnableExecutionRelationshipCounts_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableExecutionRelationshipCounts(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}
   */
  @Test
  public void testGetPerformanceSettings() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration.performanceSettings,
        jtaProcessEngineConfiguration.getPerformanceSettings());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}
   */
  @Test
  public void testGetPerformanceSettings_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration.performanceSettings,
        jtaProcessEngineConfiguration.getPerformanceSettings());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setPerformanceSettings(PerformanceSettings)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPerformanceSettings(PerformanceSettings)}
   */
  @Test
  public void testSetPerformanceSettings() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Test
   * {@link ProcessEngineConfigurationImpl#setPerformanceSettings(PerformanceSettings)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPerformanceSettings(PerformanceSettings)}
   */
  @Test
  public void testSetPerformanceSettings_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableLocalization(boolean)}
   */
  @Test
  public void testSetEnableLocalization() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableLocalization(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setEnableLocalization(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEnableLocalization(boolean)}
   */
  @Test
  public void testSetEnableLocalization_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setEnableLocalization(true));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}
   */
  @Test
  public void testGetAttachmentDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getAttachmentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}
   */
  @Test
  public void testGetAttachmentDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAttachmentDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAttachmentDataManager(AttachmentDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAttachmentDataManager(AttachmentDataManager)}
   */
  @Test
  public void testSetAttachmentDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisAttachmentDataManager attachmentDataManager = new MybatisAttachmentDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAttachmentDataManager(attachmentDataManager));
    assertSame(attachmentDataManager, jtaProcessEngineConfiguration.getAttachmentDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAttachmentDataManager(AttachmentDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAttachmentDataManager(AttachmentDataManager)}
   */
  @Test
  public void testSetAttachmentDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getByteArrayDataManager()}
   */
  @Test
  public void testGetByteArrayDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getByteArrayDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getByteArrayDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getByteArrayDataManager()}
   */
  @Test
  public void testGetByteArrayDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getByteArrayDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setByteArrayDataManager(ByteArrayDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setByteArrayDataManager(ByteArrayDataManager)}
   */
  @Test
  public void testSetByteArrayDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisByteArrayDataManager byteArrayDataManager = new MybatisByteArrayDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setByteArrayDataManager(byteArrayDataManager));
    assertSame(byteArrayDataManager, jtaProcessEngineConfiguration.getByteArrayDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setByteArrayDataManager(ByteArrayDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setByteArrayDataManager(ByteArrayDataManager)}
   */
  @Test
  public void testSetByteArrayDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommentDataManager()}
   */
  @Test
  public void testGetCommentDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCommentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommentDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommentDataManager()}
   */
  @Test
  public void testGetCommentDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommentDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommentDataManager(CommentDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommentDataManager(CommentDataManager)}
   */
  @Test
  public void testSetCommentDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisCommentDataManager commentDataManager = new MybatisCommentDataManager(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setCommentDataManager(commentDataManager));
    assertSame(commentDataManager, jtaProcessEngineConfiguration.getCommentDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommentDataManager(CommentDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommentDataManager(CommentDataManager)}
   */
  @Test
  public void testSetCommentDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeploymentDataManager()}
   */
  @Test
  public void testGetDeploymentDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDeploymentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeploymentDataManager()}
   */
  @Test
  public void testGetDeploymentDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeploymentDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeploymentDataManager(DeploymentDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeploymentDataManager(DeploymentDataManager)}
   */
  @Test
  public void testSetDeploymentDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisDeploymentDataManager deploymentDataManager = new MybatisDeploymentDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDeploymentDataManager(deploymentDataManager));
    assertSame(deploymentDataManager, jtaProcessEngineConfiguration.getDeploymentDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeploymentDataManager(DeploymentDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeploymentDataManager(DeploymentDataManager)}
   */
  @Test
  public void testSetDeploymentDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventLogEntryDataManager()}
   */
  @Test
  public void testGetEventLogEntryDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventLogEntryDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventLogEntryDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventLogEntryDataManager()}
   */
  @Test
  public void testGetEventLogEntryDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventLogEntryDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventLogEntryDataManager(EventLogEntryDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventLogEntryDataManager(EventLogEntryDataManager)}
   */
  @Test
  public void testSetEventLogEntryDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisEventLogEntryDataManager eventLogEntryDataManager = new MybatisEventLogEntryDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setEventLogEntryDataManager(eventLogEntryDataManager));
    assertSame(eventLogEntryDataManager, jtaProcessEngineConfiguration.getEventLogEntryDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventLogEntryDataManager(EventLogEntryDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventLogEntryDataManager(EventLogEntryDataManager)}
   */
  @Test
  public void testSetEventLogEntryDataManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}
   */
  @Test
  public void testGetEventSubscriptionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventSubscriptionDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}
   */
  @Test
  public void testGetEventSubscriptionDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventSubscriptionDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionDataManager(EventSubscriptionDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionDataManager(EventSubscriptionDataManager)}
   */
  @Test
  public void testSetEventSubscriptionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisEventSubscriptionDataManager eventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setEventSubscriptionDataManager(eventSubscriptionDataManager));
    assertSame(eventSubscriptionDataManager, jtaProcessEngineConfiguration.getEventSubscriptionDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionDataManager(EventSubscriptionDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionDataManager(EventSubscriptionDataManager)}
   */
  @Test
  public void testSetEventSubscriptionDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getExecutionDataManager()}
   */
  @Test
  public void testGetExecutionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getExecutionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getExecutionDataManager()}
   */
  @Test
  public void testGetExecutionDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getExecutionDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setExecutionDataManager(ExecutionDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setExecutionDataManager(ExecutionDataManager)}
   */
  @Test
  public void testSetExecutionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisExecutionDataManager executionDataManager = new MybatisExecutionDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setExecutionDataManager(executionDataManager));
    assertSame(executionDataManager, jtaProcessEngineConfiguration.getExecutionDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setExecutionDataManager(ExecutionDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setExecutionDataManager(ExecutionDataManager)}
   */
  @Test
  public void testSetExecutionDataManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricActivityInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)}
   */
  @Test
  public void testSetHistoricActivityInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisHistoricActivityInstanceDataManager historicActivityInstanceDataManager = new MybatisHistoricActivityInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricActivityInstanceDataManager(historicActivityInstanceDataManager));
    assertSame(historicActivityInstanceDataManager,
        jtaProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)}
   */
  @Test
  public void testSetHistoricActivityInstanceDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricDetailDataManager()}
   */
  @Test
  public void testGetHistoricDetailDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricDetailDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricDetailDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricDetailDataManager()}
   */
  @Test
  public void testGetHistoricDetailDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricDetailDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricDetailDataManager(HistoricDetailDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricDetailDataManager(HistoricDetailDataManager)}
   */
  @Test
  public void testSetHistoricDetailDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisHistoricDetailDataManager historicDetailDataManager = new MybatisHistoricDetailDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricDetailDataManager(historicDetailDataManager));
    assertSame(historicDetailDataManager, jtaProcessEngineConfiguration.getHistoricDetailDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricDetailDataManager(HistoricDetailDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricDetailDataManager(HistoricDetailDataManager)}
   */
  @Test
  public void testSetHistoricDetailDataManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricIdentityLinkDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)}
   */
  @Test
  public void testSetHistoricIdentityLinkDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisHistoricIdentityLinkDataManager historicIdentityLinkDataManager = new MybatisHistoricIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricIdentityLinkDataManager(historicIdentityLinkDataManager));
    assertSame(historicIdentityLinkDataManager, jtaProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)}
   */
  @Test
  public void testSetHistoricIdentityLinkDataManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricProcessInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)}
   */
  @Test
  public void testSetHistoricProcessInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisHistoricProcessInstanceDataManager historicProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricProcessInstanceDataManager(historicProcessInstanceDataManager));
    assertSame(historicProcessInstanceDataManager,
        jtaProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)}
   */
  @Test
  public void testSetHistoricProcessInstanceDataManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricTaskInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)}
   */
  @Test
  public void testSetHistoricTaskInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisHistoricTaskInstanceDataManager historicTaskInstanceDataManager = new MybatisHistoricTaskInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricTaskInstanceDataManager(historicTaskInstanceDataManager));
    assertSame(historicTaskInstanceDataManager, jtaProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)}
   */
  @Test
  public void testSetHistoricTaskInstanceDataManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricVariableInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)}
   */
  @Test
  public void testSetHistoricVariableInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisHistoricVariableInstanceDataManager historicVariableInstanceDataManager = new MybatisHistoricVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricVariableInstanceDataManager(historicVariableInstanceDataManager));
    assertSame(historicVariableInstanceDataManager,
        jtaProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)}
   */
  @Test
  public void testSetHistoricVariableInstanceDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIdentityLinkDataManager()}
   */
  @Test
  public void testGetIdentityLinkDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdentityLinkDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIdentityLinkDataManager()}
   */
  @Test
  public void testGetIdentityLinkDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getIdentityLinkDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setIdentityLinkDataManager(IdentityLinkDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setIdentityLinkDataManager(IdentityLinkDataManager)}
   */
  @Test
  public void testSetIdentityLinkDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisIdentityLinkDataManager identityLinkDataManager = new MybatisIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setIdentityLinkDataManager(identityLinkDataManager));
    assertSame(identityLinkDataManager, jtaProcessEngineConfiguration.getIdentityLinkDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setIdentityLinkDataManager(IdentityLinkDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setIdentityLinkDataManager(IdentityLinkDataManager)}
   */
  @Test
  public void testSetIdentityLinkDataManager_givenJtaProcessEngineConfiguration() {
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
  public void testGetJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getJobDataManager()}
   */
  @Test
  public void testGetJobDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJobDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setJobDataManager(JobDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setJobDataManager(JobDataManager)}
   */
  @Test
  public void testSetJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisJobDataManager jobDataManager = new MybatisJobDataManager(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setJobDataManager(jobDataManager));
    assertSame(jobDataManager, jtaProcessEngineConfiguration.getJobDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setJobDataManager(JobDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setJobDataManager(JobDataManager)}
   */
  @Test
  public void testSetJobDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTimerJobDataManager()}
   */
  @Test
  public void testGetTimerJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getTimerJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerJobDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTimerJobDataManager()}
   */
  @Test
  public void testGetTimerJobDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTimerJobDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTimerJobDataManager(TimerJobDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTimerJobDataManager(TimerJobDataManager)}
   */
  @Test
  public void testSetTimerJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisTimerJobDataManager timerJobDataManager = new MybatisTimerJobDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setTimerJobDataManager(timerJobDataManager));
    assertSame(timerJobDataManager, jtaProcessEngineConfiguration.getTimerJobDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTimerJobDataManager(TimerJobDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTimerJobDataManager(TimerJobDataManager)}
   */
  @Test
  public void testSetTimerJobDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSuspendedJobDataManager()}
   */
  @Test
  public void testGetSuspendedJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getSuspendedJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSuspendedJobDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSuspendedJobDataManager()}
   */
  @Test
  public void testGetSuspendedJobDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSuspendedJobDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSuspendedJobDataManager(SuspendedJobDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSuspendedJobDataManager(SuspendedJobDataManager)}
   */
  @Test
  public void testSetSuspendedJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisSuspendedJobDataManager suspendedJobDataManager = new MybatisSuspendedJobDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setSuspendedJobDataManager(suspendedJobDataManager));
    assertSame(suspendedJobDataManager, jtaProcessEngineConfiguration.getSuspendedJobDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSuspendedJobDataManager(SuspendedJobDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSuspendedJobDataManager(SuspendedJobDataManager)}
   */
  @Test
  public void testSetSuspendedJobDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeadLetterJobDataManager()}
   */
  @Test
  public void testGetDeadLetterJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDeadLetterJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeadLetterJobDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeadLetterJobDataManager()}
   */
  @Test
  public void testGetDeadLetterJobDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeadLetterJobDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeadLetterJobDataManager(DeadLetterJobDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeadLetterJobDataManager(DeadLetterJobDataManager)}
   */
  @Test
  public void testSetDeadLetterJobDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisDeadLetterJobDataManager deadLetterJobDataManager = new MybatisDeadLetterJobDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDeadLetterJobDataManager(deadLetterJobDataManager));
    assertSame(deadLetterJobDataManager, jtaProcessEngineConfiguration.getDeadLetterJobDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeadLetterJobDataManager(DeadLetterJobDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeadLetterJobDataManager(DeadLetterJobDataManager)}
   */
  @Test
  public void testSetDeadLetterJobDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getModelDataManager()}
   */
  @Test
  public void testGetModelDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getModelDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getModelDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getModelDataManager()}
   */
  @Test
  public void testGetModelDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getModelDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setModelDataManager(ModelDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setModelDataManager(ModelDataManager)}
   */
  @Test
  public void testSetModelDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisModelDataManager modelDataManager = new MybatisModelDataManager(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setModelDataManager(modelDataManager));
    assertSame(modelDataManager, jtaProcessEngineConfiguration.getModelDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setModelDataManager(ModelDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setModelDataManager(ModelDataManager)}
   */
  @Test
  public void testSetModelDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisModelDataManager modelDataManager = new MybatisModelDataManager(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setModelDataManager(modelDataManager));
    assertSame(modelDataManager, jtaProcessEngineConfiguration.getModelDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}
   */
  @Test
  public void testGetProcessDefinitionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessDefinitionDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}
   */
  @Test
  public void testGetProcessDefinitionDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionDataManager(ProcessDefinitionDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionDataManager(ProcessDefinitionDataManager)}
   */
  @Test
  public void testSetProcessDefinitionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisProcessDefinitionDataManager processDefinitionDataManager = new MybatisProcessDefinitionDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionDataManager(processDefinitionDataManager));
    assertSame(processDefinitionDataManager, jtaProcessEngineConfiguration.getProcessDefinitionDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionDataManager(ProcessDefinitionDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionDataManager(ProcessDefinitionDataManager)}
   */
  @Test
  public void testSetProcessDefinitionDataManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionInfoDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoDataManager(ProcessDefinitionInfoDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoDataManager(ProcessDefinitionInfoDataManager)}
   */
  @Test
  public void testSetProcessDefinitionInfoDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisProcessDefinitionInfoDataManager processDefinitionInfoDataManager = new MybatisProcessDefinitionInfoDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionInfoDataManager(processDefinitionInfoDataManager));
    assertSame(processDefinitionInfoDataManager, jtaProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoDataManager(ProcessDefinitionInfoDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoDataManager(ProcessDefinitionInfoDataManager)}
   */
  @Test
  public void testSetProcessDefinitionInfoDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPropertyDataManager()}
   */
  @Test
  public void testGetPropertyDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getPropertyDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPropertyDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPropertyDataManager()}
   */
  @Test
  public void testGetPropertyDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getPropertyDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setPropertyDataManager(PropertyDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPropertyDataManager(PropertyDataManager)}
   */
  @Test
  public void testSetPropertyDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisPropertyDataManager propertyDataManager = new MybatisPropertyDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setPropertyDataManager(propertyDataManager));
    assertSame(propertyDataManager, jtaProcessEngineConfiguration.getPropertyDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setPropertyDataManager(PropertyDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPropertyDataManager(PropertyDataManager)}
   */
  @Test
  public void testSetPropertyDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getResourceDataManager()}
   */
  @Test
  public void testGetResourceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getResourceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResourceDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getResourceDataManager()}
   */
  @Test
  public void testGetResourceDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getResourceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setResourceDataManager(ResourceDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setResourceDataManager(ResourceDataManager)}
   */
  @Test
  public void testSetResourceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisResourceDataManager resourceDataManager = new MybatisResourceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setResourceDataManager(resourceDataManager));
    assertSame(resourceDataManager, jtaProcessEngineConfiguration.getResourceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setResourceDataManager(ResourceDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setResourceDataManager(ResourceDataManager)}
   */
  @Test
  public void testSetResourceDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTaskDataManager()}
   */
  @Test
  public void testGetTaskDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getTaskDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTaskDataManager()}
   */
  @Test
  public void testGetTaskDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTaskDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTaskDataManager(TaskDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTaskDataManager(TaskDataManager)}
   */
  @Test
  public void testSetTaskDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisTaskDataManager taskDataManager = new MybatisTaskDataManager(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTaskDataManager(taskDataManager));
    assertSame(taskDataManager, jtaProcessEngineConfiguration.getTaskDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTaskDataManager(TaskDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTaskDataManager(TaskDataManager)}
   */
  @Test
  public void testSetTaskDataManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getVariableInstanceDataManager()}
   */
  @Test
  public void testGetVariableInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableInstanceDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getVariableInstanceDataManager()}
   */
  @Test
  public void testGetVariableInstanceDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getVariableInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setVariableInstanceDataManager(VariableInstanceDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setVariableInstanceDataManager(VariableInstanceDataManager)}
   */
  @Test
  public void testSetVariableInstanceDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisVariableInstanceDataManager variableInstanceDataManager = new MybatisVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setVariableInstanceDataManager(variableInstanceDataManager));
    assertSame(variableInstanceDataManager, jtaProcessEngineConfiguration.getVariableInstanceDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setVariableInstanceDataManager(VariableInstanceDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setVariableInstanceDataManager(VariableInstanceDataManager)}
   */
  @Test
  public void testSetVariableInstanceDataManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#isEnableConfiguratorServiceLoader()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableConfiguratorServiceLoader()}
   */
  @Test
  public void testIsEnableConfiguratorServiceLoader() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableConfiguratorServiceLoader());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isEnableConfiguratorServiceLoader()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isEnableConfiguratorServiceLoader()}
   */
  @Test
  public void testIsEnableConfiguratorServiceLoader_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isEnableConfiguratorServiceLoader());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}
   */
  @Test
  public void testGetAttachmentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getAttachmentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}
   */
  @Test
  public void testGetAttachmentEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAttachmentEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAttachmentEntityManager(AttachmentEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAttachmentEntityManager(AttachmentEntityManager)}
   */
  @Test
  public void testSetAttachmentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManager = new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAttachmentEntityManager(attachmentEntityManager));
    assertSame(attachmentEntityManager, jtaProcessEngineConfiguration.getAttachmentEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAttachmentEntityManager(AttachmentEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAttachmentEntityManager(AttachmentEntityManager)}
   */
  @Test
  public void testSetAttachmentEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getByteArrayEntityManager()}
   */
  @Test
  public void testGetByteArrayEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getByteArrayEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getByteArrayEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getByteArrayEntityManager()}
   */
  @Test
  public void testGetByteArrayEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getByteArrayEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setByteArrayEntityManager(ByteArrayEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setByteArrayEntityManager(ByteArrayEntityManager)}
   */
  @Test
  public void testSetByteArrayEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ByteArrayEntityManagerImpl byteArrayEntityManager = new ByteArrayEntityManagerImpl(processEngineConfiguration,
        new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setByteArrayEntityManager(byteArrayEntityManager));
    assertSame(byteArrayEntityManager, jtaProcessEngineConfiguration.getByteArrayEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setByteArrayEntityManager(ByteArrayEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setByteArrayEntityManager(ByteArrayEntityManager)}
   */
  @Test
  public void testSetByteArrayEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}
   */
  @Test
  public void testGetCommentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getCommentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}
   */
  @Test
  public void testGetCommentEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getCommentEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommentEntityManager(CommentEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommentEntityManager(CommentEntityManager)}
   */
  @Test
  public void testSetCommentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommentEntityManagerImpl commentEntityManager = new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setCommentEntityManager(commentEntityManager));
    assertSame(commentEntityManager, jtaProcessEngineConfiguration.getCommentEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setCommentEntityManager(CommentEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setCommentEntityManager(CommentEntityManager)}
   */
  @Test
  public void testSetCommentEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeploymentEntityManager()}
   */
  @Test
  public void testGetDeploymentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDeploymentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeploymentEntityManager()}
   */
  @Test
  public void testGetDeploymentEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeploymentEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeploymentEntityManager(DeploymentEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeploymentEntityManager(DeploymentEntityManager)}
   */
  @Test
  public void testSetDeploymentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentEntityManagerImpl deploymentEntityManager = new DeploymentEntityManagerImpl(processEngineConfiguration,
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDeploymentEntityManager(deploymentEntityManager));
    assertSame(deploymentEntityManager, jtaProcessEngineConfiguration.getDeploymentEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeploymentEntityManager(DeploymentEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeploymentEntityManager(DeploymentEntityManager)}
   */
  @Test
  public void testSetDeploymentEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventLogEntryEntityManager()}
   */
  @Test
  public void testGetEventLogEntryEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventLogEntryEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventLogEntryEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventLogEntryEntityManager()}
   */
  @Test
  public void testGetEventLogEntryEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventLogEntryEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventLogEntryEntityManager(EventLogEntryEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventLogEntryEntityManager(EventLogEntryEntityManager)}
   */
  @Test
  public void testSetEventLogEntryEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventLogEntryEntityManagerImpl eventLogEntryEntityManager = new EventLogEntryEntityManagerImpl(
        processEngineConfiguration, new MybatisEventLogEntryDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setEventLogEntryEntityManager(eventLogEntryEntityManager));
    assertSame(eventLogEntryEntityManager, jtaProcessEngineConfiguration.getEventLogEntryEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventLogEntryEntityManager(EventLogEntryEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventLogEntryEntityManager(EventLogEntryEntityManager)}
   */
  @Test
  public void testSetEventLogEntryEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}
   */
  @Test
  public void testGetEventSubscriptionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getEventSubscriptionEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}
   */
  @Test
  public void testGetEventSubscriptionEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getEventSubscriptionEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionEntityManager(EventSubscriptionEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionEntityManager(EventSubscriptionEntityManager)}
   */
  @Test
  public void testSetEventSubscriptionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManager = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setEventSubscriptionEntityManager(eventSubscriptionEntityManager));
    assertSame(eventSubscriptionEntityManager, jtaProcessEngineConfiguration.getEventSubscriptionEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionEntityManager(EventSubscriptionEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionEntityManager(EventSubscriptionEntityManager)}
   */
  @Test
  public void testSetEventSubscriptionEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getExecutionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getExecutionEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setExecutionEntityManager(ExecutionEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setExecutionEntityManager(ExecutionEntityManager)}
   */
  @Test
  public void testSetExecutionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManager = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setExecutionEntityManager(executionEntityManager));
    assertSame(executionEntityManager, jtaProcessEngineConfiguration.getExecutionEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setExecutionEntityManager(ExecutionEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setExecutionEntityManager(ExecutionEntityManager)}
   */
  @Test
  public void testSetExecutionEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceEntityManager(HistoricActivityInstanceEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceEntityManager(HistoricActivityInstanceEntityManager)}
   */
  @Test
  public void testSetHistoricActivityInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceEntityManager(HistoricActivityInstanceEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricActivityInstanceEntityManager(HistoricActivityInstanceEntityManager)}
   */
  @Test
  public void testSetHistoricActivityInstanceEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricDetailEntityManager()}
   */
  @Test
  public void testGetHistoricDetailEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricDetailEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricDetailEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricDetailEntityManager()}
   */
  @Test
  public void testGetHistoricDetailEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricDetailEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricDetailEntityManager(HistoricDetailEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricDetailEntityManager(HistoricDetailEntityManager)}
   */
  @Test
  public void testSetHistoricDetailEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricDetailEntityManagerImpl historicDetailEntityManager = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricDetailDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricDetailEntityManager(historicDetailEntityManager));
    assertSame(historicDetailEntityManager, jtaProcessEngineConfiguration.getHistoricDetailEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricDetailEntityManager(HistoricDetailEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricDetailEntityManager(HistoricDetailEntityManager)}
   */
  @Test
  public void testSetHistoricDetailEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkEntityManager(HistoricIdentityLinkEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkEntityManager(HistoricIdentityLinkEntityManager)}
   */
  @Test
  public void testSetHistoricIdentityLinkEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManager = new HistoricIdentityLinkEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricIdentityLinkDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricIdentityLinkEntityManager(historicIdentityLinkEntityManager));
    assertSame(historicIdentityLinkEntityManager, jtaProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkEntityManager(HistoricIdentityLinkEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricIdentityLinkEntityManager(HistoricIdentityLinkEntityManager)}
   */
  @Test
  public void testSetHistoricIdentityLinkEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceEntityManager(HistoricProcessInstanceEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceEntityManager(HistoricProcessInstanceEntityManager)}
   */
  @Test
  public void testSetHistoricProcessInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceEntityManager(HistoricProcessInstanceEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricProcessInstanceEntityManager(HistoricProcessInstanceEntityManager)}
   */
  @Test
  public void testSetHistoricProcessInstanceEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceEntityManager(HistoricTaskInstanceEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceEntityManager(HistoricTaskInstanceEntityManager)}
   */
  @Test
  public void testSetHistoricTaskInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManager = new HistoricTaskInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setHistoricTaskInstanceEntityManager(historicTaskInstanceEntityManager));
    assertSame(historicTaskInstanceEntityManager, jtaProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceEntityManager(HistoricTaskInstanceEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricTaskInstanceEntityManager(HistoricTaskInstanceEntityManager)}
   */
  @Test
  public void testSetHistoricTaskInstanceEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceEntityManager(HistoricVariableInstanceEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceEntityManager(HistoricVariableInstanceEntityManager)}
   */
  @Test
  public void testSetHistoricVariableInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceEntityManager(HistoricVariableInstanceEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoricVariableInstanceEntityManager(HistoricVariableInstanceEntityManager)}
   */
  @Test
  public void testSetHistoricVariableInstanceEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}
   */
  @Test
  public void testGetIdentityLinkEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}
   */
  @Test
  public void testGetIdentityLinkEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getIdentityLinkEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setIdentityLinkEntityManager(IdentityLinkEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setIdentityLinkEntityManager(IdentityLinkEntityManager)}
   */
  @Test
  public void testSetIdentityLinkEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    IdentityLinkEntityManagerImpl identityLinkEntityManager = new IdentityLinkEntityManagerImpl(
        processEngineConfiguration, new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setIdentityLinkEntityManager(identityLinkEntityManager));
    assertSame(identityLinkEntityManager, jtaProcessEngineConfiguration.getIdentityLinkEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setIdentityLinkEntityManager(IdentityLinkEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setIdentityLinkEntityManager(IdentityLinkEntityManager)}
   */
  @Test
  public void testSetIdentityLinkEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getJobEntityManager()}
   */
  @Test
  public void testGetJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getJobEntityManager()}
   */
  @Test
  public void testGetJobEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJobEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setJobEntityManager(JobEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setJobEntityManager(JobEntityManager)}
   */
  @Test
  public void testSetJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager = new JobEntityManagerImpl(processEngineConfiguration,
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setJobEntityManager(jobEntityManager));
    assertSame(jobEntityManager, jtaProcessEngineConfiguration.getJobEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setJobEntityManager(JobEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setJobEntityManager(JobEntityManager)}
   */
  @Test
  public void testSetJobEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}
   */
  @Test
  public void testGetTimerJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getTimerJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}
   */
  @Test
  public void testGetTimerJobEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTimerJobEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTimerJobEntityManager(TimerJobEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTimerJobEntityManager(TimerJobEntityManager)}
   */
  @Test
  public void testSetTimerJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManager = new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setTimerJobEntityManager(timerJobEntityManager));
    assertSame(timerJobEntityManager, jtaProcessEngineConfiguration.getTimerJobEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTimerJobEntityManager(TimerJobEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTimerJobEntityManager(TimerJobEntityManager)}
   */
  @Test
  public void testSetTimerJobEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSuspendedJobEntityManager()}
   */
  @Test
  public void testGetSuspendedJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getSuspendedJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSuspendedJobEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getSuspendedJobEntityManager()}
   */
  @Test
  public void testGetSuspendedJobEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getSuspendedJobEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSuspendedJobEntityManager(SuspendedJobEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSuspendedJobEntityManager(SuspendedJobEntityManager)}
   */
  @Test
  public void testSetSuspendedJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManager = new SuspendedJobEntityManagerImpl(
        processEngineConfiguration, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setSuspendedJobEntityManager(suspendedJobEntityManager));
    assertSame(suspendedJobEntityManager, jtaProcessEngineConfiguration.getSuspendedJobEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setSuspendedJobEntityManager(SuspendedJobEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setSuspendedJobEntityManager(SuspendedJobEntityManager)}
   */
  @Test
  public void testSetSuspendedJobEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeadLetterJobEntityManager()}
   */
  @Test
  public void testGetDeadLetterJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDeadLetterJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeadLetterJobEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDeadLetterJobEntityManager()}
   */
  @Test
  public void testGetDeadLetterJobEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDeadLetterJobEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeadLetterJobEntityManager(DeadLetterJobEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeadLetterJobEntityManager(DeadLetterJobEntityManager)}
   */
  @Test
  public void testSetDeadLetterJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManager = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setDeadLetterJobEntityManager(deadLetterJobEntityManager));
    assertSame(deadLetterJobEntityManager, jtaProcessEngineConfiguration.getDeadLetterJobEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDeadLetterJobEntityManager(DeadLetterJobEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDeadLetterJobEntityManager(DeadLetterJobEntityManager)}
   */
  @Test
  public void testSetDeadLetterJobEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getModelEntityManager()}
   */
  @Test
  public void testGetModelEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getModelEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getModelEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getModelEntityManager()}
   */
  @Test
  public void testGetModelEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getModelEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setModelEntityManager(ModelEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setModelEntityManager(ModelEntityManager)}
   */
  @Test
  public void testSetModelEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ModelEntityManagerImpl modelEntityManager = new ModelEntityManagerImpl(processEngineConfiguration,
        new MybatisModelDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setModelEntityManager(modelEntityManager));
    assertSame(modelEntityManager, jtaProcessEngineConfiguration.getModelEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setModelEntityManager(ModelEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setModelEntityManager(ModelEntityManager)}
   */
  @Test
  public void testSetModelEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessDefinitionEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionEntityManager(ProcessDefinitionEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionEntityManager(ProcessDefinitionEntityManager)}
   */
  @Test
  public void testSetProcessDefinitionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager = new ProcessDefinitionEntityManagerImpl(
        processEngineConfiguration, new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessDefinitionEntityManager(processDefinitionEntityManager));
    assertSame(processDefinitionEntityManager, jtaProcessEngineConfiguration.getProcessDefinitionEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionEntityManager(ProcessDefinitionEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionEntityManager(ProcessDefinitionEntityManager)}
   */
  @Test
  public void testSetProcessDefinitionEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessDefinitionInfoEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionInfoEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoEntityManager(ProcessDefinitionInfoEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoEntityManager(ProcessDefinitionInfoEntityManager)}
   */
  @Test
  public void testSetProcessDefinitionInfoEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoEntityManager(ProcessDefinitionInfoEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionInfoEntityManager(ProcessDefinitionInfoEntityManager)}
   */
  @Test
  public void testSetProcessDefinitionInfoEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPropertyEntityManager()}
   */
  @Test
  public void testGetPropertyEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getPropertyEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPropertyEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getPropertyEntityManager()}
   */
  @Test
  public void testGetPropertyEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getPropertyEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setPropertyEntityManager(PropertyEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPropertyEntityManager(PropertyEntityManager)}
   */
  @Test
  public void testSetPropertyEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    PropertyEntityManagerImpl propertyEntityManager = new PropertyEntityManagerImpl(processEngineConfiguration,
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setPropertyEntityManager(propertyEntityManager));
    assertSame(propertyEntityManager, jtaProcessEngineConfiguration.getPropertyEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setPropertyEntityManager(PropertyEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setPropertyEntityManager(PropertyEntityManager)}
   */
  @Test
  public void testSetPropertyEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getResourceEntityManager()}
   */
  @Test
  public void testGetResourceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getResourceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResourceEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getResourceEntityManager()}
   */
  @Test
  public void testGetResourceEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getResourceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setResourceEntityManager(ResourceEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setResourceEntityManager(ResourceEntityManager)}
   */
  @Test
  public void testSetResourceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager = new ResourceEntityManagerImpl(processEngineConfiguration,
        new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setResourceEntityManager(resourceEntityManager));
    assertSame(resourceEntityManager, jtaProcessEngineConfiguration.getResourceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setResourceEntityManager(ResourceEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setResourceEntityManager(ResourceEntityManager)}
   */
  @Test
  public void testSetResourceEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}
   */
  @Test
  public void testGetTaskEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getTaskEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}
   */
  @Test
  public void testGetTaskEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTaskEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTaskEntityManager(TaskEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTaskEntityManager(TaskEntityManager)}
   */
  @Test
  public void testSetTaskEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TaskEntityManagerImpl taskEntityManager = new TaskEntityManagerImpl(processEngineConfiguration,
        new MybatisTaskDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTaskEntityManager(taskEntityManager));
    assertSame(taskEntityManager, jtaProcessEngineConfiguration.getTaskEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTaskEntityManager(TaskEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTaskEntityManager(TaskEntityManager)}
   */
  @Test
  public void testSetTaskEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}
   */
  @Test
  public void testGetVariableInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getVariableInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}
   */
  @Test
  public void testGetVariableInstanceEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getVariableInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setVariableInstanceEntityManager(VariableInstanceEntityManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setVariableInstanceEntityManager(VariableInstanceEntityManager)}
   */
  @Test
  public void testSetVariableInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManager = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setVariableInstanceEntityManager(variableInstanceEntityManager));
    assertSame(variableInstanceEntityManager, jtaProcessEngineConfiguration.getVariableInstanceEntityManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setVariableInstanceEntityManager(VariableInstanceEntityManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setVariableInstanceEntityManager(VariableInstanceEntityManager)}
   */
  @Test
  public void testSetVariableInstanceEntityManager_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTableDataManager()}
   */
  @Test
  public void testGetTableDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getTableDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTableDataManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getTableDataManager()}
   */
  @Test
  public void testGetTableDataManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getTableDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTableDataManager(TableDataManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTableDataManager(TableDataManager)}
   */
  @Test
  public void testSetTableDataManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TableDataManagerImpl tableDataManager = new TableDataManagerImpl(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTableDataManager(tableDataManager));
    assertSame(tableDataManager, jtaProcessEngineConfiguration.getTableDataManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setTableDataManager(TableDataManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setTableDataManager(TableDataManager)}
   */
  @Test
  public void testSetTableDataManager_givenJtaProcessEngineConfiguration() {
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
  public void testGetHistoryManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoryManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoryManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getHistoryManager()}
   */
  @Test
  public void testGetHistoryManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoryManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoryManager(HistoryManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoryManager(HistoryManager)}
   */
  @Test
  public void testSetHistoryManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultHistoryManager historyManager = new DefaultHistoryManager(new JtaProcessEngineConfiguration(),
        HistoryLevel.NONE);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setHistoryManager(historyManager));
    assertSame(historyManager, jtaProcessEngineConfiguration.getHistoryManager());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setHistoryManager(HistoryManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setHistoryManager(HistoryManager)}
   */
  @Test
  public void testSetHistoryManager_givenJtaProcessEngineConfiguration() {
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
  public void testGetJobManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJobManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getJobManager()}
   */
  @Test
  public void testGetJobManager_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJobManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setJobManager(JobManager)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setJobManager(JobManager)}
   */
  @Test
  public void testSetJobManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultJobManager jobManager = new DefaultJobManager();

    // Act
    ProcessEngineConfigurationImpl actualSetJobManagerResult = jtaProcessEngineConfiguration.setJobManager(jobManager);

    // Assert
    assertSame(jobManager, jtaProcessEngineConfiguration.getJobManager());
    assertSame(jtaProcessEngineConfiguration, actualSetJobManagerResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setJobManager(JobManager)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setJobManager(JobManager)}
   */
  @Test
  public void testSetJobManager_givenJtaProcessEngineConfiguration() {
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
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setClock(Clock)}
   */
  @Test
  public void testSetClock() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setClock(clock));
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Clock
   * is {@link DefaultClockImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#setClock(Clock)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}
   */
  @Test
  public void testGetDelegateExpressionFieldInjectionMode() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(DelegateExpressionFieldInjectionMode.MIXED,
        jtaProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}
   */
  @Test
  public void testGetDelegateExpressionFieldInjectionMode_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(DelegateExpressionFieldInjectionMode.MIXED,
        (new JtaProcessEngineConfiguration()).getDelegateExpressionFieldInjectionMode());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDelegateExpressionFieldInjectionMode(DelegateExpressionFieldInjectionMode)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDelegateExpressionFieldInjectionMode(DelegateExpressionFieldInjectionMode)}
   */
  @Test
  public void testSetDelegateExpressionFieldInjectionMode() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetDelegateExpressionFieldInjectionModeResult = jtaProcessEngineConfiguration
        .setDelegateExpressionFieldInjectionMode(DelegateExpressionFieldInjectionMode.COMPATIBILITY);

    // Assert
    assertEquals(DelegateExpressionFieldInjectionMode.COMPATIBILITY,
        jtaProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
    assertSame(jtaProcessEngineConfiguration, actualSetDelegateExpressionFieldInjectionModeResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setDelegateExpressionFieldInjectionMode(DelegateExpressionFieldInjectionMode)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setDelegateExpressionFieldInjectionMode(DelegateExpressionFieldInjectionMode)}
   */
  @Test
  public void testSetDelegateExpressionFieldInjectionMode_givenJtaProcessEngineConfiguration() {
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
  public void testGetObjectMapper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration.objectMapper, jtaProcessEngineConfiguration.getObjectMapper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfigurationImpl#getObjectMapper()}
   */
  @Test
  public void testGetObjectMapper_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration.objectMapper, jtaProcessEngineConfiguration.getObjectMapper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setObjectMapper(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setObjectMapper(ObjectMapper)}
   */
  @Test
  public void testSetObjectMapper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    ProcessEngineConfigurationImpl actualSetObjectMapperResult = jtaProcessEngineConfiguration
        .setObjectMapper(objectMapper);

    // Assert
    assertSame(objectMapper, jtaProcessEngineConfiguration.getObjectMapper());
    assertSame(jtaProcessEngineConfiguration, actualSetObjectMapperResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setObjectMapper(ObjectMapper)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setObjectMapper(ObjectMapper)}
   */
  @Test
  public void testSetObjectMapper_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    ProcessEngineConfigurationImpl actualSetObjectMapperResult = jtaProcessEngineConfiguration
        .setObjectMapper(objectMapper);

    // Assert
    assertSame(objectMapper, jtaProcessEngineConfiguration.getObjectMapper());
    assertSame(jtaProcessEngineConfiguration, actualSetObjectMapperResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}
   */
  @Test
  public void testGetAsyncExecutorCorePoolSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(2, jtaProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}
   */
  @Test
  public void testGetAsyncExecutorCorePoolSize_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(2, (new JtaProcessEngineConfiguration()).getAsyncExecutorCorePoolSize());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorCorePoolSize(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorCorePoolSize(int)}
   */
  @Test
  public void testSetAsyncExecutorCorePoolSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorCorePoolSizeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorCorePoolSize(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorCorePoolSizeResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorCorePoolSize(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorCorePoolSize(int)}
   */
  @Test
  public void testSetAsyncExecutorCorePoolSize_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}
   */
  @Test
  public void testGetAsyncExecutorNumberOfRetries() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(3, jtaProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}
   */
  @Test
  public void testGetAsyncExecutorNumberOfRetries_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(3, (new JtaProcessEngineConfiguration()).getAsyncExecutorNumberOfRetries());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorNumberOfRetries(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorNumberOfRetries(int)}
   */
  @Test
  public void testSetAsyncExecutorNumberOfRetries() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorNumberOfRetriesResult = jtaProcessEngineConfiguration
        .setAsyncExecutorNumberOfRetries(10);

    // Assert
    assertEquals(10, jtaProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorNumberOfRetriesResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorNumberOfRetries(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorNumberOfRetries(int)}
   */
  @Test
  public void testSetAsyncExecutorNumberOfRetries_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxPoolSize()}
   */
  @Test
  public void testGetAsyncExecutorMaxPoolSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(10, jtaProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxPoolSize()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxPoolSize()}
   */
  @Test
  public void testGetAsyncExecutorMaxPoolSize_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(10, (new JtaProcessEngineConfiguration()).getAsyncExecutorMaxPoolSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxPoolSize(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxPoolSize(int)}
   */
  @Test
  public void testSetAsyncExecutorMaxPoolSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorMaxPoolSizeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorMaxPoolSize(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorMaxPoolSizeResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxPoolSize(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxPoolSize(int)}
   */
  @Test
  public void testSetAsyncExecutorMaxPoolSize_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}
   */
  @Test
  public void testGetAsyncExecutorThreadKeepAliveTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(5000L, jtaProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}
   */
  @Test
  public void testGetAsyncExecutorThreadKeepAliveTime_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(5000L, (new JtaProcessEngineConfiguration()).getAsyncExecutorThreadKeepAliveTime());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadKeepAliveTime(long)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadKeepAliveTime(long)}
   */
  @Test
  public void testSetAsyncExecutorThreadKeepAliveTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadKeepAliveTimeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadKeepAliveTime(1L);

    // Assert
    assertEquals(1L, jtaProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorThreadKeepAliveTimeResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadKeepAliveTime(long)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadKeepAliveTime(long)}
   */
  @Test
  public void testSetAsyncExecutorThreadKeepAliveTime_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}
   */
  @Test
  public void testGetAsyncExecutorThreadPoolQueueSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(100, jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}
   */
  @Test
  public void testGetAsyncExecutorThreadPoolQueueSize_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(100, (new JtaProcessEngineConfiguration()).getAsyncExecutorThreadPoolQueueSize());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueueSize(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueueSize(int)}
   */
  @Test
  public void testSetAsyncExecutorThreadPoolQueueSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueSizeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueueSize(3);

    // Assert
    assertEquals(3, jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorThreadPoolQueueSizeResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueueSize(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueueSize(int)}
   */
  @Test
  public void testSetAsyncExecutorThreadPoolQueueSize_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}
   */
  @Test
  public void testGetAsyncExecutorThreadPoolQueue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}
   */
  @Test
  public void testGetAsyncExecutorThreadPoolQueue_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAsyncExecutorThreadPoolQueue());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}
   */
  @Test
  public void testSetAsyncExecutorThreadPoolQueue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueue(null);

    // Assert
    ObjectMapper objectMapper = actualSetAsyncExecutorThreadPoolQueueResult.getObjectMapper();
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    Collection<? extends Deployer> defaultDeployers = actualSetAsyncExecutorThreadPoolQueueResult.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetAsyncExecutorThreadPoolQueueResult instanceof JtaProcessEngineConfiguration);
    assertNull(jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualSetAsyncExecutorThreadPoolQueueResult.getAsyncExecutorThreadPoolQueue());
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    Locale locale = deserializationConfig.getLocale();
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    BpmnDeployer bpmnDeployer = actualSetAsyncExecutorThreadPoolQueueResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSetAsyncExecutorThreadPoolQueueResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}.
   * <ul>
   *   <li>Given {@link Runnable}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}
   */
  @Test
  public void testSetAsyncExecutorThreadPoolQueue_givenRunnable() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    LinkedBlockingDeque<Runnable> asyncExecutorThreadPoolQueue = new LinkedBlockingDeque<>();
    asyncExecutorThreadPoolQueue.add(mock(Runnable.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueue(asyncExecutorThreadPoolQueue);

    // Assert
    ObjectMapper objectMapper = actualSetAsyncExecutorThreadPoolQueueResult.getObjectMapper();
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    Collection<? extends Deployer> defaultDeployers = actualSetAsyncExecutorThreadPoolQueueResult.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetAsyncExecutorThreadPoolQueueResult instanceof JtaProcessEngineConfiguration);
    assertSame(asyncExecutorThreadPoolQueue, jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertSame(asyncExecutorThreadPoolQueue,
        actualSetAsyncExecutorThreadPoolQueueResult.getAsyncExecutorThreadPoolQueue());
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    Locale locale = deserializationConfig.getLocale();
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    BpmnDeployer bpmnDeployer = actualSetAsyncExecutorThreadPoolQueueResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSetAsyncExecutorThreadPoolQueueResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}.
   * <ul>
   *   <li>Given {@link Runnable}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}
   */
  @Test
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
    ObjectMapper objectMapper = actualSetAsyncExecutorThreadPoolQueueResult.getObjectMapper();
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    Collection<? extends Deployer> defaultDeployers = actualSetAsyncExecutorThreadPoolQueueResult.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetAsyncExecutorThreadPoolQueueResult instanceof JtaProcessEngineConfiguration);
    assertSame(asyncExecutorThreadPoolQueue, jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertSame(asyncExecutorThreadPoolQueue,
        actualSetAsyncExecutorThreadPoolQueueResult.getAsyncExecutorThreadPoolQueue());
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    Locale locale = deserializationConfig.getLocale();
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    BpmnDeployer bpmnDeployer = actualSetAsyncExecutorThreadPoolQueueResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSetAsyncExecutorThreadPoolQueueResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}.
   * <ul>
   *   <li>Then return CustomFunctionProviders size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}
   */
  @Test
  public void testSetAsyncExecutorThreadPoolQueue_thenReturnCustomFunctionProvidersSizeIsOne() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueue(null);

    // Assert
    ObjectMapper objectMapper = actualSetAsyncExecutorThreadPoolQueueResult.getObjectMapper();
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    Collection<? extends Deployer> defaultDeployers = actualSetAsyncExecutorThreadPoolQueueResult.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetAsyncExecutorThreadPoolQueueResult instanceof JtaProcessEngineConfiguration);
    assertNull(jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualSetAsyncExecutorThreadPoolQueueResult.getAsyncExecutorThreadPoolQueue());
    assertEquals(1, actualSetAsyncExecutorThreadPoolQueueResult.getCustomFunctionProviders().size());
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    Locale locale = deserializationConfig.getLocale();
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    BpmnDeployer bpmnDeployer = actualSetAsyncExecutorThreadPoolQueueResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSetAsyncExecutorThreadPoolQueueResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}.
   * <ul>
   *   <li>When {@link LinkedBlockingDeque#LinkedBlockingDeque()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorThreadPoolQueue(BlockingQueue)}
   */
  @Test
  public void testSetAsyncExecutorThreadPoolQueue_whenLinkedBlockingDeque() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    LinkedBlockingDeque<Runnable> asyncExecutorThreadPoolQueue = new LinkedBlockingDeque<>();

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorThreadPoolQueueResult = jtaProcessEngineConfiguration
        .setAsyncExecutorThreadPoolQueue(asyncExecutorThreadPoolQueue);

    // Assert
    ObjectMapper objectMapper = actualSetAsyncExecutorThreadPoolQueueResult.getObjectMapper();
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    Collection<? extends Deployer> defaultDeployers = actualSetAsyncExecutorThreadPoolQueueResult.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertTrue(actualSetAsyncExecutorThreadPoolQueueResult instanceof JtaProcessEngineConfiguration);
    assertSame(asyncExecutorThreadPoolQueue, jtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertSame(asyncExecutorThreadPoolQueue,
        actualSetAsyncExecutorThreadPoolQueueResult.getAsyncExecutorThreadPoolQueue());
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    Locale locale = deserializationConfig.getLocale();
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    BpmnDeployer bpmnDeployer = actualSetAsyncExecutorThreadPoolQueueResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSetAsyncExecutorThreadPoolQueueResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}
   */
  @Test
  public void testGetAsyncExecutorSecondsToWaitOnShutdown() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(60L, jtaProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}
   */
  @Test
  public void testGetAsyncExecutorSecondsToWaitOnShutdown_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(60L, (new JtaProcessEngineConfiguration()).getAsyncExecutorSecondsToWaitOnShutdown());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorSecondsToWaitOnShutdown(long)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorSecondsToWaitOnShutdown(long)}
   */
  @Test
  public void testSetAsyncExecutorSecondsToWaitOnShutdown() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorSecondsToWaitOnShutdownResult = jtaProcessEngineConfiguration
        .setAsyncExecutorSecondsToWaitOnShutdown(1L);

    // Assert
    assertEquals(1L, jtaProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorSecondsToWaitOnShutdownResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorSecondsToWaitOnShutdown(long)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorSecondsToWaitOnShutdown(long)}
   */
  @Test
  public void testSetAsyncExecutorSecondsToWaitOnShutdown_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}
   */
  @Test
  public void testGetAsyncExecutorMaxTimerJobsPerAcquisition() {
    // Arrange, Act and Assert
    assertEquals(1, (new JtaProcessEngineConfiguration()).getAsyncExecutorMaxTimerJobsPerAcquisition());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}
   */
  @Test
  public void testGetAsyncExecutorMaxTimerJobsPerAcquisition2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxTimerJobsPerAcquisition(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxTimerJobsPerAcquisition(int)}
   */
  @Test
  public void testSetAsyncExecutorMaxTimerJobsPerAcquisition() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAsyncExecutorMaxTimerJobsPerAcquisition(1));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxTimerJobsPerAcquisition(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxTimerJobsPerAcquisition(int)}
   */
  @Test
  public void testSetAsyncExecutorMaxTimerJobsPerAcquisition2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAsyncExecutorMaxTimerJobsPerAcquisition(1));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  public void testGetAsyncExecutorMaxAsyncJobsDuePerAcquisition() {
    // Arrange, Act and Assert
    assertEquals(1, (new JtaProcessEngineConfiguration()).getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  public void testGetAsyncExecutorMaxAsyncJobsDuePerAcquisition2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxAsyncJobsDuePerAcquisition(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  public void testSetAsyncExecutorMaxAsyncJobsDuePerAcquisition() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAsyncExecutorMaxAsyncJobsDuePerAcquisition(1));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxAsyncJobsDuePerAcquisition(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  public void testSetAsyncExecutorMaxAsyncJobsDuePerAcquisition2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAsyncExecutorMaxAsyncJobsDuePerAcquisition(1));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}
   */
  @Test
  public void testGetAsyncExecutorDefaultTimerJobAcquireWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10000, (new JtaProcessEngineConfiguration()).getAsyncExecutorDefaultTimerJobAcquireWaitTime());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}
   */
  @Test
  public void testGetAsyncExecutorDefaultTimerJobAcquireWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(10000, jtaProcessEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultTimerJobAcquireWaitTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultTimerJobAcquireWaitTime(int)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultTimerJobAcquireWaitTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultTimerJobAcquireWaitTime(int)}
   */
  @Test
  public void testSetAsyncExecutorDefaultTimerJobAcquireWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorDefaultTimerJobAcquireWaitTimeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorDefaultTimerJobAcquireWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorDefaultTimerJobAcquireWaitTimeResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}
   */
  @Test
  public void testGetAsyncExecutorDefaultAsyncJobAcquireWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10000, (new JtaProcessEngineConfiguration()).getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}
   */
  @Test
  public void testGetAsyncExecutorDefaultAsyncJobAcquireWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(10000, jtaProcessEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultAsyncJobAcquireWaitTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultAsyncJobAcquireWaitTime(int)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultAsyncJobAcquireWaitTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultAsyncJobAcquireWaitTime(int)}
   */
  @Test
  public void testSetAsyncExecutorDefaultAsyncJobAcquireWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorDefaultAsyncJobAcquireWaitTimeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorDefaultAsyncJobAcquireWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorDefaultAsyncJobAcquireWaitTimeResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}
   */
  @Test
  public void testGetAsyncExecutorDefaultQueueSizeFullWaitTime() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getAsyncExecutorDefaultQueueSizeFullWaitTime());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}
   */
  @Test
  public void testGetAsyncExecutorDefaultQueueSizeFullWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultQueueSizeFullWaitTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultQueueSizeFullWaitTime(int)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultQueueSizeFullWaitTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorDefaultQueueSizeFullWaitTime(int)}
   */
  @Test
  public void testSetAsyncExecutorDefaultQueueSizeFullWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorLockOwner()}
   */
  @Test
  public void testGetAsyncExecutorLockOwner() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getAsyncExecutorLockOwner());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorLockOwner()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorLockOwner()}
   */
  @Test
  public void testGetAsyncExecutorLockOwner_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAsyncExecutorLockOwner());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorLockOwner(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorLockOwner(String)}
   */
  @Test
  public void testSetAsyncExecutorLockOwner() {
    // Arrange and Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorLockOwnerResult = jtaProcessEngineConfiguration
        .setAsyncExecutorLockOwner("Async Executor Lock Owner");

    // Assert
    assertEquals("Async Executor Lock Owner", jtaProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorLockOwnerResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}
   */
  @Test
  public void testGetAsyncExecutorTimerLockTimeInMillis() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(300000, jtaProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}
   */
  @Test
  public void testGetAsyncExecutorTimerLockTimeInMillis_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(300000, (new JtaProcessEngineConfiguration()).getAsyncExecutorTimerLockTimeInMillis());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorTimerLockTimeInMillis(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorTimerLockTimeInMillis(int)}
   */
  @Test
  public void testSetAsyncExecutorTimerLockTimeInMillis() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorTimerLockTimeInMillisResult = jtaProcessEngineConfiguration
        .setAsyncExecutorTimerLockTimeInMillis(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorTimerLockTimeInMillisResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorTimerLockTimeInMillis(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorTimerLockTimeInMillis(int)}
   */
  @Test
  public void testSetAsyncExecutorTimerLockTimeInMillis_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}
   */
  @Test
  public void testGetAsyncExecutorAsyncJobLockTimeInMillis() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(300000, jtaProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}
   */
  @Test
  public void testGetAsyncExecutorAsyncJobLockTimeInMillis_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(300000, (new JtaProcessEngineConfiguration()).getAsyncExecutorAsyncJobLockTimeInMillis());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorAsyncJobLockTimeInMillis(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorAsyncJobLockTimeInMillis(int)}
   */
  @Test
  public void testSetAsyncExecutorAsyncJobLockTimeInMillis() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorAsyncJobLockTimeInMillisResult = jtaProcessEngineConfiguration
        .setAsyncExecutorAsyncJobLockTimeInMillis(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorAsyncJobLockTimeInMillisResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorAsyncJobLockTimeInMillis(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorAsyncJobLockTimeInMillis(int)}
   */
  @Test
  public void testSetAsyncExecutorAsyncJobLockTimeInMillis_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}
   */
  @Test
  public void testGetAsyncExecutorResetExpiredJobsInterval() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(60000, jtaProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}
   */
  @Test
  public void testGetAsyncExecutorResetExpiredJobsInterval_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(60000, (new JtaProcessEngineConfiguration()).getAsyncExecutorResetExpiredJobsInterval());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsInterval(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsInterval(int)}
   */
  @Test
  public void testSetAsyncExecutorResetExpiredJobsInterval() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorResetExpiredJobsIntervalResult = jtaProcessEngineConfiguration
        .setAsyncExecutorResetExpiredJobsInterval(42);

    // Assert
    assertEquals(42, jtaProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorResetExpiredJobsIntervalResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsInterval(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsInterval(int)}
   */
  @Test
  public void testSetAsyncExecutorResetExpiredJobsInterval_givenJtaProcessEngineConfiguration() {
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
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)}
   */
  @Test
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
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}
   */
  @Test
  public void testGetAsyncExecutorResetExpiredJobsPageSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(3, jtaProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}
   */
  @Test
  public void testGetAsyncExecutorResetExpiredJobsPageSize_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(3, (new JtaProcessEngineConfiguration()).getAsyncExecutorResetExpiredJobsPageSize());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsPageSize(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsPageSize(int)}
   */
  @Test
  public void testSetAsyncExecutorResetExpiredJobsPageSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAsyncExecutorResetExpiredJobsPageSize(3));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsPageSize(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorResetExpiredJobsPageSize(int)}
   */
  @Test
  public void testSetAsyncExecutorResetExpiredJobsPageSize_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setAsyncExecutorResetExpiredJobsPageSize(3));
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}
   */
  @Test
  public void testIsAsyncExecutorIsMessageQueueMode_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isAsyncExecutorIsMessageQueueMode());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}
   */
  @Test
  public void testIsAsyncExecutorIsMessageQueueMode_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isAsyncExecutorIsMessageQueueMode()}
   */
  @Test
  public void testIsAsyncExecutorIsMessageQueueMode_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorMessageQueueMode(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMessageQueueMode(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMessageQueueMode(boolean)}
   */
  @Test
  public void testSetAsyncExecutorMessageQueueMode() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfigurationImpl actualSetAsyncExecutorMessageQueueModeResult = jtaProcessEngineConfiguration
        .setAsyncExecutorMessageQueueMode(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorMessageQueueModeResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMessageQueueMode(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setAsyncExecutorMessageQueueMode(boolean)}
   */
  @Test
  public void testSetAsyncExecutorMessageQueueMode_givenJtaProcessEngineConfiguration() {
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isRollbackDeployment()}
   */
  @Test
  public void testIsRollbackDeployment_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isRollbackDeployment());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isRollbackDeployment()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isRollbackDeployment()}
   */
  @Test
  public void testIsRollbackDeployment_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isRollbackDeployment());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#isRollbackDeployment()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#isRollbackDeployment()}
   */
  @Test
  public void testIsRollbackDeployment_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setRollbackDeployment(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isRollbackDeployment());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setRollbackDeployment(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setRollbackDeployment(boolean)}
   */
  @Test
  public void testSetRollbackDeployment() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    jtaProcessEngineConfiguration.setRollbackDeployment(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isRollbackDeployment());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#setRollbackDeployment(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setRollbackDeployment(boolean)}
   */
  @Test
  public void testSetRollbackDeployment_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.setRollbackDeployment(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isRollbackDeployment());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionPayloadMappingProvider(EventSubscriptionPayloadMappingProvider)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setEventSubscriptionPayloadMappingProvider(EventSubscriptionPayloadMappingProvider)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionHelper()}
   */
  @Test
  public void testGetProcessDefinitionHelper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessDefinitionHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionHelper()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#getProcessDefinitionHelper()}
   */
  @Test
  public void testGetProcessDefinitionHelper_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessDefinitionHelper());
  }

  /**
   * Test
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionHelper(ProcessDefinitionHelper)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfigurationImpl#setProcessDefinitionHelper(ProcessDefinitionHelper)}
   */
  @Test
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
