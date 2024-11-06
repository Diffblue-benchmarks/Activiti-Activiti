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
package org.activiti.engine.impl.interceptor;

import static org.junit.Assert.assertSame;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;

public class CommandContextInterceptorDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CommandContextInterceptor#CommandContextInterceptor()}
   *   <li>
   * {@link CommandContextInterceptor#setCommandContextFactory(CommandContextFactory)}
   *   <li>
   * {@link CommandContextInterceptor#setProcessEngineContext(ProcessEngineConfigurationImpl)}
   *   <li>{@link CommandContextInterceptor#getCommandContextFactory()}
   *   <li>{@link CommandContextInterceptor#getProcessEngineConfiguration()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    CommandContextInterceptor actualCommandContextInterceptor = new CommandContextInterceptor();
    CommandContextFactory commandContextFactory = new CommandContextFactory();
    actualCommandContextInterceptor.setCommandContextFactory(commandContextFactory);
    JtaProcessEngineConfiguration processEngineContext = new JtaProcessEngineConfiguration();
    actualCommandContextInterceptor.setProcessEngineContext(processEngineContext);
    CommandContextFactory actualCommandContextFactory = actualCommandContextInterceptor.getCommandContextFactory();

    // Assert that nothing has changed
    assertSame(processEngineContext, actualCommandContextInterceptor.getProcessEngineConfiguration());
    assertSame(commandContextFactory, actualCommandContextFactory);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link CommandContextFactory} (default constructor).</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CommandContextInterceptor#CommandContextInterceptor(CommandContextFactory, ProcessEngineConfigurationImpl)}
   *   <li>
   * {@link CommandContextInterceptor#setCommandContextFactory(CommandContextFactory)}
   *   <li>
   * {@link CommandContextInterceptor#setProcessEngineContext(ProcessEngineConfigurationImpl)}
   *   <li>{@link CommandContextInterceptor#getCommandContextFactory()}
   *   <li>{@link CommandContextInterceptor#getProcessEngineConfiguration()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenCommandContextFactory() {
    // Arrange
    CommandContextFactory commandContextFactory = new CommandContextFactory();

    // Act
    CommandContextInterceptor actualCommandContextInterceptor = new CommandContextInterceptor(commandContextFactory,
        new JtaProcessEngineConfiguration());
    CommandContextFactory commandContextFactory2 = new CommandContextFactory();
    actualCommandContextInterceptor.setCommandContextFactory(commandContextFactory2);
    JtaProcessEngineConfiguration processEngineContext = new JtaProcessEngineConfiguration();
    actualCommandContextInterceptor.setProcessEngineContext(processEngineContext);
    CommandContextFactory actualCommandContextFactory = actualCommandContextInterceptor.getCommandContextFactory();

    // Assert that nothing has changed
    assertSame(processEngineContext, actualCommandContextInterceptor.getProcessEngineConfiguration());
    assertSame(commandContextFactory2, actualCommandContextFactory);
  }
}
