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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DefaultActivitiEngineAgendaDiffblueTest {
  /**
   * Test {@link DefaultActivitiEngineAgenda#isEmpty()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultActivitiEngineAgenda#isEmpty()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultActivitiEngineAgenda.isEmpty()"})
  public void testIsEmpty_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new DefaultActivitiEngineAgenda(null).isEmpty());
  }

  /**
   * Test {@link DefaultActivitiEngineAgenda#getNextOperation()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultActivitiEngineAgenda#getNextOperation()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Runnable DefaultActivitiEngineAgenda.getNextOperation()"})
  public void testGetNextOperation_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new DefaultActivitiEngineAgenda(null).getNextOperation());
  }

  /**
   * Test {@link DefaultActivitiEngineAgenda#planContinueProcessOperation(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultActivitiEngineAgenda#planContinueProcessOperation(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultActivitiEngineAgenda.planContinueProcessOperation(ExecutionEntity)"
  })
  public void testPlanContinueProcessOperation_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda =
        new DefaultActivitiEngineAgenda(commandContext);

    // Act
    defaultActivitiEngineAgenda.planContinueProcessOperation(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link
   * DefaultActivitiEngineAgenda#planContinueProcessSynchronousOperation(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultActivitiEngineAgenda#planContinueProcessSynchronousOperation(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultActivitiEngineAgenda.planContinueProcessSynchronousOperation(ExecutionEntity)"
  })
  public void testPlanContinueProcessSynchronousOperation_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda =
        new DefaultActivitiEngineAgenda(commandContext);

    // Act
    defaultActivitiEngineAgenda.planContinueProcessSynchronousOperation(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link DefaultActivitiEngineAgenda#planContinueProcessInCompensation(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultActivitiEngineAgenda#planContinueProcessInCompensation(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultActivitiEngineAgenda.planContinueProcessInCompensation(ExecutionEntity)"
  })
  public void testPlanContinueProcessInCompensation_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda =
        new DefaultActivitiEngineAgenda(commandContext);

    // Act
    defaultActivitiEngineAgenda.planContinueProcessInCompensation(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link DefaultActivitiEngineAgenda#planContinueMultiInstanceOperation(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultActivitiEngineAgenda#planContinueMultiInstanceOperation(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultActivitiEngineAgenda.planContinueMultiInstanceOperation(ExecutionEntity)"
  })
  public void testPlanContinueMultiInstanceOperation_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda =
        new DefaultActivitiEngineAgenda(commandContext);

    // Act
    defaultActivitiEngineAgenda.planContinueMultiInstanceOperation(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link DefaultActivitiEngineAgenda#planTakeOutgoingSequenceFlowsOperation(ExecutionEntity,
   * boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultActivitiEngineAgenda#planTakeOutgoingSequenceFlowsOperation(ExecutionEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultActivitiEngineAgenda.planTakeOutgoingSequenceFlowsOperation(ExecutionEntity, boolean)"
  })
  public void testPlanTakeOutgoingSequenceFlowsOperation_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda =
        new DefaultActivitiEngineAgenda(commandContext);

    // Act
    defaultActivitiEngineAgenda.planTakeOutgoingSequenceFlowsOperation(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link DefaultActivitiEngineAgenda#planEndExecutionOperation(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultActivitiEngineAgenda#planEndExecutionOperation(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultActivitiEngineAgenda.planEndExecutionOperation(ExecutionEntity)"})
  public void testPlanEndExecutionOperation_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda =
        new DefaultActivitiEngineAgenda(commandContext);

    // Act
    defaultActivitiEngineAgenda.planEndExecutionOperation(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link DefaultActivitiEngineAgenda#planTriggerExecutionOperation(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultActivitiEngineAgenda#planTriggerExecutionOperation(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultActivitiEngineAgenda.planTriggerExecutionOperation(ExecutionEntity)"
  })
  public void testPlanTriggerExecutionOperation_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda =
        new DefaultActivitiEngineAgenda(commandContext);

    // Act
    defaultActivitiEngineAgenda.planTriggerExecutionOperation(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link DefaultActivitiEngineAgenda#planDestroyScopeOperation(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultActivitiEngineAgenda#planDestroyScopeOperation(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultActivitiEngineAgenda.planDestroyScopeOperation(ExecutionEntity)"})
  public void testPlanDestroyScopeOperation_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda =
        new DefaultActivitiEngineAgenda(commandContext);

    // Act
    defaultActivitiEngineAgenda.planDestroyScopeOperation(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link DefaultActivitiEngineAgenda#planExecuteInactiveBehaviorsOperation()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultActivitiEngineAgenda#planExecuteInactiveBehaviorsOperation()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultActivitiEngineAgenda.planExecuteInactiveBehaviorsOperation()"})
  public void testPlanExecuteInactiveBehaviorsOperation_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    new DefaultActivitiEngineAgenda(commandContext).planExecuteInactiveBehaviorsOperation();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }
}
