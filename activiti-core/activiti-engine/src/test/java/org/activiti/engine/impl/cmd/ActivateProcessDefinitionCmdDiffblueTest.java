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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.junit.Test;

public class ActivateProcessDefinitionCmdDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ActivateProcessDefinitionCmd#ActivateProcessDefinitionCmd(ProcessDefinitionEntity, boolean, Date, String)}
   *   <li>{@link ActivateProcessDefinitionCmd#getDelayedExecutionJobHandlerType()}
   *   <li>
   * {@link ActivateProcessDefinitionCmd#getProcessDefinitionSuspensionState()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenProcessDefinitionEntityImpl() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();

    // Act
    ActivateProcessDefinitionCmd actualActivateProcessDefinitionCmd = new ActivateProcessDefinitionCmd(
        processDefinitionEntity, true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42");
    String actualDelayedExecutionJobHandlerType = actualActivateProcessDefinitionCmd
        .getDelayedExecutionJobHandlerType();

    // Assert
    assertTrue(actualActivateProcessDefinitionCmd
        .getProcessDefinitionSuspensionState() instanceof SuspensionState.SuspensionStateImpl);
    assertEquals("activate-processdefinition", actualDelayedExecutionJobHandlerType);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Process Definition Key}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ActivateProcessDefinitionCmd#ActivateProcessDefinitionCmd(String, String, boolean, Date, String)}
   *   <li>{@link ActivateProcessDefinitionCmd#getDelayedExecutionJobHandlerType()}
   *   <li>
   * {@link ActivateProcessDefinitionCmd#getProcessDefinitionSuspensionState()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenProcessDefinitionKey() {
    // Arrange and Act
    ActivateProcessDefinitionCmd actualActivateProcessDefinitionCmd = new ActivateProcessDefinitionCmd("42",
        "Process Definition Key", true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42");
    String actualDelayedExecutionJobHandlerType = actualActivateProcessDefinitionCmd
        .getDelayedExecutionJobHandlerType();

    // Assert
    assertTrue(actualActivateProcessDefinitionCmd
        .getProcessDefinitionSuspensionState() instanceof SuspensionState.SuspensionStateImpl);
    assertEquals("activate-processdefinition", actualDelayedExecutionJobHandlerType);
  }
}
