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
package org.activiti.engine.impl.asyncexecutor;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.mockito.Mockito;

public class ResetExpiredJobsCmdDiffblueTest {
  /**
   * Test {@link ResetExpiredJobsCmd#ResetExpiredJobsCmd(Collection)}.
   * <p>
   * Method under test:
   * {@link ResetExpiredJobsCmd#ResetExpiredJobsCmd(Collection)}
   */
  @Test
  public void testNewResetExpiredJobsCmd() {
    // Arrange, Act and Assert
    Collection<String> collection = (new ResetExpiredJobsCmd(new ArrayList<>())).jobIds;
    assertTrue(collection instanceof List);
    assertTrue(collection.isEmpty());
  }

  /**
   * Test {@link ResetExpiredJobsCmd#execute(CommandContext)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResetExpiredJobsCmd#execute(CommandContext)}
   */
  @Test
  public void testExecute_thenReturnNull() {
    // Arrange
    ResetExpiredJobsCmd resetExpiredJobsCmd = new ResetExpiredJobsCmd(new ArrayList<>());
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    Void actualExecuteResult = resetExpiredJobsCmd
        .execute(new CommandContext(mock(Command.class), processEngineConfiguration));

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualExecuteResult);
  }
}
