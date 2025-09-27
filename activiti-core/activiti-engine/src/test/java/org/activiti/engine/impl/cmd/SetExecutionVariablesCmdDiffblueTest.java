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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class SetExecutionVariablesCmdDiffblueTest {
  /**
   * Test {@link SetExecutionVariablesCmd#SetExecutionVariablesCmd(String, Map, boolean)}.
   *
   * <p>Method under test: {@link SetExecutionVariablesCmd#SetExecutionVariablesCmd(String, Map,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetExecutionVariablesCmd.<init>(String, Map, boolean)"})
  public void testNewSetExecutionVariablesCmd() {
    // Arrange and Act
    SetExecutionVariablesCmd actualSetExecutionVariablesCmd =
        new SetExecutionVariablesCmd("42", new HashMap<>(), true);

    // Assert
    assertTrue(actualSetExecutionVariablesCmd.variables.isEmpty());
  }

  /**
   * Test {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)} with {@code
   * commandContext}, {@code execution}.
   *
   * <p>Method under test: {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SetExecutionVariablesCmd.execute(CommandContext, ExecutionEntity)"})
  public void testExecuteWithCommandContextExecution() {
    // Arrange
    SetExecutionVariablesCmd setExecutionVariablesCmd =
        new SetExecutionVariablesCmd("42", new HashMap<>(), true);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Object actualExecuteResult = setExecutionVariablesCmd.execute(null, execution);

    // Assert
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertNull(actualExecuteResult);
    assertEquals(Float.PRECISION, ((Map<Object, Object>) persistentState).size());
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
    assertTrue((Boolean) ((Map<Object, Object>) persistentState).get("forcedUpdate"));
  }

  /**
   * Test {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)} with {@code
   * commandContext}, {@code execution}.
   *
   * <p>Method under test: {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SetExecutionVariablesCmd.execute(CommandContext, ExecutionEntity)"})
  public void testExecuteWithCommandContextExecution2() {
    // Arrange
    SetExecutionVariablesCmd setExecutionVariablesCmd =
        new SetExecutionVariablesCmd("42", new HashMap<>(), false);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Object actualExecuteResult = setExecutionVariablesCmd.execute(null, execution);

    // Assert
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertNull(actualExecuteResult);
    assertEquals(Float.PRECISION, ((Map<Object, Object>) persistentState).size());
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
    assertTrue((Boolean) ((Map<Object, Object>) persistentState).get("forcedUpdate"));
  }

  /**
   * Test {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)} with {@code
   * commandContext}, {@code execution}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SetExecutionVariablesCmd.execute(CommandContext, ExecutionEntity)"})
  public void testExecuteWithCommandContextExecution_thenCallsCreateAgenda() {
    // Arrange
    SetExecutionVariablesCmd setExecutionVariablesCmd =
        new SetExecutionVariablesCmd("42", null, false);

    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Object actualExecuteResult = setExecutionVariablesCmd.execute(commandContext, execution);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertNull(actualExecuteResult);
    assertEquals(Float.PRECISION, ((Map<Object, Object>) persistentState).size());
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
    assertTrue((Boolean) ((Map<Object, Object>) persistentState).get("forcedUpdate"));
  }

  /**
   * Test {@link SetExecutionVariablesCmd#getSuspendedExceptionMessage()}.
   *
   * <p>Method under test: {@link SetExecutionVariablesCmd#getSuspendedExceptionMessage()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetExecutionVariablesCmd.getSuspendedExceptionMessage()"})
  public void testGetSuspendedExceptionMessage() {
    // Arrange
    SetExecutionVariablesCmd setExecutionVariablesCmd =
        new SetExecutionVariablesCmd("42", new HashMap<>(), true);

    // Act and Assert
    assertEquals(
        "Cannot set variables because execution '42' is suspended",
        setExecutionVariablesCmd.getSuspendedExceptionMessage());
  }
}
