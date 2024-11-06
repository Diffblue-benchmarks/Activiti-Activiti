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
import java.util.Collection;
import java.util.List;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.asyncexecutor.JobManager;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.event.logger.DatabaseEventFlusher;
import org.activiti.engine.impl.history.HistoryManager;
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
import org.mockito.Mockito;

public class CommandContextDiffblueTest {
  /**
   * Test
   * {@link CommandContext#CommandContext(Command, ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Then return Result is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommandContext#CommandContext(Command, ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewCommandContext_thenReturnResultIsNull() {
    // Arrange
    Command<Object> command = mock(Command.class);
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(null);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any())).thenReturn(defaultActivitiEngineAgenda);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    CommandContext actualCommandContext = new CommandContext(command, processEngineConfiguration);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualCommandContext.getResult());
    assertNull(actualCommandContext.getException());
    assertNull(actualCommandContext.getCloseListeners());
    assertNull(actualCommandContext.getSessionFactories());
    assertNull(actualCommandContext.attributes);
    assertNull(actualCommandContext.getEventDispatcher());
    assertNull(actualCommandContext.getJobManager());
    assertNull(actualCommandContext.getHistoryManager());
    assertNull(actualCommandContext.getFailedJobCommandFactory());
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
    assertTrue(actualCommandContext.getSessions().isEmpty());
    assertTrue(actualCommandContext.involvedExecutions.isEmpty());
    assertSame(defaultActivitiEngineAgenda, actualCommandContext.getAgenda());
    assertSame(processEngineConfiguration, actualCommandContext.getProcessEngineConfiguration());
    assertSame(command, actualCommandContext.getCommand());
  }

  /**
   * Test {@link CommandContext#close()}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#close()}
   */
  @Test
  public void testClose_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    (new CommandContext(mock(Command.class), processEngineConfiguration)).close();

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#rethrowExceptionIfNeeded()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#rethrowExceptionIfNeeded()}
   */
  @Test
  public void testRethrowExceptionIfNeeded_thenThrowActivitiException() throws Error {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new CommandContext(mock(Command.class), processEngineConfiguration)).rethrowExceptionIfNeeded());
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#addCloseListener(CommandContextCloseListener)}.
   * <p>
   * Method under test:
   * {@link CommandContext#addCloseListener(CommandContextCloseListener)}
   */
  @Test
  public void testAddCloseListener() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);
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
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#hasCloseListener(Class)}
   */
  @Test
  public void testHasCloseListener_thenReturnFalse() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);
    Class<Object> type = Object.class;

    // Act
    boolean actualHasCloseListenerResult = commandContext.hasCloseListener(type);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertFalse(actualHasCloseListenerResult);
  }

  /**
   * Test {@link CommandContext#executeCloseListenersClosing()}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#executeCloseListenersClosing()}
   */
  @Test
  public void testExecuteCloseListenersClosing_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    (new CommandContext(mock(Command.class), processEngineConfiguration)).executeCloseListenersClosing();

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersAfterSessionFlushed()}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommandContext#executeCloseListenersAfterSessionFlushed()}
   */
  @Test
  public void testExecuteCloseListenersAfterSessionFlushed_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    (new CommandContext(mock(Command.class), processEngineConfiguration)).executeCloseListenersAfterSessionFlushed();

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersClosed()}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#executeCloseListenersClosed()}
   */
  @Test
  public void testExecuteCloseListenersClosed_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    (new CommandContext(mock(Command.class), processEngineConfiguration)).executeCloseListenersClosed();

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#executeCloseListenersCloseFailure()}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#executeCloseListenersCloseFailure()}
   */
  @Test
  public void testExecuteCloseListenersCloseFailure_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    (new CommandContext(mock(Command.class), processEngineConfiguration)).executeCloseListenersCloseFailure();

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#flushSessions()}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#flushSessions()}
   */
  @Test
  public void testFlushSessions_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    (new CommandContext(mock(Command.class), processEngineConfiguration)).flushSessions();

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#closeSessions()}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#closeSessions()}
   */
  @Test
  public void testCloseSessions_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    (new CommandContext(mock(Command.class), processEngineConfiguration)).closeSessions();

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#exception(Throwable)}.
   * <p>
   * Method under test: {@link CommandContext#exception(Throwable)}
   */
  @Test
  public void testException() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);
    Throwable exception = new Throwable();

    // Act
    commandContext.exception(exception);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(exception, commandContext.getException());
  }

  /**
   * Test {@link CommandContext#getDeploymentEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getDeploymentEntityManager()}
   */
  @Test
  public void testGetDeploymentEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    DeploymentEntityManager actualDeploymentEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getDeploymentEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualDeploymentEntityManager);
  }

  /**
   * Test {@link CommandContext#getResourceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getResourceEntityManager()}
   */
  @Test
  public void testGetResourceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    ResourceEntityManager actualResourceEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getResourceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualResourceEntityManager);
  }

  /**
   * Test {@link CommandContext#getByteArrayEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getByteArrayEntityManager()}
   */
  @Test
  public void testGetByteArrayEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    ByteArrayEntityManager actualByteArrayEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getByteArrayEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualByteArrayEntityManager);
  }

  /**
   * Test {@link CommandContext#getProcessDefinitionEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getProcessDefinitionEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    ProcessDefinitionEntityManager actualProcessDefinitionEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getProcessDefinitionEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualProcessDefinitionEntityManager);
  }

  /**
   * Test {@link CommandContext#getModelEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getModelEntityManager()}
   */
  @Test
  public void testGetModelEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    ModelEntityManager actualModelEntityManager = (new CommandContext(mock(Command.class), processEngineConfiguration))
        .getModelEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualModelEntityManager);
  }

  /**
   * Test {@link CommandContext#getProcessDefinitionInfoEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommandContext#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    ProcessDefinitionInfoEntityManager actualProcessDefinitionInfoEntityManager = (new CommandContext(
        mock(Command.class), processEngineConfiguration)).getProcessDefinitionInfoEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualProcessDefinitionInfoEntityManager);
  }

  /**
   * Test {@link CommandContext#getExecutionEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    ExecutionEntityManager actualExecutionEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getExecutionEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualExecutionEntityManager);
  }

  /**
   * Test {@link CommandContext#getTaskEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getTaskEntityManager()}
   */
  @Test
  public void testGetTaskEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    TaskEntityManager actualTaskEntityManager = (new CommandContext(mock(Command.class), processEngineConfiguration))
        .getTaskEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualTaskEntityManager);
  }

  /**
   * Test {@link CommandContext#getIdentityLinkEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getIdentityLinkEntityManager()}
   */
  @Test
  public void testGetIdentityLinkEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    IdentityLinkEntityManager actualIdentityLinkEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getIdentityLinkEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualIdentityLinkEntityManager);
  }

  /**
   * Test {@link CommandContext#getVariableInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getVariableInstanceEntityManager()}
   */
  @Test
  public void testGetVariableInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    VariableInstanceEntityManager actualVariableInstanceEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getVariableInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualVariableInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricProcessInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommandContext#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    HistoricProcessInstanceEntityManager actualHistoricProcessInstanceEntityManager = (new CommandContext(
        mock(Command.class), processEngineConfiguration)).getHistoricProcessInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricProcessInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricDetailEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getHistoricDetailEntityManager()}
   */
  @Test
  public void testGetHistoricDetailEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    HistoricDetailEntityManager actualHistoricDetailEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getHistoricDetailEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricDetailEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricVariableInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommandContext#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    HistoricVariableInstanceEntityManager actualHistoricVariableInstanceEntityManager = (new CommandContext(
        mock(Command.class), processEngineConfiguration)).getHistoricVariableInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricVariableInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricActivityInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommandContext#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    HistoricActivityInstanceEntityManager actualHistoricActivityInstanceEntityManager = (new CommandContext(
        mock(Command.class), processEngineConfiguration)).getHistoricActivityInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricActivityInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricTaskInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommandContext#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    HistoricTaskInstanceEntityManager actualHistoricTaskInstanceEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getHistoricTaskInstanceEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricTaskInstanceEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoricIdentityLinkEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommandContext#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    HistoricIdentityLinkEntityManager actualHistoricIdentityLinkEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getHistoricIdentityLinkEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoricIdentityLinkEntityManager);
  }

  /**
   * Test {@link CommandContext#getEventLogEntryEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getEventLogEntryEntityManager()}
   */
  @Test
  public void testGetEventLogEntryEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    EventLogEntryEntityManager actualEventLogEntryEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getEventLogEntryEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualEventLogEntryEntityManager);
  }

  /**
   * Test {@link CommandContext#getJobEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getJobEntityManager()}
   */
  @Test
  public void testGetJobEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    JobEntityManager actualJobEntityManager = (new CommandContext(mock(Command.class), processEngineConfiguration))
        .getJobEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualJobEntityManager);
  }

  /**
   * Test {@link CommandContext#getTimerJobEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getTimerJobEntityManager()}
   */
  @Test
  public void testGetTimerJobEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    TimerJobEntityManager actualTimerJobEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getTimerJobEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualTimerJobEntityManager);
  }

  /**
   * Test {@link CommandContext#getSuspendedJobEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getSuspendedJobEntityManager()}
   */
  @Test
  public void testGetSuspendedJobEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    SuspendedJobEntityManager actualSuspendedJobEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getSuspendedJobEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualSuspendedJobEntityManager);
  }

  /**
   * Test {@link CommandContext#getDeadLetterJobEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getDeadLetterJobEntityManager()}
   */
  @Test
  public void testGetDeadLetterJobEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    DeadLetterJobEntityManager actualDeadLetterJobEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getDeadLetterJobEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualDeadLetterJobEntityManager);
  }

  /**
   * Test {@link CommandContext#getAttachmentEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getAttachmentEntityManager()}
   */
  @Test
  public void testGetAttachmentEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    AttachmentEntityManager actualAttachmentEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getAttachmentEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualAttachmentEntityManager);
  }

  /**
   * Test {@link CommandContext#getTableDataManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getTableDataManager()}
   */
  @Test
  public void testGetTableDataManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    TableDataManager actualTableDataManager = (new CommandContext(mock(Command.class), processEngineConfiguration))
        .getTableDataManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualTableDataManager);
  }

  /**
   * Test {@link CommandContext#getCommentEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getCommentEntityManager()}
   */
  @Test
  public void testGetCommentEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    CommentEntityManager actualCommentEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getCommentEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualCommentEntityManager);
  }

  /**
   * Test {@link CommandContext#getPropertyEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getPropertyEntityManager()}
   */
  @Test
  public void testGetPropertyEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    PropertyEntityManager actualPropertyEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getPropertyEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualPropertyEntityManager);
  }

  /**
   * Test {@link CommandContext#getEventSubscriptionEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getEventSubscriptionEntityManager()}
   */
  @Test
  public void testGetEventSubscriptionEntityManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    EventSubscriptionEntityManager actualEventSubscriptionEntityManager = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getEventSubscriptionEntityManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualEventSubscriptionEntityManager);
  }

  /**
   * Test {@link CommandContext#getHistoryManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getHistoryManager()}
   */
  @Test
  public void testGetHistoryManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    HistoryManager actualHistoryManager = (new CommandContext(mock(Command.class), processEngineConfiguration))
        .getHistoryManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualHistoryManager);
  }

  /**
   * Test {@link CommandContext#getJobManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getJobManager()}
   */
  @Test
  public void testGetJobManager_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    JobManager actualJobManager = (new CommandContext(mock(Command.class), processEngineConfiguration)).getJobManager();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualJobManager);
  }

  /**
   * Test {@link CommandContext#addInvolvedExecution(ExecutionEntity)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommandContext#addInvolvedExecution(ExecutionEntity)}
   */
  @Test
  public void testAddInvolvedExecution_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandContext.addInvolvedExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandContext#hasInvolvedExecutions()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#hasInvolvedExecutions()}
   */
  @Test
  public void testHasInvolvedExecutions_thenReturnFalse() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    boolean actualHasInvolvedExecutionsResult = (new CommandContext(mock(Command.class), processEngineConfiguration))
        .hasInvolvedExecutions();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertFalse(actualHasInvolvedExecutionsResult);
  }

  /**
   * Test {@link CommandContext#getInvolvedExecutions()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getInvolvedExecutions()}
   */
  @Test
  public void testGetInvolvedExecutions_thenReturnEmpty() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    Collection<ExecutionEntity> actualInvolvedExecutions = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getInvolvedExecutions();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertTrue(actualInvolvedExecutions.isEmpty());
  }

  /**
   * Test {@link CommandContext#getEventDispatcher()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getEventDispatcher()}
   */
  @Test
  public void testGetEventDispatcher_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    ActivitiEventDispatcher actualEventDispatcher = (new CommandContext(mock(Command.class),
        processEngineConfiguration)).getEventDispatcher();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualEventDispatcher);
  }

  /**
   * Test {@link CommandContext#getResult()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#getResult()}
   */
  @Test
  public void testGetResult_thenReturnNull() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    Object actualResult = (new CommandContext(mock(Command.class), processEngineConfiguration)).getResult();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertNull(actualResult);
  }

  /**
   * Test {@link CommandContext#setResult(Object)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandContext#setResult(Object)}
   */
  @Test
  public void testSetResult_thenCallsCreateAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act
    (new CommandContext(mock(Command.class), processEngineConfiguration)).setResult(JSONObject.NULL);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }
}
