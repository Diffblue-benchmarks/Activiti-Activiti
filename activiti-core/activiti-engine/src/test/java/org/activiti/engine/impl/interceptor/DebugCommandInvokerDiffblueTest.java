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

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.agenda.ExecuteInactiveBehaviorsOperation;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.junit.Test;
import org.mockito.Mockito;

public class DebugCommandInvokerDiffblueTest {
  /**
   * Test {@link DebugCommandInvoker#executeOperation(Runnable)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DebugCommandInvoker#executeOperation(Runnable)}
   */
  @Test
  public void testExecuteOperation_thenCallsCreateAgenda() {
    // Arrange
    DebugCommandInvoker debugCommandInvoker = new DebugCommandInvoker();
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    ExecuteInactiveBehaviorsOperation runnable = new ExecuteInactiveBehaviorsOperation(
        new CommandContext(mock(Command.class), processEngineConfiguration));
    runnable.setExecution(null);

    // Act
    debugCommandInvoker.executeOperation(runnable);

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link DebugCommandInvoker#executeOperation(Runnable)}.
   * <ul>
   *   <li>When {@link Runnable} {@link Runnable#run()} does nothing.</li>
   *   <li>Then calls {@link Runnable#run()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DebugCommandInvoker#executeOperation(Runnable)}
   */
  @Test
  public void testExecuteOperation_whenRunnableRunDoesNothing_thenCallsRun() {
    // Arrange
    DebugCommandInvoker debugCommandInvoker = new DebugCommandInvoker();
    Runnable runnable = mock(Runnable.class);
    doNothing().when(runnable).run();

    // Act
    debugCommandInvoker.executeOperation(runnable);

    // Assert that nothing has changed
    verify(runnable).run();
  }

  /**
   * Test new {@link DebugCommandInvoker} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link DebugCommandInvoker}
   */
  @Test
  public void testNewDebugCommandInvoker() {
    // Arrange, Act and Assert
    assertNull((new DebugCommandInvoker()).getNext());
  }
}
