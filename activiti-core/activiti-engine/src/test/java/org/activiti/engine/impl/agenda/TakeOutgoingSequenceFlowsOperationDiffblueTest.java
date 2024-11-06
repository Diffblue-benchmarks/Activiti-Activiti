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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class TakeOutgoingSequenceFlowsOperationDiffblueTest {
  /**
   * Test
   * {@link TakeOutgoingSequenceFlowsOperation#TakeOutgoingSequenceFlowsOperation(CommandContext, ExecutionEntity, boolean)}.
   * <ul>
   *   <li>Then return
   * {@link TakeOutgoingSequenceFlowsOperation#evaluateConditions}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TakeOutgoingSequenceFlowsOperation#TakeOutgoingSequenceFlowsOperation(CommandContext, ExecutionEntity, boolean)}
   */
  @Test
  public void testNewTakeOutgoingSequenceFlowsOperation_thenReturnEvaluateConditions() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(null);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any())).thenReturn(defaultActivitiEngineAgenda);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    TakeOutgoingSequenceFlowsOperation actualTakeOutgoingSequenceFlowsOperation = new TakeOutgoingSequenceFlowsOperation(
        commandContext, executionEntity, true);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertTrue(actualTakeOutgoingSequenceFlowsOperation.evaluateConditions);
    assertSame(defaultActivitiEngineAgenda, actualTakeOutgoingSequenceFlowsOperation.getAgenda());
    assertSame(commandContext, actualTakeOutgoingSequenceFlowsOperation.getCommandContext());
    assertSame(executionEntity, actualTakeOutgoingSequenceFlowsOperation.getExecution());
  }

  /**
   * Test {@link TakeOutgoingSequenceFlowsOperation#run()}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TakeOutgoingSequenceFlowsOperation#run()}
   */
  @Test
  public void testRun_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    (new TakeOutgoingSequenceFlowsOperation(commandContext,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true)).run();

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test
   * {@link TakeOutgoingSequenceFlowsOperation#cleanupExecutions(FlowElement)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TakeOutgoingSequenceFlowsOperation#cleanupExecutions(FlowElement)}
   */
  @Test
  public void testCleanupExecutions_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    TakeOutgoingSequenceFlowsOperation takeOutgoingSequenceFlowsOperation = new TakeOutgoingSequenceFlowsOperation(
        commandContext, ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Act
    takeOutgoingSequenceFlowsOperation.cleanupExecutions(new AdhocSubProcess());

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test
   * {@link TakeOutgoingSequenceFlowsOperation#findNextParentScopeExecutionWithAllEndedChildExecutions(ExecutionEntity, ExecutionEntity)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TakeOutgoingSequenceFlowsOperation#findNextParentScopeExecutionWithAllEndedChildExecutions(ExecutionEntity, ExecutionEntity)}
   */
  @Test
  public void testFindNextParentScopeExecutionWithAllEndedChildExecutions_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    TakeOutgoingSequenceFlowsOperation takeOutgoingSequenceFlowsOperation = new TakeOutgoingSequenceFlowsOperation(
        commandContext, ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualFindNextParentScopeExecutionWithAllEndedChildExecutionsResult = takeOutgoingSequenceFlowsOperation
        .findNextParentScopeExecutionWithAllEndedChildExecutions(executionEntity,
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualFindNextParentScopeExecutionWithAllEndedChildExecutionsResult);
  }

  /**
   * Test
   * {@link TakeOutgoingSequenceFlowsOperation#allChildExecutionsEnded(ExecutionEntity, ExecutionEntity)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TakeOutgoingSequenceFlowsOperation#allChildExecutionsEnded(ExecutionEntity, ExecutionEntity)}
   */
  @Test
  public void testAllChildExecutionsEnded_thenReturnTrue() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    TakeOutgoingSequenceFlowsOperation takeOutgoingSequenceFlowsOperation = new TakeOutgoingSequenceFlowsOperation(
        commandContext, ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);
    ExecutionEntityImpl parentExecutionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    boolean actualAllChildExecutionsEndedResult = takeOutgoingSequenceFlowsOperation
        .allChildExecutionsEnded(parentExecutionEntity, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertTrue(actualAllChildExecutionsEndedResult);
  }
}
