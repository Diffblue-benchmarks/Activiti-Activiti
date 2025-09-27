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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.runtime.ProcessInstanceBuilderImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class StartProcessInstanceByMessageCmdDiffblueTest {
  /**
   * Test {@link StartProcessInstanceByMessageCmd#StartProcessInstanceByMessageCmd(String, String,
   * Map, String)}.
   *
   * <p>Method under test: {@link
   * StartProcessInstanceByMessageCmd#StartProcessInstanceByMessageCmd(String, String, Map, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartProcessInstanceByMessageCmd.<init>(String, String, Map, String)"})
  public void testNewStartProcessInstanceByMessageCmd() {
    // Arrange and Act
    StartProcessInstanceByMessageCmd actualStartProcessInstanceByMessageCmd =
        new StartProcessInstanceByMessageCmd("Message Name", "Business Key", new HashMap<>(), "42");

    // Assert
    assertTrue(actualStartProcessInstanceByMessageCmd.processVariables.isEmpty());
  }

  /**
   * Test {@link
   * StartProcessInstanceByMessageCmd#StartProcessInstanceByMessageCmd(ProcessInstanceBuilderImpl)}.
   *
   * <ul>
   *   <li>Then return {@link StartProcessInstanceByMessageCmd#businessKey} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * StartProcessInstanceByMessageCmd#StartProcessInstanceByMessageCmd(ProcessInstanceBuilderImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartProcessInstanceByMessageCmd.<init>(ProcessInstanceBuilderImpl)"})
  public void testNewStartProcessInstanceByMessageCmd_thenReturnBusinessKeyIsNull() {
    // Arrange and Act
    StartProcessInstanceByMessageCmd actualStartProcessInstanceByMessageCmd =
        new StartProcessInstanceByMessageCmd(
            new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));

    // Assert
    assertNull(actualStartProcessInstanceByMessageCmd.businessKey);
    assertNull(actualStartProcessInstanceByMessageCmd.messageName);
    assertNull(actualStartProcessInstanceByMessageCmd.tenantId);
    assertNull(actualStartProcessInstanceByMessageCmd.processVariables);
    assertNull(actualStartProcessInstanceByMessageCmd.transientVariables);
  }

  /**
   * Test {@link StartProcessInstanceByMessageCmd#execute(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link StartProcessInstanceByMessageCmd#execute(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.runtime.ProcessInstance StartProcessInstanceByMessageCmd.execute(CommandContext)"
  })
  public void testExecute_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            new StartProcessInstanceByMessageCmd(
                    new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()))
                .execute(null));
  }
}
