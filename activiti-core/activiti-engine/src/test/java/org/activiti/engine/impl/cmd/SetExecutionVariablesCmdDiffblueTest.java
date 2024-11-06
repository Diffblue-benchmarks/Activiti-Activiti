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
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class SetExecutionVariablesCmdDiffblueTest {
  /**
   * Test
   * {@link SetExecutionVariablesCmd#SetExecutionVariablesCmd(String, Map, boolean)}.
   * <p>
   * Method under test:
   * {@link SetExecutionVariablesCmd#SetExecutionVariablesCmd(String, Map, boolean)}
   */
  @Test
  public void testNewSetExecutionVariablesCmd() {
    // Arrange, Act and Assert
    assertTrue((new SetExecutionVariablesCmd("42", new HashMap<>(), true)).variables.isEmpty());
  }

  /**
   * Test
   * {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   * with {@code commandContext}, {@code execution}.
   * <p>
   * Method under test:
   * {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   */
  @Test
  public void testExecuteWithCommandContextExecution() {
    // Arrange
    SetExecutionVariablesCmd setExecutionVariablesCmd = new SetExecutionVariablesCmd("42", new HashMap<>(), true);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Object actualExecuteResult = setExecutionVariablesCmd.execute(null, execution);

    // Assert
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertNull(actualExecuteResult);
    assertEquals(Float.PRECISION, ((Map<Object, Object>) persistentState).size());
    assertTrue(((Map<Object, Object>) persistentState).containsKey("forcedUpdate"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
  }

  /**
   * Test
   * {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   * with {@code commandContext}, {@code execution}.
   * <p>
   * Method under test:
   * {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   */
  @Test
  public void testExecuteWithCommandContextExecution2() {
    // Arrange
    SetExecutionVariablesCmd setExecutionVariablesCmd = new SetExecutionVariablesCmd("42", new HashMap<>(), false);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Object actualExecuteResult = setExecutionVariablesCmd.execute(null, execution);

    // Assert
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertNull(actualExecuteResult);
    assertEquals(Float.PRECISION, ((Map<Object, Object>) persistentState).size());
    assertTrue(((Map<Object, Object>) persistentState).containsKey("forcedUpdate"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
  }

  /**
   * Test {@link SetExecutionVariablesCmd#getSuspendedExceptionMessage()}.
   * <p>
   * Method under test:
   * {@link SetExecutionVariablesCmd#getSuspendedExceptionMessage()}
   */
  @Test
  public void testGetSuspendedExceptionMessage() {
    // Arrange, Act and Assert
    assertEquals("Cannot set variables because execution '42' is suspended",
        (new SetExecutionVariablesCmd("42", new HashMap<>(), true)).getSuspendedExceptionMessage());
  }

  /**
   * Test {@link SetExecutionVariablesCmd#getSuspendedExceptionMessage()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SetExecutionVariablesCmd#getSuspendedExceptionMessage()}
   */
  @Test
  public void testGetSuspendedExceptionMessage_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertEquals("Cannot set variables because execution '42' is suspended",
        (new SetExecutionVariablesCmd("42", variables, true)).getSuspendedExceptionMessage());
  }
}
