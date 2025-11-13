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
package org.activiti.engine.impl.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiEngineAgenda;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.asyncexecutor.JobManager;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.event.logger.DatabaseEventFlusher;
import org.activiti.engine.impl.history.HistoryManager;
import org.activiti.engine.impl.jobexecutor.FailedJobCommandFactory;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityManager;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityManager;
import org.activiti.engine.impl.persistence.entity.CommentEntityManager;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManager;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManager;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityManager;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricDetailEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.JobEntityManager;
import org.activiti.engine.impl.persistence.entity.ModelEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManager;
import org.activiti.engine.impl.persistence.entity.PropertyEntityManager;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManager;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManager;
import org.activiti.engine.impl.persistence.entity.TableDataManager;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManager;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManager;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class CommandContextDiffblueTest {
  /**
   * Test {@link CommandContext#CommandContext(Command, ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then Agenda return {@link DefaultActivitiEngineAgenda}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#CommandContext(Command,
   * ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.<init>(Command, ProcessEngineConfigurationImpl)"})
  public void testNewCommandContext_thenAgendaReturnDefaultActivitiEngineAgenda() {
    // Arrange
    Command<Object> command = mock(Command.class);

    ActivitiEngineAgendaFactory activitiEngineAgendaFactory =
        mock(ActivitiEngineAgendaFactory.class);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(null);
    when(activitiEngineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(defaultActivitiEngineAgenda);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getEngineAgendaFactory())
        .thenReturn(activitiEngineAgendaFactory);
    when(processEngineConfiguration.getFailedJobCommandFactory())
        .thenReturn(mock(FailedJobCommandFactory.class));

    // Act
    CommandContext actualCommandContext = new CommandContext(command, processEngineConfiguration);

    // Assert
    verify(activitiEngineAgendaFactory).createAgenda(isA(CommandContext.class));
    verify(processEngineConfiguration).getEngineAgendaFactory();
    verify(processEngineConfiguration).getFailedJobCommandFactory();
    verify(processEngineConfiguration).getSessionFactories();
    ActivitiEngineAgenda agenda = actualCommandContext.getAgenda();
    assertTrue(agenda instanceof DefaultActivitiEngineAgenda);
    assertNull(actualCommandContext.getResult());
    assertNull(actualCommandContext.getException());
    assertNull(actualCommandContext.getCloseListeners());
    assertNull(actualCommandContext.attributes);
    assertNull(actualCommandContext.getEventDispatcher());
    assertNull(actualCommandContext.getJobManager());
    assertNull(actualCommandContext.getHistoryManager());
    assertNull(actualCommandContext.getAttachmentEntityManager());
    assertNull(actualCommandContext.getByteArrayEntityManager());
    assertNull(actualCommandContext.getCommentEntityManager());
    assertNull(actualCommandContext.getDeadLetterJobEntityManager());
    assertNull(actualCommandContext.getDeploymentEntityManager());
    assertNull(actualCommandContext.getEventLogEntryEntityManager());
    assertNull(actualCommandContext.getEventSubscriptionEntityManager());
    assertNull(actualCommandContext.getExecutionEntityManager());
    assertNull(actualCommandContext.getHistoricActivityInstanceEntityManager());
    assertNull(actualCommandContext.getHistoricDetailEntityManager());
    assertNull(actualCommandContext.getHistoricIdentityLinkEntityManager());
    assertNull(actualCommandContext.getHistoricProcessInstanceEntityManager());
    assertNull(actualCommandContext.getHistoricTaskInstanceEntityManager());
    assertNull(actualCommandContext.getHistoricVariableInstanceEntityManager());
    assertNull(actualCommandContext.getIdentityLinkEntityManager());
    assertNull(actualCommandContext.getJobEntityManager());
    assertNull(actualCommandContext.getModelEntityManager());
    assertNull(actualCommandContext.getProcessDefinitionEntityManager());
    assertNull(actualCommandContext.getProcessDefinitionInfoEntityManager());
    assertNull(actualCommandContext.getPropertyEntityManager());
    assertNull(actualCommandContext.getResourceEntityManager());
    assertNull(actualCommandContext.getSuspendedJobEntityManager());
    assertNull(actualCommandContext.getTableDataManager());
    assertNull(actualCommandContext.getTaskEntityManager());
    assertNull(actualCommandContext.getTimerJobEntityManager());
    assertNull(actualCommandContext.getVariableInstanceEntityManager());
    assertFalse(actualCommandContext.hasInvolvedExecutions());
    assertFalse(actualCommandContext.isReused());
    assertTrue(actualCommandContext.resultStack.isEmpty());
    assertTrue(actualCommandContext.getInvolvedExecutions().isEmpty());
    assertTrue(actualCommandContext.getSessionFactories().isEmpty());
    assertTrue(actualCommandContext.getSessions().isEmpty());
    assertTrue(actualCommandContext.involvedExecutions.isEmpty());
    assertSame(defaultActivitiEngineAgenda, agenda);
    assertSame(processEngineConfiguration, actualCommandContext.getProcessEngineConfiguration());
    assertSame(command, actualCommandContext.getCommand());
  }

  /**
   * Test {@link CommandContext#close()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#close()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.close()"})
  public void testClose_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.close();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#rethrowExceptionIfNeeded()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#rethrowExceptionIfNeeded()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.rethrowExceptionIfNeeded()"})
  public void testRethrowExceptionIfNeeded_thenThrowActivitiException() throws Error {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commandContext.rethrowExceptionIfNeeded());
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#addCloseListener(CommandContextCloseListener)}.
   *
   * <p>Method under test: {@link CommandContext#addCloseListener(CommandContextCloseListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.addCloseListener(CommandContextCloseListener)"})
  public void testAddCloseListener() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    DatabaseEventFlusher commandContextCloseListener = new DatabaseEventFlusher();

    // Act
    commandContext.addCloseListener(commandContextCloseListener);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    List<CommandContextCloseListener> closeListeners = commandContext.getCloseListeners();
    assertEquals(1, closeListeners.size());
    assertSame(commandContextCloseListener, closeListeners.get(0));
  }

  /**
   * Test {@link CommandContext#hasCloseListener(Class)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#hasCloseListener(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean CommandContext.hasCloseListener(Class)"})
  public void testHasCloseListener_thenReturnFalse() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    Class<Object> type = Object.class;

    // Act
    boolean actualHasCloseListenerResult = commandContext.hasCloseListener(type);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertFalse(actualHasCloseListenerResult);
  }

  /**
   * Test {@link CommandContext#executeCloseListenersClosing()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#executeCloseListenersClosing()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.executeCloseListenersClosing()"})
  public void testExecuteCloseListenersClosing_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.executeCloseListenersClosing();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersClosing()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#executeCloseListenersClosing()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.executeCloseListenersClosing()"})
  public void testExecuteCloseListenersClosing_thenCallsCreateAgenda2() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    commandContext.addCloseListener(new DatabaseEventFlusher());
    commandContext.exception(new Throwable());

    // Act
    commandContext.executeCloseListenersClosing();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersAfterSessionFlushed()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#executeCloseListenersAfterSessionFlushed()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.executeCloseListenersAfterSessionFlushed()"})
  public void testExecuteCloseListenersAfterSessionFlushed_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.executeCloseListenersAfterSessionFlushed();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersAfterSessionFlushed()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#executeCloseListenersAfterSessionFlushed()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.executeCloseListenersAfterSessionFlushed()"})
  public void testExecuteCloseListenersAfterSessionFlushed_thenCallsCreateAgenda2() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    commandContext.addCloseListener(new DatabaseEventFlusher());
    commandContext.exception(new Throwable());

    // Act
    commandContext.executeCloseListenersAfterSessionFlushed();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersClosed()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#executeCloseListenersClosed()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.executeCloseListenersClosed()"})
  public void testExecuteCloseListenersClosed_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.executeCloseListenersClosed();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersClosed()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#executeCloseListenersClosed()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.executeCloseListenersClosed()"})
  public void testExecuteCloseListenersClosed_thenCallsCreateAgenda2() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    commandContext.addCloseListener(new DatabaseEventFlusher());
    commandContext.exception(new Throwable());

    // Act
    commandContext.executeCloseListenersClosed();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersCloseFailure()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#executeCloseListenersCloseFailure()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.executeCloseListenersCloseFailure()"})
  public void testExecuteCloseListenersCloseFailure_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.executeCloseListenersCloseFailure();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersCloseFailure()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#executeCloseListenersCloseFailure()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.executeCloseListenersCloseFailure()"})
  public void testExecuteCloseListenersCloseFailure_thenCallsCreateAgenda2() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    commandContext.addCloseListener(new DatabaseEventFlusher());
    commandContext.exception(new Throwable());

    // Act
    commandContext.executeCloseListenersCloseFailure();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#flushSessions()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#flushSessions()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.flushSessions()"})
  public void testFlushSessions_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.flushSessions();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#closeSessions()}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#closeSessions()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.closeSessions()"})
  public void testCloseSessions_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.closeSessions();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#exception(Throwable)}.
   *
   * <p>Method under test: {@link CommandContext#exception(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.exception(Throwable)"})
  public void testException() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    Throwable exception = new Throwable();

    // Act
    commandContext.exception(exception);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(exception, commandContext.getException());
  }

  /**
   * Test {@link CommandContext#exception(Throwable)}.
   *
   * <p>Method under test: {@link CommandContext#exception(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.exception(Throwable)"})
  public void testException2() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    Throwable exception = new Throwable();
    commandContext.exception(exception);

    // Act
    commandContext.exception(new Throwable());

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(exception, commandContext.getException());
  }

  /**
   * Test {@link CommandContext#getDeploymentEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getDeploymentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentEntityManager CommandContext.getDeploymentEntityManager()"})
  public void testGetDeploymentEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    DeploymentEntityManager actualDeploymentEntityManager =
        commandContext.getDeploymentEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualDeploymentEntityManager);
  }

  /**
   * Test {@link CommandContext#getResourceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getResourceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ResourceEntityManager CommandContext.getResourceEntityManager()"})
  public void testGetResourceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    ResourceEntityManager actualResourceEntityManager = commandContext.getResourceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualResourceEntityManager);
  }

  /**
   * Test {@link CommandContext#getByteArrayEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getByteArrayEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ByteArrayEntityManager CommandContext.getByteArrayEntityManager()"})
  public void testGetByteArrayEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    ByteArrayEntityManager actualByteArrayEntityManager =
        commandContext.getByteArrayEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualByteArrayEntityManager);
  }

  /**
   * Test {@link CommandContext#getProcessDefinitionEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getProcessDefinitionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntityManager CommandContext.getProcessDefinitionEntityManager()"
  })
  public void testGetProcessDefinitionEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    ProcessDefinitionEntityManager actualProcessDefinitionEntityManager =
        commandContext.getProcessDefinitionEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualProcessDefinitionEntityManager);
  }

  /**
   * Test {@link CommandContext#getModelEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getModelEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ModelEntityManager CommandContext.getModelEntityManager()"})
  public void testGetModelEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    ModelEntityManager actualModelEntityManager = commandContext.getModelEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualModelEntityManager);
  }

  /**
   * Test {@link CommandContext#getProcessDefinitionInfoEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionInfoEntityManager CommandContext.getProcessDefinitionInfoEntityManager()"
  })
  public void testGetProcessDefinitionInfoEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    ProcessDefinitionInfoEntityManager actualProcessDefinitionInfoEntityManager =
        commandContext.getProcessDefinitionInfoEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualProcessDefinitionInfoEntityManager);
  }

  /**
   * Test {@link CommandContext#getExecutionEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getExecutionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntityManager CommandContext.getExecutionEntityManager()"})
  public void testGetExecutionEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    ExecutionEntityManager actualExecutionEntityManager =
        commandContext.getExecutionEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualExecutionEntityManager);
  }

  /**
   * Test {@link CommandContext#getTaskEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getTaskEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskEntityManager CommandContext.getTaskEntityManager()"})
  public void testGetTaskEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    TaskEntityManager actualTaskEntityManager = commandContext.getTaskEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualTaskEntityManager);
  }

  /**
   * Test {@link CommandContext#getIdentityLinkEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getIdentityLinkEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IdentityLinkEntityManager CommandContext.getIdentityLinkEntityManager()"})
  public void testGetIdentityLinkEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    IdentityLinkEntityManager actualIdentityLinkEntityManager =
        commandContext.getIdentityLinkEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualIdentityLinkEntityManager);
  }

  /**
   * Test {@link CommandContext#getVariableInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getVariableInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "VariableInstanceEntityManager CommandContext.getVariableInstanceEntityManager()"
  })
  public void testGetVariableInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    VariableInstanceEntityManager actualVariableInstanceEntityManager =
        commandContext.getVariableInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualVariableInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricProcessInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceEntityManager CommandContext.getHistoricProcessInstanceEntityManager()"
  })
  public void testGetHistoricProcessInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    HistoricProcessInstanceEntityManager actualHistoricProcessInstanceEntityManager =
        commandContext.getHistoricProcessInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricProcessInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricDetailEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getHistoricDetailEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricDetailEntityManager CommandContext.getHistoricDetailEntityManager()"})
  public void testGetHistoricDetailEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    HistoricDetailEntityManager actualHistoricDetailEntityManager =
        commandContext.getHistoricDetailEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricDetailEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricVariableInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceEntityManager CommandContext.getHistoricVariableInstanceEntityManager()"
  })
  public void testGetHistoricVariableInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    HistoricVariableInstanceEntityManager actualHistoricVariableInstanceEntityManager =
        commandContext.getHistoricVariableInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricVariableInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricActivityInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceEntityManager CommandContext.getHistoricActivityInstanceEntityManager()"
  })
  public void testGetHistoricActivityInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    HistoricActivityInstanceEntityManager actualHistoricActivityInstanceEntityManager =
        commandContext.getHistoricActivityInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricActivityInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricTaskInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricTaskInstanceEntityManager CommandContext.getHistoricTaskInstanceEntityManager()"
  })
  public void testGetHistoricTaskInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    HistoricTaskInstanceEntityManager actualHistoricTaskInstanceEntityManager =
        commandContext.getHistoricTaskInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricTaskInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricIdentityLinkEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricIdentityLinkEntityManager CommandContext.getHistoricIdentityLinkEntityManager()"
  })
  public void testGetHistoricIdentityLinkEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    HistoricIdentityLinkEntityManager actualHistoricIdentityLinkEntityManager =
        commandContext.getHistoricIdentityLinkEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricIdentityLinkEntityManager);
  }

  /**
   * Test {@link CommandContext#getEventLogEntryEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getEventLogEntryEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EventLogEntryEntityManager CommandContext.getEventLogEntryEntityManager()"})
  public void testGetEventLogEntryEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    EventLogEntryEntityManager actualEventLogEntryEntityManager =
        commandContext.getEventLogEntryEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualEventLogEntryEntityManager);
  }

  /**
   * Test {@link CommandContext#getJobEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JobEntityManager CommandContext.getJobEntityManager()"})
  public void testGetJobEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    JobEntityManager actualJobEntityManager = commandContext.getJobEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualJobEntityManager);
  }

  /**
   * Test {@link CommandContext#getTimerJobEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getTimerJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobEntityManager CommandContext.getTimerJobEntityManager()"})
  public void testGetTimerJobEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    TimerJobEntityManager actualTimerJobEntityManager = commandContext.getTimerJobEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualTimerJobEntityManager);
  }

  /**
   * Test {@link CommandContext#getSuspendedJobEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getSuspendedJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SuspendedJobEntityManager CommandContext.getSuspendedJobEntityManager()"})
  public void testGetSuspendedJobEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    SuspendedJobEntityManager actualSuspendedJobEntityManager =
        commandContext.getSuspendedJobEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualSuspendedJobEntityManager);
  }

  /**
   * Test {@link CommandContext#getDeadLetterJobEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getDeadLetterJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobEntityManager CommandContext.getDeadLetterJobEntityManager()"})
  public void testGetDeadLetterJobEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    DeadLetterJobEntityManager actualDeadLetterJobEntityManager =
        commandContext.getDeadLetterJobEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualDeadLetterJobEntityManager);
  }

  /**
   * Test {@link CommandContext#getAttachmentEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getAttachmentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AttachmentEntityManager CommandContext.getAttachmentEntityManager()"})
  public void testGetAttachmentEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    AttachmentEntityManager actualAttachmentEntityManager =
        commandContext.getAttachmentEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualAttachmentEntityManager);
  }

  /**
   * Test {@link CommandContext#getTableDataManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getTableDataManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TableDataManager CommandContext.getTableDataManager()"})
  public void testGetTableDataManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    TableDataManager actualTableDataManager = commandContext.getTableDataManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualTableDataManager);
  }

  /**
   * Test {@link CommandContext#getCommentEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getCommentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommentEntityManager CommandContext.getCommentEntityManager()"})
  public void testGetCommentEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    CommentEntityManager actualCommentEntityManager = commandContext.getCommentEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualCommentEntityManager);
  }

  /**
   * Test {@link CommandContext#getPropertyEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getPropertyEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"PropertyEntityManager CommandContext.getPropertyEntityManager()"})
  public void testGetPropertyEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    PropertyEntityManager actualPropertyEntityManager = commandContext.getPropertyEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualPropertyEntityManager);
  }

  /**
   * Test {@link CommandContext#getEventSubscriptionEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getEventSubscriptionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "EventSubscriptionEntityManager CommandContext.getEventSubscriptionEntityManager()"
  })
  public void testGetEventSubscriptionEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    EventSubscriptionEntityManager actualEventSubscriptionEntityManager =
        commandContext.getEventSubscriptionEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualEventSubscriptionEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoryManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getHistoryManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoryManager CommandContext.getHistoryManager()"})
  public void testGetHistoryManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    HistoryManager actualHistoryManager = commandContext.getHistoryManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoryManager);
  }

  /**
   * Test {@link CommandContext#getJobManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getJobManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JobManager CommandContext.getJobManager()"})
  public void testGetJobManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    JobManager actualJobManager = commandContext.getJobManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualJobManager);
  }

  /**
   * Test {@link CommandContext#addInvolvedExecution(ExecutionEntity)}.
   *
   * <p>Method under test: {@link CommandContext#addInvolvedExecution(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.addInvolvedExecution(ExecutionEntity)"})
  public void testAddInvolvedExecution() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.addInvolvedExecution(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    ProcessEngineConfigurationImpl processEngineConfiguration2 =
        commandContext.getProcessEngineConfiguration();
    assertTrue(processEngineConfiguration2 instanceof JtaProcessEngineConfiguration);
    assertFalse(commandContext.hasInvolvedExecutions());
    assertTrue(commandContext.getInvolvedExecutions().isEmpty());
    assertTrue(processEngineConfiguration2.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(commandContext.involvedExecutions.isEmpty());
  }

  /**
   * Test {@link CommandContext#addInvolvedExecution(ExecutionEntity)}.
   *
   * <p>Method under test: {@link CommandContext#addInvolvedExecution(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.addInvolvedExecution(ExecutionEntity)"})
  public void testAddInvolvedExecution2() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setId("Execution Entity");

    // Act
    commandContext.addInvolvedExecution(executionEntity);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    ProcessEngineConfigurationImpl processEngineConfiguration2 =
        commandContext.getProcessEngineConfiguration();
    assertTrue(processEngineConfiguration2 instanceof JtaProcessEngineConfiguration);
    assertEquals(1, commandContext.getInvolvedExecutions().size());
    Map<String, ExecutionEntity> stringExecutionEntityMap = commandContext.involvedExecutions;
    assertEquals(1, stringExecutionEntityMap.size());
    assertTrue(processEngineConfiguration2.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(commandContext.hasInvolvedExecutions());
    assertSame(executionEntity, stringExecutionEntityMap.get("Execution Entity"));
  }

  /**
   * Test {@link CommandContext#hasInvolvedExecutions()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#hasInvolvedExecutions()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean CommandContext.hasInvolvedExecutions()"})
  public void testHasInvolvedExecutions_thenReturnFalse() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    boolean actualHasInvolvedExecutionsResult = commandContext.hasInvolvedExecutions();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertFalse(actualHasInvolvedExecutionsResult);
  }

  /**
   * Test {@link CommandContext#getInvolvedExecutions()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getInvolvedExecutions()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection CommandContext.getInvolvedExecutions()"})
  public void testGetInvolvedExecutions_thenReturnEmpty() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    Collection<ExecutionEntity> actualInvolvedExecutions = commandContext.getInvolvedExecutions();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertTrue(actualInvolvedExecutions.isEmpty());
  }

  /**
   * Test {@link CommandContext#getEventDispatcher()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getEventDispatcher()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivitiEventDispatcher CommandContext.getEventDispatcher()"})
  public void testGetEventDispatcher_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    ActivitiEventDispatcher actualEventDispatcher = commandContext.getEventDispatcher();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualEventDispatcher);
  }

  /**
   * Test {@link CommandContext#getResult()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#getResult()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object CommandContext.getResult()"})
  public void testGetResult_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    Object actualResult = commandContext.getResult();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualResult);
  }

  /**
   * Test {@link CommandContext#setResult(Object)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContext#setResult(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandContext.setResult(Object)"})
  public void testSetResult_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.setResult(JSONObject.NULL);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }
}
