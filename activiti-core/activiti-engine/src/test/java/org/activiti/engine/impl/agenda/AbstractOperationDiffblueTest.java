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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.HasExecutionListeners;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.Agenda;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractOperationDiffblueTest {
  @Mock
  private CommandContext commandContext;

  @InjectMocks
  private ExecuteInactiveBehaviorsOperation executeInactiveBehaviorsOperation;

  /**
   * Test {@link AbstractOperation#getCurrentFlowElement(ExecutionEntity)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractOperation#getCurrentFlowElement(ExecutionEntity)}
   */
  @Test
  public void testGetCurrentFlowElement_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    ExecuteInactiveBehaviorsOperation executeInactiveBehaviorsOperation = new ExecuteInactiveBehaviorsOperation(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    FlowElement actualCurrentFlowElement = executeInactiveBehaviorsOperation
        .getCurrentFlowElement(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualCurrentFlowElement);
  }

  /**
   * Test
   * {@link AbstractOperation#executeExecutionListeners(HasExecutionListeners, String)}
   * with {@code elementWithExecutionListeners}, {@code eventType}.
   * <p>
   * Method under test:
   * {@link AbstractOperation#executeExecutionListeners(HasExecutionListeners, String)}
   */
  @Test
  public void testExecuteExecutionListenersWithElementWithExecutionListenersEventType() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getListenerNotificationHelper()).thenReturn(new ListenerNotificationHelper());
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);

    // Act
    executeInactiveBehaviorsOperation.executeExecutionListeners(new AdhocSubProcess(), "Event Type");

    // Assert that nothing has changed
    verify(jtaProcessEngineConfiguration).getListenerNotificationHelper();
    verify(commandContext).getProcessEngineConfiguration();
  }

  /**
   * Test
   * {@link AbstractOperation#executeExecutionListeners(HasExecutionListeners, String)}
   * with {@code elementWithExecutionListeners}, {@code eventType}.
   * <p>
   * Method under test:
   * {@link AbstractOperation#executeExecutionListeners(HasExecutionListeners, String)}
   */
  @Test
  public void testExecuteExecutionListenersWithElementWithExecutionListenersEventType2() {
    // Arrange
    ListenerNotificationHelper listenerNotificationHelper = mock(ListenerNotificationHelper.class);
    doNothing().when(listenerNotificationHelper)
        .executeExecutionListeners(Mockito.<HasExecutionListeners>any(), Mockito.<DelegateExecution>any(),
            Mockito.<String>any());
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getListenerNotificationHelper()).thenReturn(listenerNotificationHelper);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);

    // Act
    executeInactiveBehaviorsOperation.executeExecutionListeners(new AdhocSubProcess(), "Event Type");

    // Assert that nothing has changed
    verify(listenerNotificationHelper).executeExecutionListeners(isA(HasExecutionListeners.class), isNull(),
        eq("Event Type"));
    verify(jtaProcessEngineConfiguration).getListenerNotificationHelper();
    verify(commandContext).getProcessEngineConfiguration();
  }

  /**
   * Test
   * {@link AbstractOperation#executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)}
   * with {@code elementWithExecutionListeners}, {@code executionEntity},
   * {@code eventType}.
   * <p>
   * Method under test:
   * {@link AbstractOperation#executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)}
   */
  @Test
  public void testExecuteExecutionListenersWithElementWithExecutionListenersExecutionEntityEventType() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getListenerNotificationHelper()).thenReturn(new ListenerNotificationHelper());
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);
    AdhocSubProcess elementWithExecutionListeners = new AdhocSubProcess();

    // Act
    executeInactiveBehaviorsOperation.executeExecutionListeners(elementWithExecutionListeners,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Event Type");

    // Assert that nothing has changed
    verify(jtaProcessEngineConfiguration).getListenerNotificationHelper();
    verify(commandContext).getProcessEngineConfiguration();
  }

  /**
   * Test
   * {@link AbstractOperation#executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)}
   * with {@code elementWithExecutionListeners}, {@code executionEntity},
   * {@code eventType}.
   * <p>
   * Method under test:
   * {@link AbstractOperation#executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)}
   */
  @Test
  public void testExecuteExecutionListenersWithElementWithExecutionListenersExecutionEntityEventType2() {
    // Arrange
    ListenerNotificationHelper listenerNotificationHelper = mock(ListenerNotificationHelper.class);
    doNothing().when(listenerNotificationHelper)
        .executeExecutionListeners(Mockito.<HasExecutionListeners>any(), Mockito.<DelegateExecution>any(),
            Mockito.<String>any());
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getListenerNotificationHelper()).thenReturn(listenerNotificationHelper);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);
    AdhocSubProcess elementWithExecutionListeners = new AdhocSubProcess();

    // Act
    executeInactiveBehaviorsOperation.executeExecutionListeners(elementWithExecutionListeners,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Event Type");

    // Assert that nothing has changed
    verify(listenerNotificationHelper).executeExecutionListeners(isA(HasExecutionListeners.class),
        isA(DelegateExecution.class), eq("Event Type"));
    verify(jtaProcessEngineConfiguration).getListenerNotificationHelper();
    verify(commandContext).getProcessEngineConfiguration();
  }

  /**
   * Test {@link AbstractOperation#getCommandContext()}.
   * <p>
   * Method under test: {@link AbstractOperation#getCommandContext()}
   */
  @Test
  public void testGetCommandContext() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    ExecuteInactiveBehaviorsOperation executeInactiveBehaviorsOperation = new ExecuteInactiveBehaviorsOperation(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    CommandContext actualCommandContext = executeInactiveBehaviorsOperation.getCommandContext();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(executeInactiveBehaviorsOperation.commandContext, actualCommandContext);
  }

  /**
   * Test {@link AbstractOperation#getAgenda()}.
   * <p>
   * Method under test: {@link AbstractOperation#getAgenda()}
   */
  @Test
  public void testGetAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    ExecuteInactiveBehaviorsOperation executeInactiveBehaviorsOperation = new ExecuteInactiveBehaviorsOperation(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    Agenda actualAgenda = executeInactiveBehaviorsOperation.getAgenda();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(executeInactiveBehaviorsOperation.agenda, actualAgenda);
  }

  /**
   * Test {@link AbstractOperation#getExecution()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractOperation#getExecution()}
   */
  @Test
  public void testGetExecution_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    ExecutionEntity actualExecution = (new ExecuteInactiveBehaviorsOperation(
        new CommandContext(mock(Command.class), processEngineConfiguration))).getExecution();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualExecution);
  }

  /**
   * Test {@link AbstractOperation#setExecution(ExecutionEntity)}.
   * <p>
   * Method under test: {@link AbstractOperation#setExecution(ExecutionEntity)}
   */
  @Test
  public void testSetExecution() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    ExecuteInactiveBehaviorsOperation executeInactiveBehaviorsOperation = new ExecuteInactiveBehaviorsOperation(
        new CommandContext(mock(Command.class), processEngineConfiguration));
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executeInactiveBehaviorsOperation.setExecution(execution);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(execution, executeInactiveBehaviorsOperation.getExecution());
  }
}
