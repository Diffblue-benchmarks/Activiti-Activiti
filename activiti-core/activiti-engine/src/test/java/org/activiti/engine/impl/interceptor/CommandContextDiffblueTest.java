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
   * Method under test: {@link CommandContext#close()}
   */
  @Test
  public void testClose() {
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
   * Method under test: {@link CommandContext#rethrowExceptionIfNeeded()}
   */
  @Test
  public void testRethrowExceptionIfNeeded() throws Error {
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
   * Method under test: {@link CommandContext#hasCloseListener(Class)}
   */
  @Test
  public void testHasCloseListener() {
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
   * Method under test: {@link CommandContext#executeCloseListenersClosing()}
   */
  @Test
  public void testExecuteCloseListenersClosing() {
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
   * Method under test:
   * {@link CommandContext#executeCloseListenersAfterSessionFlushed()}
   */
  @Test
  public void testExecuteCloseListenersAfterSessionFlushed() {
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
   * Method under test: {@link CommandContext#executeCloseListenersClosed()}
   */
  @Test
  public void testExecuteCloseListenersClosed() {
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
   * Method under test: {@link CommandContext#executeCloseListenersCloseFailure()}
   */
  @Test
  public void testExecuteCloseListenersCloseFailure() {
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
   * Method under test: {@link CommandContext#flushSessions()}
   */
  @Test
  public void testFlushSessions() {
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
   * Method under test: {@link CommandContext#closeSessions()}
   */
  @Test
  public void testCloseSessions() {
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
   * Method under test: {@link CommandContext#getDeploymentEntityManager()}
   */
  @Test
  public void testGetDeploymentEntityManager() {
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
   * Method under test: {@link CommandContext#getResourceEntityManager()}
   */
  @Test
  public void testGetResourceEntityManager() {
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
   * Method under test: {@link CommandContext#getByteArrayEntityManager()}
   */
  @Test
  public void testGetByteArrayEntityManager() {
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
   * Method under test: {@link CommandContext#getProcessDefinitionEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionEntityManager() {
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
   * Method under test: {@link CommandContext#getModelEntityManager()}
   */
  @Test
  public void testGetModelEntityManager() {
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
   * Method under test:
   * {@link CommandContext#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoEntityManager() {
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
   * Method under test: {@link CommandContext#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager() {
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
   * Method under test: {@link CommandContext#getTaskEntityManager()}
   */
  @Test
  public void testGetTaskEntityManager() {
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
   * Method under test: {@link CommandContext#getIdentityLinkEntityManager()}
   */
  @Test
  public void testGetIdentityLinkEntityManager() {
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
   * Method under test: {@link CommandContext#getVariableInstanceEntityManager()}
   */
  @Test
  public void testGetVariableInstanceEntityManager() {
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
   * Method under test:
   * {@link CommandContext#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceEntityManager() {
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
   * Method under test: {@link CommandContext#getHistoricDetailEntityManager()}
   */
  @Test
  public void testGetHistoricDetailEntityManager() {
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
   * Method under test:
   * {@link CommandContext#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceEntityManager() {
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
   * Method under test:
   * {@link CommandContext#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceEntityManager() {
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
   * Method under test:
   * {@link CommandContext#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceEntityManager() {
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
   * Method under test:
   * {@link CommandContext#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkEntityManager() {
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
   * Method under test: {@link CommandContext#getEventLogEntryEntityManager()}
   */
  @Test
  public void testGetEventLogEntryEntityManager() {
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
   * Method under test: {@link CommandContext#getJobEntityManager()}
   */
  @Test
  public void testGetJobEntityManager() {
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
   * Method under test: {@link CommandContext#getTimerJobEntityManager()}
   */
  @Test
  public void testGetTimerJobEntityManager() {
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
   * Method under test: {@link CommandContext#getSuspendedJobEntityManager()}
   */
  @Test
  public void testGetSuspendedJobEntityManager() {
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
   * Method under test: {@link CommandContext#getDeadLetterJobEntityManager()}
   */
  @Test
  public void testGetDeadLetterJobEntityManager() {
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
   * Method under test: {@link CommandContext#getAttachmentEntityManager()}
   */
  @Test
  public void testGetAttachmentEntityManager() {
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
   * Method under test: {@link CommandContext#getTableDataManager()}
   */
  @Test
  public void testGetTableDataManager() {
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
   * Method under test: {@link CommandContext#getCommentEntityManager()}
   */
  @Test
  public void testGetCommentEntityManager() {
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
   * Method under test: {@link CommandContext#getPropertyEntityManager()}
   */
  @Test
  public void testGetPropertyEntityManager() {
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
   * Method under test: {@link CommandContext#getEventSubscriptionEntityManager()}
   */
  @Test
  public void testGetEventSubscriptionEntityManager() {
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
   * Method under test: {@link CommandContext#getHistoryManager()}
   */
  @Test
  public void testGetHistoryManager() {
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
   * Method under test: {@link CommandContext#getJobManager()}
   */
  @Test
  public void testGetJobManager() {
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
   * Method under test:
   * {@link CommandContext#addInvolvedExecution(ExecutionEntity)}
   */
  @Test
  public void testAddInvolvedExecution() {
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
   * Method under test: {@link CommandContext#hasInvolvedExecutions()}
   */
  @Test
  public void testHasInvolvedExecutions() {
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
   * Method under test: {@link CommandContext#getInvolvedExecutions()}
   */
  @Test
  public void testGetInvolvedExecutions() {
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
   * Method under test: {@link CommandContext#getEventDispatcher()}
   */
  @Test
  public void testGetEventDispatcher() {
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
   * Method under test: {@link CommandContext#getResult()}
   */
  @Test
  public void testGetResult() {
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
   * Method under test: {@link CommandContext#setResult(Object)}
   */
  @Test
  public void testSetResult() {
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

  /**
   * Method under test:
   * {@link CommandContext#CommandContext(Command, ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewCommandContext() {
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
}
