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
package org.activiti.engine.impl.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.junit.Test;
import org.mockito.Mockito;

public class DbIdGeneratorDiffblueTest {
  /**
   * Method under test: {@link DbIdGenerator#getNextId()}
   */
  @Test
  public void testGetNextId() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<IdBlock>>any())).thenReturn(new IdBlock(1L, 1L));
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    DbIdGenerator dbIdGenerator = new DbIdGenerator();
    dbIdGenerator.setCommandExecutor(commandExecutor);

    // Act
    String actualNextId = dbIdGenerator.getNextId();

    // Assert
    verify(first).execute(isNull(), isA(Command.class));
    assertEquals("1", actualNextId);
  }

  /**
   * Method under test: {@link DbIdGenerator#getNewBlock()}
   */
  @Test
  public void testGetNewBlock() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<IdBlock>>any())).thenReturn(new IdBlock(1L, 1L));
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    DbIdGenerator dbIdGenerator = new DbIdGenerator();
    dbIdGenerator.setCommandExecutor(commandExecutor);

    // Act
    dbIdGenerator.getNewBlock();

    // Assert
    verify(first).execute(isNull(), isA(Command.class));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DbIdGenerator}
   *   <li>{@link DbIdGenerator#setCommandConfig(CommandConfig)}
   *   <li>{@link DbIdGenerator#setCommandExecutor(CommandExecutor)}
   *   <li>{@link DbIdGenerator#setIdBlockSize(int)}
   *   <li>{@link DbIdGenerator#getCommandConfig()}
   *   <li>{@link DbIdGenerator#getCommandExecutor()}
   *   <li>{@link DbIdGenerator#getIdBlockSize()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DbIdGenerator actualDbIdGenerator = new DbIdGenerator();
    CommandConfig commandConfig = new CommandConfig();
    actualDbIdGenerator.setCommandConfig(commandConfig);
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    actualDbIdGenerator.setCommandExecutor(commandExecutor);
    actualDbIdGenerator.setIdBlockSize(1);
    CommandConfig actualCommandConfig = actualDbIdGenerator.getCommandConfig();
    CommandExecutor actualCommandExecutor = actualDbIdGenerator.getCommandExecutor();

    // Assert that nothing has changed
    assertEquals(1, actualDbIdGenerator.getIdBlockSize());
    assertSame(commandExecutor, actualCommandExecutor);
    assertSame(commandConfig, actualCommandConfig);
  }
}
