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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisExecutionDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EndExecutionOperationDiffblueTest {
  @Mock
  private CommandContext commandContext;

  @InjectMocks
  private EndExecutionOperation endExecutionOperation;

  @Mock
  private ExecutionEntity executionEntity;

  /**
   * Test
   * {@link EndExecutionOperation#EndExecutionOperation(CommandContext, ExecutionEntity)}.
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#EndExecutionOperation(CommandContext, ExecutionEntity)}
   */
  @Test
  public void testNewEndExecutionOperation() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(null);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any())).thenReturn(defaultActivitiEngineAgenda);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    EndExecutionOperation actualEndExecutionOperation = new EndExecutionOperation(commandContext, execution);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(defaultActivitiEngineAgenda, actualEndExecutionOperation.getAgenda());
    assertSame(commandContext, actualEndExecutionOperation.getCommandContext());
    assertSame(execution, actualEndExecutionOperation.getExecution());
  }

  /**
   * Test
   * {@link EndExecutionOperation#handleRegularExecutionEnd(ExecutionEntityManager, ExecutionEntity)}.
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#handleRegularExecutionEnd(ExecutionEntityManager, ExecutionEntity)}
   */
  @Test
  public void testHandleRegularExecutionEnd_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    EndExecutionOperation endExecutionOperation = new EndExecutionOperation(commandContext,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManager = new ExecutionEntityManagerImpl(processEngineConfiguration2,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl parentExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualHandleRegularExecutionEndResult = endExecutionOperation
        .handleRegularExecutionEnd(executionEntityManager, parentExecution);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertSame(parentExecution, actualHandleRegularExecutionEndResult);
  }

  /**
   * Test
   * {@link EndExecutionOperation#isEndEventInMultiInstanceSubprocess(ExecutionEntity)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#isEndEventInMultiInstanceSubprocess(ExecutionEntity)}
   */
  @Test
  public void testIsEndEventInMultiInstanceSubprocess_thenReturnFalse() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    EndExecutionOperation endExecutionOperation = new EndExecutionOperation(commandContext,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    boolean actualIsEndEventInMultiInstanceSubprocessResult = endExecutionOperation
        .isEndEventInMultiInstanceSubprocess(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertFalse(actualIsEndEventInMultiInstanceSubprocessResult);
  }

  /**
   * Test
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForProcessInstance(ExecutionEntityManager, String)}.
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForProcessInstance(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetNumberOfActiveChildExecutionsForProcessInstance() {
    // Arrange
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.isActive()).thenReturn(true);

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntity);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    int actualNumberOfActiveChildExecutionsForProcessInstance = endExecutionOperation
        .getNumberOfActiveChildExecutionsForProcessInstance(
            new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42");

    // Assert
    verify(executionEntity).getId();
    verify(executionEntity).isActive();
    verify(executionDataManager).findChildExecutionsByProcessInstanceId(eq("42"));
    assertEquals(0, actualNumberOfActiveChildExecutionsForProcessInstance);
  }

  /**
   * Test
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForProcessInstance(ExecutionEntityManager, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForProcessInstance(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetNumberOfActiveChildExecutionsForProcessInstance_givenArrayList() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    int actualNumberOfActiveChildExecutionsForProcessInstance = endExecutionOperation
        .getNumberOfActiveChildExecutionsForProcessInstance(
            new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42");

    // Assert
    verify(executionDataManager).findChildExecutionsByProcessInstanceId(eq("42"));
    assertEquals(0, actualNumberOfActiveChildExecutionsForProcessInstance);
  }

  /**
   * Test
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForProcessInstance(ExecutionEntityManager, String)}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForProcessInstance(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetNumberOfActiveChildExecutionsForProcessInstance_thenReturnOne() {
    // Arrange
    when(executionEntity.getId()).thenReturn("foo");
    when(executionEntity.isActive()).thenReturn(true);

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntity);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    int actualNumberOfActiveChildExecutionsForProcessInstance = endExecutionOperation
        .getNumberOfActiveChildExecutionsForProcessInstance(
            new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42");

    // Assert
    verify(executionEntity).getId();
    verify(executionEntity).isActive();
    verify(executionDataManager).findChildExecutionsByProcessInstanceId(eq("42"));
    assertEquals(1, actualNumberOfActiveChildExecutionsForProcessInstance);
  }

  /**
   * Test
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForProcessInstance(ExecutionEntityManager, String)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForProcessInstance(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetNumberOfActiveChildExecutionsForProcessInstance_thenThrowRuntimeException() {
    // Arrange
    when(executionEntity.getId()).thenThrow(new RuntimeException("foo"));
    when(executionEntity.isActive()).thenReturn(true);

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntity);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> endExecutionOperation.getNumberOfActiveChildExecutionsForProcessInstance(
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42"));
    verify(executionEntity).getId();
    verify(executionEntity).isActive();
    verify(executionDataManager).findChildExecutionsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForExecution(ExecutionEntityManager, String)}.
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForExecution(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetNumberOfActiveChildExecutionsForExecution() {
    // Arrange
    when(executionEntity.getCurrentFlowElement()).thenReturn(new BoundaryEvent());

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntity);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    int actualNumberOfActiveChildExecutionsForExecution = endExecutionOperation
        .getNumberOfActiveChildExecutionsForExecution(
            new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42");

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
    assertEquals(0, actualNumberOfActiveChildExecutionsForExecution);
  }

  /**
   * Test
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForExecution(ExecutionEntityManager, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForExecution(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetNumberOfActiveChildExecutionsForExecution_givenArrayList_thenReturnZero() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    int actualNumberOfActiveChildExecutionsForExecution = endExecutionOperation
        .getNumberOfActiveChildExecutionsForExecution(
            new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42");

    // Assert
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
    assertEquals(0, actualNumberOfActiveChildExecutionsForExecution);
  }

  /**
   * Test
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForExecution(ExecutionEntityManager, String)}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForExecution(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetNumberOfActiveChildExecutionsForExecution_thenReturnOne() {
    // Arrange
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntity);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    int actualNumberOfActiveChildExecutionsForExecution = endExecutionOperation
        .getNumberOfActiveChildExecutionsForExecution(
            new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42");

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
    assertEquals(1, actualNumberOfActiveChildExecutionsForExecution);
  }

  /**
   * Test
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForExecution(ExecutionEntityManager, String)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getNumberOfActiveChildExecutionsForExecution(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetNumberOfActiveChildExecutionsForExecution_thenThrowRuntimeException() {
    // Arrange
    when(executionEntity.getCurrentFlowElement()).thenThrow(new RuntimeException("foo"));

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntity);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> endExecutionOperation.getNumberOfActiveChildExecutionsForExecution(
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42"));
    verify(executionEntity).getCurrentFlowElement();
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
  }

  /**
   * Test
   * {@link EndExecutionOperation#getActiveChildExecutionsForExecution(ExecutionEntityManager, String)}.
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getActiveChildExecutionsForExecution(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetActiveChildExecutionsForExecution() {
    // Arrange
    when(executionEntity.getCurrentFlowElement()).thenReturn(new BoundaryEvent());

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntity);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    List<ExecutionEntity> actualActiveChildExecutionsForExecution = endExecutionOperation
        .getActiveChildExecutionsForExecution(
            new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42");

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
    assertTrue(actualActiveChildExecutionsForExecution.isEmpty());
  }

  /**
   * Test
   * {@link EndExecutionOperation#getActiveChildExecutionsForExecution(ExecutionEntityManager, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getActiveChildExecutionsForExecution(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetActiveChildExecutionsForExecution_givenArrayList_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<ExecutionEntity> actualActiveChildExecutionsForExecution = endExecutionOperation
        .getActiveChildExecutionsForExecution(
            new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42");

    // Assert
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
    assertTrue(actualActiveChildExecutionsForExecution.isEmpty());
  }

  /**
   * Test
   * {@link EndExecutionOperation#getActiveChildExecutionsForExecution(ExecutionEntityManager, String)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getActiveChildExecutionsForExecution(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetActiveChildExecutionsForExecution_thenReturnSizeIsOne() {
    // Arrange
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntity);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    List<ExecutionEntity> actualActiveChildExecutionsForExecution = endExecutionOperation
        .getActiveChildExecutionsForExecution(
            new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42");

    // Assert
    verify(executionEntity).getCurrentFlowElement();
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
    assertEquals(1, actualActiveChildExecutionsForExecution.size());
  }

  /**
   * Test
   * {@link EndExecutionOperation#getActiveChildExecutionsForExecution(ExecutionEntityManager, String)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#getActiveChildExecutionsForExecution(ExecutionEntityManager, String)}
   */
  @Test
  public void testGetActiveChildExecutionsForExecution_thenThrowRuntimeException() {
    // Arrange
    when(executionEntity.getCurrentFlowElement()).thenThrow(new RuntimeException("foo"));

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntity);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> endExecutionOperation.getActiveChildExecutionsForExecution(
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager), "42"));
    verify(executionEntity).getCurrentFlowElement();
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
  }

  /**
   * Test
   * {@link EndExecutionOperation#allChildExecutionsEnded(ExecutionEntity, ExecutionEntity)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndExecutionOperation#allChildExecutionsEnded(ExecutionEntity, ExecutionEntity)}
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

    EndExecutionOperation endExecutionOperation = new EndExecutionOperation(commandContext,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityImpl parentExecutionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    boolean actualAllChildExecutionsEndedResult = endExecutionOperation.allChildExecutionsEnded(parentExecutionEntity,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertTrue(actualAllChildExecutionsEndedResult);
  }
}
