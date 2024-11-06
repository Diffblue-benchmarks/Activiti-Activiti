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
package org.activiti.engine.cfg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.test.impl.logger.LoggingCommandInvoker;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.junit.Test;

public class AbstractProcessEngineConfiguratorDiffblueTest {
  /**
   * Test {@link AbstractProcessEngineConfigurator#getPriority()}.
   * <p>
   * Method under test: {@link AbstractProcessEngineConfigurator#getPriority()}
   */
  @Test
  public void testGetPriority() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ProcessExecutionLoggerConfigurator processExecutionLoggerConfigurator = new ProcessExecutionLoggerConfigurator();
    processExecutionLoggerConfigurator.beforeInit(processEngineConfiguration);

    // Act and Assert
    assertEquals(10000, processExecutionLoggerConfigurator.getPriority());
  }

  /**
   * Test {@link AbstractProcessEngineConfigurator#getPriority()}.
   * <ul>
   *   <li>Given {@link ProcessExecutionLoggerConfigurator} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractProcessEngineConfigurator#getPriority()}
   */
  @Test
  public void testGetPriority_givenProcessExecutionLoggerConfigurator() {
    // Arrange, Act and Assert
    assertEquals(10000, (new ProcessExecutionLoggerConfigurator()).getPriority());
  }

  /**
   * Test
   * {@link AbstractProcessEngineConfigurator#beforeInit(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractProcessEngineConfigurator#beforeInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testBeforeInit_givenCustomFunctionProvider() {
    // Arrange
    ProcessExecutionLoggerConfigurator processExecutionLoggerConfigurator = new ProcessExecutionLoggerConfigurator();

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    processExecutionLoggerConfigurator.beforeInit(processEngineConfiguration);

    // Assert
    CommandInterceptor commandInvoker = processEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof LoggingCommandInvoker);
    assertNull(commandInvoker.getNext());
  }

  /**
   * Test
   * {@link AbstractProcessEngineConfigurator#beforeInit(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractProcessEngineConfigurator#beforeInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testBeforeInit_whenJtaProcessEngineConfiguration() {
    // Arrange
    ProcessExecutionLoggerConfigurator processExecutionLoggerConfigurator = new ProcessExecutionLoggerConfigurator();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    processExecutionLoggerConfigurator.beforeInit(processEngineConfiguration);

    // Assert
    CommandInterceptor commandInvoker = processEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof LoggingCommandInvoker);
    assertNull(commandInvoker.getNext());
  }

  /**
   * Test
   * {@link AbstractProcessEngineConfigurator#configure(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcherImpl} (default constructor).</li>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractProcessEngineConfigurator#configure(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testConfigure_givenActivitiEventDispatcherImpl_thenCallsGetEventDispatcher() {
    // Arrange
    ProcessExecutionLoggerConfigurator processExecutionLoggerConfigurator = new ProcessExecutionLoggerConfigurator();
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    // Act
    processExecutionLoggerConfigurator.configure(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
  }
}
