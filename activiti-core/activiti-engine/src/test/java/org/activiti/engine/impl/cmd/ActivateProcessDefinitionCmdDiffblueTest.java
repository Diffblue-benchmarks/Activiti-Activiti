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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.impl.persistence.entity.SuspensionState.SuspensionStateImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivateProcessDefinitionCmdDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ActivateProcessDefinitionCmd#ActivateProcessDefinitionCmd(ProcessDefinitionEntity,
   *       boolean, Date, String)}
   *   <li>{@link ActivateProcessDefinitionCmd#getDelayedExecutionJobHandlerType()}
   *   <li>{@link ActivateProcessDefinitionCmd#getProcessDefinitionSuspensionState()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivateProcessDefinitionCmd.<init>(String, String, boolean, Date, String)",
    "void ActivateProcessDefinitionCmd.<init>(ProcessDefinitionEntity, boolean, Date, String)",
    "String ActivateProcessDefinitionCmd.getDelayedExecutionJobHandlerType()",
    "SuspensionState ActivateProcessDefinitionCmd.getProcessDefinitionSuspensionState()"
  })
  public void testGettersAndSetters_whenProcessDefinitionEntityImpl() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    ActivateProcessDefinitionCmd actualActivateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(processDefinitionEntity, true, executionDate, "42");
    String actualDelayedExecutionJobHandlerType =
        actualActivateProcessDefinitionCmd.getDelayedExecutionJobHandlerType();

    // Assert
    assertTrue(
        actualActivateProcessDefinitionCmd.getProcessDefinitionSuspensionState()
            instanceof SuspensionStateImpl);
    assertEquals("activate-processdefinition", actualDelayedExecutionJobHandlerType);
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code Process Definition Key}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ActivateProcessDefinitionCmd#ActivateProcessDefinitionCmd(String, String, boolean,
   *       Date, String)}
   *   <li>{@link ActivateProcessDefinitionCmd#getDelayedExecutionJobHandlerType()}
   *   <li>{@link ActivateProcessDefinitionCmd#getProcessDefinitionSuspensionState()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivateProcessDefinitionCmd.<init>(String, String, boolean, Date, String)",
    "void ActivateProcessDefinitionCmd.<init>(ProcessDefinitionEntity, boolean, Date, String)",
    "String ActivateProcessDefinitionCmd.getDelayedExecutionJobHandlerType()",
    "SuspensionState ActivateProcessDefinitionCmd.getProcessDefinitionSuspensionState()"
  })
  public void testGettersAndSetters_whenProcessDefinitionKey() {
    // Arrange
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    ActivateProcessDefinitionCmd actualActivateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd("42", "Process Definition Key", true, executionDate, "42");
    String actualDelayedExecutionJobHandlerType =
        actualActivateProcessDefinitionCmd.getDelayedExecutionJobHandlerType();

    // Assert
    assertTrue(
        actualActivateProcessDefinitionCmd.getProcessDefinitionSuspensionState()
            instanceof SuspensionStateImpl);
    assertEquals("activate-processdefinition", actualDelayedExecutionJobHandlerType);
  }

  /**
   * Test {@link ActivateProcessDefinitionCmd#getProcessInstanceChangeStateCmd(ProcessInstance)}.
   *
   * <ul>
   *   <li>Then return {@link ActivateProcessInstanceCmd}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivateProcessDefinitionCmd#getProcessInstanceChangeStateCmd(ProcessInstance)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractSetProcessInstanceStateCmd ActivateProcessDefinitionCmd.getProcessInstanceChangeStateCmd(ProcessInstance)"
  })
  public void testGetProcessInstanceChangeStateCmd_thenReturnActivateProcessInstanceCmd() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(processDefinitionEntity, true, executionDate, "42");

    // Act
    AbstractSetProcessInstanceStateCmd actualProcessInstanceChangeStateCmd =
        activateProcessDefinitionCmd.getProcessInstanceChangeStateCmd(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualProcessInstanceChangeStateCmd instanceof ActivateProcessInstanceCmd);
    SuspensionState newState = actualProcessInstanceChangeStateCmd.getNewState();
    assertTrue(newState instanceof SuspensionStateImpl);
    assertEquals("active", newState.toString());
    assertNull(
        ((ActivateProcessInstanceCmd) actualProcessInstanceChangeStateCmd).processInstanceId);
    assertEquals(1, newState.getStateCode());
  }
}
