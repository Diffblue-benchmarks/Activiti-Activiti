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
package org.activiti.engine.impl.cfg.multitenant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;

public class ExecuteSchemaOperationCommandDiffblueTest {
  /**
   * Test
   * {@link ExecuteSchemaOperationCommand#ExecuteSchemaOperationCommand(String)}.
   * <p>
   * Method under test:
   * {@link ExecuteSchemaOperationCommand#ExecuteSchemaOperationCommand(String)}
   */
  @Test
  public void testNewExecuteSchemaOperationCommand() {
    // Arrange and Act
    ExecuteSchemaOperationCommand actualExecuteSchemaOperationCommand = new ExecuteSchemaOperationCommand(
        "Schema Operation");

    // Assert
    assertEquals("Schema Operation", actualExecuteSchemaOperationCommand.schemaOperation);
    assertNull(actualExecuteSchemaOperationCommand.tenantInfoHolder);
  }

  /**
   * Test {@link ExecuteSchemaOperationCommand#execute(CommandContext)}.
   * <ul>
   *   <li>Given
   * {@link ExecuteSchemaOperationCommand#ExecuteSchemaOperationCommand(String)}
   * with {@code Schema Operation}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecuteSchemaOperationCommand#execute(CommandContext)}
   */
  @Test
  public void testExecute_givenExecuteSchemaOperationCommandWithSchemaOperation_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new ExecuteSchemaOperationCommand("Schema Operation")).execute(null));
  }
}
