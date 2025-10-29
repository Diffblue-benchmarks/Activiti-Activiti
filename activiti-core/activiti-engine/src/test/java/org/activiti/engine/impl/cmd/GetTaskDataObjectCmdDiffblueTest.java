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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GetTaskDataObjectCmdDiffblueTest {
  /**
   * Method under test:
   * {@link GetTaskDataObjectCmd#GetTaskDataObjectCmd(String, String)}
   */
  @Test
  public void testNewGetTaskDataObjectCmd() {
    // Arrange and Act
    GetTaskDataObjectCmd actualGetTaskDataObjectCmd = new GetTaskDataObjectCmd("42", "Variable Name");

    // Assert
    assertEquals("42", actualGetTaskDataObjectCmd.taskId);
    assertEquals("Variable Name", actualGetTaskDataObjectCmd.variableName);
    assertNull(actualGetTaskDataObjectCmd.locale);
    assertFalse(actualGetTaskDataObjectCmd.withLocalizationFallback);
  }

  /**
   * Method under test:
   * {@link GetTaskDataObjectCmd#GetTaskDataObjectCmd(String, String, String, boolean)}
   */
  @Test
  public void testNewGetTaskDataObjectCmd2() {
    // Arrange and Act
    GetTaskDataObjectCmd actualGetTaskDataObjectCmd = new GetTaskDataObjectCmd("42", "Variable Name", "en", true);

    // Assert
    assertEquals("42", actualGetTaskDataObjectCmd.taskId);
    assertEquals("Variable Name", actualGetTaskDataObjectCmd.variableName);
    assertEquals("en", actualGetTaskDataObjectCmd.locale);
    assertTrue(actualGetTaskDataObjectCmd.withLocalizationFallback);
  }
}
