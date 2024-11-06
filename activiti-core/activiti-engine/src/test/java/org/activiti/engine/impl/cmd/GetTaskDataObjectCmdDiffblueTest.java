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
   * Test
   * {@link GetTaskDataObjectCmd#GetTaskDataObjectCmd(String, String, String, boolean)}.
   * <ul>
   *   <li>When {@code en}.</li>
   *   <li>Then return {@link GetTaskDataObjectCmd#locale} is {@code en}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetTaskDataObjectCmd#GetTaskDataObjectCmd(String, String, String, boolean)}
   */
  @Test
  public void testNewGetTaskDataObjectCmd_whenEn_thenReturnLocaleIsEn() {
    // Arrange and Act
    GetTaskDataObjectCmd actualGetTaskDataObjectCmd = new GetTaskDataObjectCmd("42", "Variable Name", "en", true);

    // Assert
    assertEquals("42", actualGetTaskDataObjectCmd.taskId);
    assertEquals("Variable Name", actualGetTaskDataObjectCmd.variableName);
    assertEquals("en", actualGetTaskDataObjectCmd.locale);
    assertTrue(actualGetTaskDataObjectCmd.withLocalizationFallback);
  }

  /**
   * Test {@link GetTaskDataObjectCmd#GetTaskDataObjectCmd(String, String)}.
   * <ul>
   *   <li>When {@code Variable Name}.</li>
   *   <li>Then return {@link GetTaskDataObjectCmd#locale} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetTaskDataObjectCmd#GetTaskDataObjectCmd(String, String)}
   */
  @Test
  public void testNewGetTaskDataObjectCmd_whenVariableName_thenReturnLocaleIsNull() {
    // Arrange and Act
    GetTaskDataObjectCmd actualGetTaskDataObjectCmd = new GetTaskDataObjectCmd("42", "Variable Name");

    // Assert
    assertEquals("42", actualGetTaskDataObjectCmd.taskId);
    assertEquals("Variable Name", actualGetTaskDataObjectCmd.variableName);
    assertNull(actualGetTaskDataObjectCmd.locale);
    assertFalse(actualGetTaskDataObjectCmd.withLocalizationFallback);
  }
}
