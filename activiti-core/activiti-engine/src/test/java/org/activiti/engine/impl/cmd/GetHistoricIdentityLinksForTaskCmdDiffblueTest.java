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
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;

public class GetHistoricIdentityLinksForTaskCmdDiffblueTest {
  /**
   * Test
   * {@link GetHistoricIdentityLinksForTaskCmd#GetHistoricIdentityLinksForTaskCmd(String, String)}.
   * <p>
   * Method under test:
   * {@link GetHistoricIdentityLinksForTaskCmd#GetHistoricIdentityLinksForTaskCmd(String, String)}
   */
  @Test
  public void testNewGetHistoricIdentityLinksForTaskCmd() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new GetHistoricIdentityLinksForTaskCmd(null, null));

  }

  /**
   * Test
   * {@link GetHistoricIdentityLinksForTaskCmd#GetHistoricIdentityLinksForTaskCmd(String, String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@link GetHistoricIdentityLinksForTaskCmd#taskId} is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetHistoricIdentityLinksForTaskCmd#GetHistoricIdentityLinksForTaskCmd(String, String)}
   */
  @Test
  public void testNewGetHistoricIdentityLinksForTaskCmd_when42_thenReturnTaskIdIs42() {
    // Arrange and Act
    GetHistoricIdentityLinksForTaskCmd actualGetHistoricIdentityLinksForTaskCmd = new GetHistoricIdentityLinksForTaskCmd(
        "42", "42");

    // Assert
    assertEquals("42", actualGetHistoricIdentityLinksForTaskCmd.processInstanceId);
    assertEquals("42", actualGetHistoricIdentityLinksForTaskCmd.taskId);
  }

  /**
   * Test
   * {@link GetHistoricIdentityLinksForTaskCmd#GetHistoricIdentityLinksForTaskCmd(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link GetHistoricIdentityLinksForTaskCmd#taskId} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetHistoricIdentityLinksForTaskCmd#GetHistoricIdentityLinksForTaskCmd(String, String)}
   */
  @Test
  public void testNewGetHistoricIdentityLinksForTaskCmd_whenNull_thenReturnTaskIdIsNull() {
    // Arrange and Act
    GetHistoricIdentityLinksForTaskCmd actualGetHistoricIdentityLinksForTaskCmd = new GetHistoricIdentityLinksForTaskCmd(
        null, "42");

    // Assert
    assertEquals("42", actualGetHistoricIdentityLinksForTaskCmd.processInstanceId);
    assertNull(actualGetHistoricIdentityLinksForTaskCmd.taskId);
  }
}
