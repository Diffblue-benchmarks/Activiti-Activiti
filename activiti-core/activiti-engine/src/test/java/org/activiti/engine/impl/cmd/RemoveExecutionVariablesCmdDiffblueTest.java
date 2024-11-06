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
import java.util.ArrayList;
import java.util.Collection;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class RemoveExecutionVariablesCmdDiffblueTest {
  /**
   * Test
   * {@link RemoveExecutionVariablesCmd#RemoveExecutionVariablesCmd(String, Collection, boolean)}.
   * <p>
   * Method under test:
   * {@link RemoveExecutionVariablesCmd#RemoveExecutionVariablesCmd(String, Collection, boolean)}
   */
  @Test
  public void testNewRemoveExecutionVariablesCmd() {
    // Arrange and Act
    RemoveExecutionVariablesCmd actualRemoveExecutionVariablesCmd = new RemoveExecutionVariablesCmd("42",
        new ArrayList<>(), true);

    // Assert
    assertEquals("42", actualRemoveExecutionVariablesCmd.executionId);
    assertEquals("Cannot remove variables because execution '42' is suspended",
        actualRemoveExecutionVariablesCmd.getSuspendedExceptionMessage());
  }

  /**
   * Test
   * {@link RemoveExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   * with {@code commandContext}, {@code execution}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RemoveExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   */
  @Test
  public void testExecuteWithCommandContextExecution_thenReturnNull() {
    // Arrange
    RemoveExecutionVariablesCmd removeExecutionVariablesCmd = new RemoveExecutionVariablesCmd("42", new ArrayList<>(),
        true);

    // Act and Assert
    assertNull(removeExecutionVariablesCmd.execute(null, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link RemoveExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   * with {@code commandContext}, {@code execution}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RemoveExecutionVariablesCmd#execute(CommandContext, ExecutionEntity)}
   */
  @Test
  public void testExecuteWithCommandContextExecution_thenReturnNull2() {
    // Arrange
    RemoveExecutionVariablesCmd removeExecutionVariablesCmd = new RemoveExecutionVariablesCmd("42", new ArrayList<>(),
        false);

    // Act and Assert
    assertNull(removeExecutionVariablesCmd.execute(null, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link RemoveExecutionVariablesCmd#getSuspendedExceptionMessage()}.
   * <p>
   * Method under test:
   * {@link RemoveExecutionVariablesCmd#getSuspendedExceptionMessage()}
   */
  @Test
  public void testGetSuspendedExceptionMessage() {
    // Arrange, Act and Assert
    assertEquals("Cannot remove variables because execution '42' is suspended",
        (new RemoveExecutionVariablesCmd("42", new ArrayList<>(), true)).getSuspendedExceptionMessage());
  }
}
