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

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class DefaultActivitiEngineAgendaDiffblueTest {
  /**
   * Method under test: {@link DefaultActivitiEngineAgenda#isEmpty()}
   */
  @Test
  public void testIsEmpty() {
    // Arrange, Act and Assert
    assertTrue((new DefaultActivitiEngineAgenda(null)).isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultActivitiEngineAgenda#planContinueProcessOperation(ExecutionEntity)}
   */
  @Test
  public void testPlanContinueProcessOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    defaultActivitiEngineAgenda
        .planContinueProcessOperation(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test:
   * {@link DefaultActivitiEngineAgenda#planContinueProcessSynchronousOperation(ExecutionEntity)}
   */
  @Test
  public void testPlanContinueProcessSynchronousOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    defaultActivitiEngineAgenda
        .planContinueProcessSynchronousOperation(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test:
   * {@link DefaultActivitiEngineAgenda#planContinueProcessInCompensation(ExecutionEntity)}
   */
  @Test
  public void testPlanContinueProcessInCompensation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    defaultActivitiEngineAgenda
        .planContinueProcessInCompensation(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test:
   * {@link DefaultActivitiEngineAgenda#planContinueMultiInstanceOperation(ExecutionEntity)}
   */
  @Test
  public void testPlanContinueMultiInstanceOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    defaultActivitiEngineAgenda
        .planContinueMultiInstanceOperation(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test:
   * {@link DefaultActivitiEngineAgenda#planTakeOutgoingSequenceFlowsOperation(ExecutionEntity, boolean)}
   */
  @Test
  public void testPlanTakeOutgoingSequenceFlowsOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    defaultActivitiEngineAgenda
        .planTakeOutgoingSequenceFlowsOperation(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test:
   * {@link DefaultActivitiEngineAgenda#planEndExecutionOperation(ExecutionEntity)}
   */
  @Test
  public void testPlanEndExecutionOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    defaultActivitiEngineAgenda.planEndExecutionOperation(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test:
   * {@link DefaultActivitiEngineAgenda#planTriggerExecutionOperation(ExecutionEntity)}
   */
  @Test
  public void testPlanTriggerExecutionOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    defaultActivitiEngineAgenda
        .planTriggerExecutionOperation(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test:
   * {@link DefaultActivitiEngineAgenda#planDestroyScopeOperation(ExecutionEntity)}
   */
  @Test
  public void testPlanDestroyScopeOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    defaultActivitiEngineAgenda.planDestroyScopeOperation(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Method under test:
   * {@link DefaultActivitiEngineAgenda#planExecuteInactiveBehaviorsOperation()}
   */
  @Test
  public void testPlanExecuteInactiveBehaviorsOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    (new DefaultActivitiEngineAgenda(new CommandContext(mock(Command.class), processEngineConfiguration)))
        .planExecuteInactiveBehaviorsOperation();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }
}
