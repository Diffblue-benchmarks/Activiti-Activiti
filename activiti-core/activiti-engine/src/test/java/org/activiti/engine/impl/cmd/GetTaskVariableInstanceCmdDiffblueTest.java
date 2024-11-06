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
import org.junit.Test;

public class GetTaskVariableInstanceCmdDiffblueTest {
  /**
   * Test
   * {@link GetTaskVariableInstanceCmd#GetTaskVariableInstanceCmd(String, String, boolean)}.
   * <p>
   * Method under test:
   * {@link GetTaskVariableInstanceCmd#GetTaskVariableInstanceCmd(String, String, boolean)}
   */
  @Test
  public void testNewGetTaskVariableInstanceCmd() {
    // Arrange and Act
    GetTaskVariableInstanceCmd actualGetTaskVariableInstanceCmd = new GetTaskVariableInstanceCmd("42", "Variable Name",
        true);

    // Assert
    assertEquals("42", actualGetTaskVariableInstanceCmd.taskId);
    assertEquals("Variable Name", actualGetTaskVariableInstanceCmd.variableName);
    assertTrue(actualGetTaskVariableInstanceCmd.isLocal);
  }
}
