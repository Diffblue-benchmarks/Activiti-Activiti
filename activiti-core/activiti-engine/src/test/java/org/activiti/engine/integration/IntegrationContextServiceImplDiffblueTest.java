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
package org.activiti.engine.integration;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntity;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationContextServiceImplDiffblueTest {
  @Mock private CommandExecutor commandExecutor;

  @InjectMocks private IntegrationContextServiceImpl integrationContextServiceImpl;

  /**
   * Test {@link IntegrationContextServiceImpl#findById(String)}.
   *
   * <p>Method under test: {@link IntegrationContextServiceImpl#findById(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IntegrationContextEntity IntegrationContextServiceImpl.findById(String)"})
  public void testFindById() {
    // Arrange
    IntegrationContextEntityImpl integrationContextEntityImpl = new IntegrationContextEntityImpl();
    when(commandExecutor.execute(Mockito.<Command<IntegrationContextEntity>>any()))
        .thenReturn(integrationContextEntityImpl);

    // Act
    IntegrationContextEntity actualFindByIdResult = integrationContextServiceImpl.findById("42");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(integrationContextEntityImpl, actualFindByIdResult);
  }

  /**
   * Test {@link IntegrationContextServiceImpl#deleteIntegrationContext(IntegrationContextEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * IntegrationContextServiceImpl#deleteIntegrationContext(IntegrationContextEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IntegrationContextServiceImpl.deleteIntegrationContext(IntegrationContextEntity)"
  })
  public void testDeleteIntegrationContext_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<IntegrationContextEntity>>any()))
        .thenReturn(new IntegrationContextEntityImpl());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);
    IntegrationContextServiceImpl integrationContextServiceImpl =
        new IntegrationContextServiceImpl(commandExecutor);

    // Act
    integrationContextServiceImpl.deleteIntegrationContext(new IntegrationContextEntityImpl());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }
}
