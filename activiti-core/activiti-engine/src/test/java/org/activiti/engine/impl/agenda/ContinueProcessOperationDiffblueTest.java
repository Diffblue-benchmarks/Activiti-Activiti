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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class ContinueProcessOperationDiffblueTest {
  /**
   * Method under test: {@link ContinueProcessOperation#run()}
   */
  @Test
  public void testRun() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new ContinueProcessOperation(commandContext,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections())).run());
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test: {@link ContinueProcessOperation#isMultiInstance(FlowNode)}
   */
  @Test
  public void testIsMultiInstance() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    ContinueProcessOperation continueProcessOperation = new ContinueProcessOperation(commandContext,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Boolean actualIsMultiInstanceResult = continueProcessOperation.isMultiInstance(new AdhocSubProcess());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertFalse(actualIsMultiInstanceResult);
  }

  /**
   * Method under test:
   * {@link ContinueProcessOperation#executeMultiInstanceSynchronous(FlowNode)}
   */
  @Test
  public void testExecuteMultiInstanceSynchronous() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    ContinueProcessOperation continueProcessOperation = new ContinueProcessOperation(commandContext,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> continueProcessOperation.executeMultiInstanceSynchronous(new AdhocSubProcess()));
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test:
   * {@link ContinueProcessOperation#executeActivityBehavior(ActivityBehavior, FlowNode)}
   */
  @Test
  public void testExecuteActivityBehavior() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    ContinueProcessOperation continueProcessOperation = new ContinueProcessOperation(commandContext,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ActivityBehavior activityBehavior = mock(ActivityBehavior.class);
    doNothing().when(activityBehavior).execute(Mockito.<DelegateExecution>any());

    // Act
    continueProcessOperation.executeActivityBehavior(activityBehavior, new AdhocSubProcess());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    verify(activityBehavior).execute(isA(DelegateExecution.class));
  }

  /**
   * Method under test:
   * {@link ContinueProcessOperation#executeActivityBehavior(ActivityBehavior, FlowNode)}
   */
  @Test
  public void testExecuteActivityBehavior2() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    ContinueProcessOperation continueProcessOperation = new ContinueProcessOperation(commandContext,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ActivityBehavior activityBehavior = mock(ActivityBehavior.class);
    doThrow(new ActivitiException("An error occurred")).when(activityBehavior)
        .execute(Mockito.<DelegateExecution>any());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> continueProcessOperation.executeActivityBehavior(activityBehavior, new AdhocSubProcess()));
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    verify(activityBehavior).execute(isA(DelegateExecution.class));
  }

  /**
   * Method under test:
   * {@link ContinueProcessOperation#ContinueProcessOperation(CommandContext, ExecutionEntity)}
   */
  @Test
  public void testNewContinueProcessOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(null);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any())).thenReturn(defaultActivitiEngineAgenda);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ContinueProcessOperation actualContinueProcessOperation = new ContinueProcessOperation(commandContext, execution);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertFalse(actualContinueProcessOperation.forceSynchronousOperation);
    assertFalse(actualContinueProcessOperation.inCompensation);
    assertSame(defaultActivitiEngineAgenda, actualContinueProcessOperation.getAgenda());
    assertSame(commandContext, actualContinueProcessOperation.getCommandContext());
    assertSame(execution, actualContinueProcessOperation.getExecution());
  }

  /**
   * Method under test:
   * {@link ContinueProcessOperation#ContinueProcessOperation(CommandContext, ExecutionEntity, boolean, boolean)}
   */
  @Test
  public void testNewContinueProcessOperation2() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(null);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any())).thenReturn(defaultActivitiEngineAgenda);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ContinueProcessOperation actualContinueProcessOperation = new ContinueProcessOperation(commandContext, execution,
        true, true);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertTrue(actualContinueProcessOperation.forceSynchronousOperation);
    assertTrue(actualContinueProcessOperation.inCompensation);
    assertSame(defaultActivitiEngineAgenda, actualContinueProcessOperation.getAgenda());
    assertSame(commandContext, actualContinueProcessOperation.getCommandContext());
    assertSame(execution, actualContinueProcessOperation.getExecution());
  }
}
