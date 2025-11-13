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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.Agenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ContinueProcessOperationDiffblueTest {
  /**
   * Test {@link ContinueProcessOperation#ContinueProcessOperation(CommandContext,
   * ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then Agenda return {@link DefaultActivitiEngineAgenda}.
   * </ul>
   *
   * <p>Method under test: {@link ContinueProcessOperation#ContinueProcessOperation(CommandContext,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ContinueProcessOperation.<init>(CommandContext, ExecutionEntity)"})
  public void testNewContinueProcessOperation_thenAgendaReturnDefaultActivitiEngineAgenda() {
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
    ContinueProcessOperation actualContinueProcessOperation =
        new ContinueProcessOperation(commandContext, execution);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    Agenda agenda = actualContinueProcessOperation.getAgenda();
    assertTrue(agenda instanceof DefaultActivitiEngineAgenda);
    ExecutionEntity execution2 = actualContinueProcessOperation.getExecution();
    assertTrue(execution2 instanceof ExecutionEntityImpl);
    assertFalse(actualContinueProcessOperation.forceSynchronousOperation);
    assertFalse(actualContinueProcessOperation.inCompensation);
    assertSame(defaultActivitiEngineAgenda, agenda);
    assertSame(commandContext, actualContinueProcessOperation.getCommandContext());
    assertSame(execution, execution2);
  }

  /**
   * Test {@link ContinueProcessOperation#ContinueProcessOperation(CommandContext, ExecutionEntity,
   * boolean, boolean)}.
   *
   * <ul>
   *   <li>Then Agenda return {@link DefaultActivitiEngineAgenda}.
   * </ul>
   *
   * <p>Method under test: {@link ContinueProcessOperation#ContinueProcessOperation(CommandContext,
   * ExecutionEntity, boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ContinueProcessOperation.<init>(CommandContext, ExecutionEntity, boolean, boolean)"
  })
  public void testNewContinueProcessOperation_thenAgendaReturnDefaultActivitiEngineAgenda2() {
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
    ContinueProcessOperation actualContinueProcessOperation =
        new ContinueProcessOperation(commandContext, execution, true, true);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    Agenda agenda = actualContinueProcessOperation.getAgenda();
    assertTrue(agenda instanceof DefaultActivitiEngineAgenda);
    ExecutionEntity execution2 = actualContinueProcessOperation.getExecution();
    assertTrue(execution2 instanceof ExecutionEntityImpl);
    assertTrue(actualContinueProcessOperation.forceSynchronousOperation);
    assertTrue(actualContinueProcessOperation.inCompensation);
    assertSame(defaultActivitiEngineAgenda, agenda);
    assertSame(commandContext, actualContinueProcessOperation.getCommandContext());
    assertSame(execution, execution2);
  }

  /**
   * Test {@link ContinueProcessOperation#run()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ContinueProcessOperation#run()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ContinueProcessOperation.run()"})
  public void testRun_thenThrowActivitiException() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new ContinueProcessOperation(
                    commandContext, ExecutionEntityImpl.createWithEmptyRelationshipCollections())
                .run());
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link ContinueProcessOperation#isMultiInstance(FlowNode)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ContinueProcessOperation#isMultiInstance(FlowNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Boolean ContinueProcessOperation.isMultiInstance(FlowNode)"})
  public void testIsMultiInstance_thenReturnFalse() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    ContinueProcessOperation continueProcessOperation =
        new ContinueProcessOperation(
            commandContext, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Boolean actualIsMultiInstanceResult =
        continueProcessOperation.isMultiInstance(new AdhocSubProcess());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertFalse(actualIsMultiInstanceResult);
  }
}
