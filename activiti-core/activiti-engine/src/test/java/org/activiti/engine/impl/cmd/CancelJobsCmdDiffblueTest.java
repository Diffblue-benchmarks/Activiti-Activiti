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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CancelJobsCmdDiffblueTest {
  /**
   * Test {@link CancelJobsCmd#CancelJobsCmd(List)}.
   *
   * <p>Method under test: {@link CancelJobsCmd#CancelJobsCmd(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CancelJobsCmd.<init>(List)"})
  public void testNewCancelJobsCmd() {
    // Arrange, Act and Assert
    assertTrue(new CancelJobsCmd(new ArrayList<>()).jobIds.isEmpty());
  }

  /**
   * Test {@link CancelJobsCmd#CancelJobsCmd(String)}.
   *
   * <ul>
   *   <li>Then return {@link CancelJobsCmd#jobIds} size is one.
   * </ul>
   *
   * <p>Method under test: {@link CancelJobsCmd#CancelJobsCmd(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CancelJobsCmd.<init>(String)"})
  public void testNewCancelJobsCmd_thenReturnJobIdsSizeIsOne() {
    // Arrange, Act and Assert
    List<String> stringList = new CancelJobsCmd("42").jobIds;
    assertEquals(1, stringList.size());
    assertEquals("42", stringList.get(0));
  }

  /**
   * Test {@link CancelJobsCmd#execute(CommandContext)}.
   *
   * <ul>
   *   <li>Given {@link CancelJobsCmd#CancelJobsCmd(List)} with jobIds is {@link
   *       ArrayList#ArrayList()}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CancelJobsCmd#execute(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Void CancelJobsCmd.execute(CommandContext)"})
  public void testExecute_givenCancelJobsCmdWithJobIdsIsArrayList_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new CancelJobsCmd(new ArrayList<>()).execute(null));
  }
}
