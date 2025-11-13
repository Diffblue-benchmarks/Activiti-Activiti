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
package org.activiti.engine.impl.agenda;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.Agenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ContinueMultiInstanceOperationDiffblueTest {
  /**
   * Test {@link ContinueMultiInstanceOperation#ContinueMultiInstanceOperation(CommandContext,
   * ExecutionEntity)}.
   *
   * <p>Method under test: {@link
   * ContinueMultiInstanceOperation#ContinueMultiInstanceOperation(CommandContext, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ContinueMultiInstanceOperation.<init>(CommandContext, ExecutionEntity)"})
  public void testNewContinueMultiInstanceOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(null);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(defaultActivitiEngineAgenda);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ContinueMultiInstanceOperation actualContinueMultiInstanceOperation =
        new ContinueMultiInstanceOperation(commandContext, execution);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    Agenda agenda = actualContinueMultiInstanceOperation.getAgenda();
    assertTrue(agenda instanceof DefaultActivitiEngineAgenda);
    ExecutionEntity execution2 = actualContinueMultiInstanceOperation.getExecution();
    assertTrue(execution2 instanceof ExecutionEntityImpl);
    assertSame(defaultActivitiEngineAgenda, agenda);
    assertSame(commandContext, actualContinueMultiInstanceOperation.getCommandContext());
    assertSame(execution, execution2);
  }
}
