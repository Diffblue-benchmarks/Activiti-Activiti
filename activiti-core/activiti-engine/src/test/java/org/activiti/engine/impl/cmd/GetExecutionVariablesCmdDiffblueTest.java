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
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class GetExecutionVariablesCmdDiffblueTest {
  /**
   * Test
   * {@link GetExecutionVariablesCmd#GetExecutionVariablesCmd(String, Collection, boolean)}.
   * <p>
   * Method under test:
   * {@link GetExecutionVariablesCmd#GetExecutionVariablesCmd(String, Collection, boolean)}
   */
  @Test
  public void testNewGetExecutionVariablesCmd() {
    // Arrange and Act
    GetExecutionVariablesCmd actualGetExecutionVariablesCmd = new GetExecutionVariablesCmd("42", new ArrayList<>(),
        true);

    // Assert
    Collection<String> collection = actualGetExecutionVariablesCmd.variableNames;
    assertTrue(collection instanceof List);
    assertEquals("42", actualGetExecutionVariablesCmd.executionId);
    assertTrue(collection.isEmpty());
    assertTrue(actualGetExecutionVariablesCmd.isLocal);
  }

  /**
   * Test
   * {@link GetExecutionVariablesCmd#getVariable(ExecutionEntity, CommandContext)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetExecutionVariablesCmd#getVariable(ExecutionEntity, CommandContext)}
   */
  @Test
  public void testGetVariable_thenReturnEmpty() {
    // Arrange
    GetExecutionVariablesCmd getExecutionVariablesCmd = new GetExecutionVariablesCmd("42", new ArrayList<>(), true);

    // Act and Assert
    assertTrue(getExecutionVariablesCmd.getVariable(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), null)
        .isEmpty());
  }

  /**
   * Test
   * {@link GetExecutionVariablesCmd#getVariable(ExecutionEntity, CommandContext)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetExecutionVariablesCmd#getVariable(ExecutionEntity, CommandContext)}
   */
  @Test
  public void testGetVariable_thenReturnEmpty2() {
    // Arrange
    GetExecutionVariablesCmd getExecutionVariablesCmd = new GetExecutionVariablesCmd("42", new ArrayList<>(), false);

    // Act and Assert
    assertTrue(getExecutionVariablesCmd.getVariable(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), null)
        .isEmpty());
  }
}
