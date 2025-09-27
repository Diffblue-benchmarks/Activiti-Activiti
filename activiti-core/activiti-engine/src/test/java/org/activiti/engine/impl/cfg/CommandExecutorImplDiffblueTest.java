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

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class CommandExecutorImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CommandExecutorImpl#CommandExecutorImpl(CommandConfig, CommandInterceptor)}
   *   <li>{@link CommandExecutorImpl#setFirst(CommandInterceptor)}
   *   <li>{@link CommandExecutorImpl#getDefaultConfig()}
   *   <li>{@link CommandExecutorImpl#getFirst()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CommandExecutorImpl.<init>(CommandConfig, CommandInterceptor)",
    "CommandConfig CommandExecutorImpl.getDefaultConfig()",
    "CommandInterceptor CommandExecutorImpl.getFirst()",
    "void CommandExecutorImpl.setFirst(CommandInterceptor)"
  })
  public void testGettersAndSetters() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    // Act
    CommandExecutorImpl actualCommandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    CommandContextInterceptor commandInterceptor = new CommandContextInterceptor();
    actualCommandExecutorImpl.setFirst(commandInterceptor);
    CommandConfig actualDefaultConfig = actualCommandExecutorImpl.getDefaultConfig();

    // Assert
    assertSame(defaultConfig, actualDefaultConfig);
    assertSame(commandInterceptor, actualCommandExecutorImpl.getFirst());
  }

  /**
   * Test {@link CommandExecutorImpl#execute(Command)} with {@code command}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@link JSONObject#NULL}.
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandExecutorImpl#execute(Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object CommandExecutorImpl.execute(Command)"})
  public void testExecuteWithCommand_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    // Act
    commandExecutorImpl.execute(mock(Command.class));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link CommandExecutorImpl#execute(CommandConfig, Command)} with {@code config}, {@code
   * command}.
   *
   * <ul>
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandExecutorImpl#execute(CommandConfig, Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object CommandExecutorImpl.execute(CommandConfig, Command)"})
  public void testExecuteWithConfigCommand_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    // Act
    commandExecutorImpl.execute(new CommandConfig(), mock(Command.class));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }
}
