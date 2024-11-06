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

public class HasTaskVariableCmdDiffblueTest {
  /**
   * Test {@link HasTaskVariableCmd#HasTaskVariableCmd(String, String, boolean)}.
   * <p>
   * Method under test:
   * {@link HasTaskVariableCmd#HasTaskVariableCmd(String, String, boolean)}
   */
  @Test
  public void testNewHasTaskVariableCmd() {
    // Arrange and Act
    HasTaskVariableCmd actualHasTaskVariableCmd = new HasTaskVariableCmd("42", "Variable Name", true);

    // Assert
    assertEquals("42", actualHasTaskVariableCmd.taskId);
    assertEquals("Variable Name", actualHasTaskVariableCmd.variableName);
    assertTrue(actualHasTaskVariableCmd.isLocal);
  }
}
