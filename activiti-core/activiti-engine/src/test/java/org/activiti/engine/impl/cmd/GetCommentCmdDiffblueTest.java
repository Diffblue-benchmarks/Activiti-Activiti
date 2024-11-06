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
import org.junit.Test;

public class GetCommentCmdDiffblueTest {
  /**
   * Test {@link GetCommentCmd#GetCommentCmd(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@link GetCommentCmd#commentId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetCommentCmd#GetCommentCmd(String)}
   */
  @Test
  public void testNewGetCommentCmd_when42_thenReturnCommentIdIs42() {
    // Arrange, Act and Assert
    assertEquals("42", (new GetCommentCmd("42")).commentId);
  }

  /**
   * Test {@link GetCommentCmd#GetCommentCmd(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetCommentCmd#GetCommentCmd(String)}
   */
  @Test
  public void testNewGetCommentCmd_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new GetCommentCmd(null));
  }
}
