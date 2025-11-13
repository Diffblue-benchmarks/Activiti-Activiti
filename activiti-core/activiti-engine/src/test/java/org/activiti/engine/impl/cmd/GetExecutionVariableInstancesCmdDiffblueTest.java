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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class GetExecutionVariableInstancesCmdDiffblueTest {
  /**
   * Test {@link GetExecutionVariableInstancesCmd#GetExecutionVariableInstancesCmd(String,
   * Collection, boolean)}.
   *
   * <p>Method under test: {@link
   * GetExecutionVariableInstancesCmd#GetExecutionVariableInstancesCmd(String, Collection, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetExecutionVariableInstancesCmd.<init>(String, Collection, boolean)"})
  public void testNewGetExecutionVariableInstancesCmd() {
    // Arrange and Act
    GetExecutionVariableInstancesCmd actualGetExecutionVariableInstancesCmd =
        new GetExecutionVariableInstancesCmd("42", new ArrayList<>(), true);

    // Assert
    Collection<String> collection = actualGetExecutionVariableInstancesCmd.variableNames;
    assertTrue(collection instanceof List);
    assertEquals("42", actualGetExecutionVariableInstancesCmd.executionId);
    assertTrue(collection.isEmpty());
    assertTrue(actualGetExecutionVariableInstancesCmd.isLocal);
  }

  /**
   * Test {@link GetExecutionVariableInstancesCmd#getVariable(ExecutionEntity, CommandContext)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link GetExecutionVariableInstancesCmd#getVariable(ExecutionEntity,
   * CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Map GetExecutionVariableInstancesCmd.getVariable(ExecutionEntity, CommandContext)"
  })
  public void testGetVariable_thenReturnEmpty() {
    // Arrange
    GetExecutionVariableInstancesCmd getExecutionVariableInstancesCmd =
        new GetExecutionVariableInstancesCmd("42", new ArrayList<>(), true);

    // Act and Assert
    assertTrue(
        getExecutionVariableInstancesCmd
            .getVariable(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), null)
            .isEmpty());
  }

  /**
   * Test {@link GetExecutionVariableInstancesCmd#getVariable(ExecutionEntity, CommandContext)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link GetExecutionVariableInstancesCmd#getVariable(ExecutionEntity,
   * CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Map GetExecutionVariableInstancesCmd.getVariable(ExecutionEntity, CommandContext)"
  })
  public void testGetVariable_thenReturnEmpty2() {
    // Arrange
    GetExecutionVariableInstancesCmd getExecutionVariableInstancesCmd =
        new GetExecutionVariableInstancesCmd("42", new ArrayList<>(), false);

    // Act and Assert
    assertTrue(
        getExecutionVariableInstancesCmd
            .getVariable(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), null)
            .isEmpty());
  }
}
