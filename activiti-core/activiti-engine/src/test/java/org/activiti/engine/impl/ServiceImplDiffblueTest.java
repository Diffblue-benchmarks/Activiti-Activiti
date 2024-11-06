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
package org.activiti.engine.impl;

import static org.junit.Assert.assertSame;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.junit.Test;

public class ServiceImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ServiceImpl#ServiceImpl()}
   *   <li>{@link ServiceImpl#setCommandExecutor(CommandExecutor)}
   *   <li>{@link ServiceImpl#getCommandExecutor()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ServiceImpl actualServiceImpl = new ServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    actualServiceImpl.setCommandExecutor(commandExecutor);

    // Assert that nothing has changed
    assertSame(commandExecutor, actualServiceImpl.getCommandExecutor());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ServiceImpl#ServiceImpl(ProcessEngineConfigurationImpl)}
   *   <li>{@link ServiceImpl#setCommandExecutor(CommandExecutor)}
   *   <li>{@link ServiceImpl#getCommandExecutor()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenJtaProcessEngineConfiguration() {
    // Arrange and Act
    ServiceImpl actualServiceImpl = new ServiceImpl(new JtaProcessEngineConfiguration());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    actualServiceImpl.setCommandExecutor(commandExecutor);

    // Assert that nothing has changed
    assertSame(commandExecutor, actualServiceImpl.getCommandExecutor());
  }
}
