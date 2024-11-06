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
import org.junit.Test;

public class GetTaskVariablesCmdDiffblueTest {
  /**
   * Test
   * {@link GetTaskVariablesCmd#GetTaskVariablesCmd(String, Collection, boolean)}.
   * <p>
   * Method under test:
   * {@link GetTaskVariablesCmd#GetTaskVariablesCmd(String, Collection, boolean)}
   */
  @Test
  public void testNewGetTaskVariablesCmd() {
    // Arrange and Act
    GetTaskVariablesCmd actualGetTaskVariablesCmd = new GetTaskVariablesCmd("42", new ArrayList<>(), true);

    // Assert
    Collection<String> collection = actualGetTaskVariablesCmd.variableNames;
    assertTrue(collection instanceof List);
    assertEquals("42", actualGetTaskVariablesCmd.taskId);
    assertTrue(collection.isEmpty());
    assertTrue(actualGetTaskVariablesCmd.isLocal);
  }
}
