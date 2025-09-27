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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class GetProcessInstanceCommentsCmdDiffblueTest {
  /**
   * Test {@link GetProcessInstanceCommentsCmd#GetProcessInstanceCommentsCmd(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@link GetProcessInstanceCommentsCmd#type} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * GetProcessInstanceCommentsCmd#GetProcessInstanceCommentsCmd(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GetProcessInstanceCommentsCmd.<init>(String)",
    "void GetProcessInstanceCommentsCmd.<init>(String, String)"
  })
  public void testNewGetProcessInstanceCommentsCmd_when42_thenReturnTypeIsNull() {
    // Arrange and Act
    GetProcessInstanceCommentsCmd actualGetProcessInstanceCommentsCmd =
        new GetProcessInstanceCommentsCmd("42");

    // Assert
    assertEquals("42", actualGetProcessInstanceCommentsCmd.processInstanceId);
    assertNull(actualGetProcessInstanceCommentsCmd.type);
  }

  /**
   * Test {@link GetProcessInstanceCommentsCmd#GetProcessInstanceCommentsCmd(String, String)}.
   *
   * <ul>
   *   <li>When {@code Type}.
   *   <li>Then return {@code Type}.
   * </ul>
   *
   * <p>Method under test: {@link
   * GetProcessInstanceCommentsCmd#GetProcessInstanceCommentsCmd(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GetProcessInstanceCommentsCmd.<init>(String)",
    "void GetProcessInstanceCommentsCmd.<init>(String, String)"
  })
  public void testNewGetProcessInstanceCommentsCmd_whenType_thenReturnType() {
    // Arrange and Act
    GetProcessInstanceCommentsCmd actualGetProcessInstanceCommentsCmd =
        new GetProcessInstanceCommentsCmd("42", "Type");

    // Assert
    assertEquals("42", actualGetProcessInstanceCommentsCmd.processInstanceId);
    assertEquals("Type", actualGetProcessInstanceCommentsCmd.type);
  }
}
