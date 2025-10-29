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
import org.junit.Test;

public class GetProcessInstanceCommentsCmdDiffblueTest {
  /**
   * Method under test:
   * {@link GetProcessInstanceCommentsCmd#GetProcessInstanceCommentsCmd(String)}
   */
  @Test
  public void testNewGetProcessInstanceCommentsCmd() {
    // Arrange and Act
    GetProcessInstanceCommentsCmd actualGetProcessInstanceCommentsCmd = new GetProcessInstanceCommentsCmd("42");

    // Assert
    assertEquals("42", actualGetProcessInstanceCommentsCmd.processInstanceId);
    assertNull(actualGetProcessInstanceCommentsCmd.type);
  }

  /**
   * Method under test:
   * {@link GetProcessInstanceCommentsCmd#GetProcessInstanceCommentsCmd(String, String)}
   */
  @Test
  public void testNewGetProcessInstanceCommentsCmd2() {
    // Arrange and Act
    GetProcessInstanceCommentsCmd actualGetProcessInstanceCommentsCmd = new GetProcessInstanceCommentsCmd("42", "Type");

    // Assert
    assertEquals("42", actualGetProcessInstanceCommentsCmd.processInstanceId);
    assertEquals("Type", actualGetProcessInstanceCommentsCmd.type);
  }
}
