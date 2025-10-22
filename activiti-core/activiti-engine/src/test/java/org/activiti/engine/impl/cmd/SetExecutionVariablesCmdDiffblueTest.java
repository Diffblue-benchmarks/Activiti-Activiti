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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SetExecutionVariablesCmdDiffblueTest {
  /**
   * Test {@link SetExecutionVariablesCmd#SetExecutionVariablesCmd(String, Map, boolean)}.
   * <p>
   * Method under test: {@link SetExecutionVariablesCmd#SetExecutionVariablesCmd(String, Map, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SetExecutionVariablesCmd.<init>(String, Map, boolean)"})
  public void testNewSetExecutionVariablesCmd() {
    // Arrange, Act and Assert
    assertTrue((new SetExecutionVariablesCmd("42", new HashMap<>(), true)).variables.isEmpty());
  }

  /**
   * Test {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)} with {@code commandContext}, {@code execution}.
   * <p>
   * Method under test: {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object SetExecutionVariablesCmd.execute(CommandContext, ExecutionEntity)"})
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
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
    assertTrue((Boolean) ((Map<Object, Object>) persistentState).get("forcedUpdate"));
  }

  /**
   * Test {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)} with {@code commandContext}, {@code execution}.
   * <p>
   * Method under test: {@link SetExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object SetExecutionVariablesCmd.execute(CommandContext, ExecutionEntity)"})
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
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
    assertTrue((Boolean) ((Map<Object, Object>) persistentState).get("forcedUpdate"));
  }

  /**
   * Test {@link SetExecutionVariablesCmd#getSuspendedExceptionMessage()}.
   * <p>
   * Method under test: {@link SetExecutionVariablesCmd#getSuspendedExceptionMessage()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SetExecutionVariablesCmd.getSuspendedExceptionMessage()"})
  public void testGetSuspendedExceptionMessage() {
    // Arrange, Act and Assert
    assertEquals("Cannot set variables because execution '42' is suspended",
        (new SetExecutionVariablesCmd("42", new HashMap<>(), true)).getSuspendedExceptionMessage());
  }
}
