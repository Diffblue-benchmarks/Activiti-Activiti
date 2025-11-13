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
package org.activiti.spring.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.integration.IntegrationPatternType;

class ActivitiInboundGatewayDiffblueTest {
  /**
   * Test {@link ActivitiInboundGateway#ActivitiInboundGateway(ProcessEngine, String[])}.
   *
   * <ul>
   *   <li>Then return ComponentType is {@code gateway}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiInboundGateway#ActivitiInboundGateway(ProcessEngine,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test new ActivitiInboundGateway(ProcessEngine, String[]); then return ComponentType is 'gateway'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiInboundGateway.<init>(ProcessEngine, String[])"})
  void testNewActivitiInboundGateway_thenReturnComponentTypeIsGateway() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    ProcessEngineImpl processEngine = new ProcessEngineImpl(processEngineConfiguration);

    // Act
    ActivitiInboundGateway actualActivitiInboundGateway =
        new ActivitiInboundGateway(processEngine, "Pvs Or Headers To Preserve");

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
    assertEquals("gateway", actualActivitiInboundGateway.getComponentType());
    assertNull(actualActivitiInboundGateway.getApplicationContextId());
    assertNull(actualActivitiInboundGateway.getBeanName());
    assertNull(actualActivitiInboundGateway.getComponentName());
    assertNull(actualActivitiInboundGateway.getRole());
    assertNull(actualActivitiInboundGateway.getManagedName());
    assertNull(actualActivitiInboundGateway.getManagedType());
    assertNull(actualActivitiInboundGateway.getConversionService());
    assertNull(actualActivitiInboundGateway.getExpression());
    assertNull(actualActivitiInboundGateway.getErrorChannel());
    assertNull(actualActivitiInboundGateway.getReplyChannel());
    assertNull(actualActivitiInboundGateway.getRequestChannel());
    assertEquals(0, actualActivitiInboundGateway.getPhase());
    assertEquals(
        IntegrationPatternType.inbound_gateway,
        actualActivitiInboundGateway.getIntegrationPatternType());
    assertFalse(actualActivitiInboundGateway.isActive());
    assertFalse(actualActivitiInboundGateway.isRunning());
    assertFalse(actualActivitiInboundGateway.isObserved());
    assertTrue(actualActivitiInboundGateway.isAutoStartup());
    assertTrue(actualActivitiInboundGateway.isLoggingEnabled());
  }

  /**
   * Test {@link ActivitiInboundGateway#initializeDefaultPreservedHeaders()}.
   *
   * <ul>
   *   <li>Then calls {@link StandaloneInMemProcessEngineConfiguration#getAsyncExecutor()}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiInboundGateway#initializeDefaultPreservedHeaders()}
   */
  @Test
  @DisplayName("Test initializeDefaultPreservedHeaders(); then calls getAsyncExecutor()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiInboundGateway.initializeDefaultPreservedHeaders()"})
  void testInitializeDefaultPreservedHeaders_thenCallsGetAsyncExecutor() {
    // Arrange
    ProcessEngineLifecycleListener processEngineLifecycleListener =
        mock(ProcessEngineLifecycleListener.class);
    doNothing()
        .when(processEngineLifecycleListener)
        .onProcessEngineBuilt(Mockito.<ProcessEngine>any());

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    when(processEngineConfiguration.isUsingRelationalDatabase()).thenReturn(false);
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getDynamicBpmnService())
        .thenReturn(new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoryService())
        .thenReturn(new HistoryServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getManagementService()).thenReturn(new ManagementServiceImpl());
    when(processEngineConfiguration.getProcessEngineLifecycleListener())
        .thenReturn(processEngineLifecycleListener);
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getTaskService())
        .thenReturn(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    // Act
    Activiti.inboundGateway(new ProcessEngineImpl(processEngineConfiguration), "Vars To Preserve")
        .initializeDefaultPreservedHeaders();

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration, atLeast(1)).getProcessEngineLifecycleListener();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineLifecycleListener).onProcessEngineBuilt(isA(ProcessEngine.class));
    verify(processEngineConfiguration).getCommandExecutor();
    verify(processEngineConfiguration).getDynamicBpmnService();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isUsingRelationalDatabase();
  }
}
