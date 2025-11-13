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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.history.ProcessInstanceHistoryLog;
import org.activiti.engine.history.ProcessInstanceHistoryLogQuery;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ProcessInstanceHistoryLogQueryImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       ProcessInstanceHistoryLogQueryImpl#ProcessInstanceHistoryLogQueryImpl(CommandExecutor,
   *       String)}
   *   <li>{@link ProcessInstanceHistoryLogQueryImpl#includeVariableUpdates()}
   *   <li>{@link ProcessInstanceHistoryLogQueryImpl#includeActivities()}
   *   <li>{@link ProcessInstanceHistoryLogQueryImpl#includeComments()}
   *   <li>{@link ProcessInstanceHistoryLogQueryImpl#includeFormProperties()}
   *   <li>{@link ProcessInstanceHistoryLogQueryImpl#includeTasks()}
   *   <li>{@link ProcessInstanceHistoryLogQueryImpl#includeVariables()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessInstanceHistoryLogQueryImpl.<init>(CommandExecutor, String)",
    "ProcessInstanceHistoryLogQuery ProcessInstanceHistoryLogQueryImpl.includeActivities()",
    "ProcessInstanceHistoryLogQuery ProcessInstanceHistoryLogQueryImpl.includeComments()",
    "ProcessInstanceHistoryLogQuery ProcessInstanceHistoryLogQueryImpl.includeFormProperties()",
    "ProcessInstanceHistoryLogQuery ProcessInstanceHistoryLogQueryImpl.includeTasks()",
    "ProcessInstanceHistoryLogQuery ProcessInstanceHistoryLogQueryImpl.includeVariableUpdates()",
    "ProcessInstanceHistoryLogQuery ProcessInstanceHistoryLogQueryImpl.includeVariables()"
  })
  public void testGettersAndSetters() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    // Act
    ProcessInstanceHistoryLogQueryImpl actualProcessInstanceHistoryLogQueryImpl =
        new ProcessInstanceHistoryLogQueryImpl(commandExecutor, "42");
    ProcessInstanceHistoryLogQuery actualIncludeVariableUpdatesResult =
        actualProcessInstanceHistoryLogQueryImpl.includeVariableUpdates();
    ProcessInstanceHistoryLogQuery actualIncludeActivitiesResult =
        actualProcessInstanceHistoryLogQueryImpl.includeActivities();
    ProcessInstanceHistoryLogQuery actualIncludeCommentsResult =
        actualProcessInstanceHistoryLogQueryImpl.includeComments();
    ProcessInstanceHistoryLogQuery actualIncludeFormPropertiesResult =
        actualProcessInstanceHistoryLogQueryImpl.includeFormProperties();
    ProcessInstanceHistoryLogQuery actualIncludeTasksResult =
        actualProcessInstanceHistoryLogQueryImpl.includeTasks();
    ProcessInstanceHistoryLogQuery actualIncludeVariablesResult =
        actualProcessInstanceHistoryLogQueryImpl.includeVariables();

    // Assert
    assertSame(actualProcessInstanceHistoryLogQueryImpl, actualIncludeActivitiesResult);
    assertSame(actualProcessInstanceHistoryLogQueryImpl, actualIncludeCommentsResult);
    assertSame(actualProcessInstanceHistoryLogQueryImpl, actualIncludeFormPropertiesResult);
    assertSame(actualProcessInstanceHistoryLogQueryImpl, actualIncludeTasksResult);
    assertSame(actualProcessInstanceHistoryLogQueryImpl, actualIncludeVariableUpdatesResult);
    assertSame(actualProcessInstanceHistoryLogQueryImpl, actualIncludeVariablesResult);
  }

  /**
   * Test {@link ProcessInstanceHistoryLogQueryImpl#singleResult()}.
   *
   * <p>Method under test: {@link ProcessInstanceHistoryLogQueryImpl#singleResult()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceHistoryLog ProcessInstanceHistoryLogQueryImpl.singleResult()"})
  public void testSingleResult() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    ProcessInstanceHistoryLogImpl processInstanceHistoryLogImpl =
        new ProcessInstanceHistoryLogImpl(new HistoricProcessInstanceEntityImpl());
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstanceHistoryLog>>any()))
        .thenReturn(processInstanceHistoryLogImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    // Act
    ProcessInstanceHistoryLog actualSingleResultResult =
        new ProcessInstanceHistoryLogQueryImpl(commandExecutor, "42").singleResult();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(processInstanceHistoryLogImpl, actualSingleResultResult);
  }
}
