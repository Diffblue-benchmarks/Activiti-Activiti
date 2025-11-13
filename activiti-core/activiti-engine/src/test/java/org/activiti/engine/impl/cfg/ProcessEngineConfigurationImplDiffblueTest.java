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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.GroovyClassLoader;
import jakarta.transaction.TransactionManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import javax.xml.namespace.QName;
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
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgendaFactory;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.multitenant.ExecutorPerTenantAsyncExecutor;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeploymentHelper;
import org.activiti.engine.impl.bpmn.deployer.CachingAndArtifactsManager;
import org.activiti.engine.impl.bpmn.deployer.EventSubscriptionManager;
import org.activiti.engine.impl.bpmn.deployer.ParsedDeploymentBuilderFactory;
import org.activiti.engine.impl.bpmn.deployer.TimerManager;
import org.activiti.engine.impl.bpmn.helper.DefaultClassDelegateFactory;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
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
import org.activiti.engine.impl.calendar.MapBusinessCalendarManager;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextFactory;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.CommandInvoker;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.impl.interceptor.JtaTransactionInterceptor;
import org.activiti.engine.impl.interceptor.LogInterceptor;
import org.activiti.engine.impl.interceptor.TransactionContextInterceptor;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntity;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntityImpl;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManager;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ProcessEngineConfigurationImplDiffblueTest {
  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors() {
    // Arrange
    ArrayList<CommandInterceptor> commandInterceptors = new ArrayList<>();
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
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setDefaultCommandConfig(new CommandConfig());
    multiSchemaMultiTenantProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    CommandContextInterceptor commandInvoker = new CommandContextInterceptor();
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInvoker(commandInvoker);
    multiSchemaMultiTenantProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(commandExecutor);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutors();

    // Assert that nothing has changed
    CommandExecutor commandExecutor2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor2 instanceof CommandExecutorImpl);
    CommandInterceptor commandInvoker2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker2 instanceof CommandContextInterceptor);
    CommandConfig defaultCommandConfig =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    CommandConfig schemaCommandConfig =
        multiSchemaMultiTenantProcessEngineConfiguration.getSchemaCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, schemaCommandConfig.getTransactionPropagation());
    assertTrue(defaultCommandConfig.isContextReusePossible());
    assertTrue(schemaCommandConfig.isContextReusePossible());
    assertSame(commandExecutor, commandExecutor2);
    assertSame(commandInvoker, commandInvoker2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors2() {
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

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    CommandConfig defaultCommandConfig = new CommandConfig();
    multiSchemaMultiTenantProcessEngineConfiguration.setDefaultCommandConfig(defaultCommandConfig);
    multiSchemaMultiTenantProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInvoker(
        new CommandContextInterceptor());
    multiSchemaMultiTenantProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutors();

    // Assert
    CommandExecutor commandExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    List<CommandInterceptor> commandInterceptors2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
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
    assertSame(defaultCommandConfig, commandExecutor.getDefaultConfig());
    assertSame(commandContextInterceptor, ((CommandExecutorImpl) commandExecutor).getFirst());
    assertSame(commandContextInterceptor2, getResult.getNext());
    assertSame(commandContextInterceptor3, getResult2.getNext());
    assertSame(commandContextInterceptor6, getResult3.getNext());
    assertSame(commandContextInterceptor4, next);
    assertSame(commandContextInterceptor5, getResult5.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    jtaProcessEngineConfiguration.initCommandExecutors();

    // Assert
    CommandExecutor commandExecutor = jtaProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    CommandInterceptor commandInvoker = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof CommandInvoker);
    List<CommandInterceptor> commandInterceptors =
        jtaProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    assertTrue(commandInterceptors.get(1) instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult = commandInterceptors.get(0);
    assertTrue(getResult instanceof LogInterceptor);
    assertNull(commandInvoker.getNext());
    CommandConfig schemaCommandConfig = jtaProcessEngineConfiguration.getSchemaCommandConfig();
    assertEquals(
        TransactionPropagation.NOT_SUPPORTED, schemaCommandConfig.getTransactionPropagation());
    CommandConfig defaultCommandConfig = jtaProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    assertFalse(schemaCommandConfig.isContextReusePossible());
    assertTrue(defaultCommandConfig.isContextReusePossible());
    assertSame(defaultCommandConfig, commandExecutor.getDefaultConfig());
    assertSame(commandInvoker, commandInterceptors.get(2));
    assertSame(getResult, ((CommandExecutorImpl) commandExecutor).getFirst());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors4() {
    // Arrange
    ArrayList<CommandInterceptor> commandInterceptors = new ArrayList<>();
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
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setDefaultCommandConfig(new CommandConfig());
    multiSchemaMultiTenantProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    CommandContextInterceptor commandInvoker = new CommandContextInterceptor();
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInvoker(commandInvoker);
    multiSchemaMultiTenantProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(commandExecutor);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutors();

    // Assert that nothing has changed
    CommandExecutor commandExecutor2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor2 instanceof CommandExecutorImpl);
    CommandInterceptor commandInvoker2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker2 instanceof CommandContextInterceptor);
    CommandConfig defaultCommandConfig =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    CommandConfig schemaCommandConfig =
        multiSchemaMultiTenantProcessEngineConfiguration.getSchemaCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, schemaCommandConfig.getTransactionPropagation());
    assertTrue(defaultCommandConfig.isContextReusePossible());
    assertTrue(schemaCommandConfig.isContextReusePossible());
    assertSame(commandExecutor, commandExecutor2);
    assertSame(commandInvoker, commandInvoker2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors5() {
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

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    CommandConfig defaultCommandConfig = new CommandConfig();
    multiSchemaMultiTenantProcessEngineConfiguration.setDefaultCommandConfig(defaultCommandConfig);
    multiSchemaMultiTenantProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInvoker(
        new CommandContextInterceptor());
    multiSchemaMultiTenantProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutors();

    // Assert
    CommandExecutor commandExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    List<CommandInterceptor> commandInterceptors2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
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
    assertSame(defaultCommandConfig, commandExecutor.getDefaultConfig());
    assertSame(commandContextInterceptor, ((CommandExecutorImpl) commandExecutor).getFirst());
    assertSame(commandContextInterceptor2, getResult.getNext());
    assertSame(commandContextInterceptor3, getResult2.getNext());
    assertSame(commandContextInterceptor6, getResult3.getNext());
    assertSame(commandContextInterceptor4, next);
    assertSame(commandContextInterceptor5, getResult5.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    jtaProcessEngineConfiguration.initCommandExecutors();

    // Assert
    CommandExecutor commandExecutor = jtaProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    CommandInterceptor commandInvoker = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof CommandInvoker);
    List<CommandInterceptor> commandInterceptors =
        jtaProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    assertTrue(commandInterceptors.get(1) instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult = commandInterceptors.get(0);
    assertTrue(getResult instanceof LogInterceptor);
    assertNull(commandInvoker.getNext());
    CommandConfig schemaCommandConfig = jtaProcessEngineConfiguration.getSchemaCommandConfig();
    assertEquals(
        TransactionPropagation.NOT_SUPPORTED, schemaCommandConfig.getTransactionPropagation());
    CommandConfig defaultCommandConfig = jtaProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    assertFalse(schemaCommandConfig.isContextReusePossible());
    assertTrue(defaultCommandConfig.isContextReusePossible());
    assertSame(defaultCommandConfig, commandExecutor.getDefaultConfig());
    assertSame(commandInvoker, commandInterceptors.get(2));
    assertSame(getResult, ((CommandExecutorImpl) commandExecutor).getFirst());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors_thenThrowActivitiException() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setDefaultCommandConfig(new CommandConfig());
    multiSchemaMultiTenantProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInvoker(
        new CommandContextInterceptor());
    multiSchemaMultiTenantProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutors()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutors()"})
  public void testInitCommandExecutors_thenThrowActivitiException2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setDefaultCommandConfig(new CommandConfig());
    multiSchemaMultiTenantProcessEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInvoker(
        new CommandContextInterceptor());
    multiSchemaMultiTenantProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInvoker()"})
  public void testInitCommandInvoker() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initCommandInvoker();

    // Assert
    CommandInterceptor commandInvoker = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof CommandInvoker);
    assertNull(commandInvoker.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInvoker()"})
  public void testInitCommandInvoker2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    CommandContextInterceptor commandInvoker = new CommandContextInterceptor();
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInvoker(commandInvoker);
    multiSchemaMultiTenantProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInvoker();

    // Assert that nothing has changed
    CommandInterceptor commandInvoker2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker2 instanceof CommandContextInterceptor);
    assertSame(commandInvoker, commandInvoker2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInvoker()"})
  public void testInitCommandInvoker3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);

    // Act
    jtaProcessEngineConfiguration.initCommandInvoker();

    // Assert
    CommandInterceptor commandInvoker = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof DebugCommandInvoker);
    assertNull(commandInvoker.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInvoker()"})
  public void testInitCommandInvoker4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initCommandInvoker();

    // Assert
    CommandInterceptor commandInvoker = jtaProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof CommandInvoker);
    assertNull(commandInvoker.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInvoker()"})
  public void testInitCommandInvoker5() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    CommandContextInterceptor commandInvoker = new CommandContextInterceptor();
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInvoker(commandInvoker);
    multiSchemaMultiTenantProcessEngineConfiguration.setEnableVerboseExecutionTreeLogging(false);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInvoker();

    // Assert that nothing has changed
    CommandInterceptor commandInvoker2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker2 instanceof CommandContextInterceptor);
    assertSame(commandInvoker, commandInvoker2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInvoker()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInvoker()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInvoker()"})
  public void testInitCommandInvoker6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
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
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert that nothing has changed
    assertTrue(multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(4, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(2);
    assertTrue(getResult instanceof TransactionContextInterceptor);
    assertNull(commandInterceptors.get(3));
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors3() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    CommandContextFactory commandContextFactory = new CommandContextFactory();
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        commandContextFactory);
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(null);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(1);
    assertTrue(getResult instanceof CommandContextInterceptor);
    assertSame(
        multiSchemaMultiTenantProcessEngineConfiguration,
        ((CommandContextInterceptor) getResult).getProcessEngineConfiguration());
    assertSame(
        commandContextFactory, ((CommandContextInterceptor) getResult).getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors4() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    assertTrue(commandInterceptors.get(1) instanceof TransactionContextInterceptor);
    assertNull(commandInterceptors.get(2));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors5() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(4, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(2);
    assertTrue(getResult instanceof TransactionContextInterceptor);
    assertNull(commandInterceptors.get(3));
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors6() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(4, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(2);
    assertTrue(getResult instanceof TransactionContextInterceptor);
    assertNull(commandInterceptors.get(3));
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors7() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    jtaProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        jtaProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(1);
    assertTrue(getResult instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult2 = commandInterceptors.get(0);
    assertTrue(getResult2 instanceof LogInterceptor);
    assertNull(getResult2.getNext());
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors8() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert that nothing has changed
    assertTrue(multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors9() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(4, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(2);
    assertTrue(getResult instanceof TransactionContextInterceptor);
    assertNull(commandInterceptors.get(3));
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors10() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    CommandContextFactory commandContextFactory = new CommandContextFactory();
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        commandContextFactory);
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(null);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(1);
    assertTrue(getResult instanceof CommandContextInterceptor);
    assertSame(
        multiSchemaMultiTenantProcessEngineConfiguration,
        ((CommandContextInterceptor) getResult).getProcessEngineConfiguration());
    assertSame(
        commandContextFactory, ((CommandContextInterceptor) getResult).getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors11() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    assertTrue(commandInterceptors.get(1) instanceof TransactionContextInterceptor);
    assertNull(commandInterceptors.get(2));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors12() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(4, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(2);
    assertTrue(getResult instanceof TransactionContextInterceptor);
    assertNull(commandInterceptors.get(3));
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors13() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPreCommandInterceptors(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomPostCommandInterceptors(
        new ArrayList<>());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(4, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(2);
    assertTrue(getResult instanceof TransactionContextInterceptor);
    assertNull(commandInterceptors.get(3));
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandInterceptors()"})
  public void testInitCommandInterceptors14() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    jtaProcessEngineConfiguration.initCommandInterceptors();

    // Assert
    List<CommandInterceptor> commandInterceptors =
        jtaProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(3, commandInterceptors.size());
    CommandInterceptor getResult = commandInterceptors.get(1);
    assertTrue(getResult instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult2 = commandInterceptors.get(0);
    assertTrue(getResult2 instanceof LogInterceptor);
    assertNull(getResult2.getNext());
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    CommandContextFactory commandContextFactory = new CommandContextFactory();
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        commandContextFactory);
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(null);

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(2, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    ProcessEngineConfigurationImpl processEngineConfiguration =
        ((CommandContextInterceptor) getResult).getProcessEngineConfiguration();
    assertTrue(
        processEngineConfiguration instanceof MultiSchemaMultiTenantProcessEngineConfiguration);
    assertTrue(getResult instanceof CommandContextInterceptor);
    assertSame(multiSchemaMultiTenantProcessEngineConfiguration, processEngineConfiguration);
    assertSame(
        commandContextFactory, ((CommandContextInterceptor) getResult).getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    CommandContextFactory commandContextFactory = new CommandContextFactory();
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        commandContextFactory);
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(null);

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(2, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    ProcessEngineConfigurationImpl processEngineConfiguration =
        ((CommandContextInterceptor) getResult).getProcessEngineConfiguration();
    assertTrue(
        processEngineConfiguration instanceof MultiSchemaMultiTenantProcessEngineConfiguration);
    assertTrue(getResult instanceof CommandContextInterceptor);
    assertSame(multiSchemaMultiTenantProcessEngineConfiguration, processEngineConfiguration);
    assertSame(
        commandContextFactory, ((CommandContextInterceptor) getResult).getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   *
   * <ul>
   *   <li>Then return size is three.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors_thenReturnSizeIsThree() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(3, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    assertTrue(
        ((CommandContextInterceptor) getResult).getProcessEngineConfiguration()
            instanceof MultiSchemaMultiTenantProcessEngineConfiguration);
    assertTrue(getResult instanceof CommandContextInterceptor);
    CommandInterceptor getResult2 =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(2);
    assertTrue(getResult2 instanceof TransactionContextInterceptor);
    assertNull(getResult2.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   *
   * <ul>
   *   <li>Then return size is three.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors_thenReturnSizeIsThree2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(3, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    assertTrue(
        ((CommandContextInterceptor) getResult).getProcessEngineConfiguration()
            instanceof MultiSchemaMultiTenantProcessEngineConfiguration);
    assertTrue(getResult instanceof CommandContextInterceptor);
    CommandInterceptor getResult2 =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(2);
    assertTrue(getResult2 instanceof TransactionContextInterceptor);
    assertNull(getResult2.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   *
   * <ul>
   *   <li>Then second return {@link JtaTransactionInterceptor}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors_thenSecondReturnJtaTransactionInterceptor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors =
        jtaProcessEngineConfiguration.getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(2, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    assertTrue(getResult instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult2 =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(0);
    assertTrue(getResult2 instanceof LogInterceptor);
    assertNull(getResult2.getNext());
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   *
   * <ul>
   *   <li>Then second return {@link JtaTransactionInterceptor}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors_thenSecondReturnJtaTransactionInterceptor2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors =
        jtaProcessEngineConfiguration.getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(2, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    assertTrue(getResult instanceof JtaTransactionInterceptor);
    CommandInterceptor getResult2 =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(0);
    assertTrue(getResult2 instanceof LogInterceptor);
    assertNull(getResult2.getNext());
    assertNull(getResult.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   *
   * <ul>
   *   <li>Then second return {@link TransactionContextInterceptor}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors_thenSecondReturnTransactionContextInterceptor() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(2, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(0);
    assertTrue(getResult instanceof LogInterceptor);
    CommandInterceptor getResult2 =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    assertTrue(getResult2 instanceof TransactionContextInterceptor);
    assertNull(getResult.getNext());
    assertNull(getResult2.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}.
   *
   * <ul>
   *   <li>Then second return {@link TransactionContextInterceptor}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultCommandInterceptors()"})
  public void testGetDefaultCommandInterceptors_thenSecondReturnTransactionContextInterceptor2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setTransactionContextFactory(
        mock(TransactionContextFactory.class));

    // Act
    Collection<? extends CommandInterceptor> actualDefaultCommandInterceptors =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandInterceptors();

    // Assert
    assertTrue(actualDefaultCommandInterceptors instanceof List);
    assertEquals(2, actualDefaultCommandInterceptors.size());
    CommandInterceptor getResult =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(0);
    assertTrue(getResult instanceof LogInterceptor);
    CommandInterceptor getResult2 =
        ((List<? extends CommandInterceptor>) actualDefaultCommandInterceptors).get(1);
    assertTrue(getResult2 instanceof TransactionContextInterceptor);
    assertNull(getResult.getNext());
    assertNull(getResult2.getNext());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor() {
    // Arrange
    ArrayList<CommandInterceptor> commandInterceptors = new ArrayList<>();
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
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(commandExecutor);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutor();

    // Assert that nothing has changed
    List<CommandInterceptor> commandInterceptors2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(19, commandInterceptors2.size());
    assertTrue(commandInterceptors2.get(0) instanceof CommandContextInterceptor);
    assertTrue(commandInterceptors2.get(1) instanceof CommandContextInterceptor);
    assertTrue(commandInterceptors2.get(17) instanceof CommandContextInterceptor);
    assertTrue(commandInterceptors2.get(2) instanceof CommandContextInterceptor);
    assertTrue(commandInterceptors2.get(Short.SIZE) instanceof CommandContextInterceptor);
    assertSame(
        commandExecutor, multiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor2() {
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

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutor();

    // Assert
    CommandExecutor commandExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    List<CommandInterceptor> commandInterceptors2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
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
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor3() {
    // Arrange
    ArrayList<CommandInterceptor> commandInterceptors = new ArrayList<>();
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
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());
    commandInterceptors.add(new CommandContextInterceptor());

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(commandExecutor);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutor();

    // Assert that nothing has changed
    List<CommandInterceptor> commandInterceptors2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
    assertEquals(19, commandInterceptors2.size());
    assertTrue(commandInterceptors2.get(0) instanceof CommandContextInterceptor);
    assertTrue(commandInterceptors2.get(1) instanceof CommandContextInterceptor);
    assertTrue(commandInterceptors2.get(17) instanceof CommandContextInterceptor);
    assertTrue(commandInterceptors2.get(2) instanceof CommandContextInterceptor);
    assertTrue(commandInterceptors2.get(Short.SIZE) instanceof CommandContextInterceptor);
    assertSame(
        commandExecutor, multiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor4() {
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

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(commandInterceptors);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutor();

    // Assert
    CommandExecutor commandExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor();
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    List<CommandInterceptor> commandInterceptors2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors();
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
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> new JtaProcessEngineConfiguration().initCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor_givenJtaProcessEngineConfiguration2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> new JtaProcessEngineConfiguration().initCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor_thenThrowActivitiException() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandExecutor()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandExecutor()"})
  public void testInitCommandExecutor_thenThrowActivitiException2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandInterceptors(new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> multiSchemaMultiTenantProcessEngineConfiguration.initCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCustomMybatisMappers(Configuration)}.
   *
   * <ul>
   *   <li>Then calls {@link Configuration#addMapper(Class)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#initCustomMybatisMappers(Configuration)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCustomMybatisMappers(Configuration)"})
  public void testInitCustomMybatisMappers_thenCallsAddMapper() {
    // Arrange
    HashSet<Class<?>> customMybatisMappers = new HashSet<>();
    Class<Object> forNameResult = Object.class;
    customMybatisMappers.add(forNameResult);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomMybatisMappers(customMybatisMappers);

    Configuration configuration = mock(Configuration.class);
    doNothing().when(configuration).addMapper(Mockito.<Class<Object>>any());

    // Act
    jtaProcessEngineConfiguration.initCustomMybatisMappers(configuration);

    // Assert
    verify(configuration).addMapper(isA(Class.class));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCustomMybatisMappers(Configuration)}.
   *
   * <ul>
   *   <li>Then calls {@link Configuration#addMapper(Class)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#initCustomMybatisMappers(Configuration)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCustomMybatisMappers(Configuration)"})
  public void testInitCustomMybatisMappers_thenCallsAddMapper2() {
    // Arrange
    HashSet<Class<?>> customMybatisMappers = new HashSet<>();
    Class<Object> forNameResult = Object.class;
    customMybatisMappers.add(forNameResult);

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomMybatisMappers(customMybatisMappers);

    Configuration configuration = mock(Configuration.class);
    doNothing().when(configuration).addMapper(Mockito.<Class<Object>>any());

    // Act
    jtaProcessEngineConfiguration.initCustomMybatisMappers(configuration);

    // Assert
    verify(configuration).addMapper(isA(Class.class));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ProcessEngineConfigurationImpl.getCustomMybatisMappers()"})
  public void testGetCustomMybatisMappers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomMybatisMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomMybatisMappers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ProcessEngineConfigurationImpl.getCustomMybatisMappers()"})
  public void testGetCustomMybatisMappers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomMybatisMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomMybatisXMLMappers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomMybatisXMLMappers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ProcessEngineConfigurationImpl.getCustomMybatisXMLMappers()"})
  public void testGetCustomMybatisXMLMappers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomMybatisXMLMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomMybatisXMLMappers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomMybatisXMLMappers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ProcessEngineConfigurationImpl.getCustomMybatisXMLMappers()"})
  public void testGetCustomMybatisXMLMappers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomMybatisXMLMappers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#createDbSqlSessionFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#createDbSqlSessionFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DbSqlSessionFactory ProcessEngineConfigurationImpl.createDbSqlSessionFactory()"
  })
  public void testCreateDbSqlSessionFactory() {
    // Arrange and Act
    DbSqlSessionFactory actualCreateDbSqlSessionFactoryResult =
        new JtaProcessEngineConfiguration().createDbSqlSessionFactory();

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
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#createDbSqlSessionFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DbSqlSessionFactory ProcessEngineConfigurationImpl.createDbSqlSessionFactory()"
  })
  public void testCreateDbSqlSessionFactory2() {
    // Arrange and Act
    DbSqlSessionFactory actualCreateDbSqlSessionFactoryResult =
        new JtaProcessEngineConfiguration().createDbSqlSessionFactory();

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
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setClassLoader(new GroovyClassLoader());

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
    assertTrue(jtaProcessEngineConfiguration.getAllConfigurators().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    List<ProcessEngineConfigurator> allConfigurators =
        jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(3, allConfigurators.size());
    assertSame(configurator, allConfigurators.get(2));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setClassLoader(new GroovyClassLoader());

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
    assertTrue(jtaProcessEngineConfiguration.getAllConfigurators().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    List<ProcessEngineConfigurator> allConfigurators =
        jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(3, allConfigurators.size());
    assertSame(configurator, allConfigurators.get(2));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
    assertTrue(jtaProcessEngineConfiguration.getAllConfigurators().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_givenJtaProcessEngineConfiguration2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
    assertTrue(jtaProcessEngineConfiguration.getAllConfigurators().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) ClassLoader is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_givenJtaProcessEngineConfigurationClassLoaderIsNull() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setClassLoader(null);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
    assertTrue(jtaProcessEngineConfiguration.getAllConfigurators().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) ClassLoader is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_givenJtaProcessEngineConfigurationClassLoaderIsNull2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setClassLoader(null);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    assertNull(jtaProcessEngineConfiguration.getConfigurators());
    assertTrue(jtaProcessEngineConfiguration.getAllConfigurators().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) AllConfigurators size is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_thenJtaProcessEngineConfigurationAllConfiguratorsSizeIsOne() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    List<ProcessEngineConfigurator> allConfigurators =
        jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(1, allConfigurators.size());
    assertEquals(1, jtaProcessEngineConfiguration.getConfigurators().size());
    assertSame(configurator, allConfigurators.get(0));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) AllConfigurators size is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_thenJtaProcessEngineConfigurationAllConfiguratorsSizeIsOne2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    List<ProcessEngineConfigurator> allConfigurators =
        jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(1, allConfigurators.size());
    assertEquals(1, jtaProcessEngineConfiguration.getConfigurators().size());
    assertSame(configurator, allConfigurators.get(0));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) AllConfigurators size is
   *       two.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_thenJtaProcessEngineConfigurationAllConfiguratorsSizeIsTwo() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    List<ProcessEngineConfigurator> allConfigurators =
        jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(2, allConfigurators.size());
    assertSame(configurator, allConfigurators.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initConfigurators()}.
   *
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) AllConfigurators size is
   *       two.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initConfigurators()"})
  public void testInitConfigurators_thenJtaProcessEngineConfigurationAllConfiguratorsSizeIsTwo2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Act
    jtaProcessEngineConfiguration.initConfigurators();

    // Assert
    List<ProcessEngineConfigurator> allConfigurators =
        jtaProcessEngineConfiguration.getAllConfigurators();
    assertEquals(2, allConfigurators.size());
    assertSame(configurator, allConfigurators.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   *
   * <ul>
   *   <li>Then first return {@link BpmnDeployer}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers_thenFirstReturnBpmnDeployer() {
    // Arrange and Act
    Collection<? extends Deployer> actualDefaultDeployers =
        new JtaProcessEngineConfiguration().getDefaultDeployers();

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
   *
   * <ul>
   *   <li>Then first return {@link BpmnDeployer}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers_thenFirstReturnBpmnDeployer2() {
    // Arrange and Act
    Collection<? extends Deployer> actualDefaultDeployers =
        new JtaProcessEngineConfiguration().getDefaultDeployers();

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
   *
   * <ul>
   *   <li>Then return first is {@link BpmnDeployer} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers_thenReturnFirstIsBpmnDeployer() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    bpmnDeploymentHelper.setTimerManager(new TimerManager());
    bpmnDeploymentHelper.setEventSubscriptionManager(new EventSubscriptionManager());

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    multiSchemaMultiTenantProcessEngineConfiguration.setBpmnDeployer(bpmnDeployer);
    multiSchemaMultiTenantProcessEngineConfiguration.setParsedDeploymentBuilderFactory(
        parsedDeploymentBuilderFactory);
    multiSchemaMultiTenantProcessEngineConfiguration.setTimerManager(new TimerManager());
    multiSchemaMultiTenantProcessEngineConfiguration.setEventSubscriptionManager(
        new EventSubscriptionManager());
    multiSchemaMultiTenantProcessEngineConfiguration.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    multiSchemaMultiTenantProcessEngineConfiguration.setCachingAndArtifactsManager(
        new CachingAndArtifactsManager());

    // Act
    Collection<? extends Deployer> actualDefaultDeployers =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    assertSame(bpmnDeployer, ((List<? extends Deployer>) actualDefaultDeployers).get(0));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}.
   *
   * <ul>
   *   <li>Then return first is {@link BpmnDeployer} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection ProcessEngineConfigurationImpl.getDefaultDeployers()"})
  public void testGetDefaultDeployers_thenReturnFirstIsBpmnDeployer2() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    bpmnDeploymentHelper.setTimerManager(new TimerManager());
    bpmnDeploymentHelper.setEventSubscriptionManager(new EventSubscriptionManager());

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    BpmnDeployer bpmnDeployer = new BpmnDeployer();
    multiSchemaMultiTenantProcessEngineConfiguration.setBpmnDeployer(bpmnDeployer);
    multiSchemaMultiTenantProcessEngineConfiguration.setParsedDeploymentBuilderFactory(
        parsedDeploymentBuilderFactory);
    multiSchemaMultiTenantProcessEngineConfiguration.setTimerManager(new TimerManager());
    multiSchemaMultiTenantProcessEngineConfiguration.setEventSubscriptionManager(
        new EventSubscriptionManager());
    multiSchemaMultiTenantProcessEngineConfiguration.setBpmnDeploymentHelper(bpmnDeploymentHelper);
    multiSchemaMultiTenantProcessEngineConfiguration.setCachingAndArtifactsManager(
        new CachingAndArtifactsManager());

    // Act
    Collection<? extends Deployer> actualDefaultDeployers =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultDeployers();

    // Assert
    assertTrue(actualDefaultDeployers instanceof List);
    assertEquals(1, actualDefaultDeployers.size());
    assertSame(bpmnDeployer, ((List<? extends Deployer>) actualDefaultDeployers).get(0));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBehaviorFactory()"})
  public void testInitBehaviorFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initBehaviorFactory();

    // Assert
    ActivityBehaviorFactory activityBehaviorFactory =
        jtaProcessEngineConfiguration.getActivityBehaviorFactory();
    assertTrue(activityBehaviorFactory instanceof DefaultActivityBehaviorFactory);
    assertTrue(
        ((DefaultActivityBehaviorFactory) activityBehaviorFactory)
                .getMessageExecutionContextFactory()
            instanceof DefaultMessageExecutionContextFactory);
    assertTrue(
        ((DefaultActivityBehaviorFactory) activityBehaviorFactory)
                .getMessagePayloadMappingProviderFactory()
            instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(((DefaultActivityBehaviorFactory) activityBehaviorFactory).getExpressionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBehaviorFactory()"})
  public void testInitBehaviorFactory2() {
    // Arrange
    DefaultActivityBehaviorFactory activityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    activityBehaviorFactory.setExpressionManager(new ExpressionManager());

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setActivityBehaviorFactory(
        activityBehaviorFactory);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initBehaviorFactory();

    // Assert that nothing has changed
    assertSame(
        activityBehaviorFactory,
        multiSchemaMultiTenantProcessEngineConfiguration.getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBehaviorFactory()"})
  public void testInitBehaviorFactory3() {
    // Arrange
    DefaultActivityBehaviorFactory activityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    activityBehaviorFactory.setExpressionManager(null);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setActivityBehaviorFactory(
        activityBehaviorFactory);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initBehaviorFactory();

    // Assert that nothing has changed
    assertSame(
        activityBehaviorFactory,
        multiSchemaMultiTenantProcessEngineConfiguration.getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBehaviorFactory()"})
  public void testInitBehaviorFactory4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initBehaviorFactory();

    // Assert
    ActivityBehaviorFactory activityBehaviorFactory =
        jtaProcessEngineConfiguration.getActivityBehaviorFactory();
    assertTrue(activityBehaviorFactory instanceof DefaultActivityBehaviorFactory);
    assertTrue(
        ((DefaultActivityBehaviorFactory) activityBehaviorFactory)
                .getMessageExecutionContextFactory()
            instanceof DefaultMessageExecutionContextFactory);
    assertTrue(
        ((DefaultActivityBehaviorFactory) activityBehaviorFactory)
                .getMessagePayloadMappingProviderFactory()
            instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(((DefaultActivityBehaviorFactory) activityBehaviorFactory).getExpressionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBehaviorFactory()"})
  public void testInitBehaviorFactory5() {
    // Arrange
    DefaultActivityBehaviorFactory activityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    activityBehaviorFactory.setExpressionManager(new ExpressionManager());

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setActivityBehaviorFactory(
        activityBehaviorFactory);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initBehaviorFactory();

    // Assert that nothing has changed
    assertSame(
        activityBehaviorFactory,
        multiSchemaMultiTenantProcessEngineConfiguration.getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBehaviorFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBehaviorFactory()"})
  public void testInitBehaviorFactory6() {
    // Arrange
    DefaultActivityBehaviorFactory activityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    activityBehaviorFactory.setExpressionManager(null);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setActivityBehaviorFactory(
        activityBehaviorFactory);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initBehaviorFactory();

    // Assert that nothing has changed
    assertSame(
        activityBehaviorFactory,
        multiSchemaMultiTenantProcessEngineConfiguration.getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDefaultBpmnParseHandlers()"})
  public void testGetDefaultBpmnParseHandlers() {
    // Arrange
    ArrayList<BpmnParseHandler> customDefaultBpmnParseHandlers = new ArrayList<>();
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    customDefaultBpmnParseHandlers.add(adhocSubProcessParseHandler);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomDefaultBpmnParseHandlers(
        customDefaultBpmnParseHandlers);

    // Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = actualDefaultBpmnParseHandlers.get(25);
    assertTrue(getResult instanceof AdhocSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(0) instanceof BoundaryEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(1) instanceof BusinessRuleParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(2) instanceof CallActivityParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(3) instanceof CancelEventDefinitionParseHandler);
    assertTrue(
        actualDefaultBpmnParseHandlers.get(4) instanceof CompensateEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(5) instanceof EndEventParseHandler);
    assertTrue(
        actualDefaultBpmnParseHandlers.get(Float.PRECISION) instanceof EventSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(26) instanceof TaskParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(27) instanceof TimerEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(28) instanceof TransactionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(29) instanceof UserTaskParseHandler);
    assertSame(adhocSubProcessParseHandler, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDefaultBpmnParseHandlers()"})
  public void testGetDefaultBpmnParseHandlers2() {
    // Arrange
    ArrayList<BpmnParseHandler> customDefaultBpmnParseHandlers = new ArrayList<>();
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    customDefaultBpmnParseHandlers.add(adhocSubProcessParseHandler);

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomDefaultBpmnParseHandlers(
        customDefaultBpmnParseHandlers);

    // Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers =
        multiSchemaMultiTenantProcessEngineConfiguration.getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = actualDefaultBpmnParseHandlers.get(25);
    assertTrue(getResult instanceof AdhocSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(0) instanceof BoundaryEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(1) instanceof BusinessRuleParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(2) instanceof CallActivityParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(3) instanceof CancelEventDefinitionParseHandler);
    assertTrue(
        actualDefaultBpmnParseHandlers.get(4) instanceof CompensateEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(5) instanceof EndEventParseHandler);
    assertTrue(
        actualDefaultBpmnParseHandlers.get(Float.PRECISION) instanceof EventSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(26) instanceof TaskParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(27) instanceof TimerEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(28) instanceof TransactionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(29) instanceof UserTaskParseHandler);
    assertSame(adhocSubProcessParseHandler, getResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDefaultBpmnParseHandlers()"})
  public void testGetDefaultBpmnParseHandlers_givenJtaProcessEngineConfiguration() {
    // Arrange and Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers =
        new JtaProcessEngineConfiguration().getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    assertTrue(actualDefaultBpmnParseHandlers.get(25) instanceof AdhocSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(0) instanceof BoundaryEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(1) instanceof BusinessRuleParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(2) instanceof CallActivityParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(3) instanceof CancelEventDefinitionParseHandler);
    assertTrue(
        actualDefaultBpmnParseHandlers.get(4) instanceof CompensateEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(5) instanceof EndEventParseHandler);
    assertTrue(
        actualDefaultBpmnParseHandlers.get(Float.PRECISION) instanceof EventSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(26) instanceof TaskParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(27) instanceof TimerEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(28) instanceof TransactionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(29) instanceof UserTaskParseHandler);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDefaultBpmnParseHandlers()"})
  public void testGetDefaultBpmnParseHandlers_givenJtaProcessEngineConfiguration2() {
    // Arrange and Act
    List<BpmnParseHandler> actualDefaultBpmnParseHandlers =
        new JtaProcessEngineConfiguration().getDefaultBpmnParseHandlers();

    // Assert
    assertEquals(30, actualDefaultBpmnParseHandlers.size());
    assertTrue(actualDefaultBpmnParseHandlers.get(25) instanceof AdhocSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(0) instanceof BoundaryEventParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(1) instanceof BusinessRuleParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(2) instanceof CallActivityParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(3) instanceof CancelEventDefinitionParseHandler);
    assertTrue(
        actualDefaultBpmnParseHandlers.get(4) instanceof CompensateEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(5) instanceof EndEventParseHandler);
    assertTrue(
        actualDefaultBpmnParseHandlers.get(Float.PRECISION) instanceof EventSubProcessParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(26) instanceof TaskParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(27) instanceof TimerEventDefinitionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(28) instanceof TransactionParseHandler);
    assertTrue(actualDefaultBpmnParseHandlers.get(29) instanceof UserTaskParseHandler);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initClock()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initClock()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initClock()"})
  public void testInitClock() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    DefaultClockImpl clock = new DefaultClockImpl();
    multiSchemaMultiTenantProcessEngineConfiguration.setClock(clock);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initClock();

    // Assert that nothing has changed
    assertSame(clock, multiSchemaMultiTenantProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initClock()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initClock()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initClock()"})
  public void testInitClock2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    DefaultClockImpl clock = new DefaultClockImpl();
    multiSchemaMultiTenantProcessEngineConfiguration.setClock(clock);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initClock();

    // Assert that nothing has changed
    assertSame(clock, multiSchemaMultiTenantProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAgendaFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAgendaFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAgendaFactory()"})
  public void testInitAgendaFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initAgendaFactory();

    // Assert
    assertTrue(
        jtaProcessEngineConfiguration.getEngineAgendaFactory()
            instanceof DefaultActivitiEngineAgendaFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAgendaFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAgendaFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAgendaFactory()"})
  public void testInitAgendaFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initAgendaFactory();

    // Assert
    assertTrue(
        jtaProcessEngineConfiguration.getEngineAgendaFactory()
            instanceof DefaultActivitiEngineAgendaFactory);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert that nothing has changed
    AsyncExecutor asyncExecutor2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor2 instanceof DefaultAsyncJobExecutor);
    assertSame(asyncExecutor, asyncExecutor2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof ExecutorPerTenantAsyncExecutor);
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertTrue(((ExecutorPerTenantAsyncExecutor) asyncExecutor).getTenantIds().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAsyncExecutor() instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorMessageQueueMode(true);

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAsyncExecutor() instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor5() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(tenantInfoHolder);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof ExecutorPerTenantAsyncExecutor);
    Set<String> tenantIds = ((ExecutorPerTenantAsyncExecutor) asyncExecutor).getTenantIds();
    assertEquals(1, tenantIds.size());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor6() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
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
    assertSame(
        asyncExecutorThreadPoolQueue,
        ((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor7() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert that nothing has changed
    AsyncExecutor asyncExecutor2 =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor2 instanceof DefaultAsyncJobExecutor);
    assertSame(asyncExecutor, asyncExecutor2);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor8() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof ExecutorPerTenantAsyncExecutor);
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertTrue(((ExecutorPerTenantAsyncExecutor) asyncExecutor).getTenantIds().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor9() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    jtaProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAsyncExecutor() instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor10() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorMessageQueueMode(true);

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAsyncExecutor() instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor11() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(tenantInfoHolder);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutorLockOwner("foo");

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor =
        multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof ExecutorPerTenantAsyncExecutor);
    Set<String> tenantIds = ((ExecutorPerTenantAsyncExecutor) asyncExecutor).getTenantIds();
    assertEquals(1, tenantIds.size());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor12() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
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
    assertSame(
        asyncExecutorThreadPoolQueue,
        ((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAsyncExecutor() instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initAsyncExecutor()"})
  public void testInitAsyncExecutor_givenJtaProcessEngineConfiguration2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getAsyncExecutor() instanceof DefaultAsyncJobExecutor);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandContextFactory()"})
  public void testInitCommandContextFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initCommandContextFactory();

    // Assert
    assertSame(
        jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.getCommandContextFactory().getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandContextFactory()"})
  public void testInitCommandContextFactory2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandContextFactory();

    // Assert
    assertSame(
        multiSchemaMultiTenantProcessEngineConfiguration,
        multiSchemaMultiTenantProcessEngineConfiguration
            .getCommandContextFactory()
            .getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandContextFactory()"})
  public void testInitCommandContextFactory3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initCommandContextFactory();

    // Assert
    assertSame(
        jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.getCommandContextFactory().getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initCommandContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initCommandContextFactory()"})
  public void testInitCommandContextFactory4() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCommandContextFactory(
        new CommandContextFactory());

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initCommandContextFactory();

    // Assert
    assertSame(
        multiSchemaMultiTenantProcessEngineConfiguration,
        multiSchemaMultiTenantProcessEngineConfiguration
            .getCommandContextFactory()
            .getProcessEngineConfiguration());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   *
   * <ul>
   *   <li>Then return {@link ProcessEngineConfigurationImpl#DEFAULT_GENERIC_MAX_LENGTH_STRING}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthString()"})
  public void testGetMaxLengthString_thenReturnDefault_generic_max_length_string() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_GENERIC_MAX_LENGTH_STRING,
        new JtaProcessEngineConfiguration().getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   *
   * <ul>
   *   <li>Then return {@link ProcessEngineConfigurationImpl#DEFAULT_GENERIC_MAX_LENGTH_STRING}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthString()"})
  public void testGetMaxLengthString_thenReturnDefault_generic_max_length_string2() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_GENERIC_MAX_LENGTH_STRING,
        new JtaProcessEngineConfiguration().getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   *
   * <ul>
   *   <li>Then return {@link ProcessEngineConfigurationImpl#DEFAULT_ORACLE_MAX_LENGTH_STRING}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthString()"})
  public void testGetMaxLengthString_thenReturnDefault_oracle_max_length_string() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDatabaseType(
        ProcessEngineConfigurationImpl.DATABASE_TYPE_ORACLE);

    // Act and Assert
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_ORACLE_MAX_LENGTH_STRING,
        jtaProcessEngineConfiguration.getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   *
   * <ul>
   *   <li>Then return {@link ProcessEngineConfigurationImpl#DEFAULT_ORACLE_MAX_LENGTH_STRING}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthString()"})
  public void testGetMaxLengthString_thenReturnDefault_oracle_max_length_string2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDatabaseType(
        ProcessEngineConfigurationImpl.DATABASE_TYPE_ORACLE);

    // Act and Assert
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_ORACLE_MAX_LENGTH_STRING,
        jtaProcessEngineConfiguration.getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthString()"})
  public void testGetMaxLengthString_thenReturnThree() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMaxLengthStringVariableType(3);

    // Act and Assert
    assertEquals(3, jtaProcessEngineConfiguration.getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthString()}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthString()"})
  public void testGetMaxLengthString_thenReturnThree2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMaxLengthStringVariableType(3);

    // Act and Assert
    assertEquals(3, jtaProcessEngineConfiguration.getMaxLengthString());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBusinessCalendarManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBusinessCalendarManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBusinessCalendarManager()"})
  public void testInitBusinessCalendarManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initBusinessCalendarManager();

    // Assert
    assertTrue(
        jtaProcessEngineConfiguration.getBusinessCalendarManager()
            instanceof MapBusinessCalendarManager);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBusinessCalendarManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBusinessCalendarManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBusinessCalendarManager()"})
  public void testInitBusinessCalendarManager2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initBusinessCalendarManager();

    // Assert
    assertTrue(
        jtaProcessEngineConfiguration.getBusinessCalendarManager()
            instanceof MapBusinessCalendarManager);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBeans()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBeans()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBeans()"})
  public void testInitBeans() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setBeans(new HashMap<>());

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initBeans();

    // Assert that nothing has changed
    assertTrue(multiSchemaMultiTenantProcessEngineConfiguration.getBeans().isEmpty());
    assertTrue(
        multiSchemaMultiTenantProcessEngineConfiguration
            .getWsOverridenEndpointAddresses()
            .isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBeans()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBeans()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBeans()"})
  public void testInitBeans2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setBeans(new HashMap<>());

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initBeans();

    // Assert that nothing has changed
    assertTrue(multiSchemaMultiTenantProcessEngineConfiguration.getBeans().isEmpty());
    assertTrue(
        multiSchemaMultiTenantProcessEngineConfiguration
            .getWsOverridenEndpointAddresses()
            .isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBeans()}.
   *
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) Beans Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBeans()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBeans()"})
  public void testInitBeans_thenJtaProcessEngineConfigurationBeansEmpty() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initBeans();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getBeans().isEmpty());
    assertTrue(jtaProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#initBeans()}.
   *
   * <ul>
   *   <li>Then {@link JtaProcessEngineConfiguration} (default constructor) Beans Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#initBeans()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEngineConfigurationImpl.initBeans()"})
  public void testInitBeans_thenJtaProcessEngineConfigurationBeansEmpty2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initBeans();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getBeans().isEmpty());
    assertTrue(jtaProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandConfig ProcessEngineConfigurationImpl.getDefaultCommandConfig()"})
  public void testGetDefaultCommandConfig() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDefaultCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDefaultCommandConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandConfig ProcessEngineConfigurationImpl.getDefaultCommandConfig()"})
  public void testGetDefaultCommandConfig2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDefaultCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSchemaCommandConfig()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSchemaCommandConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandConfig ProcessEngineConfigurationImpl.getSchemaCommandConfig()"})
  public void testGetSchemaCommandConfig() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSchemaCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSchemaCommandConfig()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSchemaCommandConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandConfig ProcessEngineConfigurationImpl.getSchemaCommandConfig()"})
  public void testGetSchemaCommandConfig2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSchemaCommandConfig());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandInvoker()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommandInvoker()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandInterceptor ProcessEngineConfigurationImpl.getCommandInvoker()"})
  public void testGetCommandInvoker() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommandInvoker());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandInvoker()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommandInvoker()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandInterceptor ProcessEngineConfigurationImpl.getCommandInvoker()"})
  public void testGetCommandInvoker2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommandInvoker());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPreCommandInterceptors()"})
  public void testGetCustomPreCommandInterceptors() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPreCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPreCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPreCommandInterceptors()"})
  public void testGetCustomPreCommandInterceptors2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPreCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPostCommandInterceptors()"})
  public void testGetCustomPostCommandInterceptors() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPostCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPostCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPostCommandInterceptors()"})
  public void testGetCustomPostCommandInterceptors2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPostCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCommandInterceptors()"})
  public void testGetCommandInterceptors() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandInterceptors()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommandInterceptors()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCommandInterceptors()"})
  public void testGetCommandInterceptors2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommandInterceptors());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandExecutor ProcessEngineConfigurationImpl.getCommandExecutor()"})
  public void testGetCommandExecutor() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandExecutor()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandExecutor ProcessEngineConfigurationImpl.getCommandExecutor()"})
  public void testGetCommandExecutor2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommandExecutor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getRepositoryService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getRepositoryService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RepositoryService ProcessEngineConfigurationImpl.getRepositoryService()"})
  public void testGetRepositoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    RepositoryService actualRepositoryService =
        jtaProcessEngineConfiguration.getRepositoryService();

    // Assert
    assertTrue(actualRepositoryService instanceof RepositoryServiceImpl);
    assertNull(((RepositoryServiceImpl) actualRepositoryService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.repositoryService, actualRepositoryService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getRepositoryService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getRepositoryService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RepositoryService ProcessEngineConfigurationImpl.getRepositoryService()"})
  public void testGetRepositoryService2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    RepositoryService actualRepositoryService =
        jtaProcessEngineConfiguration.getRepositoryService();

    // Assert
    assertTrue(actualRepositoryService instanceof RepositoryServiceImpl);
    assertNull(((RepositoryServiceImpl) actualRepositoryService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.repositoryService, actualRepositoryService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getRuntimeService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getRuntimeService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RuntimeService ProcessEngineConfigurationImpl.getRuntimeService()"})
  public void testGetRuntimeService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    RuntimeService actualRuntimeService = jtaProcessEngineConfiguration.getRuntimeService();

    // Assert
    assertTrue(actualRuntimeService instanceof RuntimeServiceImpl);
    assertNull(((RuntimeServiceImpl) actualRuntimeService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.runtimeService, actualRuntimeService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getRuntimeService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getRuntimeService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RuntimeService ProcessEngineConfigurationImpl.getRuntimeService()"})
  public void testGetRuntimeService2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    RuntimeService actualRuntimeService = jtaProcessEngineConfiguration.getRuntimeService();

    // Assert
    assertTrue(actualRuntimeService instanceof RuntimeServiceImpl);
    assertNull(((RuntimeServiceImpl) actualRuntimeService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.runtimeService, actualRuntimeService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoryService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoryService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoryService ProcessEngineConfigurationImpl.getHistoryService()"})
  public void testGetHistoryService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    HistoryService actualHistoryService = jtaProcessEngineConfiguration.getHistoryService();

    // Assert
    assertTrue(actualHistoryService instanceof HistoryServiceImpl);
    assertNull(((HistoryServiceImpl) actualHistoryService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.historyService, actualHistoryService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoryService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoryService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoryService ProcessEngineConfigurationImpl.getHistoryService()"})
  public void testGetHistoryService2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    HistoryService actualHistoryService = jtaProcessEngineConfiguration.getHistoryService();

    // Assert
    assertTrue(actualHistoryService instanceof HistoryServiceImpl);
    assertNull(((HistoryServiceImpl) actualHistoryService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.historyService, actualHistoryService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTaskService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskService ProcessEngineConfigurationImpl.getTaskService()"})
  public void testGetTaskService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    TaskService actualTaskService = jtaProcessEngineConfiguration.getTaskService();

    // Assert
    assertTrue(actualTaskService instanceof TaskServiceImpl);
    assertNull(((TaskServiceImpl) actualTaskService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.taskService, actualTaskService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTaskService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskService ProcessEngineConfigurationImpl.getTaskService()"})
  public void testGetTaskService2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    TaskService actualTaskService = jtaProcessEngineConfiguration.getTaskService();

    // Assert
    assertTrue(actualTaskService instanceof TaskServiceImpl);
    assertNull(((TaskServiceImpl) actualTaskService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.taskService, actualTaskService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getManagementService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getManagementService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ManagementService ProcessEngineConfigurationImpl.getManagementService()"})
  public void testGetManagementService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ManagementService actualManagementService =
        jtaProcessEngineConfiguration.getManagementService();

    // Assert
    assertTrue(actualManagementService instanceof ManagementServiceImpl);
    assertNull(((ManagementServiceImpl) actualManagementService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.managementService, actualManagementService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getManagementService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getManagementService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ManagementService ProcessEngineConfigurationImpl.getManagementService()"})
  public void testGetManagementService2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ManagementService actualManagementService =
        jtaProcessEngineConfiguration.getManagementService();

    // Assert
    assertTrue(actualManagementService instanceof ManagementServiceImpl);
    assertNull(((ManagementServiceImpl) actualManagementService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.managementService, actualManagementService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDynamicBpmnService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDynamicBpmnService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DynamicBpmnService ProcessEngineConfigurationImpl.getDynamicBpmnService()"})
  public void testGetDynamicBpmnService() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    DynamicBpmnService actualDynamicBpmnService =
        jtaProcessEngineConfiguration.getDynamicBpmnService();

    // Assert
    assertTrue(actualDynamicBpmnService instanceof DynamicBpmnServiceImpl);
    assertNull(((DynamicBpmnServiceImpl) actualDynamicBpmnService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.dynamicBpmnService, actualDynamicBpmnService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDynamicBpmnService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDynamicBpmnService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DynamicBpmnService ProcessEngineConfigurationImpl.getDynamicBpmnService()"})
  public void testGetDynamicBpmnService2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    DynamicBpmnService actualDynamicBpmnService =
        jtaProcessEngineConfiguration.getDynamicBpmnService();

    // Assert
    assertTrue(actualDynamicBpmnService instanceof DynamicBpmnServiceImpl);
    assertNull(((DynamicBpmnServiceImpl) actualDynamicBpmnService).getCommandExecutor());
    assertSame(jtaProcessEngineConfiguration.dynamicBpmnService, actualDynamicBpmnService);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getUserGroupManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getUserGroupManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.identity.UserGroupManager ProcessEngineConfigurationImpl.getUserGroupManager()"
  })
  public void testGetUserGroupManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getUserGroupManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getUserGroupManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getUserGroupManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.identity.UserGroupManager ProcessEngineConfigurationImpl.getUserGroupManager()"
  })
  public void testGetUserGroupManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getUserGroupManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIntegrationContextManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIntegrationContextManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "IntegrationContextManager ProcessEngineConfigurationImpl.getIntegrationContextManager()"
  })
  public void testGetIntegrationContextManager() {
    // Arrange and Act
    IntegrationContextManager actualIntegrationContextManager =
        new JtaProcessEngineConfiguration().getIntegrationContextManager();

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
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIntegrationContextManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "IntegrationContextManager ProcessEngineConfigurationImpl.getIntegrationContextManager()"
  })
  public void testGetIntegrationContextManager2() {
    // Arrange and Act
    IntegrationContextManager actualIntegrationContextManager =
        new JtaProcessEngineConfiguration().getIntegrationContextManager();

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
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIntegrationContextService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.integration.IntegrationContextService ProcessEngineConfigurationImpl.getIntegrationContextService()"
  })
  public void testGetIntegrationContextService() {
    // Arrange, Act and Assert
    assertTrue(
        new JtaProcessEngineConfiguration().getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIntegrationContextService()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIntegrationContextService()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.integration.IntegrationContextService ProcessEngineConfigurationImpl.getIntegrationContextService()"
  })
  public void testGetIntegrationContextService2() {
    // Arrange, Act and Assert
    assertTrue(
        new JtaProcessEngineConfiguration().getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.getProcessEngineConfiguration()"
  })
  public void testGetProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualProcessEngineConfiguration =
        jtaProcessEngineConfiguration.getProcessEngineConfiguration();

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualProcessEngineConfiguration);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessEngineConfiguration()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.getProcessEngineConfiguration()"
  })
  public void testGetProcessEngineConfiguration2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualProcessEngineConfiguration =
        jtaProcessEngineConfiguration.getProcessEngineConfiguration();

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualProcessEngineConfiguration);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSessionFactories()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSessionFactories()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getSessionFactories()"})
  public void testGetSessionFactories() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSessionFactories()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSessionFactories()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getSessionFactories()"})
  public void testGetSessionFactories2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getConfigurators()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getConfigurators()"})
  public void testGetConfigurators() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getConfigurators()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getConfigurators()"})
  public void testGetConfigurators2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addConfigurator(ProcessEngineConfigurator)"
  })
  public void testAddConfigurator() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setConfigurators(new ArrayList<>());

    // Act
    ProcessEngineConfigurationImpl actualAddConfiguratorResult =
        multiSchemaMultiTenantProcessEngineConfiguration.addConfigurator(
            new ProcessExecutionLoggerConfigurator());

    // Assert
    assertSame(multiSchemaMultiTenantProcessEngineConfiguration, actualAddConfiguratorResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   *
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addConfigurator(ProcessEngineConfigurator)"
  })
  public void testAddConfigurator_thenReturnJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualAddConfiguratorResult =
        jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualAddConfiguratorResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   *
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addConfigurator(ProcessEngineConfigurator)"
  })
  public void testAddConfigurator_thenReturnJtaProcessEngineConfiguration2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualAddConfiguratorResult =
        jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualAddConfiguratorResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   *
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addConfigurator(ProcessEngineConfigurator)"
  })
  public void testAddConfigurator_thenReturnJtaProcessEngineConfiguration3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();

    // Act
    ProcessEngineConfigurationImpl actualAddConfiguratorResult =
        jtaProcessEngineConfiguration.addConfigurator(configurator);

    // Assert
    assertTrue(actualAddConfiguratorResult instanceof JtaProcessEngineConfiguration);
    List<ProcessEngineConfigurator> configurators = actualAddConfiguratorResult.getConfigurators();
    assertEquals(2, configurators.size());
    assertSame(configurator, configurators.get(1));
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAllConfigurators()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAllConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getAllConfigurators()"})
  public void testGetAllConfigurators() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAllConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAllConfigurators()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAllConfigurators()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getAllConfigurators()"})
  public void testGetAllConfigurators2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAllConfigurators());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnDeployer ProcessEngineConfigurationImpl.getBpmnDeployer()"})
  public void testGetBpmnDeployer() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBpmnDeployer());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBpmnDeployer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnDeployer ProcessEngineConfigurationImpl.getBpmnDeployer()"})
  public void testGetBpmnDeployer2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBpmnDeployer());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnParser()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBpmnParser()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParser ProcessEngineConfigurationImpl.getBpmnParser()"})
  public void testGetBpmnParser() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBpmnParser());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnParser()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBpmnParser()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParser ProcessEngineConfigurationImpl.getBpmnParser()"})
  public void testGetBpmnParser2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBpmnParser());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ParsedDeploymentBuilderFactory ProcessEngineConfigurationImpl.getParsedDeploymentBuilderFactory()"
  })
  public void testGetParsedDeploymentBuilderFactory() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getParsedDeploymentBuilderFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getParsedDeploymentBuilderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ParsedDeploymentBuilderFactory ProcessEngineConfigurationImpl.getParsedDeploymentBuilderFactory()"
  })
  public void testGetParsedDeploymentBuilderFactory2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getParsedDeploymentBuilderFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTimerManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerManager ProcessEngineConfigurationImpl.getTimerManager()"})
  public void testGetTimerManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTimerManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTimerManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerManager ProcessEngineConfigurationImpl.getTimerManager()"})
  public void testGetTimerManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTimerManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "EventSubscriptionManager ProcessEngineConfigurationImpl.getEventSubscriptionManager()"
  })
  public void testGetEventSubscriptionManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventSubscriptionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventSubscriptionManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "EventSubscriptionManager ProcessEngineConfigurationImpl.getEventSubscriptionManager()"
  })
  public void testGetEventSubscriptionManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventSubscriptionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnDeploymentHelper ProcessEngineConfigurationImpl.getBpmnDeploymentHelper()"
  })
  public void testGetBpmnDeploymentHelper() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBpmnDeploymentHelper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnDeploymentHelper ProcessEngineConfigurationImpl.getBpmnDeploymentHelper()"
  })
  public void testGetBpmnDeploymentHelper2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCachingAndArtifactsManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCachingAndArtifactsManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CachingAndArtifactsManager ProcessEngineConfigurationImpl.getCachingAndArtifactsManager()"
  })
  public void testGetCachingAndArtifactsManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCachingAndArtifactsManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCachingAndArtifactsManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCachingAndArtifactsManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CachingAndArtifactsManager ProcessEngineConfigurationImpl.getCachingAndArtifactsManager()"
  })
  public void testGetCachingAndArtifactsManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCachingAndArtifactsManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeployers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDeployers()"})
  public void testGetDeployers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeployers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getDeployers()"})
  public void testGetDeployers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdGenerator()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdGenerator()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.cfg.IdGenerator ProcessEngineConfigurationImpl.getIdGenerator()"
  })
  public void testGetIdGenerator_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdGenerator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdGenerator()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdGenerator()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.cfg.IdGenerator ProcessEngineConfigurationImpl.getIdGenerator()"
  })
  public void testGetIdGenerator_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdGenerator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getWsSyncFactoryClassName()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getWsSyncFactoryClassName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getWsSyncFactoryClassName()"})
  public void testGetWsSyncFactoryClassName() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_WS_SYNC_FACTORY,
        new JtaProcessEngineConfiguration().getWsSyncFactoryClassName());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getWsSyncFactoryClassName()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getWsSyncFactoryClassName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getWsSyncFactoryClassName()"})
  public void testGetWsSyncFactoryClassName2() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessEngineConfigurationImpl.DEFAULT_WS_SYNC_FACTORY,
        new JtaProcessEngineConfiguration().getWsSyncFactoryClassName());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}.
   *
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfigurationImpl.addWsEndpointAddress(QName, URL)"
  })
  public void testAddWsEndpointAddress_thenReturnJtaProcessEngineConfiguration()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualAddWsEndpointAddressResult =
        jtaProcessEngineConfiguration.addWsEndpointAddress(
            QName.valueOf("Q Name As String"),
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualAddWsEndpointAddressResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}.
   *
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfigurationImpl.addWsEndpointAddress(QName, URL)"
  })
  public void testAddWsEndpointAddress_thenReturnJtaProcessEngineConfiguration2()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualAddWsEndpointAddressResult =
        jtaProcessEngineConfiguration.addWsEndpointAddress(
            QName.valueOf("Q Name As String"),
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualAddWsEndpointAddressResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConcurrentMap ProcessEngineConfigurationImpl.getWsOverridenEndpointAddresses()"
  })
  public void testGetWsOverridenEndpointAddresses() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ConcurrentMap<QName, URL> actualWsOverridenEndpointAddresses =
        jtaProcessEngineConfiguration.getWsOverridenEndpointAddresses();

    // Assert
    assertTrue(actualWsOverridenEndpointAddresses.isEmpty());
    assertSame(
        jtaProcessEngineConfiguration.wsOverridenEndpointAddresses,
        actualWsOverridenEndpointAddresses);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getWsOverridenEndpointAddresses()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConcurrentMap ProcessEngineConfigurationImpl.getWsOverridenEndpointAddresses()"
  })
  public void testGetWsOverridenEndpointAddresses2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ConcurrentMap<QName, URL> actualWsOverridenEndpointAddresses =
        jtaProcessEngineConfiguration.getWsOverridenEndpointAddresses();

    // Assert
    assertTrue(actualWsOverridenEndpointAddresses.isEmpty());
    assertSame(
        jtaProcessEngineConfiguration.wsOverridenEndpointAddresses,
        actualWsOverridenEndpointAddresses);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getScriptingEngines()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getScriptingEngines()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.scripting.ScriptingEngines ProcessEngineConfigurationImpl.getScriptingEngines()"
  })
  public void testGetScriptingEngines() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getScriptingEngines());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getScriptingEngines()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getScriptingEngines()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.scripting.ScriptingEngines ProcessEngineConfigurationImpl.getScriptingEngines()"
  })
  public void testGetScriptingEngines2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getScriptingEngines());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableTypes()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getVariableTypes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.variable.VariableTypes ProcessEngineConfigurationImpl.getVariableTypes()"
  })
  public void testGetVariableTypes() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableTypes()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getVariableTypes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.variable.VariableTypes ProcessEngineConfigurationImpl.getVariableTypes()"
  })
  public void testGetVariableTypes2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJavaClassFieldForJackson()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJavaClassFieldForJackson()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getJavaClassFieldForJackson()"})
  public void testGetJavaClassFieldForJackson() {
    // Arrange, Act and Assert
    assertEquals("@class", new JtaProcessEngineConfiguration().getJavaClassFieldForJackson());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJavaClassFieldForJackson()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJavaClassFieldForJackson()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getJavaClassFieldForJackson()"})
  public void testGetJavaClassFieldForJackson2() {
    // Arrange, Act and Assert
    assertEquals("@class", new JtaProcessEngineConfiguration().getJavaClassFieldForJackson());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExpressionManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getExpressionManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExpressionManager ProcessEngineConfigurationImpl.getExpressionManager()"})
  public void testGetExpressionManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getExpressionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExpressionManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getExpressionManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExpressionManager ProcessEngineConfigurationImpl.getExpressionManager()"})
  public void testGetExpressionManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getExpressionManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBusinessCalendarManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBusinessCalendarManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.calendar.BusinessCalendarManager ProcessEngineConfigurationImpl.getBusinessCalendarManager()"
  })
  public void testGetBusinessCalendarManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBusinessCalendarManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBusinessCalendarManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBusinessCalendarManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.calendar.BusinessCalendarManager ProcessEngineConfigurationImpl.getBusinessCalendarManager()"
  })
  public void testGetBusinessCalendarManager_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBusinessCalendarManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionQueryLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getExecutionQueryLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getExecutionQueryLimit()"})
  public void testGetExecutionQueryLimit() {
    // Arrange, Act and Assert
    assertEquals(20000, new JtaProcessEngineConfiguration().getExecutionQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionQueryLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getExecutionQueryLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getExecutionQueryLimit()"})
  public void testGetExecutionQueryLimit2() {
    // Arrange, Act and Assert
    assertEquals(20000, new JtaProcessEngineConfiguration().getExecutionQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskQueryLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTaskQueryLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getTaskQueryLimit()"})
  public void testGetTaskQueryLimit() {
    // Arrange, Act and Assert
    assertEquals(20000, new JtaProcessEngineConfiguration().getTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskQueryLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTaskQueryLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getTaskQueryLimit()"})
  public void testGetTaskQueryLimit2() {
    // Arrange, Act and Assert
    assertEquals(20000, new JtaProcessEngineConfiguration().getTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskQueryLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoricTaskQueryLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getHistoricTaskQueryLimit()"})
  public void testGetHistoricTaskQueryLimit() {
    // Arrange, Act and Assert
    assertEquals(20000, new JtaProcessEngineConfiguration().getHistoricTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskQueryLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoricTaskQueryLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getHistoricTaskQueryLimit()"})
  public void testGetHistoricTaskQueryLimit2() {
    // Arrange, Act and Assert
    assertEquals(20000, new JtaProcessEngineConfiguration().getHistoricTaskQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getHistoricProcessInstancesQueryLimit()"})
  public void testGetHistoricProcessInstancesQueryLimit() {
    // Arrange, Act and Assert
    assertEquals(
        20000, new JtaProcessEngineConfiguration().getHistoricProcessInstancesQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricProcessInstancesQueryLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getHistoricProcessInstancesQueryLimit()"})
  public void testGetHistoricProcessInstancesQueryLimit2() {
    // Arrange, Act and Assert
    assertEquals(
        20000, new JtaProcessEngineConfiguration().getHistoricProcessInstancesQueryLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandContextFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommandContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CommandContextFactory ProcessEngineConfigurationImpl.getCommandContextFactory()"
  })
  public void testGetCommandContextFactory() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommandContextFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommandContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CommandContextFactory ProcessEngineConfigurationImpl.getCommandContextFactory()"
  })
  public void testGetCommandContextFactory2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommandContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTransactionContextFactory()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTransactionContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TransactionContextFactory ProcessEngineConfigurationImpl.getTransactionContextFactory()"
  })
  public void testGetTransactionContextFactory_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTransactionContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTransactionContextFactory()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTransactionContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TransactionContextFactory ProcessEngineConfigurationImpl.getTransactionContextFactory()"
  })
  public void testGetTransactionContextFactory_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTransactionContextFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreDeployers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPreDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPreDeployers()"})
  public void testGetCustomPreDeployers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPreDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreDeployers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPreDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPreDeployers()"})
  public void testGetCustomPreDeployers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPreDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostDeployers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPostDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPostDeployers()"})
  public void testGetCustomPostDeployers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPostDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostDeployers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPostDeployers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPostDeployers()"})
  public void testGetCustomPostDeployers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPostDeployers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJobHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getJobHandlers()"})
  public void testGetJobHandlers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJobHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getJobHandlers()"})
  public void testGetJobHandlers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessInstanceHelper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessInstanceHelper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.util.ProcessInstanceHelper ProcessEngineConfigurationImpl.getProcessInstanceHelper()"
  })
  public void testGetProcessInstanceHelper() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessInstanceHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessInstanceHelper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessInstanceHelper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.util.ProcessInstanceHelper ProcessEngineConfigurationImpl.getProcessInstanceHelper()"
  })
  public void testGetProcessInstanceHelper2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessInstanceHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getListenerNotificationHelper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getListenerNotificationHelper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper ProcessEngineConfigurationImpl.getListenerNotificationHelper()"
  })
  public void testGetListenerNotificationHelper() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getListenerNotificationHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getListenerNotificationHelper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getListenerNotificationHelper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper ProcessEngineConfigurationImpl.getListenerNotificationHelper()"
  })
  public void testGetListenerNotificationHelper2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getListenerNotificationHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSqlSessionFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSqlSessionFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.ibatis.session.SqlSessionFactory ProcessEngineConfigurationImpl.getSqlSessionFactory()"
  })
  public void testGetSqlSessionFactory() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSqlSessionFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSqlSessionFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.ibatis.session.SqlSessionFactory ProcessEngineConfigurationImpl.getSqlSessionFactory()"
  })
  public void testGetSqlSessionFactory2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDbSqlSessionFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDbSqlSessionFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DbSqlSessionFactory ProcessEngineConfigurationImpl.getDbSqlSessionFactory()"})
  public void testGetDbSqlSessionFactory() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDbSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDbSqlSessionFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDbSqlSessionFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DbSqlSessionFactory ProcessEngineConfigurationImpl.getDbSqlSessionFactory()"})
  public void testGetDbSqlSessionFactory2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDbSqlSessionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTransactionFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTransactionFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.ibatis.transaction.TransactionFactory ProcessEngineConfigurationImpl.getTransactionFactory()"
  })
  public void testGetTransactionFactory() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTransactionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTransactionFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTransactionFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.ibatis.transaction.TransactionFactory ProcessEngineConfigurationImpl.getTransactionFactory()"
  })
  public void testGetTransactionFactory2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTransactionFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomSessionFactories()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomSessionFactories()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomSessionFactories()"})
  public void testGetCustomSessionFactories() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomSessionFactories()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomSessionFactories()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomSessionFactories()"})
  public void testGetCustomSessionFactories2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomSessionFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomJobHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomJobHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomJobHandlers()"})
  public void testGetCustomJobHandlers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomJobHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomJobHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomJobHandlers()"})
  public void testGetCustomJobHandlers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomJobHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomScriptingEngineClasses()"})
  public void testGetCustomScriptingEngineClasses() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomScriptingEngineClasses());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomScriptingEngineClasses()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomScriptingEngineClasses()"})
  public void testGetCustomScriptingEngineClasses2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomScriptingEngineClasses());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreVariableTypes()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPreVariableTypes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPreVariableTypes()"})
  public void testGetCustomPreVariableTypes() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPreVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPreVariableTypes()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPreVariableTypes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPreVariableTypes()"})
  public void testGetCustomPreVariableTypes2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPreVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostVariableTypes()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPostVariableTypes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPostVariableTypes()"})
  public void testGetCustomPostVariableTypes() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPostVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomPostVariableTypes()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomPostVariableTypes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomPostVariableTypes()"})
  public void testGetCustomPostVariableTypes2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomPostVariableTypes());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPreBpmnParseHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPreBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getPreBpmnParseHandlers()"})
  public void testGetPreBpmnParseHandlers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getPreBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPreBpmnParseHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPreBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getPreBpmnParseHandlers()"})
  public void testGetPreBpmnParseHandlers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getPreBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomDefaultBpmnParseHandlers()"})
  public void testGetCustomDefaultBpmnParseHandlers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomDefaultBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getCustomDefaultBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomDefaultBpmnParseHandlers()"})
  public void testGetCustomDefaultBpmnParseHandlers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomDefaultBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPostBpmnParseHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPostBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getPostBpmnParseHandlers()"})
  public void testGetPostBpmnParseHandlers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getPostBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPostBpmnParseHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPostBpmnParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getPostBpmnParseHandlers()"})
  public void testGetPostBpmnParseHandlers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getPostBpmnParseHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getActivityBehaviorFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getActivityBehaviorFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivityBehaviorFactory ProcessEngineConfigurationImpl.getActivityBehaviorFactory()"
  })
  public void testGetActivityBehaviorFactory() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getActivityBehaviorFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getActivityBehaviorFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivityBehaviorFactory ProcessEngineConfigurationImpl.getActivityBehaviorFactory()"
  })
  public void testGetActivityBehaviorFactory2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getActivityBehaviorFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getListenerFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getListenerFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.bpmn.parser.factory.ListenerFactory ProcessEngineConfigurationImpl.getListenerFactory()"
  })
  public void testGetListenerFactory() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getListenerFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getListenerFactory()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getListenerFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.bpmn.parser.factory.ListenerFactory ProcessEngineConfigurationImpl.getListenerFactory()"
  })
  public void testGetListenerFactory2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getListenerFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnParseFactory()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBpmnParseFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.cfg.BpmnParseFactory ProcessEngineConfigurationImpl.getBpmnParseFactory()"
  })
  public void testGetBpmnParseFactory_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBpmnParseFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBpmnParseFactory()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBpmnParseFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.cfg.BpmnParseFactory ProcessEngineConfigurationImpl.getBpmnParseFactory()"
  })
  public void testGetBpmnParseFactory_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBpmnParseFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBeans()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBeans()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getBeans()"})
  public void testGetBeans() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBeans());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBeans()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBeans()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getBeans()"})
  public void testGetBeans2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getBeans());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResolverFactories()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getResolverFactories()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getResolverFactories()"})
  public void testGetResolverFactories() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getResolverFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResolverFactories()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getResolverFactories()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getResolverFactories()"})
  public void testGetResolverFactories2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getResolverFactories());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomFunctionProviders()"})
  public void testGetCustomFunctionProviders() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomFunctionProviders());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomFunctionProviders()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomFunctionProviders()"})
  public void testGetCustomFunctionProviders2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomFunctionProviders());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addCustomFunctionProvider(CustomFunctionProvider)"
  })
  public void testAddCustomFunctionProvider() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomFunctionProviders(new ArrayList<>());

    // Act
    ProcessEngineConfigurationImpl actualAddCustomFunctionProviderResult =
        multiSchemaMultiTenantProcessEngineConfiguration.addCustomFunctionProvider(
            mock(CustomFunctionProvider.class));

    // Assert
    assertSame(
        multiSchemaMultiTenantProcessEngineConfiguration, actualAddCustomFunctionProviderResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addCustomFunctionProvider(CustomFunctionProvider)"
  })
  public void testAddCustomFunctionProvider2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomFunctionProviders(new ArrayList<>());

    // Act
    ProcessEngineConfigurationImpl actualAddCustomFunctionProviderResult =
        multiSchemaMultiTenantProcessEngineConfiguration.addCustomFunctionProvider(
            mock(CustomFunctionProvider.class));

    // Assert
    assertSame(
        multiSchemaMultiTenantProcessEngineConfiguration, actualAddCustomFunctionProviderResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   *
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addCustomFunctionProvider(CustomFunctionProvider)"
  })
  public void testAddCustomFunctionProvider_thenReturnJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualAddCustomFunctionProviderResult =
        jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualAddCustomFunctionProviderResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   *
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addCustomFunctionProvider(CustomFunctionProvider)"
  })
  public void testAddCustomFunctionProvider_thenReturnJtaProcessEngineConfiguration2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualAddCustomFunctionProviderResult =
        jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualAddCustomFunctionProviderResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeploymentManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.deploy.DeploymentManager ProcessEngineConfigurationImpl.getDeploymentManager()"
  })
  public void testGetDeploymentManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeploymentManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeploymentManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.deploy.DeploymentManager ProcessEngineConfigurationImpl.getDeploymentManager()"
  })
  public void testGetDeploymentManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeploymentManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDelegateInterceptor()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDelegateInterceptor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.interceptor.DelegateInterceptor ProcessEngineConfigurationImpl.getDelegateInterceptor()"
  })
  public void testGetDelegateInterceptor_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDelegateInterceptor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDelegateInterceptor()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDelegateInterceptor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.interceptor.DelegateInterceptor ProcessEngineConfigurationImpl.getDelegateInterceptor()"
  })
  public void testGetDelegateInterceptor_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDelegateInterceptor());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getEventHandlers()"})
  public void testGetEventHandlers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getEventHandlers()"})
  public void testGetEventHandlers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomEventHandlers()"})
  public void testGetCustomEventHandlers() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCustomEventHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getCustomEventHandlers()"})
  public void testGetCustomEventHandlers2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCustomEventHandlers());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getFailedJobCommandFactory()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getFailedJobCommandFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.jobexecutor.FailedJobCommandFactory ProcessEngineConfigurationImpl.getFailedJobCommandFactory()"
  })
  public void testGetFailedJobCommandFactory_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getFailedJobCommandFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getFailedJobCommandFactory()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getFailedJobCommandFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.jobexecutor.FailedJobCommandFactory ProcessEngineConfigurationImpl.getFailedJobCommandFactory()"
  })
  public void testGetFailedJobCommandFactory_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getFailedJobCommandFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSource()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSource()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "javax.sql.DataSource ProcessEngineConfigurationImpl.getIdGeneratorDataSource()"
  })
  public void testGetIdGeneratorDataSource() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdGeneratorDataSource());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSource()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSource()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "javax.sql.DataSource ProcessEngineConfigurationImpl.getIdGeneratorDataSource()"
  })
  public void testGetIdGeneratorDataSource2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdGeneratorDataSource());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getIdGeneratorDataSourceJndiName()"})
  public void testGetIdGeneratorDataSourceJndiName() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdGeneratorDataSourceJndiName());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdGeneratorDataSourceJndiName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getIdGeneratorDataSourceJndiName()"})
  public void testGetIdGeneratorDataSourceJndiName2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdGeneratorDataSourceJndiName());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBatchSizeProcessInstances()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBatchSizeProcessInstances()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getBatchSizeProcessInstances()"})
  public void testGetBatchSizeProcessInstances() {
    // Arrange, Act and Assert
    assertEquals(25, new JtaProcessEngineConfiguration().getBatchSizeProcessInstances());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBatchSizeProcessInstances()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBatchSizeProcessInstances()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getBatchSizeProcessInstances()"})
  public void testGetBatchSizeProcessInstances2() {
    // Arrange, Act and Assert
    assertEquals(25, new JtaProcessEngineConfiguration().getBatchSizeProcessInstances());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBatchSizeTasks()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBatchSizeTasks()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getBatchSizeTasks()"})
  public void testGetBatchSizeTasks() {
    // Arrange, Act and Assert
    assertEquals(25, new JtaProcessEngineConfiguration().getBatchSizeTasks());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getBatchSizeTasks()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getBatchSizeTasks()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getBatchSizeTasks()"})
  public void testGetBatchSizeTasks2() {
    // Arrange, Act and Assert
    assertEquals(25, new JtaProcessEngineConfiguration().getBatchSizeTasks());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionCacheLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionCacheLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getProcessDefinitionCacheLimit()"})
  public void testGetProcessDefinitionCacheLimit() {
    // Arrange, Act and Assert
    assertEquals(-1, new JtaProcessEngineConfiguration().getProcessDefinitionCacheLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionCacheLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionCacheLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getProcessDefinitionCacheLimit()"})
  public void testGetProcessDefinitionCacheLimit2() {
    // Arrange, Act and Assert
    assertEquals(-1, new JtaProcessEngineConfiguration().getProcessDefinitionCacheLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionCache()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionCache()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.deploy.DeploymentCache ProcessEngineConfigurationImpl.getProcessDefinitionCache()"
  })
  public void testGetProcessDefinitionCache() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionCache()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionCache()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.deploy.DeploymentCache ProcessEngineConfigurationImpl.getProcessDefinitionCache()"
  })
  public void testGetProcessDefinitionCache2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCacheLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCacheLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getKnowledgeBaseCacheLimit()"})
  public void testGetKnowledgeBaseCacheLimit() {
    // Arrange, Act and Assert
    assertEquals(-1, new JtaProcessEngineConfiguration().getKnowledgeBaseCacheLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCacheLimit()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCacheLimit()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getKnowledgeBaseCacheLimit()"})
  public void testGetKnowledgeBaseCacheLimit2() {
    // Arrange, Act and Assert
    assertEquals(-1, new JtaProcessEngineConfiguration().getKnowledgeBaseCacheLimit());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCache()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCache()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.deploy.DeploymentCache ProcessEngineConfigurationImpl.getKnowledgeBaseCache()"
  })
  public void testGetKnowledgeBaseCache() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getKnowledgeBaseCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCache()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getKnowledgeBaseCache()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.deploy.DeploymentCache ProcessEngineConfigurationImpl.getKnowledgeBaseCache()"
  })
  public void testGetKnowledgeBaseCache2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getKnowledgeBaseCache());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventDispatcher()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.delegate.event.ActivitiEventDispatcher ProcessEngineConfigurationImpl.getEventDispatcher()"
  })
  public void testGetEventDispatcher() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventDispatcher());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventDispatcher()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.delegate.event.ActivitiEventDispatcher ProcessEngineConfigurationImpl.getEventDispatcher()"
  })
  public void testGetEventDispatcher2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventDispatcher());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getTypedEventListeners()"})
  public void testGetTypedEventListeners() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTypedEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTypedEventListeners()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessEngineConfigurationImpl.getTypedEventListeners()"})
  public void testGetTypedEventListeners2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTypedEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventListeners()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventListeners()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getEventListeners()"})
  public void testGetEventListeners() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventListeners()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventListeners()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProcessEngineConfigurationImpl.getEventListeners()"})
  public void testGetEventListeners2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventListeners());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessValidator()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessValidator()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.validation.ProcessValidator ProcessEngineConfigurationImpl.getProcessValidator()"
  })
  public void testGetProcessValidator() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessValidator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessValidator()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessValidator()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.validation.ProcessValidator ProcessEngineConfigurationImpl.getProcessValidator()"
  })
  public void testGetProcessValidator2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessValidator());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthStringVariableType()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthStringVariableType()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthStringVariableType()"})
  public void testGetMaxLengthStringVariableType() {
    // Arrange, Act and Assert
    assertEquals(-1, new JtaProcessEngineConfiguration().getMaxLengthStringVariableType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxLengthStringVariableType()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxLengthStringVariableType()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxLengthStringVariableType()"})
  public void testGetMaxLengthStringVariableType2() {
    // Arrange, Act and Assert
    assertEquals(-1, new JtaProcessEngineConfiguration().getMaxLengthStringVariableType());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxNrOfStatementsInBulkInsert()"})
  public void testGetMaxNrOfStatementsInBulkInsert() {
    // Arrange, Act and Assert
    assertEquals(100, new JtaProcessEngineConfiguration().getMaxNrOfStatementsInBulkInsert());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getMaxNrOfStatementsInBulkInsert()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getMaxNrOfStatementsInBulkInsert()"})
  public void testGetMaxNrOfStatementsInBulkInsert2() {
    // Arrange, Act and Assert
    assertEquals(100, new JtaProcessEngineConfiguration().getMaxNrOfStatementsInBulkInsert());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"PerformanceSettings ProcessEngineConfigurationImpl.getPerformanceSettings()"})
  public void testGetPerformanceSettings() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    PerformanceSettings actualPerformanceSettings =
        jtaProcessEngineConfiguration.getPerformanceSettings();

    // Assert
    assertSame(jtaProcessEngineConfiguration.performanceSettings, actualPerformanceSettings);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"PerformanceSettings ProcessEngineConfigurationImpl.getPerformanceSettings()"})
  public void testGetPerformanceSettings2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    PerformanceSettings actualPerformanceSettings =
        jtaProcessEngineConfiguration.getPerformanceSettings();

    // Assert
    assertSame(jtaProcessEngineConfiguration.performanceSettings, actualPerformanceSettings);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.AttachmentDataManager ProcessEngineConfigurationImpl.getAttachmentDataManager()"
  })
  public void testGetAttachmentDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAttachmentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAttachmentDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.AttachmentDataManager ProcessEngineConfigurationImpl.getAttachmentDataManager()"
  })
  public void testGetAttachmentDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAttachmentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getByteArrayDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getByteArrayDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ByteArrayDataManager ProcessEngineConfigurationImpl.getByteArrayDataManager()"
  })
  public void testGetByteArrayDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getByteArrayDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getByteArrayDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getByteArrayDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ByteArrayDataManager ProcessEngineConfigurationImpl.getByteArrayDataManager()"
  })
  public void testGetByteArrayDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getByteArrayDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommentDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommentDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.CommentDataManager ProcessEngineConfigurationImpl.getCommentDataManager()"
  })
  public void testGetCommentDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommentDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommentDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.CommentDataManager ProcessEngineConfigurationImpl.getCommentDataManager()"
  })
  public void testGetCommentDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeploymentDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.DeploymentDataManager ProcessEngineConfigurationImpl.getDeploymentDataManager()"
  })
  public void testGetDeploymentDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeploymentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeploymentDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.DeploymentDataManager ProcessEngineConfigurationImpl.getDeploymentDataManager()"
  })
  public void testGetDeploymentDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeploymentDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventLogEntryDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventLogEntryDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.EventLogEntryDataManager ProcessEngineConfigurationImpl.getEventLogEntryDataManager()"
  })
  public void testGetEventLogEntryDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventLogEntryDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventLogEntryDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventLogEntryDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.EventLogEntryDataManager ProcessEngineConfigurationImpl.getEventLogEntryDataManager()"
  })
  public void testGetEventLogEntryDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventLogEntryDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.EventSubscriptionDataManager ProcessEngineConfigurationImpl.getEventSubscriptionDataManager()"
  })
  public void testGetEventSubscriptionDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventSubscriptionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventSubscriptionDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.EventSubscriptionDataManager ProcessEngineConfigurationImpl.getEventSubscriptionDataManager()"
  })
  public void testGetEventSubscriptionDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventSubscriptionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getExecutionDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager ProcessEngineConfigurationImpl.getExecutionDataManager()"
  })
  public void testGetExecutionDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getExecutionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getExecutionDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager ProcessEngineConfigurationImpl.getExecutionDataManager()"
  })
  public void testGetExecutionDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getExecutionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricActivityInstanceDataManager ProcessEngineConfigurationImpl.getHistoricActivityInstanceDataManager()"
  })
  public void testGetHistoricActivityInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricActivityInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricActivityInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricActivityInstanceDataManager ProcessEngineConfigurationImpl.getHistoricActivityInstanceDataManager()"
  })
  public void testGetHistoricActivityInstanceDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricActivityInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricDetailDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoricDetailDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricDetailDataManager ProcessEngineConfigurationImpl.getHistoricDetailDataManager()"
  })
  public void testGetHistoricDetailDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricDetailDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricDetailDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoricDetailDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricDetailDataManager ProcessEngineConfigurationImpl.getHistoricDetailDataManager()"
  })
  public void testGetHistoricDetailDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricDetailDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricIdentityLinkDataManager ProcessEngineConfigurationImpl.getHistoricIdentityLinkDataManager()"
  })
  public void testGetHistoricIdentityLinkDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricIdentityLinkDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricIdentityLinkDataManager ProcessEngineConfigurationImpl.getHistoricIdentityLinkDataManager()"
  })
  public void testGetHistoricIdentityLinkDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricProcessInstanceDataManager ProcessEngineConfigurationImpl.getHistoricProcessInstanceDataManager()"
  })
  public void testGetHistoricProcessInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricProcessInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricProcessInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricProcessInstanceDataManager ProcessEngineConfigurationImpl.getHistoricProcessInstanceDataManager()"
  })
  public void testGetHistoricProcessInstanceDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricProcessInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricTaskInstanceDataManager ProcessEngineConfigurationImpl.getHistoricTaskInstanceDataManager()"
  })
  public void testGetHistoricTaskInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricTaskInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricTaskInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricTaskInstanceDataManager ProcessEngineConfigurationImpl.getHistoricTaskInstanceDataManager()"
  })
  public void testGetHistoricTaskInstanceDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricTaskInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricVariableInstanceDataManager ProcessEngineConfigurationImpl.getHistoricVariableInstanceDataManager()"
  })
  public void testGetHistoricVariableInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricVariableInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.HistoricVariableInstanceDataManager ProcessEngineConfigurationImpl.getHistoricVariableInstanceDataManager()"
  })
  public void testGetHistoricVariableInstanceDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdentityLinkDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdentityLinkDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager ProcessEngineConfigurationImpl.getIdentityLinkDataManager()"
  })
  public void testGetIdentityLinkDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdentityLinkDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdentityLinkDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager ProcessEngineConfigurationImpl.getIdentityLinkDataManager()"
  })
  public void testGetIdentityLinkDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdentityLinkDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJobDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.JobDataManager ProcessEngineConfigurationImpl.getJobDataManager()"
  })
  public void testGetJobDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJobDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.JobDataManager ProcessEngineConfigurationImpl.getJobDataManager()"
  })
  public void testGetJobDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerJobDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTimerJobDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.TimerJobDataManager ProcessEngineConfigurationImpl.getTimerJobDataManager()"
  })
  public void testGetTimerJobDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTimerJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerJobDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTimerJobDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.TimerJobDataManager ProcessEngineConfigurationImpl.getTimerJobDataManager()"
  })
  public void testGetTimerJobDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTimerJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSuspendedJobDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSuspendedJobDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.SuspendedJobDataManager ProcessEngineConfigurationImpl.getSuspendedJobDataManager()"
  })
  public void testGetSuspendedJobDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSuspendedJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSuspendedJobDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSuspendedJobDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.SuspendedJobDataManager ProcessEngineConfigurationImpl.getSuspendedJobDataManager()"
  })
  public void testGetSuspendedJobDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSuspendedJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeadLetterJobDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeadLetterJobDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.DeadLetterJobDataManager ProcessEngineConfigurationImpl.getDeadLetterJobDataManager()"
  })
  public void testGetDeadLetterJobDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeadLetterJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeadLetterJobDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeadLetterJobDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.DeadLetterJobDataManager ProcessEngineConfigurationImpl.getDeadLetterJobDataManager()"
  })
  public void testGetDeadLetterJobDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeadLetterJobDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getModelDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getModelDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ModelDataManager ProcessEngineConfigurationImpl.getModelDataManager()"
  })
  public void testGetModelDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getModelDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getModelDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getModelDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ModelDataManager ProcessEngineConfigurationImpl.getModelDataManager()"
  })
  public void testGetModelDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getModelDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager ProcessEngineConfigurationImpl.getProcessDefinitionDataManager()"
  })
  public void testGetProcessDefinitionDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager ProcessEngineConfigurationImpl.getProcessDefinitionDataManager()"
  })
  public void testGetProcessDefinitionDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionInfoDataManager ProcessEngineConfigurationImpl.getProcessDefinitionInfoDataManager()"
  })
  public void testGetProcessDefinitionInfoDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionInfoDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getProcessDefinitionInfoDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionInfoDataManager ProcessEngineConfigurationImpl.getProcessDefinitionInfoDataManager()"
  })
  public void testGetProcessDefinitionInfoDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionInfoDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPropertyDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPropertyDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.PropertyDataManager ProcessEngineConfigurationImpl.getPropertyDataManager()"
  })
  public void testGetPropertyDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getPropertyDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPropertyDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPropertyDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.PropertyDataManager ProcessEngineConfigurationImpl.getPropertyDataManager()"
  })
  public void testGetPropertyDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getPropertyDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResourceDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getResourceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ResourceDataManager ProcessEngineConfigurationImpl.getResourceDataManager()"
  })
  public void testGetResourceDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getResourceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResourceDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getResourceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.ResourceDataManager ProcessEngineConfigurationImpl.getResourceDataManager()"
  })
  public void testGetResourceDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getResourceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTaskDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.TaskDataManager ProcessEngineConfigurationImpl.getTaskDataManager()"
  })
  public void testGetTaskDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTaskDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTaskDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.TaskDataManager ProcessEngineConfigurationImpl.getTaskDataManager()"
  })
  public void testGetTaskDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTaskDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableInstanceDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getVariableInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.VariableInstanceDataManager ProcessEngineConfigurationImpl.getVariableInstanceDataManager()"
  })
  public void testGetVariableInstanceDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableInstanceDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getVariableInstanceDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.data.VariableInstanceDataManager ProcessEngineConfigurationImpl.getVariableInstanceDataManager()"
  })
  public void testGetVariableInstanceDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getVariableInstanceDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.AttachmentEntityManager ProcessEngineConfigurationImpl.getAttachmentEntityManager()"
  })
  public void testGetAttachmentEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAttachmentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.AttachmentEntityManager ProcessEngineConfigurationImpl.getAttachmentEntityManager()"
  })
  public void testGetAttachmentEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAttachmentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getByteArrayEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getByteArrayEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ByteArrayEntityManager ProcessEngineConfigurationImpl.getByteArrayEntityManager()"
  })
  public void testGetByteArrayEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getByteArrayEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getByteArrayEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getByteArrayEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ByteArrayEntityManager ProcessEngineConfigurationImpl.getByteArrayEntityManager()"
  })
  public void testGetByteArrayEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getByteArrayEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.CommentEntityManager ProcessEngineConfigurationImpl.getCommentEntityManager()"
  })
  public void testGetCommentEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.CommentEntityManager ProcessEngineConfigurationImpl.getCommentEntityManager()"
  })
  public void testGetCommentEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getCommentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeploymentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.DeploymentEntityManager ProcessEngineConfigurationImpl.getDeploymentEntityManager()"
  })
  public void testGetDeploymentEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeploymentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeploymentEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeploymentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.DeploymentEntityManager ProcessEngineConfigurationImpl.getDeploymentEntityManager()"
  })
  public void testGetDeploymentEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeploymentEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventLogEntryEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventLogEntryEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.EventLogEntryEntityManager ProcessEngineConfigurationImpl.getEventLogEntryEntityManager()"
  })
  public void testGetEventLogEntryEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventLogEntryEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventLogEntryEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getEventLogEntryEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.EventLogEntryEntityManager ProcessEngineConfigurationImpl.getEventLogEntryEntityManager()"
  })
  public void testGetEventLogEntryEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventLogEntryEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityManager ProcessEngineConfigurationImpl.getEventSubscriptionEntityManager()"
  })
  public void testGetEventSubscriptionEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventSubscriptionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityManager ProcessEngineConfigurationImpl.getEventSubscriptionEntityManager()"
  })
  public void testGetEventSubscriptionEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getEventSubscriptionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getExecutionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ExecutionEntityManager ProcessEngineConfigurationImpl.getExecutionEntityManager()"
  })
  public void testGetExecutionEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getExecutionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getExecutionEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getExecutionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ExecutionEntityManager ProcessEngineConfigurationImpl.getExecutionEntityManager()"
  })
  public void testGetExecutionEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getExecutionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricActivityInstanceEntityManager()"
  })
  public void testGetHistoricActivityInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricActivityInstanceEntityManager()"
  })
  public void testGetHistoricActivityInstanceEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricDetailEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoricDetailEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricDetailEntityManager ProcessEngineConfigurationImpl.getHistoricDetailEntityManager()"
  })
  public void testGetHistoricDetailEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricDetailEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricDetailEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoricDetailEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricDetailEntityManager ProcessEngineConfigurationImpl.getHistoricDetailEntityManager()"
  })
  public void testGetHistoricDetailEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricDetailEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager ProcessEngineConfigurationImpl.getHistoricIdentityLinkEntityManager()"
  })
  public void testGetHistoricIdentityLinkEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager ProcessEngineConfigurationImpl.getHistoricIdentityLinkEntityManager()"
  })
  public void testGetHistoricIdentityLinkEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricProcessInstanceEntityManager()"
  })
  public void testGetHistoricProcessInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricProcessInstanceEntityManager()"
  })
  public void testGetHistoricProcessInstanceEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricTaskInstanceEntityManager()"
  })
  public void testGetHistoricTaskInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricTaskInstanceEntityManager()"
  })
  public void testGetHistoricTaskInstanceEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricVariableInstanceEntityManager()"
  })
  public void testGetHistoricVariableInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManager ProcessEngineConfigurationImpl.getHistoricVariableInstanceEntityManager()"
  })
  public void testGetHistoricVariableInstanceEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager ProcessEngineConfigurationImpl.getIdentityLinkEntityManager()"
  })
  public void testGetIdentityLinkEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager ProcessEngineConfigurationImpl.getIdentityLinkEntityManager()"
  })
  public void testGetIdentityLinkEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getIdentityLinkEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.JobEntityManager ProcessEngineConfigurationImpl.getJobEntityManager()"
  })
  public void testGetJobEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.JobEntityManager ProcessEngineConfigurationImpl.getJobEntityManager()"
  })
  public void testGetJobEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.TimerJobEntityManager ProcessEngineConfigurationImpl.getTimerJobEntityManager()"
  })
  public void testGetTimerJobEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTimerJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.TimerJobEntityManager ProcessEngineConfigurationImpl.getTimerJobEntityManager()"
  })
  public void testGetTimerJobEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTimerJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSuspendedJobEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSuspendedJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManager ProcessEngineConfigurationImpl.getSuspendedJobEntityManager()"
  })
  public void testGetSuspendedJobEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSuspendedJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getSuspendedJobEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getSuspendedJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManager ProcessEngineConfigurationImpl.getSuspendedJobEntityManager()"
  })
  public void testGetSuspendedJobEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getSuspendedJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeadLetterJobEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeadLetterJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManager ProcessEngineConfigurationImpl.getDeadLetterJobEntityManager()"
  })
  public void testGetDeadLetterJobEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeadLetterJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDeadLetterJobEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getDeadLetterJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManager ProcessEngineConfigurationImpl.getDeadLetterJobEntityManager()"
  })
  public void testGetDeadLetterJobEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getDeadLetterJobEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getModelEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getModelEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ModelEntityManager ProcessEngineConfigurationImpl.getModelEntityManager()"
  })
  public void testGetModelEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getModelEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getModelEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getModelEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ModelEntityManager ProcessEngineConfigurationImpl.getModelEntityManager()"
  })
  public void testGetModelEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getModelEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManager ProcessEngineConfigurationImpl.getProcessDefinitionEntityManager()"
  })
  public void testGetProcessDefinitionEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getProcessDefinitionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManager ProcessEngineConfigurationImpl.getProcessDefinitionEntityManager()"
  })
  public void testGetProcessDefinitionEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManager ProcessEngineConfigurationImpl.getProcessDefinitionInfoEntityManager()"
  })
  public void testGetProcessDefinitionInfoEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionInfoEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManager ProcessEngineConfigurationImpl.getProcessDefinitionInfoEntityManager()"
  })
  public void testGetProcessDefinitionInfoEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionInfoEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPropertyEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPropertyEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.PropertyEntityManager ProcessEngineConfigurationImpl.getPropertyEntityManager()"
  })
  public void testGetPropertyEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getPropertyEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getPropertyEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getPropertyEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.PropertyEntityManager ProcessEngineConfigurationImpl.getPropertyEntityManager()"
  })
  public void testGetPropertyEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getPropertyEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResourceEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getResourceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ResourceEntityManager ProcessEngineConfigurationImpl.getResourceEntityManager()"
  })
  public void testGetResourceEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getResourceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getResourceEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getResourceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ResourceEntityManager ProcessEngineConfigurationImpl.getResourceEntityManager()"
  })
  public void testGetResourceEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getResourceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.TaskEntityManager ProcessEngineConfigurationImpl.getTaskEntityManager()"
  })
  public void testGetTaskEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTaskEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.TaskEntityManager ProcessEngineConfigurationImpl.getTaskEntityManager()"
  })
  public void testGetTaskEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTaskEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManager ProcessEngineConfigurationImpl.getVariableInstanceEntityManager()"
  })
  public void testGetVariableInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getVariableInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManager ProcessEngineConfigurationImpl.getVariableInstanceEntityManager()"
  })
  public void testGetVariableInstanceEntityManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getVariableInstanceEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTableDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTableDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.TableDataManager ProcessEngineConfigurationImpl.getTableDataManager()"
  })
  public void testGetTableDataManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTableDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getTableDataManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getTableDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.TableDataManager ProcessEngineConfigurationImpl.getTableDataManager()"
  })
  public void testGetTableDataManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getTableDataManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoryManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoryManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.history.HistoryManager ProcessEngineConfigurationImpl.getHistoryManager()"
  })
  public void testGetHistoryManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoryManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getHistoryManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getHistoryManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.history.HistoryManager ProcessEngineConfigurationImpl.getHistoryManager()"
  })
  public void testGetHistoryManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getHistoryManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJobManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.asyncexecutor.JobManager ProcessEngineConfigurationImpl.getJobManager()"
  })
  public void testGetJobManager() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJobManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getJobManager()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getJobManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.asyncexecutor.JobManager ProcessEngineConfigurationImpl.getJobManager()"
  })
  public void testGetJobManager2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getJobManager());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DelegateExpressionFieldInjectionMode ProcessEngineConfigurationImpl.getDelegateExpressionFieldInjectionMode()"
  })
  public void testGetDelegateExpressionFieldInjectionMode() {
    // Arrange, Act and Assert
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        new JtaProcessEngineConfiguration().getDelegateExpressionFieldInjectionMode());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getDelegateExpressionFieldInjectionMode()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DelegateExpressionFieldInjectionMode ProcessEngineConfigurationImpl.getDelegateExpressionFieldInjectionMode()"
  })
  public void testGetDelegateExpressionFieldInjectionMode2() {
    // Arrange, Act and Assert
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        new JtaProcessEngineConfiguration().getDelegateExpressionFieldInjectionMode());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getObjectMapper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectMapper ProcessEngineConfigurationImpl.getObjectMapper()"})
  public void testGetObjectMapper() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ObjectMapper actualObjectMapper = jtaProcessEngineConfiguration.getObjectMapper();

    // Assert
    assertSame(jtaProcessEngineConfiguration.objectMapper, actualObjectMapper);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getObjectMapper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectMapper ProcessEngineConfigurationImpl.getObjectMapper()"})
  public void testGetObjectMapper2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ObjectMapper actualObjectMapper = jtaProcessEngineConfiguration.getObjectMapper();

    // Assert
    assertSame(jtaProcessEngineConfiguration.objectMapper, actualObjectMapper);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorCorePoolSize()"})
  public void testGetAsyncExecutorCorePoolSize() {
    // Arrange, Act and Assert
    assertEquals(2, new JtaProcessEngineConfiguration().getAsyncExecutorCorePoolSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorCorePoolSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorCorePoolSize()"})
  public void testGetAsyncExecutorCorePoolSize2() {
    // Arrange, Act and Assert
    assertEquals(2, new JtaProcessEngineConfiguration().getAsyncExecutorCorePoolSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorNumberOfRetries()"})
  public void testGetAsyncExecutorNumberOfRetries() {
    // Arrange, Act and Assert
    assertEquals(3, new JtaProcessEngineConfiguration().getAsyncExecutorNumberOfRetries());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorNumberOfRetries()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorNumberOfRetries()"})
  public void testGetAsyncExecutorNumberOfRetries2() {
    // Arrange, Act and Assert
    assertEquals(3, new JtaProcessEngineConfiguration().getAsyncExecutorNumberOfRetries());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxPoolSize()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxPoolSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorMaxPoolSize()"})
  public void testGetAsyncExecutorMaxPoolSize() {
    // Arrange, Act and Assert
    assertEquals(10, new JtaProcessEngineConfiguration().getAsyncExecutorMaxPoolSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxPoolSize()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxPoolSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorMaxPoolSize()"})
  public void testGetAsyncExecutorMaxPoolSize2() {
    // Arrange, Act and Assert
    assertEquals(10, new JtaProcessEngineConfiguration().getAsyncExecutorMaxPoolSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ProcessEngineConfigurationImpl.getAsyncExecutorThreadKeepAliveTime()"})
  public void testGetAsyncExecutorThreadKeepAliveTime() {
    // Arrange, Act and Assert
    assertEquals(5000L, new JtaProcessEngineConfiguration().getAsyncExecutorThreadKeepAliveTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorThreadKeepAliveTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ProcessEngineConfigurationImpl.getAsyncExecutorThreadKeepAliveTime()"})
  public void testGetAsyncExecutorThreadKeepAliveTime2() {
    // Arrange, Act and Assert
    assertEquals(5000L, new JtaProcessEngineConfiguration().getAsyncExecutorThreadKeepAliveTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorThreadPoolQueueSize()"})
  public void testGetAsyncExecutorThreadPoolQueueSize() {
    // Arrange, Act and Assert
    assertEquals(100, new JtaProcessEngineConfiguration().getAsyncExecutorThreadPoolQueueSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueueSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorThreadPoolQueueSize()"})
  public void testGetAsyncExecutorThreadPoolQueueSize2() {
    // Arrange, Act and Assert
    assertEquals(100, new JtaProcessEngineConfiguration().getAsyncExecutorThreadPoolQueueSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.concurrent.BlockingQueue ProcessEngineConfigurationImpl.getAsyncExecutorThreadPoolQueue()"
  })
  public void testGetAsyncExecutorThreadPoolQueue() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAsyncExecutorThreadPoolQueue());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorThreadPoolQueue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.concurrent.BlockingQueue ProcessEngineConfigurationImpl.getAsyncExecutorThreadPoolQueue()"
  })
  public void testGetAsyncExecutorThreadPoolQueue2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAsyncExecutorThreadPoolQueue());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long ProcessEngineConfigurationImpl.getAsyncExecutorSecondsToWaitOnShutdown()"
  })
  public void testGetAsyncExecutorSecondsToWaitOnShutdown() {
    // Arrange, Act and Assert
    assertEquals(
        60L, new JtaProcessEngineConfiguration().getAsyncExecutorSecondsToWaitOnShutdown());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorSecondsToWaitOnShutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long ProcessEngineConfigurationImpl.getAsyncExecutorSecondsToWaitOnShutdown()"
  })
  public void testGetAsyncExecutorSecondsToWaitOnShutdown2() {
    // Arrange, Act and Assert
    assertEquals(
        60L, new JtaProcessEngineConfiguration().getAsyncExecutorSecondsToWaitOnShutdown());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorMaxTimerJobsPerAcquisition()"
  })
  public void testGetAsyncExecutorMaxTimerJobsPerAcquisition() {
    // Arrange, Act and Assert
    assertEquals(
        1, new JtaProcessEngineConfiguration().getAsyncExecutorMaxTimerJobsPerAcquisition());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorMaxTimerJobsPerAcquisition()"
  })
  public void testGetAsyncExecutorMaxTimerJobsPerAcquisition2() {
    // Arrange, Act and Assert
    assertEquals(
        1, new JtaProcessEngineConfiguration().getAsyncExecutorMaxTimerJobsPerAcquisition());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorMaxAsyncJobsDuePerAcquisition()"
  })
  public void testGetAsyncExecutorMaxAsyncJobsDuePerAcquisition() {
    // Arrange, Act and Assert
    assertEquals(
        1, new JtaProcessEngineConfiguration().getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorMaxAsyncJobsDuePerAcquisition()"
  })
  public void testGetAsyncExecutorMaxAsyncJobsDuePerAcquisition2() {
    // Arrange, Act and Assert
    assertEquals(
        1, new JtaProcessEngineConfiguration().getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorDefaultTimerJobAcquireWaitTime()"
  })
  public void testGetAsyncExecutorDefaultTimerJobAcquireWaitTime() {
    // Arrange, Act and Assert
    assertEquals(
        10000,
        new JtaProcessEngineConfiguration().getAsyncExecutorDefaultTimerJobAcquireWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorDefaultTimerJobAcquireWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorDefaultTimerJobAcquireWaitTime()"
  })
  public void testGetAsyncExecutorDefaultTimerJobAcquireWaitTime2() {
    // Arrange, Act and Assert
    assertEquals(
        10000,
        new JtaProcessEngineConfiguration().getAsyncExecutorDefaultTimerJobAcquireWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorDefaultAsyncJobAcquireWaitTime()"
  })
  public void testGetAsyncExecutorDefaultAsyncJobAcquireWaitTime() {
    // Arrange, Act and Assert
    assertEquals(
        10000,
        new JtaProcessEngineConfiguration().getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorDefaultAsyncJobAcquireWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorDefaultAsyncJobAcquireWaitTime()"
  })
  public void testGetAsyncExecutorDefaultAsyncJobAcquireWaitTime2() {
    // Arrange, Act and Assert
    assertEquals(
        10000,
        new JtaProcessEngineConfiguration().getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorDefaultQueueSizeFullWaitTime()"
  })
  public void testGetAsyncExecutorDefaultQueueSizeFullWaitTime() {
    // Arrange, Act and Assert
    assertEquals(
        0, new JtaProcessEngineConfiguration().getAsyncExecutorDefaultQueueSizeFullWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorDefaultQueueSizeFullWaitTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorDefaultQueueSizeFullWaitTime()"
  })
  public void testGetAsyncExecutorDefaultQueueSizeFullWaitTime2() {
    // Arrange, Act and Assert
    assertEquals(
        0, new JtaProcessEngineConfiguration().getAsyncExecutorDefaultQueueSizeFullWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorLockOwner()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorLockOwner()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getAsyncExecutorLockOwner()"})
  public void testGetAsyncExecutorLockOwner() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAsyncExecutorLockOwner());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorLockOwner()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getAsyncExecutorLockOwner()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessEngineConfigurationImpl.getAsyncExecutorLockOwner()"})
  public void testGetAsyncExecutorLockOwner2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAsyncExecutorLockOwner());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorTimerLockTimeInMillis()"})
  public void testGetAsyncExecutorTimerLockTimeInMillis() {
    // Arrange, Act and Assert
    assertEquals(
        300000, new JtaProcessEngineConfiguration().getAsyncExecutorTimerLockTimeInMillis());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorTimerLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessEngineConfigurationImpl.getAsyncExecutorTimerLockTimeInMillis()"})
  public void testGetAsyncExecutorTimerLockTimeInMillis2() {
    // Arrange, Act and Assert
    assertEquals(
        300000, new JtaProcessEngineConfiguration().getAsyncExecutorTimerLockTimeInMillis());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorAsyncJobLockTimeInMillis()"
  })
  public void testGetAsyncExecutorAsyncJobLockTimeInMillis() {
    // Arrange, Act and Assert
    assertEquals(
        300000, new JtaProcessEngineConfiguration().getAsyncExecutorAsyncJobLockTimeInMillis());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorAsyncJobLockTimeInMillis()"
  })
  public void testGetAsyncExecutorAsyncJobLockTimeInMillis2() {
    // Arrange, Act and Assert
    assertEquals(
        300000, new JtaProcessEngineConfiguration().getAsyncExecutorAsyncJobLockTimeInMillis());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsInterval()"
  })
  public void testGetAsyncExecutorResetExpiredJobsInterval() {
    // Arrange, Act and Assert
    assertEquals(
        60000, new JtaProcessEngineConfiguration().getAsyncExecutorResetExpiredJobsInterval());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsInterval()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsInterval()"
  })
  public void testGetAsyncExecutorResetExpiredJobsInterval2() {
    // Arrange, Act and Assert
    assertEquals(
        60000, new JtaProcessEngineConfiguration().getAsyncExecutorResetExpiredJobsInterval());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorExecuteAsyncRunnableFactory()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorExecuteAsyncRunnableFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory ProcessEngineConfigurationImpl.getAsyncExecutorExecuteAsyncRunnableFactory()"
  })
  public void testGetAsyncExecutorExecuteAsyncRunnableFactory_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAsyncExecutorExecuteAsyncRunnableFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorExecuteAsyncRunnableFactory()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorExecuteAsyncRunnableFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory ProcessEngineConfigurationImpl.getAsyncExecutorExecuteAsyncRunnableFactory()"
  })
  public void testGetAsyncExecutorExecuteAsyncRunnableFactory_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getAsyncExecutorExecuteAsyncRunnableFactory());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsPageSize()"
  })
  public void testGetAsyncExecutorResetExpiredJobsPageSize() {
    // Arrange, Act and Assert
    assertEquals(3, new JtaProcessEngineConfiguration().getAsyncExecutorResetExpiredJobsPageSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#getAsyncExecutorResetExpiredJobsPageSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ProcessEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsPageSize()"
  })
  public void testGetAsyncExecutorResetExpiredJobsPageSize2() {
    // Arrange, Act and Assert
    assertEquals(3, new JtaProcessEngineConfiguration().getAsyncExecutorResetExpiredJobsPageSize());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionHelper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionHelper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.ProcessDefinitionHelper ProcessEngineConfigurationImpl.getProcessDefinitionHelper()"
  })
  public void testGetProcessDefinitionHelper() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionHelper());
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#getProcessDefinitionHelper()}.
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#getProcessDefinitionHelper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.ProcessDefinitionHelper ProcessEngineConfigurationImpl.getProcessDefinitionHelper()"
  })
  public void testGetProcessDefinitionHelper2() {
    // Arrange, Act and Assert
    assertNull(new JtaProcessEngineConfiguration().getProcessDefinitionHelper());
  }
}
