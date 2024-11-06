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
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;

public class GetBpmnModelCmdDiffblueTest {
  /**
   * Test {@link GetBpmnModelCmd#GetBpmnModelCmd(String)}.
   * <p>
   * Method under test: {@link GetBpmnModelCmd#GetBpmnModelCmd(String)}
   */
  @Test
  public void testNewGetBpmnModelCmd() {
    // Arrange, Act and Assert
    assertEquals("42", (new GetBpmnModelCmd("42")).processDefinitionId);
  }

  /**
   * Test {@link GetBpmnModelCmd#execute(CommandContext)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetBpmnModelCmd#execute(CommandContext)}
   */
  @Test
  public void testExecute_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new GetBpmnModelCmd(null)).execute(null));
  }
}
