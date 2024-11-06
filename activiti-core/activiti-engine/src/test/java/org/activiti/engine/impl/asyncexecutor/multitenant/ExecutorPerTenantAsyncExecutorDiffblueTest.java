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
package org.activiti.engine.impl.asyncexecutor.multitenant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import javax.script.ScriptEngineManager;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.ProcessDefinitionHelper;
import org.activiti.engine.impl.asyncexecutor.AcquireAsyncJobsDueRunnable;
import org.activiti.engine.impl.asyncexecutor.AcquireTimerJobsRunnable;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.ManagedAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.ResetExpiredJobsRunnable;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisModelDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionDataManager;
import org.activiti.engine.impl.scripting.ScriptingEngines;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.ProcessInstanceHelper;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.junit.Test;
import org.mockito.Mockito;

public class ExecutorPerTenantAsyncExecutorDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link DummyTenantInfoHolder} (default constructor).</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ExecutorPerTenantAsyncExecutor#ExecutorPerTenantAsyncExecutor(TenantInfoHolder)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#setAutoActivate(boolean)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isActive()}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isAutoActivate()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenDummyTenantInfoHolder() {
    // Arrange and Act
    ExecutorPerTenantAsyncExecutor actualExecutorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    actualExecutorPerTenantAsyncExecutor.setAutoActivate(true);
    boolean actualIsActiveResult = actualExecutorPerTenantAsyncExecutor.isActive();
    boolean actualIsAutoActivateResult = actualExecutorPerTenantAsyncExecutor.isAutoActivate();

    // Assert that nothing has changed
    assertFalse(actualIsActiveResult);
    assertTrue(actualExecutorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(actualIsAutoActivateResult);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link TenantAwareAsyncExecutorFactory}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ExecutorPerTenantAsyncExecutor#ExecutorPerTenantAsyncExecutor(TenantInfoHolder, TenantAwareAsyncExecutorFactory)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#setAutoActivate(boolean)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isActive()}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isAutoActivate()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenTenantAwareAsyncExecutorFactory() {
    // Arrange and Act
    ExecutorPerTenantAsyncExecutor actualExecutorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), mock(TenantAwareAsyncExecutorFactory.class));
    actualExecutorPerTenantAsyncExecutor.setAutoActivate(true);
    boolean actualIsActiveResult = actualExecutorPerTenantAsyncExecutor.isActive();
    boolean actualIsAutoActivateResult = actualExecutorPerTenantAsyncExecutor.isAutoActivate();

    // Assert that nothing has changed
    assertFalse(actualIsActiveResult);
    assertTrue(actualExecutorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(actualIsAutoActivateResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor() {
    // Arrange, Act and Assert
    assertNull((new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())).determineAsyncExecutor());
    assertNull(
        (new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder(), mock(TenantAwareAsyncExecutorFactory.class)))
            .determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor3() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor4() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ManagedAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor7() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setSecondsToWaitOnShutdown(1L);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandInvoker(new CommandContextInterceptor());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSessionFactories(new HashMap<>());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setExecutionQueryLimit(1);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor13() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor14() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreDeployers(new ArrayList<>());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor15() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessInstanceHelper(new ProcessInstanceHelper());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor16() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMaxLengthStringVariableType(3);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor17() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setUsingRelationalDatabase(true);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor18() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricIdentityLinkDataManager(
        new MybatisHistoricIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor19() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setProcessDefinitionDataManager(new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor20() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorMaxPoolSize(3);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor21() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorResetExpiredJobsPageSize(3);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor22() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionHelper(mock(ProcessDefinitionHelper.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor23() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcMaxCheckoutTime(1);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor24() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBatchSizeProcessInstances(3);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor25() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionCache(new DefaultDeploymentCache<>());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor26() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setClock(new DefaultClockImpl());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor27() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setObjectMapper(new ObjectMapper());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor28() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor29() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDynamicBpmnService(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor30() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setScriptingEngines(new ScriptingEngines(new ScriptEngineManager()));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor31() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSerializableVariableTypeTrackDeserializedObjects(true);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor32() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomSessionFactories(new ArrayList<>());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor33() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPreBpmnParseHandlers(new ArrayList<>());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor34() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setModelDataManager(new MybatisModelDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor35() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcPingEnabled(true);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor)
   * MessageQueueMode is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenDefaultAsyncJobExecutorMessageQueueModeIsTrue() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setMessageQueueMode(true);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor)
   * ThreadPoolQueue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenDefaultAsyncJobExecutorThreadPoolQueueIsNull() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setThreadPoolQueue(null);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenant42() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant
   * {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenant42_thenReturnNull() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant
   * {@code Tenant Id}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenantTenantId() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant
   * {@code Tenant Id}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenantTenantId2() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("Tenantid");
    tenantInfoHolder.addTenant("Tenant Id");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) CurrentTenantId
   * is {@code Tenantid}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderCurrentTenantIdIsTenantid() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("Tenantid");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) CurrentTenantId
   * is {@code Tenantid}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderCurrentTenantIdIsTenantid2() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("Tenantid");
    tenantInfoHolder.addTenant("42");
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Beans
   * is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenJtaProcessEngineConfigurationBeansIsHashMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(new HashMap<>());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   * DbHistoryUsed is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  public void testDetermineAsyncExecutor_givenJtaProcessEngineConfigurationDbHistoryUsedIsTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDbHistoryUsed(true);
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    AsyncExecutor actualDetermineAsyncExecutorResult = executorPerTenantAsyncExecutor.determineAsyncExecutor();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertNull(actualDetermineAsyncExecutorResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  public void testExecuteAsyncJob_thenReturnFalse() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(false);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    boolean actualExecuteAsyncJobResult = executorPerTenantAsyncExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).executeAsyncJob(isA(Job.class));
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertNull(executorPerTenantAsyncExecutor.getLockOwner());
    assertFalse(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  public void testExecuteAsyncJob_thenReturnTrue() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(true);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    boolean actualExecuteAsyncJobResult = executorPerTenantAsyncExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).executeAsyncJob(isA(Job.class));
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertNull(executorPerTenantAsyncExecutor.getLockOwner());
    assertTrue(actualExecuteAsyncJobResult);
  }
}
