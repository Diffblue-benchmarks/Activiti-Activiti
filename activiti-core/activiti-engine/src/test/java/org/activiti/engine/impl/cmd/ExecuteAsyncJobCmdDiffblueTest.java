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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ExecuteAsyncJobCmdDiffblueTest {
  /**
   * Test {@link ExecuteAsyncJobCmd#ExecuteAsyncJobCmd(String)}.
   * <p>
   * Method under test: {@link ExecuteAsyncJobCmd#ExecuteAsyncJobCmd(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecuteAsyncJobCmd.<init>(String)"})
  public void testNewExecuteAsyncJobCmd() {
    // Arrange, Act and Assert
    assertEquals("42", (new ExecuteAsyncJobCmd("42")).jobId);
  }

  /**
   * Test {@link ExecuteAsyncJobCmd#execute(CommandContext)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteAsyncJobCmd#execute(CommandContext)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object ExecuteAsyncJobCmd.execute(CommandContext)"})
  public void testExecute_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ExecuteAsyncJobCmd executeAsyncJobCmd = new ExecuteAsyncJobCmd(null);

    ActivitiEventDispatcherImpl eventDispatcher = new ActivitiEventDispatcherImpl();
    eventDispatcher.setEnabled(false);
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executeAsyncJobCmd.execute(new CommandContext(mock(Command.class), processEngineConfiguration)));
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }
}
