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
package org.activiti.engine.test.impl.logger;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.junit.Test;

public class ProcessExecutionLoggerConfiguratorDiffblueTest {
  /**
   * Test
   * {@link ProcessExecutionLoggerConfigurator#beforeInit(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test:
   * {@link ProcessExecutionLoggerConfigurator#beforeInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testBeforeInit() {
    // Arrange
    ProcessExecutionLoggerConfigurator processExecutionLoggerConfigurator = new ProcessExecutionLoggerConfigurator();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    processExecutionLoggerConfigurator.beforeInit(processEngineConfiguration);

    // Assert
    CommandInterceptor commandInvoker = processEngineConfiguration.getCommandInvoker();
    assertTrue(commandInvoker instanceof LoggingCommandInvoker);
    assertNull(commandInvoker.getNext());
    ProcessExecutionLogger processExecutionLogger = ((LoggingCommandInvoker) commandInvoker).processExecutionLogger;
    assertTrue(processExecutionLogger.createdExecutions.isEmpty());
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
    assertTrue(processExecutionLogger.deletedExecutions.isEmpty());
    assertSame(processExecutionLogger, processExecutionLoggerConfigurator.getProcessExecutionLogger());
  }

  /**
   * Test
   * {@link ProcessExecutionLoggerConfigurator#configure(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcherImpl} (default constructor).</li>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExecutionLoggerConfigurator#configure(ProcessEngineConfigurationImpl)}
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

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link ProcessExecutionLoggerConfigurator}
   *   <li>
   * {@link ProcessExecutionLoggerConfigurator#setProcessExecutionLogger(ProcessExecutionLogger)}
   *   <li>{@link ProcessExecutionLoggerConfigurator#getProcessExecutionLogger()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ProcessExecutionLoggerConfigurator actualProcessExecutionLoggerConfigurator = new ProcessExecutionLoggerConfigurator();
    ProcessExecutionLogger processExecutionLogger = new ProcessExecutionLogger();
    actualProcessExecutionLoggerConfigurator.setProcessExecutionLogger(processExecutionLogger);

    // Assert that nothing has changed
    assertSame(processExecutionLogger, actualProcessExecutionLoggerConfigurator.getProcessExecutionLogger());
  }
}
