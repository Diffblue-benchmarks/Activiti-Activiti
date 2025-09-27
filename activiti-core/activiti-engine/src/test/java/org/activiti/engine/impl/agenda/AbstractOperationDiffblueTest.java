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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.FlowElement;
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

public class AbstractOperationDiffblueTest {
  /**
   * Test {@link AbstractOperation#getCurrentFlowElement(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractOperation#getCurrentFlowElement(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement AbstractOperation.getCurrentFlowElement(ExecutionEntity)"})
  public void testGetCurrentFlowElement_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    ExecuteInactiveBehaviorsOperation executeInactiveBehaviorsOperation =
        new ExecuteInactiveBehaviorsOperation(commandContext);

    // Act
    FlowElement actualCurrentFlowElement =
        executeInactiveBehaviorsOperation.getCurrentFlowElement(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualCurrentFlowElement);
  }

  /**
   * Test {@link AbstractOperation#getCommandContext()}.
   *
   * <p>Method under test: {@link AbstractOperation#getCommandContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandContext AbstractOperation.getCommandContext()"})
  public void testGetCommandContext() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    ExecuteInactiveBehaviorsOperation executeInactiveBehaviorsOperation =
        new ExecuteInactiveBehaviorsOperation(commandContext);

    // Act
    CommandContext actualCommandContext = executeInactiveBehaviorsOperation.getCommandContext();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(executeInactiveBehaviorsOperation.commandContext, actualCommandContext);
  }

  /**
   * Test {@link AbstractOperation#getAgenda()}.
   *
   * <p>Method under test: {@link AbstractOperation#getAgenda()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Agenda AbstractOperation.getAgenda()"})
  public void testGetAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    ExecuteInactiveBehaviorsOperation executeInactiveBehaviorsOperation =
        new ExecuteInactiveBehaviorsOperation(commandContext);

    // Act
    Agenda actualAgenda = executeInactiveBehaviorsOperation.getAgenda();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(executeInactiveBehaviorsOperation.agenda, actualAgenda);
  }

  /**
   * Test {@link AbstractOperation#getExecution()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractOperation#getExecution()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntity AbstractOperation.getExecution()"})
  public void testGetExecution_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    ExecutionEntity actualExecution =
        new ExecuteInactiveBehaviorsOperation(commandContext).getExecution();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualExecution);
  }

  /**
   * Test {@link AbstractOperation#setExecution(ExecutionEntity)}.
   *
   * <p>Method under test: {@link AbstractOperation#setExecution(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractOperation.setExecution(ExecutionEntity)"})
  public void testSetExecution() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    ExecuteInactiveBehaviorsOperation executeInactiveBehaviorsOperation =
        new ExecuteInactiveBehaviorsOperation(commandContext);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executeInactiveBehaviorsOperation.setExecution(execution);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(execution, executeInactiveBehaviorsOperation.getExecution());
  }
}
