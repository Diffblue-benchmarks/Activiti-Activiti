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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Set;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;

public class GetTasksLocalVariablesCmdDiffblueTest {
  /**
   * Test {@link GetTasksLocalVariablesCmd#GetTasksLocalVariablesCmd(Set)}.
   * <p>
   * Method under test:
   * {@link GetTasksLocalVariablesCmd#GetTasksLocalVariablesCmd(Set)}
   */
  @Test
  public void testNewGetTasksLocalVariablesCmd() {
    // Arrange, Act and Assert
    assertTrue((new GetTasksLocalVariablesCmd(new HashSet<>())).taskIds.isEmpty());
  }

  /**
   * Test {@link GetTasksLocalVariablesCmd#execute(CommandContext)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetTasksLocalVariablesCmd#execute(CommandContext)}
   */
  @Test
  public void testExecute_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new GetTasksLocalVariablesCmd(new HashSet<>())).execute(null));
  }
}